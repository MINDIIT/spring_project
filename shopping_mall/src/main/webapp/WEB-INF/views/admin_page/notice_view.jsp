<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>    
     <%
	Date today = new Date();
%>    
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 내용 확인 페이지</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=10">
    <link rel="stylesheet" type="text/css" href="./css/main.css?v=10">
    <link rel="stylesheet" type="text/css" href="./css/notice.css?v=10">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
</head>
<script src="./js/jquery.js?v=1"></script>
<script src="./js/notice.js?v=<%=today%>"></script>
<body>
<%@ include file="/admin/header.jsp" %>
<main class="maincss">
<section>
    <p>공지사항 확인 페이지</p>
<div class="write_view">
<ul>
    <li>공지사항 제목</li>
    <li>
    	${data[0].notice_title}
    </li>
</ul>
<ul>
    <li>글쓴이</li>
    <li>
     	${data[0].admin_name}
    </li>
</ul>
<ul>
    <li>첨부파일</li>
	<li>
		<a href="#" class="download-link"   data-filepath="${data2[0].file_path}">
		     ${data2[0].file_name}
		</a>       
	</li>
</ul>
<ul class="ul_height">
    <li>공지내용</li>
    <li>
        <div class="notice_input3" style="overflow-y: auto;">
        		<cr:out value="${data[0].notice_content}" escapeXml="false"/>
        </div>
    </li>
</ul>
</div>
<div class="board_btn">
    <button class="border_del" id="notice_list_back_viewpage">공지목록</button>
    <button class="border_add" id="notice_modify_btn" value="${data[0].nidx}">공지수정</button>
    <button class="border_modify" style="margin-left: 8px;" id="notice_delete_viewpage" value="${data[0].nidx}">공지삭제</button>
</div>
</section>
<div id="filePopup" style="display:none; position:fixed; top:10%; left:10%; width:80%; height:80%; background:white; border:1px solid black; z-index:10000; overflow:auto;">
    <div style="text-align:right;">
        <button onclick="closePopup()">Close</button>
    </div>
    <iframe id="fileContent" style="width:100%; height:90%; border:none;"></iframe>
</div>
</main>
<%@ include file="../admin_page/footer.jsp" %>
</body>
</html>