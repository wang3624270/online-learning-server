package cn.edu.sdu.uims.handler.impl;

import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.form.impl.CommonProgressDataForm;
import cn.edu.sdu.uims.progress.ProgressElementObject;
import cn.edu.sdu.uims.progress.ProgressPanel;

public class CommonProgressServerDialogHandler extends UDialogHandler implements Runnable{
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ProgressPanel bar  = (ProgressPanel)this.component.getSubComponent("progressBar");
		CommonProgressDataForm dlgForm = (CommonProgressDataForm)dataForm;
		UDialogPanel dlg = (UDialogPanel)this.component;
		UProgressProcessorI oh = (UProgressProcessorI)dlg.getOwnerHandler();
		ProgressElementObject prgObject = bar.initProgressObject();
		bar.start();
		oh.requestServerProcess(dlgForm,prgObject);
		bar.end();
		dlg.doClose();
	}

}
