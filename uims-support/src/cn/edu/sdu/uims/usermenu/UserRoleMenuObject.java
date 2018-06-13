package cn.edu.sdu.uims.usermenu;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.def.UTreeMenuTemplate;
import cn.edu.sdu.uims.form.TimeControlAuthAttribute;

public class UserRoleMenuObject {
	public Date timestamp;
	public List menuCmdList;
	public List menuCmdParaList;
	public List menulist;
	public UTreeMenuTemplate treeMenuTemp;
	public HashMap menuMap;
	public List topMenuList;
	public List topMenuCmdList;
	public HashMap<Integer, TimeControlAuthAttribute> timeControlMap;
	public List<Integer> roleIdList;
}
