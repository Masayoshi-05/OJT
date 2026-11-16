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
import definition.ErrorKeys;
import definition.Paths;
import employeeManagement.DbAccessor;
import employeeManagement.DbConnection;
import employeeManagement.Employee;
import employeeManagement.EmployeeSession;
import errorValues.ErrorValue;

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
		try (Connection connection = DbConnection.getConnection();) {
			Employee employee = EmployeeSession.getEmployee(request, EmployeeKeys.EMPLOYEE);

			DbAccessor dbAccessor = new DbAccessor(connection);
			ErrorValue generalError = new ErrorValue();
			
			if (dbAccessor.getEmployeeById(employee.getId()) == null) {
				generalError.setErrorValue("対象の社員情報が見つかりませんでした。");
				request.setAttribute(ErrorKeys.GENERAL_ERROR, generalError);
				request.getRequestDispatcher(Paths.EMPLOYEE_LIST_SERVLET_PATH).forward(request, response);
				return;
			}

			dbAccessor.deleteEmployee(employee);
			EmployeeSession.removeEmployee(request, EmployeeKeys.EMPLOYEE);
			request.getRequestDispatcher(Paths.DELETE_COMPLETE_PATH).forward(request, response);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}

}
