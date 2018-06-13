package org.starfish.configure_model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.octopus.spring_utils.SpringContextHelper;
import org.sdu.rmi.RmiSession;
import org.starfish.constants.StarfishConstants;
import org.starfish.permi_dimension.PermissionDim;

public class Dimension extends SFModel {
	private String name;
	private String handler;

	public Dimension(SFModel parent) {
		super(parent);
	}

	public void parse() {
		name = currentElement.attributeValue("name");
		handler = currentElement.attributeValue("handler");

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public void walkUserPermissionDims(RmiSession session) {
		PermissionDim permissionDim = (PermissionDim) session
				.getAttribute(StarfishConstants.userPermission);
		Object ob = SpringContextHelper.getBean(handler);
		try {
			Method method = ob.getClass().getMethod(
					"getUserDimensionPermissionMap", PermissionDim.class,
					RmiSession.class);
			PermissionDim dim = new PermissionDim();
			dim.setDimensionName(name);
			dim = (PermissionDim) method.invoke(ob, dim, session);

			permissionDim.addChildDimension(dim);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void walkUserPermissionDims(HttpSession session) {

		PermissionDim permissionDim = (PermissionDim) session
				.getAttribute(StarfishConstants.userPermission);
		Object ob = SpringContextHelper.getBean(handler);
		try {
			Method method = ob.getClass().getMethod(
					"getUserDimensionPermissionMap", PermissionDim.class,
					HttpSession.class);
			PermissionDim dim = new PermissionDim();
			dim.setDimensionName(name);
			dim = (PermissionDim) method.invoke(ob, dim, session);

			permissionDim.addChildDimension(dim);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void walkUserPermissionDims(Session session) {

		PermissionDim permissionDim = (PermissionDim) session
				.getUserProperties().get(StarfishConstants.userPermission);
		Object ob = SpringContextHelper.getBean(handler);
		try {
			Method method = ob.getClass().getMethod(
					"getUserDimensionPermissionMap", Session.class);
			PermissionDim dim = new PermissionDim();
			dim.setDimensionName(name);
			dim = (PermissionDim) method.invoke(ob, dim, session);
			permissionDim.addChildDimension(dim);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<String> getAllItemsInDimension() {

		Object ob = SpringContextHelper.getBean(handler);
		try {
			Method method = ob.getClass().getMethod("getAllItemsInDimension");
			List list = (ArrayList<String>) method.invoke(ob);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}