package cn.edu.sdu.uims.handler.impl;

import cn.edu.sdu.uims.component.complex.UDataExportPanel;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.form.impl.DataIoProcessForm;
import cn.edu.sdu.uims.progress.ProgressElementObject;
import cn.edu.sdu.uims.progress.ProgressPanel;

public class DataExportServerWithProcessDialogHandler extends UDialogHandler implements Runnable{

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ProgressPanel bar  = (ProgressPanel)this.component.getSubComponent("progressBar");
		DataIoProcessForm dlgForm = (DataIoProcessForm)dataForm;
		UDialogPanel dlg = (UDialogPanel)this.component;
		UFormHandler oh = (UFormHandler)dlg.getOwnerHandler();
		UDataExportPanel p = (UDataExportPanel)(oh.getComponent().getSubComponent(dlgForm.getComName()));
		ProgressElementObject prgObject = bar.initProgressObject();
		bar.start();
		p.doDataExportServer(dlgForm.getDataIoStruct(),prgObject);
		bar.end();
		dlg.doClose();
	}

}
