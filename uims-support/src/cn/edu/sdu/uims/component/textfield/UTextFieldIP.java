package cn.edu.sdu.uims.component.textfield;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class UTextFieldIP extends UTextField {

	private int min = 0;

	private int max = 255;

	public UTextFieldIP(int column, int max, int min) {
		super(column);
		this.min = min;
		this.max = max;
		this.setDocument(new IPSegDocument(column, min, max));
	}

	class IPSegDocument extends PlainDocument {

		private int limit;

		private int min;

		private int max;

		public IPSegDocument(int limit, int min, int max) {
			super();
			this.limit = limit;
			this.min = min;
			this.max = max;
		}

		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (((getText(0, getLength()).trim()).length() + str.length()) <= limit) {

				char[] upper = str.toCharArray();
				int length = 0;
				for (int i = 0; i < upper.length; i++) {
					if (upper[i] >= '0' && upper[i] <= '9') {
						upper[length++] = upper[i];
					}
				}
				String valueStr = getText(0, offset)
						+ new String(upper, 0, length)
						+ getText(offset, getLength() - offset).trim();
				if (!valueStr.equals("")) {
					int value = Integer.parseInt(valueStr.trim());
					if (value >= min && value <= max) {
						super.remove(0, getLength());
						super.insertString(0, String.valueOf(value), attr);
					} else {
						super.remove(0, getLength());
						super.insertString(0, String.valueOf(max), attr);
					}
				}

			}
		}

		public int getLimit() {
			return limit;
		}

		public void setLimit(int limit) {
			this.limit = limit;
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}

	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

}
