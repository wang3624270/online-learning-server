package org.octopus.dim_manage.dim_org.sdjpa_dao;

import java.util.List;

import org.octopus.dim_manage.dim_org.jpa_model.DimOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DimOrgJpaDao extends JpaRepository<DimOrg, Integer> {

	public DimOrg findByCode(String code);

	public DimOrg findByLogicId(String logicId);

	public void deleteByLogicId(String logicId);

	public void deleteByBelongOrgLogicId(String logicId);

	public List<DimOrg> findByBelongOrgLogicId(String logicId);
}
