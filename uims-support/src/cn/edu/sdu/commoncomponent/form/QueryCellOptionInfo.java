package cn.edu.sdu.commoncomponent.form;

public class QueryCellOptionInfo {

    private String value = null;
    private String label = null;
    private QueryCell cell=null;
    
	public QueryCellOptionInfo(){
		super();
	}
	public QueryCellOptionInfo(QueryCell cell){
		super();
		this.label=null;
		this.value=null;
		this.cell=cell;
	}
	public QueryCellOptionInfo(String label,String value,QueryCell cell){
		super();
		this.label=label;
		this.value=value;
		this.cell=cell;
	}
	public QueryCell getCell() {
		return cell;
	}
	public void setCell(QueryCell cell) {
		this.cell = cell;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String toString() {
		String displayStr="";
		if(this.value!=null){
			displayStr=this.label;
		}else if(this.cell!=null){
			if(this.cell.getDisplayName()!=null){
				displayStr=this.cell.getDisplayName();
			}
		}
		return displayStr;
	}

}
