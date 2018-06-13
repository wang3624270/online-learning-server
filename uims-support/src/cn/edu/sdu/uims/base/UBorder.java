package cn.edu.sdu.uims.base;

import java.awt.BasicStroke;
import java.io.Serializable;

import javax.swing.BorderFactory;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.graph.view.UBasicStroke;
import cn.edu.sdu.uims.service.UFactory;

public class UBorder implements Serializable{
	public String type;
	public String name;
	public float width = 1.0f;
	public int layout = UConstants.BORDER_LAYOUT_ALL;
	public float []dash;
	transient public Object obj = null;
	public String colorName ;
	public void getObject(){
		if(obj != null)
			return ;
		if (type == null || type.equals("stroke")) {
			obj = new UBasicStroke(width,
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10,
					dash, 0);
		} else {
			int iw = (int) (width);
			if (iw == 0
					|| layout == UConstants.BORDER_LAYOUT_NULL) {
				obj = null;
			} else {
				obj = BorderFactory.createLineBorder(
						UFactory.getModelSession().getColorByName(colorName).color, iw);
			}
		}

	}
}
