package org.octopus.common_business.attachment.dao;

import org.octopus.common_business.attachment.model.BaseAttachmentData;
import org.octopus.spring_utils.jpa.GenericServiceI;

public interface BaseAttachmentDataDao extends GenericServiceI<BaseAttachmentData> {
	public BaseAttachmentData findBaseAttachmentDataById(Integer attchId);

}
