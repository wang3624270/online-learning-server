package org.starfish.login_users;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.octopus.auth.restapi.MySessionContext;
import org.starfish.constants.StarfishConstants;

public class CommonAuthUseInfoTool {

	public static UserTokenServerSide checkUser(HttpServletRequest request, Object obj) {
		// TODO Auto-generated method stub
//		System.out.println(request.getSession().get Id());

		String sessionId="";
		if(obj==null&&!(obj instanceof Map)){
			sessionId = request.getParameter("sessionId");
		}else{
				HashMap map = (HashMap)obj;
				sessionId = (String)map.get("sessionId");
		
		}
		UserTokenServerSide userToken = new UserTokenServerSide();
		if(sessionId == null||sessionId == ""||sessionId.length()==0){
			userToken = (UserTokenServerSide) request.getSession().getAttribute("userToken");
		}else{
			HttpSession session = MySessionContext.getSession(sessionId);//微信通过上次会话的sessionID获取session
			if(session==null){
				userToken = null;
			}else{
				userToken = (UserTokenServerSide) session.getAttribute("userToken");
				session.setAttribute(StarfishConstants.userToken,userToken);//刷新session
			}
		}
		return userToken;	
	}
	
	
	
	
	
	
	public static Integer getPersonId(HttpServletRequest request, Object obj){
		UserTokenServerSide userToken = CommonAuthUseInfoTool.checkUser(request, obj);
		if(userToken == null)
			return null;
		return (Integer) userToken.getPersonId();
	}

}
