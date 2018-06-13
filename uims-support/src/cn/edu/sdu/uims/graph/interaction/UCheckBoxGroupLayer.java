package cn.edu.sdu.uims.graph.interaction;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.component.checkbox.UCheckBoxGroup;
import cn.edu.sdu.uims.component.checkbox.UCheckBoxValue;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.GraphInteractionEvent;
import cn.edu.sdu.uims.component.event.GraphInteractionListener;
import cn.edu.sdu.uims.component.event.UEventListener;
import cn.edu.sdu.uims.def.UCheckBoxGroupTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;

public class UCheckBoxGroupLayer extends UCheckBoxGroup {

	private GraphInteractionListener graphInteractionListener = null;

	public UCheckBoxGroupLayer() {
		actionListener = new ToolActionProcessor();
	}

	public void initGraphContent() {
		// TODO Auto-generated method stub
		initContents();
		this.setBorder(BorderFactory.createEtchedBorder());
	}

	public void addEvents(UEventAttribute[] events) {
		UEventListener l = getUParent().getEventAdaptor();
		for (int i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_GRAPHINTERACTION)) {
				if (l instanceof GraphInteractionListener)
					graphInteractionListener = (GraphInteractionListener) l;
			}
		}
	}
	public Object getData() {
		// TODO Auto-generated method stub
		if (checkBoxs == null)
			return null;
		Boolean ret[] = new Boolean[checkBoxs.length];
		for (int i = 0; i < checkBoxs.length; i++) {
			if (checkBoxs[i].isSelected()) {
				ret[i] = true;
			} else
				ret[i] = false;
		}
		return ret;
	}

	public void setCheckBoxInfo(UCheckBoxGroupTemplate temp, int index) {
		ListOptionInfo li = (ListOptionInfo)temp.addedDatas.get(index);
		checkBoxs[index].setListOptionInfo(li);
		checkBoxs[index].setText(li.getLabel());
		checkBoxs[index].setSelected(temp.checks[index]);
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return;
		int i;
		UCheckBoxGroupTemplate temp = (UCheckBoxGroupTemplate) obj;
		if (temp.row == row && temp.column == column && checkBoxs != null
				&& temp.addedDatas != null
				&& checkBoxs.length == temp.addedDatas.size()) {
			for (i = 0; i < checkBoxs.length; i++) {
				setCheckBoxInfo(temp, i);
			}
		} else {
			this.removeAll();
			this.setLayout(new GridLayout(temp.row, temp.column));
			if (temp.addedDatas == null)
				return;
			checkBoxs = new UCheckBoxValue[temp.addedDatas.size()];
			for (i = 0; i < checkBoxs.length; i++) {
				if (temp.addedDatas.get(i) != null) {
					checkBoxs[i] = new UCheckBoxValue();
					checkBoxs[i].addActionListener(this.getActionListener());
					checkBoxs[i].setTemplate(temp);
					setCheckBoxInfo(temp, i);
					this.add(checkBoxs[i].getAWTComponent());
				}
			}	
		}
		Dimension d = new Dimension(temp.columnWidth*temp.column, temp.row* temp.rowHeight);
		this.setPreferredSize(d);
		this.setSize(d);
	}

	public int getIndexByComponent(UCheckBoxValue com) {
		int index;
		for (index = 0; index < checkBoxs.length; index++) {
			if (checkBoxs[index] == com)
				return index;
		}
		return -1;
	}

	private class ToolActionProcessor implements ActionListener {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			UCheckBoxValue acom = (UCheckBoxValue) e.getSource();
			UCheckBoxGroupTemplate template = (UCheckBoxGroupTemplate) acom
					.getTemplate();
				if (graphInteractionListener != null) {
					GraphInteractionEvent re = new GraphInteractionEvent(this,
							getIndexByComponent(acom), acom.isSelected());
					graphInteractionListener.checkSatatusSelected(re);
				}
		}
	}
}
