package cn.edu.sdu.uims.frame.def;

import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;

public class UClientDialogTemplate extends UTemplate{
	public UFrameAttribute attribute;
	public UCellAttribute title,logo;
	public UPanelTemplate panelTemplate;
	public String bgColorName = null;
	public String imageName = null;
	public int winX, winY, winW, winH; 

}
