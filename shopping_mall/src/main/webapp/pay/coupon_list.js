
function ajax_data(){
	
	var http;
	http = new XMLHttpRequest();
	http.onreadystatechange = function(){
		if(http.readyState ==4 && http.status==200){
			html_code(JSON.parse(this.response));
		}
	}
	http.open("GET","./coupon_api.do",true);
	http.send();
}
ajax_data();

//페이징
var pageno = "";			//페이지 번호
var uri = window.location.search;  		//웹 url 에 있는 ? 에 있는 파라미터 값 가져오기
if(uri==""){	//최초 접속 시
	pageno = 1;
}else{			//페이지 번호를 클릭 시
	pageno = uri.split("?page=")[1];	//페이지 번호만 추출하는 코드
}
//JSON데이터를 HTML로 출력
function html_code(data){
	var datano = 4; 		//한페이지 당 4개의데이터
	//pageno : 1번 페이지 누르면 0, 2번 -4, 3번 -6이어야함
	var startpg = (pageno-1)*datano;	//데이터 배열의 시작하는 노드 번호
	console.log(startpg); 
	var endpg = datano * pageno; //데이터 배열의 끝나는 노드 번호
	
	
	var ea = data.length;	//API data 총 배열 갯수 
	var result = document.getElementById('list');
	document.getElementById('total').append(ea); // 데이터 총 갯수 출력
	var pagehtml = document.getElementById('pages');
	
	//페이징 출력
	var pgtotal =Math.ceil(ea / datano); //무조건 반올림해야하기 때문에 ceil 사용, 소수점 올림하여 페이지 번호를 생성
	for(var p=1;p<=pgtotal;p++){//반복문을 이용하여 페이지 번호를 출력하는 코드
		pagehtml.innerHTML +=`<td><a href='./coupon_list.jsp?page=`+p+`'>`+p+`</a></td>`;
	}
	
	//데이터를 출력
	data.forEach(function(a,b,c){
		if(b<endpg && b>=startpg){
		result.innerHTML+=`
		<tr>
			<td>`+(ea-b)+`</td>
			<td>`+data[b]["cpname"]+`</td>
			<td>`+data[b]["cprate"]+`</td>
			<td>`+data[b]["cpuse"]+`</td>
			<td>`+data[b]["cpdate"]+`</td>
		</tr>`
		;
		}
	});
}