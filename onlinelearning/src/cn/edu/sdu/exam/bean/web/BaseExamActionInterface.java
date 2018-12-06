package cn.edu.sdu.exam.bean.web;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.common_business.attachment.dao.BaseAttachmentInfoDao;
import org.octopus.common_business.attachment.model.BaseAttachmentInfo;
import org.octopus.common_business.data_dictionary.server.ServerDataDictionarySI;
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

import cn.edu.sdu.common.form.ListOptionInfo;
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
import cn.edu.sdu.course.form.CourseNodeForm;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.AccessoriesInfo;
import cn.edu.sdu.course.model.BaseCollege;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.course.model.ElearningCourseCommentInfo;
import cn.edu.sdu.course.model.ElearningCourseSection;
import cn.edu.sdu.course.model.ElearningInterlocutionInfo;
import cn.edu.sdu.course.model.ElearningPlanCourse;
import cn.edu.sdu.course.model.ElearningSectionAcc;
import cn.edu.sdu.course.model.ElearningTaskNews;
import cn.edu.sdu.course.model.ElearningTeachTask;
import cn.edu.sdu.course.model.ElearningTerm;
import cn.edu.sdu.course.rule.BaseCourseActionRule;
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
import cn.edu.sdu.lecture.model.ElearningLectureEntry;

@RestController
public class BaseExamActionInterface {
	
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
	private BaseAttachmentInfoDao baseAttachmentInfoDao;
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
	
