<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程目录资源</title>
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
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('eleaningCourseManage.jsp')"></a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('eleaningCourseManage.jsp')">课程管理</a>\
		<a href="#" id=currentCourse class="easyui-linkbutton" data-options="plain:true" onclick="refresh()"></a>
    </div>
<div style="height: 95%;width:100%;postition:fixed">
<div id="courseTree" class="easyui-accordion" style="height: 100%;width:200px;float: left;">
</div>
<div style="margin:0px 0 10px 0;position:absolute;left:210px;">
</div>
	<div id="sectionSources" class="easyui-panel" title="课程资源" style="height:100%;padding:10px;">
	    <table id="table-1" class="table table-celled table-structured" style="font-size:12px;display:none;">
		  <thead>
		    <tr>
		      <th style="width:5%;text-align:center" colspan="7">本节已关联资源</th>
		    </tr>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:25%">资源名称</th>
		      <th style="width:10%">类型</th>
		      <th style="width:10%">关联类型</th>
		      <th style="width:15%">上传者</th>
		      <th style="width:15%">上传时间</th>
		      <th style="width:20%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="sectionAccList">
		    <!-- 列表 -->
		  </tbody>
	   </table>  
	   
	   <table id="table-2" class="table table-celled table-structured" style="font-size:12px;display:none;">
		  <thead>
		    <tr>
		      <th style="width:5%;" colspan="6">
		      <a id="mapVideo" onclick="doMatching()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联视频</a>&nbsp;
		      <a id="mapMp3" onclick="doMatching()" class="easyui-linkbutton" >关联音频</a>&nbsp;
		      <a id="mapPpt" onclick="doMatching()" class="easyui-linkbutton" >关联课件</a>&nbsp;
		      <a id="mapPdf" onclick="doMatching()" class="easyui-linkbutton" >关联PDF文档</a>&nbsp;
		      <a id="mapWord" onclick="doMatching()" class="easyui-linkbutton" >关联WORD文档</a>&nbsp;
		      <a id="mapOther" onclick="doMatching()" class="easyui-linkbutton" >关联其它</a>
		      </th>
		    </tr>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:25%">资源名称</th>
		      <th style="width:10%">类型</th>
		      <th style="width:15%">上传者</th>
		      <th style="width:15%">上传时间</th>
		      <th style="width:20%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="resourceList">
		    <!-- 列表 -->
		  </tbody>
	   </table> 
    </div>
