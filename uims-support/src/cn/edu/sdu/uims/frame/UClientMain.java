package cn.edu.sdu.uims.frame;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.sdu.rmi.RmiClientRequest;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.rmi.UserTokenClientSide;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormIdI;
import cn.edu.sdu.common.pi.ClientInitPlugInI;
import cn.edu.sdu.common.pi.ModelSessionBaseI;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.dialog.UOldDialogBase;
import cn.edu.sdu.uims.component.menu.UMenuInfo;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.constant.UimsConstants;
import cn.edu.sdu.uims.form.UTextFieldDataFormI;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.form.ClientInfo;
import cn.edu.sdu.uims.frame.form.LoginForm;
import cn.edu.sdu.uims.pi.ClientMainI;
import cn.edu.sdu.uims.pi.ImageDataDriverI;
import cn.edu.sdu.uims.pi.UClientFrameI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.service.UHandlerSessionClient;
import cn.edu.sdu.uims.service.UHandlerSessionI;
import cn.edu.sdu.uims.service.UModelSessionI;
import cn.edu.sdu.uims.service.UModelSessionRemote;
import cn.edu.sdu.uims.service.UModelSessionServer;
import cn.edu.sdu.uims.util.UimsUtils;
import javafx.application.Platform;

public class UClientMain implements ClientMainI {
	
	private  List<ClientInitPlugInI> plugInList = new ArrayList<ClientInitPlugInI>();
	private static UClientMain instance;
	protected UserClientInfo userClientInfo = new UserClientInfo();
	protected String rmiURL = null;
	protected UDialogPanel loginDlg = null;
	protected UClientFrame clientFrame = null;
	protected UClientDialog clientDialog = null;
	protected String startupProcessMessage = "";
	private ClientStartupProcessDlg clientStartupProcessDlg;// = new
	private String applicationBasePath = "/cn/edu/sdu/";
	private List menuList = null;
	private ClientInfo clientInfo;
	private HashMap parameterMap = null;
	private boolean requestIsLog = false;
	private Clipboard clipboard = Toolkit.getDefaultToolkit()
			.getSystemClipboard();

	public UClientMain() {
		instance = this;

	}

	public static UClientMain getInstance() {
		return instance;
	}

	public String getStartupProcessMessage() {
		return this.startupProcessMessage;
	}

	public String getUserClientInfoLoginName() {
		return userClientInfo.loginName;
	}

	public String getUserClientInfoPwd() {
		return userClientInfo.pwd;
	}

	public void setLoginName(String loginName) {
		userClientInfo.loginName = loginName;
	}

	public void setPwd(String pwd) {
		userClientInfo.pwd = pwd;
	}

	public boolean isEnglishVersion() {
		return userClientInfo.isEnglishVersion;
	}

	public void setEnglishVersion(boolean isEnglishVersion) {
		userClientInfo.isEnglishVersion = isEnglishVersion;
		ListOptionInfo.isEnglishVersion = isEnglishVersion;
	}

	public String[] getAssistServers() {
		return userClientInfo.assistServers;
	}

	public void setAssistServers(String[] assistServers) {
		userClientInfo.assistServers = assistServers;
	}

	public void setRunInLocal(boolean runInLocal) {
		userClientInfo.runInLocal = runInLocal;
	}

	public boolean getRunInLocal() {
		return userClientInfo.runInLocal;
	}

	public void setLoginInLocal(boolean loginInLocal) {
		userClientInfo.loginInLocal = loginInLocal;
	}

	public boolean getLoginInLocal() {
		return userClientInfo.loginInLocal;
	}
	
	public void setModelIsLocal(boolean modelIsLocal) {
		userClientInfo.modelIsLocal = modelIsLocal;
	}

	public boolean getModelIsLocal() {
		return userClientInfo.modelIsLocal;
	}

	public void setRoleId(Integer roleId) {
		userClientInfo.roleId = roleId;
	}

	public Integer getRoelId() {
		return userClientInfo.roleId;
	}

	public void setLoginDlg(UDialogPanel loginDlg) {
		this.loginDlg = loginDlg;
	}

	public void setFrameIsDialog(boolean frameIsDialog) {
		userClientInfo.frameIsDialog = frameIsDialog;
	}

	public boolean getFrameIsDialog() {
		return userClientInfo.frameIsDialog;
	}

	public void setNeedLogin(boolean needLogin) {
		userClientInfo.needLogin = needLogin;
	}

	public void setStartupProcessMessage(String startupProcessMessage) {
		this.startupProcessMessage = startupProcessMessage;
	}

