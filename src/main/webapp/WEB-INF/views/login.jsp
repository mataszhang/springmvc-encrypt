<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html class="login-bg">
<head>
	<title>主播后台管理系统-登录</title>
	<meta name="keywords" content="Bootstrap模版,Bootstrap模版下载,Bootstrap教程,Bootstrap中文,后台管理系统模版,后台模版下载,后台管理系统,后台管理模版" />
	<meta name="description" content="JS代码网提供Bootstrap模版,后台管理系统模版,后台管理界面,Bootstrap教程,Bootstrap中文翻译等相关Bootstrap插件下载" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
    <!-- bootstrap -->
    <link href="<c:url value='/login/css/bootstrap/bootstrap.css'/>" rel="stylesheet">
    <link href="<c:url value='/login/css/bootstrap/bootstrap-overrides.css'/>" type="text/css" rel="stylesheet">

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/login/css/compiled/layout.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/login/css/compiled/elements.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/login/css/compiled/icons.css'/>">

    <!-- libraries -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/login/css/lib/font-awesome.css'/>">
    
    <!-- this page specific styles -->
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/login/css/compiled/signin.css'/>" />

	<!-- 业务层CSS -->
	<link href="<c:url value='/resources/css/biz/login/login.css'/>" type="text/css" rel="stylesheet">

    <!-- open sans font -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    
