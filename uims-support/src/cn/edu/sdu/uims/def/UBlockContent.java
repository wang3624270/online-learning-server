package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import cn.edu.sdu.common.reportdog.UBloackBaseTemplate;
import cn.edu.sdu.uims.base.UContentElementI;

public class UBlockContent extends UBloackBaseTemplate {
	public int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public UContentElementI getObject() {
		return object;
	}
	public void setObject(UContentElementI object) {
		this.object = object;
	}
	public UBloackBaseTemplate getTemplate() {
		return template;
	}
	public void setTemplate(UBloackBaseTemplate template) {
		this.template = template;
	}
	public HashMap getParas() {
		return paras;
	}
	public void setParas(HashMap paras) {
		this.paras = paras;
	}
	public String getDataFormMember() {
		return dataFormMember;
	}
	public void setDataFormMember(String dataFormMember) {
		this.dataFormMember = dataFormMember;
	}
	public UContentElementI object = null;
	public UBloackBaseTemplate template;
	public HashMap paras = null;
	public String dataFormMember = null;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		type = in.readInt();
		template = (UBloackBaseTemplate)readTemplate(in);
		int len, i;
		len = in.readInt();
		if(len == 0) {
			paras = null;
		}
		else {
			paras = new HashMap();
			for(i = 0; i < len;i++) {
				paras.put(readString(in), readString(in));
			}
		}
		dataFormMember = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(type);
		writeTemplate(out, template);
		if(paras == null)
			out.writeInt(0);
		else {
			String s;
			out.writeInt(paras.size());
			Iterator it = paras.keySet().iterator();
			while(it.hasNext()){
				s = (String )it.next();
				writeString(out, s);
				writeString(out, (String)paras.get(s));
			}
		}
		writeString(out, dataFormMember);
	}
}
