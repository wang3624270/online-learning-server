package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.reportdog.UTemplate;


public class UPageElementTemplate extends UBaseTemplate{

	protected int typeInt= 0;
	protected boolean isComponent = false;
	protected String templateName = null;
	protected UTemplate outDefineTemplate = null;
	protected String text;
	protected String dataFormMember = null;
	protected String mapKey;
	protected String property = "" ;
	protected String cssNode = "" ;
	protected String label = "";
	protected String colSpan = "1";
	protected String validate ="";
	protected String name = "";
	protected String dataSource;//comboBox的数据源
	protected List<UBEvent> bevent;
	protected String type;
	protected List<UPageEventTemplate> eventList = null;
	protected List<ListOptionInfo> options;
	protected String option;
	protected String space;
	protected String id;
	protected UPageTemplate pt;//保存对页面模板的一个引用
	protected String rowSpan;
	protected String cLabelColSpan;//控件标签所占单元格数
	protected int labelOrientation;//标签相对控件的位置
	protected boolean me;//提交表单时为必填项
	protected String note;//对控件的注解
	protected String src;//源连接
	protected String beforeArea;
	protected String afterArea;
	protected String beforeSpace;
	protected String afterSpace;
	protected String link;
	protected String size;
	protected String labelWidth;
	public String getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getBeforeSpace() {
		return beforeSpace;
	}
	public void setBeforeSpace(String beforeSpace) {
		this.beforeSpace = beforeSpace;
	}
	public String getAfterSpace() {
		return afterSpace;
	}
	public void setAfterSpace(String afterSpace) {
		this.afterSpace = afterSpace;
	}
	public String getBeforeArea() {
		return beforeArea;
	}
	public void setBeforeArea(String beforeArea) {
		this.beforeArea = beforeArea;
	}
	public String getAfterArea() {
		return afterArea;
	}
	public void setAfterArea(String afterArea) {
		this.afterArea = afterArea;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isMe() {
		return me;
	}
	public void setMe(boolean me) {
		this.me = me;
	}
	public int getLabelOrientation() {
		return labelOrientation;
	}
	public void setLabelOrientation(int labelOrientation) {
		this.labelOrientation = labelOrientation;
	}
	public String getCLabelColSpan() {
		return cLabelColSpan;
	}
	public void setCLabelColSpan(String labelColSpan) {
		cLabelColSpan = labelColSpan;
	}
	public String getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(String rowSpan) {
		this.rowSpan = rowSpan;
	}
	public UPageTemplate getPt() {
		return pt;
	}
	public void setPt(UPageTemplate pt) {
		this.pt = pt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public List<ListOptionInfo> getOptions() {
		return options;
	}
	public void setOptions(List<ListOptionInfo> options) {
		this.options = options;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public List<UPageEventTemplate> getEventList() {
		return eventList;
	}
	public void setEventList(List<UPageEventTemplate> eventList) {
		this.eventList = eventList;
	}
	public String getType() {
		return type;
	}
	public int getTypeInt() {
		return typeInt;
	}
	public void setTypeInt(int typeInt) {
		this.typeInt = typeInt;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UPageElementTemplate(){
		this.bevent = new ArrayList<UBEvent>();
		this.eventList = new ArrayList<UPageEventTemplate>();
		this.options = new ArrayList<ListOptionInfo>();
	}
	public void addPageEventTemplateConfig(UPageEventTemplate event){
		this.eventList.add(event);
	}
	
	/**将配置的选项信息加入*/
	public void addOptions(String values){
		ListOptionInfo info =null;
		if(values!=null){
			String []split = values.split("/");
			if(split.length == 1){
				info = new ListOptionInfo("",split[0]);
			}
			else{
				info = new ListOptionInfo(split[1],split[0]);
			}
			this.options.add(info);
		}		
	}
	
	public void addComponentLabel(UPageElementTemplate element){
		String type = element.getType();
		if(type == null || "".equals(type) || !"label".equals(type))
			return;
		this.text = element.getText();
		this.cLabelColSpan = element.getColSpan();
		this.labelWidth = element.getWidth();
	}
	public void addPageTemplate(UPageTemplate pt){
		this.pt = pt;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getCssNode() {
		return cssNode;
	}
	public void setCssNode(String cssNode) {
		this.cssNode = cssNode;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getColSpan() {
		return colSpan;
	}
	public void setColSpan(String colSpan) {
		this.colSpan = colSpan;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public boolean isComponent() {
		return isComponent;
	}
	public void setComponent(boolean isComponent) {
		this.isComponent = isComponent;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDataFormMember() {
		return dataFormMember;
	}
	public void setDataFormMember(String dataFormMember) {
		this.dataFormMember = dataFormMember;
	}
	public String getMapKey() {
		return mapKey;
	}
	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}
	public List<UBEvent> getBevent() {
		return bevent;
	}
	public void setBevent(List<UBEvent> bevent) {
		this.bevent = bevent;
	}
}
