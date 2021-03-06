package cn.edu.sdu.homework.bean.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_dao.SysUserDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.auth.jpa_model.SysUser;
import org.octopus.common_business.attachment.dao.BaseAttachmentInfoDao;
import org.octopus.common_business.attachment.model.BaseAttachmentInfo;
import org.sdu.file_util.FileUtility;
import org.sdu.uploadListener.UploadListener;
import org.sdu.uploadListener.UploadStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.starfish.login_users.CommonAuthUseInfoTool;
import org.starfish.login_users.UserTokenServerSide;

import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.course.dao.AccessoriesCourseFolderDao;
import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.AccessoriesInfoDao;
import cn.edu.sdu.course.dao.BaseCollegeDao;
import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.dao.ElearningPlanCourseDao;
import cn.edu.sdu.course.dao.ElearningTeachTaskDao;
import cn.edu.sdu.course.dao.ElearningTermDao;
import cn.edu.sdu.course.form.BaseCourseActionForm;
import cn.edu.sdu.course.model.AccessoriesInfo;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.course.model.ElearningPlanCourse;
import cn.edu.sdu.course.model.ElearningTeachTask;
import cn.edu.sdu.course.model.ElearningTerm;
import cn.edu.sdu.exam.form.BaseExamActionForm;
import cn.edu.sdu.exam.model.ElearningExamInfo;
import cn.edu.sdu.homework.dao.ElearningActivityInfoDao;
import cn.edu.sdu.homework.dao.ElearningActivityScoreDao;
import cn.edu.sdu.homework.dao.ElearningHomeworkAnswerDao;
import cn.edu.sdu.homework.dao.ElearningHomeworkInfoDao;
import cn.edu.sdu.homework.form.BaseHomeWorkActionForm;
import cn.edu.sdu.homework.model.ElearningActivityInfo;
import cn.edu.sdu.homework.model.ElearningActivityScore;
import cn.edu.sdu.homework.model.ElearningHomeworkAnswer;
import cn.edu.sdu.homework.model.ElearningHomeworkInfo;

@RestController
public class BaseHomeworkActionInterface {

	@Autowired
	private InfoPersonInfoDao infoPersonInfoDao;
	@Autowired
	private ElearningCourseDao elearningCourseDao;
	@Autowired
	private ElearningPlanCourseDao elearningPlanCourseDao;
	@Autowired
	private BaseCollegeDao baseCollegeDao;
	@Autowired
	private AccessoriesCourseFolderDao accessoriesCourseFolderDao;
	@Autowired
	private ElearningTermDao elearningTermDao;
	@Autowired
	private AccessoriesInfoDao accessoriesInfoDao;
	@Autowired
	private AccessoriesFolderAccDao accessoriesFolderAccDao;
	@Autowired
	private ElearningTeachTaskDao elearningTeachTaskDao;
	@Autowired
	private ElearningHomeworkInfoDao elearningHomeworkInfoDao;
	@Autowired
	private BaseAttachmentInfoDao baseAttachmentInfoDao;
	@Autowired
	private ElearningHomeworkAnswerDao elearningHomeworkAnswerDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private ElearningActivityInfoDao elearningActivityInfoDao;
	@Autowired
	private ElearningActivityScoreDao elearningActivityScoreDao;
	
