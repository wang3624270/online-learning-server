package cn.edu.sdu.course.dao.impl;

import java.util.List;







import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.course.dao.ElearningCourseSectionDao;
import cn.edu.sdu.course.dao.ElearningSectionAccDao;
import cn.edu.sdu.course.model.ElearningCourseSection;
import cn.edu.sdu.course.model.ElearningSectionAcc;

@Repository
public class ElearningSectionAccDaoImpl extends GenericServiceImpl<ElearningSectionAcc>
implements ElearningSectionAccDao{
	public ElearningSectionAccDaoImpl(){
		super(ElearningSectionAcc.class);
	}
	
	@Override
	public List getAllListBySectionId(Integer sectionId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningSectionAcc a where 1=1 ";
		if(sectionId!=null && !sectionId.equals("")){
			hql+="and a.sectionId='"+sectionId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public List getListByAccId(Integer accId){
		// TODO Auto-generated method stub
		String hql = "from ElearningSectionAcc a where 1=1 ";
		if(accId!=null && !accId.equals("")){
			hql+="and a.accId='"+accId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public ElearningSectionAcc getMapById(Integer id) {
		// TODO Auto-generated method stub
		String hql = "from ElearningSectionAcc a where 1=1 ";
		if(id!=null && !id.equals("")){
			hql+="and a.id='"+id+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningSectionAcc) list.get(0);
		}
	}
	
	@Override
	public ElearningSectionAcc getMapBySectionIdAndAccId(Integer sectionId,Integer accId,String type) {
		// TODO Auto-generated method stub
		String hql = "from ElearningSectionAcc a where 1=1 ";
		if(sectionId!=null && !sectionId.equals("")){
			hql+="and a.sectionId='"+sectionId+"'";
		}
		if(accId!=null && !accId.equals("")){
			hql+="and a.accId='"+accId+"'";
		}
		if(type!=null && !type.equals("")){
			hql+="and a.type='"+type+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (ElearningSectionAcc) list.get(0);
		}
	}
	@Override
	public Integer getAccIdBySectionId(Integer sectionId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningSectionAcc a where 1=1 ";
		hql+="and a.sectionId="+sectionId+"";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			ElearningSectionAcc elearningSectionAcc=(ElearningSectionAcc) list.get(0);
			return elearningSectionAcc.getAccId();
		}
	}
}
