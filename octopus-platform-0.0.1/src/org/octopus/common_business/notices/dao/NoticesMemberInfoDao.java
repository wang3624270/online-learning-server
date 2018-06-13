package org.octopus.common_business.notices.dao;

import java.util.List;

import org.octopus.common_business.notices.model.NoticesMemberInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;

public interface NoticesMemberInfoDao extends GenericServiceI<NoticesMemberInfo> {
	public List<NoticesMemberInfo>  getNoticesMemberInfo(Integer noticeId);
}
