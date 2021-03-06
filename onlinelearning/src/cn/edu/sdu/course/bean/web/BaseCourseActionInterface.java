package cn.edu.sdu.course.bean.web;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import cn.edu.sdu.course.dao.ElearningTaskChargeDao;
import cn.edu.sdu.course.dao.ElearningTaskFinishDao;
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
import cn.edu.sdu.course.model.ElearningTaskCharge;
import cn.edu.sdu.course.model.ElearningTaskFinish;
import cn.edu.sdu.course.model.ElearningTaskNews;
import cn.edu.sdu.course.model.ElearningTeachTask;
import cn.edu.sdu.course.model.ElearningTerm;
import cn.edu.sdu.course.rule.BaseCourseActionRule;
import cn.edu.sdu.exam.dao.ElearningExamInfoDao;
import cn.edu.sdu.exam.dao.ElearningExamQRelationDao;
import cn.edu.sdu.exam.dao.ElearningExamQuestionDao;
import cn.edu.sdu.exam.dao.ElearningExamScoreInfoDao;
import cn.edu.sdu.exam.dao.ElearningPracticeInfoDao;
import cn.edu.sdu.exam.dao.ElearningPracticeQRelationDao;
import cn.edu.sdu.exam.form.BaseExamActionForm;
import cn.edu.sdu.exam.model.ElearningExamInfo;
import cn.edu.sdu.exam.model.ElearningExamScoreInfo;
import cn.edu.sdu.exam.model.ElearningPracticeInfo;
import cn.edu.sdu.lecture.model.ElearningLectureEntry;
import cn.edu.sdu.manage.dao.ElearningPayRecordDao;
import cn.edu.sdu.manage.model.ElearningPayRecord;
import cn.edu.sdu.statistics.dao.ElearningTaskScoreDao;
import cn.edu.sdu.statistics.model.ElearningTaskScore;

@RestController
public class BaseCourseActionInterface {
	
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
	
	@RequestMapping(value = "/course/getTermList", method = RequestMethod.GET)
	public Map getTermList(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			List termList = elearningTermDao.getAllTermList();
			List list=new ArrayList();
			for (int i = 0; i < termList.size(); i++) {
				BaseCourseActionForm termForm = new BaseCourseActionForm();
				ElearningTerm term = (ElearningTerm) termList.get(i);
				termForm.setTermId(term.getTermId());
				termForm.setTermName(term.getTermName());
				termForm.setStartTimeStr(term.getStartTime());
				termForm.setEndTimeStr(term.getEndTime());
				list.add(termForm);
			}
			data.put("termList", list);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/course/addOrEditTerm", method = RequestMethod.POST)
	public Map addOrEditTerm(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		Integer termId = (Integer) m.get("termId");
		String termName = (String) m.get("termName");
		String startTime = (String) m.get("startTime");
		String endTime = (String) m.get("endTime");
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			if(termId!=null && !termId.equals("")){
				ElearningTerm term = elearningTermDao.getTermInfoById(termId);
				term.setTermName(termName);
				term.setStartTime(startTime);
				term.setEndTime(endTime);
				elearningTermDao.update(term);
			}else{
				ElearningTerm term = new ElearningTerm();
				term.setTermName(termName);
				term.setStartTime(startTime);
				term.setEndTime(endTime);
				elearningTermDao.save(term);
			}
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，添加失败！");
		}
	}
	
	@RequestMapping(value = "/course/deleteTerm", method = RequestMethod.POST)
	public Map deleteTerm(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Integer termId = (Integer) m.get("termId");
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			ElearningTerm term = elearningTermDao.getTermInfoById(termId);
			elearningTermDao.delete(term);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}

	@RequestMapping(value = "/course/getResourceList", method = RequestMethod.POST)
	public Map getResourceList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String accName = (String) request.get("accName");
			String accType = (String) request.get("accType");
			String uploader = (String) request.get("uploader");
			List resourceList=accessoriesInfoDao.getResourceListByConditions(accName, accType, uploader);
			if(resourceList!=null){
				for(int i=0;i<resourceList.size();i++){
					AccessoriesInfo acc=(AccessoriesInfo) resourceList.get(i);
					BaseCourseActionForm accForm=new BaseCourseActionForm();
					accForm.setAccId(acc.getId());
					accForm.setAccName(acc.getAccName());
					accForm.setAccType(acc.getAccType());
					accForm.setFolderMap("");
					accForm.setSectionMap("");
					InfoPersonInfo info=infoPersonInfoDao.getInfoPersonInfoByPersonId(acc.getAccUploader());
					if(info!=null){
						accForm.setUploader(info.getPerName());
					}
					String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(acc.getUploadTime());
					accForm.setUploadDate(sdate);
					dataList.add(accForm);
				}
			}
			data.put("resourceList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/uploadResource", method = RequestMethod.POST)
	public Map uploadResource(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request,HttpSession session) throws IllegalStateException,
			IOException {
		String personId=request.getParameter("personId");
		if (files != null && files.length > 0) {
			if (files != null && files.length > 0) {
				// 循环获取file数组中的文件
				for (int i = 0; i < files.length; i++) {
					//数据库中创建一条记录
					AccessoriesInfo acc=new AccessoriesInfo();
					accessoriesInfoDao.save(acc);
					MultipartFile file = files[i];
					String accName=file.getOriginalFilename();
					String accType = accName.substring(accName.lastIndexOf(".")+1);
					String fileName = acc.getId()+"."+accType;
					String accUrl="elearning/resource/"+fileName;
					FileUtility.uploadFile(file.getInputStream(), accUrl);//文件上传
					//将记录保存到数据库
					acc.setAccName(accName);
					acc.setAccUrl(accUrl);
					acc.setAccType(accType);
					acc.setFileType(accType);
					acc.setAccUploader(Integer.valueOf(personId));
					acc.setUploadTime(new Date());
					acc.setFileSize(file.getSize());
					accessoriesInfoDao.update(acc);
				}
			}
		}
		return CommonTool.getNodeMapOk("恭喜您，上传成功！");
	}
	
