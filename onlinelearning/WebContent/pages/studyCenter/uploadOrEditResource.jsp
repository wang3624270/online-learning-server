<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传资源</title>
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
		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'" onclick="doReturn('eleaningResourceManage.jsp')"></a>
		<a class="easyui-linkbutton" data-options="plain:true" onclick="doReturn('eleaningResourceManage.jsp')">资源管理</a>\
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refresh()" id="currentState">上传资源</a>
    </div>
	<div class="easyui-panel" title="上传资源" style="width:100%;padding:10px 10px;" >
        <div id="fileUploadContent" class="fileUploadContent"></div>	
        <small>友情提示：请选择文件或者将文件拖拽入文件框中，点击上传按钮上传；一次可上传多个文件。</small>  
    </div> 
    <!-- 提交时候的锁定框 -->
	<div id="dlg" class="easyui-dialog" title="上传资源" data-options="closed:true,draggable:false,modal:true,closable: false,iconCls:'icon-save'" style="width:200px;height:100px;padding:10px">
		<a style="color:green">正在准备上传,请不要关闭该页面……</a>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	$("#fileUploadContent").initUpload({
	    "uploadUrl":"../../func/courseBean/uploadResource",//上传文件信息地址
	    //"size":350,//文件大小限制，单位kb,默认不限制
	    //"maxFileNumber":1,//文件个数限制，为整数
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
	    "isHiddenCleanBt":false,//是否隐藏清除按钮
	    "isAutoClean":false,//是否上传完成后自动清除
	    "canDrag":true,//是否可以拖动
	    "ismultiple":true,//是否选择多文件
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
}
function onUploadFun(opt,data){
	if(data=="success"){
		$('#dlg').dialog('close');
	    //uploadTools.uploadSuccess(opt);//显示上传成功
		//uploadTools.initWithUpload(opt);
		uploadTools.initWithCleanFile(opt);
	}else{
	    uploadTools.uploadError(opt);//显示上传错误
	}
}
</script>