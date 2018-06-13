package org.octopus.auth.restapi;

import org.octopus.auth.client_form.WebLoginForm;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.UserTokenClientSide;
import org.starfish.login_users.UserTokenServerSide;

public interface CommonAuthUseInfoI {
	public void addUserData(WebLoginForm loginForm, UserTokenServerSide ss, UserTokenClientSide cs);
	public void addUserDataCs(RmiRequest request, UserTokenServerSide ss, UserTokenClientSide cs);
	

}
