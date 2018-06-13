package org.octopus.spring_utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.starfish.web_security.WebRequestThreadVars;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ViewScope implements Scope {
	private static Logger logger = Logger.getLogger(ViewScope.class);

	public Object get(String name, ObjectFactory objectFactory) {
		Map<String, Object> viewMap = null;
		HttpServletRequest request = WebRequestThreadVars.currentRequest.get();
		HttpSession se = request.getSession();
		ViewScopeInfo viewScopeInfo = null;
		if (se.getAttribute("viewScopeInfo") == null) {
			viewScopeInfo = new ViewScopeInfo();
			se.setAttribute("viewScopeInfo", viewScopeInfo);
		} else
			viewScopeInfo = (ViewScopeInfo) se.getAttribute("viewScopeInfo");
		if (request.getContentType() != null && request.getContentType().indexOf("application/json") >= 0
				&& request.getMethod().equals("POST")) {
			StringBuffer jb = new StringBuffer();
			String line = null;
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null)
					jb.append(line);
			} catch (Exception e) {
				logger.error(null, e);
				return null;
			}

			ObjectMapper mapper = new ObjectMapper();
			try {
				HashMap map1 = mapper.readValue(jb.toString(), HashMap.class);
				String viewId = (String) map1.get("octopusViewId");
				HashMap<String, Object> map = viewScopeInfo.checkViewSeq(viewId);
				WebRequestThreadVars.setCurrentJsonPostInfo(jb);
				if (map.get(name) == null)
					map.put(name, objectFactory.getObject());
				return map.get(name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(null, e);
				return null;
			}

		} else {
			String viewId = request.getParameter("octopusViewId");
			if (viewId == null||viewId.length()==0) {
				viewId = UUID.randomUUID().toString();
				request.setAttribute("tempViewId", viewId);

			}

			HashMap<String, Object> map = viewScopeInfo.checkViewSeq(viewId);
			if (map.get(name) == null)
				map.put(name, objectFactory.getObject());
			return map.get(name);
		}

	}

	public Object remove(String name) {
		return null;
		// return
		// FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
	}

	public String getConversationId() {
		return null;
	}

	public void registerDestructionCallback(String name, Runnable callback) {
		// Not supported
	}

	public Object resolveContextualObject(String key) {
		return null;
	}
}