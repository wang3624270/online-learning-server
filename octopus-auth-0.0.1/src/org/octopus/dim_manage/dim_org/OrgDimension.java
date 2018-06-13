package org.octopus.dim_manage.dim_org;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.octopus.dim_manage.dim_org.jpa_model.DimOrg;
import org.octopus.dim_manage.dim_org.sdjpa_dao.DimOrgJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.starfish.DimensionHandlerI;

import org.starfish.permi_dimension.PermissionDim;

@Component
@Scope("application")
public class OrgDimension implements DimensionHandlerI {
	@Autowired
	private DimOrgJpaDao dimOrgJpaDao;

	public OrgDimension() {

	}

	public void init() {

	}

	// Note: Dimension class can be wrote to a common class, use Role Dimension
	// temporary.

	public PermissionDim getUserDimensionPermissionMap(PermissionDim permissionDim, HttpSession session) {

		// UserTokenServerSide ss = (UserTokenServerSide)
		// session.getAttribute(StarfishConstants.userToken);
		// if (ss == null)
		// return null;
		// String au =
		// authAuthoritiesJpaDao.findOne(ss.getLoginName()).getAuthority();
		//
		// SFIdentity sf = new SFIdentity(null);
		// sf.currentElement = SFModelUtils.getRootElement(au);
		// sf.parse();
		// ss.setSfIdentity(sf);
		// List<String> d =
		// sf.getDimPositions(permissionDim.getDimensionName());
		// int i;
		// for (i = 0; i < d.size(); i++) {
		// permissionDim.addDimensionItem(d.get(i));
		// }
		// return permissionDim;
		return null;
	}

	public List<String> getAllItemsNameInDimension() {

		return null;
	}

	public PermissionDim getUserDimensionPermissionMap(PermissionDim permissionDim, Session session) {

		return permissionDim;
	}

	public List<DimOrg> getAllItemsEntityInDimension() {
		List list = dimOrgJpaDao.findAll();
		return list;
	}

	public DimOrg findOrgByCode(String code) {
		return dimOrgJpaDao.findByCode(code);
	}

	public DimOrg findOrgByLogicId(String logicId) {
		return dimOrgJpaDao.findByLogicId(logicId);
	}

	public void createDimOrg(DimOrg org) {
		dimOrgJpaDao.save(org);
	}

	public void updateDimOrg(DimOrg org) {
		dimOrgJpaDao.save(org);
	}

	public void createOrg() {
	}

	@Transactional
	public void deleteOrg(String[] logicIds) {
		int i;
		for (i = 0; i < logicIds.length; i++) {
			dimOrgJpaDao.deleteByLogicId(logicIds[i]);
			dimOrgJpaDao.deleteByBelongOrgLogicId(logicIds[i]);
		}

	}

	public DimOrg getOrg(String logicId) {
		return dimOrgJpaDao.findByLogicId(logicId);
	}

	public List<DimOrg> getChildrenOrg(String logicId) {

		return dimOrgJpaDao.findByBelongOrgLogicId(logicId);
	}
}