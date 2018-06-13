package org.starfish.login_users;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.sdu.rmi.RmiSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.starfish.sf_auth.model.SFIdentity;

@Service
public class LoginUsersManager {
	private HashMap<String, UsernamePasswordAuthenticationToken> loginUsersMap;
	private HashMap<String, RmiSession> rmiSessionMap;
	private static LoginUsersManager loginUsersManager;

	public LoginUsersManager() {
		loginUsersMap = new HashMap<String, UsernamePasswordAuthenticationToken>();

		rmiSessionMap = new HashMap<String, RmiSession>();
		loginUsersManager = this;
	}

	// //////////////////////////////////////////////////////////
	public static void addLoginUsers(UsernamePasswordAuthenticationToken token) {
		loginUsersManager.loginUsersMap.put(token.getName(), token);

	}

	public static void addRmiLoginUsers(
			UsernamePasswordAuthenticationToken token) {
		loginUsersManager.loginUsersMap.put(token.getName(), token);

		RmiSession s = new RmiSession();
		loginUsersManager.rmiSessionMap.put(token.getName(), s);

	}

	public static RmiSession getRmiUserSession(String userName) {
		return loginUsersManager.rmiSessionMap.get(userName);
	}

	public static SFIdentity transUserSFIdentity(String sfIdentityStr) {

		try {
			InputStream in_withcode = new ByteArrayInputStream(
					sfIdentityStr.getBytes("UTF-8"));
			SAXReader sb = new SAXReader();
			Document doc = (Document) sb.read(in_withcode);
			SFIdentity iden = new SFIdentity(null);
			iden.currentElement = doc.getRootElement();
			iden.parse();
			return iden;
		} catch (Exception e) {
		}

		return null;
	}

}