package cn.edu.sdu.uims.sheet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.sdu.rmi.RmiKeyConstants;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.USheetParameter;
import cn.edu.sdu.uims.base.UContentElementI;
import cn.edu.sdu.uims.base.UDataContentPanel;
import cn.edu.sdu.uims.def.UBlockContent;
import cn.edu.sdu.uims.def.UContentTemplate;
import cn.edu.sdu.uims.def.USheetTemplate;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;
import cn.edu.sdu.uims.util.USheetUtil;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

public class USheetContentPanel extends UDataContentPanel implements
		USheetPanelI {
	protected USheetUtil sheetUtil = null;

	protected USheetParameter par = null;

	protected String fileName = null;

	public USheetContentPanel() {
		this(null);
	}

	public USheetContentPanel(UContentTemplate contentTemplate) {
		super(contentTemplate);
	}

	public void initFormDataFromSheet(Object data, int contentNum) {
		String attrName, methodName;
		USheetContentElementI elementI = null;
		Method method;
		Object items;
		UFormI dataForm = (UFormI) handler.getForm();
		if (dataForm != null)
			try {
				elementI = (USheetContentElementI) contentTemplate.contents[contentNum].object;
				items = elementI.initFormDataReFromSheet(data);
				attrName = contentTemplate.contents[contentNum].dataFormMember;
				if (elementI != null && attrName != null
						&& !attrName.equals("")) {
					methodName = "set" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					method = dataForm.getClass().getMethod(methodName,
							Object.class);
					method.invoke(dataForm, items);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void flush(int contNum) {
		// TODO Auto-generated method stub

	}

	public DataInitHandlerI getDataInitHandler(int contentNum) {
		// TODO Auto-generated method stub
		USheetContentElementI elementI = null;
		elementI = (USheetContentElementI) contentTemplate.contents[contentNum].object;
		return elementI.getDataInitHandler();
	}

	public void initFormDataFromDB(Object data, int contentNum) {
		// TODO Auto-generated method stub
		String attrName, methodName;
		USheetContentElementI elementI = null;
		Method method;
		Object items;
		UFormI dataForm = (UFormI) handler.getForm();
		if (dataForm != null)
			try {
				elementI = (USheetContentElementI) contentTemplate.contents[contentNum].object;
				items = elementI.initFormDataReFromDB(data);
				attrName = contentTemplate.contents[contentNum].dataFormMember;
				if (elementI != null && attrName != null
						&& !attrName.equals("")) {
					methodName = "set" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					method = dataForm.getClass().getMethod(methodName,
							Object.class);
					method.invoke(dataForm, items);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void setData(Object obj, int compNum) {
		// TODO Auto-generated method stub
		String attrName, methodName;
		UContentElementI elementI = null;
		Method method;
		UFormI dataForm = (UFormI) obj;
		if (dataForm != null) {
			try {
				elementI = contentTemplate.contents[compNum].object;
				attrName = contentTemplate.contents[compNum].dataFormMember;
				if (elementI != null && attrName != null
						&& !attrName.equals("")) {
					methodName = "get" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					method = dataForm.getClass().getMethod(methodName);
					elementI.setData(method.invoke(dataForm));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void initComponents(int compNum) {
		// TODO Auto-generated method stub
		contentTemplate.contents[compNum].object.init();
	}

	public void exContent(int compNum) {

		USElementI ei = (USElementI) contentTemplate.contents[compNum].object;
		UBlockContent c = (UBlockContent) (contentTemplate.contents[compNum]);
		ei.exSheet(sheetUtil, par, c);
	}

	public void init() {
		initHandlerObject(contentTemplate.name, null);
	}

	public void openSheetUtil() {
		if (sheetUtil == null) {
			sheetUtil = USheetUtil.getInstance();
			fileName = sheetUtil.getNewTempFileName();
			par = sheetUtil.openSheetFile(fileName);
			par.row = 0;
			par.col = 0;
		}
	}

	public void openSheetUtil(List<Integer> columnWidths, String tableHead) {
		if (sheetUtil == null) {
			sheetUtil = USheetUtil.getInstance();
			fileName = sheetUtil.getNewTempFileName();
			par = sheetUtil.openSheetFile(fileName);

			int i;
			for (i = 0; i < columnWidths.size(); i++) {
				par.sheet.setColumnView(i, columnWidths.get(i) / 9);
			}
			try {
				if (tableHead != null) {
					WritableFont font1 = new WritableFont(WritableFont.ARIAL,
							20, WritableFont.NO_BOLD);

					WritableCellFormat format = new WritableCellFormat(font1);
					format.setAlignment(jxl.format.Alignment.CENTRE);
					Label label = new Label(0, 0, tableHead, format);
					// 将定义好的单元格添加到工作表中
					par.sheet.addCell(label);
					int width = 0;
					if (columnWidths.size() > 0) {
						width = columnWidths.size() - 1;
					}
					par.sheet.mergeCells(0, 0, width, 0);
					par.row = 1;
					par.col = 0;
				} else {
					par.row = 0;
					par.col = 0;
				}
			} catch (Exception e) {
			}

		}
	}

	public byte[] closeSheetUtil() {
		byte[] b = null;
		if (sheetUtil != null) {
			sheetUtil.close(par);
			b = sheetUtil.getSheetFileBytes(fileName);
			sheetUtil = null;
			par = null;
		}
		return b;
	}

	public void imContent(HashMap requestMap, HashMap respondMap, int compNum) {
		String attrName, methodName;
		USheetContentElementI elementI = null;
		Method method;
		UFormI[] items;
		HashMap retMap = null;
		UFormI dataForm = (UFormI) handler.getForm();
		USheetTemplate template = (USheetTemplate) contentTemplate;
		contentTemplate = (UContentTemplate) template;
		elementI = (USheetContentElementI) contentTemplate.contents[compNum].object;
		attrName = contentTemplate.contents[compNum].dataFormMember;
		if (elementI != null && attrName != null && !attrName.equals("")) {
			methodName = "get" + attrName.substring(0, 1).toUpperCase()
					+ attrName.substring(1, attrName.length());

			try {
				method = dataForm.getClass().getMethod(methodName);
				items = (UFormI[]) method.invoke(dataForm);
				elementI.imContent(requestMap, respondMap, items);

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

		}
	}

	public void setWholeCount(int count) {
		// TODO Auto-generated method stub

	}

	public void dataOperateEnd(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		byte[] b = closeSheetUtil();
		respondMap.put(RmiKeyConstants.KEY_BYTES, b);
		Object responseObj = requestMap.get(RmiKeyConstants.HTTPSERVLETRESPONSE);
		String doctype = (String) requestMap.get("doctype");
		String downloadFileName = (String) requestMap.get("fileName");
		if (responseObj != null) {
			HttpServletResponse response = (HttpServletResponse) responseObj;
			String contentType = "application" + "\\" + doctype;
			String header = "attachment;filename=" + downloadFileName;
			response.setContentType(contentType);
			response.setHeader("content-disposition", header);
			ServletOutputStream outputStream;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(b);
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void dataOperateBegin(HashMap requestMap, HashMap respondMap) {
		List<Integer> columnWidths = new ArrayList();
		String tableHead = "";
		if (this.contentTemplate.contents[0].template instanceof UTableTemplate) {
			UTableTemplate temp = (UTableTemplate) this.contentTemplate.contents[0].template;
			tableHead = temp.tableHead;
			if (temp.no != null)
				columnWidths.add(temp.no.width);

			for (int i = 0; i < temp.columnNum; i++) {
				columnWidths.add(temp.columnTemplates[i].width);
			}

		}
		// this.contentTemplate.contents[0].template
		openSheetUtil(columnWidths, tableHead);
	}

	public void initEmptyComponents(HashMap requestMap, HashMap respondMap,
			int compNum) {
		// TODO Auto-generated method stub
		USheetContentElementI elementI = null;
		elementI = (USheetContentElementI) contentTemplate.contents[compNum].object;
		elementI.initEmpytComponent(requestMap, respondMap);
	}

	public void initNotEmptyComponents(HashMap requestMap, HashMap respondMap,
			int compNum) {
		// TODO Auto-generated method stub
		USheetContentElementI elementI = null;
		elementI = (USheetContentElementI) contentTemplate.contents[compNum].object;
		elementI.initNotEmpytComponent(requestMap, respondMap);
	}

	public void exContent() {
		// TODO Auto-generated method stub

	}

	public void initEmptyComponents(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub

	}

	public void initNotEmptyComponents(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub

	}

	public void initFormDataFromDB(Object data) {
		// TODO Auto-generated method stub

	}
}
