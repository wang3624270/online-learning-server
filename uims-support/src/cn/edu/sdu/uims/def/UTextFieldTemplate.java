package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

public class UTextFieldTemplate extends UElementTemplate {
	public int maxLength = 5;
	
	//liuyang added textfield匹配规则
	public String expression;
	public int lw;
	public boolean removeSpace = false;
	public String dialogName;
	

	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	public void getSelfAttribute(Element e1){
		String str;
		name = e1.attributeValue("name");
		str = e1.attributeValue("maxLength");
		if(str != null)
			maxLength = Integer.parseInt(str);
		expression=e1.attributeValue("expression");
		str = e1.attributeValue("lw");
		if(str != null)
			lw = Integer.parseInt(str);
		else
			lw = -1;
		str = e1.attributeValue("removeSpace");
		if(str != null && str.equals("true"))
			removeSpace = true;
		else
			removeSpace = false;
		dialogName = e1.attributeValue("dialogName");		

	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		maxLength = in.readInt();
		expression = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(maxLength);
		writeString(out,expression);
	}
	
}
