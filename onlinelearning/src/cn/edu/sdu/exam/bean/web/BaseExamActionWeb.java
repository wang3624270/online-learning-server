package cn.edu.sdu.exam.bean.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.common_business.data_dictionary.server.ServerDataDictionarySI;
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
import cn.edu.sdu.course.dao.ElearningTaskNewsDao;
import cn.edu.sdu.course.dao.ElearningTeachTaskDao;
import cn.edu.sdu.course.dao.ElearningTermDao;
import cn.edu.sdu.course.form.BaseCourseActionForm;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.course.model.ElearningTeachTask;
import cn.edu.sdu.exam.dao.ElearningExamInfoDao;
import cn.edu.sdu.exam.dao.ElearningExamOptionDao;
import cn.edu.sdu.exam.dao.ElearningExamQRelationDao;
import cn.edu.sdu.exam.dao.ElearningExamQuestionDao;
import cn.edu.sdu.exam.dao.ElearningPracticeInfoDao;
import cn.edu.sdu.exam.dao.ElearningPracticeQRelationDao;
import cn.edu.sdu.exam.form.BaseExamActionForm;
import cn.edu.sdu.exam.model.ElearningExamInfo;
import cn.edu.sdu.exam.model.ElearningExamOption;
import cn.edu.sdu.exam.model.ElearningExamQRelation;
import cn.edu.sdu.exam.model.ElearningExamQuestion;
import cn.edu.sdu.exam.model.ElearningPracticeInfo;
import cn.edu.sdu.exam.model.ElearningPracticeQRelation;

@RestController
public class BaseExamActionWeb {
	
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
	private ElearningExamInfoDao elearningExamInfoDao;
	@Autowired
	private ElearningExamOptionDao elearningExamOptionDao;
	@Autowired
	private ElearningExamQuestionDao elearningExamQuestionDao;
	@Autowired
	private ElearningExamQRelationDao elearningExamQRelationDao;
	@Autowired
	private ElearningPracticeInfoDao elearningPracticeInfoDao;
	@Autowired
	private ElearningPracticeQRelationDao elearningPracticeQRelationDao;
	
