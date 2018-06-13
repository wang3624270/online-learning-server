package cn.edu.sdu.uims.doc;

import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UDataContentPanel;
import cn.edu.sdu.uims.def.UContentTemplate;
import cn.edu.sdu.uims.def.UParagraphContent;

public class UDocContentPanel extends UDataContentPanel implements UDocPanelI {

	public UDocContentPanel() {
		this(null);
	}

	public UDocContentPanel(UContentTemplate contentTemplate) {
		super(contentTemplate);
	}

	public void dataOperateBegin(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub

	}

	public void dataOperateEnd(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub

	}

	public void exContent(int compNum) {
		// TODO Auto-generated method stub

	}

	public void exContent() {
		// TODO Auto-generated method stub

	}

	public void flush(int contNum) {
		// TODO Auto-generated method stub

	}

	public void imContent(HashMap requestMap, HashMap respondMap, int compNum) {
		// TODO Auto-generated method stub

	}

	public void initComponents(int compNum) {
		// TODO Auto-generated method stub

	}

	public void initEmptyComponents(HashMap requestMap, HashMap respondMap,
			int compNum) {
		// TODO Auto-generated method stub

	}

	public void initEmptyComponents(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub

	}

	public void initFormDataFromDB(Object data, int contentNum) {
		// TODO Auto-generated method stub

	}

	public void initFormDataFromDB(Object data) {
		// TODO Auto-generated method stub
		Object[] obj = (Object[]) data;
		handler.setForm((UFormI) obj[0]);

	}

	public void initFormDataFromSheet(Object data, int contentNum) {
		// TODO Auto-generated method stub

	}

	public void initNotEmptyComponents(HashMap requestMap, HashMap respondMap,
			int compNum) {
		// TODO Auto-generated method stub

	}

	public void initNotEmptyComponents(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		initAllComponents(requestMap, respondMap);
	}

	public void setData(Object obj, int compNum) {
		// TODO Auto-generated method stub

	}

	public void setWholeCount(int count) {
		// TODO Auto-generated method stub

	}

	public void initAllComponents(HashMap requestMap, HashMap respondMap) {
		for (int i = 0; i < contentTemplate.contentNum; i++) {
			UDElementI ei = (UDElementI) contentTemplate.contents[i].object;
			UParagraphContent c = (UParagraphContent) (contentTemplate.contents[i]);
			ei.setUParent(this);
			ei.initContent(c,requestMap,respondMap);
		}
	}

}
