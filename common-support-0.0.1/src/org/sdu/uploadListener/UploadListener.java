package org.sdu.uploadListener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;




public class UploadListener implements ProgressListener{

	private HttpSession session;
	public UploadListener(HttpSession session){
		super();
		this.session = session;
		UploadStatus uploadStatus = new UploadStatus();
		session.setAttribute("upload_status", uploadStatus);
	}
	
	@Override
	public void update(long bytesRead, long contentLength, int items) {
		// TODO Auto-generated method stub
		UploadStatus uploadStatus = (UploadStatus) session.getAttribute("upload_status");
		long total=uploadStatus.getBytesRead()+bytesRead;
		if(contentLength==-1){
			contentLength=uploadStatus.getContentLength();
		}
		if(items==-1){
			items=uploadStatus.getItems();
		}
		uploadStatus.setBytesRead(total);
		uploadStatus.setContentLength(contentLength);
		uploadStatus.setItems(items);
		uploadStatus.setUseTime(System.currentTimeMillis()-uploadStatus.getStartTime());
		uploadStatus.setPercent((int)(100*total/contentLength));
		//System.out.println(uploadStatus.getItems()+","+uploadStatus.getPercent());
		session.setAttribute("upload_status", uploadStatus);
		
	}

}