	@RequestMapping(value = "/homework/getHomeworkList", method = RequestMethod.POST)
	public Map getAllTeachTaskInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) throws ParseException {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String name = (String) m.get("name");
			String taskName = (String) m.get("taskName");
			List homeworkList=elearningHomeworkInfoDao.getHomelistByConditions(name, taskName);
			if(homeworkList!=null){
				for(int i=0;i<homeworkList.size();i++){
					ElearningHomeworkInfo homework=(ElearningHomeworkInfo) homeworkList.get(i);
					BaseHomeWorkActionForm homeworkForm=new BaseHomeWorkActionForm();
					ElearningTeachTask task=elearningTeachTaskDao.find(homework.getTaskId());
					homeworkForm.setHomeworkId(homework.getHomeworkId());
					homeworkForm.setName(homework.getName());
					homeworkForm.setTaskId(homework.getTaskId());
					homeworkForm.setTaskName(task.getTaskName());
					homeworkForm.setCourseName(task.getElearningCourse().getCourseName());
					homeworkForm.setStartDate(homework.getStartTime());
					homeworkForm.setEndDate(homework.getEndTime());
					homeworkForm.setContent(homework.getContent());
					List totalList=elearningPlanCourseDao.getPlanListByTaskId(homework.getTaskId());
					List submitList=elearningHomeworkAnswerDao.getAnswerListByHomeworkId(homework.getHomeworkId());
					Integer total=0;totalList.size();
					Integer submit=0;
					if(totalList!=null){
						total=totalList.size();
					}
					if(submitList!=null){
						submit=submitList.size();
					}
					homeworkForm.setTotal(total);
					homeworkForm.setSubmit(submit);
					dataList.add(homeworkForm);
				}
			}
			data.put("homeworkList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/addOrEditHomework", method = RequestMethod.POST)
	public Map addOrEditHomework(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer homeworkId=(Integer)m.get("homeworkId");
			String name = (String) m.get("name");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String content = (String) m.get("content");
			Integer taskId = (Integer) m.get("taskId");
			if(homeworkId!=null && !homeworkId.equals("")){
				ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(homeworkId);
				homework.setName(name);
				homework.setStartTime(startTime);
				homework.setEndTime(endTime);
				homework.setContent(content);
				homework.setPersonId(userToken.getPersonId());
				homework.setTaskId(taskId);
				elearningHomeworkInfoDao.update(homework);
			}else{
				ElearningHomeworkInfo homework=new ElearningHomeworkInfo();
				homework.setName(name);
				homework.setStartTime(startTime);
				homework.setEndTime(endTime);
				homework.setContent(content);
				homework.setPersonId(userToken.getPersonId());
				homework.setTaskId(taskId);
				elearningHomeworkInfoDao.save(homework);
			}
			return CommonTool.getNodeMapOk("恭喜您，编辑成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/getHomeworkSelectList", method = RequestMethod.POST)
	public Map getHomeworkSelectList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		Map map = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer homeworkId = (Integer) request.get("homeworkId");
			Integer taskId = (Integer) request.get("taskId");
			String loginName = (String) request.get("loginName");
			String perName = (String) request.get("perName");
			ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(homeworkId);
			List dataList=new ArrayList();
			List planList=elearningPlanCourseDao.getPlanListByConditions(loginName, perName, taskId, null);
			if(planList!=null){
				for(int i=0;i<planList.size();i++){
					ElearningPlanCourse plan =(ElearningPlanCourse) planList.get(i);
					Integer stuId=plan.getStuId();
					InfoPersonInfo student=infoPersonInfoDao.find(stuId);
					SysUser user=sysUserDao.getSysUsersByUserid(stuId);
					BaseHomeWorkActionForm planForm=new BaseHomeWorkActionForm();
					planForm.setHomeworkId(homeworkId);
					planForm.setPerName(student.getPerName());
					planForm.setLoginName(user.getLoginName());
					planForm.setPersonId(stuId);
					//是否提交作业
					ElearningHomeworkAnswer answer=elearningHomeworkAnswerDao.getAnswerByHomeworkIdAndStuId(homeworkId, stuId);
					if(answer!=null){
						planForm.setAnswerId(answer.getAnswerId());
						planForm.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answer.getCreateTime()));
						planForm.setCheckTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answer.getCheckTime()));
						planForm.setScore(answer.getScore());
						planForm.setAnswerContent(answer.getAnswerContent());
					}
					dataList.add(planForm);
				}
			}
			map.put("selectList", dataList);
			return CommonTool.getNodeMap(map, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/submitHomeworkScore", method = RequestMethod.POST)
	public Map submitHomeworkScore(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		Map map = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer answerId = (Integer) request.get("answerId");
			String score = (String) request.get("score");
			if(answerId!=null && !answerId.equals("")){
				ElearningHomeworkAnswer answer=elearningHomeworkAnswerDao.find(answerId);
				answer.setScore(Double.valueOf(score));
				answer.setCheckTime(new Date());
				answer.setCheckerId(userToken.getPersonId());
				elearningHomeworkAnswerDao.update(answer);
			}
			return CommonTool.getNodeMapOk("恭喜您，提交成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/getPersonHomeworkList", method = RequestMethod.GET)
	public Map getPersonHomeworkList(HttpServletRequest httpRequest) throws ParseException {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List planList=elearningPlanCourseDao.getTaskIdByStuId(userToken.getPersonId());
			if(planList!=null){
				for(int i=0;i<planList.size();i++){
					ElearningPlanCourse plan=(ElearningPlanCourse) planList.get(i);
					ElearningTeachTask task=plan.getElearningTeachTask();
					List list=elearningHomeworkInfoDao.getHomelistListByTaskId(task.getTaskId());
					for(int j=0;list!=null && j<list.size();j++){
						ElearningHomeworkInfo homework=(ElearningHomeworkInfo) list.get(j);
						BaseHomeWorkActionForm form=new BaseHomeWorkActionForm();
						form.setHomeworkId(homework.getHomeworkId());
						form.setContent(homework.getContent());
						form.setName(homework.getName());
						form.setTaskName(task.getTaskName());
						form.setStartDate(homework.getStartTime());
						form.setEndDate(homework.getEndTime());
						//是否提交作业
						form.setState("未提交");
						ElearningHomeworkAnswer answer=elearningHomeworkAnswerDao.getAnswerByHomeworkIdAndStuId(homework.getHomeworkId(), userToken.getPersonId());
						if(answer!=null){
							form.setState("已提交");
							form.setAnswerId(answer.getAnswerId());
							form.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answer.getCreateTime()));
							if(answer.getCheckTime()!=null){
								form.setCheckTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answer.getCheckTime()));
							}
							form.setScore(answer.getScore());
							form.setAnswerContent(answer.getAnswerContent());
						}
						Date nowDate=new Date();
						Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(homework.getStartTime());
						Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(homework.getEndTime());
						if(startTime.getTime()<nowDate.getTime() && nowDate.getTime()<endTime.getTime()){
							if(answer==null){
								form.setSubState("1");
							}else if(answer!=null && answer.getCheckTime()==null){
								form.setSubState("1");
							}
						}
						dataList.add(form);
					}
				}
			}
			data.put("homeworkList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/homework/addOrEditHomeworkAnswer", method = RequestMethod.POST)
	public Map addOrEditHomeworkAnswer(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		Map map = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer homeworkId = (Integer) request.get("homeworkId");
			String answerContent = (String) request.get("answerContent");
			ElearningHomeworkAnswer answer=elearningHomeworkAnswerDao.getAnswerByHomeworkIdAndStuId(homeworkId, userToken.getPersonId());
			if(answer!=null){
				answer.setAnswerContent(answerContent);
				answer.setCreateTime(new Date());
				elearningHomeworkAnswerDao.update(answer);
			}else{
				answer=new ElearningHomeworkAnswer();
				answer.setAnswerContent(answerContent);
				answer.setCreateTime(new Date());
				answer.setStuId(userToken.getPersonId());
				answer.setHomeworkId(homeworkId);
				elearningHomeworkAnswerDao.save(answer);
			}
			return CommonTool.getNodeMapOk("恭喜您，提交成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/getActivityList", method = RequestMethod.POST)
	public Map getActivityList(HttpServletRequest httpRequest,
			@RequestBody Object obj) throws ParseException {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String activityName = (String) m.get("activityName");
			String taskName = (String) m.get("taskName");
			List activityList=elearningActivityInfoDao.getActivitylistByConditions(activityName, taskName);
			if(activityList!=null){
				for(int i=0;i<activityList.size();i++){
					ElearningActivityInfo activity=(ElearningActivityInfo) activityList.get(i);
					BaseHomeWorkActionForm form=new BaseHomeWorkActionForm();
					ElearningTeachTask task=elearningTeachTaskDao.find(activity.getTaskId());
					form.setActivityId(activity.getActivityId());
					form.setActivityName(activity.getActivityName());
					form.setTaskId(activity.getTaskId());
					form.setTaskName(task.getTaskName());
					form.setCourseName(task.getElearningCourse().getCourseName());
					form.setStartDate(activity.getStartTime());
					form.setEndDate(activity.getEndTime());
					form.setGoal(activity.getGoal());
					form.setPlace(activity.getPlace());
					List totalList=elearningPlanCourseDao.getPlanListByTaskId(activity.getTaskId());
					Integer total=0;totalList.size();
					if(totalList!=null){
						total=totalList.size();
					}
					form.setTotal(total);
					dataList.add(form);
				}
			}
			data.put("activityList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/addOrEditActivity", method = RequestMethod.POST)
	public Map addOrEditActivity(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer activityId=(Integer)m.get("activityId");
			String activityName = (String) m.get("activityName");
			String goal = (String) m.get("goal");
			String place = (String) m.get("place");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			Integer taskId = (Integer) m.get("taskId");
			if(activityId!=null && !activityId.equals("")){
				ElearningActivityInfo activity=elearningActivityInfoDao.find(activityId);
				activity.setActivityName(activityName);
				activity.setStartTime(startTime);
				activity.setEndTime(endTime);
				activity.setGoal(goal);
				activity.setPersonId(userToken.getPersonId());
				activity.setTaskId(taskId);
				activity.setPlace(place);
				elearningActivityInfoDao.update(activity);
			}else{
				ElearningActivityInfo activity=new ElearningActivityInfo();
				activity.setActivityName(activityName);
				activity.setStartTime(startTime);
				activity.setEndTime(endTime);
				activity.setGoal(goal);
				activity.setPersonId(userToken.getPersonId());
				activity.setTaskId(taskId);
				activity.setPlace(place);
				elearningActivityInfoDao.save(activity);
			}
			return CommonTool.getNodeMapOk("恭喜您，编辑成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/getActivitySelectList", method = RequestMethod.POST)
	public Map getActivitySelectList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		Map map = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer activityId = (Integer) request.get("activityId");
			Integer taskId = (Integer) request.get("taskId");
			String loginName = (String) request.get("loginName");
			String perName = (String) request.get("perName");
			ElearningActivityInfo activity=elearningActivityInfoDao.find(activityId);
			List dataList=new ArrayList();
			List planList=elearningPlanCourseDao.getPlanListByConditions(loginName, perName, taskId, null);
			if(planList!=null){
				for(int i=0;i<planList.size();i++){
					ElearningPlanCourse plan =(ElearningPlanCourse) planList.get(i);
					Integer stuId=plan.getStuId();
					InfoPersonInfo student=infoPersonInfoDao.find(stuId);
					SysUser user=sysUserDao.getSysUsersByUserid(stuId);
					BaseHomeWorkActionForm planForm=new BaseHomeWorkActionForm();
					planForm.setActivityId(activityId);
					planForm.setPerName(student.getPerName());
					planForm.setLoginName(user.getLoginName());
					planForm.setPersonId(stuId);
					//是否存在成绩
					ElearningActivityScore score=elearningActivityScoreDao.getScoreByIdAndStuId(activityId, stuId);
					if(score!=null){
						planForm.setScoreId(score.getScoreId());
						planForm.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(score.getCreateTime()));
						planForm.setGrade(score.getScore());
					}
					dataList.add(planForm);
				}
			}
			map.put("selectList", dataList);
			return CommonTool.getNodeMap(map, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/submitScore", method = RequestMethod.POST)
	public Map submitScore(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			List selectList = (List) request.get("selectList");
			if(selectList!=null){
				for(int i=0;i<selectList.size();i++){
					Map map=(Map) selectList.get(i);
					Integer personId=(Integer) map.get("personId");
					Integer score=(Integer) map.get("grade");
					Integer activityId=(Integer) map.get("activityId");
					ElearningActivityScore as=elearningActivityScoreDao.getScoreByIdAndStuId(activityId, personId);
					if(as!=null){
						as.setScore(Integer.valueOf(score));
						as.setCheckerId(userToken.getPersonId());
						as.setCreateTime(new Date());
						elearningActivityScoreDao.update(as);
					}else{
						as=new ElearningActivityScore();
						as.setActivityId(activityId);
						as.setStuId(personId);
						as.setScore(Integer.valueOf(score));
						as.setCheckerId(userToken.getPersonId());
						as.setCreateTime(new Date());
						elearningActivityScoreDao.save(as);
					}
				}
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homework/getPersonActivityList", method = RequestMethod.GET)
	public Map getPersonActivityList(HttpServletRequest httpRequest) throws ParseException {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List planList=elearningPlanCourseDao.getTaskIdByStuId(userToken.getPersonId());
			if(planList!=null){
				for(int i=0;i<planList.size();i++){
					ElearningPlanCourse plan=(ElearningPlanCourse) planList.get(i);
					ElearningTeachTask task=plan.getElearningTeachTask();
					List list=elearningActivityInfoDao.getListByTaskId(task.getTaskId());
					for(int j=0;list!=null && j<list.size();j++){
						ElearningActivityInfo activity=(ElearningActivityInfo) list.get(j);
						BaseHomeWorkActionForm form=new BaseHomeWorkActionForm();
						form.setActivityId(activity.getActivityId());
						form.setGoal(activity.getGoal());
						form.setActivityName(activity.getActivityName());
						form.setPlace(activity.getPlace());
						form.setTaskName(task.getTaskName());
						form.setStartDate(activity.getStartTime());
						form.setEndDate(activity.getEndTime());
						//是否提交作业
						ElearningActivityScore score=elearningActivityScoreDao.getScoreByIdAndStuId(activity.getActivityId(), plan.getStuId());
						if(score!=null){
							form.setScoreId(score.getScoreId());
							form.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(score.getCreateTime()));
							form.setGrade(score.getScore());
						}
						dataList.add(form);
					}
				}
			}
			data.put("activityList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
}
