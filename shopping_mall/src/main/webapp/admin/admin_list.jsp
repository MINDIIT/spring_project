<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<body>
<!-- 헤더 -->
<%@ include file="./header.jsp" %>
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
    <ol class="new_admin_none">
        <li>신규 등록된 관리자가 없습니다.</li>
    </ol>
    <ol class="new_admin_lists2">
        <li>1</li>
        <li>한석봉</li>
        <li>hansbong</li>
        <li>01012345678</li>
        <li>hansbong@hanmail.net</li>
        <li>디자인팀</li>
        <li>주임</li>
        <li>2024-07-29</li>
        <li>
            <input type="button" value="승인" class="new_addbtn1" title="승인">
            <input type="button" value="미승인" class="new_addbtn2" title="미승인">
        </li>
    </ol>
</section>
<section></section>
<section></section>
</main>
<%@ include file="./footer.jsp" %>
</body>
</html>