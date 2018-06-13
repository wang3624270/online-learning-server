package cn.edu.sdu.uims.component.textfield;

import java.awt.Dialog;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.list.UListPopup;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.def.UTextFieldQueryTemplate;
import cn.edu.sdu.uims.form.UPopupIoDataI;
import cn.edu.sdu.uims.form.UTextFieldDataFormI;
import cn.edu.sdu.uims.handler.InputQueryParaProcessorI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UTextFieldQuery extends UTextFiledPopup implements MouseListener, ListSelectionListener {
	private Object data;
	private boolean enabled = true;
	private InputQueryParaProcessorI pi = null;
	
	public UTextFieldQuery() {
	}

	public void initContents() {
		// TODO Auto-generated method stub
		if(template != null) {
			this.setColumns(template.maxLength);
			if(((UTextFieldQueryTemplate)template).valueActionNum < 0) {
				this.addMouseListener(this);				
				this.setEnabled(false);
				this.setEditable(false);
			}else {
				popup = new UListPopup();
				popup.addListSelectionListener(this);
				this.addKeyListener(this);
				this.addActionListener(this);
				if(((UTextFieldQueryTemplate)template).valueActionNum >0 ) {
					
				}
				this.setEditable(true);
				this.setEnabled(true);
			}
		}
//		this.setText(toString());
	}

	public void setEnabled(boolean enable){
		this.enabled = enable;
	}
	public boolean getEnabled(){ 
		return enabled;
	}
	public Object getData() {
		reGetDataFromCcom();
		return data;
	}
	private void reGetDataFromCcom(){
		if(data != null) {
			if(data instanceof UTextFieldDataFormI) {
				UTextFieldDataFormI di = (UTextFieldDataFormI)data;
				if(di.getDataObjectId() == null) {
					di.setDataObjectNum(this.getText());
				}
			}
		}
	}

	public void setData(Object o) {
		data = o;
		updateComponent();
	}

	public void updateComponent(){
		if(data == null)
			this.setText("");
		else {
			if(data instanceof List) {
				List list = (List)data;
				String str = "";
				for(int i = 0;i < list.size();i++) {
					str += list.get(i).toString();
					if(i < list.size()-1){
						str += ";";
					}
				}
				this.setText(str);
			}
			else if(data instanceof UTextFieldDataFormI) {
				UTextFieldDataFormI t = (UTextFieldDataFormI)data;
				this.setText(t.getDataObjectText());
			}else 
				this.setText(data.toString());
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!enabled)
			return ;
		showSelectionInfoPopWindow(null, e.getLocationOnScreen());
	}

	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	private boolean isCanQuery(String str, boolean isAction) {
		UTextFieldQueryTemplate temp = (UTextFieldQueryTemplate)template;		
		if(str == null || str.length() == 0)
			return false;
	if(!temp.isNotDigit) {
		for(int i = 0; i < str.length();i++) {
				if(str.charAt(i) < '0' || str.charAt(i) > '9')
					return false;
		}
	}
	if(isAction ||  str.length() >= temp.valueActionNum) 
			return true;
		else
			return false;
	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyPressed(e);
		UTextFieldQueryTemplate temp = (UTextFieldQueryTemplate)template;
		if(!temp.keyAction)
			return;
		String str = this.getText() + e.getKeyChar();
		if(str != null && temp.valueActionNum > 0  && str.length() == temp.valueActionNum){
			processQueryAction(str, false);		
		}
	}
	public void actionPerformed(ActionEvent arg0) {
		String str = this.getText();
		processQueryAction(str, true);
	}
	public void processQueryAction(String str, boolean isAction){
		UTextFieldQueryTemplate temp = (UTextFieldQueryTemplate)template;
		if(!isCanQuery(str,isAction))
			return;
		if(temp.beanName == null)
			return;
		HashMap  map = null;
		if(pi != null) {
			map = pi.getParaMap(str);
			if(map.get("error") != null) {
				JOptionPane.showMessageDialog(null, map.get("error").toString());
				return;
			}
		}
		List list = getQueryDataListByInputString(temp.beanName,str, null, temp.dataClassName, map);
		if(list == null) {
			String msg = temp.errPromptMsg;
			if(msg == null)
				msg = "查不到与输入相配的人员！";
			JOptionPane.showMessageDialog(null, msg);
			return;
		}
		popup.setList(list);
		popup.setPopupPreferredSize();
		Point p = null;
		showSelectionInfoPopWindow(str,p);
	}
	public void showSelectionInfoDialog(String input, Point p){
		UPanelTemplate temp;
		HashMap map = null;
		String tName = ((UTextFieldQueryTemplate)template).panelTemplateName;
		UHandlerI th = this.getUParent().getHandler();
		temp = (UPanelTemplate) UFactory.getModelSession().getTemplate(
				UConstants.MAPKEY_DIALOG, tName);
		UFormI form = null;
		try {
			if (temp.dataFormClassName != null) {
				form = (UFormI) Class.forName(temp.dataFormClassName)
						.newInstance();
			}
			if (form instanceof UPopupIoDataI) {
				UPopupIoDataI listForm = (UPopupIoDataI) form;
				listForm.setIoData(data);
			}
		} catch (Exception et) {
			et.printStackTrace();
		}
		UDialogPanel item = (UDialogPanel) temp.newComponent();
		if (item != null) {
			if (this.getUParent().getAWTComponent() instanceof Dialog)
				item.SetOwner(this.getUParent());
			else
				item.SetOwner(null);
			item.setOwnerHandler(th);
			item.setComponentName(tName);
			item.setTemplate(temp);
			item.setParameters(map);
			item.setDialogForm(form);
			item.setLocatePoint(p);
			item.init();
			if (item.getReturnValue() != null && item.getReturnValue().equals(item.RETURN_OK)) {
				UPopupIoDataI listForm = (UPopupIoDataI) item.getData();
				if(listForm != null) {
					data = listForm.getIoData();
					updateComponent();
				}
			}
		}
		
	}
		
	public void showSelectionInfoPopList(String input, Point p){
		if (popup!= null && !popup.isVisible()) {
			showPopup();
		}

	}
	public List getQueryDataListByInputString(String beanName,String input, String type,String dataClassName, HashMap paraMap) {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.setCmd("uimsGetQueryDataListByInputString");
		request.add("parameters",paraMap);
		request.add("inputString", input);
		request.add("beanName",beanName);
		request.add("dataClassName", dataClassName);
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	
	public void showSelectionInfoPopWindow(String input, Point p){
		if (((UTextFieldQueryTemplate)template).panelTemplateName != null)
			showSelectionInfoDialog(input,p);
		else
			showSelectionInfoPopList(input,p);
	}
	
	public void setInputQueryParaProcessor( InputQueryParaProcessorI pi){
		this.pi = pi;
	}

}
