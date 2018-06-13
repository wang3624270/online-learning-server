package cn.edu.sdu.uims.base;

import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;

public interface UContentElementI extends UComponentI{
	public void setParameterData(HashMap map, UFormI form, UComponentI obj);
}
