package cn.edu.sdu.uims.progress;


import javax.swing.JProgressBar;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.util.UimsUtils;
public class ProgressBar extends JProgressBar implements Runnable {

	private ProgressProcessObjectI pobj = null;

	private ProgressElementObject progressObj;

	private boolean isEnd = false;

	private Thread thread = null;

	public ProgressBar() {
		super(0, 100);
		setValue(0);
		init();
	}

	public void init() {
//		setStringPainted(true);
	}


	public void addOutInfo(String str) {
		
	}

	public void start() {
		thread = new Thread(this);
		isEnd = false;
		thread.start();
	}

	public void end() {
		isEnd = true;

	}

	public ProgressElementObject initProgressObject() {
		ProgressElementObject obj = null;
		RmiRequest request = new RmiRequest();
		request.setCmd("service_common_startProgress");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() == null) {
			obj = (ProgressElementObject) respond
					.get(RmiKeyConstants.KEY_DE_PROGRESS_OBJ);
			if (obj != null) {
				progressObj = obj;
			} else {
				progressObj = null;
			}
		}
		return progressObj;
	}

	public void setPos() {
		if (progressObj == null)
			return;
		ProgressElementObject obj = null;
		String info;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_FORM, progressObj);
		request.setCmd("service_common_getProgressData");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() == null) {
			obj = (ProgressElementObject) respond
					.get(RmiKeyConstants.KEY_DE_PROGRESS_OBJ);
			if (obj != null) {
				progressObj.setMax(obj.getMax());
				progressObj.setPos(obj.getPos());
				setValue(progressObj.getValue());
//				System.out.println("pos +" +obj.getPos() + "value +" +obj.getValue());
				info = obj.getAddString();
				if (info != null) {
					// progressObj.appendAddString(info);
					addOutInfo(info);
				}
			}
		}

	}

	public void endProgress() {
		if (progressObj == null)
			return;
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_FORM, progressObj);
		request.setCmd("service_common_endProgress");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() == null) {
		}
		progressObj = null;

	}

	public void run() {
		// TODO Auto-generated method stub
		setValue(0);
		while (!isEnd) {
			try {
				thread.sleep(1000);
				setPos();
			} catch (Exception e) {
			}
		}
		endProgress();
		setValue(0);
	}

}
