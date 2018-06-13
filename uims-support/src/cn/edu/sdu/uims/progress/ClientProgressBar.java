package cn.edu.sdu.uims.progress;

import javax.swing.JProgressBar;

public class ClientProgressBar extends JProgressBar implements Runnable {


	private ProgressElementObject progressObj;

	private boolean isEnd = false;

	private Thread thread = null;

	public ClientProgressBar() {
		super(0, 100);
		setValue(0);
		init();
	}

	public void init() {
	}

	public void AddOutInfo(String str) {

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
		progressObj = ProgressFactory.getNewInstance();
		return progressObj;
	}

	public void setPos() {
		if (progressObj == null)
			return;
		setValue(progressObj.getValue());

	}

	public void endProgress() {
		if (progressObj == null)
			return;
		ProgressFactory.remove(progressObj);
		progressObj = null;

	}

	public void run() {
		// TODO Auto-generated method stub
		setValue(0);
		while (!isEnd) {
			try {
				thread.sleep(6);
				setPos();
			} catch (Exception e) {
			}
		}
		endProgress();
		setValue(0);
	}

}
