package cn.edu.sdu.uims.def.dataimport;

import java.util.Date;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.def.BaseTemplate;
import cn.edu.sdu.uims.util.UimsUtils;

public class ItemTemplate extends BaseTemplate {
	private String type;
	private String label;
	private String source;
	private String target;
	private String init;
	private String map;
	private String dataMap;
	private String dataValue;
	private String format;
	private Class valueClassType = Object.class;
	protected Integer index;
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Class getValueClassType() {
		return valueClassType;
	}
	public void setValueClassType(Class valueClassType) {
		this.valueClassType = valueClassType;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getInit() {
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getDataMap() {
		return dataMap;
	}
	public void setDataMap(String dataMap) {
		this.dataMap = dataMap;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String objectToString(Object o) {
		return UimsUtils.objectToString(o, format);
	}
	public Object StringToObject(String str) {
		if (str == null || str.equals(""))
			return null;
		if (valueClassType == Boolean.class) {
			return new Boolean(true);
		} else if (valueClassType == ListOptionInfo.class) {
			return new ListOptionInfo(str, str);
		} else if (valueClassType == Date.class) {
			if (format == null)
				format = "yyyy-MM-dd";
			return DateTimeTool.formatDateTime(str, format.substring(0,str.length()));
		} else if (valueClassType == Double.class)
			return new Double(str);
		else if (valueClassType == Float.class)
			return new Float(str);
		else if (valueClassType == Integer.class)
			return new Integer(str);
		else if (valueClassType == Long.class)
			return new Long(str);
		return str;
	}

}
