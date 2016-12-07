<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title></title>

<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- basic styles -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<link rel="stylesheet" href="static/css/beyond.min.css" />
</head>
<body>

    <div class="lock-container animated fadeInDown">
        <div class="lock-box text-align-center">
            <div class="lock-username">DIVYIA PHILLIPS</div>
            <img src="static/img/divyia.jpg" alt="divyia avatar" />
            <div class="lock-password">
                <form role="form" class="form-inline" action="index.jsp">
                    <div class="form-group">
                        <span class="input-icon">
                            <input class="form-control" placeholder="Password" type="password">
                            <i class="glyphicon glyphicon-log-in themeprimary"></i>
                        </span>
                    </div>
                </form>
            </div>

        </div>
    </div>

</body>
</html>