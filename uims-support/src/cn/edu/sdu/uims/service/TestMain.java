package cn.edu.sdu.uims.service;

import java.util.HashMap;
import java.util.Iterator;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HashMap map = new HashMap();
		map.put("key", "value");
		Iterator ite = map.keySet().iterator();
		while(ite.hasNext()){
			System.out.println(ite.next());
		}

	}

}
