package cn.edu.sdu.uims.util;

import cn.edu.sdu.uims.service.UFactory;

public class UDataMapping {
	public static String getDictionaryMapValue(String mappingName, String dictName, String value){
		return UFactory.getModelSession().getDictionaryMappingMapVlue(mappingName, dictName, value);
	}
}
