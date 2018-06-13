package cn.edu.sdu.uims.service;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.plaf.ComponentUI;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sdu.spring_util.ApplicationContextHandle;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.DefaultFontMapper.BaseFontParameters;
import com.itextpdf.text.pdf.BaseFont;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.reportdog.UBloackBaseTemplate;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UContentElementI;
import cn.edu.sdu.uims.base.UNameObjectPar;
import cn.edu.sdu.uims.base.UPointCursor;
import cn.edu.sdu.uims.base.UStroke;
import cn.edu.sdu.uims.base.UUIStyle;
import cn.edu.sdu.uims.component.table.SortColumnAttribute;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.TableStatisticsItemTemplate;
import cn.edu.sdu.uims.def.UActionBarTemplate;
import cn.edu.sdu.uims.def.UBlockContent;
import cn.edu.sdu.uims.def.UButtonTemplate;
import cn.edu.sdu.uims.def.UChartTemplate;
import cn.edu.sdu.uims.def.UColumnTemplate;
import cn.edu.sdu.uims.def.UComboBoxDateTemplate;
import cn.edu.sdu.uims.def.UCommandTemplate;
import cn.edu.sdu.uims.def.UDialogTemplate;
import cn.edu.sdu.uims.def.UDictionaryMappingTemplate;
import cn.edu.sdu.uims.def.UDocumentTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UFilterTemplate;
import cn.edu.sdu.uims.def.UGraphButtonBarTemplate;
import cn.edu.sdu.uims.def.UGroupElementTemplate;
import cn.edu.sdu.uims.def.UHandlerTemplate;
import cn.edu.sdu.uims.def.UMenuBarTemplate;
import cn.edu.sdu.uims.def.UMenuItemTemplate;
import cn.edu.sdu.uims.def.UMenuPanelMapTemplate;
import cn.edu.sdu.uims.def.UMenuTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.def.UParagraphContent;
import cn.edu.sdu.uims.def.UPromptTemplate;
import cn.edu.sdu.uims.def.UQueryDataFieldTemplate;
import cn.edu.sdu.uims.def.USheetTemplate;
import cn.edu.sdu.uims.def.UShortcutkeyTemplate;
import cn.edu.sdu.uims.def.USpinnerTemplate;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.def.UTextFieldTemplate;
import cn.edu.sdu.uims.def.UTreeMenuTemplate;
import cn.edu.sdu.uims.def.cell.UCellStruct;
import cn.edu.sdu.uims.def.cell.UCellStructColumn;
import cn.edu.sdu.uims.def.cell.UCellStructCom;
import cn.edu.sdu.uims.def.cell.UCellStructRow;
import cn.edu.sdu.uims.def.dataexport.DataExportTemplate;
import cn.edu.sdu.uims.def.dataimport.DataImportTemplate;
import cn.edu.sdu.uims.def.dataimport.ItemTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.flex.FHashMap;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.def.UClientDialogTemplate;
import cn.edu.sdu.uims.frame.def.UClientFrameControlTemplate;
import cn.edu.sdu.uims.frame.def.UClientFrameTemplate;
import cn.edu.sdu.uims.frame.def.UFixPanelTemplate;
import cn.edu.sdu.uims.frame.def.UFrameAttribute;
import cn.edu.sdu.uims.frame.def.UImageAttribute;
import cn.edu.sdu.uims.frame.def.ULabelAttribute;
import cn.edu.sdu.uims.frame.def.UStatusbarTemplate;
import cn.edu.sdu.uims.frame.def.UToolTemplate;
import cn.edu.sdu.uims.frame.def.UToolbarTemplate;
import cn.edu.sdu.uims.graph.model.GraphModel2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.data.xml.XmlGraphModelDataProcessor;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.hcims.def.UserTaskTemplate;
import cn.edu.sdu.uims.itms.def.ICursorTemplate;
import cn.edu.sdu.uims.itms.def.IEventorTemplate;
import cn.edu.sdu.uims.itms.def.IStatusDiagramTemplate;
import cn.edu.sdu.uims.itms.def.ITaskTemplate;
import cn.edu.sdu.uims.print.photo.PhotoLayoutTemplate;
import cn.edu.sdu.uims.util.UimsUtils;

public class UModelSessionServer extends UModelSession {

	private HashMap templateMap = new HashMap();

	private HashMap handlerMap = new HashMap();

	private HashMap constantMap = new HashMap();

	private HashMap constantRevMap = new HashMap();

	private HashMap classMappingMap = new HashMap();

	private HashMap templateMappingMap = new HashMap();

	private HashMap bsTemplateMap = new HashMap();
	private HashMap objectTemplateMap = new HashMap();

	private HashMap dictionaryMappingMap = new HashMap();

	private EnvironmentTemplate environmentTemplate = null;

	private DefaultFontMapper fontMapper = new DefaultFontMapper();

	private String modelPath;
	private String environmentFileName;

	private int uimsType = 0;
	private boolean realTime = false;
	private boolean isClient = false;
	private void clearAllData() {
		templateMap.clear();

		handlerMap.clear();

		constantMap.clear();

		constantRevMap.clear();

		classMappingMap.clear();

		dictionaryMappingMap.clear();

		templateMappingMap.clear();

		bsTemplateMap.clear();

		objectTemplateMap.clear();
	}

	private Element getRootByXMLFileName(String fileName) {
		Element root = null;

		Document doc = null;

		SAXReader sb = null;

		InputStream in = null;
		sb = new SAXReader();
		try {
			if (!realTime)
				in = this.getClass().getResourceAsStream(fileName);
			else
				in = this.getClass().getClassLoader().getResource(fileName)
						.openStream();
			if (in == null)
				in = new FileInputStream(new File(fileName));
			doc = sb.read(in);
			root = doc.getRootElement();
		} catch (Exception e) {
			System.out.print(fileName);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}

	public Object getTemplate(String name) {
		return templateMap.get(name);
	}

	public HashMap getFactoryTemplateMap() {
		return templateMap;
	}

	public Object getTemplate(String type, String name) {
		HashMap map = (HashMap) templateMap.get(type);
		if (map == null)
			return null;
		return map.get(name);
	}

	public String getMapNameByType(String type) {
		HashMap temp = (HashMap) constantRevMap.get(UConstants.TYPEDEF_MAP_KEY);
		String name = (String) temp.get(type);
		return name;
	}

	public HashMap getTemplateMapInstance(HashMap paraTempMap, String key) {
		String value;

		HashMap map, temp;
		temp = (HashMap) constantMap.get(UConstants.TYPEDEF_MAP_KEY);
		value = (String) temp.get(key);
		map = (HashMap) paraTempMap.get(value);
		if (map == null) {
			map = new HashMap();
			paraTempMap.put(value, map);
		}
		return map;
	}

	public HashMap getTemplateMapInstance(String key) {
		return this.getTemplateMapInstance(templateMap, key);
	}

	public UColor getDefaultColor() {
		return defaultColor;
	}

	public UColor getDefaultBgColor() {
		return defaultBgColor;
	}

	public UFont getDefaultFont() {
		return defaultFont;
	}

	public UBorder getDefaultStrokeBorder() {
		if (defaultStrokeBorder == null) {
			defaultStrokeBorder = newBorder("stroke",
					UConstants.BORDER_LAYOUT_ALL, 1, null, null);
		}
		return defaultStrokeBorder;
	}

	public UBorder getDefaultBoundBorder() {
		if (defaultBoundBorder == null) {
			defaultBoundBorder = newBorder("bound",
					UConstants.BORDER_LAYOUT_ALL, 1, null, null);
		}
		return defaultBoundBorder;
	}

	public UPointCursor getDefaultPointCursor() {
		if (defaultPointCursor == null) {
			defaultPointCursor = new UPointCursor();
		}
		return defaultPointCursor;
	}

	public UUIStyle getDefaultUIStyle() {
		if (defaultUIStyle == null) {
			defaultUIStyle = new UUIStyle();
		}
		return defaultUIStyle;
	}

	public List getTemplateNameList(String key) {
		String name;
		int ic = 0;
		List list = new ArrayList();
		HashMap map = (HashMap) templateMap.get(key);
		if (map != null) {
			Set set = map.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				list.add(new ListOptionInfo(ic + "", name));
				ic++;
			}
		}
		return list;
	}

	public UFont getFontByName(String name) {
		UFont font = null;
		if (name != null)
			font = (UFont) getTemplate(UConstants.MAPKEY_FONT, name);
		if (font == null)
			font = defaultFont;
		if (font != null)
			font.getObject();
		return font;
	}

	public UColor getColorByName(String name) {
		UColor color = null;
		if (name != null) {
			color = (UColor) getTemplate(UConstants.MAPKEY_COLOR, name);
		}
		if (color == null)
			color = defaultColor;
		if (color != null)
			color.getObject();
		return color;
	}

	public UColor getBgColorByName(String name) {
		UColor color = null;
		if (name != null) {
			color = (UColor) getTemplate(UConstants.MAPKEY_COLOR, name);
		}
		if (color == null)
			color = defaultBgColor;
		if (color != null)
			color.getObject();
		return color;
	}

	public UFilterTemplate getFilterTemplateByName(String name) {
		UFilterTemplate filter = null;
		if (name != null) {
			filter = (UFilterTemplate) getTemplate(UConstants.MAPKEY_FILTER,
					name);
		}
		return filter;
	}

	public UDictionaryMappingTemplate getDictionaryMappingTemplateByName(
			String name) {
		UDictionaryMappingTemplate template = null;
		if (name != null) {
			template = (UDictionaryMappingTemplate) getTemplate(
					UConstants.MAPKEY_DICTIONARYMAPPING, name);
		}
		return template;
	}

	public String getDictionaryMappingMapVlue(String mappingName,
			String dictName, String value) {
		UDictionaryMappingTemplate template = null;
		String ret = null;
		if (mappingName != null) {
			template = (UDictionaryMappingTemplate) getTemplate(
					UConstants.MAPKEY_DICTIONARYMAPPING, mappingName);
			if (template != null) {
				HashMap map = template.dictionMap.get(dictName);
				if (map != null) {
					ret = (String) map.get(value);
				}
			}
		}
		if (ret == null)
			ret = value;
		return ret;
	}

	public UPointCursor getPointCursorByName(String name) {
		UPointCursor cursor = null;
		if (name != null)
			cursor = (UPointCursor) getTemplate(UConstants.MAPKEY_POINTCURSOR,
					name);
		if (cursor == null)
			cursor = getDefaultPointCursor();
		return cursor;
	}

	public UBorder getBorderByName(String name) {
		UBorder border = null;
		if (name != null)
			border = (UBorder) getTemplate(UConstants.MAPKEY_BORDER, name);
		if (border == null)
			border = defaultStrokeBorder;
		if (border != null)
			border.getObject();
		return border;
	}

	public UUIStyle getUIStyleByName(String name) {
		UUIStyle style = null;
		if (name != null) {
			style = (UUIStyle) getTemplate(UConstants.MAPKEY_UISTYLE, name);
		}
		if (style == null)
			style = defaultUIStyle;
		return style;
	}

	private void setMenuTemplate(UTreeMenuTemplate treeMenuTemplate,
			UTreeMenuTemplate treeMenuTemplate2) {
		treeMenuTemplate.name = treeMenuTemplate2.name;
		treeMenuTemplate.className = treeMenuTemplate2.className;
		if (treeMenuTemplate.className == null) {
			treeMenuTemplate.className = (String) this.classMappingMap
					.get("menu");
		}
		treeMenuTemplate.content = treeMenuTemplate2.content;
		treeMenuTemplate.enContent = treeMenuTemplate2.enContent;
		treeMenuTemplate.cmd = treeMenuTemplate2.cmd;
		treeMenuTemplate.handler = treeMenuTemplate2.handler;
		for (int i = 0; i < treeMenuTemplate2.size(); i++) {
			treeMenuTemplate.items = treeMenuTemplate2.items;
		}
	}

	public void initTreeTemplate(Element e1, UClientFrameTemplate app) {
		String str = "";
		if (e1 == null) {
			app.treeMenuTemplate = null;
			return;
		}
		app.treeMenuTemplate = new UTreeMenuTemplate();
		setMenuTemplate(e1, app.treeMenuTemplate);
		str = e1.attributeValue("isExpandAll");
		if (str != null && str.equals("true"))
			app.treeMenuTemplate.isExpandAll = true;
		else
			app.treeMenuTemplate.isExpandAll = false;
		str = e1.attributeValue("alignment");
		if (str != null) {
			app.treeMenuTemplate.alignment = this.getTypeValueByString(str,
					"alignmentType");
		} else {
			app.treeMenuTemplate.alignment = UConstants.ALIGNMENT_LEFT;
		}
	}

	private void initModels(HashMap map, Element root, String path)
			throws Exception {
		Iterator it1;
		Element e = null;
		Method method = null;
		String methodName = "", key = "";
		it1 = root.elementIterator("file");
		String fileName, name;
		Element root1;
		while (it1.hasNext()) {
			e = (Element) it1.next();
			fileName = e.attributeValue("name");
			name = path + fileName;
			root1 = getRootByXMLFileName(name);
			try {
				initModels(map, root1, path);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		it1 = root.elementIterator("mode");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			methodName = e.attributeValue("method");
			if (methodName == null)
				continue;
			key = e.attributeValue("key");
			fileName = e.attributeValue("fileName");

			// System.out.println("fileName-----------"+fileName);

			method = this.getClass().getMethod(methodName, HashMap.class,
					String.class, String.class);
			method.invoke(this, map, path + fileName, key);
		}
	}

	// TODO Auto-generated constructor stub
	private void putTemplate(String name, Object o) {
		templateMap.put(name, o);
	}

	public void initConstants(HashMap paraTempMap, String fileName, String key) {
		HashMap typeMap, typeRevMap;
		Element typedefs, typedef, constants, constant;
		Iterator it1, it2;
		Element root = getRootByXMLFileName(fileName);
		String name, typeString, typeInt;
		it1 = root.elementIterator("typedef");
		while (it1.hasNext()) {
			typedef = (Element) it1.next();
			name = typedef.attributeValue("name");
			typeMap = new HashMap();
			typeRevMap = new HashMap();
			constantMap.put(name, typeMap);
			constantRevMap.put(name, typeRevMap);
			it2 = typedef.elementIterator("constant");
			while (it2.hasNext()) {
				constant = (Element) it2.next();
				typeString = constant.attributeValue("string");
				typeInt = constant.attributeValue("int");
				typeMap.put(typeString, typeInt);
				typeRevMap.put(typeInt, typeString);
			}
		}
	}

	public void initClassMapping(HashMap paraTempMap, String fileName,
			String key) {
		Element map;
		Iterator it1;
		Element root = getRootByXMLFileName(fileName);
		String type, classname;
		it1 = root.elementIterator("map");
		while (it1.hasNext()) {
			map = (Element) it1.next();
			type = map.attributeValue("type");
			classname = map.attributeValue("class");
			classMappingMap.put(type, classname);
		}
	}

	public String getDefaultClassByType(String type) {
		return (String) classMappingMap.get(type);
	}

	public String getDefaultClassByTypeIntKey(String type) {
		String str = getMapNameByType(type);
		return (String) classMappingMap.get(str);
	}

	public void initTemplateMapping(HashMap paraTempMap, String fileName,
			String key) {
		Element map;
		Iterator it1;
		Element root = getRootByXMLFileName(fileName);
		String type, classname;
		it1 = root.elementIterator("map");
		while (it1.hasNext()) {
			map = (Element) it1.next();
			type = map.attributeValue("type");
			classname = map.attributeValue("template");
			templateMappingMap.put(type, classname);
		}
	}

	public void initParameterTemplate(HashMap paraTempMap, String fileName,
			String key) {
		HashMap typeMap;
		Element fonts, font;
		Iterator it1, it2;
		Element root = getRootByXMLFileName(fileName);
		Font fontObj;
		String fontName, str;
		int style, size;
		String name;
		HashMap temp;
		HashMap fontMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_FONT);
		it1 = root.elementIterator("fonts");
		while (it1.hasNext()) {
			fonts = (Element) it1.next();
			it2 = fonts.elementIterator("font");
			while (it2.hasNext()) {
				font = (Element) it2.next();
				name = font.attributeValue("name");
				fontName = font.attributeValue("fontName");
				if (fontName == null) {
					fontName = "宋体";
				}
				str = font.attributeValue("style");
				if (str == null)
					style = Font.PLAIN;
				else {
					temp = (HashMap) constantMap.get("fontStyleType");
					style = Integer.parseInt((String) temp.get(str));
				}
				str = font.attributeValue("size");
				if (str == null) {
					size = 16;
				} else
					size = Integer.parseInt(str);

				str = font.attributeValue("fileName");
				if (str == null) {
					str = "simsun";
				}
				String fName = "cn/edu/sdu/reportfont/" + str + ".ttf";
				fontMap.put(name, new UFont(fontName, style, size, fName));
				BaseFontParameters parameters = new BaseFontParameters(fName);
				parameters.encoding = BaseFont.IDENTITY_H;
				fontMapper.putName(fontName, parameters);
			}
		}

		int r = 0, g = 0, b = 0;
		Color c;
		HashMap colorMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_COLOR);
		it1 = root.elementIterator("colors");
		while (it1.hasNext()) {
			fonts = (Element) it1.next();
			it2 = fonts.elementIterator("color");
			while (it2.hasNext()) {
				font = (Element) it2.next();
				name = font.attributeValue("name");
				str = font.attributeValue("r");
				if (str != null) {
					r = Integer.parseInt(str);
				}
				str = font.attributeValue("g");
				if (str != null) {
					g = Integer.parseInt(str);
				}
				str = font.attributeValue("b");
				if (str != null) {
					b = Integer.parseInt(str);
				}
				colorMap.put(name, new UColor(r, g, b, name));
			}
		}

