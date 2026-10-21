package environment;

/**
 * 環境変数を取得する
 */

public class Environment {
	public static String getEnvironment(String variableName) {
		return System.getenv(variableName);
	}
}
