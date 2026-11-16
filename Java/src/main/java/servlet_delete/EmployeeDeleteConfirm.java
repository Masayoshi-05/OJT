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
		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);
			
			int id = Integer.valueOf(request.getParameter(EmployeeKeys.ID));
			
			Employee employee = dbAccessor.getEmployeeById(id);
			if (employee == null) {
				ErrorValue generalError = new ErrorValue();
				generalError.setErrorValue("対象の社員情報が見つかりませんでした。");
				request.setAttribute(ErrorKeys.GENERAL_ERROR, generalError);
				request.getRequestDispatcher(Paths.EMPLOYEE_LIST_SERVLET_PATH).forward(request, response);
				return;
			}
			
			EmployeeSession.setEmployee(request, EmployeeKeys.EMPLOYEE, employee);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
		request.getRequestDispatcher(Paths.DELETE_CONFIRM_PATH).forward(request, response);
	}

}
