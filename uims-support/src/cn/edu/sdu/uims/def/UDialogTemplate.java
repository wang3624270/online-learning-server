package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UDialogTemplate extends UPanelTemplate {
	public int xo;
	public int yo;
	public String ok  = "确认";
	public String cancel ="取消"; 
	public String okEn  = "ACEPTAR";
	public String cancelEn ="CANCELAR"; 
	public UButtonTemplate buttons[];
	public String imageName;
	public String bgColorName;
	public int getXo() {
		return xo;
	}
	public void setXo(int xo) {
		this.xo = xo;
	}
	public int getYo() {
		return yo;
	}
	public void setYo(int yo) {
		this.yo = yo;
	}
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public String getCancel() {
		return cancel;
	}
	public void setCancel(String cancel) {
		this.cancel = cancel;
	}
	public UButtonTemplate[] getButtons() {
		return buttons;
	}
	public void setButtons(UButtonTemplate[] buttons) {
		this.buttons = buttons;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		xo = in.readInt();
		yo = in.readInt();
		ok = readString(in); 
		if(ok == null)
			ok = "";
		cancel  = readString(in);
		if(cancel == null)
			cancel = "";
		int len ,i;
		len = in.readInt();
		if(len == 0)
			buttons = null;
		else {
			buttons = new  UButtonTemplate[len];
			for(i = 0 ; i< len;i++)
				buttons[i] = (UButtonTemplate)readTemplate(in);
		}
		imageName = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(xo);
		out.writeInt(yo);
		writeString(out, ok);
		writeString(out,cancel);
		int len, i;
		if(buttons == null) 
			out.writeInt(0);
		else {
			out.writeInt(buttons.length);
			for(i = 0; i < buttons.length;i++){
				writeTemplate(out,buttons[i]);
			}
		}
		writeString(out,imageName);
	}
}
