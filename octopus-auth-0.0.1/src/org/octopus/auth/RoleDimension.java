package org.octopus.auth;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.octopus.auth.jpa_dao.SysUserDao;
import org.sdu.rmi.RmiSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.starfish.DimensionHandlerI;
import org.starfish.constants.StarfishConstants;
import org.starfish.login_users.UserTokenServerSide;
import org.starfish.permi_dimension.PermissionDim;
import org.starfish.sf_auth.model.SFIdentity;
import org.starfish.utils.SFModelUtils;

@Component
public class RoleDimension implements DimensionHandlerI {
	@Autowired
	private SysUserDao authUsersDao;

	public RoleDimension() {

	}

	public void init() {

	}

	// Note: Dimension class can be wrote to a common class, use Role Dimension
	// temporary.
	@Override
	public PermissionDim getUserDimensionPermissionMap(
			PermissionDim permissionDim, HttpSession session) {
 
		UserTokenServerSide ss = (UserTokenServerSide) session
				.getAttribute(StarfishConstants.userToken);
		if (ss == null)
			return null;
		String au = authUsersDao.getAuthUsersByLoginName(ss.getLoginName())
				.getAuthority();

		SFIdentity sf = new SFIdentity(null);
		sf.currentElement = SFModelUtils.getRootElement(au);
		sf.parse();
		ss.setSfIdentity(sf);
		List<String> d = sf.getDimPositions(permissionDim.getDimensionName());
		int i;
		for (i = 0; i < d.size(); i++) {
			permissionDim.addDimensionItem(d.get(i));
		}
		return permissionDim;
	}

	@Override
	public PermissionDim getUserDimensionPermissionMap(
			PermissionDim permissionDim, RmiSession session) {
	 
		UserTokenServerSide ss = (UserTokenServerSide) session
				.getAttribute(StarfishConstants.userToken);
		if (ss == null)
			return null;
		String au = authUsersDao.getAuthUsersByLoginName(ss.getLoginName())
				.getAuthority();

		SFIdentity sf = new SFIdentity(null);
		sf.currentElement = SFModelUtils.getRootElement(au);
		sf.parse();
		ss.setSfIdentity(sf);
		List<String> d = sf.getDimPositions(permissionDim.getDimensionName());
		int i;
		for (i = 0; i < d.size(); i++) {
			permissionDim.addDimensionItem(d.get(i));
		}
		return permissionDim;
	}

	public List<String> getAllItemsNameInDimension() {

		return null;
	}

	@Override
	public PermissionDim getUserDimensionPermissionMap(
			PermissionDim permissionDim, Session session) {

		return permissionDim;
	}

	public List<Object> getAllItemsEntityInDimension() {
		// TODO Auto-generated method stub
		return null;
	}
}