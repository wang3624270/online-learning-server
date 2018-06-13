package cn.edu.sdu.uims.component.groupcomponent.region;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.trans.URect;

public class RegionDescriptionHorizontal extends RegionDescription {
	public void initContents() {
		UComponentI com;
		int n = comList.size();
		panel = new JPanel();
		//panel.setLayout(new GridLayout(1, n));
		Box baseBox = Box.createHorizontalBox();
		URect rect;
		for (int i = 0; i < n; i++) {
			com = (UComponentI) comList.get(i);
			//panel.add(com.getComponent());
			baseBox.add(com.getAWTComponent());
			//设置组件大小
			rect=com.getBoundRect();
			if(rect!=null){
				innerWidth+=rect.w;
				if(innerHeight<rect.h){
					innerHeight=rect.h;
				}
			}
		}
		panel.setLayout(new BorderLayout());
		panel.add(baseBox);
		Dimension dimension = new Dimension(innerWidth, innerHeight);
		panel.setMinimumSize(dimension);
		panel.setMaximumSize(dimension);
		panel.setPreferredSize(dimension);
	}

}
