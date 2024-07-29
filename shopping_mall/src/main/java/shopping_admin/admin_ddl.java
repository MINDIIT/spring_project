package shopping_admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("admin")
public class admin_ddl {
	
	@Resource(name="template2")
	private SqlSessionTemplate tm;
	
	//로그인
	public int login(String formData) {
		JSONObject jo = new JSONObject(formData);
		String admin_id = jo.getString("admin_id");
		String admin_pass = jo.getString("admin_pass");
		Map<String, String> data = new  HashMap<String, String>();
		data.put("admin_id", admin_id);
		data.put("admin_pass", admin_pass);
		int result = tm.selectOne("Shopping_mall.login",data);
		return result;
	}
	
}
