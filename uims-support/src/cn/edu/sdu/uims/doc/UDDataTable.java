package cn.edu.sdu.uims.doc;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UContentDataTable;
import cn.edu.sdu.uims.def.UParagraphContent;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;

public class UDDataTable extends UContentDataTable implements UDElementI {
	/**
	 * 
	 */
	private UDParagraph title = null, note = null;

	public UDDataTable() {
		super();
	}

	public UDDataTable(UTableTemplate template) {
		super(template);
	}

	public void exDocument(UDocumentUtilBaseI util, UDocument doc,
			UParagraphContent content) {
		tableAttribute.horizontalAlignment = content.horizontalAlignment;
		if (items == null || items.length == 0)
			return;
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

	public void setParameterData(HashMap map, UFormI form, UComponentI obj) {
		if (title != null) {
			title.setParameterData(map, form, this);
		}
		if (note != null) {
			note.setParameterData(map, form, this);
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

	public void setTitleNoteObject() {
		if (tableTemplate.title != null) {
			title = new UDParagraph(tableTemplate.title.content);
		}
		if (tableTemplate.note != null) {
			note = new UDParagraph(tableTemplate.note.content);
		}
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

	public void initContent(UParagraphContent constant, HashMap requestMap,
			HashMap respondMap) {
		// TODO Auto-generated method stub
		initComponents();
	}

}
