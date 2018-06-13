<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学期管理</title>
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
	<div style="margin:0px 0;"></div>
	<table id="courseTable" class="easyui-datagrid" title="学期管理" style="width:100%;"
			data-options="
				singleSelect: true,
				iconCls: 'icon-save',
				rowStyler: function(index,row){
					return 'background-color:white;color:#000000;';
				}
			">
		<thead>
			<tr>
				<th data-options="field:'num',width:80,align:'center'">序号</th>
				<th data-options="field:'courseName',width:300,align:'center'">学期名称</th>
				<th data-options="field:'courseNum',width:110,align:'center'">学期编号</th>
				<th data-options="field:'collegeName',width:110,align:'center'">状态</th>
				<th data-options="field:'operation',width:100,align:'center'">操作</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog" title="添加学期" style="width:400px;height:220px;padding:10px"
			data-options="
				iconCls: 'icon-save',
				modal:true,
				buttons: [{
					text:'添加',
					iconCls:'icon-ok',
					handler:function(){
						addTerm();
					}
				},{
					text:'取消',
					handler:function(){
						$('#dlg').dialog('close');
					}
				}],
				closed: true,
			">
		<div id="newterm" style="width:300px;margin:0 auto;">
		    <div style="margin-top:20px;margin-bottom:20px">
				<label for="name">学期名称:</label>
				<input class="easyui-validatebox" type="text" id="termName" style="height: 20px;width: 220px;border: 1px solid #cccccc;"/>
		    </div>
		    <div>
				<label for="name">学期编号:</label>
				<input class="easyui-validatebox" type="text" id="termNum"  style="height: 20px;width: 220px;border: 1px solid #cccccc;"/>
		    </div>
		</div>
	</div>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
    function setTerm(termId,term){
    	var data1 = {termId:termId};
    	Kelp.jsonPost("../../func/courseBean/setTerm", data1, function(result) {
			if(result.re=="1"){
				refresh();
				alert(result.data);
			}else{
				alert(result.data);
			}
		});
    }
    function deleteTerm(termId,term){
    	$.messager.alert('提示','抱歉，该功能暂未开放！','info');
		return false;
    	if(confirm("您确认要删除该学期吗？")){
	    	var data2 = {termId:termId};
	    	Kelp.jsonPost("../../func/courseBean/deleteTerm", data2, function(result) {
				if(result.re=="1"){
					term.parentNode.parentNode.remove();
				}else{
					alert(result.data);
				}
			});
    	}
    }
	$(document).ready(function() {
		var data = {};
		Kelp.jsonPost("../../func/courseBean/getTermInfo", data, function(result) {
			if(result.re=="1"){
				$.each(result.data,function(i,term){
					var visible="<a onclick=\"setTerm('"
						+term.termId
						+"',this)\" class=\"easyui-linkbutton c1 l-btn l-btn-small\" style=\"width: 90px;\" ><span class=\"l-btn-left\" style=\"margin-top: 0px;\"><span class=\"l-btn-text\">设置当前学期</span></span></a>";
					if(term.isVisible==1){
						visible="当前学期";
					}
					$(".datagrid-btable").append("<tr  id='"
							+term.termId
							+"' onmouseover=\"strLight(this)\" onmouseout=\"strLightOut(this)\" style='font-size:12px'><td><div style=\"text-align:center;width:70px;height:auto;\" class=\"datagrid-cell\">"
							+(++i) //序号
							+"</div></td><td><div style=\"text-align:center;width:290px;height:auto;\" class=\"datagrid-cell\">"
							+term.termName
							+"</div></td><td><div style=\"text-align:center;width:100px;height:auto;\" class=\"datagrid-cell\">"
							+term.termNum
							+"</div></td><td><div style=\"text-align:center;width:100px;height:auto;\" class=\"datagrid-cell\">"
							+visible
							+"</div></td><td align=\"center\">"
							+"<a onclick=\"editTerm('"
							+term.termId
							+"',this)\" class=\"easyui-linkbutton l-btn l-btn-small\" ><span class=\"l-btn-left\"><span class=\"l-btn-text\">修改</span></span></a>"
							+"&nbsp;&nbsp;"
							+"<a onclick=\"deleteTerm('"
							+term.termId
							+"',this)\" class=\"easyui-linkbutton l-btn l-btn-small\" ><span class=\"l-btn-left\"><span class=\"l-btn-text\">删除</span></span></a>"
							+"</td></tr>");
				});
				$(".datagrid-btable").append("<a onclick=\"$('#dlg').dialog('open')\" class=\"easyui-linkbutton l-btn l-btn-small\" data-options=\"iconCls:'icon-add'\" ><span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">添加学期</span><span class=\"l-btn-icon icon-add\">&nbsp;</span></span></a>");
			}else{
				alert(result.data);
			}
		});
		$('.datagrid-cell').css('font-size','12px');
	});
	function addTerm(){
		var termName=$("#termName").val();
		var termNum=$("#termNum").val();
		$(".msg").each(function(index,element){
			element.remove();
		});
		if(termName==""){
			$("#newterm").append("<font class='msg' style='color:red'>学期名称不能为空;</font>");
			return false;
		}
		if(termNum==""){
			$("#newterm").append("<font class='msg' style='color:red'>学期编号不能为空;</font>");
			return false;
		}	
		var data3 = {termName:termName,termNum:termNum};
    	Kelp.jsonPost("../../func/courseBean/addTerm", data3, function(result) {
			if(result.re=="1"){
				refresh();
			}else{
				alert(result.data);
			}
		});
	}
	function editTerm(termId,edit){
		var parent1=edit.parentNode.parentNode.childNodes[1];
		var parent2=edit.parentNode.parentNode.childNodes[2];
		var parent4=edit.parentNode.parentNode.childNodes[4];
		var termName=parent1.childNodes[0].innerHTML;
		var termNum=parent2.childNodes[0].innerHTML;
		parent1.childNodes[0].remove();
		parent2.childNodes[0].remove();
		parent1.setAttribute("align","center");
		parent2.setAttribute("align","center");
		parent1.innerHTML="<input type='text' id='editTermName' value='"+termName+"' style='height: 23px;border: 1px solid #cccccc;'/>";
		parent2.innerHTML="<input type='text' id='termNum'  value='"+termNum+"' style='width:95px;height: 23px;border: 1px solid #cccccc;'/>";
		parent4.innerHTML="<a onclick=\"editTermSubmit('"
	     +termId
		+"',this)\" class=\"easyui-linkbutton l-btn l-btn-small\" ><span class=\"l-btn-left\"><span class=\"l-btn-text\">提交</span></span></a>";
	}
	function editTermSubmit(termId,term){
		var termName=term.parentNode.parentNode.childNodes[1].childNodes[0].value;
		var termNum=term.parentNode.parentNode.childNodes[2].childNodes[0].value;
		if(termName==""){
			alert("学期名称不能为空!");
			return false;
		}
		if(termNum==""){
			alert("学期编号不能为空!");
			return false;
		}	
		var data3 = {termId:termId,termName:termName,termNum:termNum};
    	Kelp.jsonPost("../../func/courseBean/editTerm", data3, function(result) {
			if(result.re=="1"){
				refresh();
			}else{
				alert(result.data);
			}
		});
	}
</script>