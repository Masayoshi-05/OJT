package environment;

public class Environment {
	public static String getEnvironment() {
		String env = System.getenv("APP_ENV");
		if (env.equals("aws")) {
			return "aws";
		} else {
			return "local";
		}
	}
}
