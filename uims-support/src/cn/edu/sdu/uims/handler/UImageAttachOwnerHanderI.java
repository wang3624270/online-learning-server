package cn.edu.sdu.uims.handler;

import cn.edu.sdu.common.form.BaseAttachmentInfoForm;

public interface UImageAttachOwnerHanderI {
	BaseAttachmentInfoForm getBaseAttachmentInfoForm(String cmd);
	void  doAfterAttachSaved(String cmd, Integer attachId);
}
