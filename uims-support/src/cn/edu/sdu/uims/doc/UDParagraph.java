package cn.edu.sdu.uims.doc;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UContentElementI;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UParagraphContent;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;
import cn.edu.sdu.uims.util.UTextUtil;
import cn.edu.sdu.uims.view.UVElement;

public class UDParagraph extends UVElement implements UDElementI {

	public UDParagraph() {
		this(null);
	}

	public UDParagraph(String text) {
		this.text = text;
	}

	public void setParameterData(HashMap map, UFormI form, UContentElementI obj) {
		text = UTextUtil.replaceString(text, map, form, obj);
	}

	public void exDocument(UDocumentUtilBaseI util, UDocument doc,
			UParagraphTemplate template) {
		String s = "";
		String ss = null;
		int i;
		for (i = 0; i < template.firstSpace; i++)
			s += "　";
		// // 对段落字体进行设置，修改者 刘洋
		// UFont defautlFont = template.font;
		// List list = ParagraphStyleUtility.parseSyte(text, defautlFont.name);
		// String[] strs;
		// UFont dataFont;
		// if (list != null && list.size() > 0) {
		// for (i = 0; i < list.size(); i++) {
		// strs = (String[]) list.get(i);
		// dataFont = UFactory.getFontByName(strs[1]);
		// // 写入内容
		// StringTokenizer st = new StringTokenizer(strs[0], "\n");
		// while (st.hasMoreTokens()) {
		// ss = st.nextToken();
		// util.addContent(doc, s + ss, font);
		// }
		// }
		// }
		// 设置结束 修改者刘洋
		StringTokenizer st = new StringTokenizer(text, "\n");
		while (st.hasMoreTokens()) {
			ss = st.nextToken();
			util.addParagraph(doc, s + ss, template);
		}
	}

	public void exDocument(UDocumentUtilBaseI util, UDocument doc,
			UParagraphContent content) {
		UParagraphTemplate t = (UParagraphTemplate) content.template;
		exDocument(util, doc, t);
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
		this.text = constant.text;

	}

	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}


	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserData(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}



}
