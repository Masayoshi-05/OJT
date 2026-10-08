package employeeManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
* 社員情報のセッション操作を行うクラスです。
*/

public class EmployeeSession {

	private static final String EMPLOYEE = "employee";

	/*
	 * sessionに社員情報を送る
	 */
	public static void setEmployee(HttpServletRequest request, Employee employee) {
		HttpSession session = request.getSession();
		session.setAttribute(EMPLOYEE, employee);
	}

	/*
	 * sessionから社員情報を取得する
	 */
	public static Employee getEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (Employee) session.getAttribute(EMPLOYEE);
	}

	/*
	 * sessionの社員情報を削除
	 */
	public static void removeEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(EMPLOYEE);
	}
}