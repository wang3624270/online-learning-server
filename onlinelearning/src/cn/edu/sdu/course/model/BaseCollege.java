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
@Table(name = "base_college")
public class BaseCollege {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer collegeId;// 院系id
	
	private String collegeNum;// 院系编号
	private String collegeName;// 院系名
    private String abbrName; //院系简称
	private String collegeEngName;// 英文院系名
	private String collegeIntro;
	private String howToContact;
	private String masterRetrialPlan;
	private String doctorRetrialPlan;
    private Integer  departmentId;
    private Integer orderNum;
    private String master;
    private Date foundTime;
    private String postCode;
    private String address;
    private String faxNum;
    private String telphone;
    private String website;
	private String collegeIntroEng;
    private String websiteEng;
    private String universityNum;
    private String email;
    private String collegeType;//本科/专科
    private String centerCollegeNum;
    private String wenliFenke;//学科分类
    private String unifiedCollegeNum;//统一学院编码
    
    @ManyToOne    
    @JoinColumn(name="campusNum")  
	@NotNull
    private BaseCampusInfo baseCampusInfo;
    
	@OneToMany(targetEntity=BaseCampusInfo.class,cascade=CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="campusNum",updatable=false)  
    private Set baseCampusInfos = new LinkedHashSet();

	public Integer getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeNum() {
		return collegeNum;
	}

	public void setCollegeNum(String collegeNum) {
		this.collegeNum = collegeNum;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getAbbrName() {
		return abbrName;
	}

	public void setAbbrName(String abbrName) {
		this.abbrName = abbrName;
	}

	public String getCollegeEngName() {
		return collegeEngName;
	}

	public void setCollegeEngName(String collegeEngName) {
		this.collegeEngName = collegeEngName;
	}

	public String getCollegeIntro() {
		return collegeIntro;
	}

	public void setCollegeIntro(String collegeIntro) {
		this.collegeIntro = collegeIntro;
	}

	public String getHowToContact() {
		return howToContact;
	}

	public void setHowToContact(String howToContact) {
		this.howToContact = howToContact;
	}

	public String getMasterRetrialPlan() {
		return masterRetrialPlan;
	}

	public void setMasterRetrialPlan(String masterRetrialPlan) {
		this.masterRetrialPlan = masterRetrialPlan;
	}

	public String getDoctorRetrialPlan() {
		return doctorRetrialPlan;
	}

	public void setDoctorRetrialPlan(String doctorRetrialPlan) {
		this.doctorRetrialPlan = doctorRetrialPlan;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public Date getFoundTime() {
		return foundTime;
	}

	public void setFoundTime(Date foundTime) {
		this.foundTime = foundTime;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFaxNum() {
		return faxNum;
	}

	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCollegeIntroEng() {
		return collegeIntroEng;
	}

	public void setCollegeIntroEng(String collegeIntroEng) {
		this.collegeIntroEng = collegeIntroEng;
	}

	public String getWebsiteEng() {
		return websiteEng;
	}

	public void setWebsiteEng(String websiteEng) {
		this.websiteEng = websiteEng;
	}

	public String getUniversityNum() {
		return universityNum;
	}

	public void setUniversityNum(String universityNum) {
		this.universityNum = universityNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCollegeType() {
		return collegeType;
	}

	public void setCollegeType(String collegeType) {
		this.collegeType = collegeType;
	}

	public String getCenterCollegeNum() {
		return centerCollegeNum;
	}

	public void setCenterCollegeNum(String centerCollegeNum) {
		this.centerCollegeNum = centerCollegeNum;
	}

	public String getWenliFenke() {
		return wenliFenke;
	}

	public void setWenliFenke(String wenliFenke) {
		this.wenliFenke = wenliFenke;
	}

	public String getUnifiedCollegeNum() {
		return unifiedCollegeNum;
	}

	public void setUnifiedCollegeNum(String unifiedCollegeNum) {
		this.unifiedCollegeNum = unifiedCollegeNum;
	}

	public BaseCampusInfo getBaseCampusInfo() {
		return baseCampusInfo;
	}

	public void setBaseCampusInfo(BaseCampusInfo baseCampusInfo) {
		this.baseCampusInfo = baseCampusInfo;
	}

	public Set getBaseCampusInfos() {
		return baseCampusInfos;
	}

	public void setBaseCampusInfos(Set baseCampusInfos) {
		this.baseCampusInfos = baseCampusInfos;
	}
	
}
