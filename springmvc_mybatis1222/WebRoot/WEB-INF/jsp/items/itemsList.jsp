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
			
			function ceshiMap(){
				window.location.href="${pageContext.request.contextPath }/items/ceshiMap.action";
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
			
		</script>
	</head>
	<body>
		当前用户：${usercode }<br/>
		<c:if test="${usercode!=null }">
			<a href="${pageContext.request.contextPath }/logout.action">退出</a>
		</c:if>
		<!-- ${pageContext.request.contextPath }代表的是http://localhost:8080/springmvc_mybatis1222 -->
		<form name="itemsForm" action="${pageContext.request.contextPath }/items/queryItems2.action" method="post">
			查询条件：
			<table width="100%" border="1">
				<tr>
					<td>
						<!-- 这里的input标签不需要设置value属性的值，因为是添加数据，不是用来展示数据 -->
						商品名称：<input name="itemsCustom.name" />
						商品类型：
						<select name="itemsType">
							<c:forEach items="${itemsTypes }" var="itemsType">
								<option value="${itemsType.key }" >${itemsType.value }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input type="submit" value="查询" />
						<input type="button" value="批量删除" onclick="deleteItemsPL();"/>
						<a href="${pageContext.request.contextPath }/items/editItemsPL.action">批量修改</a>
					</td>
				</tr>
			</table>
			商品列表：
			<table width="100%" border="1">
				<tr>
					<td width="40px;" align="center">选择</td>
					<td width="100px;" align="center">商品名称</td>
					<td width="100px;" align="center">商品价格</td>
					<td width="200px;" align="center">生产日期</td>
					<td width="200px;" align="center">有效日期</td>
					<td align="center">商品描述</td>
					<td width="150px;" align="center">操作</td>
				</tr>
				<c:forEach items="${itemsList }" var="item">
					<tr>
						<td align="center"><input type="checkbox" name="items_id" value="${item.id }" /></td>
						<td>${item.name }</td>
						<td>${item.price }</td>
						<td>
							<fmt:formatDate value="${item.createtime }" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							<fmt:formatDate value="${item.effectivedate }" pattern="yyyy-MM-dd" />
						</td>
						<td>${item.detail }</td>
						<td align="center">
							<!-- ${pageContext.request.contextPath }代表的是http://localhost:8080/springmvc_mybatis1222 -->
							<a href="${pageContext.request.contextPath }/items/editItems2.action?id=${item.id }">
								修改
							</a>
							&nbsp;&nbsp;
							<%-- <a href="${pageContext.request.contextPath }/items/deleteItems.action?id=${item.id }" onclick="return confirm('确定要删除吗?')">
								删除
							</a> --%>
							<input type="button" value="删除" onclick="deleteItems(${item.id });" /> 
						</td>
					</tr>
				</c:forEach>
			</table>
			<table width="100%" border="1">
				<tr>
					<td colspan="6" align="center">
						<input type="button" value="新增" onclick="add();" />
						<%-- <a href="${pageContext.request.contextPath }/items/insertItems.action">
							新增
						</a> --%>
					</td>
				</tr>
				<tr>
					<td colspan="6" align="center">
						<input type="button" value="测试Map参数绑定" onclick="ceshiMap();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>