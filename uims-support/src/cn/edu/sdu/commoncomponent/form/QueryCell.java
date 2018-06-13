package cn.edu.sdu.commoncomponent.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;

public class QueryCell {
	private String displayName;

	private String columnName;

	private String classType;// 标志类类型
	
	private String poName;//po的名字

	private List queryCellOptionList;// 可共选择的值,是一个AttributeValueForSelected对象列表
	
	public QueryCell(String dName,String cName, String classType) {
		this.columnName=cName;
		this.displayName=dName;
		this.classType=classType;
		this.poName=null;
		this.queryCellOptionList=null;
	}
	public QueryCell(String dName,String cName, String classType,String poName) {
		this.columnName=cName;
		this.displayName=dName;
		this.classType=classType;
		this.poName=poName;
		this.queryCellOptionList=null;
	}
	/**
	 * 
	 * @param dName 显示的名字
	 * @param cName 对应于po类中的属性名,要使用全名: 如培养信息中的学院id为 baseCollege.collegeId
	 * @param cls 标志字段的类型
	 * @param valueForSelected 存放的值是AttributeValueForSelected或ListOptionInfo对象；若valueForSelected不存在或为null，则表示该字段可让用户输入
	 */
	public QueryCell(String dName, String cName,String classType,String poName,List queryCellOptionList) {
		this.displayName=dName;
		this.columnName=cName;
		this.classType=classType;
		this.poName=poName;
		this.setQueryCellOptionList(queryCellOptionList);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String toString() {

		return displayName;
	}

	public void setQueryCellOptionList(List queryCellOptionList) {

		if(queryCellOptionList==null){
			this.queryCellOptionList=null;
			return;
		}
		List valuesList = new ArrayList(queryCellOptionList.size());
		Iterator iterator = queryCellOptionList.iterator();
		QueryCellOptionInfo value;
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj instanceof ListOptionInfo) {
				value = new QueryCellOptionInfo();
				value.setLabel(((ListOptionInfo) obj).getLabel());
				value.setValue(((ListOptionInfo) obj).getValue());
				value.setCell(this);
				valuesList.add(value);
			} else if (obj instanceof QueryCellOptionInfo) {
				((QueryCellOptionInfo) obj).setCell(((QueryCellOptionInfo)obj).getCell());
				valuesList.add(obj);
			}
		}

		this.queryCellOptionList = valuesList;
	}
	public List getQueryCellOptionList() {
		return queryCellOptionList;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getPoName() {
		return poName;
	}
	public void setPoName(String poName) {
		this.poName = poName;
	}

}
