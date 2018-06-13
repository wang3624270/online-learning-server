package org.octopus.common_business.globalinfo.dao.impl;

import java.util.List;

import org.octopus.common_business.globalinfo.dao.SystemGlobalVariableDao;
import org.octopus.common_business.globalinfo.model.SystemGlobalVariable;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SystemGlobalVariableDaoImpl extends
GenericServiceImpl<SystemGlobalVariable> implements
		SystemGlobalVariableDao {

	public SystemGlobalVariableDaoImpl(){
		super(SystemGlobalVariable.class);
	}

	@Override
	public SystemGlobalVariable findSystemGlobalVariableById(Integer variableId) {
		// TODO Auto-generated method stub
		String sql = "from SystemGlobalVariable where variableId = " + variableId;
		List list = this.queryForList(sql, null);
		if(list == null || list.size() == 0)
			return null;
		else
			return (SystemGlobalVariable)list.get(0);
	}

	@Transactional
	public Integer savePo(SystemGlobalVariable po) {
		// TODO Auto-generated method stub
		if(po.getVariableId() == null)
			this.create(po);
		else
			this.update(po);
		return null;
	}

	@Override
	public SystemGlobalVariable findSystemGlobalVariableByVariableName(
			String variableName) {
		// TODO Auto-generated method stub
		String sql = "from SystemGlobalVariable where variableName = '" + variableName + "'";
		List list = this.queryForList(sql, null);
		if(list == null || list.size() == 0)
			return null;
		else
			return (SystemGlobalVariable)list.get(0);
	}

	@Override
	public List<SystemGlobalVariable> findAll() {
		// TODO Auto-generated method stub
		String sql = "from SystemGlobalVariable";
		return  this.queryForList(sql, null);
	}

}
