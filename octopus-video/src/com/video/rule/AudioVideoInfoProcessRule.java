package com.video.rule;

import java.io.IOException;
import java.io.InputStream;

import org.octopus.common_business.attachment.dao.BaseAttachmentInfoDao;
import org.octopus.common_business.attachment.model.BaseAttachmentInfo;
import org.sdu.file_util.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.sdu.common.util.DateTimeTool;

@Service
public class AudioVideoInfoProcessRule {
	@Autowired
    private BaseAttachmentInfoDao baseAttachmentInfoDao;

	public Integer doUpLoadImageFile(Integer attachId, String path, InputStream in) {
		// TODO Auto-generated method stub
		try {
			FileUtility.uploadFile(in, path);
			if(attachId == null) {
				BaseAttachmentInfo info = new BaseAttachmentInfo();
				info.setAttachType("N");
				info.setFileName("Attach.jpg");
//				info.setOwnerId(ownerId);
				info.setUrlAddress(path);
//				info.setUploader(uploader);
				info.setUploadTime(DateTimeTool.getNowTime());
				baseAttachmentInfoDao.saveOrUpdatePo(info);
				return info.getAttachId();
			}else {	//无需修改BaseAttachmentInfo，只需要上传文件即可
				BaseAttachmentInfo info = baseAttachmentInfoDao.findBaseAttachmentInfoById(attachId);
				info.setUploadTime(DateTimeTool.getNowTime());
				baseAttachmentInfoDao.saveOrUpdatePo(info);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return attachId;
	}

}
