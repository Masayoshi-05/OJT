package employeeManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import environment.Environment;

/*
 * DB接続のためのプロパティをファイルから取得するクラス
 */
public class DbProperties {
	private static Properties property = new Properties();
	private static String url;
	private static String user;
	private static String password;
	private static String fileName;

	static {
		switch (Environment.getEnvironment("APP_ENV")) {
		case "aws":
			fileName = "properties/aws.properties";
			break;

		default:
			fileName = "properties/local.properties";
			break;
		}
		
		try(InputStream input = DbProperties.class.getClassLoader().getResourceAsStream(fileName);) {
			property.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		url = property.getProperty("url");

		user = property.getProperty("user");

		password = property.getProperty("password");
	}
	
	public static String getUrl() {
		return url;
	}

	public static String getUser() {
		return user;
	}

	public static String getPassword() {
		return password;
	}
}


