package cn.edu.sdu.course.form;

import java.util.Date;
import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class ElearningResourceScoreForm extends UForm{
	private Integer id;
	private String resourceType;//�������  0Ϊ�γ�  1Ϊ����γ�����
	private Integer personId;//������
	private Integer score;// 1-5��
	private Date createTime;
	private Integer taskId;//��ѧ����id
	private Integer sectionId;//�ڴ�id
	private Integer resourceId;//��Դid
	private Integer flag;//������û�����֣�1���Ѿ������ˣ�0�ǻ���û�����֡�
	
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
