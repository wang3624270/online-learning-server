package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.groupcomponent.region.RegionDescription;
import cn.edu.sdu.uims.component.groupcomponent.region.RegionDescriptionCenter;
import cn.edu.sdu.uims.component.groupcomponent.region.RegionDescriptionEast;
import cn.edu.sdu.uims.component.groupcomponent.region.RegionDescriptionNorth;
import cn.edu.sdu.uims.component.groupcomponent.region.RegionDescriptionWest;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.frame.UClientFrame;

public class UGroupComponentBorderRegion extends UGroupComponent {
	public static int REGIONMAXNUM = 5;
	RegionDescription[] regionDatas;

	private RegionDescription getRegionDescription(int layout) {
		switch (layout) {
		case UConstants.ALIGNMENT_BOTTOM:
			return new RegionDescriptionNorth();
		case UConstants.ALIGNMENT_CENTER:
			return new RegionDescriptionCenter();
		case UConstants.ALIGNMENT_LEFT:
			return new RegionDescriptionWest();
		case UConstants.ALIGNMENT_RIGHT:
			return new RegionDescriptionEast();
		case UConstants.ALIGNMENT_TOP:
			return new RegionDescriptionNorth();
		}
		return new RegionDescriptionCenter();
	}

	public void addComponentBefore() {
		regionDatas = new RegionDescription[REGIONMAXNUM];
		container.setLayout(new BorderLayout());
	}

	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		int layout = elementTemplate.layout;
		if (layout < 0 || layout >= 5)
			return;
		if (regionDatas[layout] == null) {
			regionDatas[layout] = getRegionDescription(layout);
			regionDatas[layout].setContainer(container);
		}
		regionDatas[layout].addCom(com);
		comnum++;
	}

	public void addComponentAfter() {
		int i;
		for (i = 0; i < regionDatas.length; i++) {
			if (regionDatas[i] != null) {
				regionDatas[i].initContents();
				regionDatas[i].addToContainer();
			}
		}
		int w = 100, h = 100;
		UClientFrame client = UClientFrame.getInstance();
		if (client == null)
			return;
		Dimension dimension = client.getSize();
		int cw = (int) dimension.getWidth();
		int ch = (int) dimension.getHeight();
		w = cw - 220;
		h = ch - 180;
		this.setShellBounds(0, 0, w, h);
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		Dimension dimension = new Dimension(w, h);
		container.setPreferredSize(dimension);

		//
		container.setMinimumSize(dimension);
	}
}
