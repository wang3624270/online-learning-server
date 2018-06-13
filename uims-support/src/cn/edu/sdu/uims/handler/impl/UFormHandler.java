package cn.edu.sdu.uims.handler.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.FormI;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.CallBackStruct;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.dialog.UOldDialogBase;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.flex.FNameObjectPar;
import cn.edu.sdu.uims.form.impl.CommonProgressDataForm;
import cn.edu.sdu.uims.form.impl.UMutiInfoForm;
import cn.edu.sdu.uims.form.impl.UserVisitAppInfoForm;
import cn.edu.sdu.uims.frame.UClientFrame;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.FilePrintUtil;
import cn.edu.sdu.uims.util.UimsUtils;

public class UFormHandler extends UFormHandlerBase {
	protected UPanelI component = null;

	public UFormHandler() {
	}

	public UPanelI getComponent() {
		return component;
	}

	public void setComponent(UPanelI component) {
		this.component = component;
	}

	public void setComponent(UComponentI com) {
		component = (UPanelI) com;
	}

	public boolean componentToForm() {
		if (component != null) {
			if (component.testInvalidateData())
				return false;
			component.getData();
		}
		return true;
	}

	public void formToComponent() {
		component.setData(dataForm);
	}

	public void formToComponent(FormI form) {
		this.setForm(form);
		formToComponent();
	}

	public Object getSubComponentData(String name) {

		UComponentI com = component.getSubComponent(name);
		return com.getData();
	}

	public void setSubComponentData(String name, Object o) {
		UComponentI com = component.getSubComponent(name);
		com.setData(o);
	}

	public void sendActionEventToParent(String cmd) {
		component.sendActionEventToParent(cmd);
	}

	public void updateAddedData() {
		UPanelI p = (UPanelI) this.component;
		p.updateAddedDatas();
	}

	public void updateAddedData(String name) {
		UPanelI p = (UPanelI) this.component;
		p.updateAddedData(name);

	}

	public UPanelI startDialog(String name, UFormI form, UComponentI ownerI) {
		return startDialog(name, form, ownerI, true, true);
	}

	public UPanelI startDialog(String name, UFormI form, UComponentI ownerI,
			boolean okDisplay, boolean cancelDisplay) {
		UTemplate temp;
		String className = null;
		UOldDialogBase p = null;
		try {
			className = (String) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_PANEL_OLDCSPANEL, name);
			if (className != null) {
				p = (UOldDialogBase) Class.forName(className).newInstance();
				p.setComponentName(name);
				p.setDialogForm(form);
				p.init();
				p.addActionListener(component.getEventAdaptor());
				p.setModal(true);
				p.setVisible(true);
				return p;
			}
			temp = (UTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_DIALOG, name);
			UDialogPanel item = (UDialogPanel) temp.newComponent();

			if (item != null) {
				item.setOkDisplay(okDisplay);
				item.setCancelDisplay(cancelDisplay);
				item.SetOwner(ownerI);
				item.setComponentName(name);
				item.setTemplate(temp);
				item.setDialogForm(form);
				item.init();
				if (component != null)
					item.addActionListener(component.getEventAdaptor());
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(className);
		}
		return null;
	}

	public UPanelI startDialog(String name, UFormI form, UComponentI ownerI,
			HashMap paras) {
		UTemplate temp;
		String className = null;
		UOldDialogBase p = null;
		try {
			className = (String) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_PANEL_OLDCSPANEL, name);
			if (className != null) {
				p = (UOldDialogBase) Class.forName(className).newInstance();
				p.setComponentName(name);
				p.setDialogForm(form);
				p.init();
				p.addActionListener(component.getEventAdaptor());
				p.setModal(true);
				p.setVisible(true);
				return p;
			}
			temp = (UTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_DIALOG, name);
			UDialogPanel item = (UDialogPanel) temp.newComponent();

			if (item != null) {
				item.setParameters(paras);
				item.SetOwner(ownerI);
				item.setComponentName(name);
				item.setTemplate(temp);
				item.setDialogForm(form);
				item.init();
				if (component != null)
					item.addActionListener(component.getEventAdaptor());
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(className);
		}
		return null;
	}

