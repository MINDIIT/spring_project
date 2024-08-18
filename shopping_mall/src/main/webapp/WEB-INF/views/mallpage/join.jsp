<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	Date today = new Date();
%>         
<!DOCTYPE html>
<html lang="ko">
  <head>
    <title>Shop Bag</title>
    <meta charset="utf-8" />
    <link href="../mallpage/css/index.css" rel="stylesheet" />
    <link href="../mallpage/css/subpage.css" rel="stylesheet" />
    <link href="../mallpage/css/agree.css?v=1" rel="stylesheet" />
    <link href="../mallpage/css/join.css?v=1" rel="stylesheet" />
    <script src="../mallpage/js/jquery.js?v=1"></script>
	<script src="../mallpage/js/join.js?v=<%=today%>"></script>
	<style>
		#resendBtn{
			display : none;
		}
		#timer {
		    margin-top: 10px; /* 간격 설정 */
		    color: red; /* 텍스트 색상 설정 */
		}		
	</style>
  </head>

  <body>
<%@ include file="/mallpage/mall_header.jsp" %>
 <main>
    <div class="products">
    <h3>MEMBER_JOIN</h3>
    <div class="sub_view">
   
    <div class="joinview">     
    <form id="frm_member_join" method="post" action="./member_joinok.do">
    <h3>회원가입</h3>
	<span class="join_info">
    <ol>
    <li>기본정보 </li>
    <li>※ 표시는 반드시 입력하셔야 하는 항목 입니다.</li>
    </ol>
    </span>
    <ul class="join_ul">
    <li>※ 아이디</li>
    <li>
    <input type="text" class="join_in1" id="mid" name="mid"> <input type="button" value="중복확인" id="id_duplicate_check" class="join_btn1">
    </li>
    <li>※ 비밀번호</li>
    <li>
    <input type="password" class="join_in1 join_in2" id="mpass" name="mpass"> ※ 최소 6자 이상 입력하셔야 합니다.
    </li>
    <li>※ 비밀번호 확인</li>
    <li>
    <input type="password" class="join_in1 join_in2" id="mpass_ck"> ※ 동일한 패스워드를 입력하세요.
    </li>
    <li>※ 이름</li>
    <li>
    <input type="password" class="join_in1" id="mname" name="mname">
    </li>
    <li>※ 이메일</li>
    <li>
    <input type="text" class="join_in1" id="memail" name="memail"> <input type="button" value="인증 메일 발송" id="email_verification" class="join_btn1"><input type="button" value="인증 메일 재발송" id="resendBtn" class="join_btn1"> ※ 입력하신 이메일을 확인해 주세요. <strong id="timer"></strong>
    </li>
    <li>※ 인증번호</li>
    <li>
    <input type="text" class="join_in1 join_in3" id="verification_code" maxlength="8"> <input type="button" value="이메일 인증확인" id="email_verifyck" class="join_btn1"> ※ 8자리 인증번호를 입력하세요. <strong id="verification_result"></strong>
    </li>
    <li>※ 전화번호</li>
    <li>
    <input type="text" class="join_in1 join_in2" id="mhp" name="mhp" maxlength="11"> ※ 숫자만 입력하세요
    </li>
    <li>※ 이벤트 메일 수신</li>
    <li>
    <label><input type="checkbox" class="join_ck1" id="email_ad" name="email_ad"> 정보/이벤트 메일 수신에 동의합니다.</label>
    </li>
    <li>※ 이벤트 SMS 수신</li>
    <li>
    <label><input type="checkbox" class="join_ck1" id="sms_ad" name="sms_ad"> 정보/이벤트 SMS 수신에 동의합니다.</label>
    </li>
    </ul>
    <div class="btn_center_box">
    <button type="button" id="btnNextStep" class="btn_join">회원가입</button>
    </div>
	</form>
    </div>
    </div>
    </div>
</main>
<%@ include file="/mallpage/mall_footer.jsp" %>
  </body>
</html>