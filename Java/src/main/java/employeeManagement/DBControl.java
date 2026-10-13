package employeeManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DBControl {
	public static final String insertSQL = "INSERT INTO employee (employeeNum,kanjiLastName,kanjiFirstName,romanLastName,romanFirstName,email,birthday,joinDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String EmployeeNumSelectSQL = "SELECT COUNT(*) FROM employee WHERE employeeNum = ?";
	public static final String emailSelectSQL = "SELECT COUNT(*) FROM employee WHERE email = ?";
	
	public static String getInsertSQL() {
		return insertSQL;
	}

	public static String getEmployeeNumSelectSQL() {
		return EmployeeNumSelectSQL;
	}

	public static String getEmailSelectSQL() {
		return emailSelectSQL;
	}

	public void insertEmployee(Connection connection, String sql, Employee employee) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
			statement.setString(1, employee.getEmployeeNum());
			statement.setString(2, employee.getKanjiLastName());
			statement.setString(3, employee.getKanjiFirstName());
			statement.setString(4, employee.getRomanLastName());
			statement.setString(5, employee.getRomanFirstName());
			if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
				statement.setNull(6, Types.VARCHAR);
			} else {
				statement.setString(6, employee.getEmail());
			}
			if (employee.getBirthday() == null || employee.getBirthday().isEmpty()) {
				statement.setNull(7, Types.DATE);
			} else {
				statement.setDate(7, java.sql.Date.valueOf(employee.getBirthday()));
			}
			if (employee.getJoinDate() == null || employee.getJoinDate().isEmpty()) {
				statement.setNull(8, Types.DATE);
			} else {
				statement.setDate(8, java.sql.Date.valueOf(employee.getJoinDate()));
			}

			statement.executeUpdate();
		}
	}

	public static boolean checkDuplication(Connection connection, String sql, String value)
			throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, value);
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				return resultSet.getInt(1) > 0;
			}
		}
	}
}