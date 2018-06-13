package cn.edu.sdu.uims.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UTextUtil;

public class UVElement implements UVElementI {
	protected int x,y,w,h;
	protected UFont font = UFactory.getModelSession().getDefaultFont();
	protected UColor color = UFactory.getModelSession().getDefaultColor();
	protected UBorder border = UFactory.getModelSession().getDefaultStrokeBorder();
	protected String text = "";
	protected int horizontalAlignment = UConstants.ALIGNMENT_CENTER;
	protected int verticalAlignment = UConstants.ALIGNMENT_CENTER;
	protected int arrangeType = UConstants.TEXTARRANGE_HORIZONTAL;
	private String componentName;
	private UPanelI parent;
	protected UElementTemplate elementTemplate;
	public void draw(Graphics g, UMatrix m) {
		Graphics2D g2 = (Graphics2D)g;
//		g.setColor(border.color.color);
		g.setColor(Color.blue);
		g.setFont(font.font);
		if(border.obj instanceof Stroke)
			g2.setStroke((Stroke)(border.obj));
		else {
			g2.setStroke((Stroke)(UFactory.getModelSession().getDefaultStrokeBorder().obj));
		}
		
		drawBorder(g,m);
		
	}
	public void drawBorder(Graphics g, UMatrix m){
		URect rs = new URect(x,y,w,h);
		URect rd = m.logicToView(rs); 
		switch(border.layout){
		case UConstants.BORDER_LAYOUT_NULL:
			break;
		case UConstants.BORDER_LAYOUT_ALL:
			g.drawRect(rd.x, rd.y, rd.w, rd.h);
			break;
		case UConstants.BORDER_LAYOUT_TOP:
			g.drawLine(rd.x, rd.y, rd.x+rd.w, rd.y);
			break;
		case UConstants.BORDER_LAYOUT_BOTTOM:
			g.drawLine(rd.x, rd.y+rd.h, rd.x+rd.w, rd.y+rd.h);
			break;
		case UConstants.BORDER_LAYOUT_LEFT:
			g.drawLine(rd.x, rd.y, rd.x, rd.y+rd.h);
			break;
		case UConstants.BORDER_LAYOUT_RIGHT:
			g.drawLine(rd.x+rd.w, rd.y, rd.x+rd.w, rd.y+rd.h);
			break;
		default:
			g.drawRect(rd.x, rd.y, rd.w, rd.h);			
		}
	}
	public void drawBox(Graphics g, UMatrix m){
		URect rs = new URect(x,y,w,h);
		URect rd = m.logicToView(rs); 
		g.drawRect(rd.x, rd.y, rd.w, rd.h);			
	}

	public Object getData() {
		// TODO Auto-generated method stub
		return text;
	}


	public int getHeight() {
		// TODO Auto-generated method stub
		return w;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return h;
	}


	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}


	public void init() {
		// TODO Auto-generated method stub

	}




	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}


	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		this.border = border;
	}

	public void setShellBounds(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}


	public void setData(Object obj) {
		if(obj == null)
			text = "";
		else
			text = obj.toString();
	}


	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		if(agr0 == null)
			this.font = UFactory.getModelSession().getDefaultFont();
		else
			this.font = agr0;
	}


	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub
		horizontalAlignment = arg0;
	}



	public void setTemplate(Object Template) {
		// TODO Auto-generated method stub

	}

	public void setText(String arg0) {
		if(arg0 == null)
			text = "";
		else
			text = arg0;
	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub
		verticalAlignment = arg0;
	}


	public void setColor(UColor c) {
		if(c == null)
			color = UFactory.getModelSession().getDefaultColor();
		color = c;
	}
	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		
	}
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		
	}
	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub
		
	}
	public void setMaxLength(int l) {
		// TODO Auto-generated method stub
		
	}
	public void setParameterData(HashMap map, UFormI form, UComponentI father) {
		// TODO Auto-generated method stub
		text = UTextUtil.replaceString(text, map, form, father);
	}
	public int getSelectStatus(UPoint p){
		return 0;
	}
	public URect getBox() {
		// TODO Auto-generated method stub
		return new URect(x,y,w,h);
	}
	public void shift(int dx, int dy) {
		// TODO Auto-generated method stub
		x += dx;
		y += dy;
	}
	public URect getBoundRect(){
		return new URect(x,y,w,h);
		
	}
	public void setArrangeType(int type) {
		this.arrangeType = type;
	}
	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub
		
	}
	public void setTemplate(UTemplate Template) {
		// TODO Auto-generated method stub
		
	}
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}


	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		
	}


	public void setAddedDatas(Object[] o) {
		// TODO Auto-generated method stub
		
	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub
		
	}
	public void setComponentName(String name){
		this.componentName = name;
	}
	public String getComponentName(){
		return componentName;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		
	}
	
	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		
	}

	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	public void initContents() {
		// TODO Auto-generated method stub
		
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setInitComponentData(Object data){
		
	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}
	public void onClose(){
		
	}
	public void repaintComponent() {
	}
	public void setParameters(HashMap paras){
		
	}
	public HashMap getParameters(){
		return null;
	}
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}

	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setdisplayText(String text) {
		// TODO Auto-generated method stub
		
	}
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setActionComandString(String str) {
		// TODO Auto-generated method stub
		
	}

}
