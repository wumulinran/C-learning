package com.cdsxt.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UserListener implements HttpSessionAttributeListener,ServletContextListener,HttpSessionListener{
	private ServletContext context;
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		//获取session保存的键
		String name=se.getName();
		if("uname".equals(name)){
			//获取用户名
			String uname=(String) se.getValue();
			//将用户名保存到context作用域的list中
			List<String> userList=(List<String>) context.getAttribute("userList");
			//判断当前的用户是否已经登陆过，如果没有登陆则保存到list中
			if(!userList.contains(uname)){
				userList.add(uname);
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//获取ServletContext对象
		context=sce.getServletContext();
		//往这个作用域中保存一个空的List用于后期存放在线的用户名
		context.setAttribute("userList", new ArrayList<String>());
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//查看一下即将要销毁的session中是否有用户信息
		HttpSession session=se.getSession();
		String uname=(String) session.getAttribute("uname");
		if(uname!=null){
			//移除用户名
			List<String> userList=(List<String>) context.getAttribute("userList");
			userList.remove(uname);
		}
	}
	
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}


	
	
}
