package cn.edu.sdu.exam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "elearning_practice_q_relation")
public class ElearningPracticeQRelation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;// ID
	private Integer practiceId;// 
	private Integer questionId;// 
	private Integer number;// Ã‚∫≈
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
}
