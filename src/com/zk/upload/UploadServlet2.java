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
		//���ȼ�����
		request.setCharacterEncoding("UTF-8");
		
		//Ҫִ���ļ��ϴ��Ĳ���
		//�жϱ��Ƿ�֧���ļ��ϴ�
		boolean isMultipartContent=ServletFileUpload.isMultipartContent(request);
		if(!isMultipartContent){
			throw new RuntimeException("your form is not multipart/form-data");
		}
		//����һ��DiskFileItemFactory������
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//����һ��ServletFileUpload���Ķ���
		ServletFileUpload sfu=new ServletFileUpload(factory);
		//����ϴ������������
		sfu.setHeaderEncoding("UTF-8");
		try {
			//�����ϴ��ļ��Ĵ�С
			//sfu.setFileSizeMax(1024);
			//sfu.setSizeMax(6*1024*1024);
		    //����requst���󣬵õ�һ������ļ���
			List<FileItem> fileitems=sfu.parseRequest(request);
			//������������
			for(FileItem fileItem:fileitems){
				if(fileItem.isFormField()){
					//��ͨ����
					processFormField(fileItem);
				}
				else{
					//�ϴ�����
					processUploadField(fileItem);
				}
			}
		} catch(FileUploadBase.FileSizeLimitExceededException e){
			throw new RuntimeException("�ļ�����");
		}catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void processUploadField(FileItem fileItem) {
		// TODO Auto-generated method stub
		//�õ��ϴ�������
		String filename=fileItem.getName();
		//�õ��ļ���
		try {
			InputStream is=fileItem.getInputStream();
			String dictory=this.getServletContext().getRealPath("/upload");
			System.out.println(dictory);
			//����һ�����̵�·��
			File storeDirecctory=new File(dictory);//�ȴ����ļ��ִ���Ŀ¼
			if(!storeDirecctory.exists()){
				storeDirecctory.mkdirs();//����һ��ָ��Ŀ¼
			}
			//�����ļ���
			filename=filename.substring(filename.lastIndexOf(File.separator)+1);
			if(filename!=null){
				filename=FilenameUtils.getName(filename);
			}
			//����ļ�ͬ��������
			filename=UUID.randomUUID()+"_"+filename;
			
			//Ŀ¼��ɢ
			//String childDirectory=makeChildDirectory(storeDictory);//2015-10-
			String childDirectory=makeChildDirectory2(storeDirecctory,filename);
			//System.out.println(childDirectory);
			//�ϴ��ļ�,�Զ�ɾ����ʱ�ļ�
			fileItem.write(new File(storeDirecctory,childDirectory+File.separator+filename));

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	//�ϴ�����
	private void processUploadField1(FileItem fileItem) {
		// TODO Auto-generated method stub
		
		//�õ��ϴ�������
		String filename=fileItem.getName();
		//�õ��ļ���
		try {
			//�õ��ļ���
			InputStream is=fileItem.getInputStream();
			
			String dictory=this.getServletContext().getRealPath("/upload");
			System.out.println(dictory);
			//����һ�����̵�·��
			File storeDictory=new File(dictory);//�ȴ����ļ��ִ���Ŀ¼
			if(!storeDictory.exists()){
				storeDictory.mkdirs();//����һ��ָ��Ŀ¼
			}
			//�����ļ���
			filename=filename.substring(filename.lastIndexOf(File.separator)+1);
			if(filename!=null){
				filename=FilenameUtils.getName(filename);
			}
			//����ļ�ͬ��������
			filename=UUID.randomUUID()+"_"+filename;
			
			//Ŀ¼��ɢ
			//String childDirectory=makeChildDirectory(storeDictory);//2015-10-
			String childDirectory2=makeChildDirectory2(storeDictory,filename);
			//System.out.println(childDirectory);
			//��storeDictoryĿ¼�´����������ļ�
			//File file=new File(storeDictory,filename);
			//File file=new File(storeDictory,childDirectory+File.separator+filename);
			File file2=new File(storeDictory,childDirectory2+File.separator+filename);

			//ͨ���ļ���������ϴ����ļ����浽����
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
			//ɾ����ʱ�ļ�
			fileItem.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//Ŀ¼��ɢ
	private String makeChildDirectory(File storeDictory) {
		// TODO Auto-generated method stub
		SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
		String date=sm.format(new Date());
		//����Ŀ¼
		File file=new File(storeDictory,date);
		if(!file.exists()){
			file.mkdirs();
		}
		return date;
	}
	//Ŀ¼��ɢ
	private String makeChildDirectory2(File storeDictory,String filename){
		int hashcode=filename.hashCode();
		System.out.println(hashcode);
		String code=Integer.toHexString(hashcode);
		String childDirectory=code.charAt(0)+File.separator+code.charAt(1);
		//����ָ��Ŀ¼
		File file=new File(storeDictory,childDirectory);
		if(!file.exists()){
			file.mkdirs();
		}
		return childDirectory;
	}
	//��ͨ����
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
		//Ҫִ���ļ��ϴ��Ĳ���
				//�жϱ��Ƿ�֧���ļ��ϴ�
				boolean isMultipartContent=ServletFileUpload.isMultipartContent(request);
				if(!isMultipartContent){
					throw new RuntimeException("your form is not multipart/form-data");
				}
				//����һ��DiskFileItemFactory������
				DiskFileItemFactory factory=new DiskFileItemFactory();
				//������ʱ�ļ�
				factory.setRepository(new File("f:\\temp"));
				//����һ��ServletFileUpload���Ķ���
				ServletFileUpload sfu=new ServletFileUpload(factory);
				sfu.setHeaderEncoding("UTF-8");
				try {
					//�����ϴ��ļ��Ĵ�С
					//sfu.setFileSizeMax(1024*1024*1024);
					//sfu.setSizeMax(6*1024*1024);
				    //����requst���󣬵õ�һ������ļ���
					List<FileItem> fileitems=sfu.parseRequest(request);
					//������������
					for(FileItem fileItem:fileitems){
						if(fileItem.isFormField()){
							//��ͨ����
							processFormField(fileItem);
						}
						else{
							//�ϴ�����
							processUploadField(fileItem);
						}
					}
				} catch(FileUploadBase.FileSizeLimitExceededException e){
					//throw new RuntimeException("�ļ�����");
					System.out.println("�ļ�����");
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
