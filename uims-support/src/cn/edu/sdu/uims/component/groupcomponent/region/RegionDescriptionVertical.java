package cn.edu.sdu.uims.component.groupcomponent.region;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicPanelUI;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.trans.URect;

public class RegionDescriptionVertical extends RegionDescription {
	public void initContents() {
		UComponentI com;
		int n = comList.size();
		panel = new JPanel();
		panel.setUI(new BasicPanelUI());
		// panel.setLayout(new GridLayout(n,1));
		Box baseBox = Box.createVerticalBox();
		URect rect;
		for (int i = 0; i < n; i++) {
			com = (UComponentI) comList.get(i);
			// panel.add(com.getComponent());
			baseBox.add(com.getAWTComponent());
			//设置组件大小
			rect=com.getBoundRect();
			if(com instanceof UPanelI) {
				Dimension d = new Dimension(rect.w,rect.h);
//				com.getComponent().setBounds(rect.x, rect.y, rect.w, rect.h);
//				com.getComponent().setSize(new Dimension(1,1));
				com.getAWTComponent().setPreferredSize(d);
			}
			if(rect!=null){
				innerHeight+=rect.h;
				if(innerWidth<rect.w){
					innerWidth=rect.w;
				}
			}
			
		}
		panel.add(baseBox);
		
		Dimension dimension = new Dimension(innerWidth, innerHeight);
		panel.setMinimumSize(dimension);
		panel.setMaximumSize(dimension);
		panel.setPreferredSize(dimension);
	}
}
