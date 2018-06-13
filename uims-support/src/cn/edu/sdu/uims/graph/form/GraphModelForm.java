package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class GraphModelForm extends UForm {
	
	private String name;
	
	private String num;
	
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString(){
		return name;
	}
	

}