	public UPanelI startDialog(String name, UFormI form, UHandlerI ownerHandler) {
		UTemplate temp;
		String className = null;
		UOldDialogBase p = null;
		try {
			className = (String) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_PANEL_OLDCSPANEL, name);
			if (className != null) {
				p = (UOldDialogBase) Class.forName(className).newInstance();
				p.setComponentName(name);
				p.setDialogForm(form);
				p.init();
				p.addActionListener(component.getEventAdaptor());
				p.setModal(true);
				p.setVisible(true);
				return p;
			}
			temp = (UTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_DIALOG, name);
			UDialogPanel item = (UDialogPanel) temp.newComponent();

			if (item != null) {
				// item.SetOwner(((UFormHandler)ownerHandler).getComponent().getAWTComponent());
				item.SetOwner(null);
				item.setOwnerHandler(ownerHandler);
				item.setComponentName(name);
				item.setTemplate(temp);
				item.setDialogForm(form);
				item.init();
				if (component != null)
					item.addActionListener(component.getEventAdaptor());
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(className);
		}
		return null;
	}

	public UPanelI startDialog(String name, UFormI form, UComponentI ownerI,
			UHandlerI ownerHandler) {
		return startDialog(name, form, ownerI, ownerHandler, null);
	}

	public UPanelI startDialog(String name, UFormI form, UComponentI ownerI,
			UHandlerI ownerHandler, HashMap paras) {
		UTemplate temp;
		String className = null;
		UOldDialogBase p = null;
		try {
			className = (String) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_PANEL_OLDCSPANEL, name);
			if (className != null) {
				p = (UOldDialogBase) Class.forName(className).newInstance();
				p.setComponentName(name);
				p.setDialogForm(form);
				p.init();
				p.addActionListener(component.getEventAdaptor());
				p.setModal(true);
				p.setVisible(true);
				return p;
			}
			temp = (UTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_DIALOG, name);
			UDialogPanel item = (UDialogPanel) temp.newComponent();

			if (item != null) {
				item.setParameters(paras);
				item.SetOwner(ownerI);
				item.setOwnerHandler(ownerHandler);
				item.setComponentName(name);
				item.setTemplate(temp);
				item.setDialogForm(form);
				item.init();
				if (component != null)
					item.addActionListener(component.getEventAdaptor());
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(className);
		}
		return null;
	}

	public void mutiInfoSHowDialog(List list, UComponentI ownerI) {

		startDialog("uimsMutiInfomShowDialog", new UMutiInfoForm(list), ownerI);
	}

	public void startPanel(String name, String content) {
		UClientFrame.getInstance().startPanelByHandler(name, content);
	}

	public HashMap getParameters() {
		// TODO Auto-generated method stub
		if (component == null)
			return null;
		else
			return component.getParameters();
	}

