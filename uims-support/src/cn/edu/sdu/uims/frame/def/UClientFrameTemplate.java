package cn.edu.sdu.uims.frame.def;

import java.util.List;

import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.def.UMenuBarTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.def.UTreeMenuTemplate;

public class UClientFrameTemplate extends UTemplate{

	public UFrameAttribute attribute;
	public UCellAttribute title,logo;
	public UMenuBarTemplate menuBarTemplate;
	public UTreeMenuTemplate treeMenuTemplate;
	public UStatusbarTemplate statusbarTemplate;
	public UProgressbarTemplate progressbarTemplate;
	public UPanelTemplate workbenchTemplate[];
	public String mainPanelName;
	public String startPanelName;
	public String border = "" ;
	public String framespace = "" ;
	public String toolbarName;
	public String toolPanelName;
	public String dataMenu;
	public String bgColor;
	public int bw=5, bh = 1;
	public int ebw=0;
	public boolean needSessionMenu = false;
	public List <UFixPanelTemplate> fixePanelTemplateList;
	public Integer rootFunId;
	public List<UClientFrameControlTemplate> controlTemplateList;
}
