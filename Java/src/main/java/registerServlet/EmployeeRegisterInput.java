package registerServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.Keys;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmployeeSession.removeEmployee(request);
		request.getRequestDispatcher(Keys.REGISTER_INPUT_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher(Keys.REGISTER_INPUT_PATH).forward(request, response);
	}
}
