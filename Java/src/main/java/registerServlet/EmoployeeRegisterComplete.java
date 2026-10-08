package registerServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.ErrorKeys;
import definition.Paths;
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

		if (errorValues.isHasError()) {
			request.setAttribute(ErrorKeys.EMPLOYEE_NUM_ERROR, errorValues.nullDisplay(errorValues.getEmployeeNumError()));
			request.setAttribute(ErrorKeys.KANJI_NAME_ERROR,errorValues.nullDisplay(errorValues.getKanjiNameError()));
			request.setAttribute(ErrorKeys.ROMAN_NAME_ERROR, errorValues.nullDisplay(errorValues.getRomanNameError()));
			request.setAttribute(ErrorKeys.EMAIL_ERROR, errorValues.nullDisplay(errorValues.getEmailError()));
			request.setAttribute(ErrorKeys.BIRTHDAY_ERROR, errorValues.nullDisplay(errorValues.getBirthdayError()));
			request.setAttribute(ErrorKeys.JOIN_DATE_ERROR, errorValues.nullDisplay(errorValues.getJoinDateError()));

			request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
			return;
		} else {
			request.getRequestDispatcher(Paths.REGISTER_COMPLETE_PATH).forward(request, response);
		}
	}

}
