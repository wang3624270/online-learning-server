<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>当前课程</title>
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
	<div style="margin:0px 0;"></div>
	<table id="courseTable" class="easyui-datagrid" title="当前课程" style="width:100%;height:100%"
			data-options="
				singleSelect: true,
				iconCls: 'icon-save',
				rowStyler: function(index,row){
					return 'background-color:white;color:#000000;';
				}
			">
		<thead>
			<tr>
				<th data-options="field:'num',width:80,align:'center'">序号</th>
				<th data-options="field:'courseName',width:300,align:'center'">课程名称</th>
				<th data-options="field:'courseNum',width:100,align:'center'">课程编号</th>
				<th data-options="field:'collegeName',width:150,align:'center'">开课学院</th>
				<th data-options="field:'teacherName',width:100,align:'center'">主讲</th>
				<th data-options="field:'precess',width:100,align:'center'">完成进度</th>
				<th data-options="field:'operation',width:128,align:'center'">操作</th>
			</tr>
		</thead>
	</table>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
    function downLoad(courseId,taskId){
		Kelp.url("elearningCourseVideoStudy.jsp?courseId="+courseId+"&taskId="+taskId); 
    }
	$(document).ready(function() {
		var data = {};
		Kelp.jsonPost("../../func/courseBean/getPersonalCoursesList", data, function(result) {
			if(result.re=="1"){
				$.each(result.data,function(i,course){
					$(".datagrid-btable").append("<tr onmouseover=\"strLight(this)\" onmouseout=\"strLightOut(this)\" style='font-size:12px'><td><div style=\"text-align:center;width:70px;height:auto;\" class=\"datagrid-cell\">"
							+(++i) //序号
							+"</div></td><td><div style=\"text-align:center;width:290px;height:auto;\" class=\"datagrid-cell\">"
							+course.courseName
							+"</div></td><td><div style=\"text-align:center;width:90px;height:auto;\" class=\"datagrid-cell\">"
							+course.courseNum
							+"</div></td><td><div style=\"text-align:center;width:140px;height:auto;\" class=\"datagrid-cell\">"
							+course.collegeName
							+"</div></td><td><div style=\"text-align:center;width:90px;height:auto;\" class=\"datagrid-cell\">"
							+course.teacherName
							+"</div></td><td><div style=\"text-align:center;width:90px;height:auto;\" class=\"datagrid-cell\">"
							+course.process
							+"</div></td><td align=\"center\">"
							+"<a onclick=\"downLoad('"
							+course.courseId
							+"','"
							+course.taskId
							+"')\" class=\"easyui-linkbutton c1 l-btn l-btn-small\" style=\"width: 118px;\" ><span class=\"l-btn-left\" style=\"margin-top: 0px;\"><span class=\"l-btn-text\">开始学习</span></span></a>"
							+"</td></tr>");
				});
			}else{
				alert(result.data);
			}
		});
		$('.datagrid-cell').css('font-size','12px');
	});
</script>