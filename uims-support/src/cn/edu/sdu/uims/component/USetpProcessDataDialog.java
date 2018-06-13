package cn.edu.sdu.uims.component;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.form.impl.USetpProcessDataForm;
import cn.edu.sdu.uims.form.impl.USetpProcessDataItemForm;
import cn.edu.sdu.uims.util.UimsUtils;

public class USetpProcessDataDialog extends JDialog implements Runnable{
	private USetpProcessDataForm dataForm = null;
	private JButton rButton;
	private JButton eButton;
	private JTextArea area;
	private boolean isEnd = false;
	public USetpProcessDataDialog(String title, USetpProcessDataForm dataForm){
		super();
		this.dataForm = dataForm;
		Container c = this.getContentPane();
//		JPanel p = new JPanel();
		c.setLayout(new BorderLayout());
		area = new JTextArea();
		JScrollPane sc = new JScrollPane(area);
		c.add(sc);
		this.setBounds(100, 100, 800, 600);
		this.setVisible(true);
		Thread th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			List dataList,tempList;
			List<USetpProcessDataItemForm> itemList;
			USetpProcessDataItemForm item;
			int i, j;
			int start, count,size;
			int set = 1;
			String message;
			while(!isEnd) {
				isEnd = true;
				dataList = new ArrayList();
				itemList = dataForm.getItemList();//itemList分别存放不同邮件服务器的收件地址
				for(i = 0; i < itemList.size();i++) {
					item = itemList.get(i);
					tempList = item.getDataList();
					size = tempList.size();
					start = item.getStart();
					if(item.getStart()==tempList.size())
						continue;
					count = item.getSetCount();
					if(start + count >= size) {
						count = size-start;
					}else {
						isEnd = false;
					}
					for(j = 0;j <count;j++) {
						dataList.add(tempList.get(start+j));
					}
					item.setStart(start+count);
				}
				RmiRequest request = new RmiRequest();
				request.setCmd(dataForm.getCmd());
				request.add(RmiKeyConstants.KEY_HASH,dataForm.getParas());
				request.add(RmiKeyConstants.KEY_FORMLIST,dataList);
				RmiResponse respond = UimsUtils.requestProcesser(request);
				if (respond.getErrorMsg() == null) {
					message = (String)respond.get(RmiKeyConstants.KEY_MESSAGE);
				}

				if(!isEnd)
					Thread.sleep(dataForm.getDelayTime());
			}
		}catch(Exception e) {
			
		}
	}
}
