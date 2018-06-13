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
import org.octopus.auth.jpa_model.InfoPersonInfo;
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
import cn.edu.sdu.homework.dao.ElearningHomeworkAnswerDao;
import cn.edu.sdu.homework.dao.ElearningHomeworkInfoDao;
import cn.edu.sdu.homework.form.BaseHomeWorkActionForm;
import cn.edu.sdu.homework.model.ElearningHomeworkAnswer;
import cn.edu.sdu.homework.model.ElearningHomeworkInfo;

@RestController
public class BaseHomeworkActionWeb {

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
	
	@RequestMapping(value = "/homeworkBean/addHomework", method = RequestMethod.POST)
	public Map addHomework(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String name = (String) m.get("name");
			String chapterName = (String) m.get("chapterName");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String content = (String) m.get("content");
			String taskId = (String) m.get("taskId");
			ElearningHomeworkInfo homework=new ElearningHomeworkInfo();
			homework.setName(name);
			homework.setChapterName(chapterName);
			homework.setStartTime(startTime);
			homework.setEndTime(endTime);
			homework.setContent(content);
			homework.setTaskId(Integer.valueOf(taskId));
			homework.setPersonId(userToken.getPersonId());
			elearningHomeworkInfoDao.save(homework);
			return CommonTool.getNodeMapOk(homework.getHomeworkId().toString());//返回已添加作业的Id
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homeworkBean/getAllHomeworkList", method = RequestMethod.POST)
	public Map getAllTeachTaskInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) throws ParseException {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			List teachTaskList = elearningTeachTaskDao.getAllTeachTaskList();
			for(int i=0;i<teachTaskList.size();i++){
				Map task = new HashMap();
				ElearningTeachTask teachTask=(ElearningTeachTask) teachTaskList.get(i);
				//获得选课人数
				List planList=elearningPlanCourseDao.getPlanListByTaskId(teachTask.getTaskId());
				if(planList!=null){
					task.put("stuCount",planList.size());
				}else{
					task.put("stuCount",0);
				}
				task.put("taskInfo",teachTask.getTaskName()+"--"+teachTask.getElearningCourse().getCourseNum()+teachTask.getElearningCourse().getCourseName()+"--"+teachTask.getEndDate());
				task.put("taskId",teachTask.getTaskId());
				Integer taskId=teachTask.getTaskId();
				List currentList=elearningHomeworkInfoDao.getHomelistListByTaskId(taskId);
				if(currentList!=null){
					task.put("rowNum", currentList.size());
					List homeworkList=new ArrayList();
					for(int j=0;j<currentList.size();j++){
						ElearningHomeworkInfo homework=(ElearningHomeworkInfo) currentList.get(j);
						BaseHomeWorkActionForm homeworkForm=new BaseHomeWorkActionForm();
						homeworkForm.setHomeworkId(homework.getHomeworkId());
						homeworkForm.setChapterName(homework.getChapterName());
						homeworkForm.setName(homework.getName());
						homeworkForm.setStartTime(homework.getStartTime());
						homeworkForm.setEndTime(homework.getEndTime());
						//批改人数
						Integer checkNum=0;
						//作业提交人数
						List answerList=elearningHomeworkAnswerDao.getAnswerListByHomeworkId(homework.getHomeworkId());
						if(answerList!=null){
							homeworkForm.setSubmitNum(answerList.size());
							for(int k=0;k<answerList.size();k++){
								ElearningHomeworkAnswer check=(ElearningHomeworkAnswer) answerList.get(k);
								if(check.getScore()!=null && !check.getScore().equals("")){
									checkNum++;
								}
							}
						}else{
							homeworkForm.setSubmitNum(0);
						}
						homeworkForm.setCheckNum(checkNum);
						homeworkList.add(homeworkForm);
					}
					task.put("homeworkList", homeworkList);
				}else{
					task.put("rowNum", 0);
				}
				dataList.add(task);
			}
			return CommonTool.getNodeMap(dataList, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homeworkBean/getHomeworkInfo", method = RequestMethod.POST)
	public Map getHomeworkInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String  homeworkIds = (String) m.get("homeworkId");
			ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(Integer.valueOf(homeworkIds));
			BaseHomeWorkActionForm form=new BaseHomeWorkActionForm();
			form.setName(homework.getName());
			form.setChapterName(homework.getChapterName());
			form.setStartTime(homework.getStartTime());
			form.setEndTime(homework.getEndTime());
			form.setContent(homework.getContent());
			Integer attachId=homework.getAttachId();
			if(attachId==null || attachId.equals("")){
				form.setAttachName("未上传");
				form.setUploadState("0");
			}else{
				form.setAttachId(attachId);
				BaseAttachmentInfo attach=baseAttachmentInfoDao.find(attachId);
				form.setAttachName(attach.getFileName());
				form.setUploadState("1");
			}
			return CommonTool.getNodeMap(form, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homeworkBean/editHomework", method = RequestMethod.POST)
	public Map editHomework(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// 登录信息不为空
			String name = (String) m.get("name");
			String chapterName = (String) m.get("chapterName");
			String startTime = (String) m.get("startTime");
			String endTime = (String) m.get("endTime");
			String content = (String) m.get("content");
			String homeworkId = (String) m.get("homeworkId");
			ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(Integer.valueOf(homeworkId));
			homework.setName(name);
			homework.setChapterName(chapterName);
			homework.setStartTime(startTime);
			homework.setEndTime(endTime);
			homework.setContent(content);
			homework.setPersonId(userToken.getPersonId());
			elearningHomeworkInfoDao.update(homework);
			return CommonTool.getNodeMapOk("恭喜您，编辑成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homeworkBean/uploadHomework", method = RequestMethod.POST)
	public String uploadHomework(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request,HttpSession session) throws IllegalStateException,
			IOException {
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				request, null);
		if (userToken != null) {
			//获得id
			String homeworkIds=request.getParameter("homeworkId");
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
					BaseAttachmentInfo attach=new BaseAttachmentInfo();
					baseAttachmentInfoDao.save(attach);
					MultipartFile file = files[i];
					//更新监听器
					listener.update(0, -1, -1);//初始化
					String accName=file.getOriginalFilename();
					String accType = accName.substring(accName.lastIndexOf(".")+1);
					String fileName = attach.getAttachId()+"."+accType;
					String accUrl="elearning/homework/"+fileName;
					FileUtility.uploadFileForElearning(listener,file.getInputStream(), accUrl);//文件上传
					//将记录保存到数据库
					attach.setFileName(accName);
					attach.setUrlAddress(accUrl);
					attach.setAttachType(accType);
					attach.setOwnerId(userToken.getPersonId());
					attach.setUploadTime(new Date());
					long size=file.getSize();
					attach.setRemark(String.valueOf(size));
					baseAttachmentInfoDao.update(attach);
					//保存attachId到homework中
					ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(Integer.valueOf(homeworkIds));
					homework.setAttachId(attach.getAttachId());
					elearningHomeworkInfoDao.update(homework);
				}
			}
			return "success";
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "/homeworkBean/deleteHomeworkAttach", method = RequestMethod.POST)
	public Map deleteSource(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String homeworkId = (String) request.get("homeworkId");
		if (userToken != null) {// 登录信息不为空
			ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(Integer.valueOf(homeworkId));
			Integer attachId=homework.getAttachId();
			if(attachId!=null && !attachId.equals("")){
				BaseAttachmentInfo baseAttachmentInfo=baseAttachmentInfoDao.find(attachId);
				FileUtility.deleteFile(baseAttachmentInfo.getUrlAddress());
				homework.setAttachId(null);
				baseAttachmentInfoDao.delete(baseAttachmentInfo);
			}
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homeworkBean/deleteHomework", method = RequestMethod.POST)
	public Map deleteHomework(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer homeworkId = (Integer) request.get("homeworkId");
		if (userToken != null) {// 登录信息不为空
			ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(homeworkId);
			Integer attachId=homework.getAttachId();
			if(attachId!=null && !attachId.equals("")){
				BaseAttachmentInfo baseAttachmentInfo=baseAttachmentInfoDao.find(attachId);
				FileUtility.deleteFile(baseAttachmentInfo.getUrlAddress());
				homework.setAttachId(null);
				baseAttachmentInfoDao.delete(baseAttachmentInfo);
			}
			elearningHomeworkInfoDao.delete(homework);
			return CommonTool.getNodeMapOk("恭喜您，删除成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homeworkBean/getHomeworkSelectList", method = RequestMethod.POST)
	public Map getHomeworkSelectList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String homeworkId = (String) request.get("homeworkId");
		String flag = (String) request.get("flag");
		if (userToken != null) {// 登录信息不为空
			Map map = new HashMap();
			ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(Integer.valueOf(homeworkId));
			map.put("name", homework.getName());
			Integer taskId=homework.getTaskId();
			List dataList=new ArrayList();
			List planList=elearningPlanCourseDao.getPlanListByTaskId(taskId);
			if(planList!=null){
				for(int i=0;i<planList.size();i++){
					ElearningPlanCourse plan =(ElearningPlanCourse) planList.get(i);
					Integer stuId=plan.getStuId();
					InfoPersonInfo student=infoPersonInfoDao.find(stuId);
					BaseHomeWorkActionForm planForm=new BaseHomeWorkActionForm();
					planForm.setPerName(student.getPerName());
					planForm.setStuNum(student.getPerNum());
					planForm.setPersonId(stuId);
					//是否提交作业
					ElearningHomeworkAnswer answer=elearningHomeworkAnswerDao.getAnswerByHomeworkIdAndStuId(Integer.valueOf(homeworkId), stuId);
					if(answer!=null){
						planForm.setSubState(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answer.getCheckTime()));
						if(answer.getScore()!=null && !answer.equals("")){
							planForm.setCheckState(answer.getScore().toString());
						}else{
							if(flag.equals("check")){//已批改
								continue;
							}
							planForm.setCheckState("未提交");
						}
					}else{
						if(flag.equals("sub")){//已提交
							continue;
						}
						if(flag.equals("check")){//已批改
							continue;
						}
						planForm.setState("0");
						planForm.setSubState("未提交");
						planForm.setCheckState("未提交");
					}
					dataList.add(planForm);
				}
			}
			map.put("selectList", dataList);
			return CommonTool.getNodeMap(map, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homeworkBean/getHomeworkAnswerInfo", method = RequestMethod.POST)
	public Map getHomeworkAnswerInfo(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		String homeworkIds = (String) request.get("homeworkId");
		String stuIds = (String) request.get("stuId");
		if (userToken != null) {// 登录信息不为空
			Map map = new HashMap();
			Integer homeworkId=Integer.valueOf(homeworkIds);
			Integer stuId=Integer.valueOf(stuIds);
			ElearningHomeworkInfo homework=elearningHomeworkInfoDao.find(homeworkId);
			map.put("homeworkName", homework.getName());
			InfoPersonInfo info=infoPersonInfoDao.find(stuId);
			map.put("perName", info.getPerName());
			map.put("perNum", info.getPerNum());
			ElearningHomeworkAnswer answer=elearningHomeworkAnswerDao.getAnswerByHomeworkIdAndStuId(homeworkId, stuId);
			map.put("score", answer.getScore());
			map.put("answerContent", answer.getAnswerContent());
			map.put("answerId",answer.getAnswerId());
			Integer attachId=answer.getAttachId();
			if(attachId==null || attachId.equals("")){
				map.put("attachName", "未上传");
				map.put("uploadState", 0);
			}else{
				map.put("attachId", attachId);
				BaseAttachmentInfo attach=baseAttachmentInfoDao.find(attachId);
				map.put("attachName", attach.getFileName());
				map.put("uploadState", 1);
			}
			return CommonTool.getNodeMap(map, null);
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
	@RequestMapping(value = "/homeworkBean/submitHomeworkScore", method = RequestMethod.POST)
	public Map submitHomeworkScore(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Integer answerId = (Integer) request.get("id");
		String scores = (String) request.get("score");
		if (userToken != null) {// 登录信息不为空
			Map map = new HashMap();
			Double score=Double.valueOf(scores);
			ElearningHomeworkAnswer answer=elearningHomeworkAnswerDao.find(answerId);
			answer.setScore(score);
			answer.setCheckTime(new Date());
			answer.setCheckerId(userToken.getPersonId());
			elearningHomeworkAnswerDao.update(answer);
			return CommonTool.getNodeMapOk("恭喜您，提交成功！");
		} else
			return CommonTool.getNodeMapError("抱歉，请重新登录！");
	}
	
}
