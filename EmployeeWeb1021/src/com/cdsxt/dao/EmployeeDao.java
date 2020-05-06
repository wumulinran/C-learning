package com.cdsxt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.TryCatchFinally;

import com.cdsxt.po.Employee;
import com.cdsxt.util.DaoUtil;
import com.cdsxt.vo.AgeArea;
import com.mysql.jdbc.Util;

public class EmployeeDao {
	//获取年龄区间的总数
	public int getPartAgeCount(int min,int max){
		int num=0;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("select count(*) from employeeInfo where age between ? and ?;");
			ps.setInt(1, min);
			ps.setInt(2, max);
			ret=ps.executeQuery();
			while(ret.next()){
				num=ret.getInt(1);	
			}
			return num;
		}catch (Exception e) {
				e.printStackTrace();
			}finally{
				DaoUtil.close(ret,ps,conn);
			}
			return 0;
	}
	//获取年龄区间的总数
	public int getPartAgeCount(int min){
		int num=0;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("select count(*) from employeeInfo where age>?;");
			ps.setInt(1, min);
			ret=ps.executeQuery();
			while(ret.next()){
				num=ret.getInt(1);	
			}
			return num;
		}catch (Exception e) {
				e.printStackTrace();
			}finally{
				DaoUtil.close(ret,ps,conn);
			}
			return 0;
	}
	//获取指定年龄区间的分页信息1
	public List<Employee> getPartEmployee(int min,int max,int startRow,int dataStep){	//得到所有的员工信息
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		List<Employee> empList=new ArrayList<Employee>();
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("select * from employeeInfo where age between ? and ? limit ?,?;");
			ps.setInt(1, min);
			ps.setInt(2, max);
			ps.setInt(3, startRow);
			ps.setInt(4, dataStep);
			ret = ps.executeQuery();
			while(ret.next()){
				int workNum = ret.getInt("workNum");
				String name = ret.getString("name");
				int age = ret.getInt("age");
				int gender = ret.getInt("gender");
				empList.add(new Employee(workNum, name, age, gender));		
				}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ret,ps,conn);
		}
		return empList;
	}
	//获取指定年龄区间的分页信息2
	public List<Employee> getPartEmployee(int min,int startRow,int dataStep){	//得到所有的员工信息
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		List<Employee> empList=new ArrayList<Employee>();
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("select * from employeeInfo where age>? limit ?,?;");
			ps.setInt(1, min);
			ps.setInt(2, startRow);
			ps.setInt(3, dataStep);
			ret = ps.executeQuery();
			while(ret.next()){
				int workNum = ret.getInt("workNum");
				String name = ret.getString("name");
				int age = ret.getInt("age");
				int gender = ret.getInt("gender");
				empList.add(new Employee(workNum, name, age, gender));		
				}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ret,ps,conn);
		}
		return empList;
	}
	//获取年龄区间分布的list<AgeArea>对象
	public List<AgeArea> getAgeInfo(){
		List<AgeArea> list=new ArrayList<AgeArea>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("SELECT temp.AgeArea,COUNT(*) count from (SELECT case when age BETWEEN 0 and 10 then '0-10' when age BETWEEN 11 and 20 then '11-20'when age BETWEEN 21 and 30 then '21-30'when age BETWEEN 31 and 40 then '31-40'when age BETWEEN 41 and 50 then '41-50'when age BETWEEN 51 and 60 then '51-60'else '60以上' end AgeArea from employeeinfo) temp GROUP by temp.AgeArea;");
			ret=ps.executeQuery();
			while(ret.next()){
				list.add(new AgeArea(ret.getString(1),ret.getInt(2)));	
			};
		}catch (Exception e) {
				e.printStackTrace();
			}finally{
				DaoUtil.close(ret,ps,conn);
			}
		return list;
	}
	//获取数据的总条数
	public int getDataCount(){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("select count(*) from employeeInfo;");
			ret=ps.executeQuery();
			while(ret.next()){
				return ret.getInt(1);
			}
		}catch (Exception e) {
				e.printStackTrace();
			}finally{
				DaoUtil.close(ret,ps,conn);
			}
			return 0;
	}
	//添加员工
	public void addEmployee(int workNum,String name,int age,int gender){	//添加新的员工信息
		Connection conn=null;
		PreparedStatement ps=null;
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("insert into employeeInfo values(null,?,?,?,?);");
			ps.setInt(1, workNum);
			ps.setString(2, name);
			ps.setInt(3, age);
			ps.setInt(4, gender);
			ps.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ps,conn);
		}
		
	}
	//检查ID是否存在
	public  boolean checkID(int workNum){ //验证工号是否存在
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("select * from employeeInfo where workNum=?;");
			ps.setInt(1, workNum);
			ret=ps.executeQuery();
			while(ret.next()){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ret,ps,conn);
		}
		return false;
	}
	//删除员工
	public void deleteEmployee(int workNum){	//添加新的员工信息
		Connection conn=null;
		PreparedStatement ps=null;
		try{
			conn=DaoUtil.getConn();
			ps = conn.prepareStatement("delete from employeeInfo where workNum=?;");
			ps.setInt(1, workNum);
			ps.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ps,conn);
		}
	}
	//获取分页的员工信息
	public List<Employee> getEmployee(int startRow,int dataStep){	//得到所有的员工信息
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		List<Employee> empList=new ArrayList<Employee>();
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("select * from employeeInfo limit ?,?;");
			ps.setInt(1, startRow);
			ps.setInt(2, dataStep);
			ret = ps.executeQuery();
			while(ret.next()){
				int workNum = ret.getInt("workNum");
				String name = ret.getString("name");
				int age = ret.getInt("age");
				int gender = ret.getInt("gender");
				empList.add(new Employee(workNum, name, age, gender));		
				}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ret,ps,conn);
		}
		return empList;
	}
	//修改员工信息
	public void alterEmployee(int workNum,String name,int age,int gender){	//修改员工信息
		Connection conn=null;
		PreparedStatement ps=null;
		try{
			conn=DaoUtil.getConn();
			ps = conn.prepareStatement("update employeeInfo set name=?,age=?,gender=? where workNum=?;");
			ps.setInt(1, workNum);
			ps.setString(2, name);
			ps.setInt(3, age);
			ps.setInt(4, gender);
			ps.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ps,conn);
		}
	}
	public List<Employee> getAllEmployee(){	//得到所有的员工信息
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet ret=null;
		List<Employee> empList=new ArrayList<Employee>();
		try{
			conn = DaoUtil.getConn();
			ps = conn.prepareStatement("select * from employeeInfo;");
			ret = ps.executeQuery();
			while(ret.next()){
				int workNum = ret.getInt("workNum");
				String name = ret.getString("name");
				int age = ret.getInt("age");
				int gender = ret.getInt("gender");
				empList.add(new Employee(workNum, name, age, gender));		
				}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DaoUtil.close(ret,ps,conn);
		}
		return empList;
	}
}
