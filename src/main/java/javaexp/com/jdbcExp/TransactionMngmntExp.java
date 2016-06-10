package javaexp.com.jdbcExp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

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
			System.out.println(c.getMetaData().supportsSavepoints());
			c.setAutoCommit(false);

			st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			st.executeUpdate("insert into company (id,name,age,address,salary) values (13,'first name13',57,'anywhere13',15500)");
			
			Savepoint s = c.setSavepoint();
			st.executeUpdate("insert into company (id,name,age,address,salary) values (14,'first name14',57,'anywhere14',15500)");
			
			st.executeUpdate("insert into company (id,name,age,address,salary) values (15,'first name15',57,'anywhere15',15500)");
			c.rollback(s);
			c.releaseSavepoint(s);
			c.commit();
			
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
