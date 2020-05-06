package com.cdsxt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		
		HttpSession session=req.getSession();
		Object uname=session.getAttribute("uname");
		if(uname==null){
			request.setAttribute("msg", "<font color='red'>请登陆后再使用本系统!</font>");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}else{
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
