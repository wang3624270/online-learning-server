package org.starfish;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import org.starfish.constants.StarfishConstants;
import org.starfish.pageside_access.URLAccess;
import org.starfish.pageside_access.menu.MenuAccess;
import org.starfish.pageside_access.menu.MenuNode;
import org.starfish.permi_dimension.PermissionDim;

public class MenuSupportManager {

	public static HashMap<String, MenuAccess> menuAccessMap = new HashMap<String, MenuAccess>();

	public static MenuAccess emptyMenuAccess = new MenuAccess(null);

	public static void setMenuAccess(String logicId, MenuAccess access) {
		menuAccessMap.put(logicId, access);
	}

	public static MenuAccess getMenuAccess(String logicId) {
		return menuAccessMap.get(logicId);
	}

 

}
