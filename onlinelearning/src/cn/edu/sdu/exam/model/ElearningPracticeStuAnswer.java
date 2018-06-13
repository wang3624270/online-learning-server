package cn.edu.sdu.exam.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "elearning_practice_stu_answer")
public class ElearningPracticeStuAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer answerId;// ID
	private String answer;
	private Integer practiceId;
	private Integer questionId;
	private Integer stuId;
	private Date answerTime;
	
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getPracticeId() {
		return practiceId;
	}
	public void setPracticeId(Integer practiceId) {
		this.practiceId = practiceId;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getStuId() {
		return stuId;
	}
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
	public Date getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
	
	
}
