package cn.edu.sdu.uims.component.complex.form;

import java.util.List;

public interface MultiLevelDataI {
	String getLabel();
	String getValue();
	boolean isSelected();
	void setSelected(boolean selected);
	List<MultiLevelDataI> getSubList();
	MultiLevelDataI getFather();
}
