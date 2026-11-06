package validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TestEmployeeValidator {

	private final String SYMBOL = "!#$%&!()=~^";
	private final String SINGLE_QUOTE_NUM = "'1234'";
	private final String SINGLE_QUOTE_ALP = "'abcd'";
	private final String DOUBLE_QUOTE_NUM = "\"1234\"";
	private final String DOUBLE_QUOTE_ALP = "\"abcd\"";
	private final String HTML_TAG_NUM = "<b>1234</b>";
	private final String HTML_TAG_ALP = "<b>abcd</b>";
	private final String SCRIPT_TAG = "<script>alert('xss')</script>";
	private final String SQL = "' OR '1'='1";
	private final String NEW_LINE_NUM = "12\r\n34";
	private final String NEW_LINE_ALP = "ab\r\ncd";
	private final String EMOJI = "☺";
	private final String PLATFORM_DEPENDANT_CHARACTER = "①②③ⅠⅡⅢ";

	private final static String ERROR_REQUIRED = "入力必須の項目です。";
	private final static String ERROR_TOO_LONG = "登録可能な文字数を超過しています。";
	private final static String ERROR_INVALID_CHARACTER = "使用できない文字が含まれています。";
	private final static String ERROR_EMAIL = "メールアドレスの形式が不正です。";
	private final static String ERROR_DATE = "日付の形式が不正です。";

	//社員番号
	/**
	 * 社員番号のバリデーターへの入力値が正常値だった場合のテスト
	 */
	@Test
	void testValidateNormalEmployeeNum() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateEmployeeNum("1234");
		assertEquals(expected, actual);
	}

	/**
	 * 社員番号のバリデーターへの入力値がnullだった場合のテスト
	 */
	@Test
	void testValidateNullEmployeeNum() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateEmployeeNum(null);
		assertEquals(expected, actual);
	}

	/**
	 * 社員番号のバリデーターへの入力値が空文字だった場合のテスト
	 */
	@Test
	void testValidateEmptyEmployeeNum() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateEmployeeNum("");
		assertEquals(expected, actual);
	}

	/**
	 * 社員番号のバリデーターへの入力値が入力上限を超えた場合のテスト
	 */
	@Test
	void testValidateTooLongEmployeeNum() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_TOO_LONG;
		String actual = employeeValidator.validateEmployeeNum("12345");
		assertEquals(expected, actual);
	}

	/**
	 * 社員番号のバリデーターへの入力値に使用不可文字が含まれていた場合のテスト
	 * @param value
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			SYMBOL,
			SINGLE_QUOTE_NUM,
			DOUBLE_QUOTE_NUM,
			HTML_TAG_NUM,
			SCRIPT_TAG,
			SQL,
			NEW_LINE_NUM,
			EMOJI,
			PLATFORM_DEPENDANT_CHARACTER
	})
	void testValidateInvalidEmployeeNum(String value) {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_INVALID_CHARACTER;
		String actual = employeeValidator.validateEmployeeNum(value);
		assertEquals(expected, actual);
	}

	//漢字姓

	/**
	 * 氏名(漢字)のバリデーターへの特殊文字を含む入力値のテスト(姓)
	 * @param value
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"abcd",
			SYMBOL,
			SINGLE_QUOTE_ALP,
			DOUBLE_QUOTE_ALP,
			HTML_TAG_ALP,
			SCRIPT_TAG,
			SQL,
			NEW_LINE_ALP,
			EMOJI,
			PLATFORM_DEPENDANT_CHARACTER
	})
	void testValidateNormalKanjiLastName(String value) {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateKanjiName(value, "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(漢字)のバリデーターへの入力値が姓のみ空文字だった場合のテスト
	 */
	@Test
	void testValidateEmptyKanjiLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateKanjiName("", "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(漢字)のバリデーターへの入力値が姓のみnullだった場合のテスト
	 */
	@Test
	void testValidateNullKanjiLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateKanjiName(null, "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(漢字)のテキストボックスの入力上限の境界値テスト(姓)
	 */
	@Test
	void testValidateBoundaryKanjiLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateRomanName("aaaaabbbbbcccccdddddeeeeefffff", "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(漢字)のバリデーターへの入力値が姓のみ上限値を超えた場合のテスト
	 */
	@Test
	void testValidateTooLongKanjiLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_TOO_LONG;
		String actual = employeeValidator.validateKanjiName("aaaaabbbbbcccccdddddeeeeefffffg", "abcd");
		assertEquals(expected, actual);
	}

	//漢字名

	/**
	 * 氏名(漢字)のバリデーターへの特殊文字を含む入力値のテスト(名)
	 * @param value
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"abcd",
			SYMBOL,
			SINGLE_QUOTE_ALP,
			DOUBLE_QUOTE_ALP,
			HTML_TAG_ALP,
			SCRIPT_TAG,
			SQL,
			NEW_LINE_ALP,
			EMOJI,
			PLATFORM_DEPENDANT_CHARACTER
	})
	void testValidateNormalKanjiFirstName(String value) {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateKanjiName("abcd", value);
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(漢字)のバリデーターへの入力値が姓のみ空文字だった場合のテスト
	 */
	@Test
	void testValidateEmptyKanjiFirstName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateKanjiName("abcd", "");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(漢字)のバリデーターへの入力値が姓のみnullだった場合のテスト
	 */
	@Test
	void testValidateNullKanjiFirstName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateKanjiName("abcd", null);
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(漢字)のテキストボックスの入力上限の境界値テスト(名)
	 */
	@Test
	void testValidateBoundaryKanjiFirstName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateRomanName("abcd", "aaaaabbbbbcccccdddddeeeeefffff");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(漢字)のバリデーターへの入力値が姓のみ上限値を超えた場合のテスト
	 */
	@Test
	void testValidateTooLongKanjiFirstName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_TOO_LONG;
		String actual = employeeValidator.validateKanjiName("abcd", "aaaaabbbbbcccccdddddeeeeefffffg");
		assertEquals(expected, actual);
	}

	//ローマ字姓

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値が両方とも正常値だった場合のテスト
	 */
	@Test
	void testValidateNormalRomanLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateRomanName("abcd", "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値が姓のみ空文字だった場合のテスト
	 */
	@Test
	void testValidateEmptyRomanLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateRomanName("", "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値が姓のみnullだった場合のテスト
	 */
	@Test
	void testValidateNullRomanLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateRomanName(null, "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)のテキストボックスの入力上限の境界値テスト(姓)
	 */
	@Test
	void testValidateBoundaryRomanLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateRomanName("aaaaabbbbbcccccdddddeeeeefffff", "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値が姓のみ入力上限を超えた場合のテスト
	 */
	@Test
	void testValidateTooLongRomanLastName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_TOO_LONG;
		String actual = employeeValidator.validateRomanName("aaaaabbbbbcccccdddddeeeeefffffg", "abcd");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値に使用不可文字が含まれていた場合のテスト(姓)
	 * @param value
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			SYMBOL,
			SINGLE_QUOTE_ALP,
			DOUBLE_QUOTE_ALP,
			HTML_TAG_ALP,
			SCRIPT_TAG,
			SQL,
			NEW_LINE_ALP,
			EMOJI,
			PLATFORM_DEPENDANT_CHARACTER
	})
	void testValidateInvalidRomanLastName(String value) {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_INVALID_CHARACTER;
		String actual = employeeValidator.validateRomanName(value, "abcd");
		assertEquals(expected, actual);
	}

	//ローマ字名

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値が名のみ空文字だった場合のテスト
	 */
	@Test
	void testValidateEmptyRomanFirstName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateRomanName("abcd", "");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値が名のみnullだった場合のテスト
	 */
	@Test
	void testValidateNullRomanFirstName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_REQUIRED;
		String actual = employeeValidator.validateRomanName("abcd", null);
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)テキストボックスの入力上限の境界値テスト(名)
	 */
	@Test
	void testValidateBoundaryRomanFirstName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateRomanName("abcd", "aaaaabbbbbcccccdddddeeeeefffff");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値が名のみ入力上限を超えた場合のテスト
	 */
	@Test
	void testValidateTooLongRomanFirstName() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_TOO_LONG;
		String actual = employeeValidator.validateRomanName("abcd", "aaaaabbbbbcccccdddddeeeeefffffg");
		assertEquals(expected, actual);
	}

	/**
	 * 氏名(ローマ字)のバリデーターへの入力値に使用不可文字が含まれていた場合のテスト(名)
	 * @param value
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			SYMBOL,
			SINGLE_QUOTE_ALP,
			DOUBLE_QUOTE_ALP,
			HTML_TAG_NUM,
			SCRIPT_TAG,
			SQL,
			NEW_LINE_ALP,
			EMOJI,
			PLATFORM_DEPENDANT_CHARACTER
	})
	void testValidateInvalidRomanFirstName(String value) {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_INVALID_CHARACTER;
		String actual = employeeValidator.validateRomanName("abcd", value);
		assertEquals(expected, actual);
	}

	//メールアドレス

	/**
	 * メールアドレスのバリデーターへの入力値が正常値だった場合のテスト
	 */
	@Test
	void testValidateNormalEmail() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateEmail("Test.Taro_12-34@sample.com");
		assertEquals(expected, actual);
	}

	/**
	 * メールアドレスのバリデーターへの入力値が空文字だった場合のテスト
	 */
	@Test
	void testValidateEmptyEmail() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateEmail("");
		assertEquals(expected, actual);
	}

	/**
	 * メールアドレスのバリデーターへの入力値がnullだった場合のテスト
	 */
	@Test
	void testValidateNullEmail() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateEmail(null);
		assertEquals(expected, actual);
	}

	/**
	 * メールアドレスのテキストボックスの入力上限の境界値テスト
	 */
	@Test
	void testValidateBoundaryEmail() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateEmail(
				"aaaaabbbbbcccccdddddeeeeeaaaaabbbbbcccccdddddeeeeeaaaaabbbbbcccccdddddeeeee@123456boundarysample.com");
		assertEquals(expected, actual);
	}

	/**
	 * メールアドレスのバリデーターへの入力値が入力上限を超えた場合のテスト
	 */
	@Test
	void testValidateTooLongEmail() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_TOO_LONG;
		String actual = employeeValidator.validateEmail(
				"aaaaabbbbbcccccdddddeeeeeaaaaabbbbbcccccdddddeeeeeaaaaabbbbbcccccdddddeeeee@1234567boundarysample.com");
		assertEquals(expected, actual);
	}

	/**
	 * メールアドレスのバリデーターへの入力値に使用不可文字が含まれていた場合のテスト
	 * @param value
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			SYMBOL,
			SINGLE_QUOTE_ALP,
			DOUBLE_QUOTE_ALP,
			HTML_TAG_NUM,
			SCRIPT_TAG,
			SQL,
			NEW_LINE_ALP,
			EMOJI,
			PLATFORM_DEPENDANT_CHARACTER
	})
	void testValidateInvalidEmail(String value) {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_INVALID_CHARACTER;
		String actual = employeeValidator.validateEmail(value);
		assertEquals(expected, actual);
	}

	/**
	 * メールアドレスのバリデーターへの入力値が不正な形式だった場合のテスト
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"-Test.Taro_12-34@sample.com",
			"Test.Taro_12-34-@sample.com"
	})
	void testValidateEmailFormat(String value) {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_EMAIL;
		String actual = employeeValidator.validateEmail(value);
		assertEquals(expected, actual);
	}

	//日付
	
	/**
	 * 日付のバリデーターへの入力値が正常値だった場合のテスト
	 */
	@Test
	void testValidateNormalDate() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateDate("2000-01-01");
		assertEquals(expected, actual);
	}

	/**
	 * 日付のバリデーターへの入力値が空文字だった場合のテスト
	 */
	@Test
	void testValidateEmptyDate() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateDate("");
		assertEquals(expected, actual);
	}

	/**
	 * 日付のバリデーターへの入力値がnullだった場合のテスト
	 */
	@Test
	void testValidateNullDate() {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = null;
		String actual = employeeValidator.validateDate(null);
		assertEquals(expected, actual);
	}

	/**
	 * 日付のバリデーターへの入力値が不正な形式だった場合のテスト
	 * @param value
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"2000/01/01",
			"2000-001-001",
			"2000-02-30",
	})
	void testValidateInvalidDate(String value) {
		EmployeeValidator employeeValidator = new EmployeeValidator();
		String expected = ERROR_DATE;
		String actual = employeeValidator.validateDate(value);
		assertEquals(expected, actual);
	}
}
