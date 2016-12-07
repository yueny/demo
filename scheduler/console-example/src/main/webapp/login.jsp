<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>PMS-项目管理系统</title>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="./pages/layout/top.jsp"%>

<link rel="stylesheet" href="static/css/beyond.min.css" />
<link rel="stylesheet" href="static/css/animate.min.css" />
</head>


<div class="login-container animated fadeInDown">
	<div class="loginbox bg-white">
		<div class="loginbox-title">登陆</div>
		<div class="loginbox-textbox">
			<input type="text" class="form-control" placeholder="Email" />
		</div>
		<div class="loginbox-textbox">
			<input type="text" class="form-control" placeholder="Password" />
		</div>
		<div class="loginbox-forgot">
			<a href="">Forgot Password?</a>
		</div>
		<div class="loginbox-submit">
			<input type="button" onclick="login()" class="btn btn-primary btn-small btn-block" value="Login">
		</div>
		<div class="loginbox-signup">
			<a href="register.html">Sign Up With Email</a>
		</div>
	</div>
</div>

<!--Basic Scripts-->

<!--Beyond Scripts-->
<script src="static/js/beyond.js"></script>

<!--Google Analytics::Demo Only-->
<script>

function login(){
window.location.href="index.jsp"
}
</script>
</body>

<script type="text/javascript">
	window.jQuery
			|| document
					.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<!-- 引入 -->

<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
</body>
</html>
