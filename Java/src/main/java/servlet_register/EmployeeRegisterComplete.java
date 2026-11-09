package servlet_register;

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
import validator.EmployeeValidator;
import validator.ErrorValues;

/**
 * 登録機能:完了画面のServlet
 */
@WebServlet("/EmployeeRegisterComplete")
public class EmployeeRegisterComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeRegisterComplete() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Employee employee = EmployeeSession.getEmployee(request, EmployeeKeys.EMPLOYEE);

		EmployeeValidator employeeValidator = new EmployeeValidator();
		ErrorValues errorValues = employeeValidator.validation(employee);

		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);

			if (!errorValues.hasError()) {
				errorValues.setEmployeeNumError(dbAccessor.checkEmployeeNumDuplication(employee.getEmployeeNum()));
				errorValues.setEmailError(dbAccessor.checkEmailDuplication(employee.getEmail()));
			}
			if (errorValues.hasError()) {
				request.setAttribute(ErrorKeys.ERROR_VALUES, errorValues);
				request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
				return;
			}

			dbAccessor.insertEmployee(employee);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
		EmployeeSession.removeEmployee(request, EmployeeKeys.EMPLOYEE);
		request.getRequestDispatcher(Paths.REGISTER_COMPLETE_PATH).forward(request, response);
	}

}
