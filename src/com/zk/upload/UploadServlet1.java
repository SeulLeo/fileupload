package com.zk.upload;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadServlet1 extends HttpServlet {
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
			String name=request.getParameter("name");
			String photo=request.getParameter("photo");
			//System.out.println(name);
			//System.out.println(photo);
			
			ServletInputStream inputstream=request.getInputStream();
			int len=0;
			byte[] b=new byte[1024];
			while((len=inputstream.read(b))!=-1){
				System.out.println(new String(b,0,len));
			}
			inputstream.close();
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
		/*
		String name=request.getParameter("name");
		String photo=request.getParameter("photo");
		System.out.println(name);
		System.out.println(photo);*/
		ServletInputStream inputstream=request.getInputStream();
		int len=0;
		byte[] b=new byte[1024];
		while((len=inputstream.read(b))!=-1){
			System.out.println(new String(b,0,len));
		}
		inputstream.close();
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
