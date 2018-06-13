package org.octopus.jsp_utils.custom_tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class InitJSPNotationTag extends TagSupport {
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			String str1 = "<script>";
			if (this.pageContext.getRequest().getAttribute("tempViewId") != null)
				str1 = str1 + "window.octopusViewId='"
						+ this.pageContext.getRequest().getAttribute("tempViewId").toString() + "';";
			str1 = str1 + "window.octopusContextPath='" + this.pageContext.getServletContext().getContextPath() + "';";
			str1 = str1 + "</script>";
			out.println(str1);
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		super.release();
	}
}