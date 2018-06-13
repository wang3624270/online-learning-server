package cn.edu.sdu.commoncomponent.rule;

import java.util.List;

import org.sdu.rmi.UserTokenClientSide;

public interface GetStatusListProcessorI {
	public List getStatusFilteredList(UserTokenClientSide userInfo);

}
