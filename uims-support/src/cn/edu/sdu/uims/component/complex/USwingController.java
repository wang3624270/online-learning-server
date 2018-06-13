package cn.edu.sdu.uims.component.complex;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.icepdf.ri.common.SwingController;

import cn.edu.sdu.uims.component.method.GetFile;

public class USwingController extends SwingController {
	private String fileName;
	
	public void saveFile() {
		File file = GetFile.getSaveFile("pdf", "另存文件", fileName);
		if(file == null)
			return;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			BufferedOutputStream buf = new BufferedOutputStream(
					fileOutputStream, 4096 * 2);

			getDocument().saveToOutputStream(buf);

			buf.flush();
			fileOutputStream.flush();
			buf.close();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
