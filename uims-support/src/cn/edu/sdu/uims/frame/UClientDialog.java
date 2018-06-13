package cn.edu.sdu.uims.frame;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.def.UClientDialogTemplate;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UClientDialog extends JDialog {
	// frame实例对象
	protected static UClientDialog dialog = null;
	// 模版路径
	protected String templatePath = "cn\\edu\\sdu\\";
	protected String clientDialogName = "platformClientDialog";
	// 应用模版对象

	protected List veiwKeyListenerList = new ArrayList();
	protected UClientDialogTemplate apptemplate = null;
	// 工作区对象
	protected UPanelI mainPanel;
	protected Icon image= null;

	// 菜单树和工作区的分割栏
	public class WindowFocusProcessor implements WindowFocusListener {


		@Override
		public void windowGainedFocus(WindowEvent e) {
			// TODO Auto-generated method stub
			if(mainPanel == null)
				return;
			if(mainPanel.getHandler() == null)
				return;
			mainPanel.getHandler().windowGainedFocus();
		}

		@Override
		public void windowLostFocus(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public UClientDialog() {
		dialog = this;
	}
	public UClientDialog(JFrame jf) {
		super(jf);
		dialog = this;
	}
	
	private class ImageBackPanel extends JPanel{
		public ImageBackPanel(){
			if( apptemplate.bgColorName != null) {
				UColor color = UFactory.getModelSession().getColorByName(apptemplate.bgColorName);
				setBackground(color.color);
			}	
		}
		public void paint(Graphics g) {
			super.paint(g);
			if(image == null)
				image = UimsUtils.getCSClientIcon(apptemplate.imageName);
			if (image != null) {
				int imageW = image.getIconWidth();
				int imageH = image.getIconHeight();
				int w = this.getWidth();
				int h = this.getHeight();
				image.paintIcon(this, g, (w-imageW)/2, (h-imageH)/2);
				paintComponents(g);
			}
		}
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
			p = mainPanel.getCurrentDisplayPagePanel();
			if (p != null) {
					p.sendhandlerProcess("processKeyEvent", event, "shortCut");
			}
		}
	}
	
	/**
	 * 初始化生成template
	 * 
	 */
	
	public void initTemplate() {
		List<String> list = null;
		if(UimsUtils.userTokenClientSide != null)
			list = UimsUtils.userTokenClientSide.getRoleList();
		EnvironmentTemplate envir = UFactory.getModelSession().getEnvironmentTemplate();
		apptemplate = (UClientDialogTemplate) UFactory.getModelSession()
				.getTemplate(UConstants.MAPKKEY_CLIENTDIALOG, envir.getClientDialogName(list));
		if(UimsFactory.getClientMainI().isEnglishVersion())
			setTitle(envir.applicationEnTitle);
		else
			setTitle(envir.applicationTitle);

	}

	/**
	 * frame的初始化
	 * 
	 */
	public void init() {
		// 初始化model
		initTemplate();
		if (apptemplate == null || apptemplate.panelTemplate == null)
			return;
		JPanel c = new ImageBackPanel();
		this.setContentPane(c);
//		setTitle(apptemplate.title.content);
		mainPanel = (UPanelI) apptemplate.panelTemplate.newComponent();
		mainPanel.setTemplate(apptemplate.panelTemplate);
		mainPanel.setUseOutContainer(true);
		mainPanel.setOutCantiner(c);
		mainPanel.init();
		this.addWindowFocusListener(new WindowFocusProcessor());
		initShortCutKey();
		initWindow();
		this.setResizable(false);
	}


	/**
	 * 初始化window窗口
	 * 
	 */
	public void initWindow() {
		
		setLocation(apptemplate.winX,apptemplate.winY);
		setSize(apptemplate.winW,apptemplate.winH);
		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 取得实例
	 * 
	 * @return
	 */
	public static UClientDialog getDialog() {
		if (dialog == null) {
			dialog = new UClientDialog();
		}
		return dialog;
	}

	// Overridden so we can exit when window is closed
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			quit();
		}
	}

	public void quit() {
		int i = UimsUtils.messageBoxChoice("您确定要退出么?");
		if (i == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}


	public static UClientDialog getInstance() {
		return dialog;
	}

	public static void main(String[] args) {
		UClientDialog f = new UClientDialog();
		f.init();

	}

	public void setPanelTimeControlActionAttribute(){
	}
	public UPanelI getMainPanel() {
		return mainPanel;
	}

}
