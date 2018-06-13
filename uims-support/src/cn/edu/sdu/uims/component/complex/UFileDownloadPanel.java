package cn.edu.sdu.uims.component.complex;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.attribute.UFileDataAttribute;
import cn.edu.sdu.uims.util.UimsUtils;

public class UFileDownloadPanel extends UComplexPanel {
	protected JComboBox fileComboBox;
	protected JButton downloadButton;
	protected List<UFileDataAttribute> dataList = null;

	@Override
	public void initContents() {
		super.initContents();
		int h = 20;
		if (elementTemplate == null)
			return;
		if (elementTemplate.text != null && elementTemplate.text.length() != 0)
			add(new JLabel(elementTemplate.text));
		fileComboBox = new JComboBox();
		add(fileComboBox);
		downloadButton = new JButton("下载");
		downloadButton.setActionCommand("downloadButton");
		downloadButton.addActionListener(this);

		add(downloadButton);
	}

	public Object getData() {
		return null;
	}

	public void setData(Object o) {
		if (o == null)
			dataList = null;
		else if (o instanceof UFileDataAttribute) {
			dataList = new ArrayList<UFileDataAttribute>();
			dataList.add((UFileDataAttribute) o);
		} else {
			dataList = (List<UFileDataAttribute>) o;
		}
		setListToComboBox();
	}

	private void setListToComboBox() {
		fileComboBox.removeAll();
		if (dataList == null || dataList.size() == 0)
			return;
		if (dataList.size() == 1) {
			dataList.get(0).setId(0);
			fileComboBox.addItem(dataList.get(0));
		} else {
			UFileDataAttribute temp;
			temp = new UFileDataAttribute();
			temp.setId(-1);
			temp.setTitle("请选择...");
			fileComboBox.addItem(temp);
			temp = new UFileDataAttribute();
			temp.setId(-2);
			temp.setTitle("全部");
			fileComboBox.addItem(temp);
			for (int i = 0; i < dataList.size(); i++) {
				dataList.get(i).setId(i);
				fileComboBox.addItem(dataList.get(i));
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == downloadButton) {
			downloadFile();
		}
	}

	private List<UFileDataAttribute> getSelectedList() {
		UFileDataAttribute ff = (UFileDataAttribute) fileComboBox
				.getSelectedItem();
		List<UFileDataAttribute> list = null;
		if (ff == null)
			return list;
		Integer id = ff.getId();
		if (id == null || id.equals(-1))
			return list;
		if (id.equals(-2)) {
			list = dataList;
		} else {
			list = new ArrayList<UFileDataAttribute>();
			list.add(ff);
		}
		return list;
	}

	private void downloadFile() {
		List<UFileDataAttribute> list = getSelectedList();
		if (list == null || list.size() < 1) {
			JOptionPane.showMessageDialog(null, "请选择要下载的文件！");
			return;
		}
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("下载");
		int i;
		UFileDataAttribute fa;
		String message = "";
		for (i = 0; i < list.size(); i++) {
			fa = list.get(i);
			RmiRequest request = new RmiRequest();
			request.add("fileDataAttribute", fa);
			request.setCmd("uismFileDownload");
			RmiResponse respond = UimsUtils.requestProcesser(
					request);
			if (respond.getErrorMsg() != null
					&& !respond.getErrorMsg().equals(""))
				JOptionPane.showMessageDialog(null, respond.getErrorMsg());
			else {
				FileOutputStream output = null;
				byte[] bytes = (byte[]) respond.get(RmiKeyConstants.KEY_BYTES);
				if (bytes != null && bytes.length > 0) {
					fileChooser.setSelectedFile(new File(fa.getFileName()));
					int returnVal = fileChooser.showSaveDialog(null);
					if (returnVal != JFileChooser.APPROVE_OPTION)
						continue;
					File file = fileChooser.getSelectedFile();
					try {
						output = new FileOutputStream(file);
						output.write(bytes);
						output.close();
						message += file.getName()+",";
					} catch (IOException e) {
						UimsUtils.messageBoxInfo("文件下载错误");
					}
				}
			}
		}
		if(message.length() > 1)
			UimsUtils.messageBoxInfo(message+"文件下载成功");
	}
}
