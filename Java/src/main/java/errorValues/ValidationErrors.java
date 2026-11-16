package errorValues;

public class ValidationErrors {
	private ErrorValue employeeNumError = new ErrorValue();
	private ErrorValue kanjiNameError = new ErrorValue();
	private ErrorValue romanNameError = new ErrorValue();
	private ErrorValue emailError = new ErrorValue();
	private ErrorValue birthdayError = new ErrorValue();
	private ErrorValue joinDateError = new ErrorValue();
	
	public String getEmployeeNumError() {
		return employeeNumError.getErrorValue();
	}
	public void setEmployeeNumError(String errorValue) {
		employeeNumError.setErrorValue(errorValue);
	}
	public String getKanjiNameError() {
		return kanjiNameError.getErrorValue();
	}
	public void setKanjiNameError(String errorValue) {
		kanjiNameError.setErrorValue(errorValue);
	}
	public String getRomanNameError() {
		return romanNameError.getErrorValue();
	}
	public void setRomanNameError(String errorValue) {
		romanNameError.setErrorValue(errorValue);
	}
	public String getEmailError() {
		return emailError.getErrorValue();
	}
	public void setEmailError(String errorValue) {
		emailError.setErrorValue(errorValue);
	}
	public String getBirthdayError() {
		return birthdayError.getErrorValue();
	}
	public void setBirthdayError(String errorValue) {
		birthdayError.setErrorValue(errorValue);
	}
	public String getJoinDateError() {
		return joinDateError.getErrorValue();
	}
	public void setJoinDateError(String errorValue) {
		joinDateError.setErrorValue(errorValue);
	}
	
    public boolean hasError() {
        return !employeeNumError.getErrorValue().isEmpty()
                || !kanjiNameError.getErrorValue().isEmpty()
                || !romanNameError.getErrorValue().isEmpty()
                || !emailError.getErrorValue().isEmpty()
                || !birthdayError.getErrorValue().isEmpty()
                || !joinDateError.getErrorValue().isEmpty();
    }
}
