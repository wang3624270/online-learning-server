package cn.edu.sdu.uims.form.impl;

public class UDataArrayForm extends UMForm {
	private Object data[];

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}
	public Object getDataElement(Integer index) {
		if(index == null || data== null || index < 0 || index >= data.length)
			return null;
		return data[index];
	}

	public void setDataElement(Integer index, Object obj) {
		if(index == null || data == null || index < 0 || index >= data.length)
			this.data[index] = obj;
	}
}
