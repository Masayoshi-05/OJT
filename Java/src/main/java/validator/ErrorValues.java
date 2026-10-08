package validator;

public class ErrorValues {
	private String employeeNumError;
	private String kanjiNameError;
	private String romanNameError;
	private String emailError;
	private String birthdayError;
	private String joinDateError;
	
	public String getEmployeeNumError() {
		return employeeNumError;
	}
	public void setEmployeeNumError(String employeeNumError) {
		this.employeeNumError = employeeNumError;
	}
	public String getKanjiNameError() {
		return kanjiNameError;
	}
	public void setKanjiNameError(String kanjiNameError) {
		this.kanjiNameError = kanjiNameError;
	}
	public String getRomanNameError() {
		return romanNameError;
	}
	public void setRomanNameError(String romanNameError) {
		this.romanNameError = romanNameError;
	}
	public String getEmailError() {
		return emailError;
	}
	public void setEmailError(String emailError) {
		this.emailError = emailError;
	}
	public String getBirthdayError() {
		return birthdayError;
	}
	public void setBirthdayError(String birthdayError) {
		this.birthdayError = birthdayError;
	}
	public String getJoinDateError() {
		return joinDateError;
	}
	public void setJoinDateError(String joinDateError) {
		this.joinDateError = joinDateError;
	}
	
	public boolean isHasError() {
		return employeeNumError != null
				|| kanjiNameError != null
				|| romanNameError != null
				|| emailError != null
				|| birthdayError != null
				|| joinDateError != null;
	}
	
	public String nullDisplay(String value) {
		if(value == null) {
			return "";
		}
		return value;
	}
}
