package shopping_admin;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
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
	
	@Resource(name="template2")
	private SqlSessionTemplate tm2;
	
	//상품 리스트 출력 페이지
	public List<products_dao> product_list(String admin_id) {
		List<products_dao> pl = new ArrayList<products_dao>();
		if(admin_id.equals("master")) { //master 일때는 전체 관리자의 데이터 출력
			pl = tm2.selectList("shopping.product_list_master");
		}else {// 그외 관리자는 본인이 등록한 상품들만 볼 수 있도록 함
			pl = tm2.selectList("shopping.product_list_admin",admin_id);
		}
		return pl;
	}
	
	//상품 등록
	public int product_insert(products_dao dao, HttpServletRequest req) {
		System.out.println(dao.getMain_menu_code());
		fileok(dao.getMain_product_image1(), dao , "main_product_image1" ,req);
		fileok(dao.getMain_product_image2(), dao , "main_product_image2",req);
		fileok(dao.getMain_product_image3(), dao , "main_product_image3",req);
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
					dao.setMain_product_image1_path(uploadurl);
					break;
				case "main_product_image2":
					dao.setMain_product_image2_path(uploadurl);
					break;
				case "main_product_image3":
					dao.setMain_product_image3_path(uploadurl);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			switch (fieldname) {
			case "main_product_image1":
				dao.setMain_product_image1_path(null);
				break;
			case "main_product_image2":
				dao.setMain_product_image2_path(null);
				break;
			case "main_product_image3":
				dao.setMain_product_image3_path(null);
				break;
			}
		}
	}
	
	//카테고리 등록
	public int cateinsert(Map<String, String> data) {
		int result = tm2.insert("shopping.insert_cate",data);
		return result;
	}
	
	//카테고리 리스트 출력
	public List<cate_code_dao> cate_all_data(String admin_id){
		List<cate_code_dao> cd = new ArrayList<cate_code_dao>();
		if(admin_id.equals("master")) {
			cd = tm2.selectList("shopping.cate_all_list");			
		}else {
			cd = tm2.selectList("shopping.cate_list",admin_id);
		}
		return cd;
	}
	
	//카테고리 페이징
	public int cate_list_page(String admin_id) {
		int ctn = tm2.selectOne("shopping.cate_list_page",admin_id);
		return ctn;
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
	        System.out.println(sidxStr);

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
