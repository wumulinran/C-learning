package com.cdsxt.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cdsxt.dao.UserDao;
import com.google.gson.Gson;

public class UserServlet extends HttpServlet {
	//初始化一个dao层的对象
	private UserDao userDao=new UserDao();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置请求的编码
//		request.setCharacterEncoding("项目编码");    
		//上面这句代码只针对于请求实体进行编码，也就是只能针对post进行编码。如果需要对get方式同时进行编码，需要在原来配置端口号的地方，
		//添加 useBodyEncodingForURI="true";
		request.setCharacterEncoding("utf-8");
		//获取控制逻辑的标识mark
		String mark=request.getParameter("mark");
		if("login".equals(mark)){
			userLogin(request, response);
		}else if("regis".equals(mark)){
			userRegis(request, response);
		}else if("valiUname".equals(mark)){
			valiUname(request,response);
		}else if("exit".equals(mark)){
			exitUser(request,response);
		}else if("getUsers".equals(mark)){
			getUsers(request,response);
		}
	}
	
	//将最新的用户信息返回给ajax请求
	public void getUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> userList=(List<String>) this.getServletContext().getAttribute("userList");
		response.getWriter().write(new Gson().toJson(userList));
	}
	
	//安全退出
	public void exitUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//使session强制失效
		request.getSession().invalidate();
		//跳转到登陆页面
		request.getRequestDispatcher("login.jsp").forward(request, response);
		
	}
	
	
	//验证用户名是否存在      存在 1  不存在0
	public void valiUname(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取用户名
		String uname=request.getParameter("uname");
		//获取printWriter对象
		PrintWriter pw=response.getWriter();
		
		if(userDao.valiUname(uname)){
			pw.write("1");
		}else{
			pw.write("0");
		}
	}
	
	
	//用户登陆
	public void userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置响应中文编码
		response.setHeader("content-type", "text/html;charset=utf-8");
		//获取表单参数
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		//获取往页面输出字符串的对象pw
		PrintWriter pw=response.getWriter();
		if(uname.isEmpty()||pwd.isEmpty()){
			request.setAttribute("msg", "<font color='red'>登陆信息填写不完整!</font>");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}else{
			//通过jdbc对数据库进行查询，判断是否登录成功
			//成功 true  失败false
			boolean result=userDao.userLogin(uname,pwd);
			
			if(result){
				//将用户信息保存到session作用域
				request.getSession().setAttribute("uname", uname);
				request.getRequestDispatcher("employeeServlet?mark=query").forward(request, response);
			}else{
				request.setAttribute("msg", "<font color='red'>用户名或密码错误!</font>");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
		}
	}
	
	
	//用户注册
	public void userRegis(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//初始化表单参数
		String uname=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		String repwd=request.getParameter("repwd");
		
		
		if(uname.isEmpty()||pwd.isEmpty()||repwd.isEmpty()){
			//将提示信息保存到作用域
			request.setAttribute("msg", "<font color='red'>注册信息填写不完整!</font>");
			request.getRequestDispatcher("regis.jsp").forward(request, response);
			return;
		}else if(!pwd.equals(repwd)){
			request.setAttribute("msg", "<font color='red'>两次密码输入不一致!</font>");
			request.getRequestDispatcher("regis.jsp").forward(request, response);
			return;
		}else if(userDao.valiUname(uname)){
			request.setAttribute("msg", "<font color='red'>好名字都被取了!</font>");
			request.getRequestDispatcher("regis.jsp").forward(request, response);
			return;
		}else{
			try {
				//通过jdbc将用户名和密码保存到数据库
				userDao.userRegis(uname,pwd);
				request.setAttribute("msg", "<font color='red'>注册成功，请登陆!</font>");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	

}
