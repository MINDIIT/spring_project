<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
    	HttpSession hs = request.getSession();
    	String admin_name = (String) hs.getAttribute("admin_name");
    	String admin_id = (String) hs.getAttribute("admin_id");
    %>
<!DOCTYPE html>
<html>
<header class="headercss">
    <div class="header_div">
        <p><img src="./img/logo.png" class="logo_sm"> ADMINISTRATOR</p>
        <%if(admin_name!=null){ %>
        <p><%=admin_name%> 관리자 <a href="#">[개인정보 수정]</a> <a href="./admin_logout.do">[로그아웃]</a></p>
        <%}else{ 
        	out.print("<script>alert('로그인이 필요합니다.');location.href='./index.jsp';</script>");	
        }%>
    </div>
</header>
<nav class="navcss">
    <div class="nav_div">
        <ol>
        	<%if(admin_name!=null && admin_name.equals("최고관리자")){ %>
            <li title="쇼핑몰 상품관리">쇼핑몰 관리자 리스트</li>
            <%} %>
            <li title="쇼핑몰 회원관리"><a href="/admin/shop_member_list.do">쇼핑몰 회원관리</a></li>
            <li title="쇼핑몰 상품관리"><a href="/admin/product_list.do">쇼핑몰 상품관리</a></li>
            <li title="쇼핑몰 기본설정"><a href="/admin/admin_siteinfo.do">쇼핑몰 기본설정</a></li>
            <li title="쇼핑몰 공지사항"><a href="/admin/notice_list.do">쇼핑몰 공지사항</a></li>
        </ol>
    </div>
</nav>
</html>