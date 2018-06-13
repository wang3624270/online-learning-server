package cn.edu.sdu.uims.doc;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.util.UTextUtil;

public class UDDate extends UDParagraph {
	public UDDate(){
		text = UTextUtil.getDataString(UConstants.DATA_MODE_NUMBER);
	}
}
