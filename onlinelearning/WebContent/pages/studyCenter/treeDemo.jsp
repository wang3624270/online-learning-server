<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp Demo</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/webFramework/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/webFramework/easyui/themes/icon.css">
<script	type="text/javascript" src="<%=request.getContextPath()%>/webFramework/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

	});
</script>
</head>
<body>
<div style="height: 100%;width:100%;postition:fixed">
<div class="easyui-accordion" style="height: 100%;width:200px;float: left;">
    <div title="TreeMenu" iconCls="icon-ok" style="overflow:auto;">
			<ul class="easyui-tree">
				<li>
					<span>Foods</span>
					<ul>
						<li>
							<span>Fruits</span>
							<ul>
								<li>apple</li>
								<li>orange</li>
							</ul>
						</li>
						<li>
							<span>Vegetables</span>
							<ul>
								<li>tomato</li>
								<li>carrot</li>
								<li>cabbage</li>
								<li>potato</li>
								<li>lettuce</li>
							</ul>
						</li>
					</ul>
				</li>
			</ul>
   </div>
   <div title="About Accordion" iconCls="icon-ok" style="overflow:auto;">
         <ul id="tt1" class="easyui-tree">
            <li>
                <span>Folder1</span>
                <ul>
                    <li>
                        <span>Sub Folder 1</span>
                        <ul>
                            <li><span>File 11</span></li>
                            <li><span>File 12</span></li>
                            <li><span>File 13</span></li>
                        </ul>
                    </li>
                    <li><span>File 2</span></li>
                    <li><span>File 3</span></li>
                </ul>
            </li>
            <li><span>File2</span></li>
        </ul>
   </div>
   <div title="About Accordion" iconCls="icon-ok" style="overflow:auto;">
         <ul id="tt1" class="easyui-tree">
            <li>
                <span>Folder1</span>
                <ul>
                    <li>
                        <span>Sub Folder 1</span>
                        <ul>
                            <li><span>File 11</span></li>
                            <li><span>File 12</span></li>
                            <li><span>File 13</span></li>
                        </ul>
                    </li>
                    <li><span>File 2</span></li>
                    <li><span>File 3</span></li>
                </ul>
            </li>
            <li><span>File2</span></li>
        </ul>
   </div>
   <div title="About Accordion" iconCls="icon-ok" style="overflow:auto;">
         <ul id="tt1" class="easyui-tree">
            <li>
                <span>Folder1</span>
                <ul>
                    <li>
                        <span>Sub Folder 1</span>
                        <ul>
                            <li><span>File 11</span></li>
                            <li><span>File 12</span></li>
                            <li><span>File 13</span></li>
                        </ul>
                    </li>
                    <li><span>File 2</span></li>
                    <li><span>File 3</span></li>
                </ul>
            </li>
            <li><span>File2</span></li>
        </ul>
   </div>
   <div title="About Accordion" iconCls="icon-ok" style="overflow:auto;">
         <ul id="tt1" class="easyui-tree">
            <li>
                <span>Folder1</span>
                <ul>
                    <li>
                        <span>Sub Folder 1</span>
                        <ul>
                            <li><span>File 11</span></li>
                            <li><span>File 12</span></li>
                            <li><span>File 13</span></li>
                        </ul>
                    </li>
                    <li><span>File 2</span></li>
                    <li><span>File 3</span></li>
                </ul>
            </li>
            <li><span>File2</span></li>
        </ul>
   </div>
</div>
<div style="margin:0px 0 10px 0;position:absolute;left:200px;">
</div>
	<div id="p" class="easyui-panel" title="Fluid Panel" style="height:100%;padding:10px;">
		<p>The panel has a width of 100%.<p>
</div>
</div>
</body>
<ots:initjsp />
</html>