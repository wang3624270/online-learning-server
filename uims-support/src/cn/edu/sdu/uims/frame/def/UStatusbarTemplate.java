package cn.edu.sdu.uims.frame.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.common.reportdog.UTemplate;

public class UStatusbarTemplate extends UTemplate{
	public String fontName =null;
	public String colorName;
	public String bgColorName;
	public String department;
	public String version;
	public String telephone; 
	public String email;
	public int height = 20;
	public boolean  hasProgress = true; 
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		fontName = readString(in);
		department = readString(in);
		version = readString(in);
		telephone = readString(in); 
		hasProgress = in.readBoolean(); 
		
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, fontName);
		writeString(out, department);
		writeString(out, version);
		writeString(out, telephone); 
		out.writeBoolean(hasProgress); 
	}
}
