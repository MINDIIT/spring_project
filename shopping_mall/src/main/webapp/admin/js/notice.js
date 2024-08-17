let filestodelete=[];
$(function(){
	
	//notice 첨부파일 다운로드 
	$('.download-link').on('click',function(event){
		event.preventDefault();
		let filePath = $(this).data('filepath');
		filePath = filePath.replace(/\\/g, '/');
		const encodedFilePath = encodeURIComponent(filePath);
		window.location.href = `/admin/filedownload.do?filePath=${encodedFilePath}`;
	});
	
	//공지사항 view 페이지 - 공지목록으로 돌아가기 버튼
	$('#notice_list_back_viewpage').click(function(){
		location.href='./notice_list.do';	
	});
	
	//공지사항 등록 페이지 - 공지목록으로 돌아가기 버튼
	$('#notice_list_back_insertpage').click(function(){
		location.href='./notice_list.do';	
	});
	
	//공지사항 리스트 페이지 - 공지사항 등록 버튼
	$('#notice_write_btn').click(function(){
		console.log("test2");
		location.href='./notice_write.do';
	});
	
	//공지사항 view 페이지 - 공지 삭제 버튼
	$('#notice_delete_viewpage').click(function(){
		var nidx = $('#notice_delete_viewpage').val();
		if(confirm('삭제하면 복구되지않습니다. 삭제진행하시겠습니까?')){
			location.href='./notice_delete.do?nidx='+nidx;	
		}else{
			return false;
		}
	})
	
	//공지사항 view 페이지 - 공지 수정 버튼
	$('#notice_modify_btn').click(function(){
		var nidx = $('#notice_modify_btn').val();
		location.href='./notice_modify.do?nidx='+nidx;
		
	});
	
	//페이지 로드 시 체크박스 상테에 따라 hidden 필드값을 설정
    if($('#is_pinned_md').is(':checked')) {
        $('#is_pinned_hidden').val('Y');
    } else {
        $('#is_pinned_hidden').val('N');
    }	
	
	//공지사항 수정 페이지 체크박스 핸들링
	$('#is_pinned_md').change(function() {
	    if($(this).is(':checked')) {
	        $('#is_pinned_hidden').val('Y');
			console.log($('#is_pinned_hidden').val());
	    } else {
	        $('#is_pinned_hidden').val('N');
			console.log($('#is_pinned_hidden').val());
	    }
	});
	
	//공지사항 수정 페이지 첨부파일 삭제버튼 - ui에서만 삭제
	$('.remove-file').on('click',function(){
		const filePath = $(this).data('filepath');
		const filesToDeleteInput = $('#filesToDelete');
		
		//현재 삭제된 파일 경로들을 쉼표로 구분해서 히든 필드에 추가
		let currentfiles = filesToDeleteInput.val() ? filesToDeleteInput.val().split(','):[];
		currentfiles.push(filePath);
		filesToDeleteInput.val(currentfiles.join(','));
		
		$(this).parent().remove(); //ui 에서만 삭제
		console.log(filesToDeleteInput.val());
	});
	
	//공지사항 수정 페이지 수정 취소 버튼
	$('#notice_listpage_from_modifypage').click(function(e){
		e.preventDefault();
		var nidx = $('#notice_listpage_from_modifypage').val();
		location.href='./notice_view.do?nidx='+nidx;	
	});	
	
	//공지사항 수정 페이지 - 공지 수정 버튼
	$('#notice_modifyok_btn').click(function(){
		
		var content = CKEDITOR.instances.notice_content.getData().trim();
		
		if(!$('#is_pinned_md').is(':checked')){
			$('#is_pinned_md').prop('checked', true).val('N');
		}else{
			$('#is_pinned_md').prop('checked', true).val('Y');
		}
		
		if($('#notice_title_md').val()==""){
			alert('공지사항 제목을 입력해주세요');
			$('#notice_title_md').focus();
			return false;
		}else if(content==""){
			alert('공지내용을 입력해주세요');
			CKEDITOR.instances.notice_content.focus();
			return false;
		}else{
			$('#frm_notice_modify').submit();
		}
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