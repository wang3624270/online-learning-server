package cn.edu.sdu.uims.component.combobox;

public class UComboBoxValue extends UComboBox {
	public Object getData() {
		return getSelectedItem();
	}

	public void setData(Object obj) {
		if (obj != null)
			this.setText(obj.toString());
	}

}
