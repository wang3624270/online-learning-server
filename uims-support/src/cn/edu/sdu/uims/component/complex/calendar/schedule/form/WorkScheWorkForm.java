package cn.edu.sdu.uims.component.complex.calendar.schedule.form;

import java.util.Date;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormI;

public class WorkScheWorkForm extends UForm implements CalendarFormI{
	private Integer id;
	private String title;
	private String type;
	private Date startTime;
	private Date endTime;
	private Date realStartTime;
	private Date realEndTime;
	private String description;
	private Integer createrId;
	private String createrName;
	private Integer order;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getRealStartTime() {
		return realStartTime;
	}
	public void setRealStartTime(Date realStartTime) {
		this.realStartTime = realStartTime;
	}
	public Date getRealEndTime() {
		return realEndTime;
	}
	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public Date getScheduleStartDate(){
		if(startTime == null && realStartTime == null) 
			return null;
		else if(startTime == null) {
			return realStartTime;
		}else if(realStartTime == null) 
			return startTime;
		else {
			if(startTime.before(realStartTime))
				return startTime;
			else
				return realStartTime;
		}
	}
	public Date getScheduleEndDate(){
		if(endTime == null && realEndTime == null) 
			return null;
		else if(endTime == null) {
			return realEndTime;
		}else if(realEndTime == null) 
			return endTime;
		else {
			if(endTime.after(realEndTime))
				return endTime;
			else
				return realEndTime;
		}		
	}
	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return startTime;
	}
	@Override
	public String getDispyString() {
		// TODO Auto-generated method stub		
		Date sDate = this.getScheduleStartDate();
		int h = sDate.getHours();
		if(h > 0)
			return h+":" + title;
		else
			return title;
	}
	@Override
	public Date getEndDate() {
		// TODO Auto-generated method stub
		return endTime;
	}
	@Override
	public String getHref() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
