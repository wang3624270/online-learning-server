package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.List;

public class UPageEventTemplate {

	protected String action;
	protected String reRender;
	protected String vars;
	protected String validator;
	protected List<String> depends;
	protected String path;
	protected String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getDepends() {
		return depends;
	}

	public void setDepends(List<String> depends) {
		this.depends = depends;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public UPageEventTemplate(){
		
		this.depends = new ArrayList<String>();
	}

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
	public String getVars() {
		return vars;
	}
	public void setVars(String vars) {
		this.vars = vars;
	}
	public String getValidator() {
		return validator;
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
}
