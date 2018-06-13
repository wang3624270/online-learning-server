package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;

import cn.edu.sdu.uims.base.UBorder;

public class UHtmlEditPanel extends UComplexPanel {
	private MyHTMLEditorPane editor;
	
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
//		this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
		this.setLayout(new BorderLayout());
		editor= new MyHTMLEditorPane();
		this.add(editor,BorderLayout.CENTER);
	}	
	public void setData(Object o){
		if(o == null)
			editor.setText("");
		else
			editor.setText(o.toString());
	}
	public Object getData(){
		return editor.getText();
	}
	@Override
	public void setBorder(UBorder border) {
		this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
	}
	
}
