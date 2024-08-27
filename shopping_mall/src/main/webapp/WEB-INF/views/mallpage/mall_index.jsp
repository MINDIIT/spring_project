<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
      <li>대메뉴1</li>
      <li>대메뉴2</li>
      <li>대메뉴3</li>
      <li>대메뉴4</li>
      <li>대메뉴5</li>
      <li>대메뉴6</li>
      <li>대메뉴7</li>
    </ul>
  </div>
</nav>
 <main>
    <div class="hero-header"></div>  
    <div class="products">
      <h3>NEW PRODUCTS</h3>
      <div class="product-list">
        <a href="#" class="product">
          <img src="./product/sunglasses.png" width="225">
          <div class="product-name">
            sunglasses
          </div>
          <div class="product-price">49,000</div>
        </a>
        
        <a href="#" class="product">
          <img src="./product/tassel_loafer.png" width="225">
          <div class="product-name">
            tassel_loafer
          </div>
          <div class="product-price">89,000</div>
        </a>
        
        <a href="#" class="product">
          <img src="./product/beige_bag.png" width="225">
          <div class="product-name">
            beige_bag
          </div>
          <div class="product-price">69,000</div>
        </a>
        
        <a href="#" class="product">
          <img src="./product/sneakers.png" width="225">
          <div class="product-name">
            sneakers
          </div>
          <div class="product-price">49,000</div>
        </a>
        
        <a href="#" class="product">
          <img src="./product/slippers.png" width="225">
          <div class="product-name">
            slippers
          </div>
          <div class="product-price">49,000</div>
        </a>
        
        <a href="#" class="product">
          <img src="./product/wrist_watch.png" width="225">
          <div class="product-name">
            wrist_watch
          </div>
          <div class="product-price">49,000</div>
        </a>
        
        <a href="#" class="product">
          <img src="./product/fedora_hat.png" width="225">
          <div class="product-name">
            fedora_hat
          </div>
          <div class="product-price">49,000</div>
        </a>
        
        <a href="#" class="product">
          <img src="./product/classic_loafer.png" width="225">
          <div class="product-name">
            classic_loafer
          </div>
          <div class="product-price">49,000</div>
        </a>
        
        <a href="#" class="product">
          <img src="./product/pink_bag.png" width="225">
          <div class="product-name">
            pink_bag
          </div>
          <div class="product-price">49,000</div>
        </a>
        <div class="clearfix"></div>
      </div>
    </div>
</main>
<%@ include file="../mallpage/mall_footer.jsp" %>
  </body>
</html>