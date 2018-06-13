package cn.edu.sdu.uims.component.combobox;

import cn.edu.sdu.uims.util.UimsUtils;

public class UComboBoxType extends UComboBox {
	
	public Object getData() {
		if(elementTemplate.multiple)
			return super.getData();
		else
			return UimsUtils.getComboBoxSelectedValue(this);
	}

	public void setData(Object obj) {
		if(elementTemplate.multiple) 
			 super.setData(obj);
		else {
			if(obj != null)
				UimsUtils.setItemSelectedInComboBox(obj,this);
			else {
				if(this.getItemCount() > 0)
					if(elementTemplate.setFirstItem)
						this.setSelectedIndex(0);
					else
						this.setSelectedIndex(-1);
				else
					UimsUtils.setItemSelectedInComboBox("",this);
			}
		}
	}
}
