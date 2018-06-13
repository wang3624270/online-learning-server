package cn.edu.sdu.uims.frame.def;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;


public class EnvironmentTemplate implements Serializable {
	public String name;
	public String cssPath;
	public String defaultCss;
	public String defaultContentStyle;
	public String defaultSectionStyle;
	public String defaultItemStyle;
	public List<String> defaultScriptFileList;
	public RoleFrameTypeTemplate mainFrameTemplate, mainFrameTemplateEnglish;
	public RoleFrameTypeTemplate mainFrameTemplateClientUser, mainFrameTemplateClientCommand;
	public String promptMessagePageName;
	public String confirmMessagePageName;
	public String  loginNameEnglish;
	public String loginName; 
	public String systemQueryListHandlerName;
	public String systemStuQueryRuleName;
	public String loginRuleBeanName;
	private String clientFrameName;
	private String clientDialogName;
	public String applicationTitle;
	public boolean apploginJsp = false;
	public String mobileIndexHtml;
	public String defaultIndextHtml;
	public String applicationEnTitle;
	public String modelName;
	private String csLoginName;
	public String loginPromptPageName;
	public String mainMenuPageName;
	public int defautMajorClassMode;
	public String defaultSysNotifyMessage;
	public String defaultSysNotifyMessageEng;
	public boolean mustModifyPw = false;
	public String systemNotifyType = null;
	public String loginActionBean = null;
	public String supportEmail= "91680@sdu.edu.cn";
	public HashMap<String,UserTemplate> userMap;
	public HashMap<String, String> properties;
	public HashMap<String, RoleFrameTypeTemplate> roleFrameMap;
	public HashMap<String, String> loginPageMap, csLoginDialogMap;
	public HashMap<String, RoleClientFrameTypeTemplate>roleClientFrameMap;
	public HashMap<String, RoleClientFrameTypeTemplate>roleClientDialogMap;
	public HashMap<String, RoleTmenuControlTemplate>roleMenuControlMap;
	
	public String portalTsinghuaKey;
	public String webAppName;
	public boolean autoCheckEmail;
	
