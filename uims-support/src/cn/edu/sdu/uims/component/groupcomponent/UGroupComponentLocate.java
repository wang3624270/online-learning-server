package cn.edu.sdu.uims.component.groupcomponent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.panel.UFPanel;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.trans.URect;

public class UGroupComponentLocate extends UGroupComponent {

	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(null);
		}
		container.add(com.getAWTComponent());
		comnum++;
	}

	public URect makeSubComponentsBox(UComponentI[] coms) {
		UElementTemplate et;
		URect rect2;
		UComponentI com;
		UPanelI p;
		URect rect = null;
		if(coms == null)
			return null;
		int x1, y1, x2, y2, xt, yt, w, h;
		for (int i = 0; i < coms.length; i++) {
			et = coms[i].getElementTemplate();
			x1 = et.x;
			y1 = et.y;
			if (coms[i] instanceof UPanelI) {
				p = (UPanelI) coms[i];
				rect2 = p.makeInnerBounds();
				if (rect2 != null) {
					x2 = x1 + rect2.w;
					y2 = y1 + rect2.h;
				} else {
					x2 = x1 + 1;
					y2 = y1 + 1;
				}
			} else {
				x2 = x1 + et.w;
				y2 = y1 + et.h;
			}
			if (rect == null) {
				rect = new URect(x1, y1, x2 - x1, y2 - y1);
			} else {
				if (rect.x > x1)
					xt = x1;
				else
					xt = rect.x;
				if (rect.y > y1)
					yt = y1;
				else
					yt = rect.y;
				if (rect.x + rect.w < x2) {
					w = x2 - xt;
				} else {
					w = rect.x + rect.w - xt;
				}
				if (rect.y + rect.h < y2) {
					h = y2 - yt;
				} else {
					h = rect.y + rect.h - yt;
				}
				rect.x = xt;
				rect.y = yt;
				rect.w = w;
				rect.h = h;
			}
		}
		rect.w += rect.x;
		rect.h += rect.y;
		return rect;
	}

	public void setComponentBounds(UComponentI[] coms) {
		UElementTemplate et;
		URect rect2;
		UFPanel p;
		int x1, y1, x2, y2;
		if(coms == null)
			return;
		for (int i = 0; i < coms.length; i++) {
			et = coms[i].getElementTemplate();
			x1 = et.x;
			y1 = et.y;
			if (coms[i] instanceof UFPanel) {
				p = (UFPanel) coms[i];
				x2 = x1 + et.w;
				y2 = y1 + et.h;
//				rect2 = p.gerInnerBounds();
//				if (rect2 != null) {
//					x2 = x1 + rect2.w;
//					y2 = y1 + rect2.h;
//				} else {
//					x2 = x1 + 1000;
//					y2 = y1 + 1000;
//				}
				p.setAbsolutePositon(x1, y1, x2 - x1, y2 - y1);
			} else {
				x2 = x1 + et.w;
				y2 = y1 + et.h;
				coms[i].getAWTComponent().setBounds(x1, y1, x2 - x1, y2 - y1);
			}
		}
	}

	public boolean getCanScrolling() {
		// TODO Auto-generated method stub
		return true;
	}
}
