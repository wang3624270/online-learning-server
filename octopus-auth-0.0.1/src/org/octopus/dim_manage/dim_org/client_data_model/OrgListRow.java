package org.octopus.dim_manage.dim_org.client_data_model;

import org.octopus.dim_manage.dim_org.jpa_model.DimOrg;

public class OrgListRow {
	private DimOrg dimOrg;

	public DimOrg getDimOrg() {
		return dimOrg;
	}

	public void setDimOrg(DimOrg dimOrg) {
		this.dimOrg = dimOrg;
	}

	public String getName() {
		return dimOrg.getName();
	}

	public String getCode() {
		return dimOrg.getCode();

	}

	public String getAddress() {
		return dimOrg.getAddress();
	}

	public String getLogicId() {
		return dimOrg.getLogicId();
	}

	public String getText() {
		return dimOrg.getName() + "  " + dimOrg.getCode();
	}
}