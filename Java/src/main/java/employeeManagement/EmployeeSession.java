package employeeManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 社員情報のセッション操作を行う
 */
public class EmployeeSession {	
	/**
	 * sessionに社員情報を送る
	 * @param request
	 * @param key
	 * @param searchEmployee
	 */
	public static void setEmployee(HttpServletRequest request, String key, Employee searchEmployee) {
		HttpSession session = request.getSession();
		session.setAttribute(key, searchEmployee);
	}
	
	/**
	 * sessionから社員情報を取得する
	 * @param request
	 * @return 社員情報
	 */

	public static Employee getEmployee(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (Employee) session.getAttribute(key);
	}
	
	/**
	 * sessionの社員情報を削除
	 * @param request
	 * @param key
	 */
	public static void removeEmployee(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		session.removeAttribute(key);
	}
	
	/**
	 * sessionに検索欄チェックボックスの状態を送る
	 * @param request
	 * @param condition
	 */
	public static void setIsCheckedEmployee(HttpServletRequest request, SearchCondition condition) {
		HttpSession session = request.getSession();
		session.setAttribute(SearchCondition.IS_CHECKED_EMPLOYEE, condition);
	}
}