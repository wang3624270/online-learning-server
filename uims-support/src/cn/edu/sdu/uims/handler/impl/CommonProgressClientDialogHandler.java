package cn.edu.sdu.uims.handler.impl;

import java.util.HashMap;

import cn.edu.sdu.uims.component.UProgressBar;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.form.impl.CommonProgressDataForm;


public class CommonProgressClientDialogHandler extends UDialogHandler implements Runnable{
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		UProgressBar bar  = (UProgressBar)this.component.getSubComponent("progressBar");
		CommonProgressDataForm dlgForm = (CommonProgressDataForm)dataForm;
		HashMap map = dlgForm.getParaMap();
		UDialogPanel dlg = (UDialogPanel)this.component;
		UProgressProcessorI oh = dlgForm.getPi();
		if(oh == null)
			oh = (UProgressProcessorI)dlg.getOwnerHandler();
		try{
			for(int i = 0; i < dlgForm.getCount();i++) {
				bar.setValue((i *100) /dlgForm.getCount());
				if(oh != null)
					oh.requestServerProcessOne(dlgForm, i);
				Thread.sleep(10);
			}
			bar.setValue(100);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		dlg.doClose();
	}

}
