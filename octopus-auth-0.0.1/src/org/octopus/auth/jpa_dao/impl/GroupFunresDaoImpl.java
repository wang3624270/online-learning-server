package org.octopus.auth.jpa_dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.octopus.auth.jpa_dao.GroupFunresDao;
import org.octopus.auth.jpa_model.GroupFunres;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;




@Repository
public class GroupFunresDaoImpl extends GenericServiceImpl<GroupFunres> implements GroupFunresDao{

	public GroupFunresDaoImpl(){
	    super(GroupFunres.class);
	}
	
	@Override
	public List getIdListByGroupId(Integer groupId) {
		// TODO Auto-generated method stub
		String sql = "select g.resid from GroupFunres g where groupid = '" + groupId + "'";
		List list = this.queryForList(sql);
		List idList=new ArrayList();
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				idList.add(list.get(i));
			}
			return idList;
		}
		return null;
	}
	
	@Override
	public List getListByResId(Integer resid) {
		// TODO Auto-generated method stub
		String sql = "select g.resid from GroupFunres g where resid = '" + resid + "'";
		List list = this.queryForList(sql);
		List idList=new ArrayList();
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				idList.add(list.get(i));
			}
			return idList;
		}
		return null;
	}
	
	@Override
	public List getListByConditions(Integer groupId,Integer resid) {
		// TODO Auto-generated method stub
		String sql = "from GroupFunres a where 1=1";
		if(groupId!=null && !groupId.equals("")){
			sql+="and a.groupid='"+groupId+"'";
		}
		if(resid!=null && !resid.equals("")){
			sql+="and a.resid='"+resid+"'";
		}
		List list = this.queryForList(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	
}

