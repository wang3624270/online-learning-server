package cn.edu.sdu.uims.def.dataexport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.sdu.spring_util.ApplicationContextHandle;

import cn.edu.sdu.uims.util.UimsUtils;

public class DataExportTemplateToXLSBySQL extends DataExportTemplateToXLS {
	private List<String> clauseList;
	private String whereClause;
	private String groupClause;
	private String sortClause;
	protected List<DataExportTableTemplate> tableList;
	
	public List<String> getClauseList() {
		return clauseList;
	}


	public void setClauseList(List<String> clauseList) {
		this.clauseList = clauseList;
	}




	public List<DataExportTableTemplate> getTableList() {
		return tableList;
	}


	public void setTableList(List<DataExportTableTemplate> tableList) {
		this.tableList = tableList;
	}


	public String getWhereClause() {
		return whereClause;
	}


	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}


	public String getGroupClause() {
		return groupClause;
	}


	public void setGroupClause(String groupClause) {
		this.groupClause = groupClause;
	}


	public String getSortClause() {
		return sortClause;
	}


	public void setSortClause(String sortClause) {
		this.sortClause = sortClause;
	}

	public void getAttribute(Element e) {
		super.getAttribute(e);
		Iterator it1;
		Element e1 = null;
		String str;
		DataExportTableTemplate tableItem;
		clauseList = null;
		tableList = null;
		str = UimsUtils.getMultiRowString(e, "clause");
		if(str != null) {
			if(clauseList == null)
				clauseList = new ArrayList<String>();
			clauseList.add(str);
		}
		it1 = e.elementIterator("clauses");
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			str = UimsUtils.getMultiRowString(e, "clause");
			if(str == null)
				continue;
			if (clauseList == null)
				clauseList = new ArrayList<String>();
			clauseList.add(str);			
		}
			tableItem = new DataExportTableTemplate();
		
		whereClause = UimsUtils.getMultiRowString(e, "where");
		groupClause = UimsUtils.getMultiRowString(e, "group");
		sortClause = UimsUtils.getMultiRowString(e, "sort");
		it1 = e.elementIterator("table");
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			if (tableList == null)
				tableList = new ArrayList<DataExportTableTemplate>();
			tableItem = new DataExportTableTemplate();
			tableItem.getAttribute(e1);
			tableList.add(tableItem);
		}
	}
	public String getSQLString(){
		StringBuffer sb = new StringBuffer(1024);
		if(itemList == null || tableList == null)
			return null;
		int i ;
		DataExportItemTemplate item;
		sb.append("select ");
		for(i = 0; i < itemList.size();i++) {
			item = itemList.get(i);
			sb.append(item.getAnonym() + "." + item.getName() );
			if(i < itemList.size()-1)
				sb.append(", ");
		}
		sb.append(" from ");
		DataExportTableTemplate tableItem;		
		for(i= 0; i < tableList.size();i++){
			tableItem = tableList.get(i);
			sb.append(tableItem.getName()+ " " + tableItem.getAnonym());
			if(tableItem.getClause() != null) {
				sb.append(" "+ tableItem.getClause());
			}
			if(i < tableList.size()-1)
				sb.append(", ");
		}
		return sb.toString();
	}
	public void resetChangePi(){
		if(itemList == null)
			return;
		DataExportItemTemplate item;
		DataExportItemProcessorI pi;
		for(int i = 0; i < itemList.size();i++) {
			item = itemList.get(i);
			pi = null;
			if(item.getChanger() != null) {
				pi = (DataExportItemProcessorI)ApplicationContextHandle.getBean(item.getChanger());
			}
			item.setChangePi(pi);
		}
			
	}
	public void resetComputePi(){
		if(itemList == null)
			return;
		DataExportItemTemplate item;
		DataExportItemComputerI pi;
		for(int i = 0; i < itemList.size();i++) {
			item = itemList.get(i);
			pi = null;
			if(item.getComputer() != null) {
				pi = (DataExportItemComputerI)ApplicationContextHandle.getBean(item.getComputer());
			}
			item.setComputePi(pi);
		}
			
	}
}
