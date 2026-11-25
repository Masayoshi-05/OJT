package data.employee;

/**
 * 一覧表示画面における検索機能にて、テキストボックスに入力された検索条件を保持する
 */
public class ConditionEmployee {
	private String employeeNum;
	private String kanjiName;
	private String romanName;
	private String email;
	private String birthday;
	private String joinDate;
	
	public String getEmployeeNum() {
		return employeeNum;
	}
	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	public String getKanjiName() {
		return kanjiName;
	}
	public void setKanjiName(String kanjiName) {
		this.kanjiName = kanjiName;
	}
	public String getRomanName() {
		return romanName;
	}
	public void setRomanName(String romanName) {
		this.romanName = romanName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
}
