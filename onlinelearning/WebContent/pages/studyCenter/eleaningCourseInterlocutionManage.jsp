<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问答管理</title>
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
	<div class="easyui-panel" title="问答管理" style="width:100%;padding:10px 30px;" data-options="iconCls: 'icon-save'">
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:20%">课程计划名称</th>
		      <th style="width:15%">课程编号</th>
		      <th style="width:20%">课程名称</th>
		      <th style="width:10%">课程类型</th>
		      <th style="width:10%">已提问</th>
		      <th style="width:10%">已回答</th>
		      <th style="width:10%">未回答</th>
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
	$(document).ready(function() {
		initCourseList();
	});
	function initCourseList(){
		$("#courseList").empty();
		var data = {};
		Kelp.jsonPost("../../func/courseBean/getAllTeachTaskInfo", data, function(result) {
			if(result.re=="1"){
				$.each(result.data,function(i,course){	
					$("#courseList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
							+(i+1)
							+"</td><td>"
							+course.taskName
							+"</td><td>"
							+course.courseNum
							+"</td><td>"
							+course.courseName
							+"</td><td>"
							+course.courseType
							+"</td><td>"
							+"<span onclick='showSubList("+course.taskId+")' class='badge color-blue' style='cursor:pointer'>"+course.questionNum+"</span>"
							+"</td><td>"
							+"<span onclick='showCheckList("+course.taskId+")' class='badge color-green' style='cursor:pointer'>"+course.answerNum+"</span>"
							+"</td><td>"
							+"<span onclick='showUnCheckList("+course.taskId+")' class='badge color-yellow' style='cursor:pointer'>"+course.unanswerNum+"</span>"
							+"</td></tr>");
				});
				$.parser.parse($("#courseList"));
			}else{
				alert(result.data);
			}
		});
	}
	function showSubList(taskId){
		doReturn("elearningInterlocutionList.jsp?taskId="+taskId+"&flag=sub");
	}
	function showCheckList(taskId){
		doReturn("elearningInterlocutionList.jsp?taskId="+taskId+"&flag=check");
	}
	function showUnCheckList(taskId){
		doReturn("elearningInterlocutionList.jsp?taskId="+taskId+"&flag=uncheck");
	}
</script>