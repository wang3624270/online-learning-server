package cn.edu.sdu.uims.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import cn.edu.sdu.common.reportdog.UCellAttribute;
import cn.edu.sdu.uims.attribute.UDBFParameter;

public class UDBFUtil {

	private static int fileNo = 0;
	private static UDBFUtil instance = new UDBFUtil();

	public static UDBFUtil getInstance() {
		return instance;
	}

	public String getNewTempFileName() {
		return UFileUtil.getNewTempFileName(fileNo, "xls");
	}

	public UDBFParameter openSheetFile(String fileName) {
		UDBFParameter parameter = null;
		return parameter;
	}

	public byte[] getDBFFileBytes(String fileName) {
		File file = new File(fileName);
		FileInputStream inputStream = null;
		byte[] b = null;
		try {
			inputStream = new FileInputStream(file);
			b = new byte[inputStream.available()];
			inputStream.read(b);
			inputStream.close();
			file.delete();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 传一个输出流，主要用于bs端的下载
	 * 
	 * @param os
	 * @return
	 */
	public UDBFParameter openSheetFile(OutputStream os) {
		UDBFParameter parameter = null;
		try {
			parameter = new UDBFParameter();

		} catch (Exception e) {
			e.printStackTrace();
			parameter = null;
		}
		return parameter;
	}

	/**
	 * 生成dbf
	 * 
	 * @param os
	 */
	public void addCells(UDBFParameter par, UCellAttribute[] datas) {
		try {

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void close(UDBFParameter par) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Object getCellAttribute(UCellAttribute cellAttribute) {
		return null;
	}
}
