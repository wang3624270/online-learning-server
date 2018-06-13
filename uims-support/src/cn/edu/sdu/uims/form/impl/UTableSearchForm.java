package cn.edu.sdu.uims.form.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.form.UFormModifyStatusI;
import cn.edu.sdu.common.form.USFormI;
import cn.edu.sdu.uims.util.DataProcessUtils;


public class UTableSearchForm extends UForm{
	protected Object []items;

	public Object[] getItems() {
		return items;
	}

	public void setItems(Object[] items) {
		this.items = items;
	}
	public void setItemsByList(List list) {
		if(list == null || list.size()==0) 
			items = null;
		else {
			items = new Object[list.size()];
			for(int i = 0; i < items.length;i++) {
				items[i] = list.get(i);
			}
		}
	}

	
	public void insertItems(Object []os){
		if(os == null || os.length == 0)
			return ;
		List list = new ArrayList();
		for(int i = 0; i < os.length;i++)
			list.add(os[i]);
		insertItems(list);
	}
	public void insertItems(List list){
		items = DataProcessUtils.insertItems(items, list);
	}
	public void addItem(Object item){
		items = DataProcessUtils.addItem(items, item);
	}
	public void sort(){
		Arrays.sort(items);
	}
	public void removeObjects(int indexs[]){
		items =DataProcessUtils.removeObjects(items, indexs);
	}
	public void removeObject(Object o){
		items =DataProcessUtils.removeObject(items, o);
	}
	public int getSelectItemsCount(){
		if(items == null || items.length == 0) 
			return 0;
		int count =0;
		USFormI f;
		for(int i = 0; i < items.length;i++) {
			if(!(items[i] instanceof USFormI))
				continue;
			f = (USFormI)items[i];
			if(f.getSelect() != null && f.getSelect().booleanValue()) 
				count ++;
		}
		return count;
	}
	public List getItemList(){
		if(items == null || items.length == 0) 
			return null;
		List list = new ArrayList();
		for(int i = 0; i < items.length;i++) {
			list.add(items[i]);
		}
		return list;
	}
	public List getSelectItemList(){
		if(items == null || items.length == 0) 
			return null;
		List list = new ArrayList();
		USFormI f;
		for(int i = 0; i < items.length;i++) {
			if(!(items[i] instanceof USFormI))
				continue;
			f = (USFormI)items[i];
			if(f.getSelect() != null && f.getSelect().booleanValue()) 
				list.add(f);
		}
		return list;
	}
	public List getSelectItemList(int indices[]){
		if(items == null || items.length == 0 || indices == null || indices.length == 0) 
			return null;
		List list = new ArrayList(indices.length);
		for(int i = 0; i < indices.length;i++) {
			list.add(items[indices[i]]);
		}
		return list;
	}

	public void doSelectAll(Boolean b){
		if(items == null || items.length == 0)
			return;
		if(!(items[0] instanceof UFormModifyStatusI && items[0] instanceof USFormI)) 
			return; 
		UFormModifyStatusI mi;
		USFormI si;
		for(int i = 0; i < items.length;i++ ) {
			mi = (UFormModifyStatusI) items[i];
			mi.setModify(true);
			si = (USFormI)items[i];
			si.setSelect(b);
		}
	}
	public List getModifyItemsList(){
		if(items == null || items.length == 0)
			return null;
		if(!(items[0] instanceof UFormModifyStatusI)) 
			return null; 
		List list = new ArrayList();
		UFormModifyStatusI mi;
		for(int i = 0; i < items.length;i++){
			mi = (UFormModifyStatusI)items[i];
			if(mi.isModify())
				list.add(mi);				
		}
		return list;
	}
	public void clearModifyItemsMark(){
		if(items == null || items.length == 0)
			return;
		if(!(items[0] instanceof UFormModifyStatusI)) 
			return ; 
		UFormModifyStatusI mi;
		for(int i = 0; i <items.length;i++){
			mi = (UFormModifyStatusI)items[i];
			mi.setModify(false);
		}
	}
	public String toString(){
		String str ="";
		if(items == null || items.length == 0)
			return str;
		for(int i = 0; i < items.length;i++){
			if(items[i]== null)
				continue;
			str += items[i].toString();
			if(i < items.length-1)
				str += ";";
		}
		return str;
	}

	
}
