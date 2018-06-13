package cn.edu.sdu.uims.base;

import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;


public interface UTableI extends  UTemplateComponentI {
	boolean saveDataToExcel(String fileName);
	boolean saveDataToDBF(String fileName);
	FilterI  getColumnFilter(String name);
	void setStartNo(int no);
	void displayCurrentRowDetail(UHandlerI h);
	void tableColumnSortSet(UHandlerI handler);
}
