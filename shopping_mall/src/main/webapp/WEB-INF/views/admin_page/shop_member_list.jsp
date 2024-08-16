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
    <title>쇼핑몰 회원관리</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=1">
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
</head>
<body>
<script src="./js/jquery.js?v=1"></script>
<script src="./js/admin.js?v=<%=today%>"></script>
<%@ include file="/admin/header.jsp" %>
<main class="maincss">
<section>
    <p>회원 리스트</p>
    <ol class="new_admin_title">
        <li>NO</li>
        <li>고객명</li>
        <li>아이디</li>
        <li>전화번호</li>
        <li>이메일</li>
        <li>이메일 수신</li>
        <li>SMS 수신</li>
        <li>가입일자</li>
        <li>상태</li>
        <li>정지여부</li>
    </ol>
    <cr:if test="${result==null}">
    <ol class="new_admin_none">
        <li>가입된 회원이 없습니다.</li>
    </ol>
    </cr:if>
    <cr:if test="${result!=null}">
    <cr:set var="ino" value="${ctn-startpg}"/>
    <cr:forEach var="data" items="${result}" step="1" varStatus="idx">
    <ol class="new_admin_lists">
        <li>${ino-idx.index}</li>
        <li>${data.mname}</li>
        <li>${data.mid}</li>
        <li>${data.mhp}</li>
        <li>${data.memail}</li>
        <li>${data.email_ad}</li>
        <li>${data.sms_ad}</li>
        <li>${data.mjoin.substring(0, 10)}</li>
	        <cr:if test="${data.account_suspended=='Y'}">
	        	<li>휴면</li>
	        </cr:if>
	        <cr:if test="${data.account_suspended=='N'}">
	        	<li>정상</li>
	        </cr:if>
        <li>
        	<cr:if test="${data.account_suspended=='N'}">
            	<input type="button" value="정지" class="new_addbtn1" title="정지" data-midx="${data.midx}">
            </cr:if>
            <cr:if test="${data.account_suspended=='Y'}">
            <input type="button" value="해제" class="new_addbtn2" title="해제" data-midx="${data.midx}">
            </cr:if>
        </li>
    </ol>
    </cr:forEach>
    </cr:if>
</section>
<section style="width: 1100px; height: auto; margin: 0 auto; margin-top: 30px;">
    <p style="font-size: 15px;font-weight: bolder; margin-bottom: 10px;">■ 이용 약관</p>
    <textarea <cr:if test="${empty terms}"> placeholder="이용약관에 대한 내용을 입력하세요"</cr:if> <cr:if test="${!empty terms}">value="${terms}"</cr:if> style="width: 100%; height: 100px; resize: none;" id="temsTextArea"></textarea>
    <input type="button" value="이용약관 수정" title="이용약관 수정" class="btn_button" style="position: relative; left: 100%; margin-left: -100px;" id="btnUpdateTerms">
</section>
<section style="width: 1100px; height: auto; margin: 0 auto; margin-top: 30px;">
    <p style="font-size: 15px;font-weight: bolder; margin-bottom: 10px;">■ 개인정보 수집 및 이용</p>
    <textarea placeholder="개인정보 수집 및 이용" style="width: 100%; height: 100px; resize: none;" id="privacypolicyTextArea"></textarea>
    <input type="button" value="개인정보 약관 수정" title="개인정보 약관 수정" class="btn_button" style="position: relative; left: 100%; margin-left: -100px;" id="btnUpdatePrivacyPolicy">
</section>
</main>
<%@ include file="/admin/footer.jsp" %>
</body>
</html>