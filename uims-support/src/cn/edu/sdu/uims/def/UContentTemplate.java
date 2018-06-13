package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.common.reportdog.UTemplate;

public class UContentTemplate extends UTemplate {
	public int contentNum;

	public int getContentNum() {
		return contentNum;
	}

	public void setContentNum(int contentNum) {
		this.contentNum = contentNum;
	}

	public UBlockContent[] getContents() {
		return contents;
	}

	public void setContents(UBlockContent[] contents) {
		this.contents = contents;
	}

	public String getDataFormClassName() {
		return dataFormClassName;
	}

	public void setDataFormClassName(String dataFormClassName) {
		this.dataFormClassName = dataFormClassName;
	}

	public String getHandlerClassName() {
		return handlerClassName;
	}

	public void setHandlerClassName(String handlerClassName) {
		this.handlerClassName = handlerClassName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDataInitClassName() {
		return dataInitClassName;
	}

	public void setDataInitClassName(String dataInitClassName) {
		this.dataInitClassName = dataInitClassName;
	}

	public String getInitMethodName() {
		return initMethodName;
	}

	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}

	public UBlockContent contents[];
	public String dataFormClassName, handlerClassName;

	// liuyang add
	public String ruleName;
	public String beanId;
	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String methodName;
	public String dataInitClassName;
	public String initMethodName;

	public void read(DataInputStream in) throws IOException {
		super.read(in);
		contentNum = in.readInt();
		int len, i;
		len = in.readInt();
		if (len == 0)
			contents = null;
		else {
			contents = new UBlockContent[len];
			for (i = 0; i < len; i++)
				contents[i] = (UBlockContent) readTemplate(in);
		}
		dataFormClassName = readString(in);
		handlerClassName = readString(in);
		ruleName = readString(in);
		methodName = readString(in);
		dataInitClassName = readString(in);
		initMethodName = readString(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(contentNum);
		if (contents == null)
			out.writeInt(0);
		else {
			out.writeInt(contents.length);
			for (int i = 0; i < contents.length; i++)
				writeTemplate(out, contents[i]);
		}
		writeString(out, dataFormClassName);
		writeString(out, handlerClassName);
		writeString(out, ruleName);
		writeString(out, methodName);
		writeString(out, dataInitClassName);
		writeString(out, initMethodName);
	}
}
