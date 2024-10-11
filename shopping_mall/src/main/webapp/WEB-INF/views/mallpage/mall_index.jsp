<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="cr" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <title>Shop Bag</title>
    <meta charset="utf-8" />
    <link href="../mallpage/css/index.css" rel="stylesheet" />
    <link href="../mallpage/css/menu.css?v=1" rel="stylesheet"/>
   <style>
   .hero-header {
    height: 450px;
    background-image: url(./images/hero_header.jpg);
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center center;
}
   </style> 
  </head>

  <body>
<%@ include file="../mallpage/mall_header.jsp" %>
<nav>
  <div class="menu-list">
    <ul>
	  <cr:forEach var="data" items="${cate_info}">
      	<li>${data.main_menu_name}</li>
      </cr:forEach>
    </ul>
  </div>
</nav>
 <main>
    <div class="hero-header"></div>  
    <div class="products">
      <h3>NEW PRODUCTS</h3>
      <div class="product-list">
      <cr:forEach var="product" items="${product_list}">
        <a href="#" class="product">
          <img src="./product/sunglasses.png" width="225">
          <div class="product-name">
            sunglasses
          </div>
          <div class="product-price">49,000</div>
        </a>
        </cr:forEach>
        <div class="clearfix"></div>
      </div>
    </div>
</main>
<%@ include file="../mallpage/mall_footer.jsp" %>
  </body>
</html>