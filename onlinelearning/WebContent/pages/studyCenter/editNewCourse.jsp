<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程信息</title>
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
		<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="refresh()">课程信息</a>
    </div>
	<div class="easyui-panel" title="添加课程" style="width:100%;padding:30px 30px;">
		<form id="newcourse" class="easyui-form" method="post" data-options="novalidate:true">
		    <div style="height:205px;margin-bottom:20px;">
		        <div style="float:left;width:100%;height:100%;">
		             <div style="margin-right:350px;height:100%;">
			             <div style="margin-bottom:23px">
							 <input class="easyui-textbox" name="courseName" style="width:99%" data-options="label:'课程名称:',required:true,missingMessage:'该项不允许为空'">							     
						 </div>
						 <div style="margin-bottom:23px">
						     <input class="easyui-textbox" name="enlishName" style="width:99%" data-options="label:'课程英文名称:',required:true,missingMessage:'该项不允许为空'">			 
						 </div>
						 <div style="margin-bottom:23px">
							 <input class="easyui-textbox" name="courseNum" style="width:50%" data-options="label:'课程编号:',required:true,missingMessage:'该项不允许为空'">
							 <select data-options="editable:false,valueField:'id', textField:'text'" id='courseTypeOption'  class="easyui-combobox" name="courseType" label="课程类型:" style="width:49%"></select>							 
						 </div>
						 <div style="margin-bottom:23px">
						     <select data-options="editable:false,valueField:'id', textField:'text'" id='collegeOption'  class="easyui-combobox" name="collegeId" label="开课学院:" style="width:50%"></select>
						     <input class="easyui-filebox" id="file" style="width:49%;" data-options="onChange:function(){readFile();},label:'课程封面',editable:false,buttonText:'选择文件',buttonAlign:'right',accept:'image/jpeg,image/gif,image/png'"/>				 
						 </div>
		             </div>
		        </div>
		        <div style="float:right;width:350px;height:100%;margin-left:-350px;">
		             <div style="width:100%;height:100%"><img id="img" src="" alt='课程封面' width='350px' height='205px' /></div>
		        </div>
		    </div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="teachGroup" style="width:50%" data-options="label:'任课教师:',required:true,missingMessage:'该项不允许为空'">
				<input class="easyui-textbox" name="book" style="width:49%" data-options="label:'指定书目:',required:true,missingMessage:'该项不允许为空'">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="reference" style="width:99%;height:100px" data-options="label:'参考资料:',multiline:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="briefIntroduction" style="width:99%;height:100px" data-options="label:'课程简介:',multiline:true">
			</div>
			<div style="margin-bottom:20px">
				
			</div>
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清空</a>
		</div>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	//加载学院下拉框
	function loadCombobox(collegeId){
		data1={};
		Kelp.jsonPost("../../func/courseBean/getAllCollegeOption", data1, function(result) {
			if(result.re=="1"){
				var json = [];
				var row0 = {};
				row0.id= "";
				row0.text= "请选择";
				json.push(row0);
				$.each(result.data,function(i,college){
					var row = {};
					row.id= college.collegeId;
					row.text= college.collegeName;
					json.push(row);
				});
				$("#collegeOption").combobox("clear").combobox("loadData", json);
				$('#collegeOption').combobox('setValue', collegeId);
			}else{
				alert(result.data);
			}
		});
	}
	function loadTypeCombobox(courseType){
		data13={type:"KCLXM"};
		Kelp.jsonPost("../../func/courseBean/getListOptionByType", data13, function(result) {
			if(result.re=="1"){
				var json = [];
				var row0 = {};
				row0.id= "";
				row0.text= "请选择";
				json.push(row0);
				$.each(result.data,function(i,option){
					var row = {};
					row.id= option.value;
					row.text= option.label;
					json.push(row);
				});
				$("#courseTypeOption").combobox("clear").combobox("loadData", json);
				$('#courseTypeOption').combobox('setValue', courseType);
			}else{
				alert(result.data);
			}
		});
	}
	//加载课程数据
	var courseId=getUrlValue("courseId");
	data3={courseId:courseId};
	Kelp.jsonPost("../../func/courseBean/editCourseInfo", data3, function(result) {
		if(result.re=="1"){
			$('#newcourse').form('load',{
				courseName:result.data.courseName,
				enlishName:result.data.courseEngName,
				courseNum:result.data.courseNum,
				teachGroup:result.data.teachGroup,
				book:result.data.book,
				reference:result.data.reference,
				briefIntroduction:result.data.briefIntroduction
			});
			loadCombobox(result.data.collegeId);
			loadTypeCombobox(result.data.courseType);
			if(result.data.coverImgAcc!=0){
				$("#img").attr("src","<%=request.getContextPath()%>/fileDownload?attachId="+result.data.coverImgAcc);
			}else{
				$("#img").attr("src","<%=request.getContextPath()%>/webFramework/common/image/default_cover_img.jpg");
			}
		}else{
			alert(result.data);
		}
	});
	
});
function clearForm(){
	$('#newcourse').form('clear');
}
function submitForm(){
	var courseName=$("[name='courseName']").val();
	var enlishName=$("[name='enlishName']").val();
	var courseNum=$("[name='courseNum']").val();
	var courseType=$("[name='courseType']").val();
	var collegeId=$("[name='collegeId']").val();
	var teachGroup=$("[name='teachGroup']").val();
	var book=$("[name='book']").val();
	var reference=$("[name='reference']").val();
	var briefIntroduction=$("[name='briefIntroduction']").val();
	if(courseName=="" || enlishName=="" || courseNum==""  || teachGroup=="" ||book=="" || collegeId==""|| courseType==""){
		if(courseType==""){
			$.messager.alert('提示','请选择课程类型！','info');
			return false;
		}
		if(collegeId==""){
			$.messager.alert('提示','请选择开课学院！','info');
			return false;
		}
		$('#newcourse').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		var courseId=getUrlValue("courseId");
		data2={courseType:courseType,courseId:courseId,courseName:courseName,enlishName:enlishName,courseNum:courseNum,collegeId:collegeId,teachGroup:teachGroup,book:book,reference:reference,briefIntroduction:briefIntroduction};
		Kelp.jsonPost("../../func/courseBean/editCourseInfoSubmit", data2, function(result) {
			if(result.re=="1"){
                //封面上传
				var img=document.getElementById("filebox_file_id_1").files[0];
		        var formData = new FormData();
		        var uploadUrl="../../func/courseBean/uploadImg";
		        formData.append("file",img);
		        formData.append("courseId",courseId);
		        $.ajax({
		            type:"post",
		            url:uploadUrl,
		            data:formData,
		            processData : false,
		            contentType : false
		        });
				doReturn('eleaningCourseManage.jsp');
			}else{
				alert(result.data);
			}
		});
	}
}
function readFile(){
	var file=document.getElementById("filebox_file_id_1").files[0];
	var reader=new FileReader();
	reader.readAsDataURL(file);
	reader.onload=function(e){
		$("#img").attr("src",this.result);
	}
}
</script>