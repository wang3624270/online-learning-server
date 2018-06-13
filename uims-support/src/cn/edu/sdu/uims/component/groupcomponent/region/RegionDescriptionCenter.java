package cn.edu.sdu.uims.component.groupcomponent.region;

import java.awt.BorderLayout;
import java.awt.Dimension;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.trans.URect;

public class RegionDescriptionCenter extends RegionDescription {

	public void addToContainer() {
		UComponentI com = (UComponentI) comList.get(0);
		container.add(com.getAWTComponent(), BorderLayout.CENTER);
	}

	public void initContents() {
		URect rect;
		UComponentI com = (UComponentI) comList.get(0);
		// 设置组件大小
		rect = com.getBoundRect();
		if (rect != null) {
			innerHeight = rect.h;
			innerWidth = rect.w;
		}
		Dimension dimension = new Dimension(innerWidth, innerHeight);
		container.setMinimumSize(dimension);
		container.setMaximumSize(dimension);
		container.setPreferredSize(dimension);
	}
}
