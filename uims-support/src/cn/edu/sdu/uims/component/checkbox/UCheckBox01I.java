package cn.edu.sdu.uims.component.checkbox;

public class UCheckBox01I extends UCheckBox {
	public void setData(Object obj) {
		if (obj != null) {
			Integer str = (Integer) obj;
			if(str.equals(1))
				this.setSelected(true);
			else 
				this.setSelected(false);
		}else {
			this.setSelected(false);			
		}
	}
	public Object getData() {
		// TODO Auto-generated method stub
		if(isSelected())
			return 1;
		else 
			return 0;
	}

}
