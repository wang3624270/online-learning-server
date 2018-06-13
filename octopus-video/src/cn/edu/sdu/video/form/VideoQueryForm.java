package cn.edu.sdu.video.form;

import java.util.Date;

import cn.edu.sdu.uims.form.impl.CommonQueryBaseForm;

public class VideoQueryForm extends CommonQueryBaseForm{

    private String name;
    private String title;
	private String type;
	private Date startDate;
	private Date endDate;
	private String year;
	private Integer liveState;
	private Boolean isVisable = true;
	private String types[];
	private String years[];
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String[] getYears() {
		return years;
	}
	public void setYears(String[] years) {
		this.years = years;
	}
	
	public Integer getLiveState() {
		return liveState;
	}
	public void setLiveState(Integer liveState) {
		this.liveState = liveState;
	}
	public Boolean getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(Boolean isVisable) {
		this.isVisable = isVisable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	
}
