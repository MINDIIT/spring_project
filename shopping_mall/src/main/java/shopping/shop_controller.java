package shopping;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class shop_controller {
	@Resource(name="mall")
	private shopping_ddl sd;
	
	@GetMapping("/mall/login.do")
	public String user_login() {
		
		return "/login";
	}
	
	@GetMapping("/mall/join.do")
	public String user_join() {
		
		return "/join";
	}
}
