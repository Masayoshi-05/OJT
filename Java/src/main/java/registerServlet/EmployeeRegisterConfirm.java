package registerServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import definition.Definition;
import employeeManagement.Employee;
import validator.EmployeeValidator;


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
		employee.setEmployeeNum(request.getParameter(Definition.EMPLOYEE_NUM));
		employee.setKanjiLastName(request.getParameter(Definition.KANJI_LAST_NAME));
		employee.setKanjiFirstName(request.getParameter(Definition.KANJI_FIRST_NAME));
		employee.setRomanLastName(request.getParameter(Definition.ROMAN_LAST_NAME));
		employee.setRomanFirstName(request.getParameter(Definition.ROMAN_FIRST_NAME));
		employee.setEmail(request.getParameter(Definition.EMAIL));
		employee.setBirthday(request.getParameter(Definition.BIRTHDAY));
		employee.setJoinDate(request.getParameter(Definition.JOIN_DATE));
		
		HttpSession session = request.getSession();
		session.setAttribute("employee", employee);

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
			request.setAttribute("birthdayError", birthdayError);
			request.setAttribute("joinDateError", joinDateError);
			
			request.getRequestDispatcher(
					"/WEB-INF/lib/EmployeeRegisterInput.jsp").forward(request, response);
			return;
		} else {
			request.getRequestDispatcher(
					"/WEB-INF/lib/EmployeeRegisterConfirm.jsp").forward(request, response);
		}
	}

}
