package cn.edu.sdu.commoncomponent.rule;

import java.util.List;

import org.sdu.rmi.UserTokenClientSide;

public interface GetCommBaseDataListProcessorI extends GetStatusListProcessorI,
		GetBaseClassListProcessorI {
	public List getMajorFilteredList(UserTokenClientSide userInfo);
	public List getCollegeFilteredList(UserTokenClientSide userInfo);
	public List getStuTypeCodeFilteredList(UserTokenClientSide userInfo);
	public List getStuGradeFilteredList(UserTokenClientSide userInfo);
}
