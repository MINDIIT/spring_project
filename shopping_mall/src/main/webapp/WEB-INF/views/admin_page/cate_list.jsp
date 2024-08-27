<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script src="./js/cate.js?v=1"></script>
<%@ include file="/admin/header.jsp" %>
<body>
<main class="maincss">
    <section>    
<p>카테고리관리 페이지</p>
<form id="frm_search_cate" method="get" action="./cate_list.do">
<div class="subpage_view">
    <span>등록된 카테고리 ${ctn}건</span>
    <span>
        <select class="p_select1" name="search_part_category">
            <option value="1">대메뉴명</option>
            <option value="2">대메뉴코드</option>
        </select>
        <input type="text" class="p_input1" id="search_word_category" name="search_word_category" value="${search_word_category}" placeholder="검색어를 입력해 주세요">
        <input type="submit" value="검색" title="카테고리 검색" id="cate_search_btn" class="p_submit">
    </span>
</div>
</form>
<div class="subpage_view2">
    <ul>
        <li><input type="checkbox" id="category_all_ck"></li>
        <li>분류코드</li>
        <li>대메뉴 코드</li>
        <li>대메뉴명</li>
        <li>소메뉴 코드(사용안함)</li>
        <li>소메뉴명(사용안함)</li>
        <li>사용 유/무</li>
        <li>관리</li>    
    </ul>
    <cr:if test="${result!=null}">
    <cr:forEach var="data" items="${result}">
	    <ul>
	        <li><input type="checkbox" name="category_ck" value="${data.cidx}"></li>
	        <li style="text-align: left; text-indent: 5px;">${data.classification_code}</li>
	        <li>${data.main_menu_code}</li>
	        <li style="text-align: left; text-indent: 5px;">${data.main_menu_name}</li>
	        <li>-</li>
	        <li style="text-align: left; text-indent: 5px;">-</li>
	        <li>${data.menu_usage}</li>
	        <li>[수정]</li>
	    </ul>
    </cr:forEach>
    </cr:if>    
    <cr:if test="${empty result}">
    <ul>
        <li style="width: 100%;">등록된 카테고리가 없습니다.</li>
    </ul>
    </cr:if>
    
</div>
 <cr:if test="${!empty result}">
<div class="subpage_view3">
    <ul class="pageing">
        <li><img src="./ico/double_left.svg"></li>
        <li><img src="./ico/left.svg"></li>
        <cr:set var="pg" value="${ctn/5+(1-((ctn/5)%1))%1}" />
        <cr:forEach var="no" begin="1" end="${pg}" step="1">
        <li><a href="./cate_list.do?page=${no}">${no}</a></li>
        </cr:forEach>
        <li><img src="./ico/right.svg"></li>
        <li><img src="./ico/double_right.svg"></li>
    </ul>
</div>
</cr:if>
<div class="subpage_view4">
    <input type="button" value="카테고리 삭제" title="카테고리 삭제" class="p_button" id="cate_delete_btn">
    <span style="float: right;">
    <input type="button" value="상품 리스트" title="상품 리스트" class="p_button p_button_color1" id="btn_product_list">
    <input type="button" value="카테고리 등록" title="카테고리 등록" class="p_button p_button_color2" id="btn_cate_add">
    </span>
</div>
</section>
</main>
<%@ include file="../admin_page/footer.jsp" %>
</body>
<script>
var ck ="${search_part_category}";
if(ck==""){
	ck="1";
}
frm_search_cate.search_part_category.value=ck;
</script>

</html>