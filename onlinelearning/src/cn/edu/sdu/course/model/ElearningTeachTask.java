package cn.edu.sdu.course.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.octopus.auth.jpa_model.InfoPersonInfo;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "elearning_teach_task")
public class ElearningTeachTask {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer taskId;
	
	@ManyToOne    
    @JoinColumn(name="courseId")  
	@NotNull
	private ElearningCourse elearningCourse; // 课程
	@ManyToOne    
    @JoinColumn(name="termId")  
	@NotNull
	private ElearningTerm elearningTerm; // 学年学期
	private Integer personId;//创建者	
	private String remark; // 备注	
    private Date modifyTime;
    private Integer modifyer;
    private String startDate;
    private String endDate;
    private String taskName;
    private String classPlace;
    
    
    @OneToMany(targetEntity=ElearningCourse.class,cascade=CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="courseId",updatable=false)  
    private Set elearningCourses = new LinkedHashSet();
    @OneToMany(targetEntity=ElearningTerm.class,cascade=CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="termId",updatable=false)  
    private Set elearningTerms = new LinkedHashSet();
    @OneToMany(targetEntity=InfoPersonInfo.class,cascade=CascadeType.ALL)
    
	public Integer getTaskId() {
		return taskId;
	}
	public String getClassPlace() {
		return classPlace;
	}
	public void setClassPlace(String classPlace) {
		this.classPlace = classPlace;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public ElearningCourse getElearningCourse() {
		return elearningCourse;
	}
	public void setElearningCourse(ElearningCourse elearningCourse) {
		this.elearningCourse = elearningCourse;
	}
	public ElearningTerm getElearningTerm() {
		return elearningTerm;
	}
	public void setElearningTerm(ElearningTerm elearningTerm) {
		this.elearningTerm = elearningTerm;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyer() {
		return modifyer;
	}
	public void setModifyer(Integer modifyer) {
		this.modifyer = modifyer;
	}
	public Set getElearningCourses() {
		return elearningCourses;
	}
	public void setElearningCourses(Set elearningCourses) {
		this.elearningCourses = elearningCourses;
	}
	public Set getElearningTerms() {
		return elearningTerms;
	}
	public void setElearningTerms(Set elearningTerms) {
		this.elearningTerms = elearningTerms;
	}
    
}
