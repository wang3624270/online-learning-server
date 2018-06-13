package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Container;
import java.util.List;

import javax.swing.event.ChangeListener;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.trans.URect;

public interface UGroupComponentI extends UComponentI{
	public void setContainer(Container container);
	public void setContainer(List List);
	public void addComponent(UComponentI com, UElementTemplate elementTemplate);
	public void addComponentBefore();
	public void addComponentAfter();
	public UPanelI getCurrentDisplayPagePanel();
	public UComponentI getCurrentDisplayPageCommonent();
	public void reLayoutComponents();
	public void setSubComponentVisible(String name, boolean visible);
	public void setSubComponentVisibleAttribute(String name, boolean visible);
	public URect makeSubComponentsBox(UComponentI [] coms);
	public void setComponentBounds(UComponentI [] coms);
	public void setCurrentPagePanel(int index);
	public void setCurrentPagePanelByComponentName(String comName);
	public boolean getCanScrolling();
	public void addChangeListener(ChangeListener linstener);
	public void removeChangeListener(ChangeListener Listener);
	public void setPageIconDisplayInfo(List<String>infoList);
	public void componentRepaint();
	
}

