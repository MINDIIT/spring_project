$(function(){
	
	
	//자동 난수 생성
	
	
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