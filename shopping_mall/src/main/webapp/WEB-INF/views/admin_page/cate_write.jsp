<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품등록 페이지</title>
    <link rel="stylesheet" type="text/css" href="./css/basic.css">
    <link rel="stylesheet" type="text/css" href="./css/login.css?v=1">
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" type="text/css" href="./css/category.css?v=6">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
</head>
<script src="./js/jquery.js?v=1"></script>
<script src="./js/cate.js?v=2"></script>
<%@ include file="/admin/header.jsp" %>
<body>
<main class="maincss">
<section>
<form id="frm_cate_add" method="post" action="./cateaddok.do">
<input type="hidden" value="<%=admin_id%>" name="admin_id">
    <p>카테고리 등록 페이지</p>
    <div class="cate_insert">
        <ul>
            <li>분류코드</li>
            <li><input type="text" class="cate_input1" maxlength="3" id="classification_code" name="classification_code"></li>
            <li>※ 분류코드는 대메뉴 코드와 소메뉴 코드를 합쳐서 자동 입력 됩니다.</li>
        </ul>
        <ul>
            <li>대메뉴 코드</li>
            <li>
                <input type="text" class="cate_input2" list="lg_menu" id="main_menu_code" name="main_menu_code">
                <datalist id="lg_menu">
                </datalist>
            </li>
            <li>※ 대메뉴에 사용할 코드 번호를 입력하세요 최소 2자 이상의 숫자로 입력하셔야 합니다.</li>
        </ul>
        <ul>
            <li>대메뉴명</li>
            <li><input type="text" class="cate_input3" id="main_menu_name" name="main_menu_name"> <label><!--<input type="checkbox" style="margin-left:10px; margin-right: 5px;">대메뉴만 생성</label>--></li>
            <li>※ 소메뉴만 등록시 대메뉴 코드와 대메뉴명은 무조건 입력 되어야 합니다.</li>
        </ul>
        <ul>
            <li>소메뉴 코드(사용안함)</li>
            <li>

            </li>
            <li>※ 소메뉴에 사용할 코드 번호를 입력하세요 최소 2자 이상의 숫자로 입력하셔야 합니다.</li>
        </ul>
        <ul>
            <li>소메뉴명(사용안함)</li>
            <li></li>
            <li>※ 대메뉴만 등록시 소메뉴 코드 및 소메뉴명은 입력하지 않으셔도 됩니다.</li>
        </ul>
        <ul>
            <li>사용 유/무</li>
            <li>
                <label class="rmargin"><input type="radio" name="menu_usage" value="Y" checked>사용함 </label>
                <label class="rmargin"><input type="radio" name="menu_usage" value="N">사용안함</label>
            </li>
            <li>※ 카테고리 사용안함으로 설정시 쇼핑몰에 해당 메뉴는 생성 되지 않습니다.</li>
        </ul>
    </div>
    <div class="subpage_view4" style="text-align:center;">
        <input type="button" value="카테고리 리스트" title="카테고리 리스트" class="p_button p_button_color1" style="margin-right: 5px;" id="btn_cate_list">
        <input type="button" value="카테고리 생성" title="카테고리 생성" class="p_button p_button_color2" id="insert_cate">
        </span>
    </div>
    </form>
</section>
</main>
<%@ include file="../admin_page/footer.jsp" %>
</body>
</html>