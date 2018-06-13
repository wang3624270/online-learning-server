package cn.edu.sdu.commoncomponent.rule;

import java.util.List;

import org.sdu.rmi.UserTokenClientSide;

public interface GetYearListProcessorI {
	public List getYearFilteredList(UserTokenClientSide userInfo);

}
