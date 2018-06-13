package cn.edu.sdu.uims.component.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.component.event.UEventAdaptor;
import cn.edu.sdu.uims.def.UDialogTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.frame.UClientFrame;

public class UProgressDialogPanel extends UDialogPanel {

	public UProgressDialogPanel() {
		this(new UPanelTemplate());
	}

	public UProgressDialogPanel(UPanelTemplate panelTemplate) {
		super(panelTemplate);
	}

	public void init() {
		container = new UDialog(UClientFrame.getInstance(), "", false);
		UDialogTemplate dialogTemplate = (UDialogTemplate) panelTemplate;
		initBackImage();

		eventAdaptor = new UEventAdaptor(this);
		container.addWindowListener(eventAdaptor);
		container.setUParent(this);
		initComponents();
		initHandlerObject(panelTemplate.name,null);
		initContents();
		UFormI form =  initDataByHandlerAfterInitContents();
		if (dialogForm != null) {
//			handler.setForm(dialogForm);
			form = dialogForm;
		}
		setData(form);
//		handler.setInitData(this);
		int xo, yo;
		Dimension d;
		if (dialogTemplate.xo != 0 && dialogTemplate.yo != 0) {
			xo = dialogTemplate.xo;
			yo = dialogTemplate.yo;
		} else {
			Frame f = UClientFrame.getInstance();
			if (f != null) {
				d = f.getSize();
			} else
				d = new Dimension(1024, 768);
			xo = (d.width - panelTemplate.width) / 2;
			yo = (d.height - panelTemplate.height) / 2;
		}
		container.setLayout(new BorderLayout());
		container.setContentPane(jContentPane);
		container.setSize(panelTemplate.width, panelTemplate.height);
		container.setLocation(xo, yo);
		container.setTitle(panelTemplate.title);
		container.setResizable(false);
		if(getHandler() != null)
			getHandler().start();		
		container.setVisible(true);
	}

	public void setModal(boolean modal) {
		container.setModal(modal);
	}
}
