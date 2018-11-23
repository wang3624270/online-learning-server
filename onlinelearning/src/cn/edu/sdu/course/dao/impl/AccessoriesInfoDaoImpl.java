package cn.edu.sdu.course.dao.impl;

import java.util.Date;
import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.course.dao.AccessoriesFolderAccDao;
import cn.edu.sdu.course.dao.AccessoriesInfoDao;
import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.course.model.AccessoriesFolderAcc;
import cn.edu.sdu.course.model.AccessoriesInfo;

@Repository
public class AccessoriesInfoDaoImpl extends GenericServiceImpl<AccessoriesInfo>
		implements AccessoriesInfoDao {

	public AccessoriesInfoDaoImpl(){
		super(AccessoriesInfo.class);
	}
	
	@Override
	public AccessoriesInfo getAccById(Integer id) {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesInfo a where 1 = 1 ";
		if(id!=null && !id.equals("")){
			hql+="and a.id='"+id+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return (AccessoriesInfo) list.get(0);
		}
	}
	
	@Override
	public List getAllResourceList() {
		// TODO Auto-generated method stub
		String hql = "from AccessoriesInfo a where 1 = 1 ";
		hql+="order by a.uploadTime desc";
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
	@Override
	public List getResourceListByConditions(String accName,String accType,String uploader){
		// TODO Auto-generated method stub
		String hql = "select distinct a from AccessoriesInfo a,InfoPersonInfo i where 1 = 1 ";
		if(accName!=null && !accName.equals("")){
			hql+="and a.accName like'%"+accName+"%'";
		}
		if(accType!=null && !accType.equals("")){
			hql+="and a.accType='"+accType+"'";
		}
		if(uploader!=null && !uploader.equals("")){
			hql+="and a.accUploader=i.personId and i.perName='"+uploader+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}




}