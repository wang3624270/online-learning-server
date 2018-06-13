package cn.edu.sdu.uims.component.textfield;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class UTextFieldExpression extends UTextField {
	public void initContents() {
		// TODO Auto-generated method stub
		super.initContents();
		if (template.expression != null && !template.expression.equals("")) {
			this.setDocument(new ExpressionDocument(template.expression));
		}
	}

	class ExpressionDocument extends PlainDocument {

		private String expression;

		public ExpressionDocument(String expression) {
			super();
			this.expression = expression;
		}

		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			String valueStr = getText(0, offset) + str
					+ getText(offset, getLength() - offset).trim();
			if (!valueStr.equals("")) {
				if (validate(valueStr)) {
					super.remove(0, getLength());
					super.insertString(0, valueStr, attr);
				} else {
					super.remove(0, getLength());
				}
			}
		}

		private boolean validate(String valueStr) {
			// TODO Auto-generated method stub
			return Pattern.matches(expression,valueStr);
		}

	}

}
