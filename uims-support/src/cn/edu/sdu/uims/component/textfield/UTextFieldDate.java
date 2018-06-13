package cn.edu.sdu.uims.component.textfield;

import java.util.Date;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.service.UFactory;

public class UTextFieldDate extends UTextField {
	public Object getData() {
		String str = getText();
		if (str == null || str.length() < 6)
			return null;
		if (str.length() < 8) {
			return DateTimeTool.formatDateTime(
					str.substring(0, 4) + "-" + str.substring(4, 6) + "-01", "yyyy-MM-dd");
		}if (str.length() == 8) {
			return DateTimeTool.formatDateTime(
					str.substring(0, 4) + "-" + str.substring(4, 6) + "-"
							+ str.subSequence(6, 8), "yyyy-MM-dd");
		} else {
			return DateTimeTool.formatDateTime(str, "yyyy-MM-dd");
		}
	}

	public void setData(Object obj) {
		String str = "";
		if (obj == null) {
			str = "";
		} else if (obj instanceof Date) {
			str = DateTimeTool.parseDateTime((Date) obj, "yyyy-MM-dd");
			str = str.substring(0, 4) + str.substring(5, 7)
					+ str.substring(8, 10);
		} else
			str = obj.toString();
		this.setText(str);
	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		if(border.obj == null)
			setBorder(1,new UColor(0,0,0));
		else
			setBorder((int) border.width, UFactory.getModelSession().getColorByName(border.colorName));
	}

}
