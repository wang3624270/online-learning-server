package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class GraphTransForm extends UForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Float xpoint;
	private Float ypoint;
	private Float sx;
	private Float sy;
	private Float ra;
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Float getYpoint() {
		return ypoint;
	}
	public void setYpoint(Float ypoint) {
		this.ypoint = ypoint;
	}
	public Float getSx() {
		return sx;
	}
	public void setSx(Float sx) {
		this.sx = sx;
	}
	public Float getSy() {
		return sy;
	}
	public void setSy(Float sy) {
		this.sy = sy;
	}
	public Float getRa() {
		return ra;
	}
	public void setRa(Float ra) {
		this.ra = ra;
	}
	public Float getXpoint() {
		return xpoint;
	}
	public void setXpoint(Float xpoint) {
		this.xpoint = xpoint;
	}
	
}
