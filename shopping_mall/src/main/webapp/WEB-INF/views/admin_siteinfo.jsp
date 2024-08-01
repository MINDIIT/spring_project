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
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" type="text/css" href="./css/subpage.css?v=5">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
</head>
<body>
<%@include file="/admin/header.jsp" %>
<script src="./js/jquery.js"></script>
<script src="./js/admin.js"></script>
<main class="maincss">
<section>
<form id="frm_settings">
    <p>홈페이지 가입환경 설정</p>
<div class="subpage_view">
<ul class="info_form">
    <li>홈페이지 제목</li>
    <li>
        <input type="text" id="website_title" name="website_title" value="" class="in_form1"> 
    </li>
</ul>    
<ul class="info_form">
    <li>관리자 메일 주소</li>
    <li>
        <input type="text" id="administrator_email_address" name="administrator_email_address" class="in_form2"> ※ 관리자가 보내고 받는 용도로 사용하는 메일 주소를 입력합니다.(회원가입,인증메일,회원메일발송 등에서 사용)
    </li>
</ul> 
<ul class="info_form">
    <li>포인트 사용 유/무</li>
    <li class="checkcss">
        <em><label><input type="radio" class="ckclass" name="point_use" value="Y">포인트 사용</label></em> 
        <em><label><input type="radio" class="ckclass" name="point_use" value="N" checked>포인트 미사용</label></em>
    </li>
</ul>
<ul class="info_form2" style="border-bottom:1px solid rgb(81, 61, 61);">
    <li>회원가입시 적립금</li>
    <li>
        <input type="text" class="in_form3" id="registration_bonus_points" name="registration_bonus_points" maxlength="5"> 점
    </li>
    <li>회원가입시 권한레벨</li>
    <li>
        <input type="text" class="in_form3" id="permission_level" name="permission_level"maxlength="1"> 레벨
    </li>
</ul> 
</div>
<p>홈페이지 기본환경 설정</p>
<div class="subpage_view">
<ul class="info_form2">
    <li>회사명</li>
    <li>
        <input type="text" class="in_form0" id="company_name" name="company_name"> 
    </li>
    <li>사업자등록번호</li>
    <li>
        <input type="text" class="in_form0" id="business_registration_number" name="business_registration_number" maxlength="10"> 
    </li>
</ul> 
<ul class="info_form2">
    <li>대표자명</li>
    <li>
        <input type="text" class="in_form0" id="ceo_name" name="ceo_name"> 
    </li>
    <li>대표전화번호</li>
    <li>
        <input type="text" class="in_form0" id="company_phone_number" name="company_phone_number" maxlength="11"> 
    </li>
</ul>
<ul class="info_form2">
    <li>통신판매업 신고번호</li>
    <li>
        <input type="text" class="in_form0" id="online_business_registration_number" name="online_business_registration_number" maxlength="12"> 
    </li>
    <li>부가통신 사업자번호</li>
    <li>
        <input type="text" class="in_form0" id="value_added_service_provider_number" name="value_added_service_provider_number" maxlength="12"> 
    </li>
</ul>
<ul class="info_form2">
    <li>사업장 우편번호</li>
    <li>
        <input type="text" class="in_form0" id="business_location_postal_code" name="business_location_postal_code" maxlength="5"> 
    </li>
    <li>사업장 주소</li>
    <li>
        <input type="text" class="in_form2" id="business_location_address" name="business_location_address"> 
    </li>
</ul>
<ul class="info_form2" style="border-bottom:1px solid rgb(81, 61, 61);">
    <li>정보관리책임자명</li>
    <li>
        <input type="text" class="in_form0" id="data_protection_officer_name" name="data_protection_officer_name"> 
    </li>
    <li>정보책임자 E-mail</li>
    <li>
        <input type="text" class="in_form1" id="data_protection_officer_email" name="data_protection_officer_email"> 
    </li>
