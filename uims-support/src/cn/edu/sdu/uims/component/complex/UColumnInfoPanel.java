package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.complex.form.UColumnInfoForm;
import cn.edu.sdu.uims.component.complex.form.UColumnInfoItemForm;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.SelectObjectEvent;
import cn.edu.sdu.uims.def.UEventAttribute;

public class UColumnInfoPanel extends UComplexPanel implements ListSelectionListener{
	private  InfoDispPanel[] infoPanels= null;
	private boolean sendSelectObjectEvent= false;
	public Object getData() {
		return null;
	}
	private class InfoDispPanel extends JPanel {
		private UColumnInfoPanel owner = null;
		private UColumnInfoForm infoForm;
		private JList jList;
		public InfoDispPanel(UColumnInfoPanel owner){
			this.owner = owner;
		}
		public void init(UColumnInfoForm form){
			if(form == null)
				return;
			String title = "";
			boolean b = UimsFactory.getClientMainI().isEnglishVersion();
			if(b && form.getEnTitle() != null)
				title = form.getEnTitle();
			else
				title = form.getTitle();
			infoForm = form;
			List<UColumnInfoItemForm> dList = form.getDataList();
			setLayout(new BorderLayout());
			JLabel l= new JLabel(title);
			l.setHorizontalAlignment(SwingConstants.LEFT);
			add(l,BorderLayout.NORTH);
			jList = new JList();
			if(dList != null && dList.size() !=0)
				jList.setListData(dList.toArray());
//			else
//				jList.setListData((Object[])null);
			jList.addListSelectionListener(owner);
			JScrollPane sp = new JScrollPane(jList);
			add(sp,BorderLayout.CENTER);
		}
		public JList getJList(){
			return jList;
		}
		public UColumnInfoForm getInfoForm(){
			return infoForm;
		}
	}
	public void setData(Object obj) {
		if(elementTemplate == null)
			return;
		GridLayout gl = null;
		this.removeAll();
		if(obj == null){
			gl= new GridLayout(1,1);
			this.setLayout(gl);
			add(new JLabel("您没有要处理的业务或者提示阅读的信息！"));
			infoPanels = null;
			return;
		}
		 List <UColumnInfoForm> dataList  = (List <UColumnInfoForm>)obj;
		int columnNum = elementTemplate.colNum;
		int count = dataList.size();
		int blockRowNum = count / columnNum;
		if (count % columnNum != 0)
			blockRowNum++;
		infoPanels = new InfoDispPanel[columnNum*blockRowNum];
		gl = new GridLayout(blockRowNum,columnNum);	
		this.setLayout(gl);
		int row, col, index, i;
		UColumnInfoForm form;
		UColumnInfoItemForm iForm;
		for (row = 0; row < blockRowNum; row++) {
			for (col = 0; col < columnNum; col++) {
				index = row * columnNum + col;
				if (index < count) {
					form = (UColumnInfoForm) dataList.get(index);
				}else {
					form = null;
				}
				infoPanels[index] = new InfoDispPanel(this);
				infoPanels[index].init(form);
				this.add(infoPanels[index]);
			}
		}
		
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		JList o = (JList)arg0.getSource();
		for(int i = 0; i < infoPanels.length;i++) {
			if(infoPanels[i].getJList()==o) {
				Object ret[] = new Object[2];
				ret[0] = infoPanels[i].getInfoForm();
				ret[1] = o.getSelectedValue();
				if(sendSelectObjectEvent) {
					SelectObjectEvent e =new SelectObjectEvent(this, ret);
					this.getUParent().getEventAdaptor().selectObjectSelection(e);
				}				
				return;
			}
		}
	}
	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_SELECTOBJECT)) {
				sendSelectObjectEvent = true;
			}
		}
	}

}
