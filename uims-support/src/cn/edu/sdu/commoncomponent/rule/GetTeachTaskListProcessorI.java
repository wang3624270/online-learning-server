package cn.edu.sdu.commoncomponent.rule;

import java.util.List;

public interface GetTeachTaskListProcessorI {
	List getTeachTaskNoListByCourseNumOrName(String num, String name, String taskType);
}
