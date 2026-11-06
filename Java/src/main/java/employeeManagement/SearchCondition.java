package employeeManagement;

public class SearchCondition {
	private boolean isCheckedEmployeeNum;
	private boolean isCheckedKanjiName;
	private boolean isCheckedRomanName;
	private boolean isCheckedEmail;
	private boolean isCheckedBirthday;
	private boolean isCheckedJoinDate;
	public static final String IS_CHECKED_EMPLOYEE = "isCheckedEmployee";

	public boolean isCheckedEmployeeNum() {
		return isCheckedEmployeeNum;
	}

	public void setCheckedEmployeeNum(boolean isSearchEmployeeNum) {
		this.isCheckedEmployeeNum = isSearchEmployeeNum;
	}

	public boolean isCheckedKanjiName() {
		return isCheckedKanjiName;
	}

	public void setCheckedKanjiName(boolean isSearchKanjiName) {
		this.isCheckedKanjiName = isSearchKanjiName;
	}

	public boolean isCheckedRomanName() {
		return isCheckedRomanName;
	}

	public void setCheckedRomanName(boolean isSearchRomanName) {
		this.isCheckedRomanName = isSearchRomanName;
	}

	public boolean isCheckedEmail() {
		return isCheckedEmail;
	}

	public void setCheckedEmail(boolean isSearchEmail) {
		this.isCheckedEmail = isSearchEmail;
	}

	public boolean isCheckedBirthday() {
		return isCheckedBirthday;
	}

	public void setCheckedBirthday(boolean isSearchBirthday) {
		this.isCheckedBirthday = isSearchBirthday;
	}

	public boolean isCheckedJoinDate() {
		return isCheckedJoinDate;
	}

	public void setCheckedJoinDate(boolean isSearchJoinDate) {
		this.isCheckedJoinDate = isSearchJoinDate;
	}
	
	
}
