package cn.edu.sdu.uims.filter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.uims.util.UPinYin;


public class DefaultCompletionFilter implements CompletionFilterI {
	private UPinYin pinyin = null;

	public DefaultCompletionFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void init(String code ){
		
	}
	public ArrayList filter(String text) {
		// TODO Auto-generated method stub
		pinyin = new UPinYin();
		List list = null;
		list = pinyin.exePinYin(text);
		return (ArrayList) list;
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
