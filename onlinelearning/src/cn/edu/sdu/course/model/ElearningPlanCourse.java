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
     private Date createTime;
     private Integer creator;
     private String state;//1 自主选择  2教师导入
     private Integer time;//分钟
     private Double completeDegree;
     private Integer sectionIdNode;
     private String isFinish;
     

     @OneToMany(targetEntity=ElearningTeachTask.class,cascade=CascadeType.ALL)
     @Fetch(FetchMode.JOIN)
     @JoinColumn(name="taskId",updatable=false)  
     private Set elearningTeachTasks = new LinkedHashSet();
     
     
     
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Double getCompleteDegree() {
		return completeDegree;
	}
	public void setCompleteDegree(Double completeDegree) {
		this.completeDegree = completeDegree;
	}
	public Integer getSectionIdNode() {
		return sectionIdNode;
	}
	public void setSectionIdNode(Integer sectionIdNode) {
		this.sectionIdNode = sectionIdNode;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
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
