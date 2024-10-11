$(function(){
var $id_doubleck=0;

//중복확인 
	$("#idCheck").click(function(){
		if($("#admin_id").val()==""){
			alert("아이디를 입력하세요");
			$("#admin_id").focus();
		}else{
			$.ajax({
				url : "./idcheckok.do",
				cache : false,
				type : "post",
				dataType : "html",
				data : {
					admin_id : $("#admin_id").val()
				},
				contentType : "application/x-www-form-urlencoded",
				success : function($data,$result){
					if($data == "no"){
						alert("이미 사용중인 아이디 입니다.");
						$("#admin_id").val("");
						$("#admin_id").focus();
					}else{
						alert("사용가능한 아이디 입니다.");
						$id_doubleck =1;
					}
				},error : function($error){
					console.log($error);
				}
			});
		}
	});
	
	//관리자 등록
	$('#addMaster').click(function(){
		const adminHp = $('#admin_hp1').val() + $('#admin_hp2').val() + $('#admin_hp3').val();
	    const fieldsToCheck = [
	        { id: '#admin_id', message: '아이디를 입력하셔야합니다.' },
	        { id: '#admin_pass', message: '비밀번호를 입력하셔야합니다.' },
	        { id: '#admin_passck', message: '비밀번호를 확인해주세요.', check: () => $("#admin_passck").val() === $("#admin_pass").val() },
	        { id: '#admin_name', message: '이름을 입력해주세요' },
	        { id: '#admin_email', message: '이메일을 입력해주세요' },
	        { id: '#admin_hp1', message: '핸드폰 번호 앞자리를 입력해주세요' },
	        { id: '#admin_hp2', message: '핸드폰 번호 중간 자리를 입력해주세요' },
	        { id: '#admin_hp3', message: '핸드폰 번호 뒷자리를 입력해주세요' },
			{ id: '#admin_team', message: '부서를 선택하세요' },
			{ id: '#admin_position', message: '직책을 선택하세요' }
	    ];
	    for (const field of fieldsToCheck) {
	        const input = $(field.id);
	        if (input.val() === "" || (field.check && !field.check())) {
	            alert(field.message);
	            input.focus();
	            return false;
	        }
	    }			
	    if (adminHp.length !== 11) {
	        alert("핸드폰 번호를 정확히 입력해주세요.");
	        $('#admin_hp1').focus();
	        return false;
	    }		
		$('#admin_hp').val(adminHp); // hidden 필드에 합친 값 저장
		$('#frm_join').submit();
	});
	
	//등록 취소
	$("#Cancle").click(function(){
		if(confirm("등록취소하시겠습니까?")){
			alert("로그인 페이지로 이동합니다.");
			location.href="./index.do";
		}
	});
});