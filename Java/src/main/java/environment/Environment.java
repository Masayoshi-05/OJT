package environment;

/*
 * 環境変数を取得する
 */
public class Environment {
	public static String getEnvironment(String variableName) {
		String env = System.getenv(variableName);
		if (env.equals("aws")) {
			return "aws";
		} else {
			return "local";
		}
	}
}
