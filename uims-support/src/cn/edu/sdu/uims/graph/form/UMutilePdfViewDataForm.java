package cn.edu.sdu.uims.graph.form;

import java.util.List;

import cn.edu.sdu.common.form.UForm;


public class UMutilePdfViewDataForm extends UForm {
	private UPdfViewDataForm data;
	private List pdfList;

	public List getPdfList() {
		return pdfList;
	}

	public void setPdfList(List pdfList) {
		this.pdfList = pdfList;
	}

	public UPdfViewDataForm getData() {
		return data;
	}

	public void setData(UPdfViewDataForm data) {
		this.data = data;
	}
}
