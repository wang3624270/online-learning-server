package org.octopus.common_business.news.rule;

import java.io.IOException;
import java.io.InputStream;

import org.octopus.common_business.attachment.dao.BaseAttachmentInfoDao;
import org.octopus.common_business.attachment.model.BaseAttachmentInfo;
import org.octopus.common_business.news.form.NewsInfoForm;
import org.octopus.common_business.news.model.NewsInfo;
import org.octopus.utils.CommonQueryServerUtils;
import org.sdu.file_util.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.sdu.common.util.DateTimeTool;

@Service
public class NewsInfoProcessRule {
	
	@Autowired
	BaseAttachmentInfoDao baseAttachmentInfoDao;
	
	public NewsInfoForm getNewsInfoFormFromModel(NewsInfo newsInfo) {
		if(newsInfo == null) {
			return null;
		}
		CommonQueryServerUtils pi = CommonQueryServerUtils.getInstance();
		NewsInfoForm form = new NewsInfoForm();
		form.setId(newsInfo.getId());
		form.setNewsType(newsInfo.getNewsType());
		form.setNewsNum(newsInfo.getNewsNum());
		form.setTitle(newsInfo.getTitle());
		form.setBrief(newsInfo.getBrief());
		form.setOrderNum(newsInfo.getOrderNum());
		form.setReadCount(newsInfo.getReadCount());
		form.setAttachId(newsInfo.getAttachId());
		form.setCreateTime(newsInfo.getCreateTime());
		form.setCreaterId(newsInfo.getCreaterId());
		form.setIsVisable(newsInfo.getIsVisable());
		String URL = newsInfo.getURL();
		form.setURL(URL);	
		if(pi != null)
			form.setCreaterName(pi.getPersonName(newsInfo.getCreaterId()));
		form.setModifyTime(newsInfo.getModifyTime());
		return form;
	}
	
	public Integer doUpLoadImageFile(Integer attachId, String path, InputStream in) {
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
