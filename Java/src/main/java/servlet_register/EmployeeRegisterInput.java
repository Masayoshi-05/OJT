package servlet_register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.ErrorKeys;
import definition.Paths;
import employeeManagement.EmployeeSession;

/*
 * 入力画面を表示するServlet
 */
@WebServlet("/EmployeeRegisterInput")
public class EmployeeRegisterInput extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeRegisterInput() {
		super();
	}
	
	private void clearErrors(HttpServletRequest request) {
		request.setAttribute(ErrorKeys.EMPLOYEE_NUM_ERROR, "");
		request.setAttribute(ErrorKeys.KANJI_NAME_ERROR, "");
		request.setAttribute(ErrorKeys.ROMAN_NAME_ERROR, "");
		request.setAttribute(ErrorKeys.EMAIL_ERROR, "");
		request.setAttribute(ErrorKeys.BIRTHDAY_ERROR, "");
		request.setAttribute(ErrorKeys.JOIN_DATE_ERROR, "");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmployeeSession.removeEmployee(request);
		
		this.clearErrors(request);
		
		request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		this.clearErrors(request);
		
		request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
	}
}
