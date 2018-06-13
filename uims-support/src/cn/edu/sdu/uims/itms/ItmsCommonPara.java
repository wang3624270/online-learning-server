package cn.edu.sdu.uims.itms;

import java.util.ArrayList;

import cn.edu.sdu.uims.trans.UFPoint;

public class ItmsCommonPara {
	public static String userSwitchEvent = "";//用户切换子任务的事件名字
	public static UFPoint commonPoint = new UFPoint();
	public static ArrayList cursorPointsSequence = new ArrayList();//按照顺序执行的各个任务的数据的指针ICursorDataPoints*，只包含StandardTask，复合任务分解为Standard后加入,当一个任务结束之后将它加入
	public static int  userTaskTotalPointsNum = 0;//一次userTask任务中总共记录的点数，当开始userTask时候清空

}
