package cn.edu.sdu.uims.component.complex.form;

import cn.edu.sdu.common.form.UForm;

public class BaseImageDataForm extends UForm implements ImageDataFormI {
	private Integer attachId;
	private byte[] imageData;

	@Override
	public byte[] getImageData() {
		// TODO Auto-generated method stub
		return imageData;
	}

	@Override
	public Integer getDataId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	@Override
	public Integer getAttachId() {
		// TODO Auto-generated method stub
		return attachId;
	}

	@Override
	public String[] getImageLabels() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setAttachId(Integer attachId){
		this.attachId = attachId;
	}
}
