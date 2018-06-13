package cn.edu.sdu.uims.component.table;

public class ViewUpdateUtils implements java.io.Serializable{

	private int id;

	private int row;
	
	private int column;

	private String ColumnName;

	private Object value;
	
	private Class cls;
	
	public Class getCls() {
		return cls;
	}

	public void setCls(Class cls) {
		this.cls = cls;
	}

	public ViewUpdateUtils(){
		
	}
	
	public ViewUpdateUtils(int row ,int column,Object value,Class cls){
		this.row=row;
		this.column=column;
		this.value=value;
		this.cls=cls;
	}

	public String getColumnName() {
		return ColumnName;
	}

	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
