package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.util.UimsUtils;

public class UHtmlAttachPanel extends UComplexPanel {
	private boolean isCanAction = false;
	private MyHTMLEditorPane editor;
	private Integer attachId = null;
	private String fileName;
	JButton importButton;

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());
		editor = new MyHTMLEditorPane();
		editor.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
		this.add(editor, BorderLayout.CENTER);
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		JButton jb;
		Dimension d = new Dimension(80, 30);
		jb = new JButton("浏览");
		jb.setActionCommand("doBrowse");
		jb.setPreferredSize(d);
		jb.addActionListener(this);
		p.add(jb);
		jb = new JButton("导入");
		jb.setActionCommand("doImport");
		jb.setPreferredSize(d);
		jb.addActionListener(this);
		p.add(jb);
		jb = new JButton("保存");
		jb.setActionCommand("doSave");
		jb.setPreferredSize(d);
		jb.addActionListener(this);
		p.add(jb);
		jb = new JButton("下载");
		jb.setActionCommand("download");
		jb.setPreferredSize(d);
		jb.addActionListener(this);
		p.add(jb);
		jb = new JButton("附件上传");
		jb.setActionCommand("attachUpload");
		jb.setPreferredSize(d);
		jb.addActionListener(this);
		p.add(jb);
		jb = new JButton("图片上传");
		jb.setActionCommand("imgUplaod");
		jb.setPreferredSize(d);
		jb.addActionListener(this);
		p.add(jb);
		JPanel lp = new  JPanel();
		JLabel label = new JLabel("图片列表！");
		label.setPreferredSize(new Dimension(160,30));
		lp.add(label);
		JList list = new JList();
		list.setPreferredSize(new Dimension(160,300));
		lp.add(list);
		label = new JLabel("附件列表！");
		label.setPreferredSize(new Dimension(160,30));
		lp.add(label);
		list = new JList();
		list.setPreferredSize(new Dimension(160,300));
		lp.add(list);
		lp.setPreferredSize( new Dimension(180,1000));
		lp.setLayout(new FlowLayout());
		this.add(lp, BorderLayout.EAST);
		this.add(p, BorderLayout.SOUTH);
	}

	public void setData(Object o) {
		if (o == null) {
			attachId = null;
			editor.setText("");
		} else {
			attachId = (Integer) o;
			editor.setText(downLoadAttachFile());
		}
	}

	public Object getData() {
		return attachId;
	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		for (int i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				isCanAction = true;
			}
		}

	}

	public String downLoadAttachFile() {
		if (attachId == null)
			return "";
		RmiRequest request = new RmiRequest();
		request.add("attachId", attachId+"");
		request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
		request.setCmd("attachmentDownloadAttachmentFile");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			fileName = (String) respond.get("fileName");
			byte[] data = (byte[]) respond.get(RmiKeyConstants.KEY_BYTES);
			if (data == null)
				return "";
			return new String(data);
		}
		return "";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		String cmd = e.getActionCommand();
		if (cmd.equals("doBrowse")) {
			doBrowse();
		} else if (cmd.equals("doImport")) {
			doImport();
		} else if (cmd.equals("doSave")) {
			doSave();
			if (isCanAction) {
				ActionEvent e1 = new ActionEvent(this, e.getID(),
						this.getComponentName());
				getUParent().getEventAdaptor().actionPerformed(e1);
			}
		} else if (cmd.equals("download")) {
			download();
		}
	}
	public void doBrowse(){
		UimsUtils.openAttachHtml(attachId);
	}
	public void doImport() {
		File file = GetFile.getOpenFile("html");
		if (file == null)
			return;
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GB2312");
		    BufferedReader reader=new BufferedReader(read);
		    String line;
			String str = "";
			do {
				line = reader.readLine();
				if (line != null && line.length() > 0)
					str += line + "\n";
			} while (line != null);
			editor.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doSave() {
		String str = editor.getText();
		byte[] data = str.getBytes();
		if (data == null)
			return;
			  try {
			   File f = new File(fileName);
			   if (!f.exists()) {
			    f.createNewFile();
			   }
			   OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			   BufferedWriter writer=new BufferedWriter(write);   
			   writer.write(str);
			   writer.close();
			  } catch (Exception e) {
			   System.out.println("写文件内容操作出错");
			   e.printStackTrace();
			  }
	}

	public void download() {

	}
}
