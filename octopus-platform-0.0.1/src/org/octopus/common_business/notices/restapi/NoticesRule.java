package org.octopus.common_business.notices.restapi;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.octopus.common_business.notices.dao.NoticesInfoDao;
import org.octopus.common_business.notices.dao.NoticesMemberInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesRule {
	
	@Autowired
	private NoticesInfoDao noticesInfoDao;
	@Autowired
	private NoticesMemberInfoDao noticesMemberInfoDao;
	public NoticesRule(){}
	
	
	@RequestMapping(value = "/allow/getNoticesList", method = RequestMethod.POST)
	public Map getNoticesList(HttpServletRequest request, @RequestBody Object obj) {
		return null;
		
	}
}
