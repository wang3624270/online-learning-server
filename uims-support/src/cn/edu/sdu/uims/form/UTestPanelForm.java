package cn.edu.sdu.uims.form;

import cn.edu.sdu.common.form.UForm;

public class UTestPanelForm extends UForm {
	String studentId;
	public Object getStudentId(){
		return studentId;
	}
	public void setStudentId(Object id){
		studentId  =  (String)id; 
	}
}
