package org.octopus.reportdog.document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.octopus.reportdog.module.DocLattice_P;
import org.octopus.reportdog.module.DocLattice_Paper;

import cn.edu.sdu.common.pi.UDocumentUtilBaseI;
import cn.edu.sdu.common.reportdog.UDocument;
import cn.edu.sdu.common.reportdog.UPageNumberTemplate;

public interface UDocumentUtilI extends UDocumentUtilBaseI{
	

	public UDocument openDocumentFile(File file, DocLattice_Paper paperInfo,
			UPageNumberTemplate pageNum) throws Exception;

	public UDocument openDocumentStream(ByteArrayOutputStream aStream,
			DocLattice_Paper paperInfo, UPageNumberTemplate pageNum)
			throws Exception;


	public void close(UDocument doc) throws Exception;

	public void addParagraph(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception;

	public void addTable(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType) throws Exception;

	public void addImage(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception;

	public void addBlock(UDocument ud, DocLattice_P paragraphInstance)
			throws Exception;

	public void addEmbedTable(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType, List paragraphInstances) throws Exception;

	public void addFormatText(UDocument ud, DocLattice_P paragraphInstance,
			int arrageType) throws Exception;

	public void enforceClose(UDocument doc) throws Exception;
	
}
