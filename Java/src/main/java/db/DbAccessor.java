package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import data.employee.ConditionEmployee;
import data.employee.Employee;
import definition.key.EmployeeKeys;

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
			statement.setLong(9, employee.getId());
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
			statement.setLong(1, employee.getId());
			statement.executeUpdate();
		}
	}

	/**
	 * 	idから社員情報を検索・取得
	 * @param id
	 * @throws SQLException
	 */
	public Employee getEmployeeById(int id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EMPLOYEE_BY_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Employee employee = new Employee();
				employee.setId(id);
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
	 * 社員番号の重複チェック(id指定)
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
	 * メールアドレスの重複チェック(id指定)
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
	 * @param sql 重複チェックの対象を WHERE カラム名 = ? で指定し、行数をカウントするクエリを指定する
	 * @param value 重複チェック対象の値を指定する
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
	 * idを指定し、そのidの社員は省いて重複チェック処理
	 * @param connection
	 * @param sql 重複チェックの対象を WHERE カラム名 = ? で指定し、行数をカウントするクエリを指定する
	 * @param value 重複チェック対象の値を指定する
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
	 * 社員情報検索
	 * @param conditionEmployee
	 * @return 検索条件に基づいた社員情報リスト
	 * @throws SQLException
	 */
	public List<Employee> searchEmployees(ConditionEmployee conditionEmployee) throws SQLException {
		List<String> parameters = new ArrayList<>();

		String searchSql = "SELECT * FROM employee" + buildWhere(conditionEmployee, parameters);

		try (PreparedStatement statement = connection.prepareStatement(searchSql)) {
			int index = 1;
			for (String parameter : parameters) {
				statement.setString(index++, "%" + parameter + "%");
			}
			ResultSet resultSet = statement.executeQuery();
			return makeEmployeeList(resultSet);
		}
	}

	/**
	 * クエリの検索条件部分を動的に構築する
	 * @param conditionEmployee 
	 * @param parameters 検索条件として使用するカラムの値を保持しておくリスト
	 * @return 検索条件を指定するString文字列
	 */
	private String buildWhere(ConditionEmployee conditionEmployee, List<String> parameters) {
		StringBuilder stringBuilder = new StringBuilder();
		addCondition(stringBuilder, EmployeeKeys.EMPLOYEE_NUM, conditionEmployee.getEmployeeNum(), parameters);
		//氏名は、姓・名を結合した状態で検索を行う
		addCondition(stringBuilder,
				"CONCAT("
						+ EmployeeKeys.KANJI_LAST_NAME
						+ ","
						+ EmployeeKeys.KANJI_FIRST_NAME
						+ ")",
				conditionEmployee.getKanjiName(), parameters);
		addCondition(stringBuilder,
				"CONCAT("
						+ EmployeeKeys.ROMAN_LAST_NAME
						+ ","
						+ EmployeeKeys.ROMAN_FIRST_NAME
						+ ")",
				conditionEmployee.getRomanName(), parameters);
		addCondition(stringBuilder, EmployeeKeys.EMAIL, conditionEmployee.getEmail(), parameters);
		addCondition(stringBuilder, EmployeeKeys.BIRTHDAY, conditionEmployee.getBirthday(), parameters);
		addCondition(stringBuilder, EmployeeKeys.JOIN_DATE, conditionEmployee.getJoinDate(), parameters);
		return stringBuilder.toString();
	}

	/**
	 * StringBuilderで検索条件として指定された値のみをクエリに追加する
	 * @param stringBuilder
	 * @param conditionName 検索条件として使用するカラムの名前を指定する
	 * @param conditionValue 検索条件として使用するカラムの値を指定する
	 * @param parameters 検索条件として使用するカラムの値を保持しておくリスト
	 */
	private void addCondition(StringBuilder stringBuilder, String conditionName, String conditionValue,
			List<String> parameters) {
		
		if (conditionValue == null) {
			return;
		}
		
		//conditionValueに空白やタブ文字が含まれる場合、それらを除去する
		conditionValue = conditionValue.replaceAll("[\\s|　]", "");

		if (conditionValue.isEmpty()) {
			return;
		}

		if (stringBuilder.length() == 0) {
			stringBuilder.append(" WHERE ");
		} else {
			stringBuilder.append(" AND ");
		}

		//"未登録"で検索が行われた場合、そのカラムがnullの行を検索する
		if ("未登録".equals(conditionValue)) {
			stringBuilder.append(conditionName + " IS NULL");
			return;
		}

		stringBuilder.append(conditionName + " LIKE ?");
		parameters.add(conditionValue);
	}

	/**
	 * DBから得た社員情報をリスト化
	 * @param resultSet 検索結果
	 * @return employees リスト
	 * @throws SQLException
	 */
	private List<Employee> makeEmployeeList(ResultSet resultSet) throws SQLException {
		List<Employee> employees = new ArrayList<>();
		while (resultSet.next()) {
			Employee employee = new Employee();
			employee.setId(resultSet.getInt(EmployeeKeys.ID));
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
}