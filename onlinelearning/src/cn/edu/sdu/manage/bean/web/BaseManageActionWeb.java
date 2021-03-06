package cn.edu.sdu.manage.bean.web;

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

import org.octopus.auth.jpa_dao.GroupFunresDao;
import org.octopus.auth.jpa_dao.InfoPersonInfoDao;
import org.octopus.auth.jpa_dao.SysFunResDao;
import org.octopus.auth.jpa_dao.SysUserDao;
import org.octopus.auth.jpa_dao.SysUserGroupDao;
import org.octopus.auth.jpa_dao.UserGroupDao;
import org.octopus.auth.jpa_model.GroupFunres;
import org.octopus.auth.jpa_model.InfoPersonInfo;
import org.octopus.auth.jpa_model.SysFunRes;
import org.octopus.auth.jpa_model.SysUser;
import org.octopus.auth.jpa_model.SysUserGroup;
import org.octopus.auth.jpa_model.UserGroup;
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
import cn.edu.sdu.common.util.Base64;
import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.common.util.Md5Util;
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
import cn.edu.sdu.course.model.ElearningTaskNews;
import cn.edu.sdu.course.model.ElearningTeachTask;
import cn.edu.sdu.course.model.ElearningTerm;
import cn.edu.sdu.course.rule.BaseCourseActionRule;
import cn.edu.sdu.exam.dao.ElearningPracticeInfoDao;
import cn.edu.sdu.exam.model.ElearningPracticeInfo;
import cn.edu.sdu.homework.model.ElearningHomeworkInfo;
import cn.edu.sdu.manage.dao.ElearningNoticeDao;
import cn.edu.sdu.manage.dao.ElearningPayRecordDao;
import cn.edu.sdu.manage.form.BaseManageActionForm;
import cn.edu.sdu.manage.model.ElearningNotice;
import cn.edu.sdu.manage.model.ElearningPayRecord;

@RestController
public class BaseManageActionWeb {
	
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
	private SysUserDao sysUserDao;
	@Autowired
	private UserGroupDao userGroupDao;
	@Autowired
	private SysUserGroupDao sysUserGroupDao;
	@Autowired
	private GroupFunresDao groupFunresDao;
	@Autowired
	private SysFunResDao sysFunResDao;
	@Autowired
	private ElearningTaskChargeDao elearningTaskChargeDao;
	@Autowired
	private ElearningPayRecordDao elearningPayRecordDao;
	@Autowired
	private ElearningNoticeDao elearningNoticeDao;
	
