<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ page import="definition.Definition" %>

		<!DOCTYPE html>
		<html lang="ja">

		<head>
			<meta charset="UTF-8">

			<title>確認画面</title>

			<link rel="stylesheet" href="css/style.css">

			<script src="Error.js" defer></script>

		</head>

		<body>

			<div class="message">以下の内容で社員情報を登録いたします。よろしいですか？</div>

			<div class="formRow">
				<label>社員番号</label>
				<div class="displayArea">
					<div class="displayValue">${sessionScope.employee.employeeNum}</div>
					<div class="displayValue"></div>
				</div>
			</div>

			<div class="formRow">
				<label>氏名(漢字)</label>
				<div class="displayArea">
					<div class="displayValue">
						<span>${sessionScope.employee.kanjiLastName}</span>
						<span>${sessionScope.employee.kanjiFirstName}</span>
					</div>
					<div class="displayValue"></div>
				</div>
			</div>

			<div class="formRow">
				<label>氏名(ローマ字)</label>
				<div class="displayArea">
					<div class="displayValue">
						<span>${sessionScope.employee.romanLastName}</span>
						<span>${sessionScope.employee.romanFirstName}</span>
					</div>
					<div class="displayValue"></div>
				</div>
			</div>

			<div class="formRow">
				<label>メールアドレス</label>

				<div class="displayArea">
					<div class="displayValue">${sessionScope.employee.email}</div>
					<div class="displayValue"></div>
				</div>
			</div>

			<div class="formRow">
				<label>生年月日</label>
				<div class="displayArea">
					<div class="displayValue">${sessionScope.employee.birthday}</div>
					<div class="displayValue"></div>
				</div>
			</div>

			<div class="formRow">
				<label>入社年月日</label>
				<div class="displayArea">
					<div class="displayValue">${sessionScope.employee.joinDate}</div>
					<div class="displayValue"></div>
				</div>
			</div>

			<div class="doubleButtonArea">
				<form action="<%= request.getContextPath() %>/EmployeeRegisterInput" method="post">
					<button type="submit">入力画面に戻る</button>
				</form>

				<form id="employeeForm" action="<%= request.getContextPath() %>/EmployeeRegisterComplete" method="post">
					<button type="submit">登録</button>
				</form>
			</div>
		</body>

		</html>