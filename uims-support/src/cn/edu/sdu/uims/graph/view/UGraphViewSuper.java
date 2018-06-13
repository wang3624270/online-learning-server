package cn.edu.sdu.uims.graph.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.SelectObjectEvent;
import cn.edu.sdu.uims.component.event.UScrollPanelEventAdaptor;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.graph.form.GraphPrintForm;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.URect;

public class UGraphViewSuper extends JPanel implements UComponentI {
	/**
	 * 
	 */
	protected String componentName;
	protected boolean bCanSendHcimsEvent = false;
	protected boolean canSelectObject = false;

	protected UViewport view = null;
	protected UMatrix mt;
	protected UPanelI parentI;
	protected ControlParameter controlParameter = new ControlParameter();
	protected UScrollPanelEventAdaptor eventProcessor = null;
	protected UElementTemplate elementTemplate;
	protected UScrollPane scrollPane;
	protected ViewPopMenuTesterI viewPopMenuTester;
	

	public boolean getCanSelectObject() {
		return canSelectObject;
	}

	public void setCanSelectObject(boolean canSelectObject) {
		this.canSelectObject = canSelectObject;
	}

	public UGraphViewSuper() {
		initView();
	}
	protected Component getViewshell() {
		scrollPane.setViewportView(view);
		return scrollPane;
	}
	public void initView() {
		controlParameter = new ControlParameter();
		controlParameter.isEdit = true;
		scrollPane = new UScrollPane();
		view = new UViewport(this);
		view.initContent();
		mt = new UMatrix();
		setLayout(new BorderLayout());
		add(getViewshell(), BorderLayout.CENTER);
		setPreferredSize(new Dimension(1, 1));
	}

	public void addInfoPanel(){
		
	}
	public void init() {

	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public String getComponentName() {
		// TODO Auto-generated method stub
		return componentName;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}


	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parentI;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub

	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub

	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		this.componentName = name;
	}

	public void setData(Object obj) {
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

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parentI = parent;
	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		view.setPopupMenu(menu);
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void setInitComponentData(Object data) {

	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void initContents() {
		addInfoPanel();
		resetWindowViewPort();
	}

	public GraphModelI getCurrentGraph() {
		return null;
	}

	public ViewParameter getViewParameter() {

		return new ViewParameter(getWVTrans(),getWVTrans().m, mt);
	}

	public void startPrint() {
		GraphModelI g2d = getCurrentGraph();
		if (g2d == null)
			return;
		GraphPrintForm form = new GraphPrintForm();
		form.setCurrentGraphObject(g2d);
		form.makeViewParameter();
		// form.setInputParameterMap(getInputParameterMap());
		UPaperTemplate paperTemplate = g2d.getPaperTemplate();
		if (paperTemplate == null)
			paperTemplate = new UPaperTemplate();
		UViewPrinter pt = new UViewPrinter(paperTemplate);
		pt.invokePrinterJob(form);
	}

	public ControlParameter getControlParameter() {
		return controlParameter;
	}

	public void setControlParameter(ControlParameter controlParameter) {
		this.controlParameter = controlParameter;
	}

	public void addEvents(UEventAttribute[] events) {
		int i;
		eventProcessor = new UScrollPanelEventAdaptor(this, getUParent()
				.getEventAdaptor());
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_SELECTOBJECT)) {
				canSelectObject = true;
			}
		}
	}

	public void setShellBounds(int x, int y, int w, int h) {
	}

	public void processSelectObjectEvent(MouseEvent e, UFPoint fp) {
		if(getCurrentGraph() == null)
			return;
		if(!canSelectObject)
			return;
		List list = getCurrentGraph().getSelectElementByPoint(fp);
		if(list == null || list.size() == 0) {
			return;
		}
		SelectObjectEvent event = new SelectObjectEvent(this,list);
		if(getUParent()!= null && getUParent().getEventAdaptor() != null)
			getUParent().getEventAdaptor().processEvent(event,EventConstants.EVENT_SELECTOBJECT , "select");
	}
	
	public void processHcimsEvent(MouseEvent e, String evtType, String evtCmd) {
	}

	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}
	
	public void resetWindowViewPort() {
		int w, h;
		if (getCurrentGraph() == null) {
			w = 1;
			h = 1;
		} else {
			w = (int) (getCurrentGraph().getImageWidth());
			h = (int) (getCurrentGraph().getImageHeight());
		}
		Dimension dimension = new Dimension(w, h);
		view.setMinimumSize(dimension);
		view.setMaximumSize(dimension);
		view.setPreferredSize(dimension);
		getWVTrans();
		view.repaint();
		scrollPane.updateUI();
	}
	public void repaintGraph(){
		view.repaint();
	
	}
	public WVTrans getWVTrans() {
		WVTrans wv;
		GraphModelI g2d = getCurrentGraph();
		if (g2d != null) {
			wv = g2d.getWindowViewport();
		} else {
			wv = new WVTrans();
			wv = new WVTrans();
			mt = new UMatrix();
			wv.setWindows(0, 0, 421, 298);
			wv.setViewport(0, 0, 1001, 709);
			wv.makeWVMatrix();
		}
		return wv;

	}
	public void resetViewport(){
		resetWindowViewPort();
	}
	public void scaleGraph(boolean isEnlarge) {
		GraphModelI g2d = getCurrentGraph();
		if (g2d == null)
			return;
		double xy = g2d.getScaleXY();
		if (isEnlarge) {
			xy *= 2.0;
		} else {
			xy /= 2.0;
		}
		g2d.setScaleXY(xy);
		resetViewport();
	}

	public UCanvasI getCanvas() {
		return view;
	}

	public void onClose() {
		if (view != null)
			view.removeKeylisterFromFrame();
	}

	public void repaintComponent() {
		// TODO Auto-generated method stub
		if (view != null)
			view.repaint();
	}

	public void setParameters(HashMap paras) {

	}

	public HashMap getParameters() {
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

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		GraphModelI g2d = getCurrentGraph();
		if (g2d == null || view == null)
			return new URect(0, 0, 400, 400);
		float imgDpi, graphDpi;
//		imgDpi = g2d.getImageDpi();
//		graphDpi = g2d.getGraphDpi();
		int w = (int) (g2d.getImageWidth());
		int h = (int) (g2d.getImageHeight());
		return new URect(0, 0, w, h);
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

	public void setCurrentGraph(GraphModelI graphModelI) {
	}
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setActionComandString(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserData(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isGraphShiftAction() {
		return false;
	}
	public void reDraw(){
		if(view != null)
			view.repaint();
	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
		
	}

	public ViewPopMenuTesterI getViewPopMenuTester() {
		return viewPopMenuTester;
	}

	public void setViewPopMenuTester(ViewPopMenuTesterI viewPopMenuTester) {
		this.viewPopMenuTester = viewPopMenuTester;
	}


	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}

	
}
