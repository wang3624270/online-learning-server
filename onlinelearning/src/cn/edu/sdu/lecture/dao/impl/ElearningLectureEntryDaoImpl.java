package cn.edu.sdu.lecture.dao.impl;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

import cn.edu.sdu.lecture.dao.ElearningLectureEntryDao;
import cn.edu.sdu.lecture.model.ElearningLectureEntry;

@Repository
public class ElearningLectureEntryDaoImpl extends GenericServiceImpl<ElearningLectureEntry>
implements ElearningLectureEntryDao {

	public ElearningLectureEntryDaoImpl(){
		super(ElearningLectureEntry.class);
		}
	
	@Override
	public List getEntryListByConditions(Integer lectureId,Integer personId) {
		// TODO Auto-generated method stub
		String hql = "from ElearningLectureEntry a where 1=1";
		if(lectureId!=null && !lectureId.equals("")){
			hql+="and a.lectureId="+lectureId;
		}
		if(personId!=null && !personId.equals("")){
			hql+="and a.personId="+personId;
		}
		hql+=" order by a.createTime desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public List getEntriedListByConditions(String theme, String lectureType,String state,Integer personId) {
		// TODO Auto-generated method stub
		String hql = "select distinct e from ElearningLecture a , ElearningLectureEntry e where a.lectureId=e.lectureId";
		if(theme!=null && !theme.equals("")){
			hql+=" and a.theme like '%"+theme+"%'";
		}
		if(lectureType!=null && !lectureType.equals("")){
			hql+=" and a.lectureType='"+lectureType+"'";
		}
		if(state!=null && !state.equals("")){
			hql+=" and e.state='"+state+"'";
		}
		if(personId!=null && !personId.equals("")){
			hql+=" and e.personId='"+personId+"'";
		}
		hql+=" order by e.createTime desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}

}
