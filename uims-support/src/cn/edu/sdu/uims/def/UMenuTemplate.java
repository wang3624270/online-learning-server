package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.uims.constant.UConstants;

public class UMenuTemplate extends UMenuItemTemplate {
	public UMenuItemTemplate[] items;
	public String strSubMenu;
	public UMenuItemTemplate[] getItems() {
		return items;
	}

	public void setItems(UMenuItemTemplate[] items) {
		this.items = items;
	}

	public boolean isConditionMenu() {
		return conditionMenu;
	}

	public void setConditionMenu(boolean conditionMenu) {
		this.conditionMenu = conditionMenu;
	}
	public boolean conditionMenu;
	private int depth = 0;

	public UMenuTemplate() {
		type = UConstants.COMPONENT_MENU_MENU;
	}

	public void addItems(List<UMenuItemTemplate> itemList) {
		if (itemList == null || itemList.size() == 0) {
			return;
		} else {
			if (items == null) {
				items = templateListToArray(itemList);
			} else {
				List<UMenuItemTemplate> tempList = new ArrayList<UMenuItemTemplate>(
						items.length + itemList.size());
				for (int i = 0; i < items.length; i++) {
					tempList.add(items[i]);
				}
				tempList.addAll(itemList);
				items = templateListToArray(tempList);
			}

		}
	}

	private UMenuItemTemplate[] templateListToArray(
			List<UMenuItemTemplate> templateList) {
		if (templateList == null)
			return null;
		UMenuItemTemplate[] templateArray = new UMenuItemTemplate[templateList
				.size()];
		for (int i = 0; i < templateArray.length; i++) {
			templateArray[i] = templateList.get(i);
		}
		return templateArray;
	}

	public void setDepth(int depthValue) {
		// TODO Auto-generated method stub
		this.depth = depthValue;
	}

	public int getDepth() {
		// TODO Auto-generated method stub
		return this.depth;
	}

	public int size() {
		// TODO Auto-generated method stub
		return items.length;
	}

	public UMenuItemTemplate get(int i) {
		// TODO Auto-generated method stub
		return items[i];
	}

	public UMenuTemplate getMenuTemplate(int i) {
		// TODO Auto-generated method stub
		return (UMenuTemplate)items[i];
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		int len, i;
		len = in.readInt();
		if(len == 0)
			items = null;
		else {
			items = new UMenuItemTemplate[len];
			for(i = 0; i < len; i++) {
				items[i] = (UMenuItemTemplate)readTemplate(in);
			}
		}			
		conditionMenu = in.readBoolean();
		depth = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		int i;
		if(items == null)
			out.writeInt(0);
		else {
			out.writeInt(items.length);
			for(i = 0; i < items.length;i++) 
				writeTemplate(out, items[i]);
		}
		out.writeBoolean(conditionMenu);
		out.writeInt(depth);
	}

	public String getStrSubMenu() {
		return strSubMenu;
	}

	public void setStrSubMenu(String strSubMenu) {
		this.strSubMenu = strSubMenu;
	}
	
}
