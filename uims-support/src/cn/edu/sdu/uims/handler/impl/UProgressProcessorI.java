package cn.edu.sdu.uims.handler.impl;

import cn.edu.sdu.uims.form.impl.CommonProgressDataForm;
import cn.edu.sdu.uims.progress.ProgressElementObject;

public interface UProgressProcessorI {
	public void requestServerProcess(CommonProgressDataForm form, ProgressElementObject progress);
	public void requestServerProcessOne(CommonProgressDataForm form, int pos);
}
