package shopping_admin;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class admin_controller {
	
	PrintWriter pw = null;
	
	@Resource(name="admin")
	private admin_ddl ad;
	
	@CrossOrigin ( origins="*", allowedHeaders ="*" )
	@PostMapping("/admin/admin_login.do")
	public String admin_login(@RequestBody String formData,HttpServletResponse res)throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			int result = ad.login(formData);
			if(result==1) {
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('데이터 오류로 인해 로그인되지않았습니다.');"
					+ "location.href='./index.jsp';"
					+ "</script>");
		}finally {
			
		}
		return null;
	}
	
	
	@PostMapping("/admin/admin_add.do")
	public String admin_add(@ModelAttribute("admin")admin_dao dao) {
		System.out.println(dao);
		
		return null;
	}
}
