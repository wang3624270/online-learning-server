package cn.edu.sdu.uims.component.textfield;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.EventObject;

import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.UTextFieldEventAdaptor;
import cn.edu.sdu.uims.def.UEventAttribute;

public class UCampusCardField extends UTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CampusCardListenThread thread;
	private UTextFieldEventAdaptor eventAdaptor;
	private ActionEvent actionEvent;
	private File file = new File("c:/run/pernum.txt");
	private String cadRetMsg = null;
	
	private boolean hasNum = false;
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				eventAdaptor = new UTextFieldEventAdaptor(getUParent()
						.getEventAdaptor());
				this.setActionCommand(EventConstants.CMD_CAMPUSECARD_ENTERED);
				this.addActionListener(eventAdaptor);
			}
		}
	}

	public void processActionEvent(EventObject e, String eventName, String cmd) {
		if (eventAdaptor != null) {
			eventAdaptor.processEvent(e, eventName, cmd);
		}
	}

	public void initContents() {
		// TODO Auto-generated method stub
		try {
			actionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
					EventConstants.CMD_CAMPUSECARD_ENTERED);
			thread = new CampusCardListenThread();
			thread.start();
		} catch (Error e) {
		}
	}

	public void onClose() {
		// TODO Auto-generated method stub
		if (thread != null) {
			thread.stopCard();
		}
	}

	public void clearFile() {
		hasNum = false;
		if (file != null) {
			if (file.exists()) {
				file.delete();
			}
		}
	}

	private class CampusCardListenThread extends Thread {

		private boolean state = true;

		public void run() {
			BufferedReader r;
			int index;
			String str;
			String perNum;
			while (state) {
				try {
					sleep(1000);
					if(hasNum)
						continue;
					if (!file.exists())
						continue;
					System.out.print("123");
					r = new BufferedReader(new FileReader(file));
					str = r.readLine();
					r.close();
					if (str == null) {
						file.delete();
						continue;
					}
					index = str.indexOf('-');
					if (index < 0) {
						perNum = str;
						cadRetMsg = null;
					} else {
						perNum = str.substring(0, index);
						cadRetMsg = str.substring(index + 1, str.length());
					}
					hasNum = true;
					setText(perNum);
					processActionEvent(actionEvent,
							EventConstants.EVENT_ACTION, null);
					System.out.println("456");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void stopCard() {
			state = false;
		}
	}

	public String getCadRetMsg() {
		return cadRetMsg;
	}

	public void setCadRetMsg(String cadRetMsg) {
		this.cadRetMsg = cadRetMsg;
	}
}
