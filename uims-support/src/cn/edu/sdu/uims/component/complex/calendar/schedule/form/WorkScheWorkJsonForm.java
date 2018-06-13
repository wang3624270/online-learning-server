package cn.edu.sdu.uims.component.complex.calendar.schedule.form;

public class WorkScheWorkJsonForm implements java.io.Serializable {
	private Integer id;
	private String title;
	private String startTime;
	private String endTime;
	private String url;

	public WorkScheWorkJsonForm() {
	}

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
    
    

	
	

}
