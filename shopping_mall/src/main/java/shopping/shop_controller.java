package shopping;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class shop_controller {
	
	PrintWriter pw = null;
	
	@Resource(name="mall")
	private shopping_ddl sd;
	

	
	//약관 동의 페이지
	@GetMapping("/mallpage/agree.do")
	public String agree_page() {
		
		return "./mallpage/agree";
	}
	
	//회원가입 페이지 출력
	@GetMapping("/mallpage/join.do")
	public String user_join() {
		
		return "./mallpage/join";
	}
	
	private String verify_code;
	
	//회원가입 페이지 - 인증 메일 발송
	@PostMapping("/mallpage/email_verification.do")
	public String email_verify(String memail,String number,HttpServletResponse res,HttpServletRequest req)throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			this.verify_code = number;
			String result = sd.email_verify(memail, number);
			HttpSession hs = req.getSession();
			hs.setAttribute("verificationCode", number);
			hs.setAttribute("codeEcpiryTime", System.currentTimeMillis()+(3*60*1000));
			this.pw.print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			this.pw.close();
		}
		return null;
	}
	
	//회원 가입 페이지 - 메일 인증 확인
	@PostMapping("/mallpage/verifyCode_ok.do")
	public String verify_code_ok(String code,HttpServletResponse res)throws Exception {
		this.pw = res.getWriter();
		if(code.equals(verify_code)) {
			this.pw.print("success");
		}else {
			this.pw.print("fail");
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
