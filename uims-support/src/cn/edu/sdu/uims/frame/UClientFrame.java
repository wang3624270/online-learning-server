package cn.edu.sdu.uims.frame;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.USplitPane;
import cn.edu.sdu.uims.component.UStatusBar;
import cn.edu.sdu.uims.component.UToolBar;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.menu.UMenuBar;
import cn.edu.sdu.uims.component.menu.UMenuInfo;
import cn.edu.sdu.uims.component.panel.UPanelTimerControlActionThread;
import cn.edu.sdu.uims.component.tree.UTreeMenu;
import cn.edu.sdu.uims.component.tree.UTreeNode;
import cn.edu.sdu.uims.component.workbench.UWorkbench;
import cn.edu.sdu.uims.component.workbench.UWorkbenchTable;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UMenuBarTemplate;
import cn.edu.sdu.uims.def.UMenuItemTemplate;
import cn.edu.sdu.uims.def.UMenuTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.def.UPromptTemplate;
import cn.edu.sdu.uims.def.UTreeMenuTemplate;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.def.RoleTmenuControlTemplate;
import cn.edu.sdu.uims.frame.def.UClientFrameControlTemplate;
import cn.edu.sdu.uims.frame.def.UClientFrameTemplate;
import cn.edu.sdu.uims.frame.def.UFixPanelTemplate;
import cn.edu.sdu.uims.frame.def.UStatusbarTemplate;
import cn.edu.sdu.uims.frame.def.UToolTemplate;
import cn.edu.sdu.uims.frame.def.UToolbarTemplate;
import cn.edu.sdu.uims.handler.UTimerHandlerI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.pi.UClientFrameI;
import cn.edu.sdu.uims.progress.ProgressBar;
import cn.edu.sdu.uims.service.DBDataProcessorI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UClientFrame extends JFrame implements UFrameConstants, UClientFrameI {

	// frame实例对象
	protected static UClientFrame frame = null;
	// 模版路径
	protected String templatePath = "cn\\edu\\sdu\\";
	protected String clientFrameName = "platformRmiClient";
	// 应用模版对象

	protected UClientFrameTemplate apptemplate = null;
	// 工作区对象
	protected UWorkbench[] workbench;
	private int curentWorkbenchIndex = 0;
	// toolbar对象
	protected UToolBar toolBar = null;
	// 状态栏对象
	protected UStatusBar statusBar = null;
	// 菜单bar
	protected UMenuBar menuBar = null;
	// 命令处理对象
	protected CommonCommandProcess commonProcessListener = new CommonCommandProcess();

	// 菜单树和工作区的分割栏
	protected USplitPane jSplitPane = new USplitPane();
	// 左侧scrollpanel
	protected UScrollPane jScrollPaneLeft = new UScrollPane();

	// 菜单树
	protected UTreeMenu treeMenu;

	protected List veiwKeyListenerList = new ArrayList();
	protected JPanel workBenchContainer;
	protected CardLayout cardLayout = new CardLayout();

	protected CardLayout actionCardLayout = null;
	protected UPanelI mainPanel = null, toolPanel = null;
	private List menuListMenu, menuListTree;

	private boolean isMainPanel = false;
	private TrayIconShow trayIconShow;

	private UPanelTimerControlActionThread timerControlActionThread;

	private UClientFrameControlPanel controlPanel;
	private String applicationName;

	public List getMenuListMenu() {
		return menuListMenu;
	}
	private Dimension frameSize;
	public void setMenuListMenu(List menuListMenu) {
		this.menuListMenu = menuListMenu;
	}

	public List getMenuListTree() {
		return menuListTree;
	}

	public void setMenuListTree(List menuListTree) {
		this.menuListTree = menuListTree;
	}

	public UClientFrameTemplate getUClientFrameTemplate(){
		return this.apptemplate;
	}
	public UClientFrame() {
		this("");
		frame = this;
	}

	public UClientFrame(String name) {
		this(name, "cn\\edu\\sdu\\", "platformRmiClient");
	}

	public UClientFrame(String name, String templatePath,
			String appConfigureName) {
		super(name);
		frame = this;
	}

	/**
	 * 初始化生成template
	 * 
	 */
	private String getUClientFrameTemplateName() {
		
		EnvironmentTemplate envir = UFactory.getModelSession()
				.getEnvironmentTemplate();
		String frame = envir.getClientFrameName(getRoleList());
		return frame;
	}
	public List<String> getRoleList(){
		List<String> roleList = null;
		if(UimsUtils.userTokenClientSide != null)
			roleList = UimsUtils.userTokenClientSide.getRoleList();
		if(roleList == null) {
			roleList = new ArrayList();
//			roleList.add("manager");
			roleList.add("system");
		}
		return roleList;
	}
	private RoleTmenuControlTemplate getMenuControlTemplateName() {
		EnvironmentTemplate envir = UFactory.getModelSession()
				.getEnvironmentTemplate();
		return envir.getMenuControlTemplateName(getRoleList());
	}

	public void initTemplate() {

		String menuType = MyProperties.createProperties().getProperty(
				UConstants.CS_MENU_TYPE);
		String treeType = MyProperties.createProperties().getProperty(
				UConstants.CS_TREE_TYPE);
		if (menuType == null || menuType.equals("")) {
			menuType = null;
		}
		if (treeType == null || treeType.equals("")) {
			treeType = null;
		}

		DBDataProcessorI proc = new DBDataProcess(UimsFactory.getClientMainI().getMenuList());
		List a = proc.buildMenuStructure();
		EnvironmentTemplate envir = UFactory.getModelSession()
				.getEnvironmentTemplate();
		applicationName = envir.name;
		if (UimsFactory.getClientMainI().isEnglishVersion())
			setTitle(envir.applicationEnTitle);
		else
			setTitle(envir.applicationTitle);
		String frameName = getUClientFrameTemplateName();
		apptemplate = (UClientFrameTemplate) UFactory.getModelSession()
				.getTemplate(UConstants.MAPKKEY_CLIENTFRAME, frameName);
		if (apptemplate.dataMenu != null && !apptemplate.dataMenu.equals("")) {
			if (a == null || a.size() == 0)
				return;
			if (a.size() == 1) {
				UMenuInfo mt = (UMenuInfo) a.get(0);
				a = mt.subMenuList;
			}
			UFactory.getModelSession().buildTreeTemplate(a,
					apptemplate.treeMenuTemplate, menuType);
			UFactory.getModelSession().buildMenuTemplate(a,
					apptemplate.menuBarTemplate, treeType);
		}

	}

	/**
	 * frame的初始化
	 * 
	 */

	/**
	 * 初始化menubar
	 */
	private void initMenuBar(UMenuBarTemplate menuBarTemplate) {
		if (menuBarTemplate == null)
			return;
		menuBar = (UMenuBar) menuBarTemplate.newComponent();
		// menuBar.setMargin(null);
		// menuBar.setOpaque(true);
		menuBar.setTemplate(menuBarTemplate);
		menuBar.setLeftTreeMenu(treeMenu);
		menuBar.init();
		menuBar.addActionListener(commonProcessListener);
		setJMenuBar(menuBar);
	}

	/**
	 * 初始化toolbar
	 */
	public void initToolBar(UToolbarTemplate template) {
		if (template == null)
			return;
		toolBar = (UToolBar) template.newComponent();
		toolBar.setTemplate(template);
		toolBar.init();
		toolBar.addActionListener(commonProcessListener);
	}

	/**
	 * 初始化menuTree,同时加到menubar上
	 */
	public void initMenuAndTree(UTreeMenuTemplate treeMenuTemplate, int i) {

		UTreeNode root = new UTreeNode("根菜单");
		if (treeMenu == null)
			treeMenu = new UTreeMenu(root);
		treeMenu.setTemplate(treeMenuTemplate);
		treeMenu.init(i);
		treeMenu.removeActionListener(commonProcessListener);
		treeMenu.updateUI();
		treeMenu.addActionListener(commonProcessListener);
		// repaint();
	}

	/**
	 * 初始化状态栏
	 * 
	 * @param template
	 *            状态栏模版
	 */
	public void initStatusBar(UStatusbarTemplate template) {
		if (template == null)
			return;
		statusBar = (UStatusBar) template.newComponent();
		statusBar.setTemplate(template);
		statusBar.init();
	}

	/**
	 * 初始化工作区
	 * 
	 * @param p
	 */
	public void initContents(JPanel p) {
		if (apptemplate == null || apptemplate.workbenchTemplate == null)
			return;
		curentWorkbenchIndex = 0;
		workbench = new UWorkbench[apptemplate.workbenchTemplate.length];
		for (int i = 0; i < apptemplate.workbenchTemplate.length; i++) {
			workbench[i] = (UWorkbench) apptemplate.workbenchTemplate[i]
					.newComponent();
			workbench[i].setTemplate(apptemplate.workbenchTemplate[i]);
			JPanel pt = new JPanel();
			p.add(pt, apptemplate.workbenchTemplate[i].name);
			workbench[i].setContainer(pt);
			workbench[i].init();
		}
	}

	/**
	 * 初始化window窗口
	 * 
	 */

	/**
	 * 取得实例
	 * 
	 * @return
	 */
	public static UClientFrame getFrame() {
		if (frame == null) {
			frame = new UClientFrame();
		}
		return frame;
	}

	// Overridden so we can exit when window is closed
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_ICONIFIED) {

		} else if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			if (!quit())
				return ;
		}
		super.processWindowEvent(e);
	}

	public boolean quit() {
		int i = UimsUtils.messageBoxChoice("您确定要退出么?");
		if (i == JOptionPane.YES_OPTION) {
			for (int j = 0; j < workbench.length; j++)
				workbench[i].removeAllPanel();
			// if (clientTrayIcon != null) {
			// clientTrayIcon.setTrayIconVisible(false);
			// clientTrayIcon.clearUpTheTrayIcon();
			// }
			// System.exit(0);
			infomServerExit();
			UimsFactory.getClientMainI().shutdown();
			return true;
		} else
			return false;
	}

	/**
	 * 通用命令处理
	 * 
	 * @author 赵鹏 2008.7.5
	 * 
	 */
	public void porcessActionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub

		String ms;
		Method m;
		UComponentI component = (UComponentI) e.getSource();
		UPanelI p = null;		
		if (workbench[curentWorkbenchIndex] instanceof UWorkbench) {
			p = ((UWorkbench) workbench[curentWorkbenchIndex])
					.getCurrentPanel();
		}
		String cmd = e.getActionCommand();
		if(component.getTemplate() instanceof UMenuItemTemplate) {
			UMenuItemTemplate template = (UMenuItemTemplate) component
					.getTemplate();
			if (cmd != null && cmd.equals("CHANGE_WORKBENCH")) {
				changeWorkbenchByName(template.name);
			} else if (cmd != null && cmd.startsWith("tool")) {
					if (p != null) {
						ms = "do" + cmd.substring(4, cmd.length());
						p.sendhandlerProcess(ms, null, null);
					}
			}else {
				addPanelByMenuCmd(cmd, template.name, template.content,
					template.enContent);
			}
		}
		else {
			if (p != null) {
				p.sendhandlerProcess("processActionEvent", e, "action");
			}
		}
	}

	class CommonCommandProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			porcessActionPerformed(e);
		}

	}

	public void addPanelByMenuCmd(String cmd, String name, String content,
			String enContent) {
		UPanelI p = null;
		String cc = "";
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		if (b && enContent != null && enContent.length() != 0)
			cc = enContent;
		else
			cc = content;
		if (cmd == null || cmd.trim().equals("")
				|| cmd.equals(ACTION_CMD_OPEN_PANEL)) {
			p = workbench[curentWorkbenchIndex].addPanelByPanelName(name, cc);

		} else if (cmd.equals(ACTION_CMD_NO_PANEL)) {
			workbench[curentWorkbenchIndex].execNoPanelAction(name);
		} else {
			p = workbench[curentWorkbenchIndex].addPanelByTemplateName(cmd, cc);
		}
		if (p != null) {
			enableToolBarButtons(p.getToolActions());
			p.processDispControlAfterInited();
		}
	}

	public void startPanelByHandler(String name, String content) {
		UPanelI p = null;
		p = workbench[curentWorkbenchIndex].addPanelByTemplateName(name,
				content);
		if (p != null) {
			enableToolBarButtons(p.getToolActions());
		}
	}

	public boolean isCloseCurrentPaneWhenAddPanel() {
		if (apptemplate == null)
			return false;
		else
			return apptemplate.attribute.closeExistPanel;
	}

	public int getMaxOpenPanelNum() {
		if (apptemplate == null)
			return 0;
		else
			return apptemplate.attribute.maxOpenPanelMax;
	}

	public void enableToolBarButtons(String[] acts) {
		if (toolBar != null)
			toolBar.enableToolBarButtons(acts);
	}

	public void disableToolBarButtons() {
		if (toolBar != null)
			toolBar.disableToolBarButtons();
	}

	public static ProgressBar getProgressBar() {
		if (frame.statusBar != null)
			return frame.statusBar.getProgressBar();
		else
			return null;
	}

	public static UClientFrame getInstance() {
		return frame;
	}

	public static void main(String[] args) {
		UClientFrame f = new UClientFrame("");
		f.init();

	}

	public void initShortCutKey() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		toolkit.addAWTEventListener(new ShortCutkeyProcess(),
				AWTEvent.KEY_EVENT_MASK);
	}

	public void addViewKeyListener(KeyListener l) {
		this.veiwKeyListenerList.add(l);
	}

	public void removeViewKeyListener(KeyListener l) {
		if (l == null)
			return;
		int i;
		for (i = 0; i < veiwKeyListenerList.size(); i++) {
			if (veiwKeyListenerList.get(i) == l) {
				veiwKeyListenerList.remove(i);
				break;
			}
		}
	}

	class ShortCutkeyProcess implements AWTEventListener {

		public void eventDispatched(AWTEvent event) {
			// TODO Auto-generated method stub
			boolean bCtrl = false, bShift = false, bAlt = false;
			int charCode;
			if (!(event instanceof KeyEvent))
				return;
			KeyListener l;
			for (int i = 0; i < veiwKeyListenerList.size(); i++) {
				l = (KeyListener) veiwKeyListenerList.get(i);
				l.keyTyped((KeyEvent) event);
			}
			UPanelI p = null;
			UFormHandler handler;
			if (workbench[curentWorkbenchIndex] instanceof UWorkbenchTable) {
				p = ((UWorkbenchTable) workbench[curentWorkbenchIndex])
						.getCurrentPanel();
				if (p != null) {
					p.sendhandlerProcess("processKeyEvent", event, "shortCut");
				}
			}
		}
	}

	public void changeWorkbenchByName(String name) {
		int i = getCurrentWorkbenchIndexByName(name);
		setCurrentWorkbench(i);
	}

	public int getCurrentWorkbenchIndexByName(String name) {
		int i;
		for (i = 0; i < workbench.length; i++) {
			if (apptemplate.workbenchTemplate[i].name.equals(name))
				return i;
		}
		return -1;
	}

	public void setCurrentWorkbench(int index) {
		if (curentWorkbenchIndex == index)
			return;
		if (index < 0 || index >= workbench.length)
			return;
		curentWorkbenchIndex = index;
		cardLayout.show(workBenchContainer,
				apptemplate.workbenchTemplate[curentWorkbenchIndex].name);
	}

	public void initFixPanel(List<UFixPanelTemplate> panelList) {
		List roleList = UimsUtils.userTokenClientSide.getRoleList();
		boolean test = false;
		UFixPanelTemplate panel = null;
		if (panelList != null && panelList.size() != 0) {
			for (int i = 0; i < panelList.size(); i++) {
				panel = panelList.get(i);
				// 首先判断角色，如果符合的话就加载Panel，如果不符合的话就不加载
				test = judgePermission(roleList, panel.role);
				if (test)
					break;
			}
		}
		if (test) {
			UPanelI p = workbench[curentWorkbenchIndex].addPanelByTemplateName(
					panel.name, panel.content);
			if (trayIconShow == null) {
				trayIconShow = new TrayIconShow(p, panel);
			}
			addWindowListener(trayIconShow);
		}
	}

	private Boolean judgePermission(List roleList, String role) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] arr = role.split(",");
		for (String a : arr)
			map.put(a, a);
		if (roleList != null && roleList.size() > 0) {
			for (int i = 0; i < roleList.size(); i++) {
				if (map.containsKey(roleList.get(i).toString())) {
					return true;
				}
			}
		}
		return false;
	}

	public class TrayIconShow extends WindowAdapter implements Runnable,
			ActionListener {
		private SystemTray sysTray;// 当前操作系统的托盘对象

		private TrayIcon trayIcon;// 当前对象的托盘

		private int trayNum = 1;

		private Image trayImage = null;
		private UPanelI panel = null;
		private UFixPanelTemplate fixPanelTemplate = null;
		private UTimerHandlerI handler;

		public TrayIconShow(UPanelI panel, UFixPanelTemplate fixPanelTemplate) {
			this.panel = panel;
			this.fixPanelTemplate = fixPanelTemplate;
			handler = (UTimerHandlerI) panel.getHandler();
			// 这时候就要进行注册，把系统信息注册到系统里面
			init();
		}

		public void init() {
			// 添加窗口事件,将托盘添加到操作系统的托盘
			createTrayIcon();
			setLocationRelativeTo(null);
		}

		public void createTrayIcon() {
			sysTray = SystemTray.getSystemTray();// 获得当前操作系统的托盘对象
			// icon = new ImageIcon("1.gif");// 托盘图标
			// trayImage = Toolkit.getDefaultToolkit().getImage(
			// "images/systemTrayIcon.jpg");
			trayImage = UimsUtils.getCSClientImage("systemTrayIcon.jpg");
			PopupMenu popupMenu = new PopupMenu();// 弹出菜单
			MenuItem mi = new MenuItem("弹出");
			MenuItem exit = new MenuItem("关闭");
			popupMenu.add(mi);
			popupMenu.add(exit);
			// 为弹出菜单项添加事件
			mi.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					sysTray.remove(trayIcon);
				}
			});
			exit.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			trayIcon = new TrayIcon(trayImage, "闪动托盘", popupMenu);
			trayIcon.addActionListener(this);
		}

		public void addTrayIcon() {
			try {
				sysTray.add(trayIcon);// 将托盘添加到操作系统的托盘
				// trayIcon.displayMessage("信息", "有新的会议申请！",
				// TrayIcon.MessageType.INFO);
				// new TestThread();
				handler.registerTrayIcon(trayIcon, trayImage);
				setVisible(false);// 使得当前的窗口隐藏
				trayNum = 1;
				handler.setTrayFlag(true);
				// new Thread(this).start();
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}

		public void run() {
			while (trayNum > 0) {
				this.trayIcon.setImage(new ImageIcon("").getImage());
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.trayIcon.setImage(trayImage);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void windowIconified(WindowEvent e) {
			addTrayIcon();
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// 重写actionPerformed方法
			if (e.getSource() == trayIcon) {// 双击托盘图标执行的代码
				sysTray.remove(trayIcon);
				trayNum = 0;
				setVisible(true);
				handler.setTrayFlag(false);
				handler.initAddedData();
				handler.setMessageState(false);
				setExtendedState(Frame.MAXIMIZED_BOTH);
			}
		}

	}

	public void setPanelTimeControlActionAttribute() {
		if (workbench == null || workbench.length == 0)
			return;
		for (int i = 0; i < workbench.length; i++) {
			workbench[i].setPanelTimeControlActionAttribute();
		}
	}
	public void autoStartFirstPanel(){
		addPanelByMenuCmd(this.apptemplate.startPanelName, "","","");		
	}
	public void loadSystemInfoPanel() {
		
		String type = UFactory.getModelSession().getEnvironmentTemplate()
				.getClientSystemNotifyType(UimsUtils.userTokenClientSide.getRoleList());
		UPromptTemplate pro;
		if (type != null) {
			pro = UFactory.getModelSession().getPromptTemplateByName(
					"uimsFriendlyPrompty");
			addPanelByMenuCmd("csSystemPromptMessageBusinessNotifyPanel", "",
					pro.info, pro.infoEng);
		} else {
			pro = UFactory.getModelSession().getPromptTemplateByName(
					"uimsSystemInstruction");
			addPanelByMenuCmd("systemDescriptionPanel", "", pro.info,
					pro.infoEng);
		}
	}

	public void enterWorkBenchByButton(String panelName) {
		if (isMainPanel) {
			if (actionCardLayout != null)
				actionCardLayout.show(this.getContentPane(), "workBench");
			isMainPanel = false;
		}
		UPromptTemplate pro;
		pro = UFactory.getModelSession().getPromptTemplateByName(
				"uimsFriendlyPrompty");
		addPanelByMenuCmd(panelName, "", pro.info, pro.infoEng);
	}


	public void initWindow() {
		
		this.addComponentListener(new ComponentListener(){

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				if(e.getComponent() instanceof UClientFrameControlPanel) {
					((UClientFrameControlPanel)e.getComponent()).ReLayoutInnerComponent();
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public void visibleWindow() {		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void init() {
		// 初始化model
		initTemplate();
		if (apptemplate == null)
			return;
//		initWindow();
		RoleTmenuControlTemplate menuControlTemplate =  this.getMenuControlTemplateName();
		Container c = this.getContentPane();
		if(apptemplate.controlTemplateList == null || apptemplate.controlTemplateList.size() == 0) {
			this.setExtendedState(MAXIMIZED_BOTH);			
			if (UimsFactory.getClientMainI().isEnglishVersion())
				setTitle(apptemplate.title.enContent);
			else
				setTitle(apptemplate.title.content);
			if(menuControlTemplate != null && menuControlTemplate.isDispMenuBar)
				initMenuBar(apptemplate.menuBarTemplate);
			if (apptemplate.mainPanelName != null) {
				c.setLayout(actionCardLayout);
				UPanelTemplate template = (UPanelTemplate) UFactory
						.getModelSession().getTemplate(
								UConstants.MAPKEY_PANEL_FORM,
								apptemplate.mainPanelName);
				mainPanel = (UPanelI) template.newComponent();
				mainPanel.setStartMenuName("mainPanel");
				mainPanel.setOriginalTemplate(null);
				mainPanel.setPathNameString(template.name);
				mainPanel.setTemplate(template);
				mainPanel.init();
				c.add(mainPanel.getAWTComponent(), "mainPanel");
				isMainPanel = true;
				JPanel p = new JPanel();
				c.add(p, "workBench");
				p.setLayout(new BorderLayout());
				c = p;
			} else {
				c.setLayout(new BorderLayout());
			}
			String toolbarName = null;
			if(menuControlTemplate != null)
				toolbarName = menuControlTemplate.toolbarTemplateName;
			if(toolbarName == null)
				toolbarName = apptemplate.toolbarName;
			if (toolbarName != null) {
				UToolbarTemplate toolbarTemplate = (UToolbarTemplate) UFactory
						.getModelSession().getTemplate(UConstants.MAPKEY_TOOLBAR,
								toolbarName);
				initToolBar(toolbarTemplate);
				c.add(toolBar, BorderLayout.NORTH);
				buildTreeTemplate(toolbarTemplate.items,apptemplate.treeMenuTemplate);

			} else if (apptemplate.toolPanelName != null) {
				UPanelTemplate template = (UPanelTemplate) UFactory
						.getModelSession().getTemplate(
								UConstants.MAPKEY_PANEL_FORM,
								apptemplate.toolPanelName);
				toolPanel = (UPanelI) template.newComponent();
				toolPanel.setStartMenuName("mainPanel");
				toolPanel.setOriginalTemplate(null);
				toolPanel.setPathNameString(template.name);
				toolPanel.setTemplate(template);
				toolPanel.init();
				toolPanel.getAWTComponent().setSize(template.width, template.height);
				toolPanel.getAWTComponent().setPreferredSize(
						new Dimension(template.width,template.height));
				c.add(toolPanel.getAWTComponent(), BorderLayout.NORTH);
			}
		}else {
			frameSize = Toolkit.getDefaultToolkit().getScreenSize();			
			if(this.apptemplate == null) {
				setLocation(0, 0);
				setSize(frameSize.width, frameSize.height);
			}else {
				setLocation(apptemplate.ebw, apptemplate.ebw);
				frameSize.width -= apptemplate.ebw*2;
				frameSize.height -= apptemplate.ebw*2;
				setSize(frameSize.width, frameSize.height);
			}
			c.setLayout(new BorderLayout());
			JPanel pw = new JPanel();
			pw.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
			pw.setPreferredSize(frameSize);
			pw.setSize(frameSize);
			c.add(pw,BorderLayout.CENTER);
			c = pw;
			HashMap <String, String>paraMap = new HashMap<String,String>();
			String t0 ="", t1; 
			if (UimsFactory.getClientMainI().isEnglishVersion())
				paraMap.put("title", apptemplate.title.enContent);
			else
				paraMap.put("title", apptemplate.title.content);
			paraMap.put("userName", UimsUtils.userTokenClientSide.getLoginName());
			c.setLayout(new BorderLayout());
			JPanel tp = new JPanel();
			Dimension td;
			if(apptemplate.bw > 1 && apptemplate.bgColor != null) {
				tp = new JPanel();
				td = new Dimension(apptemplate.bw, frameSize.height);
				tp.setSize(td);
				tp.setPreferredSize(td);
				tp.setBackground(UFactory.getModelSession().getColorByName(apptemplate.bgColor).color);
				c.add(tp,BorderLayout.WEST);
				tp = new JPanel();
				td = new Dimension(apptemplate.bw, frameSize.height);
				tp.setSize(td);
				tp.setPreferredSize(td);
				tp.setBackground(UFactory.getModelSession().getColorByName(apptemplate.bgColor).color);
				c.add(tp,BorderLayout.EAST);
			}
			try {
				UClientFrameControlTemplate controlTemplate;
				for(int i = 0; i <apptemplate.controlTemplateList.size();i++) {
					controlTemplate = apptemplate.controlTemplateList.get(i);
					controlPanel = (UClientFrameControlPanel )Class.forName(controlTemplate.className).newInstance();				
					controlPanel.setControlTemplate(controlTemplate);
					controlPanel.setParaMap(paraMap);					
					controlPanel.init();
					c.add(controlPanel,UimsUtils.getBorderLayoutValue(controlTemplate.align));
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		workBenchContainer = new JPanel();
		workBenchContainer.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
		workBenchContainer.setLayout(cardLayout);
		initContents(workBenchContainer);
		if (apptemplate.treeMenuTemplate != null && (menuControlTemplate == null || menuControlTemplate.isDispMenuTree)) {
			c.add(jSplitPane, BorderLayout.CENTER);
			jSplitPane.setDividerLocation(130);
			jSplitPane.add(jScrollPaneLeft, JSplitPane.LEFT);
			initMenuAndTree(apptemplate.treeMenuTemplate, 0);
			jScrollPaneLeft.getViewport().add(treeMenu);
			jSplitPane.add(workBenchContainer, JSplitPane.RIGHT);
		} else {
			c.add(workBenchContainer, BorderLayout.CENTER);
		}
		initStatusBar(apptemplate.statusbarTemplate);
		if (statusBar != null)
			c.add(statusBar, BorderLayout.SOUTH);
		visibleWindow();
		initShortCutKey();
		if (apptemplate != null && apptemplate.fixePanelTemplateList != null)
			initFixPanel(apptemplate.fixePanelTemplateList);
		timerControlActionThread = new UPanelTimerControlActionThread(this);
		timerControlActionThread.start();
		if(apptemplate.startPanelName != null) 
			autoStartFirstPanel();
		else
			loadSystemInfoPanel();
	}

	public UToolBar getToolBar() {
		return toolBar;
	}

	public CommonCommandProcess getCommonProcessListener() {
		return commonProcessListener;
	}

	public void setToolBar(UToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public void setCommonProcessListener(CommonCommandProcess commonProcessListener) {
		this.commonProcessListener = commonProcessListener;
	}
	public Dimension getFrameSize(){
		return frameSize;
	}
	public UPanelI getClientToolPanel(){
		return this.toolPanel;
	}
	public String getApplicationName(){
		return applicationName;
	}
	
	public boolean checkUserPassword(String serverName, UFormHandler sh){
		
		HashMap map = UimsFactory.getClientMainI().getParameterMap();
		if(map == null ) {
			map = new HashMap();
			UimsFactory.getClientMainI().setParameterMap(map);
		}
		Boolean isCheck = (Boolean )map.get("checkPassword-"+ serverName); 
		if(isCheck != null) 
			return true;
		HashMap<String, String> paras = new HashMap<String, String>();
		paras.put("serverName", serverName);
		UDialogPanel dlg = (UDialogPanel) sh.startDialog(
				"authPasswordCheckDialog", null, null, paras);
		if (!dlg.getReturnValue().equals(dlg.RETURN_OK))
			return false;
		else  {
			map.put("checkPassword-"+ serverName, new Boolean(true)); 
			return true;
		}
	}
	public void infomServerExit() {
//		RmiRequest request = new RmiRequest();
//		RmiResponse respond = null;
//		request.setCmd("csExit");
//		respond = UimsUtils.requestProcesser(request);
	}

	public void buildTreeTemplate(UToolTemplate items[], UMenuTemplate menu) {
		if (items == null || items.length == 0 || menu == null)
			return;
		List<UMenuItemTemplate> tempList = new ArrayList<UMenuItemTemplate>(
				items.length);
		UMenuItemTemplate itemTemplate = null;
		for (int i = 0; i < items.length; i++) {
			itemTemplate = new UMenuItemTemplate();
			itemTemplate.menuNo = items[i].menuNo;
			itemTemplate.content = items[i].content;
			itemTemplate.enContent = items[i].enContent;
			itemTemplate.name = items[i].name;
			itemTemplate.csmType = items[i].csmType;
			itemTemplate.cmd = items[i].cmd;
			itemTemplate.target = items[i].target;
			itemTemplate.handler = "";
			itemTemplate.defaultSelected = false;
			tempList.add(itemTemplate);
		}
		menu.addItems(tempList);
	}
	
}
