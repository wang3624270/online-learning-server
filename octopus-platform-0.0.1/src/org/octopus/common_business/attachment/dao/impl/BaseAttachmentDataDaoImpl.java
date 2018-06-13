package org.octopus.common_business.attachment.dao.impl;

import java.util.List;

import org.octopus.common_business.attachment.dao.BaseAttachmentDataDao;
import org.octopus.common_business.attachment.model.BaseAttachmentData;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BaseAttachmentDataDaoImpl extends GenericServiceImpl<BaseAttachmentData> implements BaseAttachmentDataDao {
	public BaseAttachmentDataDaoImpl() {
		super(BaseAttachmentData.class);
	}
	@Override
	public BaseAttachmentData findBaseAttachmentDataById(Integer attachId) {
		// TODO Auto-generated method stub
		return query(attachId);
	}

}
