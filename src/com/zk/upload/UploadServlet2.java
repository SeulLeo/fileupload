package com.zk.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class UploadServlet2 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadServlet2() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//优先级不高
		request.setCharacterEncoding("UTF-8");
		
		//要执行文件上传的操作
		//判断表单是否支持文件上传
		boolean isMultipartContent=ServletFileUpload.isMultipartContent(request);
		if(!isMultipartContent){
			throw new RuntimeException("your form is not multipart/form-data");
		}
		//创建一个DiskFileItemFactory工厂类
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//创建一个ServletFileUpload核心对象
		ServletFileUpload sfu=new ServletFileUpload(factory);
		//解决上传表单乱码的问题
		sfu.setHeaderEncoding("UTF-8");
		try {
			//限制上传文件的大小
			//sfu.setFileSizeMax(1024);
			//sfu.setSizeMax(6*1024*1024);
		    //解析requst对象，得到一个表单项的集合
			List<FileItem> fileitems=sfu.parseRequest(request);
			//遍历表单项数据
			for(FileItem fileItem:fileitems){
				if(fileItem.isFormField()){
					//普通表单项
					processFormField(fileItem);
				}
				else{
					//上传表单项
					processUploadField(fileItem);
				}
			}
		} catch(FileUploadBase.FileSizeLimitExceededException e){
			throw new RuntimeException("文件过大");
		}catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void processUploadField(FileItem fileItem) {
		// TODO Auto-generated method stub
		//得到上传的名字
		String filename=fileItem.getName();
		//得到文件流
		try {
			InputStream is=fileItem.getInputStream();
			String dictory=this.getServletContext().getRealPath("/upload");
			System.out.println(dictory);
			//创建一个存盘的路径
			File storeDirecctory=new File(dictory);//既代表文件又代表目录
			if(!storeDirecctory.exists()){
				storeDirecctory.mkdirs();//创建一个指定目录
			}
			//处理文件名
			filename=filename.substring(filename.lastIndexOf(File.separator)+1);
			if(filename!=null){
				filename=FilenameUtils.getName(filename);
			}
			//解决文件同名的问题
			filename=UUID.randomUUID()+"_"+filename;
			
			//目录打散
			//String childDirectory=makeChildDirectory(storeDictory);//2015-10-
			String childDirectory=makeChildDirectory2(storeDirecctory,filename);
			//System.out.println(childDirectory);
			//上传文件,自动删除临时文件
			fileItem.write(new File(storeDirecctory,childDirectory+File.separator+filename));

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	//上传表单项
	private void processUploadField1(FileItem fileItem) {
		// TODO Auto-generated method stub
		
		//得到上传的名字
		String filename=fileItem.getName();
		//得到文件流
		try {
			//得到文件流
			InputStream is=fileItem.getInputStream();
			
			String dictory=this.getServletContext().getRealPath("/upload");
			System.out.println(dictory);
			//创建一个存盘的路径
			File storeDictory=new File(dictory);//既代表文件又代表目录
			if(!storeDictory.exists()){
				storeDictory.mkdirs();//创建一个指定目录
			}
			//处理文件名
			filename=filename.substring(filename.lastIndexOf(File.separator)+1);
			if(filename!=null){
				filename=FilenameUtils.getName(filename);
			}
			//解决文件同名的问题
			filename=UUID.randomUUID()+"_"+filename;
			
			//目录打散
			//String childDirectory=makeChildDirectory(storeDictory);//2015-10-
			String childDirectory2=makeChildDirectory2(storeDictory,filename);
			//System.out.println(childDirectory);
			//在storeDictory目录下创建完整的文件
			//File file=new File(storeDictory,filename);
			//File file=new File(storeDictory,childDirectory+File.separator+filename);
			File file2=new File(storeDictory,childDirectory2+File.separator+filename);

			//通过文件输出流将上传的文件保存到磁盘
			//FileOutputStream fos=new FileOutputStream(file);
			FileOutputStream fos2=new FileOutputStream(file2);

			int len=0;
			byte[] b=new byte[1024];
			while((len=is.read(b))!=-1){
			//	fos.write(b,0,len);
				fos2.write(b,0,len);
			}
			//fos.close();
			fos2.close();
			is.close();
			System.out.println("success");
			//删除临时文件
			fileItem.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//目录打散
	private String makeChildDirectory(File storeDictory) {
		// TODO Auto-generated method stub
		SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
		String date=sm.format(new Date());
		//创建目录
		File file=new File(storeDictory,date);
		if(!file.exists()){
			file.mkdirs();
		}
		return date;
	}
	//目录打散
	private String makeChildDirectory2(File storeDictory,String filename){
		int hashcode=filename.hashCode();
		System.out.println(hashcode);
		String code=Integer.toHexString(hashcode);
		String childDirectory=code.charAt(0)+File.separator+code.charAt(1);
		//创建指定目录
		File file=new File(storeDictory,childDirectory);
		if(!file.exists()){
			file.mkdirs();
		}
		return childDirectory;
	}
	//普通表单项
	private void processFormField(FileItem fileItem) {
		// TODO Auto-generated method stub
		String fieldname=fileItem.getFieldName();
		
		try {
			String valuename=fileItem.getString("UTF-8");
		//	fieldname=new String(valuename.getBytes("iso-8859-1"),"utf-8");
			System.out.println(fieldname+":"+valuename);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//要执行文件上传的操作
				//判断表单是否支持文件上传
				boolean isMultipartContent=ServletFileUpload.isMultipartContent(request);
				if(!isMultipartContent){
					throw new RuntimeException("your form is not multipart/form-data");
				}
				//创建一个DiskFileItemFactory工厂类
				DiskFileItemFactory factory=new DiskFileItemFactory();
				//产生临时文件
				factory.setRepository(new File("f:\\temp"));
				//创建一个ServletFileUpload核心对象
				ServletFileUpload sfu=new ServletFileUpload(factory);
				sfu.setHeaderEncoding("UTF-8");
				try {
					//限制上传文件的大小
					//sfu.setFileSizeMax(1024*1024*1024);
					//sfu.setSizeMax(6*1024*1024);
				    //解析requst对象，得到一个表单项的集合
					List<FileItem> fileitems=sfu.parseRequest(request);
					//遍历表单项数据
					for(FileItem fileItem:fileitems){
						if(fileItem.isFormField()){
							//普通表单项
							processFormField(fileItem);
						}
						else{
							//上传表单项
							processUploadField(fileItem);
						}
					}
				} catch(FileUploadBase.FileSizeLimitExceededException e){
					//throw new RuntimeException("文件过大");
					System.out.println("文件过大");
				}catch (FileUploadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
