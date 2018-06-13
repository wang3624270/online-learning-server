package org.octopus.auth.client_form;

import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;
import org.octopus.spring_utils.CommonForm;

public class WebLoginForm extends CommonForm {
	@NotEmpty(message = "{auth.loginName.null}")
	private String loginName;
	private String password;
	private String loginType;
	private Map parameter;
	private String validateCode;
	

	public String getLoginType() {
		return loginType;
	}


	public Map getParameter() {
		return parameter;
	}


	public void setParameter(Map parameter) {
		this.parameter = parameter;
	}


	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}




	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName; 
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

}