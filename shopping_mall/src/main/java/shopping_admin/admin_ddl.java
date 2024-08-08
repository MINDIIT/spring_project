package shopping_admin;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;



@Repository("admin")
public class admin_ddl extends md5_pass{
	
	@Resource(name="Template2")
	private SqlSessionTemplate tm2;
	

	

	
	
	//공지사항 게시글 등록
	public String  notice_insert(notice_dao dao, List<MultipartFile> files) {
		String callback = "";
		int result = tm2.insert("shopping.notice_insert",dao);
		notice_attachments_dao attch_dao = new notice_attachments_dao();
		int result2 = 0;
			for(MultipartFile file :files) {
				if(!file.isEmpty()) {
					String filename = file.getOriginalFilename();
					String filepath = "/upload/"+filename;
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
	
	//상품 리스트 갯수 
	public int product_list_ea(String admin_id,String search_part,String search_word) {
		Map< String, String> data = new HashMap<String, String>();
		data.put("admin_id", admin_id);
		data.put("search_part", search_part);
		data.put("search_word", search_word);
		
		int result=tm2.selectOne("shopping.product_count",data);
		return result;
	}
	//상품 삭제
	public int product_delete(String pidx) {
		String[] pidxdata = pidx.split(",");
		List<String> pidx_data = Arrays.asList(pidxdata);
		int result = tm2.delete("shopping.product_delete",pidx_data);
		return result;
	}
	
	//상품 등록
	public int product_insert(products_dao dao, HttpServletRequest req) {
		fileok(dao.getMain_product_image1_path(), dao , "main_product_image1" ,req);
		fileok(dao.getMain_product_image2_path(), dao , "main_product_image2",req);
		fileok(dao.getMain_product_image3_path(), dao , "main_product_image3",req);
		int result = tm2.insert("shopping.product_insert",dao);
		return result;
	}
	
	
	//상품 등록 시 이미지 파일 저장 경로 설정
	public void fileok(MultipartFile file, products_dao dao, String fieldname,HttpServletRequest req) {
		if(file!=null && !file.isEmpty()) {
			try {
				//웹 디렉토리 저장
				String url = req.getServletContext().getRealPath("/upload/");
				file.transferTo(new File(url+file.getOriginalFilename()));
				
				//db에 경로 저장
				String uploadurl = "./upload/"+file.getOriginalFilename();
				
				switch (fieldname) {
				case "main_product_image1":
					dao.setMain_product_image1(uploadurl);
					break;
				case "main_product_image2":
					dao.setMain_product_image2(uploadurl);
					break;
				case "main_product_image3":
					dao.setMain_product_image3(uploadurl);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			switch (fieldname) {
			case "main_product_image1":
				dao.setMain_product_image1(null);
				break;
			case "main_product_image2":
				dao.setMain_product_image2(null);
				break;
			case "main_product_image3":
				dao.setMain_product_image3(null);
				break;
			}
		}
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
	
	//공지사항 리스트 출력
	public List<notice_dao> notice_list(String admin_id,Integer startpg, Integer pageno){
		
		List<notice_dao> result = new ArrayList<notice_dao>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("admin_id", admin_id);
		data.put("startpg", startpg);
		data.put("pageno", pageno);
		result = tm2.selectList("shopping.notice_list",data);
		
		return result;
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
	
	//홈페이지 기본설정 등록
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
	
	//관리자 승인 대기 리스트 출력
	public List<admin_dao> alldata(){
		List<admin_dao> all = new ArrayList<admin_dao>();
		all = tm2.selectList("shopping.standby_list");
		return all;
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
			idck = "no";
		}else {
			idck="yes";
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
