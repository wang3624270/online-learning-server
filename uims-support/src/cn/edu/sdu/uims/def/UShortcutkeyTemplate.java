package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.common.reportdog.UTemplate;

public class UShortcutkeyTemplate extends UTemplate {
	public boolean ctrl = false, shift = false;
	public char charValue;
	public int intValue;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		ctrl = in.readBoolean();
		shift = in.readBoolean();
		charValue = in.readChar();
		intValue = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeBoolean(ctrl);
		out.writeBoolean(shift);
		out.writeChar(charValue);
		out.writeInt(intValue);
	}
}
