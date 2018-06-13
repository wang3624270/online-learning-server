package cn.edu.sdu.uims.component;

import java.awt.event.ActionListener;

import javax.swing.Icon;

import cn.edu.sdu.uims.base.UComponentI;

public interface UActionComponentI extends UComponentI{
	public void setIcon(Icon icon);
	public void setToolTipText(String name);
	public void setActionCommand(String name);
	public void addActionListener(ActionListener l);
}
