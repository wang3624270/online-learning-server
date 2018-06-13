package cn.edu.sdu.commoncomponent.handler;

import java.util.List;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.commoncomponent.form.CommonBaseDataQueryForm;
import cn.edu.sdu.uims.handler.impl.UDialogHandler;
import cn.edu.sdu.uims.util.UimsUtils;

public class ClassCommonQueryHandler extends UDialogHandler{
	public  List getQueryClassInfoList(){
		CommonBaseDataQueryForm qForm = (CommonBaseDataQueryForm)this.component.getSubComponent("queryPanel").getData();
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add(RmiKeyConstants.KEY_FORM, qForm);
		request.setCmd("commonBaseDataQueryRequestClassInfoList");
		respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() == null) {
			return (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		} else {
			return null;
		}
	}

}
