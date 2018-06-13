package cn.edu.sdu.onlinelearning;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import cn.edu.sdu.uims.pi.ImageDataDriverI;
import cn.edu.sdu.uims.util.UimsUtils;

public class OnlinelearningLocalFactory {
	private HashMap dataMap = new HashMap();
	private BufferedImage defaultImg = null;
	public  ImageDataDriverI imageDataDriver  = new ImageDataDriverI(){
		
		public Object getImageData(Integer id) {
			// TODO Auto-generated method stub
			return getBufferedImageofAttach(id);
		}

		public void putImageDate(Integer id, Object obj) {
			// TODO Auto-generated method stub
			putBufferedImageofAttach(id, obj);
		}

		public boolean isExistImageData(Integer id) {
			// TODO Auto-generated method stub
			return isExistImageofAttach(id);
		}
		
	};

	private static OnlinelearningLocalFactory instance = new OnlinelearningLocalFactory();
	public static OnlinelearningLocalFactory getInstance(){
		return instance;
	}
	public void add(String key, Object obj) {
		dataMap.put(key,obj);
	}
	
	public Object get(String key){
		return dataMap.get(key);
	}
	public void getInitClientData(){
	
		HashMap<Integer, BufferedImage> imageMap = new HashMap<Integer, BufferedImage>();
		add(OnlinelearningConstants.ONLINELEARNING_CLIENT_DATA_IMAGE_MAP, imageMap);
		try{
			URL url = Thread.currentThread().getContextClassLoader().getResource(
					"default.png");
			defaultImg = ImageIO.read(url);
		}catch(Exception e){
			defaultImg = null;
		}
	}		
	public BufferedImage getBufferedImageofAttach(Integer attachId){
		if(attachId == null)
			return defaultImg;
		BufferedImage img = UimsUtils.getImageBufferOfAttachFile(attachId);
		if(img == null)
			img = defaultImg;
            return img;
	}
	public boolean isExistImageofAttach(Integer attachId){
		if(attachId == null)
			return false;
		HashMap<Integer, BufferedImage> imageMap = (HashMap<Integer, BufferedImage>)dataMap.get(OnlinelearningConstants.ONLINELEARNING_CLIENT_DATA_IMAGE_MAP);
		BufferedImage img = imageMap.get(attachId);
		if(img == null) {
			return false;
		}else
			return true;
	}

	public void putBufferedImageofAttach(Integer attachId, Object o){
		if(attachId == null)
			return;
		if(!(o instanceof BufferedImage)) {
			return ;
		}
		HashMap<Integer, BufferedImage> imageMap = (HashMap<Integer, BufferedImage>)dataMap.get(OnlinelearningConstants.ONLINELEARNING_CLIENT_DATA_IMAGE_MAP);
		imageMap.put(attachId, (BufferedImage)o);
	}
	
	public ImageDataDriverI getImageDataDriver(){
		return this.imageDataDriver;
	}


}
