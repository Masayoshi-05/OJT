<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="definition.path.Paths" %>


	<!DOCTYPE html>
	<html lang="ja">

	<head>
		<meta charset="UTF-8">
		<title>登録完了</title>
		<link rel="stylesheet" href="css/style.css">
	</head>

	<body>
		<div class="message">社員情報の登録が完了しました。</div>

		<form action="<%= request.getContextPath() %><%= Paths.LIST_SERVLET_PATH %>" method="get">
			<button type="submit">一覧表示画面に戻る</button>
		</form>
	</body>

	</html>