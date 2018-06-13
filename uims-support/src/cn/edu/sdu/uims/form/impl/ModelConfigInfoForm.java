package cn.edu.sdu.uims.form.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.Date;

import javax.sql.rowset.serial.SerialBlob;

import cn.edu.sdu.common.form.UForm;

public class ModelConfigInfoForm extends UForm {
	private Integer modelId;
	private String modelNum;
	private String modelName;
	private String modelType;
	private String modelKey;
	private Object modelObject;
	private Date timestamp;
	private boolean isModify;

	public String getModelNum() {
		return modelNum;
	}

	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public Object getModelObject() {
		return modelObject;
	}

	public void setModelObject(Object modelObject) {
		this.modelObject = modelObject;
	}

	public String getModelKey() {
		return modelKey;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}


	public boolean isModify() {
		return isModify;
	}

	public void setModify(boolean isModify) {
		this.isModify = isModify;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void makeModelContet(Blob b) {
		try {
			InputStream in = b.getBinaryStream();
			ObjectInputStream oIn = new ObjectInputStream(in);
			modelObject = oIn.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Blob getBody() {
		SerialBlob blob = null;
		try {
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bOut);
			out.writeObject(modelObject);
			out.flush();
			out.close();
			blob = new SerialBlob(bOut.toByteArray());
		} catch (Exception e) {

		}
		return blob;
	}


}