	@RequestMapping(value = "/examBean/getAllExamList", method = RequestMethod.POST)
	public Map getAllExamList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List examList=elearningExamInfoDao.getExamListByTaskId(null);
			if(examList!=null){
				for(int i=0;i<examList.size();i++){
					ElearningExamInfo exam=(ElearningExamInfo) examList.get(i);
					BaseExamActionForm examForm=new BaseExamActionForm();
					ElearningTeachTask task=elearningTeachTaskDao.find(exam.getTaskId());
					examForm.setExamId(exam.getExamId());
					examForm.setExamTitle(exam.getExamTitle());
					examForm.setTaskName(task.getTaskName());
					examForm.setCourseName(task.getElearningCourse().getCourseName());
					examForm.setStartDate(exam.getStartTime());
					examForm.setEndDate(exam.getEndTime());
					dataList.add(examForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/examBean/getAllTeachTaskOption", method = RequestMethod.POST)
	public Map getAllCourseOption(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List taskList = elearningTeachTaskDao.getAllTeachTaskList();
			if (taskList != null) {
				for (int i = 0; i < taskList.size(); i++) {
					BaseExamActionForm taskForm = new BaseExamActionForm();
					ElearningTeachTask task = (ElearningTeachTask) taskList.get(i);
					taskForm.setTaskId(task.getTaskId());
					taskForm.setTaskName(task.getTaskName());
					dataList.add(taskForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/getExamInfo", method = RequestMethod.POST)
	public Map getExamInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String examId = (String) m.get("examId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ElearningExamInfo exam=elearningExamInfoDao.find(Integer.valueOf(examId));
			data.put("examId", exam.getExamId());
			data.put("examTitle", exam.getExamTitle());
			data.put("taskId", exam.getTaskId());
			data.put("startTime",exam.getStartTime());
			data.put("endTime",exam.getStartTime());
			data.put("remark",exam.getRemark());
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/editExamInfo", method = RequestMethod.POST)
	public Map editExamInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String examId = (String) m.get("examId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String examTitle = (String) m.get("examTitle");
			String taskId = (String) m.get("taskId");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String remark = (String) m.get("remark");
			ElearningExamInfo exam=elearningExamInfoDao.find(Integer.valueOf(examId));
			exam.setExamTitle(examTitle);
			exam.setTaskId(Integer.valueOf(taskId));
			exam.setStartTime(startTime);
			exam.setEndTime(endTime);
			exam.setRemark(remark);
			elearningExamInfoDao.update(exam);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addExam", method = RequestMethod.POST)
	public Map addExam(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String examTitle = (String) m.get("examTitle");
			String taskId = (String) m.get("taskId");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String remark = (String) m.get("remark");
			ElearningExamInfo exam=new ElearningExamInfo();
			exam.setExamTitle(examTitle);
			exam.setTaskId(Integer.valueOf(taskId));
			exam.setStartTime(startTime);
			exam.setEndTime(endTime);
			exam.setRemark(remark);
			elearningExamInfoDao.save(exam);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/getExamQustionList", method = RequestMethod.POST)
	public Map getExamQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String examId = (String) m.get("examId");
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List questionList = elearningExamQRelationDao.getQuestionListByExamId(Integer.valueOf(examId));
			if (questionList != null) {
				for (int i = 0; i < questionList.size(); i++) {
					BaseExamActionForm qForm = new BaseExamActionForm();
					ElearningExamQRelation re = (ElearningExamQRelation) questionList.get(i);
					ElearningExamQuestion question=elearningExamQuestionDao.find(re.getQuestionId());
					qForm.setNumber(re.getNumber());
					qForm.setScore(re.getScore());
					qForm.setId(re.getId());
					qForm.setQuestionId(re.getQuestionId());
					qForm.setQuestion(question.getQuestion());
					ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
					qForm.setQuestionType(si.getDataNameByCode("TMLXM",question.getQuestionType()));
					dataList.add(qForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/getAllQustionList", method = RequestMethod.POST)
	public Map getAllQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List questionList = elearningExamQuestionDao.getQuestionListByQuestionType(null);
			if (questionList != null) {
				for (int i = 0; i < questionList.size(); i++) {
					BaseExamActionForm qForm = new BaseExamActionForm();
					ElearningExamQuestion question = (ElearningExamQuestion) questionList.get(i);
					qForm.setQuestionId(question.getQuestionId());
					qForm.setQuestion(question.getQuestion());
					ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
					qForm.setQuestionType(si.getDataNameByCode("TMLXM",question.getQuestionType()));
					dataList.add(qForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addExamRadioQuestion", method = RequestMethod.POST)
	public Map addExamRadioQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String examId = (String) m.get("examId");
			String question = (String) m.get("question");
			List optionList = (List) m.get("optionList");
			List answerList = (List) m.get("answerList");
			String analysis = (String) m.get("analysis");
			String questionScore= (String) m.get("questionScore");
			ElearningExamQuestion examQuestion=new ElearningExamQuestion();
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			examQuestion.setQuestionType("1");
			examQuestion.setPersonId(userToken.getPersonId());
			examQuestion.setCreateTime(new Date());
			elearningExamQuestionDao.save(examQuestion);//保存题目
			//获得已有题目个数
			Integer hasNum=0;
			List havList=elearningExamQRelationDao.getQuestionListByExamId(Integer.valueOf(examId));
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningExamQRelation relation=new ElearningExamQRelation();
			relation.setExamId(Integer.valueOf(examId));
			relation.setQuestionId(examQuestion.getQuestionId());
			relation.setScore(Integer.valueOf(questionScore));
			relation.setNumber(hasNum+1);
			elearningExamQRelationDao.save(relation);//与考试关联
			String answer="";
			for(int i=0;i<optionList.size();i++){//创建选项
				ElearningExamOption examOption=new ElearningExamOption();
				Map row=(Map) optionList.get(i);
				examOption.setNumber((Integer)row.get("key"));
				examOption.setOptionContent((String)row.get("value"));
				examOption.setQuestionId(examQuestion.getQuestionId());
				elearningExamOptionDao.save(examOption);
				Boolean checked=(Boolean)row.get("checked");
				if(checked==true){
					answer=examOption.getOptionId().toString();//放入正确选项
				}
			}
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addRadioQuestion", method = RequestMethod.POST)
	public Map addRadioQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String question = (String) m.get("question");
			List optionList = (List) m.get("optionList");
			List answerList = (List) m.get("answerList");
			String analysis = (String) m.get("analysis");
			String questionScore= (String) m.get("questionScore");
			ElearningExamQuestion examQuestion=new ElearningExamQuestion();
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			examQuestion.setQuestionType("1");
			examQuestion.setPersonId(userToken.getPersonId());
			examQuestion.setCreateTime(new Date());
			elearningExamQuestionDao.save(examQuestion);//保存题目
			String answer="";
			for(int i=0;i<optionList.size();i++){//创建选项
				ElearningExamOption examOption=new ElearningExamOption();
				Map row=(Map) optionList.get(i);
				examOption.setNumber((Integer)row.get("key"));
				examOption.setOptionContent((String)row.get("value"));
				examOption.setQuestionId(examQuestion.getQuestionId());
				elearningExamOptionDao.save(examOption);
				Boolean checked=(Boolean)row.get("checked");
				if(checked==true){
					answer=examOption.getOptionId().toString();//放入正确选项
				}
			}
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/editExamRadioQuestion", method = RequestMethod.POST)
	public Map editExamRadioQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String questionId = (String) m.get("questionId");
			String question = (String) m.get("question");
			List optionList = (List) m.get("optionList");
			List answerList = (List) m.get("answerList");
			String analysis = (String) m.get("analysis");
			ElearningExamQuestion examQuestion=elearningExamQuestionDao.find(Integer.valueOf(questionId));
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			elearningExamQuestionDao.update(examQuestion);//保存题目
			List oList=elearningExamOptionDao.getOptionListByQuestionId(Integer.valueOf(questionId));
			String answer="";
			for(int i=0;i<optionList.size();i++){//创建选项
				ElearningExamOption examOption=(ElearningExamOption) oList.get(i);
				Map row=(Map) optionList.get(i);
				examOption.setNumber((Integer)row.get("key"));
				examOption.setOptionContent((String)row.get("value"));
				examOption.setQuestionId(examQuestion.getQuestionId());
				elearningExamOptionDao.update(examOption);
				Boolean checked=(Boolean)row.get("checked");
				if(checked==true){
					answer=examOption.getOptionId().toString();//放入正确选项
				}
			}
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addExamMultipleQuestion", method = RequestMethod.POST)
	public Map addExamMultipleQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String examId = (String) m.get("examId");
			String question = (String) m.get("question");
			List optionList = (List) m.get("optionList");
			String analysis = (String) m.get("analysis");
			String questionScore= (String) m.get("questionScore");
			ElearningExamQuestion examQuestion=new ElearningExamQuestion();
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			examQuestion.setQuestionType("2");
			examQuestion.setPersonId(userToken.getPersonId());
			examQuestion.setCreateTime(new Date());
			elearningExamQuestionDao.save(examQuestion);//保存题目
			//获得已有题目个数
			Integer hasNum=0;
			List havList=elearningExamQRelationDao.getQuestionListByExamId(Integer.valueOf(examId));
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningExamQRelation relation=new ElearningExamQRelation();
			relation.setExamId(Integer.valueOf(examId));
			relation.setQuestionId(examQuestion.getQuestionId());
			relation.setScore(Integer.valueOf(questionScore));
			relation.setNumber(hasNum+1);
			elearningExamQRelationDao.save(relation);//与考试关联
			String answer="";
			for(int i=0;i<optionList.size();i++){//创建选项
				ElearningExamOption examOption=new ElearningExamOption();
				Map row=(Map) optionList.get(i);
				examOption.setNumber((Integer)row.get("key"));
				examOption.setOptionContent((String)row.get("value"));
				examOption.setQuestionId(examQuestion.getQuestionId());
				elearningExamOptionDao.save(examOption);
				Boolean checked=(Boolean)row.get("checked");
				if(checked==true){
					if(answer.equals("")){
						answer=examOption.getOptionId().toString();//放入正确选项
					}else{
						answer+=","+examOption.getOptionId().toString();//放入正确选
					}
				}
			}
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addMultipleQuestion", method = RequestMethod.POST)
	public Map addMultipleQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String question = (String) m.get("question");
			List optionList = (List) m.get("optionList");
			String analysis = (String) m.get("analysis");
			String questionScore= (String) m.get("questionScore");
			ElearningExamQuestion examQuestion=new ElearningExamQuestion();
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			examQuestion.setQuestionType("2");
			examQuestion.setPersonId(userToken.getPersonId());
			examQuestion.setCreateTime(new Date());
			elearningExamQuestionDao.save(examQuestion);//保存题目
			String answer="";
			for(int i=0;i<optionList.size();i++){//创建选项
				ElearningExamOption examOption=new ElearningExamOption();
				Map row=(Map) optionList.get(i);
				examOption.setNumber((Integer)row.get("key"));
				examOption.setOptionContent((String)row.get("value"));
				examOption.setQuestionId(examQuestion.getQuestionId());
				elearningExamOptionDao.save(examOption);
				Boolean checked=(Boolean)row.get("checked");
				if(checked==true){
					if(answer.equals("")){
						answer=examOption.getOptionId().toString();//放入正确选项
					}else{
						answer+=","+examOption.getOptionId().toString();//放入正确选
					}
				}
			}
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/editExamMultipleQuestion", method = RequestMethod.POST)
	public Map editExamMultipleQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String questionId = (String) m.get("questionId");
			String question = (String) m.get("question");
			List optionList = (List) m.get("optionList");
			String analysis = (String) m.get("analysis");
			ElearningExamQuestion examQuestion=elearningExamQuestionDao.find(Integer.valueOf(questionId));
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			elearningExamQuestionDao.update(examQuestion);//保存题目
			List oList=elearningExamOptionDao.getOptionListByQuestionId(Integer.valueOf(questionId));
			String answer="";
			for(int i=0;i<optionList.size();i++){//创建选项
				ElearningExamOption examOption=(ElearningExamOption) oList.get(i);
				Map row=(Map) optionList.get(i);
				examOption.setNumber((Integer)row.get("key"));
				examOption.setOptionContent((String)row.get("value"));
				examOption.setQuestionId(examQuestion.getQuestionId());
				elearningExamOptionDao.update(examOption);
				Boolean checked=(Boolean)row.get("checked");
				if(checked==true){
					if(answer.equals("")){
						answer=examOption.getOptionId().toString();//放入正确选项
					}else{
						answer+=","+examOption.getOptionId().toString();//放入正确选
					}
				}
			}
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addExamCompletionQuestion", method = RequestMethod.POST)
	public Map addExamCompletionQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String examId = (String) m.get("examId");
			String question = (String) m.get("question");
			String answer = (String) m.get("answer");
			String analysis = (String) m.get("analysis");
			String questionScore= (String) m.get("questionScore");
			ElearningExamQuestion examQuestion=new ElearningExamQuestion();
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			examQuestion.setQuestionType("3");
			examQuestion.setPersonId(userToken.getPersonId());
			examQuestion.setCreateTime(new Date());
			elearningExamQuestionDao.save(examQuestion);//保存题目
			//获得已有题目个数
			Integer hasNum=0;
			List havList=elearningExamQRelationDao.getQuestionListByExamId(Integer.valueOf(examId));
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningExamQRelation relation=new ElearningExamQRelation();
			relation.setExamId(Integer.valueOf(examId));
			relation.setQuestionId(examQuestion.getQuestionId());
			relation.setScore(Integer.valueOf(questionScore));
			relation.setNumber(hasNum+1);
			elearningExamQRelationDao.save(relation);//与考试关联
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addCompletionQuestion", method = RequestMethod.POST)
	public Map addCompletionQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String question = (String) m.get("question");
			String answer = (String) m.get("answer");
			String analysis = (String) m.get("analysis");
			String questionScore= (String) m.get("questionScore");
			ElearningExamQuestion examQuestion=new ElearningExamQuestion();
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			examQuestion.setQuestionType("3");
			examQuestion.setPersonId(userToken.getPersonId());
			examQuestion.setCreateTime(new Date());
			elearningExamQuestionDao.save(examQuestion);//保存题目
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/editExamCompletionQuestion", method = RequestMethod.POST)
	public Map editExamCompletionQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String questionId = (String) m.get("questionId");
			String question = (String) m.get("question");
			String answer = (String) m.get("answer");
			String analysis = (String) m.get("analysis");
			ElearningExamQuestion examQuestion=elearningExamQuestionDao.find(Integer.valueOf(questionId));
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			elearningExamQuestionDao.update(examQuestion);//保存题目
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/getExamQuestionInfo", method = RequestMethod.POST)
	public Map getExamQuestionInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer questionId = (Integer) m.get("questionId");
			ElearningExamQuestion question=elearningExamQuestionDao.find(questionId);
			data.put("question", question.getQuestion());	
			data.put("analysis", question.getAnalysis());
			data.put("questionType", question.getQuestionType());
			String [] answer=question.getAnswer().split(",");
			List optionList=new ArrayList();
			if(question.getQuestionType().equals("1") || question.getQuestionType().equals("2")){
				List list=elearningExamOptionDao.getOptionListByQuestionId(question.getQuestionId());
				data.put("number",list.size());
				for(int i=0;i<list.size();i++){
					ElearningExamOption option=(ElearningExamOption) list.get(i);
					BaseExamActionForm optionForm=new BaseExamActionForm();
					String optionIdS=option.getOptionId().toString();
					for(int j=0;j<answer.length;j++){
						if(optionIdS.equals(answer[j])){
							optionForm.setChecked("1");
							break;
						}
					}
					optionForm.setOptionContent(option.getOptionContent());
					optionForm.setOptionId(option.getOptionId());
					optionList.add(optionForm);
				}
			}else{
				data.put("answer",question.getAnswer());
			}
			data.put("optionList", optionList);
			//判断该题目是否有考试关联
			List relationList=elearningExamQRelationDao.getRelationListByQuestionId(questionId);
			if(relationList!=null && relationList.size()>1){
				data.put("isRelation", "1");
			}else{
				data.put("isRelation", "0");
			}
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/deleteQuestion", method = RequestMethod.POST)
	public Map deleteQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer questionId = (Integer) m.get("questionId");
			//在线考试
			List relationList=elearningExamQRelationDao.getRelationListByQuestionId(questionId);
			String examInfo="";
			if(relationList!=null && relationList.size()>0){
				for(int i=0;i<relationList.size();i++){
					ElearningExamQRelation r=(ElearningExamQRelation) relationList.get(i);
					ElearningExamInfo question=elearningExamInfoDao.find(r.getExamId());
					examInfo+=question.getExamTitle()+"/";
				}
				return CommonTool.getNodeMapError("抱歉，该题目与 "+examInfo+" 已关联，不能删除！");
			}
			//在线练习
			List relationList1=elearningPracticeQRelationDao.getRelationListByQuestionId(questionId);
			if(relationList1!=null && relationList1.size()>0){
				for(int i=0;i<relationList1.size();i++){
					ElearningPracticeQRelation r=(ElearningPracticeQRelation) relationList1.get(i);
					ElearningPracticeInfo question=elearningPracticeInfoDao.find(r.getPracticeId());
					examInfo+=question.getPracticeTitle()+"/";
				}
				return CommonTool.getNodeMapError("抱歉，该题目与 "+examInfo+" 已关联，不能删除！");
			}
			List optionList=elearningExamOptionDao.getOptionListByQuestionId(questionId);
			if(optionList!=null && optionList.size()>0){
				for(int i=0;i<optionList.size();i++){
					ElearningExamOption option=(ElearningExamOption) optionList.get(i);
					elearningExamOptionDao.delete(option);
				}
			}
			elearningExamQuestionDao.deleteById(questionId);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/deleteExamQuestionR", method = RequestMethod.POST)
	public Map deleteExamQuestionR(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer id = (Integer) m.get("id");
			ElearningExamQRelation r= elearningExamQRelationDao.find(id);
			Integer examId=r.getExamId();
			Integer number=r.getNumber();
			List questionList=elearningExamQRelationDao.getQuestionListByExamId(examId);
			for(int i=0;i<questionList.size();i++){
				ElearningExamQRelation rr=(ElearningExamQRelation) questionList.get(i);
				Integer num=rr.getNumber();
				if(num>number){
					num--;
					rr.setNumber(num);
				}
				elearningExamQRelationDao.update(rr);
			}
			elearningExamQRelationDao.delete(r);
			return CommonTool.getNodeMapOk("恭喜您，删除关联成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/adjustExamQuestion", method = RequestMethod.POST)
	public Map adjustExamQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer id = (Integer) m.get("id");
			Integer direction = (Integer) m.get("direction");
			ElearningExamQRelation r= elearningExamQRelationDao.find(id);
			Integer examId=r.getExamId();
			Integer number=r.getNumber();
			List questionList=elearningExamQRelationDao.getQuestionListByExamId(examId);
			if(direction.equals(1)){
				if(number==1){
					return CommonTool.getNodeMapError("抱歉，调整失败！");
				}else{
					ElearningExamQRelation rr=(ElearningExamQRelation) questionList.get(number-2);
					Integer temp=rr.getNumber();
					rr.setNumber(number);
					r.setNumber(temp);
					elearningExamQRelationDao.update(r);
					elearningExamQRelationDao.update(rr);
				}
			}else{
				if(number==questionList.size()){
					return CommonTool.getNodeMapError("抱歉，调整失败！");
				}else{
					ElearningExamQRelation rr=(ElearningExamQRelation) questionList.get(number);
					Integer temp=rr.getNumber();
					rr.setNumber(number);
					r.setNumber(temp);
					elearningExamQRelationDao.update(r);
					elearningExamQRelationDao.update(rr);
				}
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/getHouseQustionList", method = RequestMethod.POST)
	public Map getHouseQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String examId = (String) m.get("examId");
			List rList=elearningExamQRelationDao.getQuestionListByExamId(Integer.valueOf(examId));
			List questionList = elearningExamQuestionDao.getQuestionListOrderByQuestionType();
			if (questionList != null) {
				for (int i = 0; i < questionList.size(); i++) {
					BaseExamActionForm qForm = new BaseExamActionForm();
					ElearningExamQuestion question = (ElearningExamQuestion) questionList.get(i);
					boolean flag=false;
					for(int j=0;j<rList.size();j++){
						ElearningExamQRelation r=(ElearningExamQRelation) rList.get(j);
						if(r.getQuestionId().equals(question.getQuestionId())){
							flag=true;
							break;
						}
					}
					if(flag==true){
						continue;
					}
					qForm.setQuestionId(question.getQuestionId());
					qForm.setQuestion(question.getQuestion());
					ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
					qForm.setQuestionType(si.getDataNameByCode("TMLXM",question.getQuestionType()));
					dataList.add(qForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addQuestionFormHouse", method = RequestMethod.POST)
	public Map addQuestionFormHouse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String examId = (String) m.get("examId");
			Integer questionId = (Integer) m.get("questionId");
			String score = (String) m.get("score");
			//获得已有题目个数
			Integer hasNum=0;
			List havList=elearningExamQRelationDao.getQuestionListByExamId(Integer.valueOf(examId));
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningExamQRelation relation=new ElearningExamQRelation();
			relation.setExamId(Integer.valueOf(examId));
			relation.setQuestionId(questionId);
			relation.setScore(Integer.valueOf(score));
			relation.setNumber(hasNum+1);
			elearningExamQRelationDao.save(relation);//与考试关联
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/editExamQuestionScore", method = RequestMethod.POST)
	public Map editExamQuestionScore(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer id = (Integer) request.get("id");
			String score = (String) request.get("score");
			ElearningExamQRelation r=elearningExamQRelationDao.find(id);
			r.setScore(Integer.valueOf(score));
			elearningExamQRelationDao.update(r);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/examBean/getAllPracticeList", method = RequestMethod.POST)
	public Map getAllPracticeList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List examList=elearningPracticeInfoDao.getPracticeList();
			if(examList!=null){
				for(int i=0;i<examList.size();i++){
					ElearningPracticeInfo exam=(ElearningPracticeInfo) examList.get(i);
					BaseExamActionForm examForm=new BaseExamActionForm();
					examForm.setPracticeId(exam.getPracticeId());
					examForm.setPracticeTitle(exam.getPracticeTitle());
					String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(exam.getCreateTime());
					examForm.setStartDate(sdate);
					dataList.add(examForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/examBean/addPracticeInfo", method = RequestMethod.POST)
	public Map addPracticeInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String practiceTitle = (String) request.get("practiceTitle");
			ElearningPracticeInfo p=new ElearningPracticeInfo();
			p.setPracticeTitle(practiceTitle);
			p.setCreateTime(new Date());
			p.setPersonId(userToken.getPersonId());
			elearningPracticeInfoDao.save(p);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/examBean/getPracticeInfo", method = RequestMethod.POST)
	public Map getPracticeInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer practiceId = (Integer) m.get("practiceId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ElearningPracticeInfo exam=elearningPracticeInfoDao.find(practiceId);
			data.put("practiceTitle", exam.getPracticeTitle());
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/editPracticeInfo", method = RequestMethod.POST)
	public Map editPracticeInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String practiceId = (String) m.get("practiceId");
		String practiceTitle = (String) m.get("practiceTitle");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ElearningPracticeInfo exam=elearningPracticeInfoDao.find(Integer.valueOf(practiceId));
			exam.setPracticeTitle(practiceTitle);
			elearningPracticeInfoDao.update(exam);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/getPracticeQustionList", method = RequestMethod.POST)
	public Map getPracticeQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String practiceId = (String) m.get("practiceId");
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List questionList = elearningPracticeQRelationDao.getQuestionListByPracticeId(Integer.valueOf(practiceId));
			if (questionList != null) {
				for (int i = 0; i < questionList.size(); i++) {
					BaseExamActionForm qForm = new BaseExamActionForm();
					ElearningPracticeQRelation re = (ElearningPracticeQRelation) questionList.get(i);
					ElearningExamQuestion question=elearningExamQuestionDao.find(re.getQuestionId());
					qForm.setNumber(re.getNumber());
					qForm.setId(re.getId());
					qForm.setQuestionId(re.getQuestionId());
					qForm.setQuestion(question.getQuestion());
					ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
					qForm.setQuestionType(si.getDataNameByCode("TMLXM",question.getQuestionType()));
					dataList.add(qForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addPracticeRadioQuestion", method = RequestMethod.POST)
	public Map addPracticeRadioQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String practiceId = (String) m.get("practiceId");
			String question = (String) m.get("question");
			List optionList = (List) m.get("optionList");
			List answerList = (List) m.get("answerList");
			String analysis = (String) m.get("analysis");
			ElearningExamQuestion examQuestion=new ElearningExamQuestion();
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			examQuestion.setQuestionType("1");
			examQuestion.setPersonId(userToken.getPersonId());
			examQuestion.setCreateTime(new Date());
			elearningExamQuestionDao.save(examQuestion);//保存题目
			//获得已有题目个数
			Integer hasNum=0;
			List havList=elearningPracticeQRelationDao.getQuestionListByPracticeId(Integer.valueOf(practiceId));
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningPracticeQRelation relation=new ElearningPracticeQRelation();
			relation.setPracticeId(Integer.valueOf(practiceId));
			relation.setQuestionId(examQuestion.getQuestionId());
			relation.setNumber(hasNum+1);
			elearningPracticeQRelationDao.save(relation);//与考试关联
			String answer="";
			for(int i=0;i<optionList.size();i++){//创建选项
				ElearningExamOption examOption=new ElearningExamOption();
				Map row=(Map) optionList.get(i);
				examOption.setNumber((Integer)row.get("key"));
				examOption.setOptionContent((String)row.get("value"));
				examOption.setQuestionId(examQuestion.getQuestionId());
				elearningExamOptionDao.save(examOption);
				Boolean checked=(Boolean)row.get("checked");
				if(checked==true){
					answer=examOption.getOptionId().toString();//放入正确选项
				}
			}
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addPracticeMultipleQuestion", method = RequestMethod.POST)
	public Map addPracticeMultipleQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String practiceId = (String) m.get("practiceId");
			String question = (String) m.get("question");
			List optionList = (List) m.get("optionList");
			String analysis = (String) m.get("analysis");
			ElearningExamQuestion examQuestion=new ElearningExamQuestion();
			examQuestion.setQuestion(question);
			examQuestion.setAnalysis(analysis);
			examQuestion.setQuestionType("2");
			examQuestion.setPersonId(userToken.getPersonId());
			examQuestion.setCreateTime(new Date());
			elearningExamQuestionDao.save(examQuestion);//保存题目
			//获得已有题目个数
			Integer hasNum=0;
			List havList=elearningPracticeQRelationDao.getQuestionListByPracticeId(Integer.valueOf(practiceId));
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningPracticeQRelation relation=new ElearningPracticeQRelation();
			relation.setPracticeId(Integer.valueOf(practiceId));
			relation.setQuestionId(examQuestion.getQuestionId());
			relation.setNumber(hasNum+1);
			elearningPracticeQRelationDao.save(relation);//与考试关联
			String answer="";
			for(int i=0;i<optionList.size();i++){//创建选项
				ElearningExamOption examOption=new ElearningExamOption();
				Map row=(Map) optionList.get(i);
				examOption.setNumber((Integer)row.get("key"));
				examOption.setOptionContent((String)row.get("value"));
				examOption.setQuestionId(examQuestion.getQuestionId());
				elearningExamOptionDao.save(examOption);
				Boolean checked=(Boolean)row.get("checked");
				if(checked==true){
					if(answer.equals("")){
						answer=examOption.getOptionId().toString();//放入正确选项
					}else{
						answer+=","+examOption.getOptionId().toString();//放入正确选
					}
				}
			}
			examQuestion.setAnswer(answer);
			elearningExamQuestionDao.update(examQuestion);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/deletePracticeQuestionR", method = RequestMethod.POST)
	public Map deletePracticeQuestionR(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer id = (Integer) m.get("id");
			ElearningPracticeQRelation r= elearningPracticeQRelationDao.find(id);
			Integer practiceId=r.getPracticeId();
			Integer number=r.getNumber();
			List questionList=elearningPracticeQRelationDao.getQuestionListByPracticeId(practiceId);
			for(int i=0;i<questionList.size();i++){
				ElearningPracticeQRelation rr=(ElearningPracticeQRelation) questionList.get(i);
				Integer num=rr.getNumber();
				if(num>number){
					num--;
					rr.setNumber(num);
				}
				elearningPracticeQRelationDao.update(rr);
			}
			elearningPracticeQRelationDao.delete(r);
			return CommonTool.getNodeMapOk("恭喜您，删除关联成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/adjustPracticeQuestion", method = RequestMethod.POST)
	public Map adjustPracticeQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer id = (Integer) m.get("id");
			Integer direction = (Integer) m.get("direction");
			ElearningPracticeQRelation r= elearningPracticeQRelationDao.find(id);
			Integer practiceId=r.getPracticeId();
			Integer number=r.getNumber();
			List questionList=elearningPracticeQRelationDao.getQuestionListByPracticeId(practiceId);
			if(direction.equals(1)){
				if(number==1){
					return CommonTool.getNodeMapError("抱歉，调整失败！");
				}else{
					ElearningPracticeQRelation rr=(ElearningPracticeQRelation) questionList.get(number-2);
					Integer temp=rr.getNumber();
					rr.setNumber(number);
					r.setNumber(temp);
					elearningPracticeQRelationDao.update(r);
					elearningPracticeQRelationDao.update(rr);
				}
			}else{
				if(number==questionList.size()){
					return CommonTool.getNodeMapError("抱歉，调整失败！");
				}else{
					ElearningPracticeQRelation rr=(ElearningPracticeQRelation) questionList.get(number);
					Integer temp=rr.getNumber();
					rr.setNumber(number);
					r.setNumber(temp);
					elearningPracticeQRelationDao.update(r);
					elearningPracticeQRelationDao.update(rr);
				}
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/getHouseQustionListOfPractice", method = RequestMethod.POST)
	public Map getHouseQustionListOfPractice(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String practiceId = (String) m.get("practiceId");
			List rList=elearningPracticeQRelationDao.getQuestionListByPracticeId(Integer.valueOf(practiceId));
			List questionList = elearningExamQuestionDao.getQuestionListOrderByQuestionType();
			if (questionList != null) {
				for (int i = 0; i < questionList.size(); i++) {
					BaseExamActionForm qForm = new BaseExamActionForm();
					ElearningExamQuestion question = (ElearningExamQuestion) questionList.get(i);
					boolean flag=false;
					if(rList!=null){
						for(int j=0;j<rList.size();j++){
							ElearningPracticeQRelation r=(ElearningPracticeQRelation) rList.get(j);
							if(r.getQuestionId().equals(question.getQuestionId())){
								flag=true;
								break;
							}
						}
					}
					if(flag==true){
						continue;
					}
					qForm.setQuestionId(question.getQuestionId());
					qForm.setQuestion(question.getQuestion());
					ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
					qForm.setQuestionType(si.getDataNameByCode("TMLXM",question.getQuestionType()));
					dataList.add(qForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/examBean/addQuestionFormHouseOfPractice", method = RequestMethod.POST)
	public Map addQuestionFormHouseOfPractice(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String practiceId = (String) m.get("practiceId");
			Integer questionId = (Integer) m.get("questionId");
			//获得已有题目个数
			Integer hasNum=0;
			List havList=elearningPracticeQRelationDao.getQuestionListByPracticeId(Integer.valueOf(practiceId));
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningPracticeQRelation relation=new ElearningPracticeQRelation();
			relation.setPracticeId(Integer.valueOf(practiceId));
			relation.setQuestionId(questionId);
			relation.setNumber(hasNum+1);
			elearningPracticeQRelationDao.save(relation);//与考试关联
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	
}
