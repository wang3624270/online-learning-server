package cn.edu.sdu.uims.component.complex;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.component.complex.def.UTimeInputTemplate;
import cn.edu.sdu.uims.component.list.UListPopup;

public class UTimeInputPanel extends UComplexPanel implements MouseListener, ListSelectionListener{

	private JTextField hourField;
	private JTextField minuteField;
	private JTextField divField;
	private UListPopup hourList;
	private UListPopup minuteList;
	@Override


	public void initContents() {
		// TODO Auto-generated method stub
		this.setLayout(new GridLayout(1,3));
		hourField = new JTextField("12");
		hourField.setColumns(2);
		hourField.setBorder(null);
//		hourField.setEditable(false);
//		hourField.setEnabled(false);
		hourField.setAlignmentX(CENTER_ALIGNMENT);
		hourField.addMouseListener(this);
		this.add(hourField,0);
		divField = new JTextField(":"); 
		divField.setBorder(null);
//		divField.setEditable(false);
//		divField.setEnabled(false);
		divField.setAlignmentX(CENTER_ALIGNMENT);
		add(divField,1);
		minuteField = new JTextField("60");
		minuteField.setColumns(2);
		minuteField.setBorder(null);
		minuteField.addMouseListener(this);
//		minuteField.setEditable(false);
//		minuteField.setEnabled(false);
		minuteField.setAlignmentX(CENTER_ALIGNMENT);
		this.add(minuteField,2);
		this.setBounds(elementTemplate.x, elementTemplate.y, elementTemplate.w, elementTemplate.h);
		this.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 1));
		hourList = new UListPopup();
		hourList.addListSelectionListener(this);
		hourList.setList(getHourDataList().toArray());
		hourList.setPopupPreferredSize();
		minuteList = new UListPopup();
		minuteList.addListSelectionListener(this);
		minuteList.setList(getMinuteDataList().toArray());
		minuteList.setPopupPreferredSize();
	}

	@Override
	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		 this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == hourField) {
			hourList.show(hourField, e.getX(), e.getY());
		}else if(e.getSource() == minuteField){ 
			minuteList.show(minuteField,e.getX(),e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		JList list = (JList)e.getSource();
		Object o = list.getSelectedValue();
		if(list == hourList.getJList()) {
			hourField.setText(o.toString());
			hourList.setVisible(false);
		}else if(list == minuteList.getJList()) {
			minuteField.setText(o.toString());			
			minuteList.setVisible(false);
		}
	}
	public List getHourDataList(){
		List list = new ArrayList();
		UTimeInputTemplate te = (UTimeInputTemplate)elementTemplate;
		if(te.hListStr != null && te.hListStr.length() > 0) {
			StringTokenizer sz = new StringTokenizer(te.hListStr,",");
			while(sz.hasMoreTokens()) {
				list.add(sz.nextToken());				
			}
		}else {
			String str;
			for(int i = te.hStart; i<=te.hEnd; i+= te.hStep) {
				if(i < 10)
					str = "0" + i;
				else
					str = i/10 + "" + i%10;
				list.add(str);
			}
		}		
		return list;
	}
	public List getMinuteDataList(){
		List list = new ArrayList();
		UTimeInputTemplate te = (UTimeInputTemplate)elementTemplate;
		if(te.mListStr != null && te.mListStr.length() > 0) {
			StringTokenizer sz = new StringTokenizer(te.mListStr,",");
			while(sz.hasMoreTokens()) {
				list.add(sz.nextToken());				
			}
		}else {
			String str;
			for(int i = te.mStart; i<=te.mEnd; i+= te.mStep) {
				if(i < 10)
					str = "0" + i;
				else
					str = i/10 + "" + i%10;
				list.add(str);
			}
		}
		return list;
	}
	public Object getData() {
		return hourField.getText()+ ":" + minuteField.getText();
	}

	public void setData(Object obj) {
		if(obj == null || obj.toString().length() != 5)
			return;
		else {
			String s = obj.toString();
			hourField.setText(s.substring(0,2));
			minuteField.setText(s.substring(3,5));
		}
	}
	
}
