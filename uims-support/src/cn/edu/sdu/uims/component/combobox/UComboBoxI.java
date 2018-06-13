package cn.edu.sdu.uims.component.combobox;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;

import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.filter.FilterI;

public interface UComboBoxI {
	public Component getAWTComponent();
	public boolean getMultiple();
	Object getSelectedItem(); 
	int getItemCount();
	Object getItemAt(int index);
	void setSelectedIndex(int index);
	Object getData();
	void setData(Object o);
	void setAddedDatas(List list);
	void setMaximumRowCount(int c);
	void setPreferredSize(Dimension d);
	void addActionListener(ActionListener l);
	void setElementTemplate(UElementTemplate e);
	void initContents();
	void setEditable(boolean b);
	void setComboBoxSize(int w, int h);
	void setFilter(FilterI filter);

}