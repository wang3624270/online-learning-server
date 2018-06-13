package cn.edu.sdu.commoncomponent.rule;

import java.util.List;

import org.sdu.rmi.UserTokenClientSide;

public interface GetSecondTypeCodeListProcessorI {
	List getSecondTypeCodeFilteredList(UserTokenClientSide user);
}
