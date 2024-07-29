package shopping_admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class admin_controller {
	
	@PostMapping("/admin/admin_login.do")
	public String admin_login(String admin_id) {
		System.out.println(admin_id);
		
		return null;
	}
	
	
	@PostMapping("/admin/admin_add.do")
	public String admin_add(@ModelAttribute("admin")admin_dao dao) {
		System.out.println(dao);
		
		return null;
	}
}
