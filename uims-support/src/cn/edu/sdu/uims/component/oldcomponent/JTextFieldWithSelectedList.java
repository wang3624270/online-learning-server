/**
 * 
 */
package cn.edu.sdu.uims.component.oldcomponent;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.sdu.uims.base.UScrollPane;

/**
 * 一个带有JTextField和一个选择按钮的panel，点击选择按钮弹出选项列表对话框供用户选择新值
 * 
 * @author 赵鹏
 * 
 */
public class JTextFieldWithSelectedList extends JPanel implements
		ActionListener {

	private final JTextField textField = new JTextField();

	private final JButton selectButton = new JButton();

	private List itemList;// 选项列表

	private Object parent = null;// 对话框ower

	private String title;// 标题

	private Object value;// 默认的值

	/**
	 * Create the panel
	 */
	public JTextFieldWithSelectedList(String title, List itemList) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.title = title;
		this.itemList = itemList;

		textField.setEditable(false);
		add(textField);

		add(selectButton);
		selectButton.setActionCommand("select");
		selectButton.addActionListener(this);
		selectButton.setText("select");
		selectButton.setPreferredSize(new Dimension(20, 30));
		//

		for (Container c = this; c != null; c = c.getParent()) {
			if (c instanceof Frame) {
				parent = (Frame) c;
				break;
			}
			if(c instanceof Dialog){
				parent = (Dialog)c;
				break;
			}
		}
	}
	public void setParent(Object parent){
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = selectButton.getActionCommand();
		if (cmd.equals("select")) {
			
			SelectItemDialog dialog = null;
			if(parent instanceof Frame)
				dialog = new SelectItemDialog((Frame)parent, title,itemList);
			else
				dialog = new SelectItemDialog((Dialog)parent, title,itemList);
			dialog.setBounds(300, 200, 300, 400);
			dialog.setVisible(true);
			Object obj = dialog.getSelectedObject();
			if (obj != null) {
				setValue(obj);
			}
		}
	}
	//add by gyj
	public Object startDilog()
	{
		SelectItemDialog dialog = null;
		if(parent instanceof Frame)
			dialog = new SelectItemDialog((Frame)parent, title,itemList);
		else
			dialog = new SelectItemDialog((Dialog)parent, title,itemList);
		dialog.setBounds(300, 200, 300, 400);
		dialog.setVisible(true);
		Object obj = dialog.getSelectedObject();
		return obj;

	}

	/**
	 * 设置显示的值
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
		if (value != null) {
			this.textField.setText(value.toString());
		} else {
			this.textField.setText("");
		}
	}

	/**
	 * 获取value
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置textField是否可编辑
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		textField.setEditable(isEditable);
	}
}

/**
 * 选项列表dialog
 * 
 * @author Administrator
 * 
 */
 class SelectItemDialog extends JDialog implements MouseListener {

	private DefaultListModel listModel;
	
	private List dataList;

	private JList itemJList;

	private Object selectedObj;

	private JPanel queryPanel=new JPanel();
	
	private JTextField field=new JTextField();
	
	private JButton queryButton=new JButton();
		
	public SelectItemDialog(Frame parent, String title, List itemList) {
		super(parent, title, true);
		initDialog(title,itemList);
	}
	public SelectItemDialog(Dialog parent, String title, List itemList) {
		super(parent, title, true);
		initDialog(title,itemList);
	}
	public void initDialog(String title, List itemList) {
		queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		queryPanel.add(field);
		queryPanel.add(queryButton);
		
		field.setPreferredSize(new Dimension(200,20));
		queryButton.setPreferredSize(new Dimension(80,20));
		queryButton.setText("查询");
		queryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				queryData();
			}
				
		});
		field.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				queryData();
			}
				
		});

		
		
		this.setLayout(new BorderLayout());
		
		this.dataList=itemList;
		if (itemList != null) {
			listModel = new DefaultListModel();
			Iterator iterator = itemList.iterator();
			while (iterator.hasNext()) {
				listModel.addElement(iterator.next());
			}
			itemJList = new JList(listModel);
		}
		itemJList.addMouseListener(this);
		
		this.getContentPane().add(this.queryPanel,BorderLayout.NORTH);
		this.getContentPane().add(new UScrollPane(itemJList),BorderLayout.CENTER);
		
	}
	public void queryData(){
		listModel.removeAllElements();
		String queryStr=field.getText().trim();
		if(dataList!=null){
			Iterator iterator=dataList.iterator();
			while(iterator.hasNext()){
				Object obj=iterator.next();
				if(matchStr(obj.toString(),queryStr)){
					listModel.addElement(obj);
				}
			}
		}
	}
	public Object getSelectedObject() {
		return selectedObj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == itemJList && e.getClickCount() == 2) {
			selectedObj = itemJList.getSelectedValue();
			this.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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

	public boolean matchStr(String fatherStr, String subStr) {
		if (fatherStr != null && subStr != null) {
			if (fatherStr.indexOf(subStr) < fatherStr.length()
					&& fatherStr.indexOf(subStr) >= 0) {
				return true;
			}
		}
		return false;
	}
}
