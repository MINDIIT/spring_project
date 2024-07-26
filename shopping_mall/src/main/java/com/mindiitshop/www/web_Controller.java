package com.mindiitshop.www;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
public class web_Controller {
	
	PrintWriter pw = null;
	
	
	
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
	public String loginok(@RequestParam(value="",required = false) String mid, HttpSession session) {
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
