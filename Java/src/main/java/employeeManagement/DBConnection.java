package employeeManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB接続を担当する
 */
public class DbConnection {

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(DbProperties.getUrl(), DbProperties.getUser(),
				DbProperties.getPassword());
	}
}
