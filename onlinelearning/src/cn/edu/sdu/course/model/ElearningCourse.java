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
@Table(name = "elearning_course")
public class ElearningCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer courseId;// 课程ID
	private String courseNum;// 课程号
	private String courseName;// 课程名
	private String courseEngName;// 英文课程名
	private String courseType;
	private String teachingWayCode;// 教学方式 数据字典JXFSM
	private String examType;// 考核方式 数据字典KHFSM  考核类型
	private String teachLanguage;// 授课语言 数据字典SKYYLXM  考核类型
	private Double credit;// 学分
	private Integer classHour;// 学时
	private Integer termCount;// 学时（学期）
	private Integer teachingHour;// 上课学时
	private Integer experimentHour;// 实验学时
	private Integer onComputerHour;// 上机学时
	private Integer collegeId;// 开课院系
	private Integer collegeId1;// 开课院系
	private String teachGroup;// 教师组
	private String book;// 教材
	private String reference;// 参考书
	private String briefIntroduction;// 课程简介  
	private String englishBriefIntroduction;
	private String remark;// 备注
	@NotNull
	private Integer creatorId;// 课程创建者
	private Date createTime;
	private Date modifyTime;
	private Integer modifyerId;
	private String subSpec;// 课程说明
	private Integer coverImgAcc;
	private Integer coverVideoAcc;
	private String subject;//学科门类

	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
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

	public Integer getCoverImgAcc() {
		return coverImgAcc;
	}

	public void setCoverImgAcc(Integer coverImgAcc) {
		this.coverImgAcc = coverImgAcc;
	}

	public Integer getCoverVideoAcc() {
		return coverVideoAcc;
	}

	public void setCoverVideoAcc(Integer coverVideoAcc) {
		this.coverVideoAcc = coverVideoAcc;
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

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getTeachingWayCode() {
		return teachingWayCode;
	}

	public void setTeachingWayCode(String teachingWayCode) {
		this.teachingWayCode = teachingWayCode;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getTeachLanguage() {
		return teachLanguage;
	}

	public void setTeachLanguage(String teachLanguage) {
		this.teachLanguage = teachLanguage;
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

	public Integer getTermCount() {
		return termCount;
	}

	public void setTermCount(Integer termCount) {
		this.termCount = termCount;
	}

	public Integer getTeachingHour() {
		return teachingHour;
	}

	public void setTeachingHour(Integer teachingHour) {
		this.teachingHour = teachingHour;
	}

	public Integer getExperimentHour() {
		return experimentHour;
	}

	public void setExperimentHour(Integer experimentHour) {
		this.experimentHour = experimentHour;
	}

	public Integer getOnComputerHour() {
		return onComputerHour;
	}

	public void setOnComputerHour(Integer onComputerHour) {
		this.onComputerHour = onComputerHour;
	}

	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	public Integer getCollegeId1() {
		return collegeId1;
	}

	public void setCollegeId1(Integer collegeId1) {
		this.collegeId1 = collegeId1;
	}

	public String getTeachGroup() {
		return teachGroup;
	}

	public void setTeachGroup(String teachGroup) {
		this.teachGroup = teachGroup;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getEnglishBriefIntroduction() {
		return englishBriefIntroduction;
	}

	public void setEnglishBriefIntroduction(String englishBriefIntroduction) {
		this.englishBriefIntroduction = englishBriefIntroduction;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getSubSpec() {
		return subSpec;
	}

	public void setSubSpec(String subSpec) {
		this.subSpec = subSpec;
	}


	
}
