package javaexp.com.jdbcExp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionMngmntExp {

	public static void main(String[] args) {

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		Connection c = null;
		Statement st = null;
		try {
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			c.rollback();

			st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate("insert into company (id,name,age,address,salary) values (11,'first name1',55,'anywhere11',12500)");
			st.executeUpdate("insert into company (id,name,age,address,salary) values (14,'first name4',56,'anywhere14',13500)");
			
			c.commit();
			
			st.executeUpdate("insert into company (id,name,age,address,salary) values (15,'first name5',57,'anywhere15',15500)");
			c.rollback();
			
			FirstSqlLiteExp.selectRowCount(c,c.createStatement());
			
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			if (c != null) {
				try {
					c.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			System.exit(0);
		}finally {
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
