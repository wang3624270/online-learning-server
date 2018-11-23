package cn.edu.sdu.course.bean.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.common_business.attachment.dao.BaseAttachmentInfoDao;
import org.octopus.common_business.attachment.model.BaseAttachmentInfo;
import org.octopus.common_business.data_dictionary.server.ServerDataDictionarySI;
import org.sdu.file_util.FileUtility;
import org.sdu.spring_util.ApplicationContextHandle;
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
import cn.edu.sdu.homework.model.ElearningHomeworkInfo;

@RestController
public class BaseCourseActionWeb {
	
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
	
	@RequestMapping(value = "/courseBean/initMenu", method = RequestMethod.POST)
	public Map initMenu(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer personId=userToken.getPersonId();
			InfoPersonInfo personInfo=infoPersonInfoDao.getInfoPersonInfoByPersonId(personId);
			data.put("perTypeCode", personInfo.getPerTypeCode());
			data.put("loginName", userToken.getLoginName());
			data.put("perName", personInfo.getPerName());
			System.out.println(data);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getPersonalCoursesList", method = RequestMethod.POST)
	public Map getPersonalCoursesList(HttpServletRequest request,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				request, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer personId = userToken.getPersonId();
			List courseList = elearningPlanCourseDao
					.getCoursesListByPersonId(personId);
			if(courseList!=null){
			for (int i = 0; i < courseList.size(); i++) {
				BaseCourseActionForm courseForm = new BaseCourseActionForm();
				ElearningPlanCourse plan = (ElearningPlanCourse) courseList
						.get(i);
				ElearningTeachTask task=plan.getElearningTeachTask();
				courseForm.setCourseName(task.getElearningCourse()
						.getCourseName());
				courseForm.setCourseNum(task.getElearningCourse()
						.getCourseNum());
				courseForm.setCollegeName(baseCollegeDao
						.getCollegeNameById(task.getElearningCourse()
								.getCollegeId()));
				courseForm.setTeacherName("");
				courseForm.setProcess("");
				courseForm.setCourseId(task.getElearningCourse()
						.getCourseId());
				courseForm.setTaskId(task.getTaskId());
				courseForm.setStartDate(task.getStartDate());
				courseForm.setEndDate(task.getEndDate());
				ElearningCourse course=task.getElearningCourse();
				courseForm.setBriefIntroduction(course.getBriefIntroduction());
				if(course.getCoverImgAcc()!=null && !course.getCoverImgAcc().equals("")){
					courseForm.setCoverImgAcc(course.getCoverImgAcc());
				}else{
					courseForm.setCoverImgAcc(0);
				}
				dataList.add(courseForm);
			}
		    }
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}

	@RequestMapping(value = "/courseBean/getCourseInfo", method = RequestMethod.POST)
	public Map getCourseInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String courseIdS = (String) request.get("courseId");
		String taskIdS = (String) request.get("taskId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer courseId = Integer.valueOf(courseIdS);
			ElearningCourse course = elearningCourseDao
					.getCourseInfoByCourseId(courseId);
			data.put("courseName", course.getCourseName());// 课程名
			List tree = new ArrayList();
			List chapterList = accessoriesCourseFolderDao
					.getFolferListByCourseId(courseId);
			if(chapterList!=null){
			for (int i = 0; i < chapterList.size(); i++) {
				AccessoriesCourseFolder fold = (AccessoriesCourseFolder) chapterList
						.get(i);
				CourseNodeForm node = new CourseNodeForm();
				node.setId(fold.getId());
				node.setFolderName(fold.getFolderName());
				node.setIsLeaf(fold.getIsLeaf());
				node.setChildList(getCourseTreeListByRoot(fold));
				tree.add(node);
			}
			}
			data.put("tree", tree);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	public List getCourseTreeListByRoot(AccessoriesCourseFolder Root) {
		List courseTree = new ArrayList();
		if (Root != null && Root.getIsLeaf() != null && Root.getIsLeaf() == 0) {
			List list1 = accessoriesCourseFolderDao.getFolferTreeById(Root
					.getId());
			for (int i = 0; i < list1.size(); i++) {
				AccessoriesCourseFolder fold = (AccessoriesCourseFolder) list1
						.get(i);
				CourseNodeForm node = new CourseNodeForm();
				node.setId(fold.getId());
				node.setFolderName(fold.getFolderName());
				node.setIsLeaf(fold.getIsLeaf());
				node.setChildList(getCourseTreeListByRoot(fold));
				courseTree.add(node);
			}
			return courseTree;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/courseBean/getTermInfo", method = RequestMethod.POST)
	public Map getTermInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List termList = elearningTermDao.getAllTermList();
			for (int i = 0; i < termList.size(); i++) {
				BaseCourseActionForm termForm = new BaseCourseActionForm();
				ElearningTerm term = (ElearningTerm) termList.get(i);
				termForm.setTermId(term.getTermId());
				termForm.setTermName(term.getTermName());
				termForm.setTermNum(term.getTermNum());
				termForm.setIsVisible(term.getIsVisible());
				dataList.add(termForm);
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/setTerm", method = RequestMethod.POST)
	public Map setTerm(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		String termIdStr = (String) m.get("termId");
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			List termList = elearningTermDao.getAllTermList();
			for (int i = 0; i < termList.size(); i++) {
				ElearningTerm term = (ElearningTerm) termList.get(i);
				term.setIsVisible(0);
				elearningTermDao.update(term);
			}// 全都设置为非当前学期
			Integer termId = Integer.valueOf(termIdStr);
			ElearningTerm term = elearningTermDao.getTermInfoById(termId);
			term.setIsVisible(1);
			elearningTermDao.update(term);
			return CommonTool.getNodeMapOk("恭喜您，设置当前学期成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，设置失败！");
		}
	}

	@RequestMapping(value = "/courseBean/deleteTerm", method = RequestMethod.POST)
	public Map deleteTerm(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		String termIdStr = (String) m.get("termId");
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			Integer termId = Integer.valueOf(termIdStr);
			ElearningTerm term = elearningTermDao.getTermInfoById(termId);
			if (term.getIsVisible() == 1) {
				return CommonTool.getNodeMapError("抱歉，删除失败！失败原因：不能删除当前学期");
			}
			elearningTermDao.delete(term);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}

	@RequestMapping(value = "/courseBean/addTerm", method = RequestMethod.POST)
	public Map addTerm(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		String termName = (String) m.get("termName");
		String termNum = (String) m.get("termNum");
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			ElearningTerm term = new ElearningTerm();
			term.setTermName(termName);
			term.setTermNum(termNum);
			term.setIsVisible(0);
			elearningTermDao.save(term);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，添加失败！");
		}
	}

	@RequestMapping(value = "/courseBean/getTerm", method = RequestMethod.POST)
	public Map getTerm(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		String termIdS = (String) m.get("termId");
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer termId = Integer.valueOf(termIdS);
			ElearningTerm term = elearningTermDao.getTermInfoById(termId);
			data.put("termId", termId);
			data.put("termName", term.getTermName());
			data.put("termNum", term.getTermNum());
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/editTerm", method = RequestMethod.POST)
	public Map editTerm(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		String termIdS = (String) m.get("termId");
		String termName = (String) m.get("termName");
		String termNum = (String) m.get("termNum");
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer termId = Integer.valueOf(termIdS);
			ElearningTerm term = elearningTermDao.getTermInfoById(termId);
			term.setTermName(termName);
			term.setTermNum(termNum);
			elearningTermDao.update(term);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/getAllCourse", method = RequestMethod.POST)
	public Map getAllCourse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List courseList = elearningCourseDao
					.getCoursesListByPersonId(userToken.getPersonId());
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
					ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
					courseForm.setCourseType(si.getDataNameByCode("KCLXM",course.getCourseType()));
					dataList.add(courseForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/allNewCourse", method = RequestMethod.POST)
	public Map allNewCourse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List courseList = elearningCourseDao
					.getCoursesListByPersonId(userToken.getPersonId());
			for (int i = 0; i < courseList.size(); i++) {
				BaseCourseActionForm courseForm = new BaseCourseActionForm();
				ElearningCourse course = (ElearningCourse) courseList.get(i);
				courseForm.setCourseId(course.getCourseId());
				courseForm.setCourseName(course.getCourseName());
				courseForm.setCourseNum(course.getCourseNum());
				courseForm.setCourseEngName(course.getCourseEngName());
				dataList.add(courseForm);
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/addCourse", method = RequestMethod.POST)
	public Map addCourse(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String courseName = (String) m.get("courseName");
			String enlishName = (String) m.get("enlishName");
			String courseNum = (String) m.get("courseNum");
			String collegeId = (String) m.get("collegeId");
			String courseType = (String) m.get("courseType");
			String teachGroup = (String) m.get("teachGroup");
			String book = (String) m.get("book");
			String reference = (String) m.get("reference");
			String briefIntroduction = (String) m.get("briefIntroduction");
			ElearningCourse course = new ElearningCourse();
			course.setCourseName(courseName);
			course.setCourseEngName(enlishName);
			course.setCourseNum(courseNum);
			course.setCollegeId(Integer.valueOf(collegeId));
			course.setCourseType(courseType);
			course.setTeachGroup(teachGroup);
			course.setBook(book);
			course.setReference(reference);
			course.setBriefIntroduction(briefIntroduction);
			course.setCreatorId(userToken.getPersonId());
			course.setCreateTime(new Date());
			elearningCourseDao.save(course);
			Map data = new HashMap();
			data.put("courseId",course.getCourseId());
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/getAllCollegeOption", method = RequestMethod.POST)
	public Map getAllCollegeOption(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List collegeList = baseCollegeDao.getAllCollegeList();
			if (collegeList != null) {
				for (int i = 0; i < collegeList.size(); i++) {
					BaseCourseActionForm collegeForm = new BaseCourseActionForm();
					BaseCollege college = (BaseCollege) collegeList.get(i);
					collegeForm.setCollegeId(college.getCollegeId());
					collegeForm.setCollegeName(college.getCollegeName());
					dataList.add(collegeForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/courseBean/getListOptionByType", method = RequestMethod.POST)
	public Map getListOptionByType(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String type=(String) m.get("type");
			ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
			List courseTypeList = si.getComboxListByCode(type);
			if (courseTypeList != null) {
				for (int i = 0; i < courseTypeList.size(); i++) {
					ListOptionInfo option=(ListOptionInfo) courseTypeList.get(i);
					dataList.add(option);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}

	@RequestMapping(value = "/courseBean/editCourseInfo", method = RequestMethod.POST)
	public Map editCourseInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			String courseId = (String) m.get("courseId");
			ElearningCourse course = elearningCourseDao
					.getCourseInfoByCourseId(Integer.valueOf(courseId));
			BaseCourseActionForm courseForm = new BaseCourseActionForm();
			courseForm.setCourseName(course.getCourseName());
			courseForm.setCourseEngName(course.getCourseEngName());
			courseForm.setCourseNum(course.getCourseNum());
			courseForm.setCollegeId(course.getCollegeId());
			courseForm.setCourseType(course.getCourseType());
			courseForm.setTeachGroup(course.getTeachGroup());
			courseForm.setBook(course.getBook());
			courseForm.setReference(course.getReference());
			courseForm.setBriefIntroduction(course.getBriefIntroduction());
			if(course.getCoverImgAcc()!=null){
				courseForm.setCoverImgAcc(course.getCoverImgAcc());
			}else{
				courseForm.setCoverImgAcc(0);
			}
			return CommonTool.getNodeMap(courseForm, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/editCourseInfoSubmit", method = RequestMethod.POST)
	public Map editCourseInfoSubmit(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String courseId = (String) m.get("courseId");
			ElearningCourse course = elearningCourseDao
					.getCourseInfoByCourseId(Integer.valueOf(courseId));
			String courseName = (String) m.get("courseName");
			String enlishName = (String) m.get("enlishName");
			String courseNum = (String) m.get("courseNum");
			String collegeId = (String) m.get("collegeId");
			String courseType = (String) m.get("courseType");
			String teachGroup = (String) m.get("teachGroup");
			String book = (String) m.get("book");
			String reference = (String) m.get("reference");
			String briefIntroduction = (String) m.get("briefIntroduction");
			File coverImg = (File) m.get("img");
			course.setCourseName(courseName);
			course.setCourseEngName(enlishName);
			course.setCourseNum(courseNum);
			course.setCollegeId(Integer.valueOf(collegeId));
			course.setCourseType(courseType);
			course.setTeachGroup(teachGroup);
			course.setBook(book);
			course.setReference(reference);
			course.setBriefIntroduction(briefIntroduction);
			course.setCreatorId(userToken.getPersonId());
			course.setCreateTime(new Date());
			elearningCourseDao.update(course);
			return CommonTool.getNodeMapOk("恭喜您，更新成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/deleteCourse", method = RequestMethod.POST)
	public Map deleteCourse(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			String courseId = (String) m.get("courseId");
			ElearningCourse course = elearningCourseDao
					.getCourseInfoByCourseId(Integer.valueOf(courseId));
			elearningCourseDao.delete(course);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else {
			return CommonTool.getNodeMapError("抱歉，删除失败！");
		}
	}

	@RequestMapping(value = "/courseBean/editCourseCatalogue", method = RequestMethod.POST)
	public Map editCourseCatalogue(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List courseContent = (List) m.get("course");
			String courseIdS = (String) m.get("courseId");
			//首先判断是否已有资源记录
			List folderList=accessoriesCourseFolderDao.getAllListByCourseId(Integer.valueOf(courseIdS));
			if(folderList!=null && folderList.size()>0){
				for(int i=0;i<folderList.size();i++){
					AccessoriesCourseFolder folder=(AccessoriesCourseFolder) folderList.get(i);
					Integer folderId=folder.getId();
					List accList=accessoriesFolderAccDao.getAccListByFolderId(folderId);
					if(accList!=null && accList.size()>0){
						return CommonTool.getNodeMapError("抱歉，修改失败。失败原因：该课程已有资源关联！");
					}
				}
			}
			// 先删除已有的
			List oldList = accessoriesCourseFolderDao
					.getAllListByCourseId(Integer.valueOf(courseIdS));
			if (oldList != null && oldList.size() > 0) {
				for (int k = 0; k < oldList.size(); k++) {
					AccessoriesCourseFolder oldFolder = (AccessoriesCourseFolder) oldList
							.get(k);
					accessoriesCourseFolderDao.delete(oldFolder);
				}
			}
			// 再添加当前的
			for (int i = 0; i < courseContent.size(); i++) {
				Map chapter = new HashMap();
				chapter = (Map) courseContent.get(i);
				String chapterName = (String) chapter.get("chapterName");
				// 插入章
				AccessoriesCourseFolder courseFolder = new AccessoriesCourseFolder();
				courseFolder.setCourseId(Integer.valueOf(courseIdS));
				courseFolder.setFatherId(0);
				courseFolder.setFolderName(chapterName);
				courseFolder.setIsLeaf(0);
				courseFolder.setOrderNum(i + 1);
				accessoriesCourseFolderDao.save(courseFolder);
				// 插入完毕
				List sections = (List) chapter.get("section");
				for (int j = 0; j < sections.size(); j++) {
					Map section = new HashMap();
					section = (Map) sections.get(j);
					String sectionName = (String) section.get("sectionName");
					// 插入节
					AccessoriesCourseFolder sectionFolder = new AccessoriesCourseFolder();
					sectionFolder.setCourseId(Integer.valueOf(courseIdS));
					sectionFolder.setFatherId(courseFolder.getId());
					sectionFolder.setFolderName(sectionName);
					sectionFolder.setIsLeaf(1);
					sectionFolder.setOrderNum(j + 1);
					accessoriesCourseFolderDao.save(sectionFolder);
					// 插入完毕
				}
			}
			return CommonTool.getNodeMapOk("恭喜您，保存成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/getCourseChapterInfo", method = RequestMethod.POST)
	public Map getCourseChapterInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String courseIdS = (String) request.get("courseId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer courseId = Integer.valueOf(courseIdS);
			ElearningCourse course = elearningCourseDao
					.getCourseInfoByCourseId(courseId);
			data.put("courseName", course.getCourseName());// 课程名
			List tree = new ArrayList();
			List chapterList = accessoriesCourseFolderDao
					.getFolferListByCourseId(courseId);
			if (chapterList != null && chapterList.size() > 0) {
				for (int i = 0; i < chapterList.size(); i++) {
					AccessoriesCourseFolder fold = (AccessoriesCourseFolder) chapterList
							.get(i);
					CourseNodeForm node = new CourseNodeForm();
					node.setId(fold.getId());
					node.setFolderName(fold.getFolderName());
					node.setIsLeaf(fold.getIsLeaf());
					node.setChildList(getCourseTreeListByRoot(fold));
					tree.add(node);
				}
				data.put("hasCreate", "1");
			} else {
				data.put("hasCreate", "0");
			}
			data.put("tree", tree);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/fileUpload", method = RequestMethod.POST)
	public String fileUpload(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request) throws IllegalStateException,
			IOException {
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				request, null);
		if (userToken != null) {
			String courseId = request.getParameter("courseId");
			String sectionId = request.getParameter("sectionId");
			if (files != null && files.length > 0) {
				// 循环获取file数组中得文件
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					String fileName=file.getOriginalFilename();
					String accType = fileName.substring(fileName.lastIndexOf(".")+1);
					String accName = sectionId+"."+accType;
					String accUrl="elearning/video/"+accName;
					FileUtility.uploadFile(file.getInputStream(), accUrl);
					//将记录保存到数据库
					AccessoriesFolderAcc foldAcc=accessoriesFolderAccDao.getFolferAccByConditions(Integer.valueOf(sectionId), "video");
					if(foldAcc!=null){//已有记录则覆盖
						AccessoriesInfo oldInfo=accessoriesInfoDao.getAccById(foldAcc.getAccId());
						oldInfo.setAccName(accName);
						oldInfo.setAccUrl(accUrl);
						oldInfo.setAccType(accType);
						oldInfo.setAccUploader(userToken.getPersonId());
						oldInfo.setUploadTime(new Date());
						oldInfo.setFileType("video");
						oldInfo.setFileSize(file.getSize());
						accessoriesInfoDao.update(oldInfo);
						foldAcc.setType("video");
						foldAcc.setAccId(oldInfo.getId());
						foldAcc.setFolderId(Integer.valueOf(sectionId));
						accessoriesFolderAccDao.update(foldAcc);
					}else{//未有记录则新增
						AccessoriesInfo acc=new AccessoriesInfo();
						acc.setAccName(accName);
						acc.setAccUrl(accUrl);
						acc.setAccType(accType);
						acc.setAccUploader(userToken.getPersonId());
						acc.setUploadTime(new Date());
						acc.setFileType("video");
						acc.setFileSize(file.getSize());
						accessoriesInfoDao.save(acc);
						foldAcc=new AccessoriesFolderAcc();
						foldAcc.setType("video");
						foldAcc.setAccId(acc.getId());
						foldAcc.setFolderId(Integer.valueOf(sectionId));
						accessoriesFolderAccDao.save(foldAcc);
					}
				}
			}
			return "success";
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "/courseBean/getVideoSource", method = RequestMethod.POST)
	public Map getSourceInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer sectionId = (Integer) request.get("sectionId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			AccessoriesFolderAcc foldAcc=accessoriesFolderAccDao.getFolferAccByConditions(Integer.valueOf(sectionId), "VIDEO");//数据字典 ZYLXM 资源类型码
			if(foldAcc!=null){
				data.put("isExist", "1");
				data.put("accId", foldAcc.getAccId());
			}else{
				data.put("isExist", "0");
			}
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getAllCourseOption", method = RequestMethod.POST)
	public Map getAllCourseOption(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List courseList = elearningCourseDao.getAllCoursesList();
			if (courseList != null) {
				for (int i = 0; i < courseList.size(); i++) {
					BaseCourseActionForm courseForm = new BaseCourseActionForm();
					ElearningCourse course = (ElearningCourse) courseList.get(i);
					courseForm.setCourseId(course.getCourseId());
					courseForm.setCourseName(course.getCourseName());
					courseForm.setCourseNum(course.getCourseNum());
					dataList.add(courseForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}
	
	@RequestMapping(value = "/courseBean/getAllTermsOption", method = RequestMethod.POST)
	public Map getAllTermsOption(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List termsList = elearningTermDao.getAllTermList();
			if (termsList != null) {
				for (int i = 0; i < termsList.size(); i++) {
					BaseCourseActionForm termForm = new BaseCourseActionForm();
					ElearningTerm term = (ElearningTerm) termsList.get(i);
					termForm.setTermId(term.getTermId());
					termForm.setTermName(term.getTermName());
					if(term.getIsVisible()!=null && term.getIsVisible()==1){
						termForm.setTermName(term.getTermName()+"(当前学期)");
					}
					termForm.setTermNum(term.getTermNum());
					dataList.add(termForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else {
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
		}
	}

	@RequestMapping(value = "/courseBean/addTeachTask", method = RequestMethod.POST)
	public Map addTeachTask(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String teachTaskName = (String) m.get("teachTaskName");
			String courseId = (String) m.get("courseId");
			String termId = (String) m.get("termId");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String remark = (String) m.get("remark");
			ElearningTeachTask teachTask = new ElearningTeachTask();
			teachTask.setTaskName(teachTaskName);
			teachTask.setElearningCourse(elearningCourseDao.getCourseInfoByCourseId(Integer.valueOf(courseId)));
			teachTask.setElearningTerm(elearningTermDao.getTermInfoById(Integer.valueOf(termId)));
			teachTask.setStartDate(startTime);
			teachTask.setEndDate(endTime);
			teachTask.setRemark(remark);
			teachTask.setPersonId(userToken.getPersonId());
			elearningTeachTaskDao.save(teachTask);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}

	@RequestMapping(value = "/courseBean/getAllTeachTaskInfo", method = RequestMethod.POST)
	public Map getAllTeachTaskInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List teachTaskList = elearningTeachTaskDao.getAllTeachTaskList();
			for (int i = 0; i < teachTaskList.size(); i++) {
				BaseCourseActionForm teachTaskForm = new BaseCourseActionForm();
				ElearningTeachTask teachTask = (ElearningTeachTask) teachTaskList.get(i);
				teachTaskForm.setTaskId(teachTask.getTaskId());
				teachTaskForm.setTaskName(teachTask.getTaskName());
				teachTaskForm.setStartDate(teachTask.getStartDate());
				teachTaskForm.setEndDate(teachTask.getEndDate());
				ElearningTerm term=teachTask.getElearningTerm();
				teachTaskForm.setTermName(term.getTermName());
				ElearningCourse course=teachTask.getElearningCourse();
				teachTaskForm.setCourseNum(course.getCourseNum());
				teachTaskForm.setCourseName(course.getCourseName());
				teachTaskForm.setCollegeName(baseCollegeDao.getCollegeNameById(course.getCollegeId()));
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
		        ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
		        teachTaskForm.setCourseType(si.getDataNameByCode("KCLXM",course.getCourseType()));
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
				dataList.add(teachTaskForm);
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getTeachTaskInfo", method = RequestMethod.POST)
	public Map getTeachTaskInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		if (userToken != null) {// 登录信息不为空
			String taskId = (String) m.get("taskId");
			ElearningTeachTask teachTask = elearningTeachTaskDao.getTeachTaskByTaskId(Integer.valueOf(taskId));
			BaseCourseActionForm teachTaskForm = new BaseCourseActionForm();
			teachTaskForm.setTaskName(teachTask.getTaskName());
			teachTaskForm.setCourseId(teachTask.getElearningCourse().getCourseId());
			teachTaskForm.setCourseNum(teachTask.getElearningCourse().getCourseNum());
			teachTaskForm.setCourseName(teachTask.getElearningCourse().getCourseName());
			teachTaskForm.setTermId(teachTask.getElearningTerm().getTermId());
			teachTaskForm.setStartDate(teachTask.getStartDate());
			teachTaskForm.setEndDate(teachTask.getEndDate());
			teachTaskForm.setRemark(teachTask.getRemark());
			teachTaskForm.setTaskId(teachTask.getTaskId());
			return CommonTool.getNodeMap(teachTaskForm, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/editTeachTask", method = RequestMethod.POST)
	public Map editTeachTask(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String taskId = (String) m.get("taskId");
			String teachTaskName = (String) m.get("teachTaskName");
			String courseId = (String) m.get("courseId");
			String termId = (String) m.get("termId");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String remark = (String) m.get("remark");
			ElearningTeachTask teachTask = elearningTeachTaskDao.getTeachTaskByTaskId(Integer.valueOf(taskId));
			teachTask.setTaskName(teachTaskName);
			teachTask.setElearningCourse(elearningCourseDao.getCourseInfoByCourseId(Integer.valueOf(courseId)));
			teachTask.setElearningTerm(elearningTermDao.getTermInfoById(Integer.valueOf(termId)));
			teachTask.setStartDate(startTime);
			teachTask.setEndDate(endTime);
			teachTask.setRemark(remark);
			teachTask.setModifyer(userToken.getPersonId());
			teachTask.setModifyTime(new Date());
			elearningTeachTaskDao.save(teachTask);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getAllResourceList", method = RequestMethod.POST)
	public Map getAllResourceList(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List resourceList=accessoriesInfoDao.getAllResourceList();
			if(resourceList!=null){
				for(int i=0;i<resourceList.size();i++){
					AccessoriesInfo acc=(AccessoriesInfo) resourceList.get(i);
					BaseCourseActionForm accForm=new BaseCourseActionForm();
					accForm.setAccId(acc.getId());
					accForm.setAccName(acc.getAccName());
					accForm.setAccType(acc.getAccType());
					accForm.setFolderMap("");
					accForm.setSectionMap("");
					//已关联目录
					List folderAccList=accessoriesFolderAccDao.getFolderListByAccId(acc.getId());
					if(folderAccList!=null && folderAccList.size()>0 ){
						accForm.setFolderMap("已关联");
					}
					//已关联节次
					List sectionAccList=elearningSectionAccDao.getListByAccId(acc.getId());
					if(sectionAccList!=null && sectionAccList.size()>0){
						accForm.setSectionMap("已关联");
					}
					InfoPersonInfo info=infoPersonInfoDao.getInfoPersonInfoByPersonId(acc.getAccUploader());
					if(info!=null){
						accForm.setUploader(info.getPerName());
					}
					String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(acc.getUploadTime());
					accForm.setUploadDate(sdate);
					dataList.add(accForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/uploadResource", method = RequestMethod.POST)
	public String uploadResource(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request,HttpSession session) throws IllegalStateException,
			IOException {
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				request, null);
		if (userToken != null) {
			//准备返回上传进度信息
			UploadListener listener=new UploadListener(session);
			//更新监听器
			listener.update(0, 1, files.length);
			if (files != null && files.length > 0) {
				//获取文件总长度
				int totalSize=0;
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					totalSize+=file.getBytes().length;
				}
				//更新监听器
				listener.update(0, totalSize, files.length);
				// 循环获取file数组中的文件
				for (int i = 0; i < files.length; i++) {
					//数据库中创建一条记录
					AccessoriesInfo acc=new AccessoriesInfo();
					accessoriesInfoDao.save(acc);
					MultipartFile file = files[i];
					//更新监听器
					listener.update(0, -1, -1);//初始化
					String accName=file.getOriginalFilename();
					String accType = accName.substring(accName.lastIndexOf(".")+1);
					String fileName = acc.getId()+"."+accType;
					String accUrl="elearning/resource/"+fileName;
					FileUtility.uploadFileForElearning(listener,file.getInputStream(), accUrl);//文件上传
					//将记录保存到数据库
					acc.setAccName(accName);
					acc.setAccUrl(accUrl);
					acc.setAccType(accType);
					acc.setFileType(accType);
					acc.setAccUploader(userToken.getPersonId());
					acc.setUploadTime(new Date());
					acc.setFileSize(file.getSize());
					accessoriesInfoDao.update(acc);
				}
			}
			return "success";
		}else{
			return null;
		}
	}
	
	/**
     * 这里是获取上传文件状态信息的访问接口
     * @param session
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/courseBean/getStatus", method = RequestMethod.GET)
	public UploadStatus getStatus(HttpSession session){
    	System.out.println((UploadStatus)session.getAttribute("upload_status"));
		return (UploadStatus)session.getAttribute("upload_status");
	}
	
	@RequestMapping(value = "/courseBean/deleteSource", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/courseBean/editCourseSection", method = RequestMethod.POST)
	public Map editCourseSection(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List courseContent = (List) m.get("course");
			String taskIdS = (String) m.get("taskId");
			//首先判断是否已有资源记录
			List sectionList=elearningCourseSectionDao.getSectionListByTaskId(Integer.valueOf(taskIdS));
			if(sectionList!=null && sectionList.size()>0){
				for(int i=0;i<sectionList.size();i++){
					ElearningCourseSection section=(ElearningCourseSection) sectionList.get(i);
					List accList=elearningSectionAccDao.getAllListBySectionId(section.getSectionId());
					if(accList!=null && accList.size()>0){
						return CommonTool.getNodeMapError("抱歉，修改失败。失败原因：该节次已有资源关联！");
					}
				}
			}
			// 先删除已有的
			List oldList = elearningCourseSectionDao
					.getSectionListByTaskId(Integer.valueOf(taskIdS));
			if (oldList != null && oldList.size() > 0) {
				for (int k = 0; k < oldList.size(); k++) {
					ElearningCourseSection oldFolder = (ElearningCourseSection) oldList
							.get(k);
					elearningCourseSectionDao.delete(oldFolder);
				}
			}
			// 再添加当前的
			for (int i = 0; i < courseContent.size(); i++) {
				Map chapter = new HashMap();
				chapter = (Map) courseContent.get(i);
				String chapterName = (String) chapter.get("chapterName");
				// 插入节次
				ElearningCourseSection section=new ElearningCourseSection();
				section.setSectionName(chapterName);
				section.setTaskId(Integer.valueOf(taskIdS));
				section.setPersonId(userToken.getPersonId());
				String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				section.setCreateTime(createTime);
				section.setOrderNum(i + 1);
				elearningCourseSectionDao.save(section);
				// 插入完毕
			}
			return CommonTool.getNodeMapOk("恭喜您，保存成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getCourseSectionInfo", method = RequestMethod.POST)
	public Map getCourseSectionInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String taskIdS = (String) request.get("taskId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer taskId = Integer.valueOf(taskIdS);
			ElearningTeachTask task=elearningTeachTaskDao.find(taskId);
			ElearningCourse course = task.getElearningCourse();
			data.put("courseName", course.getCourseName());// 课程名
			data.put("courseId", course.getCourseId());// 课程名
			data.put("taskName", task.getTaskName());// 课程计划名
			List tree = new ArrayList();
			List sectionList = elearningCourseSectionDao
					.getSectionListByTaskId(taskId);
			if (sectionList != null && sectionList.size() > 0) {
				for (int i = 0; i < sectionList.size(); i++) {
					ElearningCourseSection section = (ElearningCourseSection) sectionList
							.get(i);
					BaseCourseActionForm sectionForm=new BaseCourseActionForm();
					sectionForm.setSectionId(section.getSectionId());
					sectionForm.setSectionName(section.getSectionName());
					tree.add(sectionForm);
				}
				data.put("hasCreate", "1");
			} else {
				data.put("hasCreate", "0");
			}
			data.put("tree", tree);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/matchSectionAndResource", method = RequestMethod.POST)
	public Map matchSectionAndResource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String accIdS = (String) request.get("accId");
		String type = (String) request.get("type");
		Integer sectionId = (Integer) request.get("sectionId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
			Integer accId = Integer.valueOf(accIdS);
			//首先判断是否已经匹配该资源
			ElearningSectionAcc map=elearningSectionAccDao.getMapBySectionIdAndAccId(sectionId, null,type);//数据字典 ZYLXM 资源类型码
			if(map!=null){
				return CommonTool.getNodeMapError("抱歉，关联视频失败。失败原因：该节次已关联"+si.getDataNameByCode("ZYLXM",type)+"资源！");
			}
			ElearningSectionAcc match=new ElearningSectionAcc();
			match.setType(type);
			match.setAccId(accId);
			match.setSectionId(sectionId);
			elearningSectionAccDao.save(match);
			return CommonTool.getNodeMapOk("恭喜您，关联成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getMatchListBySectionId", method = RequestMethod.POST)
	public Map getMatchListBySectionId(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer sectionId = (Integer) request.get("sectionId");
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List matchList=elearningSectionAccDao.getAllListBySectionId(sectionId);
			if(matchList!=null){
				for(int i=0;i<matchList.size();i++){
					ElearningSectionAcc match=(ElearningSectionAcc) matchList.get(i);
					BaseCourseActionForm matchForm=new BaseCourseActionForm();
					ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
					matchForm.setMatchType(si.getDataNameByCode("ZYLXM",match.getType()));
					matchForm.setId(match.getId());//匹配Id
					if(match.getType().equals("PRACTICE")){
						ElearningPracticeInfo accInfo=elearningPracticeInfoDao.find(match.getAccId());
						matchForm.setAccName(accInfo.getPracticeTitle());
						matchForm.setAccId(accInfo.getPracticeId());
						matchForm.setAccType("practice");
						InfoPersonInfo info=infoPersonInfoDao.getInfoPersonInfoByPersonId(accInfo.getPersonId());
						matchForm.setUploader(info.getPerName());
						String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(accInfo.getCreateTime());
						matchForm.setUploadDate(sdate);
					}else{
						AccessoriesInfo accInfo=accessoriesInfoDao.getAccById(match.getAccId());
						matchForm.setAccName(accInfo.getAccName());
						matchForm.setAccId(accInfo.getId());
						matchForm.setAccType(accInfo.getAccType());
						InfoPersonInfo info=infoPersonInfoDao.getInfoPersonInfoByPersonId(accInfo.getAccUploader());
						matchForm.setUploader(info.getPerName());
						String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(accInfo.getUploadTime());
						matchForm.setUploadDate(sdate);
					}
					dataList.add(matchForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getAllResourceListOfNotMatch", method = RequestMethod.POST)
	public Map getAllResourceListOfNotMatch(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer sectionId=(Integer) m.get("sectionId");
			List resourceList=accessoriesInfoDao.getAllResourceList();
			if(resourceList!=null){
				for(int i=0;i<resourceList.size();i++){
					AccessoriesInfo acc=(AccessoriesInfo) resourceList.get(i);
					//剔除已匹配的
					Integer flag=0;
					List matchList=elearningSectionAccDao.getAllListBySectionId(sectionId);
					if(matchList!=null){
						for(int j=0;j<matchList.size();j++){
							ElearningSectionAcc match=(ElearningSectionAcc) matchList.get(j);
							Integer accId=match.getAccId();
							if(accId.equals(acc.getId())){
								flag=1;
								break;//已匹配则不加载
							}
						}
					}
					if(flag==1){
						continue;
					}
					BaseCourseActionForm accForm=new BaseCourseActionForm();
					accForm.setAccId(acc.getId());
					accForm.setAccName(acc.getAccName());
					accForm.setAccType(acc.getAccType());
					InfoPersonInfo info=infoPersonInfoDao.getInfoPersonInfoByPersonId(acc.getAccUploader());
					accForm.setUploader(info.getPerName());
					String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(acc.getUploadTime());
					accForm.setUploadDate(sdate);
					dataList.add(accForm);
				}
			}
			List practiceList=elearningPracticeInfoDao.getPracticeList();
			if(practiceList!=null){
				for(int i=0;i<practiceList.size();i++){
					ElearningPracticeInfo acc=(ElearningPracticeInfo) practiceList.get(i);
					//剔除已匹配的
					Integer flag=0;
					ElearningSectionAcc match=elearningSectionAccDao.getMapBySectionIdAndAccId(sectionId, null, "PRACTICE");
					if(match!=null && match.getAccId()==acc.getPracticeId()){
						continue;
					}
					BaseCourseActionForm accForm=new BaseCourseActionForm();
					accForm.setAccId(acc.getPracticeId());
					accForm.setAccName(acc.getPracticeTitle());
					accForm.setAccType("practice");
					InfoPersonInfo info=infoPersonInfoDao.getInfoPersonInfoByPersonId(acc.getPersonId());
					accForm.setUploader(info.getPerName());
					String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(acc.getCreateTime());
					accForm.setUploadDate(sdate);
					dataList.add(accForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/cancelMatchSectionAndResource", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/courseBean/getMatchListByfolderId", method = RequestMethod.POST)
	public Map getMatchListByfolderId(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer folderId = (Integer) request.get("folderId");
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List matchList=accessoriesFolderAccDao.getAccListByFolderId(folderId);
			if(matchList!=null){
				for(int i=0;i<matchList.size();i++){
					AccessoriesFolderAcc match=(AccessoriesFolderAcc) matchList.get(i);
					BaseCourseActionForm matchForm=new BaseCourseActionForm();
					ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典 ZYLXM 资源类型码
					matchForm.setMatchType(si.getDataNameByCode("ZYLXM",match.getType()));
					matchForm.setId(match.getId());//匹配Id
					AccessoriesInfo accInfo=accessoriesInfoDao.getAccById(match.getAccId());
					matchForm.setAccName(accInfo.getAccName());
					matchForm.setAccId(accInfo.getId());
					matchForm.setAccType(accInfo.getAccType());
					InfoPersonInfo info=infoPersonInfoDao.getInfoPersonInfoByPersonId(accInfo.getAccUploader());
					matchForm.setUploader(info.getPerName());
					String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(accInfo.getUploadTime());
					matchForm.setUploadDate(sdate);
					dataList.add(matchForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getAllResourceListOfNotMatchFolder", method = RequestMethod.POST)
	public Map getAllResourceListOfNotMatchFolder(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			Integer folderId=(Integer) m.get("folderId");
			List resourceList=accessoriesInfoDao.getAllResourceList();
			if(resourceList!=null){
				for(int i=0;i<resourceList.size();i++){
					AccessoriesInfo acc=(AccessoriesInfo) resourceList.get(i);
					//剔除已匹配的
					Integer flag=0;
					List matchList=accessoriesFolderAccDao.getAccListByFolderId(folderId);
					if(matchList!=null){
						for(int j=0;j<matchList.size();j++){
							AccessoriesFolderAcc match=(AccessoriesFolderAcc) matchList.get(j);
							Integer accId=match.getAccId();
							if(accId.equals(acc.getId())){
								flag=1;
								break;//已匹配则不加载
							}
						}
					}
					if(flag==1){
						continue;
					}
					BaseCourseActionForm accForm=new BaseCourseActionForm();
					accForm.setAccId(acc.getId());
					accForm.setAccName(acc.getAccName());
					accForm.setAccType(acc.getAccType());
					InfoPersonInfo info=infoPersonInfoDao.getInfoPersonInfoByPersonId(acc.getAccUploader());
					accForm.setUploader(info.getPerName());
					String sdate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(acc.getUploadTime());
					accForm.setUploadDate(sdate);
					dataList.add(accForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/matchfolderAndResource", method = RequestMethod.POST)
	public Map matchfolderAndResource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String accIdS = (String) request.get("accId");
		String type = (String) request.get("type");
		Integer folderId = (Integer) request.get("folderId");
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			ServerDataDictionarySI si =  ServerDataDictionarySI.getInstance();//数据字典
			Integer accId = Integer.valueOf(accIdS);
			//首先判断是否已经匹配该资源
			AccessoriesFolderAcc map=accessoriesFolderAccDao.getFolferAccByCondition(folderId, null, type);//数据字典 ZYLXM 资源类型码
			if(map!=null){
				return CommonTool.getNodeMapError("抱歉，关联视频失败。失败原因：该节次已关联"+si.getDataNameByCode("ZYLXM",type)+"资源！");
			}
			AccessoriesFolderAcc match=new AccessoriesFolderAcc();
			match.setType(type);
			match.setAccId(accId);
			match.setFolderId(folderId);
			accessoriesFolderAccDao.save(match);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/cancelMatchFolderAndResource", method = RequestMethod.POST)
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
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getAllTaskNewsInfo", method = RequestMethod.POST)
	public Map getAllTaskNewsInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String taskIds=(String) m.get("taskId");
			Integer taskId=Integer.valueOf(taskIds);
			List newsList=elearningTaskNewsDao.getNewsListByTaskId(taskId);
			if(newsList!=null){
				for(int i=0;i<newsList.size();i++){
					ElearningTaskNews news=(ElearningTaskNews) newsList.get(i);
					BaseCourseActionForm newsForm=new BaseCourseActionForm();
					newsForm.setNewsId(news.getNewsId());
					newsForm.setContent(news.getContent());
					newsForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(news.getCreateTime()));
					newsForm.setTitile(news.getTitle());
					dataList.add(newsForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/addTaskNews", method = RequestMethod.POST)
	public Map addTaskNews(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String taskIds=(String) m.get("taskId");
			String title=(String) m.get("title");
			String content=(String) m.get("content");
			ElearningTaskNews news=new ElearningTaskNews();
			news.setTitle(title);
			news.setContent(content);
			news.setTaskId(Integer.valueOf(taskIds));
			news.setPersonId(userToken.getPersonId());
			news.setCreateTime(new Date());
			elearningTaskNewsDao.save(news);
			return CommonTool.getNodeMapOk("恭喜您，添加成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/deleteTaskNews", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/courseBean/getSectionInfo", method = RequestMethod.POST)
	public Map getSectionInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer sectionId=(Integer) m.get("sectionId");
			ElearningCourseSection section=elearningCourseSectionDao.find(sectionId);
			data.put("introduction", section.getIntroduction());
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/editSectionInfo", method = RequestMethod.POST)
	public Map editSectionInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer sectionId=(Integer) m.get("sectionId");
			String introduction=(String) m.get("introduction");
			ElearningCourseSection section=elearningCourseSectionDao.find(sectionId);
			section.setIntroduction(introduction);
			elearningCourseSectionDao.update(section);
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getAllCourseComment", method = RequestMethod.POST)
	public Map getAllSectionScore(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList=new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String taskId=(String) m.get("taskId");
			List scoreList=elearningCourseCommentInfoDao.getElearningResourceScoreListByCondition(Integer.valueOf(taskId),null);
			if(scoreList!=null){ 
				for(int j=0;j<scoreList.size();j++){
					BaseCourseActionForm scoreForm=new BaseCourseActionForm();
					ElearningCourseCommentInfo score=(ElearningCourseCommentInfo) scoreList.get(j);
					ElearningTeachTask task=elearningTeachTaskDao.find(score.getTaskId());
					ElearningCourse course=task.getElearningCourse();
					InfoPersonInfo person=infoPersonInfoDao.find(score.getPersonId());
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
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/deleteCourseScore", method = RequestMethod.POST)
	public Map deleteCourseScore(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList=new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String id=(String) m.get("commentId");
			ElearningCourseCommentInfo comment=elearningCourseCommentInfoDao.find(Integer.valueOf(id));
			if(comment!=null){ 
				elearningCourseCommentInfoDao.delete(comment);
			}
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/addCourseComment", method = RequestMethod.POST)
	public Map addCourseComment(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList=new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String taskId=(String) m.get("taskId");
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
	
	@RequestMapping(value = "/courseBean/getCourseOrSectionInfo", method = RequestMethod.POST)
	public Map getCourseOrSectionInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			String taskId=(String) m.get("taskId");
			String courseId=(String) m.get("courseId");
			String sectionId=(String) m.get("sectionId");
			if(courseId!=null){
				ElearningCourse course=elearningCourseDao.find(Integer.valueOf(courseId));
				data.put("courseName", course.getCourseName());
			}
			if(sectionId!=null){
				ElearningCourseSection section=elearningCourseSectionDao.find(Integer.valueOf(sectionId));
				data.put("sectionName", section.getSectionName());
			}
			if(taskId!=null){
				ElearningTeachTask task=elearningTeachTaskDao.find(Integer.valueOf(taskId));
				data.put("taskName", task.getTaskName());
			}
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getCourseInterlocutionList", method = RequestMethod.POST)
	public Map getCourseInterlocutionList(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList=new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String taskId=(String) m.get("taskId");
			String flag=(String) m.get("flag");
			ElearningTeachTask task=elearningTeachTaskDao.find(Integer.valueOf(taskId));
			List interlocutionList=elearningInterlocutionInfoDao.getInterlocutionListByCourseIdAndSectionId(Integer.valueOf(taskId), null);
			if(interlocutionList!=null){ 
				for(int j=0;j<interlocutionList.size();j++){
					BaseCourseActionForm scoreForm=new BaseCourseActionForm();
					ElearningInterlocutionInfo interlocution=(ElearningInterlocutionInfo) interlocutionList.get(j);
					ElearningCourseSection section=elearningCourseSectionDao.find(interlocution.getSectionId());
					String state="未回答";
					if(interlocution.getAnswerTime()!=null){
						state="已回答";
					}
					//根据flag筛选合适的列表
					if(flag!=null && flag.equals("check")){
						if(interlocution.getAnswerTime()==null){
							continue;
						}
					}else if(flag!=null && flag.equals("uncheck")){
						if(interlocution.getAnswerTime()!=null){
							continue;
						}
					}
					InfoPersonInfo person=infoPersonInfoDao.find(interlocution.getPersonId());
					scoreForm.setPerName(person.getPerName());
					scoreForm.setPerNum(person.getPerNum());
					scoreForm.setTaskName(task.getTaskName());
					scoreForm.setCourseName(task.getElearningCourse().getCourseName());
					scoreForm.setSectionName(section.getSectionName());
					scoreForm.setTitile(interlocution.getTitle());
					scoreForm.setQuestionId(interlocution.getQuestionId());
					scoreForm.setState(state);
					scoreForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(interlocution.getCreateTime()));
					dataList.add(scoreForm);
				}
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/getInterlocutionInfo", method = RequestMethod.POST)
	public Map getInterlocutionInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// 登录信息不为空
			Integer questionId=(Integer) m.get("questionId");
			ElearningInterlocutionInfo interlocution=elearningInterlocutionInfoDao.find(questionId);
			data.put("title", interlocution.getTitle());
			data.put("question", interlocution.getQuestion());
			data.put("answer", interlocution.getAnswer());
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/submitInterlocutionReplay", method = RequestMethod.POST)
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
			return CommonTool.getNodeMapOk("恭喜您，修改成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/courseBean/uploadImg", method = RequestMethod.POST)
	public String uploadHomework(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request,HttpSession session) throws IllegalStateException,
			IOException {
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				request, null);
		if (userToken != null) {
			//获得id
			String courseIds=request.getParameter("courseId");
			//数据库中创建一条记录
			BaseAttachmentInfo attach=new BaseAttachmentInfo();
			baseAttachmentInfoDao.save(attach);
			MultipartFile file = files[0];
			String accName=file.getOriginalFilename();
			String accType = accName.substring(accName.lastIndexOf(".")+1);
			String fileName = attach.getAttachId()+"."+accType;
			String accUrl="elearning/homework/"+fileName;
			FileUtility.uploadFile(file.getInputStream(), accUrl);//文件上传
			//图片缩放
			BaseCourseActionRule.zoomImage(accUrl, accUrl, 520, 303);
			//将记录保存到数据库
			attach.setFileName(accName);
			attach.setUrlAddress(accUrl);
			attach.setAttachType(accType);
			attach.setOwnerId(userToken.getPersonId());
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
			return "success";
		}else{
			return "error";
		}
	}
	
	
}
