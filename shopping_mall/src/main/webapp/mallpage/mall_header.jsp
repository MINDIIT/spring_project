<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>     
    <div class="navbar">
      <a href="#" id="logo">
        <img src="../mallpage/images/logo.jpg" width="149">
        </a>
        <ul id="menu">
        	<cr:if test="${mid==null}">
          		<li><a href="./login.jsp">LOGIN</a></li>
          	</cr:if>
          	<cr:if test="${!empty mid}">
          		<li><a href="#">MEMBER SHIP</a></li>
          		<li><a href="#">CART</a></li>
          		<li><a href="#">CUSTOMER CENTER</a></li>
          	</cr:if>
        </ul>
    </div>