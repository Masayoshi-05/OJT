<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="definition.key.EmployeeKeys" %>
<%@ page import="definition.path.Paths" %>
<%@ page import="java.util.List" %>
<%@ page import="data.employee.Employee" %>
<%@ page import="data.employee.IsCheckedEmployee" %>

<% List<Employee> employees = (List<Employee>)request.getAttribute(EmployeeKeys.EMPLOYEES); %>
<% IsCheckedEmployee isCheckedEmployee = (IsCheckedEmployee)request.getAttribute(EmployeeKeys.IS_CHECKED_EMPLOYEE);
	if(isCheckedEmployee == null){
		isCheckedEmployee = new IsCheckedEmployee();
	}
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
			<form id="employeeForm" action="<%= request.getContextPath() %><%= Paths.REGISTER_INPUT_SERVLET_PATH %>"
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
							<input type="checkbox" name="<%= EmployeeKeys.IS_CHECKED_EMPLOYEE_NUM %>"
							<%= isCheckedEmployee.isCheckedEmployeeNum() ? "checked" : "" %>>
							社員番号
						</td>
						<td>
							<input type="text" name="<%= EmployeeKeys.EMPLOYEE_NUM %>" placeholder="Search"
							value="${conditionEmployee.employeeNum}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.IS_CHECKED_KANJI_NAME %>" 
							<%= isCheckedEmployee.isCheckedKanjiName() ? "checked" : "" %>>
							氏名(漢字)
						</td>

						<td>
							<input type="text" name="<%= EmployeeKeys.KANJI_NAME %>" placeholder="Search"
							value="${conditionEmployee.kanjiName}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.IS_CHECKED_ROMAN_NAME %>" 
							<%= isCheckedEmployee.isCheckedRomanName() ? "checked" : "" %>>
							氏名(ローマ字)
						</td>

						<td>
							<input type="text" name="<%= EmployeeKeys.ROMAN_NAME %>" placeholder="Search"
							value="${conditionEmployee.romanName}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.IS_CHECKED_EMAIL %>" 
							<%= isCheckedEmployee.isCheckedEmail() ? "checked" : "" %>>
							メールアドレス
						</td>
						<td>
							<input type="text" name="<%= EmployeeKeys.EMAIL %>" placeholder="Search"
							value="${conditionEmployee.email}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.IS_CHECKED_BIRTHDAY %>" 
							<%= isCheckedEmployee.isCheckedBirthday() ? "checked" : "" %>>
							生年月日
						</td>

						<td>
							<input type="text" name="<%= EmployeeKeys.BIRTHDAY %>" placeholder="Search"
							value="${conditionEmployee.birthday}">
						</td>
					</tr>

					<tr>
						<td>
							<input type="checkbox" name="<%= EmployeeKeys.IS_CHECKED_JOIN_DATE %>" 
							<%= isCheckedEmployee.isCheckedJoinDate() ? "checked" : "" %>>
							入社年月日
						</td>

						<td>
							<input type="text" name="<%= EmployeeKeys.JOIN_DATE %>" placeholder="Search"
							value="${conditionEmployee.joinDate}">
						</td>
					</tr>
				</table>

				<div class="searchButtonArea">
					<input type="button" value="検索条件をクリア" onclick="clearSearchForm()">
					<input type="submit" value="検索">
				</div>
			</form>
		</details>
		
		<div class="pageInfo" id="pageInfoTop">
		</div>
		
		<div class="pagingButtons" id="pagingButtonsTop">
		</div>
		
		<table class="employeeTable" border="1">
			
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
				<tr class="employeeRow" display="none">
					<td>
						<%= employee.getEmployeeNum() %>
					</td>
					<td>
						<%= employee.getKanjiLastName() %> <%= employee.getKanjiFirstName() %>
					</td>
					<td>
						<%= employee.getRomanLastName() %> <%= employee.getRomanFirstName() %>
					</td>
					<td>
						<%= employee.getEmail() == null || employee.getEmail().isEmpty() ? "未登録" : employee.getEmail() %>
					</td>
					<td>
						<%= employee.getBirthday() == null || employee.getBirthday().isEmpty() ? "未登録" : employee.getBirthday() %>
					</td>
					<td>
						<%= employee.getJoinDate() == null || employee.getJoinDate().isEmpty() ? "未登録" : employee.getJoinDate() %>
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

		<div class="pagingButtons" id="pagingButtonsBottom">
		</div>
		
		<div class="pageInfo" id="pageInfoBottom">
		</div>
	</body>

	<script>
	/**
	 * 検索フォーム内のテキストボックス、チェックボックスへの入力をクリアする
	 */
	function clearSearchForm() {
	    document.querySelectorAll('input[type="text"]').forEach(textBox => textBox.value = "");
	    document.querySelectorAll('input[type="checkbox"]').forEach(checkBox => checkBox.checked = false);
	}
	
	const pageSize = 10;
	const rows = document.querySelectorAll(".employeeRow");
	const totalPages = Math.ceil(rows.length / pageSize);
	let currentPage = 1;
	
	/**
	 * 社員情報一覧表、ページ情報、ページ番号切り替えボタンを表示する
	 */
	function showPage() {
		const start = (currentPage - 1) * pageSize;
		const end = Math.min(currentPage * pageSize - 1,rows.length - 1);
				
		rows.forEach(row => row.style.display = "none");
		for (let i = start; i <= end; i++) {
			rows[i].style.display = "";
		}
		showPageInfo(start,end);
		showPagingButtons();
	}

	/**
	 * 現在表示されている表の社員情報が検索結果の何件目～何件目であるかの表示を更新する
	 * 例: 1～10件表示/30件中
	 */
	function showPageInfo(start,end) {
		if(rows.length === 0){
			const noContents = "0件/0件中"
		    document.getElementById("pageInfoTop").textContent = noContents;
		    document.getElementById("pageInfoBottom").textContent = noContents;
		    return;
		}
		const pageInfo = (start + 1) + "～" + (end + 1) + "件/" + rows.length + "件中";
		document.getElementById("pageInfoTop").textContent = pageInfo;
		document.getElementById("pageInfoBottom").textContent = pageInfo;
	}

	/**
	 * 表のページを切り替えるボタンの表示
	 */
	function showPagingButtons() {
		const pagingButtonsTop = document.getElementById("pagingButtonsTop");
		const pagingButtonsBottom = document.getElementById("pagingButtonsBottom");
		pagingButtonsTop.innerHTML = "";
		pagingButtonsBottom.innerHTML = "";

		if(rows.length === 0){
			pagingButtonsTop.innerHTML = `<strong>1</strong>`;
			pagingButtonsBottom.innerHTML = `<strong>1</strong>`;
		    return;
		}
		
		const pageReturn = document.createElement("span");
		pageReturn.className = "pageJump";
		if (currentPage > 1) {
			pageReturn.innerHTML += `<button type="button" onclick="currentPage = 1; showPage();">
					&lt;&lt;
				</button>`;

			pageReturn.innerHTML +=
				`<button type="button" onclick="currentPage--; showPage();">
					&lt;
				</button>`;
		}else{
			pageReturn.innerHTML += `<span class="pagePlaceholder"></span>`
			pageReturn.innerHTML += `<span class="pagePlaceholder"></span>`
		}

		const pageNums = document.createElement("span");
		pageNums.className = "pageNums";
		let startPage;
		let endPage;

		/** 
		 * ページ番号の表示制御
		 * 現在いるページの前後2ページを含めて5件表示する
		 * 表示位置が先頭・末尾の付近の場合、現在のページを含めて前後5件表示する
		 * 
		 * 例：10ページの検索結果が存在する
		 * 2ページ目を選択⇒１，[２]，３，４，５
		 * 9ページ目を選択⇒６，７，８，[９]，１０
		 */
		if(totalPages <= 5){
			startPage = 1;
			endPage = totalPages;
		}else if(currentPage <= 3){
			startPage = 1;
			endPage = 5;
		}else if(currentPage >= totalPages - 2){
			startPage = totalPages - 4;
			endPage = totalPages;
		}else{
			startPage = currentPage - 2;
			endPage = currentPage + 2;
		}
		
		if (startPage > 1) {
			pageNums.innerHTML += "<span>...</span>";
		}
		for (
			let i = startPage;
			i <= endPage;
			i++
		) {
			if (i === currentPage) {
				pageNums.innerHTML += `<strong>` + i + `</strong>`;
			} else {
				pageNums.innerHTML +=
					'<button type="button" onclick="currentPage =' + i + '; showPage();">'
					 + i + 
					'</button>';
			}
		}
		
		if (endPage < totalPages) {
			pageNums.innerHTML += "<span>...</span>";
		}

		const pageSkip = document.createElement("span");
		pageSkip.className = "pageJump";

		if (currentPage < totalPages
		) {
			pageSkip.innerHTML +=
				`<button type="button" onclick="currentPage++; showPage();">
					&gt;
				</button>`;

			pageSkip.innerHTML +=
				`<button type="button" onclick="currentPage = ` + totalPages + `; showPage();">
					&gt;&gt;
				</button>`;
		}else{
			pageSkip.innerHTML += `<span class="pagePlaceholder"></span>`
			pageSkip.innerHTML += `<span class="pagePlaceholder"></span>`
		}

		const pagingHtml =
			pageReturn.outerHTML
			+ pageNums.outerHTML
			+ pageSkip.outerHTML;
			
		pagingButtonsTop.innerHTML = pagingHtml;
		pagingButtonsBottom.innerHTML = pagingHtml;
	}

	showPage();
	</script>
	</html>