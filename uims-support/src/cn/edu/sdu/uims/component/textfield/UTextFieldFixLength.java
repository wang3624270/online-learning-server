package cn.edu.sdu.uims.component.textfield;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class UTextFieldFixLength extends UTextField {
	  public UTextFieldFixLength() {
	  }

	  public UTextFieldFixLength(String text) {
	    super(text);
	  }

	  public UTextFieldFixLength(int columns) {
	    super(columns);
	  }

	  public UTextFieldFixLength(String text, int columns) {
	    super(text, columns);
	  }

	  protected Document createDefaultModel() {
	    return new FixLengthDocument();
	  }

	  public UTextFieldFixLength(Document doc, String text, int columns) {
	    super(doc, text, columns);
	  }

	  class FixLengthDocument
	      extends PlainDocument {
	    public void insertString(int offs, String str, AttributeSet a) throws
	        BadLocationException {
	    	int maxLength = 10;
	    	 if(template != null)
	    		 maxLength = template.maxLength;
	      if (str.getBytes().length + getText(0, getLength()).getBytes().length
	          >= maxLength) {
	        String s, sRet;
	        sRet = "";
	        int i, nLength,j;
	        byte ss[];
	        nLength = maxLength - getText(0, getLength()).getBytes().length;
	        i = 0;
	        j = 0;
	        while ( (i + 2) <= nLength) {
	          s = str.substring(j, j + 1);
	          ss = s.getBytes();
	          if (ss[0] > 0) {
	            sRet += str.substring(j, j + 1);
	            i++;
	          }
	          else {
	            sRet += str.substring(j, j + 1);
	            i += 2;
	          }
	          j++;
	        }
	        if (sRet.getBytes().length + 1 == nLength){
	          s = str.substring(j);
	          ss = s.getBytes();
	          if (ss[0] > 0) {
	            sRet += str.substring(j, j + 1);
	            i++;
	          }
	        }
	        str = sRet;
	      }
	      //end modify
	      if (getLength() >= maxLength) {
	        return;
	      }
	      //modify by xiaoliu for 汉字 2004.08.04
	      if (getText(0, getLength()).getBytes().length >= maxLength) {
	        return;
	      }
	      //end modify
	      if (str == null) {
	        return;
	      }
	      super.insertString(offs, str, a);
	    }
	  }
	}
