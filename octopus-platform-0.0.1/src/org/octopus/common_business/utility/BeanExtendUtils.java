package org.octopus.common_business.utility;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class BeanExtendUtils {

	public static Object setValueOfObject(final Object obj,
			final String propertyName, final Object value) {
		Method writeMethod;
		PropertyUtilsBean utilsbean = new PropertyUtilsBean();
		PropertyDescriptor descriptor;
		try {
			descriptor = utilsbean.getPropertyDescriptor(obj, propertyName);
			writeMethod = descriptor.getWriteMethod();
			MethodUtils.getAccessibleMethod(writeMethod).invoke(obj, value);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public static Object getValueFromObject(final Object obj,
			final String propertyName) {
		Object value = null;
		Method readMethod;
		try {
			PropertyUtilsBean utilsbean = new PropertyUtilsBean();
			if (obj != null) {
				PropertyDescriptor descriptor = utilsbean
						.getPropertyDescriptor(obj, propertyName);
				readMethod = descriptor.getReadMethod();
				value = MethodUtils.getAccessibleMethod(readMethod).invoke(obj,
						new Object[0]);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 复制属性
	 * 
	 * @param aimObj
	 *            目标对象
	 * @param srcobj
	 *            源对象
	 * @param isnull
	 *            是否复制为null的对象
	 * @return
	 */
	public static Object copyProperties(final Object aimObj,
			final Object srcobj, boolean isnull) {
		if (srcobj == null) {
			return aimObj;
		}

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(srcobj.getClass());
			PropertyDescriptor[] origDescriptors = beanInfo
					.getPropertyDescriptors();
			PropertyUtilsBean utilsbean = new PropertyUtilsBean();
			String name = null;
			Object value = null;
			PropertyDescriptor aimproperty = null;
			Method readMethod = null;
			Method writeMethod = null;
			for (int i = 0; i < origDescriptors.length; i++) {
				name = origDescriptors[i].getName();

				if (utilsbean.isReadable(srcobj, name)) {
					readMethod = origDescriptors[i].getReadMethod();
					if ((readMethod == null)
							&& (origDescriptors[i] instanceof IndexedPropertyDescriptor)) {
						readMethod = ((IndexedPropertyDescriptor) origDescriptors[i])
								.getIndexedReadMethod();
					}

					if (readMethod.getParameterTypes().length > 0) {
						continue;
					}
					// 源对象属性值
					value = MethodUtils.getAccessibleMethod(readMethod).invoke(
							srcobj, new Object[0]);
					aimproperty = utilsbean.getPropertyDescriptor(aimObj, name);
					if (aimproperty != null
							&& aimproperty.getPropertyType() == origDescriptors[i]
									.getPropertyType()) {
						if (utilsbean.isWriteable(aimObj, name)) {
							writeMethod = aimproperty.getWriteMethod();
							if ((writeMethod == null)
									&& (aimproperty instanceof IndexedPropertyDescriptor)) {
								writeMethod = ((IndexedPropertyDescriptor) aimproperty)
										.getIndexedWriteMethod();
							}
							if (isnull == true) {
								MethodUtils.getAccessibleMethod(writeMethod)
										.invoke(aimObj, value);
							} else {
								if (null != value) {
									MethodUtils
											.getAccessibleMethod(writeMethod)
											.invoke(aimObj, value);
								}
							}

						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return aimObj;
	}

	/**
	 * 复制树型数据结构bean bean条件：除了parent和child为需要递归复制对象外其他属性为基本数据类型，或只复制引用
	 * child节点集合为Set
	 * 
	 * @param target
	 *            目标对象
	 * @param source
	 *            源对象
	 * @param parent
	 *            bean的父节点属性名
	 * @param childs
	 *            bean的子节点集合属性名
	 */
	@SuppressWarnings("unchecked")
	public static void copyTreeObject(final Object target, final Object source,
			String parent, String childs) {

		if (null == source) {
			return;
		}

		try {
			Class childstype = PropertyUtils.getProperty(target, childs)
					.getClass();

			copyProperties(target, source, true);

			PropertyUtils.setProperty(target, childs, childstype.newInstance());
			Set set = (Set) PropertyUtils.getProperty(source, childs);

			Set targetset = (Set) PropertyUtils.getProperty(target, childs);
			if (!set.isEmpty()) {
				Iterator ite = set.iterator();

				Object sourcetemp = null;
				Object targettemp = null;
				while (ite.hasNext()) {
					sourcetemp = ite.next();
					targettemp = target.getClass().newInstance();

					if (parent != null) {
						PropertyUtils.setProperty(targettemp, parent, target);
					}
					targetset.add(targettemp);
					copyTreeObject(targettemp, sourcetemp, parent, childs);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

}