package cn.edu.sdu.homework.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.octopus.common_business.notices.model.NoticesMemberInfo;
import org.octopus.spring_utils.jpa.GenericServiceI;

import cn.edu.sdu.course.model.AccessoriesCourseFolder;
import cn.edu.sdu.homework.model.ElearningHomeworkInfo;

public interface ElearningHomeworkInfoDao extends GenericServiceI<ElearningHomeworkInfo>{
	
	public List getHomelistListByTaskId(Integer taskId);

	public ElearningHomeworkInfo getHomeworkInfoById(Integer homeworkId);
}
