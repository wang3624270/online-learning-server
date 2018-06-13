<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ots" uri="http://www.topwellsoft.com/jsp/tag"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>当前课程</title>
<meta name="keywords" content="index">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="apple-mobile-web-app-title" content="Amaze UI" />
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
<link rel="apple-touch-icon-precomposed" href="<%=request.getContextPath()%>/webFramework/assets/i/app-icon72x72@2x.png">
<link rel="stylesheet" href="<%=request.getContextPath()%>/webFramework/common/css/studentCourseDetails.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/webFramework/assets/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/webFramework/assets/css/admin.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/webFramework/assets/css/app.css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/easyui_animation.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/easyui_plus.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/icon.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/easyui/themes/insdep/jquery.insdep-extend.min.js"></script>
<script src="<%=request.getContextPath()%>/webFramework/assets/js/echarts.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/kelp/kelp.js"></script>
</head>

<body>
        <div>
            <div class="tpl-content-scope">
                <div class="note note-info">
                    <h3><span id="courseName"></span>
                        <span class="close" data-close="note"></span>
                    </h3>
                    <p>课程介绍</p>
                    <p><span class="label label-danger" id="course-title">课程公告</span><span id="course-news"></span></p>
                </div>
            </div>
                    <div class="tpl-portlet">
                        <div class="tpl-portlet-title">
                        </div>
                        <div class="am-tabs tpl-index-tabs" data-am-tabs>
                            <ul class="am-tabs-nav am-nav am-nav-tabs" style="left:0px;">
                                <li class="am-active"><a href="#tab1">章节</a></li>
                                <li><a href="#tab2">课程介绍</a></li>
                                <li><a href="#tab3">评论</a></li>
                                <li><a href="#tab4">问答</a></li>
                            </ul>

                            <div class="am-tabs-bd">
                                <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                                     <ul id="sectionsList" class="tpl-task-list tpl-task-remind">
                                     <!-- 这里是课程节次 -->
                                     </ul>
                                </div>
                                <div class="am-tab-panel am-fade" id="tab2">
                                     <!-- 这里是课程信息 -->
                                     <form id="newcourse" class="easyui-form" method="post" data-options="novalidate:true">
										<div style="margin-bottom:20px">
											<input class="easyui-textbox" name="courseName" style="width:50%" data-options="label:'课程名称:',required:true,missingMessage:'该项不允许为空',readonly:true">							     
											<input class="easyui-textbox" name="enlishName" style="width:49%" data-options="label:'课程英文名称:',required:true,missingMessage:'该项不允许为空',readonly:true">			 
									    </div>
										<div style="margin-bottom:20px">
											<input class="easyui-textbox" name="courseNum" style="width:33%" data-options="label:'课程编号:',required:true,missingMessage:'该项不允许为空',readonly:true">
											<select data-options="editable:false,valueField:'id', textField:'text',readonly:true" id='courseTypeOption'  class="easyui-combobox" name="courseType" label="课程类型:" style="width:33%"></select>							 
											<select data-options="editable:false,valueField:'id', textField:'text',readonly:true" id='collegeOption'  class="easyui-combobox" name="collegeId" label="开课学院:" style="width:33%"></select>			 
										</div>
										<div style="margin-bottom:20px">
											<input class="easyui-textbox" name="teachGroup" style="width:50%" data-options="label:'任课教师:',required:true,missingMessage:'该项不允许为空',readonly:true">
											<input class="easyui-textbox" name="book" style="width:49%" data-options="label:'指定书目:',required:true,missingMessage:'该项不允许为空',readonly:true">
										</div>
										<div style="margin-bottom:20px">
											<input class="easyui-textbox" name="reference" style="width:99%;height:100px" data-options="label:'参考资料:',multiline:true,readonly:true">
										</div>
										<div style="margin-bottom:20px">
											<input class="easyui-textbox" name="briefIntroduction" style="width:99%;height:100px" data-options="label:'课程简介:',multiline:true,readonly:true">
										</div>
										<div style="margin-bottom:20px">
											
										</div>
									</form>
                                </div>
                                <div class="am-tab-panel am-fade" id="tab3">
                                     <ul class="tpl-task-list tpl-task-remind" id="commentList">
                                          <li>
                                              <div class="head-icon">
                                                  <span class="tpl-header-list-user-ico">
                                                     <img src="<%=request.getContextPath()%>/webFramework/assets/img/user01.png">
                                                  </span>
                                              </div>
                                              <div class="comment-body-1">
                                                  <div class="am-form am-form-horizontal">
				                                      <textarea class="comment" rows="5" id="user-comment" placeholder="输入课程评价"></textarea>
				                                      <div class="am-form-group score-panel">
				                                      		<small>评分：</small>
							                                <select data-am-selected="{btnSize: 'sm'}" id="comment-score">
													              <option value="5" style="font-size:14px;">5分(非常好)</option>
													              <option value="4">4分(很好)</option>
													              <option value="3">3分(一般)</option>
													              <option value="2">2分(差)</option>
													              <option value="1">1分(极差)</option>
												            </select>
						                                    <small id="comment-alert" style="color:red;"></small>
						                              </div>
				                                      <div class="save-btn">
					                                      <button  onclick="submitComment()" type="button" class="am-btn am-btn-primary" style="background-color: #4db14d;font-size: 15px;">发表评论</button>
					                                  </div>
				                                  </div>
                                              </div>
                                          </li>
                                          <!-- 这里是评论列表 -->
                                     </ul>
                                </div>
                                <div class="am-tab-panel am-fade" id="tab4">
                                     <ul class="tpl-task-list tpl-task-remind" id="interlocutionList">
                                          <li>
                                              <div class="">
                                                  <div class="am-form am-form-horizontal">
				                                      <div>
					                                      <button  data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0, width: 800, height: 400}" type="button" class="am-btn am-btn-primary" style="background-color: #4db14d;font-size: 15px;">提问</button>
					                                  </div>
				                                  </div>
                                              </div>
                                          </li>
                                          <!-- 这里是问答列表 -->
                                     </ul>
                                </div>
                            </div>
                        </div>
                    </div>
        </div>
        <div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1" style="">
		  <div class="am-modal-dialog" style="">
		                    <form class="am-form am-form-horizontal">
                                <div class="am-form-group">
                                    <label for="user-weibo" class="am-u-sm-3 am-form-label">微博 / Twitter</label>
                                    <div class="am-u-sm-9">
                                        <input type="text" id="user-weibo" placeholder="输入你的微博 / Twitter">
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label for="user-intro" class="am-u-sm-3 am-form-label">简介 / Intro</label>
                                    <div class="am-u-sm-9">
                                        <textarea class="" rows="5" id="user-intro" placeholder="输入个人简介"></textarea>
                                        <small>250字以内写出你的一生...</small>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="button" class="am-btn am-btn-primary">保存修改</button>
                                    </div>
                                </div>
                            </form>
		  </div>
		</div>
    <script src="<%=request.getContextPath()%>/webFramework/assets/js/amazeui.min.js"></script>
    <script src="<%=request.getContextPath()%>/webFramework/assets/js/app.js"></script>
    <script src="<%=request.getContextPath()%>/webFramework/assets-1/js/amazeui.datatables.min.js"></script>
    <script src="<%=request.getContextPath()%>/webFramework/assets-1/js/app.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/webFramework/common/js/basetool.js"></script>
