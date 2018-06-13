package cn.edu.sdu.uims.handler;

import java.util.List;

import cn.edu.sdu.uims.form.impl.UTableQueryDataForm;

public interface TablePageDataQueryHandlerI {
	void getTablePageData(UTableQueryDataForm form);
	List getDataListByIdList(List<Integer> idList);
}
