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
import errorValues.DuplicationErrors;
import errorValues.ValidationErrors;
import validator.EmployeeValidator;

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

		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);

			EmployeeValidator employeeValidator = new EmployeeValidator();
			ValidationErrors validationErrors = employeeValidator.validation(employee);

			if (validationErrors.hasError()) {
				request.setAttribute(ErrorKeys.VALIDATION_ERRORS, validationErrors);
				request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
				return;
			}
			
			DuplicationErrors duplicationErrors = new DuplicationErrors();
			duplicationErrors.setEmployeeNumError(dbAccessor.checkEmployeeNumDuplication(employee.getEmployeeNum()));
			duplicationErrors.setEmailError(dbAccessor.checkEmailDuplication(employee.getEmail()));

			if (duplicationErrors.hasError()) {
				request.setAttribute(ErrorKeys.DUPLICATION_ERRORS, duplicationErrors);
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
