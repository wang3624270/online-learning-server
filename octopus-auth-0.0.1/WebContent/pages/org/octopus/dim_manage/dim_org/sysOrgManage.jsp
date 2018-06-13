<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构管理</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/webFramework/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/webFramework/easyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/webFramework/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/webFramework/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/webFramework/kelp/kelp.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						window.selectedOrg = new Kelp.List();
						$('#orgList')
								.datagrid(
										{
											url : window.octopusContextPath
													+ '/func/dimManage/sysOrgList',
											method : 'get',
											queryParams : {
												octopusViewId : Kelp
														.getOctopusViewId()
											},
											onSelect : function(index, row) {
												//单选，现在
												window.selectedOrg.clear();
												window.selectedOrg
														.add(row.logicId);
											},
											onUnselect : function(index, row) {

											},
											singleSelect : true,
											lines : true,

											collapsible : true,
											pagination : true,
											toolbar : [
													{
														text : '新增',
														iconCls : 'icon-add',
														handler : function() {

															$('#addOrgDlg')
																	.dialog(
																			'open');
															$('#addOrgDlg')
																	.window(
																			'center');

															$('#addOrgDlg')
																	.find(
																			'input.easyui-textbox')
																	.textbox(
																			'clear');

														}
													},
													{
														text : '编辑',
														iconCls : 'icon-edit',
														handler : function() {
															$('#editOrgDlg')
																	.dialog(
																			'open');
															Kelp
																	.jsonGet(
																			window.octopusContextPath
																					+ "/func/dimManage/getOrg",
																			{
																				delOrgLogicIds : window.selectedOrg
																						.toString()
																			},
																			function(
																					result) {
																				if (result.reCode != 0) {
																					alert(result.errorMessageList);
																				} else {
																					$(
																							"#orgName_edit")
																							.textbox(
																									'setValue',
																									result.name);
																					$(
																							"#dlgBelongOrg_edit")
																							.textbox(
																									'setValue',
																									result.belongOrg);
																					$(
																							"#orgCode_edit")
																							.textbox(
																									'setValue',
																									result.code);
																					$(
																							"#orgDes_edit")
																							.textbox(
																									'setValue',
																									result.description);
																					$(
																							"#orgAddress_edit")
																							.textbox(
																									'setValue',
																									result.address);
																				}
																			});
														}
													},
													{
														text : '删除',
														iconCls : 'icon-remove',
														handler : function() {
															if (window.selectedOrg
																	.size() == 0) {
																$(
																		'#noSelectDlg')
																		.dialog(
																				'open');
															} else
																$('#delOrgDlg')
																		.dialog(
																				'open');
														}
													},
													{
														text : '查看结构树',
														iconCls : 'icon-add',
														handler : function() {
															if (window.selectedOrg
																	.size() == 0) {
																$(
																		'#noSelectDlg')
																		.dialog(
																				'open');
																return;
															}
															$('#orgTreeDlg')
																	.dialog(
																			'open');
															$('#orgTree')
																	.tree(
																			{
																				url : window.octopusContextPath
																						+ '/func/dimManage/orgTree',
																				method : 'get',
																				queryParams : {
																					currentOrgLogicIds : window.selectedOrg
																							.toString(),
																					octopusViewId : Kelp
																							.getOctopusViewId()
																				}
																			});
														}
													} ]
										});
					});
	function searchOrg() {
		$('#parentOrgList').datalist({
			url : window.octopusContextPath + '/func/dimManage/sysOrgList',
			method : 'get',
			queryParams : {
				octopusViewId : Kelp.getOctopusViewId()
			},
			singleSelect : true,
			lines : true,
			checkbox : true,
			onSelect : function(index, row) {
				window.currentParentRow = row;

			}
		});
		window.currentParentRow = null;
		$('#parentOrgListDlg').dialog('open');

	}
	function setParentOrg() {
		$('#parentOrgListDlg').dialog('close');
		if (window.currentParentRow != null) {
			$('#dlgBelongOrg').searchbox('setValue',
					window.currentParentRow.text);
		}

	}
	function editOrg() {
		data = {};
		data.orgName = $("#orgName_edit").val();
		data.dlgBelongOrg = $("#dlgBelongOrg_edit").val();
		data.orgCode = $("#orgCode_edit").val();
		data.orgDes = $("#orgDes_edit").val();
		data.orgAddress = $("#orgAddress_edit").val();
		data.logicId = window.selectedOrg.toString();
		$('#editOrgDlg').dialog('close');
		Kelp.jsonPost(window.octopusContextPath + "/func/dimManage/editOrg",
				data, function(result) {
					if (result.reCode != 0) {
						alert(result.errorMessageList);
					} else {
						$('#orgList').datagrid('reload');

					}
				});
	}
	function newOrg() {
		data = {};
		data.orgName = $("#orgName").val();
		data.dlgBelongOrg = $("#dlgBelongOrg").val();
		data.orgCode = $("#orgCode").val();
		data.orgDes = $("#orgDes").val();
		data.orgAddress = $("#orgAddress").val();
		$('#addOrgDlg').dialog('close');
		Kelp.jsonPost(window.octopusContextPath + "/func/dimManage/newOrg",
				data, function(result) {
					if (result.reCode != 0) {
						alert(result.errorMessageList);
					} else {
						$('#orgList').datagrid('reload');

					}
				});
	}
	function delOrg() {

		$('#delOrgDlg').dialog('close');
		Kelp.jsonGet(window.octopusContextPath + "/func/dimManage/delOrg", {
			delOrgLogicIds : window.selectedOrg.toString()
		}, function(result) {
			if (result.reCode != 0) {
				alert(result.errorMessageList);
			} else {
				$('#orgList').datagrid('reload');

			}
		});
	}
	function dearchOrg() {
	}
