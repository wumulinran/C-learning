package com.cdsxt.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class DaoUtil {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	static {
		InputStream is = null;
		try {
			Properties pro=new Properties();
			is=DaoUtil.class.getResourceAsStream("/db.properties");
			pro.load(is);
			driver = pro.getProperty("driver");
			url = pro.getProperty("url");
			username = pro.getProperty("username");
			password = pro.getProperty("password");
			Class.forName(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
	}
	
	public static Connection getConn(){
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void close(Object...objs){
		if(objs!=null&&objs.length>0){
			for (Object obj : objs) {
				try {
					if(obj instanceof ResultSet){
						((ResultSet) obj).close();
					}else if(obj instanceof PreparedStatement){
						((PreparedStatement) obj).close();
					}else if(obj instanceof Connection){
						((Connection) obj).close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
