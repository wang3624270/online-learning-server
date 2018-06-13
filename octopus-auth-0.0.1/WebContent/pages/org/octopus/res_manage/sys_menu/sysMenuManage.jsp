<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
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
.datagrid-header {
	position: absolute;
	visibility: hidden;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('#menuRes').treegrid({
			url : window.octopusContextPath + '/func/resManage/allSysMenu',
			method : 'get',
			idField : 'id',
			treeField : 'name',
			border : false,
			loadFilter : menuResLoad
		});
		function menuResLoad(result, parent) {
			if (result.reCode != 0) {
				alert(result.errorMessageList);
				return;
			}
			data = result.rootMenu.children;
			return data;
		}

	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,border:false"
		style="width: 220px; padding: 0px;">
		<table id="menuRes" title="菜单列表(权限管理测试)" class="easyui-treegrid"
			style="width: 100%;">
			<thead>
				<tr>
					<th data-options="field:'name'" width="210px"></th>
				</tr>
			</thead>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div>
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-add'">添加同级</a> <a href="#"
				class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加下级</a>
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove'">删除</a>
		</div>
		<div>
			<table>
				<tr>
					<td>父结点</td>
					<td></td>
				</tr>
				<tr>
					<td>編号</td>
					<td></td>
				</tr>
				<tr>
					<td>菜单名</td>
					<td></td>
				</tr>
				<tr>
					<td>地址</td>
					<td></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<ots:initjsp />
</html>