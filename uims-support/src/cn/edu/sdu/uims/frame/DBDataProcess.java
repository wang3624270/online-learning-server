package cn.edu.sdu.uims.frame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.sdu.uims.component.menu.UMenuInfo;
import cn.edu.sdu.uims.constant.UimsConstants;
import cn.edu.sdu.uims.service.DBDataProcessorI;

public class DBDataProcess implements DBDataProcessorI, Serializable{
	private List menuList;
	private List structedMenuList = null;
	public DBDataProcess(List list){
		menuList = list;
	}
	public void setMenuList(List menuList){
		this.menuList = menuList ;
	}
	private  List copyMenuListAndReverse(List menuList)
	{
		if(menuList == null)return null;
		List copyList=new ArrayList(menuList.size());
		for(int i=menuList.size()-1 ;i>=0;i--)
		{
			copyList.add(menuList.get(i));
		}
		return copyList;
	}
	private void sortStructedMenuList(List menuList)
	{
		Collections.sort(menuList,UMenuInfo.getComparator());
		UMenuInfo menuInfo = null;
		for(int i=0;i<menuList.size();i++)
		{
			menuInfo=(UMenuInfo) menuList.get(i);
			if(menuInfo.subMenuList != null){
//				Collections.sort(menuInfo.subMenuList,UMenuInfo.getComparator());
				sortStructedMenuList(menuInfo.subMenuList);
			}
		}
	}
	
    public  UMenuInfo findMenu(Integer menuNo,List menuList )
    {
    	if(menuList == null)return null;
    	UMenuInfo menuInfo=null;
    	UMenuInfo foundMenu = null;
    	for(int i=0;i<menuList.size();i++)
    	{
    		menuInfo = (UMenuInfo) menuList.get(i);
    		if(menuInfo.menuNo.equals(menuNo))
    		{
    			return menuInfo;
    		}else
    		{
    			foundMenu = findMenu(menuNo,menuInfo.subMenuList);
    			if(foundMenu != null)
    			{
    				return foundMenu;
    			}
    		}
    	}
    	return null;
    }
  
    
    
    public List buildMenuStructure() {
    	if(structedMenuList != null)
    		return structedMenuList;
    	List copyList = copyMenuListAndReverse(menuList);
    	if(copyList == null)return null;
    	structedMenuList = new ArrayList();
    	
    	UMenuInfo menuInfo;
    	boolean isRemoveMenu = false;
		while (!copyList.isEmpty()) {
			isRemoveMenu = false;
			for (int i =  copyList.size()-1; i >=0; i--) {
				menuInfo = (UMenuInfo) copyList.get(i);
				if((menuInfo.menuRight != null 
				&& !menuInfo.menuRight.equals(UimsConstants.AuthFlag_InVisible))) {
					if (menuInfo.menuUpNo.intValue() == 0) {
						structedMenuList.add(menuInfo);
						copyList.remove(menuInfo);
						isRemoveMenu = true;
					} else if (menuInfo.IsLeaf == 1 || menuInfo.IsLeaf == 2) {
						UMenuInfo foundMenu = findMenu(menuInfo.menuUpNo,structedMenuList);
						if(foundMenu != null)
						{
							if(foundMenu.subMenuList == null)
							{
								foundMenu.subMenuList  = new ArrayList<UMenuInfo>();
							}
							foundMenu.subMenuList.add(menuInfo);
							copyList.remove(menuInfo);
							isRemoveMenu = true;
						}
					} 
					
				}
			}
			if(!isRemoveMenu)
			{
				break;
			}
		}
		sortStructedMenuList(structedMenuList);
		return structedMenuList;
	}
}
