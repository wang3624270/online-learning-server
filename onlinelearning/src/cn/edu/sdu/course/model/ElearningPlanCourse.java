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
@Table(name = "elearning_plan_course")
public class ElearningPlanCourse {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Integer id;
     private Integer stuId;
	 @ManyToOne    
	 @JoinColumn(name="taskId")  
	 @NotNull
	 private ElearningTeachTask elearningTeachTask;
	 
     private String remark;
     private Date modifyTime;
     private Integer modifyerId;

     @OneToMany(targetEntity=ElearningTeachTask.class,cascade=CascadeType.ALL)
     @Fetch(FetchMode.JOIN)
     @JoinColumn(name="taskId",updatable=false)  
     private Set elearningTeachTasks = new LinkedHashSet();
     
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public ElearningTeachTask getElearningTeachTask() {
		return elearningTeachTask;
	}
	public void setElearningTeachTask(ElearningTeachTask elearningTeachTask) {
		this.elearningTeachTask = elearningTeachTask;
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
	public Integer getModifyerId() {
		return modifyerId;
	}
	public void setModifyerId(Integer modifyerId) {
		this.modifyerId = modifyerId;
	}
	public Integer getStuId() {
		return stuId;
	}
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
	public Set getElearningTeachTasks() {
		return elearningTeachTasks;
	}
	public void setElearningTeachTasks(Set elearningTeachTasks) {
		this.elearningTeachTasks = elearningTeachTasks;
	}
     
}
