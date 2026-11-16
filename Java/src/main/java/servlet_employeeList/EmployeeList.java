package servlet_employeeList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.EmployeeKeys;
import definition.PageKeys;
import definition.Paths;
import employeeManagement.DbAccessor;
import employeeManagement.DbConnection;
import employeeManagement.Employee;
import employeeManagement.EmployeeSession;
import employeeManagement.SearchCondition;

/**
 * 一覧表示画面のServlet
 */
@WebServlet(Paths.EMPLOYEE_LIST_SERVLET_PATH)
public class EmployeeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeList() {
		super();
	}
	
	private void showEmployees(HttpServletRequest request, HttpServletResponse response,Employee searchEmployee) throws ServletException {
		int page = 1;
		int pageSize = 10;
		String pageParam = request.getParameter("page");
		if (pageParam != null) {
			page = Integer.parseInt(pageParam);
		}

		try (Connection connection = DbConnection.getConnection();) {

			DbAccessor dbAccessor = new DbAccessor(connection);
			List<Employee> employees = dbAccessor.searchEmployees(searchEmployee, page, pageSize);

			EmployeeSession.setEmployee(request, EmployeeKeys.SEARCH_EMPLOYEE, searchEmployee);
			request.setAttribute(EmployeeKeys.EMPLOYEES, employees);

			int totalSearchedEmployees = (int) Math.ceil((double) dbAccessor.countSearchedEmployees(searchEmployee));
			int totalPages = (int) Math.ceil((double)totalSearchedEmployees / pageSize);

			request.setAttribute(EmployeeKeys.TOTAL_SEARCHED_EMPLOYEES, totalSearchedEmployees);
			request.setAttribute(PageKeys.CURRENT_PAGE, page);
			request.setAttribute(PageKeys.PAGE_SIZE, pageSize);
			request.setAttribute(PageKeys.TOTAL_PAGES, totalPages);
			
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Employee searchEmployee = EmployeeSession.getEmployee(request, EmployeeKeys.SEARCH_EMPLOYEE);
		
		if(searchEmployee == null) {
			searchEmployee = new Employee();
		}
		
		showEmployees(request, response, searchEmployee);
		
		request.getRequestDispatcher(Paths.EMPLOYEE_LIST_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Employee searchEmployee = new Employee();
		
		SearchCondition isCheckedEmployee = new SearchCondition();
		isCheckedEmployee.setCheckedEmployeeNum(request.getParameter(EmployeeKeys.SEARCH_EMPLOYEE_NUM) != null);
		isCheckedEmployee.setCheckedKanjiName(request.getParameter(EmployeeKeys.SEARCH_KANJI_NAME) != null);
		isCheckedEmployee.setCheckedRomanName(request.getParameter(EmployeeKeys.SEARCH_ROMAN_NAME) != null);
		isCheckedEmployee.setCheckedEmail(request.getParameter(EmployeeKeys.SEARCH_EMAIL) != null);
		isCheckedEmployee.setCheckedBirthday(request.getParameter(EmployeeKeys.SEARCH_BIRTHDAY) != null);
		isCheckedEmployee.setCheckedJoinDate(request.getParameter(EmployeeKeys.SEARCH_JOIN_DATE) != null);
		
		if(isCheckedEmployee.isCheckedEmployeeNum()) {
			searchEmployee.setEmployeeNum(request.getParameter(EmployeeKeys.EMPLOYEE_NUM));
		}
		if(isCheckedEmployee.isCheckedKanjiName()) {
			searchEmployee.setKanjiName(request.getParameter(EmployeeKeys.KANJI_NAME));
		}
		if(isCheckedEmployee.isCheckedRomanName()) {
			searchEmployee.setRomanName(request.getParameter(EmployeeKeys.ROMAN_NAME));
		}
		if(isCheckedEmployee.isCheckedEmail()) {
			searchEmployee.setEmail(request.getParameter(EmployeeKeys.EMAIL));
		}
		if(isCheckedEmployee.isCheckedBirthday()) {
			searchEmployee.setBirthday(request.getParameter(EmployeeKeys.BIRTHDAY));
		}
		if(isCheckedEmployee.isCheckedJoinDate()) {
			searchEmployee.setJoinDate(request.getParameter(EmployeeKeys.JOIN_DATE));
		}
		
		EmployeeSession.setIsCheckedEmployee(request, isCheckedEmployee);
		showEmployees(request, response, searchEmployee);
		
		request.getRequestDispatcher(Paths.EMPLOYEE_LIST_PATH).forward(request, response);
	}

}
