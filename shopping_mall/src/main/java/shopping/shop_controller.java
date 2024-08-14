package shopping;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class shop_controller {
	
	PrintWriter pw = null;
	
	@Resource(name="mall")
	private shopping_ddl sd;
	
	@GetMapping("/mallpage/login.do")
	public String user_login() {
		
		return "/login";
	}
	
	//약관 동의 페이지
	@GetMapping("/mallpage/agree.do")
	public String agree_page() {
		
		return "/agree";
	}
	
	//회원가입 페이지 출력
	@GetMapping("/mallpage/join.do")
	public String user_join() {
		
		return "/join";
	}
	
	
	//회원가입 페이지 - 메일 인증
	@PostMapping("/mallpage/email_verification.do")
	public String email_verify(String memail,String number,HttpServletResponse res)throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			String result = sd.email_verify(memail, number);
			this.pw.print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			this.pw.close();
		}
		return null;
	}
	
	
	
	//회원가입 페이지 - 중복확인
	@PostMapping("/mallpage/duplicate_idcheck.do")
	public String duplicate_idcheck(String mid,HttpServletResponse res)throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			String result = sd.duplicate_idcheck(mid);
			this.pw.print(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('데이터 오류로 인해 확인이되지않습니다.');"
					+ "history.go(-1);"
					+ "</script>");			
		}finally {
			this.pw.close();
		}
		return null;
	}
	
}
