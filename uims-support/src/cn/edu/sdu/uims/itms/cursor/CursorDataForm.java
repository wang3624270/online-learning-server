package cn.edu.sdu.uims.itms.cursor;

public class CursorDataForm {
	public static int CURSOR_DISPLAY_STATUS_ON = 1;
	public static int CURSOR_DISPLAY_STATUS_DYN = 2;
	private int status = CURSOR_DISPLAY_STATUS_ON;
	private ICursorBase cursor;
	private ICursorDataBase cursorData;
	public ICursorBase getCursor() {
		return cursor;
	}
	public void setCursor(ICursorBase cursor) {
		this.cursor = cursor;
	}
	public ICursorDataBase getCursorData() {
		return cursorData;
	}
	public void setCursorData(ICursorDataBase cursorData) {
		this.cursorData = cursorData;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
