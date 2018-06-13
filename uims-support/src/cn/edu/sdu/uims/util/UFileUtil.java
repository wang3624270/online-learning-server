package cn.edu.sdu.uims.util;

import java.io.File;

public class UFileUtil {
     private static String filePath = System.getProperty("java.io.tmpdir");
	//private static String filePath = "c:/temp/";
	public static String getNewTempFileName(Integer fileNo, String ext){
		String noString ="";
		fileNo++;
		if(fileNo >= 30000)
			fileNo =1;
		if(fileNo < 10){
			noString = "000"+fileNo;
		}else if(fileNo < 100){
			noString = "00"+fileNo;
		}else if(fileNo < 1000){
			noString = "0" +fileNo;
		}else {
			noString = ""+fileNo;
		}
		String name = filePath + ext + "\\"+ noString+"."+ ext;
		File file = new File(filePath+ext);
		if(!file.exists())
			file.mkdirs();
		return name;
	}

}
