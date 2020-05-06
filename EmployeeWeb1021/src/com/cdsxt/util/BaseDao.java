package com.cdsxt.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.cdsxt.po.Employee;

public class BaseDao {
	//支持全查询 
	/*public static List<Object> queryAll(String sql,Object obj){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Object> list=new ArrayList<Object>();
		try {
			conn=DaoUtil.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			Class clazz= obj.getClass();
			Field[] fields=clazz.getDeclaredFields();
			while(rs.next()){
				Object o = clazz.newInstance();
				for (Field field : fields) {
					String fieldName=field.getName();
					Object value= rs.getObject(fieldName);
					field.setAccessible(true);
					field.set(o, value);
					field.setAccessible(false);
				}
				list.add(o);
			}
		} catch (Exception e) {
			employee.printStackTrace();
		}finally{
			DaoUtil.close(rs,ps,conn);
		}
		return list;
	}
	
	public static void main(String[] args) {
		List<Object> list = queryAll("SELECT * from employeeinfo", new Employee());
		for (Object object : list) {
			Employee e=(Employee)object;
			System.out.println(employee.getId()+"--"+employee.getName()+"--"+employee.getAge()+"--"+employee.getGender());
		}
	}*/
	
	//支持全查询 且返回正确的类型
	/*public static<T> List<T> queryAll(String sql,Class<T> clazz){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<T> list=new ArrayList<T>();
		try {
			conn=DaoUtil.getConn();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
			Field[] fields=clazz.getDeclaredFields();
			while(rs.next()){
				T t = clazz.newInstance();
				for (Field field : fields) {
					String fieldName=field.getName();
					Object value= rs.getObject(fieldName);
					field.setAccessible(true);
					field.set(t, value);
					field.setAccessible(false);
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(rs,ps,conn);
		}
		return list;
	}
	public static void main(String[] args) {
		List<Employee> list = queryAll("SELECT * from employeeinfo", Employee.class);
		for (Employee employee : list) {
			System.out.println(employee.getId()+"--"+employee.getName()+"--"+employee.getAge()+"--"+employee.getGender());
		}
	}*/
	/*//支持全部、部分查询、且避开访问权限的设置
	public static<T> List<T> queryAll(String sql,Class<T> clazz,Object...params){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<T> list=new ArrayList<T>();
		try {
			conn=DaoUtil.getConn();
			ps=conn.prepareStatement(sql);
			if(params!=null){
				for (int i=0;i<params.length;i++) {
					ps.setObject(i+1, params[i]);
				}
			}
			rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int count = rsmd.getColumnCount();
			System.out.println(count);
			while(rs.next()){
				T t=clazz.newInstance();
				for(int i=1;i<=count;i++){
					//获取属性名
					String fieldName=rsmd.getColumnName(i);
					//获取属性
					Field field=clazz.getDeclaredField(fieldName);
					//获取设定器的名字
					String setter=Setter(fieldName);
					//获取对应属性的设定器 (设定器的参数同属性的类型)
					Method setMethod=clazz.getMethod(setter, field.getType());
					//获取值
					Object value=rs.getObject(fieldName);
					//调用方法注入属性
					setMethod.invoke(t, value);
				}
				//将对象放入list
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(rs,ps,conn);
		}
		return list;
	}*/
	public static <T> List<T> queryPos(String sql,Class<T> clazz,Object...params){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<T> list=new ArrayList<T>();
		try {
			conn=DaoUtil.getConn();
			ps=conn.prepareStatement(sql);
			//考虑填坑的问题
			if(params!=null){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			rs=ps.executeQuery();
			
			ResultSetMetaData rsmd=rs.getMetaData();
			//获取结果集的列数
			int count=rsmd.getColumnCount();
			
			while(rs.next()){
				//创建对象
				T t=clazz.newInstance();
				for(int i=1;i<=count;i++){
					//获取属性名
					String fieldName=rsmd.getColumnName(i);
					//获取属性
					Field field=clazz.getDeclaredField(fieldName);
					//获取设定器的名字
					String setter=getSetter(fieldName);
					//获取对应属性的设定器 (设定器的参数同属性的类型)
					Method setMethod=clazz.getMethod(setter, field.getType());
					//获取值
					Object value=rs.getObject(fieldName);
					//调用方法注入属性
					setMethod.invoke(t, value);
				}
				//将对象放入list
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(rs,ps,conn);
		}
		return list;
	}
	public static String getSetter(String fieldName){
		//System.out.println("set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1));
		return "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
	}
	public static void main(String[] args) {
		List<Employee> list = queryPos("SELECT ? from employeeinfo WHERE ?>? and age=?", Employee.class,"name","id","555","23");
		for (Employee employee : list) {
			System.out.println(employee.getId()+"--"+employee.getName()+"--"+employee.getAge()+"--"+employee.getGender());
		}
	}
}