	public String getEnvironmentName() {
		return userClientInfo.environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		userClientInfo.environmentName = environmentName;
	}

	protected void setLoginDlg() {
		// TODO Auto-generated method stub
	}

	protected void setClientFrame() {
		// TODO Auto-generated method stub
		if (!userClientInfo.frameIsDialog) {
			this.clientFrame = new UClientFrame();
			if (!userClientInfo.isNeedFrameTitle)
				clientFrame.setUndecorated(true);
		} else {
			this.clientDialog = new UClientDialog();
		}
		
	}

	private void excutePlugIn() {
		if (clientStartupProcessDlg != null)
			clientStartupProcessDlg.messageLabel
					.setText(startupProcessMessage);
		for (int i = 0; i < plugInList.size(); i++) {

			// this.startupProcessMessage = plugInList.get(i)
			// .getStartupProcessMessage();
			if(plugInList != null && plugInList.get(i) != null)
				plugInList.get(i).init();
		}
	}

	public  void addPlugIn(ClientInitPlugInI plugIn) {
		plugInList.add(plugIn);
	}

	public  void addData(ClientInitPlugInI plugIn) {
		plugInList.add(plugIn);
	}

	private void initRMI() throws Exception {
		MyProperties myProperties = MyProperties.createProperties();
		String serverAddress = myProperties.getProperty(MyProperties.SERVER);
		RmiClientRequest.getClientRequest().init(serverAddress);
		rmiURL = serverAddress;
	}

	private void initServerRMI() throws Exception {
		MyProperties myProperties = MyProperties.createProperties();
		String serverAddress;
		if (userClientInfo.assistServers != null
				&& userClientInfo.assistServers.length != 0) {
			for (int i = 0; i < userClientInfo.assistServers.length; i++) {
				serverAddress = myProperties
						.getProperty(userClientInfo.assistServers[i]);
				if (serverAddress != null && !serverAddress.equals(""))
					RmiClientRequest.getClientRequest().addSysService(
							userClientInfo.assistServers[i], serverAddress);
			}
		}
	}

	private void login() {
		UTemplate temp;
		String loginName;
		EnvironmentTemplate environ = UFactory.getModelSession()
				.getEnvironmentTemplate();
		loginName = environ.getCsLoginName(userClientInfo.loginDialogNameKey);
		temp = (UTemplate) UFactory.getModelSession().getTemplate(
				UConstants.MAPKEY_DIALOG, loginName);
		if (temp == null)
			return;
		loginDlg = new UDialogPanel();

		loginDlg.setComponentName(loginName);
		// loginDlg.setdisplayText(text);
		loginDlg.setTemplate(temp);
		loginDlg.init();
		if (isEnglishVersion())
			loginDlg.setDialogTitle(environ.applicationEnTitle);
		else
			loginDlg.setDialogTitle("欢迎登录" + environ.applicationTitle);
	}

	private void setLoginName() {
		if (loginDlg != null) {
			LoginForm form = (LoginForm) loginDlg.getFormfromPanel();
			userClientInfo.loginName = form.getStrLoginName();
			loginDlg.removeHandler();
		}
	}

	private class TitleImageBackPanel extends JPanel {
		public TitleImageBackPanel() {
			setBackground(new Color(255, 255, 255));
		}

		public void paint(Graphics g) {
			super.paint(g);
			Icon image = UimsUtils
					.getCSClientIcon("sdustaffisp-logo-bg.png");
			if (image != null) {
				int imageW = image.getIconWidth();
				int imageH = image.getIconHeight();
				int w = this.getWidth();
				int h = this.getHeight();
				// g.clearRect(0, 0, w, h);
				image.paintIcon(this, g, (w - imageW) / 2, (h - imageH) / 2);
				paintComponents(g);
			}
		}
	}

