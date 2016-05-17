$(function () {
	
	// bg switcher
	var $btns = $(".bg-switch .bg");
	$btns.click(function (e) {
		e.preventDefault();
		$btns.removeClass("active");
		$(this).addClass("active");
		var bg = $(this).data("img");
		console.log("bg=" + bg);
		var preurl = "<c:url value='/login/img/bgs/"+bg+"'/>";
		$("html").css("background-image", "url('" + preurl+ "')");
	});

	$(".username-warn").focus(function(e) {
		$(".username-warn").css("visibility", "hidden");
	});
	
	$(".password-warn").focus(function(e){
		$(".password-warn").css("visibility", "hidden");
	}) ;
	
	function checkEmpty(username, password) {
		var result = true;
		if (username.length == 0 || username == "") {
			console.log("username is empty!");
			$(".username-warn").css("visibility", "visible");
			result = false;
		}
		if (password.length == 0 || password == "") {
			console.log("password is empty!");
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
	
	$("#loginbtn").click(function(e) {
		var username = $("#username").val();
		var password = $("#password").val();
		if (!checkEmpty(username, password)) {
			console.log("!!!");
			return ;
		}
		$.post("auth", {"userName":username, "password":password}, function(result) {
			var responseCode = result.code;
			var responseText = result.msg;
			console.log(result);
			if ( responseCode == 0 ) {
				console.log("认证成功");
				window.location.href="admin/index";
			} else if ( responseCode == -1 ) {
				console.log("认证失败");
				$(".error-warn").text(responseText);
				$(".password-warn").css("visibility", "visible");
			}
		});
	});
	
});
