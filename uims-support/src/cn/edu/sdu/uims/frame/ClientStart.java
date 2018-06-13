package cn.edu.sdu.uims.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class ClientStart  extends Thread{

	public static final String RUNDIR="c:/run/";
	private Integer customId = 2;
	private String appName;
	private String environParas;
	private String proParas;
	private String paras;
	private Date appModifyTime;
	private String appUrl;
	private String appJarFileName;
	private JDialog dlg;
    private JProgressBar progressbar;

	public ClientStart(Integer customId){
		this.customId = customId; 		
	}
	public void setCustomId(Integer customId) {
		this.customId = customId; 
	}
	public  List queryForList(String sql) {
		// TODO Auto-generated method stub
		Connection conn = null;
		do {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://211.86.56.168:3306/sdupublicdb",
						"propertydb",
						"sduproperty.96.db");
			} catch (ClassNotFoundException e) {
			} catch (SQLException sqle) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
				}
			}
		} while (conn == null);
		Statement stmt;
		ResultSet rs;
		List list = new ArrayList();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List oList = null;
			while (rs.next()) {
				oList = new ArrayList();
				for (int i = 1; i <= columnCount; i++)
					oList.add(rs.getObject(i));
				list.add(oList);
			}
			stmt.close();
			rs.close();
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public void  getCustomInfoOfCustomId() {
		String sql;
		sql = "select a.appName,c.modifytime, c.url, c.fileName, cc.environParas,cc.clientParas  FROM app_client_custom_info cc, app_client_info c, base_app_info a where  a.appId = c.appId and cc.clientId = c.clientId and cc.customId =" + customId;
		List list = queryForList(sql);
		if(list != null && list.size() > 0) {
			List oList = (List)list.get(0);
			if(oList != null && oList.size() > 0) {
				appName = (String)oList.get(0); 
				appModifyTime = (Date)oList.get(1); 
				appUrl = (String)oList.get(2); 
				appJarFileName = (String)oList.get(3); 
				environParas = (String)oList.get(4); 
				paras = (String)oList.get(5); 
			}
		}
	}
	
	public void startApplication() {
		// TODO Auto-generated method stub
		if(appName == null) {
			JOptionPane.showMessageDialog(null, "无法访问系统数据库，程序启动错误！");
		}
		String fileName = RUNDIR + appName + ".dat";
		File f = new File(fileName);
		ObjectInputStream in;
		Date date = null;
		try {
			if(f.exists()) {
				in = new ObjectInputStream(new FileInputStream(f));
				date = (Date)in.readObject();
				proParas = (String)in.readObject();
				in.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(date == null ||date.before(appModifyTime)) {
				downFile();
		}
		startJar();
	}
	public void startJar(){

		String jarName =  RUNDIR + appJarFileName;
		String runString = "java ";
		if(environParas != null && environParas.length() != 0)
			runString += environParas;
		runString += " -jar " + jarName;
		if(paras != null || proParas != null) {
			String p = "";
			if(paras == null) 
				p = proParas;
			else {
				if(proParas== null) {
					p = paras;
				}else {
					p = paras.substring(0,paras.length()-1) + "|" + proParas.substring(1,proParas.length());
				}
			}
			runString += " " + p;
		}
		try {
			Runtime.getRuntime().exec(runString); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void startApp(){
		getCustomInfoOfCustomId();
		startApplication();
	}
	
	public void downFile() {
		// TODO Auto-generated method stub
		dlg  = new JDialog();
		dlg.setTitle("程序下载进行中..");
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
			String fileName = RUNDIR + appName + ".dat";
			File f = new File(fileName);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(appModifyTime);
			out.close();
		}catch(Exception e){
			
		}
	}
	    
	public void run() {
		String jarName =  RUNDIR + appJarFileName;
       URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
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
			urlfile = new URL(appUrl);
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
				progressbar.setValue((pos * 100) / n);
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
		}
		dlg.dispose();
		dlg.setVisible(false);		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientStart c = new ClientStart(64);
		c.startApp();
	}

}
