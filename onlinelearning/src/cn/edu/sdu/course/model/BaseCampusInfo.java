package cn.edu.sdu.course.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "base_campus_info")
public class BaseCampusInfo {
	@Id
	private String campusNum;//У�����
	private String campusName;//У������
	private String campusEngName;//У��Ӣ������
	private String universityNum;//ѧУ���
	private String campusAddress;//У����ַ
	private String campusRegion;//
	
	public String getCampusNum() {
		return campusNum;
	}
	public void setCampusNum(String campusNum) {
		this.campusNum = campusNum;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getCampusEngName() {
		return campusEngName;
	}
	public void setCampusEngName(String campusEngName) {
		this.campusEngName = campusEngName;
	}
	public String getUniversityNum() {
		return universityNum;
	}
	public void setUniversityNum(String universityNum) {
		this.universityNum = universityNum;
	}
	public String getCampusAddress() {
		return campusAddress;
	}
	public void setCampusAddress(String campusAddress) {
		this.campusAddress = campusAddress;
	}
	public String getCampusRegion() {
		return campusRegion;
	}
	public void setCampusRegion(String campusRegion) {
		this.campusRegion = campusRegion;
	}
	
}
