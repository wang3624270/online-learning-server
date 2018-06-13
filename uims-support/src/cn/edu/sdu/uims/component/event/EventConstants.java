package cn.edu.sdu.uims.component.event;

public interface EventConstants {
	int EVENT_ACTION_INT = 1;
	int EVENT_ITEM_INT = 2;
	int EVENT_MOUSE_INT = 3;
	int EVENT_MOUSEMOTION_INT = 4;
	int EVENT_ADJUSTMENT_INT = 5;
	int EVENT_WINDOW_INT = 6;
	int EVENT_KEY_INT = 7;
	int EVENT_CHANGE_INT = 8;
	int EVENT_LISTSELECTION_INT = 9;
	int EVENT_TABLEMODE_INT = 10;
	int EVENT_TREESELECTION_INT = 11;
	int EVENT_GRAPH_INT = 12;
	int EVENT_GRAPHINTERTION_INT = 13;
	int EVENT_FOCUS_INT = 14;
	
	String  EVENT_ACTION = "action";
	String  EVENT_ITEM = "item";
	String  EVENT_MOUSE = "mouse";
	String  EVENT_MOUSERELEASE = "mouseRelease";
	String  EVENT_MOUSEMOTION = "mouseMotion";
	String  EVENT_ADJUSTMENT = "adjustment";
	String  EVENT_WINDOW = "window";
	String  EVENT_KEY = "key";
	String  EVENT_CHANGE = "change";
	String  EVENT_LISTSELECTION = "listSelection";
	String  EVENT_TABLEMODE = "tableModel";
	String  EVENT_TREESELECTION= "treeSelection";
	String  EVENT_GRAPH = "graph";
	String  EVENT_CALENDAR = "calendar";
	String  EVENT_SELECTOBJECT = "selectObject";
	String  EVENT_GRAPHINTERACTION = "graphInteraction";
	String  EVENT_GROUPCOMPONENT = "groupComponent";
	String  EVENT_FOCUS ="focus";
	String  EVENT_TIMER = "timer";
	
	String CMD_CLICKED = "clicked";
	String CMD_ENTERED = "entered";
	String CMD_EXITED = "exited";
	String CMD_PRESSED = "pressed";
	String CMD_RELEASED = "released";
	String CMD_DRAGGED = "dragged";
	String CMD_MOVED = "moved";
	String CMD_TYPEE = "typed";
	String CMD_ACTIVTED = "activated";
	String CMD_CLOSED = "closed";
	String CMD_CLOSING = "closing";
	String CMD_DEACTIVATED = "deactivated";
	String CMD_DEICONIFIED = "deiconified";
	String CMD_ICONIFIED = "iconified";
	String CMD_OPENED = "opened";
	String CMD_CHANGE = "change";
	String CMD_GAINED = "gained";
	String CMD_LOST = "lost";
	
	String CMD_IDCARD_ENTERED="idCardEntered";
	String CMD_CAMPUSECARD_ENTERED="campusCardEntered";
}
