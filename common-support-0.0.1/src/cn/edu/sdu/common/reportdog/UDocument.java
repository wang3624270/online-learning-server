package cn.edu.sdu.common.reportdog;

import java.util.HashMap;


import org.sdu.rmi.RmiRequest;

import cn.edu.sdu.service.itext_reportdog_ext.ReportContext;

public class UDocument {
	public String name;// 文档名
	public Object object;// 文档对象
	public HashMap paraMap = new HashMap();// 相关参数
	public ReportContext reportContext = new ReportContext();// 导出进行时的上下文，主要用于导出需要换页时的事件处理
	public RmiRequest request;
}
