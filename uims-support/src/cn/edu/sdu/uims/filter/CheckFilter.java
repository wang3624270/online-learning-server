package cn.edu.sdu.uims.filter;

import java.util.HashMap;
import java.util.List;

import javax.swing.text.PlainDocument;

public class CheckFilter implements CheckFilterI {

	private String ruleName = null;

	public void init(String parameter) {
		ruleName = parameter;
	}

	public String change(String str, PlainDocument doc, HashMap paras) {
		if (ruleName.equals("Integer")) {
			return changeInteger(str,paras);
		}
		if (ruleName.equals("Float")) {
			return changeFloat(str,doc, paras);
		}
		if (ruleName.equals("Day")) {
			return changeDay(str,doc, paras);
		}
		return null;
	}

	public String changeInteger(String str, HashMap paras) {
		char[] upper = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < upper.length; i++) {
			if (upper[i] >= '0' && upper[i] <= '9') {
				upper[pos] = upper[i];
				pos++;
			}
		}
		return new String(upper, 0, pos);
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String changeFloat(String str, PlainDocument doc, HashMap paras) {

		boolean hasDot = false;
		
		String s = null;
		try {
			s= doc.getText(0, doc.getLength());
		}catch(Exception e){
			return null;
		}
		if (s.indexOf('.') < 0)
			hasDot = false;
		else
			hasDot = true;

		char[] upper = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < upper.length; i++) {
			if (upper[i] >= '0' && upper[i] <= '9') {
				upper[pos] = upper[i];
				pos++;
			} else if (upper[i] == '.') {
				if (!hasDot) {
					hasDot = true;
					upper[pos] = upper[i];
					pos++;
				}
			}
		}
		return new String(upper, 0, pos);
	}
	public String changeDay(String str, PlainDocument doc, HashMap paras) {

		boolean hasDot = false;
		
		int len =0;
		try {
			 len = doc.getLength();
		}catch(Exception e){
			return null;
		}
		char[] upper = str.toCharArray();
		return new String(upper);
	}
	public Object  getAddedData(){
		return null;
	}
	public void setAddedData(Object[] a){
		
	}


	public void setAddedData(List a) {
		// TODO Auto-generated method stub
		
	}


}
