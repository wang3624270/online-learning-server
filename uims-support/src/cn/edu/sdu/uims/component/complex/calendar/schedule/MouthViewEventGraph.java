package cn.edu.sdu.uims.component.complex.calendar.schedule;

import cn.edu.sdu.uims.component.complex.calendar.schedule.form.WorkScheWorkForm;

public class MouthViewEventGraph {
	private WorkScheWorkForm scheduleInfoForm;
	private MonthViewStruct view;

	public void makeViewData(){
		if(scheduleInfoForm == null) {
			view = null;
		}else{
			view = new MonthViewStruct();
		}
	}
	public WorkScheWorkForm getScheduleInfoForm() {
		return scheduleInfoForm;
	}

	public void setScheduleInfoForm(WorkScheWorkForm scheduleInfoForm) {
		this.scheduleInfoForm = scheduleInfoForm;
	}


	public MonthViewStruct getView() {
		return view;
	}
	public void setView(MonthViewStruct view) {
		this.view = view;
	}
	public int getCrossDays() {
		if(view != null)
			return view.getEndDayOffset() - view.getStartDayOffset() + 1;
		else
			return 0;	
	}

}