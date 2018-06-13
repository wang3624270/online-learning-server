package cn.edu.sdu.uims.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.common.reportdog.UTableAttribute;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.service.UFactory;

public class UWORDUtil implements UDocumentUtilBaseI {
	private static UWORDUtil instance = new UWORDUtil();

	private static int fileNo = 0;

	// word运行程序对象
	private ActiveXComponent word;

	// 所有word文档集合
	private Dispatch documents;

	private UWORDUtil() {

	}

	public static UWORDUtil getInstance() {
		return instance;
	}

	// public String getNewTempFileName() {
	// return UFileUtil.getNewTempFileName(fileNo, "doc");
	// }

	// public UDocument openDocumentFile(String fileName,
	// UPaperTemplate paperTemplate, UPageNumberTemplate pageNum,
	// OutputStream os) {
	// UDocument ud = null;
	// Dispatch doc = null;
	// // String noString = "";
	// // fileNo++;
	// // if (fileNo >= 30000)
	// // fileNo = 1;
	// // if (fileNo < 10) {
	// // noString = "000" + fileNo;
	// // } else if (fileNo < 100) {
	// // noString = "00" + fileNo;
	// // } else if (fileNo < 1000) {
	// // noString = "0" + fileNo;
	// // } else {
	// // noString = "" + fileNo;
	// // }
	// String filePath = getNewTempFileName();
	// if (word == null) {
	// word = new ActiveXComponent("Word.Application");
	// // 对象可见
	// word.setProperty("Visible", new Variant(true));
	// }
	// if (documents == null)
	// documents = word.getProperty("Documents").toDispatch();
	//
	// doc = Dispatch.call(documents, "Add").toDispatch();
	//
	// // 设置页面大小
	// Dispatch pageSet = Dispatch.get(doc, "PageSetup").toDispatch();
	// if (paperTemplate.name.equals("A4")) {
	// Dispatch
	// .put(pageSet, "PageWidth", new Variant(paperTemplate.width));
	// Dispatch.put(pageSet, "PageHeight", new Variant(
	// paperTemplate.height));
	// }
	// // -------------------
	// Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(),
	// "FileSaveAs", filePath);
	// ud = new UDocument();
	// ud.name = fileName;
	// ud.object = doc;
	// return ud;
	// }

	public void setPaperInfo(UDocument doc) {
	}

