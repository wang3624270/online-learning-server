package cn.edu.sdu.uims.filter;

import java.util.ArrayList;
import java.util.List;


public class NumberCompletionFilter implements CompletionFilterI {

	private ArrayList arrayList;

	public NumberCompletionFilter() {
		// this(null);
	}

	public void init(String code) {
	}

	public ArrayList filter(String text) {
		return arrayList;
	}
	public Object  getAddedData(){
		return arrayList.toArray();
	}
	public void setAddedData(Object[] a){
	}

	public void setAddedData(List a) {
		// TODO Auto-generated method stub
		arrayList = (ArrayList)a;
	}


}
