<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	HttpSession hs = request.getSession();
	
	String mid=(String) hs.getAttribute("mid");
	out.print(mid);
	hs.invalidate();//세션 전체 삭제
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>