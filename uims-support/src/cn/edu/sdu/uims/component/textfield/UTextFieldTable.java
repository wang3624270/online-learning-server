package cn.edu.sdu.uims.component.textfield;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.form.impl.UTableForm;
import cn.edu.sdu.uims.service.UFactory;

public class UTextFieldTable extends UTextField implements MouseListener{

	private Object[] items = null;
	private UComponentI screenOwner = null;

	public void initContents() {
		// TODO Auto-generated method stub
		this.addMouseListener(this);
		this.setEditable(false);
	}
	public void updateTextField(){
		String str = "";
		if(items != null && items.length > 0) {
			for(int i = 0; i < items.length-1;i++) {
				str += items[i].toString() + ";";
			}
			str += items[items.length-1].toString();
		}
		this.setText(str);
		
	}
	public void setData(Object obj){
		items = (Object [])obj;
		updateTextField();
	}
	public Object getData() {
		return items;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		displayTableDialog();		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	void displayTableDialog(){
		Object [] o = null;
		if(screenOwner != null || screenOwner instanceof UTablePanel ) {
			UTablePanel tp = (UTablePanel)screenOwner;
			o = (Object[] )tp.getSelectRowColumnObject();
		}else 
			o = items;
		if(template.dialogName == null) 
			return;
		try {
			UTableForm tForm = new UTableForm();
			tForm.setItems(items);
			UTemplate temp = (UTemplate) UFactory.getModelSession().getTemplate(
					UConstants.MAPKEY_DIALOG, template.dialogName);
			UDialogPanel dlg = (UDialogPanel) temp.newComponent();
			if (dlg == null) 
				return;
			dlg.SetOwner(null);
			dlg.setComponentName(template.dialogName);
			dlg.setTemplate(temp);
			dlg.setDialogForm(tForm);
			dlg.init();
			if(!dlg.getReturnValue().equals(dlg.RETURN_OK))
				return;
			items =tForm.getItems();
			updateTextField();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		this.screenOwner = screenOwner;
	}

}
