package cn.edu.sdu.uims.form;

public interface UTextFieldDataFormI {
	Integer getDataObjectId();
	String  getDataObjectNum();
	String getDataObjectText();
	void setDataObjectNum(String num);
	void setDataObjectId(Integer id);
	void clear();
}