</head>
<body>


    <!-- background switcher -->
    <div class="bg-switch visible-desktop">
        <div class="bgs">
            <a href="#" index="5" data-img="5.jpg" class="bg active">
                <img src="<c:url value='/login/img/bgs/5.jpg'/>" />
            </a>
            <a href="#" index="6" data-img="6.jpg" class="bg">
                <img src="<c:url value='/login/img/bgs/6.jpg'/>" />
            </a>            
            <a href="#" index="7" data-img="7.jpg" class="bg">
                <img src="<c:url value='/login/img/bgs/7.jpg'/>" />
            </a>
            <a href="#" index="8" data-img="8.jpg" class="bg">
                <img src="<c:url value='/login/img/bgs/8.jpg'/>" />
            </a>
            <a href="#" index="9" data-img="9.jpg" class="bg">
                <img src="<c:url value='/login/img/bgs/9.jpg'/>" />
            </a>
            <a href="#" index="10" data-img="10.jpg" class="bg">
                <img src="<c:url value='/login/img/bgs/10.jpg'/>" />
            </a>
            <a href="#" index="11" data-img="11.jpg" class="bg">
                <img src="<c:url value='/login/img/bgs/11.jpg'/>" />
            </a>
        </div>
    </div>


    <div class="login-wrapper">
		<div class="login-container">
			<div class="center">
				<h1>
					<i class="icon-leaf green"></i> <span class="red">爱闹</span> <span class="white">后台管理系统</span>
				</h1>
			</div>
		</div>

        <div class="box">
            <div class="content-wrap">
                <div class="black-line-bottom">
                	<h6 style="text-transform:Lowercase" class="sub_title">爱闹后台管理系统</h6>
                </div>
                <!-- j_spring_security_check -->
                <form id="login-form" action="<c:url value='/auth/authCheck'/>" method="POST">
	                <p>
	                	<span class="username-text">用户名：</span>
	                	<input class="form-control" name="adminAccount" id="username" type="text" placeholder="登陆名">
	               	</p>
	                <p class="username-warn"></p>
	                <p>
	                	<span class="password-text">密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
	                	<input class="form-control" name="adminPass" id="password" type="password" placeholder="密码">
	                </p>
	                <p class="password-warn error-warn"></p>
	                
	                <!-- <input class="form-control" type="text" name="encrypt" id="encrypt"> -->
	                
	                <!-- <a class="btn-glow primary login" href="index.html">登录</a> -->
	                <!-- <button class="btn-glow primary login" id="loginbtn">登陆</button> -->
	                <input id="loginBtn" type="submit" class="btn-glow primary login" value="登陆">
                </form>
                <div class="black-line-top">
                	<h6 style="text-transform:Lowercase" class="sub_title_contact">如果忘记，请与管理员联系</h6>
                </div>
                
            </div>
        </div>

    </div>

	<!-- scripts -->
    <!-- <script src="http://code.jquery.com/jquery-latest.js"></script> -->
    <script src="<c:url value='/login/js/jquery-2.1.4.min.js'/>"></script>
    <script src="<c:url value='/login/js/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/login/js/theme.js'/>"></script>
    <%-- <script src="<c:url value='/resources/js/bz/login-control.js'/>"></script> --%>
    <script type="text/javascript" src="<c:url value='/resources/js/plugins/jquery.form.js'/>"></script>
    
    <!-- rsa js lib -->
    <%-- 
    <script type="text/javascript" src="<c:url value='/resources/js/rsa/jsbn.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/rsa/prng4.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/rsa/rng.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/rsa/rsa.js'/>"></script>
     --%>
     <script type="text/javascript" src="<c:url value='/resources/js/rsa/security.js'/>"></script>
     
	<script type="text/javascript">
 	$(function () {
 		
 		var publicKeyStr = "${publicKeyStr}";
 		var publicKeyExponent = "${publicKeyExponent}";
 		var publicKeyModules = "${publicKeyModules}";
 		
		 RSAUtils.setMaxDigits(200); 
		 var publicKey = new RSAUtils.getKeyPair(publicKeyExponent, "", publicKeyModules); 
		 
		var $btns = $(".bg-switch .bg");
		
    	// js生成随机数[5,11]
    	// 随机生成背景图片
    	var random = Math.floor(Math.random()*(11-5+1)+5);
    	console.log("random=" + random);
		var preurl = "<c:url value='/login/img/bgs/"+random+".jpg'/>";
		$("html").css("background-image", "url('" + preurl+ "')");
		$btns.removeClass("active");
		$("a[index="+random+"]").addClass("active");
		// 随机生成背景图片结束
		
		// bg switcher
		$btns.click(function (e) {
			e.preventDefault();
			$btns.removeClass("active");
			$(this).addClass("active");
			var bg = $(this).data("img");
			console.log("bg=" + bg);
			var preurl = "<c:url value='/login/img/bgs/"+bg+"'/>";
			$("html").css("background-image", "url('" + preurl+ "')");
		});

		// 自动获取焦点
		$("#username").focus();
		
		$(".username-warn").focus(function(e) {
			$(".username-warn").css("visibility", "hidden");
		});
		
		$(".password-warn").focus(function(e){
			$(".password-warn").css("visibility", "hidden");
		}) ;
		
		function checkEmpty(username, password) {
			var result = true;
			if ($.trim(username).length == 0 || $.trim(username) == "") {
				console.log("username is empty!");
				$(".username-warn").text("请输入用户名");
				$(".username-warn").css("visibility", "visible");
				result = false;
			}
			if ($.trim(password).length == 0 || $.trim(password) == "") {
				console.log("password is empty!");
				$(".password-warn").text("请输入密码");
				$(".password-warn").css("visibility", "visible");
				result = false;
			}
			return result;
		};
		
		$("#username").focus(function(e) {
			$(".username-warn").css("visibility", "hidden");
		})
		
		$("#password").focus(function(e) {
			$(".password-warn").css("visibility", "hidden");
		})
		
		document.onkeydown=function(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if (e && e.keyCode==27) { // 按 Esc 

			}
			if (e && e.keyCode==113) { // 按 F2 

			}            
			if(e && e.keyCode==13){ // enter 键
				$('#loginBtn').click();
			}
	    }; 
	    
	    
		// ajax form submit
		
		var callBackGraFunc=  function(responseText, statusText) {  
		       if (responseText.code == -1) {
		    	    // $(".password-warn").text(responseText.msg);
		    	    $(".password-warn").text("用户名密码错误！");
					$(".password-warn").css("visibility", "visible");
		       } else if (responseText.code == 0) {
		    	  // window.location.href="../admin/index";
		    	  window.location.href="../main/dashboard";
		       }
		 };
		 
		$(function() {
			 var options = {  
					dataType: 'json', 
					beforeSubmit: function() {
						var username = $("#username").val();
						var password = $("#password").val();
						var isok = checkEmpty(username, password);
						return isok;
					},
					data: {password: RSAUtils.encryptedString(publicKey, $("#password").val())},
		            success: callBackGraFunc  
		    };  
        	// jquery.form 提交表单  
			$('#login-form').submit(function() {
				$('#login-form').ajaxSubmit(options);
				return false;
			}); 

		

         
		});
		
	});
	</script>
	
</body>
</html>