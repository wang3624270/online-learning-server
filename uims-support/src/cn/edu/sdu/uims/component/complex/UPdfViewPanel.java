package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.io.ByteArrayInputStream;

import javax.swing.JPanel;

import org.icepdf.ri.common.SwingViewBuilder;

import cn.edu.sdu.uims.graph.form.UPdfViewDataForm;

public class UPdfViewPanel extends UComplexPanel {
	private UPdfViewDataForm dataForm;
	private USwingController controller;
	
	@Override
	public void initContents() {
		super.initContents();
		this.setLayout(new BorderLayout());
		controller = new USwingController();
		SwingViewBuilder factory = new SwingViewBuilder(controller);
		JPanel p = factory.buildViewerPanel();
		this.add(p, BorderLayout.CENTER);
	}
	public void setData(Object obj){
		if(obj == null)
			return;
		dataForm = (UPdfViewDataForm)obj;
		byte [] b = dataForm.getData();
		controller.openDocument(new ByteArrayInputStream(b),null,null);
		controller.setFileName(dataForm.getFileName());
	}

}
