package cn.edu.sdu.uims.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UTextUtil;

public class UVText extends UVElement {

	public UVText() {
		super();
	}

	@SuppressWarnings("unchecked")
	public void draw(Graphics g, UMatrix m) {
		super.draw(g, m);
		String s;
		int  dh, yt, i;
		if (text == null || text.equals(""))
			return;
		URect r = new URect(x,y,w,h);
		URect rt = m.logicToView(r);
		if (arrangeType == UConstants.TEXTARRANGE_HORIZONTAL) {
			List list = UTextUtil.getRowLine(text, font.font, g, rt.w, 0);
			dh = rt.h / list.size();
			yt = rt.y;
			for (i = 0; i < list.size(); i++) {
				s = (String) list.get(i);
				UVUtil.drawStringBox(g,s,color,font,rt.x,yt, rt.w, dh,this.horizontalAlignment, this.verticalAlignment,false );
				yt += dh;
			}
			
//			UVUtil.drawStringBox(g,text,color,font,rt.x,rt.y, rt.w, rt.h,horizontalAlignment, verticalAlignment );
		}
		else if(arrangeType == UConstants.TEXTARRANGE_VERTICAL){
			UVUtil.drawStringRotate(g,text,color, font,rt.x, rt.y, rt.w,rt.h,270,Color.white);
		}
	}
	
}