	/**
	 * 
	 * @param doc
	 * @param cols
	 * @param colsWidth
	 *            表格各列的宽度
	 * @param cellList
	 * @param arrageType
	 *            添加cel的排列方式，竖排和横排
	 */
	public void addTable(UDocument ud, UTableAttribute tableAttr,
			UCellAttribute[] datas, int arrageType) {
		Dispatch doc = (Dispatch) ud.object;
		Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch range = Dispatch.get(selection, "Range").toDispatch();
		if (tableAttr == null)
			tableAttr = new UTableAttribute();
		// 设置文本段落格式
		Dispatch format = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();

		Dispatch.put(format, "SpaceAfter", new Variant(tableAttr.spacingAfter));
		Dispatch.put(format, "SpaceBefore",
				new Variant(tableAttr.spacingBefore));

		// 创建numRows行numCols列的表格
		int numCols = tableAttr.colNum;
		int numRows = datas.length / numCols;
		Dispatch newTable = Dispatch.call(tables, "Add", range,
				new Variant(numRows), new Variant(tableAttr.colNum))
				.toDispatch();

		Dispatch cols = Dispatch.get(newTable, "Columns").toDispatch();
		// 设置各列宽度
		for (int i = 0; i < numCols; i++) {
			Dispatch col = Dispatch.call(cols, "Item", new Variant(i + 1))
					.toDispatch();
			Dispatch
					.put(col, "PreferredWidth", new Variant(tableAttr.width[i]));
		}

		// 设置各个cell的属性和内容
		UCellAttribute cell = null;
		Dispatch wCell = null;
		UFont font = null;
		UColor color = null;
		UColor bColor = null;
		Dispatch wFont = null;
		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numCols; j++) {
				wCell = Dispatch.call(newTable, "Cell", new Variant(i + 1),
						new Variant(j + 1)).toDispatch();
				Dispatch.call(wCell, "Select");

				selection = Dispatch.get(word, "selection").toDispatch();
				cell = datas[i * numCols + j];
				font = UFactory.getModelSession().getFontByName(cell.fontName);
				color = UFactory.getModelSession().getColorByName(cell.frontColorName);
				bColor = UFactory.getModelSession().getColorByName(cell.backColorName);
				// 设置单元格文字对齐方式
				// 居中对齐
				if (cell.horizontalAlignment == UConstants.ALIGNMENT_CENTER)
					Dispatch.put(format, "Alignment", new Variant(1));
				// 左对齐
				else if (cell.horizontalAlignment == UConstants.ALIGNMENT_LEFT)
					Dispatch.put(format, "Alignment", new Variant(0));
				// 右对齐
				else if (cell.horizontalAlignment == UConstants.ALIGNMENT_RIGHT)
					Dispatch.put(format, "Alignment", new Variant(2));

				// 设置单元背景色
				if (bColor != null) {

				}
				UFont dataFont;
				String defautlFontName = cell.fontName;
//				UFont defautlFont = font;
				List list = ParagraphStyleUtility.parseSyte(cell.content,
						defautlFontName);
				String[] strs;
				if (list != null && list.size() > 0) {
					for (int k = 0; k < list.size(); k++) {
						strs = (String[]) list.get(k);
						// Dispatch.put(selection, "Text", strs[0]);
						Dispatch.call(selection, "InsertAfter", strs[0]);
						dataFont = UFactory.getModelSession().getFontByName(strs[1]);
						// 设置字体
						wFont = Dispatch.get(selection, "Font").toDispatch();
						Dispatch.put(wFont, "Name", new Variant(
								dataFont.fontName));
						Dispatch.put(wFont, "Bold", new Variant(dataFont.font
								.isBold()));
						Dispatch.put(wFont, "Italic", new Variant(dataFont.font
								.isItalic()));
						Dispatch.put(wFont, "Underline", new Variant(false));
						Dispatch.put(wFont, "Size", dataFont.size);
						if (color != null) {
							int colorInt = color.r + 256 * color.g + 256 * 256
									* color.b;
							Dispatch.put(wFont, "Color", new Variant(colorInt));
						}
						// Dispatch.call(selection, "MoveDown");
						// Dispatch.call(selection, "MoveRight", new Variant(0),
						// new Variant(1));
						// //Dispatch.call(selection,"");
					}

				}

				// // 居中对齐
				// if (cell.verticalAlignment == UConstants.ALIGNMENT_CENTER)
				// Dispatch.put(wCell, "VerticalAlignment", new Variant(1));
				// // 顶端对齐
				// else if (cell.verticalAlignment == UConstants.ALIGNMENT_TOP)
				// Dispatch.put(wCell, "VerticalAlignment", new Variant(0));
				// // 底部对齐
				// else if (cell.verticalAlignment ==
				// UConstants.ALIGNMENT_BOTTOM)
				// Dispatch.put(wCell, "VerticalAlignment", new Variant(2));

				// 设置单元格边的属性

			}
		Dispatch.call(selection, "MoveDown");
		Dispatch.call(doc, "Save");
	}

	/**
	 * 添加文本
	 * 
	 * @param doc
	 * @param content
	 * @param template
	 */
	public void addParagraph(UDocument ud, String content,
			UParagraphTemplate template) {
		Dispatch doc = (Dispatch) ud.object;

		//
		// Dispatch.put(wFont, "Name", new Variant(template.font.fontName));
		// Dispatch.put(wFont, "Bold", new Variant(true));
		// Dispatch.put(wFont, "Italic", new Variant(false));
		// Dispatch.put(wFont, "Underline", new Variant(false));
		// Dispatch.put(wFont, "Size", template.font.size);

		// if (template.color != null) {
		// int colorInt = template.color.r + 256 * template.color.g + 256
		// * 256 * template.color.b;
		// Dispatch.put(wFont, "Color", new Variant(colorInt));
		// }

		// // 对段落字体进行设置，修改者 刘洋
		Dispatch selection;
		selection = Dispatch.get(word, "Selection").toDispatch();
//		UFont defautlFont = UFactory.getModelSession().getFontByName(template.fontName);
		List list = ParagraphStyleUtility.parseSyte(content, template.fontName);
		String[] strs;
		// Dispatch selection = Dispatch.get(word, "Selection").toDispatch();
		// // 设置文本段落格式
		// Dispatch format = Dispatch.get(selection, "ParagraphFormat")
		// .toDispatch();
		//
		// Dispatch.put(format, "SpaceAfter", new Variant(template.after));
		// Dispatch.put(format, "SpaceBefore", new Variant(template.before));
		// // 居中对齐
		// if (template.horizontalAlignment == 0)
		// Dispatch.put(format, "Alignment", new Variant(1));
		// // 左对齐
		// else if (template.horizontalAlignment == 2)
		// Dispatch.put(format, "Alignment", new Variant(0));
		// // 右对齐
		// else if (template.horizontalAlignment == 4)
		// Dispatch.put(format, "Alignment", new Variant(2));
		UFont dataFont;
		int i;
		if (list != null && list.size() > 0) {
			for (i = 0; i < list.size(); i++) {
				strs = (String[]) list.get(i);
				Dispatch.put(selection, "Text", strs[0]);
				dataFont = UFactory.getModelSession().getFontByName(strs[1]);
				Dispatch wFont = Dispatch.get(selection, "Font").toDispatch();
				Dispatch.put(wFont, "Name", new Variant(dataFont.fontName));
				Dispatch
						.put(wFont, "Bold", new Variant(dataFont.font.isBold()));
				Dispatch.put(wFont, "Italic", new Variant(dataFont.font
						.isItalic()));
				Dispatch.put(wFont, "Underline", new Variant(false));
				Dispatch.put(wFont, "Size", dataFont.size);
				UColor ccc = UFactory.getModelSession().getColorByName(template.colorName);
				if (template.colorName != null) {
					int colorInt = ccc.r + 256 * ccc.g
							+ 256 * 256 * ccc.b;
					Dispatch.put(wFont, "Color", new Variant(colorInt));
				}
				Dispatch.call(selection, "MoveRight");
			}
		}
		// 设置结束 修改者刘洋
		Dispatch.call(selection, "MoveDown");
		Dispatch.call(selection, "TypeParaGraph");
		Dispatch.call(doc, "Save");
	}

	/**
	 * 关闭
	 * 
	 * @param ud
	 */
	public void closeDocumentFile(UDocument ud) {
		Dispatch doc = (Dispatch) ud.object;
		if (doc != null) {
			Dispatch.call(doc, "Save");
			Dispatch.call(doc, "Close", new Variant(true));
			doc = null;
		}
		if (documents != null) {
			documents = null;
		}
		if (word != null) {
			Dispatch.call(word, "Quit");
			word = null;
		}

	}

	public static void main(String[] args) throws Exception {
		UWORDUtil wUtil = new UWORDUtil();
		File name = wUtil.getNewTempFile();
		UPaperTemplate paper = new UPaperTemplate();
		UPageNumberTemplate pageNumber = new UPageNumberTemplate();
		UDocument ud = wUtil.openDocumentFile(name, paper, pageNumber);
		UParagraphTemplate para = new UParagraphTemplate();
		para.horizontalAlignment = UConstants.ALIGNMENT_LEFT;
		para.fontName = "font12";
		para.colorName = "colorBlue";
		para.after = 10;
		para.before = 20;
		wUtil
				.addParagraph(
						ud,
						"系统背景和目标描述：济南（泉城路）新华书店创建于1948年10月，是全国成立较早的几个省会城市店之一，位于济南繁华的中心商业区泉城路，在省内尤其是济南市的图书销售领域占有较大市场份额。但近些年来其图书营销业务受到泉城电子科技书店、东方图书公司等书店越来越大的冲击。新华书店为巩固其图书市场的龙头地位，决定改进营销模式，拟构建一个B2C网上书店，从而达到拓展业务范围，缩短图书周转周期，方便读者，同时扩大书店的影响力和知名度，最终占有更大市场份额的目的。",
						para);
		UTableAttribute tAttr = new UTableAttribute();
		tAttr.colNum = 5;
		float[] cellWidth = { 50, 80, 100, 90, 60 };
		tAttr.width = cellWidth;
		tAttr.spacingAfter = 10;
		tAttr.spacingBefore = 10;
		UCellAttribute[] cellArray = new UCellAttribute[25];
		for (int i = 0; i < 25; i++) {
			UCellAttribute cellAttr = new UCellAttribute();
			cellAttr.content = "单元格" + i;
			cellAttr.frontColorName = "colorRed";
			cellAttr.horizontalAlignment = UConstants.ALIGNMENT_RIGHT;
			cellAttr.verticalAlignment = UConstants.ALIGNMENT_BOTTOM;
			cellArray[i] = cellAttr;
		}
		wUtil.addTable(ud, tAttr, cellArray, 0);
		wUtil.closeDocumentFile(ud);

	}

	public byte[] getFileBytes(String fileName) {
		File file = new File(fileName);
		FileInputStream inputStream = null;
		byte[] b = null;
		try {
			inputStream = new FileInputStream(file);
			b = new byte[inputStream.available()];
			inputStream.read(b);
			inputStream.close();
			file.delete();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public void addImage(UDocument ud, String filePath,
			UParagraphTemplate template) {
		// TODO Auto-generated method stub

	}

	public void newPage(UDocument doc) {
		// TODO Auto-generated method stub

	}

	public File getNewTempFile() throws Exception {
		// TODO Auto-generated method stub
		return File.createTempFile("tmp", ".pdf");
	}

	public UDocument openDocumentFile(File file, UPaperTemplate paperTemplate,
			UPageNumberTemplate pageNum) throws Exception {
		// TODO Auto-generated method stub
		UDocument ud = null;
		Dispatch doc = null;
		if (word == null) {
			word = new ActiveXComponent("Word.Application");
			// 对象可见
			word.setProperty("Visible", new Variant(true));
		}
		if (documents == null)
			documents = word.getProperty("Documents").toDispatch();

		doc = Dispatch.call(documents, "Add").toDispatch();

		// 设置页面大小
		Dispatch pageSet = Dispatch.get(doc, "PageSetup").toDispatch();
		if (paperTemplate.name.equals("A4")) {
			Dispatch
					.put(pageSet, "PageWidth", new Variant(paperTemplate.width));
			Dispatch.put(pageSet, "PageHeight", new Variant(
					paperTemplate.height));
		}
		// -------------------
		Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(),
				"FileSaveAs", file);
		ud = new UDocument();
		ud.name = file.getAbsolutePath();
		ud.object = doc;
		return ud;
	}

	public UDocument openDocumentStream(ByteArrayOutputStream stream,
			UPaperTemplate paperTemplate, UPageNumberTemplate pageNum)
			throws Exception {
		return null;
	}
}
