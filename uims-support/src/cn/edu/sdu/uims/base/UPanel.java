package cn.edu.sdu.uims.base;

import java.awt.Component
;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.button.UButtonFold;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.complex.UQueryComplexBasePanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.UEventAdaptor;
import cn.edu.sdu.uims.component.event.UEventListener;
import cn.edu.sdu.uims.component.event.UEventObject;
import cn.edu.sdu.uims.component.groupcomponent.UGroupComponentI;
import cn.edu.sdu.uims.component.groupcomponent.UGroupComponentMuti;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UMenuItem;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.component.panel.UPanelTimerControlActionThread;
import cn.edu.sdu.uims.component.panel.UPanelTimerThread;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UFilterTemplate;
import cn.edu.sdu.uims.def.UGroupElementTemplate;
import cn.edu.sdu.uims.def.UMenuPanelMapTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.flex.FHashMapUtil;
import cn.edu.sdu.uims.flex.FNameObjectPar;
import cn.edu.sdu.uims.form.UPanelStatusFormI;
import cn.edu.sdu.uims.form.UTextFieldDataFormI;
import cn.edu.sdu.uims.form.impl.RoleControlACtionItemForm;
import cn.edu.sdu.uims.form.impl.TimeControlActionItemForm;
import cn.edu.sdu.uims.handler.UFormHandlerI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.handler.impl.UPageOwnerHandler;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.service.UHandlerSessionI;
import cn.edu.sdu.uims.service.UModelSessionI;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UTextUtil;
import cn.edu.sdu.uims.util.UimsUtils;
import cn.edu.sdu.uims.validator.DataValidatorI;

public class UPanel extends UComponentAdapoter implements UPanelI {

	protected UPanelTemplate panelTemplate = null;
	public UHandlerI saveHandler = null;

	protected int innerWidth = 0;
	protected int innerHeight = 0;

	protected HashMap componentMap = new HashMap();
	protected List<String> componentNameList = new ArrayList<String>();
	protected HashMap componentAttributeMap = new HashMap();
	protected HashMap getMethodMap = new HashMap();
	protected HashMap setMethodMap = new HashMap();
	protected HashMap validatorMap = new HashMap();
	protected String componentName;
	protected UPanelI parent;
	protected UEventListener eventAdaptor = null;
	protected ActionListener actionListener = null;
	protected ChangeListener changeListener = null;
	protected HashMap paraMap;
	protected UGroupComponentI groupComponent = null;
	protected boolean isTemplateUserful = true;
	protected UFormI dataForm = null;
	protected HashMap componentInitDataMap = null;
	protected URect innerRect = null;
	protected UMenuPanelMapTemplate originalTemplate = null;
	protected String pathNameString = null;
	protected UElementTemplate elementTemplate = null;
	protected String starMenuName = null;
	protected Boolean finishedInit = false;
	private UPanelTimerThread timerThread;
	private UPanelTimerControlActionThread timerControlActionThread;
	private HashMap<String, Object> timeControlMap = null;
	private UPopupMenu popupMenu = null;

	public UPanel() {
		this(null);
	}

	public UPanel(UPanelTemplate panelTemplate) {
		super();
		this.panelTemplate = panelTemplate;
		eventAdaptor = new UEventAdaptor(this);
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.panelTemplate = (UPanelTemplate) template;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return innerHeight;
	}

