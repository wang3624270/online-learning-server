package cn.edu.sdu.common.pi;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;

public interface ModelSessionBaseI {
	UFont getFontByName(String name);
	UColor getColorByName(String name);
	UParagraphTemplate getParagraphTemplateByName(String name);

}
