<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>批改课程作业</title>
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
		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('elearningSelectList.jsp')" id="back"></a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('elearningHomeworkScoreSub.jsp')">课程作业批改</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('elearningSelectList.jsp')" id="currentState">选课人员列表</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="now-title">批改课程作业</a>
    </div>
	<div class="easyui-panel" title="批改课程作业" style="width:100%;padding:30px 30px;" >
		<form id="newHomework" class="easyui-form" method="post" data-options="novalidate:true">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="perName" style="width:33%" data-options="label:'姓名',disabled:true,editable:false,required:true,missingMessage:'该项不允许为空'">		
				<input class="easyui-textbox" name="perNum" style="width:33%" data-options="label:'学号',disabled:true,editable:false,required:true,missingMessage:'该项不允许为空'">		
			    <input class="easyui-textbox" name="score" style="width:33%" data-options="label:'成绩',required:true,missingMessage:'该项不允许为空'">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="answerContent" style="width:99%;height:400px" data-options="label:'作业内容',disabled:true,editable:false,required:true,multiline:true,missingMessage:'该项不允许为空'">
			</div>
		</form>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" id="uploadLabel" style="width:300px;" data-options="label:'附件',disabled:true,editable:false,prompt:'未上传...'"/>
		    <a id="button-download" class="easyui-linkbutton" style="display:none;">下载查看</a>
		</div>
		<div style="margin-bottom:20px">
			
		</div>
	    <!-- 提交时候的锁定框 -->
		<div id="dlg" class="easyui-dialog" title="上传资源" data-options="closed:true,draggable:false,modal:true,closable: false,iconCls:'icon-save'" style="width:200px;height:100px;padding:10px">
			<a style="color:green">正在准备上传,请不要关闭该页面……</a>
		</div>
		<div style="text-align:center;padding:5px 0">
			<a id="submit" href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
		</div>
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
	$("#currentState").html(title);
	$("#currentState").attr("onclick","doReturn('elearningSelectList.jsp?homeworkId="+homeworkId+"&flag="+flag+"')");
	$("#back").attr("onclick","doReturn('elearningSelectList.jsp?homeworkId="+homeworkId+"&flag="+flag+"')");
	var stuId=getUrlValue("stuId");
	data1={homeworkId:homeworkId,stuId:stuId};
	Kelp.jsonPost("../../func/homeworkBean/getHomeworkAnswerInfo", data1, function(result) {
		if(result.re=="1"){
			$("#now-title").html("批改课程作业(课程作业："+result.data.homeworkName+")");
			$('#newHomework').form('load',{
				perName:result.data.perName,
				perNum:result.data.perNum,
				score:result.data.score,
				answerContent:result.data.answerContent,
			});
			$("#submit").attr("onclick","submitForm("+result.data.answerId+")")
			//附件上传
			if(result.data.uploadState=="1"){
				$("#button-download").show();
				$("#uploadLabel").textbox('setValue', result.data.attachName);
				//更新下载组件
				$("#button-download").attr("href","<%=request.getContextPath()%>/fileDownload?attachId="+result.data.attachId);
			}
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
});
function submitForm(id){
	var score=$("[name='score']").val();
	if( score=="" ){
		$('#newHomework').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		data2={id:id,score:score};
		Kelp.jsonPost("../../func/homeworkBean/submitHomeworkScore", data2, function(result) {
			if(result.re=="1"){
				$("#currentState").trigger("click"); 
			}else{
				$.messager.alert('提示',result.data,'info');
			}
		});
	}
}

</script>