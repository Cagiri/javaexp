package javaexp.com.jdbcExp;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class FirstSqlLiteExp {

	public static void main(String[] args) {

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection c = DriverManager.getConnection("jdbc:sqlite:test2.db");
				Statement stmt = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

			// getConnWarnings(c);

//			 createStatement(c, stmt);

			// insertStatement(c, stmt);

			// selectStatement(c, stmt);

			// rowCountExp(c, stmt);

			// updateStatement(c, stmt);

//			selectRowCount(c, stmt);

			// absoluteExp(c, stmt);

			// dataBaseMetaDataExp(c);

//			preparedStatementExp(c);

//			preparedInsertExp(c);
			
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}



	private static void preparedInsertExp(Connection c) throws SQLException {
		String sql = "insert into company (id,name,age,address,salary) values (?,?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, 12);
		ps.setString(2, "first name");
		ps.setInt(3, 45);
		ps.setString(4, "somewhere over the rainbow");
		ps.setDouble(5, 10000d);
		if (ps.execute()) {
			System.out.println("data saved...");
		}
	}



	private static void preparedStatementExp(Connection c) throws SQLException {
		String sql = "select * from company where id = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, 1);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			writeRows(rs);
		}
	}

	
	
	private static void getConnWarnings(Connection c) throws SQLException {
		SQLWarning sqlwarn = c.getWarnings();
		while (sqlwarn != null) {
			System.out.println(sqlwarn.getLocalizedMessage());
			sqlwarn = sqlwarn.getNextWarning();
		}
	}

	private static void dataBaseMetaDataExp(Connection c) throws SQLException {
		DatabaseMetaData dmd = c.getMetaData();
		ResultSet rs = dmd.getColumns(null, "%", "%", "%");
		Logger log = Logger.getLogger(FirstSqlLiteExp.class.getCanonicalName());
		log.log(Level.INFO, dmd.getDriverName());
		log.log(Level.INFO, dmd.getDriverVersion());
		while (rs.next()) {
			System.out.print(rs.getString("Table_Name") + " - ");
			System.out.println(rs.getString("Column_Name"));
		}
	}

	private static void updateStatement(Connection c, Statement stmt) throws SQLException {
		String sql = "select * from company order by id";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			rs.updateDouble("SALARY", rs.getDouble("SALARY") + 500);
			rs.updateRow();
		}
	}

	private static void rowCountExp(Connection c, Statement stmt) throws SQLException {
		String sql = "select * from company";
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			System.out.print("count : " + rs.getRow());
		}
		System.out.println();
	}

	private static void absoluteExp(Connection c, Statement stmt) throws SQLException {
		String sql = "select * from company order by id";
		ResultSet rs = stmt.executeQuery(sql);
		getColumns(rs);
		rs.absolute(5);
		writeRows(rs);

	}



	public static void writeRows(ResultSet rs) throws SQLException {
		System.out.print(leftJustify(String.valueOf(rs.getInt("ID"))));
		System.out.print(leftJustify(rs.getString("NAME")));
		System.out.print(leftJustify(String.valueOf(rs.getInt("AGE"))));
		System.out.print(leftJustify(rs.getString("ADDRESS")));
		System.out.print(leftJustify(String.valueOf(rs.getDouble("SALARY"))));
		System.out.println("");
	}

	private static String leftJustify(String s) {

		return String.format("%1$-20S", s);
	}

	public static void selectRowCount(Connection c, Statement stmt) throws SQLException {

		boolean control = false;
		String sql = "select * from company order by id";
		System.out.println("Any Value Returned : " + (control = stmt.execute(sql)));
		if (control) {
			ResultSet rs = stmt.getResultSet();
			getColumns(rs);
			while (rs.next()) {
				writeRows(rs);
			}
		}
	}

	public static void getColumns(ResultSet rs) throws SQLException {
		ResultSetMetaData rm = rs.getMetaData();
		for (int i = 1; i <= rm.getColumnCount(); i++) {
			System.out.print(leftJustify(rm.getColumnName(i)));
		}
		System.out.println();
	}

	private static void selectStatement(Connection c, Statement stmt) throws SQLException {
		String sql = "select * from company order by id";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			writeRows(rs);
		}
	}

	private static void createStatement(Connection c, Statement stmt) throws SQLException {
		String sql = "CREATE TABLE COMPANY " + "(ID INT PRIMARY KEY     NOT NULL," + " NAME           TEXT    NOT NULL, "
				+ " AGE            INT     NOT NULL, " + " ADDRESS        CHAR(50), " + " SALARY         REAL)";
		stmt.executeUpdate(sql);
	}

	private static void insertStatement(Connection c, Statement stmt) throws SQLException {
		for (int i = 0; i < 10; i++) {

			String sql2 = "insert into company (id,name,age,address,salary) values (" + (i + 1) + "" + ",'test user" + i + "'" + "," + (i + 30)
					+ ",'anywhere" + (i + 1) + "'" + "," + (1500 + i * 500) + ")";
			stmt.executeUpdate(sql2);
		}
	}
}
