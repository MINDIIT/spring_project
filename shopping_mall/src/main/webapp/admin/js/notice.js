$(function(){
	$('#notice_write_btn').click(function(){
		location.href='./notice_write.do';
	});
	
	
	
	//공지사항 최상단 노출 여부 체크박스
	$('#is_pinned').change(function(){
		if($(this).is(':checked')){
			$(this).val('Y');
		}else{
			$(this).val('N');
		}
		console.log($(this).val());
	});
	
	//공지사항 등록 버튼 핸들링
	$('#notice_insert_btn').click(function(){
		var content = CKEDITOR.instances.notice_content.getData().trim();
		
		if(!$('#is_pinned').is(':checked')){
			$('#is_pinned').prop('checked', true).val('N');
		}
		if($('#notice_title').val()==""){
			alert('공지사항 제목을 입력해주세요');
			$('#notice_title').focus();
			return false;
		}else if(content==""){
			alert('공지내용을 입력해주세요');
			CKEDITOR.instances.notice_content.focus();
			return false;
		}else{
			$('#frm_notice').submit();			
		}
	});
	
});