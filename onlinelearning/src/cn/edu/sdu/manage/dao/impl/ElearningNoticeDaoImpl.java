package cn.edu.sdu.manage.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.manage.dao.ElearningNoticeDao;
import cn.edu.sdu.manage.dao.ElearningPayRecordDao;
import cn.edu.sdu.manage.model.ElearningNotice;

@Repository
public class ElearningNoticeDaoImpl extends GenericServiceImpl<ElearningNotice>
implements ElearningNoticeDao {

	public ElearningNoticeDaoImpl(){
	super(ElearningNotice.class);
	} 
	
	@Override
	public List getListByConditions(String title){
		// TODO Auto-generated method stub
		String hql = "from ElearningNotice a where 1=1 ";
		if (title != null && !title.equals("")) {
			hql += "and a.title like '%" + title + "%'";
		}
		hql+=" order by a.createTime desc";
		List list = this.queryForList(hql);
		if (list == null || list.size() == 0)
			return null;
		else {
			return list;
		}
	}
	
}
