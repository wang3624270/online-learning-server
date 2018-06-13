package org.sdu.file_util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdu.uploadListener.UploadListener;

public class FileUtility {
	private static final String DEFAULT_CONN = "default";
	private static final String HTTP_CONN = "http";

	public static boolean uploadFile(InputStream localInStream,
			String remotePathname) throws FileNotFoundException, IOException {
		return uploadFile(localInStream, remotePathname, DEFAULT_CONN);
	}
	
	public static boolean uploadFileToWWW(InputStream localInStream,
			String remotePathname) throws FileNotFoundException, IOException {
		return uploadFile(localInStream, remotePathname, HTTP_CONN);
	}
	
	//auther:wangqianqian 获取上传进度
	public static boolean uploadFileForElearning(UploadListener listener,InputStream localInStream,
			String remotePathname) throws FileNotFoundException, IOException {
		return uploadFileForElearning(listener,localInStream, remotePathname, DEFAULT_CONN);
	}

	public static void downloadFile(OutputStream localOutStream,
			String remotePathname) throws FileNotFoundException, IOException {
		downloadFile(localOutStream, remotePathname, DEFAULT_CONN);
	}
	
	public static void downloadFileFromWWW(OutputStream localOutStream,
			String remotePathname) throws FileNotFoundException, IOException {
		downloadFile(localOutStream, remotePathname, HTTP_CONN);
	}

	public static boolean deleteFile(String remotePathname) {
		return deleteFile(remotePathname, DEFAULT_CONN);
	}
	
	public static boolean deleteFileFromWWW(String remotePathname) {
		return deleteFile(remotePathname, HTTP_CONN);
	}

	public static boolean mkdirs(String remotePathname) {
		return mkdirs(remotePathname, DEFAULT_CONN);
	}

	public static boolean rmdirs(String remotePathname) {
		return rmdirs(remotePathname, DEFAULT_CONN);
	}

	public static Object[] listFiles(String remotePathname) throws IOException {
		return listFiles(remotePathname, DEFAULT_CONN);
	}

	public static File findFile(String remotePathname) throws IOException {
		return findFile(remotePathname, DEFAULT_CONN);
	}

	public static boolean uploadFile(InputStream localInStream,
			String remotePathname, String connName)
			throws FileNotFoundException, IOException {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		boolean rs = fileConnection.writeFile(localInStream, remotePathname);
		fileConnection.close();
		return rs;
	}
	
	public static boolean uploadFileForElearning(UploadListener listener,InputStream localInStream,
			String remotePathname, String connName)
			throws FileNotFoundException, IOException {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		boolean rs = fileConnection.writeFileForElearning(listener,localInStream, remotePathname);
		fileConnection.close();
		return rs;
	}

	public static void downloadFile(OutputStream localOutStream,
			String remotePathname, String connName)
			throws FileNotFoundException, IOException {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		fileConnection.readFile(localOutStream, remotePathname);
		fileConnection.close();
	}

	public static byte[] downloadFileInBytes(String remotePathname,
			String connName) throws FileNotFoundException, IOException {
		if (connName == null)
			connName = DEFAULT_CONN;
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		byte[] bytes = null;
		try {
			bytes = fileConnection.readFileInBytes(remotePathname);
		} catch (Exception e) {
			return null;
		}
		fileConnection.close();
		return bytes;
	}

