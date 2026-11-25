package servlet.delete;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.employee.Employee;
import db.DbAccessor;
import db.DbConnection;
import definition.key.EmployeeKeys;
import definition.key.ErrorKeys;
import definition.path.Paths;
import error.value.ErrorValue;
import session.EmployeeSession;

/**
 * 削除機能:完了画面のServlet
 */
@WebServlet("/EmployeeDeleteComplete")
public class EmployeeDeleteComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeDeleteComplete() {
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
				generalError.setMessage("対象の社員情報が見つかりませんでした。");
				request.setAttribute(ErrorKeys.GENERAL_ERROR, generalError);
				request.getRequestDispatcher(Paths.LIST_SERVLET_PATH).forward(request, response);
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
