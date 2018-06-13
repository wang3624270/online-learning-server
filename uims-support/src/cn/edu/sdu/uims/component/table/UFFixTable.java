package cn.edu.sdu.uims.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UFixTable;
import cn.edu.sdu.uims.component.label.ULabel;
import cn.edu.sdu.uims.component.textfield.UTextField;
import cn.edu.sdu.uims.def.UTableTemplate;

public class UFFixTable extends UFixTable implements ActionListener{
	private JPanel p =null;

	public UFFixTable() {
		this(null);
	}

	public UFFixTable(UTableTemplate template) {
		super(template);
		p = new JPanel();
		p.setLayout(null);
	}
	public void addComponent(UComponentI com,int layout){
		p.add(com.getAWTComponent());
	}
	public UComponentI getStaticTextObject(String str){
		return new ULabel(str);
	}
	public UComponentI getFieldsObject(){
		return new UTextField();
	}

	public void setBorder(int border) {
		if (border == 0)
			p.setBorder(null);
		else
			p.setBorder(BorderFactory.createLineBorder(Color.black, border));
	}
	public void addActionListenrtoCell() {
		int i;
		for (i = 0; i < columnFields.length; i++) {
			if (columnFields[i].isEditable()) {
				((UTextField)columnFields[i]).addContainerActionListener(this);
			}
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		UTextField o = (UTextField) arg0.getSource();
		int pos, newPos;
		boolean b = false;
		pos = 0;
		while (pos < columnFields.length) {
			if (o == columnFields[pos]) {
				break;
			} else
				pos++;
		}
		newPos = (pos + 1) % columnFields.length;
		while (!b && newPos != pos) {
			if (columnFields[newPos].isEditable()) {
				UTextField t = (UTextField)columnFields[newPos];
				t.requestFocus();
				break;
			}
			newPos = (newPos + 1) % columnFields.length;
		}
	}
	public void requestFoucus(int i) {
		if (columnFields[i].isEditable()) {
			UTextField t = (UTextField)columnFields[i];
			t.requestFocus();
		}
	}

	public boolean requestFirstFoucus() {
		int i;
		for (i = 0; i < columnFields.length; i++)
			if (columnFields[i].isEditable()) {
				UTextField t = (UTextField)columnFields[i];
				t.requestFocus();
				return true;
			}
		return false;
	}
	public Component getComponent(){
		return p;
	}
	public void setShellBounds(int x, int y, int w, int h) {
//		p.setLocation(x,y);
//		p.setPreferredSize(new Dimension(w,h));
		p.setBounds(x, y, w, h);
	}
	
}