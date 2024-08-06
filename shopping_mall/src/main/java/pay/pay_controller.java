package pay;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Controller
public class pay_controller {
	
	//front가 pageing을 다 하는 형태
	//backend 는 api 만 구축을 해주는 형태
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/pay/coupon_api.do", method = RequestMethod.GET)
	public String coupon_api(HttpServletResponse res,HttpServletRequest req) throws Exception{
		
		
		String ips = req.getRemoteAddr(); //접속 도메인 및 아이피 정보를 확인
		String browser = req.getHeader("User-Agent");
		//모바일 접속시 주로 이렇게 많이 걸어둠
		if(browser.contains("Edg")) {
			System.out.println("Edge로 접속 확인");
		}else if(browser.contains("Chrome")) {
			System.out.println("chrome으로 접속 확인");
		}else if(browser.contains("Firefox")) {
			System.out.println("Firefox로 접속 확인");
		}
		System.out.println(browser);
		/*Spring Boot에서 사용하는 IP정보 형태를 출력
		HttpServletRequest req2 = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ips2 = req.getHeader("X-FORWARED-FOR");
		System.out.println(ips2);*/
		
		JSONObject jo = null;
		org.json.JSONArray ja = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PrintWriter pw = null;
		
		int startpg = 0;
		int pageno = 2; //2개씩
		pw = res.getWriter();
		try {
			con = new dbinfo().info();
			//limit로 보내면 front에서 찍기 힘들어짐 -> 그냥 desc로 데이터 전체 찍어주면 됨
			String sql = "select * from coupon order by cidx desc";
			ps = con.prepareStatement(sql);
			//ps.setInt(1, startpg);
			//ps.setInt(2, pageno);
			rs = ps.executeQuery();
			ja = new org.json.JSONArray();
			//대표키 유무는 선택사항
			while(rs.next()) {
				jo = new JSONObject();
				jo.put("cidx",rs.getString(1));
				jo.put("cpname", rs.getString(2));
				jo.put("cprate", rs.getString(3));
				jo.put("cpuse", rs.getString(4));
				jo.put("cpdate", rs.getString(5));
				ja.put(jo);
			}
			pw.print(ja);
			
		} catch (Exception e) {
			e.printStackTrace();
			pw.print("error");
		}finally {
			rs.close();
			ps.close();
			con.close();
			pw.close();
		}
		return null;
	}
	
	@GetMapping("/pay/coupon_list.do")
	public String coupon_list(@RequestParam(value = "",required = false)Integer page,Model m)throws Exception {
		//defaultValue="" , value="" 둘다 값음
		//굳이 따지자면 전자가 구식, 후자가 신식
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		int pageno = 2; //한페이지 당 2개 씩
		int startpg =0;
		try {
			if(page==null||page==1) {
				startpg = 0;
			}else {
				startpg = (page - 1)* pageno;
			}
			con = new dbinfo().info();
			//데이터 총 갯수(데이터 전체 갯수)
			String count = "select count(*) as ctn from coupon";
			ps = con.prepareStatement(count);
			rs2 = ps.executeQuery();
			rs2.next(); 
			m.addAttribute("startpg",startpg); //가공된 page번호
			m.addAttribute("total",rs2.getString("ctn")); //ctn 값 			
			String sql = "select * from coupon order by cidx desc limit ?,?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, startpg);
			ps.setInt(2, pageno);
			rs = ps.executeQuery();
			ArrayList<ArrayList<String>> all = new ArrayList<ArrayList<String>>();
			while(rs.next()) {
				ArrayList<String> al = new ArrayList<String>();
				al.add(rs.getString(1));
				al.add(rs.getString(2));
				al.add(rs.getString(3));
				al.add(rs.getString(4));
				al.add(rs.getString(5));
				all.add(al);
			}
			//addAllAttributes : object 메소드배열관련 객체를 차례대로 추가하는 방식 (${stringList})
			// addAllAttributes 단점 : 2차 배열시 jsp로 전달할 때 문제가 발생함
			System.out.println(all);
			m.addAttribute("all",all);
			System.out.println(con);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("연결실패");
		}finally {
			rs2.close();
			rs.close();
			ps.close();
			con.close();
		}
		return "/coupon_list";
	}
	@PostMapping("/pay/pay3.do")
	public String pay3(@ModelAttribute("payinfo") pay_dao dao , HttpServletRequest req)throws Exception{
		req.setAttribute("goodcode", dao.getGoodcode()); 	//상품코드
		req.setAttribute("goodname", dao.getGoodname()); 	//상품 명
		req.setAttribute("goodea", dao.getGoodea()); 		//상품갯수
		req.setAttribute("goodprice", dao.getGoodprice());	//상품 가격
		req.setAttribute("price", dao.getPrice());			//결제 금액
		req.setAttribute("buyername", dao.getBuyername());	//결제자 이름
		req.setAttribute("buyertel", dao.getBuyertel());	//결제자 연락처
		req.setAttribute("buyeremail", dao.getBuyeremail());//결제자 이메일
		req.setAttribute("rec_post", dao.getRec_post());	//결제자 우편번호
		req.setAttribute("rec_way", dao.getRec_way());		//도로명
		req.setAttribute("rec_addr",dao.getRec_addr());		//상세주소
		req.setAttribute("gopaymethod", dao.getGopaymethod());//결제방식
		return "pay3";
	}
	
	
	@PostMapping("/pay/pay2.do")
	public String pay2(@ModelAttribute("products") pay_dao dao,Model m) throws Exception {
		//allAttribute 쓰는 방법 1
		List<String> as = new ArrayList<String>();
		as.add(dao.getProduct_code());
		as.add(dao.getProduct_nm());
		as.add(dao.getProduct_money());
		as.add(dao.getProduct_ea());
		as.add(dao.getPrice());
		
		/* allAttribute 쓰는 방법 2
		Collection<String> cl = new ArrayList<String>();
		cl.add(null);
		*/
		/* allAttribute 쓰는 방법 3
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("pdcode", dao.getProduct_code());
		m.addAllAttributes(Arrays.asList(as));
		*/
		//addAllAttributes : key 가 없음 , 단 배열명 + 자료형을 기반으로 jsp 출력
		m.addAllAttributes(Arrays.asList(as)); //jsp에서 출력하느 방법 : .get(), []으로 찍으면 출력이 가능함
		return "pay2"; //WEB-INF
	}
}
