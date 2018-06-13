package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.sdu.rmi.RmiClientRequest;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.rmi.UserTokenClientSide;

import cn.edu.sdu.common.util.Base64;
import cn.edu.sdu.uims.component.button.UButton;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.UEventObject;
import cn.edu.sdu.uims.component.menu.UMenuInfo;
import cn.edu.sdu.uims.component.textfield.UPasswordField;
import cn.edu.sdu.uims.component.textfield.UTextField;
import cn.edu.sdu.uims.constant.UimsConstants;
import cn.edu.sdu.uims.frame.UClientMain;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.def.UserTemplate;
import cn.edu.sdu.uims.frame.form.LoginForm;
import cn.edu.sdu.uims.frame.form.PasswdForm;
import cn.edu.sdu.uims.frame.form.SysUserCForm;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.service.UModelSessionI;
import cn.edu.sdu.uims.util.UimsUtils;

public class ULoginDialogHandler extends UDialogHandler {
	public static final String RUNDIR="c:\\run\\";
	private  LoginForm getDefautLgoinForm(){
		String fileName = RUNDIR + UFactory.getModelSession().getEnvironmentTemplate().name +".log";
		File file = new File(fileName);
		if(!file.exists()) 
			return null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			LoginForm f = new LoginForm();
			String str = in.readLine();
			f.setStrLoginName(str);
			str = in.readLine();
			f.setStrPassword(str);
			f.setIsSave(true);
			in.close();
			return f;
		}catch(Exception e){
			return null;
		}
	}
	private void saveLoginFormToFile(){
		LoginForm f = (LoginForm) dataForm;
		String fileName = RUNDIR + UFactory.getModelSession().getEnvironmentTemplate().name +".log";	
		File dir = new File(RUNDIR);
		if (dir.exists()) {// 判断目录是否存在
//			System.out.println("创建目录失败，目标目录已存在！");
		}else {
			if (dir.mkdirs()) {// 创建目标目录
				System.out.println("创建目录成功！" + RUNDIR);	
			} else {
				System.out.println("创建目录失败！");
			}
		}
		File file = new File(fileName);
		if(f.getIsSave()== null || !f.getIsSave().booleanValue()) {
			if(!file.exists()) 
				return;
			else {
				file.delete();
			}
		}else {
			try {
				PrintWriter out = new PrintWriter(file);
				out.println(f.getStrLoginName());
				out.println(f.getStrPassword());
				out.close();
			}catch(Exception e){
			}
		}
	}
	public void start(){
		LoginForm f = getDefautLgoinForm();
		if(f != null)
			formToComponent(f);
	}
	public List processWindowEvent(UEventObject e, String cmd) {
		if (cmd.equals(EventConstants.CMD_CLOSING)) {
			System.exit(0);
		}
		return null;
	}

	public List processKeyEvent(UEventObject o, String cmd) {
		KeyEvent e = (KeyEvent) o.getEventObject();
		if (cmd.equals(EventConstants.CMD_TYPEE)) {
			if ((int) e.getKeyChar() == 10) {
				processLongin();
			}
		}
		return null;
	}

	public void processLongin() {
		this.componentToForm();
		LoginForm form = (LoginForm) dataForm;
		if ("".equals(form.getStrLoginName())) {
			UTextField field = (UTextField) component
					.getSubComponent("userNameField");
			field.requestFocus();
			UClientMain.getInstance().showMessageDialog("请输入用户名");
			return;
		}
		if ("".equals(form.getStrPassword())) {
			UPasswordField field = (UPasswordField) component
					.getSubComponent("passwordField");
			field.requestFocus();
			UClientMain.getInstance().showMessageDialog("请输入密码");
			return;
		}
		String user = form.getStrLoginName();
		String pass = form.getStrPassword();
		login(user, pass);

	}

	public List processActionEvent(UEventObject o, String cmd) {
		ActionEvent e = (ActionEvent) o.getEventObject();

		String command = e.getActionCommand();
		UDialogPanel dlg = (UDialogPanel) component;
		if (e.getSource() == component.getSubComponent("userNameField")) {
			UPasswordField pt = (UPasswordField) component
					.getSubComponent("passwordField");
			pt.requestFocus();
		} else if (e.getSource() == component.getSubComponent("passwordField")) {
			UButton b = (UButton) component.getSubComponent("loginButton");
			b.requestFocus();
		} else if (command.equals("loginCommand")) {
			processLongin();
		} else if (command.equals("cancelCommand")) {
			System.exit(0);
		}
		return null;
	}

	public void clearInput() {
		LoginForm form = (LoginForm) dataForm;
		form.setStrPassword("");
		this.formToComponent();
		UPasswordField field = (UPasswordField) component
				.getSubComponent("passwordField");
		field.requestFocus();
	}

	public boolean isUserHaveAuth(List menuList) {
		boolean flag = false;
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

	public void closeDialog() {
		UDialogPanel dlg = (UDialogPanel) component;
		dlg.doClose();

	}
	protected void login(String userName, String password) {
		// TODO Auto-generated method stub
		// 获得主机IP和名称
		boolean loginInLocal = UClientMain.getInstance().getLoginInLocal();
		if(loginInLocal) {
			String name = UClientMain.getInstance().getUserClientInfoLoginName();
			String pwd = UClientMain.getInstance().getUserClientInfoPwd();
			if(name != null && !name.equals("")) {
				if(password.equals(pwd) && name.equals(userName)) {
					closeDialog();
				}  
				else {
					UClientMain.getInstance().showMessageDialog("用户名或者密码错误！");
					return;					
				}
			}else {
				UModelSessionI mi = UFactory.getModelSession();
				if(mi == null) {
					UClientMain.getInstance().showMessageDialog("用户名或者密码错误！");
					return;
				}
				EnvironmentTemplate evr= mi.getEnvironmentTemplate();
				if(evr == null || evr.userMap == null)  {
					UClientMain.getInstance().showMessageDialog("用户名或者密码错误！");
					return;
				}
				UserTemplate user = evr.userMap.get(userName);
				if(user.pw.equals(password)) {
					closeDialog();				
				}
			}
		}else  
		{
			try {
				InetAddress addr = InetAddress.getLocalHost();
				String hostIp = addr.getHostAddress().toString();
				// 加入客户机信息
	
				SysUserCForm form = new SysUserCForm();
				form.setLoginName(userName);
				form.setPassword(password);
				form.setHostIp(hostIp);
				RmiRequest request = new RmiRequest();
				request.setCmd("login");
				request.add("loginName", form.getLoginName());
				request.add("password", form.getPassword());
				request.add("clientVersion", UClientMain.getInstance().getClientVersion());
				RmiResponse respond = RmiClientRequest.getClientRequest().request(request);
				if (respond.getErrorMsg() == null) {
					UClientMain.getInstance().setClientSectionObject(respond);
					UserTokenClientSide cs = (UserTokenClientSide)respond.get("userTokenClientSide");
					if(cs == null) {
						clearInput();
						UClientMain.getInstance().showMessageDialog("登录错误！");																		
					}else  {
						if(cs.getErrorMessageList() == null || cs.getErrorMessageList().size() == 0) {
							UimsUtils.userTokenClientSide = cs;
							saveLoginFormToFile();
							closeDialog();
						}else {
							String msg = "";
							for(int k = 0; k < cs.getErrorMessageList().size();k++) {
								msg += cs.getErrorMessageList().get(k);
								if(k <cs.getErrorMessageList().size()-1)
									msg += ";";
							}
							clearInput();
							UClientMain.getInstance().showMessageDialog(msg);													
						}
					}
				} else {
					if(respond.getErrorMsg().equals("weakpasswordchange")) {
						PasswdForm pForm = new PasswdForm();
						pForm.setEnterNewLoginName(userName);
						UDialogPanel dlg = (UDialogPanel)this.startDialog("userPasswordChangeDialog",pForm);
						if(dlg.getReturnValue().equals(dlg.RETURN_OK)) {
							pForm = (PasswdForm)dlg.getDialogForm();
							form.setLoginName(userName);
							form.setPassword(new String (Base64.decode(pForm.getEnterNewPwd().getBytes())));
							request = new RmiRequest();
							request.setCmd("login");
							request.add("loginName", form.getLoginName());
							request.add("password", form.getPassword());
							respond = RmiClientRequest.getClientRequest().request(request);
							if(respond.getErrorMsg() == null) {
								UClientMain.getInstance().setClientSectionObject(respond);
								UserTokenClientSide cs = (UserTokenClientSide)request.get("userTokenClientSide");
								if(cs.getErrorMessageList() != null && cs.getErrorMessageList().size() >0) {
									String msg = "";
									for(int k = 0; k < cs.getErrorMessageList().size();k++) {
										msg += cs.getErrorMessageList().get(k);
										if(k <cs.getErrorMessageList().size()-1)
											msg += ";";
									}
									clearInput();
									UClientMain.getInstance().showMessageDialog(msg);							
								}else {
									UimsUtils.userTokenClientSide = cs;
									saveLoginFormToFile();
									closeDialog();
								}
							}else {
								clearInput();
								UClientMain.getInstance().showMessageDialog(respond.getErrorMsg());								
							}
						}else {
							clearInput();
							UClientMain.getInstance().showMessageDialog(respond.getErrorMsg());															
						}
					}else {
						clearInput();
						UClientMain.getInstance().showMessageDialog(respond.getErrorMsg());
					}
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
