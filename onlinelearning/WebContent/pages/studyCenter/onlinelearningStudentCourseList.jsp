<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>当前课程</title>
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
<link rel="apple-touch-icon-precomposed" href="<%=request.getContextPath()%>/webFramework/assets/i/app-icon72x72@2x.png">
<link rel="stylesheet" href="<%=request.getContextPath()%>/webFramework/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/webFramework/assets/css/admin.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/webFramework/assets/css/app.css">
<script src="<%=request.getContextPath()%>/webFramework/assets/js/echarts.min.js"></script>
<script src="<%=request.getContextPath()%>/webFramework/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/kelp/kelp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/js/basetool.js"></script>
</head>

<body>
        <div>
            <div class="tpl-portlet-components">
                <div class="tpl-block">
                    <div class="am-g">
                        <div class="am-u-sm-12 am-u-md-3">
                            <div class="am-form-group">
                                <select data-am-selected="{btnSize: 'sm'}">
					              <option value="option1">所有类别</option>
					              <option value="option2">IT业界</option>
					              <option value="option3">数码产品</option>
					              <option value="option3">笔记本电脑</option>
					              <option value="option3">平板电脑</option>
					              <option value="option3">只能手机</option>
					              <option value="option3">超极本</option>
					            </select>
                            </div>
                        </div>
                        <div class="am-u-sm-12 am-u-md-3">
                            <div class="am-input-group am-input-group-sm">
                                <input type="text" class="am-form-field">
                                <span class="am-input-group-btn">
            <button class="am-btn  am-btn-default am-btn-success tpl-am-btn-success am-icon-search" type="button"></button>
          </span>
                            </div>
                        </div>
                    </div>
                    <div class="am-g">
                        <div id="imgList" class="tpl-table-images">

                        </div>

                    </div>
                </div>
            </div>
        </div>
    <script src="<%=request.getContextPath()%>/webFramework/assets/js/amazeui.min.js"></script>
    <script src="<%=request.getContextPath()%>/webFramework/assets/js/app.js"></script>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
    initCourseList();
	function initCourseList(){
		var data = {};
		Kelp.jsonPost("../../func/courseBean/getPersonalCoursesList", data, function(result){
			if(result.re=="1"){
				var courseImgList="";
				$.each(result.data,function(i,course){
					var src="<%=request.getContextPath()%>/webFramework/common/image/default_cover_img.jpg";
					if(course.coverImgAcc!=0){
						src="<%=request.getContextPath()%>/fileDownload?attachId="+course.coverImgAcc;
					}
					courseImgList+="<div class='am-u-sm-12 am-u-md-6 am-u-lg-4' style='float:left;'><div class='tpl-table-images-content'><div class='tpl-table-images-content-i-time'>课程结束时间："+
						course.endDate+
						"</div><div class='tpl-i-title'><span class=' tpl-1-font'>课程名称："+
						course.courseName+
						"</span></div><a href='javascript:;' class='tpl-table-images-content-i'><img src='"+src+
						"' alt='封面图片'></a><div class='tpl-table-images-content-block'><div class='tpl-i-font' style='height:42px;'>"+
	                    course.briefIntroduction+
	                    "</div><div class='tpl-i-more'><ul><li><span class='am-text-warning'>已学0%</span></li><li><span class='am-text-success tpl-1-font'>用时36小时23分</span></li><li><span class='font-green tpl-1-font'>学习至1-1 课程介绍</span></li></ul></div>"+
	                    "<div class='am-btn-toolbar'><div class='am-btn-group am-btn-group-xs tpl-edit-content-btn'><button onclick='startStudy("+course.taskId+")' type='button' class='am-btn am-btn-default am-btn-success'><span class='am-icon-hand-o-right'></span> 学习</button><button onclick='deleteCourse("+course.taskId+")' type='button' class='am-btn am-btn-default am-btn-danger' style='float:right'><span class='am-icon-trash-o'></span> 删除</button></div></div></div></div></div>";
				});
				$("#imgList").html(courseImgList);
			}else{
				alert(result.data);
			}
		});
	}
	function startStudy(taskId){
		doReturn('onlinelearningStudentCourseDetails.jsp?taskId='+taskId);
	}
	function deleteCourse(){
		alert("delete");
	}
	//图片加载失败动态替换图片
	document.addEventListener("error", function (e) {
		var elem = e.target;
		if (elem.tagName.toLowerCase() == 'img') {
		   elem.src = "<%=request.getContextPath()%>/webFramework/common/image/default_cover_img.jpg";
		}
	}, true);
</script>