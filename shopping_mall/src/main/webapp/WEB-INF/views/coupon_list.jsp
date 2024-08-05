<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL 페이징 사용법</title>
</head>
<body>
<p>쿠폰 리스트</p>
${stringList[0]}
<table border="1">
	<thead>
		<tr>
			<th>번호</th>
			<th>쿠폰명</th>
			<th>할인율</th>
			<th>사용 유/무</th>
			<th>만료기한</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</tbody>
</table>
</body>
</html>