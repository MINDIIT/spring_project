package shopping_admin;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;



@Repository("admin")
public class admin_ddl extends md5_pass{
	
	@Resource(name="Template2")
	private SqlSessionTemplate tm2;
	
	//상품 수정 페이지 출력
	public List<products_dao> product_edit(String pidx){
		//pidx 로 해당 데이터 다 끌어오기
		Map<String, String> data = new HashMap<String, String>();
		data.put("pidx", pidx);
		List<products_dao> result = tm2.selectList("shopping.product_edit",data);
		return result;
	}
	//상품 수정 페이지 대메뉴 출력
	public List<cate_code_dao> cateAllData(){
		List<cate_code_dao> result = tm2.selectList("shopping.cateforProductsModify");
		return result;
	}

	//상품 리스트 갯수 
	public int product_list_ea(String admin_id,String search_part,String search_word) {
		Map< String, String> data = new HashMap<String, String>();
		System.out.println(admin_id+"1");
		System.out.println(search_part);
		data.put("admin_id", admin_id);
		data.put("search_part", search_part);
		data.put("search_word", search_word);
		
		int result=tm2.selectOne("shopping.product_count",data);
		return result;
	}
	
	//상품 기본 정보 db 저장
	public int product_insert(products_dao dao) {
		int result = tm2.insert("shopping.product_insert", dao);
		return dao.getPidx();
	}
	
