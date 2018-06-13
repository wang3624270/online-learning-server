package org.octopus.common_business.attachment.dao.impl;

import java.util.Date;
import java.util.List;

import org.octopus.common_business.attachment.dao.BaseAttachmentInfoDao;
import org.octopus.common_business.attachment.model.BaseAttachmentData;
import org.octopus.common_business.attachment.model.BaseAttachmentInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.octopus.spring_utils.jpa.Page;
import org.octopus.spring_utils.jpa.PageBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.sdu.common.util.DateTimeTool;

@Repository
public class BaseAttachmentInfoDaoImpl extends GenericServiceImpl<BaseAttachmentInfo> implements BaseAttachmentInfoDao {

	public BaseAttachmentInfoDaoImpl() {
		super(BaseAttachmentInfo.class);
	}

	@Transactional
	public void deletePo(BaseAttachmentInfo po) {
		// TODO Auto-generated method stub
		this.delete(po);
	}

	@Override
	@Transactional
	public Integer saveOrUpdatePo(BaseAttachmentInfo po) {
		// TODO Auto-generated method stub
		if (po.getAttachId() == null || po.getAttachId() == 0) {
			// save
			this.create(po);
		} else {
			// update
			this.update(po);
		}
		return po.getAttachId();
	}

	@Transactional
	@Override
	public void updatePo(BaseAttachmentInfo po) {
		// TODO Auto-generated method stub
		this.update(po);
	}

