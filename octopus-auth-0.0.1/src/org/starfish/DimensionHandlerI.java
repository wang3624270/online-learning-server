package org.starfish;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.sdu.rmi.RmiSession;
import org.starfish.permi_dimension.PermissionDim;

public interface DimensionHandlerI {

	public PermissionDim getUserDimensionPermissionMap(
			PermissionDim permissionDim, HttpSession session);

	public PermissionDim getUserDimensionPermissionMap(
			PermissionDim permissionDim, Session session);

	public default PermissionDim getUserDimensionPermissionMap(
			PermissionDim permissionDim, RmiSession session) {
		return permissionDim;
	}

	public List<String> getAllItemsNameInDimension();

	// public List<Object> getAllItemsEntityInDimension();
}