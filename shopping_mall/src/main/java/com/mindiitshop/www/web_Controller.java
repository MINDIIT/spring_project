package com.mindiitshop.www;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class web_Controller extends md5_pass {
	
	PrintWriter pw = null;
	
	//DAO 를 사용하려면 @ModelAttribute 꼭 사용해야함 (회원가입등에서는 필수)
	//DAO없이 사용시 : 자료형 객체 OR @RequestParam을 이용해서 사용
	//아이디 찾기 등 여러개 입력값이 많이 필요하지 않을 경우 DAO 없이 진행할 수도 ㅇ
	//아이디 찾기
	@Resource(name="userselect")
	private user_select us;
	
	@PostMapping("/idsearch.do")
	public String idsearch(String[] uname, String uemail,HttpServletResponse res)throws Exception {
		res.setContentType("text/html; charset=utf-8");
		//null 일 경우 핸들링 꼭 해줘야 오류 안남
		this.pw = res.getWriter();
		try {
			//uname[0] -> 앞에 uname 이 두개임
			if(uname[0]==null||uemail==null) {
				this.pw.print("<script>"
						+ "alert('올바른 접근방식이 아닙니다.');"
						+ "location.href='./search_user.jsp';"
						+ "</script>");
			}else {
				ArrayList<Object> onedata = us.search_id(uname[0], uemail);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.pw.print("<script>"
					+ "alert('database 문제로 인해 해당 정보가 확인되지않습니다');"
					+ "location.href='./search_user.jsp';"
					+ "</script>");
		}finally {
			this.pw.close();
		}
		return null;
	}
	
	//패스워드 변경
	@PostMapping("/passmodify.do")
	public String passmodify() {
		
		
		return null;
	}
	
	//server retime 잡아줘야 anotation 뜸
	//@Resource(name="md5pass")
	//private md5_pass md;
	
	//md5 : 회원가입, 로그인, 패스워드 변경, 1:1문의, 자유게시판, 상품구매내역
	//패스워드 변경여부를 체크
	@GetMapping("/passwd.do")
	public String passwd() {
		String pwd = "a1234";
		String result = this.md5_making(pwd);
		System.out.println(result);
		return null;
	}
	
	
	
	@CrossOrigin(origins="*",allowedHeaders ="*")
	@PostMapping("/ajaxokhw.do")
	public String ajaxokhw (@RequestBody String arr , HttpServletResponse res) {
		res.setContentType("text/html;charset=utf-8");
		JSONArray ja = new JSONArray(arr);
		JSONObject jo1 = (JSONObject)ja.get(0);
		JSONObject jo2 = (JSONObject)ja.get(1);
		JSONObject jo3 = (JSONObject)ja.get(2);
		JSONArray ja1 = new JSONArray();
		ja1.put(jo1);
		ja1.put(jo2);
		ja1.put(jo3);
		
		System.out.println(ja1);
		
		
		return null;
	}
	
	
	@GetMapping("/restapi.do")
	//@SessionAttribute : session이 이미 등록되어 있는 상황일 경우 해당 정보를 가져올 수 있음
	//restapi 만들 때 필요한 형태
	public String restapi (@SessionAttribute(name="mid",required =false)String mid) 
			throws Exception {
		if(mid==null) {
			System.out.println("로그인 해야만 결제내역을 확인 하실 수 있습니다.");
		}else {
			System.out.println("결제내역은 다음과 같습니다.");
		}
		System.out.println(mid);
		return null;
	}
	
	//HttpSession : interface를 활영하여, 세션을 빠르게 구현하는 방식 스타일
	@PostMapping("/loginok.do")
	public String loginok(@RequestParam(value="",required = false) 
	String mid, HttpSession session) {
		
		if(mid!=null||mid!="") {
			session.setAttribute("mid", mid);
			session.setMaxInactiveInterval(1800);			
		}
		return null;
	}
	/*
	@PostMapping("/loginok.do")
	public String loginok(String mid,HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("mid", mid);
		session.setMaxInactiveInterval(1800);//일반 쇼핑몰 기준 세션 1800초
		System.out.println(mid);
		return null;
	}
	*/	
	@CrossOrigin(origins="*",allowedHeaders ="*")
	@PostMapping("/ajaxok3.do")
	public String ajaxok3(@RequestBody String arr, HttpServletResponse res)throws Exception{
		JSONArray jo = new JSONArray(arr);
		//System.out.println(arr);
		JSONArray ja =(JSONArray)jo.get(0);
		JSONArray ja1 =(JSONArray)jo.get(1);
		JSONArray ja2 = new JSONArray();
		ja2.put(ja);
		ja2.put(ja1);
		this.pw = res.getWriter();
		this.pw.print("ok");
		//System.out.println();
		return null;
	}
	
	@CrossOrigin(origins="*",allowedHeaders ="*")
	@PostMapping("/ajaxok2.do")
	public String ajaxok2(@RequestBody String all_data,HttpServletResponse res)throws Exception {
		JSONObject jo = new JSONObject(all_data); //{}인식 시킨 후 key값으로 배열을 체크
		//{a,b,c}
		JSONArray ja = (JSONArray)jo.get("all_data"); // -> new를 쓰면 안됨 위에서 풀어놓은걸 다시 묶어버림
		System.out.println(ja.get(0)); //데이터 출력
		
		this.pw = res.getWriter();
		//front가 dataType을 json으로 받겠다고 했기 때문에 json으로 날려줌 
		JSONObject result = new JSONObject();
		result.put("result", "ok");
		this.pw.print(result);
		return null;
	}
	
	//@ResponseBody : 미디어 타입
	//@RequestBody : GET/POST 에서는 잘 쓰지 않고, JSON으로 날아왔을 때만 사용
	//ajax 통신을 하려면 무조건 CORS 방식을 써줘야함
	@CrossOrigin(origins="*",allowedHeaders ="*")
	
	//@RequestParam : 배열을 이용하여 대표키로 전달 또는 대표키 없이 보조키로 전달 될 경우 사용가능
	@GetMapping("/ajaxok.do")
	public String ajaxok(@RequestParam(value = "alldata") List<String> alldata,HttpServletResponse res)
			throws Exception{
		
		System.out.println(alldata);
		System.out.println(alldata.get(0));
		this.pw = res.getWriter();
		JSONObject jo = new JSONObject();
		jo.put("result","ok");
		this.pw.print(jo);
		this.pw.close();
		return null;
	}
	
}
