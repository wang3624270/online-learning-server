<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录在线学习系统</title>
<meta name="description" content="这是一个 index 页面">
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="icon" href="images/favicon.ico" type="image/x-icon" />
<link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
<link rel="stylesheet" href="webFramework/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="webFramework/assets-1/css/amazeui.datatables.min.css" />
<link rel="stylesheet" href="webFramework/assets-1/css/app.css">
<script src="webFramework/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="webFramework/kelp/kelp.js"></script>
</head>

<body>
    <script src="webFramework/assets-1/js/theme.js"></script>
    <div class="am-g tpl-g">
        <div class="tpl-login">
            <div class="tpl-login-content">
                <div class="tpl-login-logo">
                </div>
                <form class="am-form tpl-form-line-form">
                    <div class="am-form-group">
                        <input type="text" class="tpl-form-input" id="username" placeholder="请输入账号">
                    </div>
                    <div class="am-form-group">
                        <input type="password" class="tpl-form-input" id="password" placeholder="请输入密码">
                    </div>
                    <div class="am-form-group tpl-login-remember-me">
                        <input id="remember-me" type="checkbox">
                        <label for="remember-me">
                                                                              记住密码
                        </label>
                        <font id="prompt" style="float:right;color:red;" ></font>
                    </div>
                    <div class="am-form-group">
                        <button onclick="login()" type="button" class="am-btn am-btn-primary  am-btn-block tpl-btn-bg-color-success  tpl-login-btn">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="webFramework/assets/js/amazeui.min.js"></script>
    <script src="webFramework/assets/js/app.js"></script>  
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
    //超时登录跳出iframe
	if(window.top.location.href!=location.href)      
	{         
	    window.top.location.href=location.href;      
	} 
	function login() {
		data = {
			'loginName' : $("#username").val(),
			'password' : $("#password").val()
		};
		Kelp.jsonPost("func/auth/webLogin", data, function(result) {
			if (result.reCode != 0) {
				$("#prompt").html(result.errorMessageList);
			} else {
				sessionStorage['userToken'] = result;
				Kelp.url("mainFrame.jsp");
				//alert($.toJSON(result));
			}
		});
	} 
</script>