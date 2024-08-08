<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Date today = new Date();
%>    
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 등록 페이지</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=10">
    <link rel="stylesheet" type="text/css" href="./css/main.css?v=10">
    <link rel="stylesheet" type="text/css" href="./css/notice.css?v=10">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
    <script src="https://cdn.ckeditor.com/4.16.2/standard/ckeditor.js"></script>
</head>
<script src="./js/jquery.js?v=1"></script>
<script src="./js/notice.js?v=<%=today%>"></script>
<body>
<%@ include file="/admin/header.jsp" %>
<main class="maincss">
<section>
    <p>공지사항 등록페이지</p>
    <form id="frm_notice" method="post" action="./notice_insertok.do" enctype="multipart/form-data">
    <input type="hidden" name="admin_id" value="<%=admin_id%>">
<div class="write_view">
<ul>
    <li>공지사항 여부</li>
    <li>
        <label class="label_notice"><em class="cbox"><input type="checkbox" id="is_pinned" name="is_pinned"></em> 공지 출력</label> ※ 공지출력을 체크할 시 해당 글 내용은 최상단에 노출 되어 집니다.
    </li>
</ul>
<ul>
    <li>공지사항 제목</li>
    <li>
        <input type="text" class="notice_input1" id="notice_title" name="notice_title" required="required"> ※ 최대 150자까지 입력이 가능
    </li>
</ul>
<ul>
    <li>글쓴이</li>
    <li>
        <input type="text" class="notice_input2" id="admin_name" value="<%=admin_name %>" readonly name="admin_name"> ※ 관리자 이름이 노출 됩니다.       
    </li>
</ul>
<ul>
    <li>첨부파일</li>
    <li>
        <input type="file" id="nfile" name="nfile" multiple="multiple"> ※ 첨부파일 최대 용량은 2MB 입니다.       
    </li>
</ul>
<ul class="ul_height">
    <li>공지내용</li>
    <li>
        <textarea class="notice_input3" placeholder="공지내용을 입력하세요!" id="notice_content" name="notice_content"></textarea>
        <script>
        	CKEDITOR.replace('notice_content');
        </script>
    </li>
</ul>
</div>
<div class="board_btn">
    <button class="border_del">공지목록</button>
    <button class="border_add" id="notice_insert_btn">공지등록</button>
</div>
</form>
</section>
</main>
<%@ include file="/admin/footer.jsp" %>
</body>
</html>