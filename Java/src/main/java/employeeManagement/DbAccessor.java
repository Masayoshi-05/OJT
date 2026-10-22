package employeeManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * DB操作を担当する
 */
public class DbAccessor {
	private static final String INSERT_SQL = "INSERT INTO employee (employeeNum,kanjiLastName,kanjiFirstName,romanLastName,romanFirstName,email,birthday,joinDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE employee SET employeeNum = ?, kanjiLastName = ?, kanjiFirstName = ?, romanLastName = ?, romanFirstName = ?, email = ?, birthday = ?, joinDate = ? WHERE id = ?";
	private static final String DELETE_SQL = "DELETE FROM employee WHERE id = ?";
	private static final String SELECT_SQL = "SELECT ? FROM employee";
	private static final String EMPLOYEE_NUM_SELECT_SQL = "SELECT COUNT(*) FROM employee WHERE employeeNum = ?";
	private static final String EMAIL_SELECT_SQL = "SELECT COUNT(*) FROM employee WHERE email = ?";

	private final static String ERROR_DUPRICATION = "既に使用されている値です。";
	private Connection connection;

	public DbAccessor(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 社員情報登録
	 * @param employee
	 * @throws SQLException
	 */
	public void insertEmployee(Employee employee) throws SQLException {
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

	/**
	 * 社員情報更新
	 * @param employee
	 * @throws SQLException
	 */
	public void updateEmployee(Employee employee) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
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
			statement.setLong(9, employee.getID());
			statement.executeUpdate();
		}
	}

	/**
	 * 社員情報削除
	 * @param employee
	 * @throws SQLException
	 */
	public void deleteEmployee(Employee employee) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
			statement.setLong(1, employee.getID());
			statement.executeUpdate();
		}
	}

	/**
	 * DBから社員情報を取得し、リストにして返す
	 * @return employees List
	 * @throws SQLException
	 */
	public List<Employee> makeEmployeeList() throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(SELECT_SQL)) {
			statement.setString(1, "*");
			ResultSet resultSet = statement.executeQuery();
			List<Employee> employees = new ArrayList<>();
			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setID(resultSet.getInt("id"));
				employee.setEmployeeNum(resultSet.getString("employeenum"));
				employee.setKanjiLastName(resultSet.getString("kanjiLastName"));
				employee.setKanjiFirstName(resultSet.getString("setKanjiFirstName"));
				employee.setRomanLastName(resultSet.getString("romanLastName"));
				employee.setRomanFirstName(resultSet.getString("RomanFirstName"));
				employee.setBirthday(resultSet.getString("Birthday"));
				employee.setJoinDate(resultSet.getString("JoinDate"));
				employees.add(employee);
			}
			return employees;
		}
	}

	/**
	 * 社員番号の重複チェック
	 * @param value
	 * @return 重複があればエラーメッセージ、なければnull
	 * @throws SQLException
	 */
	public String checkEmployeeNumDuplication(String value) throws SQLException {
		String employeeNumSql = EMPLOYEE_NUM_SELECT_SQL;
		return checkDuplication(connection, employeeNumSql, value);
	}

	/**
	 * メールアドレスの重複チェック
	 * @param value
	 * @return 重複があればエラーメッセージ、なければnull
	 * @throws SQLException
	 */
	public String checkEmailDuplication(String value) throws SQLException {
		String emailSql = EMAIL_SELECT_SQL;
		return checkDuplication(connection, emailSql, value);
	}

	/**
	 * 重複チェック処理
	 * @param connection
	 * @param sql
	 * @param value
	 * @return 重複があればエラーメッセージ、なければnull
	 * @throws SQLException
	 */
	private String checkDuplication(Connection connection, String sql, String value) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, value);
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