</body>
<ots:initjsp />
</html>
<script type="text/javascript">
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
initCourseInfo();
function initCourseInfo(){
	var taskId=getUrlValue("taskId");
	var data = {taskId:taskId};
	//节次信息
	Kelp.jsonPost("../../func/courseBean/getCourseSectionInfo", data, function(result){
		if(result.re=="1"){
			$("#courseName").html(result.data.courseName);
			var sectionsList="";
			$.each(result.data.tree,function(i,section){
				sectionsList+="<li style='cursor:pointer;' onclick='playVideo("+
				     section.sectionId+
					 ")'><div class='cosB'><span class='am-icon-circle-o' style='margin-top: 4px;'></span></div>"+
				     "<div class='cosA'><span class='am-icon-caret-right' style='width:15px;'></span><span>"+
				     section.sectionName+
				     "</span></div></li>";
			});
			$("#sectionsList").append(sectionsList);
			//加载课程数据
			var courseId=result.data.courseId+"";
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
				}else{
					alert(result.data);
				}
			});
		}else{
			alert(result.data);
		}
	});
	//课程公告
	Kelp.jsonPost("../../func/courseBean/getAllTaskNewsInfo", data, function(result) {
		if(result.re=="1"){
			$("#course-title").html(result.data[0].titile);
			$("#course-news").html(" ["+result.data[0].createTimeStr+"] "+result.data[0].content);
		}else{
			alert(result.data);
		}
	});
}
function playVideo(sectionId){
	window.open('onlinelearningVideoPlay.jsp?sectionId='+sectionId);
}
initComment();
function initComment(){
	var taskId=getUrlValue("taskId");
	var data2 = {taskId:taskId};
	Kelp.jsonPost("../../func/courseBean/getAllCourseComment", data2, function(result){
		if(result.re=="1"){
			var scoreList="";
			$.each(result.data,function(i,score){
				scoreList+="<li name='comment-li'><div class='head-icon'><span class='tpl-header-list-user-ico'><img src='<%=request.getContextPath()%>/webFramework/assets/img/user01.png'>"+
                    "</span></div><div class='comment-body'><div class='comment-name'>"+
                    score.perName+
                    "</div><div class='comment-content'>"+
                    score.comment+
                    "</div><div class='comment-time'><span class='comment-time-1'>时间:"+
                    score.createTimeStr+
                    "</span><span>评分："+
                    score.courseScoreStr+
                    "</span></div></div></li>";
			});
			$("#commentList").append(scoreList);
		}
	});
}
function submitComment(){
	var taskId=getUrlValue("taskId");
	var score=$("#comment-score").val();
	var comment=$("#user-comment").val();
	if(comment==""){
		$('#comment-alert').html("评价内容不能为空！提交失败");
		return false;
	}
	var data4 = {taskId:taskId,score:score,comment:comment};
	Kelp.jsonPost("../../func/courseBean/addCourseComment", data4, function(result){
		if(result.re=="1"){
			$("[name='comment-li']").remove();
			initComment();
			$("#comment-score").val("5");
			$("#user-comment").val("");
			$('#comment-alert').html("");
		}
	});
}
initInterlocution();
function initInterlocution(){
	var taskId=getUrlValue("taskId");
	var data2 = {taskId:taskId};
	Kelp.jsonPost("../../func/courseBean/getCourseInterlocutionList", data2, function(result){
		if(result.re=="1"){
			var interlocutionList="";
			$.each(result.data,function(i,interlocution){
				interlocutionList+="<li name='comment-li'><div class='head-icon'><span class='tpl-header-list-user-ico'><img src='<%=request.getContextPath()%>/webFramework/assets/img/user01.png'>"+
                    "</span></div><div class='comment-body'><div class=''>"+
                    interlocution.perName+
                    "</div><div class='interlocution-content'>"+
                    interlocution.titile+
                    "</div><div class='interlocution-time'><span>时间:"+
                    interlocution.createTimeStr+
                    "</span></div></div></li>";
			});
			$("#interlocutionList").append(interlocutionList);
		}
	});
}
function addInterlocution(){
	
}
</script>