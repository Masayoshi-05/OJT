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
import validator.EmployeeValidator;
import validator.ErrorValues;

/**
 * 更新機能:完了画面のServlet
 */
@WebServlet("/EmployeeUpdateComplete")
public class EmoployeeUpdateComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmoployeeUpdateComplete() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Employee employee = EmployeeSession.getEmployee(request, EmployeeKeys.EMPLOYEE);

		EmployeeValidator employeeValidator = new EmployeeValidator();
		ErrorValues errorValues = employeeValidator.validation(employee);

		if (!errorValues.hasError()) {
			try (Connection connection = DbConnection.getConnection();) {
				DbAccessor dbAccessor = new DbAccessor(connection);
				errorValues.setEmployeeNumError(dbAccessor.checkEmployeeNumDuplication(employee.getEmployeeNum(), employee.getID()));
				errorValues.setEmailError(dbAccessor.checkEmailDuplication(employee.getEmail(), employee.getID()));

				if (errorValues.hasError()) {
					request.setAttribute(ErrorKeys.ERROR_VALUES,errorValues);
					request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
					return;
				}

				dbAccessor.updateEmployee(employee);
				EmployeeSession.removeEmployee(request, EmployeeKeys.EMPLOYEE);
				request.getRequestDispatcher(Paths.UPDATE_COMPLETE_PATH).forward(request, response);
			} catch (SQLException | ClassNotFoundException e) {
				throw new ServletException(e);
			}
		}
	}
}
