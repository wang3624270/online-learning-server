<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增或编辑课程作业</title>
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
<link href="<%=request.getContextPath()%>/webFramework/common/css/iconfont.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/webFramework/common/css/fileUpload.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/js/fileUpload.js"></script>
</head>
<body>
    <div style="margin:0px 0 0px 0;"></div>
	<div class="easyui-panel" >
		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('elearningHomeworkManage.jsp')"></a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('elearningHomeworkManage.jsp')">课程作业管理</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="currentState">新增课程作业</a>
    </div>
	<div class="easyui-panel" title="新增课程作业" style="width:100%;padding:30px 30px;" >
		<form id="newHomework" class="easyui-form" method="post" data-options="novalidate:true">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="teachTaskInfo" style="width:99%" data-options="label:'课程计划名称--课程--结束时间:',disabled:true">		
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="name" style="width:50%" data-options="label:'作业题目',required:true,missingMessage:'该项不允许为空'">		
				<input class="easyui-textbox" name="chapterName" style="width:49%" data-options="label:'所属章节',required:true,missingMessage:'该项不允许为空'">		
			</div>
			<div style="margin-bottom:20px">
				<input id="startTime" class="easyui-datetimebox" label="提交开始时间" labelPosition="left" style="width:50%;" data-options="editable:false">
				<input id="endTime" class="easyui-datetimebox" label="提交结束时间" labelPosition="left" style="width:49%;" data-options="editable:false">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="content" style="width:99%;height:100px" data-options="label:'作业要求',required:true,multiline:true,missingMessage:'该项不允许为空'">
			</div>
		</form>
		<input type="text" id="homeworkId" value="" style="display:none;"/>
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" id="uploadLabel" style="width:300px;" data-options="label:'附件',editable:false,prompt:'未上传...'"/>
		    <a onclick="upload()" id="button-upload" class="easyui-linkbutton" style="display:none;">上传附件</a>
		    <a id="button-download" class="easyui-linkbutton" style="display:none;">下载查看</a>
		    <a onclick="deleteAttach()" id="button-delete" class="easyui-linkbutton" style="display:none;">删除附件</a>
		</div>
		<div style="text-indent: 10px;padding-right: 15px;">
            <div id="fileUploadContent" class="fileUploadContent" style="display:none;"></div>	
	    </div>
	    <!-- 提交时候的锁定框 -->
		<div id="dlg" class="easyui-dialog" title="上传资源" data-options="closed:true,draggable:false,modal:true,closable: false,iconCls:'icon-save'" style="width:200px;height:100px;padding:10px">
			<a style="color:green">正在准备上传,请不要关闭该页面……</a>
		</div>
		<div id="dlg1" class="easyui-dialog" title="上传资源" data-options="closed:true,draggable:false,modal:true,closable: false,iconCls:'icon-save'" style="width:200px;height:100px;padding:10px">
			<a style="color:green">正在加载上传组件,请不要关闭该页面……</a>
		</div>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm(0)" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清空</a>
		</div>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	initTeachTaskInfo();
	var flag=getUrlValue("flag");
	if(flag=="edit"){
		$("#currentState").html("编辑课程作业");
		$(".panel-title").html("编辑课程作业");
		var homeworkId=getUrlValue("homeworkId");
		$("#homeworkId").val(homeworkId);
		data3={homeworkId:homeworkId};
		Kelp.jsonPost("../../func/homeworkBean/getHomeworkInfo", data3, function(result) {
			if(result.re=="1"){
				$('#newHomework').form('load',{
					name:result.data.name,
					chapterName:result.data.chapterName,
					content:result.data.content,
				});
				$('#startTime').datetimebox('setValue', result.data.startTime); // 设置日期输入框的值
				$('#endTime').datetimebox('setValue', result.data.endTime); // 设置日期输入框的值
				//附件上传
				if(result.data.uploadState=="1"){
					$("#button-download").show();
					$("#button-delete").show();
					$("#uploadLabel").textbox('setValue', result.data.attachName);
					//更新下载组件
					$("#button-download").attr("href","<%=request.getContextPath()%>/fileDownload?attachId="+result.data.attachId);
				}else{
					$("#button-upload").show();//显示上传按钮
				}
			}else{
				$.messager.alert('提示',result.data,'info');
			}
		});
	}else{
		$("#button-upload").show();//显示上传按钮
	}
});
function initTeachTaskInfo(){
	var taskId=getUrlValue("taskId");
	data={taskId:taskId};
	Kelp.jsonPost("../../func/courseBean/getTeachTaskInfo", data, function(result) {
		if(result.re=="1"){
			$('#newHomework').form('load',{
				teachTaskInfo:result.data.taskName+"--"+result.data.courseId+result.data.courseName+"--"+result.data.endDate,
			});
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
function clearForm(){
	$('#newHomework').form('clear');
	initTeachTaskInfo();
}
function submitForm(type){//区分是否是上传附件引起的提交
	var name=$("[name='name']").val();
	var chapterName=$("[name='chapterName']").val();
	var startTime = $('#startTime').datetimebox('getValue'); // 获取日期输入框的值
	var endTime = $('#endTime').datetimebox('getValue'); // 获取日期输入框的值
	var content=$("[name='content']").val();
	if( startTime==""  || endTime=="" || name=="" || chapterName=="" || content==""){
		$("#button-upload").show();//显示上传按钮
		if(startTime=="" ){
			$.messager.alert('提示','请选择提交开始时间！','info');
			return false;
		}
		if(endTime=="" ){
			$.messager.alert('提示','请选择提交结束时间！','info');
			return false;
		}
		$('#newHomework').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		var taskId=getUrlValue("taskId");
		var homeworkId=getUrlValue("homeworkId");
		var id=$("#homeworkId").val();
		var flag=getUrlValue("flag");
		if(flag=="edit" || id!=""){
			if(homeworkId==null){
				homeworkId=id;
			}
			data4={homeworkId:homeworkId,name:name,chapterName:chapterName,startTime:startTime,endTime:endTime,content:content};
			Kelp.jsonPost("../../func/homeworkBean/editHomework", data4, function(result) {
				if(result.re=="1"){
					doReturn('elearningHomeworkManage.jsp');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}else{
			data2={taskId:taskId,name:name,chapterName:chapterName,startTime:startTime,endTime:endTime,content:content};
			Kelp.jsonPost("../../func/homeworkBean/addHomework", data2, function(result) {
				if(result.re=="1"){
					if(type=="1"){
						$("#fileUploadContent").show();//显示上传组件
						$("#homeworkId").val(result.data);
					}else{
						doReturn('elearningHomeworkManage.jsp');
					}
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}
	}
}
$(document).ready(function() {
	$("#fileUploadContent").initUpload({
	    "uploadUrl":"../../func/homeworkBean/uploadHomework",//上传文件信息地址
	    //"size":350,//文件大小限制，单位kb,默认不限制
	    "maxFileNumber":1,//文件个数限制，为整数
	    //"filelSavePath":"",//文件上传地址，后台设置的根目录
	    "beforeUpload":beforeUploadFun,//在上传前执行的函数
	    "onUpload":onUploadFun,//在上传后执行的函数
	    "autoCommit":false,//文件是否自动上传
	    //"fileType":['mp4','avi']//文件类型限制，默认不限制，注意写的是文件后缀
	    "progressUrl":"../../func/courseBean/getStatus",//可选，获取进度信息的url
	    //"scheduleStandard":true,//模拟进度的模式
	    //"selfUploadBtId":"",//自定义文件上传按钮id
	    //"velocity":100,//模拟进度速度
	    "isHiddenUploadBt":false,//是否隐藏上传按钮
	    "isHiddenCleanBt":true,//是否隐藏清除按钮
	    "isAutoClean":true,//是否上传完成后自动清除
	    "canDrag":true,//是否可以拖动
	    "ismultiple":false,//是否选择多文件
	    "showSummerProgress":true,//显示总进度条
	    "showFileItemProgress":true,//是否显示文件单个总进度
	});
});
function beforeUploadFun(opt){
	uploadTools.startUpload(opt);
    var fileNumber = uploadTools.getFileNumber(opt);
	//锁定界面
    if(fileNumber>0){
    	$('#dlg').dialog('open');
    }
	//获取已生成作业id
	var id=$("#homeworkId").val();
    opt.otherData =[{"name":"homeworkId","value":id}];
}
function onUploadFun(opt,data){
	if(data=="success"){
		$('#dlg').dialog('close');
		$("#button-download").show();//显示下载按钮
		$("#button-delete").show();//显示删除按钮
		//显示已上传附件名称
		var id=$("#homeworkId").val();
		data1={homeworkId:id};
		Kelp.jsonPost("../../func/homeworkBean/getHomeworkInfo", data1, function(result) {
			if(result.re=="1"){
				$("#uploadLabel").textbox('setValue', result.data.attachName);
				//更新下载组件
				$("#button-download").attr("href","<%=request.getContextPath()%>/fileDownload?attachId="+result.data.attachId);
			    //隐藏上传组件
			    $("#fileUploadContent").hide();
			}else{
				$.messager.alert('提示',result.data,'info');
			}
		});
	}else{
	    uploadTools.uploadError(opt);//显示上传错误
	}
}
function upload(){
	$("#button-upload").hide();//隐藏上传按钮
	$('#dlg1').dialog('open');
	var flag=getUrlValue("flag");
	if(flag=="add"){
		submitForm(1);//新增作业：上传附件之前保存作业信息
	}else{
		$("#fileUploadContent").show();//显示上传组件
	}
	$('#dlg1').dialog('close');
}
function deleteAttach(){
	var id=$("#homeworkId").val();
	var data5 = {homeworkId:id};
	Kelp.jsonPost("../../func/homeworkBean/deleteHomeworkAttach", data5, function(result){
		if(result.re=="1"){
			$("#button-download").hide();
			$("#button-delete").hide();
			$("#button-upload").show();//显示上传按钮
			$("#uploadLabel").textbox('setValue', "未上传...");
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
</script>