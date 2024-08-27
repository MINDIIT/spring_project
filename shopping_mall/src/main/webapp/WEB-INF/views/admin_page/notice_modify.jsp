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
    <title>공지사항 수정 페이지</title>
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
    <p>공지사항 수정페이지</p>
    <form id="frm_notice_modify" method="post" action="./notice_modifyok.do" enctype="multipart/form-data">
    <input type="hidden" name="admin_id" value="<%=admin_id%>">
    <input type="hidden" name="nidx" value="${data[0].nidx}">
    <input type="hidden" name="is_pinned" id="is_pinned_hidden" value="N">
    <!-- 삭제할 목록 저장 -->
    <input type="hidden" name="filesToDelete" id="filesToDelete">
<div class="write_view">
<ul>
    <li>공지사항 여부</li>
    <li>
        <label class="label_notice">
        <em class="cbox">
        <input type="checkbox" id="is_pinned_md" name="is_pinned"
        	<cr:if test="${data[0].is_pinned == 'Y'}">checked="checked"</cr:if>> 공지 출력</em></label> ※ 공지출력을 체크할 시 해당 글 내용은 최상단에 노출 되어 집니다.
    </li>
</ul>
<ul>
    <li>공지사항 제목</li>
    <li>
        <input type="text" class="notice_input1" value="${data[0].notice_title}" id="notice_title_md" name="notice_title" required="required"> ※ 최대 150자까지 입력이 가능
    </li>
</ul>
<ul>
    <li>글쓴이</li>
    <li>
        <input type="text" class="notice_input2" id="admin_name_md" value="<%=admin_name %>" readonly name="admin_name"> ※ 관리자 이름이 노출 됩니다.       
    </li>
</ul>
<cr:if test="${!empty data2[0].file_name}">
<ul>
    <li>첨부파일</li>
    <li>
    	<!-- 기존 첨부파일 목록을 출력 -->
    	<ul id="existingfiles">
    		<cr:forEach var="file" items="${data2}">
    			<li>
    			${file.file_name}
    			<button type="button" class="remove-file" data-filepath="${file.file_path}">삭제</button>
    			</li>
    		</cr:forEach>
    	</ul>
    </li>
</ul>
</cr:if>
<ul>    
    <li>첨부파일 추가</li>
    <li>
    	<input type="file" id="nfile_md" name="nfile" multiple="multiple"> ※ 첨부파일 최대 용량은 2MB 입니다. 
    </li>
</ul>
<ul class="ul_height">
    <li>공지내용</li>
    <li>
        <textarea class="notice_input3" placeholder="공지내용을 입력하세요!" id="notice_content_md" name="notice_content">${data[0].notice_content}</textarea>
        <script>
        	CKEDITOR.replace('notice_content');
        </script>
    </li>
</ul>
</div>
<div class="board_btn">
    <button class="border_del" id="notice_listpage_from_modifypage" value="${data[0].nidx}">수정취소</button>
    <button class="border_add" id="notice_modifyok_btn">공지수정</button>
</div>
</form>
</section>
</main>
<%@ include file="../admin_page/footer.jsp" %>
</body>
</html>