<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ page import="definition.key.ErrorKeys" %>
		<%@ page import="definition.key.EmployeeKeys" %>
		<%@ page import="definition.paths.Paths" %>
		
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
					<form action="<%= request.getContextPath() %><%= Paths.LIST_SERVLET_PATH %>" method="get">
						<button type="submit">一覧表示画面に戻る</button>
					</form>
				</div>
			</body>

			</html>