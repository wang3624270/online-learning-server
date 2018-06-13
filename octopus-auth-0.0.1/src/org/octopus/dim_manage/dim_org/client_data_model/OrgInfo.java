package org.octopus.dim_manage.dim_org.client_data_model;

import java.util.List;

import org.octopus.dim_manage.dim_org.jpa_model.DimOrg;
import org.sdu.rmi.ReturnToClientStruct;

public class OrgInfo extends ReturnToClientStruct {
	private DimOrg dimOrg;

	private String belongOrg;
	private List<OrgInfo> childrenOrgInfo;

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

	public String getBelongOrg() {
		return belongOrg;
	}

	public void setBelongOrg(String belongOrg) {
		this.belongOrg = belongOrg;
	}

	public String getDescription() {
		return dimOrg.getDescription();
	}

	public List<OrgInfo> getChildrenOrgInfo() {
		return childrenOrgInfo;
	}

	public void setChildrenOrgInfo(List<OrgInfo> childrenOrgInfo) {
		this.childrenOrgInfo = childrenOrgInfo;
	}

}