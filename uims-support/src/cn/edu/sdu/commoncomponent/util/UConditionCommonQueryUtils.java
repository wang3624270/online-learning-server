package cn.edu.sdu.commoncomponent.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.commoncomponent.constants.UConditionCommonQueryConstants;
import cn.edu.sdu.commoncomponent.form.QueryCell;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UConditionCommonQueryUtils {
	private static String po_infoPersonInfo="stuTrain.infoPersonInfo";
	private static String po_infoStuTrainInfo="stuTrain";
	private static String po_infoPunish="punish";
	private static String po_infoPunish_person="punishperson";
	

	
	public static List<QueryCell> getQueryCellList(String queryType){
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		List queryCellList=new ArrayList<QueryCell>();
		if(queryType == null || queryType.equals("basicInfo")){
			RmiRequest request=new RmiRequest();
			RmiResponse respond=null;
			QueryCell cell0=new QueryCell("学号",UConditionCommonQueryConstants.FIELD_PER_NUM,"String",po_infoPersonInfo);
			queryCellList.add(cell0);
			QueryCell cell1=new QueryCell("姓名",UConditionCommonQueryConstants.FIELD_PER_NAME,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			cell1=new QueryCell("证件号码",UConditionCommonQueryConstants.FIELD_PER_ID_CARD,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			List tempList=new ArrayList();
			tempList=util.getComboxListByCode("XBM");
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("性别",UConditionCommonQueryConstants.FIELD_GENDER_CODE,"String",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
		
			request.setCmd("getNationList");
		    respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				tempList=(List)respond.get(RmiKeyConstants.KEY_FORMLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取国别列表出错!");
			}
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("国别",UConditionCommonQueryConstants.FIELD_NATION_ID,"Integer",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
			request.setCmd("getPeopleList");
			respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				tempList=(List)respond.get(RmiKeyConstants.KEY_ARRAYLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取民族列表出错!");
			}
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("民族",UConditionCommonQueryConstants.FIELD_PEOPLE_ID,"Integer",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
			tempList=util.getComboxListByCode("ZZMMM");
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("政治面貌",UConditionCommonQueryConstants.FIELD_POLITICS_CODE,"String",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
		    cell1=new QueryCell("出生日期",UConditionCommonQueryConstants.FIELD_PER_BIRTHDAT,"Date",po_infoPersonInfo);
			queryCellList.add(cell1);
	
			
			tempList=new ArrayList();
			tempList.add(new ListOptionInfo("0","未婚"));
			tempList.add(new ListOptionInfo("1","已婚"));
			cell1=new QueryCell("婚否",UConditionCommonQueryConstants.FIELD_IS_MARRIED,"String",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
			request.setCmd("getProvinceList");
			respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				tempList=(List)respond.get(RmiKeyConstants.KEY_FORMLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取省列表出错!");
			}
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("籍贯(省)",UConditionCommonQueryConstants.FIELD_NATIVE_PROVINCE,"String",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
			request.setCmd("getCityList");
			respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				tempList=(List)respond.get(RmiKeyConstants.KEY_ARRAYLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取市列表出错!");
			}
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("籍贯(市)",UConditionCommonQueryConstants.FIELD_NATIVE_CITY,"String",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
		
			cell1=new QueryCell("移动电话",UConditionCommonQueryConstants.FIELD_MOBILE_PHONE,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("联系地址",UConditionCommonQueryConstants.FIELD_PER_ADDRES,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("联系邮编",UConditionCommonQueryConstants.FIELD_PER_POSTAL_CODE,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("联系电话",UConditionCommonQueryConstants.FIELD_PER_TELPHONE,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("QQ",UConditionCommonQueryConstants.FIELD_QQ,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("MSN",UConditionCommonQueryConstants.FIELD_MSN,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("EMAIL",UConditionCommonQueryConstants.FIELD_EMAIL,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
		}
		else  if(queryType.equals("cultivate") || queryType.equals("punish")){
			RmiRequest request=new RmiRequest();
			RmiResponse respond=null;
			List tempList=new ArrayList();
			QueryCell cell0=new QueryCell("学号",UConditionCommonQueryConstants.FIELD_PER_NUM,"String",po_infoPersonInfo);
			queryCellList.add(cell0);
			
			
			QueryCell cell1=new QueryCell("姓名",UConditionCommonQueryConstants.FIELD_PER_NAME,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("证件号码",UConditionCommonQueryConstants.FIELD_PER_ID_CARD,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			request.setCmd("getCollegeListByUser");
			respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				tempList=(List)respond.get(RmiKeyConstants.KEY_ARRAYLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取学院列表出错!");
			}
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("学院",UConditionCommonQueryConstants.FIELD_COLLEGE_ID,"Integer",po_infoStuTrainInfo+".infoPersonInfo.baseCollege",tempList);
			queryCellList.add(cell1);
			
			
			request.setCmd("getMajorList");
			respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				tempList=(List)respond.get(RmiKeyConstants.KEY_ARRAYLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取专业列表出错!");
			}
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("专业",UConditionCommonQueryConstants.FIELD_MAJOR_ID,"Integer",po_infoStuTrainInfo,tempList);
			queryCellList.add(cell1);
			
			
			
		    tempList=util.getComboxListByCode("XSLXM");
			if(tempList==null){
				tempList=new ArrayList();
			}
		    cell1=new QueryCell("学生类型",UConditionCommonQueryConstants.FIELD_STU_TYPE_CODE,"String",po_infoStuTrainInfo,tempList);
			queryCellList.add(cell1);
			
			
		    tempList=util.getComboxListByCode("XXFSM");
			if(tempList==null){
				tempList=new ArrayList();
			}
		    cell1=new QueryCell("学习方式",UConditionCommonQueryConstants.FIELD_STUDY_FORM_CODE,"String",po_infoStuTrainInfo,tempList);
			queryCellList.add(cell1);
			
			
			tempList=util.getComboxListByCode("LQLBM");
			if(tempList==null){
				tempList=new ArrayList();
			}
		    cell1=new QueryCell("录取类别",UConditionCommonQueryConstants.FIELD_TRAIN_FORM_CODE,"String",po_infoStuTrainInfo,tempList);
			queryCellList.add(cell1);
			

			cell1=new QueryCell("委培单位",UConditionCommonQueryConstants.FIELD_CONSIGN_UNIT,"String",po_infoStuTrainInfo);
			queryCellList.add(cell1);
			
			
			tempList=util.getComboxListByCode("XLM");
			if(tempList==null){
				tempList=new ArrayList();
			}
		    cell1=new QueryCell("学历",UConditionCommonQueryConstants.FIELD_STUDY_LEVEL_CODE,"String",po_infoStuTrainInfo,tempList);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("专业方向",UConditionCommonQueryConstants.FIELD_SPECIAL_WAY,"String",po_infoStuTrainInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("准考证号",UConditionCommonQueryConstants.FIELD_EXAM_NO,"String",po_infoStuTrainInfo);
			queryCellList.add(cell1);
			
			tempList=util.getComboxListByCode("XBM");
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("性别",UConditionCommonQueryConstants.FIELD_GENDER_CODE,"String",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
		
			request.setCmd("getNationList");
		    respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				tempList=(List)respond.get(RmiKeyConstants.KEY_ARRAYLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取国别列表出错!");
			}
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("国别",UConditionCommonQueryConstants.FIELD_NATION_ID,"Integer",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
			request.setCmd("getPeopleList");
			respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				tempList=(List)respond.get(RmiKeyConstants.KEY_ARRAYLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取民族列表出错!");
			}
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("民族",UConditionCommonQueryConstants.FIELD_PEOPLE_ID,"Integer",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
			tempList=util.getComboxListByCode("ZZMMM");
			if(tempList==null){
				tempList=new ArrayList();
			}
			cell1=new QueryCell("政治面貌",UConditionCommonQueryConstants.FIELD_POLITICS_CODE,"String",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
		    cell1=new QueryCell("出生日期",UConditionCommonQueryConstants.FIELD_PER_BIRTHDAT,"Date",po_infoPersonInfo);
			queryCellList.add(cell1);

			
			tempList=new ArrayList();
			tempList.add(new ListOptionInfo("0","未婚"));
			tempList.add(new ListOptionInfo("1","已婚"));
			cell1=new QueryCell("婚否",UConditionCommonQueryConstants.FIELD_IS_MARRIED,"String",po_infoPersonInfo,tempList);
			queryCellList.add(cell1);
			
			
			List provinceList=new ArrayList();
			request.setCmd("getProvinceList");
			respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				provinceList=(List)respond.get(RmiKeyConstants.KEY_FORMLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取省列表出错!");
			}
			if(provinceList==null){
				provinceList=new ArrayList();
			}
			cell1=new QueryCell("籍贯(省)",UConditionCommonQueryConstants.FIELD_NATIVE_PROVINCE,"String",po_infoPersonInfo,provinceList);
			queryCellList.add(cell1);
			
			
			List cityList=new ArrayList();
			request.setCmd("getCityList");
			respond=UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg()==null){
				cityList=(List)respond.get(RmiKeyConstants.KEY_ARRAYLIST);
			}else{
				JOptionPane.showMessageDialog(null, "获取市列表出错!");
			}
			if(cityList==null){
				cityList=new ArrayList();
			}
			cell1=new QueryCell("籍贯(市)",UConditionCommonQueryConstants.FIELD_NATIVE_CITY,"String",po_infoPersonInfo,cityList);
			queryCellList.add(cell1);
			
			cell1=new QueryCell("家庭住址(省)",UConditionCommonQueryConstants.FIELD_FAMILY_PROVINCE,"String",po_infoPersonInfo,provinceList);
			queryCellList.add(cell1);
			cell1=new QueryCell("家庭住址(市)",UConditionCommonQueryConstants.FIELD_FAMILY_CITY,"String",po_infoPersonInfo,cityList);
			queryCellList.add(cell1);
			cell1=new QueryCell("工作单位(省)",UConditionCommonQueryConstants.FIELD_UNIT_PROVINCE,"String",po_infoPersonInfo,provinceList);
			queryCellList.add(cell1);
			cell1=new QueryCell("工作单位(市)",UConditionCommonQueryConstants.FIELD_UNIT_CITY,"String",po_infoPersonInfo,cityList);
			queryCellList.add(cell1);
			
			tempList=new ArrayList();
			tempList.add(new ListOptionInfo("0","否"));
			tempList.add(new ListOptionInfo("1","是"));
			cell1=new QueryCell("是否华侨",UConditionCommonQueryConstants.FIELD_IS_OVERSEA,"String",po_infoStuTrainInfo,tempList);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("是否英烈子女",UConditionCommonQueryConstants.FIELD_IS_HERO_CHILD,"String",po_infoStuTrainInfo,tempList);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("入学日期",UConditionCommonQueryConstants.FIELD_ENTR_YEAR_MON,"Date",po_infoStuTrainInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("校园卡号",UConditionCommonQueryConstants.FIELD_CAMPUS_CARD_ID,"String",po_infoStuTrainInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("移动电话",UConditionCommonQueryConstants.FIELD_MOBILE_PHONE,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("联系地址",UConditionCommonQueryConstants.FIELD_PER_ADDRES,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("联系邮编",UConditionCommonQueryConstants.FIELD_PER_POSTAL_CODE,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("联系电话",UConditionCommonQueryConstants.FIELD_PER_TELPHONE,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("QQ",UConditionCommonQueryConstants.FIELD_QQ,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("MSN",UConditionCommonQueryConstants.FIELD_MSN,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			
			cell1=new QueryCell("EMAIL",UConditionCommonQueryConstants.FIELD_EMAIL,"String",po_infoPersonInfo);
			queryCellList.add(cell1);
			
			if(queryType.equals("punish")) {
				tempList=util.getComboxListByCode("CFDJM");
				if(tempList==null)
					tempList=new ArrayList();
				cell1=new QueryCell("惩罚等级",UConditionCommonQueryConstants.FIELD_PUNISH_GRADE,"String",po_infoPunish_person,tempList);
				queryCellList.add(0,cell1);	
			}
		}
		return queryCellList;
	}
	
}
