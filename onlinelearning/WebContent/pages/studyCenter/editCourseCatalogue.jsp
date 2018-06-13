<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程目录</title>
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
		<a href="#" id="currentCourse" class="easyui-linkbutton" data-options="plain:true" onclick="refresh()">课程目录</a>
    </div>
	<div class="easyui-panel" title="课程目录" style="width:100%;padding:10px 10px 0 10px;">
	    <div style="margin:0 0;"></div>
		<div class="easyui-panel" style="width:100%;max-width:100%;padding:10px 10px;">
		<div name='chapter'>
			<div name='add-chapter' style='padding:0 0;float:left;'>
			    <a class='easyui-linkbutton' data-options="iconCls:'icon-add'" onclick='addChapter(this)'>增加一章</a>
			    <a style='font-size:15px;vertical-align: middle;cursor:default'>本章名称：</a>
			    <!--
		        <a class='easyui-linkbutton' data-options="plain:true,iconCls:'icon-cancel'" onclick='deleteChapter(this)'>删除本章</a>
			     -->
			</div>
			<div name='chapter-name' style='margin-bottom:10px;margin-left:200px;'>
				<input class='easyui-textbox' id="chapter1Name" name='chapterName' data-options="prompt:'请输入本章名称(例如：第一章：绪论)'" style='margin-left:30px;width:100%;height:30px'>
			</div>
			<div id='add-section' style='padding:0 0;float:left;margin-left:90px'>
			    <a class='easyui-linkbutton' data-options="iconCls:'icon-add'" onclick='addSection(this)'>增加一节</a>
			    <a style='font-size:15px;vertical-align: middle;cursor:default'>本节名称：</a>
			    <!-- 
			    <a class='easyui-linkbutton' data-options="plain:true,iconCls:'icon-cancel'" onclick='deleteSection(this)'>删除本节</a>
			     -->
			</div>
			<div name='section' style='margin-bottom:10px;margin-left:290px'>
				<input class='easyui-textbox' id="section1Name" name='sectionName' data-options="prompt:'请输入本节名称(例如：1.1 前言)'" style='width:100%;height:30px'>
			</div>
		</div>
	    </div>
	    <div style="padding:5px 0;text-align: center;">
		    <a class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="margin:0 auto;width:15%" onclick="save()">保存</a>
		</div>
	</div>
	<!-- 提交时候的锁定框 -->
	<div id="dlg" class="easyui-dialog" title="课程目录" data-options="closed:true,draggable:false,modal:true,closable: false,iconCls:'icon-save'" style="width:200px;height:100px;padding:10px">
		<a style="color:green">保存中……</a>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	//载入已有章节信息
	var courseId=getUrlValue("courseId");
	var data2 = {courseId:courseId};
	Kelp.jsonPost("../../func/courseBean/getCourseChapterInfo", data2, function(result) {
		if(result.re=="1"){
			//课程名
			$("#currentCourse").html("课程目录(当前课程："+result.data.courseName+")");
			//章
			if(result.data.hasCreate!=1){
				return false;
			}
			var chapter=result.data.tree;
			var $jChapter=$(chapter);
			//待渲染界面内容
			var pageContent="";
			if($jChapter.size()>0){
				$.each(result.data.tree,function(i,chapter){
				    //节
				    if(i==0){//第一章单做处理
						$("#chapter1Name").textbox('setValue',chapter.folderName);//赋值
						$.each(chapter.childList,function(j,section){
						    if(j==0){
						    	$("#section1Name").textbox('setValue',section.folderName);//赋值
						    }else{
						    	var newSection=
						    		"<div name='add-section' style='padding:0 0;float:left;margin-left:90px'>"+
						    			   "<a class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" onclick='addSection(this)'>增加一节</a>"+
						    			   "<a class='easyui-linkbutton' data-options=\"plain:true,iconCls:'icon-cancel'\" onclick='deleteSection(this)'>删除本节</a>"+
						    		"</div>"+
						    		"<div name='section' style='margin-bottom:10px;margin-left:290px'>"+
						    			"<input class='easyui-textbox' name='sectionName' value='"+section.folderName+"' data-options=\"prompt:'请输入本节名称(例如：1.1 前言)'\" style='width:100%;height:30px'>"+
						    		"</div>";
						    	var targetObj = $(newSection).insertAfter($("#section1Name").next().parent());
						    	$.parser.parse(targetObj);
						    }
					    });
				    }else{
				    	pageContent+=
				    	"<div name='chapter'>"+
							"<div name='add-chapter' style='padding:0 0;float:left;'>"+
						       "<a class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" onclick='addChapter(this)'>增加一章</a>"+
						       "<a class='easyui-linkbutton' data-options=\"plain:true,iconCls:'icon-cancel'\" onclick='deleteChapter(this)'>删除本章</a>"+
							"</div>"+
							"<div name='chapter-name' style='margin-bottom:10px;margin-left:200px;'>"+
								"<input class='easyui-textbox' name='chapterName' value='"+chapter.folderName+"' data-options=\"prompt:'请输入本章名称(例如：第一章：绪论)'\" style='margin-left:30px;width:100%;height:30px'>"+
							"</div>";
					    $.each(chapter.childList,function(j,section){
						    //本节内容
						    if(j==0){
						    	//第一节单做处理
						    	pageContent+=
								"<div name='add-section' style='padding:0 0;float:left;margin-left:90px'>"+
								    "<a class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" onclick='addSection(this)'>增加一节</a>"+
								    "<a style='font-size:15px;vertical-align: middle;padding-left: 5px;cursor:default'>本节名称：</a>"+
								"</div>"+
								"<div name='section' style='margin-bottom:10px;margin-left:290px'>"+
									"<input class='easyui-textbox' name='sectionName' value='"+section.folderName+"' data-options=\"prompt:'请输入本节名称(例如：1.1 前言)'\" style='width:100%;height:30px'>"+
								"</div>"
						    }else{
						    	var newSection=
						    		"<div name='add-section' style='padding:0 0;float:left;margin-left:90px'>"+
						    			   "<a class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" onclick='addSection(this)'>增加一节</a>"+
						    			   "<a class='easyui-linkbutton' data-options=\"plain:true,iconCls:'icon-cancel'\" onclick='deleteSection(this)'>删除本节</a>"+
						    		"</div>"+
						    		"<div name='section' style='margin-bottom:10px;margin-left:290px'>"+
						    			"<input class='easyui-textbox' name='sectionName' value='"+section.folderName+"' data-options=\"prompt:'请输入本节名称(例如：1.1 前言)'\" style='width:100%;height:30px'>"+
						    		"</div>";
						    	pageContent+=newSection;
						    }
					    });
					    pageContent+="</div>";
				    }
				});
				var targetObj = $(pageContent).insertAfter($("[name='chapter']"));
				$.parser.parse(targetObj);
			}
		}else{
			alert(result.data);
		}
	});
});
function addChapter(chapter){
	var newChapter=
		"<div name='chapter'>"+
			"<div name='add-chapter' style='padding:0 0;float:left;'>"+
		       "<a class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" onclick='addChapter(this)'>增加一章</a>"+
		       "<a class='easyui-linkbutton' data-options=\"plain:true,iconCls:'icon-cancel'\" onclick='deleteChapter(this)'>删除本章</a>"+
			"</div>"+
			"<div name='chapter-name' style='margin-bottom:10px;margin-left:200px;'>"+
				"<input class='easyui-textbox' name='chapterName' data-options=\"prompt:'请输入本章名称(例如：第一章：绪论)'\" style='margin-left:30px;width:100%;height:30px'>"+
			"</div>"+
			"<div name='add-section' style='padding:0 0;float:left;margin-left:90px'>"+
			    "<a class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" onclick='addSection(this)'>增加一节</a>"+
			    "<a style='font-size:15px;vertical-align: middle;padding-left: 5px;cursor:default'>本节名称：</a>"+
			"</div>"+
			"<div name='section' style='margin-bottom:10px;margin-left:290px'>"+
				"<input class='easyui-textbox' name='sectionName' data-options=\"prompt:'请输入本节名称(例如：1.1 前言)'\" style='width:100%;height:30px'>"+
			"</div>"+
		"</div>";
	var position=chapter.parentNode.parentNode;
	var $jposition=$(position);
	var targetObj = $(newChapter).insertAfter($jposition);
	$.parser.parse(targetObj);
}
function addSection(section){
	var newSection=
		"<div name='add-section' style='padding:0 0;float:left;margin-left:90px'>"+
			   "<a class='easyui-linkbutton' data-options=\"iconCls:'icon-add'\" onclick='addSection(this)'>增加一节</a>"+
			   "<a class='easyui-linkbutton' data-options=\"plain:true,iconCls:'icon-cancel'\" onclick='deleteSection(this)'>删除本节</a>"+
		"</div>"+
		"<div name='section' style='margin-bottom:10px;margin-left:290px'>"+
			"<input class='easyui-textbox' name='sectionName' data-options=\"prompt:'请输入本节名称(例如：1.1 前言)'\" style='width:100%;height:30px'>"+
		"</div>";
	var position=section.parentNode;
	var $jposition=$(position);
	var targetObj = $(newSection).insertAfter($jposition.next());
	$.parser.parse(targetObj);
}
function deleteChapter(chapter){
	var position=chapter.parentNode.parentNode;
	var $jposition=$(position);
	$jposition.remove();
}
function deleteSection(section){
	var position=section.parentNode;
	var $jposition=$(position);
	$jposition.next().remove();
	$jposition.remove();
}
function save(){
	//锁定界面
	$('#dlg').dialog('open');
	var $chapters=$("[name='chapterName']");
	var course = [];//课程章节
	for(var i=0;i<$chapters.size();i++){
		var chapter = {};//章
		chapter.chapterName=$chapters.eq(i).val();
		var chapterSection=[];//节
		var $chapter=$chapters.eq(i).parent().parent();
		var $sections=$chapter.nextAll().find("[name='sectionName']");
		for(var j=0;j<$sections.size();j++){
			var section={};
			section.sectionName=$sections.eq(j).val();
			chapterSection.push(section);
		}
		chapter.section=chapterSection;
		course.push(chapter);
	}
	var courseId=getUrlValue("courseId");
	var data1 = {course:course,courseId:courseId};
	Kelp.jsonPost("../../func/courseBean/editCourseCatalogue", data1, function(result) {
		if(result.re=="1"){
			doReturn('eleaningCourseManage.jsp');
		}else{
			$('#dlg').dialog('close');
			$.messager.alert('提示',result.data,'info');
			return false;
		}
	});
}
</script>