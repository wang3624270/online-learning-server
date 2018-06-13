
package cn.edu.sdu.common.form;

public final class ListOptionInfo implements
		Comparable,UFormI {
	private static final long serialVersionUID = 1L;
	
	public static boolean isEnglishVersion = false; 
	private String value = null;
	private String label = null;
	private String enLabel = null;
	private String num;
	private String parentValue = "0";
	public static int compareType = 0;
	public static final int COMPARE_TYPE_NAME = 1; 
	public static final int COMPARE_TYPE_VALUE = 0; 
	private String category=null;//类别
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ListOptionInfo() {
		super();
	}

	public ListOptionInfo(String parentValue, String value, String label) {
		this.parentValue = parentValue;
		this.value = value;
		this.label = label;
	}
	public ListOptionInfo(String parentValue, String value, String label, String enLabel) {
		this.parentValue = parentValue;
		this.value = value;
		this.label = label;
		this.enLabel = enLabel;
	}
	public ListOptionInfo(String parentValue, String value, String label, String enLabel,String num) {
		this.parentValue = parentValue;
		this.value = value;
		this.label = label;
		this.enLabel = enLabel;
		this.num = num;
	}

	public ListOptionInfo(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {

		String str = "";
		if(num != null && num.length() != 0) {
			str += num + "-";
		}
		if(isEnglishVersion && this.enLabel != null)
			str += enLabel;
		else
			str += label;
		return str;
	}

	public boolean equals(Object obj) {
		// ------------赵鹏添加
		if (obj == null || !(obj instanceof ListOptionInfo)) {
			return false;
		}
		// ------------

		ListOptionInfo temp = (ListOptionInfo) obj;

		// ------------赵鹏添加
		if (this.value == null && temp.getValue() == null) {
			return true;
		}
		// ------------

		if (temp.getValue() != null && this.value != null
				&& temp.getValue().equals(this.value)) {
			return true;
		} else {
			return false;
		}
	}

	public String getFilterKey() {
		// TODO Auto-generated method stub
		return value;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getParentValue() {
		return parentValue;
	}

	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}

	public String getEnLabel() {
		return enLabel;
	}

	public void setEnLabel(String enLabel) {
		this.enLabel = enLabel;
	}

	public int compareTo(Object o) {
		ListOptionInfo obj = (ListOptionInfo) o;
		switch(compareType) {
		case COMPARE_TYPE_VALUE:
			if (this.value.compareTo(obj.getValue()) < 0) {
				return -1;
			}
			if (this.value.compareTo(obj.getValue()) > 0) {
				return 1;
			}
			return 0;
		case COMPARE_TYPE_NAME:	
			if (this.label.compareTo(obj.getLabel()) < 0) {
				return -1;
			}
			if (this.label.compareTo(obj.getLabel()) > 0) {
				return 1;
			}
			return 0;
		}
		return 0;
	}

	@Override
	public Object getAttributeObject(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttributeObject(String attributeName, Integer index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getDependFieldValues() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttributeByParse(String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttributeObject(String attributeName, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttributeObject(String attributeName, Object obj,
			Integer index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	public String getNumName(){
		return toString();
	}
	
	public void setNumName(String numName){
		
	}
}
