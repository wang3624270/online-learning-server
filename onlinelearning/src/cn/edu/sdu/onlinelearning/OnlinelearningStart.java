package cn.edu.sdu.onlinelearning;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.sdu.rmi.RmiClientRequest;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.component.DownloadPromptDialog;
import cn.edu.sdu.uims.frame.MyProperties;

public class OnlinelearningStart  extends Thread{

	public static final String RUNDIR="c:/run/";
	private Date clientUpdateTime;
	private JDialog dlg;
    private JProgressBar progressbar;

	public OnlinelearningStart(){
		try {
			MyProperties myProperties = MyProperties.createProperties();
			String serverAddress = myProperties.getProperty(MyProperties.SERVER);
			RmiClientRequest.getClientRequest().init(serverAddress);
		}catch(Exception e){
			
		}
	}
	public void  getClientKarInfo() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond;
		request.setCmd("badmintonhotGetBadmintonhotSystemInfoClient");
		respond = RmiClientRequest.getClientRequest().request(request);
		if (respond.getErrorMsg() == null) {
			clientUpdateTime  = (Date)respond.get("clientUpdateTime");
		}
	}
	
	
	public void startApplication() {
		// TODO Auto-generated method stub
		String fileName = RUNDIR + "badmintonhot.dat";
		File f = new File(fileName);
		ObjectInputStream in;
		Date date = null;
		try {
			if(f.exists()) {
				in = new ObjectInputStream(new FileInputStream(f));
				date = (Date)in.readObject();
				in.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(date == null || date.before(clientUpdateTime)) {
			String jarName =  RUNDIR + "badmintonhot.jar";
			File tf = new File(jarName);
			if(!tf.exists()) {
				downFile();				
			}else {
				DownloadPromptDialog dlg = new DownloadPromptDialog();
				if(dlg.isDownload()) {
					downFile();
				}
			}
		}
		startJar();
	}
	public void startJar(){

		String jarName =  RUNDIR + "badmintonhot.jar";
		String runString = "java ";
		runString += " -jar " + jarName;
		try {
			Runtime.getRuntime().exec(runString); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void startApp(){
		getClientKarInfo();
		startApplication();
	}
	
	public void downFile() {
		// TODO Auto-generated method stub
		dlg  = new JDialog();
		dlg.setTitle("");
		dlg.setBounds(300, 300, 400, 70);
		Container contentPanel = dlg.getContentPane();
		progressbar = new JProgressBar();
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
	    progressbar.setMaximum(100);
	    progressbar.setValue(0);
	    progressbar.setStringPainted(true);
	    progressbar.setPreferredSize(new Dimension(300, 20));
	    progressbar.setBorderPainted(true);
	    contentPanel.add(progressbar, BorderLayout.CENTER);
	    dlg.setAlwaysOnTop(true);
//	    dlg.setDefaultCloseOperation(dlg.DO_NOTHING_ON_CLOSE);
	    dlg.setResizable(false);
	    dlg.setModal(true);
	    start();
	    dlg.setVisible(true);
		try{
			String fileName = RUNDIR + "badmintonhot.dat";
			File f = new File(fileName);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(clientUpdateTime);
			out.close();
		}catch(Exception e){
			
		}
	}
	    
	public void run() {
		String jarName =  RUNDIR + "badmintonhot.jar";
		FileOutputStream bos = null;
		File f = new File(jarName);
		File parent = f.getParentFile(); 
		if(parent!=null&&!parent.exists()){ 
			parent.mkdirs(); 
		} 
		if(!f.exists()){//判断文件是否真正存在,如果不存在,创建一个;       
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			RmiRequest request = new RmiRequest();
			request.add("attachId",3);
			request.setCmd("attachmentOpenDownloadAttachmentFile");
			RmiResponse respond = RmiClientRequest.getClientRequest().request(request);
			if (respond.getErrorMsg() != null) {
				JOptionPane.showMessageDialog(null, respond.getErrorMsg());
				return;
			}
			String fileKey = (String)respond.get("fileKey");
			int size = (Integer)respond.get("fileSize");
			int len = 40960;
			int n = size / len;
			bos = new FileOutputStream(f);
			byte[] b = new byte[len];
			for(int i = 0; i < n;i++) {
				request.add("length",len);
				request.add("fileKey",fileKey);
				request.setCmd("attachmentReadAttachmentFileData");
				respond  = RmiClientRequest.getClientRequest().request(request);
				if (respond.getErrorMsg() != null) {
					JOptionPane.showMessageDialog(null, respond.getErrorMsg());
					return;
				}
				b = (byte []) respond.get(RmiKeyConstants.KEY_ARRAY);
				bos.write(b, 0, len);
				progressbar.setValue((i * 100) / n);
				Thread.sleep(10);
			}
			len = size - n* len;
			if( len  > 0) {
				request.add("length",len);
				request.add("fileKey",fileKey);
				request.setCmd("attachmentReadAttachmentFileData");
				respond = RmiClientRequest.getClientRequest().request(
						request);
				if (respond.getErrorMsg() != null) {
					JOptionPane.showMessageDialog(null, respond.getErrorMsg());
					return;
				}
				b = (byte []) respond.get(RmiKeyConstants.KEY_ARRAY);
				bos.write(b, 0, len);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dlg.dispose();
		dlg.setVisible(false);		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OnlinelearningStart c = new OnlinelearningStart();
		c.startApp();
	}

}
