package employeeManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(
					"com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("MySQLドライバが読み込めません。", e);
		}
		return DriverManager.getConnection(DBProperties.getLocalUrl(), DBProperties.getLocalUser(),
				DBProperties.getLocalPassword());
	}
}
