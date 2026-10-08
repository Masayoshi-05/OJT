package registerServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.Keys;
import employeeManagement.Employee;
import employeeManagement.EmployeeSession;
import validator.EmployeeValidator;
import validator.ErrorValues;

/*
 * 完了画面を表示するServlet
 */
@WebServlet("/EmployeeRegisterComplete")
public class EmoployeeRegisterComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmoployeeRegisterComplete() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Employee employee = EmployeeSession.getEmployee(request);

		ErrorValues errorValues = new ErrorValues();
		errorValues.setEmployeeNumError(EmployeeValidator.validateEmployeeNum(employee.getEmployeeNum()));
		errorValues.setKanjiNameError(
				EmployeeValidator.validateKanjiName(employee.getKanjiLastName(), employee.getKanjiFirstName()));
		errorValues.setRomanNameError(
				EmployeeValidator.validateRomanName(employee.getRomanLastName(), employee.getRomanFirstName()));
		errorValues.setEmailError(EmployeeValidator.validateEmail(employee.getEmail()));
		errorValues.setBirthdayError(EmployeeValidator.validateDate(employee.getBirthday()));
		errorValues.setJoinDateError(EmployeeValidator.validateDate(employee.getJoinDate()));

		boolean hasError = errorValues.getEmployeeNumError() != null
				|| errorValues.getKanjiNameError() != null
				|| errorValues.getRomanNameError() != null
				|| errorValues.getEmailError() != null
				|| errorValues.getBirthdayError() != null
				|| errorValues.getJoinDateError() != null;

		if (hasError) {
			request.setAttribute(Keys.EMPLOYEE_NUM_ERROR, errorValues.getEmployeeNumError());
			request.setAttribute(Keys.KANJI_NAME_ERROR, errorValues.getKanjiNameError());
			request.setAttribute(Keys.ROMAN_NAME_ERROR, errorValues.getRomanNameError());
			request.setAttribute(Keys.EMAIL_ERROR, errorValues.getEmailError());
			request.setAttribute(Keys.BIRTHDAY_ERROR, errorValues.getBirthdayError());
			request.setAttribute(Keys.JOIN_DATE_ERROR, errorValues.getJoinDateError());

			request.getRequestDispatcher(Keys.REGISTER_INPUT_PATH).forward(request, response);
			return;
		} else {
			request.getRequestDispatcher(Keys.REGISTER_COMPLETE_PATH).forward(request, response);
		}
	}

}
