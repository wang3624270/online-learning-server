package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.List;

import cn.edu.sdu.uims.base.UPageActionComponentI;
import cn.edu.sdu.uims.component.UPageActionPanel;
import cn.edu.sdu.uims.component.complex.def.UPageImageViewTemplate;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.SelectObjectEvent;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.form.impl.UTableQueryDataForm;
import cn.edu.sdu.uims.handler.TablePageDataQueryHandlerI;

public class UPageImageViewPanel extends UComplexPanel implements UPageActionComponentI {

	private UImageCanvas imageCanvas;
	private UPageActionPanel pageActionPanel;
	private UPopupMenu popupMenu = null;
	private boolean enablePopupMenu = true;
	private boolean sendSelectObjectEvent= false;
	private List dataList = null;
	@Override
	public void initContents() {		
		super.initContents();
		this.setLayout(new BorderLayout());
		UPageImageViewTemplate pp = (UPageImageViewTemplate)this.elementTemplate;
		if(pp.isPages) {
			pageActionPanel = new UPageActionPanel(this);
			this.add(pageActionPanel.getJPanel(),BorderLayout.SOUTH);
		}
		imageCanvas = new UImageCanvas(this);
		this.add(imageCanvas,BorderLayout.CENTER);
	}
	@Override
	public void doTablePageDataQuery(UTableQueryDataForm form) {
		// TODO Auto-generated method stub		
		TablePageDataQueryHandlerI handler = (TablePageDataQueryHandlerI) getUParent()
				.getHandler();
		if (handler == null)
			return;
		if(this.pageActionPanel != null)
			return;
		handler.getTablePageData(this.pageActionPanel.getTableQueryDataForm());
	}
	@Override
	public void setTableQueryDataForm(UTableQueryDataForm form) {
		// TODO Auto-generated method stub
		if(this.pageActionPanel != null)
			pageActionPanel.setTableQueryDataForm(form);
	}

	@Override
	public UTableQueryDataForm getTableQueryDataForm() {
		// TODO Auto-generated method stub
		if(pageActionPanel != null)
			return pageActionPanel.getTableQueryDataForm();
		else
			return null;
	}
	@Override
	public void displayPageData() {
		// TODO Auto-generated method stub
		if(pageActionPanel == null)
			imageCanvas.setDrawData(dataList);
		else	
			imageCanvas.setDrawData(this.pageActionPanel.getPageDataList());
	}
	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		popupMenu = new UPopupMenu();
		int n = menu.getItemCount();
		while (menu.getItemCount() > 0) {
			popupMenu.add(menu.getItem(0));
		}
	}

	public UPopupMenu getUPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(UPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void displyPopMenu(Component com, int x, int y) {
		if (popupMenu != null && popupMenu.getSubElements().length >= 1)
			popupMenu.show(com, x, y);
	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_SELECTOBJECT)) {
				sendSelectObjectEvent = true;
			}
		}
	}
	public void dispPopMenu(MouseEvent mouseEvent) {
		if(enablePopupMenu) {
			displyPopMenu((Component) mouseEvent.getSource(), mouseEvent.getX(), mouseEvent.getY());
		}
	}

	public void doSelectObject( Object obj){
		if(sendSelectObjectEvent) {
			SelectObjectEvent e =new SelectObjectEvent(this, obj);
			this.getUParent().getEventAdaptor().selectObjectSelection(e);
		}				
	}
	public ImageCanvasProcessI getImageCanvasProcessor(){
		return this.imageCanvas;
	}
	
	public Object getData() {
		if(pageActionPanel == null)
			return dataList;
		else
			return null; 
	}

	public void setData(Object obj) {
		if(pageActionPanel == null)
			dataList = (List)obj;
	}
	
}
