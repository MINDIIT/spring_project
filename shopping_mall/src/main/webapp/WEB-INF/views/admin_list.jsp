<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 등록 페이지</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=1">
    <link rel="stylesheet" type="text/css" href="./css/main.css?v=1">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
</head>
<script src="./js/jquery.js?v=1"></script>
<script src="./js/admin.js?v=1"></script>
<body>
<!-- 헤더 -->
<%@include file="/admin/header.jsp" %>
<main class="maincss">
<section>
    <p>신규등록 관리자</p>
    <ol class="new_admin_title2">
        <li>NO</li>
        <li>관리자명</li>
        <li>아이디</li>
        <li>전화번호</li>
        <li>이메일</li>
        <li>담당부서</li>
        <li>담당직책</li>
        <li>가입일자</li>
        <li>승인여부</li>
    </ol>
    <cr:if test="${result==null}">
    <ol class="new_admin_none">
        <li>신규 등록된 관리자가 없습니다.</li>
    </ol>
    </cr:if>
    <cr:if test="${result!=null}">
    <cr:set var="ino" value="${ctn-1}"/>
    <cr:forEach var="data" items="${result}" step="1" varStatus="idx">
    <cr:if test="${data.admin_name!='최고관리자'}">
    <ol class="new_admin_lists2">
        <li>${ino-idx.index}</li>
        <li>${data.admin_name}</li>
        <li>${data.admin_id}</li>
        <li>${data.admin_hp}</li>
        <li>${data.admin_email}</li>
        <li>${data.admin_team}</li>
        <li>${data.admin_position}</li>
        <li>${data.admin_join}</li>
        <li>
        <cr:if test="${data.admin_confirm=='Y'}">
            <input type="button" onclick="agree(${data.aidx})" value="승인" class="new_addbtn1" title="승인" >
        </cr:if>
        <cr:if test="${data.admin_confirm=='N'}">
            <input type="button" onclick="disagree(${data.aidx})" value="미승인" class="new_addbtn2" title="미승인">
        </cr:if>
        </li>
    </ol>
    </cr:if>
    </cr:forEach>
    </cr:if>
</section>
<section></section>
<section></section>
</main>
<%@ include file="/admin/footer.jsp" %>
</body>
</html>