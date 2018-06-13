package cn.edu.sdu.uims.itms.eventor;

import java.awt.event.MouseEvent;
import java.util.EventObject;

import cn.edu.sdu.uims.itms.cursor.ICursorDataBase;
import cn.edu.sdu.uims.itms.cursor.ICursorDataPoints;
import cn.edu.sdu.uims.trans.UFPoint;

public class IEventorLeftDown extends IEventorBase {
	public void process(ICursorDataBase dataToImg, EventObject e){
		MouseEvent me = (MouseEvent)e;
		UFPoint pp = new UFPoint(me.getX(), me.getY());
		ICursorDataPoints pd = (ICursorDataPoints)dataToImg;
		pd.addPoint(pp);
	}

}
