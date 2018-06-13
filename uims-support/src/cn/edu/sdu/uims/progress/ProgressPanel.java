package cn.edu.sdu.uims.progress;

import cn.edu.sdu.uims.component.UProgressBar;

public class ProgressPanel extends UProgressBar {

	public ProgressPanel(){
		progressBar = new ProgressBar();
	}
	public void start() {
		((ProgressBar)progressBar).start();
	}

	public void end() {
		((ProgressBar)progressBar).end();
	}

	public ProgressElementObject initProgressObject() {
		return ((ProgressBar)progressBar).initProgressObject();
	}

	public void setPos() {
		((ProgressBar)progressBar).setPos();
	}

	public void endProgress() {
		((ProgressBar)progressBar).setPos();
	}


}
