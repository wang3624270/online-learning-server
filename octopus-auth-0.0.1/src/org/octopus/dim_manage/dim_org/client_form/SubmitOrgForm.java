package org.octopus.dim_manage.dim_org.client_form;

import org.hibernate.validator.constraints.NotEmpty;
import org.octopus.spring_utils.CommonForm;

public class SubmitOrgForm extends CommonForm {
	@NotEmpty(message = "{org.octopus.dim_manage.dim_org.orgName.null}")
	private String orgName;
	private String dlgBelongOrg;
	@NotEmpty(message = "{org.octopus.dim_manage.dim_org.orgCode.null}")

	private String orgCode;
	private String orgDes;
	private String orgAddress;

	private String logicId;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDes() {
		return orgDes;
	}

	public void setOrgDes(String orgDes) {
		this.orgDes = orgDes;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getDlgBelongOrg() {
		return dlgBelongOrg;
	}

	public void setDlgBelongOrg(String dlgBelongOrg) {
		this.dlgBelongOrg = dlgBelongOrg;
	}

	public String getLogicId() {
		return logicId;
	}

	public void setLogicId(String logicId) {
		this.logicId = logicId;
	}

}