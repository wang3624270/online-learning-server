<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源管理</title>
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
    <div class="easyui-panel" title="资源管理" style="width:100%;padding:10px 30px;" data-options="iconCls: 'icon-save'">
	    <div style="padding:0px 0;">
		   <a onclick="uploadResource()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width: 200px;display: block;">上传资源</a>
	    </div> 
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:25%">资源名称</th>
		      <th style="width:5%">类型</th>
		      <th style="width:10%">关联课程目录</th>
		      <th style="width:10%">关联课程节次</th>
		      <th style="width:15%">上传者</th>
		      <th style="width:15%">上传时间</th>
		      <th style="width:15%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="resourceList">
		    <!-- 课程作业列表 -->
		  </tbody>
	   </table>  
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	initSourceList();
});
function initSourceList(){
	$("#resourceList").empty();
	var data = {};
	Kelp.jsonPost("../../func/courseBean/getAllResourceList", data, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,resource){	
				$("#resourceList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(i+1)
						+"</td><td>"
						+resource.accName
						+"</td><td>"
						+resource.accType
						+"</td><td>"
						+resource.folderMap
						+"</td><td>"
						+resource.sectionMap
						+"</td><td>"
						+resource.uploader
						+"</td><td>"
						+resource.uploadDate
						+"</td><td style='text-align:center'>"
						+"<a class='easyui-linkbutton' style='height:27px' href='<%=request.getContextPath()%>/accDownload?accId="+resource.accId+"'>下载</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='delect("+resource.accId+")'>删除</a>"
						+"</td></tr>");
			});
			$.parser.parse($("#resourceList"));
		}else{
			alert(result.data);
		}
	});
}
function uploadResource(){
	doReturn('uploadOrEditResource.jsp?flag=add');
}
function delect(accId){
	var data2 = {accId:accId};
	Kelp.jsonPost("../../func/courseBean/deleteSource", data2, function(result){
		if(result.re=="1"){
			initSourceList();
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
</script>