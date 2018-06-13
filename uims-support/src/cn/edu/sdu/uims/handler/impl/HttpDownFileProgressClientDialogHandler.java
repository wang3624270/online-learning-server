package cn.edu.sdu.uims.handler.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cn.edu.sdu.uims.component.UProgressBar;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.form.impl.CommonProgressDataForm;

public class HttpDownFileProgressClientDialogHandler extends UDialogHandler
		implements Runnable {
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		UProgressBar bar = (UProgressBar) this.component
				.getSubComponent("progressBar");
		CommonProgressDataForm dlgForm = (CommonProgressDataForm) dataForm;
		HashMap map = dlgForm.getParaMap();
		String remoteFilePath, localFilePath;
		remoteFilePath = (String) map.get("remoteFilePath");
		localFilePath = (String) map.get("localFilePath");
		UDialogPanel dlg = (UDialogPanel) this.component;
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			int size = httpUrl.getContentLength();
			int len = 2048;
			int n = size / len;
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			byte[] b = new byte[len];
			int pos = 0;
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
				bar.setValue((pos * 100) / n);
				if(pos % 50 == 0)
					Thread.sleep(10);
				pos++;
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dlg.doClose();
		}
	}
}
