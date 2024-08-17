$(function(){
	
	//로그인 페이지 - 회원가입 버튼
	$('#to_agreepage').click(function(){
		location.href='./agree.do';
	});
	
	//약관동의 페이지 - 다음단계 버튼
	$('#btnNextStep').click(function(){
		location.href='./join.do';
	});
	
	
})