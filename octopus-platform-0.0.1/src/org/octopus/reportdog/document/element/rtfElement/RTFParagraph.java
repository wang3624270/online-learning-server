package org.octopus.reportdog.document.element.rtfElement;

import java.util.HashMap;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.document.ExportObjectOperator;
import org.octopus.reportdog.document.element.ReportElement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;

public class RTFParagraph implements ReportElement {
	static BaseFont baseFont = null;
	static Font font = null;
	static int align = Element.ALIGN_LEFT;

	private BaseFont privateBaseFont = null;
	private Font privateFont = null;
	private int privateAlign = Element.ALIGN_LEFT;

	private String content = "";

	public void setContent(String content) {
		this.content = content;
	}

	public void setAlign(String alignStr) {
		if (alignStr.equals(ReportdogConstants.RTF_PARAGRAPH_ALIGN_LEFT))
			align = Element.ALIGN_LEFT;
		else if (alignStr.equals(ReportdogConstants.RTF_PARAGRAPH_ALIGN_MIDDLE))
			align = Element.ALIGN_MIDDLE;
		else if (alignStr.equals(ReportdogConstants.RTF_PARAGRAPH_ALIGN_RIGHT))
			align = Element.ALIGN_RIGHT;
		else if (alignStr.equals(ReportdogConstants.RTF_PARAGRAPH_ALIGN_CENTER))
			align = Element.ALIGN_CENTER;
		privateAlign = align;
	}

	public RTFParagraph() {
		try {
			if (baseFont == null)
				baseFont = BaseFont.createFont(
						"cn/edu/sdu/reportfont/simsun.ttf",
						BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (font == null)
			font = new Font(baseFont, 15, Font.NORMAL);
		updateStyle();
	}

	public void updateStyle() {
		privateBaseFont = baseFont;
		privateFont = font;
		privateAlign = align;
	}

	@Override
	public void injectContent(Object ob, HashMap paraMap) {
		// TODO Auto-generated method stub

	}

	public void setFontSize(int size) {
		font = new Font(baseFont, size, Font.NORMAL);
		privateFont = font;
	}

	@Override
	public void renderElement(ExportObjectOperator ud) {
		// TODO Auto-generated method stub
		Document doc = (Document) ud.getDocOperator();
		try {
			content = content.replace("\\n", "\n");
			Paragraph paragraph = new Paragraph(content);
			paragraph.setAlignment(privateAlign);
			paragraph.setFont(privateFont);
			doc.add(paragraph);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}