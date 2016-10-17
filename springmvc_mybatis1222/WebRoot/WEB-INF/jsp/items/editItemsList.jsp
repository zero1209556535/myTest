<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>查询商品列表</title>
		<script type="text/javascript">
			function add(){
				window.location.href="${pageContext.request.contextPath }/items/insertItems.action";
			}
			
			function deleteItems(id){
				if(confirm("您确认要删除吗？")){ 
					window.location.href="${pageContext.request.contextPath }/items/deleteItems.action?id="+id;
				}
			}
			
			/* function deleteItemsPL2(){
				if(confirm("您确认要批量删除吗？")){ 
					window.location.href="${pageContext.request.contextPath }/items/deleteItemsPL.action";
				}
			} */
			
			function deleteItemsPL(){
				if(confirm("您确认要批量删除吗？")){ 
					//通过POST请求方式的submit提交Form表单来传递数据，也就是请求参数（利用GET请求方式的URL来传递参数这里行不通）
					document.itemsForm.action="${pageContext.request.contextPath }/items/deleteItemsPL.action";
					document.itemsForm.submit();
				}
			}
			
			function editItemsPLSubmit(){
				if(confirm("您确认要批量修改吗？")){ 
					//通过POST请求方式的submit提交Form表单来传递数据，也就是请求参数（利用GET请求方式的URL来传递参数这里行不通）
					document.itemsForm.action="${pageContext.request.contextPath }/items/editItemsPLSubmit.action";
					document.itemsForm.submit();
				}
			}
			
		</script>
	</head>
	<body>
		<!-- ${pageContext.request.contextPath }代表的是http://localhost:8080/springmvc_mybatis1222 -->
		<form name="itemsForm" action="${pageContext.request.contextPath }/items/editItemsPL.action" method="post">
			查询条件：
			<table width="100%" border="1">
				<tr>
					<td>
						<!-- 这里的input标签不需要设置value属性的值，因为是添加数据，不是用来展示数据 -->
						商品名称：<input name="itemsCustom.name" />
					</td>
					<td>
						<input type="submit" value="查询" />
						<input type="button" value="批量修改" onclick="editItemsPLSubmit();"/>
					</td>
				</tr>
			</table>
			商品列表：
			<table width="100%" border="1">
				<tr>
					<td width="100px;" align="center">商品名称</td>
					<td width="100px;" align="center">商品价格</td>
					<td width="100px;" align="center">生产日期</td>
					<td width="100px;" align="center">有效日期</td>
					<td align="center">商品描述</td>
				</tr>
				<c:forEach items="${itemsList }" var="item" varStatus="status">
					<tr>
						<!-- 重点： 主键id必须有值，否则没办法更新 -->
						<input type="hidden" name="itemsCustomList[${status.index }].id" value="${item.id }" />
						<td><input type="text" name="itemsCustomList[${status.index }].name" value="${item.name }" /></td>
						<td><input type="text" name="itemsCustomList[${status.index }].price" value="${item.price }" /></td>
						<td>
							<input type="text" name="itemsCustomList[${status.index }].createtime" value="<fmt:formatDate value="${item.createtime }" pattern="yyyy-MM-dd HH:mm:ss" />" />
						</td>
						<td>
							<input type="text" name="itemsCustomList[${status.index }].effectivedate" value="<fmt:formatDate value="${item.effectivedate }" pattern="yyyy-MM-dd" />" />
						</td>
						<td><input type="text" name="itemsCustomList[${status.index }].detail" value="${item.detail }" style="width:99%;"/></td>
					</tr>
				</c:forEach> 
			</table>
		</form>
	</body>
</html>