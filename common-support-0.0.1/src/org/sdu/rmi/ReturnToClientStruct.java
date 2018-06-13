package org.sdu.rmi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReturnToClientStruct implements Serializable{

	private String reCode = "0";
	private List<String> errorMessageList = null;

	public ReturnToClientStruct() {
		errorMessageList = new ArrayList<String>();
	}

	public String getReCode() {
		return reCode;
	}

	public void setReCode(String code) {
		this.reCode = code;
		if (this.reCode.equals(RestConstants.ret_error))
			errorMessageList.add("登录错误提示：");
	}

	public List<String> getErrorMessageList() {

		return errorMessageList;
	}

	public void setErrorMessageList(List<String> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}

	public void addErrorMessage(String errorMessage) {
		if (errorMessageList == null)
			errorMessageList = new ArrayList<String>();
		errorMessageList.add(errorMessage);
	}

}