		var arr = [
				["10%","20%","30%"],
				["30","40","50"]
					];
var basket = [{"seq":1,"product":"냉장고","price":"195000"},
				{"seq":2,"product":"세탁기","price":"287000"},
				{"seq":3,"product":"에어프라이어","price":"97000"}];					
$(function(){
	
	//숙제
	$("#btn4").click(function(){
		$.ajax({
			url : "./ajaxokhw.do",
			type : "post",
			cache : false,
			dataType : "text",
			contentType : "application/json",
			data : JSON.stringify(basket),
			success:function($result){
				console.log($result);
			},error:function(){
				console.log("error");
			}
		});
	});
		
	//front 배열값 응용편
	$("#btn3").click(function(){
		$.ajax({
			url : "./ajaxok3.do",
			type : "post",
			cache : false,
			dataType : "text",
			contentType : "application/json",
			data: JSON.stringify(arr),
			success:function($result){
				console.log($result);
			},error:function(){
				console.log("error");
			}
		})
	});
	
	$("#btn2").click(function(){
		var $data = new Array();
		$data[0] = "홍길동";
		$data[1] = "강감찬";
		$data[2] = "이순신";
		$.ajax({
			url : "./ajaxok2.do",
			type : "post",
			cache : false,
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify({
				"all_data" : $data
			}),
			success:function($result){
				console.log($result);
			},error:function(){
				console.log("error");
			}
			
		});
	});
	
	
	
	$("#btn").click(function(){
		var $data = new Array();
		$data[0] = "20";
		$data[1] = "30";
		$data[2] = "40";
		//console.log($data.join(",")); //배열을 문자열로 출력함
		$.ajax({
			url : "./ajaxok.do",
			cache : false,
			dataType :"json",//받을 때 타입(printwriter로 쓸 때)
			contentType : "application/json",
			type :"get",
			data : {
				"alldata" : $data.join(",")
			},success:function($result){
				console.log($result);
			},error:function(){
				console.log("error");
			}			
		});
	});
});