package cn.edu.sdu.common.pi;

import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UParagraphTemplate;
import cn.edu.sdu.common.reportdog.UTableAttribute;

public interface UDocumentUtilBaseI {
	public File getNewTempFile() throws Exception;

	public UDocument openDocumentFile(File file, UPaperTemplate paperTemplate,
			UPageNumberTemplate pageNum) throws Exception;

	public void setPaperInfo(UDocument doc);

	public void newPage(UDocument doc);

	public void addTable(UDocument doc, UTableAttribute tableAttr,
			UCellAttribute[] datas, int arrageType);

	public void addParagraph(UDocument doc, String content,
			UParagraphTemplate template);

	public void addImage(UDocument ud, String filePath,
			UParagraphTemplate template);

	public void closeDocumentFile(UDocument doc);

	public byte[] getFileBytes(String fileName);

	public UDocument openDocumentStream(ByteArrayOutputStream stream,
			UPaperTemplate paperTemplate, UPageNumberTemplate pageNum)
			throws Exception;
		
}
