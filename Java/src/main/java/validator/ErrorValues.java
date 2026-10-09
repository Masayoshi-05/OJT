package validator;

public class ErrorValues {
	private String employeeNumError;
	private String kanjiNameError;
	private String romanNameError;
	private String emailError;
	private String birthdayError;
	private String joinDateError;
	
	public String getEmployeeNumError() {
		if(employeeNumError == null) {
			return "";
		}
		return employeeNumError;
	}
	public void setEmployeeNumError(String employeeNumError) {
		this.employeeNumError = employeeNumError;
	}
	public String getKanjiNameError() {
		if(kanjiNameError == null) {
			return "";
		}
		return kanjiNameError;
	}
	public void setKanjiNameError(String kanjiNameError) {
		this.kanjiNameError = kanjiNameError;
	}
	public String getRomanNameError() {
		if(romanNameError == null) {
			return "";
		}
		return romanNameError;
	}
	public void setRomanNameError(String romanNameError) {
		this.romanNameError = romanNameError;
	}
	public String getEmailError() {
		if(emailError == null) {
			return "";
		}
		return emailError;
	}
	public void setEmailError(String emailError) {
		this.emailError = emailError;
	}
	public String getBirthdayError() {
		if(birthdayError == null) {
			return "";
		}
		return birthdayError;
	}
	public void setBirthdayError(String birthdayError) {
		this.birthdayError = birthdayError;
	}
	public String getJoinDateError() {
		if(joinDateError == null) {
			return "";
		}
		return joinDateError;
	}
	public void setJoinDateError(String joinDateError) {
		this.joinDateError = joinDateError;
	}
	
	public boolean hasError() {
		return employeeNumError != null
				|| kanjiNameError != null
				|| romanNameError != null
				|| emailError != null
				|| birthdayError != null
				|| joinDateError != null;
	}
}
