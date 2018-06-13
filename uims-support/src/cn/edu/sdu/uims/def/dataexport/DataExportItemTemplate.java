package cn.edu.sdu.uims.def.dataexport;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.BaseTemplate;

public class DataExportItemTemplate extends BaseTemplate {
	private String anonym;
	private String label;
	private int width;
	private String dict;
	private String changer;
	private String computer;
	private DataExportItemProcessorI changePi;
	private DataExportItemComputerI computePi;
	
	
	public String getComputer() {
		return computer;
	}
	public void setComputer(String computer) {
		this.computer = computer;
	}
	public DataExportItemComputerI getComputePi() {
		return computePi;
	}
	public void setComputePi(DataExportItemComputerI computePi) {
		this.computePi = computePi;
	}
	public String getChanger() {
		return changer;
	}
	public void setChanger(String changer) {
		this.changer = changer;
	}
	public DataExportItemProcessorI getChangePi() {
		return changePi;
	}
	public void setChangePi(DataExportItemProcessorI changePi) {
		this.changePi = changePi;
	}
	public String getDict() {
		return dict;
	}
	public void setDict(String dict) {
		this.dict = dict;
	}
	public String getAnonym() {
		return anonym;
	}
	public void setAnonym(String anonym) {
		this.anonym = anonym;
	}
	public int getWidth() {
		return width;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void getAttribute(Element e) {
		super.getAttribute(e);
		anonym= e.attributeValue("anonym");
		label = e.attributeValue("label");
		dict = e.attributeValue("dict");
		changer = e.attributeValue("changer");
		computer = e.attributeValue("computer");
		String str;
		str = e.attributeValue("width");
		if(str == null)
			width = 8;
		else
			width = Integer.parseInt(str);
	}
}