</script>
</head>
<body class="main-body">
	<div>
		<table style="width: 100%;">
			<tr>
				<td>上级机构</td>
				<td><input id="s_belongOrg" class="easyui-textbox"
					style="width: 200px" data-options=" " /></td>
				<td>机构名称</td>
				<td><input id="s_orgName" class="easyui-textbox"
					style="width: 200px" /></td>
				<td>机构代码</td>
				<td><input id="s_orgCode" class="easyui-textbox"
					style="width: 100px" /></td>
				<td><a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-search',onClick:dearchOrg"
					style="width: 80px">查询</a></td>
		</table>
	</div>

	<br />
	<div>
		<table id="orgList" class="easyui-datagrid" title="机构列表"
			style="width: 100%; height: 350">
			<thead>
				<tr>
					<th data-options="field:'name',width:200,checkbox:true">机构名称</th>
					<th data-options="field:'code',width:200">机构代码</th>
					<th data-options="field:'address',width:400">机构地址</th>

				</tr>
			</thead>
		</table>
		<div id="addOrgDlg" class="easyui-dialog" title="新增组织结构"
			data-options="iconCls:'icon-add',closed:true"
			style="width: 400px; height: 300px; padding: 10px">
			<table style="margin: auto;">
				<tr>
					<td>机构名称:</td>
					<td><input id="orgName"
						class="easyui-textbox easyui-validatebox"
						data-options="required:true,validateOnCreate:false,validateOnBlur:true"
						style="width: 200px;"></td>
				</tr>
				<tr>
					<td>上级机构:</td>
					<td><input id="dlgBelongOrg" class="easyui-textbox"
						style="width: 200px;"
						data-options="
				icons:[{iconCls:'icon-search',
				handler: searchOrg
			}],editable:false
			"></td>
				</tr>
				<tr>
					<td>机构代码:</td>
					<td><input id="orgCode" class="easyui-textbox"
						data-options="required:true,validateOnCreate:false,validateOnBlur:true"
						style="width: 200px;"></td>
				</tr>
				<tr>
					<td>机构描述:</td>
					<td><input id="orgDes" class="easyui-textbox"
						data-options="multiline:true" style="width: 200px; height: 50px"></td>
				</tr>
				<tr>
					<td>机构地址:</td>
					<td><input id="orgAddress" class="easyui-textbox"
						data-options="multiline:true" style="width: 200px; height: 50px"></td>
				</tr>
			</table>
			<br />
			<div style="margin: auto; text-align: center">
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-save',onClick:newOrg"
					style="width: 80px">保存</a> <a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel',onClick:function(){$('#addOrgDlg').dialog('close');}"
					style="width: 80px">取消</a>
			</div>
		</div>

		<div id="editOrgDlg" class="easyui-dialog" title="编辑组织结构"
			data-options="iconCls:'icon-add',closed:true"
			style="width: 400px; height: 300px; padding: 10px">
			<table style="margin: auto;">
				<tr>
					<td>机构名称:</td>
					<td><input id="orgName_edit"
						class="easyui-textbox easyui-validatebox"
						data-options="required:true,validateOnCreate:false,validateOnBlur:true"
						style="width: 200px;"></td>
				</tr>
				<tr>
					<td>上级机构:</td>
					<td><input id="dlgBelongOrg_edit" class="easyui-textbox"
						style="width: 200px;"
						data-options="
				icons:[{iconCls:'icon-search',
				handler: searchOrg
			}],editable:false
			"></td>
				</tr>
				<tr>
					<td>机构代码:</td>
					<td><input id="orgCode_edit" class="easyui-textbox"
						data-options="required:true,validateOnCreate:false,validateOnBlur:true"
						style="width: 200px;"></td>
				</tr>
				<tr>
					<td>机构描述:</td>
					<td><input id="orgDes_edit" class="easyui-textbox"
						data-options="multiline:true" style="width: 200px; height: 50px"></td>
				</tr>
				<tr>
					<td>机构地址:</td>
					<td><input id="orgAddress_edit" class="easyui-textbox"
						data-options="multiline:true" style="width: 200px; height: 50px"></td>
				</tr>
			</table>
			<br />
			<div style="margin: auto; text-align: center">
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-save',onClick:editOrg"
					style="width: 80px">保存</a> <a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel',onClick:function(){$('#editOrgDlg').dialog('close');}"
					style="width: 80px">取消</a>
			</div>
		</div>
		<div id="orgTreeDlg" class="easyui-dialog" title="查看机构树"
			data-options="iconCls:'icon-search',closed:true"
			style="width: 450px; height: 350px; padding: 10px">
			<div id="orgTree" class="easyui-panel" data-options=" border:false"
				style="padding: 5px"></div>
		</div>
		<div id="parentOrgListDlg" class="easyui-dialog" title="组织结构列表"
			data-options="iconCls:'icon-search',closed:true"
			style="width: 450px; height: 350px; padding: 10px">
			<div style="width: 315px; margin-left: auto; margin-right: auto;">
				<div id="parentOrgList" style="width: 310px; height: 250px"></div>
			</div>
			<br />
			<div style="margin: auto; text-align: center">
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-ok',onClick:setParentOrg"
					style="width: 80px">确定</a> <a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel',onClick:function(){$('#parentOrgListDlg').dialog('close');}"
					style="width: 80px">取消</a>
			</div>

		</div>
		<div id="delOrgDlg" class="easyui-dialog" title="删除"
			data-options="iconCls:'icon-delete',closed:true"
			style="width: 300px; height: 120px; padding: 10px">
			<div style="margin: auto; text-align: center">
				<div style="width: 250px; margin-left: auto; margin-right: auto;">
					您确定要删除该条数据吗?</div>
				<br /> <a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-ok',onClick:delOrg" style="width: 80px">确定</a>
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel',onClick:function(){$('#delOrgDlg').dialog('close');}"
					style="width: 80px">取消</a>
			</div>
		</div>
		<div id="noSelectDlg" class="easyui-dialog" title="提示"
			data-options="iconCls:'icon-tip',closed:true"
			style="width: 250px; height: 100px; padding: 10px">
			<div style="margin: auto; text-align: center">
				<div style="width: 250px; margin-left: auto; margin-right: auto;">
					未选中数据.</div>
				<br /> <a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel',onClick:function(){$('#noSelectDlg').dialog('close');}"
					style="width: 80px">确定</a>
			</div>
		</div>
	</div>
</body>
<ots:initjsp />
</html>