package cn.edu.sdu.uims.base;

import cn.edu.sdu.uims.form.impl.UTableQueryDataForm;

public interface UPageActionComponentI {
	void doTablePageDataQuery(UTableQueryDataForm form);
	void setTableQueryDataForm(UTableQueryDataForm form);
	UTableQueryDataForm getTableQueryDataForm();
	void displayPageData();
}
