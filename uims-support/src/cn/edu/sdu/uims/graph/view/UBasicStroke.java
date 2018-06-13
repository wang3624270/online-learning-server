package cn.edu.sdu.uims.graph.view;

import java.awt.BasicStroke;
import java.io.Serializable;

public class UBasicStroke extends BasicStroke implements Serializable {

	public UBasicStroke(float width, int cap, int join, float miterlimit,
			float[] dash, float dash_phase) {
		super(width, cap, join, miterlimit, dash, dash_phase);
	}

	public UBasicStroke(float width) {
		super(width);
	}

	public UBasicStroke(float width, int cap, int join) {
		super(width, cap, join);
	}

	public UBasicStroke(float width, int cap, int join, float miterlimit) {
		super(width, cap, join, miterlimit);
	}

}
