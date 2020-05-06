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
		$(function(){
			$("#sel").change(function(){
				window.location="employeeServlet?mark=query&curPage="+$(this).val();
			});
			window.setInterval(function(){
				$.ajax({
					type:"get",
					url:"userServlet?"+new Date().getTime(),
					data:{
						mark:"getUsers"
					},
					success:function(data){
						$("#userSpan").html(data.replace(/"/g,""));
					}
				});				
				
			},10000);
			
			$("#age").click(function(){
			$("#main").css("display","block");
		 var myChart = echarts.init(document.getElementById('main'));
			    	option = {
			    title : {
			        text: '年龄比例',
			        subtext: '纯属虚构',
			        x:'center'
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: []
			    },
			    series : [
			        {
			            name: '年龄段',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[],
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
			  
     	$.ajax({
     		type:"get",
     		url:"employeeServlet?mark=queryAgaPie",
     		success:function(data){
     			eval("var arr="+data);
     			for(var i in arr){
     				option.legend.data.push(arr[i].area);
     				option.series[0].data.push({value:arr[i].count,name:arr[i].area})	
     			}
     			myChart.setOption(option);
     			myChart.on('click', function (params) {
    			window.location="employeeServlet?mark=queryPart&range="+encodeURIComponent(params.name)
    		})
     		}
     	})
     	})
			
			$("#dis").click(function(){
			$("#main").css("display","none");
		})
			
		});
	</script>
	<script src="echarts.js"></script>
  </head>
  
  <body>
    <h1>登陆成功,欢迎${uname }使用本系统!<a href='userServlet?mark=exit'>[安全退出]</a></h1>
    当前在线的管理员:<span id="userSpan">${userList}</span>
    <hr/>
    <table align="center" border="1" width="700px">
    	<tr>
    		<td colspan="5" align="center">
    			<h1>员工信息表</h1>
    			<a href="addEmp.jsp">[添加员工]</a>
    			<input type="button" value="查看年龄比例" id="age"/>
    			<input type="button" value="取消显示年龄图表" id="dis"/>
    			<form action="employeeServlet?mark=empXlsUp" method="post" enctype="multipart/form-data">
    				请上传xls格式的员工表：<input type="file" name="emp"/>
    				<input type='submit' value="提交" />
    			</form>
    			<a href="employeeServlet?mark=empXlsDown"><input type="button" value='导出员工信息'/></a>
    		</td>
    	</tr>
    	<tr>
    		<th>编号</th>
    		<th>姓名</th>
    		<th>年龄</th>
    		<th>性别</th>
    		<th>操作</th>
    	</tr>
    	<c:forEach items="${empList }" var="emp">
    		<tr align="center">
    			<td>${emp.id}</td>
    			<td>${emp.name}</td>
    			<td>${emp.age}</td>
    			<td>${emp.gender==1?"男":"女"}</td>
    			<td>
    				<a href="updateEmp.jsp?id=${emp.id}&name=${emp.name}&age=${emp.age}&gender=${emp.gender}">修改</a>
    				<a href="employeeServlet?mark=del&id=${emp.id}">删除</a>
    			</td>
    		</tr>
    	</c:forEach>
    	<tr align="center">
    	<td colspan="5">
    		<c:if test="${PageUtil.curPage!=1 }"><a href="employeeServlet?mark=query&curPage=1">首页</a>&nbsp;
    			<a href="employeeServlet?mark=query&curPage=${PageUtil.prePage }">上一页</a></c:if>
    		<c:forEach var="i" begin="${PageUtil.leftNav}" end="${PageUtil.rightNav}">
    		<c:choose>
    			<c:when test="${PageUtil.curPage==i}"><font color="red">${i}</font></c:when>
    			<c:otherwise><a href="employeeServlet?mark=query&curPage=${i}">${i}</a></c:otherwise>
    		</c:choose>
    		</c:forEach>
    		<c:if test="${PageUtil.curPage!=PageUtil.pageCount}">
    			<a href="employeeServlet?mark=query&curPage=${PageUtil.nextPage }">下一页</a>&nbsp;
    			<a href="employeeServlet?mark=query&curPage=${PageUtil.pageCount }">尾页</a>
    		</c:if>
    		<form action="employeeServlet?mark=query" method="post">
    		<input type="text" name="curPage" value=${PageUtil.curPage} size="2"/>页
    		<input type="submit" value="跳转到">
    		</form>
    		<form action="employeeServlet?mark=query" method="post">
    		<select name=curPage id="sel">
    			<c:forEach var="i" begin="${PageUtil.homePage}" end="${PageUtil.pageCount}">
    			<c:choose>
	    			<c:when test="${PageUtil.curPage==i}">
		    				<option selected="selected" >${i}</option>
		    		</c:when>
	    			<c:otherwise>
	    				<option >${i}</option>
	    			</c:otherwise>
    			</c:choose>
    			</c:forEach>
    		</select>
    		</form>
    		${gender }
    	</td>
    	</tr>
    </table>
    <div id="main" style="text-align:center;margin:auto; width: 600px;height:400px;"></div>
    
    
  </body>
</html>