</ul>
</div>
<p>결제 기본환경 설정</p>
<div class="subpage_view">  
<ul class="info_form2">
    <li>무통장 은행</li>
    <li>
        <input type="text" class="in_form0" id="bank_transfer_bank_name" name="bank_transfer_bank_name"> 
    </li>
    <li>은행계좌번호</li>
    <li>
        <input type="text" class="in_form1" id="bank_transfer_account_number" name="bank_transfer_account_number"> 
    </li>
</ul>
<ul class="info_form">
    <li>신용카드 결제 사용</li>
    <li class="checkcss">
        <em><label><input type="radio" class="ckclass" name="credit_card_payment" value="Y"> 사용</label></em> 
        <em><label><input type="radio" class="ckclass" name="credit_card_payment" value="N" checked> 미사용</label></em> ※ 해당 PG사가 있을 경우 사용으로 변경합니다.
    </li>
</ul>
<ul class="info_form">
    <li>휴대폰 결제 사용</li>
    <li class="checkcss">
        <em><label><input type="radio" class="ckclass" name="mobile_payment" value="Y"> 사용</label></em> 
        <em><label><input type="radio" class="ckclass" name="mobile_payment" value="N" checked> 미사용</label></em> ※ 주문시 휴대폰 결제를 가능하게 할 것인지를 설정합니다.
    </li>
</ul>
<ul class="info_form">
    <li>도서상품권 결제사용</li>
    <li class="checkcss">
        <em><label><input type="radio" class="ckclass" name="book_voucher_payment" value="Y"> 사용</label></em> 
        <em><label><input type="radio" class="ckclass" name="book_voucher_payment" value="N" checked> 미사용</label></em> ※ 도서상품권 결제만 적용이 되며, 그 외에 상품권은 결제 되지 않습니다.
    </li>
</ul>
<ul class="info_form2">
    <li>결제 최소 포인트</li>
    <li>
        <input type="text" class="in_form0" id="min_payment_point" name="min_payment_point" maxlength="5" value="1000"> 점
    </li>
    <li>결제 최대 포인트</li>
    <li>
        <input type="text" class="in_form0" id="max_payment_point" name="max_payment_point" maxlength="5"> 점
    </li>
</ul>
<ul class="info_form">
    <li>현금 영수증 발급사용</li>
    <li class="checkcss" >
        <em><label><input type="radio" class="ckclass" name="cash_receipt_usage" value="Y"> 사용</label></em> 
        <em><label><input type="radio" class="ckclass" name="cash_receipt_usage" value="N" checked> 미사용</label></em> ※ 현금영수증 관련 사항은 PG사 가입이 되었을 경우 사용가능 합니다.
    </li>
</ul>
<ul class="info_form2">
    <li>배송업체명</li>
    <li>
        <input type="text" class="in_form0" id="delivery_company_name" name="delivery_company_name"> 
    </li>
    <li>배송비 가격</li>
    <li>
        <input type="text" class="in_form0" id="delivery_fee" name="delivery_fee" maxlength="7"> 원
    </li>
</ul>
<ul class="info_form" style="border-bottom:1px solid rgb(81, 61, 61);">
    <li>희망배송일</li>
    <li class="checkcss" >
        <em><label><input type="radio" class="ckclass" name="preferred_delivery_date_usage" value="Y"> 사용</label></em> 
        <em><label><input type="radio" class="ckclass" name="preferred_delivery_date_usage" value="N" checked> 미사용</label></em> ※ 희망배송일 사용시 사용자가 직접 설정 할 수 있습니다.
    </li>
</ul>
</div>
<div class="btn_div">
    <button type="button" class="sub_btn1" title="설정 저장" id="btn_save">설정 저장</button>
    <button type="button" class="sub_btn2" title="저장 취소" id="btn_cancel">저장 취소</button>
</div>
</form>
</section>
<section></section>
<section></section>
</main>
<%@ include file="/admin/footer.jsp" %>
</body>
</html>