	public String defaultCollegeType;
	public String defaultMajorType;
	public boolean  defaultLoginEnglishLanguage = false;
	public boolean checkPassword = false;
	public boolean validateCode = false;
	public boolean clientIsSingleLogin = false;
	public void getAttribute(Element e){
		String str;
		name = e.attributeValue("name");
		webAppName = e.attributeValue("webAppName");
		if(webAppName == null)
			webAppName = "local";
		str = e.attributeValue("autoCheckEmail");
		if(str != null && str.equals("true"))
			autoCheckEmail = true;
		else
			autoCheckEmail = false;
		str = e.attributeValue("apploginJsp");
		if(str != null && str.equals("true"))
			apploginJsp = true;
		else
			apploginJsp = false;
		mobileIndexHtml = e.attributeValue("mobileIndexHtml");
		defaultIndextHtml = e.attributeValue("defaultIndextHtml");
		applicationTitle = e.attributeValue("applicationTitle");
		applicationEnTitle = e.attributeValue("applicationEnTitle");
		cssPath = e.attributeValue("cssPath");
		defaultCss = e.attributeValue("css");
		if(defaultCss == null)
			defaultCss = "/bsuims/common.css";
		defaultContentStyle = e.attributeValue("contentStyle");
		if(defaultContentStyle == null) 
			defaultContentStyle = "bsuims_contentStyle";
		defaultSectionStyle = e.attributeValue("sectionStyle");
		if(defaultSectionStyle == null) 
			defaultSectionStyle = "bsuims_sectionStyle";
		defaultItemStyle = e.attributeValue("itemStyle");
		if(defaultItemStyle == null) 
			defaultItemStyle = "bsuims_itemStyle";
		promptMessagePageName = e.attributeValue("promptmessagePageName");
		if(promptMessagePageName == null)
			promptMessagePageName = "bsUimsPromptMessagePage";
		confirmMessagePageName = e.attributeValue("confirmMessagePageName");
		if(confirmMessagePageName == null)
			confirmMessagePageName = "bsUimsConfirmMessagePage";
		systemQueryListHandlerName = e.attributeValue("systemQueryListHandlerName");
		systemStuQueryRuleName = e.attributeValue("systemStuQueryRuleName");
		Iterator it = e.elementIterator("scripFile");
		Element e1;
		String fileName;
		while(it.hasNext()) {
			e1 = (Element)it.next();
			if(defaultScriptFileList == null)
				defaultScriptFileList = new ArrayList<String>();
			fileName = e1.attributeValue("fileName");
			defaultScriptFileList.add(fileName);
		}
		loginActionBean = e.attributeValue("loginActionBean");
		String url  = e.attributeValue("mainFrameName");
		if(url == null)
			url =name + "MainFrame";
		mainFrameTemplate = new RoleFrameTypeTemplate(url);
		mainFrameTemplate.responseType =e.attributeValue("mainFrameResponseType");
		mainFrameTemplate.menuType = e.attributeValue("mainFrameMenuType");
		url = e.attributeValue("mainFrameNameEnglish");
		if(url == null)
			mainFrameTemplateEnglish =mainFrameTemplate;
		else {
			mainFrameTemplateEnglish = new RoleFrameTypeTemplate(url);			
			mainFrameTemplateEnglish.responseType =e.attributeValue("mainFrameResponseTypeEnglish");
			mainFrameTemplateEnglish.menuType = e.attributeValue("mainFrameMenuTypeEnglish");
		}

		url = e.attributeValue("mainFrameNameClientUser");
		if(url == null)
			mainFrameTemplateClientUser =mainFrameTemplate;
		else {
			mainFrameTemplateClientUser = new RoleFrameTypeTemplate(url);			
			mainFrameTemplateClientUser.responseType =e.attributeValue("mainFrameResponseTypeClientUser");
			mainFrameTemplateClientUser.menuType = e.attributeValue("mainFrameMenuTypeClientUser");
		}
		url = e.attributeValue("mainFrameNameClientCommand");
		if(url == null)
			mainFrameTemplateClientCommand =mainFrameTemplate;
		else {
			mainFrameTemplateClientCommand = new RoleFrameTypeTemplate(url);			
			mainFrameTemplateClientCommand.responseType =e.attributeValue("mainFrameResponseTypeClientCommand");
			mainFrameTemplateClientCommand.menuType = e.attributeValue("mainFrameMenuTypeClientCommand");
		}

		mainMenuPageName = e.attributeValue("mainMenuPageName");
		if(mainMenuPageName == null)
			mainMenuPageName = name + "MainMenuPage";
		loginName = e.attributeValue("loginName");
		if(loginName == null)
			loginName = name + "LoginPage";
		loginNameEnglish = e.attributeValue("loginNameEnglish");
		if(loginNameEnglish == null)
			loginNameEnglish = loginName;
		loginRuleBeanName = e.attributeValue("loginRuleBeanName");
		if(loginRuleBeanName == null)
			loginRuleBeanName = name + "LoginProcessRuleBean";
		clientFrameName  =e.attributeValue("clientFrameName");
		if(clientFrameName == null)
			clientFrameName = name + "ClientFrame";
		clientDialogName  =e.attributeValue("clientDialogName");
		if(clientDialogName == null)
			clientDialogName = name + "ClientDialog";
		csLoginName = e.attributeValue("csLoginName");
		if(csLoginName == null)
			csLoginName = name + "LoginDialog";
		modelName = e.attributeValue("modelName");
		if(modelName == null)
			modelName = name + "/" + name + "-modeltemplate.xml";
		loginPromptPageName = e.attributeValue("loginPromptPageName");
		
		portalTsinghuaKey = e.attributeValue("portalTsinghuaKey");
		if(portalTsinghuaKey == null)
			portalTsinghuaKey = "sdunicportal";
		
		str = e.attributeValue("defautMajorClassMode");
		if(str== null)
			defautMajorClassMode = 0;
		else
			defautMajorClassMode = Integer.parseInt(str);
		str = e.attributeValue("mustModifyPw");
		if("true".equals(str))
			mustModifyPw = true;
		str = e.attributeValue("supportEmail");
		if(str != null)
			supportEmail = str;
		defaultSysNotifyMessage = e.attributeValue("defaultSysNotifyMessage");
		if(defaultSysNotifyMessage == null) {
			defaultSysNotifyMessage = "在系统使用过程中遇到操作问题请发邮件到：";
			defaultSysNotifyMessage	+=  supportEmail;
			defaultSysNotifyMessage += "，进行咨询，我们会尽快回复，谢谢！";
		}
		defaultSysNotifyMessageEng = e.attributeValue("defaultSysNotifyMessageEng");
		if(defaultSysNotifyMessageEng == null) {
			defaultSysNotifyMessageEng = "When the system problem occurs, please email to ";
			defaultSysNotifyMessageEng	+=  supportEmail;
			defaultSysNotifyMessageEng += ". We will reply as soon as possible, thanks.";
		}
		systemNotifyType = e.attributeValue("systemNotifyType");
		it = e.elementIterator("properties");
		String pName, pValue; 
		while(it.hasNext()) {
			e1 = (Element)it.next();
			pName = e1.attributeValue("name");
			pValue = e1.attributeValue("value");
			if(properties == null) {
				properties = new HashMap<String,String>();
			}
			properties.put(pName, pValue);
		}
		
		it = e.elementIterator("menuControl");
		while(it.hasNext()) {
			e1 = (Element)it.next();
			pName = e1.attributeValue("roleNames");
			pValue = e1.attributeValue("toolbarTemplateName");
			RoleTmenuControlTemplate t = new RoleTmenuControlTemplate();
			t.toolbarTemplateName = pValue;
			pValue = e1.attributeValue("isDispMenuBar");
			if(pValue!= null && pValue.equals("false"))
				t.isDispMenuBar = false;
			else
				t.isDispMenuBar = true;
			pValue = e1.attributeValue("isDispMenuTree");
			if(pValue!= null && pValue.equals("false"))
				t.isDispMenuTree = false;
			else
				t.isDispMenuTree = true;			
			if(roleMenuControlMap == null) {
				roleMenuControlMap = new HashMap<String,RoleTmenuControlTemplate>();
			}
			roleMenuControlMap.put(pName, t);
		}
		it = e.elementIterator("clientFrame");
		while(it.hasNext()) {
			e1 = (Element)it.next();
			pName = e1.attributeValue("roleId");
			pValue = e1.attributeValue("frameTemplateName");
			RoleClientFrameTypeTemplate t = new RoleClientFrameTypeTemplate();
			t.frameTemplateName = pValue;
			t.systemNotifyType = e1.attributeValue("systemNotifyType");
			if(roleClientFrameMap == null) {
				roleClientFrameMap = new HashMap<String,RoleClientFrameTypeTemplate>();
			}
			roleClientFrameMap.put(pName, t);
		}
		it = e.elementIterator("clientDialog");
		while(it.hasNext()) {
			e1 = (Element)it.next();
			pName = e1.attributeValue("roleId");
			pValue = e1.attributeValue("dialogTemplateName");
			RoleClientFrameTypeTemplate t = new RoleClientFrameTypeTemplate();
			t.frameTemplateName = pValue;
			if(roleClientDialogMap == null) {
				roleClientDialogMap = new HashMap<String,RoleClientFrameTypeTemplate>();
			}
			roleClientDialogMap.put(pName, t);
		}
		it = e.elementIterator("mainFrame");
		while(it.hasNext()) {
			e1 = (Element)it.next();
			pName = e1.attributeValue("roleId");
			pValue = e1.attributeValue("url");
			RoleFrameTypeTemplate t = new RoleFrameTypeTemplate(pValue);
			t.enUrl = e1.attributeValue("enUrl");
			t.responseType =e1.attributeValue("responseType");
			t.responseTypeEn =e1.attributeValue("responseTypeEn");
			if(t.responseTypeEn == null)
				t.responseTypeEn = t.responseType;
			t.menuType = e1.attributeValue("menuType");
			t.beanName = e1.attributeValue("beanName");
			t.systemNotifyType = e1.attributeValue("systemNotifyType");
			if(roleFrameMap == null) {
				roleFrameMap = new HashMap<String,RoleFrameTypeTemplate>();
			}
			roleFrameMap.put(pName, t);
		}
		it = e.elementIterator("loginPage");
		while(it.hasNext()) {
			e1 = (Element)it.next();
			pName = e1.attributeValue("key");
			pValue = e1.attributeValue("loginName");
			if(loginPageMap == null) {
				loginPageMap = new HashMap<String,String>();
			}
			loginPageMap.put(pName, pValue);
		}
		it = e.elementIterator("csLoginDialog");
		while(it.hasNext()) {
			e1 = (Element)it.next();
			pName = e1.attributeValue("key");
			pValue = e1.attributeValue("loginName");
			if(csLoginDialogMap == null) {
				csLoginDialogMap = new HashMap<String,String>();
			}
			csLoginDialogMap.put(pName, pValue);
		}
		it = e.elementIterator("user");
		UserTemplate user;
		while(it.hasNext()) {
			e1 = (Element)it.next();
			user = new UserTemplate();
			user.name = e1.attributeValue("name");
			user.pw = e1.attributeValue("pw");
			user.realName = e1.attributeValue("realName");
			user.panelName = e1.attributeValue("panelName");
			if(userMap == null)
					userMap = new HashMap<String, UserTemplate>();
			userMap.put(user.getName(), user);
		}
		defaultCollegeType = e.attributeValue("collegeType");	
		defaultMajorType = e.attributeValue("mojorType");
		str = e.attributeValue("defaultLoginEnglishLanguage");
		if(str != null && str.equals("true"))
			defaultLoginEnglishLanguage = true;
		str = e.attributeValue("checkPassword");
		if(str != null && str.equals("true"))
			checkPassword = true;
		str = e.attributeValue("validateCode");
		if(str != null && str.equals("true"))
			validateCode = true;
		str = e.attributeValue("clientIsSingleLogin");
		if(str != null && str.equals("true"))
			clientIsSingleLogin = true;
	}
	public String getSystemNotifyType(Integer roleIds[]){
		if(roleIds != null && roleIds.length > 0 && roleFrameMap != null) {
			RoleFrameTypeTemplate t;
			for(int i = 0; i < roleIds.length;i++) {
				t = roleFrameMap.get(roleIds[i].toString());
				if(t != null && t.systemNotifyType != null)
					return t.systemNotifyType;
			}
		}
		return this.systemNotifyType;
	}
	public String getClientSystemNotifyType(List<String> roleList){
		if(roleList != null && roleList.size() > 0 && roleClientFrameMap != null) {
			RoleClientFrameTypeTemplate t;
			for(int i = 0; i < roleList.size();i++) {
				t = roleClientFrameMap.get(roleList.get(i));
				if(t != null && t.systemNotifyType != null)
					return t.systemNotifyType;
			}
		}
		return this.systemNotifyType;
	}
	public String getClientFrameName(List<String> roleList){
		if(roleList != null && roleList.size() > 0 && roleClientFrameMap != null) {
			RoleClientFrameTypeTemplate t;
			for(int i = 0; i < roleList.size();i++) {
				t = roleClientFrameMap.get(roleList.get(i));
				if(t != null && t.frameTemplateName != null)
					return t.frameTemplateName;
			}
		}
		return this.clientFrameName;		
	}
	public String getClientFrameName(String clientFrameNameKey){
		if(roleClientFrameMap != null) {
			RoleClientFrameTypeTemplate t;
			t = roleClientFrameMap.get(clientFrameNameKey);
			if(t != null && t.frameTemplateName != null)
				return t.frameTemplateName;
		}
		return this.clientFrameName;		
	}
	public RoleTmenuControlTemplate getMenuControlTemplateName(List<String>roleList){
		if(roleList != null && roleList.size() > 0 && roleMenuControlMap != null) {
			RoleTmenuControlTemplate t;
			for(int i = 0; i < roleList.size();i++) {
				t = roleMenuControlMap.get(roleList.get(i));
				if(t != null && t.toolbarTemplateName != null)
					return t;
			}
		}
		return null;		
	}
	public String getClientDialogName(List<String> roleList){
		if(roleList != null && roleList.size() > 0 && roleClientDialogMap != null) {
			RoleClientFrameTypeTemplate t;
			for(int i = 0; i < roleList.size();i++) {
				t = roleClientDialogMap.get(roleList.get(i));
				if(t != null && t.frameTemplateName != null)
					return t.frameTemplateName;
			}
		}
		return this.clientDialogName;		
	}
	public String getCsLoginName(String key){
		if(key == null || this.csLoginDialogMap == null)
			return this.csLoginName;
		else 
			return csLoginDialogMap.get(key);
	}
}
