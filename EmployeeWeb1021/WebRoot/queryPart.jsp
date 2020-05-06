<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'success.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
		a{text-decoration:none}
	</style>
	<script src="jquery-1.11.1.js"></script>
	<script>
	</script>
	<script src="echarts.js"></script>
  </head>
  
  <body>
    <h1>登陆成功,欢迎${uname }使用本系统!<a href='userServlet?mark=exit'>[安全退出]</a></h1>
    当前在线的管理员:<span id="userSpan">${userList}</span>
    <hr/>
    <table align="center" border="1" width="700px">
    	<tr>
    		<td colspan="4" align="center">
    			<h1>${range }年龄员工信息表</h1><br/>
    			<a href="employeeServlet?mark=query">[回到首页]</a>
    		</td>
    	</tr>
    	<tr>
    		<th>编号</th>
    		<th>姓名</th>
    		<th>年龄</th>
    		<th>性别</th>
    	</tr>
    	<c:forEach items="${empList }" var="emp">
    		<tr align="center">
    			<td>${emp.id}</td>
    			<td>${emp.name}</td>
    			<td>${emp.age}</td>
    			<td>${emp.gender==1?"男":"女"}</td>
    		</tr>
    	</c:forEach>
    	<tr align="center">
    	<td colspan="5">
    		<c:if test="${PageUtil.curPage!=1 }"><a href="employeeServlet?mark=queryPart&curPage=1&range=${range}">首页</a>&nbsp;
    			<a href="employeeServlet?mark=queryPart&curPage=${PageUtil.prePage }&range=${range}">上一页</a></c:if>
    		<c:forEach var="i" begin="${PageUtil.leftNav}" end="${PageUtil.rightNav}">
    		<c:choose>
    			<c:when test="${PageUtil.curPage==i}"><font color="red">${i}</font></c:when>
    			<c:otherwise><a href="employeeServlet?mark=queryPart&curPage=${i}&range=${range}">${i}</a></c:otherwise>
    		</c:choose>
    		</c:forEach>
    		<c:if test="${PageUtil.curPage!=PageUtil.pageCount}">
    			<a href="employeeServlet?mark=queryPart&curPage=${PageUtil.nextPage }&range=${range}">下一页</a>&nbsp;
    			<a href="employeeServlet?mark=queryPart&curPage=${PageUtil.pageCount }&range=${range}">尾页</a>
    		</c:if>
    </table>

  </body>
</html>
