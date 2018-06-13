package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.base.CheckObject;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.checkbox.UCheckBoxValue;
import cn.edu.sdu.uims.component.complex.def.UGraphEditTemplate;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.GraphInteractionEvent;
import cn.edu.sdu.uims.component.event.GraphInteractionListener;
import cn.edu.sdu.uims.component.event.UEventAdaptor;
import cn.edu.sdu.uims.component.list.UListCheck;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.panel.UFPanel;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UGraphButtonBarTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.graph.form.GraphEditFormI;
import cn.edu.sdu.uims.graph.handler.GraphInteractionHandler;
import cn.edu.sdu.uims.graph.interaction.UCheckBoxGroupLayer;
import cn.edu.sdu.uims.graph.interaction.UGraphButtonBar;
import cn.edu.sdu.uims.graph.model.Graph2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.util.XmlGraphMoelUtils;
import cn.edu.sdu.uims.graph.view.UGraphView;
import cn.edu.sdu.uims.graph.view.ViewPopMenuTesterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.hcims.UserTask;
import cn.edu.sdu.uims.hcims.def.UserTaskTemplate;
import cn.edu.sdu.uims.itms.service.ItmsTaskService;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;
import pagelayout.Cell;
import pagelayout.Column;
import pagelayout.ComponentCell;


public class UGraphEditPanel extends UComplexPanel {
	protected UGraphEditTemplate editTemplate;
	protected GraphEditFormI graphForm = null;
	private UFPanel infoPanel;
	private JScrollPane scrollPane, checkScrollPane;
	private UGraphView graphView;
	private UGraphButtonBar  graphToolBar;
	private UGraphButtonBar commandButtonBar;
	private JPanel attributePanel;	
	
	private JPanel dispControlPanel;
	private UCheckBoxGroupLayer checkBoxGroupLayer;
	private UListCheck layerList;
	
	private JComboBox pageComboBox; 
	private JComboBox layerComboBox; 
	private JComboBox graphNameComboBox; 
	private JComboBox fontComboBox; 
	private JComboBox colorComboBox; 
	private JComboBox penComboBox; 

	private JCheckBox boxDisplyCheckBox;
	private JCheckBox dataDisplayCheckBox;

	
	
	private String graphToolAction = null;
	
	private boolean isNeedSendAvtionEvent = false;
	private GraphLayerVisibleProcessor graphLayerVisibleProcessor= new GraphLayerVisibleProcessor();
	
	
	private HashMap userTaskMap = null;
	private UserTask userTask = null;
	
	private GraphInteractionTaskProcessor graphInteractionTaskProcessor = new GraphInteractionTaskProcessor();
		
