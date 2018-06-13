package cn.edu.sdu.uims.component.complex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.BaseAttachmentInfoForm;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.component.complex.def.UAttachFileTemplate;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.form.impl.AttachmentInfoForm;
import cn.edu.sdu.uims.util.UimsUtils;

public class UAttachFilePanel extends UComplexPanel implements MouseListener {
	private BaseAttachmentInfoForm dataForm;
	private List<AttachmentInfoForm> dataList;
	private JList attachList;
	private JTextField titleField;
	private JTextField fileNameField;
	private JButton uploadButton;
	private JButton deleteButton;

	@Override
	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
	}

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		JPanel p, p1;
		int h = 24;
		attachList = new JList();
		attachList.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),
				1));

		titleField = new JTextField(20);
		titleField.setPreferredSize(new Dimension(200, h));
		titleField.addActionListener(this);

		fileNameField = new JTextField(20);
		fileNameField.setPreferredSize(new Dimension(200, h));
		fileNameField.addMouseListener(this);
		deleteButton = new JButton("删除");
		deleteButton.addActionListener(this);
		deleteButton.setActionCommand("deleteButton");

		uploadButton = new JButton("上传");
		uploadButton.addActionListener(this);
		uploadButton.setActionCommand("uploadButton");
		setLayout(new GridLayout(1, 2));
		add(attachList);
		p = new JPanel();
		add(p);
		p.setLayout(new GridLayout(3, 1));
		p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("标题"));
		p1.add(titleField);
		p.add(p1);
		p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("文件"));
		p1.add(fileNameField);
		p.add(p1);
		p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(deleteButton);
		p1.add(uploadButton);
		p.add(p1);
	}

	private void updataAttachList() {
		if (attachList == null)
			return;
		if (dataList != null && dataList.size() != 0) {
			List<AttachmentInfoForm> tempList = new ArrayList();
			for (int i = 0; i < dataList.size(); i++) {
				if (dataList.get(i).getIsDelete() == null
						|| dataList.get(i).getIsDelete().equals(false))
					tempList.add(dataList.get(i));
			}
			attachList.setListData(tempList.toArray());
		}
		else
			attachList.setListData(new ArrayList().toArray());
		this.updateUI();
	}

	@Override
	public Object getData() {
		return dataForm;
	}

	@Override
	public void setData(Object o) {
		if (o != null && o instanceof BaseAttachmentInfoForm) {
			dataForm = (BaseAttachmentInfoForm) o;
		} else
			dataForm = null;
		dataList = null;
		if (dataForm != null && dataForm.getOwnerId() != null) {
			RmiRequest request = new RmiRequest();
			request.add("attachType", dataForm.getAttachType());
			request.add("ownerId", dataForm.getOwnerId());
			request.setCmd("attachmentGetBaseAttachmentInfoFormList");
			RmiResponse response = UimsUtils.requestProcesser(
					request);
			if (response.getErrorMsg() == null) {
				dataList = (List) response.get(RmiKeyConstants.KEY_FORMLIST);
			}
		}
		updataAttachList();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		File file;
		UAttachFileTemplate temp = (UAttachFileTemplate) elementTemplate;
		if (temp.fileType != null && !temp.fileType.equals(""))
			file = GetFile.getOpenFile(temp.fileType);
		else
			file = GetFile.getOpenFile();
		if (file != null) {
			fileNameField.setText(file.getAbsolutePath());
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(dataForm == null || dataForm.getOwnerId() == null) {
			UimsUtils.messageBoxInfo("没有所属信息，不能添加附件！");
			return;
		}
		String amd = e.getActionCommand();
		if (amd.equals("deleteButton")) {
			deletAttachFile();
		} else if (amd.equals("uploadButton")) {
			uploadAttachFile();
		}
		if (actionListener != null)
			actionListener.actionPerformed(e);
	}

	public void addAttachFile() {
	}

	public void deletAttachFile() {
		AttachmentInfoForm form = (AttachmentInfoForm) attachList
				.getSelectedValue();
		if (form == null ||  form.getAttachId() == null) {
			UimsUtils.messageBoxInfo("没有选中附件不能删除！");
			return;
		}
		RmiRequest request = new RmiRequest();
		request.add("attachId", form.getAttachId());
		request.setCmd("attachmentDeleteAttachmentFile");
		RmiResponse response = UimsUtils.requestProcesser(request);
		if (response.getErrorMsg() != null) {
			JOptionPane.showMessageDialog(null, "删除失败！");
			return;
		}
		for (int i = 0; i < dataList.size(); i++) {
			if (form == dataList.get(i)) {
				dataList.remove(i);
				break;
			}
		}
		updataAttachList();
	}

	public void uploadAttachFile() {
		AttachmentInfoForm f;
		File file;
		long length;
		FileInputStream fis;
		byte[] data;
		try {
			String titleName = titleField.getText();
			String fileName = fileNameField.getText();
			RmiRequest request = new RmiRequest();
			file = new File(fileName);
			file.getName();
			length = file.length();
			fis = new FileInputStream(file);
			data = new byte[(int) length];
			fis.read(data);
			fis.close();
			request.add("attachType", dataForm.getAttachType());
			request.add("ownerId", dataForm.getOwnerId());
			request.add("fileName", file.getName());
			request.add("folderName", dataForm.getAttachFolder());
			request.add("fileData", data);
			request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
			request.setCmd("attachmentUpLoadAttachmentFile");
			RmiResponse response = UimsUtils.requestProcesser(request);
			if (response.getErrorMsg() != null) {
				JOptionPane.showMessageDialog(null, response.getErrorMsg());
				return;
			}else {
				JOptionPane.showMessageDialog(null, "文件上传成功！");
				AttachmentInfoForm form = new AttachmentInfoForm();
				form.setRemark(titleName);
				form.setFileName(fileName);
				if(dataList == null) {
					dataList = new ArrayList<AttachmentInfoForm>();
				}
				dataList.add(form);
				updataAttachList();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "系统错误！");
		}
	}
}
