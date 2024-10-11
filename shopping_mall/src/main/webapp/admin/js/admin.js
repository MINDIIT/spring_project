$(function(){	
////////아이디 중복체크 여부 
var $id_doubleck=0;

//////    상품관리페이지   //////////////////////////////////////////////////////
//////// 홈페이지 기본 설정 저장취소버튼
	$('#btn_cancel').click(function(){
		if(confirm('저장취소 하시겠습니까?')){
			location.reload();			
		}
	});

//////// 쇼핑몰 기본 설정 페이지   ////////////////////////////////////////////////////////////////////

//////// 홈페이지 기본설정 저장버튼
	$('#btn_save').click(function(){
		
		if($('#website_title').val()==""){
			alert("홈페이지 제목을 입력하세요.");
			$('#website_title').focus();
		}else{
			if($('#administrator_email_address').val()==""){
				alert("관리자 메일 주소를 입력하세요.");
				$('#administrator_email_address').focus();
			}else{
				if($('#registration_bonus_points').val()==""){
					alert("회원가입시 적립금을 입력하세요.");
					$('#registration_bonus_points').focus();
				}else{
					if($('#permission_level').val()==""){
						alert("회원가입시 권한레벨을 입력하세요.");
						$('#permission_level').focus();
					}else{
						if($('#company_name').val()==""){
							alert("회사명을 입력하세요.");
							$('#company_name').focus();
						}else{
							if($('#business_registration_number').val()==""){
								alert("사업자등록번호를 입력하세요.");
								$('#business_registration_number').focus();
							}else{
								if($('#business_registration_number').val().length<10){
									alert("사업자등록 번호는 '-'을 제외한 10자리로 입력해주세요.");
									return false;
									$('#business_registration_number').focus();
								}else{
									if($('#ceo_name').val()==""){
										alert("대표자명을 입력하세요.");
										$('#ceo_name').focus();
									}else{
										if($('#company_phone_number').val()==""){
											alert("대표전화번호를 입력하세요.");
											$('#company_phone_number').focus();
										}else{
											if($('#business_location_postal_code').val()==""){
												alert("사업장 우편번호를 입력하세요.");
												$('#business_location_postal_code').focus();
											}else{
												if($('#business_location_address').val()==""){
													alert("사업장 주소를 입력하세요.");
													$('#business_location_address').focus();
												}else{
													if($('#data_protection_officer_name').val()==""){
														alert("정보관리책임자명을 입력하세요.");
														$('#data_protection_officer_name').focus();
													}else{
														if($('#data_protection_officer_email').val()==""){
															alert("정보책임자 E-mail을 입력하세요.");
															$('#data_protection_officer_email').focus();
														}else{
															if($('#bank_transfer_bank_name').val()==""){ //은행입력란에 값이 없을 때 
																if($('#bank_transfer_account_number').val()!=""){
																	alert("무통장 은행을 입력하세요.");
																	$('#bank_transfer_bank_name').focus();
																}else{
																	if(!$('#bank_transfer_account_number').val().includes('-')&&$('#bank_transfer_bank_name').val()!=""){
																		alert("'-'을 포함하여 계좌번호를 입력하여주시기 바랍니다.");
																		$('#bank_transfer_account_number').focus();
																	}else{
																		
																	
																	if($('#min_payment_point').val()==""){
																		alert("결제 최소 포인트를 입력하세요.");
																		$('#min_payment_point').focus();
																	}else{
																		if($('#min_payment_point').val()<1000){
																			alert("결제 최소 포인트는 1000이상이어야합니다.");
																			$('#min_payment_point').focus();
																		}else{
																			if($('#max_payment_point').val()==""){
																				alert("결제 최대 포인트를 입력하세요.");
																				$('#max_payment_point').focus();
																			}else{
																				if($('#min_payment_point').val()>$('#max_payment_point').val()){
																					alert('결제 최대 포인트는 결제 최소 포인트보다 큰 숫자가 입력되어야합니다.');
																					$('#max_payment_point').focus();
																				}else{
																					if($('#delivery_company_name').val()==""){
																						alert("배송업체명을 입력하세요.");
																						$('#delivery_company_name').focus();
																					}else{
																						if($('#delivery_fee').val()==""){
																							alert("배송비 가격을 입력하세요.");
																							$('#delivery_fee').focus();
																						}else{
																							if($('#online_business_registration_number').val()!=""&&$('#online_business_registration_number').val().length<12){
																								alert("통신판매업 신고번호와 부가통신 사업자번호는 '-'를 제외한 12자리로 입력해주세요");
																								$('#online_business_registration_number').focus();
																							}else{
																								if($('#value_added_service_provider_number').val()!="" && $('#value_added_service_provider_number').val().length<12){
																									alert("통신판매업 신고번호와 부가통신 사업자번호는 '-'를 제외한 12자리로 입력해주세요");
																									$('#value_added_service_provider_number').focus();
																								}else{
																									var formData = $('#frm_settings').serialize();
																										$.ajax({
																											url : "./settingsok.do",
																											cache : false,
																											type : "post",
																											data : formData,
																											contentType : "application/x-www-form-urlencoded",
																											success:function(response){
																												alert('설정 저장에 성공했습니다.');
																												location.href="/admin/admin_siteinfo.do";
																											},error:function(xhr,status,error){
																												alert('설정 저장에 실패했습니다 :'+error);
																												location.reload();
																											}
																										});
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
																}else{//은행 입력란에 값이 있을 때 
																	if($('#bank_transfer_account_number').val()==""){
																		alert("은행계좌번호를 입력하세요.");
																		$('#bank_transfer_account_number').focus();
																	}else{
																		if(!$('#bank_transfer_account_number').val().includes('-')&&$('#bank_transfer_account_number').val()!=""){
																			alert("'-'을 포함하여 계좌번호를 입력하여주시기 바랍니다.");
																			$('#bank_transfer_account_number').focus();
																		}else{
																			
																	
																		if($('#min_payment_point').val()==""){
																			alert("결제 최소 포인트를 입력하세요.");
																			$('#min_payment_point').focus();
																		}else{
																			if($('#min_payment_point').val()<1000){
																				alert("결제 최소 포인트는 1000이상이어야합니다.");
																				$('#min_payment_point').focus();
																			}else{
																				if($('#max_payment_point').val()==""){
																					alert("결제 최대 포인트를 입력하세요.");
																					$('#max_payment_point').focus();
																				}else{
																					if(parseInt($('#min_payment_point').val())>parseInt($('#max_payment_point').val())){
																						alert('결제 최대 포인트는 결제 최소 포인트보다 큰 숫자가 입력되어야합니다.');
																						$('#max_payment_point').focus();
																					}else{
																						if($('#delivery_company_name').val()==""){
																							alert("배송업체명을 입력하세요.");
																							$('#delivery_company_name').focus();
																						}else{
																							if($('#delivery_fee').val()==""){
																								alert("배송비 가격을 입력하세요.");
																								$('#delivery_fee').focus();
																							}else{
																								if($('#online_business_registration_number').val()!=""&&$('#online_business_registration_number').val().length<12){
																									alert("통신판매업 신고번호와 부가통신 사업자번호는 '-'를 제외한 12자리로 입력해주세요");
																									$('#online_business_registration_number').focus();
																								}else{
																									if($('#value_added_service_provider_number').val()!="" && $('#value_added_service_provider_number').val().length<12){
																										alert("통신판매업 신고번호와 부가통신 사업자번호는 '-'를 제외한 12자리로 입력해주세요");
																										$('#value_added_service_provider_number').focus();
																									}else{
																										var formData = $('#frm_settings').serialize();
																											$.ajax({
																												url : "./settingsok.do",
																												cache : false,
																												type : "post",
																												data : formData,
																												contentType : "application/x-www-form-urlencoded",
																												success:function(response){
																													alert('설정 저장에 성공했습니다.');
																													location.href="/admin/admin_siteinfo.do";
																												},error:function(xhr,status,error){
																													alert('설정 저장에 실패했습니다 :'+error);
																													location.reload();
																												}
																											});
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
					}
				}							
	});


////////로그인 ajax
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
	
	//일반 회원 계정 상태 핸들링
	//1.계정 비활성화
	$('.new_addbtn1').click(function(){
		var midx = $(this).data("midx");
		if(confirm('계정을 비활성화 하시겠습니까?')){
			$.ajax({
				url : "./member_active.do",
				type : "post",
				contentType : "application/x-www-form-urlencoded",
				data : {
					account_suspended : "Y",
					midx : midx
				},success: function(result){
					if(result=="success"){
						alert('계정이 비활성화 되었습니다.');
						location.reload();						
					}
				},error:function(){
					alert('계쩡 비활성화에 실패했습니다.')
				}
			});
		}	
	});
	//2.계정 활성화
	$('new_addbtn2').click(function(){
		var midx = $(this).data("midx"); 
		if(confirm('계정을 활성화 하시겠습니까?')){
			$.ajax({
				url : "./member_active.do",
				type : "post",
				data : {
					account_suspended : "N",
					midx : midx
				},contentType : "application/x-www-form-urlencoded",
				success: function(result){
					if(result=="success"){
						alert('계정이 활성화 되었습니다.');
						location.reload();						
					}
				},error:function(){
					alert('계쩡 비활성화에 실패했습니다.')
				}
			});
		}		
	});
	
	//이용 약관 수정 버튼
	$('#btnUpdateTerms').click(function(){
		var updateTerms = $('#temsTextArea').val();
		$.ajax({
			url : "./update_terms.do",
			type : "post",
			data : {term_content : updateTerms,
					term_type : "terms_of_service"
			},
			contentType : "application/x-www-form-urlencoded",
			success : function(result){
				if(result=="success"){
					alert('약관이 성공적으로 업데이트 되었습니다.');
					location.reload();
				}
			},error : function(){
				alert("약관 업데이트 중 오류가 발생했습니다.");
			}
		});
	});
	
	//개인정보 수집 및 이용 약관 수정 버튼
	$('#btnUpdatePrivacyPolicy').click(function(){
		var updatePrivacyPolicy = $('#privacypolicyTextArea').val();
		$.ajax({
			url : "./update_terms.do",
			type : "post",
			data : {term_content : updatePrivacyPolicy,
					term_type : "privacypolicy"
			},
			contentType : "application/x-www-form-urlencoded",
			success : function(result){
				if(result=="success"){
					alert('약관이 성공적으로 업데이트 되었습니다.');
					location.reload();
				}
			},error : function(){
				alert("약관 업데이트 중 오류가 발생했습니다.");
			}
		});		
	});
});
//관리자 등록 승인여부 등록
function agree(aidx){
	if(confirm("관리자 승인해제 하시겠습니까?")){
		location.href="./admin_userok.do?agree=N,"+aidx;
	}
}
function disagree(aidx){
		if(confirm("관리자 승인을 진행하시겠습니까?")){
		location.href="./admin_userok.do?agree=Y,"+aidx;
	}
}