</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
    var courseId=getUrlValue("courseId");
    var courseId;
    var sectionName;
	$(document).ready(function() {
		var taskId=getUrlValue("taskId");
		var data = {courseId:courseId,taskId:taskId};
		Kelp.jsonPost("../../func/courseBean/getCourseInfo", data, function(result){
			if(result.re=="1"){
				//课程名
				$("#currentCourse").html("课程目录资源(当前课程："+result.data.courseName+")");
				//章
				$.each(result.data.tree,function(i,chapter){
					var chapterContent="<ul class=\"easyui-tree\">";
				    //节
				    $.each(chapter.childList,function(i,section){
				    	chapterContent+="<li><span>"+section.folderName+"</span><ul>";
					    //本节内容
					    chapterContent+="<li><span><a onclick=\"showContent("+section.id+",'"+section.folderName+"(资源关联)')\">资源关联</a></span></li>";
					    chapterContent+="</ul></li>";
				    });
				    chapterContent+="</ul>";
					$('#courseTree').accordion('add', {
						title: chapter.folderName,
						content: chapterContent,
						iconCls: "icon-ok",
						selected: false
					});
				});
			}else{
				alert(result.data);
			}
		});
	});
	function showContent(id,name){
		sectionId=id;
		sectionName=name;
		//将节次号放入Button中
		$("#mapVideo").attr("onclick","doMatching('VIDEO',"+sectionId+")");
		$("#mapMp3").attr("onclick","doMatching('MP3',"+sectionId+")");
		$("#mapPpt").attr("onclick","doMatching('PPT',"+sectionId+")");
		$("#mapPdf").attr("onclick","doMatching('PDF',"+sectionId+")");
		$("#mapWord").attr("onclick","doMatching('WORD',"+sectionId+")");
		$("#mapOther").attr("onclick","doMatching('OTHER',"+sectionId+")");
		//显示列表
		$("#table-1").show();
		$("#table-2").show();
		//清空已有记录
		$('#sectionSources').panel({
				'title':name
		});
		if(sectionId!="" && sectionId!="undifined"){
			//已匹配资源列表
			hasMatchList(sectionId);
			//未匹配资源列表
			hasNotMatchList(sectionId);
		}
	}
	function doMatching(type,folderId){
		var accId="";
		var selectSourcesList=document.getElementsByName("resourceId");
		for(var i=0;i<selectSourcesList.length;i++){
			if(selectSourcesList[i].checked==true){		
				accId=selectSourcesList[i].value;
				break;
			}
		}
		if(accId==""){
			$.messager.alert('提示','请至少选择一项资源 !','info');
			return false;
		}
		var data2 = {accId:accId,folderId:folderId,type:type};
		Kelp.jsonPost("../../func/courseBean/matchfolderAndResource", data2, function(result){
			if(result.re=="1"){
				//已匹配资源列表
				hasMatchList(folderId);
				//未匹配资源列表
				hasNotMatchList(folderId);
			}else{
				$.messager.alert('提示',result.data,'info');
			}
		});
	}
	//未匹配资源列表
	function hasNotMatchList(folderId){
		$("#resourceList").empty();
		var data1 = {folderId:folderId};
		Kelp.jsonPost("../../func/courseBean/getAllResourceListOfNotMatchFolder", data1, function(result){
			if(result.re=="1"){
				$.each(result.data,function(i,resource){
					var accId=resource.accId;
					$("#resourceList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
							+"<input type='radio' name='resourceId' value='"
							+accId
							+"'>&nbsp;&nbsp;"
							+(i+1)
							+"</td><td>"
							+resource.accName
							+"</td><td>"
							+resource.accType
							+"</td><td>"
							+resource.uploader
							+"</td><td>"
							+resource.uploadDate
							+"</td><td>"
							+"<a class='easyui-linkbutton' style='height:27px' href='<%=request.getContextPath()%>/accDownload?accId="+resource.accId+"'>下载</a>"
							+"</td></tr>");
				});
				$.parser.parse($("#resourceList"));
			}else{
				alert(result.data);
			}
		});
	}
	//已匹配资源列表
	function hasMatchList(folderId){
		$("#sectionAccList").empty();
		var data3 = {folderId:folderId};
		Kelp.jsonPost("../../func/courseBean/getMatchListByfolderId", data3, function(result){
			if(result.re=="1"){
				$.each(result.data,function(i,sectionAcc){
					var accId=sectionAcc.accId;
					$("#sectionAccList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"						
							+(i+1)
							+"</td><td>"
							+sectionAcc.accName
							+"</td><td>"
							+sectionAcc.accType
							+"</td><td>"
							+sectionAcc.matchType
							+"</td><td>"
							+sectionAcc.uploader
							+"</td><td>"
							+sectionAcc.uploadDate
							+"</td><td>"
							+"<a onclick='cancelMatch("+folderId+","+sectionAcc.id+")' class='easyui-linkbutton' style='height:27px'>取消关联</a>"
							+"</td></tr>");
				});
				$.parser.parse($("#sectionAccList"));
			}else{
				alert(result.data);
			}
		});
	}
	function cancelMatch(folderId,id){
		var data2 = {id:id};
		Kelp.jsonPost("../../func/courseBean/cancelMatchFolderAndResource", data2, function(result){
			if(result.re=="1"){
				//已匹配资源列表
				hasMatchList(folderId);
				//未匹配资源列表
				hasNotMatchList(folderId);
			}else{
				$.messager.alert('提示',result.data,'info');
			}
		});
	}
</script>