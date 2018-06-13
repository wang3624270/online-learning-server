package cn.edu.sdu.uims.filter;

import java.util.List;

public interface MultiLevelFilterI extends FilterI {
	 List getAddedDataList(Object [] code, Integer pos);
}
