$(function(){

  	let countdown; //타이머 변수
  	let remainingTime = 180;
  	//setInterval 쓰면 멈췄다가 다시 진행시켜도 시계가 계속 돌고있음, 
	//그래서 setTimeout 써야함
  	function startTimer() {
	     if (countdown) {
	        clearTimeout(countdown);
	    } 	
		countdown = setTimeout(function tick() {
			remainingTime--;
			const minutes = Math.floor(remainingTime/60);
			const seconds = remainingTime % 60;
			$('#timer').text(`${minutes}:${seconds < 10 ? '0' : ''}${seconds}`);
			if (remainingTime >0 ) {
				countdown = setTimeout(tick, 1000);
			}else{
				clearTimeout(countdown);
				countdown = null; //타이머 변수 초기화
				$('#email_verification').hide();
				$('#timer').text('유효시간 만료');
			}
		},1000);
	}
	
	
	//아이디 중복 체크 여부
	var $id_doubleck=0;
	//아이디 중복 확인
	$('#id_duplicate_check').click(function(){
		var mid = $('#mid').val().trim();
		var userid = /^[a-zA-Z0-9]+$/;
		
		if($('#mid').val().trim()==""){
			alert('아이디를 입력하세요');
			$('#mid').focus();
		}else if(!userid.test(mid)){
			alert('아이디는 영문자와 숫자만 허용됩니다.');
			$('#mid').focus();
		}else if(mid.length < 6){
			alert('아이디는 최소 6자 이상이어야 합니다.');
			$('#mid').focus();
		}else{
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
		}		
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
	
	//메일인증여부 플래그
	let isEmailVerified = false;
	
	//인증 메일 발송 버튼
	$('#email_verification').click(function(){
		if($('#memail').val()=="" || $('#memail').val() == null){
			alert("이메일 주소를 입력해주세요");
			$('#memail').focus();
			return false;
		}
		
		$('#timer').text('03:00');
		var verify_code = generateAuthCode();
		
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
					remainingTime = 180;
					startTimer();
					$('#email_verification').hide();
					$('#resendBtn').show();
					$('#verification_code').focus();
				}else{
					alert("오류로 인해 인증번호 발송에 실패했습니다.");
				}
			},error : function(error){
				console.log($error);
			}			
		});
	});
	
	//이메일 인증 재발송 ajax 요청
	$('#resendBtn').click(function(){
	   if (countdown) {
	        clearInterval(countdown); // 기존 타이머 멈추기
	    }
		$('#timer').text('03:00');
		remainingTime = 180;
		var verify_code = generateAuthCode();
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
					alert("인증번호가 재발송 되었습니다.");
					startTimer();
					$('#email_verification').hide();
					$('#resendBtn').show();
					$('#verification_code').focus();
				}else{
					alert("오류로 인해 인증번호 재발송에 실패했습니다.");
				}
			},error : function(error){
				console.log($error);
			}			
		});
	});	
	
	//이메일 인증코드 확인
	$('#email_verifyck').click(function(){
		var userInputCode = $('#verification_code').val();
		$.ajax({
			url : "./verifyCode_ok.do",
			method : 'post',
			dataType : "html",
			data : {
				code : userInputCode
			},contentType : "application/x-www-form-urlencoded",
			success : function($data){
				if($data =="success"){
					alert("인증되었습니다.");
					isEmailVerified = true;
					
					//타이머 멈추기 
					clearTimeout(countdown);
					countdown = null;
					
					//타이머와 재발송 버튼, 인증확인 버튼 숨기기
					$('#timer').hide();
					$('#resendBtn').hide();
					$('#email_verifyck').hide();
					//이메인 인증버튼, 재발송 버튼 비활성화
					$('#email_verification').prop('disabled', true); 
				}else{
					alert("인증 실패! 올바른 코드를 입력해주세요.");
				}
			},error : function(error){
				console.log('Error:', error);
			}
		});
	
	});
	
	//전화번호입력 시 숫자만 입력받는코드
	$('#mhp').on('input',function(){
		var input = $(this).val();
		var fileteredInput= input.replace(/[^0-9]/g,'');
		$(this).val(fileteredInput);
	});
	
		
	//회원 가입 버튼
	
	$('#btnNextStep').click(function(){
	
		var mid = $('#mid').val().trim();
		var mpass = $('#mpass').val().trim();
		var mpass_ck = $('#mpass_ck').val().trim();
		
		var userid = /^[a-zA-Z0-9]+$/;
		var passpattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{6,}$/;
		var namepattern = /^[가-힣a-zA-Z]+$/;
		
		//이벤트 수신 동의 체크박스 핸들링
		if($('#email_ad').is(':checked')){
			$('#email_ad').val('Y');
		}else{
			$('#email_ad').val('N');
		}
		if($('#sms_ad').is(':checked')){
			$('#sms_ad').val('Y');
		}else{
			$('#sms_ad').val('N');
		}		
		
		
		if($('#mid').val().trim()==""){
			alert('아이디를 입력하세요');
			$('#mid').focus();
		}else if($('#mpass').val()==""){
			alert('비밀번호를 입력하세요');
			$('#mpass').focus();
		}else if($('#mpass').val().length < 6){
			alert('비밀번호는 최소 6자리 이상이어야합니다.');
			$('#mpass').focus();
		}else if(!passpattern.test(mpass)){
			alert('비밀번호는 숫자, 대소문자, 특수문자를 포함해야 합니다.');
		}else if(mpass != mpass_ck){
			 alert('비밀번호와 비밀번호 재확인이 일치하지 않습니다.');
			 $('#mpass_ck').focus();
		}else if ($('#mname').val().trim()==""){
			alert('이름을 입력하세요.');
			$('#mname').focus();
		}else if(!namepattern.test($('#mname').val().trim())){
			alert('이름에는 한글 또는 영문만 입력할 수 있습니다. 공백, 숫자, 특수문자는 허용되지 않습니다.');
			$('#mname').focus();
		}else if($('#mhp').val().trim()==""){
			alert('전화번호를 입력하세요');
			$('#mhp').focus();
		}else if($id_doubleck!=1){
			alert('아이디 중복확인은 필수입니다.');
			$('#id_duplicate_check').focus();
		}else if(!isEmailVerified){
			 alert('메일 인증을 완료해주세요.');
		}else{
			$('#frm_member_join').submit();
		}
		
		
	});
	
});