	//상품 등록 시 이미지 db 저장
	public int saveProductImage(int productId, String imagePath, String imageType) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("productId", productId);
	    params.put("imagePath", imagePath);
	    params.put("imageType", imageType);
	    return tm2.insert("shopping.saveProductImage", params);	
	}
		   // 파일 업로드 메서드
	 public String uploadFile(MultipartFile file) throws IOException {
	     // 파일 저장 디렉토리 설정
	     String uploadDir = "/upload/";
	     
	     // 고유한 파일명 생성 (UUID를 사용하여 파일 이름을 유니크하게 만듭니다)
	     String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	     
	     // 저장할 파일 경로 설정
	     Path filePath = Paths.get(uploadDir + fileName);
	     System.out.println(filePath);
	     // 파일 디렉토리가 존재하지 않으면 생성
	     File directory = new File(uploadDir);
	     if (!directory.exists()) {
	         directory.mkdirs();
	     }
	
	     // 파일 저장
	     Files.write(filePath, file.getBytes());
	     
	     // 파일 경로 반환
	     return filePath.toString();
	 }
	//상품 삭제
	public int product_delete(String pidx,HttpServletRequest req) {
		String[] pidxdata = pidx.split(",");
		List<String> pidx_data = Arrays.asList(pidxdata);
		//첨부파일 경로 조회
		List<Map<String, String>> filepath = tm2.selectList("shopping.select_file_path_products",pidx_data);
		//실제 파일 삭제
		for(Map<String, String> filepaths : filepath) {
			deletefile(filepaths.get("main_product_image1"),req);
			deletefile(filepaths.get("main_product_image2"),req);
			deletefile(filepaths.get("main_product_image3"),req);
		}
		int result = tm2.delete("shopping.product_delete",pidx_data);
		return result;
	}
	
	//공지사항 삭제
	public int notice_delete(String nidx,HttpServletRequest req) {
		String callback = "";
		String[] nidxdata = nidx.split(",");
		List<String> nidx_data = Arrays.asList(nidxdata);
		//첨부파일 경로 조회
		List<Map<String, String>> filepath = tm2.selectList("shopping.select_file_path_notice",nidx_data);
		//실제 파일 삭제
		for(Map<String, String> filepaths : filepath) {
			deletefile(filepaths.get("file_name"),req);
		}
		//공지사항 삭제
		int result = tm2.delete("shopping.notice_delete",nidx_data);
		return result;
	}
	
	//파일 삭제 메소드
	private void deletefile(String filepath,HttpServletRequest req) {
		String url = req.getServletContext().getRealPath("/upload/");
		if(filepath != null && !filepath.isEmpty()) {
			File file = new File(url+filepath);
			if(file.exists()) {
				file.delete();
			}
		}
	}
	
	//공지사항 게시글 등록
	public String  notice_insert(notice_dao dao, List<MultipartFile> files,HttpServletRequest req) {
		String callback = "";
		int result = tm2.insert("shopping.notice_insert",dao);
		notice_attachments_dao attch_dao = new notice_attachments_dao();
		int result2 = 0;
			for(MultipartFile file :files) {
				if(!file.isEmpty()) {
					String url = req.getServletContext().getRealPath("/upload/");
					String filename = file.getOriginalFilename();
					String filepath = url+filename;
					File f = new File(filepath);
					try {
						file.transferTo(f);
						} catch (Exception e) {
							e.printStackTrace();
						}
					attch_dao.setNidx(dao.getNidx());
					attch_dao.setFile_name(filename);
					attch_dao.setFile_path(filepath);
					result2 = tm2.insert("shopping.notice_insert_attachments",attch_dao);
					}else {
						attch_dao.setNidx(dao.getNidx());
						attch_dao.setFile_name(null);
						attch_dao.setFile_path(null);
						result2 = tm2.insert("shopping.notice_insert_attachments",attch_dao);
					}
				}
		if(result>0 && result2>0) {
			callback = "ok";
		}else {
			callback = "no";
		}
		return callback;
	}
	
	//공지사항 리스트 갯수
	public int notice_list_count(String admin_id) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("admin_id", admin_id);
		int result = tm2.selectOne("shopping.notice_list_count",data);
		return result;
	}
	
	//공지사항 수정페이지 - 수정 버튼
	public String notice_modify(notice_dao dao,HttpServletRequest req ,
			List<String> filestodelete, List<MultipartFile> newfiles) {
		//수정 정보 업데이트
		String result ="";
		boolean fileOperationsSuccessful = true;//파일 작업 성공 여부 플래그
		try {
			//기존 파일 삭제
			if(filestodelete != null && !filestodelete.isEmpty()) {
				for(String filePath : filestodelete) {
					if(!delete_realfile(filePath)) {
						fileOperationsSuccessful = false;
						break;
					}
				}
			}
			//새로운 파일 추가
			List<notice_attachments_dao> attachments = new ArrayList<>();
			if(newfiles != null && !newfiles.isEmpty() && fileOperationsSuccessful) {
				String uploadDir = req.getServletContext().getRealPath("/upload/");
				File dir = new File(uploadDir);
				if(!dir.exists()) {
					dir.mkdirs();
				}
				for(MultipartFile file : newfiles) {
					if(!file.isEmpty()) {
						String filename = file.getOriginalFilename();
						String filepath = uploadDir + filename;
						File f = new File(filepath);
						try {
							file.transferTo(f);//실제 파일 저장
							//파일 저장 성공 시에만 첨부파일 정보 리스트에 추가
							notice_attachments_dao attachment = new notice_attachments_dao();
							attachment.setNidx(dao.getNidx());
							attachment.setFile_name(filename);
							attachment.setFile_path(filepath);
							attachments.add(attachment);
						} catch (Exception e) {
							e.printStackTrace();
							fileOperationsSuccessful = false;
							break;
						}
					}
				}
			}
			//파일 작업이 모두 성공했을 때 만 db 업데이트
			if (fileOperationsSuccessful) {
				int updateResult = tm2.update("shopping.update_notice",dao);
				int removeFileResult = 1;//삭제할 파일이 없는 경우를 위해 기본값 설정
				int saveFileResult = 1;//추가할 파일이 없는 경우를 위해 기본값 설정
				//파일 삭제 db 업데이트
				if(filestodelete != null && !filestodelete.isEmpty()) {
					removeFileResult = tm2.delete("shopping.delete_file_path",filestodelete);
				}
				//파일 추가 db 업데이트
				if(!attachments.isEmpty()) {
					saveFileResult = tm2.insert("shopping.modify_insert_attachment",attachments);
				}
				
				//db 업데이트가 모두 성공한 경우 결과 설정
				if(updateResult > 0 && removeFileResult > 0 && saveFileResult > 0) {
					result = "ok";
				}else {
					result = "no";
				}
			}else {
				result = "no";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "no";
		}
		
		return result;
	}
	

	// 실제 파일 삭제 메소드
	private boolean delete_realfile(String filePath) {
	    File file = new File(filePath);
	    return file.exists() && file.delete();
	}
	
	//카테고리 등록
	public int cateinsert(Map<String, String> data) {
		int result = tm2.insert("shopping.insert_cate",data);
		return result;
	}
	
	//카테고리 삭제
	public String category_delete(List<String> cidx_data) {
		String callback ="";
		List<String> main_menu_name = tm2.selectList("shopping.find_main_menu_code", cidx_data);
		//products 테이블에 해당 분류코드 가진 상품 존재여부 확인
		int count = tm2.selectOne("shopping.find_exist_main_menu_name",main_menu_name);
		if(count>0) {//존재하면 reject 반환
			callback= "delete_reject";
		}else {
			int result = tm2.delete("shopping.category_delete",cidx_data);
			if(result>0) {
				callback = "delete_ok";
			}else {
				callback = "delete_failed";
			}
		}
		return callback;
	}	
	
	//카테고리 리스트 출력
	public List<cate_code_dao> cate_all_data(String admin_id,String search_part_category,String search_word_category,Integer startpg, Integer pageno,boolean paginate){
		List<cate_code_dao> cd = new ArrayList<cate_code_dao>();
		Map< String, Object> data = new HashMap<String, Object>();
		data.put("admin_id", admin_id);
		data.put("search_part_category", search_part_category);
		data.put("search_word_category", search_word_category);
		data.put("pageno", pageno);
		data.put("startpg", startpg);
		if(paginate) { //카테고리 리스트 페이지의 페이징처리 + 검색 기능 된 리스트 출력
			cd = tm2.selectList("shopping.category_list_paginated",data);
		}else { //상품 등록 페이지 카테고리 선택을 위한 전체 출력
			cd = tm2.selectList("shopping.cate_all_data",data);
		}
		return cd;
	}
	
	//상품 리스트 출력 페이지
	public List<products_dao> product_list(String admin_id,String search_part,String search_word,Integer startpg, Integer pageno) {
		List<products_dao> pl = new ArrayList<products_dao>();
		Map< String, Object> search_data = new HashMap<String, Object>(); 
		search_data.put("admin_id", admin_id);
		search_data.put("search_part", search_part);
		search_data.put("search_word", search_word);
		search_data.put("startpg", startpg);
		search_data.put("pageno", pageno);
		
		pl =tm2.selectList("shopping.product_list",search_data);
		return pl;
	}

	
	//카테고리 데이터 갯수
	public int cate_list_page(String admin_id,String search_part_category,String search_word_category) {
		Map< String, String> data = new HashMap<String, String>();
		data.put("admin_id", admin_id);
		data.put("search_part_category", search_part_category);
		data.put("search_word_category", search_word_category);
		int result = tm2.selectOne("shopping.category_count",data);
		return result;
	}
	

	//공지사항 게시글 view page 출력
	public List<notice_dao> notice_view(String admin_id,String nidx){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("admin_id", admin_id);
		data.put("nidx", nidx);
		List<notice_dao> result = tm2.selectList("shopping.notice_view",data);
		return result;
	}
	
	//공지사항 view page 첨부파일 출력
	public List<notice_attachments_dao> notice_view_attach(String admin_id,String nidx){
		Map<String, Object> data = new HashMap<String, Object>(); 
		data.put("admin_id", admin_id);
		data.put("nidx", nidx);
		List<notice_attachments_dao> result = tm2.selectList("shopping.notice_view_attach",data);
		return result;
	}
	
	//일반 회원 리스트 페이지 - 회원 리스트 출력
	public List<member_dao> member_list(Integer startpg, Integer pageno) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("startpg", startpg);
		data.put("pageno", pageno);
		List<member_dao> result = tm2.selectList("shopping.memberlist",data);
		
		return result;
	}
	
	//일반 회원 리스트 페이지 - 총 회원 수 출력
	public int member_list_count() {
		int ctn = tm2.selectOne("shopping.member_list_count");
		return ctn;
	}
	
	//일반 회원 리스트 페이지 - 약관 출력
	public List<terms_dao> get_terms() {
		List<terms_dao> result = tm2.selectList("shopping.get_terms");
		return result; 
	}
	
	//일반 회원 리스트 페이지 - 약관 수정
	public int update_termsok(String term_type, String term_content) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("term_type", term_type);
		data.put("term_content", term_content);
		int result = tm2.update("shopping.update_terms",data);
		return result;
	}
	

	
	//공지사항 게시글 리스트 출력
	public List<notice_dao> notice_list(String admin_id,int startpg, int pageno){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("startpg", startpg);
		data.put("pageno", pageno);
		List<notice_dao> result = tm2.selectList("shopping.notice_list",data);
		return result;
	}


	
	//공지사항 게시물 조회수
	public int notice_view_count(String nidx) {
		int result = tm2.update("shopping.notice_list_count",nidx);
		return result;
	}
	
	//홈페이지 기본설정 페이지 - 기본설정 등록
	public int settings(Map<String, String> data) {
		int result1 = tm2.insert("shopping.insert_websiteinfo",data);
		  Object sidxObj = data.get("sidx");
	        String sidxStr = null;
	        if (sidxObj instanceof BigInteger) {
	            sidxStr = ((BigInteger) sidxObj).toString();
	        } else if (sidxObj instanceof Number) {
	            sidxStr = String.valueOf(((Number) sidxObj).longValue());
	        } else {
	            throw new IllegalStateException("Unexpected type for sidx: " + sidxObj.getClass());
	        }

	        // sidx 값을 문자열로 변환하여 데이터 맵에 추가
	        data.put("sidx", sidxStr);		
	    int result2 = tm2.insert("shopping.insert_companyinfo",data);
		int result3 = tm2.insert("shopping.insert_payment_delivery_settings",data);
		int result = result1+result2+result3;
		return result;
	}
	
	//홈페이지 기본설정 페이지 - 페이지 출력
	public List<website_settings_dao> website_setting_data (){
		List<website_settings_dao> data = tm2.selectList("shopping.get_websiteinfo");
		return data;
	}
	
	public List<company_info_dao> company_info_data(){
		List<company_info_dao> data = tm2.selectList("shopping.get_companyinfo");
		return data;
	}
	
	public List<payment_delivery_settings_dao> payment_delivery_info(){
		List<payment_delivery_settings_dao> data = tm2.selectList("shopping.get_payment_deliveryinfo");
		return data;
	}
	
	
	//일반 회원 계정 활성화/비활성화 핸들링
	public int member_account(String account_suspended,String midx) {
		Map<String, Object> data = new HashMap<String, Object>(); 
		data.put("account_suspended", account_suspended);
		data.put("midx",midx);
		int result = tm2.update("shopping.member_account",data);
		return result;
	}
	
	//관리자 등록 승인 여부 핸들링
	public int user_agree(String data) {
		Map<String, String> ag = new HashMap<String, String>();		
		if(data.split(",")[0].equals("Y")) {
			ag.put("admin_confirm","Y");
			
		}else if(data.split(",")[0].equals("N")){
			ag.put("admin_confirm","N");			
		}
		ag.put("aidx",data.split(",")[1]);
		int result = tm2.update("shopping.agree",ag);
		return result;
	}
	
	//관리자 리스트 출력
	public List<admin_dao> alldata(){
		List<admin_dao> all = new ArrayList<admin_dao>();
		all = tm2.selectList("shopping.standby_list");
		return all;
	}
	
	//관리자 리스트 수 출력
	public int admin_count() {
		int result = tm2.selectOne("shopping.admin_list_count");
		return result;
	}
	
	//상품코드 중복 체크
	public String product_code(String code) {
		String codeck = null;
		int result = tm2.selectOne("shopping.product_code_ck",code);
		if(result==0) { //중복되는 상품코드가 없음
			codeck = "no";
		}else { //중복되는 상품코드가 존재
			codeck= "yes";
		}
		return codeck;
	}
	
	//아이디 중복 체크
	public String idcheck(String admin_id) {
		String idck = null;
		int result = tm2.selectOne("shopping.idcheck",admin_id);
		if(result>0) {
			idck = "no";//중복됨
		}else {
			idck="yes";//사용가능
		}
		return idck;
	}
	
	//로그인
	public admin_dao login(String formData) {
		String[] arr = formData.split("&");
		String admin_id = arr[0].split("=")[1];
		String admin_pass = this.md5_making(arr[1].split("=")[1]);
		Map<String, String> data = new  HashMap<String, String>();
		data.put("admin_id", admin_id);
		data.put("admin_pass", admin_pass);
		admin_dao dao = tm2.selectOne("shopping.login",data);
		return dao;
	}
	
	//관리자 등록
	public int admin_add (admin_dao dao) {
		dao.setAdmin_pass(this.md5_making(dao.getAdmin_pass()));
		dao.setAdmin_hp(dao.getAdmin_hp().replace(",", ""));
		int result = tm2.insert("shopping.admin_standbyadd",dao);
		return result;
	}

}
