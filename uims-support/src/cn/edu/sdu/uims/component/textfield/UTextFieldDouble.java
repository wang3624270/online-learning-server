package cn.edu.sdu.uims.component.textfield;

import java.text.DecimalFormat;

public class UTextFieldDouble extends UTextField {
	public Object getData() {
		if(getText() == null  || getText().length() == 0 )
			return 0.0;
		return new Double(getText());
	}
	
	public void setData(Object obj) {
		if(obj == null)
			setText("");
		else {
			if(template != null && template.maxLength != 0) {
				String mark = "0.";
				for(int i = 0;i< template.maxLength; i++)
					mark += "#";
			    DecimalFormat fmt = new DecimalFormat (mark);
			    if(obj instanceof Double) {
			    	Double dd = (Double)obj;		    
			    	setText(fmt.format(dd));
			    }
			    else if(obj instanceof Float) {
			    	Float ff = (Float)obj;		    
			    	setText(fmt.format(ff));
			    }
			    else {
			    	setText(obj.toString());
			    }
			}
		    else 
		    {
		    	setText(obj.toString());
		    }
		}
	}
}
