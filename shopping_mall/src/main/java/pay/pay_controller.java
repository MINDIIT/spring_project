package pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class pay_controller {
	
	@GetMapping("/pay/coupon_list.do")
	public String coupon_list(Model m)throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pageno = 2; //한페이지 당 2개 씩
		try {
			con = new dbinfo().info();
			String sql = "select * from coupon order by cidx desc limit ?,?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
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
			System.out.println(all);
			m.addAllAttributes(Arrays.asList(all));
			System.out.println(con);
		} catch (Exception e) {
			System.out.println("연결실패");
		}finally {
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
