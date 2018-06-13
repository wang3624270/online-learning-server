package cn.edu.sdu.common.reportdog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UPageNumberTemplate extends UTemplate{
	public int layoutType;
	public int numberType;
	public String fontName;
	public String colorName;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		layoutType = in.readInt();
		numberType = in.readInt();
		fontName = readString(in);
		colorName = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(layoutType);
		out.writeInt(numberType);
		writeString(out,fontName);
		writeString(out,colorName);
	}
}
