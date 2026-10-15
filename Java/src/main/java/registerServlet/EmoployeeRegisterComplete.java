package registerServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.ErrorKeys;
import definition.Paths;
import employeeManagement.DBConnection;
import employeeManagement.DbAccessor;
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
		EmployeeValidator.callValidators(employee, errorValues);

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
			try(Connection connection = DBConnection.getConnection();) {
				DbAccessor dbControl = new DbAccessor();
				dbControl.insertEmployee(connection,employee);
				EmployeeSession.removeEmployee(request);
				request.getRequestDispatcher(Paths.REGISTER_COMPLETE_PATH).forward(request, response);
			} catch (SQLException | ClassNotFoundException e) {
				request.getRequestDispatcher(Paths.ERROR_PATH).forward(request, response);
			}
		}
	}

}
