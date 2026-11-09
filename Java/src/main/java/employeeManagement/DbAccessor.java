package employeeManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import definition.EmployeeKeys;

/**
 * DB操作を担当する
 */
public class DbAccessor {
	private static final String SQL_INSERT = "INSERT INTO employee (employeeNum,kanjiLastName,kanjiFirstName,romanLastName,romanFirstName,email,birthday,joinDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE employee SET employeeNum = ?, kanjiLastName = ?, kanjiFirstName = ?, romanLastName = ?, romanFirstName = ?, email = ?, birthday = ?, joinDate = ? WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM employee WHERE id = ?";
	private static final String SQL_SELECT_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE id = ?";
	private static final String SQL_COUNT_DUPLICATED_EMPLOYEE_NUM = "SELECT COUNT(*) FROM employee WHERE employeeNum = ?";
	private static final String SQL_COUNT_DUPLICATED_EMAIL = "SELECT COUNT(*) FROM employee WHERE email = ?";
	private static final String SQL_COUNT_DUPLICATED_EMPLOYEE_NUM_AND_DIFFERRNT_ID = "SELECT COUNT(*) FROM employee WHERE employeeNum = ? AND id <> ?";
	private static final String SQL_COUNT_DUPLICATED_EMAIL_AND_DIFFERENT_ID = "SELECT COUNT(*) FROM employee WHERE email = ? AND id <> ?";
	
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
		try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
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
		try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
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
		try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
			statement.setLong(1, employee.getID());
			statement.executeUpdate();
		}
	}

	/**
	 * IDから社員情報を検索・取得
	 * @param employee
	 * @throws SQLException
	 */
	public Employee getEmployeeById(int id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EMPLOYEE_BY_ID)) {
			Employee employee = new Employee();
			employee.setID(id);
			statement.setLong(1, employee.getID());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				employee.setEmployeeNum(resultSet.getString(EmployeeKeys.EMPLOYEE_NUM));
				employee.setKanjiLastName(resultSet.getString(EmployeeKeys.KANJI_LAST_NAME));
				employee.setKanjiFirstName(resultSet.getString(EmployeeKeys.KANJI_FIRST_NAME));
				employee.setRomanLastName(resultSet.getString(EmployeeKeys.ROMAN_LAST_NAME));
				employee.setRomanFirstName(resultSet.getString(EmployeeKeys.ROMAN_FIRST_NAME));
				employee.setEmail(resultSet.getString(EmployeeKeys.EMAIL));
				employee.setBirthday(resultSet.getString(EmployeeKeys.BIRTHDAY));
				employee.setJoinDate(resultSet.getString(EmployeeKeys.JOIN_DATE));
				
				return employee;
			}
			return null;
		}
	}

	/**
	 * 社員番号の重複チェック
	 * @param employeeNum
	 * @return 重複があればエラーメッセージ、なければnull
	 * @throws SQLException
	 */
	public String checkEmployeeNumDuplication(String employeeNum) throws SQLException {
		return checkDuplication(connection, SQL_COUNT_DUPLICATED_EMPLOYEE_NUM, employeeNum);
	}

	/**
	 * 社員番号の重複チェック(ID指定)
	 * @param employeeNum
	 * @param id
	 * @return 同じidのデータを除いて重複があればエラーメッセージ、なければnull
	 * @throws SQLException
	 */
	public String checkEmployeeNumDuplication(String employeeNum, int id) throws SQLException {
		return checkDuplication(connection, SQL_COUNT_DUPLICATED_EMPLOYEE_NUM_AND_DIFFERRNT_ID, employeeNum, id);
	}

	/**
	 * メールアドレスの重複チェック
	 * @param email
	 * @return 重複があればエラーメッセージ、なければnull
	 * @throws SQLException
	 */
	public String checkEmailDuplication(String email) throws SQLException {
		return checkDuplication(connection, SQL_COUNT_DUPLICATED_EMAIL, email);
	}

	/**
	 * メールアドレスの重複チェック(ID指定)
	 * @param email
	 * @param id
	 * @return 同じidのデータを除いて重複があればエラーメッセージ、なければnull
	 * @throws SQLException
	 */
	public String checkEmailDuplication(String email, int id) throws SQLException {
		return checkDuplication(connection, SQL_COUNT_DUPLICATED_EMAIL_AND_DIFFERENT_ID, email, id);
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

	/**
	 * IDを指定し、そのIDの社員は省いて重複チェック処理
	 * @param connection
	 * @param sql
	 * @param value
	 * @param id
	 * @return 重複があればエラーメッセージ、なければnull
	 * @throws SQLException
	 */
	private String checkDuplication(Connection connection, String sql, String value, int id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, value);
			statement.setInt(2, id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			if (resultSet.getInt(1) > 0) {
				return ERROR_DUPRICATION;
			} else {
				return null;
			}
		}
	}
	
	/**
	 * DBから得た社員情報をリスト化
	 * @param resultSet
	 * @return employees リスト
	 * @throws SQLException
	 */
	private List<Employee> makeEmployeeLists(ResultSet resultSet) throws SQLException {
		List<Employee> employees = new ArrayList<>();
		while (resultSet.next()) {
			Employee employee = new Employee();
			employee.setID(resultSet.getInt(EmployeeKeys.ID));
			employee.setEmployeeNum(resultSet.getString(EmployeeKeys.EMPLOYEE_NUM));
			employee.setKanjiLastName(resultSet.getString(EmployeeKeys.KANJI_LAST_NAME));
			employee.setKanjiFirstName(resultSet.getString(EmployeeKeys.KANJI_FIRST_NAME));
			employee.setRomanLastName(resultSet.getString(EmployeeKeys.ROMAN_LAST_NAME));
			employee.setRomanFirstName(resultSet.getString(EmployeeKeys.ROMAN_FIRST_NAME));
			employee.setEmail(resultSet.getString(EmployeeKeys.EMAIL));
			employee.setBirthday(resultSet.getString(EmployeeKeys.BIRTHDAY));
			employee.setJoinDate(resultSet.getString(EmployeeKeys.JOIN_DATE));
			employees.add(employee);
		}
		return employees;
	}

	/**
	 * StringBuilderでSQL文を作成
	 * @param sql
	 * @param paramName
	 * @param value
	 * @param parameters
	 */
	private void buildSQL(StringBuilder sql, String paramName, String value, List<String> parameters) {
		if (value != null && !value.isEmpty()) {
			sql.append(" AND " + paramName + " LIKE ?");
			parameters.add("%" + value + "%");
		}
	}

	private String buildSearchWhere(Employee employee,List<String> parameters) {
		StringBuilder where = new StringBuilder(" WHERE 1 = 1");
		buildSQL(where, EmployeeKeys.EMPLOYEE_NUM, employee.getEmployeeNum(), parameters);
		buildSQL(where,
				"CONCAT("
						+ EmployeeKeys.KANJI_LAST_NAME
						+ ","
						+ EmployeeKeys.KANJI_FIRST_NAME
						+ ")",
				employee.getKanjiName(), parameters);
		buildSQL(where,
				"CONCAT("
						+ EmployeeKeys.ROMAN_LAST_NAME
						+ ","
						+ EmployeeKeys.ROMAN_FIRST_NAME
						+ ")",
				employee.getRomanName(), parameters);
		buildSQL(where, EmployeeKeys.EMAIL, employee.getEmail(), parameters);
		buildSQL(where, EmployeeKeys.BIRTHDAY, employee.getBirthday(), parameters);
		buildSQL(where, EmployeeKeys.JOIN_DATE, employee.getJoinDate(), parameters);
		return where.toString();
	}
	
	/**
	 * 社員情報検索
	 * @param employee
	 * @param page
	 * @param pageSize
	 * @return 検索条件に基づいたページサイズ分の件数の社員情報リスト
	 * @throws SQLException
	 */
	public List<Employee> searchEmployees(Employee employee, int page,int pageSize) throws SQLException {
		List<String> parameters = new ArrayList<>();

		String searchSql = "SELECT * FROM employee" +  buildSearchWhere(employee, parameters) + " LIMIT ? OFFSET ?";
		
		try (PreparedStatement statement = connection.prepareStatement(searchSql)) {
			int index = 1;
			for (String parameter : parameters) {
				statement.setString(index++, parameter);
			}
			statement.setInt(index++, pageSize);
			statement.setInt(index, (page - 1) * pageSize);
			ResultSet resultSet = statement.executeQuery();
			return makeEmployeeLists(resultSet);
		}
	}
	
	/**
	 * DBから得た社員情報数をカウント
	 * @param employee
	 * @return 社員情報の総数
	 * @throws SQLException
	 */
	public int countSearchedEmployees(Employee employee) throws SQLException {
		List<String> parameters = new ArrayList<>();
		String countSql = "SELECT COUNT(*) FROM employee" +  buildSearchWhere(employee, parameters);

		try (PreparedStatement statement = connection.prepareStatement(countSql)) {
			int index = 1;
			for (String parameter : parameters) {
				statement.setString(index++, parameter);
			}
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		}
	}
}