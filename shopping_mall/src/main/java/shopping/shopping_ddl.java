package shopping;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


@Repository("mall")
public class shopping_ddl extends md5_pass{
	
	PrintWriter pw = null;
	
	@Resource(name="TemplateMall")
	private SqlSessionTemplate tm3;
	
	//로그인 페이지 - footer 회사 정보 출력
	public List<company_info_dao> Company_info() {
		List<company_info_dao> data = tm3.selectList("mall.footer_banner");
		return data;
	}
	
	//로그인 페이지 - 로그인 핸들링 (계정 상태 구분해서 로그인 승인필요)
	public String member_login(String mid,String mpass) {
		String account_status ="";
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("mid", mid);
		data.put("mpass",this.md5_making(mpass));
		int result = tm3.selectOne("mall.member_login",data);
		if(result>0) { //아이디,패스워드 조회가 됐을 때 계정 상태여부 조회
			account_status = tm3.selectOne("mall.account_status",data);
		}
		return account_status;
	}
	
	//회원 등록
	public int member_join(member_dao dao) {
		dao.setMpass(this.md5_making(dao.getMpass()));
		if(dao.getSms_ad()==null) {
			dao.setSms_ad("N");
		}
		if(dao.getEmail_ad()==null) {
			dao.setEmail_ad("N");
		}
		int result = tm3.insert("mall.member_join",dao);
		return result;
		
	}
	
	//약관 동의페이지 - 약관 출력
	public List<terms_dao> get_terms() {
		List<terms_dao> result = tm3.selectList("mall.get_terms");
		return result; 
	}	
	
	//이메일 인증
	public String email_verify(String memail,String number) {
		String result ="";
		
		String fromemail = "leillian8318@naver.com";
		String ename = "MINDIIT";
		String subject ="이메일 인증";
		String mailtext ="인증번호는 "+number+" 입니다.";
		
		String host = "smtp.naver.com";
		String login_user = "leillian8318";
		String login_pass ="!Leillian0817";
		String to_mail = memail;
		
		Properties pp = new Properties();
		pp.put("mail.smtp.host", host);
		pp.put("mail.smtp.port", 587);
		pp.put("mail.smtp.auth", "true");
		pp.put("mail.smtp.debug", "true");
		pp.put("mail.smtp.socketFactory.port", 587);
		
		pp.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session ss = Session.getDefaultInstance(pp, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(login_user, login_pass);
			}
		});
		try {
			MimeMessage msg = new MimeMessage(ss);
			msg.setFrom(new InternetAddress(fromemail,ename));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_mail));
			msg.setSubject(subject);
			msg.setText(mailtext);
			Transport.send(msg);
			result ="success";
		} catch (Exception e) {
			e.printStackTrace();
			result ="error";
		}
		return result;
	}
	
	//아이디 중복확인
	public String duplicate_idcheck(String mid) {
		String result ="";
		int duplicate_ck = tm3.selectOne("mall.duplicate_idcheck",mid);
		if(duplicate_ck==1) {
			result = "duplicate";
		}else {
			result = "usable";
		}
		return result;
	}
}
