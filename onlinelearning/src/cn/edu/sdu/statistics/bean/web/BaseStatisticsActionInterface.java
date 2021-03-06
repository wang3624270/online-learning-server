package cn.edu.sdu.statistics.bean.web;

import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_dao.SysUserDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.auth.jpa_model.SysUser;
import org.octopus.common_business.attachment.dao.BaseAttachmentInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.starfish.login_users.CommonAuthUseInfoTool;
import org.starfish.login_users.UserTokenServerSide;

import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.course.dao.AccessoriesCourseFolderDao;
import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.AccessoriesInfoDao;
import cn.edu.sdu.course.dao.BaseCollegeDao;
import cn.edu.sdu.course.dao.ElearningCourseCommentInfoDao;
import cn.edu.sdu.course.dao.ElearningCourseDao;
import cn.edu.sdu.course.dao.ElearningCourseSectionDao;
import cn.edu.sdu.course.dao.ElearningInterlocutionInfoDao;
import cn.edu.sdu.course.dao.ElearningPlanCourseDao;
import cn.edu.sdu.course.dao.ElearningSectionAccDao;
import cn.edu.sdu.course.dao.ElearningTaskChargeDao;
import cn.edu.sdu.course.dao.ElearningTaskFinishDao;
import cn.edu.sdu.course.dao.ElearningTaskNewsDao;
import cn.edu.sdu.course.dao.ElearningTeachTaskDao;
import cn.edu.sdu.course.dao.ElearningTermDao;
import cn.edu.sdu.course.form.BaseCourseActionForm;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.course.model.ElearningPlanCourse;
import cn.edu.sdu.course.model.ElearningTaskFinish;
import cn.edu.sdu.course.model.ElearningTeachTask;
import cn.edu.sdu.course.model.ElearningTerm;
import cn.edu.sdu.exam.dao.ElearningExamInfoDao;
import cn.edu.sdu.exam.dao.ElearningExamQRelationDao;
import cn.edu.sdu.exam.dao.ElearningExamQuestionDao;
import cn.edu.sdu.exam.dao.ElearningExamScoreInfoDao;
import cn.edu.sdu.exam.dao.ElearningPracticeInfoDao;
import cn.edu.sdu.exam.dao.ElearningPracticeQRelationDao;
import cn.edu.sdu.exam.model.ElearningExamInfo;
import cn.edu.sdu.exam.model.ElearningExamScoreInfo;
import cn.edu.sdu.homework.dao.ElearningActivityInfoDao;
import cn.edu.sdu.homework.dao.ElearningActivityScoreDao;
import cn.edu.sdu.homework.dao.ElearningHomeworkAnswerDao;
import cn.edu.sdu.homework.dao.ElearningHomeworkInfoDao;
import cn.edu.sdu.homework.form.BaseHomeWorkActionForm;
import cn.edu.sdu.homework.model.ElearningActivityInfo;
import cn.edu.sdu.homework.model.ElearningActivityScore;
import cn.edu.sdu.homework.model.ElearningHomeworkAnswer;
import cn.edu.sdu.homework.model.ElearningHomeworkInfo;
import cn.edu.sdu.lecture.dao.ElearningLectureDao;
import cn.edu.sdu.lecture.dao.ElearningLectureEntryDao;
import cn.edu.sdu.lecture.model.ElearningLectureEntry;
import cn.edu.sdu.manage.dao.ElearningPayRecordDao;
import cn.edu.sdu.manage.model.ElearningPayRecord;
import cn.edu.sdu.statistics.dao.ElearningTaskScoreDao;
import cn.edu.sdu.statistics.form.BaseStatisticsActionForm;
import cn.edu.sdu.statistics.model.ElearningTaskScore;

