package definition;

/**
 * 社員情報の各パラメータに対するエラー名として使用するキーを定義する
 */
public class ErrorKeys {
	
	public static final String GENERAL_ERROR = "generalError";
	public static final String VALIDATION_ERRORS = "validationErrors";
	public static final String DUPLICATION_ERRORS = "duplicationErrors";
	//各項目に対するエラー
	public static final String EMPLOYEE_NUM_ERROR = "employeeNumError";
	public static final String KANJI_NAME_ERROR = "kanjiNameError";
	public static final String ROMAN_NAME_ERROR = "romanNameError";
	public static final String EMAIL_ERROR = "emailError";
	public static final String BIRTHDAY_ERROR = "birthdayError";
	public static final String JOIN_DATE_ERROR = "joinDateError";
	public static final String EMPLOYEE_MANAGEMENT_ERROR = "employeeManagementError";
}
