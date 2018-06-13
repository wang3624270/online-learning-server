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

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "elearning_achievement")
public class ElearningAchievement{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer achiId;//.����
    private Integer personId;//��Ա��Ϣ
    private String examTime;//����ʱ��
    private Integer recorder;//¼����ID
    private String subItemScore;//���Գɼ�
    private Double endScore;//��ĩ�ɼ�
    private Date recordTime;//¼��ʱ��
    private Integer recordState;//¼��״̬ �ύ ����
    private String remark;//��ע
    private Integer sort;//����
    private String evaluate;//ѧ���Ƿ������۸ý�ʦ 1������ 0δ����
    private Date modifyTime;
    private String modifyer;
    private String courseType;//1���������ڵĳɼ�  0�ǿγ̵ĳɼ�
    private String courseNum;
    private String courseName;
    private String courseEngName;
    private String courseSort;  //�γ����� ��������������)
    private String examType;  //���Է�ʽ ���γ̾�����
    private Double credit;//ѧ��
    private Integer classHour;//ѧʱ
    private Integer teaNum;
    private String teaName;
    private String perNum;
    
    @ManyToOne    
    @JoinColumn(name="taskId")  
	@NotNull
	private ElearningTeachTask elearningTeachTask;
    
    @OneToMany(targetEntity=ElearningTeachTask.class,cascade=CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="taskId",updatable=false)  
    private Set elearningTeachTasks = new LinkedHashSet();

	public Integer getAchiId() {
		return achiId;
	}

	public void setAchiId(Integer achiId) {
		this.achiId = achiId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public Integer getRecorder() {
		return recorder;
	}

	public void setRecorder(Integer recorder) {
		this.recorder = recorder;
	}

	public String getSubItemScore() {
		return subItemScore;
	}

	public void setSubItemScore(String subItemScore) {
		this.subItemScore = subItemScore;
	}

	public Double getEndScore() {
		return endScore;
	}

	public void setEndScore(Double endScore) {
		this.endScore = endScore;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Integer getRecordState() {
		return recordState;
	}

	public void setRecordState(Integer recordState) {
		this.recordState = recordState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseEngName() {
		return courseEngName;
	}

	public void setCourseEngName(String courseEngName) {
		this.courseEngName = courseEngName;
	}

	public String getCourseSort() {
		return courseSort;
	}

	public void setCourseSort(String courseSort) {
		this.courseSort = courseSort;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Integer getClassHour() {
		return classHour;
	}

	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}

	public Integer getTeaNum() {
		return teaNum;
	}

	public void setTeaNum(Integer teaNum) {
		this.teaNum = teaNum;
	}

	public String getTeaName() {
		return teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getPerNum() {
		return perNum;
	}

	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}

	public ElearningTeachTask getElearningTeachTask() {
		return elearningTeachTask;
	}

	public void setElearningTeachTask(ElearningTeachTask elearningTeachTask) {
		this.elearningTeachTask = elearningTeachTask;
	}

	public Set getElearningTeachTasks() {
		return elearningTeachTasks;
	}

	public void setElearningTeachTasks(Set elearningTeachTasks) {
		this.elearningTeachTasks = elearningTeachTasks;
	}
    
}
