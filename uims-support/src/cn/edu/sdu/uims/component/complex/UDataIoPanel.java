package cn.edu.sdu.uims.component.complex;

import java.awt.FlowLayout;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.complex.def.UDataIoTemplate;
import cn.edu.sdu.uims.component.label.ULabel;

public class UDataIoPanel extends UComplexPanel {
	protected UDataIoTemplate dataTemplate;
	protected ULabel label;
	protected UComboBox typeComboBox;

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		if (dataTemplate == null)
			return;
		this.setLayout(new FlowLayout());
		label = new ULabel();
		label.setText(dataTemplate.title);
		typeComboBox = new UComboBox();
		if (dataTemplate.modelList != null
				&& dataTemplate.modelList.size() != 0) {
			for (int i = 0; i < dataTemplate.modelList.size(); i++) {
				typeComboBox.addItem(dataTemplate.modelList.get(i));
			}
		}
		add(label.getAWTComponent());
		add(typeComboBox.getAWTComponent());

	}
	@Override
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return dataTemplate;
	}

	@Override
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.dataTemplate = (UDataIoTemplate) template;
	}
	public Object getData() {
		return typeComboBox.getSelectedItem();
	}

	public void setData(Object obj) {
		if (obj != null)
			typeComboBox.setSelectedItem(obj);
	}
}
