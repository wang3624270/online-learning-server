package org.sdu.file_util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.sdu.uploadListener.UploadListener;

/**
 * 
 * @author JING Ming
 * 
 */
public interface FileConnection {
	void readFile(OutputStream localOutStream, String remotePathname)
			throws FileNotFoundException, IOException;

	boolean writeFile(InputStream localInStream, String remotePathname)
			throws FileNotFoundException, IOException;
	
	boolean writeFileForElearning(UploadListener listener,InputStream localInStream, String remotePathname)
			throws FileNotFoundException, IOException;

	void close() throws IOException;

	boolean isOpen();

	boolean deleteFile(String remotePathname);

	boolean deleteDirectory(String remotePathname);

	boolean mkdirs(String remotePathname);

	Object[] listFiles(String remotePathname) throws IOException;

	boolean createBlankFile(String remotePathname);

	boolean changeDirectoryName(String remotePathname, String newName);

	void directoryFileMove(String from, String to) throws Exception;

	boolean fileMove(String from, String to) throws IOException;

	void deleteFolder(String folderPath) throws Exception;

	boolean exist(String filePath);

	// added by zjc
	File retrieveRemoteFile(String remoteRelativePathName);

	byte[] readFileInBytes(String remotePathname) throws FileNotFoundException,
			IOException;

	public File findFile(String remotePathname) throws IOException;
	
	public String directoryFileUpload(File file , String destPath);
}
