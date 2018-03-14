<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
  <script type="text/javascript">
    function deleteUser(id){
    	$.ajax({
			type : "post",
			url : "<%=basePath%>user/deleteUser.htm",
			dataType : "text",
			data :{id:id},
			success:function(data) {
				if(data=="1"){
					window.location.href="<%=basePath%>user/user.htm";
				}
			},
			error:function(data){
		    }
	    });
    }
    function updateUser(id){
    	window.location.href="<%=basePath%>user/updateUser.htm?id="+id;
    }
  </script>
  </head>
  <body>
   <table border="1">
   <tr>
    <th>序号</th>
    <th>用户名称</th>
    <th>年级</th>
    <th>操作</th>
   </tr>
    <c:forEach items="${userList}" var="u" varStatus="index">
    <tr>
    <td>${u.id}</td>
    <td>${u.name}</td>
    <td>${u.age}</td>
    <td>
    <a style="color: blue;" onclick="deleteUser('${u.id}')">删除</a>|<a  onclick="updateUser('${u.id}')" style="color: blue;">修改</a>
    </td>
   </tr>
   </c:forEach>
</table>
  <a href="<%=basePath%>user/findUser.htm">批量导出</a>
  <a href="<%=basePath%>user/importUser.htm">批量导入</a>
  </body>
</html>
