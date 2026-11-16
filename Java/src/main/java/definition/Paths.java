package definition;

/**
 * 各画面へのパス
 */

public class Paths {
	//一覧表示画面
	public static final String EMPLOYEE_LIST_PATH = "/WEB-INF/jsp/EmployeeList.jsp";
	public static final String EMPLOYEE_LIST_SERVLET_PATH = "/EmployeeList";
	
	//登録機能
	public static final String REGISTER_INPUT_PATH = "/WEB-INF/jsp/EmployeeRegisterInput.jsp";
	public static final String REGISTER_CONFIRM_PATH = "/WEB-INF/jsp/EmployeeRegisterConfirm.jsp";
	public static final String REGISTER_COMPLETE_PATH = "/WEB-INF/jsp/EmployeeRegisterComplete.jsp";
	
	//更新機能
	public static final String UPDATE_INPUT_PATH = "/WEB-INF/jsp/EmployeeUpdateInput.jsp";
	public static final String UPDATE_CONFIRM_PATH = "/WEB-INF/jsp/EmployeeUpdateConfirm.jsp";
	public static final String UPDATE_COMPLETE_PATH = "/WEB-INF/jsp/EmployeeUpdateComplete.jsp";
	
	//削除機能
	public static final String DELETE_CONFIRM_PATH = "/WEB-INF/jsp/EmployeeDeleteConfirm.jsp";
	public static final String DELETE_COMPLETE_PATH = "/WEB-INF/jsp/EmployeeDeleteComplete.jsp";
	
	//エラー
	public static final String ERROR_PATH = "/WEB-INF/jsp/Error.jsp";
}
