package org.octopus.common_business.attachment.util;

import java.io.InputStream;
import java.util.HashMap;

import org.octopus.common_business.attachment.rule.BaseAttachmentInfoProcessRule;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.spring_util.ApplicationContextHandle;

public class BaseAttachmentInfoProcessUtil {
	static private int MAXNUMBER = 20000;
	static private int no = 0;
	static private HashMap map = new HashMap();
	public static FileProcessObject  getNewInstance(){
		FileProcessObject obj = new FileProcessObject(no); 
		map.put(obj.getKey(),obj);
		no++;
		if(no >MAXNUMBER)
			no = 0;
		return obj;
	}
	public static FileProcessObject getFileProcessObject(String key){
		return (FileProcessObject)map.get(key);
	}
	public static void remove(String key){
		map.remove(key);
	}
	public  static Integer upLoadAttachmentFile(Integer personId, String attachType, Integer ownerId,String fileName, String folderName, InputStream in){
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule)ApplicationContextHandle.getBean("baseAttachmentInfoProcessRule");
		if(rule == null)
			return null;
		return rule.upLoadAttachmentFile(personId, attachType, ownerId, fileName, folderName, in);
	}
	public  static Integer upLoadAttachmentFileToWWW(Integer personId, String attachType, Integer ownerId,String fileName, String folderName, InputStream in){
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule)ApplicationContextHandle.getBean("baseAttachmentInfoProcessRule");
		if(rule == null)
			return null;
		return rule.upLoadAttachmentFileToWWW(personId, attachType, ownerId, fileName, folderName, in);
	}
	public  static  Object[] getDownloadAttachmentFile(Integer attachId){
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule)ApplicationContextHandle.getBean("baseAttachmentInfoProcessRule");
		if(rule == null)
			return null;
		return rule.getDownloadAttachmentFile(attachId);
	}
	
	public  static  Object[] getDownloadAttachmentFileFromWWW(Integer attachId){
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule)ApplicationContextHandle.getBean("baseAttachmentInfoProcessRule");
		if(rule == null)
			return null;
		return rule.getDownloadAttachmentFileFromWWW(attachId);
	}
	public  static  byte[] getFileDataOfBaseAttachmentInfoFromWWW(String urlAdress){
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule)ApplicationContextHandle.getBean("baseAttachmentInfoProcessRule");
		if(rule == null)
			return null;
		return rule.getFileDataOfBaseAttachmentInfoFromWWW(urlAdress);
	}
	
	
	
	public static void deleteAttachmentFile(Integer attachId){
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule)ApplicationContextHandle.getBean("baseAttachmentInfoProcessRule");
		if(rule == null)
			return;
		rule.deleteAttachmentFile(attachId);
	}
	
	public static void deleteAttachmentFileFromWWW(Integer attachId){
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule)ApplicationContextHandle.getBean("baseAttachmentInfoProcessRule");
		if(rule == null)
			return;
		rule.deleteAttachmentFileFromWWW(attachId);
	}
	
	public static void downloadAttachmentBSFile(RmiRequest request,
		RmiResponse response) {
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule)ApplicationContextHandle.getBean("baseAttachmentInfoProcessRule");
		if(rule == null)
			return;
		try {
			rule.downloadAttachmentBSFile(request,response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
}
