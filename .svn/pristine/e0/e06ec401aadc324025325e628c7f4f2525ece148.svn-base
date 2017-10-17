<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/6/13
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

  request.getSession().setAttribute("ACEGI_SAVED_REQUEST_KEY",null);
  String ip=request.getRemoteAddr();


  String type = request.getParameter("type");
  String message = "";
  if (type == null) {
    message = "";
  } else if (type.equals("1")) {
    message = "帐号或密码错误！";
  } else if (type.equals("2")) {
    message = "";
  } else if (type.equals("3")) {
    message = "与服务器的连接已断开，请重新登录";
  } else if (type.equals("4")) {
    message = "校验码不正确，登录失败！";
  }else if (type.equals("6")) {
    message = "该用户已登录,同一帐号不能同时多次登录!";
  }else if (type.equals("7")) {
    message = "您不是本系统的正式用户！";
  } else if (type.equals("5")) {
    message = "对不起，您当前的帐号暂时没有访问本系统的权限!";
  }
%>
<!DOCTYPE html>
<html>

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">


  <title>登录</title>
  <link rel="shortcut icon" href="favicon.ico"> <link href="${pageContext.request.contextPath}/page/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/page/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">

  <link href="${pageContext.request.contextPath}/page/css/animate.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/page/css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
  <!--[if lt IE 8]>
  <meta http-equiv="refresh" content="0;ie.html" />
  <![endif]-->
  <%--<script>if(window.top !== window.self){ window.top.location = window.location;}</script>--%>
</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
  <div>
    <div>
    </div>
    <h3>欢迎使用</h3>

    <form class="" role="" action="${pageContext.request.contextPath}/user/UserLogin.do" method="POST">
      <div class="form-group">
        <input type="text" class="form-control" name="username" placeholder="用户名" required="">
      </div>
      <div class="form-group">
        <input type="password" class="form-control" name="password" placeholder="密码" required="">
      </div>

      <input type="submit" class="btn btn-primary block full-width m-b"></input>
      <%--<p class="text-muted text-center"> <a href="login.html#"><small>忘记密码了？</small></a> | <a href="register.html">注册一个新账号</a>--%>
      <%--</p>--%>

    </form>
  </div>
</div>
</body>
</html>
