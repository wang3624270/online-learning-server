package cn.edu.sdu.uims.component.textfield;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
import cn.edu.sdu.uims.def.UTextFieldTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UIPCompositeField extends JPanel implements UComponentI,
		KeyListener {
	
	protected UTextFieldTemplate template;

	private final UTextFieldIP txt_number1;

	private final JLabel lbl_number1;

	private final UTextFieldIP txt_number2;

	private final JLabel lbl_number2;

	private final UTextFieldIP txt_number3;

	private final JLabel lbl_number3;

	private final UTextFieldIP txt_number4;
	protected UElementTemplate elementTemplate;

	public UIPCompositeField() {
		super();
		setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		txt_number1 = new UTextFieldIP(3, 255, 0);
		this.add(txt_number1);
		txt_number1.addKeyListener(this);
		lbl_number1 = new JLabel(".");
		this.add(lbl_number1);
		txt_number2 = new UTextFieldIP(3, 255, 0);
		this.add(txt_number2);
		txt_number2.addKeyListener(this);
		lbl_number2 = new JLabel(".");
		this.add(lbl_number2);
		txt_number3 = new UTextFieldIP(3, 255, 0);
		this.add(txt_number3);
		txt_number3.addKeyListener(this);
		lbl_number3 = new JLabel(".");
		this.add(lbl_number3);
		txt_number4 = new UTextFieldIP(3, 255, 0);
		this.add(txt_number4);
		txt_number4.addKeyListener(this);

	}

	public void setText(String arg0) {
		StringTokenizer st = new StringTokenizer(arg0, ".");
		txt_number1.setText(st.nextToken());
		txt_number2.setText(st.nextToken());
		txt_number3.setText(st.nextToken());
		txt_number4.setText(st.nextToken());
	}

	public void clear() {
		txt_number1.setText("");
		txt_number2.setText("");
		txt_number3.setText("");
		txt_number4.setText("");
	}

	public String getText() {
		if (txt_number1.getText().equals("")
				|| txt_number2.getText().equals("")
				|| txt_number3.getText().equals("")
				|| txt_number4.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入完整", "WARNING",
					JOptionPane.WARNING_MESSAGE);
			return null;
		} else {
			return Integer.parseInt(txt_number1.getText().trim()) + "."
					+ Integer.parseInt(txt_number2.getText().trim()) + "."
					+ Integer.parseInt(txt_number3.getText().trim()) + "."
					+ Integer.parseInt(txt_number4.getText().trim());
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
		return null;
	}

	public String getComponentName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public UPanelI getUParent() {
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

	public void initContents() {
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

	public void setComponentName(String name) {
		// TODO Auto-generated method stub

	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub

	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

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
       this.template=(UTextFieldTemplate) template;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Container c = frame.getContentPane();
		UIPCompositeField txt = new UIPCompositeField();
		c.add(txt);
		frame.setSize(400, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_PERIOD || keyCode == KeyEvent.VK_ENTER) {
			Component component = e.getComponent();
			nextFocus(component);
		}

	}

	public void nextFocus(Component component) {
		if (component == txt_number1) {
			txt_number2.requestFocus();
			txt_number2.selectAll();
		} else if (component == txt_number2) {
			txt_number3.requestFocus();
			txt_number3.selectAll();
		} else if (component == txt_number3) {
			txt_number4.requestFocus();
			txt_number4.selectAll();
		}

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public void setInitComponentData(Object data){
		
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
