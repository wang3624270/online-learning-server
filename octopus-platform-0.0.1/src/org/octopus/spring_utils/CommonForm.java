package org.octopus.spring_utils;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;

public class CommonForm {
	static JSR303Helper helper = new JSR303Helper();
	private String octopusViewId;

	public CommonForm() {

	}

	public List<ConstraintViolation> valid() {
		return helper.validForm(this);
	}

	public List<String> validForMessages() {
		List<ConstraintViolation> l = valid();
		List<String> list = new ArrayList<String>();
		int i;
		for (i = 0; i < l.size(); i++) {

			list.add(l.get(i).getMessage());

		}
		return list;
	}

	public String getOctopusViewId() {
		return octopusViewId;
	}

	public void setOctopusViewId(String octopusViewId) {
		this.octopusViewId = octopusViewId;
	}
}