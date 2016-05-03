<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head lang="zh">
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="format-detection" content="telephone=no" />
<meta name="renderer" content="webkit" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<title>登录 | 博客管理系统</title>

<link rel="alternate icon" type="image/png" href="${ctx}/assets/i/favicon.png">
<link rel="stylesheet" href="${ctx}/assets/css/amazeui.min.css" />
<!-- Bootstrap Core CSS -->
<link href="${ctx}/assets/css/bootstrap/bootstrap.min.css" rel="stylesheet" />

<!-- MetisMenu CSS -->
<link href="${ctx}/assets/css/metisMenu/metisMenu.min.css" rel="stylesheet" />

<!-- Custom CSS -->
<link href="${ctx}/assets/css/sb-admin-2.css" rel="stylesheet" />

<!-- Custom Fonts -->
<link href="${ctx}/assets/css/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
.header {
	text-align: center;
}

.header h1 {
	font-size: 200%;
	color: #333;
	margin-top: 30px;
}

.header p {
	font-size: 14px;
}
</style>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div align="center">
					<h2 class="page-header">博客管理系统</h2>
				</div>
				<div class="login-panel panel panel-success">
					<div class="panel-heading">
						<p class="panel-title">登录</p>
					</div>
					<div class="panel-body">
						<c:if test="${not empty msg}">
							<div class="alert alert-danger alert-dismissable">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
								<p class="am-text-center">${msg}</p>
							</div>
						</c:if>
						<form role="form" method="post" action="${ctx}/login">
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="用户名" name="username" type="text" required autofocus />
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="密码" name="password" type="password" required />
								</div>
								<%--<div class="checkbox">
									<label> <input name="rememberMe" type="checkbox" value="Remember Me">记住我
									</label>
								</div>--%>
								<!-- Change this to a button or input when using this as a form -->
								<button id="submit" class="btn btn-lg btn-success btn-block">登录</button>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="${ctx}/assets/js/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="${ctx}/assets/js/bootstrap/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="${ctx}/assets/js/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="${ctx}/assets/js/sb-admin-2.js"></script>

</body>
</html>