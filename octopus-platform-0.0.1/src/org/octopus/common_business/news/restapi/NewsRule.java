package org.octopus.common_business.news.restapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.octopus.common_business.attachment.rule.BaseAttachmentInfoProcessRule;
import org.octopus.common_business.attachment.util.BaseAttachmentInfoProcessUtil;

import org.octopus.common_business.news.dao.NewsInfoDao;
import org.octopus.common_business.news.form.NewsInfoForm;
import org.octopus.common_business.news.model.NewsInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import cn.edu.sdu.common.util.CommonTool;


@RestController
public class NewsRule {
	
//	@Autowired
//	private GroupNewsUserRuleI groupNewsTypeUserRule;
	@Autowired
	private NewsInfoDao newsInfoDao;
	@Autowired
	private BaseAttachmentInfoProcessRule baseAttachmentInfoProcessRule;
	public NewsRule(){}
	
	
	@RequestMapping(value = "/allow/getNewsList", method = RequestMethod.POST)
	public Map getNewsList(HttpServletRequest request, @RequestBody Object obj) {
		Map map = (Map)obj;
		String newsType = (String)map.get("newsType");
		Map<String, Object> result = new HashMap<String, Object>();
		ArrayList a = new ArrayList();
		List <NewsInfo> newsTitleList = newsInfoDao.getNewsTitleList(newsType);
		for(NewsInfo news: newsTitleList){
			Integer attachId = news.getAttachId();
			String img = "";
			if(attachId != null){
				img="data:image/jpeg;base64,";
				Object file[] = BaseAttachmentInfoProcessUtil.getDownloadAttachmentFile(attachId);
				byte[] b= baseAttachmentInfoProcessRule.getFileDataOfBaseAttachmentInfo(attachId);
			    String s = new String(Base64.encode(b));
			    img=img+s;
			}
			NewsInfoForm newsData = new NewsInfoForm();
			newsData.setImg(img);
			newsData.setNewsType(news.getNewsType());
			newsData.setBrief(news.getBrief());
			newsData.setCreateTime(news.getCreateTime());
			newsData.setReadCount(news.getReadCount());
			newsData.setTitle(news.getTitle());
			newsData.setId(news.getId());
			newsData.setNewsNum(news.getNewsNum());
		    a.add(newsData);
		}
		return CommonTool.getNodeMap(a, null);
	}
	
	@RequestMapping(value = "/allow/getHomepageNews", method = RequestMethod.POST)
	public Map getHomepageNews(HttpServletRequest request, @RequestBody Object obj) {
		Map map = (Map)obj;
		String newsType = (String)map.get("newsType");
		Map<String, Object> result = new HashMap<String, Object>();
		ArrayList a = new ArrayList();
		List <NewsInfo> newsTitleList = newsInfoDao.getNewsTitleList(newsType);
		int i = 0;
		for(NewsInfo news: newsTitleList){
			Integer attachId = news.getAttachId();
			String img = "";
			if(attachId != null){
				img="data:image/jpeg;base64,";
				Object file[] = BaseAttachmentInfoProcessUtil.getDownloadAttachmentFile(attachId);
				byte[] b= baseAttachmentInfoProcessRule.getFileDataOfBaseAttachmentInfo(attachId);
			    String s = new String(Base64.encode(b));
			    img=img+s;
			}
			NewsInfoForm newsData = new NewsInfoForm();
			newsData.setImg(img);
			newsData.setNewsType(news.getNewsType());
			newsData.setBrief(news.getBrief());
			newsData.setCreateTime(news.getCreateTime());
			newsData.setReadCount(news.getReadCount());
			newsData.setTitle(news.getTitle());
			newsData.setId(news.getId());
			newsData.setNewsNum(news.getNewsNum());
		    a.add(newsData);
		    i++;
		    if(i>=4)break;
		}
		return CommonTool.getNodeMap(a, null);
	}
	
	@RequestMapping(value = "/allow/getAllCompetieionNews", method = RequestMethod.POST)
	public Map getAllCompetieionNews(HttpServletRequest request, @RequestBody Object obj) {
		Map map = (Map)obj;
		String newsType = (String)map.get("newsType");
		Map<String, Object> result = new HashMap<String, Object>();
		ArrayList a = new ArrayList();
		List <NewsInfo> newsTitleList = newsInfoDao.getNewsTitleList(newsType);
		if(newsTitleList!=null){
		for(NewsInfo news: newsTitleList){
			NewsInfoForm newsData = new NewsInfoForm();
			newsData.setNewsType(news.getNewsType());
			newsData.setBrief(news.getBrief());
			newsData.setCreateTime(news.getCreateTime());
			newsData.setReadCount(news.getReadCount());
			newsData.setTitle(news.getTitle());
			newsData.setId(news.getId());
			newsData.setNewsNum(news.getNewsNum());
		    a.add(newsData);
		}
		}
		return CommonTool.getNodeMap(a, null);
	}
	
//	@RequestMapping(value = "/allow/getNewsContent", method = RequestMethod.POST)
//	public Map getNewsContent(HttpServletRequest request, @RequestBody Object obj) {
//		Map map = (Map)obj;
//		Integer newsId = (Integer)map.get("newsId");
//		Map<String, Object> result = new HashMap<String, Object>();
//		ArrayList a = new ArrayList();
//		NewsInfo newsContent = newsInfoDao.getNewsContent(newsId);
//		result.put("data", newsContent);
//		return result;
//		
//	}
}
