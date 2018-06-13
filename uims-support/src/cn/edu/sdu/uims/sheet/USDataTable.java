package cn.edu.sdu.uims.sheet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.sdu.spring_util.ApplicationContextHandle;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.USheetParameter;
import cn.edu.sdu.common.reportdog.UTableAttribute;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UContentDataTable;
import cn.edu.sdu.uims.def.UBlockContent;
import cn.edu.sdu.uims.def.UColumnTemplate;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.handler.impl.DataInitHandlerI;
import cn.edu.sdu.uims.util.USheetUtil;

public class USDataTable extends UContentDataTable implements USElementI {
	private UCellAttribute title = null, note = null;

	private boolean isStart = true;

	private boolean isEnd = false;

	private int exCount = 0;

	private int wholeCount = 0;

	public USDataTable() {
		super();
	}

	public USDataTable(UTableTemplate template) {
		super(template);
	}

	public void setTitleNoteObject() {
		if (tableTemplate.title != null) {
			title = new UCellAttribute();
			title.row = 0;
			title.col = 0;
			title.colSpan = getCellColumnNum();
			title.rowSpan = 1;
			title.content = tableTemplate.title.content;
		}
		if (tableTemplate.note != null) {
			note = new UCellAttribute();
			note.row = 0;
			note.col = 0;
			note.colSpan = getCellColumnNum();
			note.rowSpan = 1;
			note.content = tableTemplate.title.content;
		}

	}

