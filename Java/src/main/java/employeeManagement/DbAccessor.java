package employeeManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import definition.EmployeeKeys;

public class DbAccessor {
	private static final String INSERT_SQL = "INSERT INTO employee (employeeNum,kanjiLastName,kanjiFirstName,romanLastName,romanFirstName,email,birthday,joinDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String EMPLOYEE_NUM_SELECT_SQL = "SELECT COUNT(*) FROM employee WHERE employeeNum = ?";
	private static final String EMAIL_SELECT_SQL = "SELECT COUNT(*) FROM employee WHERE email = ?";

	private final static String ERROR_DUPRICATION = "既に使用されている値です。";

	public void insertEmployee(Connection connection, Employee employee) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
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

	public String checkDuplication(Connection connection, String value, String key) throws SQLException {
		switch (key) {
		case EmployeeKeys.EMPLOYEE_NUM:
			checkEmployeeNumDuplication(connection, value);
		case EmployeeKeys.EMAIL: 
			checkEmailDuplication(connection, value);
			
		default:
			return null;
		}
	}

	public String checkEmployeeNumDuplication(Connection connection, String employeeNumValue) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(EMPLOYEE_NUM_SELECT_SQL)) {
			statement.setString(1, employeeNumValue);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			if (resultSet.getInt(1) > 0) {
				return ERROR_DUPRICATION;
			} else {
				return null;
			}
		}
	}

	public String checkEmailDuplication(Connection connection, String emailValue) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(EMAIL_SELECT_SQL)) {
			statement.setString(1, emailValue);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			if (resultSet.getInt(1) > 0) {
				return ERROR_DUPRICATION;
			} else {
				return null;
			}

		}
	}

}