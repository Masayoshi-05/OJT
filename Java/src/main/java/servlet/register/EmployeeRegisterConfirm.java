package servlet.register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.employee.Employee;
import db.DbAccessor;
import db.DbConnection;
import definition.key.EmployeeKeys;
import definition.key.ErrorKeys;
import definition.path.Paths;
import error.validator.EmployeeValidator;
import error.value.DuplicationErrors;
import error.value.ValidationErrors;
import session.EmployeeSession;

/**
 * 登録機能:確認画面のServlet
 */
@WebServlet("/EmployeeRegisterConfirm")
public class EmployeeRegisterConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeRegisterConfirm() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Employee employee = new Employee();
		employee.setEmployeeNum(request.getParameter(EmployeeKeys.EMPLOYEE_NUM));
		employee.setKanjiLastName(request.getParameter(EmployeeKeys.KANJI_LAST_NAME));
		employee.setKanjiFirstName(request.getParameter(EmployeeKeys.KANJI_FIRST_NAME));
		employee.setRomanLastName(request.getParameter(EmployeeKeys.ROMAN_LAST_NAME));
		employee.setRomanFirstName(request.getParameter(EmployeeKeys.ROMAN_FIRST_NAME));
		employee.setEmail(request.getParameter(EmployeeKeys.EMAIL));
		employee.setBirthday(request.getParameter(EmployeeKeys.BIRTHDAY));
		employee.setJoinDate(request.getParameter(EmployeeKeys.JOIN_DATE));

		EmployeeSession.setEmployee(request, EmployeeKeys.EMPLOYEE, employee);

		EmployeeValidator employeeValidator = new EmployeeValidator();
		ValidationErrors validationErrors = employeeValidator.validation(employee);
		if (validationErrors.hasError()) {
			request.setAttribute(ErrorKeys.VALIDATION_ERRORS, validationErrors);
			request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
			return;
		}
		
		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);

			DuplicationErrors duplicationErrors = new DuplicationErrors();
			duplicationErrors.setEmployeeNumError(dbAccessor.checkEmployeeNumDuplication(employee.getEmployeeNum()));
			duplicationErrors.setEmailError(dbAccessor.checkEmailDuplication(employee.getEmail()));

			if (duplicationErrors.hasError()) {
				request.setAttribute(ErrorKeys.DUPLICATION_ERRORS, duplicationErrors);
				request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
				return;
			}

		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}

		request.getRequestDispatcher(Paths.REGISTER_CONFIRM_PATH).forward(request, response);
	}

}
