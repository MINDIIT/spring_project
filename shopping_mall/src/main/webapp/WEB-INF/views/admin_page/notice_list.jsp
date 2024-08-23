<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>공지사항 리스트 페이지</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=10">
    <link rel="stylesheet" type="text/css" href="./css/main.css?v=10">
    <link rel="stylesheet" type="text/css" href="./css/notice.css?v=10">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
        <script>
        window.addEventListener('pageshow', function(event) {
            if (event.persisted) {
                window.location.reload();
            }
        });
    </script>
</head>
<script src="./js/jquery.js?v=1"></script>
<script src="./js/notice.js?v=<%=today%>"></script>
<body>
<%@ include file="/admin/header.jsp" %>
<main class="maincss">
<section>
    <p>공지사항 관리페이지</p>
    <div class="subpage_view">
    <ul>
        <li><input type="checkbox" id="notice_all_ck"></li>
        <li>NO</li>
        <li>제목</li>
        <li>글쓴이</li>
        <li>날짜</li>
        <li>조회</li>
    </ul>
    <cr:if test="${result_nt!=null}">
    <cr:set var="ino" value="${ctn-startpg_nt}"/>
    <cr:forEach var="data" items="${result_nt}" step="1" varStatus="idx">
    <ol>
        <li><input type="checkbox"name="product_ck" value="${data.nidx}"></li>
        <li>${ino-idx.index}</li>
        <li>
        	<a href="notice_view.do?nidx=${data.nidx}">
        		${data.notice_title}
        	</a>
        </li>
        <li>${data.admin_name}</li>
        <li>${data.notice_update.substring(0, 10)}</li>
        <li>${data.view_count}</li>
    </ol>
    </cr:forEach>
    </cr:if>
    <cr:if test="${empty result_nt}">
    <ol class="none_text">
        <li>등록된 공지 내용이 없습니다.</li>
    </ol>
    </cr:if>
    </div>
    <div class="board_btn">
        <button class="border_del" id="notice_delete_btn">공지삭제</button>
        <button class="border_add" id="notice_write_btn">공지등록</button>
    </div>
    <cr:if test="${result_nt!=null}">
    <div class="border_page">
        <ul class="pageing">
            <li><img src="./ico/double_left.svg"></li>
            <li><img src="./ico/left.svg"></li>
            <cr:set var="pg" value="${ctn/15+(1-((ctn/15)%1))%1}" />
	        <cr:forEach var="no" begin="1" end="${pg}" step="1">
            <li><a href="./notice_list.do?page=${no}">${no}</a></li>
            </cr:forEach>
            <li><img src="./ico/right.svg"></li>
            <li><img src="./ico/double_right.svg"></li>
        </ul>
    </div>
    </cr:if>
</section>
</main>
<%@ include file="/admin/footer.jsp" %>
</body>
</html>