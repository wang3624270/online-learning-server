package cn.edu.sdu.exam.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "elearning_practice_info")
public class ElearningPracticeInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer practiceId;// 测试ID
	private String practiceTitle;//测试名称
	private Integer personId;//创建人
	private Date createTime;//创建时间
	
	public Integer getPracticeId() {
		return practiceId;
	}
	public void setPracticeId(Integer practiceId) {
		this.practiceId = practiceId;
	}
	public String getPracticeTitle() {
		return practiceTitle;
	}
	public void setPracticeTitle(String practiceTitle) {
		this.practiceTitle = practiceTitle;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
