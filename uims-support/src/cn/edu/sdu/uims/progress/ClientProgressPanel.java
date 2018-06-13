package cn.edu.sdu.uims.progress;

import cn.edu.sdu.uims.component.UProgressBar;

public class ClientProgressPanel extends UProgressBar {

	public ClientProgressPanel() {
		progressBar = new ClientProgressBar();
	}

	public void start() {
		((ClientProgressBar) progressBar).start();
	}

	public void end() {
		((ClientProgressBar) progressBar).end();
	}

	public ProgressElementObject initProgressObject() {
		return ((ClientProgressBar) progressBar).initProgressObject();
	}

	public void setPos() {
		((ClientProgressBar) progressBar).setPos();
	}

	public void endProgress() {
		((ClientProgressBar) progressBar).setPos();
	}

}
