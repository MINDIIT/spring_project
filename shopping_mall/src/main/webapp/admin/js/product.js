$(function(){
	
	
	
	//할인 가격 자동 계산 함수(할인율 자동적용)
	function calculateDiscountPrice(){
		var ori_price = $('#product_price').val();
		var dis_rate = $('#discount_rate').val();
		if(ori_price && dis_rate){
		//소수점 이하 반올림
			var discount_price =ori_price- Math.round(ori_price * dis_rate / 100);
			$('#discount_price').val(discount_price);
		}	
	}
	
	//입력 받을 때 불필요한 0 제거하는 함수
	function removeZero(value){
		return value.replace(/^0+/, '');
	}
	
	// 숫자유효성 검사 함수
	function numeric_validation(value){
	    if (/[^0-9]/.test(value)) {
	        alert('숫자만 입력하세요');
	        return value.replace(/[^0-9]/g, '');
	    }
	    return value;		
	}
	//첨부파일 형식 검증하는 함수
	function validateFile(input) {
		var file = input[0].files[0];
		var validExtensions = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif'];
		//파일이 존재 하고 유효한 파일 형식에 포함되지 않으면
		console.log("File type:", file.type);
		/*
		if(file && !validExtensions.includes(file.type)){
			alert('이미지 파일만 업로드 가능합니다.(jpg, jpeg, png, gif)');
			input.val('');
		}*/
	}
	
	
    // 각 파일 입력 요소에 대해 change 이벤트 핸들러 설정
    $('#main_product_image1, #main_product_image2, #main_product_image3').on('change', function() {
        validateFile($(this));
    });	
	
	//첨부파일 첨부 시 이미지형식 유효성 검사
	$('.image-input').on('change',function(){
		validateFile($(this));
	});	
	
	// 할인율 입력 시 할인가격 자동 적용
	$('#discount_rate').on('input',function(){
		 var discountRate = $(this).val();
		if($('#product_price').val()==""){
			alert('판매가격을 먼저 입력하세요');
			$('#product_price').focus();
		}else{
			//숫자 유효성 검사 및 불필요한 0 제거
			discountRate = numeric_validation(discountRate);
			discountRate = removeZero(discountRate);
			$(this).val(discountRate);
			calculateDiscountPrice();
		}
	});
	
	//판매 가격 입력 후 포커스 잃을 때 할인 가격 계산, 입력 값 가공
	$('#product_price').on('blur',function(){
		var productPrice = $(this).val();
		
		productPrice = numeric_validation(productPrice);
		productPrice = removeZero(productPrice);
		$(this).val(productPrice);
		
		if($('#discount_rate').val()!=""){
			calculateDiscountPrice();
		}
	});	
	
	//판매 가격 숫자 유효성 검사
	$('#product_price').on('input',function(){
		var inputvalue = $(this).val();
		inputvalue = numeric_validation(inputvalue);
		$(this).val(inputvalue);
	});
	
	
	//자동 난수 생성
	function generateRandomCode(){
		return Math.floor(1000000 + Math.random()*9000000).toString();
	}
	//난수 생성해서 삽입
	function setProductCode(){
		var productCode = generateRandomCode();
		$('#product_code').val(productCode);	
	}
	//페이지 로드 시 난수 생성
	setProductCode();
	
	//상품코드 중복 체크 여부 확인
	var $product_code_doubleck=0;
	
	//상품 리스트 버튼
	$('#product_list_btn').click(function(){
		location.href='./product_list.do';
		
	});
	
	//상품 등록 버튼
	$('#product_insert_btn').click(function(){
		//파일이 첨부되었는지 여부를 검사하는 변수
		var fileox = false;
		
		//파일이 있으면 true 값 입력됨
		if($('#main_product_image1').val()|| $('#main_product_image2').val() || $('#main_product_image3').val()){
			fileox = true;
		}
		
		//할인율이 입력되지 않았을 경우 값을 0으로 설정
		if($('#discount_rate').val() === ''){
			$('#discount_rate').val(0);	
		}
	
		if($('#product_name').val()==""){
			alert('상품명을 입력하세요');
			$('#product_name').focus();
		}else if($('#product_additional_description').val()==""){
			alert('상품 부가 설명을 입력하세요');
			$('#product_additional_description').focus();
		}else if($('#product_price').val()==""){
			alert('판매가격을 입력하세요');
			$('#product_price').focus();
		}else if($('#product_stock').val()==""){
			alert('상품 재고를 입력하세요');
			$('#product_stock').focus();
		}else if(!fileox){
			alert('최소 1개의 이미지를 첨부해야 합니다.');
		}else if($('#product_detailed_description').val()==""){
			alert('상품 상세 설명을 입력하세요.');
			$('#product_detailed_description').focus();
		}else if($product_code_doubleck!=1){
			alert('상품 코드 중복확인은 필수 입니다.');
			$('#product_btn').focus();
		}else{
			$('#frm_product_insert').submit();
		}
	});

	
	//상품 코드 중복 확인 버튼
	$('#product_btn').click(function(){
		$.ajax({
			url : "./product_codeok.do",
			cache : false,
			type : "post",
			dataType : "html",
			data : {
				product_code : $('#product_code').val()
			},
			contentType : "application/x-www-form-urlencoded",
			success : function($data,$result){
				if($data == "yes"){
					alert('상품코드가 중복되었습니다.');
					setProductCode();
				}else{
					alert('사용가능한 상품코드입니다.');
					$product_code_doubleck =1;
				}
			},error : function($error){
				console.log($error);
			}
		});
	});
	
	//상품 삭제 버튼 핸들링
	$('#product_delete_btn').click(function(){
		var select_product=[];
		var delete_data="";
		$('input[name="product_ck"]:checked').each(function(){
			select_product.push($(this).val());
		});
		if(select_product==""){
			alert('삭제할 상품을 선택하세요');
			return false;
		}else{
			for(var i = 0;i<select_product.length;i++){
				delete_data =select_product.join(',');
			}
			location.href="./product_delete.do?pidx="+delete_data;
		}
	});
	
	//상품 전체 체크박스 버튼 핸들링
	$('#product_all_ck').click(function(){
		var isChecked = $(this).prop('checked');
		
		$('input[name="product_ck"]').prop('checked',isChecked);
	});
	
	//상품 체크박스 핸들링
	$('input[name="product_ck"]').click(function(){
		var total = $('input[name="product_ck"]').length;
		var checked = $('input[name="product_ck"]:checked').length;
		$('#product_all_ck').prop('checked',total===checked);
	});
	
	//카테고리 리스트 검색 버튼
	$('#cate_search_btn').click(function(){
		if($('#search_word_category').val()==""){
			alert('검색할 단어를 입력하세요');
			$('#search_word_category').focus();
		}else{
			$('#frm_search_cate').submit();
		}
	});
	
	
	//상품 리스트 검색 버튼
	$('#search_btn').click(function(){
		if($('#search_word').val()==""){
			alert('검색할 단어를 입력하세요');
			$('#search_word').focus();
		}else{
			$('#frm_search_btn').submit();
		}
	});
	
	//카테고리 등록 버튼
	$('#cate_add').click(function(){
		location.href="/admin/cate_list.do";
	});	
//////// 신규상품 등록 버튼
	$('#btn_product_add').click(function(){
		location.href="/admin/product_write.do";
	});
//////// 카테고리 리스트 버튼
	$('#btn_cate_list').click(function(){   
		location.href="/admin/cate_list.do";
	})		
});