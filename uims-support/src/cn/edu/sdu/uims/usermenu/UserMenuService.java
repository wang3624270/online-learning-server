package cn.edu.sdu.uims.usermenu;

import java.util.HashMap;



public class UserMenuService {
	private static UserMenuService instance = new UserMenuService();
	public  HashMap<String, UserRoleMenuObject> roleMenuMap;
	private static volatile HashMap<String,Boolean> roleMenuMapInitedMap = new HashMap<String,Boolean>();
	private HashMap<String, HashMap> sysFunresTypeMap = new HashMap<String, HashMap>();

	public HashMap<String, HashMap> getSysFunresTypeMap() {
		return sysFunresTypeMap;
	}

	public HashMap<Integer, Integer> getSysFunresTypeMap(String type) {
		return sysFunresTypeMap.get(type);
	}

	public void setSysFunresTypeMap(HashMap<String, HashMap> sysFunresTypeMap) {
		this.sysFunresTypeMap = sysFunresTypeMap;
	}

	public static UserMenuService getInstance() {
		return instance;
	}

	private UserMenuService() {
		roleMenuMap = new HashMap<String, UserRoleMenuObject>();
	}

	public UserRoleMenuObject getUserRolMenu(String key) {
		return roleMenuMap.get(key);
	}

	public void putUserRolMenu(String key, UserRoleMenuObject obj) {
		roleMenuMap.put(key, obj);
	}

	public void setRoleMenuMapInited(String key,boolean roleMenuMapInited) {
		roleMenuMapInitedMap.put(key, roleMenuMapInited);
	}

	public Boolean isRoleMenuMapInited(String key) {
		return roleMenuMapInitedMap.get(key);
	}
	public  void initSysFunresType(){
		initStateFunresType();
	}
	public  void initStateFunresType(){
//		SysFunresTypeRule rule = (SysFunresTypeRule)BusinessBeanFactory
//		.getJavaBean("sysFunresTypeRuleBean");
//		if(rule != null) {
//			sysFunresTypeMap.put(SysAuthServerConstants.BusinessRelatedType_StateRelated, rule.getSysFunresByType(SysAuthServerConstants.BusinessRelatedType_StateRelated));
//		}
			
	}
	public void clearRolyMenu(){
		roleMenuMap.clear();
		roleMenuMapInitedMap.clear();
		sysFunresTypeMap.clear();
		
	}
}
