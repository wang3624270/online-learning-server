package org.sdu.spring_util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
public class ApplicationContextHandle implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	private static String springMode = "server";

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHandle.applicationContext = applicationContext;

		if (applicationContext instanceof WebApplicationContext) {
			springMode = "server";
		} else
			springMode = "client";
	}

	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
		if (springMode.equals("server"))
			return ((WebApplicationContext) applicationContext).getBean(name, clazz);
		else
			return ((ClassPathXmlApplicationContext) applicationContext).getBean(name, clazz);
	}

	public static String getApplicationName() throws BeansException {
		return applicationContext.getApplicationName();
	}

}