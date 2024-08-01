$(function(){
	////////     카테고리 리스트 페이지     ///////////////////////////////////
	$("#btn_cate_add").click(function(){
		location.href="/admin/cate_write.do";
	});
	
	$('#btn_product_list').click(function(){
		location.href="/admin/product_list.do";
	});
	
	///////     카테고리 등록 페이지     ////////////////////////
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
		var inputvalue = $(this).val();
		if (/[^가-힣a-zA-Z]/.test(inputValue)){
			alert('한글과 영문만 입력 가능합니다.');
			var sanitizedValue = inputValue.replace(/[^가-힣a-zA-Z]/g, '');
			$(this).val(sanitizedValue);
		}
	});
	
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
							//$("#frm_cate_add").submit();							
						}
					}
				}
			}
		}
	});
});