	@Override
	public BaseAttachmentInfo findBaseAttachmentInfoById(Integer attachId) {
		// TODO Auto-generated method stub
		if (attachId == null || attachId == 0) {
			return null;
		}
		String hql = "from BaseAttachmentInfo a where a.attachId = " + attachId;
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);
		if (list == null || list.size() == 0)
			return null;
		else
			return (BaseAttachmentInfo) list.get(0);
	}

	@Override
	public BaseAttachmentInfo findBaseAttachmentInfo(String attachType, Integer ownerId, String fileName) {
		// TODO Auto-generated method stub
		String hql = "from BaseAttachmentInfo a where 1=1";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		if (ownerId != null) {
			hql += " and a.ownerId = " + ownerId;
		}
		if (fileName != null && !"".equals(fileName)) {
			hql += " and a.fileName = '" + fileName + "'";
		}
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);
		if (list == null || list.size() == 0)
			return null;
		else
			return (BaseAttachmentInfo) list.get(0);
	}

	@Override
	public BaseAttachmentInfo findBaseAttachmentInfoByConditions(String attachType, Integer ownerId,
			String permanentFileName) {
		// TODO Auto-generated method stub
		String hql = "from BaseAttachmentInfo a where 1=1 ";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		if (ownerId != null) {
			hql += " and a.ownerId = " + ownerId;
		}
		if (permanentFileName != null && !"".equals(permanentFileName)) {
			if (permanentFileName.contains("'")) {
				permanentFileName = permanentFileName.replace("'", "''");
			}
			hql += " and a.permanentFileName = '" + permanentFileName + "'";
		}
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);
		if (list == null || list.size() == 0)
			return null;
		else
			return (BaseAttachmentInfo) list.get(0);
	}

	@Override
	public BaseAttachmentInfo findBaseAttachmentInfo(String attachType, Integer attachIndex, Integer ownerId,
			String permanentFileName) {
		String hql = "from BaseAttachmentInfo a where 1=1 ";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		if (attachIndex != null) {
			hql += " and a.attachIndex = " + attachIndex;
		}
		if (ownerId != null) {
			hql += " and a.ownerId = " + ownerId + "";
		}
		if (permanentFileName != null && !"".equals(permanentFileName)) {
			if (permanentFileName.contains("'")) {
				permanentFileName = permanentFileName.replace("'", "''");
			}
			hql += " and a.permanentFileName = '" + permanentFileName + "'";
		}
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);
		if (list == null || list.size() == 0)
			return null;
		else
			return (BaseAttachmentInfo) list.get(0);
	}

	@Override
	public List<BaseAttachmentInfo> findBaseAttachmentInfoList(String attachType, Integer ownerId) {
		// TODO Auto-generated method stub
		String hql = "from BaseAttachmentInfo a where 1=1 ";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		hql += " and a.ownerId = " + ownerId + " order by a.attachType,a.attachIndex";
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);
		return list;
	}

	@Override
	public BaseAttachmentInfo findBaseAttachmentInfoByConditions(String attachType, Integer attachIndex,
			Integer ownerId, String fileName) {
		// TODO Auto-generated method stub
		String hql = "from BaseAttachmentInfo a where 1=1 ";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		if (attachIndex != null) {
			hql += " and a.attachIndex = " + attachIndex;
		}
		if (ownerId != null) {
			hql += " and a.ownerId = " + ownerId + "";
		}
		if (fileName != null && !"".equals(fileName)) {
			if (fileName.contains("'")) {
				fileName = fileName.replace("'", "''");
			}
			hql += " and a.fileName = '" + fileName + "'";
		}
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);
		if (list == null || list.size() == 0)
			return null;
		else
			return (BaseAttachmentInfo) list.get(0);
	}

	public List<BaseAttachmentInfo> findBaseAttachmentInfoListByUploadId(String attachType, Integer uploader,
			String date, Integer ownerId, String fileName, Integer collegeId) {
		String hql = "from BaseAttachmentInfo a where 1=1 ";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		if (uploader != null) {
			hql += " and a.uploader.personId = " + uploader;
		}
		if (ownerId != null) {
			hql += " and a.ownerId = " + ownerId + "";
		}
		if (fileName != null && !"".equals(fileName)) {
			if (fileName.contains("'")) {
				fileName = fileName.replace("'", "''");
			}
			hql += " and a.fileName = '" + fileName + "'";
		}
		if (date != null) {
			hql += " and a.uploadTime  like  '" + date + "%'";
		}
		if (collegeId != null) {
			hql += " and a.uploader.baseCollege.collegeId = " + collegeId;
		}
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);

		return list;

	}

	public Integer getFileNumByConditions(String attachType, Integer uploader, String date, Integer ownerId,
			String fileName) {

		List fileList = this.findBaseAttachmentInfoListByUploadId(attachType, uploader, date, ownerId, fileName, null);
		Integer count = 0;
		for (int i = 0; i < fileList.size(); i++) {
			count++;
		}
		return count;
	}

	public List<BaseAttachmentInfo> findEndBaseAttachmentInfoListByUploadId(String attachType, Integer uploader,
			String date, Integer ownerId, String fileName, Integer collegeId) {
		String hql = "from BaseAttachmentInfo a where 1=1 ";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		if (uploader != null) {
			hql += " and a.uploader.personId = " + uploader;
		}
		if (ownerId != null) {
			hql += " and a.ownerId = " + ownerId + "";
		}
		if (fileName != null && !"".equals(fileName)) {
			if (fileName.contains("'")) {
				fileName = fileName.replace("'", "''");
			}
			hql += " and a.fileName = '" + fileName + "'";
		}
		if (date != null) {
			hql += " and a.uploadTime  like  '" + date + "%'";
		}
		if (collegeId != null) {
			hql += " and a.uploader.baseCollege.collegeId = " + collegeId;
		}
		hql += " and a.docType = 'end'";
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);

		return list;
	}

	@Override
	public BaseAttachmentInfo findBaseAttachmentInfo(String attachType, Integer attachIndex, Integer ownerId,
			String remark, String permanentFileName) {
		String hql = "from BaseAttachmentInfo a where 1=1 ";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		if (remark != null && !"".equals(remark)) {
			hql += " and a.remark = '" + remark + "'";
		}
		if (attachIndex != null) {
			hql += " and a.attachIndex = " + attachIndex;
		}
		if (ownerId != null) {
			hql += " and a.ownerId = " + ownerId + "";
		}
		if (permanentFileName != null && !"".equals(permanentFileName)) {
			if (permanentFileName.contains("'")) {
				permanentFileName = permanentFileName.replace("'", "''");
			}
			hql += " and a.permanentFileName = '" + permanentFileName + "'";
		}
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);
		System.out.println(hql);
		if (list == null || list.size() == 0)
			return null;
		else
			return (BaseAttachmentInfo) list.get(0);
	}

	@Override
	public List findBaseAttachmentInfoList(String attachType, Integer attachIndex, Integer ownerId, String remark,
			String permanentFileName) {
		// TODO Auto-generated method stub
		String hql = "from BaseAttachmentInfo a where 1=1 ";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		if (remark != null && !"".equals(remark)) {
			hql += " and a.remark like '" + remark + "%'";
		}
		if (attachIndex != null) {
			hql += " and a.attachIndex = " + attachIndex;
		}
		if (ownerId != null) {
			hql += " and a.ownerId = " + ownerId + "";
		}
		if (permanentFileName != null && !"".equals(permanentFileName)) {
			if (permanentFileName.contains("'")) {
				permanentFileName = permanentFileName.replace("'", "''");
			}
			hql += " and a.permanentFileName = '" + permanentFileName + "'";
		}
		hql += "  order by uploader";
		List<BaseAttachmentInfo> list = this.queryForList(hql, null);
		System.out.println(hql);
		if (list == null || list.size() == 0)
			return null;
		else
			return list;
	}

	public PageBean findBaseAttachmentInfoPageBean(int pageNum, int pageSize, String attchType, String url,
			String fileName, Integer upLoader) {
		PageBean pageBean = null;
		Page page = new Page(pageNum, pageSize);
		String countHql;
		String listHql;
		countHql = "select count(*) from BaseAttachmentInfo a where 1=1";
		listHql = "from BaseAttachmentInfo a where 1=1";
		if (attchType != null && !attchType.equals("")) {
			countHql += " and a.attachType='" + attchType + "'";
			listHql += " and a.attachType='" + attchType + "'";
		}
		if (url != null && !url.equals("")) {
			countHql += " and a.urlAddress='" + url + "'";
			listHql += " and a.urlAddress='" + url + "'";
		}
		if (fileName != null && !fileName.equals("")) {
			countHql += " and a.fileName='" + fileName + "'";
			listHql += " and a.fileName='" + fileName + "'";
		}
		if (upLoader != null && !upLoader.equals("") && upLoader != 0) {
			countHql += " and a.infoPersonInfo.personId=" + upLoader;
			listHql += " and a.infoPersonInfo.personId=" + upLoader;
		}

		countHql += "  order by uploadTime";
		listHql += "  order by uploadTime";

		List list = this.queryForList(countHql, listHql, null, page);

		pageBean = new PageBean(pageSize, countHql, listHql);
		pageBean.setTotalRows(page.getTotalCount());
		pageBean.setCurrentPage(pageNum);
		pageBean.setDispList(list);
		return pageBean;
	}

	@Override
	public List<Integer> findBaseAttachmentIdListBeforeDate(String attachType, Date date) {
		// TODO Auto-generated method stub
		String dStr = DateTimeTool.parseDateTime(date, "yyyy-MM-dd HH:mm:ss");
		String hql = "select attachId from BaseAttachmentInfo a where  a.uploadTime <= '" + dStr + "'";
		if (attachType != null && !"".equals(attachType)) {
			hql += " and a.attachType = '" + attachType + "'";
		}
		return this.queryForList(hql, null);
	}

	@Override
	public List<BaseAttachmentData> findBaseAttachmentNotInData() {
		// TODO Auto-generated method stub
//		String hql = "select a from BaseAttachmentInfo a where  a.attachId not in (select attachId from BaseAttachmentData ) ";
		String hql = "select a from BaseAttachmentInfo a  where a.attachId > 1 ";
		return this.queryForList(hql);
	}
}
