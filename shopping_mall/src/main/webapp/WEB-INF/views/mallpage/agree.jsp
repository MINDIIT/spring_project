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
    <script src="./js/jquery.js?v=1"></script>
	<script src="./js/mall.js?v=<%=today%>"></script>
  </head>

  <body>
<%@ include file="../mallpage/mall_header.jsp" %>
 <main>
    <div class="products">
      <h3>MEMBER_JOIN</h3>
      <div class="sub_view">
   
    <div class="joinview">     
    <form>
        <h3>약관동의</h3>
				<div class="join_agreement_cont">
					<div class="join_agreement_box">
						<div class="form_element">
							<input type="checkbox" id="allAgree_ck"/>
							<label class="check" for="allAgree" id="allAgree"> <em>ShopBag의 모든 약관을 확인하고 전체 동의합니다.</em></label>
							<span>(전체동의, 선택항목도 포함됩니다.)</span>
						</div>
					</div>
					
					<div class="join_agreement_box js_terms_view">
						<div class="form_element">
							<input type="checkbox" id="termsAgree1" name="agreementInfoFl" class="require" />
							<label class="check_s" for="termsAgree1"><strong>(필수)</strong> 이용약관</label>
							<span><a href="#" target="_blank">전체보기</a></span>
						</div>
						<div class="agreement_box">
							${service_of_terms}
						</div>
						
					</div>
					
					<div class="join_agreement_box js_terms_view">
						<div class="form_element">
							<input type="checkbox" id="termsAgree2" name="privateApprovalFl" class="require" />
							<label class="check_s" for="termsAgree2"><strong>(필수)</strong> 개인정보 수집 및 이용 </label>
							<span><a href="#" target="_blank">전체보기</a></span>
						</div>
						<div class="agreement_box">
							${privacypolicy}
						</div>
					</div>
					<div class="important_check_box">
						<strong class="important_check dn">이용약관과 개인정보 수집 및 이용에 대한 안내 모두 동의해주세요.</strong>
					</div>
				</div>
				
				<div class="btn_center_box">
					<button type="button" id="btnNextStep_agree" class="btn_join">다음단계</button>
                </div>
			</form>
        </div>
      </div>
    </div>
</main>
<%@ include file="../mallpage/mall_footer.jsp" %>
  </body>
</html>