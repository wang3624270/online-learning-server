<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程作业管理</title>
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
    <div class="easyui-panel" title="课程作业管理" style="width:100%;padding:30px 30px;" data-options="iconCls: 'icon-save'">
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:25%">课程计划名称--课程--结束时间</th>
		      <th style="width:15%">作业题目</th>
		      <th style="width:15%">所属章节</th>
		      <th style="width:10%">提交开始时间</th>
		      <th style="width:10%">提交结束时间</th>
		      <th style="width:10%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="homeworkList">
		    <!-- 课程作业列表 -->
		  </tbody>
	   </table>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	initHomeworkList();
});
function initHomeworkList(){
	var data = {};
	Kelp.jsonPost("../../func/homeworkBean/getAllHomeworkList", data, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,teachTask){	
				if(teachTask.rowNum=="0"){
					$("#homeworkList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
							+(i+1)
							+"</td><td>"
							+teachTask.taskInfo
							+"</td><td colspan='5' style='text-align:center;'>"
							+"<a onclick='addTeachTask("+teachTask.taskId+")' class='easyui-linkbutton' style='width:300px' data-options=\"iconCls:'icon-add'\" onclick='editTeachTask("+teachTask.taskId+")'>新建作业</a>"
							+"</td></tr>");
				}else{
					$.each(teachTask.homeworkList,function(j,homework){	
						if(j==0){//第一行需要特殊处理
							$("#homeworkList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td  rowspan='"
									+(teachTask.rowNum+1)
									+"'>"
									+(i+1)
									+"</td><td rowspan='"
									+(teachTask.rowNum+1)
									+"'>"
									+teachTask.taskInfo
									+"</td><td>"
									+homework.name
									+"</td><td>"
									+homework.chapterName
									+"</td><td>"
									+homework.startTime
									+"</td><td>"
									+homework.endTime
									+"</td><td>"
									+"<a class='easyui-linkbutton' style='height:27px' onclick='editTeachTask("+teachTask.taskId+","+homework.homeworkId+")'>修改</a>"
									+"&nbsp;&nbsp;"
									+"<a class='easyui-linkbutton' style='height:27px' data-options=\"disabled:false\" onclick='deleteHomework("+homework.homeworkId+")'>删除</a>"
									+"</td></tr>");
							
						 }else{
							 $("#homeworkList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
									+homework.name
									+"</td><td>"
									+homework.chapterName
									+"</td><td>"
									+homework.startTime
									+"</td><td>"
									+homework.endTime
									+"</td><td>"
									+"<a class='easyui-linkbutton' style='height:27px' onclick='editTeachTask("+teachTask.taskId+","+homework.homeworkId+")'>修改</a>"
									+"&nbsp;&nbsp;"
									+"<a class='easyui-linkbutton' style='height:27px' data-options=\"disabled:false\" onclick='deleteHomework("+homework.homeworkId+")'>删除</a>"
									+"</td></tr>");
							
						 }
				    });
					//最后一行需要特殊处理
					$("#homeworkList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td colspan='5' style='text-align:center;'>"
							+"<a onclick='addTeachTask("+teachTask.taskId+")' class='easyui-linkbutton' style='width:300px' data-options=\"iconCls:'icon-add'\" onclick='editTeachTask("+teachTask.taskId+")'>新建作业</a>"
							+"</td></tr>");
				}
			});
			$.parser.parse($("#homeworkList"));
		}else{
			alert(result.data);
		}
	});
}
function addTeachTask(taskId){
	doReturn('addOrEditHomework.jsp?flag=add&taskId='+taskId);
}
function editTeachTask(taskId,homeworkId){
	doReturn('addOrEditHomework.jsp?flag=edit&taskId='+taskId+'&homeworkId='+homeworkId);
}
function deleteHomework(homeworkId){
	$.messager.alert('提示',"抱歉，该功能暂未开放!",'info');
	return false;
	$.messager.confirm('提示', '您确认要删除本次作业吗?', function(r){
		if (r){
			var data3 = {homeworkId:homeworkId};
	    	Kelp.jsonPost("../../func/homeworkBean/deleteHomework", data3, function(result) {
				if(result.re=="1"){
					 $("#homeworkList").empty();
					initHomeworkList();
				}else{
					alert(result.data);
				}
			});
		}
	});
}
</script>