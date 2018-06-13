package cn.edu.sdu.uims.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UTemplateComponentI;
import cn.edu.sdu.uims.component.UToolBar;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UFilterTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.flex.FHashMapUtil;
import cn.edu.sdu.uims.frame.def.UClientFrameControlTemplate;
import cn.edu.sdu.uims.frame.def.UImageAttribute;
import cn.edu.sdu.uims.frame.def.ULabelAttribute;
import cn.edu.sdu.uims.frame.def.UToolbarTemplate;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UClientFrameControlPanel extends JPanel {
	public static int WINDOW_MIN_WIDTH = 100;
	private UToolBar windowToolBar;
	private List<UToolBar>  toolBarList;
	private UClientFrameControlTemplate controlTemplate;
	private WindowCommandProcessor windowCommandProcessor = new WindowCommandProcessor();
	private List<JLabel> labelList = null;
	private HashMap<String, String> paraMap;

	public void setParaMap(HashMap<String,String> map){
		paraMap = map; 
	}
	public String getParaValue(String name){
		if(paraMap != null && paraMap.get(name) != null)
			return paraMap.get(name);
		else
			return "";
	}
	public String replaceString(String s) {
		if(s == null || s.length() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		int i = 0, j, len = s.length();
		boolean b = false;
		String ss;
		while( i < len) {
			if(s.charAt(i) == '$') {
				j = i+2;
				b= true;
				while(j < len && b) {
					if(s.charAt(j)== '}') {
						ss = s.substring(i+2, j);
					sb.append(getParaValue(ss));
					b = false;
					i= j+1;
					}
					else
						j++;
				}
			}else {
				sb.append(s.charAt(i));
				i++;
			}
		}
		return sb.toString();
	}
	public UClientFrameControlTemplate getControlTemplate() {
		return controlTemplate;
	}

	public void setControlTemplate(UClientFrameControlTemplate controlTemplate) {
		this.controlTemplate = controlTemplate;
	}


	
	private class WindowCommandProcessor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String str = e.getActionCommand();
			UClientFrame f = UClientFrame.getInstance();
			if(str.equals("windowIconified"))
				f.setExtendedState(JFrame.ICONIFIED);
			else if(str.equals("windowMaximized"))
				f.setExtendedState(JFrame.MAXIMIZED_BOTH);
			else
				if(str.equals("windowClose")) {
					if (!f.quit())
						return;
					else
						System.exit(1);
				}
		}
	}

	
	public UClientFrameControlPanel() {
	}


	public void init() {
		if (controlTemplate == null)
			return;
		getClientFrameData();
		Dimension s = UClientFrame.getInstance().getFrameSize();
		Dimension d;
		setLayout(null);
		this.setBackground(UFactory.getModelSession().getColorByName(controlTemplate.bgColorName).color);
		UToolbarTemplate toolbarTemplate;
		String toolbarName;
		int i;
		int x,y;
		if(controlTemplate.windowToolBarName != null) {
			toolbarTemplate = (UToolbarTemplate) UFactory.getModelSession()
					.getTemplate(UConstants.MAPKEY_TOOLBAR, controlTemplate.windowToolBarName);
			windowToolBar = (UToolBar) toolbarTemplate.newComponent();
			windowToolBar.setTemplate(toolbarTemplate);
			windowToolBar.init();
			windowToolBar.addActionListener(windowCommandProcessor);
			d = toolbarTemplate.getSize();
			y = (controlTemplate.titleHeight - d.height)/2;
			windowToolBar.setBounds(s.width-d.width, y, d.width, d.height);
			add(windowToolBar);
		}
		if (controlTemplate.toolBarNames != null) {
			UToolBar toolBar;
			toolBarList = new ArrayList<UToolBar>();
			StringTokenizer sz = new StringTokenizer(controlTemplate.toolBarNames, ";");
			while(sz.hasMoreTokens()) {
				toolbarName = (String)sz.nextToken();
					toolbarTemplate = (UToolbarTemplate) UFactory
							.getModelSession().getTemplate(
									UConstants.MAPKEY_TOOLBAR, toolbarName);
					toolBar = (UToolBar) toolbarTemplate.newComponent();
					toolBar.setTemplate(toolbarTemplate);
					toolBar.init();
					toolBar.addActionListener(UClientFrame.getInstance().getCommonProcessListener());
					d = toolbarTemplate.getSize();
					toolBar.setBounds(toolbarTemplate.x, toolbarTemplate.y, d.width, d.height);
					add(toolBar);
					toolBarList.add(toolBar);
			}
		}
		if(controlTemplate.labelList != null) {
			JLabel jl;
			ULabelAttribute l;
			labelList = new ArrayList<JLabel>();
			for (i = 0; i < controlTemplate.labelList.size();i++) {
				l = controlTemplate.labelList.get(i);
				jl = new JLabel(replaceString(l.value));
				if(l.fontName != null)
					jl.setFont(UFactory.getModelSession().getFontByName(l.fontName).font);
				if(l.colorName != null)
					jl.setForeground(UFactory.getModelSession().getColorByName(l.colorName).color);
				jl.setBounds(l.x, l.y, l.w, l.h);
				add(jl);
				labelList.add(jl);
			}
		}
		if(controlTemplate.imageList != null) {
			JLabel jl;
			UImageAttribute ii;
			Icon icon;
			for (i = 0; i < controlTemplate.imageList.size();i++) {
				ii = controlTemplate.imageList.get(i);
				icon = UimsUtils.getCSClientIcon(ii.imageName);
				jl = new JLabel(icon);
				if(ii.align ==	UConstants.ALIGNMENT_RIGHT)
//					jl.setBounds(controlTemplate.width-icon.getIconWidth(), ii.y, icon.getIconWidth(), icon.getIconHeight());
					jl.setBounds(0, ii.y, s.width, icon.getIconHeight());
				else if(ii.align ==	UConstants.ALIGNMENT_LEFT)
					jl.setBounds(0, ii.y, icon.getIconWidth(), icon.getIconHeight());
				else if(ii.align ==	UConstants.ALIGNMENT_TOP)
					jl.setBounds(ii.x, 0, icon.getIconWidth(), icon.getIconHeight());
				else if(ii.align ==	UConstants.ALIGNMENT_BOTTOM)
					jl.setBounds(ii.x, ii.y, icon.getIconWidth(), icon.getIconHeight());
				else
					jl.setBounds(ii.x, ii.y, icon.getIconWidth(), icon.getIconHeight());
				add(jl);
			}
		}
		if(controlTemplate.comList != null) {
			UElementTemplate el;
			String className;
			UComponentI item;
			UFilterTemplate filterTemplate;
			FilterI filter;
			for(i = 0; i <controlTemplate.comList.size();i++) {
				el = (UElementTemplate) controlTemplate.comList.get(i);
				if (el.className == null)
						el.className = UFactory.getModelSession()
								.getDefaultClassByType(el.typeString);
				className = el.className;
				item = null;
				if (className != null) {
						try {
							item = (UComponentI) Class.forName(className)
									.newInstance();
						}catch(Exception e) {
							e.printStackTrace();
							item = null;
						}
				}
				if (item != null) {
					item.setTemplate(el);
					if (el.colorName != null)
						item.setColor(UFactory.getModelSession()
								.getColorByName(el.colorName));
					if (el.backgroundName != null)
						item.setBackground(UFactory.getModelSession()
								.getColorByName(el.backgroundName));
					item.setElementTemplate(el);
					item.setComponentName(el.name);
	//				item.setUParent(this);
					item.setParameters(FHashMapUtil
							.fHashMapToHashMap(el.parameters));
					if(el.addedDatas  != null && el.addedDatas.size() != 0)
						item.setAddedDatas(el.addedDatas.toArray());
					if (el.filter != null) {
						filterTemplate = UFactory.getModelSession()
								.getFilterTemplateByName(el.filter);
						filter = filterTemplate.newInstance();
						filter.init(filterTemplate.parameter);
						item.setFilter(filter);
					}
					if (el.filter1 != null) {
						filterTemplate = (UFilterTemplate) UFactory
								.getModelSession().getFilterTemplateByName(
										el.filter1);
						filter = filterTemplate.newInstance();
						item.setFilter1(filter);
					}
					item.updateAddedDatas(); // lxq temp add because 数据字典显示
					if (item instanceof UTemplateComponentI)
						((UTemplateComponentI) item).initComponents();
					item.setShellBounds(el.x, el.y, el.w, el.h);
					add(item.getAWTComponent());
	//				componentMap.put(el.name, item);
	//				componentNameList.add(el.name);
	//				componentAttributeMap.put(el.name, el);
				}				
			}			
		}
		if(controlTemplate.align == UConstants.ALIGNMENT_TOP || controlTemplate.align == UConstants.ALIGNMENT_BOTTOM)
			d = new Dimension(s.width,controlTemplate.height);
		else
			d = new Dimension(controlTemplate.width,s.height);			
		this.setSize(d);
		this.setPreferredSize(d);
		setClientFrameData();
	}
	public void ResetComponentBound(){
		Dimension s = this.getSize();
		if(s.width < WINDOW_MIN_WIDTH || s.height <WINDOW_MIN_WIDTH)
			s = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public void ReLayoutInnerComponent(){
		Dimension s = this.getSize();
		Dimension d;
		UToolbarTemplate toolbarTemplate; 
		if(windowToolBar != null){
			toolbarTemplate = (UToolbarTemplate)windowToolBar.getTemplate();
			d = toolbarTemplate.getSize();
			windowToolBar.setBounds(s.width-d.width, 0, d.width, d.height);
		}
	}
	public void getClientFrameData() {
		UClientFrame f = UClientFrame.getInstance();
	}

	public void setClientFrameData() {
		UClientFrame f = UClientFrame.getInstance();
	}


}
