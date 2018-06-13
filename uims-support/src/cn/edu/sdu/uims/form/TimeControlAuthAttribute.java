package cn.edu.sdu.uims.form;

import java.io.Serializable;
import java.util.Date;

public class TimeControlAuthAttribute implements Serializable {
	private Integer id;
	private Date startDate;
	private Date endDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
}