		Element papers, paper;
		UPaperTemplate paperAttribute;
		HashMap paperMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_PAPER);
		it1 = root.elementIterator("papers");
		while (it1.hasNext()) {
			papers = (Element) it1.next();
			it2 = papers.elementIterator("paper");
			while (it2.hasNext()) {
				paper = (Element) it2.next();
				paperAttribute = new UPaperTemplate();
				name = paper.attributeValue("name");
				paperAttribute.name = name;
				str = paper.attributeValue("width");
				if (str != null) {
					paperAttribute.width = Float.parseFloat(str);
				}
				str = paper.attributeValue("height");
				if (str != null) {
					paperAttribute.height = Float.parseFloat(str);
				}
				str = paper.attributeValue("left");
				if (str != null) {
					paperAttribute.left = Float.parseFloat(str);
				}
				str = paper.attributeValue("right");
				if (str != null) {
					paperAttribute.right = Float.parseFloat(str);
				}
				str = paper.attributeValue("top");
				if (str != null) {
					paperAttribute.top = Float.parseFloat(str);
				}
				str = paper.attributeValue("bottom");
				if (str != null) {
					paperAttribute.bottom = Float.parseFloat(str);
				}
				str = paper.attributeValue("scale");
				if (str != null) {
					paperAttribute.scale = Float.parseFloat(str);
				}
				str = paper.attributeValue("orientation");
				if (str != null) {
					paperAttribute.orientation = Integer.parseInt(str);
				}
				paperMap.put(name, paperAttribute);
			}
		}

		Element pare, pares;

		UParagraphTemplate pa;
		HashMap paragraphMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_PARAGRAPH);
		it1 = root.elementIterator("paragraphs");
		while (it1.hasNext()) {
			pares = (Element) it1.next();
			it2 = pares.elementIterator("paragraph");
			while (it2.hasNext()) {
				pare = (Element) it2.next();
				pa = new UParagraphTemplate();
				name = pare.attributeValue("name");
				str = pare.attributeValue("type");
				if (str != null) {
					temp = (HashMap) constantMap.get("paragraphType");
					if (temp.get(str) != null)
						pa.type = Integer.parseInt((String) temp.get(str));
				}
				pa.colorName = pare.attributeValue("color");
				pa.fontName = pare.attributeValue("font");
				str = pare.attributeValue("firstSpace");
				if (str != null) {
					pa.firstSpace = Integer.parseInt(str);
				}
				str = pare.attributeValue("before");
				if (str != null) {
					pa.before = Integer.parseInt(str);
				}
				str = pare.attributeValue("after");
				if (str != null) {
					pa.after = Integer.parseInt(str);
				}
				str = pare.attributeValue("horizontalAlignment");
				if (str != null) {
					pa.horizontalAlignment = getTypeValueByString(str,
							"alignmentType");
				}
				str = pare.attributeValue("verticalAlignment");
				if (str != null) {
					pa.verticalAlignment = getTypeValueByString(str,
							"alignmentType");
				}
				str = pare.attributeValue("leading");
				if (str != null) {
					pa.leading = Float.parseFloat(str);
				}
				paragraphMap.put(name, pa);
			}
		}

		Element pages, page;

		UPageNumberTemplate pna;
		HashMap pageMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_PAGENUMBER);
		it1 = root.elementIterator("pagenumbers");
		while (it1.hasNext()) {
			pages = (Element) it1.next();
			it2 = pages.elementIterator("pagenumber");
			while (it2.hasNext()) {
				page = (Element) it2.next();
				pna = new UPageNumberTemplate();
				name = page.attributeValue("name");
				str = page.attributeValue("layoutType");
				if (str != null) {
					temp = (HashMap) constantMap.get("pageNumberLayoutType");
					pna.layoutType = Integer.parseInt((String) temp.get(str));
				}
				pna.colorName = page.attributeValue("color");
				pna.fontName = page.attributeValue("font");
				str = page.attributeValue("numberType");
				if (str != null) {
					temp = (HashMap) constantMap.get("pageNumberType");
					pna.numberType = Integer.parseInt((String) temp.get(str));
				}
				pageMap.put(name, pna);
			}
		}

		UBorder bObject;
		Element border, borders, dash;
		HashMap borderMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_BORDER);
		Iterator it3;
		int bn;
		it1 = root.elementIterator("borders");
		while (it1.hasNext()) {
			borders = (Element) it1.next();
			it2 = borders.elementIterator("border");
			while (it2.hasNext()) {
				border = (Element) it2.next();
				bObject = new UBorder();
				name = border.attributeValue("name");
				str = border.attributeValue("layout");
				if (str != null) {
					bObject.layout = this.getTypeValueByString(str,
							"borderLayoutType");
				} else
					bObject.layout = UConstants.BORDER_LAYOUT_ALL;
				bObject.colorName = border.attributeValue("color");
				str = border.attributeValue("width");
				if (str != null) {
					bObject.width = Float.parseFloat(str);
				}
				it3 = border.elementIterator("dash");
				bn = 0;
				while (it3.hasNext()) {
					dash = (Element) it3.next();
					bn++;
				}
				if (bn == 0) {
					bObject.dash = null;
				} else {
					bObject.dash = new float[bn];
					it3 = border.elementIterator("dash");
					bn = 0;
					while (it3.hasNext()) {
						dash = (Element) it3.next();
						str = dash.attributeValue("value");
						bObject.dash[bn++] = Float.parseFloat(str);
					}
				}
				bObject.type = border.attributeValue("type");
				bObject.getObject();
				borderMap.put(name, bObject);
			}
		}
		String strokeName;
		float[] dashes = null;
		float width;
		Element stroke, strokes;
		UStroke uStroke;
		String strs[], cap, join, miterlimit;
		HashMap strokeMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_STROKE);
		it1 = root.elementIterator("strokes");
		while (it1.hasNext()) {
			strokes = (Element) it1.next();
			it2 = strokes.elementIterator("stroke");
			while (it2.hasNext()) {
				stroke = (Element) it2.next();
				strokeName = stroke.attributeValue("name");
				str = stroke.attributeValue("width");
				if (str == null)
					width = 1.0f;
				else
					width = Float.parseFloat(str);
				cap = stroke.attributeValue("cap");
				join = stroke.attributeValue("join");
				miterlimit = stroke.attributeValue("miterlimit");
				str = stroke.attributeValue("dash");
				if (str == null) {
					uStroke = new UStroke(strokeName, width);
				} else {
					strs = str.split(",");
					dashes = new float[strs.length];
					int i;
					for (i = 0; i < strs.length; i++) {
						dashes[i] = Float.parseFloat(strs[i]);
					}
					uStroke = new UStroke(strokeName, width, dashes);
				}
				strokeMap.put(strokeName, uStroke);
			}
		}
		HashMap cursorMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_POINTCURSOR);
		Element cursors, cursor;
		String imgName;
		Point p;
		int x, y;
		it1 = root.elementIterator("cursors");
		while (it1.hasNext()) {
			cursors = (Element) it1.next();
			it2 = cursors.elementIterator("cursor");
			while (it2.hasNext()) {
				cursor = (Element) it2.next();
				name = cursor.attributeValue("name");
				str = cursor.attributeValue("type");
				if (str == null)
					style = Cursor.DEFAULT_CURSOR;
				else {
					style = Integer.parseInt(str);
				}
				imgName = cursor.attributeValue("imagename");
				str = cursor.attributeValue("x");
				if (str == null)
					x = 0;
				else
					x = Integer.parseInt(str);
				str = cursor.attributeValue("y");
				if (str == null)
					y = 0;
				else
					y = Integer.parseInt(str);
				if (imgName != null)
					p = new Point(x, y);
				else
					p = null;
				cursorMap.put(name, new UPointCursor(name, style, imgName, p));
			}
		}
		HashMap uistylesMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_UISTYLE);
		Element uistyles, uistyle;
		UUIStyle ui;
		it1 = root.elementIterator("uistyles");
		while (it1.hasNext()) {
			uistyles = (Element) it1.next();
			it2 = uistyles.elementIterator("uistyle");
			while (it2.hasNext()) {
				ui = new UUIStyle();
				uistyle = (Element) it2.next();
				ui.name = uistyle.attributeValue("name");
				str = uistyle.attributeValue("frontColor");
				ui.frontColor = getColorByName(str);
				if (ui.frontColor == null)
					ui.frontColor = getDefaultColor();
				str = uistyle.attributeValue("backColor");
				ui.backColor = getColorByName(str);
				if (ui.backColor == null)
					ui.backColor = getDefaultColor();
				str = uistyle.attributeValue("selectedFrontColor");
				ui.selectedFrontColor = getColorByName(str);
				if (ui.selectedFrontColor == null)
					ui.selectedFrontColor = getDefaultColor();
				str = uistyle.attributeValue("selectedBackColor");
				ui.selectedBackColor = getColorByName(str);
				if (ui.selectedBackColor == null)
					ui.selectedBackColor = getDefaultColor();
				str = uistyle.attributeValue("border");
				ui.border = getBorderByName(str);
				if (ui.border == null)
					ui.border = getDefaultStrokeBorder();
				str = uistyle.attributeValue("selectedBorder");
				ui.selectedBorder = getBorderByName(str);
				if (ui.selectedBorder == null)
					ui.selectedBorder = getDefaultStrokeBorder();
				str = uistyle.attributeValue("uiClassName");
				if (str != null) {
					try {
						ui.comUI = (ComponentUI) Class.forName(str)
								.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				uistylesMap.put(ui.name, ui);
			}
		}
		Element prompts, prompt;
		UPromptTemplate promptAttribute;
		HashMap promptMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_PROMPT);
		it1 = root.elementIterator("prompts");
		while (it1.hasNext()) {
			prompts = (Element) it1.next();
			it2 = prompts.elementIterator("prompt");
			while (it2.hasNext()) {
				prompt = (Element) it2.next();
				promptAttribute = new UPromptTemplate();
				name = prompt.attributeValue("name");
				promptAttribute.name = name;
				promptAttribute.info = prompt.attributeValue("info");
				promptAttribute.infoEng = prompt.attributeValue("infoEng");
				promptMap.put(name, promptAttribute);
			}
		}

	}

	public void initHandlerTemplate(HashMap paraTempMap, String fileName,
			String key) {
		Element root = getRootByXMLFileName(fileName);
		UHandlerTemplate handlerObj;
		Iterator it1, it2;
		String name;
		HashMap handlerMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_HANDLER);
		Element handler;
		it1 = root.elementIterator("handler");
		while (it1.hasNext()) {
			handler = (Element) it1.next();
			name = handler.attributeValue("name");
			handlerObj = newHandlerObject(handler);
			handlerMap.put(name, handlerObj);
		}
	}

	public void initFilterTemplate(HashMap paraTempMap, String fileName,
			String key) {

		FilterI obj = null;
		Element root = getRootByXMLFileName(fileName);
		Element filter;
		String name, className, parameter;
		Iterator it2;
		HashMap filterMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_FILTER);
		UFilterTemplate filterTemp;
		it2 = root.elementIterator("filter");
		while (it2.hasNext()) {
			filter = (Element) it2.next();
			filterTemp = new UFilterTemplate();
			filterTemp.name = filter.attributeValue("name");
			filterTemp.className = filter.attributeValue("className");
			filterTemp.parameter = filter.attributeValue("parameter");
			filterMap.put(filterTemp.name, filterTemp);
		}
	}

	public void initDictionaryMappingTemplate(HashMap paraTempMap,
			String fileName, String key) {

		FilterI obj = null;
		Element root = getRootByXMLFileName(fileName);
		Element mapping, dict, map;
		String name, className, parameter;
		Iterator it2, it3, it4;
		HashMap mappingMap = getTemplateMapInstance(paraTempMap,
				UConstants.KEY_STRING_DICTIONARYMAPPING);
		UFilterTemplate filterTemp;
		it2 = root.elementIterator("dictionarymaaping");
		String mappingName;
		UDictionaryMappingTemplate template;
		String dictName, value, mapValue;
		HashMap dictMap;
		while (it2.hasNext()) {
			mapping = (Element) it2.next();
			mappingName = mapping.attributeValue("name");
			template = (UDictionaryMappingTemplate) mappingMap.get(mappingName);
			if (template == null) {
				template = new UDictionaryMappingTemplate();
				mappingMap.put(mappingName, template);
				template.name = mappingName;
			}
			it3 = mapping.elementIterator("dictionary");
			while (it3.hasNext()) {
				dict = (Element) it3.next();
				dictName = dict.attributeValue("name");
				dictMap = template.dictionMap.get(dictName);
				if (dictMap == null) {
					dictMap = new HashMap();
					template.dictionMap.put(dictName, dictMap);
				}
				it4 = dict.elementIterator("map");
				while (it4.hasNext()) {
					map = (Element) it4.next();
					value = map.attributeValue("value");
					mapValue = map.attributeValue("mapValue");
					dictMap.put(value, mapValue);
				}
			}
		}
	}

	public void initTableTemplate(HashMap paraTempMap, String fileName,
			String key) {
		HashMap tableMap = getTemplateMapInstance(paraTempMap, key);
		UTableTemplate obj;
		Element tabs, tab;
		Iterator it1 = null;
		String name, tempStr;
		Element root = getRootByXMLFileName(fileName);

		it1 = root.elementIterator("table");

		while (it1.hasNext()) {
			tab = (Element) it1.next();
			// if (key.equals(UConstants.KEY_STRING_PRINTDATATABLE)) {
			// obj = newPrintTableTempletObject(tab);
			// } else {
			name = tab.attributeValue("name");
//			if(name.equals("insuranceFinanceInsuranceLifeProductRatioTable")) {
//				System.out.print("1111");
//			}
			obj = newTableTempletObject(tab);
			// }
			obj.name = name;
			String type = tab.attributeValue("type");
			obj.className = tab.attributeValue("className");
			obj.ruleName = tab.attributeValue("ruleName");
			obj.beanId = tab.attributeValue("beanId");
			obj.methodName = tab.attributeValue("methodName");
			obj.initMethodName = tab.attributeValue("initMethodName");
			obj.dataInitClassName = tab.attributeValue("dataInitClassName");
			obj.tableHead = tab.attributeValue("tableHead");
			obj.detailTemplateName = tab.attributeValue("detailTemplateName");
			tempStr = tab.attributeValue("pageDisplayNum");
			if (tempStr == null)
				obj.pageDisplayNum = obj.columnNum;
			else {
				obj.pageDisplayNum = Integer.parseInt(tempStr);
			}
			if (obj.className == null) {
				obj.className = (String) this.classMappingMap.get(type);
			}
			tempStr = tab.attributeValue("pageRowNum");
			if (tempStr == null)
				obj.pageRowNum = -1;
			else
				obj.pageRowNum = Integer.parseInt(tempStr);
			tempStr = tab.attributeValue("displayRowDetail");
			if (tempStr == null || tempStr.equals("false"))
				obj.isDisplayRowDetail = false;
			else
				obj.isDisplayRowDetail = true;
			tempStr = tab.attributeValue("mustCellSelect");
			if (tempStr == null || tempStr.equals("false"))
				obj.mustCellSelect = false;
			else
				obj.mustCellSelect = true;
			tempStr = tab.attributeValue("mustRowSelect");
			if (tempStr == null || tempStr.equals("false"))
				obj.mustRowSelect = false;
			else
				obj.mustRowSelect = true;

			tempStr = tab.attributeValue("isHeadCanSort");
			if (tempStr != null && tempStr.equals("false"))
				obj.isHeadCanSort = false;
			else
				obj.isHeadCanSort = true;
			tempStr = tab.attributeValue("autoResizeLastColumn");
			if (tempStr != null && tempStr.equals("true"))
				obj.autoResizeLastColumn = true;
			else
				obj.autoResizeLastColumn = false;
			tableMap.put(obj.name, obj);
		}
	}


	private String processEspecialSymbole(String s) {
		String o;
		int n, start, end;
		if (s == null || s.equals("") || s.indexOf("@n@") < 0) {
			return s;
		}
		o = "";
		n = s.length();
		start = 0;
		while (start < n) {
			end = s.indexOf("@n@", start);
			if (end > 0) {
				o += s.substring(start, end) + "\n";
				start = end + 3;
			} else {
				o += s.substring(start, n) + "\n";
				start = n;
			}
		}
		return o;
	}

	private UCellAttribute getCellAttribute(Element tab) {
		if (tab == null)
			return null;
		String str;
		UCellAttribute o = new UCellAttribute();
		setCellAttribute(o, tab);
		str = tab.attributeValue("row");
		if (str != null) {
			o.row = Integer.parseInt(str);
		}
		str = tab.attributeValue("col");
		if (str != null) {
			o.col = Integer.parseInt(str);
		}
		str = tab.attributeValue("horizontalAlignment");
		if (str != null) {
			if ("center".equals(str))
				o.horizontalAlignment = UConstants.ALIGNMENT_CENTER;
			else if ("left".equals(str))
				o.horizontalAlignment = UConstants.ALIGNMENT_LEFT;
			else
				o.horizontalAlignment = UConstants.ALIGNMENT_RIGHT;
		}
		str = tab.attributeValue("verticalAlignment");
		if (str != null) {
			if ("middle".equals(str))
				o.verticalAlignment = UConstants.ALIGNMENT_CENTER;
			else if ("bottom".equals(str))
				o.verticalAlignment = UConstants.ALIGNMENT_BOTTOM;
			else if ("top".equals(str))
				o.verticalAlignment = UConstants.ALIGNMENT_TOP;
		}
		return o;

	}

	private UCellAttribute setCellAttribute(UCellAttribute o, Element tab) {
		String str;
		FilterI filter;
		o.content = tab.attributeValue("content");
		o.enContent = tab.attributeValue("enContent");
		str = tab.attributeValue("row");
		if (str != null) {
			o.row = Integer.parseInt(str);
		}
		str = tab.attributeValue("col");
		if (str != null) {
			o.col = Integer.parseInt(str);
		}
		str = tab.attributeValue("x");
		if (str != null) {
			o.x = Integer.parseInt(str);
		}
		str = tab.attributeValue("y");
		if (str != null) {
			o.y = Integer.parseInt(str);
		}
		str = tab.attributeValue("w");
		if (str != null) {
			o.w = Integer.parseInt(str);
		}
		str = tab.attributeValue("h");
		if (str != null) {
			o.h = Integer.parseInt(str);
		}
		str = tab.attributeValue("horizontalAlignment");
		if (str != null) {
			o.horizontalAlignment = getTypeValueByString(str, "alignmentType");
		}
		str = tab.attributeValue("verticalAlignment");
		if (str != null) {
			o.verticalAlignment = getTypeValueByString(str, "alignmentType");
		}

		o.fontName = tab.attributeValue("font");
		o.frontColorName = tab.attributeValue("frontColor");
		str = tab.attributeValue("editable");
		if (str != null && str.equals("false"))
			o.editable = false;
		str = tab.attributeValue("border");
		o.borderName = str;
		o.backColorName = tab.attributeValue("backColor");

		str = tab.attributeValue("rowSpan");
		if (str != null) {
			o.rowSpan = Integer.parseInt(str);
		}
		str = tab.attributeValue("colSpan");
		if (str != null) {
			o.colSpan = Integer.parseInt(str);
		}
		str = tab.attributeValue("indent");
		if (str != null) {
			o.indent = Integer.parseInt(str);
		}
		str = tab.attributeValue("fieldClassName");
		if (str != null)
			o.fieldClassName = str;
		o.filter = tab.attributeValue("filter");
		o.filter1 = tab.attributeValue("filter1");
		str = tab.attributeValue("fixHeight");
		if (str != null) {
			o.fixHeight = Integer.parseInt(str);
		}
		return o;
	}

	private UTableTemplate newTableTempletObject(Element tab) {

		UTableTemplate obj = new UTableTemplate();
		Element e, e1, en;
		String str;
		Iterator it, it1;
		String filter;
		int i, rc, k, ii, row, col;
		HashMap tempMap, temp1Map;
		str = tab.attributeValue("withTotal");
		if (str == null || str.equals("false")) {
			obj.withTotal = false;
		} else {
			obj.withTotal = true;
		}
		obj.style = tab.attributeValue("style");
		if ((str = tab.attributeValue("showTable")) != null) {
			if ("false".equals(str))
				obj.showTable = false;
		}
		str = tab.attributeValue("rowNum");
		if (str != null) {
			obj.rowNum = Integer.parseInt(str);
		}
		str = tab.attributeValue("fontName");
		if (str != null) {
			obj.fontName = str;
		}
		str = tab.attributeValue("rowHeight");
		if (str != null) {
			obj.rowHeight = Integer.parseInt(str);
		}

		obj.reportTitle = tab.attributeValue("reportTitle");

		str = tab.attributeValue("singleRowSelect");

		str = tab.attributeValue("subTableNum");
		if (str != null) {
			obj.subTableNum = Integer.parseInt(str);
		}
		str = tab.attributeValue("sumColumnNum");
		if (str != null) {
			obj.sumColumnNum = Integer.parseInt(str);
		}
		str = tab.attributeValue("sumString");
		if (str != null) {
			obj.sumString = str;
		}
		en = tab.element("no");
		if (en != null) {
			obj.no = new UColumnTemplate();
			str = en.attributeValue("width");
			if (str != null) {
				obj.no.width = Integer.parseInt(str);
			}
			obj.no.fontName = en.attributeValue("font");
			obj.no.colorName = en.attributeValue("color");
		}
		en = tab.element("rowSpanNo");
		if (en != null) {
			obj.rowSpanNo = new UColumnTemplate();
			str = en.attributeValue("width");
			if (str != null) {
				obj.rowSpanNo.width = Integer.parseInt(str);
			}
			obj.rowSpanNo.fontName = en.attributeValue("font");
			obj.rowSpanNo.colorName = en.attributeValue("color");
			obj.rowSpanNo.rowspanMap = new HashMap();
		}
		str = tab.attributeValue("leftWidth");
		if (str != null) {
			obj.leftWidth = Integer.parseInt(str);
		}
		obj.itemFormClassName = tab.attributeValue("itemFormClassName");

		obj.title = getCellAttribute(tab.element("title"));
		obj.note = getCellAttribute(tab.element("note"));
		obj.renderClassName = tab.attributeValue("renderClassName");
		obj.rowSpecificRenderName = tab.attributeValue("rowSpecificRenderName");
		obj.columnNum = 0;
		it = tab.elementIterator("columns");
		if(it.hasNext()) {
			e = (Element) it.next();
			it1 = e.elementIterator("column");
			while (it1.hasNext()) {
				obj.columnNum++;
				e1 = (Element) it1.next();
			}
		}
		obj.columnTemplates = new UColumnTemplate[obj.columnNum];
		for (k = 0; k < obj.columnNum; k++) {
			obj.columnTemplates[k] = new UColumnTemplate();
		}
		if (obj.rowNum >= 1) {
			obj.cells = new UCellAttribute[obj.columnNum * obj.rowNum];
			for (k = 0; k < obj.columnNum * obj.rowNum; k++)
				obj.cells[k] = new UCellAttribute();
		}
		it = tab.elementIterator("columns");
		i = 0;
		int tc;
		Iterator it2;
		Element e2;
		if (it.hasNext()) {
			e = (Element) it.next();
			it1 = e.elementIterator("column");
			while (it1.hasNext()) {
				e1 = (Element) it1.next();
				str = e1.attributeValue("width");
				if (str != null) {
					ii = Integer.parseInt(str);
					obj.columnTemplates[i].width = ii;
				}
				obj.columnTemplates[i].beanFormMember = e1
						.attributeValue("beanFormMember");
				obj.columnTemplates[i].type = e1.attributeValue("type");
				obj.columnTemplates[i].href = e1.attributeValue("link");
				obj.columnTemplates[i].resource = e1.attributeValue("resource");
				obj.columnTemplates[i].panelTemplateName = e1
						.attributeValue("panelTemplateName");
				str = e1.attributeValue("popupMode");
				if (str != null && str.length() != 0)
					obj.columnTemplates[i].popupMode = str;
				str = e1.attributeValue("popupable");
				if (str != null && str.equals("true"))
					obj.columnTemplates[i].popupable = true;
				else
					obj.columnTemplates[i].popupable = false;

				// str = e1.attributeValue("fixHeight");
				// if (str != null) {
				// obj.columnTemplates[i].fixHeight = Integer.parseInt(str);
				// }
				obj.columnTemplates[i].paras = e1.attributeValue("paras");
				str = e1.attributeValue("horizontalAlignment");
				if (str != null) {
					obj.columnTemplates[i].horizontalAlignment = getTypeValueByString(
							str, "alignmentType");
				} else {
					obj.columnTemplates[i].horizontalAlignment = getTypeValueByString(
							"left", "alignmentType");
				}
				str = e1.attributeValue("maxLength");
				if (str != null) {
					ii = Integer.parseInt(str);
					obj.columnTemplates[i].maxLength = ii;
				} else
					obj.columnTemplates[i].maxLength = 0 - 1;
				str = e1.attributeValue("editable");
				if (str == null || str.equals("false")) {
					obj.columnTemplates[i].editable = false;
				} else
					obj.columnTemplates[i].editable = true;
				if (str != null && obj.rowNum >= 1) {
					for (k = 0; k < obj.rowNum; k++)
						if (str.equals("false")) {
							obj.cells[k * obj.columnNum + i].editable = false;
						} else {
							obj.cells[k * obj.columnNum + i].editable = true;
						}
				}
				obj.columnTemplates[i].className = e1
						.attributeValue("className");
				obj.columnTemplates[i].templateName = e1
						.attributeValue("templateName");
				str = e1.attributeValue("comType");
				if (str != null) {
					obj.columnTemplates[i].className = (String) classMappingMap
							.get(str);
					obj.columnTemplates[i].templateName = (String) templateMappingMap
							.get(str);

					// obj.columnTemplates[i].comType =
					// this.getTypeValueByString(
					// str, UConstants.TYPEDEF_COMPONENT_TYPE);
				}
				obj.columnTemplates[i].dictionary = e1
						.attributeValue("dictionary");
				filter = e1.attributeValue("filter");
				if (filter != null && obj.rowNum >= 1) {
					for (k = 0; k < obj.rowNum; k++)
						obj.cells[k * obj.columnNum + i].filter = filter;
				}
				filter = e1.attributeValue("filter1");
				if (filter != null && obj.rowNum >= 1) {
					for (k = 0; k < obj.rowNum; k++)
						obj.cells[k * obj.columnNum + i].filter1 = filter;
				}
				str = e1.attributeValue("fieldClassName");
				if (str != null && obj.rowNum >= 1) {
					for (k = 0; k < obj.rowNum; k++)
						obj.cells[k * obj.columnNum + i].fieldClassName = str;
				}
				str = e1.attributeValue("border");
				if (str != null && obj.rowNum >= 1) {
					for (k = 0; k < obj.rowNum; k++)
						obj.cells[k * obj.columnNum + i].borderName = str;
				}
				str = e1.attributeValue("font");
				obj.columnTemplates[i].fontName = str;
				str = e1.attributeValue("color");
				obj.columnTemplates[i].colorName = str;
				str = e1.attributeValue("border");
				obj.columnTemplates[i].borderName = str;
				str = e1.attributeValue("itemFormMember");
				if (str != null) {
					obj.columnTemplates[i].itemFormMember = str;
				}
				str = e1.attributeValue("itemPar");
				if (str != null) {
					obj.columnTemplates[i].itemPar = str;
				}
				str = e1.attributeValue("itemIndex");
				if (str != null) {
					obj.columnTemplates[i].itemIndex = new Integer(str);
				}
				if (obj.columnTemplates[i].name == null) {
					if (obj.columnTemplates[i].itemIndex == null) {
						obj.columnTemplates[i].name = obj.columnTemplates[i].itemFormMember;
					} else
						obj.columnTemplates[i].name = obj.columnTemplates[i].itemFormMember
								+ obj.columnTemplates[i].itemIndex;
				}
				str = e1.attributeValue("canSum");
				if (str == null || str.equals("false")) {
					obj.columnTemplates[i].canSum = false;
				} else {
					obj.columnTemplates[i].canSum = false;
				}
				obj.columnTemplates[i].format = e1.attributeValue("format");
				str = e1.attributeValue("exlout");
				if (str != null && str.equals("false"))
					obj.columnTemplates[i].exlout = false;
				else
					obj.columnTemplates[i].exlout = true;
				str = e1.attributeValue("visible");
				if (str != null && str.equals("false"))
					obj.columnTemplates[i].visible = false;
				else
					obj.columnTemplates[i].visible = true;
				obj.columnTemplates[i].filter = e1.attributeValue("filter");
				obj.columnTemplates[i].filter1 = e1.attributeValue("filter1");
				obj.columnTemplates[i].comparator = e1
						.attributeValue("comparator");
				obj.columnTemplates[i].validatorName = e1
						.attributeValue("validatorName");
				obj.columnTemplates[i].validatorClassName = e1
						.attributeValue("validatorClassName");
				obj.columnTemplates[i].validatorMsg = e1
						.attributeValue("validatorMsg");
				obj.columnTemplates[i].validatorParas = e1
						.attributeValue("validatorParas");
				str = e1.attributeValue("isDynamicVisible");
				if ("true".equals(str))
					obj.columnTemplates[i].isDynamicVisible = true;
				str = e1.attributeValue("isDimension");
				if ("true".equals(str))
					obj.columnTemplates[i].isDimension = true;
				str = e1.attributeValue("singleChoice");
				if ("true".equals(str))
					obj.columnTemplates[i].singleChoice = true;
				tc = 0;
				it2 = e1.elementIterator("addedData");
				while (it2.hasNext()) {
					e2 = (Element) it2.next();
					tc++;
				}
				if (tc > 0) {
					obj.columnTemplates[i].addedDatas = new ListOptionInfo[tc];
					tc = 0;
					it2 = e1.elementIterator("addedData");
					while (it2.hasNext()) {
						e2 = (Element) it2.next();
						obj.columnTemplates[i].addedDatas[tc] = new ListOptionInfo();
						obj.columnTemplates[i].addedDatas[tc].setLabel(e2
								.attributeValue("label"));
						obj.columnTemplates[i].addedDatas[tc].setValue(e2
								.attributeValue("value"));
						obj.columnTemplates[i].addedDatas[tc].setEnLabel(e2
								.attributeValue("enLabel"));
						tc++;
					}
				} else
					obj.columnTemplates[i].addedDatas = null;
				i++;
			}
		}
		rc = 0;
		it = tab.elementIterator("topItems");
		while (it.hasNext()) {
			e = (Element) it.next();
			it1 = e.elementIterator("item");
			while (it1.hasNext()) {
				rc++;
				e1 = (Element) it1.next();
			}
		}
		obj.topNum = rc / obj.columnNum;
		if (rc % obj.columnNum != 0) {
			obj.topNum++;
		}
		if (rc > 0) {
			obj.topStrings = new String[obj.topNum * obj.columnNum];
			obj.topEnStrings = new String[obj.topNum * obj.columnNum];
			obj.topItems = new UCellAttribute[obj.topNum * obj.columnNum];
			rc = 0;
			it = tab.elementIterator("topItems");
			while (it.hasNext()) {
				e = (Element) it.next();
				it1 = e.elementIterator("item");
				while (it1.hasNext()) {
					e1 = (Element) it1.next();
					obj.topStrings[rc] = processEspecialSymbole(e1
							.attributeValue("value"));
					obj.topEnStrings[rc] = processEspecialSymbole(e1
							.attributeValue("enValue"));
					obj.topItems[rc] = getCellAttribute(e1);
					rc++;
				}
			}
		}
		rc = 0;
		it = tab.elementIterator("bottomItems");
		while (it.hasNext()) {
			e = (Element) it.next();
			it1 = e.elementIterator("item");
			while (it1.hasNext()) {
				rc++;
				e1 = (Element) it1.next();
			}
		}
		obj.bottomNum = rc / obj.columnNum;
		if (rc % obj.columnNum != 0) {
			obj.bottomNum++;
		}
		if (rc > 0) {
			obj.bottomStrings = new String[obj.bottomNum * obj.columnNum];
			obj.bottomEnStrings = new String[obj.bottomNum * obj.columnNum];
			obj.bottomItems = new UCellAttribute[obj.bottomNum * obj.columnNum];
			rc = 0;
			it = tab.elementIterator("bottomItems");
			while (it.hasNext()) {
				e = (Element) it.next();
				it1 = e.elementIterator("item");
				while (it1.hasNext()) {
					e1 = (Element) it1.next();
					obj.bottomStrings[rc] = processEspecialSymbole(e1
							.attributeValue("value"));
					obj.bottomEnStrings[rc] = processEspecialSymbole(e1
							.attributeValue("enValue"));
					obj.bottomItems[rc] = getCellAttribute(e1);
					rc++;
				}
			}
		}
		rc = 0;
		it = tab.elementIterator("leftIndexItems");
		while (it.hasNext()) {
			e = (Element) it.next();
			it1 = e.elementIterator("item");
			while (it1.hasNext()) {
				rc++;
				e1 = (Element) it1.next();
			}
		}
		if (rc > 0) {
			obj.leftIndexs = new int[rc];
			obj.leftStrings = new String[rc];
			obj.leftEnStrings = new String[rc];
			rc = 0;
			it = tab.elementIterator("leftIndexItems");
			while (it.hasNext()) {
				e = (Element) it.next();
				it1 = e.elementIterator("item");
				while (it1.hasNext()) {
					e1 = (Element) it1.next();
					obj.leftIndexs[rc] = Integer.parseInt(e1
							.attributeValue("value"));
					obj.leftStrings[rc++] = processEspecialSymbole(e1
							.attributeValue("title"));
					obj.leftEnStrings[rc++] = processEspecialSymbole(e1
							.attributeValue("enTitle"));
				}
			}
		}
		it = tab.elementIterator("rowTemplates");
		while (it.hasNext()) {
			e1 = (Element) it.next();
			it1 = tab.elementIterator("rowTemplate");
			while (it1.hasNext()) {
				e1 = (Element) it.next();
				row = Integer.parseInt(e1.attributeValue("row"));
				str = e1.attributeValue("editable");
				if (str != null) {
					for (k = 0; k < obj.columnNum; k++)
						if (str.equals("false"))
							obj.cells[row * obj.columnNum + k].editable = false;
						else
							obj.cells[row * obj.columnNum + k].editable = true;
				}
			}
		}
		rc = 0;
		it = tab.elementIterator("cells");
		while (it.hasNext()) {
			e = (Element) it.next();
			it1 = e.elementIterator("cell");
			while (it1.hasNext()) {
				rc++;
				e1 = (Element) it1.next();
			}
		}
		if (rc > 0) {
			boolean isNew = false;
			int r = 0, c = 0;
			if (obj.cells == null) {
				obj.cells = new UCellAttribute[rc];
				isNew = true;
			}
			rc = 0;
			it = tab.elementIterator("cells");
			while (it.hasNext()) {
				e = (Element) it.next();
				it1 = e.elementIterator("cell");
				while (it1.hasNext()) {
					e1 = (Element) it1.next();
					if (isNew)
						obj.cells[rc] = getCellAttribute(e1);
					else {
						str = e1.attributeValue("row");
						if (str != null) {
							r = Integer.parseInt(str);
						}
						str = e1.attributeValue("col");
						if (str != null) {
							c = Integer.parseInt(str);
						}
						setCellAttribute(obj.cells[r * obj.columnNum + c], e1);
					}
					rc++;
				}
			}
		}
		e1 = tab.element("actionBar");
		if (e1 != null) {
			String tempStr;
			obj.actionBar = new UActionBarTemplate();
			tempStr = e1.attributeValue("allButton");
			if (tempStr != null && tempStr.equals("true")) {
				obj.actionBar.deleteRowButton = true;
				obj.actionBar.addNewRowButton = true;
				obj.actionBar.clearButton = true;
				obj.actionBar.okButton = true;
				obj.actionBar.cancelButton = true;
			}
			tempStr = e1.attributeValue("rowNum");
			if (tempStr != null && !tempStr.equals(""))
				obj.actionBar.rowNum = Integer.parseInt(tempStr);
			else
				obj.actionBar.rowNum = 1;
			tempStr = e1.attributeValue("deleteRowButton");
			if (tempStr == null || tempStr.equals("false"))
				obj.actionBar.deleteRowButton = false;
			else
				obj.actionBar.deleteRowButton = true;
			tempStr = e1.attributeValue("addNewRowButton");
			if (tempStr == null || tempStr.equals("false"))
				obj.actionBar.addNewRowButton = false;
			else
				obj.actionBar.addNewRowButton = true;
			tempStr = e1.attributeValue("clearButton");
			if (tempStr == null || tempStr.equals("false"))
				obj.actionBar.clearButton = false;
			else
				obj.actionBar.clearButton = true;
			tempStr = e1.attributeValue("okButton");
			if (tempStr == null || tempStr.equals("false"))
				obj.actionBar.okButton = false;
			else
				obj.actionBar.okButton = true;
			tempStr = e1.attributeValue("cancelButton");
			if (tempStr == null || tempStr.equals("false"))
				obj.actionBar.cancelButton = false;
			else
				obj.actionBar.cancelButton = true;
			Iterator iq = e1.elementIterator("queryDataField");
			if (iq.hasNext()) {
				obj.actionBar.queryDataField = new UQueryDataFieldTemplate();
				Element eq = (Element) iq.next();
				obj.actionBar.queryDataField.beanName = eq
						.attributeValue("beanName");
				obj.actionBar.queryDataField.className = eq
						.attributeValue("className");
				tempStr = eq.attributeValue("valueActionNum");
				if (tempStr == null || tempStr.equals(""))
					obj.actionBar.queryDataField.valueActionNum = 2;
				else
					obj.actionBar.queryDataField.valueActionNum = Integer
							.parseInt(tempStr);
				tempStr = eq.attributeValue("isNotDigit");
				if (tempStr != null && tempStr.equals("false"))
					obj.actionBar.queryDataField.isNotDigit = false;
				else
					obj.actionBar.queryDataField.isNotDigit = true;
				tempStr = eq.attributeValue("keyAction");
				if (tempStr != null && tempStr.equals("true"))
					obj.actionBar.queryDataField.keyAction = true;
				else
					obj.actionBar.queryDataField.keyAction = false;
					obj.actionBar.queryDataField.errPromptMsg = eq.attributeValue("errPromptMsg") ;
			} else
				obj.actionBar.queryDataField = null;

			str = e1.attributeValue("startEndType");
			if (str == null)
				obj.actionBar.startEndClassName = null;
			else
				obj.actionBar.startEndClassName = (String) this.classMappingMap
						.get(str);
			str = e1.attributeValue("rowArrange");
			if (str != null)
				obj.actionBar.rwoArrange = this.getTypeValueByString(str,
						UConstants.TYPEDEF_ROW_ARRANGE_TYPE);
			str = e1.attributeValue("layout");
			if (str != null)
				obj.actionBar.layout = this.getTypeValueByString(str,
						UConstants.TYPEDEF_ALIGNMENT_TYPE);
			str = e1.attributeValue("width");
			if (str != null)
				obj.actionBar.width = Integer.parseInt(str);
			str = e1.attributeValue("height");
			if (str != null)
				obj.actionBar.height = Integer.parseInt(str);

			obj.actionBar.labels = getLabels(e1);
			obj.actionBar.fields = getFields(e1);
			obj.actionBar.spinners = getSpinners(e1);
			obj.actionBar.comboBoxs = getComboBoxs(e1);
			obj.actionBar.comboBoxDates = getComboBoxDates(e1);
			obj.actionBar.buttons = getButtons(e1);
			obj.actionBar.visibleNames = e1.attributeValue("visibleNames");
			obj.actionBar.setVisibleString();
		} else {
			obj.actionBar = null;
		}
		rc = 0;
		it = tab.elementIterator("checkgroups");
		while (it.hasNext()) {
			e = (Element) it.next();
			rc++;
		}
		int rc1, kt;
		if (rc > 0) {
			obj.checkGroup = new int[rc][];
			it = tab.elementIterator("checkgroups");
			rc = 0;
			while (it.hasNext()) {
				e = (Element) it.next();
				rc1 = 0;
				it1 = e.elementIterator("element");
				while (it1.hasNext()) {
					e1 = (Element) it1.next();
					rc1++;
				}
				if (rc1 > 0) {
					obj.checkGroup[rc] = new int[rc1];
					rc1 = 0;
					it1 = e.elementIterator("element");
					while (it1.hasNext()) {
						e1 = (Element) it1.next();
						str = e1.attributeValue("name");
						if (str != null) {
							kt = 0;
							while (kt < obj.columnNum) {
								if (obj.columnTemplates[kt].itemFormMember != null
										&& obj.columnTemplates[kt].itemFormMember
												.equals(str)) {
									obj.checkGroup[rc][rc1] = kt;
									break;
								}
								kt++;
							}
							obj.checkGroup[rc][rc1] = kt;
						}
						rc1++;
					}
				}
				rc++;
			}
		}
		obj.sortList = null;
		SortColumnAttribute s;
		int scol;
		boolean sasc;
		Comparator scom;
		it = tab.elementIterator("sortItems");
		while (it.hasNext()) {
			e = (Element) it.next();
			it1 = e.elementIterator("item");
			while (it1.hasNext()) {
				if (obj.sortList == null)
					obj.sortList = new ArrayList<SortColumnAttribute>();
				e1 = (Element) it.next();
				str = e1.attributeValue("column");
				if (str != null && str.length() != 0)
					scol = Integer.parseInt(str);
				else
					scol = 0;
				str = e1.attributeValue("ascending");
				if (str != null && str.equals("false"))
					sasc = false;
				else
					sasc = true;
				str = e1.attributeValue("comparator");
				String tName = obj.columnTemplates[scol].comparator;
				scom = null;
				if (str != null && str.length() != 0)
					tName = str;
				String tClassName = null;
				if (tName != null)
					tClassName = getDefaultClassByType(tName);
				if (tClassName != null)
					try {
						scom = (Comparator<Object>) Class.forName(tClassName)
								.newInstance();
					} catch (Exception err) {
						err.printStackTrace();
						scom = null;
					}
				obj.sortList.add(new SortColumnAttribute(scol, sasc, scom));
			}
		}
		obj.statisticsItemList = null;
		TableStatisticsItemTemplate st;
		it = tab.elementIterator("statisticsItems");
		while (it.hasNext()) {
			e = (Element) it.next();
			if (obj.statisticsItemList == null)
				obj.statisticsItemList = new ArrayList<TableStatisticsItemTemplate>();
			st = new TableStatisticsItemTemplate();
			st.label = e.attributeValue("label");
			st.variables = e.attributeValue("variables");
			st.expression = e.attributeValue("expression");
			obj.statisticsItemList.add(st);
		}
		obj.getControlAttribute(tab);
		return obj;
	}

	public UButtonTemplate[] getButtons(Element tab) {
		int rc;
		Iterator it = null;
		Element e;
		UButtonTemplate[] buttons = null;
		rc = 0;
		String str;
		it = tab.elementIterator("button");
		while (it.hasNext()) {
			e = (Element) it.next();
			rc++;
		}
		if (rc > 0) {
			buttons = new UButtonTemplate[rc];
			it = tab.elementIterator("button");
			rc = 0;
			while (it.hasNext()) {
				e = (Element) it.next();
				buttons[rc] = new UButtonTemplate();
				buttons[rc].name = e.attributeValue("name");
				buttons[rc].content = e.attributeValue("content");
				buttons[rc].enContent = e.attributeValue("enContent");
				buttons[rc].cmd = e.attributeValue("cmd");
				if (buttons[rc].cmd == null)
					buttons[rc].cmd = buttons[rc].name;
				str = e.attributeValue("w");
				if (str != null)
					buttons[rc].w = Integer.parseInt(str);
				str = e.attributeValue("h");
				if (str != null)
					buttons[rc].h = Integer.parseInt(str);
				rc++;
			}
		}
		return buttons;
	}

	public UElementTemplate[] getLabels(Element tab) {
		int rc;
		Iterator it = null;
		Element e;
		UElementTemplate[] labels = null;
		rc = 0;
		it = tab.elementIterator("label");
		while (it.hasNext()) {
			e = (Element) it.next();
			rc++;
		}
		if (rc > 0) {
			labels = new UElementTemplate[rc];
			it = tab.elementIterator("label");
			rc = 0;
			while (it.hasNext()) {
				e = (Element) it.next();
				labels[rc] = new UButtonTemplate();
				labels[rc].name = e.attributeValue("name");
				labels[rc].text = e.attributeValue("text");
				labels[rc].enLabel = e.attributeValue("enLabel");
				rc++;
			}
		}
		return labels;
	}

	public UElementTemplate[] getComboBoxs(Element tab) {
		int rc;
		Iterator it = null;
		Element e;
		UElementTemplate[] comboBoxs = null;
		rc = 0;
		it = tab.elementIterator("comboBox");
		while (it.hasNext()) {
			e = (Element) it.next();
			rc++;
		}
		if (rc > 0) {
			comboBoxs = new UElementTemplate[rc];
			it = tab.elementIterator("comboBox");
			rc = 0;
			while (it.hasNext()) {
				e = (Element) it.next();
				comboBoxs[rc] = new UElementTemplate();
				comboBoxs[rc].name = e.attributeValue("name");
				comboBoxs[rc].dictionary = e.attributeValue("dictionary");
				comboBoxs[rc].text = e.attributeValue("text");
				comboBoxs[rc].enLabel = e.attributeValue("enLabel");
				rc++;
			}
		}
		return comboBoxs;
	}

	public UComboBoxDateTemplate[] getComboBoxDates(Element tab) {
		int rc;
		Iterator it = null;
		Element e;
		UComboBoxDateTemplate[] comboBoxDates = null;
		rc = 0;
		it = tab.elementIterator("comboBoxDate");
		while (it.hasNext()) {
			e = (Element) it.next();
			rc++;
		}
		if (rc > 0) {
			comboBoxDates = new UComboBoxDateTemplate[rc];
			it = tab.elementIterator("comboBoxDate");
			rc = 0;
			while (it.hasNext()) {
				e = (Element) it.next();
				comboBoxDates[rc] = new UComboBoxDateTemplate();
				comboBoxDates[rc].getSelfAttribute(e);
				rc++;
			}
		}
		return comboBoxDates;
	}

	public UTextFieldTemplate[] getFields(Element tab) {
		int rc;
		Iterator it = null;
		Element e;
		String str;
		UTextFieldTemplate[] fields = null;
		rc = 0;
		it = tab.elementIterator("field");
		while (it.hasNext()) {
			e = (Element) it.next();
			rc++;
		}
		if (rc > 0) {
			fields = new UTextFieldTemplate[rc];
			it = tab.elementIterator("field");
			rc = 0;
			while (it.hasNext()) {
				e = (Element) it.next();
				fields[rc] = new UTextFieldTemplate();
				fields[rc].name = e.attributeValue("name");
				fields[rc].text = e.attributeValue("text");
				fields[rc].enLabel = e.attributeValue("enLabel");
				str = e.attributeValue("maxLength");
				if (str != null)
					fields[rc].maxLength = new Integer(str);
				else
					fields[rc].maxLength = 10;
				str = e.attributeValue("editable");
				if (str != null && str.equals("false"))
					fields[rc].editable = false;
				else
					fields[rc].editable = true;
				rc++;
			}
		}
		return fields;
	}

	public USpinnerTemplate[] getSpinners(Element tab) {
		int rc;
		Iterator it = null;
		Element e;
		String str;
		USpinnerTemplate[] spinners = null;
		rc = 0;
		it = tab.elementIterator("spinner");
		while (it.hasNext()) {
			e = (Element) it.next();
			rc++;
		}
		if (rc > 0) {
			spinners = new USpinnerTemplate[rc];
			it = tab.elementIterator("spinner");
			rc = 0;
			while (it.hasNext()) {
				e = (Element) it.next();
				spinners[rc] = new USpinnerTemplate();
				spinners[rc].name = e.attributeValue("name");
				spinners[rc].text = e.attributeValue("text");
				spinners[rc].enLabel = e.attributeValue("enLabel");
				str = e.attributeValue("value");
				if(str != null)
					spinners[rc].value = Integer.parseInt(str);
				str = e.attributeValue("minimum");
				if(str != null)
					spinners[rc].minimum = Integer.parseInt(str);
				str = e.attributeValue("maximum");
				if(str != null)
					spinners[rc].maximum = Integer.parseInt(str);
				str = e.attributeValue("stepSize");
				if(str != null)
					spinners[rc].stepSize = Integer.parseInt(str);
				spinners[rc].listStr = e.attributeValue("listStr");
				str = e.attributeValue("editable");
				if (str != null && str.equals("false"))
					spinners[rc].editable = false;
				else
					spinners[rc].editable = true;
				rc++;
			}
		}
		return spinners;
	}
	
	private boolean isComponent(int type) {
		if (type == UConstants.COMPONENT_LOCATE
				|| type == UConstants.COMPONENT_SHIFT
				|| type == UConstants.COMPONENT_DEFINE) {
			return false;
		} else
			return true;
	}

	public List getPanelList(String key) {
		HashMap temp = (HashMap) constantMap.get(UConstants.TYPEDEF_MAP_KEY);
		String keyValue = (String) temp.get(key);
		if (keyValue == null)
			return null;
		HashMap panelMap = (HashMap) templateMap.get(keyValue);
		if (panelMap == null)
			return null;
		UNameObjectPar par;
		List list = new ArrayList();
		Set set = panelMap.keySet();
		String name;
		Iterator it = set.iterator();
		while (it.hasNext()) {
			name = (String) it.next();
			par = new UNameObjectPar(name, panelMap.get(name));
			list.add(par);
		}
		return list;
	}

	public void initPanelTemplate(HashMap paraTempMap, String fileName,
			String key) {
		UPanelTemplate obj;
		Element panel;
		Iterator it1;
		String name;
		Element root = getRootByXMLFileName(fileName);
		if (root == null) {
			System.out.println(fileName);
			return;
		}
		HashMap panelMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("panel");
		while (it1.hasNext()) {
			panel = (Element) it1.next();
			name = panel.attributeValue("name");
			obj = newPanelTempletObject(panel, key);
			panelMap.put(name, obj);
		}
	}

	public void initPagePanelTemplate(HashMap paraTempMap, String fileName,
			String key) {

	}

	public HashMap getConstantsMap() {
		return this.constantMap;
	}

	private UPanelTemplate newPanelTempletObject(Element panel, String key) {
		if (key.equals(UConstants.KEY_STRING_DIALOG)) {
			UDialogTemplate obj = new UDialogTemplate();
			setPanelTemplet(panel, obj);
			String str;
			str = panel.attributeValue("xo");
			if (str != null) {
				obj.xo = Integer.parseInt(str);
			}
			str = panel.attributeValue("yo");
			if (str != null) {
				obj.yo = Integer.parseInt(str);
			}
			str = panel.attributeValue("cancel");
			if (str != null) {
				obj.cancel = str;
			}
			str = panel.attributeValue("ok");
			if (str != null) {
				obj.ok = str;
			}
			obj.buttons = getButtons(panel);
			obj.imageName = panel.attributeValue("imageName");
			obj.bgColorName = panel.attributeValue("bgColorName");
			return obj;
		} else {
			UPanelTemplate obj = new UPanelTemplate();
			setPanelTemplet(panel, obj);
			return obj;
		}
	}

	private void setPanelTemplet(Element panel, UPanelTemplate obj) {
		HashMap temp;
		Element e, e1, e2;
		String str, istr, typeString;
		Iterator it, it1, it2;
		int i, tc;
		obj.groupElementTemplate = new UGroupElementTemplate();
		obj.name = panel.attributeValue("name");
		typeString = panel.attributeValue("type");
		if (typeString != null) {
			obj.type = this.getTypeValueByString(typeString,
					UConstants.TYPEDEF_COMPONENT_TYPE);
		}
		obj.className = panel.attributeValue("className");
		if (obj.className == null) {
			obj.className = (String) this.classMappingMap.get(typeString);
		}
		obj.title = processEspecialSymbole(panel.attributeValue("title"));
		obj.enTitle = processEspecialSymbole(panel.attributeValue("enTitle"));
		str = panel.attributeValue("titleHeight");
		if (str != null) {
			obj.titleHeight = Integer.parseInt(str);
		}
		obj.dataFormClassName = panel.attributeValue("dataFormClassName");
		obj.handlerClassName = panel.attributeValue("handlerClassName");
		obj.frontHandlerClassName = panel
				.attributeValue("frontHandlerClassName");

		obj.paperName = panel.attributeValue("paperName");
		obj.userTaskGroupName = panel.attributeValue("userTaskGroupName");
		obj.defaultUserTaskName = panel.attributeValue("defaultUserTaskName");
		obj.defaultMethodName = panel.attributeValue("defaultMethodName");
		str = panel.attributeValue("canScrolling");
		if (str != null && str.equals("false"))
			obj.canScrolling = false;
		else
			obj.canScrolling = true;

		tc = 0;
		it = panel.elementIterator("toolAction");
		while (it.hasNext()) {
			e = (Element) it.next();
			tc++;
		}
		if (tc > 0) {
			obj.toolActions = new String[tc];
			tc = 0;
			it = panel.elementIterator("toolAction");
			while (it.hasNext()) {
				e = (Element) it.next();
				obj.toolActions[tc] = e.attributeValue("name");
				tc++;
			}
		}
		str = panel.attributeValue("width");
		if (str != null) {
			obj.width = Integer.parseInt(str);
		}
		str = panel.attributeValue("height");
		if (str != null) {
			obj.height = Integer.parseInt(str);
		}
		obj.groupElementTemplate.layoutMode = panel
				.attributeValue("locateMode");
		if (obj.groupElementTemplate.layoutMode == null)
			obj.groupElementTemplate.layoutMode = UConstants.LAYOUTMODE_SHIFT;
		str = panel.attributeValue("align");
		if (str != null)
			obj.groupElementTemplate.align = this.getTypeValueByString(str,
					"alignmentType");

		str = panel.attributeValue("dispTitleMode");
		if (str != null)
			obj.groupElementTemplate.dispTitleMode = str;
		obj.groupElementTemplate.className = panel
				.attributeValue("layoutClassName");
		str = panel.attributeValue("colNum");
		if (str != null && str.length() != 0)
			obj.groupElementTemplate.colNum = Integer.parseInt(str);
		str = panel.attributeValue("rowNum");
		if (str != null && str.length() != 0)
			obj.groupElementTemplate.rowNum = Integer.parseInt(str);

		str = panel.attributeValue("divw");
		if (str != null) {
			obj.groupElementTemplate.divw = Integer.parseInt(str);
		}
		str = panel.attributeValue("divh");
		if (str != null) {
			obj.groupElementTemplate.divh = Integer.parseInt(str);
		}
		str = panel.attributeValue("divsw");
		if (str != null) {
			obj.groupElementTemplate.divsw = Integer.parseInt(str);
		}
		str = panel.attributeValue("visible");
		if (str != null && str.equals("false"))
			obj.groupElementTemplate.visible = false;
		else
			obj.groupElementTemplate.visible = true;
		setGroupElement(obj.groupElementTemplate, panel.element("components"));
		
		// 2010-3-9 liuyang added
		// the icon path of the tab
		
		str = panel.attributeValue("addedCompontBeanName");
		if(str != null && !isClient) {
			SubComponentsGeneratorI pi = (SubComponentsGeneratorI)ApplicationContextHandle.getBean(str);
			if(pi != null) {
				pi.addSubComponentUElementTemplates(obj.name, obj.groupElementTemplate.componentTemplates);
				obj.groupElementTemplate.componentNum = obj.groupElementTemplate.componentTemplates.size();
			}
		}
		str = panel.attributeValue("addedCompontHandlerName");
		if(str != null && isClient ) {
			try {
				SubComponentsGeneratorI pi = (SubComponentsGeneratorI)Class.forName(str).newInstance();
				if(pi != null) {
					pi.addSubComponentUElementTemplates(obj.name, obj.groupElementTemplate.componentTemplates);
					obj.groupElementTemplate.componentNum = obj.groupElementTemplate.componentTemplates.size();
				}
			}catch(Exception ee) {
				ee.printStackTrace();
			}
		}
		obj.tabIconName = panel.attributeValue("tabIconName");
		tc = 0;
		it = panel.elementIterator("closePanel");
		while (it.hasNext()) {
			e = (Element) it.next();
			tc++;
		}
		if (tc > 0) {
			obj.closePanels = new String[tc];
			tc = 0;
			it = panel.elementIterator("closePanel");
			while (it.hasNext()) {
				e = (Element) it.next();
				obj.closePanels[tc] = e.attributeValue("name");
				tc++;
			}
		}
		str = panel.attributeValue("isOnTimer");
		if (str != null && str.equals("true")) {
			obj.isOnTimer = true;
		} else
			obj.isOnTimer = false;
		obj.menu = panel.attributeValue("menu");
		str = panel.attributeValue("isTimeControlAction");
		if (str != null && str.equals("true")) {
			obj.isTimeControlAction = true;
		} else
			obj.isTimeControlAction = false;

		obj.groupElementTemplate.cellStruct = getCellStruct(panel
				.element("cell"));
	}

	private UCellStruct getCellStruct(Element e) {
		String type;
		Iterator it;
		Element e1;
		UCellStruct cellStruct = null, c1;
		if (e == null)
			return null;
		String str;
		type = e.attributeValue("type");
		if (type.equals("com")) {
			cellStruct = new UCellStructCom();
		} else {
			if (type.equals("row"))
				cellStruct = new UCellStructRow();
			else
				cellStruct = new UCellStructColumn();
		}
		cellStruct.setComName(e.attributeValue("name"));
		str = e.attributeValue("col");
		if (str != null && str.length() != 0)
			cellStruct.setCol(new Integer(str).intValue());
		str = e.attributeValue("row");
		if (str != null && str.length() != 0)
			cellStruct.setRow(new Integer(str).intValue());
		str = e.attributeValue("colNum");
		if (str != null && str.length() != 0)
			cellStruct.setColNum(new Integer(str).intValue());
		str = e.attributeValue("rowNum");
		if (str != null && str.length() != 0)
			cellStruct.setRowNum(new Integer(str).intValue());
		it = e.elementIterator("cell");
		List<UCellStruct> cellList = null;
		while (it.hasNext()) {
			e1 = (Element) it.next();
			c1 = getCellStruct(e1);
			if (c1 != null) {
				if (cellList == null)
					cellList = new ArrayList<UCellStruct>();
				cellList.add(c1);
			}
		}
		cellStruct.setCellList(cellList);
		return cellStruct;
	}

	public UElementTemplate getElementTemplate(Element e) {
		UElementTemplate obj = null;
		Iterator it, it1;
		Element e1;
		int i, tc;
		String typeString, istr, str;
		HashMap temp;
		int typeInt = 0;
		typeString = e.attributeValue("type");
		if (typeString != null) {
			temp = (HashMap) constantMap.get("componentType");
			istr = (String) temp.get(typeString);
			if (istr != null)
				typeInt = Integer.parseInt(istr);
		}
		String templateClassName = (String) templateMappingMap.get(typeString);
		if (templateClassName != null) {
			try {
				obj = (UElementTemplate) Class.forName(templateClassName)
						.newInstance();
			} catch (Exception et) {
				et.printStackTrace();
			}
		} else {
			obj = new UElementTemplate();
		}
		obj.getSelfAttribute(e);
		obj.type = typeInt;
		obj.typeString = typeString;
		temp = (HashMap) constantMap.get(UConstants.TYPEDEF_MAP_KEY);
		obj.mapKey = (String) temp.get(typeString);
		obj.name = e.attributeValue("name");
		obj.className = e.attributeValue("className");
		// str = e.attributeValue("className");
		// if (str != null) {
		// obj.className = str;
		// } else {
		// obj.className = (String) classMappingMap.get(typeString);
		// }
		obj.templateName = e.attributeValue("templateName");
		obj.templateMaker = e.attributeValue("templateMaker");
		obj.xStr = e.attributeValue("x");
		obj.yStr = e.attributeValue("y");
		obj.wStr = e.attributeValue("w");
		obj.hStr = e.attributeValue("h");
		obj.text = processEspecialSymbole(e.attributeValue("text"));
		obj.enLabel = processEspecialSymbole(e.attributeValue("enLabel"));
		str = e.attributeValue("horizontalAlignment");
		if (str != null) {
			obj.horizontalAlignment = getTypeValueByString(str, "alignmentType");
		}
		str = e.attributeValue("verticalAlignment");
		if (str != null) {
			obj.verticalAlignment = getTypeValueByString(str, "alignmentType");
		}
		str = e.attributeValue("border");
		if (str != null)
			obj.borderName = str;
		str = e.attributeValue("editable");
		if (str != null && str.equals("false")) {
			obj.editable = false;
		} else
			obj.editable = true;
		str = e.attributeValue("visible");
		if (str != null && str.equals("false")) {
			obj.visible = false;
		} else {
			obj.visible = true;
		}
		str = e.attributeValue("enable");
		if (str != null && str.equals("false")) {
			obj.enable = false;
		} else {
			obj.enable = true;
		}
		obj.uiStyleName = e.attributeValue("uiStyle");
		obj.fontName = e.attributeValue("font");
		obj.colorName = e.attributeValue("color");
		obj.backgroundName = e.attributeValue("backgroundName");
		obj.dataFormMember = e.attributeValue("dataFormMember");
		obj.filter = e.attributeValue("filter");
		obj.filterParameter = e.attributeValue("filterParameter");
		obj.filter1 = e.attributeValue("filter1");
		obj.filterParameter1 = e.attributeValue("filterParameter1");
		str = e.attributeValue("arrangeType");
		if (str != null)
			obj.arrangeType = getTypeValueByString(str, "arrangeType");
		ListOptionInfo lp;
		obj.addedDatas = null;
		it1 = e.elementIterator("addedData");
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			lp = new ListOptionInfo();
			lp.setLabel(e1.attributeValue("label"));
			lp.setEnLabel(e1.attributeValue("enLabel"));
			lp.setValue(e1.attributeValue("value"));
			if (obj.addedDatas == null)
				obj.addedDatas = new ArrayList();
			obj.addedDatas.add(lp);
		}
		obj.event = null;
		String strt;
		it1 = e.elementIterator("event");
		UEventAttribute ea;
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			strt = e1.attributeValue("type");
			if (obj.event == null)
				obj.event = new ArrayList();
			ea = new UEventAttribute();
			ea.name = strt;
			strt = e1.attributeValue("server");
			if (strt != null && strt.equalsIgnoreCase("false")) {
				ea.server = false;
			} else
				ea.server = true;
			obj.event.add(ea);
		}
		obj.menu = e.attributeValue("menu");
		str = e.attributeValue("layout");
		if (str != null)
			obj.layout = this.getTypeValueByString(str, "alignmentType");
		String paraName, paraValue;
		it1 = e.elementIterator("parameters");
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			if (obj.parameters == null) {
				obj.parameters = new FHashMap();
			}
			paraName = e1.attributeValue("name");
			paraValue = e1.attributeValue("value");
			obj.parameters.putData(paraName, paraValue);
		}
		str = e.attributeValue("left");
		if (str != null)
			obj.left = Integer.parseInt(str);
		else
			obj.left = 0;
		str = e.attributeValue("right");
		if (str != null)
			obj.right = Integer.parseInt(str);
		else
			obj.right = 0;
		str = e.attributeValue("top");
		if (str != null)
			obj.top = Integer.parseInt(str);
		else
			obj.top = 0;
		str = e.attributeValue("bottom");
		if (str != null)
			obj.bottom = Integer.parseInt(str);
		else
			obj.bottom = 0;
		obj.dictionary = e.attributeValue("dictionary");
		obj.beanName = e.attributeValue("beanName");
		str = e.attributeValue("addSelectItem");
		if (str == null || str.equals("false"))
			obj.addSelectItem = false;
		else
			obj.addSelectItem = true;
		str = e.attributeValue("addAllItem");
		if (str == null || str.equals("false"))
			obj.addAllItem = false;
		else
			obj.addAllItem = true;
		str = e.attributeValue("addEmptyItem");
		if (str == null || str.equals("false"))
			obj.addEmptyItem = false;
		else
			obj.addEmptyItem = true;

		str = e.attributeValue("setFirstItem");
		if (str == null || str.equals("true"))
			obj.setFirstItem = true;
		else
			obj.setFirstItem = false;

		str = e.attributeValue("returnPanel");
		if (str == null)
			obj.returnPanel = 0;
		else if (str.equals("1"))
			obj.returnPanel = 1;
		else
			obj.returnPanel = -1;

		str = e.attributeValue("gridx");
		if (str != null) {
			obj.gridx = Integer.parseInt(str);
		}
		str = e.attributeValue("gridy");
		if (str != null) {
			obj.gridy = Integer.parseInt(str);
		}
		str = e.attributeValue("gridwidth");
		if (str != null) {
			obj.gridwidth = Integer.parseInt(str);
		}
		str = e.attributeValue("gridheight");
		if (str != null) {
			obj.gridheight = Integer.parseInt(str);
		}
		str = e.attributeValue("weightx");
		if (str != null) {
			obj.weightx = Double.parseDouble(str);
		}
		str = e.attributeValue("weighty");
		if (str != null) {
			obj.weighty = Double.parseDouble(str);
		}
		str = e.attributeValue("fill");
		obj.fill = this.getTypeValueByString(str, "gridBagFillType");
		str = e.attributeValue("anchor");
		obj.anchor = this.getTypeValueByString(str, "gridBagAnchorType");
		obj.validatorName = e.attributeValue("validatorName");
		obj.validatorClassName = e.attributeValue("validatorClassName");
		obj.validatorMsg = e.attributeValue("validatorMsg");
		obj.validatorParas = e.attributeValue("validatorParas");
		obj.iconName = e.attributeValue("icon");
		obj.queryName = e.attributeValue("queryName");
		str = e.attributeValue("colNum");
		if (str != null) {
			obj.colNum = Integer.parseInt(str);
		}
		str = e.attributeValue("rowNum");
		if (str != null) {
			obj.rowNum = Integer.parseInt(str);
		}
		str = e.attributeValue("lineNo");
		if (str != null) {
			obj.lineNo = Integer.parseInt(str);
		}
		str = e.attributeValue("multiple");
		if (str != null) {
			obj.multiple = Boolean.parseBoolean(str);
		} else {
			if ("formDataTable".equals(obj.typeString))
				obj.multiple = true;
			else
				obj.multiple = false;
		}
		str = e.attributeValue("dlgSelect");
		if (str != null) {
			obj.dlgSelect = Boolean.parseBoolean(str);
		} else {
			if ("formDataTable".equals(obj.typeString))
				obj.dlgSelect = true;
			else
				obj.dlgSelect = false;
		}
		str = e.attributeValue("groupNo");
		if (str != null) {
			obj.groupNo = Integer.parseInt(str);
		}
		return obj;
	}

	private void setGroupElement(UGroupElementTemplate obj, Element ge) {
		Iterator it, it1;
		Element e, e1;
		int i, tc;
		String typeString, istr, str;
		HashMap temp;
		UGroupElementTemplate tp;
		if (ge == null)
			return;
		str = ge.attributeValue("locateMode");
		if (str != null)
			obj.layoutMode = str;
		str = ge.attributeValue("dispTitleMode");
		if (str != null)
			obj.dispTitleMode = str;
		obj.className = ge.attributeValue("layoutClassName");
		str = ge.attributeValue("divw");
		if (str != null) {
			obj.divw = Integer.parseInt(str);
		}
		str = ge.attributeValue("divh");
		if (str != null) {
			obj.divh = Integer.parseInt(str);
		}
		str = ge.attributeValue("divsw");
		if (str != null) {
			obj.divsw = Integer.parseInt(str);
		}
		obj.xStr = ge.attributeValue("x");
		obj.yStr = ge.attributeValue("y");
		obj.wStr = ge.attributeValue("w");
		obj.hStr = ge.attributeValue("h");
		obj.componentNum = 0;
		it = ge.elementIterator();
		while (it.hasNext()) {
			e = (Element) it.next();
			obj.componentNum++;
		}
		obj.componentTemplates = new ArrayList();
		it = ge.elementIterator();
		while (it.hasNext()) {
			e = (Element) it.next();
			if (e.getName().equals("components")) {
				tp = new UGroupElementTemplate();
				setGroupElement(tp, e);
				obj.componentTemplates.add(tp);
			} else {
				obj.componentTemplates.add(getElementTemplate(e));
			}
		}
		if (obj != null)
			obj.getIntValueFromString();
	}

	public int getTypeValueByString(String str, String key) {
		HashMap temp = (HashMap) constantMap.get(key);
		String s = (String) temp.get(str);
		if (s == null)
			return -1;
		else
			return Integer.parseInt(s);
	}

	public List getTypeList(String key) {
		HashMap temp = (HashMap) constantMap.get(key);
		Set set = temp.keySet();
		List list = new ArrayList();
		Iterator it = set.iterator();
		String name;
		while (it.hasNext()) {
			name = (String) it.next();
			list.add(new ListOptionInfo((String) temp.get(name), name));
		}
		return list;
	}

	private UHandlerTemplate newHandlerObject(Element handler) {

		UHandlerTemplate obj = new UHandlerTemplate();
		Element e, e1;
		String str;
		Iterator it, it1;
		int i = 0;
		obj.className = handler.attributeValue("className");
		try {
			obj.instance = (UHandlerI) Class.forName(obj.className)
					.newInstance();
		} catch (Exception et) {
			et.printStackTrace();
		}
		obj.commandMap = new HashMap();
		it = handler.elementIterator("commands");
		i = 0;
		Iterator iit, iit1;
		Element ee, ee1;
		String paraName, paraValue;
		UCommandTemplate commandTemplate;
		obj.commandMap = new HashMap();
		while (it.hasNext()) {
			e = (Element) it.next();
			it1 = e.elementIterator("command");
			while (it1.hasNext()) {
				e1 = (Element) it1.next();
				commandTemplate = new UCommandTemplate();
				commandTemplate.name = e1.attributeValue("name");
				commandTemplate.method = e1.attributeValue("method");
				commandTemplate.service = e1.attributeValue("service");
				commandTemplate.paras = new FHashMap();
				iit = e1.elementIterator("parameters");
				while (iit.hasNext()) {
					ee = (Element) iit.next();
					iit1 = ee.elementIterator("parameter");
					while (iit1.hasNext()) {
						ee1 = (Element) iit1.next();
						paraName = ee1.attributeValue("name");
						paraValue = ee1.attributeValue("value");
						commandTemplate.paras.putData(paraName, paraValue);
					}
				}
				obj.commandMap.put(commandTemplate.name, commandTemplate);
			}
		}
		return obj;
	}

	private UBloackBaseTemplate getBlockTemplateObject(String str, int key) {
		if (key < 0)
			return new UParagraphTemplate();
		UBloackBaseTemplate o = null;
		if (str != null)
			o = (UBloackBaseTemplate) getTemplate(key + "", str);
		if (o == null) {
			o = new UParagraphTemplate();
		}
		return o;
	}

	private UBlockContent newBlockContent(Element e) {
		UBlockContent o = new UBlockContent();
		setBlockContent(o, e);
		return o;
	}

	private void setBlockContent(UBlockContent o, Element e) {
		String typeString, str, className;
		int typeMapKey;
		o.name = e.attributeValue("name");
		typeString = e.attributeValue("type");
		typeMapKey = 0;
		if (typeString != null) {
			o.type = getTypeValueByString(typeString, "componentType");
			typeMapKey = getTypeValueByString(typeString,
					UConstants.TYPEDEF_MAP_KEY);
		}

		str = e.attributeValue("templateName");
		o.template = getBlockTemplateObject(str, typeMapKey);
		str = e.attributeValue("className");
		if (str == null) {
			str = (String) classMappingMap.get(typeString);
		}
		try {
			o.object = (UContentElementI) Class.forName(str).newInstance();
			if (o.template != null) {
				o.object.setTemplate(o.template);
			}
		} catch (Exception et) {
			et.printStackTrace();
		}
		str = e.attributeValue("text");
		if (str != null) {
			o.object.setData(str);
		}
		str = e.attributeValue("x");
		if (str != null) {
			o.template.sX = str;
		}
		str = e.attributeValue("y");
		if (str != null) {
			o.template.sY = str;
		}
		str = e.attributeValue("height");
		if (str != null) {
			o.template.sHeight = str;
		}
		str = e.attributeValue("width");
		if (str != null) {
			o.template.sWidth = str;
		}
		o.dataFormMember = e.attributeValue("dataFormMember");
		o.paras = new HashMap();
		Iterator it2 = e.elementIterator("parameter");
		Element ep;
		while (it2.hasNext()) {
			ep = (Element) it2.next();
			o.paras.put(ep.attributeValue("name"), ep.attributeValue("value"));
		}
	}

	private UParagraphContent newParagraphContent(Element e) {
		String typeString, str, className;
		int typeMapKey;
		UParagraphContent o = new UParagraphContent();
		setBlockContent(o, e);
		o.text = e.attributeValue("text");
		// o.filePath = e.attributeValue("filePath");
		o.backgroundColorName = e.attributeValue("backgroundColor");
		str = e.attributeValue("horizontalAlignment");
		if (str != null) {
			o.horizontalAlignment = getTypeValueByString(str, "alignmentType");
		}
		str = e.attributeValue("newPage");
		if (str != null) {
			o.newPage = str;
		}
		return o;
	}

	public void initDocumentTemplate(HashMap paraTempMap, String fileName,
			String key) {
		UDocumentTemplate obj;
		Element doc, para;
		Iterator it1, it2;
		String name, str, typeString;
		int typeMapKey;
		Element root = getRootByXMLFileName(fileName);
		HashMap docMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("document");
		while (it1.hasNext()) {
			doc = (Element) it1.next();
			obj = new UDocumentTemplate();
			name = doc.attributeValue("name");
			str = doc.attributeValue("paper");
			if (str != null) {
				obj.paperTemplate = (UPaperTemplate) getTemplate(
						UConstants.MAPKEY_PAPER, str);
			} else
				obj.paperTemplate = new UPaperTemplate();
			typeString = doc.attributeValue("type");
			typeMapKey = 0;
			if (typeString != null) {
				obj.type = getTypeValueByString(typeString, "componentType");
				typeMapKey = getTypeValueByString(typeString,
						UConstants.TYPEDEF_MAP_KEY);
			}
			str = doc.attributeValue("className");
			if (str == null) {
				str = (String) classMappingMap.get(typeString);
			}
			obj.className = str;
			obj.dataFormClassName = doc.attributeValue("dataFormClassName");
			obj.handlerClassName = doc.attributeValue("handlerClassName");
			obj.ruleName = doc.attributeValue("ruleName");
			obj.beanId = doc.attributeValue("beanId");
			obj.methodName = doc.attributeValue("methodName");
			obj.initMethodName = doc.attributeValue("initMethodName");
			obj.dataInitClassName = doc.attributeValue("dataInitClassName");
			obj.title = getCellAttribute(doc.element("title"));
			str = doc.attributeValue("pageNumber");
			if (str != null)
				obj.pageNumberTemplate = (UPageNumberTemplate) this
						.getTemplate(UConstants.MAPKEY_PAGENUMBER, str);
			else
				obj.pageNumberTemplate = new UPageNumberTemplate();
			obj.contentNum = 0;
			it2 = doc.elementIterator("paragraph");
			while (it2.hasNext()) {
				para = (Element) it2.next();
				obj.contentNum++;
			}
			int i = 0;
			obj.contents = new UParagraphContent[obj.contentNum];
			if (obj.contentNum != 0) {
				it2 = doc.elementIterator("paragraph");
				while (it2.hasNext()) {
					para = (Element) it2.next();
					obj.contents[i] = newParagraphContent(para);
					i++;
				}
			}

			str = doc.attributeValue("numPerPage");
			if (str != null)
				obj.numPerPaper = Integer.parseInt(str);
			docMap.put(name, obj);
		}
	}

	public void initSheetTemplate(HashMap paraTempMap, String fileName,
			String key) {
		USheetTemplate obj;
		Element sheet, block;
		Iterator it1, it2;
		String name, str, typeString;
		int typeMapKey;
		Element root = getRootByXMLFileName(fileName);
		HashMap docMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("sheet");
		while (it1.hasNext()) {
			sheet = (Element) it1.next();
			obj = new USheetTemplate();
			name = sheet.attributeValue("name");
			obj.name = name;
			typeString = sheet.attributeValue("type");
			typeMapKey = 0;
			if (typeString != null) {
				// obj. = getTypeValueByString(typeString, "componentType");
				typeMapKey = getTypeValueByString(typeString,
						UConstants.TYPEDEF_MAP_KEY);
			}
			str = sheet.attributeValue("className");
			if (str == null) {
				str = (String) classMappingMap.get(typeString);
			}
			obj.className = str;
			obj.dataFormClassName = sheet.attributeValue("dataFormClassName");
			obj.handlerClassName = sheet.attributeValue("handlerClassName");
			obj.contentNum = 0;
			it2 = sheet.elementIterator("block");
			while (it2.hasNext()) {
				block = (Element) it2.next();
				obj.contentNum++;
			}
			int i = 0;
			obj.contents = new UBlockContent[obj.contentNum];
			if (obj.contentNum != 0) {
				it2 = sheet.elementIterator("block");
				while (it2.hasNext()) {
					block = (Element) it2.next();
					obj.contents[i] = newBlockContent(block);
					i++;
				}
			}
			docMap.put(name, obj);
		}
	}

	public void initChartTemplate(HashMap paraTempMap, String fileName,
			String key) {
		HashMap typeMap;
		UChartTemplate chartTemplate = null;
		Element chart, labels, label;
		Iterator it1, it2, it3;
		HashMap temp;
		String name, str;
		int rc = 0, rc1;
		HashMap chartMap = getTemplateMapInstance(paraTempMap, key);
		Element root = getRootByXMLFileName(fileName);
		String type, classname;
		it1 = root.elementIterator("chart");
		while (it1.hasNext()) {
			chart = (Element) it1.next();
			chartTemplate = new UChartTemplate();
			chartTemplate.name = chart.attributeValue("name");
			chartTemplate.title = getCellAttribute(chart.element("title"));
			chartTemplate.note = getCellAttribute(chart.element("note"));
			str = chart.attributeValue("type");
			if (str != null) {
				chartTemplate.type = this
						.getTypeValueByString(str, "chartType");
			}
			rc = 0;
			it2 = chart.elementIterator("labels");
			while (it2.hasNext()) {
				labels = (Element) it2.next();
				rc++;
			}
			chartTemplate.dimension = rc;
			chartTemplate.label = new String[rc][];
			rc = 0;
			it2 = chart.elementIterator("labels");
			while (it2.hasNext()) {
				labels = (Element) it2.next();
				it3 = labels.elementIterator();
				rc1 = 0;
				while (it3.hasNext()) {
					label = (Element) it3.next();
					rc1++;
				}
				chartTemplate.label[rc] = new String[rc1];
				it3 = labels.elementIterator();
				rc1 = 0;
				while (it3.hasNext()) {
					label = (Element) it3.next();
					chartTemplate.label[rc][rc1] = label
							.attributeValue("content");
					rc1++;
				}
				rc++;
			}
			chartTemplate.initDataObject();
			chartMap.put(chartTemplate.name, chartTemplate);
		}
	}

	public void initMenuTemplate(HashMap paraTempMap, String fileName,
			String key) {
		HashMap typeMap;
		UMenuTemplate menuTemplate = null;
		Element menus, menu, item, menubars, menubar;
		Iterator it1, it2, it3;
		HashMap temp;
		String name, str;
		int rc = 0;
		Element root = getRootByXMLFileName(fileName);
		HashMap menuMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator(key);
		while (it1.hasNext()) {
			menu = (Element) it1.next();
			menuTemplate = new UMenuTemplate();
			setMenuTemplate(menu, menuTemplate);
			menuMap.put(menuTemplate.name, menuTemplate);
		}
	}

	private void setMenuTemplate(Element menu, UMenuTemplate menuTemplate) {
		HashMap typeMap;
		Element item;
		Iterator it1;
		HashMap temp;
		String name, str, type;
		int rc = 0;
		menuTemplate.name = menu.attributeValue("name");
		menuTemplate.className = menu.attributeValue("className");
		if (menuTemplate.className == null) {
			menuTemplate.className = (String) this.classMappingMap.get("menu");
		}
		str = menu.attributeValue("conditionMenu");
		if (str != null && str.equals("true"))
			menuTemplate.conditionMenu = true;
		else
			menuTemplate.conditionMenu = false;
		menuTemplate.content = menuTemplate.name;
		menuTemplate.cmd = menuTemplate.name;
		str = menu.attributeValue("content");
		if (str != null)
			menuTemplate.content = str;
		str = menu.attributeValue("enContent");
		if (str != null)
			menuTemplate.enContent = str;
		str = menu.attributeValue("cmd");
		if (str != null)
			menuTemplate.cmd = str;
		menuTemplate.handler = menu.attributeValue("handler");
		it1 = menu.elementIterator("menuItem");
		rc = 0;
		while (it1.hasNext()) {
			item = (Element) it1.next();
			rc++;
		}
		menuTemplate.items = new UMenuItemTemplate[rc];
		UMenuItemTemplate mit;// yxj add 2010-4-17
		it1 = menu.elementIterator("menuItem");
		rc = 0;
		while (it1.hasNext()) {
			// modified by yxj 2010-4-17
			item = (Element) it1.next();

			name = item.attributeValue("name");
			type = item.attributeValue("type");
			if (type != null && type.equals("menu")) {
				menuTemplate.items[rc] = (UMenuItemTemplate) this.getTemplate(
						""
								+ this.getTypeValueByString("menu",
										UConstants.TYPEDEF_MAP_KEY), name);
			} else if (type != null) {

				mit = new UMenuItemTemplate();
				mit.type = this.getTypeValueByString(type,
						UConstants.TYPEDEF_COMPONENT_TYPE);
				setTemplate(mit, item, type);

				mit.getSelfAttribute(item);
				menuTemplate.items[rc] = mit;

			}
			rc++;

		}
	}

	public void initWorkbenchTemplate(HashMap paraTempMap, String fileName,
			String key) {
		HashMap typeMap;
		UPanelTemplate workbenchTemplate = null;
		Iterator it1, it2, it3;
		Element workbench;
		HashMap temp;
		String name, str;
		int rc = 0;
		Element root = getRootByXMLFileName(fileName);
		HashMap workbanchMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator(key);
		while (it1.hasNext()) {
			workbench = (Element) it1.next();
			workbenchTemplate = new UPanelTemplate();
			setPanelTemplet(workbench, workbenchTemplate);
			if (workbenchTemplate.className == null) {
				workbenchTemplate.className = (String) classMappingMap
						.get("workbench");
			}
			workbanchMap.put(workbenchTemplate.name, workbenchTemplate);
		}
	}

	public void initOldCSPanelTemplate(HashMap paraTempMap, String fileName,
			String key) {
		HashMap typeMap;
		Iterator it1, it2, it3;
		Element e;
		HashMap temp;
		String name, className;
		int rc = 0;
		Element root = getRootByXMLFileName(fileName);
		HashMap oldMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("panel");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			name = e.attributeValue("name");
			className = e.attributeValue("className");
			oldMap.put(name, className);
		}
	}

	public void initMenuTemplatMapTemplate(HashMap paraTempMap,
			String fileName, String key) {
		HashMap typeMap;
		Iterator it1, it2, it3;
		Element e, e1;
		HashMap temp;
		String name, value;
		int rc = 0;
		Element root = getRootByXMLFileName(fileName);
		// System.out.println("fileName" + fileName);
		HashMap oldMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("map");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			UMenuPanelMapTemplate r = new UMenuPanelMapTemplate();
			r.name = e.attributeValue("name");
			r.templateName = e.attributeValue("template");
			it2 = e.elementIterator("parameter");
			while (it2.hasNext()) {
				e1 = (Element) it2.next();
				name = e1.attributeValue("name");
				value = e1.attributeValue("value");
				if (r.parameterMap == null) {
					r.parameterMap = new FHashMap();
				}
				r.parameterMap.putData(name, value);
			}
			it2 = e.elementIterator("replace");
			while (it2.hasNext()) {
				e1 = (Element) it2.next();
				name = r.templateName + "." + e1.attributeValue("name");
				value = e1.attributeValue("value");
				if (r.replaceMap == null) {
					r.replaceMap = new FHashMap();
				}
				r.replaceMap.putData(name, value);
			}
			oldMap.put(r.name, r);
		}
	}

	private void setTemplate(UTemplate template, Element e, String typeString) {
		template.name = e.attributeValue("name");
		template.className = e.attributeValue("className");
		if (template.className == null) {
			template.className = (String) classMappingMap.get(typeString);
		}
	}

	private void setToolTemplate(UToolTemplate temp, Element e) {
		String typeString = e.attributeValue("type");
		if (typeString != null)
			temp.type = this.getTypeValueByString(typeString,
					UConstants.TYPEDEF_COMPONENT_TYPE);
		setTemplate(temp, e, typeString);
		temp.cmd = e.attributeValue("cmd");
		temp.content = e.attributeValue("content");
		temp.enContent = e.attributeValue("enContent");
		temp.iconName = e.attributeValue("icon");
		temp.selectedIconName = e.attributeValue("selectedIcon");
		temp.pressedIconName = e.attributeValue("pressedIcon");
		temp.colorName = e.attributeValue("color");
		temp.backgroundName = e.attributeValue("backgroundName");
		temp.userTaskName = e.attributeValue("userTaskName");
		temp.methodName = e.attributeValue("methodName");

	}

	private void setToolbarTemplate(UToolbarTemplate template, Element e) {
		Element te;
		String str;
		int ic;
		setTemplate(template, e, "toolbar");
		str = e.attributeValue("x");
		if (str != null)
			template.x = Integer.parseInt(str);
		else
			template.x = 0;
		str = e.attributeValue("y");
		if (str != null)
			template.y = Integer.parseInt(str);
		else
			template.y = 0;
		template.fontName = e.attributeValue("font");
		template.colorName = e.attributeValue("color");
		Iterator it = e.elementIterator("tool");
		ic = 0;
		while (it.hasNext()) {
			te = (Element) it.next();
			ic++;
		}
		template.items = new UToolTemplate[ic];
		ic = 0;
		it = e.elementIterator("tool");
		while (it.hasNext()) {
			te = (Element) it.next();
			template.items[ic] = new UToolTemplate();
			setToolTemplate(template.items[ic], te);
			ic++;
		}
		int row = 0, col = 0;// 行列值初始化为0
		str = e.attributeValue("row");
		if (str == null) {// 默认行数为1
			row = 1;
		} else {
			row = Integer.parseInt(str);
		}
		template.row = row;
		str = e.attributeValue("col");
		if (str == null) {
			col = (int) Math.round((ic / row) + 0.5);
		} else {
			col = Integer.parseInt(str);
		}
		template.col = col;

		str = e.attributeValue("floatable");
		if (str == null) {
			str = "true";
		}
		str = e.attributeValue("defaultEnable");
		if (str != null && str.equals("true")) {
			template.defaultEnable = true;
		} else
			template.defaultEnable = false;

		str = e.attributeValue("width");
		if (str != null) {
			template.width = Integer.parseInt(str);
		}
		str = e.attributeValue("height");
		if (str != null) {
			template.height = Integer.parseInt(str);
		}
		str = e.attributeValue("labelWidth");
		if (str != null) {
			template.labelWidth = Integer.parseInt(str);
		}
		str = e.attributeValue("labelHeight");
		if (str != null) {
			template.labelHeight = Integer.parseInt(str);
		}
		str = e.attributeValue("alignment");
		if (str != null) {
			template.alignment = getTypeValueByString(str, "alignmentType");
		} else {
			template.alignment = UConstants.ALIGNMENT_LEFT;
		}
		str = e.attributeValue("labelAlignment");
		if (str != null) {
			template.labelAlignment = getTypeValueByString(str, "alignmentType");
		} else {
			template.labelAlignment = UConstants.ALIGNMENT_TIP;
		}
		template.selectMode = e.attributeValue("selectMode");
		str = e.attributeValue("hgap");
		if (str != null) {
			template.hgap = Integer.parseInt(str);
		}
		str = e.attributeValue("vgap");
		if (str != null) {
			template.vgap = Integer.parseInt(str);
		}

	}

	public void initToolbarTemplate(HashMap paraTempMap, String fileName,
			String key) {
		Iterator it1, it2, it3;
		Element e, e1;
		HashMap temp;
		String name, value;
		int rc = 0;
		Element root = getRootByXMLFileName(fileName);
		HashMap toolbarMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("toolbar");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			UToolbarTemplate t = new UToolbarTemplate();
			t.name = e.attributeValue("name");
			t.className = e.attributeValue("className");
			setToolbarTemplate(t, e);
			toolbarMap.put(t.name, t);
		}
	}

	public void initGraphButtonBarTemplate(HashMap paraTempMap,
			String fileName, String key) {
		Iterator it1, it2, it3;
		Element e, e1;
		HashMap temp;
		String name, value;
		int rc = 0;
		Element root = getRootByXMLFileName(fileName);
		HashMap buttonbarMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("buttonBar");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			UGraphButtonBarTemplate t = new UGraphButtonBarTemplate();
			t.name = e.attributeValue("name");
			t.className = e.attributeValue("className");
			setGraphButtonBarTemplate(t, e);
			buttonbarMap.put(t.name, t);
		}
	}

	private void setGraphButtonBarTemplate(UGraphButtonBarTemplate template,
			Element e) {
		Element te;
		String str, typeStr;
		int ic;
		setTemplate(template, e, "graphButtonBar");
		str = e.attributeValue("row");
		if (str != null)
			template.row = Integer.parseInt(str);
		str = e.attributeValue("column");
		if (str != null)
			template.column = Integer.parseInt(str);
		Iterator it = e.elementIterator("button");
		ic = 0;
		while (it.hasNext()) {
			te = (Element) it.next();
			ic++;
		}
		if (template.row == 1 && template.column == 1) {
			template.row = ic;
		}
		template.items = new ArrayList();
		UButtonTemplate bt;
		it = e.elementIterator("button");
		while (it.hasNext()) {
			te = (Element) it.next();
			bt = new UButtonTemplate();
			bt.iconName = te.attributeValue("icon");
			typeStr = te.attributeValue("type");
			bt.type = this.getTypeValueByString(typeStr,
					UConstants.TYPEDEF_COMPONENT_TYPE);
			setTemplate(bt, te, typeStr);
			bt.getSelfAttribute(te);
			template.items.add(bt);
		}
	}

	private void setStatusbarTemplate(UStatusbarTemplate template, Element e) {
		String str;
		setTemplate(template, e, "statusBar");
		template.fontName = e.attributeValue("font");
		template.colorName = e.attributeValue("color");
		template.bgColorName = e.attributeValue("bgColor");
		template.department = e.attributeValue("department");
		template.telephone = e.attributeValue("telephone");
		template.version = e.attributeValue("version");
		template.email = e.attributeValue("email");
		str = e.attributeValue("hasProgress");
		if (str != null && str.equals("false")) {
			template.hasProgress = false;
		}
		str = e.attributeValue("height");
		if (str != null) {
			template.height = Integer.parseInt(str);
		}
	}

	public void setShortkeyTemplate(Element e, UShortcutkeyTemplate template) {
		String str;
		template.name = e.attributeValue("name");
		str = e.attributeValue("ctrl");
		if (str != null && str.equals("true")) {
			template.ctrl = true;
		}
		str = e.attributeValue("shift");
		if (str != null && str.equals("true")) {
			template.shift = true;
		}
		str = e.attributeValue("char");
		if (str != null)
			template.charValue = str.charAt(0);
	}

	public void initShortcutkeyTemplate(HashMap paraTempMap, String fileName,
			String key) {
		HashMap typeMap;
		UShortcutkeyTemplate keyTemplate = null;
		Element keyElement;
		Iterator it1, it2, it3;
		HashMap temp;
		String name, str;
		int rc = 0;
		Element root = getRootByXMLFileName(fileName);
		HashMap menuMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator(key);
		while (it1.hasNext()) {
			keyElement = (Element) it1.next();
			keyTemplate = new UShortcutkeyTemplate();
			setShortkeyTemplate(keyElement, keyTemplate);
			menuMap.put(keyTemplate.name, keyTemplate);
		}
	}

	public HashMap initEditPanelModels(String path, String appname) {
		Element root, e, e1;
		Iterator it1, it2;
		String str, typeString;
		String name;
		name = path + appname;
		HashMap map = new HashMap();
		root = getRootByXMLFileName(name);
		try {
			initModels(map, root, path);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	private UFrameAttribute getFrameAttribute(Element e) {
		UFrameAttribute attribute = new UFrameAttribute();
		if (e != null) {
			String str = e.attributeValue("closeExistPanel");
			if (str != null && str.equals("true")) {
				attribute.closeExistPanel = true;
			}
			str = e.attributeValue("maxOpenPanelMax");
			if (str != null && !str.equals("")) {
				attribute.maxOpenPanelMax = Integer.parseInt(str);
			}
		}
		return attribute;
	}

	/**
	 * @author 刘洋
	 * @param paraTempMap
	 * @param fileName
	 * @param key
	 */
	public void initNoPanelTemplate(HashMap paraTempMap, String fileName,
			String key) {
		HashMap typeMap;
		Iterator it1;
		Element e;
		HashMap temp;
		String name, className;
		Element root = getRootByXMLFileName(fileName);
		HashMap actionMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("noPanelAction");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			name = e.attributeValue("name");
			className = e.attributeValue("className");
			actionMap.put(name, className);
		}
	}

	public UParagraphTemplate getParagraphTemplateByName(String name) {

		UParagraphTemplate paragraphTemplate = null;
		if (name != null) {
			paragraphTemplate = (UParagraphTemplate) getTemplate(
					UConstants.MAPKEY_PARAGRAPH, name);
		}
		return paragraphTemplate;

	}

	public UPaperTemplate getPaperTemplateByName(String name) {

		UPaperTemplate paperTemplate = null;
		if (name != null) {
			paperTemplate = (UPaperTemplate) getTemplate(
					UConstants.MAPKEY_PAPER, name);
		}
		return paperTemplate;

	}

	public UPromptTemplate getPromptTemplateByName(String name) {

		UPromptTemplate promptTemplate = null;
		if (name != null) {
			promptTemplate = (UPromptTemplate) getTemplate(
					UConstants.MAPKEY_PROMPT, name);
		}
		return promptTemplate;

	}

	/**
	 * @author 洋
	 */
	public int getTypeValue(String str, String key) {
		return getTypeValueByString(str, key);
	}

	public UStroke getStrokeByName(String strokeName) {
		// TODO Auto-generated method stub
		UStroke storke = null;
		if (strokeName != null)
			storke = (UStroke) getTemplate(UConstants.MAPKEY_STROKE, strokeName);
		// if (storke == null)
		// storke = defaultStroke;
		return storke;
	}

	public void initICursorTemplate(HashMap paraTempMap, String fileName,
			String key) {

		FilterI obj = null;
		Element root = getRootByXMLFileName(fileName);
		Element cursor;
		String name, className, parameter;
		Iterator it2;
		HashMap filterMap = getTemplateMapInstance(paraTempMap, key);
		ICursorTemplate cursorTemp;
		it2 = root.elementIterator("cursor");
		while (it2.hasNext()) {
			cursor = (Element) it2.next();
			cursorTemp = new ICursorTemplate();
			cursorTemp.name = cursor.attributeValue("name");
			cursorTemp.className = cursor.attributeValue("className");
			filterMap.put(cursorTemp.name, cursorTemp);
		}
	}

	public void initIEventorTemplate(HashMap paraTempMap, String fileName,
			String key) {

		Element root = getRootByXMLFileName(fileName);
		Element eventor;
		String name, className, parameter;
		Iterator it2;
		HashMap eventorMap = getTemplateMapInstance(paraTempMap, key);
		IEventorTemplate eventorTemp;
		it2 = root.elementIterator("eventor");
		while (it2.hasNext()) {
			eventor = (Element) it2.next();
			eventorTemp = new IEventorTemplate();
			eventorTemp.name = eventor.attributeValue("name");
			eventorTemp.className = eventor.attributeValue("className");
			eventorMap.put(eventorTemp.name, eventorTemp);
		}
	}

	public void initIStatusDiagramTemplate(HashMap paraTempMap,
			String fileName, String key) {

		Element root = getRootByXMLFileName(fileName);
		Element statusDiagram;
		String name, className, parameter;
		Iterator it2;
		HashMap statusDiagramMap = getTemplateMapInstance(paraTempMap, key);
		IStatusDiagramTemplate statusDiagramTemp;
		it2 = root.elementIterator("statusdiagram");
		while (it2.hasNext()) {
			statusDiagram = (Element) it2.next();
			statusDiagramTemp = new IStatusDiagramTemplate();
			statusDiagramTemp.name = statusDiagram.attributeValue("name");
			statusDiagramTemp.className = statusDiagram
					.attributeValue("className");
			statusDiagramTemp.setAttribute(statusDiagram);
			statusDiagramMap.put(statusDiagramTemp.name, statusDiagramTemp);
		}
	}

	public void initITaskTemplate(HashMap paraTempMap, String fileName,
			String key) {

		FilterI obj = null;
		Element root = getRootByXMLFileName(fileName);
		Element task;
		String name, className, parameter;
		Iterator it2;
		HashMap taskMap = getTemplateMapInstance(paraTempMap, key);
		ITaskTemplate taskTemp;
		it2 = root.elementIterator("task");
		while (it2.hasNext()) {
			task = (Element) it2.next();
			taskTemp = new ITaskTemplate();
			taskTemp.name = task.attributeValue("name");
			taskTemp.className = task.attributeValue("className");
			taskTemp.setAttribute(task);
			taskMap.put(taskTemp.name, taskTemp);
		}
	}

	public void initUserTaskTemplate(HashMap paraTempMap, String fileName,
			String key) {

		Element root = getRootByXMLFileName(fileName);
		Element userTask, userTaskGroup;
		String name, className, parameter;
		Iterator it2, it3;
		HashMap map = null;
		HashMap userTaskMap = getTemplateMapInstance(paraTempMap, key);
		UserTaskTemplate userTaskTemp;
		it2 = root.elementIterator("userTaskGroup");
		while (it2.hasNext()) {
			userTaskGroup = (Element) it2.next();
			name = userTaskGroup.attributeValue("name");
			map = new HashMap();
			userTaskMap.put(name, map);
			it3 = userTaskGroup.elementIterator("userTask");
			while (it3.hasNext()) {
				userTask = (Element) it3.next();
				userTaskTemp = new UserTaskTemplate();
				userTaskTemp.setAttribute(userTask);
				userTaskTemp.name = userTask.attributeValue("name");
				userTaskTemp.className = userTask.attributeValue("className");
				map.put(userTaskTemp.name, userTaskTemp);
			}
		}
	}

	public void initClientFrameTemplate(HashMap paraTempMap, String fileName,
			String key) {
		UClientFrameTemplate obj;
		Element frame;
		Iterator it1;
		String name;
		Element root = getRootByXMLFileName(fileName);
		HashMap frameMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("clientframe");
		while (it1.hasNext()) {
			frame = (Element) it1.next();
			obj = newClientFrameTempletObject(frame, key);
			name = frame.attributeValue("name");
			frameMap.put(name, obj);
		}
	}

	public UClientFrameTemplate newClientFrameTempletObject(Element e,
			String key) {
		UClientFrameTemplate frameTemplate = null;
		Element e1;
		Iterator it1, it2;
		String str, typeString;
		frameTemplate = new UClientFrameTemplate();
		frameTemplate.name = e.attributeValue("name");
		frameTemplate.toolbarName = e.attributeValue("toolbarName");
		frameTemplate.toolPanelName = e.attributeValue("toolPanelName");
		frameTemplate.mainPanelName = e.attributeValue("mainPanelName");
		frameTemplate.startPanelName = e.attributeValue("startPanelName");
		frameTemplate.bgColor = e.attributeValue("bgColor");
		str = e.attributeValue("ebw");
		if (str != null)
			frameTemplate.ebw = new Integer(str);
		str = e.attributeValue("bw");
		if (str != null)
			frameTemplate.bw = new Integer(str);
		str = e.attributeValue("bh");
		if (str != null)
			frameTemplate.bh = new Integer(str);
		str = e.attributeValue("rootFunId");
		if (str != null)
			frameTemplate.rootFunId = new Integer(str);
		str = e.attributeValue("needSessionMenu");
		if (str == null || !str.equals("true"))
			frameTemplate.needSessionMenu = false;
		else
			frameTemplate.needSessionMenu = true;

		frameTemplate.title = getCellAttribute(e.element("title"));
		frameTemplate.logo = this.getCellAttribute(e.element("logo"));
		frameTemplate.attribute = getFrameAttribute(e.element("attribute"));
		e1 = e.element("menuBar");
		if (e1 != null) {
			frameTemplate.menuBarTemplate = new UMenuBarTemplate();
			setMenuTemplate(e1, frameTemplate.menuBarTemplate);
			str = e1.attributeValue("alignment");
			if (str != null) {
				frameTemplate.menuBarTemplate.alignment = this
						.getTypeValueByString(str, "alignmentType");
			} else {
				frameTemplate.menuBarTemplate.alignment = UConstants.ALIGNMENT_TOP;
			}
			frameTemplate.dataMenu = str = e1.attributeValue("dataMenu");
		}
		// 初始化菜单树
		this.initTreeTemplate(e.element("treeMenu"), frameTemplate);
		e1 = e.element("statusbar");
		if (e1 != null) {
			frameTemplate.statusbarTemplate = new UStatusbarTemplate();
			setStatusbarTemplate(frameTemplate.statusbarTemplate, e1);
		}
		// e1 = e.element("workbench");
		int iii = 0;
		it1 = e.elementIterator("workbench");
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			iii++;
		}
		frameTemplate.workbenchTemplate = new UPanelTemplate[iii];
		it1 = e.elementIterator("workbench");
		iii = 0;
		while (it1.hasNext()) {
			e1 = (Element) it1.next();
			str = e1.attributeValue("name");
			frameTemplate.workbenchTemplate[iii] = new UPanelTemplate();
			setPanelTemplet(e1, frameTemplate.workbenchTemplate[iii]);
			if (frameTemplate.workbenchTemplate[iii].className == null) {
				frameTemplate.workbenchTemplate[iii].className = (String) classMappingMap
						.get("workbench");
			}
			iii++;
		}
		Iterator itt1 = e.elementIterator("fixPanel");
		Element et1;
		UFixPanelTemplate ttt;
		while (itt1.hasNext()) {
			et1 = (Element) itt1.next();
			if (frameTemplate.fixePanelTemplateList == null)
				frameTemplate.fixePanelTemplateList = new ArrayList<UFixPanelTemplate>();
			ttt = new UFixPanelTemplate();
			ttt.name = et1.attributeValue("name");
			ttt.content = et1.attributeValue("content");
			// ttt.enContent = et1.attributeValue("content");
			ttt.imgName = et1.attributeValue("imgName");
			ttt.role = et1.attributeValue("role");
			str = et1.attributeValue("timer");
			if (str != null)
				ttt.timer = Integer.parseInt(str);
			else
				ttt.timer = 10;
			frameTemplate.fixePanelTemplateList.add(ttt);
		}
		itt1 = e.elementIterator("controlPanel");
		UClientFrameControlTemplate cc;
		Element et2;
		while (itt1.hasNext()) {
			et1 = (Element) itt1.next();
			if (frameTemplate.controlTemplateList == null)
				frameTemplate.controlTemplateList = new ArrayList<UClientFrameControlTemplate>();
			cc = new UClientFrameControlTemplate();
			cc.name = et1.attributeValue("name");
			cc.className = et1.attributeValue("className");
			if (cc.className == null)
				cc.className = "cn.edu.sdu.uims.frame.cs.panel.UClientFrameControlPanel";
			str = et1.attributeValue("align");
			if (str != null)
				cc.align = this.getTypeValueByString(str, "alignmentType");
			str = et1.attributeValue("width");
			if (str != null)
				cc.width = Integer.parseInt(str);
			str = et1.attributeValue("height");
			if (str != null)
				cc.height = Integer.parseInt(str);
			str = et1.attributeValue("titleHeight");
			if (str != null)
				cc.titleHeight = Integer.parseInt(str);
			cc.bgColorName = et1.attributeValue("bgColor");
			cc.windowToolBarName = et1.attributeValue("windowToolBar");
			cc.toolBarNames = et1.attributeValue("toolBars");
			it2 = et1.elementIterator("label");
			ULabelAttribute ll;
			while (it2.hasNext()) {
				et2 = (Element) it2.next();
				if (cc.labelList == null)
					cc.labelList = new ArrayList<ULabelAttribute>();
				ll = new ULabelAttribute();
				ll.name = et2.attributeValue("name");
				ll.value = et2.attributeValue("value");
				str = et2.attributeValue("x");
				if (str != null)
					ll.x = Integer.parseInt(str);
				else
					ll.x = 0;
				str = et2.attributeValue("y");
				if (str != null)
					ll.y = Integer.parseInt(str);
				else
					ll.y = 0;
				str = et2.attributeValue("w");
				if (str != null)
					ll.w = Integer.parseInt(str);
				else
					ll.w = 0;
				str = et2.attributeValue("h");
				if (str != null)
					ll.h = Integer.parseInt(str);
				else
					ll.h = 0;
				ll.colorName = et2.attributeValue("color");
				ll.fontName = et2.attributeValue("font");
				cc.labelList.add(ll);
			}
			it2 = et1.elementIterator("image");
			UImageAttribute ii;
			while (it2.hasNext()) {
				et2 = (Element) it2.next();
				if (cc.imageList == null)
					cc.imageList = new ArrayList<UImageAttribute>();
				ii = new UImageAttribute();
				ii.name = et2.attributeValue("name");
				ii.imageName = et2.attributeValue("imageName");
				str = et2.attributeValue("x");
				if (str != null)
					ii.x = Integer.parseInt(str);
				else
					ii.x = 0;
				str = et2.attributeValue("y");
				if (str != null)
					ii.y = Integer.parseInt(str);
				else
					ii.y = 0;
				str = et2.attributeValue("w");
				if (str != null)
					ii.w = Integer.parseInt(str);
				else
					ii.w = 0;
				str = et2.attributeValue("h");
				if (str != null)
					ii.h = Integer.parseInt(str);
				else
					ii.h = 0;
				str = et2.attributeValue("align");
				if (str != null)
					ii.align = this.getTypeValueByString(str, "alignmentType");
				cc.imageList.add(ii);
			}
			it2 = et1.elementIterator("component");
			while (it2.hasNext()) {
				et2 = (Element) it2.next();
				if (cc.comList == null)
					cc.comList = new ArrayList<UElementTemplate>();
				cc.comList.add(getElementTemplate(et2));
			}
			frameTemplate.controlTemplateList.add(cc);
		}
		return frameTemplate;
	}

	public UClientFrameTemplate getClientFrameTemplate(String templateName) {
		return (UClientFrameTemplate) getTemplate(
				UConstants.MAPKKEY_CLIENTFRAME, templateName);
	}

	public void initClientDialogTemplate(HashMap paraTempMap, String fileName,
			String key) {
		UClientDialogTemplate obj;
		Element frame;
		Iterator it1;
		String name;
		Element root = getRootByXMLFileName(fileName);
		HashMap frameMap = getTemplateMapInstance(paraTempMap, key);
		it1 = root.elementIterator("clientdialog");
		while (it1.hasNext()) {
			frame = (Element) it1.next();
			obj = newClientDialogTempletObject(frame, key);
			name = frame.attributeValue("name");
			frameMap.put(name, obj);
		}
	}

	public UClientDialogTemplate newClientDialogTempletObject(Element e,
			String key) {
		UClientDialogTemplate frameTemplate = null;
		Element e1;
		Iterator it1, it2;
		String str, typeString;
		frameTemplate = new UClientDialogTemplate();
		frameTemplate.name = e.attributeValue("name");
		str = e.attributeValue("winX");
		if (str != null && !str.equals(""))
			frameTemplate.winX = new Integer(new Integer(str));
		str = e.attributeValue("winY");
		if (str != null && !str.equals(""))
			frameTemplate.winY = new Integer(new Integer(str));
		str = e.attributeValue("winW");
		if (str != null && !str.equals(""))
			frameTemplate.winW = new Integer(new Integer(str));
		str = e.attributeValue("winH");
		if (str != null && !str.equals(""))
			frameTemplate.winH = new Integer(new Integer(str));
		frameTemplate.title = getCellAttribute(e.element("title"));
		frameTemplate.logo = this.getCellAttribute(e.element("logo"));
		frameTemplate.attribute = getFrameAttribute(e.element("attribute"));
		frameTemplate.panelTemplate = new UPanelTemplate();
		e1 = e.element("panel");
		str = e1.attributeValue("name");
		frameTemplate.panelTemplate = new UPanelTemplate();
		setPanelTemplet(e1, frameTemplate.panelTemplate);
		if (frameTemplate.panelTemplate.className == null) {
			frameTemplate.panelTemplate.className = (String) classMappingMap
					.get("formPanel");
		}
		frameTemplate.bgColorName = e.attributeValue("bgColorName");
		frameTemplate.imageName = e.attributeValue("imageName");
		return frameTemplate;
	}

	public UClientDialogTemplate getClientDialogTemplate(String templateName) {
		return (UClientDialogTemplate) getTemplate(
				UConstants.MAPKKEY_CLIENTDIALOG, templateName);
	}

	public HashMap getUserTaskMap(String name) {
		return (HashMap) getTemplate(UConstants.MAPKEY_USER_TASK, name);
	}

	public GraphModelI getGraphModel2DObject(String name) {
		ModelProcessRule rule = (ModelProcessRule) ApplicationContextHandle
				.getBean("modelProcessRule");
		GraphModelI model = null;
		if (rule != null) {
			model = rule.getGraphModel2DObject(name);
		} else
			model = getGraphModel2DObjectFromLocal(name);
		return model;
	}


	public HashMap getBsTemplateInstanceMap(String key) {
		HashMap tMap = (HashMap) bsTemplateMap.get(key);
		if (tMap == null) {
			tMap = new HashMap();
			bsTemplateMap.put(key, tMap);
		}
		return tMap;
	}


	public void initItemStyleTemplate(HashMap paraTempMap, String fileName,
			String key) {
		Element panel;
		Iterator it1;
		String name;
		Element root = getRootByXMLFileName(fileName);
		if (root == null) {
			System.out.println(fileName);
			return;
		}
		HashMap panelMap = getBsTemplateInstanceMap(key);
		it1 = root.elementIterator("style");
		String style;
		while (it1.hasNext()) {
			panel = (Element) it1.next();
			name = panel.attributeValue("name");
			style = panel.attributeValue("value");
			panelMap.put(name, style);
		}
	}

	public String getItemStyleTemplate(String name) {
		HashMap map = (HashMap) bsTemplateMap.get("bsItemStyle");
		if (map == null)
			return null;
		return (String) map.get(name);
	}


	public void resetModels() {
		clearAllData();
		initApplicationModels();
	}




	public EnvironmentTemplate getEnvironmentTemplate(String name) {
		return (EnvironmentTemplate) getBsTemplate("environment", name);
	}

	private EnvironmentTemplate newEnvironmentTemplateObject(Element panel,
			String key) {
		if (key.equals("environment")) {
			String type = panel.attributeValue("type");
			EnvironmentTemplate template = new EnvironmentTemplate();
			template.getAttribute(panel);
			return template;
		}
		return null;
	}

	public EnvironmentTemplate getEnvironmentTemplate() {
		// TODO Auto-generated method stub
		return environmentTemplate;
	}

	public DefaultFontMapper getFontMapper() {
		// TODO Auto-generated method stub
		return this.fontMapper;
	}


	public void initApplicationModels() {
		initApplicationModels(modelPath, environmentFileName);
	}

	public void initApplicationModels(String path, String environName) {
		Element root, e, e1;
		Iterator it1, it2;
		String str, typeString;
		String name;
		modelPath = path;
		this.environmentFileName = environName;
		try {
			name = modelPath + "uims/configure/uims-model-file.xml";
			root = getRootByXMLFileName(name);
			if (root == null) {
				System.out.println(name);
				return;
			}
			initSysModels(templateMap, root, modelPath);

			initEnvironmentTemplate(environName);
			name = modelPath + environmentTemplate.modelName;
			realTime = false;
			root = getRootByXMLFileName(name);
			initModels(templateMap, root, path);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void initEnvironmentTemplate(String environName) {
		Object obj;
		Element e;
		Iterator it1;
		String name;
		Element root;

		name = modelPath + environName;
		root = getRootByXMLFileName(name);
		if (root == null) {
			System.out.println(name);
			return;
		}
		e = root.element("environment");
		name = e.attributeValue("name");
		environmentTemplate = newEnvironmentTemplateObject(e, "environment");

		HashMap map = getTemplateMapInstance(templateMap, "clientFrame");
		it1 = root.elementIterator("clientframe");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			name = e.attributeValue("name");
			obj = newClientFrameTempletObject(e, "clientFrame");
			map.put(name, obj);
		}
		it1 = root.elementIterator("clientdialog");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			map = getTemplateMapInstance(templateMap, "clientDialog");
			name = e.attributeValue("name");
			obj = newClientDialogTempletObject(e, "clientDialog");
			map.put(name, obj);
		}
		map = getTemplateMapInstance(templateMap, "dialog");
		it1 = root.elementIterator("panel");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			name = e.attributeValue("name");
			obj = newPanelTempletObject(e, "dialog");
			map.put(name, obj);
		}
	}

	private void initSysModels(HashMap map, Element root, String path)
			throws Exception {
		Iterator it1;
		String fileName;
		Element e = null;
		Method method = null;
		String methodName = "", key = "";
		it1 = root.elementIterator("mode");
		while (it1.hasNext()) {
			e = (Element) it1.next();
			methodName = e.attributeValue("method");
			if (methodName == null)
				continue;
			key = e.attributeValue("key");
			fileName = e.attributeValue("fileName");

			method = this.getClass().getMethod(methodName, HashMap.class,
					String.class, String.class);
			method.invoke(this, map, path + fileName, key);
		}
	}

	public String removeRowSymbol(String con) {
		StringTokenizer sz = new StringTokenizer(con, "\n");
		String ret = "";
		StringBuffer sb = new StringBuffer(1024);
		while (sz.hasMoreTokens()) {
			sb.append(sz.nextElement() + " ");
		}
		return sb.toString();
	}

	public void initSqlTemplate(HashMap paraTempMap, String fileName, String key) {
		Element panel;
		Iterator it1;
		String name;
		InputStream in = null;
		StringBuffer sb = new StringBuffer(1024);
		String str;
		in = this.getClass().getResourceAsStream(fileName);
		try {
			if (in == null)
				in = new FileInputStream(new File(fileName));
			BufferedReader read = new BufferedReader(new InputStreamReader(in,
					"utf-8"));
			do {
				str = read.readLine();
				if (str != null)
					sb.append(str + "\n");
			} while (str != null);
			read.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap jsMap = getBsTemplateInstanceMap(key);
		String strContent = sb.toString();
		int pos = 0;
		int length = strContent.length();
		int index;
		int left;
		int right;
		int start, end;
		String funName, content;
		pos = 0;
		String segName;
		String conName;
		while (pos < length) {
			start = strContent.indexOf("segment", pos);
			if (start < 0)
				break;
			left = strContent.indexOf("{", start + 8);
			right = strContent.indexOf("}", left + 1);
			segName = strContent.substring(start + 8, left - 1).trim();
			content = strContent.substring(left + 1, right - 1).trim();
			jsMap.put(segName, removeRowSymbol(content));
			pos = right + 1;
		}
	}

	public HashMap getSqlSegmentMap() {
		return (HashMap) bsTemplateMap.get("sqlSegment");
	}

	public String getSqlSegmentByName(String name) {
		HashMap map = (HashMap) bsTemplateMap.get("sqlSegment");
		if (map == null)
			return null;
		return (String) map.get(name);
	}

	public void initDataExportTemplate(HashMap paraTempMap, String fileName,
			String key) {
		Element e;
		Iterator it1;
		String name;
		Element root = getRootByXMLFileName(fileName);
		if (root == null) {
			System.out.println(fileName);
			return;
		}
		HashMap panelMap = getBsTemplateInstanceMap(key);
		it1 = root.elementIterator("dataExport");
		String style;
		String type;
		DataExportTemplate template;
		while (it1.hasNext()) {
			e = (Element) it1.next();
			name = e.attributeValue("name");
			type = e.attributeValue("type");
			if (type == null || type.length() == 0)
				type = "toXLS";
			template = UimsUtils.getDataExportTemplate(type);
			template.getAttribute(e);
			panelMap.put(name, template);
		}
	}

	public DataExportTemplate getDataExportTemplate(String name) {
		HashMap map = (HashMap) bsTemplateMap.get("dataExport");
		if (map == null)
			return null;
		return (DataExportTemplate) map.get(name);
	}

	public void initDataImportTemplate(HashMap paraTempMap, String fileName,
			String key) {
		Element e;
		Iterator it1;
		String name;
		Element root = getRootByXMLFileName(fileName);
		if (root == null) {
			System.out.println(fileName);
			return;
		}
		HashMap panelMap = getBsTemplateInstanceMap(key);
		it1 = root.elementIterator("dataImport");
		String style;
		String type;
		DataImportTemplate template;
		while (it1.hasNext()) {
			e = (Element) it1.next();
			name = e.attributeValue("name");
			type = e.attributeValue("type");
			if (type == null)
				type = "fromXLS";
			template = UimsUtils.getDataImportTemplate(type);
			template.getAttribute(e);
			panelMap.put(name, template);
		}
	}

	public DataImportTemplate getDataImportTemplate(String name) {
		HashMap map = (HashMap) bsTemplateMap.get("dataImport");
		if (map == null)
			return null;
		return (DataImportTemplate) map.get(name);
	}


	public List getTimeControlActionListByPanelName(String panelName,
			Integer[] sysRoleIds) {
		TimeControlActionProcessorI pi = (TimeControlActionProcessorI) ApplicationContextHandle
				.getBean("sysTimeControlActionInfoRule");
		if (pi == null)
			return null;
		return pi.getTimeControlActionListByPanelName(panelName, sysRoleIds);

	}


	public void initExpandItemTemplate(HashMap paraTempMap, String fileName,
			String key) {
		Element e;
		Iterator it1;
		String name;
		Element root = getRootByXMLFileName(fileName);
		if (root == null) {
			System.out.println(fileName);
			return;
		}
		HashMap panelMap = getBsTemplateInstanceMap(key);
		it1 = root.elementIterator("item");
		String style;
		String type;
		ItemTemplate template;
		while (it1.hasNext()) {
			e = (Element) it1.next();
			name = e.attributeValue("name");
			type = e.attributeValue("type");
			if (type == null)
				type = "table";
			template = UimsUtils.getItemTemplate(type);
			template.getAttribute(e);
			panelMap.put(name, template);
		}
	}

	public ItemTemplate getExpandItemTemplate(String name) {
		HashMap map = (HashMap) bsTemplateMap.get("expandItem");
		if (map == null)
			return null;
		return (ItemTemplate) map.get(name);
	}

	public void initPhotoLayoutTemplate(HashMap paraTempMap, String fileName,
			String key) {
		Element root = getRootByXMLFileName(fileName);
		PhotoLayoutTemplate photoObj;
		Iterator it1, it2;
		String name;
		HashMap photoMap = getTemplateMapInstance(paraTempMap, key);
		Element photo;
		it1 = root.elementIterator("photolayout");
		while (it1.hasNext()) {
			photo = (Element) it1.next();
			name = photo.attributeValue("name");
			photoObj = new PhotoLayoutTemplate();
			photoObj.name = name;
			photoObj.getAttribute(photo);
			photoMap.put(name, photoObj);
		}
	}

	public PhotoLayoutTemplate getPhotoLayoutTemplate(String name) {
		HashMap photoMap = getTemplateMapInstance(templateMap,
				UConstants.KEY_STRING_PHOTOLAYOUT);
		if (photoMap == null)
			return null;
		else
			return (PhotoLayoutTemplate) photoMap.get(name);
	}

	public HashMap getPhotoLayoutTemplateMap() {
		return getTemplateMapInstance(templateMap,
				UConstants.KEY_STRING_PHOTOLAYOUT);
	}

	public String getEnvironmentProperties(String name) {
		// TODO Auto-generated method stub
		if (name == null)
			return null;
		EnvironmentTemplate t = getEnvironmentTemplate();
		if (t == null || t.properties == null)
			return null;
		return t.properties.get(name);
	}


	public GraphModelI getGraphModel2DObjectFromLocal(String name) {

		GraphModel2D graphModel2D = (GraphModel2D) UModelFactory.getInstance()
				.getGraphModel2DMap().get(name);
		if (graphModel2D != null) {
			return graphModel2D;
		}
		Document doc = null;
		SAXReader sb = null;
		InputStream in = null;
		try {
			sb = new SAXReader();
			in = this.getClass().getResourceAsStream(modelPath + name);
			doc = sb.read(in);
		} catch (Exception e) {
			doc = null;
		}
		if (doc != null) {
			graphModel2D = new GraphModel2D();
			XmlGraphModelDataProcessor graphDataProcessor = new XmlGraphModelDataProcessor(
					graphModel2D);
			graphModel2D.setGraphDataProcessor(graphDataProcessor);
			graphModel2D.setName(name);
			graphDataProcessor.setXmlDoc(doc);
			UModelFactory.getInstance().getGraphModel2DMap()
					.put(name, graphModel2D);
			return graphModel2D;
		}
		return null;
	}

	public boolean isClient() {
		return isClient;
	}

	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}

}
