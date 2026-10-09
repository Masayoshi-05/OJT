package registerServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.EmployeeKeys;
import definition.ErrorKeys;
import definition.Paths;
import employeeManagement.Employee;
import employeeManagement.EmployeeSession;
import validator.EmployeeValidator;
import validator.ErrorValues;

/*
 * 確認画面を表示するServlet
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
		
		EmployeeSession.setEmployee(request,employee);
		
		ErrorValues errorValues = new ErrorValues();
		errorValues.setEmployeeNumError(EmployeeValidator.validateEmployeeNum(employee.getEmployeeNum()));
		errorValues.setKanjiNameError(EmployeeValidator.validateKanjiName(employee.getKanjiLastName(), employee.getKanjiFirstName()));
		errorValues.setRomanNameError(EmployeeValidator.validateRomanName(employee.getRomanLastName(), employee.getRomanFirstName()));
		errorValues.setEmailError(EmployeeValidator.validateEmail(employee.getEmail()));
		errorValues.setBirthdayError(EmployeeValidator.validateDate(employee.getBirthday()));
		errorValues.setJoinDateError(EmployeeValidator.validateDate(employee.getJoinDate()));

		if (errorValues.hasError()) {
			request.setAttribute(ErrorKeys.EMPLOYEE_NUM_ERROR, errorValues.getEmployeeNumError());
			request.setAttribute(ErrorKeys.KANJI_NAME_ERROR,errorValues.getKanjiNameError());
			request.setAttribute(ErrorKeys.ROMAN_NAME_ERROR, errorValues.getRomanNameError());
			request.setAttribute(ErrorKeys.EMAIL_ERROR, errorValues.getEmailError());
			request.setAttribute(ErrorKeys.BIRTHDAY_ERROR, errorValues.getBirthdayError());
			request.setAttribute(ErrorKeys.JOIN_DATE_ERROR, errorValues.getJoinDateError());
			
			request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
			return;
		} else {
			request.getRequestDispatcher(Paths.REGISTER_CONFIRM_PATH).forward(request, response);
		}
	}

}
