package definition;

/**
* 社員情報のパラメータ名や属性名として使用するキーを定義するクラス
*/
public class Keys {
	
	//社員情報
	public static final String EMPLOYEE_NUM = "employeeNum";
	public static final String KANJI_LAST_NAME = "kanjiLastName";
	public static final String KANJI_FIRST_NAME = "kanjiFirstName";
	public static final String ROMAN_LAST_NAME = "romanLastName";
	public static final String ROMAN_FIRST_NAME = "romanFirstName";
	public static final String EMAIL = "email";
	public static final String BIRTHDAY = "birthday";
	public static final String JOIN_DATE = "joinDate";
	
	
	//各項目に対するエラー
	public static final String EMPLOYEE_NUM_ERROR = "employeeNumError";
	public static final String KANJI_NAME_ERROR = "kanjiNameError";
	public static final String ROMAN_NAME_ERROR = "romanNameError";
	public static final String EMAIL_ERROR = "emailError";
	public static final String BIRTHDAY_ERROR = "birthdayError";
	public static final String JOIN_DATE_ERROR = "joinDateError";
	
	
	//各画面へのパス
	public static final String REGISTER_INPUT_PATH = "/WEB-INF/lib/EmployeeRegisterInput.jsp";
	public static final String REGISTER_CONFIRM_PATH = "/WEB-INF/lib/EmployeeRegisterConfirm.jsp";
	public static final String REGISTER_COMPLETE_PATH = "/WEB-INF/lib/EmployeeRegisterComplete.jsp";
}
