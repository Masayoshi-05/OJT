package validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
* 社員情報の各入力値に対するバリデーションチェック
*/
public class EmployeeValidator {
	private final static String ERROR_REQUIRED = "入力必須の項目です。";
	private final static String ERROR_TOO_LONG = "登録可能な文字数を超過しています。";
	private final static String ERROR_INVALID_CHARACTER = "使用できない文字が含まれています。";
	private final static String ERROR_EMAIL = "メールアドレスの形式が不正です。";
	private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
			.withResolverStyle(ResolverStyle.STRICT);
	private final static String ERROR_DATE = "日付の形式が不正です。";

	/**
	* 社員番号のバリデーションチェック
	* @param employeeNum 社員番号
	* @return エラーがある場合はエラーメッセージ、正常な場合はnull
	*/
	public static String validateEmployeeNum(String employeeNum) {
		if (employeeNum == null) {
			return ERROR_REQUIRED;
		} else if (employeeNum.length() > 4) {
			return ERROR_TOO_LONG;
		} else if (!employeeNum.matches("[0-9]+")) {
			return ERROR_INVALID_CHARACTER;
		}
		return null;
	}

	/**
	* 漢字氏名のバリデーションチェック
	* @param kanjiLastName 漢字姓
	* @param kanjiFirstName 漢字名
	* @return エラーがある場合はエラーメッセージ、正常な場合はnull
	*/
	public static String validateKanjiName(String kanjiLastName, String kanjiFirstName) {
		if (kanjiLastName == null || kanjiFirstName == null) {
			return ERROR_REQUIRED;
		} else if (kanjiLastName.length() > 30 || kanjiFirstName.length() > 30) {
			return ERROR_TOO_LONG;
		}
		return null;
	}

	/**
	* ローマ字氏名のバリデーションチェック
	* @param romanLastName ローマ字姓
	* @param romanFirstName ローマ字名
	* @return エラーがある場合はエラーメッセージ、正常な場合はnull
	*/
	public static String validateRomanName(String romanLastName, String romanFirstName) {
		if (romanLastName == null || romanFirstName == null) {
			return ERROR_REQUIRED;
		} else if (romanLastName.length() > 30 || romanFirstName.length() > 30) {
			return ERROR_TOO_LONG;
		} else if (!romanLastName.matches("[A-Za-z]+") || !romanFirstName.matches("[A-Za-z]+")) {
			return ERROR_INVALID_CHARACTER;
		}
		return null;
	}

	/**
	* メールアドレスのバリデーションチェック
	* @param email メールアドレス
	* @return エラーがある場合はエラーメッセージ、正常な場合はnull
	*/
	public static String validateEmail(String email) {
		if (email != null) {
			if (!email.matches("^[A-Za-z0-9][A-Za-z0-9._-]*[A-Za-z0-9]$")) {
				return ERROR_EMAIL;
			} else if (email.length() > 100) {
				return ERROR_TOO_LONG;
			}
		}
		return null;
	}

	/**
	* 日付のバリデーションチェック
	* @param date 日付
	* @return エラーがある場合はエラーメッセージ、正常な場合はnull
	*/
	public static String validateDate(String date) {
		if (date != null) {
			try {
				LocalDate.parse(date, DATE_FORMATTER);
			} catch (DateTimeParseException e) {
				return ERROR_DATE;
			}
		}
		return null;
	}
}
