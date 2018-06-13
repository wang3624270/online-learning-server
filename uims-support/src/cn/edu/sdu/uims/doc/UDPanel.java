package cn.edu.sdu.uims.doc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.sdu.spring_util.ApplicationContextHandle;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UContentTemplate;
import cn.edu.sdu.uims.def.UDocumentTemplate;
import cn.edu.sdu.uims.def.UParagraphContent;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UWORDUtil;

public class UDPanel extends UDocContentPanel implements UDElementI {
	private UDParagraph title = null;

	public UDPanel() {
		super();
	}

	public UDPanel(UContentTemplate contentTemplate) {
		super(contentTemplate);
	}

	public void setParameterData(HashMap map, UFormI form, UComponentI father) {
		if (title != null) {
			title.setParameterData(null, form, father);
		}
		super.setParameterData(map, form, father);
	}

	public void exContent(HashMap requestMap, HashMap respondMap, String type) {
		UDocumentTemplate template = (UDocumentTemplate) contentTemplate;

		UPaperTemplate paperTemplate = new UPaperTemplate();
		this.copyPaperTemplate(paperTemplate, template.paperTemplate);

		String orientation = (String) requestMap.get("orientation");
		if (orientation != null) {
			paperTemplate.orientation = Integer.parseInt(orientation);
		}
		UDocumentUtilBaseI docUtil = null;
		ByteArrayOutputStream aStream = new ByteArrayOutputStream();
		String fileName = "";
		File file = null;
		docUtil = (UDocumentUtilBaseI) respondMap.get("docUtil");
		UDocument doc = (UDocument) respondMap.get("doc");

		Integer currentCount = (Integer) respondMap.get("CURRENTCOUNT");
		if (docUtil == null) {
			if (type.equals("pdf")) {

//				docUtil = UPDFUtil.getInstance();
			} else if (type.equals("doc")) {
				docUtil = UWORDUtil.getInstance();
			}
			// fileName = docUtil.getNewTempFileName();
			try {
				if (requestMap.get("isPdfStream") == null)
					file = docUtil.getNewTempFile();
				respondMap.put("docUtil", docUtil);
				if (doc == null) {
					if (requestMap.get("isPdfStream") != null) {
						doc = docUtil.openDocumentStream(aStream,
								paperTemplate, template.pageNumberTemplate);
						respondMap.put("pdfStream", aStream);
					} else {
						doc = docUtil.openDocumentFile(file, paperTemplate,
								template.pageNumberTemplate);
					}
					doc.paraMap = new HashMap();
					doc.paraMap.put("font", requestMap.get("font"));
					respondMap.put("doc", doc);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			if (currentCount == null) {
				docUtil.newPage(doc);
			} else {
				if (currentCount % template.numPerPaper == 0)
					docUtil.newPage(doc);
			}

		}

		for (int i = 0; i < contentTemplate.contentNum; i++) {
			UDElementI ei = (UDElementI) contentTemplate.contents[i].object;
			UParagraphContent c = (UParagraphContent) (contentTemplate.contents[i]);
			ei.setParameterData(c.paras, (UFormI) handler.getForm(), null);
			if (c.newPage != null && c.newPage.equals("1"))
				docUtil.newPage(doc);
			ei.exDocument(docUtil, doc, c);
		}
	}

	public void initTitle() {
		UDocumentTemplate template = (UDocumentTemplate) contentTemplate;
		if (template.title != null) {
			title = new UDParagraph();
			title.setText(template.title.content);
			title.setColor(UFactory.getModelSession().getColorByName(
					template.title.frontColorName));
			title.setFont(UFactory.getModelSession().getFontByName(
					template.title.fontName));
		}

	}

	public UPaperTemplate getPaperAttribute() {
		return new UPaperTemplate();
		/*
		 * if (contentTemplate.paperName == null) { return new UPaperTemplate();
		 * } else { return (UPaperTemplate) UFactory.getTemplate(
		 * UConstants.MAPKEY_PAPER, panelTemplate.paperName); }
		 */
	}

	public void exDocument(UDocumentUtilBaseI util, UDocument doc,
			UParagraphContent constant) {
		// TODO Auto-generated method stub

	}

	public Object[] getDataItemById(HashMap requestMap, HashMap respondMap,
			Object dataId) {
		// TODO Auto-generated method stub
		Object[] dataObjects = null;

		Object businessrule = null;
		String beanId = contentTemplate.beanId;
		businessrule =  ApplicationContextHandle.getBean(beanId);

		String methodName = contentTemplate.methodName;

		try {
			Method method = businessrule.getClass().getDeclaredMethod(
					methodName, HashMap.class, HashMap.class, Object.class);
			dataObjects = (Object[]) method.invoke(businessrule, requestMap,
					respondMap, dataId);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataObjects;
	}

	public List getInitDataList(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		List initDataList = null;

		Object businessrule = null;
		String beanId = contentTemplate.beanId;
//		if (beanId == null) {
//			String ruleName = contentTemplate.ruleName;
//			businessrule = BusinessRuleFactory.getBusinessRule(ruleName);
//		} else {
			businessrule =  ApplicationContextHandle.getBean(beanId);
//		}

		String initMethodName = contentTemplate.initMethodName;

		try {
			Method method = businessrule.getClass().getDeclaredMethod(
					initMethodName, HashMap.class, HashMap.class);
			initDataList = (List) method.invoke(businessrule, requestMap,
					respondMap);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return initDataList;
	}

	public DataInitHandlerI getDataInitHandler(int compNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataInitHandlerI getDataInitHandler() {
		// TODO Auto-generated method stub
		DataInitHandlerI dataInitHandlerI = null;
		
		
		Object businessrule = null;
		String beanId = contentTemplate.beanId;
//		if (beanId == null) {
//			String ruleName = contentTemplate.ruleName;
//			businessrule = BusinessRuleFactory.getBusinessRule(ruleName);
//		} else {
			businessrule =  ApplicationContextHandle.getBean(beanId);
//		}
		
 
		String methodName = contentTemplate.methodName;
		String initMethodName = contentTemplate.initMethodName;
		try {
			if (contentTemplate.dataInitClassName != null) {
				dataInitHandlerI = (DataInitHandlerI) Class.forName(
						contentTemplate.dataInitClassName).newInstance();
				dataInitHandlerI.setComponent(this);
			}
		} catch (Exception e) {
			System.out.println("panelTemplate.handlerClassName ="
					+ contentTemplate.dataInitClassName);
			e.printStackTrace();
		}
		return dataInitHandlerI;
	}

	public void initContent(UParagraphContent constant, HashMap requestMap,
			HashMap respondMap) {
		// TODO Auto-generated method stub

	}

	public void copyPaperTemplate(UPaperTemplate target, UPaperTemplate source) {
		target.name = source.name;
		target.width = source.width;
		target.height = source.height;
		target.left = source.left;
		target.right = source.right;
		target.top = source.top;
		target.bottom = source.bottom;
		target.scale = source.scale;
		target.orientation = source.orientation;
		target.dpi = source.dpi;
	}
}