	@RequestMapping(value = "/manage/getPersonInfo", method = RequestMethod.GET)
	public Map getPersonInfo(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer personId=userToken.getPersonId();
			InfoPersonInfo info=infoPersonInfoDao.find(personId);
			SysUser user=sysUserDao.getSysUsersByUserid(personId);
			UserGroup ug=userGroupDao.getRelationBySys(user.getSysusrid());
			List list=sysUserGroupDao.getGroupListByConditions(ug.getGroupid(), null);
			if(list!=null && list.size()>0){
				SysUserGroup group=(SysUserGroup) list.get(0);
				data.put("groupName", group.getName());
			}
			data.put("personId", personId);
			data.put("perName", info.getPerName());
			data.put("mobilePhone", info.getMobilePhone());
			data.put("loginName", user.getLoginName());
			data.put("password", new String(Base64.decode(user.getPwd().getBytes())));
			data.put("genderCode", info.getGenderCode());
			data.put("perIdCard", info.getPerIdCard());
			data.put("perTypeCode", info.getPerTypeCode());
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/getMenuList", method = RequestMethod.POST)
	public Map menuInit(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		String name = (String) m.get("name");
		String path = (String) m.get("path");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List menuList=new ArrayList();
			menuList=sysFunResDao.getMenuListByCondition(name, path);
			data.put("menuList", menuList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/deleteMenu", method = RequestMethod.POST)
	public Map deleteMenu(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer resid = (Integer) m.get("resid");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List gfList=groupFunresDao.getListByResId(resid);
			if(gfList!=null && gfList.size()>0){
				for(int i=0;i<gfList.size();i++){
					GroupFunres gf=(GroupFunres) gfList.get(i);
					groupFunresDao.delete(gf);
				}
			}
			sysFunResDao.deleteById(resid);
			return CommonTool.getNodeMapOk("��ϲ�����޸ĳɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}

	@RequestMapping(value = "/manage/addOrEditMenu", method = RequestMethod.POST)
	public Map addOrEditMenu(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer resid = (Integer) m.get("resid");
		String name = (String) m.get("name");
		String path = (String) m.get("path");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			if(resid==null){
				SysFunRes res=new SysFunRes();
				res.setName(name);
				res.setPath(path);
				sysFunResDao.save(res);
			}else{
				SysFunRes res=sysFunResDao.find(resid);
				res.setName(name);
				res.setPath(path);
				sysFunResDao.update(res);
			}
			return CommonTool.getNodeMapOk("��ϲ�����޸ĳɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/getGroupList", method = RequestMethod.GET)
	public Map getGroupList(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List groupList=sysUserGroupDao.getGroupListByConditions(null, null);
			data.put("groupList", groupList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/deleteGroup", method = RequestMethod.POST)
	public Map deleteGroup(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer groupid = (Integer) m.get("groupid");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List gfList=groupFunresDao.getIdListByGroupId(groupid);
			if(gfList!=null && gfList.size()>0){
				for(int i=0;i<gfList.size();i++){
					GroupFunres gf=(GroupFunres) gfList.get(i);
					groupFunresDao.delete(gf);
				}
			}
			List ugList=userGroupDao.getListByConditionds(null, groupid);
			if(ugList!=null && ugList.size()>0){
				for(int i=0;i<ugList.size();i++){
					UserGroup ug=(UserGroup) ugList.get(i);
					userGroupDao.delete(ug);
				}
			}
			sysUserGroupDao.deleteById(groupid);
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}

	@RequestMapping(value = "/manage/addOrEditGroup", method = RequestMethod.POST)
	public Map addOrEditGroup(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer groupid = (Integer) m.get("groupid");
		String name = (String) m.get("name");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			if(groupid==null){
				SysUserGroup ug=new SysUserGroup();
				ug.setName(name);
				ug.setCreaterId(userToken.getPersonId());
				sysUserGroupDao.save(ug);
			}else{
				SysUserGroup ug=sysUserGroupDao.find(groupid);
				ug.setName(name);
				ug.setCreaterId(userToken.getPersonId());
				sysUserGroupDao.update(ug);
			}
			return CommonTool.getNodeMapOk("��ϲ�����޸ĳɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}

	@RequestMapping(value = "/manage/getResListByGroupid", method = RequestMethod.POST)
	public Map getUnrelatedListByGroupid(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer groupid = (Integer) m.get("groupid");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��\
			List unrelatedList=new ArrayList();
			List relatedList=new ArrayList();
			List gfList=groupFunresDao.getListByConditions(groupid, null);
			Integer [] resids=new Integer[(gfList!=null && gfList.size()>0 )? gfList.size(): 0];
			if(gfList!=null && gfList.size()>0){
				for(int i=0;i<gfList.size();i++){
					GroupFunres gf=(GroupFunres) gfList.get(i);
					resids[i]=gf.getResid();
				}
			}
			List resList=sysFunResDao.getMenuListByCondition(null, null);
			for(int i=0;i<resList.size();i++){
				SysFunRes res=(SysFunRes) resList.get(i);
				Integer resid=res.getResid();
				BaseManageActionForm resform=new BaseManageActionForm();
				resform.setValue(res.getResid());
				resform.setDesc(res.getName());
				int flag=0;
				for(int j=0;j<resids.length;j++){
					if(resid.equals(resids[j])){
						flag=1;
						break;
					}
				}
				if(flag==1){
					relatedList.add(res.getResid());
				}
				unrelatedList.add(resform);
			}
			data.put("relatedList", relatedList);
			data.put("unrelatedList", unrelatedList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/setGroupFunres", method = RequestMethod.POST)
	public Map setGroupFunres(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer groupid = (Integer) m.get("groupid");
		List relatedList = (List) m.get("relatedList");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��\
			List list=groupFunresDao.getListByConditions(groupid, null);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					GroupFunres gf=(GroupFunres) list.get(i);
					groupFunresDao.delete(gf);
				}
			}
			if(relatedList!=null && relatedList.size()>=0){
				for(int i=0;i<relatedList.size();i++){
					GroupFunres gf=new GroupFunres();
					Integer resid=(Integer) relatedList.get(i);
					gf.setResid(resid);
					gf.setGroupid(groupid);
					groupFunresDao.save(gf);
				}
			}
			return CommonTool.getNodeMapOk("��ϲ�����޸ĳɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/getPersonList", method = RequestMethod.POST)
	public Map getPersonList(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer personId = (Integer) m.get("personId");
		String perName = (String) m.get("perName");
		Integer groupid = (Integer) m.get("groupid");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			List list=infoPersonInfoDao.getPersonListbyConditions(personId, perName,groupid);
			List personList=new ArrayList();
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					BaseManageActionForm personForm=new BaseManageActionForm();
					InfoPersonInfo person=(InfoPersonInfo) list.get(i);
					SysUser user=sysUserDao.getSysUsersByUserid(person.getPersonId());
					List groupList=userGroupDao.getListByConditionds(user.getSysusrid(), null);
					String groupName="";
					if(groupList!=null && groupList.size()>0){
						UserGroup ug=(UserGroup) groupList.get(0);
						SysUserGroup group=sysUserGroupDao.find(ug.getGroupid());
						personForm.setGroupid(ug.getGroupid());
						groupName=group.getName();
					}
					personForm.setPersonId(person.getPersonId());
					personForm.setPerName(person.getPerName());
					personForm.setLoginName(user.getLoginName());
					personForm.setPerNum(user.getLoginName());
					personForm.setPassword(new String(Base64.decode(user.getPwd().getBytes())));
					personForm.setPwd(new String(Base64.decode(user.getPwd().getBytes())));
					personForm.setGroupName(groupName);
					personForm.setMobilePhone(person.getMobilePhone());
					personForm.setGenderCode(person.getGenderCode());
					personForm.setSysusrid(user.getSysusrid());
					personForm.setPerIdCard(person.getPerIdCard());
					personList.add(personForm);
				}
			}
			data.put("personList", personList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/setUserGroup", method = RequestMethod.POST)
	public Map setUserGroup(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer sysusrid = (Integer) m.get("sysusrid");
		Integer groupid = (Integer) m.get("groupid");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��\
			List list=userGroupDao.getListByConditionds(sysusrid, groupid);
			if(list==null || list.size()==0){
				UserGroup ug=new UserGroup();
				ug.setGroupid(groupid);
				ug.setSysusrid(sysusrid);
				userGroupDao.save(ug);
			}
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/addOrEditUser", method = RequestMethod.POST)
	public Map addOrEditUser(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		Integer personId = (Integer) m.get("personId");
		String perNum = (String) m.get("perNum");
		String pwd = (String) m.get("pwd");
		Integer groupid = (Integer) m.get("groupid");
		String perName = (String) m.get("perName");
		String perIdCard = (String) m.get("perIdCard");
		String genderCode = (String) m.get("genderCode");
		String mobilePhone = (String) m.get("mobilePhone");
		String perTypeCode = (String) m.get("perTypeCode");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			if(personId!=null){
				InfoPersonInfo po=infoPersonInfoDao.getInfoPersonInfoByPerNum(perNum);
				if(po!=null && !po.getPersonId().equals(personId)){
					return CommonTool.getNodeMapError("��Ǹ���õ�¼���Ѵ��ڣ����������룡");
				}
				InfoPersonInfo info=infoPersonInfoDao.find(personId);
				SysUser user=sysUserDao.getSysUsersByUserid(personId);
				info.setPerNum(perNum);
				info.setPerName(perName);
				info.setPerIdCard(perIdCard);
				info.setGenderCode(genderCode);
				info.setMobilePhone(mobilePhone);
				info.setPerTypeCode(perTypeCode);
				info.setModifyTime(new Date());
				info.setModifyerId(userToken.getPersonId());
				infoPersonInfoDao.update(info);
				user.setLoginName(perNum);
				user.setPassword(Md5Util.GetMD5Code(pwd));
				user.setPwd(Base64.getBase64Code(pwd));
				sysUserDao.update(user);
				UserGroup ug= userGroupDao.getUserGroup(user.getSysusrid(), groupid);
				if(ug==null){
					ug= new UserGroup();
					ug.setGroupid(groupid);
					ug.setSysusrid(user.getSysusrid());
					userGroupDao.save(ug);
				}else{
					ug.setGroupid(groupid);
					userGroupDao.update(ug);
				}
			}else{
				InfoPersonInfo po=infoPersonInfoDao.getInfoPersonInfoByPerNum(perNum);
				if(po!=null){
					return CommonTool.getNodeMapError("��Ǹ���õ�¼���Ѵ��ڣ����������룡");
				}
				InfoPersonInfo info=new InfoPersonInfo();
				SysUser user=new SysUser();
				info.setPerNum(perNum);
				info.setPerName(perName);
				info.setPerIdCard(perIdCard);
				info.setGenderCode(genderCode);
				info.setPerTypeCode(perTypeCode);
				info.setMobilePhone(mobilePhone);
				info.setCreateTime(new Date());
				infoPersonInfoDao.save(info);
				user.setUserid(info.getPersonId());
				user.setLoginName(perNum);
				user.setPassword(Md5Util.GetMD5Code(pwd));
				user.setPwd(Base64.getBase64Code(pwd));
				user.setEnabled(1);
				sysUserDao.save(user);
				UserGroup ug= new UserGroup();
				ug.setGroupid(groupid);
				ug.setSysusrid(user.getSysusrid());
				userGroupDao.save(ug);
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/editPersonInfo", method = RequestMethod.POST)
	public Map editPersonInfo(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		String loginName = (String) m.get("loginName");
		String password = (String) m.get("password");
		String perName = (String) m.get("perName");
		String perIdCard = (String) m.get("perIdCard");
		String genderCode = (String) m.get("genderCode");
		String mobilePhone = (String) m.get("mobilePhone");
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			InfoPersonInfo info=infoPersonInfoDao.find(userToken.getPersonId());
			SysUser user=sysUserDao.getSysUsersByUserid(userToken.getPersonId());
			info.setPerIdCard(perIdCard);
			info.setGenderCode(genderCode);
			info.setMobilePhone(mobilePhone);
			info.setModifyTime(new Date());
			info.setModifyerId(userToken.getPersonId());
			infoPersonInfoDao.update(info);
			user.setPassword(Md5Util.GetMD5Code(password));
			user.setPwd(Base64.getBase64Code(password));
			sysUserDao.update(user);
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/getPersonalPayList", method = RequestMethod.POST)
	public Map getPersonalPayList(HttpServletRequest request,
			@RequestBody Object obj) {
		Map m = (Map) obj;
		Map data = new HashMap();
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				request, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			String taskName = (String) m.get("taskName");
			String courseType = (String) m.get("courseType");
			Integer personId = userToken.getPersonId();
			List courseList = elearningPlanCourseDao
					.getTaskListByConditions(personId, taskName, courseType,null);
			if(courseList!=null){
				for (int i = 0; i < courseList.size(); i++) {
					BaseManageActionForm courseForm=new BaseManageActionForm();
					ElearningPlanCourse plan = (ElearningPlanCourse) courseList
							.get(i);
					Integer planId=plan.getId();
					ElearningPayRecord record=elearningPayRecordDao.getRecordByConditions(userToken.getPersonId(), "1", planId);
					if(record!=null && record.getAmount()>0){
						ElearningTeachTask task=plan.getElearningTeachTask();
						courseForm.setTaskId(task.getTaskId());
						courseForm.setTaskName(task.getTaskName());
						ElearningCourse course=task.getElearningCourse();
						courseForm.setCourseType(course.getCourseType());
						courseForm.setPayNumber(record.getPayNumber());
						courseForm.setPayMode(record.getPayMode());
						courseForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(record.getCreateTime()));
						courseForm.setAmount(record.getAmount());
						dataList.add(courseForm);
					}
				}
		    }
			data.put("recordList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else {
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
		}
	}
	
	@RequestMapping(value = "/manage/editTeachTaskPrice", method = RequestMethod.POST)
	public Map editTeachTaskPrice(HttpServletRequest httpRequest, @RequestBody Object obj) {
		Map m = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		List dataList = new ArrayList();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer chargeId = (Integer) m.get("chargeId");
			String price = (String) m.get("price");
			if(chargeId!=null && !chargeId.equals("")){
				ElearningTaskCharge charge=elearningTaskChargeDao.find(chargeId);
				charge.setPrice(Double.valueOf(price));
				elearningTaskChargeDao.update(charge);
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/getCourseChargeList", method = RequestMethod.POST)
	public Map getCourseChargeList(HttpServletRequest httpRequest,
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
					BaseManageActionForm planForm=new BaseManageActionForm();
					SysUser user=sysUserDao.getSysUsersByUserid(plan.getStuId());
					InfoPersonInfo info=infoPersonInfoDao.find(plan.getStuId());
					planForm.setPerTypeCode(info.getPerTypeCode());
					planForm.setLoginName(user.getLoginName());
					planForm.setPerName(info.getPerName());
					planForm.setMobilePhone(info.getMobilePhone());
					planForm.setId(plan.getId());
					planForm.setState(plan.getState());
					ElearningPayRecord record=elearningPayRecordDao.getRecordByConditions(plan.getStuId(), "1", plan.getId());
					if(record!=null && record.getAmount()>0){
						ElearningTeachTask task=plan.getElearningTeachTask();
						planForm.setTaskId(task.getTaskId());
						planForm.setTaskName(task.getTaskName());
						ElearningCourse course=task.getElearningCourse();
						planForm.setCourseType(course.getCourseType());
						planForm.setPayNumber(record.getPayNumber());
						planForm.setPayMode(record.getPayMode());
						planForm.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(record.getCreateTime()));
						planForm.setAmount(record.getAmount());
						dataList.add(planForm);
					}
				}
			}
			data.put("personList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/getPayRecordList", method = RequestMethod.POST)
	public Map getPayRecordList(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			String taskName = (String) request.get("taskName");
			String courseType = (String) request.get("courseType");
			String loginName = (String) request.get("loginName");
			String perName = (String) request.get("perName");
			List rList=elearningPayRecordDao.getRecordListByConditions(taskName, courseType, loginName, perName, "1");
			List dataList=new ArrayList();
			if(rList!=null){
				for(int i=0;i<rList.size();i++){
					ElearningPayRecord record=(ElearningPayRecord) rList.get(i);
					BaseManageActionForm form=new BaseManageActionForm();
					SysUser user=sysUserDao.getSysUsersByUserid(record.getPersonId());
					InfoPersonInfo info=infoPersonInfoDao.find(record.getPersonId());
					form.setPerTypeCode(info.getPerTypeCode());
					form.setLoginName(user.getLoginName());
					form.setPerName(info.getPerName());
					form.setMobilePhone(info.getMobilePhone());
					ElearningPlanCourse plan=elearningPlanCourseDao.find(record.getPlanId());
					ElearningTeachTask task=plan.getElearningTeachTask();
					form.setTaskId(task.getTaskId());
					form.setTaskName(task.getTaskName());
					ElearningCourse course=task.getElearningCourse();
					form.setCourseName(course.getCourseName());
					form.setCourseType(course.getCourseType());
					form.setPayNumber(record.getPayNumber());
					form.setPayMode(record.getPayMode());
					form.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(record.getCreateTime()));
					form.setAmount(record.getAmount());
					dataList.add(form);
				}
			}
			data.put("payRecordList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/getNoticeList", method = RequestMethod.GET)
	public Map getNoticeList(HttpServletRequest httpRequest) {
		UserTokenServerSide userToken = (UserTokenServerSide) httpRequest.getSession().getAttribute("userToken");
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer personId=userToken.getPersonId();
			List noticeList=elearningNoticeDao.getListByConditions(null);
			List dataList=new ArrayList();
			for(int i=0;noticeList!=null && i<noticeList.size();i++){
				ElearningNotice notice=(ElearningNotice) noticeList.get(i);
				BaseManageActionForm form=new BaseManageActionForm();
				form.setCreateTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(notice.getCreateTime()));
				form.setTitle(notice.getTitle());
				form.setContent(notice.getContent());
				form.setNoticeId(notice.getNoticeId());
				dataList.add(form);
			}
			data.put("noticeList", dataList);
			return CommonTool.getNodeMap(data, null);
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/addOrEditNotice", method = RequestMethod.POST)
	public Map addOrEditNotice(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer noticeId = (Integer) request.get("noticeId");
			String title = (String) request.get("title");
			String content = (String) request.get("content");
			if(noticeId!=null){
				ElearningNotice notice=elearningNoticeDao.find(noticeId);
				notice.setTitle(title);
				notice.setContent(content);
				notice.setPersonId(userToken.getPersonId());
				notice.setCreateTime(new Date());
				elearningNoticeDao.update(notice);
			}else{
				ElearningNotice notice=new ElearningNotice();
				notice.setTitle(title);
				notice.setContent(content);
				notice.setPersonId(userToken.getPersonId());
				notice.setCreateTime(new Date());
				elearningNoticeDao.save(notice);
			}
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	@RequestMapping(value = "/manage/deleteNotice", method = RequestMethod.POST)
	public Map deleteNotice(HttpServletRequest httpRequest,
			@RequestBody Object obj) {
		Map request = (Map) obj;
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(
				httpRequest, obj);
		Map data = new HashMap();
		if (userToken != null) {// ��¼��Ϣ��Ϊ��
			Integer noticeId = (Integer) request.get("noticeId");
			elearningNoticeDao.deleteById(noticeId);
			return CommonTool.getNodeMapOk("��ϲ���������ɹ���");
		} else
			return CommonTool.getNodeMapError("��Ǹ�������µ�¼��");
	}
	
	
	
	
}
