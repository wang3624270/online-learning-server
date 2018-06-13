package cn.edu.sdu.onlinelearning;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.octopus.common_business.data_dictionary.ClientDataDictionary;
import org.octopus.common_business.news.form.NewsInfoForm;
import org.sdu.rmi.RmiRequest;

import cn.edu.sdu.common.form.UFormIdI;
import cn.edu.sdu.common.pi.ClientInitPlugInI;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.form.UTextFieldDataFormI;
import cn.edu.sdu.uims.frame.UClientMain;
import cn.edu.sdu.uims.pi.ImageDataDriverI;

public class OnlinelearningClientMain extends UClientMain{
	protected Logger logger = Logger.getLogger(OnlinelearningClientMain.class);

	public static void main(String[] args) {
		URL url = Thread.currentThread().getContextClassLoader().getResource("clientlog4j.properties");
		PropertyConfigurator.configure(url);
		UClientMain clientMain = new OnlinelearningClientMain();
		clientMain.setRequestIsLog(true);
		Logger l = clientMain.getLogger();
		try {
			clientMain.setModelIsLocal(true);
			clientMain
			.setEnvironmentName("onlinelearning/onlinelearning-environmenttemplate.xml");
			clientMain.setStartupProcessMessage("应用程序窗口初始化，请稍等...");
			clientMain.runApp(null); 
		}catch(Exception e) {
			if (l != null) {
				l.error(getTrace(e));
			}

		}
	}
	public void addUserSystemPlugIn(){
	}
	public void addObjectSystemRequestAttribute(RmiRequest req){
	}
	public  ImageDataDriverI getImgeDataDriver(){
		return null;
	}
	public void userPromptBeforeStartFrame(){
	}
	public Logger getLogger() {
		return logger;
	}
	public ClientInitPlugInI getClientDataDictionaryPlugIn(){
		UimsFactory.setClientDataDictionaryI(ClientDataDictionary.getInstance());
		return ClientDataDictionary.getInstance();
	}
	public UTextFieldDataFormI creatUTextFieldDataForm() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getClientVersion(){
		return "1.0";
	}
	
	@Override
	public UFormIdI creatNewsInfoForm() {
		// TODO Auto-generated method stub
		return new NewsInfoForm();
	}
}
