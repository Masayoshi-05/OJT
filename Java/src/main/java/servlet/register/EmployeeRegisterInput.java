package servlet.register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.key.EmployeeKeys;
import definition.key.ErrorKeys;
import definition.path.Paths;
import error.value.ValidationErrors;
import session.EmployeeSession;

/**
 * 登録機能:入力画面のServlet
 */
@WebServlet("/EmployeeRegisterInput")
public class EmployeeRegisterInput extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeRegisterInput() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmployeeSession.removeEmployee(request,EmployeeKeys.EMPLOYEE);
		
		ValidationErrors validationErrors = new ValidationErrors();
		request.setAttribute(ErrorKeys.VALIDATION_ERRORS,validationErrors);
		
		request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		ValidationErrors validationErrors = new ValidationErrors();
		request.setAttribute(ErrorKeys.VALIDATION_ERRORS,validationErrors);
		
		request.getRequestDispatcher(Paths.REGISTER_INPUT_PATH).forward(request, response);
	}
}
