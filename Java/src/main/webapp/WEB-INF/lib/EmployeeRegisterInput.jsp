<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ page import="definition.ErrorKeys" %>
	<%@ page import="definition.EmployeeKeys" %>
		<!DOCTYPE html>
		<html lang="ja">

		<head>
			<meta charset="UTF-8">

			<title>入力画面</title>

			<link rel="stylesheet" href="css/style.css">

			<script src="EmployeeValidator.js" defer></script>

		</head>

		<body>
			<div class="pageHeader">
				<h2>社員情報を入力</h2>
				<a href="<%= request.getContextPath() %>/EmployeeList">
					<一覧表示画面に戻る </a>
			</div>

			<form id="employeeForm" action="<%= request.getContextPath() %>/EmployeeRegisterConfirm" method="post">

				<div class="formRow">
					<label for="employeeNum">社員番号*</label>

					<div class="inputArea">
						<input type="text" id="employeeNum" name="<%= EmployeeKeys.EMPLOYEE_NUM %>"
							value="${sessionScope.employee.employeeNum}" maxlength="4" placeholder="(例)1234">
						<div class="errorMessage" id="employeeNumError">
							<%= request.getAttribute(ErrorKeys.EMPLOYEE_NUM_ERROR) %>
						</div>
					</div>
				</div>

				<div class="formRow">
					<label>姓・名(漢字)*</label>

					<div class="inputArea">
						<div class="nameInputs">
							<input type="text" id="kanjiLastName" name="<%= EmployeeKeys.KANJI_LAST_NAME %>"
								value="${sessionScope.employee.kanjiLastName}" placeholder="(例)山田" maxlength="30">

							<input type="text" id="kanjiFirstName" name="<%= EmployeeKeys.KANJI_FIRST_NAME %>"
								value="${sessionScope.employee.kanjiFirstName}" maxlength="30" placeholder="(例)太郎">
						</div>
						<div class="errorMessage" id="kanjiNameError">
							<%= request.getAttribute(ErrorKeys.KANJI_NAME_ERROR) %>
						</div>
					</div>
				</div>

				<div class="formRow">
					<label>姓・名(ローマ字)*</label>

					<div class="inputArea">
						<div class="nameInputs">
							<input type="text" id="romanLastName" name="<%= EmployeeKeys.ROMAN_LAST_NAME %>"
								value="${sessionScope.employee.romanLastName}" placeholder="(例)yamada" maxlength="30">

							<input type="text" id="romanFirstName" name="<%= EmployeeKeys.ROMAN_FIRST_NAME %>"
								value="${sessionScope.employee.romanFirstName}" placeholder="(例)taro" maxlength="30">
						</div>
						<div class="errorMessage" id="romanNameError">
							<%= request.getAttribute(ErrorKeys.ROMAN_NAME_ERROR) %>
						</div>
					</div>
				</div>

				<div class="formRow">
					<label for="email">メールアドレス</label>

					<div class="inputArea">
						<input type="text" id="email" name="<%= EmployeeKeys.EMAIL %>" value="${sessionScope.employee.email}"
							maxlength="100" placeholder="(例)yamada.taro@example.com">
						<div class="errorMessage" id="emailError">
							<%= request.getAttribute(ErrorKeys.EMAIL_ERROR) %>
						</div>
					</div>
				</div>

				<div class="formRow">
					<label for="birthday">生年月日</label>

					<div class="inputArea">
						<input type="date" id="birthday" name="<%= EmployeeKeys.BIRTHDAY %>"
							value="${sessionScope.employee.birthday}">
						<div class="errorMessage" id="birthdayError">
							<%= request.getAttribute(ErrorKeys.BIRTHDAY_ERROR) %>
						</div>
					</div>
				</div>

				<div class="formRow">
					<label for="joinDate">入社年月日</label>

					<div class="inputArea">
						<input type="date" id="joinDate" name="<%= EmployeeKeys.JOIN_DATE %>"
							value="${sessionScope.employee.joinDate}">
						<div class="errorMessage" id="joinDateError">
							<%= request.getAttribute(ErrorKeys.JOIN_DATE_ERROR) %>
						</div>
					</div>
				</div>

				<div class="requiredText">
					*は入力必須項目
				</div>
				<div class="singleButtonArea">
					<button type="submit">確認</button>
				</div>
			</form>
			<script>

				const form =
					document.getElementById("employeeForm");

				form.addEventListener("submit", function (event) {
					let hasError = false;

					const employeeNum = document.getElementById("employeeNum").value;
					const employeeNumError = validateEmployeeNum(employeeNum);
					document.getElementById("employeeNumError").textContent; = employeeNumError;
					if (employeeNumError !== "") {
						hasError = true;
					}

					const kanjiLastName = document.getElementById("kanjiLastName").value;
					const kanjiFirstName = document.getElementById("kanjiFirstName").value;
					const kanjiNameError = validateKanjiName(kanjiLastName, kanjiFirstName);
					document.getElementById("kanjiNameError").textContent = kanjiNameError;
					if (kanjiNameError !== "") {
						hasError = true;
					}

					const romanLastName = document.getElementById("romanLastName").value;
					const romanFirstName = document.getElementById("romanFirstName").value;
					const romanNameError = validateRomanName(romanLastName, romanFirstName);
					document.getElementById("romanNameError").textContent
						= romanNameError;
					if (romanNameError !== "") {
						hasError = true;
					}

					const email = document.getElementById("email").value;
					const emailError = validateEmail(email);
					document.getElementById("emailError").textContent = emailError;
					if (emailError !== "") {
						hasError = true;
					}

					const birthday = document.getElementById("birthday").value;
					const birthdayError = validateDate(birthday);
					document.getElementById("birthdayError").textContent = birthdayError;
					if (birthdayError !== "") {
						hasError = true;
					}

					const joinDate = document.getElementById("joinDate").value;
					const joinDateError = validateDate(joinDate);
					document.getElementById("joinDateError").textContent = joinDateError;
					if (joinDateError !== "") {
						hasError = true;
					}

					if (hasError) {
						event.preventDefault();
					}
				});

			</script>
		</body>

		</html>