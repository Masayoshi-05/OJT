<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ page import="definition.ErrorKeys" %>
		<%@ page import="definition.EmployeeKeys" %>
			<!DOCTYPE html>
			<html lang="ja">

			<head>
				<meta charset="UTF-8">

				<title>エラー</title>

				<link rel="stylesheet" href="css/style.css">

			</head>

			<body>

				<div class="message">問題が発生しました。</div>

				<div class="singleButtonArea">
					<form action="<%= request.getContextPath() %>/EmployeeRegisterInput" method="post">
						<button type="submit">入力画面に戻る</button>
					</form>
				</div>
			</body>

			</html>