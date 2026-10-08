package employeeManagement;

/**
* 社員情報を保持するクラス
*/
public class Employee {
	private int iD;
	private String employeeNum;
	private String kanjiFirstName;
	private String kanjiLastName;
	private String romanFirstName;
	private String romanLastName;
	private String email;
	private String birthday;
	private String joinDate;

	public int getID() {
		return iD;
	}

	public void setID(int iD) {
		this.iD = iD;
	}

	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}

	public String getKanjiFirstName() {
		return kanjiFirstName;
	}

	public void setKanjiFirstName(String kanjiFirstName) {
		this.kanjiFirstName = kanjiFirstName;
	}

	public String getKanjiLastName() {
		return kanjiLastName;
	}

	public void setKanjiLastName(String kanjiLastName) {
		this.kanjiLastName = kanjiLastName;
	}

	public String getRomanFirstName() {
		return romanFirstName;
	}

	public void setRomanFirstName(String romanFirstName) {
		this.romanFirstName = romanFirstName;
	}

	public String getRomanLastName() {
		return romanLastName;
	}

	public void setRomanLastName(String romanLastName) {
		this.romanLastName = romanLastName;
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
