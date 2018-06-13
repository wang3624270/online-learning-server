package cn.edu.sdu.uims.component.complex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import cn.edu.sdu.uims.component.complex.form.ScheduleCellData;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.SelectObjectEvent;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UEventAttribute;

public class UCurriculumEditPanel extends UComplexPanel implements MouseListener{
	public static final int HORIZONTAL_LEFT = 0;
	public static final int HORIZONTAL_CENTER = 1;
	public static final int HORIZONTAL_RIGHT = 2;
	public static final int VERTICAL_TOP = 0;
	public static final int VERTICAL_CENTER = 1;
	public static final int VERTICAL_BOTTOM = 2;

	private int unitWidth = 93;

	private int unitHeight = 30;

	private int unitTextWidthNum = 12;

	private int columnHeight = 30;

	private int rowWidth = 30;

	final Color blackColor = new Color(0, 0, 0);

	final Color grayColor = new Color(224, 224, 224);

	final Color redColor = new Color(255, 0, 0);

	final String weekString[] = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };

	final Font bFont = new Font("Dialog", Font.BOLD, 14);

	final Font sFont = new Font("Dialog", Font.PLAIN, 12);

	
	private ScheduleCellData[][] scheduleMatrix;	
	private boolean sendSelectObjectEvent= false;
	
	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_SELECTOBJECT)) {
				sendSelectObjectEvent = true;
			}
		}
	}

	@Override
	public void initContents() {
//		this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
		super.initContents();
		addMouseListener(this);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Dimension d = this.getSize();
		int w = d.width;
		int h = d.height;
		unitWidth  = (w -this.rowWidth) / UConstants.SCHEDULE_DAY_NUM;
		if(unitWidth < 80)
			unitWidth = 80;
		unitHeight  = (h -this.columnHeight) / UConstants.SCHEDULE_SECTION_NUM;
		if(unitHeight < 30)
			unitHeight = 30;
		g.setColor(blackColor);
		drawTableFrame(g);
		drawAllUseUnit(g);
	}


	public void drawTableFrame(Graphics g) {
		int i = 0;
		int x1, x2, y1, y2;
		g.setFont(sFont);
		g.drawRect(0, 0, rowWidth + unitWidth * UConstants.SCHEDULE_DAY_NUM, 
				columnHeight+ UConstants.SCHEDULE_SECTION_NUM * unitHeight);
		g.drawLine(0, 0 + columnHeight, rowWidth + unitWidth * UConstants.SCHEDULE_DAY_NUM,
				 columnHeight);
		g.drawLine(rowWidth, 0, rowWidth,  columnHeight
				+ unitHeight * UConstants.SCHEDULE_SECTION_NUM);
		x1 = rowWidth;
		x2 = x1 + unitWidth;
		y1 = 0;
		y2 =  columnHeight;
		for (i = 0; i < UConstants.SCHEDULE_DAY_NUM; i++) {
			g.drawLine(x1, y1, x1, y2);
			drawStringInBox(g, sFont, weekString[i], x1,
					y1, unitWidth, columnHeight);
			x1 += unitWidth;
			x2 += unitWidth;
		}
		y1 =  columnHeight;
		y2 = y1 + unitHeight;
		x1 = 0;
		x2 = rowWidth;
		for (i = 0; i < UConstants.SCHEDULE_SECTION_NUM; i++) {
			g.drawLine(x1, y2, x2, y2);
			drawStringInBox(g, sFont, "" + (i + 1), x1,
					y1, rowWidth, unitHeight);
			y1 += unitHeight;
			y2 += unitHeight;
		}

		g.setColor(grayColor);
		x1 =  rowWidth + unitWidth;
		y1 =  columnHeight;
		y2 =  columnHeight + unitHeight * UConstants.SCHEDULE_SECTION_NUM;
		for (i = 0; i < UConstants.SCHEDULE_DAY_NUM - 1; i++) {
			g.drawLine(x1, y1, x1, y2);
			x1 += unitWidth;
		}

		x1 =  rowWidth;
		x2 = x1 + unitWidth * UConstants.SCHEDULE_DAY_NUM;
		y1 = columnHeight + unitHeight;
		for (i = 0; i < UConstants.SCHEDULE_SECTION_NUM - 1; i++) {
			g.drawLine(x1, y1, x2, y1);
			y1 += unitHeight;
		}
	}

	public void drawAllUseUnit(Graphics g) {
		g.setColor(blackColor);
		if(scheduleMatrix == null )
			return;
		int i,j;
		int x1 = 0, y1 = columnHeight;
		for(i = 0; i < scheduleMatrix.length; i++) {
			x1 =  rowWidth;
			for(j = 0;j < scheduleMatrix[i].length; j++,x1 += unitWidth) {
				if(scheduleMatrix[i][j].scheduleId == null || scheduleMatrix[i][j].data == null ||
						scheduleMatrix[i][j].spn == null || scheduleMatrix[i][j].spn.equals(0))
					continue;
				g.drawRect(x1, y1, unitWidth, unitHeight * scheduleMatrix[i][j].spn);
				int k = 0;
				int pos = 0;
				String ds;
				int len = scheduleMatrix[i][j].title.length();
				while (k < scheduleMatrix[i][j].spn && pos < len) {
					if (pos + unitTextWidthNum < len) {
						ds = scheduleMatrix[i][j].title.substring(pos, pos + unitTextWidthNum);
						pos += unitTextWidthNum;
					} else {
						ds = scheduleMatrix[i][j].title.substring(pos, len);
						pos = len;
					}
					drawStringInBox(g, sFont, ds, x1, y1 + k
							* unitHeight, unitWidth, unitHeight);
					k++;
				}
			}
			y1 += unitHeight;
		}
	}
	public void doSelectSchedule(Point p){
		if(scheduleMatrix == null )
			return;
		int i,j;
		int x1 = 0, y1 = columnHeight;
		for(i = 0; i < scheduleMatrix.length; i++) {
			x1 =  rowWidth;
			for(j = 0;j < scheduleMatrix[i].length; j++,x1 += unitWidth) {
				if(scheduleMatrix[i][j].spn == null || scheduleMatrix[i][j].spn.equals(0))
					continue;
				if(p.x >= x1 && p.x <= x1 + unitWidth && p.y >= y1 && p.y <= y1 + unitHeight * scheduleMatrix[i][j].spn) {
					if(sendSelectObjectEvent) {
						Object a[] = new Object[3];
						a[0] = scheduleMatrix[i][j].data;
						a[1] = i;
						a[2] = j;
						SelectObjectEvent e =new SelectObjectEvent(this, a);
						this.getUParent().getEventAdaptor().selectObjectSelection(e);
					}				
					return ;
				}
			}
			y1 += unitHeight;
		}
	}

	public void drawBox(Graphics g, int x, int y, int w, int h) {
		g.drawRect(x, y, w, h);
	}
	public void drawStringInBox(Graphics g, Font font, String s, int x, int y, int w, int h) {
		drawStringInBox(g,font, s, x, y, w, h, HORIZONTAL_CENTER,VERTICAL_CENTER);
	}
	public void drawStringInBox(Graphics g, Font font, String s, int x, int y, int w, int h, int xType, int yType) {
		int fh, fw, xo=0, t1=0, t2=0, yo=0;
		Graphics2D g2 = (Graphics2D) g;
		if(s == null)
			s = "" ;
		Rectangle2D b = font.getStringBounds(s, 0, s.length(), g2
				.getFontRenderContext());
//		g.setFont(font);
		fh = (int) b.getHeight();
		fw = (int) b.getWidth();
		switch(xType){
		case HORIZONTAL_LEFT: 
			xo = x;
			break;
		case HORIZONTAL_CENTER:
			xo = x + (w - fw)/2;
			break;
		case HORIZONTAL_RIGHT:
			xo = x + w -fw;
			break;
		}
		switch(yType){
		case VERTICAL_TOP:
			yo = y + fh;
			break;
		case VERTICAL_CENTER:
			t1 = y + (h + fh)/2;
			t2 = (int)(b.getMinY())/2;
			yo = t1+t2;
			break;
		case VERTICAL_BOTTOM:
			yo = y + h - fh;
		}
		g.drawString(s, xo, yo);
	}

	public int getColNum(int x) {
		if (x >  rowWidth && x <  rowWidth + unitWidth * UConstants.SCHEDULE_DAY_NUM)
			return (x  - rowWidth) / unitWidth;
		else
			return -1;
	}

	public int getRowNum(int y) {
		if (y >  columnHeight
				&& y <  columnHeight + unitHeight * UConstants.SCHEDULE_SECTION_NUM)
			return (y  - columnHeight) / unitHeight;
		else
			return -1;
	}
	public Object getData() {
		return scheduleMatrix;
	}

	public void setData(Object obj) {
		if(obj != null && obj instanceof ScheduleCellData[][])
			scheduleMatrix = (ScheduleCellData[][]) obj;
		else
			scheduleMatrix = null;
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p = e.getPoint();
		if(e.getButton()== MouseEvent.BUTTON1) {
			doSelectSchedule(p);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
