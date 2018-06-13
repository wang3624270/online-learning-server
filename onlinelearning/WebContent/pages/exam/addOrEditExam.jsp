<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或编辑考试信息</title>
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
		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('elearningExamManege.jsp')"></a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('elearningExamManege.jsp')">在线考试管理</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="currentState">创建考试</a>
    </div>
	<div class="easyui-panel" title="创建考试" style="width:100%;padding:30px 30px;" >
		<form id="newExam" class="easyui-form" method="post" data-options="novalidate:true">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="examTitle" style="width:50%" data-options="label:'考试名称',required:true,missingMessage:'该项不允许为空'">		
				<select data-options="editable:false,valueField:'id', textField:'text'" id='taskOption'  class="easyui-combobox" name="taskId" label="课程计划:" style="width:49%"></select>
			</div>
			<div style="margin-bottom:20px">
				<input id="startTime" class="easyui-datetimebox" label="提交开始时间" labelPosition="left" style="width:50%;" data-options="editable:false">
				<input id="endTime" class="easyui-datetimebox" label="提交结束时间" labelPosition="left" style="width:49%;" data-options="editable:false">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="remark" style="width:99%;height:100px" data-options="label:'备注',required:true,multiline:true,missingMessage:'该项不允许为空'">
			</div>
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
		</div>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	initTaskOption();
	var flag=getUrlValue("flag");
	if(flag=="edit"){
		$("#currentState").html("编辑考试信息");
		$(".panel-title").html("编辑考试信息");
		var examId=getUrlValue("examId");
		data3={examId:examId};
		Kelp.jsonPost("../../func/examBean/getExamInfo", data3, function(result) {
			if(result.re=="1"){
				$('#newExam').form('load',{
					examTitle:result.data.examTitle,
					remark:result.data.remark,
				});
				$('#startTime').datetimebox('setValue', result.data.startTime); // 设置日期输入框的值
				$('#endTime').datetimebox('setValue', result.data.endTime); // 设置日期输入框的值
				$('#taskOption').combobox('setValue', result.data.taskId);
			}else{
				$.messager.alert('提示',result.data,'info');
			}
		});
	}
});
function initTaskOption(){
	data={};
	Kelp.jsonPost("../../func/examBean/getAllTeachTaskOption", data, function(result) {
		if(result.re=="1"){
			var json = [];
			var row0 = {};
			row0.id= "";
			row0.text= "请选择";
			json.push(row0);
			$.each(result.data,function(i,task){
				var row = {};
				row.id= task.taskId;
				row.text= task.taskName;
				json.push(row);
			});
			$("#taskOption").combobox("loadData", json);
		}else{
			alert(result.data);
		}
	});
}
function submitForm(){
	var examTitle=$("[name='examTitle']").val();
	var taskId=$("[name='taskId']").val();
	var startTime = $('#startTime').datetimebox('getValue'); // 获取日期输入框的值
	var endTime = $('#endTime').datetimebox('getValue'); // 获取日期输入框的值
	var remark=$("[name='remark']").val();
	if( startTime==""  || endTime=="" || examTitle=="" || taskId=="" ){
		if(taskId=="" ){
			$.messager.alert('提示','请选择课程计划！','info');
			return false;
		}
		if(startTime=="" ){
			$.messager.alert('提示','请选择提交开始时间！','info');
			return false;
		}
		if(endTime=="" ){
			$.messager.alert('提示','请选择提交结束时间！','info');
			return false;
		}
		$('#newExam').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		var examId=getUrlValue("examId");
		var flag=getUrlValue("flag");
		if(flag=="edit"){
			data4={examId:examId,examTitle:examTitle,taskId:taskId,startTime:startTime,endTime:endTime,remark:remark};
			Kelp.jsonPost("../../func/examBean/editExamInfo", data4, function(result) {
				if(result.re=="1"){
					doReturn('elearningExamManege.jsp');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}else{
			data2={examTitle:examTitle,taskId:taskId,startTime:startTime,endTime:endTime,remark:remark};
			Kelp.jsonPost("../../func/examBean/addExam", data2, function(result) {
				if(result.re=="1"){
					doReturn('elearningExamManege.jsp');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}
	}
}
</script>