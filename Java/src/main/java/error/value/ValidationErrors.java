package error.value;

public class ValidationErrors {
	private ErrorValue employeeNumError = new ErrorValue();
	private ErrorValue kanjiNameError = new ErrorValue();
	private ErrorValue romanNameError = new ErrorValue();
	private ErrorValue emailError = new ErrorValue();
	private ErrorValue birthdayError = new ErrorValue();
	private ErrorValue joinDateError = new ErrorValue();
	
	public String getEmployeeNumError() {
		return employeeNumError.getMessage();
	}
	public void setEmployeeNumError(String message) {
		employeeNumError.setMessage(message);
	}
	public String getKanjiNameError() {
		return kanjiNameError.getMessage();
	}
	public void setKanjiNameError(String message) {
		kanjiNameError.setMessage(message);
	}
	public String getRomanNameError() {
		return romanNameError.getMessage();
	}
	public void setRomanNameError(String message) {
		romanNameError.setMessage(message);
	}
	public String getEmailError() {
		return emailError.getMessage();
	}
	public void setEmailError(String message) {
		emailError.setMessage(message);
	}
	public String getBirthdayError() {
		return birthdayError.getMessage();
	}
	public void setBirthdayError(String message) {
		birthdayError.setMessage(message);
	}
	public String getJoinDateError() {
		return joinDateError.getMessage();
	}
	public void setJoinDateError(String message) {
		joinDateError.setMessage(message);
	}
	
    public boolean hasError() {
        return !employeeNumError.getMessage().isEmpty()
                || !kanjiNameError.getMessage().isEmpty()
                || !romanNameError.getMessage().isEmpty()
                || !emailError.getMessage().isEmpty()
                || !birthdayError.getMessage().isEmpty()
                || !joinDateError.getMessage().isEmpty();
    }
}
