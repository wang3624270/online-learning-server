<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选课人员列表</title>
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
		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('elearningHomeworkScoreSub.jsp')"></a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('elearningHomeworkScoreSub.jsp')">课程作业批改</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="currentState">选课人员列表</a>
    </div>
	<div class="easyui-panel" id="main-panel" title="选课人员列表" style="width:100%;padding:30px 30px;" data-options="iconCls: 'icon-save'">
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:10%">序号</th>
		      <th style="width:18%">姓名</th>
		      <th style="width:18%">学号</th>
		      <th style="width:18%">作业提交时间</th>
		      <th style="width:18%">分数</th>
		      <th style="width:18%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="homeworkList">
		    <!-- 人员列表 -->
		  </tbody>
	   </table>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	var homeworkId=getUrlValue("homeworkId");
	var flag=getUrlValue("flag");
	var title="";
	if(flag=="select"){
		title="选课人员列表";
	}else if(flag=="sub"){
		title="已提交作业列表";
	}else{
		title="已批改作业列表";
	}
	//修改panel上的值
	$('#main-panel').panel({   
	  title: title    
	}); 
	var data = {homeworkId:homeworkId,flag:flag};
	Kelp.jsonPost("../../func/homeworkBean/getHomeworkSelectList", data, function(result) {
		if(result.re=="1"){
			$("#currentState").html(title+"(课程作业："+result.data.name+")");
			$.each(result.data.selectList,function(i,selectList){				
				$("#homeworkList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(++i)
						+"</td><td>"
						+selectList.perName
						+"</td><td>"
						+selectList.stuNum
						+"</td><td>"
						+selectList.subState
						+"</td><td>"
						+selectList.checkState
						+"</td><td>"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='check("+selectList.personId+","+selectList.state+")'>批改</a>"
						+"</td></tr>");
			});
			$.parser.parse($("#homeworkList"));
		}else{
			alert(result.data);
		}
	});
});
function check(stuId,state){
	if(state=="0"){
		$.messager.alert('提示',"该生未提交作业！",'info');
		return false;
	}
	var flag=getUrlValue("flag");
	var homeworkId=getUrlValue("homeworkId");
	doReturn("elearningHomeworkCheck.jsp?homeworkId="+homeworkId+"&stuId="+stuId+"&flag="+flag);
}
</script>