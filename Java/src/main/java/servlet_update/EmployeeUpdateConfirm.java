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
import errorValues.DuplicationErrors;
import errorValues.ErrorValue;
import errorValues.ValidationErrors;
import validator.EmployeeValidator;

/**
 * 更新機能:確認画面のServlet
 */
@WebServlet("/EmployeeUpdateConfirm")
public class EmployeeUpdateConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeUpdateConfirm() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Employee employee = EmployeeSession.getEmployee(request, EmployeeKeys.EMPLOYEE);

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
			request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
			return;
		}
		
		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);

			DuplicationErrors duplicationErrors = new DuplicationErrors();
			duplicationErrors.setEmployeeNumError(dbAccessor.checkEmployeeNumDuplication(employee.getEmployeeNum(), employee.getId()));
			duplicationErrors.setEmailError(dbAccessor.checkEmailDuplication(employee.getEmail(), employee.getId()));

			if (duplicationErrors.hasError()) {
				request.setAttribute(ErrorKeys.DUPLICATION_ERRORS, duplicationErrors);
				request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
				return;
			}

			if (dbAccessor.getEmployeeById(employee.getId()) == null) {
				ErrorValue generalError = new ErrorValue();
				generalError.setErrorValue("対象の社員情報が見つかりませんでした。");
				request.setAttribute(ErrorKeys.GENERAL_ERROR, generalError);
				request.getRequestDispatcher(Paths.EMPLOYEE_LIST_SERVLET_PATH).forward(request, response);
				return;
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}

		request.getRequestDispatcher(Paths.UPDATE_CONFIRM_PATH).forward(request, response);
	}

}
