package cn.edu.sdu.uims.component.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JPanel;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.button.UButton;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.event.UEventAdaptor;
import cn.edu.sdu.uims.component.groupcomponent.UGroupComponentI;
import cn.edu.sdu.uims.component.panel.UFPanel;
import cn.edu.sdu.uims.def.UDialogTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UDialogPanel extends UFPanel {
	final static int CONTENT_WIDTH = 1000;

	public final static int CONTENT_HEIGHT = 650;
	public final static String RETURN_OK = "ok";
	public final static String RETURN_CANCEL = "cancel";
	protected UDialog container;
//	private Container innerContainer;
	protected UFormI dialogForm = null;
	protected UComboBox comboBoxs[];
	private String buttonValue;
	private UButton okButton = null;
	private UButton cancelButton = null;
	private boolean okDisplay = true;
	private boolean cancelDisplay = true;
	
	private UButton button[] = null;
	private Icon image = null;
	protected JPanel jContentPane = null;
	private UComponentI ownerI = null;
	private UHandlerI ownerHandler = null;
	private Point locatePoint = null;
	
	
	public UButton[] getButton() {
		return button;
	}

	public Point getLocatePoint() {
		return locatePoint;
	}

	public void setLocatePoint(Point locatePoint) {
		this.locatePoint = locatePoint;
	}

	public UDialog getContainer() {
		return container;
	}

	public boolean isOkDisplay() {
		return okDisplay;
	}

	public void setOkDisplay(boolean okDisplay) {
		this.okDisplay = okDisplay;
	}

	public boolean isCancelDisplay() {
		return cancelDisplay;
	}

	public void setCancelDisplay(boolean cancelDisplay) {
		this.cancelDisplay = cancelDisplay;
	}

	public UHandlerI getOwnerHandler() {
		return ownerHandler;
	}

	public void setOwnerHandler(UHandlerI ownerHandlerI) {
		this.ownerHandler = ownerHandlerI;
	}

	public UDialogPanel() {
		this(new UPanelTemplate());
	}

	public void SetOwner(UComponentI ownerI) {
		this.ownerI = ownerI;
	}

	public UDialogPanel(UPanelTemplate panelTemplate) {
		super(panelTemplate);
	}

	private class ImageBackPanel extends JPanel{
		public ImageBackPanel(){
			UDialogTemplate temp = (UDialogTemplate) panelTemplate;
			if(temp.bgColorName != null) {
				UColor color = UFactory.getModelSession().getColorByName(temp.bgColorName);
				setBackground(color.color);
			}	
		}
		public void paint(Graphics g) {
			super.paint(g);
			UDialogTemplate temp = (UDialogTemplate) panelTemplate;
			image = UimsUtils.getCSClientIcon(temp.imageName);
			if (image != null) {
				int imageW = image.getIconWidth();
				int imageH = image.getIconHeight();
				int w = this.getWidth();
				int h = this.getHeight();
//				g.clearRect(0, 0, w, h);
				image.paintIcon(this, g, (w-imageW)/2, (h-imageH)/2);
				paintComponents(g);
			}
		}
	}
	protected void initBackImage() {
		jContentPane = new ImageBackPanel(); 
	}
	public class WindowFocusProcessor implements WindowFocusListener {


		@Override
		public void windowGainedFocus(WindowEvent e) {
			// TODO Auto-generated method stub
			if(getHandler() != null)
				getHandler().windowGainedFocus();			
		}

		@Override
		public void windowLostFocus(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	public void init() {
		if (ownerI == null)
			container = new UDialog();
		else if(ownerI instanceof UDialogPanel){
			UDialogPanel tempDlg = (UDialogPanel)ownerI;
				container = new UDialog((Dialog) tempDlg.container);
		}else {
			container = new UDialog((Dialog) ownerI.getAWTComponent());
		}
		UDialogTemplate dialogTemplate = (UDialogTemplate) panelTemplate;
		initBackImage();

		eventAdaptor = new UEventAdaptor(this);
		container.addWindowListener(eventAdaptor);
		container.addWindowFocusListener(new WindowFocusProcessor());
		container.setUParent(this);
		initComponents();
		initHandlerObject(panelTemplate.name,null);
		initContents();

		UFormI form = initDataByHandlerAfterInitContents();
		if (dialogForm != null) {
			sendFormHandler(dialogForm);
			setHandlerOutFormData();
			form = dialogForm;
		}
		setData(form);
		makeInnerBounds();
		setComponentBounds();
		resetScrollPanelSize();
		
		int xo = 0, yo = 0;
		Dimension d;
		if (dialogTemplate.xo != 0 && dialogTemplate.yo != 0) {
			xo = dialogTemplate.xo;
			yo = dialogTemplate.yo;
		} else {
			// Frame f = UClientFrame.getInstance();
			// if (f != null) {
			// d = f.getSize();
			// } else
			// d = new Dimension(1024, 768);
			// xo = (d.width - panelTemplate.width) / 2;
			// yo = (d.height - panelTemplate.height) / 2;
			Toolkit kit = Toolkit.getDefaultToolkit();
			Dimension screenSize = kit.getScreenSize();
			int screenWidth = screenSize.width;
			int screenHeight = screenSize.height;
			xo = screenWidth / 2 - panelTemplate.width / 2;
			yo = screenHeight / 2 - panelTemplate.height / 2;

		}
//		finishedInit = true;
		setDisplayPanelStatusByHandlerAfterInitContents();
		setFinnishedInitMak(true);
		container.setLayout(new BorderLayout());
		container.setContentPane(jContentPane);
		container.setSize(panelTemplate.width, panelTemplate.height);
		container.setLocation(xo, yo);
		if(UimsFactory.getClientMainI().isEnglishVersion()) {
			container.setTitle(panelTemplate.enTitle);
		}else
			container.setTitle(panelTemplate.title);
			
		container.setResizable(false);
		setRolePanelControlActionAttribute();	
		initValidatorMap();
		if(getHandler() != null)
			getHandler().start();
		container.setVisible(true);
		
	}

	protected void initContentBounds() {
		int w, h;
		w = getInnerWidth();
		h = getInnerHeight();
		// container.setBounds((CONTENT_WIDTH - w) / 2, (CONTENT_HEIGHT - h) /
		// 2,
		// w, h);
	}

	public void setBorder(int border) {
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		String name;
		Iterator it = getNameIterator();
		UComponentI com;
		int i;
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			if (com.hasEmptyFileds())
				return true;
		}
		return false;
	}
	public UGroupComponentI initGroupComponent(boolean isRoot) {
		UGroupComponentI gc = getGroupComponentObject();
		JPanel pt;
		int i;
		boolean isEnglish = UimsFactory.getClientMainI().isEnglishVersion();
		if (isRoot) {
			UDialogTemplate dialogTemplate = (UDialogTemplate) panelTemplate;
			JPanel ipanel = jContentPane;
			ipanel.setBounds(0, 0, dialogTemplate.width, dialogTemplate.height);
			ipanel.setLayout(new BorderLayout());
			int cn = 0;
			if (!dialogTemplate.cancel.equals(""))
				cn++;
			if (!dialogTemplate.ok.equals(""))
				cn++;
			if (dialogTemplate.buttons != null) {
				cn += dialogTemplate.buttons.length;
			}
			if (!dialogTemplate.cancel.equals("")
					|| !dialogTemplate.ok.equals("")) {
				innerPanel = new JPanel();
				ipanel.add(innerPanel, BorderLayout.CENTER);
				JPanel p = new JPanel();
				ipanel.add(p, BorderLayout.SOUTH);
				p.setLayout(new GridLayout(1, cn));
				if (!dialogTemplate.ok.equals("") && okDisplay) {
					okButton = new UButton();
					okButton.setUParent(this);
					okButton.setComponentName("ok");
					if(isEnglish)
						okButton.setText(dialogTemplate.okEn);
					else
						okButton.setText(dialogTemplate.ok);
					okButton.setActionCommand("okCommand");
					okButton.addActionListener(eventAdaptor);
					pt = new JPanel();
					pt.setLayout(new FlowLayout());
					pt.add(okButton);
					p.add(pt);
				}
				if (dialogTemplate.buttons != null) {
					button = new UButton[dialogTemplate.buttons.length];
					for (i = 0; i < button.length; i++) {
						button[i] = new UButton();
						button[i]
								.setComponentName(dialogTemplate.buttons[i].name);
						;
						button[i].setText(dialogTemplate.buttons[i].content);
						button[i]
								.setActionCommand(dialogTemplate.buttons[i].cmd);
						button[i].setUParent(this);
						button[i].addActionListener(eventAdaptor);
						pt = new JPanel();
						pt.setLayout(new FlowLayout());
						pt.add(button[i]);
						p.add(pt);
					}
				}
				if (!dialogTemplate.cancel.equals("") && cancelDisplay) {
					cancelButton = new UButton();
					cancelButton.setComponentName("cancel");
					if(isEnglish)
						cancelButton.setText(dialogTemplate.cancelEn);
					else
						cancelButton.setText(dialogTemplate.cancel);
					cancelButton.setActionCommand("cancelCommand");
					cancelButton.addActionListener(eventAdaptor);
					cancelButton.setUParent(this);
					pt = new JPanel();
					pt.setLayout(new FlowLayout());
					pt.add(cancelButton);
					p.add(pt);
				}
			} else {
				innerPanel = ipanel;
			}
			gc.setContainer(innerPanel);
		} else {
			innerPanel = new JPanel();
			gc.setContainer(innerPanel);
		}
		return gc;
	}

	public void setDialogForm(UFormI form) {
		dialogForm = form;

	}

	public UFormI getDialogForm() {
		return dialogForm;
	}

	public Component getComponent() {
		return container;
	}

	public String getReturnValue() {
		return buttonValue;
	}

	public void doOk() {
		JDialog dlg = (JDialog) container;
		buttonValue = RETURN_OK;
		dialogForm = (UFormI) getData();
		dlg.dispose();
		dlg.setVisible(false);
	}
	public void doReturn(String returnValue){
		JDialog dlg = (JDialog) container;
		buttonValue = returnValue;
		dialogForm = (UFormI) getData();
		dlg.dispose();
		dlg.setVisible(false);
		
	}
	public void doCancel() {
		JDialog dlg = (JDialog) container;
		buttonValue = RETURN_CANCEL;
		dlg.dispose();
		dlg.setVisible(false);
	}

	public void doUserAction(String command) {
		JDialog dlg = (JDialog) container;
		UDialogTemplate dialogTemplate = (UDialogTemplate) panelTemplate;
		if(dialogTemplate == null || dialogTemplate.buttons == null || dialogTemplate.buttons.length == 0)
			return;
		for (int i = 0; i < dialogTemplate.buttons.length; i++) {
			if (command.equals(dialogTemplate.buttons[i].cmd)) {
				buttonValue = dialogTemplate.buttons[i].cmd;
				dlg.dispose();
				dlg.setVisible(false);
				break;
			}
		}
	}

	public void doClose() {
		JDialog dlg = (JDialog) container;
		buttonValue = RETURN_CANCEL;
		dlg.dispose();
		dlg.setVisible(false);
		removeHandler();
	}

	public UButton getOkButton() {
		return okButton;
	}
	public void setDialogTitle(String title){
		if(container != null)
			container.setTitle(title);
	}
	public UButton getCancelButton() {
		return cancelButton;
	}
	public void updatetpActionBarComboBoxAdddData(int index, List list ){
		if (comboBoxs == null || index >= comboBoxs.length)
			return;
		if(list != null) {
			Object o[] = list.toArray();
			comboBoxs[index].setAddedDatas( o);
		}
		else
			comboBoxs[index].setAddedDatas((Object[])null) ;
		comboBoxs[index].updateAddedDatas();
	}
}
