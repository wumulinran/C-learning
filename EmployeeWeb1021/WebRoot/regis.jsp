<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="jquery-1.11.1.js"></script>
	<script>
		$(function(){
			$("[name='uname']").blur(function(){
				var unameVal=$(this).val();
				
				//获取请求对象
				var request;
				if(window.XMLHttpRequest){
					request=new XMLHttpRequest();
				}else if(window.ActiveXObject){
					request=new ActiveXObject("Msxml2.XMLHTTP");
				}
				
				//准备服务器
				request.open("get","userServlet?mark=valiUname&uname="+unameVal);
				
				//监听响应的状态
				request.onreadystatechange=function(){
					//获取响应的状态
					var state=request.readyState;
					//数据接收完成
					if(state==4){
						//获取响应的状态码
						var status=request.status;
						if(status==200){
							//接收服务器响应的内容
							var result=request.responseText;
							//根据result的结果，判断是否存在
							if(result=="1"){
								$("#unameSpan").html("该用户名已经存在!");
							}else{
								$("#unameSpan").html("该用户名可以使用!");
							}
						}
					}
				};
				
				//传输参数的方法
				request.send(null);
				
				
				
			});
		});
	</script>
  </head>
  
  <body>
	<h1>注册页面</h1>
	${requestScope.msg }
	<hr/>
	<form action='userServlet?mark=regis' method='post' >
		用户名:<input type='text' name='uname' /><span style="color:red" id="unameSpan"></span><br/>
		密码:<input type='password' name='pwd' /><br/>
		重复密码:<input type='password' name='repwd' /><br/>
		<input type='submit' value='注册' />
		<a href="login.jsp">去登陆</a>
	</form>
  </body>
</html>
