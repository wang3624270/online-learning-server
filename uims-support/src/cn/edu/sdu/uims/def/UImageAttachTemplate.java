package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

public class UImageAttachTemplate 	extends UElementTemplate {
		public  int viewWidth = 960;
		public int viewHeight = 720;
		public void getSelfAttribute(Element e1){
			String str;
			name = e1.attributeValue("name");
			str = e1.attributeValue("viewWidth");
			if(str != null && str.length() > 0) {
				viewWidth = Integer.parseInt(str);
			}
			str = e1.attributeValue("viewHeight");
			if(str != null && str.length() > 0) {
				viewHeight = Integer.parseInt(str);
			}
		}
		public void read(DataInputStream in) throws IOException {
			super.read(in);
		}
		public void write(DataOutputStream out) throws IOException {
			super.write(out);
		}

}
