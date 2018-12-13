package cn.edu.sdu.exam.bean.web;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import cn.edu.sdu.exam.dao.ElearningExamScoreInfoDao;
import cn.edu.sdu.exam.dao.ElearningExamStuAnswerDao;
import cn.edu.sdu.exam.dao.ElearningPracticeInfoDao;
import cn.edu.sdu.exam.dao.ElearningPracticeQRelationDao;
import cn.edu.sdu.exam.form.BaseExamActionForm;
import cn.edu.sdu.exam.model.ElearningExamInfo;
import cn.edu.sdu.exam.model.ElearningExamOption;
import cn.edu.sdu.exam.model.ElearningExamQRelation;
import cn.edu.sdu.exam.model.ElearningExamQuestion;
import cn.edu.sdu.exam.model.ElearningExamScoreInfo;
import cn.edu.sdu.exam.model.ElearningExamStuAnswer;
import cn.edu.sdu.exam.model.ElearningPracticeInfo;
import cn.edu.sdu.exam.model.ElearningPracticeQRelation;
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
	@Autowired
	private ElearningExamStuAnswerDao elearningExamStuAnswerDao;
	@Autowired
	private ElearningExamScoreInfoDao elearningExamScoreInfoDao;
	
	@RequestMapping(value = "/exam/getQustionList", method = RequestMethod.POST)
	public Map getQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
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
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/addOrEditQuestion", method = RequestMethod.POST)
	public Map addRadioQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer examId = (Integer) m.get("examId");
			Integer practiceId = (Integer) m.get("practiceId");
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
				elearningExamQuestionDao.update(examQuestion);//������Ŀ
				String answer0="";
				if(questionType.equals("1")){
					for(int i=0;i<optionList.size();i++){//����ѡ��
						Map row=(Map) optionList.get(i);
						ElearningExamOption examOption=elearningExamOptionDao.find(Integer.valueOf((String)row.get("index")));
						examOption.setOptionContent((String)row.get("optionContent"));
						elearningExamOptionDao.update(examOption);
					}
					answer0=answer;
				}
				if(questionType.equals("2")){
					for(int i=0;i<optionList.size();i++){//����ѡ��
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
				elearningExamQuestionDao.save(examQuestion);//������Ŀ
				String answer0="";
				if(questionType.equals("1")){
					Integer [] arrs=new Integer[optionList.size()];
					for(int i=0;i<optionList.size();i++){//����ѡ��
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
					for(int i=0;i<optionList.size();i++){//����ѡ��
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
					//���������Ŀ����
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
					elearningExamQRelationDao.save(relation);//�뿼�Թ���
				}	
				if(practiceId!=null){
					//���������Ŀ����
					Integer hasNum=0;
					List havList=elearningPracticeQRelationDao.getQuestionListByPracticeId(practiceId);
					if(havList!=null){
						hasNum=havList.size();
					}
					ElearningPracticeQRelation relation=new ElearningPracticeQRelation();
					relation.setId(relation.getId());
					relation.setPracticeId(practiceId);
					relation.setQuestionId(examQuestion.getQuestionId());
					relation.setNumber(hasNum+1);
					elearningPracticeQRelationDao.save(relation);//�뿼�Թ���
				}
			}
			return CommonTool.getNodeMapOk("��ϲ�������ӳɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/getExamList", method = RequestMethod.POST)
	public Map getExamList(HttpServletRequest httpRequest,
			@RequestBody Object obj) throws ParseException {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
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
					Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(exam.getStartTime());
					Date endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(exam.getEndTime());
					Date currentTime=new Date();
					if(currentTime.getTime()>endTime.getTime()){
						examForm.setFinish("1");
					}
					examForm.setRemark(exam.getRemark());
					examForm.setState(exam.getState());
					dataList.add(examForm);
				}
			}
			data.put("examList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/exam/addOrEditExamInfo", method = RequestMethod.POST)
	public Map addOrEditExamInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer examId = (Integer) m.get("examId");
			String examTitle = (String) m.get("examTitle");
			Integer taskId = (Integer) m.get("taskId");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String remark = (String) m.get("remark");
			String state = (String) m.get("state");
			if(examId!=null){
				ElearningExamInfo exam=elearningExamInfoDao.find(examId);
				exam.setExamTitle(examTitle);
				exam.setTaskId(taskId);
				exam.setStartTime(startTime);
				exam.setPersonId(userToken.getPersonId());
				exam.setCreateTime(new Date());
				exam.setEndTime(endTime);
				exam.setRemark(remark);
				exam.setState(state);
				elearningExamInfoDao.update(exam);
			}else{
				ElearningExamInfo exam=new ElearningExamInfo();
				exam.setPersonId(userToken.getPersonId());
				exam.setCreateTime(new Date());
				exam.setExamTitle(examTitle);
				exam.setTaskId(taskId);
				exam.setStartTime(startTime);
				exam.setEndTime(endTime);
				exam.setRemark(remark);
				exam.setState("0");
				elearningExamInfoDao.save(exam);
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
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
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
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
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/editExamQuestionScore", method = RequestMethod.POST)
	public Map editExamQuestionScore(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer id = (Integer) request.get("id");
			Integer score = (Integer) request.get("score");
			ElearningExamQRelation r=elearningExamQRelationDao.find(id);
			r.setScore(score);
			elearningExamQRelationDao.update(r);
			return CommonTool.getNodeMapOk("��ϲ�����޸ĳɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/exam/adjustExamQuestion", method = RequestMethod.POST)
	public Map adjustExamQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer id = (Integer) m.get("id");
			Integer direction = (Integer) m.get("direction");
			ElearningExamQRelation r= elearningExamQRelationDao.find(id);
			Integer examId=r.getExamId();
			Integer number=r.getNumber();
			List questionList=elearningExamQRelationDao.getQuestionListByExamId(examId);
			if(direction.equals(1)){
				if(number==1){
					return CommonTool.getNodeMapError("��Ǹ������ʧ�ܣ�");
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
					return CommonTool.getNodeMapError("��Ǹ������ʧ�ܣ�");
				}else{
					ElearningExamQRelation rr=(ElearningExamQRelation) questionList.get(number);
					Integer temp=rr.getNumber();
					rr.setNumber(number);
					r.setNumber(temp);
					elearningExamQRelationDao.update(r);
					elearningExamQRelationDao.update(rr);
				}
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/deleteExamQuestionR", method = RequestMethod.POST)
	public Map deleteExamQuestionR(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
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
			return CommonTool.getNodeMapOk("��ϲ����ɾ�������ɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/addQuestionFormHouse", method = RequestMethod.POST)
	public Map addQuestionFormHouse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer examId = (Integer) m.get("examId");
			Integer questionId = (Integer) m.get("questionId");
			Integer score = (Integer) m.get("score");
			//���������Ŀ����
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
			elearningExamQRelationDao.save(relation);//�뿼�Թ���
			return CommonTool.getNodeMapOk("��ϲ�������ӳɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
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
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
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
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/getPracticeList", method = RequestMethod.POST)
	public Map getPracticeList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			String practiceTitle = (String) m.get("practiceTitle");
			String taskName = (String) m.get("taskName");
			List examList=elearningPracticeInfoDao.getPracticeListByConditions(practiceTitle, taskName);
			if(examList!=null){
				for(int i=0;i<examList.size();i++){
					ElearningPracticeInfo exam=(ElearningPracticeInfo) examList.get(i);
					BaseExamActionForm examForm=new BaseExamActionForm();
					examForm.setPracticeId(exam.getPracticeId());
					examForm.setPracticeTitle(exam.getPracticeTitle());
					String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(exam.getCreateTime());
					examForm.setCreateTimeStr(sdate);
					ElearningSectionAcc acc=elearningSectionAccDao.getMapBySectionIdAndAccId(null, examForm.getPracticeId(), "PRACTICE");
					if(acc!=null){
						examForm.setState("�Ѱ�");
					}else{
						examForm.setState("δ��");
					}
					dataList.add(examForm);
				}
			}
			data.put("practiceList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/exam/addOrEditPracticeInfo", method = RequestMethod.POST)
	public Map addOrEditPracticeInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer practiceId = (Integer) request.get("practiceId");
			String practiceTitle = (String) request.get("practiceTitle");
			if(practiceId!=null){
				ElearningPracticeInfo p=elearningPracticeInfoDao.find(practiceId);
				p.setPracticeTitle(practiceTitle);
				p.setCreateTime(new Date());
				p.setPersonId(userToken.getPersonId());
				elearningPracticeInfoDao.update(p);
			}else{
				ElearningPracticeInfo p=new ElearningPracticeInfo();
				p.setPracticeTitle(practiceTitle);
				p.setCreateTime(new Date());
				p.setPersonId(userToken.getPersonId());
				elearningPracticeInfoDao.save(p);
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/exam/getPracticeQustionList", method = RequestMethod.POST)
	public Map getPracticeQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer practiceId = (Integer) m.get("practiceId");
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List questionList = elearningPracticeQRelationDao.getQuestionListByPracticeId(practiceId);
			if (questionList != null) {
				for (int i = 0; i < questionList.size(); i++) {
					BaseExamActionForm qForm = new BaseExamActionForm();
					ElearningPracticeQRelation re = (ElearningPracticeQRelation) questionList.get(i);
					ElearningExamQuestion question=elearningExamQuestionDao.find(re.getQuestionId());
					qForm.setNumber(re.getNumber());
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
			data.put("practiceQustionList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/deletePracticeQuestionR", method = RequestMethod.POST)
	public Map deletePracticeQuestionR(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
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
			return CommonTool.getNodeMapOk("��ϲ����ɾ�������ɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/getHouseQustionListOfPractice", method = RequestMethod.POST)
	public Map getHouseQustionListOfPractice(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer practiceId = (Integer) m.get("practiceId");
			String question0 = (String) m.get("question");
			String questionType = (String) m.get("questionType");
			List rList=elearningPracticeQRelationDao.getQuestionListByPracticeId(practiceId);
			List questionList = elearningExamQuestionDao.getQuestionListByConditions(question0,questionType);;
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
					if(question.getQuestionType().equals("3")){
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
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/addQuestionFormHouseOfPractice", method = RequestMethod.POST)
	public Map addQuestionFormHouseOfPractice(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer practiceId = (Integer) m.get("practiceId");
			Integer questionId = (Integer) m.get("questionId");
			//���������Ŀ����
			Integer hasNum=0;
			List havList=elearningPracticeQRelationDao.getQuestionListByPracticeId(practiceId);
			if(havList!=null){
				hasNum=havList.size();
			}
			ElearningPracticeQRelation relation=new ElearningPracticeQRelation();
			relation.setPracticeId(Integer.valueOf(practiceId));
			relation.setQuestionId(questionId);
			relation.setNumber(hasNum+1);
			elearningPracticeQRelationDao.save(relation);//�뿼�Թ���
			return CommonTool.getNodeMapOk("��ϲ�������ӳɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/adjustPracticeQuestion", method = RequestMethod.POST)
	public Map adjustPracticeQuestion(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer id = (Integer) m.get("id");
			Integer direction = (Integer) m.get("direction");
			ElearningPracticeQRelation r= elearningPracticeQRelationDao.find(id);
			Integer practiceId=r.getPracticeId();
			Integer number=r.getNumber();
			List questionList=elearningPracticeQRelationDao.getQuestionListByPracticeId(practiceId);
			if(direction.equals(1)){
				if(number==1){
					return CommonTool.getNodeMapError("��Ǹ������ʧ�ܣ�");
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
					return CommonTool.getNodeMapError("��Ǹ������ʧ�ܣ�");
				}else{
					ElearningPracticeQRelation rr=(ElearningPracticeQRelation) questionList.get(number);
					Integer temp=rr.getNumber();
					rr.setNumber(number);
					r.setNumber(temp);
					elearningPracticeQRelationDao.update(r);
					elearningPracticeQRelationDao.update(rr);
				}
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/getUnfinishedExamList", method = RequestMethod.GET)
	public Map getUnfinishedExamList(HttpServletRequest httpRequest) throws ParseException {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List planList = elearningPlanCourseDao.getCoursesListByPersonId(userToken.getPersonId());
			List list=new ArrayList();
			if(planList!=null){
				for (int i = 0; i < planList.size(); i++) {
					ElearningPlanCourse plan = (ElearningPlanCourse) planList.get(i);
					ElearningTeachTask task=plan.getElearningTeachTask();
					Integer taskId=task.getTaskId();
					List examList=elearningExamInfoDao.getExamListByTaskId(taskId);
					if(examList!=null){
						for(int j=0;j<examList.size();j++){
							BaseExamActionForm form = new BaseExamActionForm();
							ElearningExamInfo exam=(ElearningExamInfo) examList.get(j);
						    Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(exam.getStartTime());
							Date endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(exam.getEndTime());
							Date currentTime=new Date();
							if(currentTime.getTime()>endTime.getTime()){
								continue;
							}
							if(startTime.getTime()<currentTime.getTime() && currentTime.getTime()<endTime.getTime()){
								form.setState("1");
							}else{
								form.setState("0");
							}
							form.setTaskId(taskId);
							form.setCourseId(task.getElearningCourse().getCourseId());
							form.setExamId(exam.getExamId());
							form.setExamTitle(exam.getExamTitle());
							form.setTaskName(task.getTaskName());
							form.setStartDate(exam.getStartTime());
							form.setEndDate(exam.getEndTime());
							List questionList = elearningExamQRelationDao.getQuestionListByExamId(exam.getExamId());
							Integer score=0;
							if(questionList!=null){
								for(int k=0;k<questionList.size();k++){
									ElearningExamQRelation r=(ElearningExamQRelation) questionList.get(k);
									score+=r.getScore();
								}
							}
							form.setScore(score);
							list.add(form);
						}
					}
				}
			}
			data.put("examList", list);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/exam/getOnlineExamQustionList", method = RequestMethod.POST)
	public Map getOnlineExamQustionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer examId = (Integer) m.get("examId");
			Integer pageIndex = (Integer) m.get("pageIndex");
			Integer pageSize = (Integer) m.get("pageSize");
			List questionList = elearningExamQRelationDao.getQuestionListByExamId(examId);
			if (questionList != null) {
				for (int i = (pageIndex-1)*pageSize; i < questionList.size() && i< pageIndex*pageSize; i++) {
					BaseExamActionForm qForm = new BaseExamActionForm();
					ElearningExamQRelation re = (ElearningExamQRelation) questionList.get(i);
					ElearningExamQuestion question=elearningExamQuestionDao.find(re.getQuestionId());
					qForm.setExamId(examId);
					qForm.setNumber(re.getNumber());
					qForm.setScore(re.getScore());
					//qForm.setId(re.getId());
					qForm.setQuestionId(re.getQuestionId());
					qForm.setQuestion(question.getQuestion());
					qForm.setQuestionType(question.getQuestionType());
					ElearningExamStuAnswer examAnswer=elearningExamStuAnswerDao.getElearningExamStuAnswerByQuestionId(examId, re.getQuestionId(), userToken.getPersonId());
					String answer="";
					if(examAnswer!=null){
						answer=examAnswer.getAnswer();
					}
					qForm.setAnswer(answer);
					//qForm.setAnswer(question.getAnswer());
					//qForm.setAnalysis(question.getAnalysis());
					List optionList=elearningExamOptionDao.getOptionListByQuestionId(question.getQuestionId());
					if(optionList!=null){
						qForm.setOptionList(optionList);
					}
					dataList.add(qForm);
				}
				data.put("resultSize", questionList.size());
			}
			data.put("examQustionList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/submitExamAnswers", method = RequestMethod.POST)
	public Map submitExamAnswers(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List list = (List) m.get("list");
			for(int i=0;i<list.size();i++){
				Map q=(Map) list.get(i);
				Integer examId=(Integer) q.get("examId");
				Integer questionId=(Integer) q.get("questionId");
				Integer stuId=userToken.getPersonId();
				Integer score=(Integer) q.get("score");
				String questionType=(String) q.get("questionType");
				ElearningExamStuAnswer examAnswer=elearningExamStuAnswerDao.getElearningExamStuAnswerByQuestionId(examId, questionId, stuId);
				String answer="";
				if(questionType.equals("1") || questionType.equals("3")){
					answer=(String) q.get("answer");
				}
				if(questionType.equals("2")){
					List aList=(List) q.get("answer");
					for(int j=0;j<aList.size();j++){
						String num=(String) aList.get(j);
						if(j!=0){
							answer+=",";
						}
						answer+=num;
					}
				}
				if(examAnswer!=null){
					examAnswer.setAnswer(answer);
					examAnswer.setExamId(examId);
					examAnswer.setQuestionId(questionId);
					examAnswer.setStuId(stuId);
					examAnswer.setAnswerTime(new Date());
					examAnswer.setScore(score);
					elearningExamStuAnswerDao.update(examAnswer);
				}else{
					ElearningExamStuAnswer po=new ElearningExamStuAnswer();
					po.setAnswer(answer);
					po.setExamId(examId);
					po.setQuestionId(questionId);
					po.setStuId(stuId);
					po.setAnswerTime(new Date());
					po.setScore(score);
					elearningExamStuAnswerDao.save(po);
				}
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/getFinishNumber", method = RequestMethod.POST)
	public Map submitExam(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer examId = (Integer) m.get("examId");
			List questionList = elearningExamQRelationDao.getQuestionListByExamId(examId);
			Integer total=0;
			Integer finish=0;
			if (questionList != null) {
				total=questionList.size();
			}
			List list=elearningExamStuAnswerDao.getListByConditions(examId, null, userToken.getPersonId());
			if(list!=null){
				finish=list.size();
			}
			data.put("total",total);
			data.put("finish", finish);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/exam/getFinishedExamList", method = RequestMethod.GET)
	public Map getFinishedExamList(HttpServletRequest httpRequest) throws ParseException {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List planList = elearningPlanCourseDao.getCoursesListByPersonId(userToken.getPersonId());
			List list=new ArrayList();
			if(planList!=null){
				for (int i = 0; i < planList.size(); i++) {
					ElearningPlanCourse plan = (ElearningPlanCourse) planList.get(i);
					ElearningTeachTask task=plan.getElearningTeachTask();
					Integer taskId=task.getTaskId();
					List examList=elearningExamInfoDao.getExamListByTaskId(taskId);
					if(examList!=null){
						for(int j=0;j<examList.size();j++){
							BaseExamActionForm form = new BaseExamActionForm();
							ElearningExamInfo exam=(ElearningExamInfo) examList.get(j);
							Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(exam.getStartTime());
							Date endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(exam.getEndTime());
							Date currentTime=new Date();
							if(currentTime.getTime()<endTime.getTime()){
								continue;
							}
							form.setTaskId(taskId);
							form.setCourseId(task.getElearningCourse().getCourseId());
							form.setExamId(exam.getExamId());
							form.setExamTitle(exam.getExamTitle());
							form.setTaskName(task.getTaskName());
							form.setStartDate(exam.getStartTime());
							form.setEndDate(exam.getEndTime());
							List questionList = elearningExamQRelationDao.getQuestionListByExamId(exam.getExamId());
							Integer score=0;
							if(questionList!=null){
								for(int k=0;k<questionList.size();k++){
									ElearningExamQRelation r=(ElearningExamQRelation) questionList.get(k);
									score+=r.getScore();
								}
							}
							form.setScore(score);
							ElearningExamScoreInfo scoreInfo=elearningExamScoreInfoDao.getElearningExamScoreInfoByConditions(userToken.getPersonId(), exam.getExamId());
							form.setAchieve(scoreInfo.getScore());
							form.setState(exam.getState());
							list.add(form);
						}
					}
				}
			}
			data.put("examList", list);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/exam/calculateScore", method = RequestMethod.POST)
	public Map calculateScore(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer examId = (Integer) m.get("examId");
			ElearningExamInfo exam=elearningExamInfoDao.find(examId);
			List planList=elearningPlanCourseDao.getTaskListByConditions(null, null, null, exam.getTaskId());
			if(planList!=null){
				for(int i=0;i<planList.size();i++){
					ElearningPlanCourse plan=(ElearningPlanCourse) planList.get(i);
					Integer personId=plan.getStuId();
					List answerList=elearningExamStuAnswerDao.getListByConditions(examId, null, personId);
					Integer score=0;
					if(answerList!=null){
						for(int j=0;j<answerList.size();j++){
							ElearningExamStuAnswer answer=(ElearningExamStuAnswer) answerList.get(j);
							Integer questionId=answer.getQuestionId();
							ElearningExamQuestion question=elearningExamQuestionDao.find(questionId);
							if(question.getAnswer().equals(answer.getAnswer())){
								score+=answer.getScore();
							}
						}
					}
					ElearningExamScoreInfo scoreInfo=elearningExamScoreInfoDao.getElearningExamScoreInfoByConditions(personId, examId);
					if(scoreInfo!=null){
						scoreInfo.setScore(score);
						scoreInfo.setCreateTime(new Date());
						elearningExamScoreInfoDao.update(scoreInfo);
					}else{
						ElearningExamScoreInfo sI=new ElearningExamScoreInfo();
						sI.setScore(score);
						sI.setExamId(examId);
						sI.setStuId(personId);
						sI.setCreateTime(new Date());
						elearningExamScoreInfoDao.save(sI);
					}
					
				}
			}
			exam.setState("1");
			elearningExamInfoDao.update(exam);
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	
	

}