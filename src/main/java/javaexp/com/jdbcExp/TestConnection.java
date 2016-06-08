package javaexp.com.jdbcExp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestConnection {

   public static void main(String[] args) {
       Connection conn = null;
       Statement stmt = null;
       String masterServer = "";
       String slaveServer = "";
       String connServer = "";
       SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
       int i = 0;
       boolean readonly = (args.length >= 1) ? "true".equals(args[0]) : false;
       try {
           // specify connection to three nodes of aurora
//           conn = DriverManager.getConnection("jdbc:mariadb://webexpdb.cubj2gkt5gv5.us-west-2.rds.amazonaws.com:3306/webexpdb","cagiri", "111222333a");
    	   
    	   String driver = "com.mysql.jdbc.Driver";
    	    String connection = "jdbc:mysql://awsdb.cubj2gkt5gv5.us-west-2.rds.amazonaws.com:3306/awstestdb";
    	    String user = "awsuser";
    	    String password = "111222333a";
    	    Class.forName(driver);
    	    Connection con = DriverManager.getConnection(connection, user, password);
    	    if (!con.isClosed()) {
    	      con.close();
    	    }

           conn.setReadOnly(readonly); //read on master or replica
           while (i < 100) {
               try {
                   stmt = conn.createStatement();
                   slaveServer="";
                   // read server information from INFORMATION_SCHEMA
                   ResultSet rs = stmt.executeQuery("SELECT SERVER_ID, SESSION_ID FROM INFORMATION_SCHEMA.REPLICA_HOST_STATUS");
                   while (rs.next()) {
                       if (rs.getString(2).equals("MASTER_SESSION_ID"))
                           masterServer = rs.getString(1); // the node that is the master currently has a session named MASTER_SESSION_ID
                       else
                           slaveServer += (("".equals(slaveServer)) ? "" : ",") + rs.getString(1); // other nodes ares slaves
                   }

                   // get the server that is used by the current connection
                   String url = conn.getMetaData().getURL();
                   connServer = conn.getMetaData().getURL().substring(url.indexOf("//") + 2 , url.indexOf("."));

               } catch (SQLException e) {
                   continue;
               } catch (NullPointerException ne) {
                   ne.printStackTrace();
               }
               // print out timestamp and servers
               System.out.println(dateFormat.format(new Date()) + " connected-to:"
                       + connServer + " master:" + masterServer + " slave:"
                       + slaveServer);
               i++;
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e1) {
		e1.printStackTrace();
	} finally {
           try {
               if (stmt != null) {
                   stmt.close();
               }
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException sqle) {
               //eat exception
           }
       }
   }
}