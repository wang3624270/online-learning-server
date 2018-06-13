package cn.edu.sdu.uims.graph.handler;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.list.UList;
import cn.edu.sdu.uims.graph.form.UMutilePdfViewDataForm;
import cn.edu.sdu.uims.graph.form.UPdfViewDataForm;
import cn.edu.sdu.uims.handler.impl.UDialogHandler;
import cn.edu.sdu.uims.util.FilePrintUtil;

public class UMutilePdfPrintPreviewDialogHandler extends UDialogHandler {
	
	public void processListSelectionEvent(Object  o, String cmd){
		ListSelectionEvent e = (ListSelectionEvent)o;
		UList uList = (UList)e.getSource();
		String selected = uList.getSelectedValue().toString();
//		int index = (int) uList.getSelectedValue();
		int index = e.getFirstIndex();
		UMutilePdfViewDataForm f = (UMutilePdfViewDataForm)dataForm;
		List list = f.getPdfList();
		if(list == null || list.size() == 0 || index <0 ||index >= list.size())
			return;
		for(int i=0;i<list.size();i++){
			UPdfViewDataForm pf = (UPdfViewDataForm)list.get(i);
			if(selected.equals(pf.getFileName())){
				index = i;
			}
		}
		UPdfViewDataForm pf = (UPdfViewDataForm)list.get(index);
		f.setData(pf);
		UComponentI com = this.component.getSubComponent("pdfView");
		com.setData(pf);
	}
	public List<byte[]> getPdfDataList(){
		UMutilePdfViewDataForm f = (UMutilePdfViewDataForm)dataForm;
		List list = f.getPdfList();
		if(list == null || list.size() == 0 )
			return null;
		UPdfViewDataForm pf;
		List<byte[]> retList = new ArrayList<byte[]>();
		for(int i = 0; i < list.size();i++){
			pf = (UPdfViewDataForm)list.get(i);
			retList.add(pf.getData());
		}
		return retList;
	}
	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent)o;
		String amd = e.getActionCommand();
		if(amd.equals("mergeSave")) {
			mergeSave();
		}else if(amd.equals("mergePrint")) {
			mergePrint();
		}else 
			super.processActionEvent(o,cmd);
	}
	public void mergeSave(){
		List<byte[]> list = getPdfDataList();
		if(list == null)
			return;
		FilePrintUtil.mergePDFFileStreamToOneFileCS(list);
	}
	public void mergePrint(){
		List<byte[]> list = getPdfDataList();
		if(list == null)
			return;
		try{
			FilePrintUtil.printPDFFile(FilePrintUtil.mergePDFFileStream(list), false);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
