package error.value;

public class ErrorValue {
	private String message;
	
	public String getMessage() {
		if(message == null) {
			return "";
		}
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
