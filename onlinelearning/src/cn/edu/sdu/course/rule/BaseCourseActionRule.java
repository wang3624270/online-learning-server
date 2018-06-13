package cn.edu.sdu.course.rule;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BaseCourseActionRule {

	 /**
	  * 改变图片尺寸
	  * @param srcFileName 源图片路径
	  * @param tagFileName 目的图片路径
	  * @param width 修改后的宽度
	  * @param height 修改后的高度
	  */
	 public static void zoomImage(String srcFileName,String tagFileName,int width,int height){  
		 try {
			 String rootpath="/data/ftproot/";//路径头
			 srcFileName=rootpath+srcFileName;
			 tagFileName=rootpath+tagFileName;
			 BufferedImage bi = ImageIO.read(new File(srcFileName));
			 BufferedImage tag=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
			 Graphics g = tag.getGraphics();
			 g.drawImage(bi, 0, 0, width, height, null);
			 File file2 = new File(tagFileName);
			 ImageIO.write(tag, "jpg", file2);
		  } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
}
