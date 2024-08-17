$(function(){
	
	//아이디 중복 체크 여부
	var $id_doubleck=0;
	//아이디 중복 확인
	$('#id_duplicate_check').click(function(){
		$.ajax({
			url :'./duplicate_idcheck.do',
			cache : false,
			type : "post",
			dataType : "html",
			data :{
				mid : $('#mid').val()
			} ,
			contentType : "application/x-www-form-urlencoded",
			success : function($data){
				if($data == "duplicate"){
					alert("이미 사용중인 아이디 입니다.");
					$("#mid").val("");
					$("#mid").focus();
				}else{
					alert("사용가능한 아이디 입니다.");
					$id_doubleck =1;
				}
			},error : function(error){
				console.log($error);
			}
			
		})
		
	});
	
	//인증 번호 생성
	function generateAuthCode(){
		//알파벳 대문자 + 숫자 조합 인증코드
		var characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
		var authcode = '';
		for(var i=0;i<8;i++){
			var random = Math.floor(Math.random()*characters.length);
			authcode += characters.charAt(random);
		}
		return authcode;
	}
	var verify_code = generateAuthCode();
	
	//이메일 인증 버튼
	$('#email_verification').click(function(){
		
		$.ajax({
			url :'./email_verification.do',
			cache : false,
			type : "post",
			dataType : "html",
			data :{
				memail : $('#memail').val(),
				number : verify_code
			} ,contentType : "application/x-www-form-urlencoded",
			success : function($data){
				if($data =="success"){
					alert("인증번호가 발송 되었습니다.");
					$('#verification_code').focus();
				}else{
					alert("오류로 인해 인증번호 발송에 실패했습니다.");
				}
			},error : function(error){
				console.log($error);
			}			
		});
	});
		
	//회원 가입 버튼
	$('#btnNextStep').click(function(){
		
	});	
	
})