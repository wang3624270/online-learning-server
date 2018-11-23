package cn.edu.sdu.course.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "elearning_term")
public class ElearningTerm {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer termId;
    private String termName;
    private String termEngName;
    private String startTime;
    private String endTime;
    private String remark;
    private String examTime;
    private String termNum;
    private String termType;
    private Integer isVisible;//是否为当前学期
    
	public Integer getTermId() {
		return termId;
	}
	public void setTermId(Integer termId) {
		this.termId = termId;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public String getTermEngName() {
		return termEngName;
	}
	public void setTermEngName(String termEngName) {
		this.termEngName = termEngName;
	}

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public String getTermNum() {
		return termNum;
	}
	public void setTermNum(String termNum) {
		this.termNum = termNum;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public Integer getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}
    
}
