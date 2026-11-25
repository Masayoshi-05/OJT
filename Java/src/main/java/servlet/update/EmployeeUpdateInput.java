package servlet.update;

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
import error.value.ValidationErrors;
import session.EmployeeSession;

/**
 * 更新機能:入力画面のServlet
 */
@WebServlet("/EmployeeUpdateInput")
public class EmployeeUpdateInput extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeUpdateInput() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ValidationErrors validationErrors = new ValidationErrors();
		request.setAttribute(ErrorKeys.VALIDATION_ERRORS, validationErrors);

		request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		EmployeeSession.removeEmployee(request, EmployeeKeys.EMPLOYEE);
		ValidationErrors validationErrors = new ValidationErrors();
		request.setAttribute(ErrorKeys.VALIDATION_ERRORS, validationErrors);

		try (Connection connection = DbConnection.getConnection();) {
			DbAccessor dbAccessor = new DbAccessor(connection);
			
			int id = Integer.valueOf(request.getParameter(EmployeeKeys.ID));
			Employee employee = dbAccessor.getEmployeeById(id);
			if (employee == null) {
				ErrorValue generalError = new ErrorValue();
				generalError.setMessage("対象の社員情報が見つかりませんでした。");
				request.setAttribute(ErrorKeys.GENERAL_ERROR, generalError);
				request.getRequestDispatcher(Paths.LIST_SERVLET_PATH).forward(request, response);
				return;
			}
			EmployeeSession.setEmployee(request, EmployeeKeys.EMPLOYEE, employee);
			request.getRequestDispatcher(Paths.UPDATE_INPUT_PATH).forward(request, response);
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}
}
