package org.octopus.reportdog.state;

import java.io.Serializable;

public class BlockState implements Serializable{
	
	public String content;
	
	public String name;
	
	public String type;
	
	public String templateName;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}




	
	

}
