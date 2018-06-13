<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题库管理</title>
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
	<div class="easyui-panel" title="题库管理" style="width:100%;padding:10px 30px;" data-options="iconCls: 'icon-save'">
		<div style="padding:0px 0;">
		   <select data-options="editable:false,valueField:'id', textField:'text'" id='numberOption'  class="easyui-combobox" name="numberOption" label="选项个数:" style="width:140px">
			   <option value="1">1</option>
			   <option value="2">2</option>
			   <option value="3">3</option>
			   <option value="4" selected>4</option>
			   <option value="5">5</option>
			   <option value="6">6</option>
		   </select>&nbsp;&nbsp;
		   <a onclick="addRadio()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width: 150px;">单选题</a>&nbsp;&nbsp;
		   <a onclick="addMultiple()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width: 150px;">多选题</a>&nbsp;&nbsp;
		   <a onclick="addCompletion()" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width: 150px;">简答题</a>&nbsp;&nbsp;
		</div> 
		<table class="table table-celled table-structured" style="font-size:12px;">
		  <thead>
		    <tr>
		      <th style="width:5%">序号</th>
		      <th style="width:55%">题目</th>
		      <th style="width:10%">类型</th>
		      <th style="width:30%">操作</th>
		    </tr>
		  </thead>
		  <tbody id="examList">
		    <!-- 列表 -->
		  </tbody>
	   </table>
	</div>
	<input type="text" id="questionId" style="display:none;"/>
    <!-- 单选题模态框 -->
	<div id="add-radio" class="easyui-window" title="单项选择题" data-options="closed:true,modal:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false,closable:false" style="width:800px;height:570px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<form id="radio" class="easyui-form" method="post" data-options="novalidate:true">
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" name="question" style="width:99%;height:60px;" data-options="label:'题目',required:true,missingMessage:'该项不允许为空',multiline:true">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="option0" name="option" style="width:99%;" data-options="label:'选项1'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="option1" name="option" style="width:99%;" data-options="label:'选项2'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="option2" name="option" style="width:99%;" data-options="label:'选项3'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="option3" name="option" style="width:99%;" data-options="label:'选项4'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="option4" name="option" style="width:99%;" data-options="label:'选项5'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="option5"  name="option" style="width:99%;" data-options="label:'选项6'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" name="rightAnswer" style="width:1px;" data-options="label:'答案'">
					<input type="radio"  name="answer" value="" style="width:15px;height:15px;">选项1 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio"  name="answer" value="" style="width:15px;height:15px;">选项2 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio"  name="answer" value="" style="width:15px;height:15px;">选项3 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio"  name="answer" value="" style="width:15px;height:15px;">选项4 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio"  name="answer" value="" style="width:15px;height:15px;">选项5 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio"  name="answer" value="" style="width:15px;height:15px;">选项6 &nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" name="analysis" style="width:99%;height:150px;" data-options="label:'解析',multiline:true">
				</div>
			</form>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a id="question-submit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="submitRadio()" style="width:80px">提交</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="closeRadio()" style="width:80px">取消</a>
			</div>
		</div>
    </div>
    <!-- 多选题模态框 -->
	<div id="add-multiple" class="easyui-window" title="多项选择题" data-options="closed:true,modal:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false,closable:false" style="width:800px;height:570px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<form id="multiple" class="easyui-form" method="post" data-options="novalidate:true">
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="multiple-question" name="multiple-question" style="width:99%;height:60px;" data-options="label:'题目',required:true,missingMessage:'该项不允许为空',multiline:true">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="multiple-option0" name="multiple-option" style="width:99%;" data-options="label:'选项1'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="multiple-option1" name="multiple-option" style="width:99%;" data-options="label:'选项2'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="multiple-option2" name="multiple-option" style="width:99%;" data-options="label:'选项3'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="multiple-option3" name="multiple-option" style="width:99%;" data-options="label:'选项4'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="multiple-option4" name="multiple-option" style="width:99%;" data-options="label:'选项5'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="multiple-option5"  name="multiple-option" style="width:99%;" data-options="label:'选项6'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" name="rightAnswer" style="width:1px;" data-options="label:'答案'">
					<input type="checkbox"  name="multiple-answer" value="" style="width:15px;height:15px;">选项1 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox"  name="multiple-answer" value="" style="width:15px;height:15px;">选项2 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox"  name="multiple-answer" value="" style="width:15px;height:15px;">选项3 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox"  name="multiple-answer" value="" style="width:15px;height:15px;">选项4 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox"  name="multiple-answer" value="" style="width:15px;height:15px;">选项5 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox"  name="multiple-answer" value="" style="width:15px;height:15px;">选项6 &nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="multiple-analysis" name="multiple-analysis" style="width:99%;height:150px;" data-options="label:'解析',multiline:true">
				</div>
			</form>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a id="question-submit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="subMultiple()" style="width:80px">提交</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="closeMultiple()" style="width:80px">取消</a>
			</div>
		</div>
    </div>
    <!-- 简答题模态框 -->
	<div id="add-completion" class="easyui-window" title="简答题" data-options="closed:true,modal:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false,closable:false" style="width:800px;height:355px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<form id="completion" class="easyui-form" method="post" data-options="novalidate:true">
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="completion-question" name="completion-question" style="width:99%;height:60px;" data-options="label:'题目',required:true,missingMessage:'该项不允许为空',multiline:true">
				</div>
				<div style="margin-bottom:5px">
				    <input class="easyui-textbox" id="completion-answer" name="completion-answer" style="width:99%;" data-options="label:'答案',required:true,missingMessage:'该项不允许为空'">
				</div>
				<div style="margin-bottom:5px">
					<input class="easyui-textbox" id="completion-analysis"name="completion-analysis" style="width:99%;height:150px;" data-options="label:'解析',multiline:true">
				</div>
			</form>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a id="question-submit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="subCompletion()" style="width:80px">提交</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="closeCompletion()" style="width:80px">取消</a>
			</div>
		</div>
    </div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