	@RequestMapping(value = "/exam/getQustionList", method = RequestMethod.POST)
	public Map getQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String question = (String) m.get("question");
			String questionType = (String) m.get("questionType");
			List questionList = elearningExamQuestionDao.getQuestionListByConditions(question, questionType);
			if (questionList != null) {
				for (int i = 0; i < questionList.size(); i++) {
					BaseExamActionForm qForm = new BaseExamActionForm();
					ElearningExamQuestion q = (ElearningExamQuestion) questionList.get(i);
					qForm.setQuestionId(q.getQuestionId());
					qForm.setQuestion(q.getQuestion());
					qForm.setAnswer(q.getAnswer());
					qForm.setQuestionType(q.getQuestionType());
					qForm.setAnalysis(q.getAnalysis());
					List optionList=elearningExamOptionDao.getOptionListByQuestionId(q.getQuestionId());
					if(optionList!=null){
						qForm.setOptionList(optionList);
					}
					dataList.add(qForm);
				}
			}
			data.put("questionList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/exam/addOrEditQuestion", method = RequestMethod.POST)
	public Map addRadioQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer examId = (Integer) m.get("examId");
			Integer score = (Integer) m.get("score");
			Integer questionId = (Integer) m.get("questionId");
			String question = (String) m.get("question");
			String questionType = (String) m.get("questionType");
			List optionList = (List) m.get("optionList");
			String answer = (String) m.get("answer");
			List answers = (List) m.get("answers");
			String analysis = (String) m.get("analysis");
			if(questionId!=null){
				ElearningExamQuestion examQuestion=elearningExamQuestionDao.find(questionId);
				examQuestion.setQuestion(question);
				examQuestion.setQuestionType(questionType);
				examQuestion.setAnalysis(analysis);
				examQuestion.setQuestionType(questionType);
				examQuestion.setPersonId(userToken.getPersonId());
				examQuestion.setCreateTime(new Date());
				elearningExamQuestionDao.update(examQuestion);//保存题目
				String answer0="";
				if(questionType.equals("1")){
					for(int i=0;i<optionList.size();i++){//创建选项
						Map row=(Map) optionList.get(i);
						ElearningExamOption examOption=elearningExamOptionDao.find(Integer.valueOf((String)row.get("index")));
						examOption.setOptionContent((String)row.get("optionContent"));
						elearningExamOptionDao.update(examOption);
					}
					answer0=answer;
				}
				if(questionType.equals("2")){
					for(int i=0;i<optionList.size();i++){//创建选项
						Map row=(Map) optionList.get(i);
						ElearningExamOption examOption=elearningExamOptionDao.find(Integer.valueOf((String)row.get("index")));
						examOption.setOptionContent((String)row.get("optionContent"));
						elearningExamOptionDao.update(examOption);
					}
					for(int i=0;i<answers.size();i++){
						String num=(String) answers.get(i);
						if(i!=0){
							answer0+=",";
						}
						answer0+=num;
					}
				}
				if(questionType.equals("3")){
					answer0=answer;
				}
				examQuestion.setAnswer(answer0);
				elearningExamQuestionDao.update(examQuestion);	
			}else{
				ElearningExamQuestion examQuestion=new ElearningExamQuestion();
				examQuestion.setQuestion(question);
				examQuestion.setQuestionType(questionType);
				examQuestion.setAnalysis(analysis);
				examQuestion.setQuestionType(questionType);
				examQuestion.setPersonId(userToken.getPersonId());
				examQuestion.setCreateTime(new Date());
				elearningExamQuestionDao.save(examQuestion);//保存题目
				String answer0="";
				if(questionType.equals("1")){
					Integer [] arrs=new Integer[optionList.size()];
					for(int i=0;i<optionList.size();i++){//创建选项
						ElearningExamOption examOption=new ElearningExamOption();
						Map row=(Map) optionList.get(i);
						examOption.setNumber((Integer)row.get("index"));
						examOption.setOptionContent((String)row.get("optionContent"));
						examOption.setQuestionId(examQuestion.getQuestionId());
						elearningExamOptionDao.save(examOption);
						arrs[i]=examOption.getOptionId();
					}
					answer0=arrs[Integer.valueOf(answer)].toString();
				}
				if(questionType.equals("2")){
					Integer [] arrs=new Integer[optionList.size()];
					for(int i=0;i<optionList.size();i++){//创建选项
						ElearningExamOption examOption=new ElearningExamOption();
						Map row=(Map) optionList.get(i);
						examOption.setNumber((Integer)row.get("index"));
						examOption.setOptionContent((String)row.get("optionContent"));
						examOption.setQuestionId(examQuestion.getQuestionId());
						elearningExamOptionDao.save(examOption);
						arrs[i]=examOption.getOptionId();
					}
					for(int i=0;i<answers.size();i++){
						Integer num=(Integer) answers.get(i);
						if(i!=0){
							answer0+=",";
						}
						answer0+=arrs[num].toString();
					}
				}
				if(questionType.equals("3")){
					answer0=answer;
				}
				examQuestion.setAnswer(answer0);
				elearningExamQuestionDao.update(examQuestion);	
				if(examId!=null){
					//获得已有题目个数
					Integer hasNum=0;
					List havList=elearningExamQRelationDao.getQuestionListByExamId(examId);
					if(havList!=null){
						hasNum=havList.size();
					}
					ElearningExamQRelation relation=new ElearningExamQRelation();
					relation.setId(relation.getId());
					relation.setExamId(examId);
					relation.setQuestionId(examQuestion.getQuestionId());
					relation.setScore(score);
					relation.setNumber(hasNum+1);
					elearningExamQRelationDao.save(relation);//与考试关联
				}
			}
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/exam/getExamList", method = RequestMethod.POST)
	public Map getExamList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String examTitle = (String) m.get("examTitle");
			String taskName = (String) m.get("taskName");
			List examList=elearningExamInfoDao.getExamListByConditions(examTitle, taskName);
			if(examList!=null){
				for(int i=0;i<examList.size();i++){
					ElearningExamInfo exam=(ElearningExamInfo) examList.get(i);
					BaseExamActionForm examForm=new BaseExamActionForm();
					ElearningTeachTask task=elearningTeachTaskDao.find(exam.getTaskId());
					examForm.setExamId(exam.getExamId());
					examForm.setExamTitle(exam.getExamTitle());
					examForm.setTaskId(exam.getTaskId());
					examForm.setTaskName(task.getTaskName());
					examForm.setCourseName(task.getElearningCourse().getCourseName());
					examForm.setStartDate(exam.getStartTime());
					examForm.setEndDate(exam.getEndTime());
					examForm.setRemark(exam.getRemark());
					dataList.add(examForm);
				}
			}
			data.put("examList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/exam/addOrEditExamInfo", method = RequestMethod.POST)
	public Map addOrEditExamInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer examId = (Integer) m.get("examId");
			String examTitle = (String) m.get("examTitle");
			Integer taskId = (Integer) m.get("taskId");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String remark = (String) m.get("remark");
			if(examId!=null){
				ElearningExamInfo exam=elearningExamInfoDao.find(examId);
				exam.setExamTitle(examTitle);
				exam.setTaskId(taskId);
				exam.setStartTime(startTime);
				exam.setEndTime(endTime);
				exam.setRemark(remark);
				elearningExamInfoDao.update(exam);
			}else{
				ElearningExamInfo exam=new ElearningExamInfo();
				exam.setExamTitle(examTitle);
				exam.setTaskId(taskId);
				exam.setStartTime(startTime);
				exam.setEndTime(endTime);
				exam.setRemark(remark);
				elearningExamInfoDao.save(exam);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/exam/getExamQustionList", method = RequestMethod.POST)
	public Map getExamQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer examId = (Integer) m.get("examId");
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List questionList = elearningExamQRelationDao.getQuestionListByExamId(examId);
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
					qForm.setQuestionType(question.getQuestionType());
					qForm.setAnswer(question.getAnswer());
					qForm.setAnalysis(question.getAnalysis());
					List optionList=elearningExamOptionDao.getOptionListByQuestionId(question.getQuestionId());
					if(optionList!=null){
						qForm.setOptionList(optionList);
					}
					dataList.add(qForm);
				}
			}
			data.put("examQustionList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/exam/editExamQuestionScore", method = RequestMethod.POST)
	public Map editExamQuestionScore(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer id = (Integer) request.get("id");
			Integer score = (Integer) request.get("score");
			ElearningExamQRelation r=elearningExamQRelationDao.find(id);
			r.setScore(score);
			elearningExamQRelationDao.update(r);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/exam/adjustExamQuestion", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/exam/deleteExamQuestionR", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/exam/addQuestionFormHouse", method = RequestMethod.POST)
	public Map addQuestionFormHouse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer examId = (Integer) m.get("examId");
			Integer questionId = (Integer) m.get("questionId");
			Integer score = (Integer) m.get("score");
			//获得已有题目个数
			Integer hasNum=0;
			List havList=elearningExamQRelationDao.getQuestionListByExamId(examId);
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningExamQRelation relation=new ElearningExamQRelation();
			relation.setExamId(examId);
			relation.setQuestionId(questionId);
			relation.setScore(score);
			relation.setNumber(hasNum+1);
			elearningExamQRelationDao.save(relation);//与考试关联
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/exam/getHouseQustionList", method = RequestMethod.POST)
	public Map getHouseQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer examId = (Integer) m.get("examId");
			String question0 = (String) m.get("question");
			String questionType = (String) m.get("questionType");
			List rList=elearningExamQRelationDao.getQuestionListByExamId(examId);
			List questionList = elearningExamQuestionDao.getQuestionListByConditions(question0, questionType);
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
					qForm.setQuestionType(question.getQuestionType());
					dataList.add(qForm);
				}
			}
			data.put("questionList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}

}
