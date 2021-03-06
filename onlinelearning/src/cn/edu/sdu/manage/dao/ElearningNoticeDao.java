package cn.edu.sdu.manage.dao;

import java.util.List;

import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.manage.model.ElearningNotice;

public interface ElearningNoticeDao extends GenericServiceI<ElearningNotice> {

	public List getListByConditions(String title);
}
