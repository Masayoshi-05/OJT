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
 * 削除機能:完了画面のServlet
 */
@WebServlet("/EmployeeDeleteComplete")
public class EmoployeeDeleteComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmoployeeDeleteComplete() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Employee employee = EmployeeSession.getEmployee(request,EmployeeKeys.EMPLOYEE);
		
		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);
			dbAccessor.deleteEmployee(employee);
			EmployeeSession.removeEmployee(request,EmployeeKeys.EMPLOYEE);
			request.getRequestDispatcher(Paths.DELETE_COMPLETE_PATH).forward(request, response);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}

}
