package cn.edu.sdu.video.handler;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.event.ListSelectionEvent;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import com.video.form.AudioVideoInfoForm;

import cn.edu.sdu.common.form.BaseAttachmentInfoForm;
import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.complex.UVideoPlayPanel;
import cn.edu.sdu.uims.component.list.UList;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.form.impl.ImageAttachViewForm;
import cn.edu.sdu.uims.handler.UImageAttachOwnerHanderI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.util.UimsUtils;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;
 

public class AudioVideoInfoManageHandler extends UFormHandler{
	//private UVideoPlayPanel video;
	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent) o;
		String amd = e.getActionCommand();
		if(amd.equals("query")) {
			doQuery();
		}else if(amd.equals("doNew")) {
			doNew();
		}else if(amd.equals("doSave")) {
			doSave();
		}else if(amd.equals("doDelete")) {
			doDelete();
		}else if(amd.equals("doUploadImage")) {
			doUploadImage();
		}else if(amd.equals("doSelect")) {
			doSelect();
		}else if(amd.equals("doUploadVideo")) {
			doUploadVideo();
		}
	}

	private void doSelect() {
		File file = GetFile.getOpenFile();
		String path = file.getAbsolutePath();
		String type = (String)this.component.getSubComponent("infoPanel").getSubComponent("videoType").getData();
		if(type == null) {
			UimsUtils.messageBoxInfo("请选择上传类型在进行选择");
			return;
		}
		
		UComponentI com = (UComponentI)this.component.getSubComponent("infoPanel")
				.getSubComponent("videoPlay");
		com.setData(path);
		UComponentI videoUrl = (UComponentI)this.component.getSubComponent("infoPanel")
				.getSubComponent("videoUrl");
		//videoUrl.setData(path);
		videoUrl.setText(path);
		this.componentToForm();
	}

	public void processListSelectionEvent(Object o, String cmd){
		ListSelectionEvent e = (ListSelectionEvent)o;
		UList uList = (UList)e.getSource();
		if(uList == null || uList.getSelectedValue() == null)
			return;
		ListOptionInfo info = (ListOptionInfo)uList.getSelectedValue();
		Integer videoId = new Integer(info.getValue());
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		AudioVideoInfoForm f = (AudioVideoInfoForm)sh.getForm();
		if(f.getId()!= null && f.getId().equals(videoId)) {
			return;
		}
		updateVideoInfoPanel(videoId);
		
	}
	
	public void doQuery(){
		List list = null;
		RmiRequest request= new RmiRequest();
		request.add(RmiKeyConstants.KEY_FORM,this.component.getSubComponent("queryPanel").getData());
		request.setCmd("videoGetAudioVideoInfoOptionListByQueryForm");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() == null) {
			list =  (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		UComponentI com = this.component.getSubComponent("videoList");
		FilterI f = com.getFilter();
		f.setAddedData(list);
		com.updateAddedDatas();
	}
	
	public void updateVideoInfoPanel(Integer videoId) {
		String[] data = new String[2];
		AudioVideoInfoForm f = null;
		if(videoId != null) {
			RmiRequest request= new RmiRequest();
			request.add("videoId",videoId);
			request.setCmd("videoGetAudioVideoInfoFormById");
			RmiResponse respond = UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg() == null) {
				f =  (AudioVideoInfoForm)respond.get(RmiKeyConstants.KEY_FORM);
			}
		}
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		if(f == null) {
			f = new AudioVideoInfoForm();
		}else {
			//path为本机地址，后期需更改成远程服务器的地址
			String path = "E:/ftpRoot/video/"+f.getVideoNum()+"/test.mp4";
			UComponentI com = this.component.getSubComponent("infoPanel").getSubComponent("videoPlay");
			
			data[0] = path;
			if(f.getVideoType().equals("1")) {
				data[1] = "1";
			}else if(f.getVideoType().equals("2")) {
				data[1]= "2";
			}
			com.setData(data);
			//com.setType(f.getVideoType());
		}
		sh.formToComponent(f);		
	}
	public void doNew(){
		AudioVideoInfoForm f = new AudioVideoInfoForm();
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		sh.formToComponent(f);			
	}
	public void doSave(){
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		sh.componentToForm();	
		AudioVideoInfoForm f= (AudioVideoInfoForm)sh.getForm();
		if(!f.getIsPossess().equals("已上传")) {
			UimsUtils.messageBoxInfo("请上传视频后再进行保存！");
			return;
		}
		String videoType = (String)this.component.getSubComponent("infoPanel").getSubComponent("videoType").getData();
		String title = (String)this.component.getSubComponent("infoPanel").getSubComponent("title").getData();
		if(title.equals("")) {
			UimsUtils.messageBoxInfo("视频标题不能为空，请输入标题后重新保存！");
			return;
		}
		RmiRequest request= new RmiRequest();
		request.add(RmiKeyConstants.KEY_FORM,sh.getForm());
		request.setCmd("videoSaveOrUpdateAudioVideoInfo");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() == null) {
			Integer videoId = (Integer)respond.get("videoId");
			if(videoId!= null) {
				f.setId(videoId);
				doQuery();
			}
			UimsUtils.messageBoxInfo("修改保存成功！");
		}
		else {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());			
		}
	}
	public void doDelete(){
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		AudioVideoInfoForm f= (AudioVideoInfoForm)sh.getForm();
		Integer videoId = f.getId();
		if(videoId == null) {
			UimsUtils.messageBoxInfo("请先选择好视频后再进行删除！");
			return;
		}
		int ret = UimsUtils.messageBoxChoice("你确定要删除："+f.getTitle()+"这个视频么？");
		if(ret != UimsUtils.YES_OPTION)
			return;
		RmiRequest request= new RmiRequest();
		request.add("videoId",videoId);
		request.setCmd("videoDeleteAudioVideoInfo");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() == null) {
			UimsUtils.messageBoxInfo("删除成功");
			doQuery();
			AudioVideoInfoForm form = new AudioVideoInfoForm();
			sh.formToComponent(form);
		}
		else {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());			
		}		
	}
	
	public void doUploadImage() {
		File file = GetFile.getOpenFile();
		if(file == null)
			return;
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		AudioVideoInfoForm f = (AudioVideoInfoForm)sh.getForm();
		Integer attachId = f.getAttachId();
		String videoNum = f.getVideoNum();
		if(videoNum == null || videoNum.equals("")) {
			UimsUtils.messageBoxInfo("视频编号为空，请核对信息后再做操作");
		    return;
		}
		Integer videoId = f.getId();
		byte data[] = null;
		try {
			BufferedImage img = ImageIO.read(new FileInputStream(file));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img,"jpg",out);
			out.flush();
			data = out.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(data == null)
			return;
		RmiRequest request = new RmiRequest();
		request.add("videoId", videoId);
		request.add("videoNum", videoNum);
		request.add("attachId", attachId);
		request.add("data", data);
		request.setCmd("videoUpLoadImageFile");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() != null) {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
		}else {
			Integer backAttachId = (Integer)respond.get("attachId");
			f.setAttachId(backAttachId);
			sh.formToComponent(f);
			UimsUtils.messageBoxInfo("照片上传成功！");
		}
	}
	/**
	 * @author XPT
	 * */
	public void doUploadVideo() {
		/*File file = GetFile.getOpenFile();
		String path = file.getAbsolutePath();
		File file1 = new File("E:\\workspace\\badmintonhot\\output.mp4");
		if(file1 == null)
			return;
		UComponentI com = this.component.getSubComponent("infoPanel").getSubComponent("videoPlay");
		com.setData(path);*/
		String path = (String)this.component.getSubComponent("infoPanel")
				.getSubComponent("videoUrl").getData();
		if(path == null || path.length() == 0) {
			UimsUtils.messageBoxInfo("请选择音频或视频文件后再进行上传！");
			return;
		}
		FFmpegFrameGrabber fg = new FFmpegFrameGrabber(path);
		try {
			fg.start();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    long Is = fg.getLengthInTime();
        try {
			fg.close();
			fg.release();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/*Encoder encoder = new Encoder();
		long time = 0;
		try {
			MultimediaInfo m = encoder.getInfo(file);
			long Is = m.getDuration();
			time = Is/1000;
		} catch (InputFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EncoderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
        File file = new File(path);
		if(file == null)
			return;
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		AudioVideoInfoForm f = (AudioVideoInfoForm)sh.getForm();
		Integer videoId = f.getId();
		Date createtime = f.getCreatetime();
		RmiRequest request1 = new RmiRequest();
		request1.add("createtime", createtime);
		request1.setCmd("audioVideoGetVideoNum");
		RmiResponse respond1 = UimsUtils.requestProcesser(request1);
		if(respond1.getErrorMsg() != null) {
			UimsUtils.messageBoxInfo(respond1.getErrorMsg());
			return;
		}
		String videoNum = (String)respond1.get("videoNum");
		if(videoNum == null || videoNum.length() == 0) {
			UimsUtils.messageBoxInfo("视频编号为空，请核对数据后再做操作！");
			return;
		}
		byte []data = new byte[(int) file.length()]; //用file的长度初始化一个byte型数组
		String size = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			FileChannel fc = fis.getChannel();
			BigDecimal fileSize = null;
			try {
				fileSize = new BigDecimal(fc.size());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			size = fileSize.divide(new BigDecimal(1048576),2,RoundingMode.HALF_UP)+"MB";
			try {
				fis.read(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UComponentI videoSize = this.component.getSubComponent("infoPanel").getSubComponent("size");
		videoSize.setText(size);
		Integer length = new Long(Is/1000).intValue();
		UComponentI videoLength = this.component.getSubComponent("infoPanel").getSubComponent("length");
		videoLength.setText(String.valueOf(length));
		if(data == null)
			return;
		RmiRequest request = new RmiRequest();
		/*request.add("videoId", videoId);*/
		request.add("videoNum",videoNum);
		/*request.add("size", size);
		request.add("time", Is/1000);*/
		request.add("data", data);
		request.setCmd("videoUploadAudioVideoFile");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() != null) {
			UimsUtils.messageBoxError(respond.getErrorMsg());
		}else {
			UimsUtils.messageBoxInfo("视频上传成功！");
			UComponentI com = this.component.getSubComponent("infoPanel").getSubComponent("isPossess");
			com.setText("已上传");
		}
		
	}

	
	
	
}
