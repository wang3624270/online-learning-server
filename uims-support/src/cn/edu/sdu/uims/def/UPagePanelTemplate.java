package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.List;

public class UPagePanelTemplate extends UPanelTemplate{

	public String module = "" ;//所属模块
	public String pagename = "" ;
	public int componentNum = 0;
	public UPageElementTemplate [] componentTemplates = null;
	public String style = "";
	public String[] cssSrc = new String[0];//css样式连接文件
	public String[] javascriptSrc = new String[0];//js连接文件
	public String[] jspSrc = new String[0];//引用的jsp连接文件
	public List<String> validatorForm = new ArrayList<String>();//验证框架form的内容
	public String formShortName = "" ;//对应于struts里面formBean 的名字
	public String formActions = "" ;//form 内的所有事件类，存放格式为：actionType+target(这里是单数)+";",主要是为了生成界面时生成相应的span
	public String javascritpArea = "" ;//直接在 xml 里写的javaScript片段
	public String showFormElement = "false" ;//页面内的form元素是否要显示
	public String showTableStyle = "";
	public String childFunctionArea;//子页面内生成的javaScript 函数
	public boolean validate = false;
	public String fromPath = "";
	public String requestCmd = "" ;//请求命令
}
