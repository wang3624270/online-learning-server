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
@Table(name = "accessories_folder_acc")
public class AccessoriesFolderAcc{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer folderId;
	private Integer accId;
	private String type;//数据字典 ZYLXM 资源类型码 VIDEO视频 MP3音频  PPT课件  OTHER其它

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFolderId() {
		return folderId;
	}
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
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