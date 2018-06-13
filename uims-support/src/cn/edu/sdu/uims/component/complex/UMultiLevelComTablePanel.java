package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.complex.def.UMultiLevelComTableTemplate;
import cn.edu.sdu.uims.component.complex.form.MultiLevelDataI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UEventAttribute;

public class UMultiLevelComTablePanel extends UComplexPanel {
	private  List<MultiLevelDataI> comList;
	private JScrollPane scrollPane  = null;
	private JPanel innerPanel;
	private int colNum;
	private int panelWidth, panelHeight;
	private int comH[];
	private CheckActionEventProcessor checkActionEventProcessor= new CheckActionEventProcessor();
	
	private UMultiLevelComTableTemplate getUMultiLevelComTableTemplate(){
		return (UMultiLevelComTableTemplate)elementTemplate;
	}
	private class InnerPanel extends JPanel {
		
		private void drawComCheckBox(Graphics g, List<MultiLevelDataI> list) {
			MultiLevelCheckBox com;
			Rectangle r;
			UMultiLevelComTableTemplate t = getUMultiLevelComTableTemplate();
			for(int i = 0; i < list.size();i++) {
				com = (MultiLevelCheckBox)list.get(i);
				r = com.getBounds();
				g.drawRect(r.x-t.boundw, r.y-t.boundw, r.width+t.boundw*2, r.height+t.boundw*2);
				if(com.getSubList() != null) {
					drawComCheckBox(g, com.getSubList());
				}
			}
		}
		public void paintComponent(Graphics g) {
			drawComCheckBox(g, comList);
		}
	}
	private class CheckActionEventProcessor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			MultiLevelDataI data = (MultiLevelDataI)arg0.getSource();
			if(data.isSelected()) {
				while(data.getFather() != null) {
					if(!data.getFather().isSelected())
						data.getFather().setSelected(true);
					data  = data.getFather();
				}
			}
		}
		
	}

	
	private List<MultiLevelDataI> getMultiLevelComList(MultiLevelCheckBox fCom, int col, String da[], List[] dList, int xo, int yo) {
		UMultiLevelComTableTemplate t = getUMultiLevelComTableTemplate();
		List<MultiLevelDataI> list = new ArrayList<MultiLevelDataI>();
		MultiLevelCheckBox data, sd;
		ListOptionInfo dInfo;
		int i,j;
		for(i = 0; i < dList[col].size();i++) {
			dInfo = (ListOptionInfo)dList[col].get(i);
			data= new MultiLevelCheckBox();
			data.setFather(fCom);
			data.setLabel(dInfo.getLabel());
			data.setValue(dInfo.getValue());
			data.addActionListener(checkActionEventProcessor);
			data.setText(data.getLabel());
			data.setBounds(xo+t.boundw, yo + i *  comH[col] +t.boundw, t.comUnitWidth-2*t.boundw,  comH[col]-2*t.boundw);
			innerPanel.add(data);
			if(col < da.length-1)
				data.setSubList(getMultiLevelComList(data, col+1, da, dList, xo + t.comUnitWidth,yo + i *  comH[col]));
			else
				data.setSubList(null);
			list.add(data);
		}
		return list;
	}
	private void getComListByDictionary(String dics) {
		UMultiLevelComTableTemplate t = getUMultiLevelComTableTemplate();
		String da []= dics.split(";");
		colNum = 0;;
		if(da == null || da.length == 0)
			return;
		colNum = da.length;
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if(util == null)
			return ;
		int i,j,k;
		List []dList = new List[da.length];
		for(i = 0; i <da.length; i++)
			dList[i] = (ArrayList) util.getComboxListByCode(da[i]);
		comH = new int[da.length];
		comH[da.length-1] = t.comUnitHeight;
		for(i = da.length-2; i >= 0; i--) {
			comH[i] = comH[i+1]*dList[i+1].size();
		}
		panelWidth = t.comUnitWidth * da.length;
		panelHeight = t.comUnitHeight;
		for(i = 0; i < da.length;i++) {
			panelHeight *=  dList[i].size();
		}

		comList = getMultiLevelComList(null, 0,da, dList, 0, 0);
	}
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		if(elementTemplate == null || elementTemplate.dictionary == null || elementTemplate.dictionary == null) 
			return;
		innerPanel = new InnerPanel();
		innerPanel.setLayout(null);		
		getComListByDictionary(elementTemplate.dictionary);
		Dimension d = new Dimension(panelWidth,panelHeight);
		innerPanel.setSize(d);
		innerPanel.setPreferredSize(d);
		scrollPane = new JScrollPane(innerPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.updateUI();
		this.setLayout(new BorderLayout());
		this.add(scrollPane,BorderLayout.CENTER);
	}
	
	private void addSelectComToList(List<String []> list, List<MultiLevelDataI> cList, String []a, int index){
		int i,j;
		MultiLevelDataI com;
		String []ta;
		for(i = 0; i < cList.size();i++) {
			com = cList.get(i);
			if(com.isSelected()) {
				a[index] = com.getValue();
				if(com.getSubList() != null) {
					addSelectComToList(list, com.getSubList(), a, index+1);
				}
				else {
					ta = new String [a.length];
					for(j= 0; j < a.length;j++)
						ta[j] = a[j];
					list.add(ta);
				}
			}
		}
	}

	public Object getData() {
		if(colNum == 0)
			return null;
		List<String []> list = new ArrayList<String[]>();
		String []a = new String [colNum];
		addSelectComToList(list, comList, a, 0);
		return list;
	}
	private void changeStringToHashMap(HashMap map, String []a, int index){
		if(index == a.length -1) {
			map.put(a[index], true);
		}else {
			HashMap m = (HashMap) map.get(a[index]);
			if(m == null) {
				m = new HashMap();
				map.put(a[index], m);
			}
			changeStringToHashMap(m,a, index+1);
		}
	}

	private HashMap changeStringListToHashMap(List<String[]> list){
		if(list == null || list.size() == 0)
			return null;
		HashMap map= new HashMap();
		int i;
		String a[];
		for(i =0; i < list.size();i++) {
			a = list.get(i);
			changeStringToHashMap(map, a, 0);
		}
		return map;
	}
	private void setComCheckStatus(List<MultiLevelDataI> list,  HashMap map) {
		MultiLevelDataI com;
		Object o;
		for(int i = 0; i < list.size();i++) {
			com = list.get(i);
			if(map == null) {
				com.setSelected(false);
				if(com.getSubList() != null)
					setComCheckStatus(com.getSubList(),null);
			}else {
				o = map.get(com.getValue());
				if(o != null) {
					com.setSelected(true);
				}else
					com.setSelected(false);
				if(com.getSubList() != null)
					setComCheckStatus(com.getSubList(),(HashMap)o);
			}
		}
	}

	public void setData(Object obj) {
		HashMap map= null;
		if(obj == null ) 
			map = null;
		else
			map = changeStringListToHashMap((List<String[]>)obj);
		setComCheckStatus(comList, map);
	}
	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_SELECTOBJECT)) {
//				sendSelectObjectEvent = true;
			}
		}
	}

}
