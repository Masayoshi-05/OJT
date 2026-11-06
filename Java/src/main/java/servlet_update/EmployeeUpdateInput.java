package servlet_update;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.EmployeeKeys;
import definition.ErrorKeys;
import definition.Paths;
import employeeManagement.DbAccessor;
import employeeManagement.DbConnection;
import employeeManagement.Employee;
import employeeManagement.EmployeeSession;

/**
 * 更新機能:入力画面のServlet
 */
@WebServlet("/EmployeeUpdateInput")
public class EmployeeUpdateInput extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeUpdateInput() {
		super();
	}

	private void clearErrors(HttpServletRequest request) {
		request.setAttribute(ErrorKeys.EMPLOYEE_NUM_ERROR, "");
		request.setAttribute(ErrorKeys.KANJI_NAME_ERROR, "");
		request.setAttribute(ErrorKeys.ROMAN_NAME_ERROR, "");
		request.setAttribute(ErrorKeys.EMAIL_ERROR, "");
		request.setAttribute(ErrorKeys.BIRTHDAY_ERROR, "");
		request.setAttribute(ErrorKeys.JOIN_DATE_ERROR, "");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.clearErrors(request);
		request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		EmployeeSession.removeEmployee(request,EmployeeKeys.EMPLOYEE);
		Employee employee = new Employee();
		employee.setID(Integer.valueOf(request.getParameter(EmployeeKeys.ID)));
		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);
			dbAccessor.getEmployeeById(employee);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
		EmployeeSession.setEmployee(request,EmployeeKeys.EMPLOYEE,employee);
		this.clearErrors(request);

		request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
	}
}
