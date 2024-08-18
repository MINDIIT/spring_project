package shopping_admin;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.cj.Session;

@Controller
public class admin_controller {
	
	PrintWriter pw = null;
	@Resource(name="admin")
	private admin_ddl ad;

	//공지사항 view page 첨부파일 다운로드
	@GetMapping("/admin/filedownload.do")
	public ResponseEntity<FileSystemResource> notice_viewpage_file_download(@RequestParam("filePath") String filePath) {
	    try {
	        // Decode the file path if necessary
	        filePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.toString());
	    } catch (UnsupportedEncodingException e) {
	        return ResponseEntity.badRequest().build();
	    }
		
		FileSystemResource resource = new FileSystemResource(filePath);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
		
		return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
	}
	
	
	//공지사항 등록 페이지 이동
	@GetMapping("/admin/notice_write.do")
	public String notice_writeok() {
		
		return "notice_write";
	}
	
	//공지사항 view 페이지
	@GetMapping("/admin/notice_view.do")
	public String notice_viewpage(String nidx,HttpSession hs,HttpServletRequest req,Model m) {
		//HttpSession hs =req.getSession();
		List<notice_dao> data = ad.notice_view((String)hs.getAttribute("admin_id"), nidx);
		List<notice_attachments_dao> data2 = ad.notice_view_attach((String)hs.getAttribute("admin_id"), nidx);
		m.addAttribute("data",data);
		m.addAttribute("data2",data2);
		
		@SuppressWarnings("unchecked")
		List<Object> viewedNotices = (List<Object>) hs.getAttribute("view_notice");
		
		if(viewedNotices==null) {
			viewedNotices = new ArrayList<>();
			hs.setAttribute("view_notice", viewedNotices);
		}
		if(!viewedNotices.contains(nidx)) {
			ad.notice_view_count(nidx);
			//세션에 현재 게시물 id 추가
			viewedNotices.add(nidx);
		}
		
		return "notice_view";
	}
	
	//공지사항 게시글 수정페이지 출력
	@GetMapping("/admin/notice_modify.do")
	public String notice_modify(@RequestParam(value="",required = false)String nidx,HttpServletRequest req,Model m)throws Exception {
			HttpSession hs =req.getSession();
			List<notice_dao> data = ad.notice_view((String)hs.getAttribute("admin_id"), nidx);
			List<notice_attachments_dao> data2 = ad.notice_view_attach((String)hs.getAttribute("admin_id"), nidx);
			m.addAttribute("data",data);
			m.addAttribute("data2",data2);

		return "/notice_modify";
	}
	
	//공지사항 게시물 등록
	@PostMapping("/admin/notice_insertok.do")
	public String notice_insertok(@ModelAttribute notice_dao dao,@RequestParam("nfile") List<MultipartFile> files,HttpServletResponse res,HttpServletRequest req)throws Exception {
		res.setContentType("text/html;charset=utf-8");	
		this.pw = res.getWriter();
		String result = "";
		result = ad.notice_insert(dao, files,req);
		try {
			if(result.equals("ok")) {
				this.pw.print("<script>"
						+ "alert('정상적으로 공지사항이 등록되었습니다.');"
						+ "location.href='./notice_list.do';"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('오류로 인해 요청하신 작업을 처리하지 못했습니다.');"
					+ "history.go(-1);"
					+ "</script>");
		}	
		return null;
	}
	
	//공지사항 수정페이지 게시글 수정 핸들링
	@PostMapping("/admin/notice_modifyok.do")
	public String notice_modifyok(@ModelAttribute notice_dao dao,
			@RequestParam("nfile") List<MultipartFile> files,
			@RequestParam("filesToDelete") List<String> filestodelete ,HttpServletResponse res,HttpServletRequest req)throws Exception {
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		String result = "";
		try {
			result = ad.notice_modify(dao, req,filestodelete,files);
			if(result.equals("ok")) {
				this.pw.print("<script>"
						+ "alert('정상적으로 공지사항이 수정되었습니다.');"
						+ "location.href='./notice_modify.do';"
						+ "</script>");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('오류가 발생하여 수정하지 못했습니다.');"
					+ "location.href=history.go(-1);"
					+ "</script>");	
		}
		return null;
	}
	
	//카테고리 등록 페이지
	@GetMapping("/admin/cate_write.do")
	public String cate_write() {	
		return "cate_write";
	}
	
	
	//상품등록 페이지 이동
	@GetMapping("/admin/product_write.do")
	public String product_write(Model m,HttpServletRequest req,@RequestParam(defaultValue = "",required = false)String search_part_category,@RequestParam(defaultValue = "",required = false)String search_word_category) {
		try {
			HttpSession hs =req.getSession();
			List<cate_code_dao> result = ad.cate_all_data((String)hs.getAttribute("admin_id"),search_part_category,search_word_category,null,null,false);
			m.addAttribute("result",result);
			m.addAttribute("classification_code",result.get(0).getClassification_code());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("db오류-1");
		}
		return "product_write";
	}
	
	//카테고리 리스트 페이지 이동
	@GetMapping("/admin/cate_list.do")
	public String cate_list(@RequestParam(value = "",required = false)Integer page,Model m,@RequestParam(defaultValue = "",required = false)String search_part_category,@RequestParam(defaultValue = "",required = false)String search_word_category,HttpServletRequest req) {
		int pageno = 5;
		int startpg=0;
		try {
			if(page==null||page==1) {
				startpg=0;
			}else {
				startpg = (page-1)*pageno;
			}			
			HttpSession hs =req.getSession();
			List<cate_code_dao> result = ad.cate_all_data((String)hs.getAttribute("admin_id"),search_part_category,search_word_category,pageno,startpg, true);
			int ctn = ad.cate_list_page((String)hs.getAttribute("admin_id"),search_part_category,search_word_category);
			m.addAttribute("result",result);
			m.addAttribute("ctn",ctn);
			m.addAttribute("search_part_category",search_part_category);
			m.addAttribute("search_word_category",search_word_category);
			m.addAttribute("startpg",startpg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cate_list";
	}
	
	
	//공지사항 리스트 페이지로 이동
	@GetMapping("/admin/notice_list.do")
	public String notice_list (@RequestParam(value = "",required = false)Integer page,Model m,HttpServletRequest req) {
		int pageno = 15;
		int startpg=0;
			if(page==null||page==1) {
				startpg=0;
			}else {
				startpg = (page-1)*pageno;
			}			
			HttpSession hs =req.getSession();
		List<notice_dao> result = ad.notice_list((String)hs.getAttribute("admin_id"), startpg, pageno);
		int ctn = ad.notice_list_count((String)hs.getAttribute("admin_id"));
		System.out.println(result);
		m.addAttribute("ctn",ctn);
		m.addAttribute("result",result);
		m.addAttribute("startpg",startpg);
		
		return "/notice_list";
	}
	
	//일반회원 리스트 페이지
	@GetMapping("/admin/shop_member_list.do")
	public String shop_member_list(Model m,@RequestParam(value = "",required = false)Integer page) {
		
		int pageno = 15;
		int startpg = 0;
		if(page == null || page == 1) {
			startpg = 0;
		}else {
			startpg = (page - 1)* pageno;
		}
		List<member_dao> result = ad.member_list(startpg, pageno);
		int ctn = ad.member_list_count();
		m.addAttribute("result",result);
		m.addAttribute("ctn",ctn);
		m.addAttribute("startpg",startpg);
		
		return "/admin_page/shop_member_list";
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
	
	//웹 사이트 기본설정 페이지 
	@GetMapping("/admin/admin_siteinfo.do")
	public String admin_siteinfo(HttpServletRequest req,Model m) {
		List<website_settings_dao> sitedata = ad.website_setting_data();
		List<company_info_dao> companydata = ad.company_info_data();
		List<payment_delivery_settings_dao> pddata = ad.payment_delivery_info();
		
		m.addAttribute("sitedata",sitedata);
		m.addAttribute("companydata", companydata);
		m.addAttribute("pddata",pddata);
		return "/admin_siteinfo";
	}
	
	//상품 삭제
	@GetMapping("/admin/product_delete.do")
	public String product_deleteok(String pidx,HttpServletResponse res,HttpServletRequest req)throws Exception {
		res.setContentType("text/html;charset=utf-8");	
		this.pw = res.getWriter();
		try {
			int result = ad.product_delete(pidx,req);
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
	
	//공지사항 게시글 삭제
	@GetMapping("/admin/notice_delete.do")
	public String notice_deleteok(@RequestParam(value="",required = false)String nidx,HttpServletResponse res,HttpServletRequest req)throws Exception{
		res.setContentType("text/html;charset=utf-8");
		this.pw = res.getWriter();
		try {
			int result = ad.notice_delete(nidx, req);
			System.out.println(result);
			if(result>0) {
				this.pw.write("<script>"
						+ "alert('게시글이 정상적으로 삭제되었습니다.');"
						+ "location.href='./notice_list.do';"
						+ "</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.write("<script>"
					+ "alert('오류로 인해 요청하신 작업을 수행하지못했습니다.');"
					+ "location.href='./notice_list.do';"
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
	
	//이용약관 수정 핸들링
	@PostMapping("/admin/update_terms.do")
	public String update_terms(String term_type, String term_content) {
		
		
		return null;
	}
	
	//일반 회원 계정 정지/해제 핸들링
	@PostMapping("/admin/member_active.do")
	public String member_active(String account_suspended,String midx,HttpServletResponse res)throws Exception {
		this.pw = res.getWriter();
		int callback = ad.member_account(account_suspended, midx);
		if(callback>0) {
			this.pw.print("success");
		}else {
			this.pw.print("fail");
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
	
	//관리자 리스트 출력
	@GetMapping("/admin/admin_list.do")
	public String admin_list(Model m,HttpServletResponse res) {
		try {
			List<admin_dao> result = ad.alldata();
			int ctn = ad.admin_count();
			m.addAttribute("result",result);
			m.addAttribute("ctn",ctn);
		} catch (Exception e) {
			e.printStackTrace();
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
	
	//관리자 등록 
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
