package error.value;

public class DuplicationErrors {
	private ErrorValue employeeNumError = new ErrorValue();
	private ErrorValue emailError = new ErrorValue();
	
	public String getEmployeeNumError() {
		return employeeNumError.getMessage();
	}
	
	public void setEmployeeNumError(String message) {
		employeeNumError.setMessage(message);
	}
	
	public String getEmailError() {
		return emailError.getMessage();
	}
	
	public void setEmailError(String message) {
		this.emailError.setMessage(message);
	}
	
    public boolean hasError() {
        return !employeeNumError.getMessage().isEmpty()
                || !emailError.getMessage().isEmpty();
    }
}
