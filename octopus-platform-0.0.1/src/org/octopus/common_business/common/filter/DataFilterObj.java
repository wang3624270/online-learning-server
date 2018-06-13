package org.octopus.common_business.common.filter;

public interface DataFilterObj {
	/** 获取用于过滤关键值 ，要与过滤配置文件中的字段对应起来*/
	String getFilterKey();
}
