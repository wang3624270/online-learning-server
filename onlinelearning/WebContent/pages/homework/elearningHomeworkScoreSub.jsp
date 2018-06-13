<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程作业批改</title>
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
    <div class="easyui-panel" title="课程作业批改" style="width:100%;padding:30px 30px;" data-options="iconCls: 'icon-save'">
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:25%">课程计划名称--课程--结束时间</th>
		      <th style="width:15%">作业题目</th>
		      <th style="width:15%">所属章节</th>
		      <th style="width:10%">提交截止时间</th>
		      <th style="width:10%">选课人数</th>
		      <th style="width:10%">已提交作业</th>
		      <th style="width:10%">已批改</th>
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
							+"</td><td colspan='6' style='text-align:center;'>"
							+"该课程无课程作业"
							+"</td></tr>");
				}else{
					$.each(teachTask.homeworkList,function(j,homework){	
						if(j==0){//第一行需要特殊处理
							$("#homeworkList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td  rowspan='"
									+(teachTask.rowNum)
									+"'>"
									+(i+1)
									+"</td><td rowspan='"
									+(teachTask.rowNum)
									+"'>"
									+teachTask.taskInfo
									+"</td><td>"
									+homework.name
									+"</td><td>"
									+homework.chapterName
									+"</td><td>"
									+homework.endTime
									+"</td><td>"
									+"<span onclick='showSelectList("+homework.homeworkId+")' class='badge color-blue' style='cursor:pointer'>"+teachTask.stuCount+"</span>"
									+"</td><td>"
									+"<span onclick='showSubList("+homework.homeworkId+")' class='badge color-yellow' style='cursor:pointer'>"+homework.submitNum+"</span>"
									+"</td><td>"
									+"<span onclick='showCheckList("+homework.homeworkId+")' class='badge color-green' style='cursor:pointer'>"+homework.checkNum+"</span>"
									+"</td></tr>");
							
						 }else{
							 $("#homeworkList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
									+homework.name
									+"</td><td>"
									+homework.chapterName
									+"</td><td>"
									+homework.endTime
									+"</td><td>"
									+"<span onclick='showSelectList("+homework.homeworkId+")' class='badge color-blue' style='cursor:pointer'>"+teachTask.stuCount+"</span>"
									+"</td><td>"
									+"<span onclick='showSubList("+homework.homeworkId+")' class='badge color-yellow' style='cursor:pointer'>"+homework.submitNum+"</span>"
									+"</td><td>"
									+"<span onclick='showCheckList("+homework.homeworkId+")' class='badge color-green' style='cursor:pointer'>"+homework.checkNum+"</span>"
									+"</td></tr>");
							
						 }
				    });
				}
			});
			$.parser.parse($("#homeworkList"));
		}else{
			alert(result.data);
		}
	});
}
function showSelectList(homeworkId){
	doReturn("elearningSelectList.jsp?homeworkId="+homeworkId+"&flag=select");
}
function showSubList(homeworkId){
	doReturn("elearningSelectList.jsp?homeworkId="+homeworkId+"&flag=sub");
}
function showCheckList(homeworkId){
	doReturn("elearningSelectList.jsp?homeworkId="+homeworkId+"&flag=check");
}
</script>