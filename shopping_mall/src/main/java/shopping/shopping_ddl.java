package shopping;

import java.io.PrintWriter;
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
public class shopping_ddl {
	
	PrintWriter pw = null;
	
	@Resource(name="TemplateMall")
	private SqlSessionTemplate tm3;
	
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