	public Object[] getAddedDatasObjects(List list) {
		if (list == null)
			return null;
		Object a[] = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			a[i] = list.get(i);
		}
		return a;
	}

	public void initComponents(UGroupElementTemplate gtemplate) {
		int i;
		String className;
		UComponentI item = null;
		UTemplate template;
		UFilterTemplate filterTemplate;
		FilterI filter;
		try {
			UElementTemplate el;
			for (i = 0; i < gtemplate.componentNum; i++) {
				el = (UElementTemplate) gtemplate.componentTemplates.get(i);
				if (el instanceof UGroupElementTemplate) {
					item = initGroupComponent(false);
					item.setTemplate(el);
					initComponents((UGroupElementTemplate) (el));
				} else {
					if (el.className == null)
						el.className = UFactory.getModelSession()
								.getDefaultClassByType(el.typeString);
					className = el.className;
					template = null;
					if (el.templateName != null || el.templateMaker != null) {
						if (el.templateName != null)
							template = (UTemplate) UFactory.getModelSession()
									.getTemplate(el.mapKey, el.templateName);
						else {
							TemplateMaker maker = (TemplateMaker) Class
									.forName(el.templateMaker).newInstance();
							template = maker.makeTemplate();
						}
						if (className == null)
							item = (UComponentI)template.newComponent();
						else
							item = (UComponentI) Class.forName(className)
									.newInstance();
//						template.setSelfAttribute(el);
						if (item instanceof UPanelI) {
							UPanelI tpi = (UPanelI) item;
							tpi.setStartMenuName(starMenuName);
							tpi.setOriginalTemplate(this.originalTemplate);
							tpi.setPathNameString(this.pathNameString + "."
									+ el.name);
							tpi.setTemplate(template);
							tpi.replaceTemplateContent();
						} else {
							item.setTemplate(template);
						}
					} else if (className != null) {
						item = (UComponentI) Class.forName(className)
								.newInstance();
						item.setTemplate(el);
						if (el.colorName != null)
							item.setColor(UFactory.getModelSession()
									.getColorByName(el.colorName));
						if (el.backgroundName != null)
							item.setBackground(UFactory.getModelSession()
									.getColorByName(el.backgroundName));
					}
				}
				if (item != null) {
					item.setElementTemplate(el);
					// .com = item;
					item.setComponentName(el.name);
					item.setUParent(this);
					item.setParameters(FHashMapUtil
							.fHashMapToHashMap(el.parameters));
					if (!(item instanceof UComboBox))
						item.setAddedDatas(getAddedDatasObjects(el.addedDatas));
					if (el.filter != null) {
						filterTemplate = UFactory.getModelSession()
								.getFilterTemplateByName(el.filter);
						filter = filterTemplate.newInstance();
						if (el.filterParameter != null)
							filter.init(el.filterParameter);
						else
							filter.init(filterTemplate.parameter);
						item.setFilter(filter);
					}
					if (el.filter1 != null) {
						filterTemplate = (UFilterTemplate) UFactory
								.getModelSession().getFilterTemplateByName(
										el.filter1);
						filter = filterTemplate.newInstance();
						item.setFilter1(filter);
					}
					item.updateAddedDatas(); // lxq temp add because 数据字典显示
					if (item instanceof UTemplateComponentI)
						((UTemplateComponentI) item).initComponents();
					componentMap.put(el.name, item);
					componentNameList.add(el.name);
					componentAttributeMap.put(el.name, el);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UGroupComponentI initGroupComponent(boolean isRoot) {
		return null;
	}

	public void initComponents() {
		if (panelTemplate.isTimeControlAction) {
			timeControlMap = getTimeControlMap();
		} else {
			timeControlMap = null;
		}

		groupComponent = this.initGroupComponent(true);
		groupComponent.setUParent(this);
		groupComponent.setTemplate(panelTemplate.groupElementTemplate);
		initComponents(panelTemplate.groupElementTemplate);
		resetScrollPanelSize();
		changeListener = new pageChangeProcessor();
		groupComponent.addChangeListener(changeListener);
		if (panelTemplate.isOnTimer) {
			timerThread = new UPanelTimerThread(this);
			timerThread.start();
		} else
			timerThread = null;
		if (panelTemplate.isTimeControlAction) {
			// timerControlActionThread = new
			// UPanelTimerControlActionThread(this);
			// timerControlActionThread.start();
			// setTimeControlAction();
		} else {
			timerControlActionThread = null;
		}
	}

	public void initContents() {
		initContents(groupComponent, panelTemplate.groupElementTemplate);
		initValidatorMap();
	}

	public void ResetComponentContent() {
		ResetComponentContent(panelTemplate.groupElementTemplate);
	}

	protected void computerInnerSize() {
		computerInnerSize(panelTemplate.groupElementTemplate);
	}

	public void computerComponentsInitXY() {
		computerComponentsInitXY(panelTemplate.groupElementTemplate);
	}

	public void setPreferredSize() {

	}

	public void makeDataForm() {
		UFormI form;
		form = getDataFormFromHandler();
		if (form != null)
			this.setData(form);
	}

	public void init() {
		initComponents();
		initHandlerObject(panelTemplate.name, null);
		initContents();
		initDataByHandlerAfterInitContents();
		makeDataForm();
		initInteraction();
		makeInnerBounds();
		setComponentBounds();
		setPreferredSize();
		setDisplayPanelStatusByHandlerAfterInitContents();
		setFinnishedInitMak(true);
		setRolePanelControlActionAttribute();
		handlerStart();
	}

	public void setDisplayPanelStatusByHandlerAfterInitContents() {
		UHandlerI h = getHandler();
		if (h != null)
			h.setDisplayPanel();
	}

	public void resetInnerComponentBounds() {

	}

	public void setOtherAttribute(UComponentI component, UElementTemplate e) {
		component.setHorizontalAlignment(e.horizontalAlignment);
		component.setVerticalAlignment(e.verticalAlignment);
		component.setFont(UFactory.getModelSession().getFontByName(e.fontName));
		if (e.text != null) {
			component.setText(UTextUtil.replaceString(e.text, null, dataForm,
					null));
		}
		if (e.borderName != null)
			component.setBorder(UFactory.getModelSession().getBorderByName(
					e.borderName));
		if (e.colorName != null)
			component.setColor(UFactory.getModelSession().getColorByName(
					e.colorName));
		if (e.backgroundName != null)
			component.setBackground(UFactory.getModelSession().getColorByName(
					e.backgroundName));
		component.setArrangeType(e.arrangeType);
		component.setEditable(e.editable);
		component.setVisible(e.visible);
		if (timeControlMap != null) {
			Object o = timeControlMap.get(e.name);
			if (o instanceof Boolean) {
				Boolean b = (Boolean) o;
				if (b != null)
					component.setEnabled(b);
				else
					component.setEnabled(e.enable);
			} else {
				component.setEnabled(e.enable);
			}
		} else
			component.setEnabled(e.enable);
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		if (b && e.enLabel != null)
			component.setLabel(e.enLabel);
	}

	protected void computerComponentsInitPosition(
			UGroupElementTemplate gtemplate) {
		computerComponentsInitSize(gtemplate);
		computerComponentsInitXY(gtemplate);
	}

	public void initContents(UGroupComponentI gc,
			UGroupElementTemplate gtemplate) {
		int currentx = 0, currenty = 0;
		UComponentI component;
		UGroupComponentI sgc;
		computerComponentsInitPosition(gtemplate);
		computerInnerSize(gtemplate);
		gc.addComponentBefore();
		int i;
		UElementTemplate el;
		for (i = 0; i < gtemplate.componentNum; i++) {
			el = (UElementTemplate) gtemplate.componentTemplates.get(i);
			component = (UComponentI) componentMap.get(el.name);
			if (el instanceof UGroupElementTemplate) {
				sgc = (UGroupComponentI) component;
				initContents(sgc, (UGroupElementTemplate) el);
				setOtherAttribute(component, el);
				gc.addComponent(sgc, el);
			} else {
				if (gtemplate.layoutMode.equals(UConstants.LAYOUTMODE_LOCATE)) {

					component.initContents();
					setOtherAttribute(component, el);
					gc.addComponent(component, el);
				} else {
					if (el.type == UConstants.COMPONENT_LOCATE) {
						currentx = el.x;
						currenty = el.y;
					} else if (el.type == UConstants.COMPONENT_SHIFT) {
						currentx += el.x;
						currenty += el.y;
					} else {
						component = (UComponentI) componentMap.get(el.name);
						// 刘洋添加，设置内置组件的区域
						component
								.setShellBounds(currentx, currenty, el.w, el.h);
						component.initContents();
						setOtherAttribute(component, el);
						currentx += el.x;
						currenty += el.y;
						gc.addComponent(component, el);
					}
				}
			}
		}
		gc.addComponentAfter();// noted by yxj 2010-5-11
	}

	public void ResetComponentContent(UGroupElementTemplate gtemplate) {
		UComponentI component;
		int i;
		UElementTemplate el;
		for (i = 0; i < gtemplate.componentNum; i++) {
			el = (UElementTemplate) gtemplate.componentTemplates.get(i);
			if (el instanceof UGroupElementTemplate) {
				ResetComponentContent((UGroupElementTemplate) el);
			} else {
				component = (UComponentI) componentMap.get(el.name);
				if (component instanceof UTemplateComponentI)
					((UTemplateComponentI) component).ResetComponentContent();
				if (el.text != null) {
					component.setText(el.text);
				}
			}
		}
	}

	protected void initContentBounds() {
	}

	protected void computerInnerSize(UGroupElementTemplate gtemplate) {
		int maxX = 0, maxY = 0;
		int currentx = 0, currenty = 0, i;
		UElementTemplate el;
		for (i = 0; i < gtemplate.componentNum; i++) {
			el = (UElementTemplate) gtemplate.componentTemplates.get(i);
			if (el instanceof UGroupElementTemplate) {
				computerInnerSize((UGroupElementTemplate) el);
			} else {
				if (gtemplate.layoutMode.equals(UConstants.LAYOUTMODE_LOCATE)) {
					if (el.x > maxX)
						maxX = el.x;
					if (el.y > maxY)
						maxY = el.y;
					if (el.x + el.w > maxX)
						maxX = el.x + el.w;
					if (el.y + el.h > maxY)
						maxY = el.y + el.h;
				} else {
					if (el.type == UConstants.COMPONENT_LOCATE) {
						currentx = el.x;
						currenty = el.y;
						if (currentx > maxX)
							maxX = currentx;
						if (currenty > maxY)
							maxY = currenty;
					} else if (el.type == UConstants.COMPONENT_SHIFT) {
						currentx += el.x;
						currenty += el.y;
						if (currentx > maxX)
							maxX = currentx;
						if (currenty > maxY)
							maxY = currenty;
					} else {
						if (currentx + el.w > maxX)
							maxX = currentx + el.w;
						if (currenty + el.h > maxY)
							maxY = currenty + el.h;
						currentx += el.x;
						currenty += el.y;
					}
				}
			}
			innerWidth = maxX + 1;
			innerHeight = maxY + 1;
		}
	}

	protected int getValueFromString(int type, String str) {
		int value = 0;
		int f = 1;
		UElementTemplate componentAttribute;
		StringTokenizer st = new StringTokenizer(str, "+-", true);
		String sub;
		while (st.hasMoreTokens()) {
			sub = st.nextToken();
			if (sub.equals("+")) {
				f = 1;
			} else if (sub.equals("-")) {
				f = -1;
			} else if (sub.charAt(0) == '@') {
				componentAttribute = (UElementTemplate) componentAttributeMap
						.get(sub.substring(1, sub.length()));
				switch (type) {
				case 0:
					if (componentAttribute.w > 0) {
						value += f * componentAttribute.w;
					} else {
						return -1;
					}
					break;
				case 1:
					if (componentAttribute.h > 0) {
						value += f * componentAttribute.h;
					} else {
						return -1;
					}
					break;
				}
			} else {
				value += f * Integer.parseInt(sub);
			}
		}
		return value;
	}

	protected void computerComponentsInitSize(UGroupElementTemplate gtemplate) {
		int i, value;
		boolean bfinished = false;
		UComponentI component;
		UElementTemplate el;
		if (gtemplate.layoutMode.equals(UConstants.LAYOUTMODE_LOCATE)) {
			for (i = 0; i < gtemplate.componentNum; i++) {
				el = (UElementTemplate) gtemplate.componentTemplates.get(i);
				if (el instanceof UGroupElementTemplate) {
					computerComponentsInitSize((UGroupElementTemplate) el);
				} else {
					el.w = Integer.parseInt(el.wStr);
					el.h = Integer.parseInt(el.hStr);
				}
			}
		} else {
			while (!bfinished) {
				bfinished = true;
				for (i = 0; i < gtemplate.componentNum; i++) {
					el = (UElementTemplate) gtemplate.componentTemplates.get(i);
					if (el instanceof UGroupElementTemplate) {
						computerComponentsInitSize((UGroupElementTemplate) el);
					} else {
						/*
						 * if (!el.isComponent) { if (el.wStr == null) el.w = 0;
						 * else { value = getValueFromString(0, el.wStr); if
						 * (value > 0) { el.w = value; } else { bfinished =
						 * false; } } if (el.hStr == null) el.h = 0; else {
						 * value = getValueFromString(1, el.hStr); if (value >
						 * 0) { el.h = value; } else { bfinished = false; } } }
						 * else {
						 */
						component = (UComponentI) componentMap.get(el.name);
						if (el.wStr == null)
							el.w = component.getWidth();
						else {
							value = getValueFromString(0, el.wStr);
							if (value > 0) {
								el.w = value;
							} else {
								bfinished = false;
							}
						}
						if (el.hStr == null)
							el.h = component.getHeight();
						else {
							value = getValueFromString(1, el.hStr);
							if (value > 0) {
								el.h = value;
							} else {
								bfinished = false;
							}
						}
						// }
					}
				}
			}
		}
	}

	public void computerComponentsInitXY(UGroupElementTemplate gtemplate) {
		int i, value;
		UElementTemplate el;
		if (gtemplate.layoutMode.equals(UConstants.LAYOUTMODE_LOCATE)) {
			for (i = 0; i < gtemplate.componentNum; i++) {
				el = (UElementTemplate) gtemplate.componentTemplates.get(i);
				if (el instanceof UGroupElementTemplate) {
					computerComponentsInitXY((UGroupElementTemplate) el);
				} else {

					el.x = Integer.parseInt(el.xStr);
					el.y = Integer.parseInt(el.yStr);
				}
			}
		} else {
			for (i = 0; i < gtemplate.componentNum; i++) {
				el = (UElementTemplate) gtemplate.componentTemplates.get(i);
				if (el instanceof UGroupElementTemplate) {
					computerComponentsInitXY((UGroupElementTemplate) el);
				} else {
					/*
					 * if (!el.isComponent) { if (el.xStr == null) el.x = 0;
					 * else { value = getValueFromString(0, el.xStr); el.x =
					 * value; } if (el.yStr == null) el.y = 0; else { value =
					 * getValueFromString(1, el.yStr); el.y = value; } } else
					 */{
						if (el.xStr == null)
							el.x = 0;
						else {
							value = getValueFromString(0, el.xStr);
							// if (value >= 0) {
							el.x = value;
							// }
						}
						if (el.yStr == null)
							el.y = 0;
						else {
							value = getValueFromString(1, el.yStr);
							// if (value >= 0) {
							el.y = value;
							// }
						}
					}
				}
			}
		}
	}

	public int getInnerWidth() {
		return innerWidth;
	}

	public int getInnerHeight() {
		return innerHeight;
	}

	public Dimension getInnerSize() {
		return new Dimension(innerWidth, innerHeight);
	}

	public Iterator getNameIterator() {
		Set set = componentMap.keySet();
		return set.iterator();
	}

	public boolean requestFirstFoucus() {
		int i;
		String name;
		UComponentI component;
		Iterator it = getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			component = (UComponentI) componentMap.get(name);
			if (component.requestFirstFoucus()) {
				return true;
			}
		}
		return false;
	}

	public Object getData() {
		UFormI form = getDataFormFromHandler();
		Field field;
		if (form == null)
			return null;
		sendDataToForm(form);
		return form;
	}

	public Object getSubData(String name) {
		UFormI form = getDataFormFromHandler();
		if (form == null || name == null)
			return null;
		sendDataToForm(form, name);
		return form;
	}

	public void sendDataToForm(UFormI form) {
		String name;
		for (int kt = 0; kt < this.componentNameList.size(); kt++) {
			name = componentNameList.get(kt);
			sendDataToForm(form, name);
		}
	}

	public void sendDataToForm(UFormI form, String name) {
		String attrName, methodName = "";
		UElementTemplate ele;
		UComponentI component;
		Method method;
		int i;
		try {
			ele = (UElementTemplate) this.componentAttributeMap.get(name);
			attrName = ele.dataFormMember;
			component = (UComponentI) componentMap.get(name);
			if (component == null) {
				return;
			}
			if (form != null && attrName != null && attrName.equals("this")) {
				component.sendDataToForm(form);
			}
			if (form != null && attrName != null && !attrName.equals("")) {
				Object o = component.getData();
				Object ro;
				if (o != null) {
					method = (Method) setMethodMap.get(name);
					if (method == null) {
						form.setAttributeObject(attrName, o);
					} else {
						Class types[] = method.getParameterTypes();

						if (types != null && types.length != 0
								&& types[0] != null) {
							if (o instanceof List
									|| o instanceof UTextFieldDataFormI) {
								method.invoke(form, o);
							} else if (!o.getClass().equals(types[0])) {
								if (o.getClass() == String.class) {
									if (types[0] == Integer.class) {
										if (o.toString().equals("")) {
											ro = null;
										} else {
											ro = new Integer(o.toString());
										}
										method.invoke(form, ro);
									} else if (types[0] == int.class) {
										if (o.toString().equals("")) {
										} else {
											ro = new Integer(o.toString());
											method.invoke(form, ro);
										}
									} else if (types[0] == Float.class
											|| types[0] == float.class) {
										if (o == null || o.equals("")) {
											ro = null;
										} else {
											ro = new Float(o.toString());
											method.invoke(form, ro);
										}
									} else if (types[0] == Double.class
											|| types[0] == double.class) {
										if (o == null || o.equals("")) {

										} else {
											ro = new Double(o.toString());
											method.invoke(form, ro);
										}
									} else if (types[0] == Date.class) {
										ro = DateTimeTool.formatDateTime(
												((String) o).trim(),
												"yyyy-MM-dd");
										method.invoke(form, ro);
									} else if (types[0] == Long.class
											|| types[0] == long.class) {
										if (o == null || o.equals("")) {

										} else {
											ro = new Long(o.toString());
											method.invoke(form, ro);
										}
									}
								}
							} else
								method.invoke(form, o);
						}
					}
				}
			} else {
				if (component instanceof UPanelI)
					component.getData();
			}
		} catch (Exception e) {
			System.out.println(methodName);
			e.printStackTrace();
		}
	}

	public void setData(Object obj) {
		String attrName, methodName = "", name;
		UComponentI component = null;
		UElementTemplate ele;
		Method method;
		int i;
		Object o;
		if (obj == null)
			obj = getDataFormFromHandler();
		if (obj == null)
			return;
		try {
			for (int kt = 0; kt < this.componentNameList.size(); kt++) {
				name = componentNameList.get(kt);
				component = (UComponentI) componentMap.get(name);
				if (component == null) {
					continue;
				}
				ele = (UElementTemplate) this.componentAttributeMap.get(name);
				attrName = ele.dataFormMember;
				if (obj != null && attrName != null && !attrName.equals("")) {
					if (attrName.equals("this")) {
						o = obj;
						component.setData(o);
					} else {
						method = (Method) getMethodMap.get(name);
						if (method != null) {
							o = method.invoke(obj);
							component.setData(o);
						} else {
							UFormI fI = (UFormI) obj;
							o = fI.getAttributeObject(attrName);
							if (o != null)
								component.setData(o);
						}
					}
				} else {
					if (component instanceof UPanelI) {
						component.setData(null);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(methodName);
			e.printStackTrace();
		}
	}

	public void getFormMemberMethod(UFormI form) {
		int i, j;
		String methodName, attrName, name;
		UElementTemplate ele;
		Method[] methods = form.getClass().getMethods();
		try {
			Iterator it = getNameIterator();
			while (it.hasNext()) {
				name = (String) it.next();
				ele = (UElementTemplate) this.componentAttributeMap.get(name);
				if (ele.dataFormMember != null
						&& !ele.dataFormMember.equals("this")) {
					attrName = ele.dataFormMember;
					methodName = "set" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					for (j = 0; j < methods.length; j++) {
						if (methods[j].getName().equals(methodName)) {
							setMethodMap.put(name, methods[j]);
							break;
						}
					}
					methodName = "get" + attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1, attrName.length());
					for (j = 0; j < methods.length; j++) {

						if (methods[j].getName().equals(methodName)) {
							getMethodMap.put(name, methods[j]);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initHandlerObject(String componentName, UHandlerI parent) {
		UComponentI com;
		String name;
		UHandlerSessionI session = UFactory.getHandlerSession();
		UHandlerI handler = null;

		try {
			if (panelTemplate.handlerClassName != null) {
				handler = session.newHandlerInstance(
						UHandlerSessionI.CLIENT_TYPE_JAVA,
						session.getClientSessionId(), getHandlerId(),
						panelTemplate.handlerClassName, componentName, parent,
						panelTemplate.dataFormClassName, new Boolean(false));
				((UFormHandlerI) handler).setComponent(this);
				this.componentInitDataMap = ((UFormHandlerI) handler)
						.getComponentInitDataMap();
			}
			if (handler != null) {
				Iterator it = this.getNameIterator();
				while (it.hasNext()) {
					name = (String) it.next();
					com = (UComponentI) componentMap.get(name);
					if (com instanceof UPanelI) {
						((UPanelI) com).initHandlerObject(name, getHandler());
					}
				}
			}

		} catch (Exception e) {
			handler = null;
		}
		saveHandler = handler;
	}

	public UFormI initDataByHandlerAfterInitContents() {
		return null;
	}

	public UPaperTemplate getPaperAttribute() {
		if (panelTemplate.paperName == null) {
			return new UPaperTemplate();
		} else {
			return (UPaperTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_PAPER, panelTemplate.paperName);
		}
	}

	public URect getBoundRect() {
		URect r = new URect(0, 0, innerWidth, innerHeight);
		return r;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void setComponentName(String name) {
		this.componentName = name;
	}

	public String getComponentName() {
		return componentName;
	}

	public void addActionListener(ActionListener l) {
		actionListener = l;
	}

	public void removeAtionListener(ActionListener l) {
		actionListener = null;
	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionListener = this.getUParent().getEventAdaptor();
				break;
			}
		}
	}

	public UEventListener getEventAdaptor() {
		// TODO Auto-generated method stub
		return eventAdaptor;
	}

	public void sendActionEventToParent(String cmd) {
		if (actionListener == null)
			return;
		ActionEvent se = new ActionEvent(this, 0, cmd);
		actionListener.actionPerformed(se);
	}

	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return (UComponentI) componentMap.get(name);
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return this.panelTemplate;
	}

	public String[] getToolActions() {
		if (panelTemplate != null) {
			return panelTemplate.toolActions;
		}
		return null;
	}

	public void updatePanel(UFormI form) {
		// TODO Auto-generated method stub

	}

	public void setDialogForm(UFormI form) {

	}

	public UTableI getTableComponent() {
		UComponentI com = null;
		if (panelTemplate == null)
			return null;
		if (this.groupComponent instanceof UGroupComponentMuti) {
			UGroupComponentMuti g = (UGroupComponentMuti) groupComponent;
			return g.getCurrentDisplayPagePanel().getTableComponent();
		}
		String name;
		Iterator it = getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			if (com instanceof UTableI)
				return (UTableI) com;
			else if (com instanceof UPanelI) {
				UTableI temp = ((UPanelI) com).getTableComponent();
				if (temp != null)
					return temp;
			}
		}
		return null;
	}

	public HashMap getParameters() {
		// TODO Auto-generated method stub
		return paraMap;
	}

	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub
		this.paraMap = paras;
	}

	public void updateAddedDatas() {
		String name;
		UComponentI com;
		Iterator it = getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			com.updateAddedDatas();
		}
	}

	public void updateAddedData(String name) {
		UComponentI com = (UComponentI) componentMap.get(name);
		com.updateAddedDatas();
	}

	public Object getSubComponentInitData(String name) {
		// TODO Auto-generated method stub
		if (componentInitDataMap == null)
			return null;
		if (componentInitDataMap instanceof HashMap) {
			HashMap map = (HashMap) componentInitDataMap;
			return map.get(name);
		}
		return null;
	}

	public void repaintComponent() {
		String name;
		UComponentI com;
		Iterator it = getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			com.repaintComponent();
		}
	}

	public void onClose() {
		String name;
		UComponentI com;
		Iterator it = getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			com.onClose();
		}
		if (timerThread != null)
			timerThread.endTimer();
		if (timerControlActionThread != null)
			timerControlActionThread.endTimer();
		removeHandler();

	}

	public UGroupComponentI getGroupComponentObject() {
		UGroupComponentI group = null;
		if (this.panelTemplate == null
				|| this.panelTemplate.groupElementTemplate == null)
			return group;
		String className = panelTemplate.groupElementTemplate.className;
		if (className == null) {
			String name = "layoutMode_"
					+ this.panelTemplate.groupElementTemplate.layoutMode;
			className = UFactory.getModelSession().getDefaultClassByType(name);
		}
		try {
			group = (UGroupComponentI) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}

	public UComponentI[] getAllSubComponents() {
		// TODO Auto-generated method stub
		String name;
		UComponentI com;
		List list = new ArrayList();
		Iterator it = getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			list.add(com);
		}
		if (list == null || list.size() == 0)
			return null;
		UComponentI[] a = new UComponentI[list.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = (UComponentI) list.get(i);
		}
		return a;
	}

	public void initInteraction() {
		String name;
		UComponentI com;
		Iterator it = getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			if (com instanceof UPanelI) {
				((UPanelI) com).initInteraction();
			}
		}

	}

	public UComponentI getCurrentDisplayPageCommonent() {
		if (this.panelTemplate == null
				|| panelTemplate.groupElementTemplate == null
				|| (!UimsUtils
						.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode) && !panelTemplate.groupElementTemplate.layoutMode
						.equals(UConstants.LAYOUTMODE_TABLE)))
			return null;
		return groupComponent.getCurrentDisplayPageCommonent();
	}

	public UComponentI getCurrentDisplayTableCommonent() {
		if (this.panelTemplate == null
				|| panelTemplate.groupElementTemplate == null
				|| !UimsUtils
						.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode))
			return null;
		return groupComponent.getCurrentDisplayPageCommonent();
	}

	public UPanelI getCurrentDisplayPagePanel() {
		if (this.panelTemplate == null
				|| panelTemplate.groupElementTemplate == null)
			return null;
		if (panelTemplate.groupElementTemplate.layoutMode
				.equals(UConstants.LAYOUTMODE_TABLE))
			return groupComponent.getCurrentDisplayPagePanel();
		if (!UimsUtils
				.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode))
			return null;
		return groupComponent.getCurrentDisplayPagePanel();
	}

	public void setCurrentPagePanel(int index) {
		if (this.panelTemplate == null
				|| panelTemplate.groupElementTemplate == null
				|| (!panelTemplate.groupElementTemplate.layoutMode
						.equals(UConstants.LAYOUTMODE_TABLE) && !UimsUtils
						.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode)))
			return;
		groupComponent.setCurrentPagePanel(index);
	}

	public void setCurrentPagePanelByComponentName(String comName) {
		if (this.panelTemplate == null
				|| panelTemplate.groupElementTemplate == null
				|| !UimsUtils
						.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode))
			return;
		groupComponent.setCurrentPagePanelByComponentName(comName);
	}

	public void reLayoutComponents() {
		// TODO Auto-generated method stub
		if (this.panelTemplate == null
				|| panelTemplate.groupElementTemplate == null
				|| !UimsUtils
						.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode))
			return;
		groupComponent.reLayoutComponents();
	}

	public void setSubComponentVisible(String name, boolean visible) {
		// TODO Auto-generated method stub
		if (this.panelTemplate == null
				|| panelTemplate.groupElementTemplate == null
				|| !UimsUtils
						.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode) && !panelTemplate.groupElementTemplate.layoutMode .equals(UConstants.LAYOUTMODE_TABLE))
			return;
		groupComponent.setSubComponentVisible(name, visible);
	}
	public void setSubComponentVisibleAttribute(String name, boolean visible) {
		// TODO Auto-generated method stub
		if (this.panelTemplate == null
				|| panelTemplate.groupElementTemplate == null 
				|| (!UimsUtils
						.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode) && !panelTemplate.groupElementTemplate.layoutMode .equals(UConstants.LAYOUTMODE_TABLE)))
			return;
		groupComponent.setSubComponentVisibleAttribute(name, visible);
	}

	public HashMap getComponentMap() {
		return componentMap;
	}

	public UPanel getParent() {
		return (UPanel) parent;
	}

	public void setUpperNoScrolling() {
		// TODO Auto-generated method stub

	}

	public URect makeInnerBounds() {
		innerRect = groupComponent.makeSubComponentsBox(getAllSubComponents());
		return innerRect;
	}

	public URect gerInnerBounds() {
		return innerRect;
	}

	public void resetScrollPanelSize() {

	}

	public void setComponentBounds() {
		groupComponent.setComponentBounds(getAllSubComponents());
	}

	public class pageChangeProcessor implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			UComponentI com = (UComponentI) e.getSource();
			if (com == null)
				return;
			sendhandlerProcess("processChangeEvent", e, "select");
		}
	}

	public UMenuPanelMapTemplate getOriginalTemplate() {
		return originalTemplate;
	}

	public void setOriginalTemplate(UMenuPanelMapTemplate originalTemplate) {
		this.originalTemplate = originalTemplate;
	}

	public String getPathNameString() {
		return pathNameString;
	}

	public void setPathNameString(String pathNameString) {
		this.pathNameString = pathNameString;
	}

	public void replaceTemplateContent() {
		// TODO Auto-generated method stub
		if (originalTemplate != null && panelTemplate != null)
			this.panelTemplate.replaceTemplateContent(pathNameString,
					originalTemplate.replaceMap);
	}

	public UFormI getDataFormFromHandler() {
		if (this.saveHandler != null) {
			return (UFormI) saveHandler.getForm();
		} else {
			UHandlerSessionI session = UFactory.getHandlerSession();
			return (UFormI) session.getDataFormFromHandler(
					UHandlerSessionI.CLIENT_TYPE_JAVA,
					session.getClientSessionId(), getHandlerId());
		}
	}

	public void getinitDataFromHandler() {
		UHandlerSessionI session = UFactory.getHandlerSession();

		List respond = session.getinitDataFromHandler(
				UHandlerSessionI.CLIENT_TYPE_JAVA,
				session.getClientSessionId(), getHandlerId(), paraMap);
		processCallBack(respond);
	}

	public void sendhandlerProcess(String methodName, EventObject event,
			String actionCmd) {
		String name;
		if (event == null || !(event.getSource() instanceof UComponentI))
			name = null;
		else {
			UComponentI com = (UComponentI) event.getSource();
			name = com.getComponentName();
		}
		UEventObject ue = new UEventObject(event, name, dataForm);
		List respond;
		if (saveHandler != null) {
			try {
				Method method;
				List RmiResponse = null;
				if (methodName.substring(0, 2).equals("do")) {
					if (saveHandler instanceof UPageOwnerHandler) {
						UPanelI temp = getCurrentDisplayPagePanel();
						if (temp != null) {
							UHandlerI th = temp.getHandler();
							if (th != null) {
								method = th.getClass().getMethod(methodName);
								method.invoke(th);
								respond = null;
							} else {
								method = saveHandler.getClass().getMethod(
										methodName);
								method.invoke(saveHandler);
								respond = null;
							}
						} else {
							method = saveHandler.getClass().getMethod(
									methodName);
							method.invoke(saveHandler);
							respond = null;
						}
					} else {
						method = saveHandler.getClass().getMethod(methodName);
						method.invoke(saveHandler);
						respond = null;
					}
				} else {
					method = saveHandler.getClass().getMethod(methodName,
							UEventObject.class, String.class);
					respond = (List) method.invoke(saveHandler, ue, actionCmd);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				respond = null;
			}
		} else {
			UHandlerSessionI session = UFactory.getHandlerSession();
			respond = session.sendhandlerProcess(
					UHandlerSessionI.CLIENT_TYPE_JAVA,
					session.getClientSessionId(), getHandlerId(), methodName,
					ue, actionCmd);
		}
		processCallBack(respond);

	}

	public UFormI getFormfromPanel() {
		UFormI form = null;
		try {
			if (panelTemplate.dataFormClassName != null) {
				form = (UFormI) Class.forName(panelTemplate.dataFormClassName)
						.newInstance();
				sendDataToForm(form);
				return form;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public UHandlerI getHandler() {
		// TODO Auto-generated method stub
		if (saveHandler != null)
			return saveHandler;
		else {
			UHandlerSessionI session = UFactory.getHandlerSession();
			return session.getHandlerObject(UHandlerSessionI.CLIENT_TYPE_JAVA,
					session.getClientSessionId(), getHandlerId());
		}
	}

	public void removeHandler() {
		UHandlerSessionI session = UFactory.getHandlerSession();
		session.removeHandlerObject(UHandlerSessionI.CLIENT_TYPE_JAVA,
				session.getClientSessionId(), getHandlerId());
	}

	public void sendFormHandler(UFormI form) {
		// TODO Auto-generated method stub
		UHandlerSessionI session = UFactory.getHandlerSession();
		session.sendFormHandler(UHandlerSessionI.CLIENT_TYPE_JAVA,
				session.getClientSessionId(), getHandlerId(), form);

	}

	public void setHandlerOutFormData() {
		// TODO Auto-generated method stub
		UHandlerSessionI session = UFactory.getHandlerSession();
		// session.setHandlerOutFormData(UHandlerSessionI.CLIENT_TYPE_JAVA,session.getClientSessionId(),getHandlerId());
	}

	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}

	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}

	public String getHandlerId() {
		// TODO Auto-generated method stub
		if (panelTemplate == null)
			return null;
		return starMenuName + "." + panelTemplate.name;
	}

	public void setStartMenuName(String startMenuName) {
		// TODO Auto-generated method stub
		this.starMenuName = startMenuName;
	}

	public UPanelI searchUPanelById(String id) {
		UPanelI p;
		if (id.equals(getHandlerId())) {
			return this;
		}
		if (getUParent() != null) {
			p = getUParent().searchUPanelById(id);
			if (p != null)
				return p;
		}
		UComponentI[] coms = getAllSubComponents();
		UPanelI st;
		if (coms == null)
			return null;
		for (int i = 0; i < coms.length; i++) {
			if (coms[i] instanceof UPanel) {
				st = (UPanelI) coms[i];
				p = st.searchUPanelById(id);
				if (p != null)
					return p;
			}
		}
		return null;
	}

	public void processCallBack(List retList) {
		if (retList == null)
			return;
		int i, j;
		CallBackStruct stru;
		FNameObjectPar obj;
		UPanelI pi;
		for (i = 0; i < retList.size(); i++) {
			stru = (CallBackStruct) retList.get(i);
			pi = this.searchUPanelById(stru.handlerId);
			if (pi == null)
				continue;
			for (j = 0; j < stru.commandList.size(); j++) {
				obj = (FNameObjectPar) stru.commandList.get(j);
				pi.processUpdate(obj.name, obj.ob);
			}
		}
	}

	public void processUpdate(String methodName, Object paras) {
		try {
			Method method;
			method = this.getClass().getMethod(methodName, Object.class);
			method.invoke(this, paras);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initAddedData(Object o) {
		if (o == null)
			return;
		List parList = (List) o;
		String comName = (String) parList.get(0);
		List list = (List) parList.get(1);
		UComponentI com = this.getSubComponent(comName);
		FilterI filter = com.getFilter();
		filter.setAddedData(list);
		com.updateAddedDatas();
	}

	public void formToComponent(Object o) {
		setData(o);
	}

	public void messageBoxInfo(Object o) {
		if (o == null)
			return;
		messageBoxInfo(o.toString());
	}

	public List messageBoxChoice(String s) {
		int ret = UimsUtils.messageBoxChoice(s);
		HashMap map = new HashMap();
		RequestStruct requestStruct = new RequestStruct();
		requestStruct.handlerId = "UimsUtils";
		requestStruct.methodName = "messageBoxChoice";
		requestStruct.dataObject = new Integer(ret);
		UHandlerSessionI session = UFactory.getHandlerSession();
		return session.sendHandlerRequestData(
				UHandlerSessionI.CLIENT_TYPE_JAVA,
				session.getClientSessionId(), getHandlerId(), map);

	}

	public void requestData(Object o) {
		if (o == null)
			return;
		List request = (List) o;
		if (request.size() == 0)
			return;
		RequestStruct par;
		HashMap map = new HashMap();
		UPanelI pi;
		UComponentI com;
		Object dataObject = null;
		Method method;
		for (int i = 0; i < request.size(); i++) {
			par = (RequestStruct) request.get(i);
			pi = searchUPanelById(par.handlerId);
			com = pi.getSubComponent(par.comName);
			try {
				method = com.getClass().getMethod(par.methodName, Object.class);
				dataObject = method.invoke(com, par.dataObject);
			} catch (Exception e) {
				if (par.dataObject == null) {
					try {
						method = com.getClass().getMethod(par.methodName);
						dataObject = method.invoke(com);
					} catch (Exception e1) {
						dataObject = null;
					}
				}
			}
			par.dataObject = dataObject;
			map.put(par.handlerId + par.comName + par.methodName, par);
		}
		UHandlerSessionI session = UFactory.getHandlerSession();
		List respond = session.sendHandlerRequestData(
				UHandlerSessionI.CLIENT_TYPE_JAVA,
				session.getClientSessionId(), getHandlerId(), map);
		this.processCallBack(respond);
	}

	@Override
	public void resetTemplate(UTemplate template) {
		// TODO Auto-generated method stub
	}

	@Override
	public UTableI getInnerTable() {
		// TODO Auto-generated method stub
		UPanelI pt;
		if (this.panelTemplate != null
				&& panelTemplate.groupElementTemplate != null
				&& UimsUtils
						.panelLayoutIsPageLayout(panelTemplate.groupElementTemplate.layoutMode))
			pt = groupComponent.getCurrentDisplayPagePanel();
		else
			pt = this;
		UComponentI coms[] = pt.getAllSubComponents();
		if (coms == null)
			return null;
		for (int i = 0; i < coms.length; i++)
			if (coms[i] instanceof UTableI)
				return (UTableI) coms[i];
		return null;
	}

	public Boolean isFinishedInit() {
		return finishedInit;
	}

	public void setFinnishedInitMak(boolean b) {
		finishedInit = b;
		UComponentI coms[] = getAllSubComponents();
		if (coms == null)
			return;
		for (int i = 0; i < coms.length; i++)
			if (coms[i] instanceof UPanel) {
				UPanel pt = (UPanel) (coms[i]);
				pt.setFinnishedInitMak(b);
			}
	}

	@Override
	public String[] getClosePanels() {
		// TODO Auto-generated method stub
		if (panelTemplate == null)
			return null;
		return panelTemplate.closePanels;
	}

	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
		UComponentI coms[] = getAllSubComponents();
		if (coms == null)
			return;
		for (int i = 0; i < coms.length; i++) {
			coms[i].processDispControlAfterInited();
		}

	}

	public void setPanelTimeControlActionAttribute() {
		if (panelTemplate != null && panelTemplate.isTimeControlAction)
			setTimeControlAction();
		UComponentI coms[] = getAllSubComponents();
		if (coms == null)
			return;
		for (int i = 0; i < coms.length; i++) {
			if (coms[i] instanceof UPanel)
				((UPanel) coms[i]).setPanelTimeControlActionAttribute();
		}

	}

	public HashMap<String, Object> getTimeControlMap() {
		List list = getTimeControlActionList();
		if (list == null)
			return null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		TimeControlActionItemForm form;
		for (int i = 0; i < list.size(); i++) {
			form = (TimeControlActionItemForm) list.get(i);
			if (form.getMenuName() == null)
				map.put(form.getComponentName(), form.isCando());
			else {
				HashMap<String, Object> menuMap;
				menuMap = (HashMap<String, Object>) map.get(form
						.getComponentName());
				if (menuMap == null) {
					menuMap = new HashMap<String, Object>();
					map.put(form.getComponentName(), menuMap);
				}
				menuMap.put(form.getMenuName(), form.isCando());
			}
		}
		return map;
	}

	public List getTimeControlActionList() {
		String panelName = getTemplate().name;
		UModelSessionI ms = UFactory.getModelSession();
		return ms.getTimeControlActionListByPanelName(panelName, UimsUtils.userTokenClientSide.getRoleList());
	}

	public void setTimeControlAction() {
		List list = getTimeControlActionList();
		if (list != null) {
			setTimeControlActionItem(list);
		}
	}

	public void setMenuTimeControl(String name, UMenu menu) {
		if (timeControlMap == null)
			return;
		Object o = timeControlMap.get(name);
		if (o == null || !(o instanceof HashMap))
			return;
		HashMap menuMap = (HashMap) o;
		Object ob;
		JMenuItem item;
		int ncomponents = menu.getItemCount();
		for (int j = 0; j < ncomponents; j++) {
			item = menu.getItem(j);
			UMenuItem mi = (UMenuItem) item;
			ob = menuMap.get(mi.getTemplate().getName());
			if (ob != null)
				mi.setEnabled((Boolean) ob);
		}
	}

	public void setTimeControlActionItem(List list) {
		int i, j;
		TimeControlActionItemForm form;
		UComponentI com;
		for (i = 0; i < list.size(); i++) {
			form = (TimeControlActionItemForm) list.get(i);
			com = (UComponentI) componentMap.get(form.getComponentName());
			if (com == null)
				continue;
			if (form.getMenuName() == null) {
				com.setEnabled(form.isCando());
			} else {
				UPopupMenu popMenu = com.getUPopupMenu();
				if (popMenu == null)
					continue;
				int ncomponents = popMenu.getComponentCount();
				Component[] component = popMenu.getComponents();
				for (j = 0; j < ncomponents; j++) {
					Component comp = component[j];
					if (comp instanceof UMenuItem) {
						UMenuItem mi = (UMenuItem) comp;
						if (mi.getTemplate().getName()
								.equals(form.getMenuName())) {
							mi.setEnabled(form.isCando());
						}
					}
				}
			}
		}
	}

	@Override
	public void closePopUpPanel(int type) {
		// TODO Auto-generated method stub
	}

	public void setComStatusAttribute(UComponentI com, String status) {
		if (status == null || com == null)
			return;
		if (status.equals("1"))
			com.setVisible(true);
		else if (status.equals("2"))
			com.setVisible(false);
		else if (status.equals("3"))
			com.setEnabled(true);
		else if (status.equals("4"))
			com.setEnabled(false);
		else if (status.equals("5"))
			com.setEditable(true);
		else if (status.equals("6"))
			com.setEditable(false);
	}

	public void setRolePanelControlActionAttribute() {
		setRoleControlAction();
		UComponentI coms[] = getAllSubComponents();
		if (coms == null)
			return;
		for (int i = 0; i < coms.length; i++) {
			if (coms[i] instanceof UPanel)
				((UPanel) coms[i]).setRolePanelControlActionAttribute();
		}
	}

	public void setRoleControlAction() {
		HashMap map = UimsFactory.getClientMainI().getSysRolePanelMap();
		if (map == null)
			return;
		List list = (List) map.get(this.panelTemplate.getName());
		if (list != null && list.size() > 0) {
			setRoleControlActionItem(list);
		}
	}

	public void setRoleControlActionItemAll(String status) {
		UComponentI coms[] = getAllSubComponents();
		if (coms == null)
			return;
		for (int i = 0; i < coms.length; i++) {
			if (coms[i] instanceof UPanel)
				((UPanel) coms[i]).setRoleControlActionItemAll(status);
			else {
				setComStatusAttribute(coms[i], status);
				UPopupMenu popMenu = coms[i].getUPopupMenu();
				if (popMenu == null)
					continue;
				int ncomponents = popMenu.getComponentCount();
				Component[] component = popMenu.getComponents();
				for (int j = 0; j < ncomponents; j++) {
					Component comp = component[j];
					if (comp instanceof UMenuItem) {
						UMenuItem mi = (UMenuItem) comp;
						setComStatusAttribute(mi, status);
					}
				}
			}
		}
	}

	public void setRoleControlActionItem(List list) {
		int i, j;
		RoleControlACtionItemForm form;
		UComponentI com;
		for (i = 0; i < list.size(); i++) {
			form = (RoleControlACtionItemForm) list.get(i);
			if (form.getComponentName().equals("ALL")) {
				setRoleControlActionItemAll(form.getStatus());
				continue;
			}
			com = (UComponentI) componentMap.get(form.getComponentName());
			if (com == null)
				continue;
			if (form.getMenuName() == null) {
				setComStatusAttribute(com, form.getStatus());
			} else {
				UPopupMenu popMenu = com.getUPopupMenu();
				if (popMenu == null)
					continue;
				int ncomponents = popMenu.getComponentCount();
				Component[] component = popMenu.getComponents();
				for (j = 0; j < ncomponents; j++) {
					Component comp = component[j];
					if (comp instanceof UMenuItem) {
						UMenuItem mi = (UMenuItem) comp;
						if (mi.getTemplate().getName()
								.equals(form.getMenuName())) {
							setComStatusAttribute(mi, form.getStatus());
						}
					}
				}
			}
		}
	}

	public void initValidatorMap() {
		String name;
		UElementTemplate ele;
		String className;
		DataValidatorI vi = null;
		for (int kt = 0; kt < this.componentNameList.size(); kt++) {
			name = componentNameList.get(kt);
			ele = (UElementTemplate) this.componentAttributeMap.get(name);
			className = ele.validatorClassName;
			if (className == null && ele.validatorName != null) {
				className = UFactory.getModelSession().getDefaultClassByType(
						ele.validatorName);
			}
			if (className == null)
				continue;
			try {
				vi = (DataValidatorI) Class.forName(className).newInstance();
				vi.init(ele.validatorMsg,
						UimsUtils.stringToHashMap(ele.validatorParas));
				validatorMap.put(name, vi);
			} catch (Exception e) {
				e.printStackTrace();
				vi = null;
			}
		}
	}

	public boolean testInvalidateData() {
		String name;
		UComponentI com;
		DataValidatorI vi = null;
		for (int kt = 0; kt < this.componentNameList.size(); kt++) {
			name = componentNameList.get(kt);
			com = (UComponentI) componentMap.get(name);
			vi = (DataValidatorI) this.validatorMap.get(name);
			if (vi != null) {
				if (vi.isInvalidate(com.getData())) {
					UimsUtils.messageBoxInfo(vi.getInvalidateMessage());
					return true;
				}
			} else {
				if (com instanceof UTemplateComponentI) {
					UTemplateComponentI tc = (UTemplateComponentI) com;
					tc.testInvalidateData();
				}
			}
		}
		return false;
	}

	@Override
	public void setUseOutContainer(boolean isUse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOutCantiner(Container outContainer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshComponentData() {
		// TODO Auto-generated method stub
		if (getHandler() != null)
			getHandler().refreshComponentData();
	}

	@Override
	public void setPanelDisplayStatus(UPanelStatusFormI form) {
		// TODO Auto-generated method stub
		if (form == null)
			return;
		this.groupComponent
				.setPageIconDisplayInfo(form.getPageIconDisplyInfo());
	}

	public void handlerStart() {
		UHandlerI h = this.getHandler();
		if (h == null)
			return;
		h.start();
		UHandlerI sh[] = h.getAllSubHandler();
		if (sh == null || sh.length == 0)
			return;
		for (int i = 0; i < sh.length; i++)
			sh[i].start();
	}

	@Override
	public void updateComAddedDataList(String panelName, String comName,
			List dataList) {
		String name;
		UPanel p1;
		UComponentI com;
		FilterI f;
		name = this.getTemplate().getName();
		if (name != null && name.equals(panelName)) {
			com = (UComponentI) componentMap.get(comName);
			f = com.getFilter();
			if (f == null)
				return;
			f.setAddedData(dataList);
			com.updateAddedDatas();
		} else {
			for (int kt = 0; kt < this.componentNameList.size(); kt++) {
				name = componentNameList.get(kt);
				com = (UComponentI) componentMap.get(name);
				if (com instanceof UPanelI) {
					UPanelI pt = (UPanelI) com;
					pt.updateComAddedDataList(panelName, comName, dataList);
				}
			}
		}
	}

	@Override
	public void updateQueryComAddedDataList(String panelName, String queryName,
			String comName, List dataList) {
		// TODO Auto-generated method stub
		String name;
		UPanel p1;
		UComponentI com;
		FilterI f;
		name = this.getTemplate().getName();
		if (name != null && name.equals(panelName)) {
			com = (UComponentI) componentMap.get(queryName);
			if (com instanceof UQueryComplexBasePanel) {
				UQueryComplexBasePanel qp = (UQueryComplexBasePanel) com;
				qp.updateComAddedDataList(comName, dataList);
			}
		} else {
			for (int kt = 0; kt < this.componentNameList.size(); kt++) {
				name = componentNameList.get(kt);
				com = (UComponentI) componentMap.get(name);
				if (com instanceof UPanelI) {
					UPanelI pt = (UPanelI) com;
					pt.updateQueryComAddedDataList(panelName, queryName,
							comName, dataList);
				}
			}
		}
	}

	public void reComputeComponentBound() {
		if (panelTemplate == null)
			return;
		List<UButtonFold> foldList = new ArrayList();
		int i, j;
		String name;
		UComponentI com;
		for (i = 0; i < this.componentNameList.size(); i++) {
			name = componentNameList.get(i);
			com = (UComponentI) componentMap.get(name);
			if (com instanceof UButtonFold) {
				foldList.add((UButtonFold) com);
			}
		}
		if (foldList.size() == 0)
			return;
		int yo = 5;
		UButtonFold button;
		UElementTemplate e, se;
		boolean groupOpend;
		int dis = 5;
		Component c;
		for (i = 0; i < foldList.size(); i++) {
			button = foldList.get(i);
			e = button.getElementTemplate();
			groupOpend = button.getGroupOpened();
			button.setShellBounds(e.x, yo, e.w, e.h);
			for (j = 0; j < this.componentNameList.size(); j++) {
				name = componentNameList.get(j);
				com = (UComponentI) componentMap.get(name);
				if (com instanceof UButtonFold)
					continue;
				se = com.getElementTemplate();
				if(se.groupNo != i)
					continue;
				if (groupOpend) {
//					com.setShellBounds(se.x, yo + e.h + dis + se.y, se.w, se.h);
					c = com.getAWTComponent();
					c.setLocation(se.x, yo + e.h + dis + se.y);
					com.setVisible(true);
				} else {
					com.setVisible(false);
				}
			}
			if (groupOpend) {
				yo += e.h + button.getGroupHeight() + dis * 2;
			} else {
				yo += e.h + dis;
			}
		}
	}
	public void componentRepaint(){
		if(groupComponent != null)
			this.groupComponent.componentRepaint();
	}
	public void setAllComponentEnable(boolean b) {
		String name;
		UComponentI com;
		for (int j = 0; j < this.componentNameList.size(); j++) {
			name = componentNameList.get(j);
			com = (UComponentI) componentMap.get(name);
			com.setEditable(b);
			com.setEnabled(b);
		}
	}
	public String getPanelTemplateName(){
		return panelTemplate.name;
	}
	

}
