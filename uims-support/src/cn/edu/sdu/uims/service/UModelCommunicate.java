package cn.edu.sdu.uims.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.sdu.db_service.DatabaseServiceUtils;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.spring_util.ApplicationContextHandle;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.flex.FHashMap;
import cn.edu.sdu.uims.frame.def.EnvironmentTemplate;
import cn.edu.sdu.uims.frame.def.UClientFrameTemplate;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.hcims.def.UserTaskTemplate;



public class UModelCommunicate  {

	public void getStrokeByName(RmiRequest request, RmiResponse respond) {
		String strokeName = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getStrokeByName(strokeName));
	}

	public void getParagraphTemplateByName(RmiRequest request,
			RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getParagraphTemplateByName(name));
	}

	public void getPaperTemplateByName(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getPaperTemplateByName(name));
	}
	public void getPromptTemplateByName(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getPromptTemplateByName(name));
	}

	public void getTypeValue(RmiRequest request, RmiResponse respond) {
		String str = (String) request.get(RmiKeyConstants.KEY_STRING);
		String key = (String) request.get(RmiKeyConstants.KEY_STRING1);
		respond.add(RmiKeyConstants.KEY_INTEGER, UFactory.getModelSession()
				.getTypeValue(str, key));
	}

	public void getPointCursorByName(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getPointCursorByName(name));
	}

	public void getColorByName(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getColorByName(name));
	}

	public void getFontByName(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getFontByName(name));
	}

	public void getBorderByName(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getBorderByName(name));
	}

	public void getFilterTemplateByName(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getFilterTemplateByName(name));
	}

	public void getDefaultClassByType(RmiRequest request, RmiResponse respond) {
		String type = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getDefaultClassByType(type));
	}
	public void getDefaultClassByTypeIntKey(RmiRequest request, RmiResponse respond) {
		String type = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_OBJECT, UFactory.getModelSession()
				.getDefaultClassByTypeIntKey(type));
	}
	public void getTemplateNameList(RmiRequest request,
			RmiResponse respond) {
		String key = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_FORMLIST, UFactory.getModelSession().getTemplateNameList(key));
	}

	public void getTemplate(RmiRequest request, RmiResponse respond) {
		String type = (String) request.get(RmiKeyConstants.KEY_STRING);
		String name = (String) request.get(RmiKeyConstants.KEY_STRING1);
		Object template = UFactory.getModelSession().getTemplate(type, name);
		respond.add(RmiKeyConstants.KEY_OBJECT, template);
	}
	public void getBsTemplate(RmiRequest request, RmiResponse respond) {
		String type = (String) request.get(RmiKeyConstants.KEY_STRING);
		String name = (String) request.get(RmiKeyConstants.KEY_STRING1);
		Object template = UFactory.getModelSession().getBsTemplate(type, name);
		respond.add(RmiKeyConstants.KEY_OBJECT, template);
	}

	public FHashMap flex_getTemplate(FHashMap map) {
		String type = (String) map.getData("type");
		String name = (String) map.getData("name");
		Object template = UFactory.getModelSession().getTemplate(type, name);
		map.putData("template", template);
		return map;
	}

	public void getClientFrameTemplate(RmiRequest request, RmiResponse respond) {
		String templateName = (String) request.get("templateName");
		UClientFrameTemplate template = (UClientFrameTemplate) UFactory
				.getModelSession().getTemplate(UConstants.MAPKKEY_CLIENTFRAME,
						templateName);
		respond.add(RmiKeyConstants.KEY_OBJECT, template);
	}

	public void getUserTaskMap(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		HashMap map = (HashMap) UFactory.getModelSession().getUserTaskMap(name);
		respond.add(RmiKeyConstants.KEY_OBJECT, map);
	}

	public void getGraphModel2DObject(RmiRequest request, RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		ModelProcessRule rule = (ModelProcessRule) ApplicationContextHandle
				.getBean("modelProcessRule");
		GraphModelI model = rule.getGraphModel2DObject(name);
		respond.add(RmiKeyConstants.KEY_OBJECT, model);
	}
	
	public void getModelTimestampFromServer(RmiRequest request,RmiResponse respond){
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		ModelProcessRule rule = (ModelProcessRule) ApplicationContextHandle
				.getBean("modelProcessRule");
		GraphModelI model = rule.getGraphModel2DObject(name);
		if(model!=null)
		respond.add(RmiKeyConstants.KEY_DATE, model.getTimestamp());
		else 
			respond.setErrorMsg("取不到模型");
	}

	// --------------

	public void getClientFrameTemplateByStream(RmiRequest request,
			RmiResponse respond) {
		String templateName = (String) request.get("templateName");
		UClientFrameTemplate template = (UClientFrameTemplate) UFactory
				.getModelSession().getTemplate(UConstants.MAPKKEY_CLIENTFRAME,
						templateName);
		ByteArrayOutputStream byteArrayOutputStrem = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteArrayOutputStrem);
		try {
			template.write(out);
			respond.add(RmiKeyConstants.KEY_OBJECT, byteArrayOutputStrem
					.toByteArray());
		} catch (Exception e) {
			return;
		}
	}

	public static void getTemplateByStream(RmiRequest request,
			RmiResponse respond) {
		String type = (String) request.get(RmiKeyConstants.KEY_STRING);
		String name = (String) request.get(RmiKeyConstants.KEY_STRING1);
		Object o = UFactory.getModelSession().getTemplate(type, name);
		if (o != null && o instanceof UTemplate) {
			UTemplate template = (UTemplate) o;
			ByteArrayOutputStream byteArrayOutputStrem = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(byteArrayOutputStrem);
			try {
				template.write(out);
				respond.add(RmiKeyConstants.KEY_STRING, template.getClass()
						.getName());
				respond.add(RmiKeyConstants.KEY_OBJECT, byteArrayOutputStrem
						.toByteArray());
			} catch (Exception e) {
				return;
			}
		}
	}

	public static void getUserTaskMapByStream(RmiRequest request,
			RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		HashMap map = (HashMap) UFactory.getModelSession().getUserTaskMap(name);
		ByteArrayOutputStream byteArrayOutputStrem = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteArrayOutputStrem);
		try {
			if (map == null)
				out.writeInt(0);
			else {
				String key;
				out.writeInt(map.size());
				UTemplate ts = new UTemplate();
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					key = (String) it.next();
					UserTaskTemplate temp = (UserTaskTemplate) map.get(key);
					ts.writeString(out, key);
					ts.writeTemplate(out, temp);
				}
			}
			respond.add(RmiKeyConstants.KEY_OBJECT, byteArrayOutputStrem
					.toByteArray());
		} catch (Exception e) {
			return;
		}
	}

	public void getGraphModel2DObjectByStream(RmiRequest request,
			RmiResponse respond) {
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		ModelProcessRule rule = (ModelProcessRule) ApplicationContextHandle
				.getBean("modelProcessRule");
		GraphModelI model = rule.getGraphModel2DObject(name);
		ByteArrayOutputStream byteArrayOutputStrem = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteArrayOutputStrem);
		try {
			model.write(out);
			respond.add(RmiKeyConstants.KEY_STRING, model.getClass().getName());
			respond.add(RmiKeyConstants.KEY_OBJECT, byteArrayOutputStrem
					.toByteArray());
		} catch (Exception e) {
			return;
		}
	}




	public void sendDisplayMessageToServer(RmiRequest request,
			RmiResponse respond) {
		String messageString = (String) request.get(RmiKeyConstants.KEY_STRING);
		System.out.println(messageString);
	}

	public void resetApplicationAllModels(RmiRequest request,
			RmiResponse respond) {
		ModelProcessRule rule = (ModelProcessRule) ApplicationContextHandle
		.getBean("modelProcessRule");
		String messageString = (String) request.get(RmiKeyConstants.KEY_STRING);
		System.out.println(messageString);
	}
	public void resetUimsAllModel(RmiRequest request,
			RmiResponse respond){
		UFactory.getModelSession().resetModels();
		respond.add(RmiKeyConstants.KEY_MESSAGE, "模板初始成功");
	}
	public void getEnvironmentTemplate(RmiRequest request, RmiResponse respond) {
		EnvironmentTemplate template = UFactory
				.getModelSession().getEnvironmentTemplate();
		respond.add(RmiKeyConstants.KEY_OBJECT, template);
	}
	
	public void getTimeControlActionListByPanelName(RmiRequest request, RmiResponse respond){
		String panelName = (String) request.get(RmiKeyConstants.KEY_STRING);
		List<String> roleList= (List<String>)request.get(RmiKeyConstants.KEY_ARRAY);
		List list = UFactory
		.getModelSession().getTimeControlActionListByPanelName(panelName, roleList);
		respond.add(RmiKeyConstants.KEY_FORMLIST, list);
		
	}
	public void getEnvironmentProperties(RmiRequest request, RmiResponse respond){
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_STRING, UFactory
				.getModelSession().getEnvironmentProperties(name));
		
	}
	public void getDictionaryMappingTemplateByName(RmiRequest request, RmiResponse respond){
		String name = (String) request.get(RmiKeyConstants.KEY_STRING);
		respond.add(RmiKeyConstants.KEY_FORM, UFactory
				.getModelSession().getDictionaryMappingTemplateByName(name));
		
	}

	public void reloadModleTemplate(RmiRequest request, RmiResponse respond) {
		UFactory
		.getModelSession().initApplicationModels();
	}

	public void getAppJarModifyTime(RmiRequest request, RmiResponse respond) {
		String appName = (String)request.get("appName");
		String sql;
		Date date = null;
		sql = "select c.modifyTime from app_client_info c, base_app_info a where a.appId = c.appId and a.appName = '" + appName + "'";
		List list = DatabaseServiceUtils.getInstantce().queryForArrayList("sdusystemms", sql);
		if(list != null && list.size() > 0) {
			Object [] oa = (Object[])list.get(0);
			if(oa != null && oa.length > 0) {
				respond.add("modifyTime", (Date)oa[0]);
			}
		}
	}

	public void doRefreshData() {
		// TODO Auto-generated method stub
		UFactory
		.getModelSession().initApplicationModels();
	}

}
