package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UMenuBarTemplate extends UMenuTemplate {
	public boolean isExpandAll = false;
	public int alignment;
	public int getAlignment() {
		return alignment;
	}
	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		alignment = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(alignment);
	}
}
