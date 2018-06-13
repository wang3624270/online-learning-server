<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程计划管理</title>
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
    <div class="easyui-panel" title="课程计划管理" style="width:100%;padding:30px 30px;" data-options="iconCls: 'icon-save'">
		<table class="table table-celled" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:15%">课程计划名称</th>
		      <th style="width:5%">课程编号</th>
		      <th style="width:15%">课程名称</th>
		      <th style="width:15%">课程节次</th>
		      <th style="width:5%">课程公告</th>
		      <th style="width:10%">开始时间</th>
		      <th style="width:10%">结束时间</th>
		      <th style="width:20%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="teachTaskList">
		    <!-- 课程计划列表 -->
		  </tbody>
	   </table>
	   <div style="padding:5px 0;align:center;">
		   <a onclick="addTeachTask()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="margin: 0 auto;width: 200px;display: block;">新增课程计划</a>
	   </div> 
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	var data = {};
	Kelp.jsonPost("../../func/courseBean/getAllTeachTaskInfo", data, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,teachTask){				
				$("#teachTaskList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(++i)
						+"</td><td>"
						+teachTask.taskName
						+"</td><td>"
						+teachTask.courseNum
						+"</td><td>"
						+teachTask.courseName
						+"</td><td>"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editCourseSection("+teachTask.taskId+")'>节次信息</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editSectionResource("+teachTask.taskId+")'>节次资源</a>"
						+"</td><td>"
						+"<span onclick='showNews("+teachTask.taskId+")' class='badge color-green' style='cursor:pointer'>"+teachTask.newsNum+"</span>"
						+"</td><td>"
						+teachTask.startDate
						+"</td><td>"
						+teachTask.endDate
						+"</td><td>"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editTeachTaskStudent("+teachTask.taskId+")'>课程人员</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editTeachTask("+teachTask.taskId+")'>课程信息</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' data-options=\"disabled:true\" onclick='deleteTeachTask("+teachTask.taskId+")'>删除</a>"
						+"</td></tr>");
			});
			$.parser.parse($("#teachTaskList"));
		}else{
			alert(result.data);
		}
	});
});
function addTeachTask(){
	doReturn('addOrEditTeachTask.jsp?flag=add');
}
function editTeachTask(taskId){
	doReturn('addOrEditTeachTask.jsp?flag=edit&taskId='+taskId);
}
function deleteTeachTask(taskId){
	//doReturn('addOrEditTeachTask.jsp?flag=dele');
}
function showNews(taskId){
	doReturn("elearningTaskNewsManage.jsp?taskId="+taskId);
}
function editCourseSection(taskId){
	Kelp.url("eleaningCourseSectionManage.jsp?taskId="+taskId); 
}
function editSectionResource(taskId){
	Kelp.url("eleaningSectionResourceManage.jsp?taskId="+taskId); 
}
</script>