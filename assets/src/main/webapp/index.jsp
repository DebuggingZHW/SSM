<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/bootstrap.css"/>
<style>
	.format{
		position:relative;
		left:100px;
	}
</style>
<title>首页</title>
</head>
<body>
	<div class="format">
		<h2>
		  <a href="<%=basePath%>user/getAllAssets">进入资产管理界面（管理员）</a>
		  <p></p>
		  <a onclick="show()">进入资产管理界面（普通用户）</a>
		  <p></p>本机IP地址：<%=request.getRemoteAddr()%>
		</h2>
	</div>
	<p/>
	<div class="person" style="display:none">
		<form class="form-horizontal" action="">
		  <div class="form-group">
		    <label class="col-md-2 control-label">用户名：</label>
		    <div class="col-sm-3">
		      <input class="form-control" type="text" name="pname" id="pname" required/>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-md-2 control-label">密码：</label>
		    <div class="col-sm-3">
		      <input class="form-control" type="text" name="ppassword" id="ppassword" required/>
		    </div>
		  </div>
		  <div class="form-group">
	        <div class="col-sm-offset-2 col-sm-10">
	          <input type="button" value="登陆" class="btn btn-primary" onclick="personalForm()"/>
	        </div>
	      </div>
	  	</form>
	  	<div class="format">
	  		<p>用户名：hanwei
		  	<p>密码：a
		  	<p>_(:з」∠)_
  		</div>
  	</div>
</body>
<script>
	function show() {
		$(".person").css("display","block");
	}
	function personalForm() {
		if ($("#pname").val() == "hanwei" && $("#ppassword").val() == "a") {
			var form = document.forms[0];
		    form.action = "<%=basePath%>user/getPersonalAssets";
		    form.method = "post";
		    form.submit();
		}else {
			alert("用户名/密码错误，请重新输入！");
		}
	}
</script>
</html>