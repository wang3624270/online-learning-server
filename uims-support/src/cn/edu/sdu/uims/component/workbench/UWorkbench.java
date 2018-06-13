package cn.edu.sdu.uims.component.workbench;

import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UNameObjectPar;
import cn.edu.sdu.uims.base.UPanel;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.complex.UWebAppPanel;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.event.UEventAdaptor;
import cn.edu.sdu.uims.component.panel.SuperPanel;
import cn.edu.sdu.uims.component.panel.UFPanel;
import cn.edu.sdu.uims.component.panel.UOldPanelBase;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UDialogTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UMenuPanelMapTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.flex.FHashMapUtil;
import cn.edu.sdu.uims.handler.ToolCommandHandlerI;
import cn.edu.sdu.uims.handler.UNoPanelHandlerI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UWorkbench extends UFPanel {
	protected List panelList = null;
	int num = 0;

	public UWorkbench() {

		panelList = new ArrayList();
		eventAdaptor = new UEventAdaptor(this);
	}

	public void setContainer(Container c) {
		container = (JPanel) c;
	}
	public void panelInit() {
		super.init();
	}

	public void initContainer() {
	}

	private UComponentI newComponentByTemplate(UElementTemplate template) {
		UComponentI item;
		UTemplate temp;

		item = (UComponentI)template.newComponent();
		if (item != null) {
			if (template.templateName != null) {
				temp = (UTemplate) UFactory.getModelSession().getTemplate(
						template.mapKey, template.templateName);
				item.setTemplate(temp);
			}
			item.init();

		}
		return item;
	}

	public void initContentPanels() {

	}

	public UPanelI addPanelByPanelName(String name, String content) {
		String templateName = null;
		HashMap paras = null;
		HashMap replaceMap = null;
		String className = null;
		UOldPanelBase p = null;
		Object o = UFactory.getModelSession().getTemplate(
				UConstants.MAPKEY_PANEL_FORM, name);
		UMenuPanelMapTemplate mt = null;
		if (o == null) {
			o = UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_MENUMAP, name);
			if (o != null) {
				mt = (UMenuPanelMapTemplate) o;
				paras = FHashMapUtil.fHashMapToHashMap(mt.parameterMap);
				templateName = mt.templateName;
			} else {
				templateName = name;
			}
			className = (String) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_PANEL_OLDCSPANEL, templateName);
		}
		if (className == null)
			return null;
		if (isPanelExist(name) == -1) {
			p = null;
			try {
				p = (UOldPanelBase) Class.forName(className).newInstance();
				p.setParameters(paras);
				p.init();
			} catch (Exception e) {
				UimsUtils.messageBoxInfo("无法加载面板类:" + className);
				e.printStackTrace();
				return null;
			}
			addPanel(name, content, p);
		}
		return p;
	}

	public UPanelI startDialog(UDialogTemplate temp, HashMap paras) {
		UDialogPanel item = (UDialogPanel) temp.newComponent();
		if (item != null) {
			item.setComponentName(temp.name);
			item.setTemplate(temp);
			item.setParameters(paras);
			item.setDialogForm(null);
			item.init();
			// item.addActionListener(component.getEventAdaptor());
			return item;
		}
		return null;
	}
	public UPanel getCurrentSelectPanel(){
		return  null;
	}
	public UPanelI addPanelByTemplateName(String nameTemp, String content) {
		UPanelI item = null;
		String templateName = null;
		HashMap paras = null;
		UMenuPanelMapTemplate mt = null;
		String name;
		int pos = nameTemp.indexOf("&");
		if( pos >= 0) {
			name = nameTemp.substring(0,pos);
			paras = UimsUtils.stringToHashMap(nameTemp.substring(pos+1,nameTemp.length()));
		}else {
			pos = nameTemp.indexOf("[");
			if(pos >= 0) {
				name = nameTemp.substring(0,pos);
				paras = UimsUtils.stringToHashMap(nameTemp.substring(pos,nameTemp.length()));				
			}else
				name = nameTemp;
		}
		Object o = UFactory.getModelSession().getTemplate(
				UConstants.MAPKEY_PANEL_FORM, name);
		if (o == null) {
			o = UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_MENUMAP, name);
			if (o != null) {
				mt = (UMenuPanelMapTemplate) o;
				paras = FHashMapUtil.fHashMapToHashMap(mt.parameterMap);
				templateName = mt.templateName;
				o = UFactory.getModelSession().getTemplate(
						UConstants.MAPKEY_PANEL_FORM, templateName);
			} else {
				templateName = name;
			}
			if (o == null) {
				o = UFactory.getModelSession().getTemplate(
						UConstants.MAPKEY_DIALOG, templateName);
				if (o != null) {
					startDialog((UDialogTemplate) o, paras);
				}
				return null;
			}
		}
		if (isPanelExist(name) == -1) {
			UPanelTemplate template = (UPanelTemplate) o;
			item = (UPanelI) template.newComponent();
			item.setStartMenuName(name);
			item.setOriginalTemplate(mt);
			item.setPathNameString(template.name);
			item.setTemplate(template);
			item.replaceTemplateContent();
			item.setParameters(paras); 
			item.init();
			addPanel(name, content, item);
		}else {
			if(name.equals("uimsWebCommandPanel")) {
				String cmd =  null;
				if(paras != null)
					cmd = (String)paras.get("cmd");
				if(cmd != null) {
					UPanelI pp = getCurrentPanel();
					if(pp != null)  {
						UWebAppPanel p = (UWebAppPanel) pp.getSubComponent("workBench");
						p.requestWebCommand(cmd);
					}
				}
			}
		}
		return item;
	}

	/**
	 * 检测面板是否存在，如果存在则设置为当前面板。
	 * 
	 * @param panelName
	 * @return
	 */
	public int isPanelExist(String name) {
		UNameObjectPar par;
		int i = -1;
		int size = panelList.size();
		for (i = 0; i < size; i++) {
			par = (UNameObjectPar) panelList.get(i);
			if (par.getName().equals(name)) {
				// tabbedPane.setSelectedIndex(i);
				break;
			}
		}
		if (i == size)
			return -1;
		return i;
	}

	protected void addPanel(String name, String tabName, UPanelI inPanel) {
	}

	public void removeAllPanel() {
		if (panelList != null && panelList.size() > 0) {
			for (int i = 0; i < panelList.size(); i++) {
				UNameObjectPar par = (UNameObjectPar) panelList.get(i);
				Object o = par.getObject();
				if (o instanceof SuperPanel) {
					SuperPanel p = (SuperPanel) o;
					p.onClose();
				}
				if (o instanceof UFPanel) {
					UFPanel p = (UFPanel) o; 
//					ToolCommandHandlerI selectedPanel = (ToolCommandHandlerI) o;
					UFormHandler fh = (UFormHandler)p.getHandler();
					fh.doBeforePanelClosed();
				}
			}
			panelList.clear();
		}
	}

	public boolean isSameAs(String name, UNameObjectPar par) {
		if (name.equals(par.getName()))
			return true;
		Object p = par.getObject();
		if (p instanceof UPanel) {
			UTemplate pp = ((UPanel) p).getTemplate();
			if (pp.name.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public int removePanelByName(String name) {
		if (panelList != null && panelList.size() > 0) {
			for (int i = 0; i < panelList.size(); i++) {
				UNameObjectPar par = (UNameObjectPar) panelList.get(i);
				if (isSameAs(name, par)) {
					Object o = par.getObject();
					if (o instanceof SuperPanel) {
						SuperPanel p = (SuperPanel) o;
						p.onClose();
					}
					if (o instanceof ToolCommandHandlerI) {
						ToolCommandHandlerI selectedPanel = (ToolCommandHandlerI) o;
						selectedPanel.doBeforePanelClosed();
					}
					panelList.remove(i);
					return i;
				}
			}
		}
		return -1;
	}
	public String  removePanel(int i) {
		if (panelList != null && panelList.size() >= i) {
		UNameObjectPar par = (UNameObjectPar) panelList.get(i);
			Object o = par.getObject();
			if (o instanceof SuperPanel) {
				SuperPanel p = (SuperPanel) o;
				p.onClose();
			}
			if (o instanceof ToolCommandHandlerI) {
				ToolCommandHandlerI selectedPanel = (ToolCommandHandlerI) o;
				selectedPanel.doBeforePanelClosed();
			}
			panelList.remove(i);
			return par.getName();
		}
		return null;
	}

	/**
	 * @author 刘洋
	 * @param name
	 * @param content
	 * 
	 */
	public void execNoPanelAction(String name) {
		String className = null;
		className = (String) UFactory.getModelSession().getTemplate(
				UConstants.MAPKEY_NOPANEL_ACTION, name);
		if (className == null)
			return;
		try {
			UNoPanelHandlerI noPanelHandler = (UNoPanelHandlerI) Class.forName(
					className).newInstance();
			noPanelHandler.process();
		} catch (Exception e) {
			UimsUtils.messageBoxInfo("无法加载:" + className);
			e.printStackTrace();
		}
	}
	public void setPanelTimeControlActionAttribute(){
		if(panelList == null || panelList.size() == 0)
			return;
		UPanel p;
		UNameObjectPar par;
		for(int i = 0; i < panelList.size();i++) {
			if(!(panelList.get(i) instanceof UNameObjectPar))
				continue;
			par = (UNameObjectPar)panelList.get(i);
			if(par.getObject() instanceof UPanel) {
				p = (UPanel)par.getObject();
				p.setPanelTimeControlActionAttribute();
			}
		}
	}
	public UPanelI getCurrentPanel() {
		return null;
	}
}
