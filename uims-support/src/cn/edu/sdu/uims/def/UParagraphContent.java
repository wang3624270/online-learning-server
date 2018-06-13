package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.uims.constant.UConstants;

public class UParagraphContent extends UBlockContent {
	public String text;
	public String backgroundColorName;
	public String newPage;
	public int horizontalAlignment = UConstants.ALIGNMENT_CENTER;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		text =readString(in);
		backgroundColorName =readString(in);
		newPage =readString(in);
		horizontalAlignment = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, text);
		writeString(out, backgroundColorName);
		writeString(out, newPage);
		out.writeInt(horizontalAlignment);
	}
}
