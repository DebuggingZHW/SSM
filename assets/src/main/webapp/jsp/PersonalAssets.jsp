<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css"/>
<script>
  function delUser(id) {
	  $.get("<%=basePath%>user/delUser?id=" + id,function(data) {
		  if ("success" == data.result) {
			 alert("删除成功！");
			 window.location.reload();
		 } else {
			 alert("删除失败！");
		 }
	  });
  }
  
  function exportExcel() {
	  $.get("<%=basePath%>user/personalExport",function(data) {
		  if ("success" == data.result) {
			 alert("导出成功！");
			 window.location.reload();
		 } else {
			 alert("导出失败！");
		 }
	  });
  }
</script>
<style>
  .btn1 {
    position:relative;
    left:15px;
    top:5px;
  }
</style>
<title>资产列表</title>
</head>
<body>
  <div>
    <button type="button" class="btn btn-info btn1" onclick="location='<%=basePath%>'">返回首页</button>
  </div>
  <p>
  <table class="table table-striped table-hover">  
    <thead>
      <tr>
        <td>资产</td>
        <td>资产数目</td>
        <td>资产明细</td>
        <td></td>
      </tr>
    </thead>
    <tbody>
      <c:if test="${!empty personList}">
        <c:forEach items="${personList}" var="person">  
          <tr>  
            <td>${person.asset}</td>
            <td>${person.assetnum}</td>
            <td>${person.assetinfo}</td>
            <td>
              <button type="button" class="btn btn-info" onclick="location='<%=basePath%>user/getPersonalUser?id=${person.userid}'">编辑</button>
              <button type="button" class="btn btn-danger" onclick="delUser('${person.userid}')">删除</button>
            </td>  
          </tr>
        </c:forEach>  
      </c:if>  
    </tbody>
  </table>
  <div>
    <button type="button" class="btn btn-primary btn1" onclick="location='<%=basePath%>user/toAddPersonalAssets?id=mine'">添加我的资产</button>
    <button type="button" class="btn btn-success btn1" onclick="location='<%=basePath%>user/personalExport'">导出资料</button>
  </div>
</body>
</html>