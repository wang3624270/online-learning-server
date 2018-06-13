package cn.edu.sdu.uims.doc;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UContentRadomTable;
import cn.edu.sdu.uims.def.UParagraphContent;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;
import cn.edu.sdu.uims.util.UTextUtil;

public class UDRandomTable extends UContentRadomTable implements UDElementI {

	private UDParagraph title = null, note = null;

	public void exDocument(UDocumentUtilBaseI util, UDocument doc,
			UParagraphContent constant) {
		// TODO Auto-generated method stub
		tableAttribute.horizontalAlignment = constant.horizontalAlignment;
		if (title != null) {
			title.exDocument(util, doc,
					getParagraphTemplateByCellAttribute(tableTemplate.title));
		}
		try {
			util.addTable(doc, tableAttribute, data, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (note != null) {
			note.exDocument(util, doc,
					getParagraphTemplateByCellAttribute(tableTemplate.note));
		}
	}

	private UParagraphTemplate getParagraphTemplateByCellAttribute(
			UCellAttribute cell) {
		UParagraphTemplate temp = new UParagraphTemplate();
		temp.horizontalAlignment = cell.horizontalAlignment;
		temp.verticalAlignment = cell.verticalAlignment;
		temp.colorName = cell.frontColorName;
		temp.fontName = cell.fontName;
		return temp;

	}

	public DataInitHandlerI getDataInitHandler(int compNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataInitHandlerI getDataInitHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getDataItemById(HashMap requestMap, HashMap respondMap,
			Object dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getInitDataList(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParameterData(HashMap map, UFormI form, UComponentI obj) {
		// TODO Auto-generated method stub
		if (title != null) {
			title.setParameterData(map, form, this);
		}
		int i;
		UCellAttribute cell;
		String text;
		if (data != null) {
			for (i = 0; i < data.length; i++) {
				cell = data[i];
				text = cell.content;
				if (text != null) {
					text = UTextUtil.replaceString(text, map, form, obj);
					cell.content = text;
				}
			}
		}
		if (note != null) {
			note.setParameterData(map, form, this);
		}

	}

	public void setTitleNoteObject() {
		if (tableTemplate.title != null) {
			title = new UDParagraph(tableTemplate.title.content);
		}
		if (tableTemplate.note != null) {
			note = new UDParagraph(tableTemplate.note.content);
		}
	}

	public void initContent(UParagraphContent constant, HashMap requestMap,
			HashMap respondMap) {
		// TODO Auto-generated method stub
		initComponents();
	}
}
