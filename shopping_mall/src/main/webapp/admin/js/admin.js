$(function(){
//아이디 중복체크 여부 
var $id_doubleck=0;
//로그인 ajax
	$('#frm_login').submit(function (event) {
		if($("#admin_id").val()==""){
			alert("아이디를 입력하세요");
			$("#admin_id").focus();
			return false;
		}else{
			if($("#admin_pass").val()==""){
				alert("비밀번호를 입력하세요");
				$("#admin_pass").focus();
				return false;
			}else{
				$("#frm_login").submit();
			}
		}
	
	});

	//아이디 중복 체크
	$("#idck").click(function(){
		if($("#admin_id").val()==""){
			alert("아이디를 입력하세요");
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

	//관리자 등록 버튼
	$("#add").click(function(){
		if($("#admin_id").val()==""){
				alert("아이디를 입력하셔야합니다.");
				$("#admin_id").focus();
				return false;
			}else{
				if($("#admin_pass").val()==""){
					alert("비밀번호를 입력하셔야합니다.");
					$("#admin_pass").focus();
					return false;
				}else{
					if($("#admin_passck").val()==""||$("#admin_passck").val()!=$("#admin_pass").val()){
						alert("비밀번호를 확인해주세요.");
						$("#admin_passck").focus();
						return false;
					}else{
						if($("#admin_name").val()==""){
							alert("이름을 입력해주세요");
							$("#admin_name").focus();
							return false;
						}else{
							if($("#admin_email").val()==""){
								alert("이메일을 입력해주세요");
								$("#admin_email").focus();
								return false;
							}else{
								if($("#admin_hp1").val()==""){
									alert("핸드폰번호를 확인해주세요");
									$("#admin_hp1").focus();
									return false;
								}else{
									if($("#admin_hp2").val()==""){
										alert("핸드폰번호를 확인해주세요");
										$("#admin_hp2").focus();
										return false;
									}else{
										if($("#admin_hp3").val()==""){
											alert("핸드폰번호를 확인해주세요");
											$("#admin_hp3").focus();
											return false;
										}else{
											if($("#admin_team").val()==""){
												alert("담당자 부서를 선택하세요");
												$("#admin_team").focus();
												return false;
											}else{
												if($("#admin_position").val()==""){
													alert("담당자 직책을 선택하세요");
													$("#admin_position").focus();
													return false;
												}else{
													if($id_doubleck!=1){
														alert("아이디 중복체크는 필수 사항입니다.");
														$("#idck").focus();
													}else{
														$("#frm_join").submit();
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}	
	});

	
	//등록 취소
	$("#back").click(function(){
		if(confirm("등록취소하시겠습니까?")){
			alert("로그인 페이지로 이동합니다.");
			location.href="./index.jsp";
		}
	});
});
//관리자 등록 승인여부 등록
function agree(aidx){
	if(confirm("승인을 취소 하시겠습니까?")){
		location.href="./admin_userok.do?agree=N,"+aidx;
	}
}
function disagree(aidx){
		if(confirm("승인하시겠습니까?")){
		location.href="./admin_userok.do?agree=Y,"+aidx;
	}
}