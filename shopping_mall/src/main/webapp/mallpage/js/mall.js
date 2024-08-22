$(function(){
	
	//로그인 페이지 - 회원가입 버튼
	$('#to_agreepage').click(function(){
		location.href='./agree.do';
	});
	
	//로그인 페이지 - 로그인 버튼
	$('#member_login_btn').click(function(){
		if($('#rememberMe').is(':checked')){
			var userId = $('#mid').val();
			
			
		}
		$('#member_login').submit();
	});
	
	//약관동의 페이지 - 다음단계 버튼
	$('#btnNextStep').click(function(){
		location.href='./join.do';
	});
	
	//회원 가입 시 약관 동의 체크박스 핸들링
	//# 약관 전체 체크박스
	$('#allAgree_ck').click(function(){
		var isChecked = $(this).prop('checked');
		$('input[name="agreementInfoFl"]').prop('checked',isChecked);
		$('input[name="privateApprovalFl"]').prop('checked',isChecked);
	});
	//#약관 개별 체크박스 핸들링
	$('.require').on('change',function(){
		if($('#termsAgree1').is(':checked')&& $('#termsAgree2').is(':checked')){
			$('#allAgree_ck').prop('checked',true);
		}else{
			$('#allAgree_ck').prop('checked',false);
		}
	});
	
	//다음단계 버튼 핸들링
	$('#btnNextStep_agree').click(function(){
		if(!$('#termsAgree1').is(':checked')|| !$('#termsAgree2').is(':checked')){
			alert("약관에 모두 동의하셔야 회원가입을 진행 할 수 있습니다.");
			return false;
		}else{
			location.href="./join.do";
		}
	});
})
function setCookie(name,value,days){
	var date = new Date();
	date.setTime(date.getTime()+(days*24*60*60*1000));
	var expires = 'expires='+date.toUTCString();
	document.cookie = name + "=" + value + ";" + expires + ";path=/";
}