<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>json交互测试</title>
		<!-- 引入js文件 -->
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript">
			/* 客户端请求json，服务器响应json */
			function requestJsonGet(){
				$.ajax({
					//一般get请求都是url方式的数据请求，可以请求key/value数据，不可以请求json数据（我不可以）。
					type:'get',//使用get请求方式，经过测试发现，get请求方式行不通，数据传不到后台Handler方法的形参中，但是通过url的get请求进行传递key/value类型的数据是可以的。      
					url:'${pageContext.request.contextPath }/requestJsonGet.action',
					contentType:'application/json;charset=utf-8',
					data:'{"name":"笔记本电脑","price":5999}',//{key:value,key:value}格式为json数据，其实就是商品信息
					success:function(data){//success是回调函数，data是响应返回的json结果
						alert(data.name);
					}
				});
			}
			/* 客户端请求json，服务器响应json */
			function requestJson(){
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath }/requestJson.action',
					contentType:'application/json;charset=utf-8',
					data:'{"name":"手机","price":2999}',//{key:value,key:value}格式为json数据，其实就是商品信息
					success:function(data){//success是回调函数，data是响应返回的json结果
						//说明：这里的参数名称必须是data
						alert(data.name);
					}
				});
			}
			/* 客户端请求key/value，服务器响应json */
			function responseJson(){
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath }/responseJson.action',
					//这里不需要指定contentType，因为默认contentType=application/x-www-form-urlencoded
					//也就是说默认就是支持key/value类型请求
					//contentType:'application/json;charset=utf-8',
					//data:'{"name":"手机","price":2999}',//{key:value,key:value}格式为json数据，其实就是商品信息
					data:'name=手机&price=2999',//key/value格式数据
					success:function(data){//success是回调函数，data是响应返回的json结果
						alert(data.price);
					}
				});
			}
		</script>
	</head>
	
	<body>
		<!-- 客户端请求json，服务器响应json ，使用get请求方式-->
		<input type="button" value="请求json，响应json，get" onclick="requestJsonGet();" />
		<!-- 客户端请求json，服务器响应json -->
		<input type="button" value="请求json，响应json" onclick="requestJson();" />
		<!-- 客户端请求key/value，服务器响应json -->
		<input type="button" value="请求key/value，响应json" onclick="responseJson();" />
	</body>
</html>