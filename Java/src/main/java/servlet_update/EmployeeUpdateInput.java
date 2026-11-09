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
import validator.ErrorValues;

/**
 * 更新機能:入力画面のServlet
 */
@WebServlet("/EmployeeUpdateInput")
public class EmployeeUpdateInput extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeUpdateInput() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ErrorValues errorValues = new ErrorValues();
		request.setAttribute(ErrorKeys.ERROR_VALUES,errorValues);
		
		request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		EmployeeSession.removeEmployee(request, EmployeeKeys.EMPLOYEE);
		
		ErrorValues errorValues = new ErrorValues();
		request.setAttribute(ErrorKeys.ERROR_VALUES,errorValues);

		int id = Integer.valueOf(request.getParameter(EmployeeKeys.ID));

		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);
			Employee employee = dbAccessor.getEmployeeById(id);
			if(employee == null) {
				request.getRequestDispatcher(Paths.ERROR_PATH).forward(request, response);
				return;
			}
			EmployeeSession.setEmployee(request, EmployeeKeys.EMPLOYEE, employee);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
		request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
	}
}
