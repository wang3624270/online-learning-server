package cn.edu.sdu.uims.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.sdu.file_util.FileUtility;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

/**
 * 附件管理客户端
 * 
 * @author CJY
 * 
 */
public class ClientAccessoriesUtility {

	public static String Server_Context_File_Path = "Server_Context_File_Path";

	/**
	 * 上传文件
	 * 
	 * @param localInStream
	 *            本地输入流
	 * @param remotePathname
	 *            路径类型，目前是路径
	 * @param fileName
	 *            文件名，包含后缀名
	 * @return accId 返回值是附件ID，可作为外键
	 * @param remark
	 *            上传文件备注
	 * 
	 */
	public static String uploadFile(InputStream localInStream, String remotePathname, String fileName, String remark) {
		if (remotePathname != null && fileName.indexOf('.') > 0 && fileName.indexOf('.') < fileName.length()) {
			RmiRequest request = new RmiRequest();
			RmiResponse respond = new RmiResponse();
			request.add("fileName", fileName);
			request.add("remark", remark);

			byte[] data = null;
			try {
				data = readFileInBytes(localInStream);

				request.add("remotePathname", remotePathname);
				request.add("FileBytes", data);
				request.setCmd("upload_server_file_in_bytes");
				respond = UimsUtils.requestProcesser(request);
				String accId = "";
				if (respond.getErrorMsg() == null) {
					UimsUtils.messageBoxInfo("附件上传成功");
					accId = respond.get("accId").toString();
					return accId;
				}
			} catch (Exception e) {
				UimsUtils.messageBoxInfo("附件上传失败");
			}

		}
		return "";
	}

	public static String uploadFile(byte[] data, String remotePathname, String fileName, String remark) {
		if (remotePathname != null && fileName.indexOf('.') > 0 && fileName.indexOf('.') < fileName.length()) {
			RmiRequest request = new RmiRequest();
			RmiResponse respond = new RmiResponse();
			request.add("fileName", fileName);
			request.add("remark", remark);

			try {
				request.add("remotePathname", remotePathname);
				request.add("FileBytes", data);
				request.setCmd("upload_server_file_in_bytes");
				respond = UimsUtils.requestProcesser(request);
				String accId = "";
				if (respond.getErrorMsg() == null) {
					UimsUtils.messageBoxInfo("附件上传成功");
					accId = respond.get("accId").toString();
					return accId;
				}
			} catch (Exception e) {
				UimsUtils.messageBoxInfo("附件上传失败");
			}

		}
		return "";
	}

	/**
	 * 下载文件
	 * 
	 * @param localOutStream
	 *            本地输出流
	 * @param accId
	 *            文件ID
	 */
	public static void downloadFile(OutputStream localOutStream, Integer accId) {
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_INTEGER, accId);
		request.setCmd("download_file_in_bytes");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {

			byte[] bytes = (byte[]) response.get("FileBytes");
			try {
				localOutStream.write(bytes);
				localOutStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			UimsUtils.messageBoxInfo(response.getErrorMsg());
		}
	}

	public static void downloadServerContextFile(OutputStream localOutStream, String path) {
		RmiRequest request = new RmiRequest();
		request.add(Server_Context_File_Path, path);
		request.setCmd("download_server_context_file_in_bytes");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {

			byte[] bytes = (byte[]) response.get("FileBytes");
			try {
				localOutStream.write(bytes);
				localOutStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			UimsUtils.messageBoxInfo(response.getErrorMsg());
		}
	}

	public static void uploadServerContextFile(InputStream inputStream, String path) {
		byte[] data = null;
		try {
			data = readFileInBytes(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RmiRequest request = new RmiRequest();
		request.add(Server_Context_File_Path, path);
		request.setCmd("upload_server_context_file_in_bytes");
		request.add("FileBytes", data);
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			UimsUtils.messageBoxInfo("上传成功");

		} else {
			UimsUtils.messageBoxInfo(response.getErrorMsg());
		}
	}

	private static byte[] readFileInBytes(InputStream input) throws FileNotFoundException, IOException {

		try {

			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = input.read()) != -1) {
				bytestream.write(ch);
			}
			byte[] data = bytestream.toByteArray();
			bytestream.close();
			input.close();
			return data;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

	}

	public static byte[] downloadServerContextFile(String path) {
		RmiRequest request = new RmiRequest();
		request.add(Server_Context_File_Path, path);
		request.setCmd("download_server_context_file_in_bytes");
		RmiResponse response = UimsUtils.requestProcesser(request);
		byte[] bytes = null;
		if (response.getErrorMsg() == null) {
			bytes = (byte[]) response.get("FileBytes");
		} else {
			UimsUtils.messageBoxInfo(response.getErrorMsg());
		}
		return bytes;
	}

	/**
	 * 根据附件ID删除文件
	 * 
	 * @param accId
	 */
	public static void deleteFile(Integer accId) {
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_INTEGER, accId);
		request.setCmd("delete_accessories_file_by_accId");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			UimsUtils.messageBoxInfo("删除成功");
		} else {
			UimsUtils.messageBoxInfo(response.getErrorMsg());
		}
	}

	/**
	 * 根据附件Id取得附件类型
	 * 
	 * @param accId
	 * @return
	 */
	public static String getFileType(Integer accId) {
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_INTEGER, accId);
		request.setCmd("get_file_type_by_accId");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() == null) {
			String fileType = (String) response.get(RmiKeyConstants.KEY_STRING);
			return fileType;
		} else {
			UimsUtils.messageBoxError(response.getErrorMsg());
			return "";
		}
	}

	/**
	 * 创建新文件夹
	 * 
	 * @param remotePathname
	 * @return
	 */
	public static Boolean mkdirs(String remotePathname) {
		try {
			return FileUtility.mkdirs(remotePathname);
		} catch (Exception e) {
			return false;
		}
	}
}
