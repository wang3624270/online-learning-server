package cn.edu.sdu.uims.filter;

import java.util.HashMap;

import javax.swing.text.PlainDocument;

public interface CheckFilterI extends FilterI {
	public String change(String str,PlainDocument doc, HashMap paras);

}
