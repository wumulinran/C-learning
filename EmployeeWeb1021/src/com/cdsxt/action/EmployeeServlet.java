package com.cdsxt.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.cdsxt.dao.EmployeeDao;
import com.cdsxt.po.Employee;
import com.cdsxt.util.PageUtil;
import com.cdsxt.vo.AgeArea;
import com.google.gson.Gson;
import com.sun.faces.taglib.html_basic.InputSecretTag;

public class EmployeeServlet extends HttpServlet {
	private EmployeeDao empdao= new EmployeeDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		String mark=request.getParameter("mark");
		if("query".equals(mark)){
			queryEmployees(request, response);
		}else if("del".equals(mark)){
			deleteEmp(request,response);
		}else if("add".equals(mark)){
			addEmployee(request,response);
		}else if("update".equals(mark)){
			updateEmployee(request,response);
		}else if("queryAgaPie".equals(mark)){
			queryAgaPie(request, response);
		}else if("queryPart".equals(mark)){
			queryPart(request, response);
		}else if("empXlsUp".equals(mark)){
			empXlsUp(request, response);
		}else if("empXlsDown".equals(mark)){
			empXlsDown(request, response);
		}
	}
	//文件下载
	public void empXlsDown(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Workbook wb=new HSSFWorkbook();
		Sheet st=wb.createSheet();
		Row headRow=st.createRow(0);
		headRow.createCell(0).setCellValue("工号");
		headRow.createCell(1).setCellValue("姓名");
		headRow.createCell(2).setCellValue("年龄");
		headRow.createCell(3).setCellValue("性别");
		List<Employee> empList= empdao.getAllEmployee();
		for (int i = 1; i <=empList.size(); i++) {
			Row row=st.createRow(i);
			row.createCell(0).setCellValue(empList.get(i-1).getId());
			row.createCell(1).setCellValue(empList.get(i-1).getName());
			row.createCell(2).setCellValue(empList.get(i-1).getAge());
			row.createCell(3).setCellValue(empList.get(i-1).getGender()==1?"男":"女");
		}
		String fileName="empInfo+"+new Date().getTime()+".xls";
		wb.write(new FileOutputStream(new File(this.getServletContext().getRealPath("/temp/"+fileName))));
		System.out.println("success");
		response.setHeader("content-disposition", "attachment;filename="+new String("empInfo.xls".getBytes("iso8859-1"), "utf-8"));
		InputStream is = this.getServletContext().getResourceAsStream("/temp/"+fileName);
		byte[] arr=new byte[1024];
		int len;
		OutputStream os = response.getOutputStream();
		while((len=is.read(arr))!=-1){
			os.write(arr, 0, len);
			}
		System.out.println("文件下载完成！");
		new File(this.getServletContext().getRealPath("/temp/"+fileName)).delete();//下载完成删除服务器端的临时文件
	}
	//文件上传
	public void empXlsUp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("文件上传");
		// 创建一个工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 配置一个临时文件夹地址
		ServletContext servletContext = this.getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// 通过工厂创建对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		//处理文件的中文乱码问题
		upload.setHeaderEncoding("utf-8");
		try {
			// 解析请求
			List<FileItem> items = upload.parseRequest(request);
			if(items.size()>0){
				for(FileItem item:items){
					if(!item.isFormField()){
						if("xls".equals(item.getName().substring(item.getName().lastIndexOf(".")+1))){
							String fileName="empInfo"+new Date().getTime()+".xls";
							System.out.println(this.getServletContext().getRealPath("/temp/"+fileName));
							item.write(new File(this.getServletContext().getRealPath("/temp/"+fileName)));
							//读取excel 并保存到数据库
							Workbook wb=WorkbookFactory.create(this.getServletContext().getResourceAsStream("/temp/"+fileName));
							Sheet st = wb.getSheetAt(0);
							Row head = st.getRow(0);
							int workNumIndex = -1;
							int nameIndex=-1;
							int ageIndex=-1;
							int genderIndex=-1;
							for (int i = 0; i < head.getLastCellNum(); i++) {
								String key=head.getCell(i).getStringCellValue();
								if("workNum".equals(key)||"工号".equals(key)){
									workNumIndex=i;
								}else if("name".equals(key)||"姓名".equals(key)){
									nameIndex=i;
								}else if("age".equals(key)||"年龄".equals(key)){
									ageIndex=i;
								}else if("gender".equals(key)||"性别".equals(key)){
									genderIndex=i;
								}
								
							}
							for (int i = 1; i <= st.getLastRowNum(); i++) {
								int workNum=(int)st.getRow(i).getCell(workNumIndex).getNumericCellValue();
								int age=(int)st.getRow(i).getCell(ageIndex).getNumericCellValue();
								String name=st.getRow(i).getCell(nameIndex).getStringCellValue();
								int gender="男".equals(st.getRow(i).getCell(genderIndex).getStringCellValue())?1:0;
								System.out.println(""+workNum+"-"+name+"-"+age+"-"+gender);
								empdao.addEmployee(workNum, name, age, gender);
							}
							System.out.println("上传成功！");
							new File(this.getServletContext().getRealPath("/temp/"+fileName)).delete();//下载完成删除服务器端的临时文件
						}
					}
				}
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		System.out.println("上传成功！");
		queryEmployees(request, response);
	}
	
	//根据指定的范围和当前页数返回对应的页面
	public void queryPart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String range=request.getParameter("range");
		String curp=request.getParameter("curPage");
		List<Employee> list;
		int curPage;
		if(curp==null){
			curPage=1;
		}else{curPage=Integer.parseInt(curp);}
		int dataCount;
		PageUtil pu;
		if(range.contains("-")){
			String[] arr=range.split("-");
			int min=Integer.parseInt(arr[0]);
			int max=Integer.parseInt(arr[1]);
			dataCount = empdao.getPartAgeCount(min, max);
			pu=new PageUtil(dataCount, curPage);
			list=empdao.getPartEmployee(min, max, pu.getStartRow(), pu.getDataStep());
		}else{
			int min=Integer.parseInt(range.substring(0,range.length()-2));
			dataCount = empdao.getPartAgeCount(min);
			pu=new PageUtil(dataCount, curPage);
			list=empdao.getPartEmployee(min,pu.getStartRow(), pu.getDataStep());
		}
		request.setAttribute("range", range);
		request.setAttribute("empList", list);
		request.setAttribute("PageUtil", pu);
		request.getRequestDispatcher("queryPart.jsp").forward(request, response);
		return;
	}
	
	//修改员工
	public void updateEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int workNum=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		int age=Integer.parseInt(request.getParameter("age"));
		int gender=Integer.parseInt(request.getParameter("gender"));
		empdao.alterEmployee(workNum, name, age, gender);
		queryEmployees(request, response);
	}
	
	//添加员工
	public void addEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取添加的数据
		int workNum=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		int age=Integer.parseInt(request.getParameter("age"));
		int gender=Integer.parseInt(request.getParameter("gender"));
		//判断是否存在  存在则返回添加页面提示信息
			if(empdao.checkID(workNum)){
				request.setAttribute("msg", "<font color='red'>员工编号不能重复</font>");
				request.getRequestDispatcher("addEmp.jsp").forward(request, response);
				return;
			}
		//不存在则添加到数据库中返回成功页面显示
		empdao.addEmployee(workNum, name, age, gender);
		queryEmployees(request, response);
	}
	
	//根据传入的id删除对应的员工
	public void deleteEmp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取要删除的id值
		int workNum=Integer.parseInt(request.getParameter("id"));
		empdao.deleteEmployee(workNum);
		//调用查询的方法将最新的数据放入作用域返回成功页面
		queryEmployees(request, response);
	}

	
	//查询员工，并且返回到成功页面
	public void queryEmployees(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int curPage = request.getParameter("curPage")==null?1:Integer.parseInt(request.getParameter("curPage"));
		int dataCount=empdao.getDataCount();
		PageUtil pu=new PageUtil(dataCount, curPage);
		int startRow=pu.getStartRow();
		int dataStep=pu.getDataStep();
		List<Employee> empList=empdao.getEmployee(startRow, dataStep);
		request.setAttribute("empList", empList);
		request.setAttribute("PageUtil", pu);
		request.getRequestDispatcher("success.jsp").forward(request, response);
		return;
		}
	
	public void queryAgaPie(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<AgeArea> list=empdao.getAgeInfo();
		response.getWriter().write(new Gson().toJson(list));
	}
	
}
