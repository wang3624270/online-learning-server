/**
 * 
 */
package cn.edu.sdu.uims.component.textfield;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

/**
 * @author 赵鹏 2006.11.21
 * 
 * 一个数字输入控件：可以输入10位十进制正整数，并通过按钮和上下方向键进行加1减1操作。
 * 
 * 需要改进的地方：1.功能单一，只能输入十进制正整数，应增加其他常用的输入功能 2.无法控制用户输入不至于输入的数字溢出 3.没有提供数字的分割符等辅助信息
 */
public class UNumberFieldWithAddMinus extends JPanel implements ActionListener,
		KeyListener, MouseListener, UComponentI {
	private boolean mousePressed = false;

	// 加1按钮
	private final JButton btn_reduceButton = new JButton();

	// 减一按钮
	private final JButton btn_addButton = new JButton();

	// 格式化输入框
	private final JFormattedTextField txt_number;

	private final JPanel panel = new JPanel();

	// 输入格式掩码
	private MaskFormatter mf = null;

	private DecimalFormat numberFormat;
	protected UElementTemplate elementTemplate;
	private String componentName;
	protected UPanelI parent = null;

	/**
	 * Create the panel
	 */
	public UNumberFieldWithAddMinus() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		panel.setLayout(new GridLayout(2, 0));
		panel.setPreferredSize(new Dimension(18, 0));

		btn_addButton.setText("+");
		btn_addButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		btn_addButton.setBorderPainted(false);
		btn_addButton.setActionCommand("add");
		btn_addButton.addActionListener(this);
		btn_addButton.addMouseListener(this);
		panel.add(btn_addButton);

		btn_reduceButton.setText("-");
		btn_reduceButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		btn_reduceButton.setBorderPainted(false);
		btn_reduceButton.setActionCommand("reduce");
		btn_reduceButton.addActionListener(this);
		btn_reduceButton.addMouseListener(this);
		panel.add(btn_reduceButton);

		btn_reduceButton.setToolTipText("单位减小");
		btn_addButton.setToolTipText("单位增加");

		try {
			mf = new MaskFormatter("##########");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if(mf!=null){
		// mf.setPlaceholderCharacter('_');
		// }
		//		
		// numberFormat = new DecimalFormat("###,###.0000");
		// Locale.setDefault(Locale.US);
		// txt_number = new JFormattedTextField(numberFormat);
		txt_number = new JFormattedTextField();
		txt_number.addKeyListener(this);
		mf.install(txt_number);

		add(txt_number);
		add(panel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		/*
		 * String cmd = e.getActionCommand(); // 加一 if (cmd.equals("add")) {
		 * 
		 * add(); // 减一 } else if (cmd.equals("reduce")) {
		 * 
		 * reduce(); }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("keychar"+e.getKeyChar());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("kecode"+e.getKeyCode());
		int keyCode = e.getKeyCode();
		// 38代表向上方向键
		// 40代表向下方向键
		if (keyCode == 38) {
			add();
		} else if (keyCode == 40) {
			reduce();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// 加一操作
	public void add() {

		String numberStr = txt_number.getText().trim();

		if (numberStr.equals("")) {
			txt_number.setText("1");
		} else {
			int i;
			try {
				i = Integer.parseInt(numberStr);
				// 判断是否溢出
				if (i < Integer.MAX_VALUE) {
					i++;
				}
				txt_number.setText(String.valueOf(i));
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, "非法数字!!!");
			}
		}
	}

	// 减一操作
	public void reduce() {

		String numberStr = txt_number.getText().trim();

		try {
			if (numberStr.equals("")) {
				txt_number.setText("0");
			} else {
				int i = Integer.parseInt(numberStr);
				// 判断是否小于0
				if (i > 0) {
					i--;
				}
				txt_number.setText(String.valueOf(i));
			}
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "非法数字!!!");
		}
	}

	/**
	 * 获取输入的Integer类型的数字值
	 * 
	 * @return 如果输入为""或非法字符串返回null 否则返回Integer数字值
	 */
	public Integer getIntegerValue() {
		try {
			String numberStr = txt_number.getText().trim();
			if (numberStr.equals("")) {
				return null;
			} else {
				return Integer.parseInt(numberStr);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取int类型数字值
	 * 
	 * @return 如果输入为""或非法字符串返回-1 否则返回int数字值
	 */
	public int getIntValue() {
		try {
			String numberStr = txt_number.getText().trim();
			if (numberStr.equals("")) {
				return -1;
			} else {
				return Integer.parseInt(numberStr);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return -1;
		}
	}

	public void setNumber(Integer value) {
		if (value == null) {
			txt_number.setText("");
		} else {
			txt_number.setText(String.valueOf(value));
		}
	}

	public void clear() {
		txt_number.setText("");
	}

	/**
	 * 对输入数字的文本框设置actionListener
	 * 
	 * @param listener
	 */
	public void addActionListener(ActionListener listener, String actionCmd) {
		txt_number.addActionListener(listener);
		txt_number.setActionCommand(actionCmd);
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			// DecimalFormat numberFormat = new DecimalFormat("###,###.0000");
			// Locale.setDefault(Locale.US);
			// System.out.println(numberFormat.format(111111123456.12));

			JFrame f = new JFrame();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container content = f.getContentPane();
			content.setLayout(new BorderLayout());

			UNumberFieldWithAddMinus test = new UNumberFieldWithAddMinus();
			content.add(test);

			f.setSize(300, 100);
			f.setVisible(true);

			test.setNumber(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePressed = true;
		if (e.getSource() == btn_addButton) {
			new AddThread().start();
		} else if (e.getSource() == btn_reduceButton) {
			new ReduceThread().start();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePressed = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	class AddThread extends Thread {

		public void run() {
			while (mousePressed) {
				try {
					add();
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class ReduceThread extends Thread {

		public void run() {
			while (mousePressed) {
				try {
					reduce();
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		
	}


	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if(elementTemplate != null)
			return new URect(elementTemplate.x,elementTemplate.y,elementTemplate.w, elementTemplate.h);
		else
			return null;
	}


	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}


	public void setComponentName(String name) {
		this.componentName = name;
	}

	public String getComponentName() {
		return componentName;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}


	public Object getData() {
		// TODO Auto-generated method stub
		return getIntegerValue();
	}


	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}




	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}


	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub
		
	}


	public void setArrangeType(int type) {
		// TODO Auto-generated method stub
		
	}


	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		
	}


	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub
		
	}


	public void setColor(UColor c) {
		// TODO Auto-generated method stub
		
	}



	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if(obj == null)
			setNumber(0);
		else
			setNumber((Integer)obj);
	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		txt_number.setEditable(b);
		btn_addButton.setVisible(b);
		btn_reduceButton.setVisible(b);
	}


	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		
	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub
		
	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		
	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub
		
	}


	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		
	}


	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		
	}


	public void setText(String arg0) {
		// TODO Auto-generated method stub
		
	}


	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		
	}
	public void initContents() {
		// TODO Auto-generated method stub
		
	}
	public void setInitComponentData(Object data){
		
	}
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
//		this.setLocation(x, y);
//		this.setPreferredSize(new Dimension(w,h));
	}
	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}
	public void onClose(){
		
	}
	public void repaintComponent() {
	}
	public void setParameters(HashMap paras){
		
	}
	public HashMap getParameters(){
		return null;
	}
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}



	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setdisplayText(String text) {
		// TODO Auto-generated method stub
		
	}
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setActionComandString(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserData(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}



}
