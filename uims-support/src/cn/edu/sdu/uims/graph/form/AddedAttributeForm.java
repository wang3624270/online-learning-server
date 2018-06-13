package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class AddedAttributeForm extends UForm implements AddedAttributeFormI {
	private Integer addedAttributeFormId;

	public Integer getAddedAttributeFormId() {
		return addedAttributeFormId;
	}

	public void setAddedAttributeFormId(Integer addedAttributeFormId) {
		this.addedAttributeFormId = addedAttributeFormId;
	}

	public void getAddedAttributeById(Integer id) {
		this.addedAttributeFormId = id;
	}
}
