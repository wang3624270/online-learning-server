package org.octopus.common_business.town.dao;

import java.util.HashMap;
import java.util.List;

import org.octopus.common_business.town.model.BaseProTown;
import org.octopus.spring_utils.jpa.GenericServiceI;

public interface BaseProTownDao extends GenericServiceI<BaseProTown> {

	public List getBaseProTownList();

	public void saveBaseProTownList(List list);

	public void saveBaseProTown(BaseProTown town);

	public int saveTransferBaseProTown(BaseProTown town);

	public BaseProTown getBaseProTownById(Integer archivesId);
	
	public String getCityNameByProTown(String provinceName,String townName);
	
	/*
	 * 获取城市编码获取城市信息-zjc
	 */
	public BaseProTown getTownByRegionalismCodeByType(String RegionalismCode);
	public BaseProTown getTownByTownName(String provinceName, String cityName, String townName);
	
	public void updateBaseProTown(BaseProTown gradmsTown);

	public String getDegreePlaceById(Integer baseProTownByNativePlaceId);

	public String getNativePlaceById(Integer baseProTownByNativePlaceId);

	public String getNativeProvinceById(Integer baseProTownByNativePlaceId);

	public String getNativeCityById(Integer baseProTownByNativePlaceId);

	public String getNativeTownById(Integer baseProTownByNativePlaceId);

	public List getProvinceList();

	public List getCityListByPro(String proName);

	public List getTownListByProCity(String proName, String cityName);
	

	public String getIsPoorByCondition(String pro, String town, String city);

	public List findRoadListByCode(String code);
	
	public List findDictTownByCode(String code);
	
	public BaseProTown getProvinceByCode(String proCode);
	
	public String getNativePlaceByProCity(String province, String city);
	
	public String getPerRegionalismName(String province, String city,String town);
	
	public List getTownListByTownName(String provinceName, String cityName,String townName);
	
	public String getNativePlace(String province, String city, String town);
	public List getProvinceListOptionInfoList();
	public HashMap<String, String> getProvinceCodeNameMap();
	
}
