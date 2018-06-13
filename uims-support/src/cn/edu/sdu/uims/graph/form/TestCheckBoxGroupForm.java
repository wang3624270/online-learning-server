package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class TestCheckBoxGroupForm extends UForm {
	Boolean [] cheks;

	public TestCheckBoxGroupForm(){
		cheks = new Boolean []{false,false,false};
	}
	public Boolean[] getCheks() {
		return cheks;
	}

	public void setCheks(Boolean[] cheks) {
		this.cheks = cheks;
	}
}
