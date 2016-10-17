<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>系统登录</title>
	</head>
	
	<body>
		<!-- Form表单默认的请求方法是GET，如果我们想使用POST请求，需要进行手动配置 -->
		<form action="${pageContext.request.contextPath }/login.action" method="post">
			用户帐号：<input type="text" name="usercode" /><br/>
			用户密码：<input type="password" name="password" /><br/>
			<input type="submit" value="登陆" />
		</form>
	</body>
</html>