	private class GraphInteractionTaskProcessor extends UEventAdaptor implements
		GraphInteractionListener {

		@Override
		public void commandSelected(GraphInteractionEvent e) {
			// TODO Auto-generated method stub
			initUserTask(e.getUserTaskName(), e.getMethodName());
			
		}

		@Override
		public void itemStatusChanged(GraphInteractionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void checkSatatusSelected(GraphInteractionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	public void initInteraction() {
		if (editTemplate == null || editTemplate.userTaskGroupName == null)
			return;
		userTaskMap = (HashMap) UFactory.getModelSession().getUserTaskMap(editTemplate.userTaskGroupName);
		if(editTemplate.defaultUserTaskName == null || editTemplate.defaultMethodName == null)
			return ;
		initUserTask(editTemplate.defaultUserTaskName,editTemplate.defaultMethodName);
	}
	
	public void initUserTask(String userTaskName, String methodName) {
		if (userTaskMap == null)
			return;
		UserTaskTemplate tmp = (UserTaskTemplate) userTaskMap.get(userTaskName);
		if (tmp == null)
			return;
		userTask = UserTask.getInstance();
		userTask.setCanvas(graphView.getCanvas());
		userTask.setTemplate(tmp);

		// 下面根据template找到交互Task
		userTask.setHciTask(ItmsTaskService.getInstance().getTaskByName(
				tmp.hciTaskName));
		ItmsTaskService.getInstance().setCurrentTaskName(tmp.hciTaskName);
		userTask.setCurrentTaskRepeat(tmp.isRepeat);
		userTask.setMethodName(methodName);
		userTask.setFieldForm(graphForm);
		userTask.setCurrentHandler((GraphInteractionHandler) this.getUParent().getHandler());
		userTask.doUserTask();
	}

	
	public UserTask getUserTask() {
		return userTask;
	}

	public void setUserTask(UserTask userTask) {
		this.userTask = userTask;
	}
	
	
	private List geGraphNameList(){
		GraphModelI g = this.getCurrentGraph();
		if(g == null)
			return null;
		HashMap<String, Graph2D>map  = null;
		if(map == null)
			return null;
		List  list = new ArrayList();
		Iterator ie = map.keySet().iterator();
		while(ie.hasNext()) {
			list.add((String)ie.next());
		}
		return list;
	}

	private void setComboBoxOption(JComboBox comboBox, List list){
		comboBox.removeAllItems();
		if(list == null || list.size() == 0)
			return;
		for(int i = 0; i < list.size();i++) {
			comboBox.addItem(list.get(i));
		}
	}
	private void initInfoPanel(){
		if(parameters == null)
			return;
		String name = (String)parameters.get("infoPanelName");
		if(name == null)
			return;
		UPanelTemplate template = (UPanelTemplate)UFactory.getModelSession().getTemplate(UConstants.MAPKEY_PANEL_FORM, name);
		infoPanel = (UFPanel) template.newComponent();
		infoPanel.setTemplate(template);
		infoPanel.init();
		infoPanel.getAWTComponent().setPreferredSize(new Dimension(1024, 30));
	}
	private void initDispControlPanel(){
		dispControlPanel = new JPanel();
		dispControlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,5,0));
		dataDisplayCheckBox = new JCheckBox("数据");
		dispControlPanel.add(dataDisplayCheckBox);
		boxDisplyCheckBox = new JCheckBox("边界");
		dispControlPanel.add(boxDisplyCheckBox);
	}
	private void initCommandButtonBar(){
		if(parameters == null)
			return;
		String name = (String)parameters.get("commandBarName");
		if(name == null)
			return;
		UGraphButtonBarTemplate template = (UGraphButtonBarTemplate)UFactory.getModelSession().getTemplate(UConstants.MAPKEY_GRAPHBUTTONBAR, name);
		commandButtonBar = (UGraphButtonBar) template.newComponent();
		commandButtonBar.setTemplate(template);
		commandButtonBar.init();
		commandButtonBar.addActionListener(this);
		add(commandButtonBar,BorderLayout.SOUTH);	
	}
	private void setValuetoComboBoxList(String value, JComboBox com){
		if(value == null ||value.length() == 0)
			return;
		StringTokenizer sz = new StringTokenizer(value, "|");
		while(sz.hasMoreTokens()){
			com.addItem(sz.nextToken());
		}
		com.setSelectedIndex(0);
	}
	private void initGraphLayerPanel(){
		checkBoxGroupLayer = new UCheckBoxGroupLayer();
		checkBoxGroupLayer.setActionListener(graphLayerVisibleProcessor);
		checkBoxGroupLayer.initGraphContent();
		Dimension d = new Dimension(120,600);
		checkBoxGroupLayer.setSize(d);
		checkScrollPane = new JScrollPane(checkBoxGroupLayer.getAWTComponent());
		checkScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		checkScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		checkScrollPane.setPreferredSize(d);
		checkScrollPane.setSize(d);
	}
	
	private void initGraphLayerList(){
		layerList = new UListCheck();
		layerList.setListSelectionListener(graphLayerVisibleProcessor);
		layerList.initContents();
		Dimension d = new Dimension(80,200);
		layerList.setPreferredSize(d);
		layerList.setSize(d);
	}
	private void initAttributeBar(){
		if(parameters == null)
			return;
		attributePanel = null;
		String value;
		boolean isNeed = false;
		Column column = new Column();
		value = (String)parameters.get("graphPage");
		if(value != null) {	
			isNeed = true;
			pageComboBox = new JComboBox();
			setValuetoComboBoxList(value, pageComboBox);
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(pageComboBox));	
		}
		value = (String)parameters.get("graphLayer");
		if(value != null && value.equals("true")) {	
			isNeed = true;
			initGraphLayerList();
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(layerList));
		}
		value = (String)parameters.get("graphName");
		if(value != null && value.equals("true")) {	
			isNeed = true;
			graphNameComboBox = new JComboBox();
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(graphNameComboBox));	
		}
		value = (String)parameters.get("graphFont");
		if(value != null) {	
			isNeed = true;
			fontComboBox = new JComboBox();
			setValuetoComboBoxList(value, fontComboBox);
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(fontComboBox));	
		}
		value = (String)parameters.get("graphColor");
		if(value != null) {	
			isNeed = true;
			colorComboBox = new JComboBox();
			setValuetoComboBoxList(value, colorComboBox);
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(colorComboBox));	
		}
		value = (String)parameters.get("graphPen");
		if(value != null) {	
			isNeed = true;
			penComboBox = new JComboBox();
			setValuetoComboBoxList(value, penComboBox);
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(penComboBox));	
		}
		if(isNeed){
			attributePanel = new JPanel();
			column.createLayout(attributePanel);
		}
	}
	private  void initGraphToolBar(){
		if(parameters == null)
			return;
		String name = (String)parameters.get("toolBarName");
		if(name == null)
			return;
		UGraphButtonBarTemplate template = (UGraphButtonBarTemplate)UFactory.getModelSession().getTemplate(UConstants.MAPKEY_GRAPHBUTTONBAR, name);
		graphToolBar = (UGraphButtonBar) template.newComponent();
		graphToolBar.setTemplate(template);
		graphToolBar.init();
		graphToolBar.setGraphInteractionListener(graphInteractionTaskProcessor);
		graphToolBar.addActionListener(this);
	}
	private void initTopBar(){
		if(infoPanel ==null || dispControlPanel == null) 
			return;
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		if(infoPanel != null)
			p.add(infoPanel.getAWTComponent(),BorderLayout.WEST);
		if(dispControlPanel != null)
			p.add(dispControlPanel,BorderLayout.EAST);
		p.setPreferredSize(new Dimension(1024, 30));
		add(p, BorderLayout.NORTH);
	}
	private void initRightBar(){
		if(attributePanel == null && graphToolBar == null)
			return ;
		JPanel p = new JPanel();
		Column column = new Column();
		if(attributePanel != null)
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(attributePanel));	
		if(graphToolBar != null)
			column.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,new ComponentCell(graphToolBar));	
		column.createLayout(p);
		add(p,BorderLayout.EAST);

	}
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		initInfoPanel();
		initDispControlPanel();
		initGraphToolBar();
		initAttributeBar();
		initTopBar();
		initRightBar();
		initCommandButtonBar();
		UPanelI pp = getUParent();
		UHandlerI h= pp.getHandler();
		graphView = new UGraphView();
		graphView.initContents();
		graphView.setUParent(pp);
		if(h instanceof ViewPopMenuTesterI) 
		graphView.setViewPopMenuTester((ViewPopMenuTesterI)h);
		scrollPane =new JScrollPane(graphView);
		add(scrollPane,BorderLayout.CENTER);
		initInteraction();
	}

	
	public GraphModelI getCurrentGraph(){
		if(graphForm == null)
			return null;
		else
			return (GraphModelI)graphForm.getCurrentGraphObject();
	}

	@Override
	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
		this.editTemplate = (UGraphEditTemplate)elementTemplate;
	}
	@Override
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				isNeedSendAvtionEvent = true;
			}
		}
	}
	@Override
	public void setData(Object obj){
		graphForm = (GraphEditFormI)obj;
		if(graphForm != null)
			graphForm.setModify(false);
		updateInfoPanel();
		updateGraphView();
		updateLayerList();
	}
	@Override
	public Object getData(){
		return  graphForm;
	}
	
	private void updateLayerList(){
		if(layerList == null)
			return;
		GraphModelI g = this.getCurrentGraph();
		if(g == null)
			layerList.setData(null);
		else
			layerList.setData(g.getLayersVisibleStatusList());
	}
	
	private String getModelDisplyInfo() {
//		return graphForm.getModelNum() +"-" + graphForm.getModelName() ;
		return graphForm.getGraphInfo();
	}
	private void updateInfoPanel(){
		if(graphForm == null) {
		}else {
//			String type = graphForm.getModelType();
			String type = null;
			if(type == null || type.length() == 0)
				type = UConstants.MODEL_TYPE_GRAPH_PRINT;
			else 
				type = type.substring(2,4);
		}
	}
	private void infoPanelDataToForm(){
		if(graphForm == null)
			return;
	}
	
	public void updateGraphView(){
		if(graphView == null)
			return;
		if(graphForm == null)
			graphView.setData(null);
		else
			graphView.setData(getCurrentGraph());
	}
	public void repaintGraph(){
		if(graphView != null)
			graphView.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(changeGraphToolCommand(cmd)) {
			return;
		}
		if(cmd.equals("clear")) {
			doClear();
		}else if(cmd.equals("create")){
			doCreate();
		}else if(cmd.equals("save")){
			doSave();
		}else if(cmd.equals("load")){
			doLoad();
		}else if(cmd.equals("export")){
			doExport();
		}else if(cmd.equals("graphZoomIn")) {
			zoom(false);
		}else if(cmd.equals("graphZoomOut")) {
			zoom(true);
		}else {
			if(isNeedSendAvtionEvent) {
				if(getUParent() != null && getUParent().getEventAdaptor() != null){
					e.setSource(this);
					getUParent().getEventAdaptor().actionPerformed(e);
				}
			}
		}
	}
	private boolean changeGraphToolCommand(String cmd) {
		if(cmd.equals("graphMove")) {
			graphToolAction = cmd;
			return true;
		}
		return false;
	}
	public void zoom(boolean isEnlarge) {
		if(graphView != null)
			graphView.scaleGraph(isEnlarge);
	}
	public void doClear(){
		if(getCurrentGraph() == null)
			return;
	}
	public void doSave(){
		if(graphForm == null)
			return;
		if(!(graphForm.isModify())) {
			UimsUtils.messageBoxInfo("图形没有修改，不需要保存！");
		}
		RmiRequest request = new RmiRequest();
		request.setCmd("graphModelSaveModelConfigInfo");
		request.add(RmiKeyConstants.KEY_FORM,graphForm);
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() != null) {
			return ;
		}
	}
	
	public void doCreate(){
/*		
		if(graphForm != null && graphForm.isModify()) {
			int ret = UimsUtils.messageBoxChoice("当前编辑的图形没有保存，确实要放弃，新建一个图形！");
			if(ret != CommonMethod.YES_OPTION)
				return;			
		}
		graphForm  = new ModelConfigInfoForm();
		infoPanelDataToForm();
		RmiRequest request = new RmiRequest();
		request.setCmd("graphModelSaveModelConfigInfo");
		request.add(RmiKeyConstants.KEY_FORM,graphForm);
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() != null) {
			return ;
		}
		graphForm.setModelId((Integer)response.get("modelId"));
		graphForm.setModelNum((String)response.get("modelNum"));
		graphForm.setModelName((String)response.get("modelName"));
		this.updateInfoPanel();
*/		
	}
	public void doLoad(){
		if(graphForm == null)
			return;
		File file = GetFile.getOpenFile();
		if(file == null)
			return;
		Object obj = XmlGraphMoelUtils.getGraph2DModelByFile(file);
		if(obj == null) {
			UimsUtils.messageBoxInfo("数据加载错误！");
			return;
		}
		graphForm.setCurrentGraphObject((GraphModelI)obj);
		graphForm.setModify(true);
//		getCurrentGraph().setName(graphForm.getModelName());
		updateGraphView();
	}
	public void doExport(){
		if(graphForm == null || getCurrentGraph()== null)
			return;
		File file = GetFile.getSaveFile();
		if(file == null)
			return;
		XmlGraphMoelUtils.exportGraph2DModelToFile(getCurrentGraph(), file);	
		Object o =new Object();
		o.hashCode();
		
	}
	private class GraphLayerVisibleProcessor implements ActionListener, ListSelectionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			UCheckBoxValue com = (UCheckBoxValue)arg0.getSource();
			String name = com.getText();
			boolean visible = com.isSelected();
			changeGraphLayerVisibleStatus(name,visible);
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			JList jList = (JList)e.getSource();
			Object o = jList.getSelectedValue();
			if(o instanceof CheckObject) {
				CheckObject co = (CheckObject)o;
				ListOptionInfo info = (ListOptionInfo)co.value;
				changeGraphLayerVisibleStatus(info.getValue(), co.bolValue);
			}
		}
		
	}
	public void changeGraphLayerVisibleStatus(String layerName, boolean visible){
		GraphModelI g = this.getCurrentGraph();
		if(g == null) 
			return;
		boolean b = g.getLayerVisible(layerName);
		if(b == visible)
			return;
		g.setLayerVisible(layerName, visible);
		graphView.reDraw();
	}

	@Override
	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		graphView.setPopupMenu(menu);
	}

}
