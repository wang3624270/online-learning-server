package cn.edu.sdu.uims.itms;

public interface ItmsConstants {
	String DEFAULY_CLASSNAME_TASK = "cn.edu.sdu.uims.itms.task.ITaskBase";
	String DEFAULY_CLASSNAME_DIAGRAM = "IDiagramBase";
	String DEFAULY_CLASSNAME_CURSOR = "cn.edu.sdu.uims.itms.cursor.ICursorBase";
	String DEFAULY_CLASSNAME_EVENTOR = "cn.edu.sdu.uims.itms.eventor.IEventorBase";
	String DEFAULY_CLASSNAME_STATUS = "cn.edu.sdu.uims.itms.status.IStatusBase";
	String DEFAULY_CLASSNAME_HANDLER = "IHandlerBase";
	String DEFAULY_CLASSNAME_FORM = "cn.edu.sdu.uims.itms.form.IForm";

	String KEY_STRING_CURSOR = "cursor";
	String KEY_STRING_EVENTOR = "eventor";
	String KEY_STRING_DIAGRAM = "statusdiagram";
	String KEY_STRING_TASK = "task";

	String STATUS_TYPE_STRING_NORMAL = "normal";
	String STATUS_TYPE_STRING_START = "start";
	String STATUS_TYPE_STRING_END = "end";
	String STATUS_TYPE_STRING_END_ESC = "esc_end"; // esc键的退出
	String STATUS_TYPE_STRING_END_USER_SWITCH = "userSwitch_end";// 用户切换的退出
	String STATUS_TYPE_STRING_TERMINATE_TASK = "TERMINATE_TASK";// 直接退出任务修改DIB

	int STATUS_TYPE_INT_NORMAL = 1;
	int STATUS_TYPE_INT_START = 2;
	int STATUS_TYPE_INT_END = 3;
	int STATUS_TYPE_INT_END_ESC = 4; // esc键的退出
	int STATUS_TYPE_INT_END_USER_SWITCH = 5;// 用户切换的退出
	int STATUS_TYPE_INT_TERMINATE_TASK = 6;// 直接退出任务修改DIB

	String OPERATE_TYPE_STRING_SEQUENCE = "seq";
	String OPERATE_TYPE_STRING_SELECT = "sel";

	int OPERATE_TYPE_INT_SEQUENCE = 1;
	int OPERATE_TYPE_INT_SELECT = 2;

	String TASK_TYPE_STRING_DIAGRAM = "diagram";
	String TASK_TYPE_STRING_TASK = "task";

	int TASK_TYPE_INT_DIAGRAM = 1;
	int TASK_TYPE_INT_TASK = 2;

	int EVENT_PROCESS_RETURN_STATUS_ERROR = -1;
	int EVENT_PROCESS_RETURN_STATUS_NORMAL = 1;
	int EVENT_PROCESS_RETURN_STATUS_END = 2;
	int EVENT_PROCESS_RETURN_STATUS_ESC = 3;
	int EVENT_PROCESS_RETURN_STATUS_HELP = 4;
	int EVENT_PROCESS_RETURN_STATUS_END_USER_SWITCH = 5;
	int EVENT_PROCESS_RETURN_STATUS_TERMINATE_TASK = 6;

	String EVENT_TYPE_LEFT_CLICK = "leftClick";
	String EVENT_TYPE_MOUSE_MOVE = "mouseMove";
	String EVENT_TYPE_lEFT_DOUBLE_CLICK = "leftDoubleClick";
	String EVENT_TYPE_MOUSE_DRAG = "mouseDrag";
	String EVENT_TYPE_LEFT_UP = "leftUp";
	String EVENT_TYPE_LEFT_DOWN = "leftDown";
	String EVENT_TYPE_ENTER_KEY_DOWN = "enterKeyDown";
	String EVENT_TYPE_SPACE_KEY_DOWN = "spaceKeyDown";
	String EVENT_TYPE_ESC_KEY_DOWN = "escapeKeyDown";
	String EVENT_TYPE_UNKNOW_KEY_DOWN = "keyDown";
	String EVENT_TYPE_BACKSPACE_KEY_DOWN = "backspaceKeyDown";

	String TRANS_ACTION_TYPE_DRAW_CURSOR = "drawCursor";
	String TRANS_ACTION_TYPE_COPY_REGION = "copyRegion";

	String TRANS_ACTION_TYPE_POPUP_MENU = "popUpMenu";

	String TRANS_ACTION_TYPE_ZOOM_IN = "zoomIn";

	String TRANS_ACTION_TYPE_ZOOM_OUT = "zoomOut";

	String TRANS_ACTION_TYPE_END_TO_MODIFY_GRAPH_DATA = "taskEndToModifyGraphData";

	String TRANS_ACTION_TYPE_SELECT_DRAGE_ELEMENT = "selectDrageElement";

	String TRANS_ACTION_TYPE_SELECT_DRAGE_RECT_PART = "selectDrageRectPart";

	String TASK_SUB_RELATION_ORDER = "order";
	String TASK_SUB_RELATION_SELECT = "select";

	int CURSOR_DRAW_MODE_DEFAULT = 1;
	int CURSOR_DRAW_MODE_DRAW = 1;
	int CURSOR_DRAW_MODE_IMAGECOPY = 2;

}
