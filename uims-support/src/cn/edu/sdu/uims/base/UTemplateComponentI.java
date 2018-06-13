package cn.edu.sdu.uims.base;

import cn.edu.sdu.common.reportdog.UTemplate;


public interface UTemplateComponentI extends UComponentI {
	void initComponents();
	void ResetComponentContent();
	void resetTemplate(UTemplate template);
	boolean testInvalidateData();

}
