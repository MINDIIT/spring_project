<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <link rel="stylesheet" type="text/css" href="./css/product.css?v=5">
    <link rel="icon" href="./img/logo.png" sizes="128x128">
    <link rel="icon" href="./img/logo.png" sizes="64x64">
    <link rel="icon" href="./img/logo.png" sizes="32x32">
    <link rel="icon" href="./img/logo.png" sizes="16x16">
</head>
<body>
<script src="./js/jquery.js"></script>
<script src="./js/product.js?v=2"></script>
<%@ include file="/admin/header.jsp" %>
<main class="maincss">
<section>
<form id="frm_product_insert" action="./product_insertok.do" method="post" enctype="multipart/form-data">
<input type="hidden" value="<%=admin_id%>" name="admin_id">
 <input type="hidden" id="classification_code" name="classification_code">
 <input type="hidden" id="main_menu_code" name="main_menu_code">
 
<p>상품 수정 페이지</p>
<div class="product_insert">
    <ul>
        <li>대메뉴 카테고리</li>
        <li>
            <select class="product_input1" id="main_menu_name" name="main_menu_name">
            <cr:forEach var="data" items="${cate_info}">
                <option value="${data.main_menu_name}" data-main-menu-code="${data.main_menu_code}" data-classification-code="${data.classification_code}"
                 <cr:if test="${data.main_menu_name == products_info[0].main_menu_name}">
                    selected
                </cr:if>>${data.main_menu_name}</option>
            </cr:forEach>   
            </select>
            <input type="button" value="카테고리 등록" title="카테고리 등록" class="product_btn" id="cate_add"> 
            <span class="help_text">※ 해당 카테고리가 없을 경우 신규 등록하시길 바랍니다.</span>
        </li>
    </ul>
    <ul>
        <li>상품코드</li>
        <li>
            <input type="text" class="product_input1" id="product_code_edit" name="product_code" value="${products_info[0].product_code}">  
        </li>
    </ul>
    <ul>
        <li>상품명</li>
        <li>
            <input type="text" class="product_input2" maxlength="100" id="product_name" name="product_name" value="${products_info[0].product_name}"> 
            <span class="help_text">※ 상품명은 최대 100자까지만 적용할 수 있습니다.</span>
        </li>
    </ul>
    <ul>
        <li>상품 부가설명</li>
        <li>
            <input type="text" class="product_input4" maxlength="200" id="product_additional_description" name="product_additional_description" value="${products_info[0].product_additional_description}"> 
            <span class="help_text">※ 상품명은 최대 200자까지만 적용할 수 있습니다.</span>
        </li>
    </ul>
    <ul>
        <li>판매가격</li>
        <li>
            <input type="text" class="product_input3" maxlength="7" id="product_price" name="product_price" value="${products_info[0].product_price}"> 
            <span class="help_text">※ , 없이 숫자만 입력하세요 최대 7자리</span>
        </li>
    </ul>
    <ul>
        <li>할인율</li>
        <li>
            <input type="text" class="product_input3" maxlength="2" value="0" id="discount_rate" name="discount_rate" value="${products_info[0].discount_rate}">% 
            <span class="help_text">※ 숫자만 입력하세요</span>
        </li>
    </ul>
    <ul>
        <li>할인가격</li>
        <li>
            <input type="text" class="product_input3" maxlength="7" value="0" readonly id="discount_price" name="discount_price" value="${products_info[0].discount_price}"> 
            <span class="help_text">※ 할인율이 0%일 경우 할인가격은 0으로 처리 합니다.</span>
        </li>
    </ul>
    <ul>
        <li>상품재고</li>
        <li>
            <input type="text" class="product_input3" maxlength="4" value="0" id="product_stock" name="product_stock" value="${products_info[0].product_stock}">EA 
            <span class="help_text">※ 숫자만 입력하세요. 재고가 0일 경우 soldout이 됩니다.
            </span>
        </li>
    </ul>
    <ul>
        <li>판매 유/무</li>
        <li>
            <label class="product_label">
            <input type="radio" name="is_available" value="Y" style="vertical-align:-1px;" checked> 판매시작
            </label>
            <label class="product_label">
            <input type="radio" name="is_available" value="N" style="vertical-align:-1px;"> 판매종료
            </label> <span class="help_text">※ 숫자만 입력하세요. 재고가 0일 경우 soldout이 됩니다.</span>
        </li>
    </ul>
    <ul>
        <li>조기품절</li>
        <li>
            <label class="product_label">
                <input type="radio" name="is_sold_out_early" value="Y" style="vertical-align:-1px;"> 사용
            </label>
            <label class="product_label">
                <input type="radio" name="is_sold_out_early" value="N" style="vertical-align:-1px;" checked> 미사용
            </label>
        </li>
    </ul>
    <ul style="height: 160px;">
        <li>상품 대표이미지</li>
        <li>
            <ol style="width:100%; height: auto;">
            <li style="width:100%; height:45px;">
            <input type="file" accept="image/*" id="main_product_image1" name="main_product_image1_path">
            <span class="help_text">※ 상품 대표이미지 이며, 이미지 용량은 2MB 까지 입니다.</span>
            </li>
            <li style="height:45px;">
            <input type="file" accept="image/*" id="main_product_image2" name="main_product_image2_path">
            <span class="help_text">※ 추가 이미지 이며, 이미지 용량은 2MB 까지 입니다.</span>
            </li>
            <li style="height:45px;">
            <input type="file" accept="image/*" id="main_product_image3" name="main_product_image3_path">
            <span class="help_text">※ 추가 이미지 이며, 이미지 용량은 2MB 까지 입니다.</span>
            </li>
            </ol>
        </li>
    </ul>
    <ul style="height: 400px;">
        <li>상품 상세설명</li>
        <li>
            <textarea class="product_text1" id="product_detailed_description" name="product_detailed_description"></textarea>
        </li>
    </ul>
