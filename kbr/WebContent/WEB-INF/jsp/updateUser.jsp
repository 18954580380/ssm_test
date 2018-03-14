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
  </head>
  <body>
   <form action="<%=basePath%>user/trueUpdateUser.htm" method="post" autocomplete="on">
   <input type="hidden" name="id"  value="${user.id }"/><br />
        姓名: <input type="text" name="name" value="${user.name }"/><br />
        年龄: <input type="text" name="age" autocomplete="off" value="${user.age }" /><br />
   <input type="submit" />
  </form>
  </body>
</html>
