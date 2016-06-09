package javaexp.com.jdbcExp;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.RowSetProvider;

public class RowsetExp {
	static final String url = "jdbc:sqlite:test.db";

	public static void main(String[] args) {

		try (RowSet rs = RowSetProvider.newFactory().createJdbcRowSet()) {
			rs.setType(ResultSet.TYPE_FORWARD_ONLY);
			rs.setConcurrency(ResultSet.CONCUR_READ_ONLY);

			String sql = "select * from company order by id";
			rs.setCommand(sql);
			rs.setUrl(url);
			rs.addRowSetListener(new RowListenerExp());
			rs.execute();

			FirstSqlLiteExp.getColumns(rs);
			while (rs.next()) {
				FirstSqlLiteExp.writeRows(rs);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}

class RowListenerExp implements RowSetListener {

	@Override
	public void rowSetChanged(RowSetEvent event) {
		// do nothing

	}

	@Override
	public void rowChanged(RowSetEvent event) {

		try {
			if (event.getSource() instanceof RowSet) {
				((RowSet) event.getSource()).execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cursorMoved(RowSetEvent event) {
		// do nothing

	}

}
