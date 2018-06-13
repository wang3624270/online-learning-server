package cn.edu.sdu.course.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "elearning_section_acc")
public class ElearningSectionAcc {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer sectionId;//�ڴ�Id
	private Integer accId;//��ԴId
	private String type;//�����ֵ� ZYLXM ��Դ������ VIDEO��Ƶ MP3��Ƶ  PPT�μ�  PDF:PDF�ĵ�   WORD:WORD�ĵ� LIVE��ֱ��  OTHER����
	                   
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public Integer getAccId() {
		return accId;
	}
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
