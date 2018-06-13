package org.starfish.utils;

public class AuthUtils {
	public static String getAuthorityByRoleName(String roleName) {
		String authority = "<sf-identity> ";
		authority += "<belong dim=\"role\" value=\"" + roleName + "\" /> ";
		authority += "</sf-identity>";
		return authority;
	}
}