</div>
<div class="subpage_view4" style="text-align:center; margin-bottom: 100px;">
    <input type="button" value="상품 리스트" title="상품 리스트" id="product_list_btn" class="p_button p_button_color1" style="margin-right: 5px;">
    <input type="button" value="상품 등록" title="상품 등록" id="product_insert_btn" class="p_button p_button_color2">
    </span>
</div>
</form>
</section>
</main>
<%@ include file="../admin_page/footer.jsp" %>
</body>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
        	var mainmenuselect = document.getElementById('main_menu_name');
        	var calssificationcodeinput = document.getElementById('classification_code');
        	var mainmenucodeinput =  document.getElementById('main_menu_code');
            
        	// 카테고리 선택 시 hidden 에 분류코드 값 넣기
            mainmenuselect.addEventListener('change', function() {
                var selectedOption = this.options[this.selectedIndex];
                var classificationCode = selectedOption.getAttribute('data-classification-code');
                var mainmenucode = selectedOption.getAttribute('data-main-menu-code');
                
                
                calssificationcodeinput.value = classificationCode;
                mainmenucodeinput.value = mainmenucode;
                
                //스토리지에 값 저장
                sessionStorage.setItem('classification_code', classificationCode);
                sessionStorage.setItem('main_menu_code', mainmenucode);
                console.log(classificationCode);
                console.log(mainmenucode);
            });
			
        	//페이지 로드 시 스토리지에서 값 불러오기
        	var stored_classificationcode = sessionStorage.getItem('classification_code');
        	var stored_mainmenucode = sessionStorage.getItem('main_menu_code');
        	
        	if(stored_classificationcode && stored_mainmenucode){
        		calssificationcodeinput.value = stored_classificationcode;
        		mainmenucodeinput.value = stored_mainmenucode;
        		
        		for(var i = 0; i<mainmenuselect.options.length;i++){
        			var option = mainmenuselect.options[i];
        			if(option.petAttribute('data-classification-code')=== stored_classificationcode &&
        					option.petAttribute('data-main-menu-code')=== stored_mainmenucode){
        				option.selected = true;
        				break;
        			}
        		}
        	}else{
	            // 초기 선택된 옵션에 따라 hidden 
	            var first = document.getElementById('main_menu_name').options[document.getElementById('main_menu_name').selectedIndex];
	            var firstcode_cl = first.getAttribute('data-classification-code');
	            var firstcode_mn = first.getAttribute('data-main-menu-code');
	            calssificationcodeinput.value = firstcode_cl;
	            mainmenucodeinput.value = firstcode_mn;
	            console.log(firstcode_cl);
	            console.log(firstcode_mn);        		
        	}
        });
    </script>
</html>