$(document).ready(function() {
	initQuestionList();
});
function initQuestionList(){
	$("#examList").empty();
	data={};
	Kelp.jsonPost("../../func/examBean/getAllQustionList", data, function(result) {
		if(result.re=="1"){
			$.each(result.data,function(i,exam){
				$("#examList").append("<tr onmouseover='strLight(this)' onmouseout='strLightOut(this)' style='height:33px'><td>"
						+(i+1)
						+"</td><td>"
						+exam.question
						+"</td><td>"
						+exam.questionType
						+"</td><td style='text-align:center'>"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='editExamQuestionInfo("+exam.questionId+")'>题目信息</a>"
						+"&nbsp;&nbsp;"
						+"<a class='easyui-linkbutton' style='height:27px' onclick='deleteQuestion("+exam.questionId+")'>删除题目</a>"
						+"</td></tr>");
			});
			$.parser.parse($("#examList"));
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
function addRadio(){
	$('#add-radio').window('open');
	var number=$("#numberOption").val();
	var option=$("[name='option']");
	var answer=$("[name='answer']");
	for(var i=number;i<6;i++){
		$("#option"+i).textbox('readonly',true);  // 启用只读模式
		$(answer[i]).attr("disabled",true);
	}
}
function submitRadio(){
	var question=$("[name='question']").val();
	if(question=="" ){
		$('#radio').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		var number=$("#numberOption").val();
		var answer=$("[name='answer']");
		var flag=false;
		var optionList=[];
		for(var i=0;i<number;i++){
			var option={};
			var optionVal=$("#option"+i).val();
			var answerVal=answer[i].checked;
			if(answerVal==true){
				flag=true;
			}
			option.key=i;
			option.value=optionVal;
			option.checked=answerVal;
			optionList.push(option);
		}
		if(flag==false){
			$.messager.alert('提示',"答案不能为空,请填写后再提交！",'info');
			return false;
		}
		$('#add-radio').window('close');
		var score=$("[name='questionScore']").val();
		var question=$("[name='question']").val();
		var analysis=$("[name='analysis']").val();
		var questionId=$("#questionId").val();
		if(questionId==""){
			data2={question:question,optionList:optionList,analysis:analysis,questionScore:score};
			Kelp.jsonPost("../../func/examBean/addRadioQuestion", data2, function(result) {
				if(result.re=="1"){
					initQuestionList();
					$('#radio').form('clear');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}else{
			data2={questionId:questionId,question:question,optionList:optionList,analysis:analysis};
			Kelp.jsonPost("../../func/examBean/editExamRadioQuestion", data2, function(result) {
				if(result.re=="1"){
					initQuestionList();
					$('#radio').form('clear');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
				$("#questionId").val("");
			});
		}
	}
}
function closeRadio(){
	$("#questionId").val("");
	$('#radio').form('clear');
	$('#add-radio').window('close');
}
function addMultiple(){
	$('#add-multiple').window('open');
	var number=$("#numberOption").val();
	var option=$("[name='multiple-option']");
	var answer=$("[name='multiple-answer']");
	for(var i=number;i<6;i++){
		$("#multiple-option"+i).textbox('readonly',true);  // 启用只读模式
		$(answer[i]).attr("disabled",true);
	}
}
function subMultiple(){
	var question=$("[name='multiple-question']").val();
	if(question=="" ){
		$('#multiple').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		var number=$("#numberOption").val();
		var answer=$("[name='multiple-answer']");
		var flag=false;
		var optionList=[];
		for(var i=0;i<number;i++){
			var option={};
			var optionVal=$("#multiple-option"+i).val();
			var answerVal=answer[i].checked;
			if(answerVal==true){
				flag=true;
			}
			option.key=i;
			option.value=optionVal;
			option.checked=answerVal;
			optionList.push(option);
		}
		if(flag==false){
			$.messager.alert('提示',"答案不能为空,请填写后再提交！",'info');
			return false;
		}
		$('#add-multiple').window('close');
		var score=$("[name='questionScore']").val();
		var question=$("[name='multiple-question']").val();
		var analysis=$("[name='multiple-analysis']").val();
		var questionId=$("#questionId").val();
		if(questionId==""){
			data2={question:question,optionList:optionList,analysis:analysis,questionScore:score};
			Kelp.jsonPost("../../func/examBean/addMultipleQuestion", data2, function(result) {
				if(result.re=="1"){
					initQuestionList();
					$('#multiple').form('clear');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}else{
			data2={questionId:questionId,question:question,optionList:optionList,analysis:analysis};
			Kelp.jsonPost("../../func/examBean/editExamMultipleQuestion", data2, function(result) {
				if(result.re=="1"){
					initQuestionList();
					$('#multiple').form('clear');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
				$("#questionId").val("");
			});
		}
	}
}
function closeMultiple(){
	$("#questionId").val("");
	$('#multiple').form('clear');
	$('#add-multiple').window('close');
}
function addCompletion(){//填空题
	$('#add-completion').window('open');
}
function subCompletion(){
	var question=$("[name='completion-question']").val();
	if(question=="" ){
		$('#completion').form('submit',{
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			}
		});
	}else{
		$('#add-completion').window('close');
		var answer=$("[name='completion-answer']").val();
		var score=$("[name='questionScore']").val();
		var question=$("[name='completion-question']").val();
		var analysis=$("[name='completion-analysis']").val();
		var questionId=$("#questionId").val();
		if(questionId==""){
			data2={question:question,analysis:analysis,questionScore:score,answer:answer};
			Kelp.jsonPost("../../func/examBean/addCompletionQuestion", data2, function(result) {
				if(result.re=="1"){
					initQuestionList();
					$('#completion').form('clear');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
			});
		}else{
			data2={questionId:questionId,question:question,analysis:analysis,answer:answer};
			Kelp.jsonPost("../../func/examBean/editExamCompletionQuestion", data2, function(result) {
				if(result.re=="1"){
					initQuestionList();
					$('#completion').form('clear');
				}else{
					$.messager.alert('提示',result.data,'info');
				}
				$("#questionId").val("");
			});
		}
	}
}
function closeCompletion(){
	$("#questionId").val("");
	$('#completion').form('clear');
	$('#add-completion').window('close');
}
function editExamQuestionInfo(questionId){
	$("#questionId").val(questionId);
	data4={questionId:questionId};
	Kelp.jsonPost("../../func/examBean/getExamQuestionInfo", data4, function(result) {
		if(result.re=="1"){
			if(result.data.questionType=="1"){
				$('#add-radio').window('open');
				$('#radio').form('load',{
					question:result.data.question,
					analysis:result.data.analysis
				});
				var number=result.data.number;
				var answer=$("[name='answer']");
				for(var i=number;i<6;i++){
					$("#option"+i).textbox('readonly',true);  // 启用只读模式
					$(answer[i]).attr("disabled",true);
				}
				var optionList=result.data.optionList;//选项列表
				$.each(result.data.optionList,function(k,exam){
					$("#option"+k).textbox('setValue', exam.optionContent);
					if(exam.checked=="1"){
						$(answer[k]).prop("checked",true);
					}
				});
			}else if(result.data.questionType=="2"){
				$('#add-multiple').window('open');
				$("#multiple-question").textbox('setValue',result.data.question);  
				$("#multiple-analysis").textbox('setValue',result.data.analysis);
				var number=result.data.number;
				var answer=$("[name='multiple-answer']");
				for(var i=number;i<6;i++){
					$("#multiple-option"+i).textbox('readonly',true);  // 启用只读模式
					$(answer[i]).attr("disabled",true);
				}
				var optionList=result.data.optionList;//选项列表
				$.each(result.data.optionList,function(k,exam){
					$("#multiple-option"+k).textbox('setValue', exam.optionContent);
					if(exam.checked=="1"){
						$(answer[k]).prop("checked",true);
					}
				});
			}else{
				$('#add-completion').window('open');
				$("#completion-question").textbox('setValue',result.data.question); 
				$("#completion-answer").textbox('setValue',result.data.answer); 
				$("#completion-analysis").textbox('setValue',result.data.analysis);
			}
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
function deleteQuestion(questionId){
	data3={questionId:questionId};
	Kelp.jsonPost("../../func/examBean/deleteQuestion", data3, function(result) {
		if(result.re=="1"){
			initQuestionList();
		}else{
			$.messager.alert('提示',result.data,'info');
		}
	});
}
</script>