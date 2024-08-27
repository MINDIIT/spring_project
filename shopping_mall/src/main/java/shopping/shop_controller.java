package shopping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class shop_controller {
	
	PrintWriter pw = null;
	
	@Resource(name="mall")
	private shopping_ddl sd;
	
	//쇼핑몰 메인 페이지 출력
	@GetMapping("/mallpage/mall_index")
	public String mall_index(Model m) {
		List<company_info_dao> data = sd.Company_info();
		m.addAttribute("company_info",data);		
		return "./mallpage/mall_index";
	}
	
	
	//로그인 페이지 - 하단 배너 회사 정보 출력 기능
	@GetMapping("/mallpage/login.do")
	public String loginpage_footer(Model m) {
		List<company_info_dao> data = sd.Company_info();
		m.addAttribute("company_info",data);
		return "./mallpage/login";
	}
	
	//회원 가입 
	@PostMapping("/mallpage/member_joinok.do")
	public String member_join(@ModelAttribute("mall")member_dao dao,HttpServletResponse res) throws Exception {
		this.pw = res.getWriter();
		try {
			int result = sd.member_join(dao);
			if(result>0) {
				this.pw.print("<script>"
						+ "alert('회원 가입이 완료되었습니다.');"
						+ "location.href='./login.jsp';"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('오류로 인해 회원가입이 완료되지않았습니다.');"
					+ "location.href=history.go(-1);"
					+ "</script>");			
		}
		return null;
	}
	
	//약관 동의 페이지
	@GetMapping("/mallpage/agree.do")
	public String agree_page(Model m) {
		List<terms_dao> terms = sd.get_terms();
		m.addAttribute("privacypolicy",terms.get(0).getTerm_content());
		m.addAttribute("service_of_terms",terms.get(1).getTerm_content());
		List<company_info_dao> data = sd.Company_info();
		m.addAttribute("company_info",data);		
		return "./mallpage/agree";
	}
	
	//회원가입 페이지 출력
	@GetMapping("/mallpage/join.do")
	public String user_join(Model m) {
		List<company_info_dao> data = sd.Company_info();
		m.addAttribute("company_info",data);		
		return "./mallpage/join";
	}
	
	//메일 인증 코드
	private String verify_code;
	
	
	//로그인 페이지 - 로그인 핸들링
	@PostMapping("/mallpage/member_loginok.do")
	public String member_loginok(@RequestParam String mid,String mpass,HttpServletResponse res,HttpServletRequest req) throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			String result = sd.member_login(mid, mpass);
			if(result.equals("N")) {
				HttpSession hs =req.getSession();
				hs.setAttribute("mid", mid);
				this.pw.print("<script>"
						+ "alert('"+mid+"님 환영합니다.');"
								+ "location.href='./mall_index';"
						+ "</script>");
			}else {
				this.pw.print("<script>"
						+ "alert('휴면 계정입니다. 휴면상태 해제 후 로그인 하실 수 있습니다.');"
						+ "location.href='./login.do';"
						+ "</script>");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('아이디와 비밀번호를 확인하세요.');"
					+ "location.href='./login.do';"
					+ "</script>");							
		}
		return null;
	}
	
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
