<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<footer>
    <div class="footer">
      <a href="http://facebook.com">
        <img src="../mallpage/images/facebook.png"height="20">
      </a>
      <a href="http://instagram.com">
        <img src="../mallpage/images/instagram.png"height="20">
      </a>
      <a href="http://twitter.com">
        <img src="../mallpage/images/twitter.png"height="20">
      </a>
    </div>
    <section class="foot_section"></section>
    <aside class="aside_footer">
        <div class="div_footer">
        <ul>
        <li><img src="../mallpage/images/foot_logo.png"></li>
        <li>
회사명 : ${company_info[0].company_name} 
대표자 : ${company_info[0].ceo_name}
주소 : ${company_info[0].business_location_address}  <br>
고객센터 : ${company_info[0].company_phone_number}
상담시간 : 
E-Mail : 
사업자등록번호 : ${company_info[0].business_registration_number} <br>
통신판매업신고번호 : ${company_info[0].online_business_registration_number}
개인정보보호책임자 : ${company_info[0].data_protection_officer_name}   <br>
Copyright © 도메인명 All Rights Reserved.
        </li>
        </ul>    
        </div>
    </aside>
</footer>