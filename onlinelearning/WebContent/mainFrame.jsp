<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线学习系统</title>
<meta name="description" content="这是一个 index 页面">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
<link rel="apple-touch-icon-precomposed" href="webFramework/assets/i/app-icon72x72@2x.png">
<link rel="stylesheet" href="webFramework/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="webFramework/assets/css/admin.css">
<link rel="stylesheet" href="webFramework/assets/css/app.css">
<script src="webFramework/assets/js/echarts.min.js"></script>
<script src="webFramework/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="webFramework/kelp/kelp.js"></script>
</head>

<body>
    <!-- 顶部菜单 -->
    <header class="am-topbar am-topbar-inverse admin-header">
        <div class="am-topbar-brand">
            <a href="mainFrame.jsp" class="tpl-logo" style="margin-top: 25px;">
                <img src="images/logo.gif" alt="">
            </a>
        </div>
        <div class="am-icon-list tpl-header-nav-hover-ico am-fl am-margin-right" style="margin-top: 30px;"></div>
        <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
        <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
            <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list tpl-header-list">
                <li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
                    <a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
                        <span class="am-icon-bell-o"></span> 提醒 <span class="am-badge tpl-badge-success am-round">5</span></span>
                    </a>
                    <ul class="am-dropdown-content tpl-dropdown-content">
                        <li class="tpl-dropdown-content-external">
                            <h3>你有 <span class="tpl-color-success">5</span>条提醒</h3><a href="###">全部</a></li>
                        <li class="tpl-dropdown-list-bdbc"><a href="#" class="tpl-dropdown-list-fl"><span class="am-icon-btn am-icon-plus tpl-dropdown-ico-btn-size tpl-badge-success"></span> 【预览模块】移动端 查看时 手机、电脑框隐藏。</a>
                            <span class="tpl-dropdown-list-fr">3小时前</span>
                        </li>
                        <li class="tpl-dropdown-list-bdbc"><a href="#" class="tpl-dropdown-list-fl"><span class="am-icon-btn am-icon-check tpl-dropdown-ico-btn-size tpl-badge-danger"></span> 移动端，导航条下边距处理</a>
                            <span class="tpl-dropdown-list-fr">15分钟前</span>
                        </li>
                        <li class="tpl-dropdown-list-bdbc"><a href="#" class="tpl-dropdown-list-fl"><span class="am-icon-btn am-icon-bell-o tpl-dropdown-ico-btn-size tpl-badge-warning"></span> 追加统计代码</a>
                            <span class="tpl-dropdown-list-fr">2天前</span>
                        </li>
                    </ul>
                </li>
                <li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
                    <a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
                        <span class="am-icon-comment-o"></span> 消息 <span class="am-badge tpl-badge-danger am-round">9</span></span>
                    </a>
                    <ul class="am-dropdown-content tpl-dropdown-content">
                        <li class="tpl-dropdown-content-external">
                            <h3>你有 <span class="tpl-color-danger">9</span> 条新消息</h3><a href="###">全部</a></li>
                        <li>
                            <a href="#" class="tpl-dropdown-content-message">
                                <span class="tpl-dropdown-content-photo">
                      <img src="webFramework/assets/img/user02.png" alt=""> </span>
                                <span class="tpl-dropdown-content-subject">
                      <span class="tpl-dropdown-content-from"> 王前前 </span>
                                <span class="tpl-dropdown-content-time">10分钟前 </span>
                                </span>
                                <span class="tpl-dropdown-content-font"> Amaze UI 的诞生，依托于 GitHub 及其他技术社区上一些优秀的资源；Amaze UI 的成长，则离不开用户的支持。 </span>
                            </a>
                            <a href="#" class="tpl-dropdown-content-message">
                                <span class="tpl-dropdown-content-photo">
                      <img src="webFramework/assets/img/user03.png" alt=""> </span>
                                <span class="tpl-dropdown-content-subject">
                      <span class="tpl-dropdown-content-from"> Steam </span>
                                <span class="tpl-dropdown-content-time">18分钟前</span>
                                </span>
                                <span class="tpl-dropdown-content-font"> 为了能最准确的传达所描述的问题， 建议你在反馈时附上演示，方便我们理解。 </span>
                            </a>
                        </li>

                    </ul>
                </li>
                <li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
                    <a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
                        <span class="am-icon-calendar"></span> 进度 <span class="am-badge tpl-badge-primary am-round">4</span></span>
                    </a>
                    <ul class="am-dropdown-content tpl-dropdown-content">
                        <li class="tpl-dropdown-content-external">
                            <h3>你有 <span class="tpl-color-primary">4</span> 个任务进度</h3><a href="###">全部</a></li>
                        <li>
                            <a href="javascript:;" class="tpl-dropdown-content-progress">
                                <span class="task">
                        <span class="desc">Amaze UI 用户中心 v1.2 </span>
                                <span class="percent">45%</span>
                                </span>
                                <span class="progress">
                        <div class="am-progress tpl-progress am-progress-striped"><div class="am-progress-bar am-progress-bar-success" style="width:45%"></div></div>
                    </span>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;" class="tpl-dropdown-content-progress">
                                <span class="task">
                        <span class="desc">新闻内容页 </span>
                                <span class="percent">30%</span>
                                </span>
                                <span class="progress">
                       <div class="am-progress tpl-progress am-progress-striped"><div class="am-progress-bar am-progress-bar-secondary" style="width:30%"></div></div>
                    </span>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;" class="tpl-dropdown-content-progress">
                                <span class="task">
                        <span class="desc">管理中心 </span>
                                <span class="percent">60%</span>
                                </span>
                                <span class="progress">
                        <div class="am-progress tpl-progress am-progress-striped"><div class="am-progress-bar am-progress-bar-warning" style="width:60%"></div></div>
                    </span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="am-dropdown" data-am-dropdown data-am-dropdown-toggle>
                    <a class="am-dropdown-toggle tpl-header-list-link" href="javascript:;">
                        <span class="tpl-header-list-user-nick">王前前</span><span class="tpl-header-list-user-ico"> <img src="webFramework/assets/img/user01.png"></span>
                    </a>
                    <ul class="am-dropdown-content">
                        <li><a href="#"><span class="am-icon-bell-o"></span> 资料</a></li>
                        <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
                        <li><a href="#"><span class="am-icon-power-off"></span> 退出</a></li>
                    </ul>
                </li>
                <li><a href="###" class="tpl-header-list-link"><span class="am-icon-sign-out tpl-header-list-ico-out-size" style="margin-top: 30px;"></span></a></li>
            </ul>
        </div>
    </header>
    
    <div class="tpl-page-container tpl-page-header-fixed">
        <!-- 左侧菜单 -->
        <!-- 教师端 -->
        <div class="tpl-left-nav tpl-left-nav-hover" id="teacher-menu" style="display:none;">
            <div class="tpl-left-nav-title">
                                                教师端
            </div>
            <div class="tpl-left-nav-list">
                <ul class="tpl-left-nav-menu">
                    <li class="tpl-left-nav-item" >
                        <a href="#" name="first-level" class="nav-link active" onclick="openUrl(this,'homepage.jsp')">
                            <i class="am-icon-home"></i>
                            <span>首页</span>
                        </a>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-table"></i>
                            <span>教学管理</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu">
                            <li>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/elearningTermManege.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>学期管理</span>
                                </a>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/elearningTeachTaskManege.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>课程计划管理</span>
                                </a> 
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/eleaningCourseScoreManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>评论管理</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-wpforms"></i>
                            <span>课程资源</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu">
                            <li>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/eleaningCourseManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>课程管理</span>
                                </a> 
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/eleaningResourceManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>资源管理</span>
                                </a> 
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/eleaningCourseInterlocutionManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>问答管理</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-file-o"></i>
                            <span>课程作业</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/homework/elearningHomeworkManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>课程作业管理</span>
                                </a> 
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/homework/elearningHomeworkScoreSub.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>课程作业批改</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-pencil-square-o"></i>
                            <span>考试管理</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/exam/elearningPracticeManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>在线练习</span>
                                </a> 
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/exam/elearningExamManege.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>在线考试</span>
                                </a> 
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/exam/elearningQuestionManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>题库管理</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-male"></i>
                            <span>人员管理</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li>
                                <a href="#" name="second-level" ">
                                    <i class="am-icon-angle-right"></i>
                                    <span>学生管理</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-graduation-cap"></i>
                            <span>成绩管理</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li>
                                <a href="#" name="second-level" >
                                    <i class="am-icon-angle-right"></i>
                                    <span>成绩管理</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="login.jsp" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-key"></i>
                            <span>注销</span>

                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 学生端 -->
        <div class="tpl-left-nav tpl-left-nav-hover" id="student-menu" style="display:none;">
            <div class="tpl-left-nav-title">
                                                学生端
            </div>
            <div class="tpl-left-nav-list">
                <ul class="tpl-left-nav-menu">
                    <li class="tpl-left-nav-item" >
                        <a href="#" name="first-level" class="nav-link active" onclick="openUrl(this,'homepage.jsp')">
                            <i class="am-icon-home"></i>
                            <span>首页</span>
                        </a>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-table"></i>
                            <span>我的课程</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu">
                            <li>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/onlinelearningStudentCourseList.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>课程学习</span>
                                </a>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/elearningTeachTaskManege.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>已完成课程</span>
                                </a> 
                                <!--
                                <a href="#" name="second-level" onclick="openNewWindow(this,'pages/studyCenter/videoDemo.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>视频播放</span>
                                </a>    -->
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-wpforms"></i>
                            <span>我的作业</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu">
                            <li>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/studyCenter/eleaningCourseManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>课程作业</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-file-o"></i>
                            <span>我的考试</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/homework/elearningHomeworkManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>在线考试</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-pencil-square-o"></i>
                            <span>我的成绩</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li>
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/exam/elearningPracticeManage.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>在线练习成绩</span>
                                </a> 
                                <a href="#" name="second-level" onclick="openUrl(this,'pages/exam/elearningExamManege.jsp')">
                                    <i class="am-icon-angle-right"></i>
                                    <span>在线考试成绩</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="login.jsp" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-key"></i>
                            <span>注销</span>

                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 管理端 -->
        <div class="tpl-left-nav tpl-left-nav-hover" id="manager-menu" style="display:none;">
            <div class="tpl-left-nav-title">
                                                教师端
            </div>
            <div class="tpl-left-nav-list">
                <ul class="tpl-left-nav-menu">
                    <li class="tpl-left-nav-item" >
                        <a href="#" name="first-level" class="nav-link active" onclick="openUrl(this,'homepage.jsp')">
                            <i class="am-icon-home"></i>
                            <span>首页</span>
                        </a>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-male"></i>
                            <span>人员管理</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li>
                                <a href="#" name="second-level" ">
                                    <i class="am-icon-angle-right"></i>
                                    <span>教师管理</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="javascript:;" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-graduation-cap"></i>
                            <span>成绩管理</span>
                            <i class="am-icon-angle-right tpl-left-nav-more-ico am-fr am-margin-right tpl-left-nav-more-ico-rotate"></i>
                        </a>
                        <ul class="tpl-left-nav-sub-menu" style="display: none;">
                            <li>
                                <a href="#" name="second-level" >
                                    <i class="am-icon-angle-right"></i>
                                    <span>成绩管理</span>
                                </a> 
                            </li>
                        </ul>
                    </li>
                    <li class="tpl-left-nav-item">
                        <a href="login.jsp" name="first-level" class="nav-link tpl-left-nav-link-list">
                            <i class="am-icon-key"></i>
                            <span>注销</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 主页面 -->
        <div class="tpl-content-wrapper">
            <iframe src="homepage.jsp" width="100%" onload="this.height=0" id="iframe" frameborder="0" scrolling="no" allowfullscreen="true" style="overflow-x:hidden;">
            </iframe>
        </div>
    </div>
    <script src="webFramework/assets/js/amazeui.min.js"></script>
    <script src="webFramework/assets/js/app.js"></script>  
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
/*菜单初始化*/
initMenu();
function initMenu(){
	var data = {};
	Kelp.jsonPost("func/courseBean/initMenu", data, function(result) {
		if(result.re=="1"){
			//初始化登录名和姓名
			//$("#role").before(result.data.perName);
			//$("#name .l-btn-text").html(result.data.perName);
			//$("#account-name").append(result.data.loginName);
			var perTypeCode=result.data.perTypeCode;
			if(perTypeCode=="T"){
				$("#teacher-menu").eq(0).show();
				$("#student-menu").eq(0).remove();
				$("#manager-menu").eq(0).remove();
			}else if(perTypeCode=="S"){
				$("#student-menu").eq(0).show();
				$("#teacher-menu").eq(0).remove();
				$("#manager-menu").eq(0).remove();
			}else if(perTypeCode=="M"){
				$("#manager-menu").eq(0).show();
				$("#student-menu").eq(0).remove();
				$("#teacher-menu").eq(0).remove();
			}
		}else{
			alert(result.data);
		}
	});
}
/*调整iframe使高度自适应*/
function reinitIframe(){
	var iframe = document.getElementById("iframe");
	try{
		var bHeight = iframe.contentWindow.document.body.scrollHeight;
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		var height = Math.max(bHeight, dHeight);
		if(height==0){
			height=1400;
		}
		iframe.height = height;
		//console.log(height);
	}catch (ex){
		//
	}
}
window.setInterval("reinitIframe()", 200);
function openUrl(menu,url){
	$("#iframe").attr("src",url);
	switchMenu(menu);
}
function openNewWindow(menu,url){
	window.open("http://localhost:8080/onlinelearning/"+url);   
	switchMenu(menu);
}
//点击菜单高亮切换
function switchMenu(menu){
	//清除已有高亮显示
	$("[name='first-level']").removeClass("active");
	$("[name='second-level']").removeClass("active");
	$(menu).parent().parent().prev().addClass("active");
	$(menu).addClass("active");
}
</script>