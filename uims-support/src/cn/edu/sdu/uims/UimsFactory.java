package cn.edu.sdu.uims;

import java.util.HashMap;

import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.common.pi.SysConfigInfoProcessorI;
import cn.edu.sdu.uims.pi.ClientMainI;
import cn.edu.sdu.uims.pi.ImageDataDriverI;

public class UimsFactory {
	private static ClientMainI clientMainI;
	private static ClientDataDictionaryI clientDataDictionaryI;
	private static SysConfigInfoProcessorI  sysConfigInfoProcessorI; 	
	private static HashMap<String, Object> systemData = new HashMap<String, Object>(); 
	public static ImageDataDriverI getImageDataDriverI() {
		if(clientMainI == null)
			return null;
		else
		return clientMainI.getImgeDataDriver();
	}
	public static void setClientMainI(ClientMainI clientMainI1){
		clientMainI = clientMainI1;
	}
	public static ClientMainI getClientMainI(){
		return clientMainI;
	}
	public static void setClientDataDictionaryI(ClientDataDictionaryI ci){
		clientDataDictionaryI = ci;
	}
	public static ClientDataDictionaryI getClientDataDictionaryI(){
		return clientDataDictionaryI;
	}
		
	public static SysConfigInfoProcessorI getSysConfigInfoProcessorI() {
		return sysConfigInfoProcessorI;
	}
	public static void setSysConfigInfoProcessorI(SysConfigInfoProcessorI sysConfigInfoProcessorI) {
		UimsFactory.sysConfigInfoProcessorI = sysConfigInfoProcessorI;
	}
	public static void addSystemData(String key, Object data){
		systemData.put(key, data);
	}
	public static  Object getSystemData(String key){
		return systemData.get(key);
	}
}