	private void showClientFrame() {
		if (clientFrame == null && clientDialog == null)
			return;
		if (clientStartupProcessDlg != null) {
			if (startupProcessMessage == null
					|| startupProcessMessage.length() == 0)
				this.startupProcessMessage = "应用程序窗口初始化，请稍等...";
			clientStartupProcessDlg.messageLabel.setText(startupProcessMessage);
		}
		if (!userClientInfo.frameIsDialog) {
			clientFrame.init();
			getLoginName();
			String version = getClientVersion();
			if(version != null && version.length() != 0) {
				version = "(V" + version + ")";
			}
			if (userClientInfo.loginName == null) {
				clientFrame.setTitle(clientFrame.getTitle()+version);
			} else {
				clientFrame.setTitle(clientFrame.getTitle() + version + "——"
						+ userClientInfo.loginName);
			}
			clientFrame.setLocationRelativeTo(null);
			if (clientStartupProcessDlg != null) {
				startupProcessMessage = "endProcess";
				clientStartupProcessDlg.messege = "endProcess";
			}
			clientFrame.setCursor(Cursor.DEFAULT_CURSOR);
			clientFrame.setVisible(true);
		} else {
			clientDialog.init();
			getLoginName();
			if (userClientInfo.loginName != null && userClientInfo.needLogin) {
				clientDialog.setTitle(clientDialog.getTitle() + "——"
						+ userClientInfo.loginName);
			}
			clientDialog.setLocationRelativeTo(null);
			if (clientStartupProcessDlg != null) {
				this.startupProcessMessage = "endProcess";
				clientStartupProcessDlg.messege = "endProcess";
			}
			clientDialog.setVisible(true);

		}

	}
	private void getLoginName() {
		if (loginDlg != null) {
			LoginForm form = (LoginForm) loginDlg.getFormfromPanel();
			userClientInfo.loginName = form.getStrLoginName();
			loginDlg.removeHandler();
		}
	}

	public void addUserSystemPlugIn() {

	}

	private void initUserClient(String[] args) {
	}

	private void setLocalUserInfo() {
	}

	public void runApp(String args[]) {
		Platform.setImplicitExit(false);
		if (args != null && args.length >= 1)
			userClientInfo.serAttribute(args[0]);
		UModelSessionI modelSession = null;
		UHandlerSessionI handlerSession = new UHandlerSessionClient();
		try {
			if (userClientInfo.runInLocal) {
				addPlugIn(getClientDataDictionaryPlugIn());
				addPlugIn(new ClientDataInitPlugIn());
				addUserSystemPlugIn();
				initServerRMI();
				modelSession = new UModelSessionServer();
				modelSession.setClient(true);
				UFactory.setModelSession(modelSession);
				modelSession.initApplicationModels(applicationBasePath,
						userClientInfo.environmentName);
				UFactory.setHandlerSession(handlerSession);
				setModelSessionBaseI(modelSession);
				setClientFrame();
				setClientMain();
				setLocalUserInfo();
				if (userClientInfo.needLogin)
					login();
				excutePlugIn();				
			} else {
				initRMI();
				if (userClientInfo.modelIsLocal) {
					modelSession = new UModelSessionServer();
					modelSession.setClient(true);
					UFactory.setModelSession(modelSession);
					modelSession.initApplicationModels(applicationBasePath,
							userClientInfo.environmentName);
					UFactory.setHandlerSession(handlerSession);
				} else {
					modelSession = new UModelSessionRemote();
					UFactory.setModelSession(modelSession);
					UFactory.setHandlerSession(handlerSession);
				}
				setLoginDlg();
				setClientFrame();
				setClientMain();
				
				addPlugIn(getClientDataDictionaryPlugIn());
				addPlugIn(new ClientDataInitPlugIn());
				addUserSystemPlugIn();
				initServerRMI();
				if (userClientInfo.needLogin) {
					login();
					clientStartupProcessDlg = new ClientStartupProcessDlg();
					if (startupProcessMessage == null
							|| startupProcessMessage.length() == 0)
						this.startupProcessMessage = "从服务器获取菜单列表，请稍等...";
					clientStartupProcessDlg.messageLabel
							.setText(startupProcessMessage);
				} else {
					InetAddress addr = InetAddress.getLocalHost();
					String hostIp = addr.getHostAddress().toString();

					RmiRequest request = new RmiRequest();
					request.setCmd("login");
					request.add("loginName", "userClientInfo.loginName");
					RmiResponse respond = RmiClientRequest.getClientRequest().request(request);
					UimsUtils.userTokenClientSide = (UserTokenClientSide)respond.get("userTokenClientSide");
					if (respond.getErrorMsg() == null) {
						UClientMain.getInstance()
								.setClientSectionObject(respond);
					}

				}
				if (!isUserHaveAuth(menuList)) {
					JOptionPane.showMessageDialog(null, "对不起,您没有客户端功能");
					return;
				}
				excutePlugIn();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "系统启动错误!");
		}
		showClientFrame();
		userPromptBeforeStartFrame();
	}

	public void initApplicationContext() throws Exception {

	}