	public static void bsDownloadFile(String remotePathAndFileName,
			String connName, HttpServletResponse httpServletResponse)
			throws FileNotFoundException, IOException {
		if (connName == null)
			connName = DEFAULT_CONN;
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		byte[] bytes = null;
		try {
			bytes = fileConnection.readFileInBytes(remotePathAndFileName);
		} catch (Exception e) {

		}
		fileConnection.close();
		// ////////////////////////////////////////////
		String fileType = "pdf";

		if (remotePathAndFileName != null)
			fileType = remotePathAndFileName.substring(remotePathAndFileName
					.indexOf('.') + 1);
		if (fileType.equals("pdf"))
			httpServletResponse.setContentType("APPLICATION/pdf");
		else if (fileType.equals("doc") || fileType.equals("docx"))
			httpServletResponse.setContentType("APPLICATION/vnd.ms-word");
		else if (fileType.equals("xls") || fileType.equals("xlsx"))
			httpServletResponse.setContentType("APPLICATION/vnd.ms-excel");

		httpServletResponse.setHeader(
				"content-disposition",
				"attachment;filename=\""
						+ remotePathAndFileName.substring(remotePathAndFileName
								.lastIndexOf('/')+1) + "\"");
		ServletOutputStream seveletOuputStream;
		try {
			seveletOuputStream = httpServletResponse.getOutputStream();

			seveletOuputStream.write(bytes, 0, bytes.length);
			seveletOuputStream.flush();
			seveletOuputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public static byte[] downloadFileInBytes(String remotePathname,
			String downloadFileName, String connName,
			HttpServletResponse httpServletResponse)
			throws FileNotFoundException, IOException {
		if (connName == null)
			connName = DEFAULT_CONN;
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		byte[] bytes = null;
		try {
			bytes = fileConnection.readFileInBytes(remotePathname);
		} catch (Exception e) {
			return null;
		}
		fileConnection.close();
		// ////////////////////////////////////////////
		String fileType = "pdf";

		if (remotePathname != null)
			fileType = remotePathname.substring(remotePathname.indexOf('.'));
		if (fileType.equals("pdf"))
			httpServletResponse.setContentType("APPLICATION/pdf");
		else if (fileType.equals("doc") || fileType.equals("docx"))
			httpServletResponse.setContentType("APPLICATION/vnd.ms-word");
		else if (fileType.equals("xls") || fileType.equals("xlsx"))
			httpServletResponse.setContentType("APPLICATION/vnd.ms-excel");

		if (downloadFileName == null)
			downloadFileName = "export." + fileType;

		httpServletResponse.setHeader("content-disposition",
				"attachment;filename=\"" + downloadFileName + "\"");
		ServletOutputStream seveletOuputStream;
		try {
			seveletOuputStream = httpServletResponse.getOutputStream();

			seveletOuputStream.write(bytes, 0, bytes.length);
			seveletOuputStream.flush();
			seveletOuputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return bytes;
	}

	public static boolean deleteFile(String remotePathname, String connName) {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		boolean rs = fileConnection.deleteFile(remotePathname);
		try {
			fileConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static boolean mkdirs(String remotePathname, String connName) {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		boolean rs = fileConnection.mkdirs(remotePathname);
		try {
			fileConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static boolean rmdirs(String remotePathname, String connName) {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		boolean rs = fileConnection.deleteDirectory(remotePathname);
		try {
			fileConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static Object[] listFiles(String remotePathname, String connName)
			throws IOException {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		Object[] files = fileConnection.listFiles(remotePathname);
		try {
			fileConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}

	public static File findFile(String remotePathname, String connName)
			throws IOException {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		File file = fileConnection.findFile(remotePathname);
		try {
			fileConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
 
	public static boolean createBlankFile(String remotePathname)
			throws IOException {
		return createBlankFile(remotePathname, DEFAULT_CONN);
	}

	public static boolean createBlankFile(String remotePathname, String connName)
			throws IOException {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(connName);
		boolean rs = fileConnection.createBlankFile(remotePathname);
		fileConnection.close();
		return rs;
	}

	public static boolean changeDirectoryName(String remotePathname,
			String newName) throws IOException {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(DEFAULT_CONN);
		boolean rs = fileConnection
				.changeDirectoryName(remotePathname, newName);
		fileConnection.close();
		return rs;
	}

	public static void directoryFileMove(String from, String to)
			throws Exception {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(DEFAULT_CONN);
		fileConnection.directoryFileMove(from, to);
		fileConnection.close();
	}

	public static void fileMove(String from, String to) throws Exception {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(DEFAULT_CONN);
		fileConnection.fileMove(from, to);
		fileConnection.close();
	}

	public static void deleteFolder(String folderPath) {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(DEFAULT_CONN);
		try {
			fileConnection.deleteFolder(folderPath);
			fileConnection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean exist(String filePath) throws Exception {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(DEFAULT_CONN);
		boolean rs = fileConnection.exist(filePath);
		fileConnection.close();
		return rs;
	}

	/**
	 * 
	 * @param remoteRelativePathName
	 *            上传文件的路径
	 * @return author:zhengjiecai Aug 11, 2010
	 */
	public static File retrieveRemoteFile(String remoteRelativePathName) {
		File retrieveFile = null;
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(DEFAULT_CONN);
		try {
			retrieveFile = fileConnection
					.retrieveRemoteFile(remoteRelativePathName);
			fileConnection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return retrieveFile;
	}
	
	public static String getFileDownloadName(HttpServletRequest request,
			String name, boolean isNeedConvertEncode) {
		if (isNeedConvertEncode) {
			String downFileName = "";
			try {
				if (request.getHeader("User-Agent").indexOf("MSIE") != -1) {
					downFileName = URLEncoder.encode(name, "UTF-8");
				} else {
					downFileName = new String(name.getBytes("UTF-8"),
							"ISO-8859-1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return downFileName;
		}
		return name;
	}


	public static String directoryFileUpload(File file, String destPath)
			throws Exception {
		FileConnection fileConnection = FileConnectionFactory
				.currentConnection(HTTP_CONN);
		String URL = fileConnection.directoryFileUpload(file, destPath);
		return URL;
	}
	
}