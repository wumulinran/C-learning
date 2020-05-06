package com.cdsxt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cdsxt.util.DaoUtil;

public class UserDao {
	public boolean userLogin(String uname,String pwd){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		try {
			conn=DaoUtil.getConn();
			//获取执行sql语句的对象
			ps=conn.prepareStatement("select * from user where uname=? and pwd=?");
			ps.setString(1, uname);
			ps.setString(2, pwd);
			//执行查询语句
			ret=ps.executeQuery();
			while(ret.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ret,ps,conn);
		}
		return false;
	}
	
	public boolean valiUname(String uname){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		try {
			conn=DaoUtil.getConn();
			//获取执行sql语句的对象
			ps=conn.prepareStatement("select * from user where uname=?");
			//初始化sql语句
			ps.setString(1, uname);
			//执行查询语句
			ret=ps.executeQuery();
			while(ret.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ret,ps,conn);
		}
		return false;
	}
	
	
	
	public void userRegis(String uname,String pwd){
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=DaoUtil.getConn();
			//获取执行sql语句的对象
			ps=conn.prepareStatement("insert into user values(null,?,?)");
			ps.setString(1, uname);
			ps.setString(2, pwd);
			//执行查询语句
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ps,conn);
		}
	}
}