	public void exSheet(USheetUtil util, USheetParameter par,
			UBlockContent constant) {
		UCellAttribute[] dds = new UCellAttribute[1];
		try {
			if (title != null && isStart) {
				dds[0] = title;
				util.addCells(par, dds);
				par.row++;
			}
			par.columnNum = this.getCellColumnNum();
			util.addCells(par, data);
			par.row += getRowNum() + getTopRowNum() + getBottomRowNum();
			if (note != null && isEnd) {
				dds[0] = note;
				util.addCells(par, dds);
				par.row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public void imSheet(Object[] data) {
		// TODO Auto-generated method stub
		Object[] line;
		String s, methodName;
		Method method;
		Object dataForm;
		if (data != null && data.length > 0) {
			line = (Object[]) data[0];
			if (line != null && line.length > 0) {
				int j;
				String itemFormClassName = tableTemplate.itemFormClassName;
				try {
					if (itemFormClassName != null) {
						dataForm = Class.forName(itemFormClassName)
								.newInstance();
						for (j = 0; j < line.length; j++) {
							s = tableTemplate.columnTemplates[j].itemFormMember;
							if (s != null) {
								methodName = "set"
										+ s.substring(0, 1).toUpperCase()
										+ s.substring(1, s.length());
								method = dataForm.getClass().getMethod(
										methodName, Object.class);
								method.invoke(dataForm, line[j]);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public UFormI[] initFormDataReFromDB(Object data) {
		Object[] datas;
		UFormI[] forms = null;
		int i, size;
		if (data != null) {
			datas = (Object[]) data;
			size = datas.length;
			if (size > 0) {
				forms = new UFormI[size];
				for (i = 0; i < size; i++) {
					forms[i] = (UFormI) datas[i];
				}
			}
		}
		return forms;
	}

	public UFormI[] initFormDataReFromSheet(Object data) {
		// TODO Auto-generated method stub
		Object[] datas;
		UFormI[] forms = null;
		HashMap line;
		int lineSize;
		String s, methodName;
		Method method;
		int i, size;
		Set keySet;
		Iterator keyIt;
		Object key;
		Object mapData;
		if (data != null) {
			datas = (Object[]) data;
			size = datas.length;
			if (size > 0) {
				for (i = 0; i < size; i++) {
					forms = new UFormI[size];
					line = (HashMap) datas[i];

					if (line != null && line.size() > 0) {
						lineSize = line.size();
						String itemFormClassName = tableTemplate.itemFormClassName;
						try {
							if (itemFormClassName != null) {
								forms[i] = (UFormI) Class.forName(
										itemFormClassName).newInstance();
								// for (j = 1; j < lineSize; j++) {
								keySet = line.keySet();
								keyIt = keySet.iterator();
								while (keyIt.hasNext()) {
									key = keyIt.next();
									s = this.getItemFormMember(key);
									if (s != null) {
										methodName = "set"
												+ s.substring(0, 1)
														.toUpperCase()
												+ s.substring(1, s.length());
										method = forms[i].getClass().getMethod(
												methodName, Object.class);
										mapData = line.get(key);
										method.invoke(forms[i], mapData);
									}
									// }
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return forms;
	}

	public String getItemFormMember(Object name) {
		String itemFormMember = null;
		String[] topStrings = tableTemplate.topStrings;
		String topString;
		UColumnTemplate[] columnTemplates = tableTemplate.columnTemplates;
		UColumnTemplate columnTemplate;
		if (columnTemplates != null && columnTemplates.length > 0) {
			int i;
			for (i = 0; i < columnTemplates.length; i++) {
				topString = topStrings[i];
				if (topString.trim().equals(name.toString().trim())) {
					columnTemplate = columnTemplates[i];
					itemFormMember = columnTemplate.itemFormMember;
					return itemFormMember;
				}
			}
		}
		return itemFormMember;
	}

	public void imContent(HashMap requestMap, HashMap respondMap, UFormI[] items) {
		HashMap retMap = new HashMap();
		if (items != null && items.length > 0) {
			int i;
			Object data;
			String ruleName = tableTemplate.ruleName;
			String methodName = tableTemplate.methodName;
			String beanId = tableTemplate.beanId;
			Object businessrule = null;
			businessrule = ApplicationContextHandle.getBean(beanId);
			try {
				Method method = businessrule.getClass().getDeclaredMethod(
						methodName, Object.class, HashMap.class, HashMap.class);

				for (i = 0; i < items.length; i++) {
					data = items[i];
					method.invoke(businessrule, data, requestMap, respondMap);
				}
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

	public DataInitHandlerI getDataInitHandler() {
		// TODO Auto-generated method stub
		DataInitHandlerI dataInitHandlerI = null;
		// HashMap initMap = null;
		// String ruleName = tableTemplate.ruleName;
		// String methodName = tableTemplate.methodName;
		// String initMethodName = tableTemplate.initMethodName;
		try {
			if (tableTemplate.dataInitClassName != null) {
				dataInitHandlerI = (DataInitHandlerI) Class.forName(
						tableTemplate.dataInitClassName).newInstance();
				dataInitHandlerI.setComponent(this);
			}
			// if (dataInitHandlerI != null) {
			// initMap = new HashMap();
			// if (ruleName != null) {
			// initMap.put("ruleName", ruleName);
			// }
			// if (methodName != null) {
			// initMap.put("methodName", methodName);
			// }
			// if (initMethodName != null) {
			// initMap.put("initMethodName", initMethodName);
			// }
			// dataInitHandlerI.setOperateParaMap(initMap);
			// }
		} catch (Exception e) {
			System.out.println("panelTemplate.handlerClassName ="
					+ tableTemplate.dataInitClassName);
			e.printStackTrace();
		}
		return dataInitHandlerI;
	}

	public void flush() {
		// TODO Auto-generated method stub
		String ruleName = tableTemplate.ruleName;
		String methodName = "flushAllData";
		Object businessrule = ApplicationContextHandle.getBean(ruleName);
		try {
			Method method = businessrule.getClass().getDeclaredMethod(
					methodName, Object.class);
			method.invoke(businessrule);
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

	public void flush(int contNum) {
		// TODO Auto-generated method stub

	}

	public DataInitHandlerI getDataInitHandler(int compNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getInitDataList(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		List initDataList = null;
		String ruleName = tableTemplate.ruleName;
		String initMethodName = tableTemplate.initMethodName;
		Object businessrule = ApplicationContextHandle.getBean(ruleName);
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
		exCount = 0;
		this.isStart = true;
		this.isEnd = false;
		if (initDataList != null) {
			wholeCount = initDataList.size();
		}
		return initDataList;
	}

	public Object[] getDataItemById(HashMap requestMap, HashMap respondMap,
			Object dataId) {
		// TODO Auto-generated method stub
		Object[] dataObjects = null;
		String ruleName = tableTemplate.ruleName;
		String methodName = tableTemplate.methodName;
		Object businessrule = ApplicationContextHandle.getBean(ruleName);
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

	public void initComponents() {
		int i, j, k, n, nt, ccn, sccn, ct = 0;
		String s, methodName;
		UCellAttribute obj;
		Method method;
		UFormI form;
		Object temp;
		if (items != null) {
			if (exCount + items.length >= this.wholeCount) {
				isEnd = true;
			}
		} else {
			isEnd = true;
		}
		initCellObjects();
		setTitleNoteObject();
		ccn = getCellColumnNum();
		sccn = ccn / tableTemplate.subTableNum;
		n = tableTemplate.columnNum;
		nt = 0;
		if (tableTemplate.no != null)
			nt++;
		int rowNum = getRowNum();
		int tr = getTopRowNum();
		int br = this.getBottomRowNum();

		tableAttribute = new UTableAttribute();
		tableAttribute.colNum = ccn;
		tableAttribute.width = new float[ccn];

		for (k = 0; k < tableTemplate.subTableNum; k++) {
			if (tableTemplate.no != null) {
				ct = k * sccn;
				tableAttribute.width[ct] = tableTemplate.no.width;
			}
			for (i = 0; i < n; i++) {
				ct = k * sccn + i + nt;
				tableAttribute.width[ct] = tableTemplate.columnTemplates[i].width;
			}
		}
		// if (tableTemplate.topNum >= 1) {
		if (tr > 0) {
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				if (tableTemplate.no != null) {
					ct = k * sccn;
					data[ct] = new UCellAttribute("序号");
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, 0, 1, 1);
				}
				for (i = 0; i < n; i++) {
					ct = k * sccn + i + nt;
					data[ct] = new UCellAttribute(tableTemplate.topStrings[i]);
					data[ct].fontName = tableTemplate.columnTemplates[i].fontName;
					setCellRowCol(data[ct], k * sccn + i + nt, 0, 1, 1);
				}
			}
		}
		if (items != null)
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				for (i = 0; i < rowNum; i++) {
					if ((k * rowNum + i) >= items.length) {
						if (tableTemplate.no != null) {
							ct = (i + tr) * ccn + k * sccn;
							data[ct] = new UCellAttribute();
							setCellRowCol(data[ct], k * sccn, (i + tr), 1, 1);
						}
						for (j = 0; j < n; j++) {
							ct = (i + tr) * ccn + k * sccn + nt + j;
							data[ct] = new UCellAttribute("");
							setCellRowCol(data[ct], k * sccn + nt + j, i + tr,
									1, 1);
						}
						continue;
					}
					form = (UFormI) items[k * rowNum + i];
					if (tableTemplate.no != null) {
						ct = (i + tr) * ccn + k * sccn;
						data[ct] = new UCellAttribute(""
								+ (k * rowNum + i + 1 + exCount));
						data[ct].fontName = tableTemplate.no.fontName;
						setCellRowCol(data[ct], k * sccn, i + tr + exCount, 1,
								1);
					}
					for (j = 0; j < n; j++) {
						s = tableTemplate.columnTemplates[j].itemFormMember;
						obj = new UCellAttribute();
						if (s != null) {
							try {
								methodName = "get"
										+ s.substring(0, 1).toUpperCase()
										+ s.substring(1, s.length());
								method = form.getClass().getMethod(methodName);
								temp = method.invoke(form);
								if (temp == null)
									obj.content = "";
								else
									obj.content = temp.toString();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						ct = (i + tr) * ccn + k * sccn + nt + j;
						data[ct] = obj;
						data[ct].fontName = tableTemplate.columnTemplates[j].fontName;
						setCellRowCol(data[ct], k * sccn + nt + j, i + tr
								+ exCount, 1, 1);
					}
				}
			}
		// if (tableTemplate.bottomNum >= 1) {
		if (br > 0) {
			for (k = 0; k < tableTemplate.subTableNum; i++) {
				if (tableTemplate.no != null) {
					ct = (rowNum + tr) * ccn + k * sccn;
					data[ct] = new UCellAttribute("");
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, rowNum + tr, 1, 1);
				}
				for (i = 0; i < n; i++) {
					ct = (rowNum + tr) * ccn + k * sccn + i + nt;
					data[ct] = new UCellAttribute(
							tableTemplate.bottomStrings[i]);
					data[ct].fontName = tableTemplate.columnTemplates[i].fontName;
					setCellRowCol(data[ct], k * sccn + i * nt, rowNum + tr
							+ exCount, 1, 1);
				}
			}
		}
		if (items != null) {
			exCount += items.length;
		}
		isStart = false;
	}

	public int getTopRowNum() {
		if (tableTemplate != null && tableTemplate.topNum >= 1 && isStart) {
			return 1;
		} else
			return 0;
	}

	public int getBottomRowNum() {
		if (tableTemplate != null && tableTemplate.bottomNum >= 1 && isEnd) {
			return 1;
		} else
			return 0;
	}

	public void setWholeCount(int wholeCount) {
		this.wholeCount = wholeCount;
	}

	public void initEmpytComponent(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		Object selectObjects = requestMap.get("selectObjects");
		if (selectObjects != null) {
			List selectList = (List) selectObjects;
			initSelectComponents(selectList);
		} else {
			this.items = new UFormI[0];
			initAllComponents();
		}
	}

	public void initNotEmpytComponent(HashMap requestMap, HashMap respondMap) {
		// TODO Auto-generated method stub
		Object selectObjects = requestMap.get("selectObjects");
		if (selectObjects != null) {
			List selectList = (List) selectObjects;
			initSelectComponents(selectList);
		} else {
			initAllComponents();
		}
	}

	public void initAllComponents() {
		int i, j, k, n, nt, ccn, sccn, ct = 0;
		String s, methodName;
		UCellAttribute obj;
		Method method;
		UFormI form;
		Object temp;
		if (items != null) {
			if (exCount + items.length >= this.wholeCount) {
				isEnd = true;
			}
		} else {
			isEnd = true;
		}
		initCellObjects();
		setTitleNoteObject();
		ccn = getCellColumnNum();
		sccn = ccn / tableTemplate.subTableNum;
		n = tableTemplate.columnNum;
		nt = 0;
		if (tableTemplate.no != null)
			nt++;
		int rowNum = getRowNum();
		int tr = getTopRowNum();
		int br = this.getBottomRowNum();

		tableAttribute = new UTableAttribute();
		tableAttribute.colNum = ccn;
		tableAttribute.width = new float[ccn];

		for (k = 0; k < tableTemplate.subTableNum; k++) {
			if (tableTemplate.no != null) {
				ct = k * sccn;
				tableAttribute.width[ct] = tableTemplate.no.width;
			}
			for (i = 0; i < n; i++) {
				ct = k * sccn + i + nt;
				tableAttribute.width[ct] = tableTemplate.columnTemplates[i].width;
			}
		}
		// if (tableTemplate.topNum >= 1) {
		if (tr > 0) {
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				if (tableTemplate.no != null) {
					ct = k * sccn;
					data[ct] = new UCellAttribute("序号");
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, 0, 1, 1);
				}
				for (i = 0; i < n; i++) {
					ct = k * sccn + i + nt;
					data[ct] = new UCellAttribute(tableTemplate.topStrings[i]);
					data[ct].fontName = tableTemplate.columnTemplates[i].fontName;
					setCellRowCol(data[ct], k * sccn + i + nt, 0, 1, 1);
				}
			}
		}
		if (items != null) {
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				for (i = 0; i < rowNum; i++) {
					if ((k * rowNum + i) >= items.length) {
						if (tableTemplate.no != null) {
							ct = (i + tr) * ccn + k * sccn;
							data[ct] = new UCellAttribute();
							setCellRowCol(data[ct], k * sccn, (i + tr), 1, 1);
						}
						for (j = 0; j < n; j++) {
							ct = (i + tr) * ccn + k * sccn + nt + j;
							data[ct] = new UCellAttribute("");
							setCellRowCol(data[ct], k * sccn + nt + j, i + tr,
									1, 1);
						}
						continue;
					}
					form = (UFormI) items[k * rowNum + i];
					if (tableTemplate.no != null) {
						ct = (i + tr) * ccn + k * sccn;
						data[ct] = new UCellAttribute(""
								+ (k * rowNum + i + 1 + exCount));
						data[ct].fontName = tableTemplate.no.fontName;
						setCellRowCol(data[ct], k * sccn, i
								+ tableTemplate.topNum, 1, 1);
					}
					for (j = 0; j < n; j++) {
						s = tableTemplate.columnTemplates[j].itemFormMember;
						obj = new UCellAttribute();
						if (s != null) {
							try {
								methodName = "get"
										+ s.substring(0, 1).toUpperCase()
										+ s.substring(1, s.length());
								method = form.getClass().getMethod(methodName);
								temp = method.invoke(form);
								if (temp == null)
									obj.content = "";
								else
									obj.content = temp.toString();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						ct = (i + tr) * ccn + k * sccn + nt + j;
						data[ct] = obj;
						data[ct].fontName = tableTemplate.columnTemplates[j].fontName;
						// if(tr>0)
						// setCellRowCol(data[ct], k * sccn + nt + j, i + tr
						// , 1, 1);
						// else
						// setCellRowCol(data[ct], k * sccn + nt + j, i + tr+1
						// , 1, 1);
						setCellRowCol(data[ct], k * sccn + nt + j, i
								+ tableTemplate.topNum, 1, 1);
					}
				}
			}
			exCount += items.length;
		}
		// if (tableTemplate.bottomNum >= 1) {
		if (br > 0) {
			for (k = 0; k < tableTemplate.subTableNum; i++) {
				if (tableTemplate.no != null) {
					ct = (rowNum + tr) * ccn + k * sccn;
					data[ct] = new UCellAttribute("");
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, rowNum + tr, 1, 1);
				}
				for (i = 0; i < n; i++) {
					ct = (rowNum + tr) * ccn + k * sccn + i + nt;
					data[ct] = new UCellAttribute(
							tableTemplate.bottomStrings[i]);
					data[ct].fontName = tableTemplate.columnTemplates[i].fontName;
					setCellRowCol(data[ct], k * sccn + i * nt, rowNum + tr, 1,
							1);
				}
			}
		}
		isStart = false;
	}

	public void initSelectComponents(List selectList) {
		int i, j, k, n, nt, ccn, sccn, ct = 0;
		int sn = 0;
		String s, methodName, topItemName;
		UCellAttribute obj;
		Method method;
		UFormI form;
		Object temp;
		int size = selectList.size();
		if (items != null) {
			if (exCount + items.length >= this.wholeCount) {
				isEnd = true;
			}
		} else {
			isEnd = true;
		}
		initCellObjects(size);
		setTitleNoteObject();
		ccn = getCellColumnNum(size);
		sccn = ccn / tableTemplate.subTableNum;
		n = tableTemplate.columnNum;
		nt = 0;
		if (tableTemplate.no != null)
			nt++;
		int rowNum = getRowNum();
		int tr = getTopRowNum();
		int br = this.getBottomRowNum();

		tableAttribute = new UTableAttribute();
		tableAttribute.colNum = ccn;
		tableAttribute.width = new float[ccn];

		for (k = 0; k < tableTemplate.subTableNum; k++) {
			if (tableTemplate.no != null) {
				ct = k * sccn;
				tableAttribute.width[ct] = tableTemplate.no.width;
			}
			sn = 0;
			for (i = 0; i < n; i++) {
				topItemName = tableTemplate.topStrings[i];
				if (this.isInList(selectList, topItemName)) {

					ct = k * sccn + sn + nt;
					tableAttribute.width[ct] = tableTemplate.columnTemplates[i].width;
					sn++;
				}
			}
		}
		// if (tableTemplate.topNum >= 1) {
		if (tr > 0) {
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				if (tableTemplate.no != null) {
					ct = k * sccn;
					data[ct] = new UCellAttribute("序号");
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, 0, 1, 1);
				}
				sn = 0;
				for (i = 0; i < n; i++) {
					topItemName = tableTemplate.topStrings[i];
					if (this.isInList(selectList, topItemName)) {
						ct = k * sccn + sn + nt;
						data[ct] = new UCellAttribute(
								tableTemplate.topStrings[i]);
						data[ct].fontName = tableTemplate.columnTemplates[i].fontName;
						setCellRowCol(data[ct], k * sccn + sn + nt, 0, 1, 1);
						sn++;
					}
				}
			}
		}
		if (items != null)
			for (k = 0; k < tableTemplate.subTableNum; k++) {
				for (i = 0; i < rowNum; i++) {
					if ((k * rowNum + i) >= items.length) {
						if (tableTemplate.no != null) {
							ct = (i + tr) * ccn + k * sccn;
							data[ct] = new UCellAttribute();
							setCellRowCol(data[ct], k * sccn, (i + tr), 1, 1);
						}
						for (j = 0; j < n; j++) {
							ct = (i + tr) * ccn + k * sccn + nt + j;
							data[ct] = new UCellAttribute("");
							setCellRowCol(data[ct], k * sccn + nt + j, i + tr,
									1, 1);
						}
						continue;
					}
					form = (UFormI) items[k * rowNum + i];
					if (tableTemplate.no != null) {
						ct = (i + tr) * ccn + k * sccn;
						data[ct] = new UCellAttribute(""
								+ (k * rowNum + i + 1 + exCount));
						data[ct].fontName = tableTemplate.no.fontName;
						setCellRowCol(data[ct], k * sccn, i + tr + exCount, 1,
								1);
					}
					sn = 0;
					for (j = 0; j < n; j++) {
						s = tableTemplate.columnTemplates[j].itemFormMember;
						topItemName = tableTemplate.topStrings[j];
						if (this.isInList(selectList, topItemName)) {
							obj = new UCellAttribute();
							if (s != null) {
								try {
									methodName = "get"
											+ s.substring(0, 1).toUpperCase()
											+ s.substring(1, s.length());
									method = form.getClass().getMethod(
											methodName);
									temp = method.invoke(form);
									if (temp == null)
										obj.content = "";
									else
										obj.content = temp.toString();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							ct = (i + tr) * ccn + k * sccn + nt + sn;
							data[ct] = obj;
							data[ct].fontName = tableTemplate.columnTemplates[j].fontName;
							setCellRowCol(data[ct], k * sccn + nt + sn, i + tr
									+ exCount, 1, 1);
							sn++;
						}
					}
				}
			}
		// if (tableTemplate.bottomNum >= 1) {
		if (br > 0) {
			for (k = 0; k < tableTemplate.subTableNum; i++) {
				if (tableTemplate.no != null) {
					ct = (rowNum + tr) * ccn + k * sccn;
					data[ct] = new UCellAttribute("");
					data[ct].fontName = tableTemplate.no.fontName;
					setCellRowCol(data[ct], k * sccn, rowNum + tr, 1, 1);
				}
				sn = 0;
				for (i = 0; i < n; i++) {
					ct = (rowNum + tr) * ccn + k * sccn + sn + nt;
					data[ct] = new UCellAttribute(
							tableTemplate.bottomStrings[i]);
					data[ct].fontName = tableTemplate.columnTemplates[i].fontName;
					setCellRowCol(data[ct], k * sccn + sn * nt, rowNum + tr
							+ exCount, 1, 1);
					sn++;
				}
			}
		}
		if (items != null) {
			exCount += items.length;
		}
		isStart = false;
	}

	public void initCellObjects(int size) {
		int n = getBodyCellNum(size);
		data = new UCellAttribute[n];
	}

	public int getBodyCellNum(int size) {
		return (getRowNum() + getTopRowNum() + getBottomRowNum())
				* getCellColumnNum(size);
	}

	public int getCellColumnNum(int size) {
		int n = size;
		if (tableTemplate.no != null)
			n++;
		n *= tableTemplate.subTableNum;
		return n;
	}

	public boolean isInList(List list, String value) {
		if (list != null && list.size() > 0) {
			int i;
			for (i = 0; i < list.size(); i++) {
				if (value.equals(list.get(i))) {
					return true;
				}
			}
		}
		return false;
	}
}
