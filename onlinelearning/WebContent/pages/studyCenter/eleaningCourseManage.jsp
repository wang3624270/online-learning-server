<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程管理</title>
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
	<div class="easyui-panel" title="课程管理" style="width:100%;padding:10px 30px;" data-options="iconCls: 'icon-save'">
	    <div style="padding:0px 0;">
		   <a onclick="addCourse()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width: 200px;display: block;">创建课程</a>
	    </div> 
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:15%">课程编号</th>
		      <th style="width:20%">课程名称</th>
		      <th style="width:15%">课程类型</th>
		      <th style="width:20%">开课学院</th>
		      <th style="width:25%">操作</th>
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
		Kelp.jsonPost("../../func/courseBean/getAllCourse", data, function(result) {
			if(result.re=="1"){
				$.each(result.data,function(i,course){	
					$("#courseList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
							+(i+1)
							+"</td><td>"
							+course.courseNum
							+"</td><td>"
							+course.courseName
							+"</td><td>"
							+course.courseType
							+"</td><td>"
							+course.collegeName
							+"</td><td style='text-align:center'>"
							+"<a class='easyui-linkbutton' style='height:27px' onclick='editCourse("+course.courseId+")'>课程信息</a>"
							+"&nbsp;&nbsp;"
							+"<a class='easyui-linkbutton' style='height:27px' onclick='editCourseCatalogue("+course.courseId+")'>课程目录</a>"
							+"&nbsp;&nbsp;"
							+"<a class='easyui-linkbutton' style='height:27px' onclick='editCourseResource("+course.courseId+")'>目录资源</a>"
							+"&nbsp;&nbsp;"
							+"<a class='easyui-linkbutton' style='height:27px' onclick='deleteCourse("+course.courseId+")'>删除</a>"
							+"</td></tr>");
				});
				$.parser.parse($("#courseList"));
			}else{
				alert(result.data);
			}
		});
	}
	function addCourse(){
		Kelp.url("addNewCourse.jsp"); 
	}
	function editCourse(courseId){
		Kelp.url("editNewCourse.jsp?courseId="+courseId); 
	}
	function deleteCourse(courseId,course){
		$.messager.alert('提示','抱歉，该功能暂未开放！','info');
		return false;
		if(confirm("您确认要删除该课程吗？")){
			var data3 = {courseId:courseId};
	    	Kelp.jsonPost("../../func/courseBean/deleteCourse", data3, function(result) {
				if(result.re=="1"){
					initCourseList();
				}else{
					alert(result.data);
				}
			});
		}
	}
	function editCourseCatalogue(courseId){
		Kelp.url("editCourseCatalogue.jsp?courseId="+courseId); 
	} 
	function editCourseResource(courseId){
		Kelp.url("eleaningCourseResourceManage.jsp?courseId="+courseId); 
	}
</script>