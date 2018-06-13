package cn.edu.sdu.course.model;

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
@Table(name = "elearning_task_finish")
public class ElearningTaskFinish {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne    
    @JoinColumn(name="stuId")  
	@NotNull
	private InfoPersonInfo infoPersonInfo;
	@ManyToOne    
    @JoinColumn(name="taskId")  
	@NotNull
	private ElearningTeachTask elearningTeachTask;
	
	private String isFinish;
	
	@OneToMany(targetEntity=InfoPersonInfo.class,cascade=CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="stuId",updatable=false)  
    private Set infoPersonInfos = new LinkedHashSet();
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
	public InfoPersonInfo getInfoPersonInfo() {
		return infoPersonInfo;
	}
	public void setInfoPersonInfo(InfoPersonInfo infoPersonInfo) {
		this.infoPersonInfo = infoPersonInfo;
	}
	public ElearningTeachTask getElearningTeachTask() {
		return elearningTeachTask;
	}
	public void setElearningTeachTask(ElearningTeachTask elearningTeachTask) {
		this.elearningTeachTask = elearningTeachTask;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	public Set getInfoPersonInfos() {
		return infoPersonInfos;
	}
	public void setInfoPersonInfos(Set infoPersonInfos) {
		this.infoPersonInfos = infoPersonInfos;
	}
	public Set getElearningTeachTasks() {
		return elearningTeachTasks;
	}
	public void setElearningTeachTasks(Set elearningTeachTasks) {
		this.elearningTeachTasks = elearningTeachTasks;
	}
	
}
