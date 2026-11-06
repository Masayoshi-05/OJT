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

		Employee employee = EmployeeSession.getEmployee(request,EmployeeKeys.EMPLOYEE);

		employee.setEmployeeNum(request.getParameter(EmployeeKeys.EMPLOYEE_NUM));
		employee.setKanjiLastName(request.getParameter(EmployeeKeys.KANJI_LAST_NAME));
		employee.setKanjiFirstName(request.getParameter(EmployeeKeys.KANJI_FIRST_NAME));
		employee.setRomanLastName(request.getParameter(EmployeeKeys.ROMAN_LAST_NAME));
		employee.setRomanFirstName(request.getParameter(EmployeeKeys.ROMAN_FIRST_NAME));
		employee.setEmail(request.getParameter(EmployeeKeys.EMAIL));
		employee.setBirthday(request.getParameter(EmployeeKeys.BIRTHDAY));
		employee.setJoinDate(request.getParameter(EmployeeKeys.JOIN_DATE));

		EmployeeSession.setEmployee(request,EmployeeKeys.EMPLOYEE,employee);

		EmployeeValidator employeeValidator = new EmployeeValidator();

		ErrorValues errorValues = employeeValidator.validation(employee);

		if (!errorValues.hasError()) {
			try (Connection connection = DbConnection.getConnection();) {
				DbAccessor dbAccessor = new DbAccessor(connection);
				errorValues.setEmployeeNumError(dbAccessor.checkEmployeeNumDuplication(employee.getEmployeeNum(),employee.getID()));
				errorValues.setEmailError(dbAccessor.checkEmailDuplication(employee.getEmail(),employee.getID()));
			} catch (SQLException | ClassNotFoundException e) {
				throw new ServletException(e);
			}
		}

		if (errorValues.hasError()) {
			request.setAttribute(ErrorKeys.EMPLOYEE_NUM_ERROR, errorValues.getEmployeeNumError());
			request.setAttribute(ErrorKeys.KANJI_NAME_ERROR, errorValues.getKanjiNameError());
			request.setAttribute(ErrorKeys.ROMAN_NAME_ERROR, errorValues.getRomanNameError());
			request.setAttribute(ErrorKeys.EMAIL_ERROR, errorValues.getEmailError());
			request.setAttribute(ErrorKeys.BIRTHDAY_ERROR, errorValues.getBirthdayError());
			request.setAttribute(ErrorKeys.JOIN_DATE_ERROR, errorValues.getJoinDateError());
			request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
			return;
		}
		request.getRequestDispatcher(Paths.UPDATE_CONFIRM_PATH).forward(request, response);
	}

}
