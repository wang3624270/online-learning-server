package org.sdu.file_util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.sdu.uploadListener.UploadListener;
import org.sdu.uploadListener.UploadStatus;

/**
 * 
 * @author JING Ming
 * 
 */
public class LocalFileConnection implements FileConnection {
	private String rootpath = null;

	public LocalFileConnection(ConnectionParam param) {
		this.rootpath = param.getString(ConnectionParam.LOCALPATH);
	}

	public LocalFileConnection(ConnectionParam param, String type) {
		if(type == null) {
			this.rootpath = param.getString(ConnectionParam.LOCALPATH);
		}else if(type.equals("news")) {
			this.rootpath = param.getString(ConnectionParam.HTTPPATH);
		}else {
			this.rootpath = param.getString(ConnectionParam.SPACEPATH);			
		}
	}
	
	

	public void close() {
		// do nothing
	}

	public void readFile(OutputStream localOutStream, String remotePathname)
			throws FileNotFoundException, IOException {
		String path = rootpath + checkPath(remotePathname);
 
		File file = new File(path);
		InputStream inStream = new FileInputStream(file);
		byte buffer[] = new byte[8192];
		int readSize;
		while ((readSize = inStream.read(buffer)) != -1) {
 
			localOutStream.write(buffer, 0, readSize);
		}
		localOutStream.close();
		inStream.close();
	}

	private String checkPath(String path) {
		String str = path;
		if (str.indexOf("..") >= 0)
			str = "error";
		return str;

	}

	public boolean writeFile(InputStream localInStream, String remotePathname)
			throws FileNotFoundException, IOException {
		File file = new File(rootpath + checkPath(remotePathname));
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		OutputStream outStream = new FileOutputStream(file);
		byte buffer[] = new byte[8192];
		int readSize;
		while ((readSize = localInStream.read(buffer)) != -1) {
			System.out.println(readSize);
			outStream.write(buffer, 0, readSize);
		}
		outStream.close();
		localInStream.close();
		return true;
	}
	
	public boolean writeFileForElearning(UploadListener listener,InputStream localInStream, String remotePathname)
			throws FileNotFoundException, IOException {
		File file = new File(rootpath + checkPath(remotePathname));
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		OutputStream outStream = new FileOutputStream(file);
		byte buffer[] = new byte[8192];
		int readSize;
		while ((readSize = localInStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, readSize);
			listener.update(readSize,-1,-1);
		}
		outStream.close();
		localInStream.close();
		return true;
	}

	public boolean isOpen() {
		return true;
	}

	public boolean deleteFile(String remotePathname) {
		File file = new File(rootpath + checkPath(remotePathname));
		return file.delete();
	}

	public boolean deleteDirectory(String remotePathname) {
		return deleteFile(remotePathname);
	}

	public boolean mkdirs(String remotePathname) {
		// File file = new File(rootpath + File.separator + remotePathname);
		File file = new File(rootpath + checkPath(remotePathname));
		return file.mkdirs();
	}

	public Object[] listFiles(String remotePathname) throws IOException {
		// TODO Auto-generated method stub
		File file = new File(rootpath + checkPath(remotePathname));
		return file.listFiles();

	}

	public File findFile(String remotePathname) throws IOException {
		// TODO Auto-generated method stub
		// File file = new File(rootpath + File.separator + remotePathname);

		File file = new File(rootpath + checkPath(remotePathname));
		if (file.exists())
			return file;
		else
			return null;
	}

	public boolean createBlankFile(String remotePathname) {
		String fileName = remotePathname.substring(
				remotePathname.lastIndexOf('/') + 1, remotePathname.length());
		try {
			File file = File.createTempFile("tempFile", fileName.substring(
					fileName.indexOf('.'), fileName.length()));
			InputStream input = new FileInputStream(file);
			return this.writeFile(input, remotePathname);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * @param remotePathname
	 *            要修改名称的路径
	 * @param newName
	 *            新路径的名称
	 */
	public boolean changeDirectoryName(String remotePathname, String newName) {
		String newFilePath = rootpath
				+ remotePathname.substring(0, remotePathname.lastIndexOf("/"))
				+ "/" + newName;
		File old_file = new File(rootpath + remotePathname);
		File new_file = new File(newFilePath);
		return old_file.renameTo(new_file);
	}

	public void directoryFileMove(String from, String to) throws Exception {
		try {
			File dir = new File(rootpath + from);
			File[] files = dir.listFiles();
			if (files == null)
				return;
			File moveDir = new File(rootpath + to);
			if (!moveDir.exists()) {
				moveDir.mkdirs();
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					directoryFileMove(files[i].getPath(),
							to + "\\" + files[i].getName());
					files[i].delete();
				}
				File moveFile = new File(moveDir.getPath() + "\\"
						+ files[i].getName());
				if (moveFile.exists()) {
					moveFile.delete();
				}
				files[i].renameTo(moveFile);
			}
			if (dir.exists()) {
				dir.delete();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean fileMove(String from, String to) throws IOException {
		File file = new File(rootpath + from);
		if (file.exists()) {
			File moveFile = new File(rootpath + to);
			if (moveFile.exists()) {
				moveFile.delete();
			}
			boolean test = file.renameTo(moveFile);
			if (test) {
				file.delete();
			}
			return test;
		}
		return false;
	}

	public void deleteFolder(String folderPath) throws Exception {
		this.deleteFolder1(rootpath + folderPath);
	}

	private void deleteFolder1(String folderPath) throws Exception {

		try {
			File dir = new File(folderPath);
			File[] files = dir.listFiles();
			if (files == null)
				return;
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFolder1(files[i].getPath());
					files[i].delete();
				} else {
					files[i].delete();
				}
			}
			if (dir.exists()) {
				dir.delete();
			}
		} catch (Exception e) {
			throw e;
		}

	}

	public boolean exist(String filePath) {
		File file = new File(rootpath + filePath);

		return file.exists();
	}

	public File retrieveRemoteFile(String remoteRelativePathName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] readFileInBytes(String remotePathname)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		File file = new File(rootpath + remotePathname);
		byte[] data = null;
		try {
			InputStream input = new FileInputStream(file);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = input.read()) != -1) {
				bytestream.write(ch);
			}
			data = bytestream.toByteArray();
			bytestream.close();
			input.close();
			return data;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

	}

	@Override
	public String directoryFileUpload(File file, String remotePathname) {
		// TODO Auto-generated method stub
		String URL = rootpath + remotePathname;
		try {
			File [] files = file.listFiles();
			if(files == null) {
				return null;
			}
			File moveDir = new File(URL);		//创建目录
			if (!moveDir.exists()) {
				moveDir.mkdirs();
			}
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) {
					directoryFileUpload(files[i], remotePathname + "/" + files[i].getName());
				}
				InputStream inStream = new FileInputStream(files[i]);
				writeFile(inStream, remotePathname + "/" + files[i].getName());	
			}
			return URL + "/" + "index.html";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
