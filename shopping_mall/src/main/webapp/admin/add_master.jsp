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
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
</head>
<script src="./js/jquery.js"></script>
<script src="./js/admin.js?v=2"></script>
<body>
    <header class="admin_title_add">
        <p><img src="./img/logo.png" class="logo_sm"> ADMINISTRATOR ADD</p>
    </header>

    <section class="admin_bgcolor_add">
    <form id="frm_join" method="POST" action="./admin_add.do">
        <div class="admin_login_add">
            <ul>
                <li class="font_color1">아이디 및 패스워드 정보</li>
                <li>
                <input type="text" class="add_input1" id="admin_id" name="admin_id" placeholder="생성할 관리자 아이디를 입력하세요">
                <button type="button" id="idck" class="btn_button">중복체크</button>
                </li>
                <li>
                    <input type="password" class="add_input1" id="admin_pass" placeholder="접속할 패스워드를 입력하세요">
                    <input type="password" class="add_input1" id="admin_passck" name="admin_pass" placeholder="동일한 패스워드를 입력하세요">
                </li>
                <li class="font_color1">관리자 기본정보 입력</li>
                <li>
                    <input type="text" class="add_input1" id="admin_name" name="admin_name" placeholder="담당자 이름을 입력하세요">
                </li>
                <li>
                <input type="text" class="add_input1 emails" id="admin_email" name="admin_email" placeholder="담당자 이메일을 입력하세요">
                </li>
                <li class="font_color1">
                <input type="text" class="add_input1 hp1" id="admin_hp1" name="admin_hp" placeholder="HP" value="010" maxlength="3">
                -
                <input type="text" class="add_input1 hp2" id="admin_hp2" name="admin_hp" placeholder="1234" maxlength="4">
                -
                <input type="text" class="add_input1 hp2" id="admin_hp3" name="admin_hp" placeholder="5678" maxlength="4">
                </li>
                <li class="font_color1">관리자 담당부서 및 직책</li>
                <li>
                    <select class="add_select1" id="admin_team" name="admin_team">
                        <option value="">담당자 부서를 선택하세요</option>
                        <option value="임원">임원</option>
                        <option value="전산팀">전산팀</option>
                        <option value="디자인팀">디자인팀</option>
                        <option value="CS팀">CS팀</option>
                        <option value="MD팀">MD팀</option>
                    </select>
                    <select class="add_select1" id="admin_position" name="admin_position">
                        <option value="">담당자 직책을 선택하세요</option>
                        <option value="대표">대표</option>
                        <option value="부장">부장</option>
                        <option value="팀장">팀장</option>
                        <option value="과장">과장</option>
                        <option value="주임">주임</option>
                        <option value="대리">대리</option>
                        <option value="사원">사원</option>
                    </select>
                </li>
                <li class="font_color1">※ 가입완료 후 전산 담당자가 확인 후 로그인 할 수 있습니다.</li>
            </ul>
            <span id="btn" class="admin_addbtn">
                <button type="button" id="add" class="btn_button btncolor1" title="관리자 등록" >관리자 등록</button>
                <button type="button" id="back" class="btn_button btncolor2" title="관리자 취소" >등록 취소</button> 
            </span>
        </div>
        </form>
    </section>
<%@ include file="./footer.jsp" %>
</body>
</html>