@RestController
public class BaseStatisticsActionInterface {
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
	private ElearningCourseSectionDao elearningCourseSectionDao;
	@Autowired
	private ElearningSectionAccDao elearningSectionAccDao;
	@Autowired
	private ElearningTaskNewsDao elearningTaskNewsDao;
	@Autowired
	private ElearningCourseCommentInfoDao elearningCourseCommentInfoDao;
	@Autowired
	private ElearningInterlocutionInfoDao elearningInterlocutionInfoDao;
	@Autowired
	private ElearningPracticeInfoDao elearningPracticeInfoDao;
	@Autowired
	private BaseAttachmentInfoDao baseAttachmentInfoDao;
	@Autowired
	private ElearningExamQuestionDao elearningExamQuestionDao;
	@Autowired
	private ElearningExamQRelationDao elearningExamQRelationDao;
	@Autowired
	private ElearningPracticeQRelationDao elearningPracticeQRelationDao;
	@Autowired
	private ElearningExamInfoDao elearningExamInfoDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private ElearningTaskFinishDao elearningTaskFinishDao;
	@Autowired
	private ElearningTaskChargeDao elearningTaskChargeDao;
	@Autowired
	private ElearningPayRecordDao elearningPayRecordDao;
	@Autowired
	private ElearningExamScoreInfoDao elearningExamScoreInfoDao;
	@Autowired
	private ElearningTaskScoreDao elearningTaskScoreDao;
	@Autowired
	private ElearningHomeworkAnswerDao elearningHomeworkAnswerDao;
	@Autowired
	private ElearningHomeworkInfoDao elearningHomeworkInfoDao;
	@Autowired
	private ElearningActivityInfoDao elearningActivityInfoDao;
	@Autowired
	private ElearningActivityScoreDao elearningActivityScoreDao;
	@Autowired
	private ElearningLectureEntryDao elearningLectureEntryDao;
	@Autowired
	private ElearningLectureDao elearningLectureDao;
	
