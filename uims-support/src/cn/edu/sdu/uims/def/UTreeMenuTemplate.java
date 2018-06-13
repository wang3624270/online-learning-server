package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UTreeMenuTemplate extends UMenuBarTemplate {

	public int size() {
		// TODO Auto-generated method stub
		return items.length;
	}

	public UMenuTemplate getMenuTemplate(int i) {
		// TODO Auto-generated method stub
		return (UMenuTemplate) items[i];
	}

	public UMenuItemTemplate get(int i) {
		// TODO Auto-generated method stub
		return items[i];
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}

}
