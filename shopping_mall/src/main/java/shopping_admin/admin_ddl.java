package shopping_admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.DocFlavor.STRING;

import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.mindiitshop.www.md5_pass;

@Repository("admin")
public class admin_ddl extends md5_pass{
	
	@Resource(name="template2")
	private SqlSessionTemplate tm2;
	
	//관리자 등록 승인 여부 핸들링
	public int user_agree(String data) {
		Map<String, String> ag = new HashMap<String, String>();
		ag.put("aidx",data.split(",")[0]);
		ag.put("admin_cofirm",data.split(",")[1]);
		int result = tm2.update("Shopping_mall.agree",ag);
		return result;
	}
	
	//관리자 승인 대기 리스트 출력
	public List<admin_dao> alldata(){
		List<admin_dao> all = new ArrayList<admin_dao>();
		all = tm2.selectList("Shopping_mall.standby_list");
		return all;
	}
	
	//아이디 중복 체크
	public String idcheck(String admin_id) {
		String idck = null;
		int result = tm2.selectOne("Shopping_mall.idcheck",admin_id);
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
		admin_dao dao = tm2.selectOne("Shopping_mall.login",data);
		return dao;
	}
	
	//관리자 등록
	public int admin_add (admin_dao dao) {
		dao.setAdmin_pass(this.md5_making(dao.getAdmin_pass()));
		dao.setAdmin_hp(dao.getAdmin_hp().replace(",", ""));
		int result = tm2.insert("Shopping_mall.admin_standbyadd",dao);
		return result;
	}
	
}
