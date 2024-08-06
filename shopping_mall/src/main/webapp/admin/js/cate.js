$(function(){
	////////     카테고리 리스트 페이지     ///////////////////////////////////
	
	//카테고리 등록페이지로 이동
	$("#btn_cate_add").click(function(){
		location.href="/admin/cate_write.do";
	});
	
	//상품 리스트 페이지로 이동
	$('#btn_product_list').click(function(){
		location.href="/admin/product_list.do";
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
	//카테고리 삭제 버튼 핸들링
	$('#cate_delete_btn').click(function(){
		var select_category=[];
		var delete_data="";
		$('input[name="category_ck"]:checked').each(function(){
			select_category.push($(this).val());
		});
		if(select_category==""){
			alert('삭제할 카테고리를 선택하세요');
			return false;
		}else{
			for(var i = 0;i<select_category.length;i++){
				delete_data = select_category.join(',');
			}
		}
		location.href="./category_delete.do?cidx="+delete_data;
	});	
	
	//카테고리 전체 체크박스 핸들링
	$('#category_all_ck').click(function(){
		var isChecked = $(this).prop('checked');
		$('input[name="category_ck"]').prop('checked',isChecked);
	});
	
	//카테고리 체크박스 핸들링
	$('input[name="category_ck"]').click(function(){
		var total = $('input[name="category_ck"]').length;
		var checked = $('input[name="category_ck"]:checked').length;
		$('#category_all_ck').prop('checked',total===checked);
	});
	
	
	
	///////     카테고리 등록 페이지     ////////////////////////
	
	//카테고리 리스트 페이지로 이동
	$("#btn_cate_list").click(function(){
		location.href="/admin/cate_list.do";
	});
	
	//분류코드 숫자 입력 유효성 검사
	$('#classification_code').on('input',function(){
		var inputvalue = $(this).val();
		if(/[^0-9]/.test(inputvalue)){
			alert('숫자만 입력하세요');
			var sanitizedValue = inputvalue.replace(/[^0-9]/g,'');
			$(this).val(sanitizedValue);
		}
	});
	
	//대메뉴 코드 숫자만 입력 가능하게 하는 유효성 검사
		$('#main_menu_code').on('input',function(){
		var inputvalue = $(this).val();
		if(/[^0-9]/.test(inputvalue)){
			alert('숫자만 입력하세요');
			var sanitizedValue = inputvalue.replace(/[^0-9]/g,'');
			$(this).val(sanitizedValue);
		}
	});
	
	//대메뉴명 한글 or 영문만 입력하게하는 유효성 검사
	$('#main_menu_name').on('input',function(){
		var inputv = $(this).val();
		if (/[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]/.test(inputv)){
			alert('한글과 영문만 입력 가능합니다.');
			var sanitizedValue = inputv.replace(/[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]/g, '');
			$(this).val(sanitizedValue);
		}
	});
	
	//카테고리 등록
	$("#insert_cate").click(function(){
		if($('#classification_code').val()==""){
			alert('분류 코드를 입력하세요');
			$('#classification_code').focus();
		}else{
			if($("#classification_code").val().length<2){
				alert('분류코드는 최소 2자리 이상 숫자로 이루어져야합니다.');
				$('#classification_code').focus();
			}else{
				if($('#main_menu_code').val()==""){
					alert('대메뉴 코드를 입력하세요');
					$('#main_menu_code').focus();
				}else{
					if($('#main_menu_code').val().length<2){
						alert('대메뉴 코드는 최소 2자리 이상 숫자로 이루어져야합니다.');
						$('#main_menu_code').focus();
					}else{
						if($('#main_menu_name').val()==""){
							alert('대메뉴 명을 입력하세요');
							$('#main_menu_name').focus();
						}else{
							$("#frm_cate_add").submit();							
						}
					}
				}
			}
		}
	});
});