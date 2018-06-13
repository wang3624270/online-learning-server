package cn.edu.sdu.commoncomponent.rule;

import java.util.List;

import org.sdu.rmi.UserTokenClientSide;

public interface GetLoanStatusListProcessorI {
	public List getLoanStatusFilteredList(UserTokenClientSide userInfo);

	public List getLoanStatusNameFilteredList(UserTokenClientSide userInfo);

}
