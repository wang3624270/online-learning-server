package org.octopus.auth.jpa_dao.impl;


import java.util.List;
import org.octopus.auth.jpa_dao.SysFunResDao;
import org.octopus.auth.jpa_model.SysFunRes;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;

@Repository
public class SysFunResDaoImpl extends GenericServiceImpl<SysFunRes>
implements SysFunResDao {

	public SysFunResDaoImpl(){
	super(SysFunRes.class);
	}
	
	@Override
	public List getMenuListByCondition(String name,String path) {
		// TODO Auto-generated method stub
		String hql = "from SysFunRes a where 1 = 1 ";
		if(name!=null && !name.equals("")){
			hql+="and a.name='"+name+"'";
		}
		if(path!=null && !path.equals("")){
			hql+="and a.path='"+path+"'";
		}
		List list = this.queryForList(hql);
		if(list == null || list.size()== 0)
			return null;
		else{
			return list;
		}
	}
	
}
