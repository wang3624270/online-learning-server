package cn.edu.sdu.uims.frame.def;

import java.io.Serializable;

public class RoleFrameTypeTemplate implements Serializable {
	public String url;
	public String enUrl;
	public String responseType;
	public String responseTypeEn;
	public String menuType;
	public String beanName;
	public String systemNotifyType;
	public RoleFrameTypeTemplate(String url){
		this.url = url;
	}
}
