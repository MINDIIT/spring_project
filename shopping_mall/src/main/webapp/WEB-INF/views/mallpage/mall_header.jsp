<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>     
    <div class="navbar">
   	<cr:choose>
   		<cr:when test="${not empty mid}">
   			<a href="./mall_index" id="logo">
   				<img src="../mallpage/images/logo.jpg" width="149">
   			</a>
   		</cr:when>
   		<cr:otherwise>
	      <a href="./login.do" id="logo">
	        <img src="../mallpage/images/logo.jpg" width="149">
	      </a>
   		
   		</cr:otherwise>
   	</cr:choose>
        <ul id="menu">
        	<cr:if test="${mid==null}">
          		<li><a href="./login.do">LOGIN</a></li>
          	</cr:if>
          	<cr:if test="${!empty mid}">
          		<li><a href="#">MEMBER SHIP</a></li>
          		<li><a href="#">CART</a></li>
          		<li><a href="#">CUSTOMER CENTER</a></li>
          	</cr:if>
        </ul>
    </div>