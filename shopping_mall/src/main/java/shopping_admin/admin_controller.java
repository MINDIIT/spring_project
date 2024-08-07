package shopping_admin;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	//공지사항 리스트 페이지로 이동
	@GetMapping("/admin/notice_list.do")
	public String notice_list () {
		
		return "notice_list";
	}
	
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
	public String cate_list(Model m,@RequestParam(defaultValue = "",required = false)String search_part_category,@RequestParam(defaultValue = "",required = false)String search_word_category,HttpServletRequest req) {

		try {
			HttpSession hs =req.getSession();
			List<cate_code_dao> result = ad.cate_all_data((String)hs.getAttribute("admin_id"),search_part_category,search_word_category);
			int ctn = ad.cate_list_page((String)hs.getAttribute("admin_id"),search_part_category,search_word_category);
			m.addAttribute("result",result);
			m.addAttribute("ctn",ctn);
			m.addAttribute("search_part_category",search_part_category);
			m.addAttribute("search_word_category",search_word_category);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cate_list";
	}
	
	//상품등록 페이지 이동
	@GetMapping("/admin/product_write.do")
	public String product_write(Model m,HttpServletRequest req,@RequestParam(defaultValue = "",required = false)String search_part_category,@RequestParam(defaultValue = "",required = false)String search_word_category) {
		//분류코드 리스트 출력되어야함
		try {
			HttpSession hs =req.getSession();
			List<cate_code_dao> result = ad.cate_all_data((String)hs.getAttribute("admin_id"),search_part_category,search_word_category);
			m.addAttribute("result",result);
			m.addAttribute("classification_code",result.get(0).getClassification_code());
			
		} catch (Exception e) {
			System.out.println("db오류-1");
		}
		return "product_write";
	}
	
	//상품 리스트 출력 페이지
	@GetMapping("/admin/product_list.do")
	public String product_list(@RequestParam(value = "",required = false)Integer page ,Model m,@RequestParam(defaultValue = "",required = false)String search_part,@RequestParam(defaultValue = "",required = false)String search_word,HttpServletRequest req) {
		HttpSession hs = req.getSession();
		int pageno = 5;//한페이지 당 5개 씩
		int startpg = 0;
		try {
			//페이징
			if(page==null||page==1) {
				startpg=0;
			}else {
				startpg = (page-1)*pageno;
			}
			
			//검색 기능
			List<products_dao> result = ad.product_list((String)hs.getAttribute("admin_id"),search_part,search_word,pageno,startpg);
				int ctn = ad.product_list_ea((String)hs.getAttribute("admin_id"),search_part,search_word);			
				
				m.addAttribute("result",result);
				m.addAttribute("ctn",ctn);
				m.addAttribute("search_part",search_part);
				m.addAttribute("search_word",search_word);
				m.addAttribute("startpg",startpg);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	//상품 삭제
	@GetMapping("/admin/product_delete.do")
	public String product_deleteok(String pidx,HttpServletResponse res)throws Exception {
		res.setContentType("text/html;charset=utf-8");	
		this.pw = res.getWriter();
		try {
			int result = ad.product_delete(pidx);
			if(result>0) {
				this.pw.write("<script>"
						+ "alert('정상적으로 상품이 삭제되었습니다.');"
						+ "location.href='./product_list.do';"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.write("<script>"
					+ "alert('오류로 인해 요청하신 작업을 수행하지못했습니다.');"
					+ "location.href='./product_list.do';"
					+ "</script>");
		}
		return null;
	}
	
	//카테고리 삭제
	@PostMapping("/admin/category_delete.do")
	public String category_deleteok(@RequestParam List<String> cidx,HttpServletResponse res)throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		String result = "";
		try {
			result = ad.category_delete(cidx);
			this.pw.print(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print(result);
		}
		return null;
	}
	
	//상품 코드 중복 확인
	@PostMapping("/admin/product_codeok.do")
	public String product_codeok(String product_code,HttpServletResponse res)throws Exception {
		this.pw = res.getWriter();
		try {
			String result = ad.product_code(product_code);
			this.pw.print(result);
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('데이터 오류로 인헤 중복확인되지 않습니다.');"
					+ "history.go(-1);"
					+ "</script>");
		}finally {
			this.pw.close();
		}
		return null;
	}
	
	
	//상품 등록
	@PostMapping("/admin/product_insertok.do")
	public String product_insertok(@ModelAttribute products_dao dao, HttpServletResponse res, HttpServletRequest req)throws Exception{
		res.setContentType("text/html;charset=utf-8");	
		this.pw = res.getWriter();
		System.out.println(dao.getMain_menu_code());
		try {
			int result = ad.product_insert(dao,req);
			if(result>0) {
				this.pw.print("<script>"
						+ "alert('정상적으로 상품이 등록 되었습니다.');"
						+ "location.href='./product_list.do';"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('상품이 정상적으로 등록되지못했습니다.');"
					+ "history.go(-1);"
					+ "</script>");
		}
		return null;
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
		}finally {
			this.pw.close();
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
		}finally {
			this.pw.close();
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
