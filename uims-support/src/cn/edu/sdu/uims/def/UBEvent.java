package cn.edu.sdu.uims.def;

public class UBEvent {

	public String action;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getReRender() {
		return reRender;
	}
	public void setReRender(String reRender) {
		this.reRender = reRender;
	}
	public String[] getVars() {
		return vars;
	}
	public void setVars(String[] vars) {
		this.vars = vars;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public String getValidator() {
		return validator;
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
	public String reRender;
	public String[] vars;
	public String handler;
	public String validator;
}
