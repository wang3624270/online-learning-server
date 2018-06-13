package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;

public class UDocumentTemplate extends UContentTemplate{
	public int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public UPaperTemplate getPaperTemplate() {
		return paperTemplate;
	}
	public void setPaperTemplate(UPaperTemplate paperTemplate) {
		this.paperTemplate = paperTemplate;
	}
	public UCellAttribute getTitle() {
		return title;
	}
	public void setTitle(UCellAttribute title) {
		this.title = title;
	}
	public UPageNumberTemplate getPageNumberTemplate() {
		return pageNumberTemplate;
	}
	public void setPageNumberTemplate(UPageNumberTemplate pageNumberTemplate) {
		this.pageNumberTemplate = pageNumberTemplate;
	}
	public int getNumPerPaper() {
		return numPerPaper;
	}
	public void setNumPerPaper(int numPerPaper) {
		this.numPerPaper = numPerPaper;
	}
	public UPaperTemplate paperTemplate;
	public UCellAttribute title;
	public UPageNumberTemplate pageNumberTemplate;
	//刘洋
	public int numPerPaper;
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		type = in.readInt();
		paperTemplate = (UPaperTemplate)readTemplate(in);
		title = (UCellAttribute)readTemplate(in);
		pageNumberTemplate = (UPageNumberTemplate)readTemplate(in);
		//刘洋
		numPerPaper = in.readInt();
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(type);
		writeTemplate(out, paperTemplate);
		writeTemplate(out, title);
		writeTemplate(out, pageNumberTemplate);
		out.writeInt(numPerPaper);
	}

}
