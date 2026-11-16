package errorValues;

public class ErrorValue {
	private String errorValue;
	
	public String getErrorValue() {
		if(errorValue == null) {
			return "";
		}
		return errorValue;
	}
	
	public void setErrorValue(String errorValue) {
		this.errorValue = errorValue;
	}
	
	public boolean hasError(String errorValue) {
		return errorValue != null;
	}
}
