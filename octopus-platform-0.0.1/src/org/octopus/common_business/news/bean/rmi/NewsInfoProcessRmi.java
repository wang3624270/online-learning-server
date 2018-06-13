package org.octopus.common_business.news.bean.rmi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.octopus.common_business.news.dao.NewsInfoDao;
import org.octopus.common_business.news.form.NewsInfoForm;
import org.octopus.common_business.news.form.NewsInfoQueryForm;
import org.octopus.common_business.news.model.NewsInfo;
import org.octopus.common_business.news.rule.NewsInfoProcessRule;
import org.sdu.file_util.FileUtility;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.sdu.common.util.CommonTool;



@Service
@Scope("singleton")
public class NewsInfoProcessRmi {
	private static String CONFIG_FILE_LOCATION = "/fs.cfg.properties";
	@Autowired
	private NewsInfoDao newsInfoDao;
	@Autowired
	private NewsInfoProcessRule newsInfoProcessRule;
	
	public void getAllNewsInfo(RmiRequest request, RmiResponse respond) {
		List<NewsInfo> list = newsInfoDao.getAllNewsInfo();
		List<NewsInfoForm> formList = new ArrayList<NewsInfoForm>();
		NewsInfo info;
		if(list != null && list.size() != 0) {
			for(int i = 0; i < list.size(); i++) {
				info = (NewsInfo)list.get(i);
				formList.add(this.newsInfoProcessRule.getNewsInfoFormFromModel(info));
			}	
		}
		respond.add(RmiKeyConstants.KEY_FORMLIST, formList);
	}

	public void getNewsInfoFormListByQueryForm(RmiRequest request, RmiResponse respond) {
		NewsInfoQueryForm qForm = (NewsInfoQueryForm)request.get(RmiKeyConstants.KEY_FORM);
		List<NewsInfo> list = newsInfoDao.getNewsInfoListByQueryForm(qForm);
		List<NewsInfoForm> formList = new ArrayList<NewsInfoForm>();
		NewsInfo info;
		if(list != null && list.size() != 0) {
			for(int i = 0; i < list.size(); i++) {
				info = (NewsInfo)list.get(i);
				formList.add(this.newsInfoProcessRule.getNewsInfoFormFromModel(info));
			}	
		}
		respond.add(RmiKeyConstants.KEY_FORMLIST, formList);
	}
	
	public void saveOrUpdateNewsInfo(RmiRequest request, RmiResponse respond) {
		NewsInfoForm form = (NewsInfoForm)request.get(RmiKeyConstants.KEY_FORM);
		if(form.getTitle() == null || form.getTitle().equals("")) {
			respond.setErrorMsg("标题不允许为空，请填写标题！");
			return;
		}
		NewsInfo newsInfo = null;
		if(form.getId() != null)
			newsInfo = newsInfoDao.query(form.getId());
		if(newsInfo == null) {
			newsInfo = new NewsInfo();
			newsInfo.setCreateTime(new Date());
			newsInfo.setReadCount(0);
			newsInfo.setNewsNum(newsInfoDao.getNewNewsNum());
		}
		newsInfo.setAttachId(form.getAttachId());
		newsInfo.setBrief(form.getBrief());
		newsInfo.setTitle(form.getTitle());
		newsInfo.setIsVisable(form.getIsVisable());
		newsInfo.setReadCount(form.getReadCount());
		newsInfo.setNewsType(form.getNewsType());
		newsInfo.setModifyTime(new Date());
		newsInfo.setCreaterId(form.getCreaterId());
		if(newsInfo.getId() == null) {
			newsInfo.setOrderNum(newsInfoDao.getNewOrderNum());
			newsInfoDao.save(newsInfo);
			respond.add("newsId", newsInfo.getId());
		}else {
			
			Integer oldNum = newsInfo.getOrderNum();
			Integer newNum = form.getOrderNum();
			if(!oldNum.equals(form.getOrderNum())) {
				Integer maxNum = newsInfoDao.getMaxOrderNum();
				if(newNum > maxNum) {
					newNum = maxNum;
				}
				newsInfoDao.updateOrderNum(oldNum, newNum);
			}
			newsInfo.setOrderNum(newNum);
			newsInfo.setNewsType(form.getNewsType());
			newsInfo.setIsVisable(form.getIsVisable());
			newsInfo.setBrief(form.getBrief());
			newsInfo.setTitle(form.getTitle());
			newsInfo.setModifyTime(new Date());
			newsInfoDao.update(newsInfo);
		}
	}
	public void deleteNewsInfo(RmiRequest request, RmiResponse respond) {
		Integer newsId = (Integer)request.get("newsId");
		NewsInfo newsInfo = newsInfoDao.query(newsId);
		newsInfoDao.delete(newsInfo);
	}
	
	public void upLoadImageFile(RmiRequest request, RmiResponse response) {
		byte[] data = (byte[])request.get("data");		
		if(data == null) {
			return;
		}
		Integer newsId = (Integer)request.get("newsId");
		InputStream in = new ByteArrayInputStream(data);
		Integer attachId = (Integer)request.get("attachId");
		String newsNum = (String)request.get("newsNum");
		String path = "news/photo/" + newsNum + "/Attach.jpg";
		attachId = newsInfoProcessRule.doUpLoadImageFile(attachId, path, in);
		NewsInfo newsInfo = newsInfoDao.query(newsId);
		newsInfo.setAttachId(attachId);
		newsInfoDao.update(newsInfo);
		response.add("attachId", attachId);
	}

	public void uploadNewsFile(RmiRequest request, RmiResponse response) {
		Integer id = (Integer)request.get("id");
		File file = (File)request.get("file");
		String URL = null;
		NewsInfo newsInfo = newsInfoDao.find(id);
		String newsNum = newsInfo.getNewsNum();
		if(newsNum == null || newsNum.equals("")) {
			newsInfo.setNewsNum(newsInfoDao.getNewNewsNum());
			newsInfoDao.update(newsInfo);
		}
		String path = "news" + "/" + newsNum;
		try {
			URL = FileUtility.directoryFileUpload(file, path);
			String httpUrl = CommonTool.getHttpPort(CONFIG_FILE_LOCATION);
			
			if(httpUrl != null && URL != null) {
				int index = URL.indexOf("news");
				URL = URL.substring(index);
				newsInfo.setURL(httpUrl + "/" + URL);
				newsInfoDao.update(newsInfo);
				response.add("URL", URL);
				response.add("httpUrl", httpUrl);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
