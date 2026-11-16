package errorValues;

public class DuplicationErrors {
	private ErrorValue employeeNumError = new ErrorValue();
	private ErrorValue emailError = new ErrorValue();
	
	public String getEmployeeNumError() {
		return employeeNumError.getErrorValue();
	}
	
	public void setEmployeeNumError(String errorValue) {
		employeeNumError.setErrorValue(errorValue);
	}
	
	public String getEmailError() {
		return emailError.getErrorValue();
	}
	
	public void setEmailError(String errorValue) {
		this.emailError.setErrorValue(errorValue);
	}
	
    public boolean hasError() {
        return !employeeNumError.getErrorValue().isEmpty()
                || !emailError.getErrorValue().isEmpty();
    }
}
