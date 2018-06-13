package cn.edu.sdu.uims.component.textfield;

import java.util.HashMap;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import cn.edu.sdu.uims.filter.CheckFilter;
import cn.edu.sdu.uims.filter.FilterI;



public class UTextFieldCheck extends UTextField {
	private CheckFilter filter = null;

	public UTextFieldCheck() {
	}

	public UTextFieldCheck(String text) {
		super(text);
	}

	public UTextFieldCheck(int columns) {
		super(columns);
	}

	public UTextFieldCheck(String text, int columns) {
		super(text, columns);
	}

	public UTextFieldCheck(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	protected Document createDefaultModel() {
		return new ContentDocument();
	}

	public FilterI getFilter() {
		return filter;
	}

	public void setFilter(FilterI filter) {
		this.filter = (CheckFilter) filter;
	}

	class ContentDocument extends PlainDocument {
		public void insertString(int offs, String old, AttributeSet a)
				throws BadLocationException {
			if (old == null) {
				return;
			}
			if (filter == null)
				return;
			if(template.maxLength > 0 && getLength() >= template.maxLength){
				return ;
			}
			String str = filter.change(old, this, getFilterParameters());
			if (str != null)
				super.insertString(offs, str, a);
		}
	}

	HashMap getFilterParameters() {
		return null;
	}

}
