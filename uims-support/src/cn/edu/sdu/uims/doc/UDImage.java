package cn.edu.sdu.uims.doc;

import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.uims.def.UParagraphContent;

public class UDImage extends UDParagraph {
	
	private String filePath;

	public void exDocument(UDocumentUtilBaseI util, UDocument doc,
			UParagraphContent content) {
		// TODO Auto-generated method stub
		UParagraphTemplate t = (UParagraphTemplate) content.template;
		exDocument(util, doc, t);
	}
	public void exDocument(UDocumentUtilBaseI util, UDocument doc,
			UParagraphTemplate template) {
		util.addImage(doc,filePath,template);
	}
	public void setData(Object obj) {
		if(obj == null)
			filePath = "";
		else
			filePath = obj.toString();
	}

}
