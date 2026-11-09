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

		ErrorValues errorValues = employeeValidator.validation(employee);

		if (!errorValues.hasError()) {
			try (Connection connection = DbConnection.getConnection();) {
				DbAccessor dbAccessor = new DbAccessor(connection);
				errorValues.setEmployeeNumError(dbAccessor.checkEmployeeNumDuplication(employee.getEmployeeNum()));
				errorValues.setEmailError(dbAccessor.checkEmailDuplication(employee.getEmail()));
			} catch (SQLException | ClassNotFoundException e) {
				throw new ServletException(e);
			}
		}

		if (errorValues.hasError()) {
			request.setAttribute(ErrorKeys.ERROR_VALUES, errorValues);
			request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
			return;
		} else {
			request.getRequestDispatcher(Paths.REGISTER_CONFIRM_PATH).forward(request, response);
		}
	}

}
