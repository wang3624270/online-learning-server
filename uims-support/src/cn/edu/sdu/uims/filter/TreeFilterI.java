package cn.edu.sdu.uims.filter;

import cn.edu.sdu.uims.form.impl.UTreeNodeForm;

public interface TreeFilterI extends FilterI {
	public String getRootName();
	public void setAddedData(UTreeNodeForm root);
}
