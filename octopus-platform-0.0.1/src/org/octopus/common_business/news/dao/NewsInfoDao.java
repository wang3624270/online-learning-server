package org.octopus.common_business.news.dao;



import java.util.List;

import org.octopus.common_business.news.form.NewsInfoQueryForm;
import org.octopus.common_business.news.model.NewsInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;


public interface NewsInfoDao extends GenericServiceI<NewsInfo> {

	public List<NewsInfo>  getNewsTitleList(String newsType);
	public NewsInfo getNewsContent(Integer id);
	public String getNewNewsNum();
	public Integer getNewOrderNum();
	public List<NewsInfo> getAllNewsInfo();
	public List<NewsInfo> getNewsInfoListByQueryForm(NewsInfoQueryForm qForm);
	public void updateOrderNum(Integer oldNum, Integer newNum);
	public Integer getMaxOrderNum();
	public List getNewsTitleListByTypeAndNum(String newsType,Integer num);

}
