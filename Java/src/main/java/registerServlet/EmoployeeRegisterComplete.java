package registerServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import employeeManagement.Employee;
import validator.EmployeeValidator;

@WebServlet("/EmployeeRegisterComplete")
public class EmoployeeRegisterComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmoployeeRegisterComplete() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		Employee employee =
		(Employee) session.getAttribute("employee");

		String employeeNumError = EmployeeValidator.validateEmployeeNum(employee.getEmployeeNum());
		String kanjiNameError = EmployeeValidator.validateKanjiName(employee.getKanjiLastName(), employee.getKanjiFirstName());
		String romanNameError = EmployeeValidator.validateRomanName(employee.getRomanLastName(), employee.getRomanFirstName());
		String emailError = EmployeeValidator.validateEmail(employee.getEmail());
		String birthdayError = EmployeeValidator.validateDate(employee.getBirthday());
		String joinDateError = EmployeeValidator.validateDate(employee.getJoinDate());
		
		boolean hasError = employeeNumError != null
				|| kanjiNameError != null
				|| romanNameError != null
				|| emailError != null
				|| birthdayError != null
				|| joinDateError != null;

		if (hasError) {
			request.setAttribute("employeeNumError", employeeNumError);
			request.setAttribute("kanjiNameError", kanjiNameError);
			request.setAttribute("romanNameError", romanNameError);
			request.setAttribute("emailError", emailError);
			request.setAttribute("birthDayError", birthdayError);
			request.setAttribute("joinDateError", joinDateError);

			request.getRequestDispatcher(
					"/WEB-INF/lib/EmployeeRegisterInput.jsp").forward(request, response);
			return;
		} else {
			request.getRequestDispatcher(
					"/WEB-INF/lib/EmployeeRegisterComplete.jsp").forward(request, response);
		}
	}

}