	/**
	 * 根据权限列表判断用户是否至少有一个菜单的权限。
	 *
	 * @param menuList
	 * @return 如果用户至少看到一个菜单返回true ，否则返回false
	 */
	public boolean isUserHaveAuth(List menuList) {
		boolean flag = true;
		if (menuList != null) {
			int size = menuList.size();
			UMenuInfo menuInfo = null;
			for (int i = 0; i < size; i++) {
				menuInfo = (UMenuInfo) menuList.get(i);
				if (menuInfo.menuRight != null
						&& !menuInfo.menuRight
								.equals(UimsConstants.AuthFlag_InVisible)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public void setClientAddedSectionObject(RmiResponse respond) {

	}

	public void setClientSectionObject(RmiResponse respond) {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String hostIp = addr.getHostAddress().toString();
			String hostName = addr.getHostName().toString();
			// 加入客户机信息
			clientInfo = new ClientInfo();
			clientInfo.setHostIp(hostIp);
			clientInfo.setHostName(hostName);
			setClientAddedSectionObject(respond);
			if (!userClientInfo.frameIsDialog) {
				menuList = (List) respond.get(RmiKeyConstants.KEY_ARRAYLIST);
				List menuList_Menu = (List) respond.get("KEY_MENU_LIST_MENU");
				List menuList_Tree = (List) respond.get("KEY_MENU_LIST_TREE");
				UClientFrame.getInstance().setMenuListMenu(menuList_Menu);
				UClientFrame.getInstance().setMenuListTree(menuList_Tree);
				parameterMap = (HashMap) respond.get("clientParameterMap");// 无过滤的menu
				
			} else {

			}
		} catch (Exception e) {

		}
	}

	/**
	 * 在session中增加clientmain对象
	 */
	protected void setClientMain() {
		UimsFactory.setClientMainI(this);
	}

	public void reLogin() {

	}

	public void quit() {

	}

	public static void main(String[] args) {
		UClientMain clientMain = new UClientMain();
		clientMain.runApp(args);

	}

	public UClientDialog getClientDialog() {
		return clientDialog;
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

	public void addObjectSystemRequestAttribute(RmiRequest req) {
	}

	public ImageDataDriverI getImgeDataDriver() {
		return null;
	}

	public int showConfirmDialog(String message) {
		return UimsUtils.messageBoxChoice(message);
	}

	public void showMessageDialog(String message) {
		UimsUtils.messageBoxInfo(message);
	}

	public void userPromptBeforeStartFrame() {
	}

	public UPanelI startDialog(String name) {
		UTemplate temp;
		String className = null;
		UOldDialogBase p = null;
		try {
			temp = (UTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_DIALOG, name);
			UDialogPanel item = (UDialogPanel) temp.newComponent();

			if (item != null) {
				// item.setOkDisplay(okDisplay);
				// item.setCancelDisplay(cancelDisplay);
				// item.SetOwner(ownerI);
				item.setComponentName(name);
				item.setTemplate(temp);
				// item.setDialogForm(form);
				item.init();
				// if (component != null)
				// item.addActionListener(component.getEventAdaptor());
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(className);
		}
		return null;
	}

	public void shutdown() {

	}
	
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	public Logger getLogger(){
		return null;
	}

	@Override
	public HashMap getSysRolePanelMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWebSeverUrl() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getClientParamerter(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	public ClientInitPlugInI getClientDataDictionaryPlugIn(){
		return null;
	}
	public List getMenuList(){
		return menuList;
	}

	@Override
	public HashMap getParameterMap() {
		// TODO Auto-generated method stub
		return parameterMap;
	}

	@Override
	public void setParameterMap(HashMap map) {
		// TODO Auto-generated method stub
		this.parameterMap = map;
	}


	@Override
	public UClientFrameI getUClientFrame() {
		// TODO Auto-generated method stub
		return this.clientFrame;
	}

	@Override
	public boolean requestIsLog() {
		// TODO Auto-generated method stub
		return requestIsLog;
	}
	public void setRequestIsLog(boolean requestIsLog) {
		// TODO Auto-generated method stub
		this.requestIsLog = requestIsLog;
	}

	@Override
	public Integer getManageCollegeId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UTextFieldDataFormI creatUTextFieldDataForm() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getClientVersion(){
		return null;
	}

	@Override
	public UFormIdI creatNewsInfoForm() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setModelSessionBaseI( ModelSessionBaseI modelSessionBaseI){
		
	}

	@Override
	public Boolean isFileDataInDB() {
		// TODO Auto-generated method stub
		return false;
	}
}
