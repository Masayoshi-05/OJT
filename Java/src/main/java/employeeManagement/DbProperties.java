package employeeManagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import environment.Environment;

public class DbProperties {
	private static Properties property = new Properties();
	private static String URL;
	private static String USER;
	private static String PASSWORD;
	private static String fileName;

	static {
		switch (Environment.getEnvironment()) {
		case "aws":
			fileName = "properties/awsProperty";
			break;

		default:
			fileName = "properties/localProperty";
			break;
		}

		InputStream input = DbProperties.class.getClassLoader().getResourceAsStream(fileName);
		System.out.println("APP_ENV=" + Environment.getEnvironment());
		System.out.println("fileName=" + fileName);
		System.out.println("input=" + input);
		
		try {
			property.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		URL = property.getProperty("url");

		USER = property.getProperty("user");

		PASSWORD = property.getProperty("password");
	}
	
	public static String getURL() {
		return URL;
	}

	public static String getUSER() {
		return USER;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}
}


