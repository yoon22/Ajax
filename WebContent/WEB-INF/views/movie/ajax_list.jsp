<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border=1>
		<tr>
			<th>번호</th>
			<th>영화명</th>
			<th>개봉년도</th>
			<th>제작국가</th>
			<th>제작사</th>
			<th>감독</th>
		</tr>
		<tbody id ="tbody">
		
		</tbody>
	</table>
	<c:if test="${sessionScope.user!=null }">
		<a href="/views/movie/insert">개봉 영화 등록</a>
	</c:if>
	<script>
	var xhr = new XMLHttpRequest();
	xhr.open('GET','/am/list');
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4 && xhr.status==200){
			var list = JSON.parse(xhr.response);
			var html ='';
			for(var i=0;i<list.length;i++){ 
				//var movie of list 라고 써도 됨!!아래 list[i] 를 movie[i]로 바꿔!
				html += '<tr style="cursor:pointer" onmouseover="this.style.backgroundColor=\'#F8E0F7\'"';
				html += ' onmouseout="this.style.backgroundColor=\'\'"';	
				html += ' onclick="goPage(' + list[i]['mi_num']+')">';
				html +='<td>' + list[i]['mi_num'] + '</td>' ;
				html +='<td>' + list[i]['mi_name'] + '</td>';
				html +='<td>' + list[i]['mi_year'] + '</td>';
				html +='<td>' + list[i]['mi_national'] + '</td>';
				html +='<td>' + list[i]['mi_vendor'] + '</td>';
				html +='<td>' + list[i]['mi_director'] + '</td>';
				html += '</tr>';
				}
			document.querySelector('#tbody').innerHTML = html;
		}		
	}
	xhr.send();
	function goPage(miNum){
		location.href = '/views/movie/ajax_view?miNum=' + miNum;
	}
	</script>
</body>
</html>