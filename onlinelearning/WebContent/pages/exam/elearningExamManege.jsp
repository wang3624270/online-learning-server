<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线考试管理</title>
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
    <div class="easyui-panel" title="在线考试管理" style="width:100%;height:800px;padding:10px 30px;" data-options="iconCls: 'icon-save'">
		<div style="padding:0px 0;">
		   <a onclick="addExam()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width: 200px;display: block;">创建考试</a>
	    </div> 
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:15%">考试名称</th>
		      <th style="width:15%">所属课程计划</th>
		      <th style="width:15%">所属课程</th>
		      <th style="width:10%">提交开始时间</th>
		      <th style="width:10%">提交结束时间</th>
		      <th style="width:20%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="examList">
		    <!-- 课程作业列表 -->
		  </tbody>
	   </table>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	initExamList();
});
function initExamList(){
	var data = {};
	Kelp.jsonPost("../../func/examBean/getAllExamList", data, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,exam){
				$("#examList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(i+1)
						+"</td><td>"
						+exam.examTitle
						+"</td><td>"
						+exam.taskName
						+"</td><td>"
						+exam.courseName
						+"</td><td>"
						+exam.startDate
						+"</td><td>"
						+exam.endDate
						+"</td><td style='text-align:center'>"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editExamInfo("+exam.examId+")'>考试信息</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editExamQuestion("+exam.examId+")'>考试试题</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='deleteExam("+exam.examId+")'>删除</a>"
						+"</td></tr>");
			});
			$.parser.parse($("#examList"));
		}else{
			alert(result.data);
		}
	});
}
function editExamInfo(examId){
	doReturn('addOrEditExam.jsp?flag=edit&examId='+examId);
}
function editExamQuestion(examId){
	doReturn('addOrEditExamQuestion.jsp?examId='+examId);
}
function addExam(){
	doReturn('addOrEditExam.jsp?flag=add');
}
function deleteExam(homeworkId){
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