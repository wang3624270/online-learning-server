package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.ElearningTermDao;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.ElearningCourse;
import cn.edu.sdu.course.model.ElearningTerm;

@Repository
public class ElearningTermDaoImpl extends GenericServiceImpl<ElearningTerm>
implements ElearningTermDao {

	public ElearningTermDaoImpl(){
	super(ElearningTerm.class);
	}

	@Override
	public List getAllTermList() {
		// TODO Auto-generated method stub
		String hql = "from ElearningTerm a where 1 = 1";
		hql+="order by startTime desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}

	@Override
	public ElearningTerm getTermInfoById(Integer termId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningTerm a where 1 = 1";
		if(termId!=null && !termId.equals("")){
			hql+="and a.termId='"+termId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningTerm) list.get(0);
		}
	}




}
