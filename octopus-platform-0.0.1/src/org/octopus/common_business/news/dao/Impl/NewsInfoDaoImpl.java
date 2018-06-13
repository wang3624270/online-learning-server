package org.octopus.common_business.news.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.octopus.common_business.news.dao.NewsInfoDao;
import org.octopus.common_business.news.form.NewsInfoQueryForm;
import org.octopus.common_business.news.model.NewsInfo;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.common.util.DateTimeTool;

@Repository
public class NewsInfoDaoImpl extends GenericServiceImpl<NewsInfo> implements NewsInfoDao {

	public NewsInfoDaoImpl() {
		super(NewsInfo.class);
	}

	
	
	@Override
	public List<NewsInfo>  getNewsTitleList(String newsType){
		String hql = "from NewsInfo where isVisable = 1";
		if(newsType != null){
			hql += "and newsType = "+"'"+newsType+"'";
		}
		List<NewsInfo> list = (List<NewsInfo>)this.queryForList(hql);
		if(list!=null&list.size()!=0){
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	public NewsInfo getNewsContent(Integer id) {
		// TODO Auto-generated method stub
		List NewsList = null;
		String hql = "from NewsInfo where isVisable = 1";
		if(id != null){
			hql += "and id = "+id;
		}
		NewsInfo list = (NewsInfo)this.queryForList(hql);
		if(list!=null){
			return list;
		}else{
			return null;
		}
	}
	@Override
	public String getNewNewsNum(){
		Date date = new Date();
		String prefix = DateTimeTool.parseDateTime(date, "yyyy-MM-dd").substring(0,4);
		String sql = " select max(n.newsNum) from NewsInfo n";
		String num = (String)this.queryForObject(sql, null);
		return CommonTool.getNextNum(prefix, num);
		
	}

	@Override
	public Integer getNewOrderNum(){
		String sql = " select max(n.orderNum) from NewsInfo n";
		Integer num = (Integer)this.queryForObject(sql, null);
		if(num == null)
			return 1;
		else
			return num.intValue()+1;		
	}
	
	@Override
	public List<NewsInfo> getAllNewsInfo() {
		// TODO Auto-generated method stub
		String hql = "from NewsInfo where 1 = 1 and isVisable = 1 order by orderNum desc";
		return this.queryForList(hql);
	}



	@Override
	public List<NewsInfo> getNewsInfoListByQueryForm(NewsInfoQueryForm qForm) {
		// TODO Auto-generated method stub
		String newsType = qForm.getNewsType();
		String newsNum = qForm.getNewsNum();
		String title =qForm.getTitle();
		Boolean isVisable = qForm.getIsVisable();
		Date startDate = qForm.getStartDate();
		Date endDate =qForm.getEndDate();
		String str;
		String hql = "from NewsInfo where 1 = 1 ";
		if(newsType != null && newsType.length() != 0 && !newsType.equals("-1")) {
			hql += " and newsType= '" + newsType + "'";
		}
		if(newsNum != null && newsNum.length() != 0 ) {
			hql += "and newsNum like '" + newsNum + "%'";
		}
		if(title != null && title.length() != 0 ) {
			hql += "and title like '%" + title + "%'";
		}
		if(isVisable != null && isVisable.booleanValue()) {
			hql += "and isVisable = 1";
		}else {
			hql += "and isVisable = 0";			
		}
		if(startDate != null ) {
			str = DateTimeTool.parseDateTime(startDate, "yyyy-MM-dd HH:mm:ss");
			hql += "and createTime >='" + str + "'";
		}
		if(endDate != null ) {
			str = DateTimeTool.parseDateTime(endDate, "yyyy-MM-dd HH:mm:ss");
			hql += "and createTime <='" + str + "'";
		}
		hql += " order by orderNum desc";
		return this.queryForList(hql);
	}



	@Override
	@Transactional
	public void updateOrderNum(Integer oldNum, Integer newNum) {
		// TODO Auto-generated method stub
		String hql = null;
		if(oldNum < newNum) {
			oldNum = oldNum + 1;
			hql = "update NewsInfo set orderNum = orderNum - 1 where 1 = 1 and orderNum between " + oldNum + " and " + newNum ;
		}else if(oldNum > newNum) {
			oldNum = oldNum - 1;
			hql = "update NewsInfo set orderNum = orderNum + 1 where 1 = 1 and orderNum between " + newNum + " and " + oldNum  ;
		}
		if(hql != null) {	
			this.execute(hql);
		}
	}



	@Override
	public Integer getMaxOrderNum() {
		// TODO Auto-generated method stub
		String sql = " select max(n.orderNum) from NewsInfo n";
		Integer num = (Integer)this.queryForObject(sql, null);
		if(num == null)
			return 1;
		else
			return num.intValue();
	}
	
	@Override
	public List getNewsTitleListByTypeAndNum(String newsType,Integer num){
		String hql = "from NewsInfo where isVisable = 1";
		if(newsType != null){
			hql += "and newsType = "+"'"+newsType+"'";
		}
		hql += "order by createTime desc";
		List list = (List)this.queryForList(hql);
		if(list!=null&list.size()!=0){
			List dataList=new ArrayList();
			if(num != null){
				for(int i=0;i<num && i<list.size();i++){
					dataList.add(list.get(i));
				}
				return dataList;
			}else{
				return list;
			}
		}else{
			return null;
		}
	}
	
	
	
	
	
}
