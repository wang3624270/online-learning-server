package org.octopus.common_business.attachment.dao;

import java.util.Date;
import java.util.List;

import org.octopus.common_business.attachment.model.BaseAttachmentData;
import org.octopus.common_business.attachment.model.BaseAttachmentInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;
import org.octopus.spring_utils.jpa.PageBean;


public interface BaseAttachmentInfoDao extends GenericServiceI<BaseAttachmentInfo> {
	public BaseAttachmentInfo findBaseAttachmentInfoById(Integer attchId);
	public void deletePo(BaseAttachmentInfo po);
	public Integer saveOrUpdatePo(BaseAttachmentInfo po);
	
	public BaseAttachmentInfo findBaseAttachmentInfo(String attachType, Integer ownerId, String fileName);
	public BaseAttachmentInfo findBaseAttachmentInfo(String attachType,Integer attachIndex,Integer ownerId, String permanentFileName);
	public List<BaseAttachmentInfo> findBaseAttachmentInfoList(String attachType, Integer ownerId);
	public BaseAttachmentInfo findBaseAttachmentInfoByConditions(String attachType,Integer ownerId, String permanentFileName);
	public BaseAttachmentInfo findBaseAttachmentInfoByConditions(String attachType,Integer attachIndex,Integer ownerId, String fileName);
	public List<BaseAttachmentInfo> findBaseAttachmentInfoListByUploadId(String attachType, Integer uploader,String date,Integer ownerId,String fileName,Integer collegeId);
	public void updatePo(BaseAttachmentInfo po);
	//根据条件得到文件数量
	public Integer getFileNumByConditions(String attachType, Integer uploader,String date,Integer ownerId,String fileName);
	
	public List<BaseAttachmentInfo> findEndBaseAttachmentInfoListByUploadId(String attachType, Integer uploader,String date,Integer ownerId,String fileName,Integer collegeId);
	public BaseAttachmentInfo findBaseAttachmentInfo(String attachType,
			Integer attachIndex, Integer ownerId, String remark, String permanentFileName);
	public List findBaseAttachmentInfoList(String attachType,
			Integer attachIndex, Integer ownerId, String remark,
			String permanentFileName);
	public PageBean findBaseAttachmentInfoPageBean(int currentPage,
			int numberPerPage,String attchType,String url,String fileName,Integer upLoader);
	public List<Integer> findBaseAttachmentIdListBeforeDate(String attachType, Date date);
	public List<BaseAttachmentData> findBaseAttachmentNotInData();

	
}
