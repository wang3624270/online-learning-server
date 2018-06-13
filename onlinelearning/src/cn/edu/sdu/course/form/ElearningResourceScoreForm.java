package cn.edu.sdu.course.form;

import java.util.Date;
import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class ElearningResourceScoreForm extends UForm{
	private Integer id;
	private String resourceType;//打分类型  0为课程  1为具体课程内容
	private Integer personId;//评分人
	private Integer score;// 1-5分
	private Date createTime;
	private Integer taskId;//教学任务id
	private Integer sectionId;//节次id
	private Integer resourceId;//资源id
	private Integer flag;//本人有没有评分，1是已经评分了，0是还是没有评分。
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
