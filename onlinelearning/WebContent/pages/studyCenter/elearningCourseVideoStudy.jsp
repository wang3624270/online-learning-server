<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程学习</title>
<link href="<%=request.getContextPath()%>/webFramework/common/css/video-js.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/easyui_animation.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/easyui_plus.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/icon.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/kelp/kelp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/js/basetool.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/js/video.min.js"></script>
<style>
video {
     width: 100%;
     height: 100%; 
     object-fit: cover;
}
</style>
</head>
<body>
<div style="margin:0px 0 0px 0;"></div>
	<div class="easyui-panel" >
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('elearningCurrentCourseList.jsp')"></a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('elearningCurrentCourseList.jsp')">我的课程</a>\
		<a href="#" id="courseName" class="easyui-linkbutton" data-options="plain:true" onclick="refresh()"></a>
</div>
<div style="height: 95%;width:100%;postition:fixed">
<div id="courseTree" class="easyui-accordion" style="height: 100%;width:200px;float: left;">
</div>
<div style="margin:0px 0 10px 0;position:absolute;left:200px;">
</div>
	<div id="section-panel" class="easyui-panel" title="课程内容" style="height:100%;padding:10px;">
		<!-- 插入视频插件 -->
    </div>
</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
	$(document).ready(function() {
		var courseId=getUrlValue("courseId");
		var taskId=getUrlValue("taskId");
		var data = {courseId:courseId,taskId:taskId};
		Kelp.jsonPost("../../func/courseBean/getCourseInfo", data, function(result){
			if(result.re=="1"){
				//课程名称
				$("#courseName").html(result.data.courseName+"(当前课程)");
				//章
				$.each(result.data.tree,function(i,chapter){
					var chapterContent="<ul class=\"easyui-tree\">";
				    //节
				    $.each(chapter.childList,function(i,section){
				    	chapterContent+="<li><span>"+section.folderName+"</span><ul>";
					    //本节内容
					    chapterContent+="<li><span><a onclick=\"showContent("+section.id+",'"+section.folderName+"')\">课程资源</a></span></li>";
					    chapterContent+="</ul></li>";
				    });
				    chapterContent+="</ul>";
					$('#courseTree').accordion('add', {
						title: chapter.folderName,
						content: chapterContent,
						iconCls: "icon-ok",
						selected: false
					});
				});
			}else{
				alert(result.data);
			}
		});
	});
	function showContent(id,name){
		sectionId=id;
		$('#section-panel').panel({
				'title':name
		});
		//获取资源路径
		var data1 = {sectionId:sectionId};
		Kelp.jsonPost("../../func/courseBean/getVideoSource", data1, function(result){
			if(result.re=="1"){
				if(result.data.isExist=="1"){
					//加载播放器
					var videoPlayer=$("#my-video").get(0);
					if(typeof(videoPlayer)!="undefined"){
						var myPlayer = videojs('my-video');
						myPlayer.dispose();
					}
					$("#section-panel").html("<video id='my-video' class='video-js' controls preload='none'  poster='<%=request.getContextPath()%>/webFramework/common/image/wait.jpg' ><source id='video-source' src='' type='video/webm'></video>");
					var accId=result.data.accId;
					$("#video-source").attr("src","<%=request.getContextPath()%>/accDownload?accId="+accId);
					//设置资源路径
					videojs("my-video", {}, function(){//自动播放
						// Player (this) is initialized and ready.
						var myPlayer = videojs("my-video");
						videojs("my-video").ready(function(){
							var myPlayer = this;
							myPlayer.play();
						});
					});
				}
			}else{
				alert(result.data);
			}
		});
		
	}
</script>
<script type="text/javascript">     
</script>