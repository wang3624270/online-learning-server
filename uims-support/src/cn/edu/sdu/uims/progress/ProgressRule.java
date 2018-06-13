package cn.edu.sdu.uims.progress;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public class ProgressRule  {

	public ProgressRule() {
		super();
	}

	public void startProgress(RmiRequest request, RmiResponse respond) {

		ProgressElementObject obj = ProgressFactory.getNewInstance();
		if (obj == null) {
			respond.setErrorMsg("没有进度条！");
		} else
			respond.add(RmiKeyConstants.KEY_DE_PROGRESS_OBJ, obj);
	}

	public void getProgressData(RmiRequest request, RmiResponse respond) {

		ProgressElementObject obj = (ProgressElementObject) request
				.get(RmiKeyConstants.KEY_FORM);
		if (obj == null) {
			respond.setErrorMsg("不存在滚动条！");
			return;
		}
		obj = ProgressFactory.getEqualObjectData(obj);
		if (obj == null) {
			respond.setErrorMsg("没有进度条！");
		} else
			respond.add(RmiKeyConstants.KEY_DE_PROGRESS_OBJ, obj);
	}
	public void endProgress(RmiRequest request, RmiResponse respond) {

		ProgressElementObject obj = (ProgressElementObject) request
				.get(RmiKeyConstants.KEY_FORM);
		if (obj == null) {
			respond.setErrorMsg("不存在滚动条！");
			return;
		}
		ProgressFactory.remove(obj);
	}
}
