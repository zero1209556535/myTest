<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改商品信息</title>
	</head>
	<body>
		<!-- c:if判断可以不用加 -->
		<c:if test="${allErrors!=null}">
			<c:forEach items="${allErrors }" var="error">
				<font color="red" >${error.defaultMessage }</font><br/>
			</c:forEach>
		</c:if>
		<form id="itemsForm" action="${pageContext.request.contextPath }/items/editItemsSubmitCDX.action?getQingQiu=呵呵呵呵呵" method="post" enctype="multipart/form-data" >
			<input type="hidden" name="id" value="${itemsCustom.id }"/>
			修改商品信息：
			<table width="100%" border=1>
				<tr>
					<td>
						商品名称
					</td>
					<td>
						<input type="text" name="name" value="${itemsCustom.name }" >
							<font color="red" >输入框后面的提示语</font>
						</input>
					</td>
				</tr>
				<tr>
					<td>
						商品价格
					</td>
					<td>
						<input type="text" name="price" value="${itemsCustom.price }"/>
					</td>
				</tr>
				<!-- 对于Date日期类型的value，我们没办法直接使用Spring提供的参数绑定，我们必须进行自定义参数绑定 -->
				<tr>
					<td>
						商品生产日期
					</td>
					<td>
						<input type="text" name="createtime" value="<fmt:formatDate value='${itemsCustom.createtime }' pattern='yyyy-MM-dd HH:mm:ss' />" />
					</td>
				</tr>
				<tr>
					<td>
						商品有效日期
					</td>
					<td>
						<input type="text" name="effectivedate" value="<fmt:formatDate value='${itemsCustom.effectivedate }' pattern='yyyy-MM-dd' />" />
					</td>
				</tr>
				<tr>
					<td>
						商品图片
					</td>
					<td>
						<c:if test="${itemsCustom.pic!=null }">
							<!-- src最前面是/表示这是一个绝对路径，从图片服务器上获取，我们这里是虚拟路径对应的物理路径 -->
							<!-- 我们通过虚拟目录来访问图片 -->
							<!-- img标签最好将name属性加上，也就是说name="pic"，否则没办法进行参数绑定-->
							<img src="/pic/${itemsCustom.pic }" width=100 height=100 />
							<br/>
						</c:if>
						<input type="file" name="items_pic"/>
					</td>
				</tr>
				<tr>
					<td>
						商品简介
					</td>
					<td>
						<textarea rows="3" cols="30" name="detail" >
							${itemsCustom.detail }
						</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="提交"/>
					</td>
				</tr>
				
			</table>
		
		</form>
	</body>
</html>