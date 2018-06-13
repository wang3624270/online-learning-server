package org.octopus.common_business.notices.dao.Impl;

import java.util.List;

import org.octopus.common_business.news.model.NewsInfo;
import org.octopus.common_business.notices.dao.NoticesMemberInfoDao;
import org.octopus.common_business.notices.model.NoticesMemberInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class NoticesMemberInfoDaoImpl extends GenericServiceImpl<NoticesMemberInfo> implements NoticesMemberInfoDao {

	public NoticesMemberInfoDaoImpl() {
		super(NoticesMemberInfo.class);
	}

	
	@Override
	public List<NoticesMemberInfo> getNoticesMemberInfo(Integer noticeId){
		String hql = "from NewsInfo where isVisable = 1";
		if(noticeId != null){
			hql += "and noticeId = "+"'"+noticeId+"'";
		}
		List<NoticesMemberInfo> list = (List<NoticesMemberInfo>)this.queryForList(hql);
		if(list!=null&list.size()!=0){
			return list;
		}else{
			return null;
		}
	}
}
