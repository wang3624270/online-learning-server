package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.constant.UConstants;

public class UActionBarTemplate extends UTemplate{
	public int layout = UConstants.ALIGNMENT_BOTTOM;
	public int rwoArrange = UConstants.ROW_ARRANGE_CLOSE;
	public UElementTemplate []labels = null;
	public UTextFieldTemplate []fields = null;
	public USpinnerTemplate []spinners = null;
	public UElementTemplate []comboBoxs = null;
	public UComboBoxDateTemplate []comboBoxDates = null;
	public String startEndClassName;
	public UButtonTemplate []buttons = null;
	public int rowNum = 1;

	
	public boolean deleteRowButton = false;
	public boolean addNewRowButton = false;
	public boolean clearButton = false;
	public boolean okButton = false;
	public boolean cancelButton = false;
	public String visibleNames = null;
	public int width = 0,height = 0;
	public UQueryDataFieldTemplate queryDataField = null;

	public int getRwoArrange() {
		return rwoArrange;
	}
	public void setRwoArrange(int rwoArrange) {
		this.rwoArrange = rwoArrange;
	}
	public UElementTemplate[] getLabels() {
		return labels;
	}
	public void setLabels(UElementTemplate[] labels) {
		this.labels = labels;
	}
	public String getStartEndClassName() {
		return startEndClassName;
	}
	public void setStartEndClassName(String startEndClassName) {
		this.startEndClassName = startEndClassName;
	}
	public UButtonTemplate[] getButtons() {
		return buttons;
	}
	public void setButtons(UButtonTemplate[] buttons) {
		this.buttons = buttons;
	}
	public void copy(UTemplate temp){
		UActionBarTemplate ds = (UActionBarTemplate)temp;
		this.layout = ds.layout;
		this.width = ds.width;
		this.height = ds.height;
		this.rwoArrange = ds.rwoArrange; 
		this.startEndClassName = ds.startEndClassName;
		this.labels = ds.labels;
		this.fields = ds.fields;
		this.spinners = ds.spinners;
		this.comboBoxs = ds.comboBoxs;
		this.comboBoxDates = ds.comboBoxDates;
		this.visibleNames = ds.visibleNames;
		this.addNewRowButton = ds.addNewRowButton;
		this.deleteRowButton = ds.deleteRowButton;
		this.clearButton = ds.clearButton;
		this.okButton = ds.okButton;
		this.cancelButton = ds.cancelButton;
		this.queryDataField = ds.queryDataField;
		this.rowNum = ds.rowNum;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		rwoArrange = in.readInt();
		int len, i;
		len = in.readInt();
		if(len == 0)
			labels = null;
		else {
			labels = new UElementTemplate[len];
			for(i = 0; i < len;i++)
				labels[i] = (UElementTemplate)readTemplate(in);
		}
		startEndClassName = readString(in);
		len = in.readInt();
		if(len == 0)
			buttons = null;
		else {
			buttons = new UButtonTemplate[len];
			for(i = 0; i < len;i++)
				buttons[i] = (UButtonTemplate)readTemplate(in);
		}
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(rwoArrange);
		int len, i;
		if(labels == null)
			out.writeInt(0);
		else {
			out.writeInt(labels.length);
			for(i = 0; i < labels.length;i++)
				writeTemplate(out, labels[i]);
		}
		writeString(out, startEndClassName);
		if(buttons == null)
			out.writeInt(0);
		else {
			out.writeInt(buttons.length);
			for(i = 0; i < buttons.length;i++)
				writeTemplate(out, buttons[i]);
		}
	}
	public void  setVisibleString(){
		if(visibleNames != null && visibleNames.length() != 0)
			return;
		visibleNames = "";
		if (queryDataField != null)
			visibleNames +="queryDataField/";
		if (addNewRowButton)
			visibleNames +="addNewRowButton/";
		if (deleteRowButton)
			visibleNames +="deleteRowButton/";
		if (clearButton)
			visibleNames +="clearButton/";
		if (okButton)
			visibleNames +="okButton/";
		if (cancelButton)
			visibleNames +="cancelButton/";
		if (startEndClassName != null) {
			visibleNames +="cancelButton/";
		}
		int i;
		if (labels != null) {
			for(i = 0; i < labels.length;i++) 
				visibleNames += labels[i].name + "/";
		}
		if (fields != null) {
			for(i = 0; i < fields.length;i++) 
				visibleNames += fields[i].name + "/";
		}
		if (spinners != null) {
			for(i = 0; i < spinners.length;i++) 
				visibleNames += spinners[i].name + "/";
		}
		if (comboBoxs != null) {
			for(i = 0; i < comboBoxs.length;i++) 
				visibleNames += comboBoxs[i].name + "/";
		}
		if (comboBoxDates != null) {
			for(i = 0; i < comboBoxDates.length;i++) 
				visibleNames += comboBoxDates[i].name + "/";
		}
		if (buttons != null) {
			for(i = 0; i < buttons.length;i++) 
				visibleNames += buttons[i].name + "/";
		}
		if(visibleNames.length() > 1)
			visibleNames = visibleNames.substring(0,visibleNames.length());
	}
}