	@RequestMapping(value = "/statistics/getPersonScoreList", method = RequestMethod.POST)
	public Map getPersonScoreList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer taskId = (Integer) request.get("taskId");
			String loginName = (String) request.get("loginName");
			String perName = (String) request.get("perName");
			String state = (String) request.get("state");
			List rList=elearningPlanCourseDao.getPlanListByConditions(loginName, perName, taskId,state);
			List dataList=new ArrayList();
			if(rList!=null){
				for(int i=0;i<rList.size();i++){
					ElearningPlanCourse plan=(ElearningPlanCourse) rList.get(i);
					BaseStatisticsActionForm planForm=new BaseStatisticsActionForm();
					SysUser user=sysUserDao.getSysUsersByUserid(plan.getStuId());
					InfoPersonInfo info=infoPersonInfoDao.find(plan.getStuId());
					planForm.setTaskId(plan.getElearningTeachTask().getTaskId());
					planForm.setPersonId(info.getPersonId());
					planForm.setPerTypeCode(info.getPerTypeCode());
					planForm.setLoginName(user.getLoginName());
					planForm.setPerName(info.getPerName());
					planForm.setMobilePhone(info.getMobilePhone());
					planForm.setId(plan.getId());
					planForm.setState(plan.getState());
					planForm.setCompleteDegree(plan.getCompleteDegree());
					List list=elearningExamInfoDao.getExamListByTaskId(plan.getElearningTeachTask().getTaskId());
					if(list!=null && list.size()>0){
						ElearningExamInfo exam=(ElearningExamInfo) list.get(0);
						ElearningExamScoreInfo score=elearningExamScoreInfoDao.getElearningExamScoreInfoByConditions(plan.getStuId(), exam.getExamId());
						if(score!=null){
							planForm.setExamScore(score.getScore());
						}
					}
					List levelList=elearningTaskScoreDao.getScoreListByConditions(plan.getStuId(), plan.getElearningTeachTask().getTaskId());
					if(levelList!=null && levelList.size()>0){
						ElearningTaskScore level=(ElearningTaskScore) levelList.get(0);
						planForm.setScoreLevel(level.getScoreLevel());
					}
					dataList.add(planForm);
				}
			}
			data.put("personList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/statistics/getPersonHomeworkScore", method = RequestMethod.POST)
	public Map getPersonHomeworkScore(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer taskId = (Integer) request.get("taskId");
			Integer personId = (Integer) request.get("personId");
			List list1=new ArrayList();
			List list2=new ArrayList();
			List homeworkList=elearningHomeworkInfoDao.getHomelistListByTaskId(taskId);
			if(homeworkList!=null){
				for(int i=0;i<homeworkList.size();i++){
					BaseStatisticsActionForm form=new BaseStatisticsActionForm();
					ElearningHomeworkInfo homework=(ElearningHomeworkInfo) homeworkList.get(i);
					form.setName(homework.getName());
					Integer homeworkId=homework.getHomeworkId();
					ElearningHomeworkAnswer answer=elearningHomeworkAnswerDao.getAnswerByHomeworkIdAndStuId(homeworkId, personId);
					if(answer!=null){
						form.setHomeworkScore(answer.getScore());
					}
					list1.add(form);
				}
			}
			data.put("homeworkList", list1);
			List activityList=elearningActivityInfoDao.getListByTaskId(taskId);
			if(activityList!=null){
				for(int i=0;i<activityList.size();i++){
					BaseStatisticsActionForm form=new BaseStatisticsActionForm();
					ElearningActivityInfo activity=(ElearningActivityInfo) activityList.get(i);
					form.setActivityName(activity.getActivityName());
					Integer activityId=activity.getActivityId();
					ElearningActivityScore aScore=elearningActivityScoreDao.getScoreByIdAndStuId(activityId, personId);
					if(aScore!=null){
						form.setActivityScore(Double.valueOf(aScore.getScore()));
					}
					list2.add(form);
				}
			}
			data.put("activityList", list2);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/statistics/setPersonScoreLevel", method = RequestMethod.POST)
	public Map setPersonScoreLevel(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer taskId = (Integer) request.get("taskId");
		String scoreLevel = (String) request.get("scoreLevel");
		Integer personId = (Integer) request.get("personId");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List scoreList=elearningTaskScoreDao.getScoreListByConditions(personId, taskId);
			if(scoreList!=null && scoreList.size()>0){
				ElearningTaskScore score=(ElearningTaskScore) scoreList.get(0);
				score.setScoreLevel(scoreLevel);
				score.setTeacher(userToken.getPersonId());
				score.setCreateTime(new Date());
				elearningTaskScoreDao.update(score);
			}else{
				ElearningTaskScore score=new ElearningTaskScore();
				score.setTaskId(taskId);
				score.setStuId(personId);
				score.setScoreLevel(scoreLevel);
				score.setTeacher(userToken.getPersonId());
				score.setCreateTime(new Date());
				elearningTaskScoreDao.update(score);
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/statistics/getAchievementInfo", method = RequestMethod.GET)
	public Map getAchievementInfo(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer personId=userToken.getPersonId();
			List taskList=elearningPlanCourseDao.getCoursesListByPersonId(personId);
			List examList=elearningExamScoreInfoDao.getListByConditions(personId, null);
			List lectureList=elearningLectureEntryDao.getEntryListByConditions(null, personId);
			List chargeList=elearningPayRecordDao.getRecordListByConditions(null, null, userToken.getLoginName(), null, "1");
			Integer taskNum=taskList!=null? taskList.size() : 0;
			Integer examNum=examList!=null? examList.size() : 0;
			Integer lectureNum=lectureList!=null? lectureList.size() : 0;
			Double chargeNum=0.00;
			if(chargeList!=null){
				for(int i=0;i<chargeList.size();i++){
					ElearningPayRecord record=(ElearningPayRecord) chargeList.get(i);
					chargeNum+=record.getAmount();
				}
			}
			Integer time=0;
			List finishList=elearningTaskFinishDao.getListByConditions(null, null, null, personId, null);
			if(finishList!=null){
				for(int i=0;i<finishList.size();i++){
					ElearningTaskFinish finish=(ElearningTaskFinish) finishList.get(i);
					time+=finish.getTime();
				}
			}
			data.put("taskNum", taskNum);
			data.put("examNum", examNum);
			data.put("lectureNum", lectureNum);
			data.put("chargeNum", chargeNum);
			data.put("time", time);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/statistics/getLearningInfo", method = RequestMethod.GET)
	public Map getLearningInfo(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer personId=userToken.getPersonId();
			List taskList=elearningTeachTaskDao.getAllTeachTaskList();
			List examList=elearningExamInfoDao.getExamListByTaskId(null);
			List lectureList=elearningLectureDao.getLectureListByConditions(null, null);
			List chargeList=elearningPayRecordDao.getRecordListByConditions(null, null, null, null, "1");
			List personList1=infoPersonInfoDao.getListbyConditions(null, null, "S", null);
			List personList2=infoPersonInfoDao.getListbyConditions(null, null, "U", null);
			List personList3=infoPersonInfoDao.getListbyConditions(null, null, "T", null);
			Integer taskNum=taskList!=null? taskList.size() : 0;
			Integer examNum=examList!=null? examList.size() : 0;
			Integer lectureNum=lectureList!=null? lectureList.size() : 0;
			Integer chargeNum=chargeList!=null? chargeList.size() : 0;
			data.put("taskNum", taskNum);
			data.put("examNum", examNum);
			data.put("lectureNum", lectureNum);
			data.put("chargeNum", chargeNum);
			data.put("personNum", personList1.size()+personList2.size());
			data.put("teacherNum", personList3.size());
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/statistics/getCourseSubjectInfo", method = RequestMethod.GET)
	public Map getCourseSubjectInfo(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer personId=userToken.getPersonId();
			List taskList=elearningTeachTaskDao.getAllTeachTaskList();
			List courseList=new ArrayList();
			List dataList2=new ArrayList();
			for(int i=0;taskList!=null && i<taskList.size();i++){
				ElearningTeachTask task=(ElearningTeachTask) taskList.get(i);
				BaseStatisticsActionForm form=new BaseStatisticsActionForm();
				form.setSubject(task.getElearningCourse().getSubject());
				courseList.add(form);
			}
			data.put("courseList", courseList);
			List dataList=new ArrayList();
			List planList=elearningPlanCourseDao.getCoursesListByPersonId(null);
			for(int i=0;planList!=null &&  i<planList.size();i++){
				ElearningPlanCourse plan=(ElearningPlanCourse) planList.get(i);
				if(plan.getState().equals("1")){
					BaseStatisticsActionForm form=new BaseStatisticsActionForm();
					form.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd")).format(plan.getCreateTime()));
					dataList2.add(form);
				}
				ElearningCourse course=plan.getElearningTeachTask().getElearningCourse();
				dataList.add(course);
			}
			List dataList1=new ArrayList();
			data.put("planList", dataList);
			List courseList1=elearningCourseDao.getAllCoursesList();
			for(int i=0;courseList1!=null &&  i<courseList1.size();i++){
				ElearningCourse course=(ElearningCourse) courseList1.get(i);
				dataList1.add(course);
			}
			data.put("courses", dataList1);
			List dataList3=new ArrayList();
			List lectureList=elearningLectureEntryDao.getEntryListByConditions(null, null);
			for(int i=0;lectureList!=null &&  i<lectureList.size();i++){
				ElearningLectureEntry entry=(ElearningLectureEntry) lectureList.get(i);
				BaseStatisticsActionForm form=new BaseStatisticsActionForm();
				form.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd")).format(entry.getCreateTime()));
				dataList3.add(form);
			}
			data.put("entryList", dataList3);
			data.put("selectList", dataList2);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
}
