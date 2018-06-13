package cn.edu.sdu.course.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.BaseCollegeDao;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.BaseCollege;

@Repository
public class BaseCollegeDaoImpl extends GenericServiceImpl<BaseCollege>
implements BaseCollegeDao{

	public BaseCollegeDaoImpl(){
		super(BaseCollege.class);
	}

	@Override
	public String getCollegeNameById(Integer collegeId) {
		// TODO Auto-generated method stub
		String hql = "select a.collegeName from BaseCollege a where 1 = 1 ";
		if(collegeId!=null && !collegeId.equals("")){
			hql+="and a.collegeId='"+collegeId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (String) list.get(0);
		}
	}
	
	public List getAllCollegeList() {
		// TODO Auto-generated method stub
		String hql = "from BaseCollege b where 1 = 1 ";
		hql+="order by b.collegeId";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
}
