package org.octopus.reportdog.document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.octopus.reportdog.module.DocLattice_P;

import cn.edu.sdu.common.reportdog.UPageNumberTemplate;
import cn.edu.sdu.common.reportdog.UPaperTemplate;

public class BaseStreamDocumentUtil {

	public File getNewTempFile() throws Exception {
		return null;
	}

	public ExportObjectOperator openDocumentStream(
			ByteArrayOutputStream aStream, UPaperTemplate paperTemplate,
			UPageNumberTemplate pageNum) throws Exception {
		return null;
	}

	public void newPage(ExportObjectOperator doc) throws Exception {
	}

	public void close(ExportObjectOperator doc) throws Exception {
	}

	public void addParagraph(ExportObjectOperator ud,
			DocLattice_P paragraphInstance) throws Exception {
	}

	public void addTable(ExportObjectOperator ud,
			DocLattice_P paragraphInstance, int arrageType)
			throws Exception {
	}

	public void addImage(ExportObjectOperator ud,
			DocLattice_P paragraphInstance) throws Exception {
	}

	public void addBlock(ExportObjectOperator ud,
			DocLattice_P paragraphInstance) throws Exception {
	}

	public void addEmbedTable(ExportObjectOperator ud,
			DocLattice_P paragraphInstance, int arrageType,
			List paragraphInstances) throws Exception {
	}

	public void addFormatText(ExportObjectOperator ud,
			DocLattice_P paragraphInstance, int arrageType)
			throws Exception {
	}

	public void enforceClose(ExportObjectOperator doc) throws Exception {
	}

	public ExportObjectOperator getExportDocument(File file) throws Exception {
		ExportObjectOperator ud = new ExportObjectOperator();
		ud.setDocName(file.getAbsolutePath());
		ud.setDocTarget(file);
		// ud.paraMap.put("tempFile", file);
		return ud;
	}

	public void initExportTarget(ExportObjectOperator document) {
	}

}
