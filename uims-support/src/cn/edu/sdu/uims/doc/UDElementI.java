package cn.edu.sdu.uims.doc;
import java.util.HashMap;

import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.uims.def.UParagraphContent;

public interface UDElementI extends UDocContentElementI {
	public void exDocument(UDocumentUtilBaseI util, UDocument doc, UParagraphContent constant);
	public void initContent(UParagraphContent constant,HashMap requestMap,HashMap respondMap);

}
