package cn.edu.sdu.lecture.bean.web;

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
import cn.edu.sdu.exam.dao.ElearningPracticeInfoDao;
import cn.edu.sdu.exam.model.ElearningPracticeInfo;
import cn.edu.sdu.lecture.dao.ElearningLectureAttendanceDao;
import cn.edu.sdu.lecture.dao.ElearningLectureDao;
import cn.edu.sdu.lecture.dao.ElearningLectureEntryDao;
import cn.edu.sdu.lecture.dao.ElearningLectureLiveDao;
import cn.edu.sdu.lecture.form.BaseLectureActionForm;
import cn.edu.sdu.lecture.model.ElearningLecture;
import cn.edu.sdu.lecture.model.ElearningLectureAttendance;
import cn.edu.sdu.lecture.model.ElearningLectureEntry;
import cn.edu.sdu.lecture.model.ElearningLectureLive;

@RestController
public class BaseLectureActionInterface {
	
	@Autowired
	private InfoPersonInfoDao infoPersonInfoDao;
	@Autowired
	private SysUserDao sysUserDao;
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
	private ElearningLectureDao elearningLectureDao;
	@Autowired
	private ElearningLectureEntryDao elearningLectureEntryDao;
	@Autowired
	private ElearningLectureAttendanceDao elearningLectureAttendanceDao;
	@Autowired
	private ElearningLectureLiveDao elearningLectureLiveDao;
	
