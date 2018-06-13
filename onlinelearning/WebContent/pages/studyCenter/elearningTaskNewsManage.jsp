<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程计划公告管理</title>
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
		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('elearningTeachTaskManege.jsp')"></a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('elearningTeachTaskManege.jsp')">课程计划管理</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="currentState">课程公告管理</a>
    </div>
    <div class="easyui-panel" title="课程公告管理" style="width:100%;padding:10px 30px;" data-options="iconCls: 'icon-save'">
	   <div style="padding:0px 0;align:center;">
		   <a onclick="addTaskNews()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="margin: 0 auto;width: 200px;display: block;">新增课程公告</a>
	   </div> 
		<table class="table table-celled" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:10%">公告标题</th>
		      <th style="width:65%">公告内容</th>
		      <th style="width:10%">创建时间</th>
		      <th style="width:10%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="teachTaskList">
		    <!-- 课程计划列表 -->
		  </tbody>
	   </table>
	</div>
	<div id="add-news" class="easyui-window" title="新增课程公告" data-options="closed:true,modal:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false,closable:false" style="width:500px;height:350px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<form id="new-news" class="easyui-form" method="post" data-options="novalidate:true">
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" name="title" style="width:99%" data-options="label:'公告标题:',required:true,missingMessage:'该项不允许为空'">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" name="content" style="width:99%;height:200px" data-options="label:'公告内容:',multiline:true,required:true,missingMessage:'该项不允许为空'">
				</div>
			</form>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="addNewsSubmit()" style="width:80px">发布</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#add-news').window('close')" style="width:80px">取消</a>
			</div>
		</div>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	var taskId=getUrlValue("taskId");
	data3={taskId:taskId};
	Kelp.jsonPost("../../func/courseBean/getTeachTaskInfo", data3, function(result) {
		if(result.re=="1"){
			$("#currentState").html("课程公告管理(课程计划名称："+result.data.taskName+")");
		}else{
			alert(result.data);
		}
	});
	initNewsList();
});
function initNewsList(){
	var taskId=getUrlValue("taskId");
	var data = {taskId:taskId};
	Kelp.jsonPost("../../func/courseBean/getAllTaskNewsInfo", data, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,taskNews){				
				$("#teachTaskList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(++i)
						+"</td><td>"
						+taskNews.titile
						+"</td><td>"
						+taskNews.content
						+"</td><td>"
						+taskNews.createTimeStr
						+"</td><td>"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='deleteTeachTask("+taskNews.newsId+")'>删除</a>"
						+"</td></tr>");
			});
			$.parser.parse($("#teachTaskList"));
		}else{
			alert(result.data);
		}
	});
}
function deleteTeachTask(newsId){
	data2={newsId:newsId};
	Kelp.jsonPost("../../func/courseBean/deleteTaskNews", data2, function(result) {
		if(result.re=="1"){
			$("#teachTaskList").empty();
			initNewsList();
		}else{
			alert(result.data);
		}
	});
}
function addNewsSubmit(){
	$('#new-news').form('submit',{
		onSubmit:function(){
			return $(this).form('enableValidation').form('validate');
		}
	});
	var taskId=getUrlValue("taskId");
	var title=$("[name='title']").val();
	var content=$("[name='content']").val();
	data1={taskId:taskId,title:title,content:content};
	Kelp.jsonPost("../../func/courseBean/addTaskNews", data1, function(result) {
		if(result.re=="1"){
			$('#add-news').window('close')
			$("#teachTaskList").empty();
			initNewsList();
		}else{
			alert(result.data);
		}
	});
}
function addTaskNews(){
	$('#new-news').form('clear');
	$('#add-news').window('open');
}
</script>