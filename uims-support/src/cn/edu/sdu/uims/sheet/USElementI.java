package cn.edu.sdu.uims.sheet;

import cn.edu.sdu.common.reportdog.USheetParameter;
import cn.edu.sdu.uims.def.UBlockContent;
import cn.edu.sdu.uims.util.USheetUtil;

public interface USElementI extends USheetContentElementI{
	public void exSheet(USheetUtil util, USheetParameter par , UBlockContent constant);
	public void imSheet(Object[] data);
}
