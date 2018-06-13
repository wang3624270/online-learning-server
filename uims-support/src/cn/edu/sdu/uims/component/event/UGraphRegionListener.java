package cn.edu.sdu.uims.component.event;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface UGraphRegionListener extends ActionListener, MouseListener, MouseMotionListener {
	public void commandSelected(UGraphRegionEvent e);
	public void itemStatusChanged(UGraphRegionEvent e);
}
