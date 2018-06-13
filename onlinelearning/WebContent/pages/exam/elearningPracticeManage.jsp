<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线练习管理</title>
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
    <div class="easyui-panel" title="在线练习管理" style="width:100%;padding:10px 30px;" data-options="iconCls: 'icon-save'">
		<div style="padding:0px 0;">
		   <a onclick="addPractice()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width: 200px;display: block;">创建在线练习</a>
	    </div> 
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:55%">在线练习名称</th>
		      <th style="width:10%">创建时间</th>
		      <th style="width:10%">是否已关联节次</th>
		      <th style="width:20%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="practiceList">
		    <!-- 列表 -->
		  </tbody>
	   </table>
	</div>
	<input type="text" id="practiceId" style="display:none;"/>
    <!-- 模态框 -->
	<div id="add-practice" class="easyui-window" title="在线练习" data-options="closed:true,modal:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false,closable:false" style="width:800px;height:130px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<form id="practice" class="easyui-form" method="post" data-options="novalidate:true">
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="practiceTitle" name="practiceTitle" style="width:99%;" data-options="label:'名称',required:true,missingMessage:'该项不允许为空'">
				</div>
			</form>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a id="practice-submit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="subPractice()" style="width:80px">提交</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="closePractice()" style="width:80px">取消</a>
			</div>
		</div>
    </div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	initPracticeList();
});
function initPracticeList(){
	$("#practiceList").empty();
	var data = {};
	Kelp.jsonPost("../../func/examBean/getAllPracticeList", data, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,exam){
				$("#practiceList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(i+1)
						+"</td><td>"
						+exam.practiceTitle
						+"</td><td>"
						+exam.startDate
						+"</td><td>"
						+""
						+"</td><td style='text-align:center'>"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editPracticeInfo("+exam.practiceId+")'>在线练习信息</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editPracticeQuestion("+exam.practiceId+")'>试题详情</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='deletePractice("+exam.practiceId+")'>删除</a>"
						+"</td></tr>");
			});
			$.parser.parse($("#practiceList"));
		}else{
			alert(result.data);
		}
	});
}
function editPracticeInfo(practiceId){
	$('#add-practice').window('open');
	$("#practiceId").val(practiceId);
	var data1 = {practiceId:practiceId};
	Kelp.jsonPost("../../func/examBean/getPracticeInfo", data1, function(result) {
		if(result.re=="1"){
			$('#practice').form('load',{
				practiceTitle:result.data.practiceTitle,
			});
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
function editPracticeQuestion(practiceId){
	doReturn('addOrEditPracticeQuestion.jsp?practiceId='+practiceId);
}
function addPractice(practiceId){
	$('#add-practice').window('open');
}
function deletePractice(homeworkId){
	$.messager.alert('提示',"抱歉，该功能暂未开放!",'info');
	return false;
}
function subPractice(){
	var practiceTitle=$("[name='practiceTitle']").val();
	if(practiceTitle=="" ){
		$('#practice').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		var practiceId=$("#practiceId").val();
		if(practiceId==""){
			data2={practiceTitle:practiceTitle};
			Kelp.jsonPost("../../func/examBean/addPracticeInfo", data2, function(result) {
				if(result.re=="1"){
					closePractice();
					initPracticeList();
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}else{
			data2={practiceId:practiceId,practiceTitle:practiceTitle};
			Kelp.jsonPost("../../func/examBean/editPracticeInfo", data2, function(result) {
				if(result.re=="1"){
					closePractice();
					initPracticeList();
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}
	}
}
function closePractice(){
	$("#practiceId").val("");
	$('#practice').form('clear');
	$('#add-practice').window('close');
}
</script>