package cn.edu.sdu.common.reportdog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UParagraphTemplate extends UBlockTemplate {
	public int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getFirstSpace() {
		return firstSpace;
	}
	public void setFirstSpace(int firstSpace) {
		this.firstSpace = firstSpace;
	}
	int height;
	public int firstSpace = 2;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		type = in.readInt();
		height = in.readInt();
		firstSpace = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(type);
		out.writeInt(height);
		out.writeInt(firstSpace);
	}
}
