package cn.edu.sdu.uims.frame;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import cn.edu.sdu.uims.util.UimsUtils;


public class UserClientInfo implements Serializable {
	public Date uploadTime = null;
	public String loginName = null;
	public String pwd = null;
	public String environmentName;
	public boolean isConnectServer = false;
	public boolean frameIsDialog = false;
	public boolean needLogin = true;
	public boolean runInLocal = false;
	public boolean loginInLocal = false;
	public boolean modelIsLocal = false;
	public boolean isEnglishVersion = false;
	public boolean isNeedFrameTitle = true;
	public String loginDialogNameKey = null;
	public String clientMainFrameNameKey = null;
	public Integer roleId;
	public String perNum;
	public String perName;
	public String assistServers[];
	public Integer roleIds[];
	public HashMap<String,String> parameterMap = null;
	public void serAttribute(String paras){
		HashMap<String,String> map = UimsUtils.stringToHashMap2(paras);
		String key, value;
		Iterator ie;
		if(map == null)
			return;
		ie = map.keySet().iterator();
		while(ie.hasNext()) {
			key = (String)ie.next();
			value = map.get(key);
			if(key.equals("isNeedFrameTitle")) {
				if(value.equals("true"))
					isNeedFrameTitle = true;
				else
					isNeedFrameTitle = false;
			}
			else if(key.equals("frameIsDialog")) {
				if(value.equals("true"))
					frameIsDialog = true;
				else
					frameIsDialog = false;
			}else if(key.equals("needLogin")) {
				if(value.equals("true"))
					needLogin = true;
				else
					needLogin = false;
			}else if(key.equals("isConnectServer")) {
				if(value.equals("true"))
					isConnectServer = true;
				else
					isConnectServer = false;
			}else if(key.equals("runInLocal")) {
				if(value.equals("true"))
					runInLocal = true;
				else
					runInLocal = false;
			}else if(key.equals("modelIsLocal")) {
				if(value.equals("true"))
					modelIsLocal = true;
				else
					modelIsLocal = false;
			}else if(key.equals("isEnglishVersion")) {
				if(value.equals("true"))
					isEnglishVersion = true;
				else
					isEnglishVersion = false;
			}else if(key.equals("loginName")) {
				loginName = value;
			}else if(key.equals("pwd")) {
				pwd = value;
			}else if(key.equals("perName")) {
				perName = value;
			}else if(key.equals("perNum")) {
				perNum = value;
			}else if(key.equals("loginDialogNameKey")) {
				loginDialogNameKey = value;
			}else if(key.equals("clientMainFrameNameKey")) {
				clientMainFrameNameKey = value;
			}else if(key.equals("environmentName")){
				environmentName = value;
			}else if(key.equals("roleId")){
				roleId = new Integer(value);
			}else if(key.equals("assistServers")){
				if(value != null && value.length() > 0) {
					 StringTokenizer sz = new StringTokenizer(value,"-");
					 int count = sz.countTokens();
					 if(count == 0) return;
					 assistServers = new String[count];
					 for(int i = 0; i< assistServers.length;i++) {
						 assistServers[i] = sz.nextToken();
					 }
				}
			}else {
				if(parameterMap == null) {
					parameterMap = new HashMap<String,String>();
				}
				parameterMap.put(key, value);
			}
		}
	}
}
