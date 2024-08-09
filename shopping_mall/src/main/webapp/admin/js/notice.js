$(function(){
	$('#notice_write_btn').click(function(){
		console.log("test2");
		location.href='./notice_write.do';
	});
	
	
	
	//공지사항 최상단 노출 여부 체크박스
	$('#is_pinned').change(function(){
		if($(this).is(':checked')){
			$(this).val('Y');
		}else{
			$(this).val('N');
		}
	});
	
	//공지사항 등록 버튼 핸들링
	$('#notice_insert_btn').click(function(){
		var content = CKEDITOR.instances.notice_content.getData().trim();
		
		if(!$('#is_pinned').is(':checked')){
			$('#is_pinned').prop('checked', true).val('N');
		}else{
			$('#is_pinned').prop('checked', true).val('Y');
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
	
	//공지사항 삭제버튼 핸들링
	$('#notice_delete_btn').click(function(){
		var select_notice=[];
		var delete_data="";
		$('input[name="notice_ck"]:checked').each(function(){
			select_notice.push($(this).val());
		});
		
		if(select_notice==""){
			alert('삭제 할 게시글을 선택하세요.');
			return false;
		}else{
			if(confirm('삭제 시 해당 데이터는 복구되지않습니다. 삭제하시겠습니까?')){
				for(var i = 0;i<select_notice.length;i++){
					delete_data =select_notice.join(',');
				}
				location.href="./notice_delete.do?nidx="+delete_data;				
			}else{
				alert('삭제가 취소되었습니다.');
			}
		}
		
	});
	
	//공지사항 전체 체크박스 핸들링
	$('#notice_all_ck').click(function(){
		var isChecked = $(this).prop('checked');
		$('input[name="notice_ck"]').prop('checked',isChecked);
	});
	
	//공지사항 개별 체크박스 핸들링
	$('input[name="notice_ck"]').click(function(){
		var total = $('input[name="notice_ck"]').length;
		var checked = $('input[name="notice_ck"]:checked').length;
		$('#notice_all_ck').prop('checked',total===checked);
	});
	
});