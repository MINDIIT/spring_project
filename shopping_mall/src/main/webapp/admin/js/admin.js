$(function(){
//로그인 ajax
	$('#frm_login').submit(function (event) {
		event.preventDefault();
		var formData = {
			admin_id : $("#admin_id").val(),
			admin_pass : $("#admin_pass").val()
		
		};
		
		$.ajax({
			url : "./admin_login.do",
			cache : false,
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			data : JSON.stringify(formData),
			success : function(response){
				console.log('성공:',response);
			},error:function(error){
				console.log("오류발생",error);
			}
		});
	});

//function login(){
//	var http, result;
//	
//	if(frm_login.admin_id.value==""){
//		alert("아이디를 입력하셔야합니다.");
//		return false;
//	}else{
//		if(frm_login.admin_pass.value==""){
//			alert("비밀번호를 입력하셔야합니다.");
//			return false;
//		}else{
//			return true;
//		}	
//	}
//}






	//관리자 등록 버튼
	$("#add").click(function(){
		if($("#admin_id").val()==""){
				alert("아이디를 입력하셔야합니다.");
				return false;
			}else{
				if($("#admin_pass").val()==""){
					alert("비밀번호를 입력하셔야합니다.");
					return false;
				}else{
					if($("#admin_passck").val()==""||$("#admin_passck").val()!=$("#admin_pass").val()){
						alert("비밀번호를 확인해주세요.");
						return false;
					}else{
						if($("#admin_name").val()==""){
							alert("이름을 입력해주세요");
							return false;
						}else{
							if($("#admin_email").val()==""){
								alert("이메일을 입력해주세요");
								return false;
							}else{
								if($("#admin_hp1").val()==""){
									alert("핸드폰번호를 확인해주세요");
									return false;
								}else{
									if($("#admin_hp2").val()==""){
										alert("핸드폰번호를 확인해주세요");
										return false;
									}else{
										if($("#admin_hp3").val()==""){
											alert("핸드폰번호를 확인해주세요");
											return false;
										}else{
											if($("#admin_team").val()==""){
												alert("담당자 부서를 선택하세요");
												return false;
											}else{
												if($("#admin_position").val()==""){
													alert("담당자 직책을 선택하세요");
													return false;
												}else{
													console.log("test");
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
