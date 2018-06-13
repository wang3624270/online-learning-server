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
import org.starfish.pageside_access.menu.MenuNode;
import org.starfish.permi_dimension.PermissionDim;

public class URLSupportManager {

	public static HashMap<String, URLAccess> urlAccessMap = new HashMap<String, URLAccess>();

	public static URLAccess emptyURLAccess = new URLAccess(null);

	public static void setURLAccess(String url, URLAccess access) {
		urlAccessMap.put(url, access);
	}

	public static URLAccess getURLAccess(String url) {
		return urlAccessMap.get(url);
	}

	// // Useless
	// public static boolean checkPermitMenuURLAccess(MenuNode menu) {
	// if (menu.getMenuURL() == null)
	// return true;
	// URLAccess aa = urlAccessMap.get(menu.getMenuURL());
	//
	// if (aa == null) {
	// try {
	// ByteArrayInputStream in = new ByteArrayInputStream(menu
	// .getMenuURL().getBytes("UTF-8"));
	// SAXReader sb = new SAXReader();
	// Document doc = (Document) sb.read(in);
	// in.close();
	// URLAccess actionAccess = new URLAccess(null);
	// actionAccess.currentElement = doc.getRootElement();
	// actionAccess.parse();
	// urlAccessMap.put(menu.getMenuURL(), actionAccess);
	//
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (DocumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	//
	// HttpSession session = null;
	// PermissionDim permissionDim = (PermissionDim) session
	// .getAttribute(StarfishConstants.userPermission);
	// return aa.checkUserPermission(permissionDim);
	// }

}
