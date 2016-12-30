<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Filexplorer | 后台管理登录</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="/css/_all-skins.min.css">


  <link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon">
  <link rel="Bookmark" href="/img/favicon.ico">
  <link rel="stylesheet" media="screen" href="/css/particles-style.css">


  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="/js/html5shiv.min.js"></script>
  <script src="/js/respond.min.js"></script>
  <![endif]-->
</head>
<body id="particles-js">


<div class="login-box" style="position:absolute;left:50%;margin-left:-180px;vertical-align: middle;">
  <div class="login-logo">
    <a href="#"><b>Fil</b>explorer</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body" style="background:rgba(255, 255, 255, 0);">
    <p class="login-box-msg">Sign in to start your session</p>

    <form action="/login" method="post">
      <div class="form-group has-feedback">
        <input type="username" class="form-control" placeholder="Username" id="username" name="username"/>
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Password" id="password" name="password"/>
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">

        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
        </div>
        <!-- /.col -->
      </div>
    </form>



  </div>
  <!-- /.login-box-body -->
  <c:if test="${loginFail != null}">
    <c:if test="${loginFail}">
      <div class="alert alert-danger alert-dismissible" style="top:20px;">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4><i class="icon fa fa-ban"></i> Failed!</h4>
        Invalid account.
      </div>
    </c:if>
  </c:if>

  <c:if test="${logoutSuccess != null}">
    <c:if test="${logoutSuccess}">
      <div class="alert alert-success alert-dismissible">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
        <h4><i class="icon fa fa-info"></i> Succeed!</h4>
        Logout.
      </div>
    </c:if>
  </c:if>
</div>
<!-- /.login-box -->



<!-- jQuery 2.2.0 -->
<script src="/js/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="/js/icheck.min.js"></script>

<script src="/js/particles.js"></script>
<script src="/js/particles-app.js"></script>

<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>
</body>
</html>
