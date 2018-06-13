package cn.edu.sdu.course.form;

import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class CourseNodeForm extends UForm{
	private Integer id;
	private String folderName;
	private String folderPath;
	private Integer isLeaf;
	private List childList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public List getChildList() {
		return childList;
	}
	public void setChildList(List childList) { 
		this.childList = childList;
	}
	
}
