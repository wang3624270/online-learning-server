package cn.edu.sdu.uims.component.label;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.def.ULabelMultiLineTemplate;
import cn.edu.sdu.uims.util.UimsUtils;

public class ULabelMultiLine extends ULabelTitle {
	private ULabel lines[];
	private JPanel lablesPanel;
	private UScrollPane labelsScrollPane;
	
	public void initContents() {
		if(template == null){
			return;
		}
		this.setLayout(new BorderLayout());
		lablesPanel = new JPanel();
		ULabelMultiLineTemplate temp = (ULabelMultiLineTemplate)template;
		setLayout(new BorderLayout());
		if(temp.fixHeight) {
			labelsScrollPane = new UScrollPane(lablesPanel);
			add(labelsScrollPane, BorderLayout.CENTER);
		}
		else {
			add(lablesPanel, BorderLayout.CENTER);
		}
	}
	public void setData(Object obj) {
		ULabelMultiLineTemplate temp = (ULabelMultiLineTemplate)template;
		String s = null;
		if (obj != null)
			s = obj.toString();
		List<String> strList = UimsUtils.getMultiStringList(s, temp.charMax);
		resetLabels(strList);
	}
	public void resetLabels(List<String> strList){
		ULabelMultiLineTemplate temp = (ULabelMultiLineTemplate)template;
		int rn;
		if(strList == null || strList.size() == 0) {
			lines = null;
			rn = 0;
		}
		else {
			rn = strList.size();
			lines = new ULabel[rn];
		}
		int i;
		if(rn== 0)			
			lablesPanel.setLayout(new GridLayout(1, 1));
		else
			lablesPanel.setLayout(new GridLayout(rn, 1));
		lablesPanel.removeAll();
		for (i = 0; i < rn; i++) {
			lines[i] = new ULabel();
			lines[i].setText(strList.get(i));
			lablesPanel.add(lines[i]);
		}
		if(labelsScrollPane !=null){
			labelsScrollPane.updateUI();
		}else {
			Dimension d = lablesPanel.getSize();
			d.height = rn*25;
			this.setSize(d);
			this.setPreferredSize(d);
			lablesPanel.updateUI();
		}
	}
	public void setText(String arg0) {
		// TODO Auto-generated method stub
		setData(arg0);
	}
	
}
