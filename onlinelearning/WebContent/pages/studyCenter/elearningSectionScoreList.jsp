<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看课程节次评分列表</title>
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
</head>
<body>
    <div style="margin:0px 0 0px 0;"></div>
	<div class="easyui-panel" >
		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('eleaningCourseScoreManage.jsp')"></a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('eleaningCourseScoreManage.jsp')">评分管理</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="currentState">课程节次评分列表</a>
    </div>
	<div class="easyui-panel" title="课程评论列表" style="width:100%;padding:10px 30px;" data-options="iconCls: 'icon-save'">
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:10%">姓名</th>
		      <th style="width:10%">学号</th>
		      <th style="width:5%">评分</th>
		      <th style="width:45%">评论内容</th>
		      <th style="width:15%">创建时间</th>
		      <th style="width:10%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="courseList">
		    <!-- 课程列表 -->
		  </tbody>
	   </table>  
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
function initCommentList(){
	var taskId=getUrlValue("taskId");
	var data = {taskId:taskId};
	Kelp.jsonPost("../../func/courseBean/getAllCourseComment", data, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,score){	
				$("#courseList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(i+1)
						+"</td><td>"
						+score.perName
						+"</td><td>"
						+score.perNum
						+"</td><td>"
						+score.courseScoreStr
						+"</td><td>"
						+score.comment
						+"</td><td>"
						+score.createTimeStr
						+"</td><td style='text-align:center'>"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='deleteScore("+score.commentId+")'>删除</a>&nbsp;&nbsp;"
						+"</td></tr>");
			});
			$.parser.parse($("#courseList"));
		}else{
			alert(result.data);
		}
	});
}
$(document).ready(function() {
	initCommentList();
	var taskId=getUrlValue("taskId");
	var data = {taskId:taskId};
	Kelp.jsonPost("../../func/courseBean/getCourseOrSectionInfo", data, function(result) {
		if(result.re=="1"){
			$("#currentState").html("课程节次评分列表(课程计划："+result.data.taskName+")");
		}else{
			alert(result.data);
		}
	});
});
function deleteScore(commentId){
	var data1 = {commentId:commentId+""};
	Kelp.jsonPost("../../func/courseBean/deleteCourseScore", data1, function(result) {
		if(result.re=="1"){
			$("#courseList").empty();
			initCommentList();
		}else{
			alert(result.data);
		}
	});
}
</script>