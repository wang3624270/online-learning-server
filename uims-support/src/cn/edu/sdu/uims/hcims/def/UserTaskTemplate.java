package cn.edu.sdu.uims.hcims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.itms.ItmsConstants;

public class UserTaskTemplate extends UTemplate{
	public String hciTaskName;
	public String pointCursorName;
	public int cursorDrawMode = ItmsConstants.CURSOR_DRAW_MODE_DEFAULT;
	public boolean isRepeat;
	public String dataForm;
	public void setAttribute(Element e){
		String str;
		hciTaskName = e.attributeValue("hciTask");
		pointCursorName = e.attributeValue("pointCursor");
		if(pointCursorName == null)
			pointCursorName = "default";
		str = e.attributeValue("cursorDrawMode");
		if(str != null ){
			if(str.equals("draw"))
				cursorDrawMode = ItmsConstants.CURSOR_DRAW_MODE_DRAW;
			else if(str.equals("imageCopy")){
				cursorDrawMode = ItmsConstants.CURSOR_DRAW_MODE_IMAGECOPY;				
			}	
		}		
		str = e.attributeValue("repeat");
		if(str == null || str.equals("false")){
			isRepeat = false;
		}
		else 
			isRepeat = true;
		dataForm = e.attributeValue("dataForm");
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		hciTaskName = readString(in);
		pointCursorName = readString(in);
		cursorDrawMode = in.readInt();
		isRepeat = in.readBoolean();
		dataForm = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, hciTaskName);
		writeString(out, pointCursorName);
		out.writeInt(cursorDrawMode);
		out.writeBoolean(isRepeat);
		writeString(out, dataForm);
	}
}
