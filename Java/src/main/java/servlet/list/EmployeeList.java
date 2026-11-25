package servlet.list;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.employee.Employee;
import data.employee.IsCheckedEmployee;
import data.employee.ConditionEmployee;
import db.DbAccessor;
import db.DbConnection;
import definition.key.EmployeeKeys;
import definition.path.Paths;

/**
 * 一覧表示画面のServlet
 */
@WebServlet(Paths.LIST_SERVLET_PATH)
public class EmployeeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeList() {
		super();
	}
	
	private void setEmployees(HttpServletRequest request, HttpServletResponse response,ConditionEmployee conditionEmployee) throws ServletException {

		try (Connection connection = DbConnection.getConnection();) {

			DbAccessor dbAccessor = new DbAccessor(connection);
			List<Employee> conditionEmployees = dbAccessor.searchEmployees(conditionEmployee);
			
			request.setAttribute(EmployeeKeys.EMPLOYEES, conditionEmployees);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ConditionEmployee conditionEmployee = new ConditionEmployee();
		setEmployees(request, response, conditionEmployee);
		
		request.getRequestDispatcher(Paths.LIST_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		IsCheckedEmployee isCheckedEmployee = new IsCheckedEmployee();
		isCheckedEmployee.setCheckedEmployeeNum(request.getParameter(EmployeeKeys.IS_CHECKED_EMPLOYEE_NUM) != null);
		isCheckedEmployee.setCheckedKanjiName(request.getParameter(EmployeeKeys.IS_CHECKED_KANJI_NAME) != null);
		isCheckedEmployee.setCheckedRomanName(request.getParameter(EmployeeKeys.IS_CHECKED_ROMAN_NAME) != null);
		isCheckedEmployee.setCheckedEmail(request.getParameter(EmployeeKeys.IS_CHECKED_EMAIL) != null);
		isCheckedEmployee.setCheckedBirthday(request.getParameter(EmployeeKeys.IS_CHECKED_BIRTHDAY) != null);
		isCheckedEmployee.setCheckedJoinDate(request.getParameter(EmployeeKeys.IS_CHECKED_JOIN_DATE) != null);
		
		ConditionEmployee conditionEmployee = new ConditionEmployee();
		if(isCheckedEmployee.isCheckedEmployeeNum()) {
			conditionEmployee.setEmployeeNum(request.getParameter(EmployeeKeys.EMPLOYEE_NUM));
		}
		if(isCheckedEmployee.isCheckedKanjiName()) {
			conditionEmployee.setKanjiName(request.getParameter(EmployeeKeys.KANJI_NAME));
		}
		if(isCheckedEmployee.isCheckedRomanName()) {
			conditionEmployee.setRomanName(request.getParameter(EmployeeKeys.ROMAN_NAME));
		}
		if(isCheckedEmployee.isCheckedEmail()) {
			conditionEmployee.setEmail(request.getParameter(EmployeeKeys.EMAIL));
		}
		if(isCheckedEmployee.isCheckedBirthday()) {
			conditionEmployee.setBirthday(request.getParameter(EmployeeKeys.BIRTHDAY));
		}
		if(isCheckedEmployee.isCheckedJoinDate()) {
			conditionEmployee.setJoinDate(request.getParameter(EmployeeKeys.JOIN_DATE));
		}
		
		request.setAttribute(EmployeeKeys.CONDITION_EMPLOYEE, conditionEmployee);
		request.setAttribute(EmployeeKeys.IS_CHECKED_EMPLOYEE, isCheckedEmployee);
		setEmployees(request, response, conditionEmployee);
		
		request.getRequestDispatcher(Paths.LIST_PATH).forward(request, response);
	}

}
