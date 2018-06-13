package org.sdu.uploadListener;

public class UploadStatus {
	private long bytesRead;//已读数据
	private long contentLength;//文件总数据
	private int items;//第几个文件
	private long startTime = System.currentTimeMillis();//开始时间
	private long useTime = System.currentTimeMillis();//已用时间
	private int percent;//完成百分比
	
	public long getBytesRead() {
		return bytesRead;
	}
	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	
	public int getItems() {
		return items;
	}
	public void setItems(int items) {
		this.items = items;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getUseTime() {
		return useTime;
	}
	public void setUseTime(long useTime) {
		this.useTime = useTime;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "UploadStatus [percent=" + percent + ", items=" + items + "]";
	}
}
