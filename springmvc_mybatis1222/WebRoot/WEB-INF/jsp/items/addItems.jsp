<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加商品信息</title>
	</head>
	<body>
		<form id="itemsForm" action="${pageContext.request.contextPath }/items/insertItemsSubmitCDX.action" method="post" >
			<%-- <input type="hidden" name="id" value="${itemsCustom.id }"/> --%>
			修改商品信息：
			<table width="100%" border=1>
				<tr>
					<td>
						商品名称
					</td>
					<td>
						<%-- <input type="text" name="name" value="${itemsCustom.name }" /> --%>
						<input type="text" name="name" /><font color="red">必须输入商品名称！</font>
					</td>
				</tr>
				<tr>
					<td>
						商品价格
					</td>
					<td>
						<%-- <input type="text" name="price" value="${itemsCustom.price }" /> --%>
						<input type="text" name="price" /><font color="red">必须输入商品价格！</font>
					</td>
				</tr>
				<!-- 对于Date日期类型的value，我们没办法直接使用Spring提供的参数绑定，我们必须进行自定义参数绑定 -->
				<tr>
					<td>
						商品生产日期
					</td>
					<td>
						<%-- <input type="text" name="createtime" value="<fmt:formatDate value='${itemsCustom.createtime }' pattern='yyyy-MM-dd HH:mm:ss' />" /> --%>
						<input type="text" name="createtime" />
					</td>
				</tr>
				<tr>
					<td>
						商品有效日期
					</td>
					<td>
						<%-- <input type="text" name="effectivedate" value="<fmt:formatDate value='${itemsCustom.effectivedate }' pattern='yyyy-MM-dd' />" /> --%>
						<input type="text" name="effectivedate" />
					</td>
				</tr>
				<%-- <tr>
					<td>
						商品图片
					</td>
					<td>
						<c:if test="${itemsCustom.pic!=null }">
							<img src="/pic/${itemsCustom.pic }" width=100 height=100 />
							<br/>
						</c:if>
						<input type="file" name="pictureFile"/>
					</td>
				</tr> --%>
				<tr>
					<td>
						商品简介
					</td>
					<td>
						<%-- <textarea rows="3" cols="30" name="detail" >
							${itemsCustom.detail }
						</textarea> --%>
						<!-- textarea标签必须对称写，也就是成对写 -->
						<textarea rows="3" cols="30" name="detail" ></textarea>
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