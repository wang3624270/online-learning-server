<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或编辑课程计划</title>
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
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="currentState">添加课程计划</a>
    </div>
	<div class="easyui-panel" title="添加课程计划" style="width:100%;padding:30px 30px;" >
		<form id="newTeachTask" class="easyui-form" method="post" data-options="novalidate:true">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="teachTaskName" style="width:99%" data-options="label:'课程计划名称:',required:true,missingMessage:'该项不允许为空'">		
			</div>
			<div style="margin-bottom:20px">
				<select data-options="editable:false,valueField:'id', textField:'text'" id='coursesOption'  class="easyui-combobox" name="courseId" label="课程:" style="width:50%"></select>
				<select data-options="editable:false,valueField:'id', textField:'text'" id='termsOption'  class="easyui-combobox" name="termId" label="学期:" style="width:49%"></select>
			</div>
			<div style="margin-bottom:20px">
				<input id="startTime" class="easyui-datetimebox" label="开始日期" labelPosition="left" style="width:50%;" data-options="editable:false">
				<input id="endTime" class="easyui-datetimebox" label="结束日期" labelPosition="left" style="width:49%;" data-options="editable:false">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="remark" style="width:99%;height:100px" data-options="label:'备注:',multiline:true">
			</div>
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清空</a>
		</div>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	data={};
	Kelp.jsonPost("../../func/courseBean/getAllCourseOption", data, function(result) {
		if(result.re=="1"){
			var json = [];
			var row0 = {};
			row0.id= "";
			row0.text= "请选择";
			json.push(row0);
			$.each(result.data,function(i,course){
				var row = {};
				row.id= course.courseId;
				row.text= course.courseNum+" "+course.courseName;
				json.push(row);
			});
			$("#coursesOption").combobox("clear").combobox("loadData", json);
		}else{
			alert(result.data);
		}
	});
	data1={};
	Kelp.jsonPost("../../func/courseBean/getAllTermsOption", data1, function(result) {
		if(result.re=="1"){
			var json = [];
			var row0 = {};
			row0.id= "";
			row0.text= "请选择";
			json.push(row0);
			$.each(result.data,function(i,term){
				var row = {};
				row.id= term.termId;
				row.text= term.termNum+" "+term.termName;
				json.push(row);
			});
			$("#termsOption").combobox("clear").combobox("loadData", json);
		}else{
			alert(result.data);
		}
	});
	var flag=getUrlValue("flag");
	if(flag=="edit"){
		$("#currentState").html("编辑课程计划");
		$(".panel-title").html("编辑课程计划");
		var taskId=getUrlValue("taskId");
		data3={taskId:taskId};
		Kelp.jsonPost("../../func/courseBean/getTeachTaskInfo", data3, function(result) {
			if(result.re=="1"){
				$('#newTeachTask').form('load',{
					teachTaskName:result.data.taskName,
					remark:result.data.remark
				});
				$('#coursesOption').combobox('setValue', result.data.courseId);
				$('#termsOption').combobox('setValue',  result.data.termId);
				$('#startTime').datetimebox('setValue', result.data.startDate); // 设置日期输入框的值
				$('#endTime').datetimebox('setValue', result.data.endDate); // 设置日期输入框的值
			}else{
				alert(result.data);
			}
		});
	}
});
function clearForm(){
	$('#newTeachTask').form('clear');
}
function submitForm(){
	var teachTaskName=$("[name='teachTaskName']").val();
	var courseId=$("[name='courseId']").val();
	var termId=$("[name='termId']").val();
	var startTime = $('#startTime').datetimebox('getValue'); // 获取日期输入框的值
	var endTime = $('#endTime').datetimebox('getValue'); // 获取日期输入框的值
	var remark=$("[name='remark']").val();
	if(teachTaskName=="" || courseId=="" || termId==""  || startTime=="" ||endTime=="" ){
		if(courseId=="" ){
			$.messager.alert('提示','请选择课程！','info');
			return false;
		}
		if(termId=="" ){
			$.messager.alert('提示','请选择学期！','info');
			return false;
		}
		if(startTime=="" ){
			$.messager.alert('提示','请选择开始日期！','info');
			return false;
		}
		if(endTime=="" ){
			$.messager.alert('提示','请选择结束日期！','info');
			return false;
		}
		$('#newTeachTask').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		var taskId=getUrlValue("taskId");
		var flag=getUrlValue("flag");
		if(flag=="edit"){
			data4={taskId:taskId,teachTaskName:teachTaskName,courseId:courseId,termId:termId,startTime:startTime,endTime:endTime,remark:remark};
			Kelp.jsonPost("../../func/courseBean/editTeachTask", data4, function(result) {
				if(result.re=="1"){
					doReturn('elearningTeachTaskManege.jsp');
				}else{
					alert(result.data);
				}
			});
		}else{
			data2={teachTaskName:teachTaskName,courseId:courseId,termId:termId,startTime:startTime,endTime:endTime,remark:remark};
			Kelp.jsonPost("../../func/courseBean/addTeachTask", data2, function(result) {
				if(result.re=="1"){
					doReturn('elearningTeachTaskManege.jsp');
				}else{
					alert(result.data);
				}
			});
		}
	}
	
}
</script>