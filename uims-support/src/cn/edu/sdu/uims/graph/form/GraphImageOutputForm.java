package cn.edu.sdu.uims.graph.form;

import java.awt.image.BufferedImage;

public class GraphImageOutputForm  extends GraphPrintForm{
	
	private BufferedImage photo;
	private String perNum;
	private String perName;
	private BufferedImage photo1;
	
	private String filePath;
	public BufferedImage getPhoto() {
		return photo;
	}
	public BufferedImage getPhoto(Integer index) {
		return photo;
	}
	public BufferedImage getPhoto1(Integer index) {
		return photo1;
	}

	public void setPhoto(BufferedImage photo) {
		this.photo = photo;
	}
	public String getPerNum() {
		return perNum;
	}
	public void setPerNum(String perNum) {
		this.perNum = perNum;
	}
	public String getPerName() {
		return perName;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getOutputFileName(){
		return filePath;
	}
	public BufferedImage getPhoto1() {
		return photo1;
	}
	public void setPhoto1(BufferedImage photo1) {
		this.photo1 = photo1;
	}
	

}
