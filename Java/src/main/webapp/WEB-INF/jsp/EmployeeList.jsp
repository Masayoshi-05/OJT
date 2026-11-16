<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="definition.EmployeeKeys" %>
<%@ page import="definition.PageKeys" %>
<%@ page import="java.util.List" %>
<%@ page import="employeeManagement.Employee" %>
<%@ page import="employeeManagement.SearchCondition" %>

<%
	int totalSearchedEmployees = (Integer)request.getAttribute(EmployeeKeys.TOTAL_SEARCHED_EMPLOYEES);
	int currentPage = (Integer)request.getAttribute(PageKeys.CURRENT_PAGE);
	int totalPages = (Integer)request.getAttribute(PageKeys.TOTAL_PAGES);
	int pageSize = (Integer)request.getAttribute(PageKeys.PAGE_SIZE);
%>
<% List<Employee> employees = (List<Employee>)request.getAttribute(EmployeeKeys.EMPLOYEES); %>
<% SearchCondition condition = (SearchCondition)session.getAttribute(SearchCondition.IS_CHECKED_EMPLOYEE);
	if(condition == null){condition = new SearchCondition();}
%>
	<!DOCTYPE html>
	<html lang="ja">

	<head>
		<meta charset="UTF-8">

		<title>社員情報一覧</title>

		<link rel="stylesheet" href="css/style.css">
	</head>

	<body>
		<div class="errorMessage" id="employeeList">
			${generalError.errorValue}
		</div>
		<div class="pageHeader">
			<h2>社員情報一覧</h2>
		</div>
		<div class="singleButtonArea">
			<form id="employeeForm" action="<%= request.getContextPath() %>/EmployeeRegisterInput"
				method="get">
				<button type="submit">社員情報の新規登録</button>
			</form>
		</div>
		<details class="searchArea">
			<summary>検索</summary>
			<form action="EmployeeList" method="post">
				<table>
					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.SEARCH_EMPLOYEE_NUM %>"
							<%= condition.isCheckedEmployeeNum() ? "checked" : "" %>>
							社員番号
						</td>
						<td>
							<input type="text" name="<%= EmployeeKeys.EMPLOYEE_NUM %>" placeholder="Search"
							value="${sessionScope.searchEmployee.employeeNum}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.SEARCH_KANJI_NAME %>" 
							<%= condition.isCheckedKanjiName() ? "checked" : "" %>>
							氏名(漢字)
						</td>

						<td>
							<input type="text" name="<%= EmployeeKeys.KANJI_NAME %>" placeholder="Search"
							value="${sessionScope.searchEmployee.kanjiName}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.SEARCH_ROMAN_NAME %>" 
							<%= condition.isCheckedRomanName() ? "checked" : "" %>>
							氏名(ローマ字)
						</td>

						<td>
							<input type="text" name="<%= EmployeeKeys.ROMAN_NAME %>" placeholder="Search"
							value="${sessionScope.searchEmployee.romanName}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.SEARCH_EMAIL %>" 
							<%= condition.isCheckedEmail() ? "checked" : "" %>>
							メールアドレス
						</td>
						<td>
							<input type="text" name="<%= EmployeeKeys.EMAIL %>" placeholder="Search"
							value="${sessionScope.searchEmployee.email}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.SEARCH_BIRTHDAY %>" 
							<%= condition.isCheckedBirthday() ? "checked" : "" %>>
							生年月日
						</td>

						<td>
							<input type="text" name="<%= EmployeeKeys.BIRTHDAY %>" placeholder="Search"
							value="${sessionScope.searchEmployee.birthday}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.SEARCH_JOIN_DATE %>" 
							<%= condition.isCheckedJoinDate() ? "checked" : "" %>>
							入社年月日
						</td>

						<td>
							<input type="text" name="<%= EmployeeKeys.JOIN_DATE %>" placeholder="Search"
							value="${sessionScope.searchEmployee.joinDate}">
						</td>
					</tr>
				</table>

				<div class="searchButtonArea">
					<input type="button" value="検索条件をクリア" onclick="clearSearchForm()">
					<input type="submit" value="検索">
				</div>
			</form>
		</details>
		
		<div class = displayTotalAndCurrentPage>
			<% if(currentPage * pageSize > totalSearchedEmployees){ %>
				<%= (currentPage - 1) * pageSize + 1 %>～<%= totalSearchedEmployees %>件/<%= totalSearchedEmployees %>件中
			<% }else{ %>
				<%= (currentPage - 1) * pageSize + 1 %>～<%= currentPage * pageSize %>件/<%= totalSearchedEmployees %>件中
			<% } %>
		</div>
		
		<div class="pagingButtons">
			<span class="pageSkip">
				<% if(currentPage > 1){ %>
					<a href="EmployeeList?page=<%= 1 %>">
						<<
					</a>
				<% }else{ %>
					<span></span>
				<% } %>
				<% if(currentPage > 1){ %>
					<a href="EmployeeList?page=<%= currentPage - 1 %>">
						<
					</a>
				<% }else{ %>
					<span></span>
				<% } %>
			</span>

			<span class = "pageNums">
				<% if(totalPages >= 6 && currentPage >= 4){ %>
					<span>
						...
					</span>
				<% } %>
				<% if(totalPages - currentPage == 0){ %>
					<% if(totalPages == 4){ %>
						<a href="EmployeeList?page=<%= currentPage - 3 %>">
							<%= currentPage - 3 %>
						</a>
					<% }else if(totalPages >= 5){ %>
						<a href="EmployeeList?page=<%= currentPage - 4 %>">
							<%= currentPage - 4 %>
						</a>
						<a href="EmployeeList?page=<%= currentPage - 3 %>">
							<%= currentPage - 3 %>
						</a>
					<% } %>
				<% } %>
				<% if(totalPages - currentPage == 1 && totalPages >= 5){ %>
					<a href="EmployeeList?page=<%= currentPage - 3 %>">
						<%= currentPage - 3 %>
					</a>
				<% } %>
				<% for(int i = currentPage - 2 ; i <= currentPage + 2 ; i++){ %>
					<% if(i == currentPage){ %>
						<strong><%= i %></strong>
					<% }else if(i > 0 && i <= totalPages){ %>
						<a href="EmployeeList?page=<%= i %>">
							<%= i %>
						</a>
					<% } %>
				<% } %>
				<% if(currentPage == 1){ %>
					<% if(totalPages == 4){ %>
						<a href="EmployeeList?page=4">
							4
						</a>
					<% }else if(totalPages >= 5){ %>
						<a href="EmployeeList?page=4">
							4
						</a>
						<a href="EmployeeList?page=5">
							5
						</a>
					<% } %>
				<% } %>
				<% if(currentPage == 2 && totalPages >= 5){ %>
					<a href="EmployeeList?page=5">
						5
					</a>
				<% } %>
				<% if(totalPages >= 6 && totalPages - currentPage >= 3){ %>
					<span>
						...
					</span>
				<% } %>
			</span>
			
			<span class = "pageSkip">
				<% if(currentPage < totalPages){ %>
					<a href="EmployeeList?page=<%= currentPage + 1 %>">
				    	>
					</a>
				<% }else{ %>
					<span></span>
				<% } %>

				<% if(currentPage < totalPages){ %>
					<a href="EmployeeList?page=<%= totalPages %>">
						>>
					</a>
				<% }else{ %>
					<span></span>
				<% } %>
			</span>
		</div>
		
		<table class = "employeeTable" border="1">

			<tr>
				<th>社員番号</th>
				<th>氏名(漢字)</th>
				<th>氏名(ローマ字)</th>
				<th>メールアドレス</th>
				<th>生年月日</th>
				<th>入社年月日</th>
				<th>更新</th>
				<th>削除</th>
			</tr>

			<% for(Employee employee : employees){ %>
				<tr>
					<td>
						<%= employee.getEmployeeNum() %>
					</td>
					<td>
						<%= employee.getKanjiLastName() %>　<%= employee.getKanjiFirstName() %>
					</td>
					<td>
						<%= employee.getRomanLastName() %>　<%= employee.getRomanFirstName() %>
					</td>
					<td>
						<%= employee.getEmail() %>
					</td>
					<td>
						<%= employee.getBirthday() %>
					</td>
					<td>
						<%= employee.getJoinDate() %>
					</td>
					<td>
						<form action="EmployeeUpdateInput" method="post">
							<input type="hidden" name="<%= EmployeeKeys.ID %>" value="<%= employee.getId() %>">
							<input type="submit" value="更新">
						</form>
					</td>
					<td>
						<form action="EmployeeDeleteConfirm" method="post">
							<input type="hidden" name="<%= EmployeeKeys.ID %>" value="<%= employee.getId() %>">
							<input type="submit" value="削除">
						</form>
					</td>
				</tr>

			<% } %>

		</table>

		<div class="pagingButtons">
			<span class="pageSkip">
				<% if(currentPage > 1){ %>
					<a href="EmployeeList?page=<%= 1 %>">
						<<
					</a>
				<% }else{ %>
					<span></span>
				<% } %>
				<% if(currentPage > 1){ %>
					<a href="EmployeeList?page=<%= currentPage - 1 %>">
						<
					</a>
				<% }else{ %>
					<span></span>
				<% } %>
			</span>
			
			<span class = "pageNums">
				<% if(totalPages >= 6 && currentPage >= 4){ %>
					<span>
						...
					</span>
				<% } %>
				<% if(totalPages - currentPage == 0){ %>
					<% if(totalPages == 4){ %>
						<a href="EmployeeList?page=<%= currentPage - 3 %>">
							<%= currentPage - 3 %>
						</a>
					<% }else if(totalPages >= 5){ %>
						<a href="EmployeeList?page=<%= currentPage - 4 %>">
							<%= currentPage - 4 %>
						</a>
						<a href="EmployeeList?page=<%= currentPage - 3 %>">
							<%= currentPage - 3 %>
						</a>
					<% } %>
				<% } %>
				<% if(totalPages - currentPage == 1 && totalPages >= 5){ %>
					<a href="EmployeeList?page=<%= currentPage - 3 %>">
						<%= currentPage - 3 %>
					</a>
				<% } %>
				<% for(int i = currentPage - 2 ; i <= currentPage + 2 ; i++){ %>
					<% if(i == currentPage){ %>
						<strong><%= i %></strong>
					<% }else if(i > 0 && i <= totalPages){ %>
						<a href="EmployeeList?page=<%= i %>">
							<%= i %>
						</a>
					<% } %>
				<% } %>
				<% if(currentPage == 1){ %>
					<% if(totalPages == 4){ %>
						<a href="EmployeeList?page=4">
							4
						</a>
					<% }else if(totalPages >= 5){ %>
						<a href="EmployeeList?page=4">
							4
						</a>
						<a href="EmployeeList?page=5">
							5
						</a>
					<% } %>
				<% } %>
				<% if(currentPage == 2 && totalPages >= 5){ %>
					<a href="EmployeeList?page=5">
						5
					</a>
				<% } %>
				<% if(totalPages >= 6 && totalPages - currentPage >= 3){ %>
					<span>
						...
					</span>
				<% } %>
			</span>

			<span class = "pageSkip">
				<% if(currentPage < totalPages){ %>
					<a href="EmployeeList?page=<%= currentPage + 1 %>">
				    	>
					</a>
				<% }else{ %>
					<span></span>
				<% } %>

				<% if(currentPage < totalPages){ %>
					<a href="EmployeeList?page=<%= totalPages %>">
						>>
					</a>
				<% }else{ %>
					<span></span>
				<% } %>
			</span>
		</div>
		
		<div class = displayTotalAndCurrentPage>
			<% if(currentPage * pageSize > totalSearchedEmployees){ %>
				<%= (currentPage - 1) * pageSize + 1 %>～<%= totalSearchedEmployees %>件/<%= totalSearchedEmployees %>件中
			<% }else{ %>
				<%= (currentPage - 1) * pageSize + 1 %>～<%= currentPage * pageSize %>件/<%= totalSearchedEmployees %>件中
			<% } %>
		</div>
	</body>

	<script>
	function clearSearchForm() {

	    document
	        .querySelectorAll(
	            'input[type="text"]')
	        .forEach(
	            text => text.value = "");

	    document
	        .querySelectorAll(
	            'input[type="checkbox"]')
	        .forEach(
	            check => check.checked = false);
	}
	
	</script>
	</html>