	public String getParameter(String key) {
		// TODO Auto-generated method stub
		if (component == null)
			return null;
		else {
			HashMap map = getParameters();
			if (map == null)
				return null;
			else {
				Object o = map.get(key);
				if (o != null)
					return o.toString();
				else
					return null;
			}
		}
	}
	public Object getParameterObject(String key) {
		// TODO Auto-generated method stub
		if (component == null)
			return null;
		else {
			HashMap map = getParameters();
			if (map == null)
				return null;
			else {
				return  map.get(key);
			}
		}
	}

	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub
		if (component != null)
			component.setParameters(paras);
	}

	public void doBeforePanelClosed() {

	}

	public UComponentI getComponentByActionCommandString(String str) {
		UComponentI[] coms = component.getAllSubComponents();
		for (int i = 0; i < coms.length; i++)
			if (coms[i].getActionComandString().equals(str)) {
				return coms[i];
			}
		return null;
	}

	public RmiResponse request(RmiRequest request) {
		RmiResponse response = UimsUtils.requestProcesser(
				request);
		return response;
	}

	public void addStartDialogCommand(CallBackStruct stru, String templateName,
			List parDataList) {
		FNameObjectPar obj = new FNameObjectPar();
		obj.name = "startDialog";
		List parList = new ArrayList();
		parList.add(templateName);
		parList.add(parDataList);
		obj.ob = parList;
		stru.commandList.add(obj);
	}

	public void sendToFrontHandler(CallBackStruct stru, String methodName,
			List parDataList) {
		FNameObjectPar obj = new FNameObjectPar();
		obj.name = "sendToFrontHandler";
		List parList = new ArrayList();
		parList.add(methodName);
		parList.add(parDataList);
		obj.ob = parList;
		stru.commandList.add(obj);
	}

	public void reportPdfPrint(String exportName, String fileName, String cmd,
			HashMap cmdParaMap) {
		RmiRequest request = new RmiRequest();
		ArrayList printList = new ArrayList();
		request.add("exportName", exportName);
		request.add("exportType", "1094");
		request.add("doctype", "pdf");
		request.add("isPdfStream", "true");
		request.add("fileName", fileName);
		request.add("font", "songti");
		String key;
		if (cmdParaMap != null) {
			Iterator it = cmdParaMap.keySet().iterator();
			while (it.hasNext()) {
				key = (String) it.next();
				request.add(key, cmdParaMap.get(key));
			}
		}
		request.setCmd(cmd);
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			byte[] a = (byte[]) respond.get("pdfStream");
			try {
				FilePrintUtil.printPDFFile(a, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, respond.getErrorMsg());
		}
	}

	public void doTablePageDataQuery(String name) {
		UTablePanel tablePanel = (UTablePanel) (getComponent()
				.getSubComponent(name));
		tablePanel.doTablePageDataQuery();
	}

	public Object getSelectedValue(String name) {
		UComponentI com = this.component.getSubComponent(name);
		if (com == null)
			return null;
		else
			return com.getSelectedValue();
	}

	public int[] getSelectedIndices(String name) {
		UComponentI com = this.component.getSubComponent(name);
		if (com == null)
			return null;
		else
			return com.getSelectedIndices();
	}

	@Override
	public void resetPanelData() {
		// TODO Auto-generated method stub
		this.componentToForm();
	}

	public void downFile(String remoteFilePath, String localFilePath) {
		CommonProgressDataForm dlgForm = new CommonProgressDataForm();
		HashMap map = new HashMap();
		map.put("remoteFilePath", remoteFilePath);
		map.put("localFilePath", localFilePath);
		dlgForm.setParaMap(map);
		this.startDialog("uimsHttpDownFileProgressClientDialog", dlgForm, this);
	}

	public Date getAppClientJarDate(String appName) {
		RmiRequest request = new RmiRequest();
		request.add("appName", appName);
		request.setCmd("uimsGetAppJarModifyTime");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (Date) respond.get("modifyTime");
		} else
			return null;
	}

	public void startApplication(String appName) {
		// TODO Auto-generated method stub
		RmiRequest request = new RmiRequest();
		request.add("appName", appName);
		request.setCmd("serviceAuthGetUserVisitAppInfoForm");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() != null) {
			JOptionPane.showMessageDialog(null, respond.getErrorMsg());
			return;
		}
		UserVisitAppInfoForm form = (UserVisitAppInfoForm) respond
				.get(RmiKeyConstants.KEY_FORM);
		String fileName = form.getLocalDir() + appName + ".dat";
		File f = new File(fileName);
		ObjectInputStream in;
		Date date = null;
		Date sDate = getAppClientJarDate(appName);
		if (sDate == null) {
			UimsUtils.messageBoxError(null, "无法启动应用程序!");
			return;
		}
		try {
			String jarName = form.getLocalDir() + appName + ".jar";
			if (f.exists()) {
				in = new ObjectInputStream(new FileInputStream(f));
				date = (Date) in.readObject();
				in.close();
			}
			if (date == null || date.before(sDate)) {
				downFile(form.getJarUrl(), jarName);
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(f));
				out.writeObject(sDate);
				out.close();
			}
			String runString = "java -jar " + jarName;
			String paras = form.getParameter();
			if (paras == null) {
				paras = "[loginName:";
				if (form.getAppLoginName() != null)
					paras += form.getAppLoginName();
				else
					paras += form.getLoginName();
				paras += ";needLogin:false]";
			}
			runString += jarName;
			Runtime.getRuntime().exec(runString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startDisplayHtmlContent(String content) {
		UMutiInfoForm f = new UMutiInfoForm();
		f.setInfo(content);
		startDialog("uimsHtmlInfomShowDialog",f);
	}
	public Object[] getObjectsFromList(List list) {
		Object items[];
		if(list == null || list.size()==0) 
			items = null;
		else {
			items = new Object[list.size()];
			for(int i = 0; i < items.length;i++) {
				items[i] = list.get(i);
			}
		}
		return items;
	}
	public void doPanelRefreshAfterDataUpate(){
		
	}
}
