package org.octopus.common_business.help.dao;

import java.util.HashMap;
import java.util.List;

import org.octopus.common_business.help.model.UserHelpInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;

public interface UserHelpInfoDao extends GenericServiceI<UserHelpInfo> {
	List findAll();
	HashMap<String, UserHelpInfo> getUserHelpInfoMap();
}
