package cn.edu.sdu.uims.base;

import java.io.Serializable;

import javax.swing.plaf.ComponentUI;

import cn.edu.sdu.common.reportdog.UColor;

public class UUIStyle implements Serializable {
	public String name;
	public UColor frontColor, backColor,selectedFrontColor, selectedBackColor;
	public UBorder border, selectedBorder;
	public ComponentUI comUI = null;
	public UUIStyle(){
	}
}
