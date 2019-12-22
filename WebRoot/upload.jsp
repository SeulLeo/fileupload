<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <script type="text/javascript">
  function addFile(){
     //得到div容器
     var div1=document.getElementById("div1");
     div1.innerHTML +="<div><input type='file' name='photo' /><input type='button' value='删除' onclick='delFile(this)' /><br/></div>";
  }
  function delFile(input){
     //使用input对象的爷爷删除他的爸爸
     input.parentNode.parentNode.removeChild(input.parentNode);
  }
  </script>
  <body>
    <!--<form enctype="application/x-www-form-urlencoded" action="${pageContext.request.contextPath }/servlet/UploadServlet1" method="post">-->
    <!--<form enctype="multipart/form-data" action="${pageContext.request.contextPath }/servlet/UploadServlet2" method="post">-->
    <form enctype="multipart/form-data" action="${pageContext.request.contextPath }/servlet/UploadServlet2" method="post">
    <input type="text" name="name" value="name" /><br/>
    <div id="div1">
    <div>
    <input type="file" name="photo" /><input type="button" value="添加" onclick="addFile()"><br/>
    </div>
    <div>
    <input type="file" name="photo" /><input type="button" value="删除" onclick="delFile(this)"><br/>
    </div>
    </div>
    <input type="submit" name="上传" /><br/>
    </form>
  </body>
</html>
