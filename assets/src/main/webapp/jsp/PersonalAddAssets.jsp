<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>添加资产</title>
<style>
  .btn1 {
    position:relative;
    left:15px;
    top:5px;
  }
</style>
</head>
<body>
  <button type="button" class="btn btn-success btn1" onclick="location='<%=basePath%>user/getPersonalAssets'">返回</button>
  <p>
  <form class="form-horizontal" action="" name="AssetsForm">
    <input type="hidden" name="userid" value="${assets.userid}"/>
    <div class="form-group">
      <label for="username" class="col-md-2 control-label">姓名：</label>
      <div class="col-sm-3">
        <input type="text" name="username" value="${assets.username}" class="form-control"/>
      </div>
    </div>
    <div class="form-group">
      <label for="username" class="col-md-2 control-label">资产名称：</label>
      <div class="col-sm-3">
        <select name="asset" id="asset" class="form-control">
   	      <option></option>
   	      <option>电脑主机</option>
   	      <option>鼠标</option>
   	      <option>内存</option>
   	    </select>
      </div>
    </div>
    <div class="form-group">
      <label for="username" class="col-md-2 control-label">资产数目：</label>
      <div class="col-sm-3">
        <input type="text" name="assetnum" class="form-control"/>
      </div>
    </div>
    <div>
    <div class="form-group computer" style="display:none">
      <label for="username" class="col-md-2 control-label">资产明细：</label>
      <div class="col-sm-3">
        <!-- <textarea name="assetinfo" class="form-control" rows="6" cols="22"></textarea> -->
        <div class="input-group">
          <span class="input-group-addon">CPU</span>
          <input type="text" class="form-control" id="CPU"/>
        </div>
        <p>
        <div class="input-group">
          <span class="input-group-addon">硬盘</span>
          <input type="text" class="form-control" id="disk"/>
        </div>
        <p>
        <div class="input-group">
          <span class="input-group-addon">内存</span>
          <input type="text" class="form-control" id="memory"/>
        </div>
      </div>
    </div>
    <div class="form-group mouse" style="display:none">
      <label for="username" class="col-md-2 control-label">资产明细：</label>
      <div class="col-sm-3">
        <div class="input-group">
          <span class="input-group-addon">品牌</span>
          <input type="text" class="form-control" id="MouseLabel"/>
        </div>
      </div>
    </div>
    <div class="form-group memory" style="display:none">
      <label for="username" class="col-md-2 control-label">资产明细：</label>
      <div class="col-sm-3">
        
        <div class="input-group">
          <span class="input-group-addon">品牌</span>
          <input type="text" class="form-control" id="MemoryLabel"/>
        </div>
        <p>
        <div class="input-group">
          <span class="input-group-addon">大小</span>
          <input type="text" class="form-control" id="size"/>
        </div>
    </div>
    <input type="hidden" name="assetinfo" id="assetinfo" class="form-control"/>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <input type="button" value="添加" class="btn btn-primary" onclick="addAssets()"/>
      </div>
    </div>
  </form>
</body>
<script>
  function addAssets() {
	  if($("#asset").val() == "电脑主机") {
		  var assetInfo = "电脑主机(CPU:" + $("#CPU").val() + " " + "硬盘:" +  $("#disk").val() + " " + "内存:" + $("#memory").val() + ")";
	  } else if($("#asset").val() == "鼠标") {
		  var assetInfo = "鼠标(品牌:" + $("#MouseLabel").val() + ")";
	  } else if($("#asset").val() == "内存") {
		  var assetInfo = "内存(品牌:" + $("#MemoryLabel").val() + " " + "大小:" +  $("#size").val() + ")";
	  }
	  $("#assetinfo").val(assetInfo);
	  var form = document.forms[0];
	  form.action = "<%=basePath%>user/personalAddAssets";
	  form.method = "post";
	  form.submit();
  }
  
  $("#asset").blur(function() {
	  if($("#asset").val() == "电脑主机") {
		  $(".mouse").css("display","none");
		  $(".memory").css("display","none");
		  $(".computer").css("display","block");
	  } else if($("#asset").val() == "鼠标") {
		  $(".computer").css("display","none");
		  $(".memory").css("display","none");
		  $(".mouse").css("display","block");
	  } else if($("#asset").val() == "内存") {
		  $(".computer").css("display","none");
		  $(".mouse").css("display","none");
		  $(".memory").css("display","block");
	  }
  });
  
</script>
</html>