package cn.edu.sdu.course.dao.impl;

import java.util.Date;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;











import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;

@Repository
public class AccessoriesFolderAccDaoImpl extends GenericServiceImpl<AccessoriesFolderAcc>
		implements AccessoriesFolderAccDao {

	public AccessoriesFolderAccDaoImpl(){
		super(AccessoriesFolderAcc.class);
	}

	@Override
	public AccessoriesFolderAcc getFolferAccByConditions(Integer folderId,String type){
		// TODO Auto-generated method stub
		String hql = "from AccessoriesFolderAcc a where 1 = 1 ";
		if(folderId!=null && !folderId.equals("")){
			hql+="and a.folderId='"+folderId+"'";
		}
		if(type!=null && !type.equals("")){
			hql+="and a.type='"+type+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (AccessoriesFolderAcc) list.get(0);
		}
	}
	
	@Override
	public AccessoriesFolderAcc getFolderByAccId(Integer accId) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesFolderAcc a where 1=1";
		if(accId!=null && !accId.equals("")){
			hql+="and a.accId='"+accId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (AccessoriesFolderAcc) list.get(0);
		}
	}
	
	@Override
	public List getFolderListByAccId(Integer accId) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesFolderAcc a where 1=1";
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
	public List getAccListByFolderId(Integer folderId) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesFolderAcc a where 1=1";
		if(folderId!=null && !folderId.equals("")){
			hql+="and a.folderId='"+folderId+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public AccessoriesFolderAcc getFolferAccByCondition(Integer folderId,Integer accId,String type){
		// TODO Auto-generated method stub
		String hql = "from AccessoriesFolderAcc a where 1 = 1 ";
		if(folderId!=null && !folderId.equals("")){
			hql+="and a.folderId='"+folderId+"'";
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
			return (AccessoriesFolderAcc) list.get(0);
		}
	}


}

