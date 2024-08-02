package shopping_admin;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class admin_controller {
	
	PrintWriter pw = null;
	
	@Resource(name="admin")
	private admin_ddl ad;
	
	
	//일반회원 리스트 페이지
	@GetMapping("/admin/shop_member_list.do")
	public String shop_member_list() {
		return "shop_member_list";
	}
	
	//카테고리 등록 페이지
	@GetMapping("/admin/cate_write.do")
	public String cate_write(HttpServletRequest req,Model m) {
		HttpSession hs =req.getSession();
		String admin_id = (String)hs.getAttribute("admin_id");
		m.addAttribute("admin_id",admin_id);	
		return "cate_write";
	}
	
	//카테고리 리스트 페이지 이동
	@GetMapping("/admin/cate_list.do")
	public String cate_list(Model m,HttpServletRequest req) {
		try {
			HttpSession hs =req.getSession();
			List<cate_code_dao> result = ad.cate_all_data((String)hs.getAttribute("admin_id"));
			int ctn = ad.cate_list_page((String)hs.getAttribute("admin_id"));
			m.addAttribute("result",result);
			m.addAttribute("ctn",ctn);
		} catch (Exception e) {
			System.out.println("db오류");
		}
		return "cate_list";
	}
	
	//상품등록 페이지 이동
	@GetMapping("/admin/product_write.do")
	public String product_write(Model m,HttpServletRequest req) {
		//분류코드 리스트 출력되어야함
		try {
			HttpSession hs =req.getSession();
			List<cate_code_dao> result = ad.cate_all_data((String)hs.getAttribute("admin_id"));
			m.addAttribute("result",result);
		} catch (Exception e) {
			System.out.println("db오류");
		}
		return "product_write";
	}
	//쇼핑몰 상품관리 페이지 이동
	@GetMapping("/admin/product_list.do")
	public String product_list() {
		return "/product_list";
	}
	
	//관리자 추가 페이지 이동
	@GetMapping("/admin/add_master.do")
	public String add_master() {
		return "/add_master";
	}
	
	//웹 사이트 기본설정 페이지 이동
	@GetMapping("/admin/admin_siteinfo.do")
	public String admin_siteinfo(HttpServletRequest req,Model m) {
		HttpSession hs =req.getSession();
		String admin_id = (String)hs.getAttribute("admin_id");
		m.addAttribute("admin_id",admin_id);
		return "/admin_siteinfo";
	}
	
	
	//카테고리 등록
	@PostMapping("/admin/cateaddok.do")
	public String cateinsertok(@RequestParam(required = false) Map< String, String> data, HttpServletResponse res)throws Exception {
		res.setContentType("text/html;charset=utf-8");	
		this.pw = res.getWriter();
		try {
			int result = ad.cateinsert(data);
			if(result>0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 카테고리가 생성되었습니다.');"
						+ "location.href='./cate_list.do';"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('오류로 인해 카테고리생성에 실패했습니다.');"
					+ "location.href='./cate_list.do';"
					+ "</script>");
		}
		return null;
	}
	
	//쇼핑몰 기본설정 등록
	@PostMapping("/admin/settingsok.do")
	public String settingsok(@RequestParam(required = false) Map<String, String> formData,HttpServletResponse res) throws Exception{
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			int result = ad.settings(formData);
			if(result>2) {
				this.pw.print("<script>"
						+ "alert('정상적으로 설정이 등록되었습니다.');"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('오류로 인하여 설정을 등록에 실패했습니다.');"
					+ "</script>");
		}
		return null;
	}
	
	
	//관리자 등록 승인 핸들링
	@GetMapping("/admin/admin_userok.do")
	public String admin_userok(String agree,HttpServletResponse res)throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			int result = ad.user_agree(agree);
			if(result > 0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 변경 되었습니다.');"
						+ "location.href='./admin_list.do';"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('오류로 인해 변경하지 못했습니다.');"
					+ "history.go(-1);"
					+ "</script>");
		}finally {
			this.pw.close();
		}
		return null;
	}
	
	//관리자 등록 승인 대기 리스트 출력
	@GetMapping("/admin/admin_list.do")
	public String admin_list(Model m,HttpServletResponse res) {
		try {
			List<admin_dao> result = ad.alldata();
			m.addAttribute("result",result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return "/admin_list";
	}
	//아이디 중복 체크
	@PostMapping("/admin/idcheckok.do")
	public String idcheckok(String admin_id,HttpServletResponse res)throws Exception{
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			String result = ad.idcheck(admin_id);
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
	//로그아웃
	@GetMapping("/admin/admin_logout.do")
	public String admin_logout(HttpServletRequest req,HttpServletResponse res) throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			HttpSession hs = req.getSession();
			String admin_id = (String)hs.getAttribute("admin_name");
			if(admin_id==null) {
				this.pw.print("<script>alert('잘못된 접근 입니다.');location.href='./index.jsp';</script>");
			}
			this.pw.print("<script>alert('정상적으로 로그아웃 되셨습니다.');location.href='./index.jsp';</script>");
			hs.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.pw.close();
		return null;
	}
	
	//관리자 로그인 
	@PostMapping("/admin/admin_login.do")
	public String admin_login(@RequestBody String formData,
			HttpServletResponse res,HttpServletRequest req)throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			admin_dao data = ad.login(formData);
			if(data.getAdmin_id() != null && (data.getAdmin_confirm().equals("Y")||data.getAdmin_confirm().equals("-"))) {
				HttpSession session = req.getSession();
				session.setAttribute("admin_name", data.getAdmin_name());
				session.setAttribute("admin_id", data.getAdmin_id());
				session.setMaxInactiveInterval(1800);
				this.pw.print("<script>"
						+ "alert('"+data.getAdmin_name()+"님 환영합니다.');"
						+ "location.href='./admin_main.do';"
						+ "</script>");
			}else if(data.getAdmin_id() != null && data.getAdmin_confirm().equals("N")) {
				this.pw.print("<script>"
						+ "alert('관리자 등록 승인되지 않았습니다. 승인된 후 로그인 시도하세요.');"
						+ "location.href='./index.jsp';"
						+ "</script>");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('아이디와 비밀번호를 확인하세요.');"
					+ "location.href='./index.jsp';"
					+ "</script>");
		}finally {
			this.pw.close();
		}
		return null;
	}
	
	//관리자 등록 controller
	@PostMapping("/admin/admin_add.do")
	public String admin_add(@ModelAttribute("admin")admin_dao dao,HttpServletResponse res) throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			int callback = ad.admin_add(dao);
			if(callback>0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 관리자 등록을 요청하였습니다.');"
						+ "history.go(-1);"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('DB오류로 인해 관리자 등록요청에 실패하였습니다.');"
					+ "history.go(-1);"
					+ "</script>");
		}finally {
			this.pw.close();
		}
		return null;
	}
}
