package cn.edu.sdu.uims.frame.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UMenuItemTemplate;

public class UToolTemplate extends UMenuItemTemplate{
	public int type = UConstants.COMPONENT_SEPARATOR;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		type = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(type);
	}
}
