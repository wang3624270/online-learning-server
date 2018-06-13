package cn.edu.sdu.uims.trans;

import java.util.ArrayList;
import java.util.List;

public class UMatrixStack {
	static List stack = new ArrayList();
	public static void push(UMatrix m){
		stack.add(m);
	}
	static UMatrix popup(){
		int size = stack.size();
		if(size == 0)
			return null;
		UMatrix m = (UMatrix)stack.get(size-1);
		stack.remove(size-1);
		return m;
	}
}
