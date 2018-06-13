package cn.edu.sdu.uims.handler;

import java.util.List;

import cn.edu.sdu.common.form.UFormI;

public interface UQueryAddTableItemHandlerI {
	List getQueryItems(UFormI form);
	void addItemListToTable(List list);
}
