package cn.edu.sdu.lecture.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "elearning_lecture")
public class ElearningLecture {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer lectureId;
	private String theme;
	private Date createTime;
	private Integer creator;
	private Date modifyTime;
	private Integer modifier;
	private String startTime;
	private String endTime;
	private String place;
	private String lectureType;
	private Integer maxNumber;
	private String registStart;
	private String registEnd;
	private Integer coverImgAcc;
	private String speaker;
	private Integer collegeId;
	private String introduce;
	
	
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public Integer getLectureId() {
		return lectureId;
	}
	public void setLectureId(Integer lectureId) {
		this.lectureId = lectureId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
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
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getModifier() {
		return modifier;
	}
	public void setModifier(Integer modifier) {
		this.modifier = modifier;
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
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}

	public String getLectureType() {
		return lectureType;
	}
	public void setLectureType(String lectureType) {
		this.lectureType = lectureType;
	}
	public Integer getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}
	public String getRegistStart() {
		return registStart;
	}
	public void setRegistStart(String registStart) {
		this.registStart = registStart;
	}
	public String getRegistEnd() {
		return registEnd;
	}
	public void setRegistEnd(String registEnd) {
		this.registEnd = registEnd;
	}
	public Integer getCoverImgAcc() {
		return coverImgAcc;
	}
	public void setCoverImgAcc(Integer coverImgAcc) {
		this.coverImgAcc = coverImgAcc;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	public Integer getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	
}
