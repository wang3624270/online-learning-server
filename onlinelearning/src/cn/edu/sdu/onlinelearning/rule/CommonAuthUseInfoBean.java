package cn.edu.sdu.onlinelearning.rule;

import java.util.Map;

import org.octopus.auth.client_form.WebLoginForm;
import org.octopus.auth.restapi.CommonAuthUseInfoI;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.UserTokenClientSide;
import org.springframework.stereotype.Service;
import org.starfish.login_users.UserTokenServerSide;

@Service
public class CommonAuthUseInfoBean implements CommonAuthUseInfoI{

	@Override
	public void addUserData(WebLoginForm loginForm, UserTokenServerSide ss, UserTokenClientSide cs) {
		// TODO Auto-generated method stub
		Map parameter = loginForm.getParameter();
		String appVersion = null;
		if(parameter != null)
			appVersion = (String)parameter.get("appVersion");
		Integer personId = ss.getPersonId();
//		infoPersonInfoAuxiliaryDao.updateUserLoginInfo(personId, loginForm.getLoginType(), appVersion);
	}

	@Override
	public void addUserDataCs(RmiRequest request, UserTokenServerSide ss, UserTokenClientSide cs) {
		// TODO Auto-generated method stub
		String clientVersion = (String) request.get("clientVersion");
		Integer personId = ss.getPersonId();
//		infoPersonInfoAuxiliaryDao.updateUserLoginInfo(personId,"4", clientVersion);
	}


}
