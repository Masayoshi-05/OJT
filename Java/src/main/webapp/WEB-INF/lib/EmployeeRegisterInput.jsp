<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="definition.Definition" %>

	<!DOCTYPE html>
	<html lang="ja">

	<head>
		<meta charset="UTF-8">

		<title>入力画面</title>

		<link rel="stylesheet" href="css/style.css">
		
		<script src="Error.js" defer></script>
	</head>

	<body>
		<div class="pageHeader">
			<h2>社員情報を入力</h2>
			<a href="<%= request.getContextPath() %>/EmployeeList">
				<一覧表示画面に戻る </a>
		</div>

		<form 
		id="employeeForm"
		action="<%= request.getContextPath() %>/EmployeeRegisterConfirm" method="post">
			<div class="formRow">
				<label for="employeeNum">社員番号*</label>

				<div class="inputArea">
					<input type="text" id="employeeNum" name="<%= Definition.EMPLOYEE_NUM %>" value="${sessionScope.employee.employeeNum}" maxlength="4"
						placeholder="(例)1234">
					<div class="errorMessage" id="employeeNumError">${employeeNumError}</div>
				</div>
			</div>

			<div class="formRow">
				<label>姓・名(漢字)*</label>

				<div class="inputArea">
					<div class="nameInputs">
						<input type="text" id="kanjiLastName" name="<%= Definition.KANJI_LAST_NAME %>" value="${sessionScope.employee.kanjiLastName}"
							placeholder="(例)山田" maxlength="30">

						<input type="text" id="kanjiFirstName" name="<%= Definition.KANJI_FIRST_NAME %>" value="${sessionScope.employee.kanjiFirstName}"
							maxlength="30" placeholder="(例)太郎">
					</div>
					<div class="errorMessage" id="kanjiNameError">${kanjiNameError}</div>
				</div>
			</div>

			<div class="formRow">
				<label>姓・名(ローマ字)*</label>

				<div class="inputArea">
					<div class="nameInputs">
						<input type="text" id="romanLastName" name="<%= Definition.ROMAN_LAST_NAME %>" value="${sessionScope.employee.romanLastName}"
							placeholder="(例)yamada" maxlength="30">

						<input type="text" id="romanFirstName" name="<%= Definition.ROMAN_FIRST_NAME %>" value="${sessionScope.employee.romanFirstName}"
							placeholder="(例)taro" maxlength="30">
					</div>
					<div class="errorMessage" id="romanNameError">${romanNameError}</div>
				</div>
			</div>

			<div class="formRow">
				<label for="email">メールアドレス</label>

				<div class="inputArea">
					<input type="text" id="email" name="<%= Definition.EMAIL %>" value="${sessionScope.employee.email}" maxlength="100"
						placeholder="(例)yamada.taro@example.com">
					<div class="errorMessage" id="emailError">${emailError}</div>
				</div>
			</div>

			<div class="formRow">
				<label for="birthday">生年月日</label>

				<div class="inputArea">
					<input type="date" id="birthday" name="<%= Definition.BIRTHDAY %>" value="${sessionScope.employee.birthday}">
					<div class="errorMessage" id="birthdayError">${birthdayError}</div>
				</div>
			</div>

			<div class="formRow">
				<label for="joinDate">入社年月日</label>

				<div class="inputArea">
					<input type="date" id="joinDate" name="<%= Definition.JOIN_DATE %>" value="${sessionScope.employee.joinDate}">
					<div class="errorMessage" id="joinDateError">${joinDateError}</div>
				</div>
			</div>

			<div class="requiredText">
				*は入力必須項目
			</div>

			<div class="singleButtonArea">
				<button type="submit">確認</button>
			</div>
		</form>

	</body>

	</html>