	@RequestMapping(value = "/lecture/getLectureList", method = RequestMethod.POST)
	public Map getLectureList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String theme = (String) m.get("theme");
			String lectureType = (String) m.get("lectureType");
			List courseList = elearningLectureDao
					.getLectureListByConditions(theme, lectureType);
			List dataList = new ArrayList();
			if(courseList!=null){
				for (int i = 0; i < courseList.size(); i++) {
					BaseLectureActionForm lectureForm = new BaseLectureActionForm();
				    ElearningLecture lecture = (ElearningLecture) courseList.get(i);
				    lectureForm.setLectureId(lecture.getLectureId());
				    lectureForm.setTheme(lecture.getTheme());
				    String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lecture.getCreateTime());
				    lectureForm.setCreateTimeStr(createTime);
					InfoPersonInfo person=infoPersonInfoDao.find(lecture.getCreator());
				    lectureForm.setPerName(person.getPerName());
				    lectureForm.setStartTimeStr(lecture.getStartTime());
				    lectureForm.setEndTimeStr(lecture.getEndTime());
				    lectureForm.setPlace(lecture.getPlace());
				    lectureForm.setLectureType(lecture.getLectureType());
				    lectureForm.setMaxNumber(lecture.getMaxNumber());
				    lectureForm.setSpeaker(lecture.getSpeaker());
				    lectureForm.setCollegeName(baseCollegeDao
							.getCollegeNameById(lecture.getCollegeId()));
				    lectureForm.setRegistStart(lecture.getRegistStart());
				    lectureForm.setRegistEnd(lecture.getRegistEnd());
				    lectureForm.setCoverImgAcc(lecture.getCoverImgAcc());
				    lectureForm.setIntroduce(lecture.getIntroduce());
				    //报名信息
					List applyList=new ArrayList();
					List checkedList=new ArrayList();
					List auditedList=new ArrayList();
					List entriedList = elearningLectureEntryDao
							.getEntryListByConditions(lecture.getLectureId(), null);
					if(entriedList!=null){
						for(int j=0;j<entriedList.size();j++){
							ElearningLectureEntry entry=(ElearningLectureEntry) entriedList.get(j);
							BaseLectureActionForm entryInfo = new BaseLectureActionForm();
							InfoPersonInfo info=infoPersonInfoDao.find(entry.getPersonId());
							SysUser user=sysUserDao.getSysUsersByUserid(info.getPersonId());
							entryInfo.setPerNum(user.getLoginName());
							entryInfo.setPerName(info.getPerName());
							entryInfo.setMobilePhone(info.getMobilePhone());
						    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entry.getCreateTime());
							entryInfo.setCreateTimeStr(time);
							entryInfo.setEntryId(entry.getEntryId());
							entryInfo.setState(entry.getState());
							ElearningLectureAttendance attendance=elearningLectureAttendanceDao.getAttendanceByConditions(entry.getLectureId(), entry.getEntryId());
							if(attendance!=null){
								entryInfo.setAttendance(attendance.getAttendance());
							}
							String state=entry.getState();
							if(state.equals("1")){
								checkedList.add(entryInfo);
							}
							if(!state.equals("3")){
								applyList.add(entryInfo);
							}
							if(state.equals("0")){
								auditedList.add(entryInfo);
							}
						}
					}
					lectureForm.setApplyList(applyList);
					lectureForm.setCheckedList(checkedList);
					lectureForm.setAuditedList(auditedList);
					dataList.add(lectureForm);
				}
			}
			data.put("lectureList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/lecture/getLectureInfo", method = RequestMethod.POST)
	public Map getLectureInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer lectureId = (Integer) m.get("lectureId");
			BaseLectureActionForm lectureForm = new BaseLectureActionForm();
			ElearningLecture lecture = elearningLectureDao.find(lectureId);
		    lectureForm.setLectureId(lecture.getLectureId());
		    lectureForm.setTheme(lecture.getTheme());
		    String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lecture.getCreateTime());
		    lectureForm.setCreateTimeStr(createTime);
			InfoPersonInfo person=infoPersonInfoDao.find(lecture.getCreator());
		    lectureForm.setPerName(person.getPerName());
		    lectureForm.setStartTimeStr(lecture.getStartTime());
		    lectureForm.setEndTimeStr(lecture.getEndTime());
		    lectureForm.setRegistStart(lecture.getRegistStart());
		    lectureForm.setRegistEnd(lecture.getRegistEnd());
		    lectureForm.setPlace(lecture.getPlace());
		    lectureForm.setLectureType(lecture.getLectureType());
		    lectureForm.setMaxNumber(lecture.getMaxNumber());
		    lectureForm.setSpeaker(lecture.getSpeaker());
		    lectureForm.setCollegeId(lecture.getCollegeId());
		    lectureForm.setIntroduce(lecture.getIntroduce());
		    lectureForm.setCoverImgAcc(lecture.getCoverImgAcc());
			data.put("lectureInfo", lectureForm);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/lecture/addOrEditLectureInfo", method = RequestMethod.POST)
	public Map addOrEditCourseInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer lectureId = (Integer) m.get("lectureId");
			String theme = (String) m.get("theme");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String place = (String) m.get("place");
			String lectureType = (String) m.get("lectureType");
			Integer maxNumber = (Integer) m.get("maxNumber");
			String registStart = (String) m.get("registStart");
			String registEnd = (String) m.get("registEnd");
			String speaker = (String) m.get("speaker");
			Integer collegeId = (Integer) m.get("collegeId");
			String introduce = (String) m.get("introduce");
			if(lectureId!=null && !lectureId.equals("")){
				ElearningLecture lecture = elearningLectureDao.find(lectureId);
				lecture.setTheme(theme);
				lecture.setStartTime(startTime);
				lecture.setEndTime(endTime);
				lecture.setPlace(place);
				lecture.setLectureType(lectureType);
				lecture.setMaxNumber(maxNumber);
				lecture.setRegistStart(registStart);
				lecture.setRegistEnd(registEnd);
				lecture.setSpeaker(speaker);
				lecture.setCollegeId(collegeId);
				lecture.setIntroduce(introduce);
				lecture.setModifyTime(new Date());
				lecture.setModifier(userToken.getPersonId());
				elearningLectureDao.update(lecture);
				return CommonTool.getNodeMapOk("恭喜您，操作成功！");
			}else{
				ElearningLecture lecture=new ElearningLecture();
				lecture.setTheme(theme);
				lecture.setStartTime(startTime);
				lecture.setEndTime(endTime);
				lecture.setPlace(place);
				lecture.setLectureType(lectureType);
				lecture.setMaxNumber(maxNumber);
				lecture.setRegistStart(registStart);
				lecture.setRegistEnd(registEnd);
				lecture.setSpeaker(speaker);
				lecture.setCollegeId(collegeId);
				lecture.setIntroduce(introduce);
				lecture.setCreateTime(new Date());
				lecture.setCreator(userToken.getPersonId());
				elearningLectureDao.save(lecture);
				return CommonTool.getNodeMap(lecture, null);
			}
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/lecture/uploadCoverImg", method = RequestMethod.POST)
	public Map uploadCoverImg(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request,HttpSession session) throws IllegalStateException,
			IOException {
		//获得id
		String personId=request.getParameter("personId");
		String lectureIdStr=request.getParameter("lectureId");
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
		ElearningLecture lecture = elearningLectureDao.find(Integer.valueOf(lectureIdStr));
		if(lecture!=null && lecture.getCoverImgAcc()!=null){
			BaseAttachmentInfo old=baseAttachmentInfoDao.find(lecture.getCoverImgAcc());
			if(old!=null){
				FileUtility.deleteFile(old.getUrlAddress());
				baseAttachmentInfoDao.delete(old);
			}
		}
		lecture.setCoverImgAcc(attach.getAttachId());
		elearningLectureDao.update(lecture);
		return CommonTool.getNodeMapOk("恭喜您，上传成功！");
	}
	
	@RequestMapping(value = "/lecture/deleteLecture", method = RequestMethod.POST)
	public Map deleteCourse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer lectureId = (Integer) m.get("lectureId");
			ElearningLecture lecture = elearningLectureDao.find(lectureId);
			if(lecture!=null && lecture.getCoverImgAcc()!=null){
				BaseAttachmentInfo old=baseAttachmentInfoDao.find(lecture.getCoverImgAcc());
				if(old!=null){
					FileUtility.deleteFile(old.getUrlAddress());
					baseAttachmentInfoDao.delete(old);
				}
			}
			elearningLectureDao.deleteById(lectureId);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}
	
	@RequestMapping(value = "/lecture/entryLecture", method = RequestMethod.POST)
	public Map entryLecture(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer lectureId = (Integer) m.get("lectureId");
			List entryList=elearningLectureEntryDao.getEntryListByConditions(lectureId, userToken.getPersonId());
			if(entryList!=null && entryList.size()>0){
				return CommonTool.getNodeMapError("抱歉，您已报名，请到已报名讲座研讨中查看！");
			}
			ElearningLectureEntry  entry=new ElearningLectureEntry();
			entry.setLectureId(lectureId);
			entry.setPersonId(userToken.getPersonId());
			entry.setCreateTime(new Date());
			entry.setState("0");
			elearningLectureEntryDao.save(entry);
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}
	
	@RequestMapping(value = "/lecture/getEntryList", method = RequestMethod.POST)
	public Map getEntryList(HttpServletRequest httpRequest,
			@RequestBody Object obj) throws ParseException {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String theme = (String) m.get("theme");
			String lectureType = (String) m.get("lectureType");
			String state = (String) m.get("state");
			List entriedList = elearningLectureEntryDao
					.getEntriedListByConditions(theme, lectureType,state,userToken.getPersonId());
			List dataList = new ArrayList();
			if(entriedList!=null){
				for (int i = 0; i < entriedList.size(); i++) {
					BaseLectureActionForm lectureForm = new BaseLectureActionForm();
					ElearningLectureEntry entry = (ElearningLectureEntry) entriedList.get(i);
					lectureForm.setEntryId(entry.getEntryId());
				    lectureForm.setLectureId(entry.getLectureId());
				    lectureForm.setState(entry.getState());
				    ElearningLecture lecture = elearningLectureDao.find(entry.getLectureId());
				    lectureForm.setTheme(lecture.getTheme());
				    String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lecture.getCreateTime());
				    lectureForm.setCreateTimeStr(createTime);
					InfoPersonInfo person=infoPersonInfoDao.find(lecture.getCreator());
				    lectureForm.setPerName(person.getPerName());
				    lectureForm.setStartTimeStr(lecture.getStartTime());
				    lectureForm.setEndTimeStr(lecture.getEndTime());
				    lectureForm.setPlace(lecture.getPlace());
				    lectureForm.setLectureType(lecture.getLectureType());
				    lectureForm.setMaxNumber(lecture.getMaxNumber());
				    lectureForm.setSpeaker(lecture.getSpeaker());
				    lectureForm.setCollegeName(baseCollegeDao
							.getCollegeNameById(lecture.getCollegeId()));
				    lectureForm.setRegistStart(lecture.getRegistStart());
				    lectureForm.setRegistEnd(lecture.getRegistEnd());
				    lectureForm.setCoverImgAcc(lecture.getCoverImgAcc());
				    lectureForm.setIntroduce(lecture.getIntroduce());
				    List attendanceList=elearningLectureAttendanceDao.getAttendanceListByConditions(lecture.getLectureId(), userToken.getPersonId(), null, null);
					if(attendanceList!=null && attendanceList.size()>0){
						ElearningLectureAttendance attend=(ElearningLectureAttendance) attendanceList.get(0);
					    lectureForm.setAttendance(attend.getAttendance());
					}else{
					    lectureForm.setAttendance(null);
					}
					//判断是否在直播时间内
					Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lecture.getStartTime());
					Date endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lecture.getEndTime());
					Date currentTime=new Date();
					if(currentTime.getTime()>startTime.getTime() && currentTime.getTime()<endTime.getTime()){
						lectureForm.setValue("1");
					}
					dataList.add(lectureForm);
				}
			}
			data.put("entryList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/lecture/cancelEntry", method = RequestMethod.POST)
	public Map cancelEntry(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer entryId = (Integer) m.get("entryId");
			String cancelReason = (String) m.get("cancelReason");
			ElearningLectureEntry entry =elearningLectureEntryDao.find(entryId);
			entry.setState("3");
			entry.setCancelReason(cancelReason);
			elearningLectureEntryDao.update(entry);
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}
	
	@RequestMapping(value = "/lecture/getCheckList", method = RequestMethod.POST)
	public Map getCheckList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String theme = (String) m.get("theme");
			String lectureType = (String) m.get("lectureType");
			String state = (String) m.get("state");
			List entriedList = elearningLectureEntryDao
					.getEntriedListByConditions(theme, lectureType,state,userToken.getPersonId());
			List dataList = new ArrayList();
			if(entriedList!=null){
				for (int i = 0; i < entriedList.size(); i++) {
					BaseLectureActionForm lectureForm = new BaseLectureActionForm();
					ElearningLectureEntry entry = (ElearningLectureEntry) entriedList.get(i);
					lectureForm.setEntryId(entry.getEntryId());
				    lectureForm.setLectureId(entry.getLectureId());
				    lectureForm.setState(entry.getState());
				    ElearningLecture lecture = elearningLectureDao.find(entry.getLectureId());
				    lectureForm.setTheme(lecture.getTheme());
				    String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lecture.getCreateTime());
				    lectureForm.setCreateTimeStr(createTime);
					InfoPersonInfo person=infoPersonInfoDao.find(lecture.getCreator());
				    lectureForm.setPerName(person.getPerName());
				    lectureForm.setStartTimeStr(lecture.getStartTime());
				    lectureForm.setEndTimeStr(lecture.getEndTime());
				    lectureForm.setPlace(lecture.getPlace());
				    lectureForm.setLectureType(lecture.getLectureType());
				    lectureForm.setMaxNumber(lecture.getMaxNumber());
				    lectureForm.setSpeaker(lecture.getSpeaker());
				    lectureForm.setCollegeName(baseCollegeDao
							.getCollegeNameById(lecture.getCollegeId()));
				    lectureForm.setRegistStart(lecture.getRegistStart());
				    lectureForm.setRegistEnd(lecture.getRegistEnd());
				    lectureForm.setCoverImgAcc(lecture.getCoverImgAcc());
				    lectureForm.setIntroduce(lecture.getIntroduce());
					dataList.add(lectureForm);
				}
			}
			data.put("entryList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/lecture/applyCheck", method = RequestMethod.POST)
	public Map applyCheck(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer entryId = (Integer) m.get("entryId");
			String state = (String) m.get("state");
			String refuseReason = (String) m.get("refuseReason");
			ElearningLectureEntry entry =elearningLectureEntryDao.find(entryId);
			entry.setState(state);
			entry.setRefuseReason(refuseReason);
			elearningLectureEntryDao.update(entry);
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}
	
	@RequestMapping(value = "/lecture/subAttendance", method = RequestMethod.POST)
	public Map subAttendance(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			List entryList = (List) m.get("entryList");
			Integer lectureId = (Integer) m.get("lectureId");
			List entriedList = elearningLectureEntryDao
					.getEntryListByConditions(lectureId, null);
			if(entriedList!=null){
				for(int i=0;i<entriedList.size();i++){
					ElearningLectureEntry entry =(ElearningLectureEntry) entriedList.get(i);
					ElearningLectureAttendance atten=new ElearningLectureAttendance();
					atten.setLectureId(entry.getLectureId());
					atten.setStuId(entry.getPersonId());
					atten.setEntryId(entry.getEntryId());
					atten.setCreateTime(new Date());
					atten.setCreator(userToken.getPersonId());
					atten.setAttendance("0");
					elearningLectureAttendanceDao.save(atten);
				}
			}
			if(entryList!=null){
				for(int i=0;i<entryList.size();i++){
					Map entry =(Map) entryList.get(i);
					ElearningLectureAttendance atten=new ElearningLectureAttendance();
					Integer entryId=(Integer) entry.get("entryId");
					ElearningLectureAttendance attendance=elearningLectureAttendanceDao.getAttendanceByConditions(atten.getLectureId(), entryId);
					attendance.setAttendance("1");
					elearningLectureAttendanceDao.update(attendance);
				}
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}
	
	@RequestMapping(value = "/lecture/hasAttendance", method = RequestMethod.POST)
	public Map hasAttendance(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer lectureId = (Integer) m.get("lectureId");
			List attendanceList=elearningLectureAttendanceDao.getAttendanceListByConditions(lectureId, null, null, null);
			if(attendanceList!=null && attendanceList.size()>0){
				data.put("showAttend", false);
			}else{
				data.put("showAttend", true);
			}
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/lecture/getLectureLiveInfo", method = RequestMethod.POST)
	public Map getLectureLiveInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer lectureId = (Integer) m.get("lectureId");
			//直播信息
			ElearningLectureLive live=elearningLectureLiveDao.getLiveByConditions(lectureId);
			if(live!=null){
				data.put("startTime",live.getStartTime());
				data.put("endTime",live.getEndTime());
				data.put("lectureId",lectureId);
				data.put("pushUrl",live.getPushUrl());
				data.put("outputUrl",live.getOutputUrl());
			}else{
				data.put("startTime","");
				data.put("endTime","");
				data.put("lectureId","");
				data.put("pushUrl","");
				data.put("outputUrl","");
			}
			data.put("lectureId",lectureId);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/lecture/submitLiveInfo", method = RequestMethod.POST)
	public Map submitLiveInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer lectureId = (Integer) m.get("lectureId");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String pushUrl = (String) m.get("pushUrl");
			String outputUrl = (String) m.get("outputUrl");
			ElearningLectureLive live=elearningLectureLiveDao.getLiveByConditions(lectureId);
			if(live!=null){
				live.setStartTime(startTime);
				live.setEndTime(endTime);
				live.setPersonId(userToken.getPersonId());
				live.setCreateTime(new Date());
				live.setPushUrl(pushUrl);
				live.setOutputUrl(outputUrl);
				elearningLectureLiveDao.update(live);
			}else{
				live=new ElearningLectureLive();
				live.setLectureId(lectureId);
				live.setStartTime(startTime);
				live.setEndTime(endTime);
				live.setPersonId(userToken.getPersonId());
				live.setCreateTime(new Date());
				live.setPushUrl(pushUrl);
				live.setOutputUrl(outputUrl);
				elearningLectureLiveDao.save(live);
			}
			return CommonTool.getNodeMapOk("恭喜您，操作成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	
}
