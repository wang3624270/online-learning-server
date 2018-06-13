package org.starfish.pageside_access.menu;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.starfish.MenuSupportManager;
import org.starfish.URLSupportManager;
import org.starfish.configure_model.SFModel;
import org.starfish.pageside_access.Access;
import org.starfish.pageside_access.URLAccess;
import org.starfish.permi_dimension.PermissionDim;

public class MenuNode {

	private Integer id;												//菜单id
	private String menuName;										//菜单名
	private String menuURL;											//菜单url(与starfish_menu_access表相对应)
	private String menuAuth;										//菜单权限
	private Date updateTime;										//
	private String logicId;											//逻辑id(干嘛用的)
	private String parentLogicId;									//父逻辑id
	private String parentMenuName;									//父菜单
	private Integer positionNum;									//
	private String iconURL;											//
	private MenuAccess menuAccess;									//

	private List<MenuNode> sfMenuList;								//
	private boolean isLeaf;											//
	private String isDisplay;										//
	private String isAutoShort;										//

	public MenuNode() {

		setSfMenuList(new ArrayList<MenuNode>());

	}

	public MenuAccess getMenuAccess() {
		return menuAccess;
	}

	public void setMenuAccess(MenuAccess menuAccess) {
		this.menuAccess = menuAccess;
	}

	public void parseAuth() {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(
					menuAuth.getBytes("UTF-8"));
			SAXReader sb = new SAXReader();
			Document doc = (Document) sb.read(in);

			in.close();
			menuAccess = new MenuAccess(null);
			menuAccess.currentElement = doc.getRootElement();
			menuAccess.setMenuUrl(menuURL);
			menuAccess.setMenuLogicId(this.getLogicId());
			menuAccess.parse();
			isLeaf = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean checkUserPermission(PermissionDim permissionDim) {
		boolean flag = menuAccess.checkUserPermission(permissionDim);
		if (this.getParentLogicId() != null
				&& MenuSupportManager.getMenuAccess(this.getParentLogicId()) != null) {
			flag = flag
					& MenuSupportManager.getMenuAccess(this.getParentLogicId())
							.checkUserPermission(permissionDim);
		}

		return flag;

	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getLogicId() {
		return logicId;
	}

	public void setLogicId(String logicId) {
		this.logicId = logicId;
	}

	public String getParentLogicId() {
		return parentLogicId;
	}

	public void setParentLogicId(String parentLogicId) {
		this.parentLogicId = parentLogicId;
	}

	public Integer getPositionNum() {
		return positionNum;
	}

	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	// public List getAccess() {
	// return menuAccess.getAccesses();
	//
	// }

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getIsAutoShort() {
		return isAutoShort;
	}

	public void setIsAutoShort(String isAutoShort) {
		this.isAutoShort = isAutoShort;
	}

	public String toString() {
		return this.menuName;
	}

	public List<MenuNode> getSfMenuList() {
		return sfMenuList;
	}

	public void setSfMenuList(List<MenuNode> sfMenuList) {
		this.sfMenuList = sfMenuList;
	}

	public String getMenuAuth() {
		return menuAuth;
	}

	public void setMenuAuth(String menuAuth) {
		this.menuAuth = menuAuth;
	}

}