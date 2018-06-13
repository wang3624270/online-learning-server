<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp Demo</title>
<link href="<%=request.getContextPath()%>/webFramework/common/css/iconfont.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/webFramework/common/css/fileUpload.css" rel="stylesheet" type="text/css">
<script	type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/kelp/kelp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/js/fileUpload.js"></script>

</head>
<body>
<div id="fileUploadContent" class="fileUploadContent"></div>
<input type="button" name="button" onclick="testUpload()" value="上传"/>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$("#fileUploadContent").initUpload({
    "uploadUrl":"../../func/courseBean/fileUpload",//上传文件信息地址
    //"size":350,//文件大小限制，单位kb,默认不限制
    //"maxFileNumber":3,//文件个数限制，为整数
    //"filelSavePath":"",//文件上传地址，后台设置的根目录
    "beforeUpload":beforeUploadFun,//在上传前执行的函数
    "onUpload":onUploadFun,//在上传后执行的函数
    autoCommit:true,//文件是否自动上传
    "fileType":['png','jpg','docx','doc','zip','7z']//文件类型限制，默认不限制，注意写的是文件后缀
});
function beforeUploadFun(opt){
    opt.otherData =[{"name":"name","value":"zxm"}];
}
function onUploadFun(opt,data){
	if(data=="success"){
		uploadTools.uploadSuccess(opt);//显示上传成功
		uploadTools.initWithUpload(opt);
		uploadTools.initWithCleanFile(opt);
	}else{
	    uploadTools.uploadError(opt);//显示上传错误
	}
}
function testUpload(){
	var opt = uploadTools.getOpt("fileUploadContent");
	uploadEvent.uploadFileEvent(opt);
}
</script>