<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程问题列表</title>
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
		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('eleaningCourseInterlocutionManage.jsp')" ></a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('eleaningCourseInterlocutionManage.jsp')">问答管理</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="currentState">课程问题列表</a>
    </div>
	<div class="easyui-panel" title="课程问题列表" style="width:100%;padding:10px 30px;" data-options="iconCls: 'icon-save'">
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:15%">课程计划</th>
		      <th style="width:20%">节次名称</th>
		      <th style="width:20%">问题标题</th>
		      <th style="width:10%">状态</th>
		      <th style="width:10%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="courseList">
		    <!-- 课程列表 -->
		  </tbody>
	   </table>  
	</div>
    <!-- 模态框 -->
	   <div id="add-news" class="easyui-window" title="课程问答" data-options="closed:true,modal:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false,closable:false" style="width:800px;height:570px;padding:5px;top:90px">
		<div class="easyui-layout" data-options="fit:true">
			<form id="new-news" class="easyui-form" method="post" data-options="novalidate:true">
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" name="title" style="width:99%;" data-options="label:'问题标题:',readonly:true">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" name="question" style="width:99%;height:200px" data-options="label:'问题内容:',multiline:true,readonly:true">
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-textbox" name="answer" style="width:99%;height:200px" data-options="label:'问题回复:',multiline:true">
				</div>
			</form>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a id="interlocution-submit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="editSubmit()" style="width:80px">提交</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="$('#add-news').window('close')" style="width:80px">取消</a>
			</div>
		</div>
	   </div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	var flag=getUrlValue("flag");
	var title="";
	if(flag=="sub"){
		title="已提问";
	}else if(flag=="check"){
		title="已回答";
	}else{
		title="未回答";
	}
	$("#currentState").html(title);
	initInterlocutionList();
});
function initInterlocutionList(){
	$("#courseList").empty();
	var taskId=getUrlValue("taskId");
	var flag=getUrlValue("flag");
	data1={taskId:taskId,flag:flag};
	Kelp.jsonPost("../../func/courseBean/getCourseInterlocutionList", data1, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,course){	
				$("#courseList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(i+1)
						+"</td><td>"
						+course.taskName
						+"</td><td>"
						+course.sectionName
						+"</td><td>"
						+course.titile
						+"</td><td>"
						+course.state
						+"</td><td>"
						+"<a class='easyui-linkbutton' style='height:27px;width:80px' onclick='replay("+course.questionId+")'>查看/回复</a>"
						+"</td></tr>");
			});
			$.parser.parse($("#courseList"));
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
function replay(questionId){
	$('#add-news').window('open');
	$('#interlocution-submit').attr("onclick","editSubmit("+questionId+")")
	data={questionId:questionId};
	Kelp.jsonPost("../../func/courseBean/getInterlocutionInfo", data, function(result) {
		if(result.re=="1"){
			$('#new-news').form('load',{
			title:result.data.title,
			question:result.data.question,
			answer:result.data.answer,
			});
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
function editSubmit(questionId){
	var answer=$("[name='answer']").val();
	data2={questionId:questionId,answer:answer};
	Kelp.jsonPost("../../func/courseBean/submitInterlocutionReplay", data2, function(result) {
		if(result.re=="1"){
			$('#add-news').window('close');
			initInterlocutionList();
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
</script>