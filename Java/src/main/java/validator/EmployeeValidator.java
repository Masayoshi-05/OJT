package validator;

public class EmployeeValidator {
	private final static String requiredError = "入力必須の項目です。";
	private final static String tooLongError = "登録可能な文字数を超過しています。";
	private final static String invalidCharacterError = "使用できない文字が含まれています。";
	private final static String emailError = "メールアドレスの形式が不正です。";
	private final static String dateError = "日付の形式が不正です。";

	public static String validateEmployeeNum(String employeeNum) {
		if (employeeNum == null || employeeNum.isEmpty()) {
			return requiredError;
		} else if (employeeNum.length() > 4) {
			return tooLongError;
		} else if (!employeeNum.matches("[0-9]+")) {
			return invalidCharacterError;
		}
		return null;
	}

	public static String validateKanjiName(String kanjiLastName, String kanjiFirstName) {
		if (kanjiLastName == null || kanjiLastName.isEmpty() || kanjiFirstName == null || kanjiFirstName.isEmpty()) {
			return requiredError;
		} else if (kanjiLastName.length() > 30 || kanjiFirstName.length() > 30) {
			return tooLongError;
		}
		return null;
	}

	public static String validateRomanName(String romanLastName, String romanFirstName) {
		if (romanLastName == null || romanLastName.isEmpty() || romanFirstName == null|| romanFirstName.isEmpty()) {
			return requiredError;
		} else if (romanLastName.length() > 30 || romanFirstName.length() > 30) {
			return tooLongError;
		} else if (!romanLastName.matches("[A-Za-z]+") || !romanFirstName.matches("[A-Za-z]+")) {
			return invalidCharacterError;
		}
		return null;
	}

	public static String validateEmail(String email) {
		if (email != null && !email.isEmpty()) {
			if (!email.matches("^[A-Za-z0-9][A-Za-z0-9._-]*[A-Za-z0-9]$")) {
				return emailError;
			} else if (email.length() > 100) {
				return tooLongError;
			}
		}
		return null;
	}

	public static String validateDate(String date) {
		if (date != null && !date.isEmpty()) {
			if (!date.matches("^[0-2]0[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[01])$")) {
				return dateError;
			}
		}
		return null;
	}
}
