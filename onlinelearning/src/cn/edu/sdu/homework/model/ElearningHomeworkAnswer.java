package cn.edu.sdu.homework.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "elearning_homework_answer")
public class ElearningHomeworkAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer answerId;
	private Integer stuId;//学生id
	private Integer homeworkId;//作业id
	private String answerContent;//答案内容
	private Integer attachId;
	private Double score;//成绩
	private Integer checkerId;//批改人Id
	private Date checkTime;//批改时间
	private Date createTime;//创建答案时间
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	public Integer getStuId() {
		return stuId;
	}
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
	public Integer getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public Integer getAttachId() {
		return attachId;
	}
	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Integer getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(Integer checkerId) {
		this.checkerId = checkerId;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	
}