	@RequestMapping(value = "/course/deleteSource", method = RequestMethod.POST)
	public Map deleteSource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer accId = (Integer) request.get("accId");
		if (userToken != null) {// 登录信息不为空
			//首先判断是否已经有关联记录
			List folderAccList=accessoriesFolderAccDao.getFolderListByAccId(accId);
			if(folderAccList!=null && folderAccList.size()>0 ){
				return CommonTool.getNodeMapError("抱歉，删除失败。失败原因：该资源已有课程目录关联！");
			}
			List sectionAccList=elearningSectionAccDao.getListByAccId(accId);
			if(sectionAccList!=null && sectionAccList.size()>0){
				return CommonTool.getNodeMapError("抱歉，删除失败。失败原因：该资源已有课程节次关联！");
			}
			AccessoriesInfo oldInfo=accessoriesInfoDao.getAccById(accId);
			FileUtility.deleteFile(oldInfo.getAccUrl());
			accessoriesInfoDao.deleteById(accId);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/course/getCourseList", method = RequestMethod.POST)
	public Map getAllCourse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String courseName = (String) m.get("courseName");
			String courseType = (String) m.get("courseType");
			List courseList = elearningCourseDao
					.getCoursesListByConditions(courseName, courseType);
			List dataList = new ArrayList();
			if(courseList!=null){
				for (int i = 0; i < courseList.size(); i++) {
					BaseCourseActionForm courseForm = new BaseCourseActionForm();
					ElearningCourse course = (ElearningCourse) courseList.get(i);
					courseForm.setCourseId(course.getCourseId());
					courseForm.setCourseName(course.getCourseName());
					courseForm.setCourseNum(course.getCourseNum());
					courseForm.setCourseEngName(course.getCourseEngName());
					courseForm.setCollegeName(baseCollegeDao
							.getCollegeNameById(course.getCollegeId()));
					courseForm.setCourseType(course.getCourseType());
					courseForm.setTeachGroup(course.getTeachGroup());
					courseForm.setCoverImgAcc(course.getCoverImgAcc());
					dataList.add(courseForm);
				}
			}
			data.put("courseList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/course/getAllCollegeOption", method = RequestMethod.GET)
	public Map getAllCollegeOption(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List collegeList = baseCollegeDao.getAllCollegeList();
			if (collegeList != null) {
				for (int i = 0; i < collegeList.size(); i++) {
					BaseCourseActionForm collegeForm = new BaseCourseActionForm();
					BaseCollege college = (BaseCollege) collegeList.get(i);
					collegeForm.setValue(college.getCollegeId().toString());
					collegeForm.setLabel(college.getCollegeName());
					dataList.add(collegeForm);
				}
			}
			data.put("colleges", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/course/uploadCoverImg", method = RequestMethod.POST)
	public Map uploadCoverImg(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request,HttpSession session) throws IllegalStateException,
			IOException {
		//获得id
		String personId=request.getParameter("personId");
		String courseIds=request.getParameter("courseId");
		//数据库中创建一条记录
		BaseAttachmentInfo attach=new BaseAttachmentInfo();
		baseAttachmentInfoDao.save(attach);
		MultipartFile file = files[0];
		String accName=file.getOriginalFilename();
		String accType = accName.substring(accName.lastIndexOf(".")+1);
		String fileName = attach.getAttachId()+"."+accType;
		String accUrl="elearning/coverImg/"+fileName;
		FileUtility.uploadFile(file.getInputStream(), accUrl);//文件上传
		//图片缩放
		BaseCourseActionRule.zoomImage(accUrl, accUrl, 520, 303);
		//将记录保存到数据库
		attach.setFileName(accName);
		attach.setUrlAddress(accUrl);
		attach.setAttachType(accType);
		attach.setOwnerId(Integer.valueOf(personId));
		attach.setUploadTime(new Date());
		long size=file.getSize();
		attach.setRemark(String.valueOf(size));
		baseAttachmentInfoDao.update(attach);
		ElearningCourse course = elearningCourseDao
				.getCourseInfoByCourseId(Integer.valueOf(courseIds));
		if(course!=null && course.getCoverImgAcc()!=null){
			BaseAttachmentInfo old=baseAttachmentInfoDao.find(course.getCoverImgAcc());
			if(old!=null){
				FileUtility.deleteFile(old.getUrlAddress());
				baseAttachmentInfoDao.delete(old);
			}
		}
		course.setCoverImgAcc(attach.getAttachId());
		elearningCourseDao.update(course);
		return CommonTool.getNodeMapOk("恭喜您，上传成功！");
	}
	
	@RequestMapping(value = "/course/addOrEditCourseInfo", method = RequestMethod.POST)
	public Map addOrEditCourseInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer courseId = (Integer) m.get("courseId");
			String courseName = (String) m.get("courseName");
			String courseEngName = (String) m.get("courseEngName");
			String courseNum = (String) m.get("courseNum");
			String collegeId = (String) m.get("collegeId");
			String courseType = (String) m.get("courseType");
			String teachGroup = (String) m.get("teachGroup");
			String book = (String) m.get("book");
			String subject = (String) m.get("subject");
			String reference = (String) m.get("reference");
			String briefIntroduction = (String) m.get("briefIntroduction");
			if(courseId!=null && !courseId.equals("")){
				ElearningCourse course = elearningCourseDao
						.getCourseInfoByCourseId(Integer.valueOf(courseId));
				course.setCourseName(courseName);
				course.setCourseEngName(courseEngName);
				course.setCourseNum(courseNum);
				course.setCollegeId(Integer.valueOf(collegeId));
				course.setCourseType(courseType);
				course.setTeachGroup(teachGroup);
				course.setBook(book);
				course.setReference(reference);
				course.setBriefIntroduction(briefIntroduction);
				course.setCreatorId(userToken.getPersonId());
				course.setCreateTime(new Date());
				course.setSubject(subject);
				elearningCourseDao.update(course);
				return CommonTool.getNodeMapOk("恭喜您，操作成功！");
			}else{
				ElearningCourse course=new ElearningCourse();
				course.setCourseName(courseName);
				course.setCourseEngName(courseEngName);
				course.setCourseNum(courseNum);
				course.setCollegeId(Integer.valueOf(collegeId));
				course.setCourseType(courseType);
				course.setTeachGroup(teachGroup);
				course.setBook(book);
				course.setReference(reference);
				course.setBriefIntroduction(briefIntroduction);
				course.setCreatorId(userToken.getPersonId());
				course.setCreateTime(new Date());
				course.setSubject(subject);
				elearningCourseDao.save(course);
				return CommonTool.getNodeMap(course, null);
			}
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getCourseInfo", method = RequestMethod.POST)
	public Map getCourseInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer courseId = (Integer) m.get("courseId");
			ElearningCourse course = elearningCourseDao
					.getCourseInfoByCourseId(courseId);
			BaseCourseActionForm courseForm = new BaseCourseActionForm();
			courseForm.setCourseId(course.getCourseId());
			courseForm.setCourseName(course.getCourseName());
			courseForm.setCourseEngName(course.getCourseEngName());
			courseForm.setCourseNum(course.getCourseNum());
			courseForm.setCollegeId(course.getCollegeId());
			courseForm.setCourseType(course.getCourseType());
			courseForm.setTeachGroup(course.getTeachGroup());
			courseForm.setBook(course.getBook());
			courseForm.setReference(course.getReference());
			courseForm.setBriefIntroduction(course.getBriefIntroduction());
			courseForm.setSubject(course.getSubject());
			if(course.getCoverImgAcc()!=null){
				courseForm.setCoverImgAcc(course.getCoverImgAcc());
			}else{
				courseForm.setCoverImgAcc(0);
			}
			data.put("courseInfo", courseForm);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/course/deleteCourse", method = RequestMethod.POST)
	public Map deleteCourse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer courseId = (Integer) m.get("courseId");
			ElearningCourse course = elearningCourseDao
					.getCourseInfoByCourseId(courseId);
			if(course!=null && course.getCoverImgAcc()!=null){
				BaseAttachmentInfo old=baseAttachmentInfoDao.find(course.getCoverImgAcc());
				if(old!=null){
					FileUtility.deleteFile(old.getUrlAddress());
					baseAttachmentInfoDao.delete(old);
				}
			}
			elearningCourseDao.delete(course);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}
	
	@RequestMapping(value = "/course/getTeachTaskList", method = RequestMethod.POST)
	public Map getTeachTaskList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String taskName = (String) m.get("taskName");
			String courseType = (String) m.get("courseType");
			List teachTaskList = elearningTeachTaskDao.getTeachTaskListByConditions(taskName, courseType);
			if(teachTaskList!=null){
				for (int i = 0; i < teachTaskList.size(); i++) {
					BaseCourseActionForm teachTaskForm = new BaseCourseActionForm();
					ElearningTeachTask teachTask = (ElearningTeachTask) teachTaskList.get(i);
					teachTaskForm.setTaskId(teachTask.getTaskId());
					teachTaskForm.setTaskName(teachTask.getTaskName());
					teachTaskForm.setStartDate(teachTask.getStartDate().substring(0, 10));
					teachTaskForm.setEndDate(teachTask.getEndDate().substring(0, 10));
//					ElearningTerm term=teachTask.getElearningTerm();
//					teachTaskForm.setTermName(term.getTermName());
					ElearningCourse course=teachTask.getElearningCourse();
					teachTaskForm.setCoverImgAcc(course.getCoverImgAcc());
					teachTaskForm.setCourseId(course.getCourseId());
					teachTaskForm.setCourseNum(course.getCourseNum());
					teachTaskForm.setCourseName(course.getCourseName());
					teachTaskForm.setCourseEngName(course.getCourseEngName());
					teachTaskForm.setCollegeName(baseCollegeDao.getCollegeNameById(course.getCollegeId()));
					teachTaskForm.setBriefIntroduction(course.getBriefIntroduction());
					teachTaskForm.setTeachGroup(course.getTeachGroup());
					//课程资讯
					Integer newsNum=0;
					List newsList=elearningTaskNewsDao.getNewsListByTaskId(teachTask.getTaskId());
					if(newsList!=null){
						newsNum=newsList.size();
					}
					teachTaskForm.setNewsNum(newsNum);
					//课程综合评分
					teachTaskForm.setNumber(0);
					Double courseScore=0.0;
					List scoreList=elearningCourseCommentInfoDao.getElearningResourceScoreListByCondition(teachTask.getTaskId(),null);
					if(scoreList!=null){ 
						teachTaskForm.setNumber(scoreList.size());
						Double count=0.0; 
						for(int j=0;j<scoreList.size();j++){
							ElearningCourseCommentInfo score=(ElearningCourseCommentInfo) scoreList.get(j);
							if(score.getScore()!=null && !score.getScore().equals("")){
								count+=score.getScore();
							}
						}
						if(scoreList.size()>0){
							courseScore=count/scoreList.size(); 
						}
					}
					NumberFormat nf = NumberFormat.getNumberInstance();  
			        // 2是显示的小数点后的显示的最多位,显示的最后位是舍入的  
			        nf.setMaximumFractionDigits(2);  
			        String result = nf.format(courseScore);  
			        teachTaskForm.setCourseScoreStr(result);
					teachTaskForm.setCourseType(course.getCourseType());
					//问答管理
					Integer answerNum=0;
					Integer questionNum=0;
				    List interlocutionList=elearningInterlocutionInfoDao.getInterlocutionListByCourseIdAndSectionId(teachTask.getTaskId(), null);
				    if(interlocutionList!=null){
				    	questionNum=interlocutionList.size();
					    for(int j=0;j<interlocutionList.size();j++){
					    	ElearningInterlocutionInfo interlocution=(ElearningInterlocutionInfo) interlocutionList.get(j);
					    	if(interlocution.getAnswerTime()!=null){
					    		answerNum++;
					    	}
					    }
				    }
				    teachTaskForm.setQuestionNum(questionNum);
				    teachTaskForm.setAnswerNum(answerNum);
				    teachTaskForm.setUnanswerNum(questionNum-answerNum);
				    //课程收费
				    ElearningTaskCharge charge=elearningTaskChargeDao.getChargeByTaskId(teachTask.getTaskId());
				    if(charge!=null){
				    	teachTaskForm.setPrice(charge.getPrice());
				    	teachTaskForm.setChargeId(charge.getChargeId());
				    }
					dataList.add(teachTaskForm);
				}
			}
			data.put("teachTaskList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getAllCourseOption", method = RequestMethod.GET)
	public Map getAllCourseOption(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List courseList = elearningCourseDao.getAllCoursesList();
			if (courseList != null) {
				for (int i = 0; i < courseList.size(); i++) {
					BaseCourseActionForm courseForm = new BaseCourseActionForm();
					ElearningCourse course = (ElearningCourse) courseList.get(i);
					courseForm.setLabel(course.getCourseName()+"  "+baseCollegeDao
							.getCollegeNameById(course.getCollegeId()));
					courseForm.setValue(course.getCourseId().toString());
					dataList.add(courseForm);
				}
			}
			data.put("courses", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/course/addOrEditTeachTask", method = RequestMethod.POST)
	public Map addOrEditTeachTask(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer taskId = (Integer) m.get("taskId");
			String teachTaskName = (String) m.get("taskName");
			String courseId = (String) m.get("courseId");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String price = (String) m.get("price");
			String remark = (String) m.get("remark");
			if(taskId!=null && !taskId.equals("")){
				ElearningTeachTask teachTask = elearningTeachTaskDao.getTeachTaskByTaskId(Integer.valueOf(taskId));
				teachTask.setTaskName(teachTaskName);
				teachTask.setElearningCourse(elearningCourseDao.getCourseInfoByCourseId(Integer.valueOf(courseId)));
				teachTask.setStartDate(startTime);
				teachTask.setEndDate(endTime);
				teachTask.setRemark(remark);
				teachTask.setModifyer(userToken.getPersonId());
				teachTask.setModifyTime(new Date());
				elearningTeachTaskDao.update(teachTask);
			}else{
				ElearningTeachTask teachTask = new ElearningTeachTask();
				teachTask.setTaskName(teachTaskName);
				teachTask.setElearningCourse(elearningCourseDao.getCourseInfoByCourseId(Integer.valueOf(courseId)));
				teachTask.setStartDate(startTime);
				teachTask.setEndDate(endTime);
				teachTask.setRemark(remark);
				teachTask.setPersonId(userToken.getPersonId());
				teachTask.setModifyTime(new Date());
				elearningTeachTaskDao.save(teachTask);
				taskId=teachTask.getTaskId();
			}
			ElearningTaskCharge charge=elearningTaskChargeDao.getChargeByTaskId(taskId);
			if(charge!=null){
				charge.setPrice(Double.valueOf(price));
				elearningTaskChargeDao.update(charge);
			}else{
				charge=new ElearningTaskCharge();
				charge.setTaskId(taskId);
				charge.setPrice(Double.valueOf(price));
				elearningTaskChargeDao.save(charge);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getCourseSectionList", method = RequestMethod.POST)
	public Map getCourseSectionList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer taskId = (Integer) request.get("taskId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ElearningTeachTask task=elearningTeachTaskDao.find(taskId);
			List tree = new ArrayList();
			List sectionList = elearningCourseSectionDao
					.getSectionListByTaskId(taskId);
			if (sectionList != null && sectionList.size() > 0) {
				String last="0";
				for (int i = 0; i < sectionList.size(); i++) {
					ElearningCourseSection section = (ElearningCourseSection) sectionList
							.get(i);
					BaseCourseActionForm sectionForm=new BaseCourseActionForm();
					sectionForm.setTaskId(taskId);
					sectionForm.setCourseId(task.getElearningCourse().getCourseId());
					sectionForm.setSectionId(section.getSectionId());
					sectionForm.setSectionName(section.getSectionName());
					sectionForm.setOrderNum(section.getOrderNum());
					//首先判断是否已经匹配该资源
					ElearningSectionAcc video=elearningSectionAccDao.getMapBySectionIdAndAccId(section.getSectionId(), null,"VIDEO");
					if(video!=null){
						sectionForm.setVideoAcc(video.getAccId());
						sectionForm.setVideoId(video.getId());
					}
					ElearningSectionAcc audio=elearningSectionAccDao.getMapBySectionIdAndAccId(section.getSectionId(), null,"AUDIO");
					if(audio!=null){
						sectionForm.setAudioAcc(audio.getAccId());
						sectionForm.setAudioId(audio.getId());
					}
					ElearningSectionAcc ppt=elearningSectionAccDao.getMapBySectionIdAndAccId(section.getSectionId(), null,"PPT");
					if(ppt!=null){
						sectionForm.setPptAcc(ppt.getAccId());
						sectionForm.setPptId(ppt.getId());
					}
					ElearningSectionAcc practice=elearningSectionAccDao.getMapBySectionIdAndAccId(section.getSectionId(), null,"PRACTICE");
					if(practice!=null){
						sectionForm.setPracticeId(practice.getAccId());
						sectionForm.setId(practice.getId());
					}
					String isFinish="0";
					ElearningTaskFinish tf=elearningTaskFinishDao.getElearningTaskFinish(taskId, task.getElearningCourse().getCourseId(), section.getSectionId(), userToken.getPersonId(),null);
					if(tf!=null){
						isFinish=tf.getIsFinish();
					}
					sectionForm.setIsFinish(isFinish);
					if(section.getOrderNum()==1){
						sectionForm.setState("1");
					}else{
						if(last.equals("1")){
							sectionForm.setState("1");
						}else{
							sectionForm.setState("0");
						}
					}
					last=isFinish;
					tree.add(sectionForm);
				}
			} 
			data.put("sectionList", tree);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/addOrEditSection", method = RequestMethod.POST)
	public Map addOrEditSection(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer taskId = (Integer) request.get("taskId");
			Integer sectionId = (Integer) request.get("sectionId");
			String sectionName = (String) request.get("sectionName");
			if(sectionId!=null && !sectionId.equals("")){
				ElearningCourseSection section=elearningCourseSectionDao.find(sectionId);
				section.setSectionName(sectionName);
				section.setPersonId(userToken.getPersonId());
				String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				section.setCreateTime(createTime);
				elearningCourseSectionDao.update(section);
			}else{
				ElearningCourseSection section=new ElearningCourseSection();
				section.setTaskId(taskId);
				section.setSectionName(sectionName);
				section.setPersonId(userToken.getPersonId());
				String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				section.setCreateTime(createTime);
				Integer maxNum=elearningCourseSectionDao.getMaxOrderNum(taskId);
				section.setOrderNum(maxNum+1);
				elearningCourseSectionDao.save(section);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/adjustSectionOrder", method = RequestMethod.POST)
	public Map adjustSectionOrder(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer sectionId = (Integer) request.get("sectionId");
			String direction = (String) request.get("direction");
			ElearningCourseSection section=elearningCourseSectionDao.find(sectionId);
			Integer orderNum=section.getOrderNum();
			if(direction.equals("up")){
				if(orderNum!=1){
					ElearningCourseSection sec=elearningCourseSectionDao.getSectionByConditions(section.getTaskId(), orderNum-1);
					sec.setOrderNum(orderNum);
					elearningCourseSectionDao.update(sec);
					section.setOrderNum(orderNum-1);
					elearningCourseSectionDao.update(section);
				}
			}else{
				Integer maxNum=elearningCourseSectionDao.getMaxOrderNum(section.getTaskId());
				if(orderNum!=maxNum){
					ElearningCourseSection sec=elearningCourseSectionDao.getSectionByConditions(section.getTaskId(), orderNum+1);
					sec.setOrderNum(orderNum);
					elearningCourseSectionDao.update(sec);
					section.setOrderNum(orderNum+1);
					elearningCourseSectionDao.update(section);
				}
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/matchSectionAndResource", method = RequestMethod.POST)
	public Map matchSectionAndResource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer accId = (Integer) request.get("accId");
		String type = (String) request.get("type");
		Integer sectionId = (Integer) request.get("sectionId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			//首先判断是否已经匹配该资源
			ElearningSectionAcc map=elearningSectionAccDao.getMapBySectionIdAndAccId(sectionId, null,type);
			if(map!=null){
				map.setAccId(accId);
				elearningSectionAccDao.update(map);
			}else{
				ElearningSectionAcc match=new ElearningSectionAcc();
				match.setType(type);
				match.setAccId(accId);
				match.setSectionId(sectionId);
				elearningSectionAccDao.save(match);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/cancelMatchSectionAndResource", method = RequestMethod.POST)
	public Map cancelMatchSectionAndResource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer id = (Integer) request.get("id");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ElearningSectionAcc map=elearningSectionAccDao.getMapById(id);
			elearningSectionAccDao.delete(map);
			return CommonTool.getNodeMapOk("恭喜您，取消关联成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getPersonalCoursesList", method = RequestMethod.POST)
	public Map getPersonalCoursesList(HttpServletRequest request,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				request, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String taskName = (String) m.get("taskName");
			String courseType = (String) m.get("courseType");
			Integer personId = userToken.getPersonId();
			List courseList = elearningPlanCourseDao
					.getListByConditions(personId, taskName, courseType,null);
			if(courseList!=null){
				for (int i = 0; i < courseList.size(); i++) {
					BaseCourseActionForm courseForm = new BaseCourseActionForm();
					ElearningPlanCourse plan = (ElearningPlanCourse) courseList
							.get(i);
					ElearningTeachTask task=plan.getElearningTeachTask();
					courseForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd")).format(plan.getCreateTime()));
					courseForm.setCourseName(task.getElearningCourse()
							.getCourseName());
					courseForm.setCourseNum(task.getElearningCourse()
							.getCourseNum());
					courseForm.setCollegeName(baseCollegeDao
							.getCollegeNameById(task.getElearningCourse()
									.getCollegeId()));
					courseForm.setTeacherName("");
					courseForm.setCourseType(task.getElearningCourse().getCourseType());
					courseForm.setProcess("");
					courseForm.setCourseId(task.getElearningCourse()
							.getCourseId());
					courseForm.setTaskId(task.getTaskId());
					courseForm.setTaskName(task.getTaskName());
					courseForm.setStartDate(task.getStartDate());
					courseForm.setEndDate(task.getEndDate());
					ElearningCourse course=task.getElearningCourse();
					courseForm.setBriefIntroduction(course.getBriefIntroduction());
					if(course.getCoverImgAcc()!=null && !course.getCoverImgAcc().equals("")){
						courseForm.setCoverImgAcc(course.getCoverImgAcc());
					}else{
						courseForm.setCoverImgAcc(0);
					}
					courseForm.setCompleteDegree(plan.getCompleteDegree());
					courseForm.setTime(plan.getTime());
					Integer sectionNum=0;
					if(plan.getSectionIdNode()!=null){
						ElearningCourseSection section=elearningCourseSectionDao.find(plan.getSectionIdNode());
						sectionNum=section.getOrderNum();
					}
					courseForm.setSectionIdNode(sectionNum);
					List list=elearningExamInfoDao.getExamListByTaskId(plan.getElearningTeachTask().getTaskId());
					if(list!=null && list.size()>0){
						ElearningExamInfo exam=(ElearningExamInfo) list.get(0);
						ElearningExamScoreInfo score=elearningExamScoreInfoDao.getElearningExamScoreInfoByConditions(plan.getStuId(), exam.getExamId());
						if(score!=null){
							courseForm.setExamScore(score.getScore());
						}
					}
					List levelList=elearningTaskScoreDao.getScoreListByConditions(plan.getStuId(), plan.getElearningTeachTask().getTaskId());
					if(levelList!=null && levelList.size()>0){
						ElearningTaskScore level=(ElearningTaskScore) levelList.get(0);
						courseForm.setScoreLevel(level.getScoreLevel());
					}
					dataList.add(courseForm);
				}
		    }
			data.put("courseList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/course/getCourseCommentList", method = RequestMethod.POST)
	public Map getCourseCommentList(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList=new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer taskId=(Integer) m.get("taskId");
			List scoreList=elearningCourseCommentInfoDao.getElearningResourceScoreListByCondition(taskId,null);
			if(scoreList!=null){ 
				for(int j=0;j<scoreList.size();j++){
					BaseCourseActionForm scoreForm=new BaseCourseActionForm();
					ElearningCourseCommentInfo score=(ElearningCourseCommentInfo) scoreList.get(j);
					ElearningTeachTask task=elearningTeachTaskDao.find(score.getTaskId());
					ElearningCourse course=task.getElearningCourse();
					InfoPersonInfo person=infoPersonInfoDao.find(score.getPersonId());
					SysUser user=sysUserDao.getSysUsersByUserid(person.getPersonId());
					scoreForm.setLoginName(user.getLoginName());
					scoreForm.setPerName(person.getPerName());
					scoreForm.setPerNum(person.getPerNum());
					DecimalFormat df = new DecimalFormat("#.0");
					String str = df.format(score.getScore());  
					scoreForm.setCourseScoreStr(str);
					scoreForm.setComment(score.getComment());
					scoreForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(score.getCreateTime()));
					scoreForm.setCommentId(score.getId());
					dataList.add(scoreForm);
				}
			}
			data.put("commentList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/addCourseComment", method = RequestMethod.POST)
	public Map addCourseComment(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList=new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer taskId=(Integer) m.get("taskId");
			String score=(String) m.get("score");
			String comment=(String) m.get("comment");
			ElearningCourseCommentInfo commentInfo=new ElearningCourseCommentInfo();
			commentInfo.setTaskId(Integer.valueOf(taskId));
			commentInfo.setComment(comment);
			commentInfo.setScore(Integer.valueOf(score));
			commentInfo.setCreateTime(new Date());
			commentInfo.setPersonId(userToken.getPersonId());
			elearningCourseCommentInfoDao.save(commentInfo);
			return CommonTool.getNodeMapOk("恭喜您，提交成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/entryTeachTask", method = RequestMethod.POST)
	public Map entryTeachTask(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer taskId = (Integer) m.get("taskId");
			List entryList=elearningPlanCourseDao.getTaskListByConditions(userToken.getPersonId(), null, null, taskId);
			if(entryList!=null && entryList.size()>0){
				return CommonTool.getNodeMapError("抱歉，您已报名，请到我的课程中查看！");
			}
			ElearningPlanCourse entry=new ElearningPlanCourse();
			entry.setElearningTeachTask(elearningTeachTaskDao.find(taskId));
			entry.setStuId(userToken.getPersonId());
			entry.setCreateTime(new Date());
			entry.setCreator(userToken.getPersonId());
			entry.setState("1");
			entry.setTime(0);
			entry.setIsFinish("0");
			entry.setCompleteDegree(0.00);
			elearningPlanCourseDao.save(entry);
			InfoPersonInfo info=infoPersonInfoDao.find(userToken.getPersonId());
			String perTypeCode=info.getPerTypeCode();
			if(perTypeCode.equals("U")){
				ElearningTaskCharge charge=elearningTaskChargeDao.getChargeByTaskId(taskId);
				ElearningPayRecord pay=new ElearningPayRecord();
				pay.setPersonId(info.getPersonId());
				pay.setCreateTime(new Date());
				pay.setPayMode("UnionPay");
				pay.setPayNumber("1115646854658");
				pay.setState("1");
				pay.setChargeId(charge.getChargeId());
				pay.setAmount(charge.getPrice());
				pay.setPlanId(entry.getId());
				elearningPayRecordDao.save(pay);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}
	
	@RequestMapping(value = "/course/getCourseResourceList", method = RequestMethod.POST)
	public Map getCourseResourceList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer taskId = (Integer) request.get("taskId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ElearningTeachTask task=elearningTeachTaskDao.find(taskId);
			List tree = new ArrayList();
			List folderList = accessoriesCourseFolderDao.getFolferListByTaskId(taskId);
			if (folderList != null && folderList.size() > 0) {
				for (int i = 0; i < folderList.size(); i++) {
					AccessoriesCourseFolder folder = (AccessoriesCourseFolder) folderList
							.get(i);
					BaseCourseActionForm sectionForm=new BaseCourseActionForm();
					sectionForm.setFolderId(folder.getId());
					sectionForm.setFolderName(folder.getFolderName());
					//首先判断是否已经匹配该资源
					AccessoriesFolderAcc acc=accessoriesFolderAccDao.getFolferAccByCondition(folder.getId(), null, null);
					if(acc!=null){
						sectionForm.setAccId(acc.getAccId());
						sectionForm.setId(acc.getId());
					}
					tree.add(sectionForm);
				}
			} 
			data.put("courseResourceList", tree);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/addOrEditCourseResource", method = RequestMethod.POST)
	public Map addOrEditCourseResource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer taskId = (Integer) request.get("taskId");
			Integer folderId = (Integer) request.get("folderId");
			String folderName = (String) request.get("folderName");
			if(folderId!=null && !folderId.equals("")){
				AccessoriesCourseFolder fc=accessoriesCourseFolderDao.find(folderId);
				fc.setFolderName(folderName);
				fc.setPersonId(userToken.getPersonId());
				fc.setCreateTime(new Date());
				accessoriesCourseFolderDao.update(fc);
			}else{
				AccessoriesCourseFolder fc=new AccessoriesCourseFolder();
				fc.setTaskId(taskId);
				fc.setFolderName(folderName);
				fc.setPersonId(userToken.getPersonId());
				fc.setCreateTime(new Date());
				ElearningTeachTask task=elearningTeachTaskDao.find(taskId);
				fc.setCourseId(task.getElearningCourse().getCourseId());
				accessoriesCourseFolderDao.save(fc);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/matchFolderAndResource", method = RequestMethod.POST)
	public Map matchFolderAndResource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer accId = (Integer) request.get("accId");
		Integer folderId = (Integer) request.get("folderId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			//首先判断是否已经匹配该资源
			AccessoriesFolderAcc map=accessoriesFolderAccDao.getFolferAccByCondition(folderId, accId, null);
			if(map!=null){
				map.setAccId(accId);
				accessoriesFolderAccDao.update(map);
			}else{
				AccessoriesFolderAcc match=new AccessoriesFolderAcc();
				match.setAccId(accId);
				match.setFolderId(folderId);
				accessoriesFolderAccDao.save(match);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/cancelMatchFolderAndResource", method = RequestMethod.POST)
	public Map cancelMatchFolderAndResource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer id = (Integer) request.get("id");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			AccessoriesFolderAcc map=accessoriesFolderAccDao.find(id);
			accessoriesFolderAccDao.delete(map);
			return CommonTool.getNodeMapOk("恭喜您，取消关联成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getCoursePersonList", method = RequestMethod.POST)
	public Map getCoursePersonList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer taskId = (Integer) request.get("taskId");
			String loginName = (String) request.get("loginName");
			String perName = (String) request.get("perName");
			String state = (String) request.get("state");
			List rList=elearningPlanCourseDao.getPlanListByConditions(loginName, perName, taskId,state);
			List dataList=new ArrayList();
			if(rList!=null){
				for(int i=0;i<rList.size();i++){
					ElearningPlanCourse plan=(ElearningPlanCourse) rList.get(i);
					BaseCourseActionForm planForm=new BaseCourseActionForm();
					SysUser user=sysUserDao.getSysUsersByUserid(plan.getStuId());
					InfoPersonInfo info=infoPersonInfoDao.find(plan.getStuId());
					planForm.setPerTypeCode(info.getPerTypeCode());
					planForm.setLoginName(user.getLoginName());
					planForm.setPerName(info.getPerName());
					planForm.setMobilePhone(info.getMobilePhone());
					planForm.setId(plan.getId());
					planForm.setState(plan.getState());
					ElearningPayRecord record=elearningPayRecordDao.getRecordByConditions(plan.getStuId(), "1", plan.getId());
					if(record!=null){
						planForm.setPrice(record.getAmount());
					}else{
						planForm.setPrice(0.0);
					}
					dataList.add(planForm);
				}
			}
			data.put("personList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getNotMapPersonList", method = RequestMethod.POST)
	public Map getNotMapPersonList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer taskId = (Integer) request.get("taskId");
			String loginName = (String) request.get("loginName");
			String perName = (String) request.get("perName");
			List rList=elearningPlanCourseDao.getPlanListByTaskId(taskId);
			Integer size=0;
			if(rList!=null){
				size=rList.size();
			}
			Integer [] mapArr=new Integer[size];
			if(rList!=null){
				for(int i=0;i<rList.size();i++){
					ElearningPlanCourse plan=(ElearningPlanCourse) rList.get(i);
					mapArr[i]=plan.getStuId();
					
				}
			}
			List list=new ArrayList();
			List list1=infoPersonInfoDao.getListbyConditions(loginName, perName, null, 3);
			List list2=infoPersonInfoDao.getListbyConditions(loginName, perName, null, 4);
			if(list1!=null){
				list.addAll(list1);
			}
			if(list2!=null){
				list.addAll(list2);
			}
			List dataList=new ArrayList();
			if(list!=null){
				for(int i=0;i<list.size();i++){
					InfoPersonInfo info=(InfoPersonInfo) list.get(i);
					Integer personId=info.getPersonId();
					if (Arrays.asList(mapArr).contains(personId)) {
						continue;
					}
					BaseCourseActionForm planForm=new BaseCourseActionForm();
					SysUser user=sysUserDao.getSysUsersByUserid(info.getPersonId());
					planForm.setPerTypeCode(info.getPerTypeCode());
					planForm.setLoginName(user.getLoginName());
					planForm.setPerName(info.getPerName());
					planForm.setMobilePhone(info.getMobilePhone());
					planForm.setPersonId(info.getPersonId());
					dataList.add(planForm);
				}
			}
			data.put("personList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/addToTask", method = RequestMethod.POST)
	public Map addToTask(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer personId = (Integer) request.get("personId");
		Integer taskId = (Integer) request.get("taskId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ElearningPlanCourse plan=new ElearningPlanCourse();
			plan.setStuId(personId);
			plan.setElearningTeachTask(elearningTeachTaskDao.find(taskId));
			plan.setCreateTime(new Date());
			plan.setCreator(userToken.getPersonId());
			plan.setState("2");
			plan.setTime(0);
			plan.setIsFinish("0");
			plan.setCompleteDegree(0.00);
			elearningPlanCourseDao.save(plan);
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/deletePeopleFromPlan", method = RequestMethod.POST)
	public Map deletePeopleFromPlan(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer id = (Integer) request.get("id");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			elearningPlanCourseDao.deleteById(id);
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getAllTaskNewsInfo", method = RequestMethod.POST)
	public Map getAllTaskNewsInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer taskId=(Integer) m.get("taskId");
			List newsList=elearningTaskNewsDao.getNewsListByTaskId(taskId);
			if(newsList!=null){
				for(int i=0;i<newsList.size();i++){
					ElearningTaskNews news=(ElearningTaskNews) newsList.get(i);
					BaseCourseActionForm newsForm=new BaseCourseActionForm();
					newsForm.setNewsId(news.getNewsId());
					newsForm.setContent(news.getContent());
					newsForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(news.getCreateTime()));
					newsForm.setTitle(news.getTitle());
					dataList.add(newsForm);
				}
			}
			data.put("newsList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/deleteTaskNews", method = RequestMethod.POST)
	public Map deleteTaskNews(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer newsId=(Integer) m.get("newsId");
			elearningTaskNewsDao.deleteById(newsId);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/addOrEditTaskNews", method = RequestMethod.POST)
	public Map addOrEditTaskNews(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer newsId=(Integer) m.get("newsId");
			Integer taskId=(Integer) m.get("taskId");
			String title=(String) m.get("title");
			String content=(String) m.get("content");
			if(newsId!=null){
				ElearningTaskNews news=elearningTaskNewsDao.find(newsId);
				news.setTitle(title);
				news.setContent(content);
				news.setTaskId(taskId);
				news.setPersonId(userToken.getPersonId());
				news.setCreateTime(new Date());
				elearningTaskNewsDao.update(news);
			}else{
				ElearningTaskNews news=new ElearningTaskNews();
				news.setTitle(title);
				news.setContent(content);
				news.setTaskId(taskId);
				news.setPersonId(userToken.getPersonId());
				news.setCreateTime(new Date());
				elearningTaskNewsDao.save(news);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	
	@RequestMapping(value = "/course/deleteComment", method = RequestMethod.POST)
	public Map deleteComment(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList=new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer id=(Integer) m.get("id");
			ElearningCourseCommentInfo comment=elearningCourseCommentInfoDao.find(id);
			if(comment!=null){ 
				elearningCourseCommentInfoDao.delete(comment);
			}
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getCourseInterlocutionList", method = RequestMethod.POST)
	public Map getCourseInterlocutionList(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		Map data=new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList=new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer taskId=(Integer) m.get("taskId");
			String state=(String) m.get("state");
			ElearningTeachTask task=elearningTeachTaskDao.find(taskId);
			List interlocutionList=elearningInterlocutionInfoDao.getInterlocutionListByConditions(taskId, state);
			if(interlocutionList!=null){ 
				for(int j=0;j<interlocutionList.size();j++){
					BaseCourseActionForm scoreForm=new BaseCourseActionForm();
					ElearningInterlocutionInfo interlocution=(ElearningInterlocutionInfo) interlocutionList.get(j);
					InfoPersonInfo person=infoPersonInfoDao.find(interlocution.getPersonId());
					scoreForm.setPerName(person.getPerName());
					SysUser user=sysUserDao.getSysUsersByUserid(person.getPersonId());
					scoreForm.setLoginName(user.getLoginName());
					scoreForm.setTaskName(task.getTaskName());
					scoreForm.setCourseName(task.getElearningCourse().getCourseName());
					scoreForm.setTitle(interlocution.getTitle());
					scoreForm.setQuestionId(interlocution.getQuestionId());
					scoreForm.setState(interlocution.getState());
					scoreForm.setQuestion(interlocution.getQuestion());
					scoreForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(interlocution.getCreateTime()));
					scoreForm.setAnswer(interlocution.getAnswer());
					if(interlocution.getAnswerTime()!=null && !interlocution.getAnswerTime().equals("")){
						scoreForm.setStartDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(interlocution.getAnswerTime()));
					}
					dataList.add(scoreForm);
				}
			}
			data.put("questionList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/submitInterlocutionReplay", method = RequestMethod.POST)
	public Map submitInterlocutionReplay(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer questionId=(Integer) m.get("questionId");
			String answer=(String) m.get("answer");
			ElearningInterlocutionInfo interlocution=elearningInterlocutionInfoDao.find(questionId);
			interlocution.setAnswer(answer);
			interlocution.setAnswerTime(new Date());
			interlocution.setTeacherId(userToken.getPersonId());
			elearningInterlocutionInfoDao.update(interlocution);
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/deleteInterlocution", method = RequestMethod.POST)
	public Map deleteInterlocution(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer questionId=(Integer) m.get("questionId");
			elearningInterlocutionInfoDao.deleteById(questionId);
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getSectionResourceInfo", method = RequestMethod.POST)
	public Map getSectionResourceInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer taskId=(Integer) m.get("taskId");
			Integer sectionId=(Integer) m.get("sectionId");
			List folderList=accessoriesCourseFolderDao.getFolferListByTaskId(taskId);
			List dataList=new ArrayList();
			if(folderList!=null){
				for(int i=0;i<folderList.size();i++){
					BaseCourseActionForm form=new BaseCourseActionForm();
					AccessoriesCourseFolder folder=(AccessoriesCourseFolder) folderList.get(i);
					form.setFolderId(folder.getId());
					form.setFolderName(folder.getFolderName());
					AccessoriesFolderAcc acc=accessoriesFolderAccDao.getFolferAccByConditions(folder.getId(), null);
					if(acc!=null){
						form.setAccId(acc.getAccId());
						dataList.add(form);
					}
				}
			};
			data.put("folderList", dataList);
			if(sectionId!=null){
				ElearningSectionAcc sa=elearningSectionAccDao.getMapBySectionIdAndAccId(sectionId, null, "VIDEO");
				if(sa!=null){
					data.put("videoAcc", sa.getAccId());
				}
			}
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/submitInterlocutionQuestion", method = RequestMethod.POST)
	public Map submitInterlocutionQuestion(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer taskId=(Integer) m.get("taskId");
			String title=(String) m.get("title");
			String question=(String) m.get("question");
			ElearningInterlocutionInfo interlocution=new ElearningInterlocutionInfo();
			interlocution.setTitle(title);
			interlocution.setQuestion(question);
			interlocution.setPersonId(userToken.getPersonId());
			interlocution.setCreateTime(new Date());
			interlocution.setTaskId(taskId);
			interlocution.setState("0");
			elearningInterlocutionInfoDao.save(interlocution);
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/matchSectionAndPractice", method = RequestMethod.POST)
	public Map matchSectionAndPractice(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer practiceId = (Integer) request.get("practiceId");
		String type = (String) request.get("type");
		Integer sectionId = (Integer) request.get("sectionId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			//首先判断是否已经匹配该资源
			ElearningSectionAcc map=elearningSectionAccDao.getMapBySectionIdAndAccId(sectionId, null,type);
			if(map!=null){
				map.setAccId(practiceId);
				elearningSectionAccDao.update(map);
			}else{
				ElearningSectionAcc match=new ElearningSectionAcc();
				match.setType(type);
				match.setAccId(practiceId);
				match.setSectionId(sectionId);
				elearningSectionAccDao.save(match);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/videoPlayRecord", method = RequestMethod.POST)
	public Map videoPlayRecord(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer taskId = (Integer) request.get("taskId");
		Integer courseId = (Integer) request.get("courseId");
		Integer sectionId = (Integer) request.get("sectionId");
		Map data = new HashMap();
		String practiceFinish="0";
		if (userToken != null) {// 登录信息不为空
			ElearningTaskFinish tf=elearningTaskFinishDao.getElearningTaskFinish(taskId, courseId, sectionId, userToken.getPersonId(),null);
			if(tf!=null){
				practiceFinish=tf.getPracticeFinish();
				Integer time=tf.getTime();
				time+=1;
				tf.setTime(time);
				elearningTaskFinishDao.update(tf);
			}else{
				tf=new ElearningTaskFinish();
				tf.setStuId(userToken.getPersonId());
				tf.setTaskId(taskId);
				tf.setIsFinish("0");
				tf.setCourseId(courseId);
				tf.setSectionId(sectionId);
				tf.setTime(1);
				tf.setPracticeFinish("0");
				elearningTaskFinishDao.save(tf);
			}
			Integer sectionSize=1;
			List sectionList=elearningCourseSectionDao.getSectionListByTaskId(taskId);
			if(sectionList!=null){
				sectionSize=sectionList.size();
			}
			Integer finishSize=0;
			List finishList=elearningTaskFinishDao.getListByConditions(taskId, courseId, null, userToken.getPersonId(), "1");
			if(finishList!=null){
				finishSize=finishList.size();
			}
			List planList=elearningPlanCourseDao.getTaskListByConditions(userToken.getPersonId(), null, null, taskId);
			ElearningPlanCourse plan=(ElearningPlanCourse) planList.get(0);
			plan.setCompleteDegree((double) finishSize/sectionSize);
			Integer ti=plan.getTime();
			plan.setTime(++ti);
			plan.setSectionIdNode(sectionId);
			if(finishSize==sectionSize){
				plan.setIsFinish("1");
			}
			elearningPlanCourseDao.update(plan);
			data.put("practiceFinish", practiceFinish);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getNextSection", method = RequestMethod.POST)
	public Map getNextSection(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer taskId = (Integer) request.get("taskId");
		Integer courseId = (Integer) request.get("courseId");
		Integer sectionId = (Integer) request.get("sectionId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ElearningCourseSection section=elearningCourseSectionDao.find(sectionId);
			Integer max=elearningCourseSectionDao.getMaxOrderNum(taskId);
			Integer nextNum=section.getOrderNum()%max+1;
			ElearningCourseSection next=elearningCourseSectionDao.getSectionByConditions(taskId, nextNum);
			data.put("taskId",taskId);
			data.put("courseId", courseId);
			data.put("sectionId", next.getSectionId());
			data.put("sectionName", next.getSectionName());
			ElearningSectionAcc practice=elearningSectionAccDao.getMapBySectionIdAndAccId(section.getSectionId(), null,"PRACTICE");
			if(practice!=null){
				data.put("practiceId", practice.getAccId());
				data.put("id", practice.getId());
			}
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/course/getTaskInfo", method = RequestMethod.POST)
	public Map getTaskInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer taskId=(Integer) m.get("taskId");
			List newsList=elearningTaskNewsDao.getNewsListByTaskId(taskId);
			if(newsList!=null){
				ElearningTaskNews news=(ElearningTaskNews) newsList.get(0);
				BaseCourseActionForm newsForm=new BaseCourseActionForm();
				newsForm.setNewsId(news.getNewsId());
				newsForm.setContent(news.getContent());
				newsForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(news.getCreateTime()));
				newsForm.setTitle(news.getTitle());
				data.put("news", newsForm);
			}
			ElearningTeachTask task=elearningTeachTaskDao.find(taskId);
			data.put("taskName", task.getTaskName());
			data.put("briefIntroduction", task.getElearningCourse().getBriefIntroduction());
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	
	
}
