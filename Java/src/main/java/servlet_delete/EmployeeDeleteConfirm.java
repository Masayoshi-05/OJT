package servlet_delete;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import definition.EmployeeKeys;
import definition.Paths;
import employeeManagement.DbAccessor;
import employeeManagement.DbConnection;
import employeeManagement.Employee;
import employeeManagement.EmployeeSession;

/**
 * 削除機能:確認画面のServlet
 */
@WebServlet("/EmployeeDeleteConfirm")
public class EmployeeDeleteConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeDeleteConfirm() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		EmployeeSession.removeEmployee(request,EmployeeKeys.EMPLOYEE);

		Employee employee = new Employee();
		employee.setID(Integer.valueOf(request.getParameter(EmployeeKeys.ID)));
		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);
			dbAccessor.getEmployeeById(employee);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
		EmployeeSession.setEmployee(request, EmployeeKeys.EMPLOYEE, employee);
		request.getRequestDispatcher(Paths.DELETE_CONFIRM_PATH).forward(request, response);
	}

}
