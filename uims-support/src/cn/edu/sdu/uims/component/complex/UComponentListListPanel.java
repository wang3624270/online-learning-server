package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.edu.sdu.uims.component.button.UButton;
import cn.edu.sdu.uims.component.complex.def.UComAttributeDataI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UEventAttribute;


public class UComponentListListPanel extends UComplexPanel {
	private List<List<UComAttributeDataI>> comListList;
	private JPanel innerPanel = null;
	private JScrollPane scrollPane = null;
	private static int BUTTON_WIDTH = 100;
	private static int BUTTON_HEIGHT = 30;
	@Override
	public void initContents() {
		super.initContents();
		scrollPane = new JScrollPane(innerPanel);
		add(scrollPane,BorderLayout.CENTER);
	}
	
	private void refreshPanel(){
		innerPanel.removeAll();
		if(comListList == null || comListList.size()==0)
			return;
		int rowNum = comListList.size();
		innerPanel.setLayout(new GridLayout(rowNum,1));
		int row, col,colNum;
		List<UComAttributeDataI> comList;
		JPanel p;
		FlowLayout lay;
		UButton button;
		UComAttributeDataI data;
		for( row = 0; row < rowNum;row++) {
			p = new JPanel();
			p.setLayout(new FlowLayout(FlowLayout.LEFT));
			comList= comListList.get(row);
			colNum = comList.size();
			for(col = 0; col < colNum;col++) {
				data  = comList.get(col);
				button = new UButton();
				button.setText(data.getContent());
				button.setComponentName(data.getName());
				if(data.getCmd() != null)
					button.setActionCommand(data.getCmd());
				else
					button.setActionComandString(data.getName());
				if(this.actionListener != null)
					button.addActionListener(this.actionListener);
				button.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
				p.add(button);
			}
			innerPanel.add(p);
		}
		resetScrollPanel();		
	}
	private void resetScrollPanel(){
		int width = 1, height = 1;
		int colNum, rowNum;
		if(comListList != null && comListList.size()!= 0) {
			rowNum = comListList.size();
			colNum = 1;
			for(int row = 0; row < comListList.size();row++) {
				if(comListList.get(row).size() > colNum)
					colNum = comListList.get(row).size();
			}
			width = BUTTON_WIDTH * colNum;
			height = BUTTON_HEIGHT * rowNum;
		}
		Dimension d = new Dimension(width, height);
		innerPanel.setPreferredSize(d);
		innerPanel.setSize(d);
		scrollPane.updateUI();
			
	}
	public void setData(Object o){
		if( o == null)
			comListList = null;
		comListList = (List<List<UComAttributeDataI>>)o;
		refreshPanel();
	}
	@Override
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				this.actionListener = getUParent().getEventAdaptor();
			}
		}
	}
	
}
