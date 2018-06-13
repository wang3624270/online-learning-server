package org.octopus.common_business.town.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.octopus.common_business.town.dao.BaseProTownDao;
import org.octopus.common_business.town.model.BaseProTown;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.sdu.common.form.ListOptionInfo;
@Repository
public class BaseProTownDaoImpl extends GenericServiceImpl<BaseProTown>
		implements BaseProTownDao {

	public BaseProTownDaoImpl() {
		super(BaseProTown.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 张慧， 得到所有的PO的List
	 */
	public List getBaseProTownList() {
		String hql = "from BaseProTown town";
		List list = this.queryForList(hql, null);
		return list;
	}

	/**
	 * 张慧， 保存特定的PO
	 */
	@Transactional
	public void saveBaseProTown(BaseProTown town) {
		this.create(town);
	}

	/***************************************************************************
	 * 张慧，数据迁移专用 start
	 */
	/**
	 * 张慧，保存List中所有合法PO
	 */
	@Transactional
	public void saveBaseProTownList(List list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				BaseProTown town = (BaseProTown) list.get(i);
				this.create(town);
			}
		}
	}

	/**
	 * 张慧，保存指定的PO，返回新Id
	 */
	@Transactional
	public int saveTransferBaseProTown(BaseProTown town) {
		this.create(town);
		return town.getTownId();
	}

	/***************************************************************************
	 * 张慧，数据迁移专用 end
	 */

	public BaseProTown getBaseProTownById(Integer archivesId) {
		// TODO Auto-generated method stub
		return this.query(archivesId);
	}

	@Override
	public void updateBaseProTown(BaseProTown gradmsTown) {
		// TODO Auto-generated method stub
		this.update(gradmsTown);
	}

	@Override
	public String getDegreePlaceById(Integer baseProTownByNativePlaceId) {
		// TODO Auto-generated method stub
		String hql = "select degreePlace from BaseProTown town where town.townId="
				+ baseProTownByNativePlaceId;
		String degreePlace = (String) this.queryForObject(hql, null);
		return degreePlace;
	}

	@Override
	public String getNativePlaceById(Integer baseProTownByNativePlaceId) {
		// TODO Auto-generated method stub
		String hql = "from BaseProTown town where town.townId="
				+ baseProTownByNativePlaceId;
		BaseProTown town = (BaseProTown) this.queryForObject(hql, null);
		if (town != null) {
			return town.getProvinceName() + town.getCityName()
					+ town.getTownName();
		} else {
			return null;
		}
	}

	@Override
	public String getNativeCityById(Integer baseProTownByNativePlaceId) {
		// TODO Auto-generated method stub
		String hql = "from BaseProTown town where town.townId="
				+ baseProTownByNativePlaceId;
		BaseProTown town = (BaseProTown) this.queryForObject(hql, null);
		if (town != null) {
			return town.getCityName();
		} else {
			return null;
		}
	}

	@Override
	public String getNativeProvinceById(Integer baseProTownByNativePlaceId) {
		// TODO Auto-generated method stub
		String hql = "from BaseProTown town where town.townId="
				+ baseProTownByNativePlaceId;
		BaseProTown town = (BaseProTown) this.queryForObject(hql, null);
		if (town != null) {
			return town.getProvinceName();
		} else {
			return null;
		}
	}

	@Override
	public String getNativeTownById(Integer baseProTownByNativePlaceId) {
		// TODO Auto-generated method stub
		String hql = "from BaseProTown town where town.townId="
				+ baseProTownByNativePlaceId;
		BaseProTown town = (BaseProTown) this.queryForObject(hql, null);
		if (town != null) {
			return town.getTownName();
		} else {
			return null;
		}
	}

	@Override
	public List getProvinceList() {
		// TODO Auto-generated method stub
		String hql = "select distinct t.provinceName from BaseProTown t" ;
		
		List list = this.queryForList(hql, null);

		List result = new ArrayList();

		ListOptionInfo option = null;
		String objs = "";
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				objs = (String) list.get(i);

				option = new ListOptionInfo();
				option.setValue(objs);
				option.setLabel(objs);
				result.add(option);
			}
		}
		return result;
	}
	@Override
	public HashMap<String, String> getProvinceCodeNameMap() {
		// TODO Auto-generated method stub
		String hql = "select distinct t.provinceName, t.provinceCode from BaseProTown t" ;
		
		List list = this.queryForList(hql, null);

		HashMap<String,String> map= new HashMap<String,String>();

		Object a[];
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				a = (Object[]) list.get(i);
				map.put((String)a[0],(String)a[1]);
				map.put((String)a[1],(String)a[0]);
			}
		}
		return map;
	}
	
	@Override
	public List getProvinceListOptionInfoList() {
		// TODO Auto-generated method stub
		String hql = "select distinct t.provinceCode, t.provinceName from BaseProTown t" ;
		
		List list = this.queryForList(hql, null);

		List result = new ArrayList();

		ListOptionInfo option = null;
		Object objs[];
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				objs = (Object[]) list.get(i);

				option = new ListOptionInfo();
				option.setValue((String)objs[0]);
				option.setLabel((String)objs[1]);
				result.add(option);
			}
		}
		return result;
	}

	
	@Override
	public List getCityListByPro(String proName) {
		// TODO Auto-generated method stub
		String hql = "select distinct t.cityName from BaseProTown t where t.provinceName = '"
						+ proName + "'";
		List list = this.queryForList(hql, null);
		List result = new ArrayList();
		ListOptionInfo option = null;
		String objs = "";
		for (int i = 0; i < list.size(); i++) {
			objs = (String) list.get(i);
			option = new ListOptionInfo();
			option.setValue(objs);
			option.setLabel(objs);
			result.add(option);
		}
		return result;
	}


	/**
	 * 根据省名称和市名称得到县列表
	 */
	@Override
	public List getTownListByProCity(String proName, String cityName) {
		// TODO Auto-generated method stub
		String hql = "select distinct t.townName from BaseProTown t where t.provinceName='"
				+ proName + "'and t.cityName='" + cityName + "'";
		List list = this.queryForList(hql, null);
		List result = new ArrayList();
		ListOptionInfo option = null;
		String objs = "";
		for (int i = 0; i < list.size(); i++) {
			objs = (String) list.get(i);
			option = new ListOptionInfo();
			option.setValue(objs);
			option.setLabel(objs);
			result.add(option);
		}
		return result;
	}

	public BaseProTown getTownByRegionalismCodeByType(String RegionalismCode) {
		// TODO Auto-generated method stub
		if(RegionalismCode==null||RegionalismCode.trim().equals("")){
			return null;
		}
		
		BaseProTown town = new BaseProTown();
		BaseProTown  querytown=null; 
		String hql="from BaseProTown b where b.regionalismCode='"+ RegionalismCode + "'";
		List list = this.queryForList(hql, null);
		if (list != null && list.size() > 0) {
			querytown = (BaseProTown) list.get(0);
			try {
				BeanUtils.copyProperties(town, querytown);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			hql="from BaseProTown b where b.regionalismCode like '"+ RegionalismCode.substring(0,4) + "%'";
			list = this.queryForList(hql, null);
			if (list != null && list.size() > 0) {
				querytown = (BaseProTown) list.get(0);
				try {
					BeanUtils.copyProperties(town, querytown);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				town.setTownName(null);
		}else{
			hql="from BaseProTown b where b.regionalismCode like '"+ RegionalismCode.substring(0,2) + "%'";
			
		    list = this.queryForList(hql, null);
			if (list != null && list.size() > 0) {
				querytown = (BaseProTown) list.get(0);
				try {
					BeanUtils.copyProperties(town, querytown);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				town.setTownName(null);
				town.setCityName(null);
			}
		}
		}
		return town;
	}

	@Override
	public BaseProTown getTownByTownName(String provinceName, String cityName,
			String townName) {
		// TODO Auto-generated method stub
		String hql = "from BaseProTown bpt where bpt.provinceName='"+provinceName+"'";
		if(cityName!=null&&!cityName.trim().equals("")){
			hql += " and bpt.cityName='"+cityName+"'";
		}
		if(townName!=null&&!townName.trim().equals("")){
			hql += " and bpt.townName='"+townName+"'";
		}
		
		hql += "order by regionalismCode asc";
		
		List list = this.queryForList(hql, null);
		if(list==null||list.size()==0)
			return null;
		else
			return (BaseProTown)list.get(0);
	}

	@Override
	public String getIsPoorByCondition(String pro,String city, String town) {
		// TODO Auto-generated method stub
		BaseProTown bt=null;
		String hql="";
		if(town!=null&&!town.equals("")){
			 hql = "from BaseProTown bpt where bpt.provinceName='"+pro+"' and bpt.cityName='"+city+"' and bpt.townName='"+town+"'";
		}else{
			 hql = "from BaseProTown bpt where bpt.provinceName='"+pro+"' and bpt.cityName='"+city+"'";
		}
		List list = this.queryForList(hql, null);
		if(list==null||list.size()==0){
			return null;
		}else if(list.size()==1){ 
				bt = (BaseProTown)list.get(0);
				return bt.getIsPoor();
			}
		return null;
			
	}
	
	public List findRoadListByCode(String code){
		String hql = "select dictCode,dictName from DictTown t where t.dictCode like '"+code+"%'";
//		this.getDBsession().createQuery();
		List list = this.queryForList(hql, null);

		if(list==null||list.size()==0)
			return null;
		
		List result = new ArrayList();
		ListOptionInfo option = null;
		Object[] objs = null;

		try{
			for (int i = 0; i < list.size(); i++) {
				objs = (Object[]) list.get(i);
	
				option = new ListOptionInfo();
				option.setValue(objs[0].toString());
				option.setLabel(objs[1].toString());
	
				result.add(option);
			}
		}catch(Exception e){}

		return result;
	}
	
	public List findDictTownByCode(String code){
		String hql = "from DictTown t where t.dictCode like '"+code+"%'";

		return this.queryForList(hql, null);
	}

	/**
	 * 根据省代码获得省
	 */
	public BaseProTown getProvinceByCode(String proCode) {
		String hql = "from BaseProTown t where t.provinceCode='" + proCode
				+ "'";
		List list = this.queryForList(hql, null);
		BaseProTown proTown = null;
		if (list.size() > 0) {
			proTown = (BaseProTown) list.get(0);
		}
		return proTown;
	}
	
	/**
	 * 根据省市获得籍贯
	 * @param province
	 * @param city
	 * @return
	 */
	public String getNativePlaceByProCity(String province, String city) {
		String hql = "select pt.degreePlace from BaseProTown pt where pt.provinceName='"
				+ province + "' and pt.cityName='" + city + "'";
		List list = this.queryForList(hql, null);
		String place = "";
		if (list!=null && list.size() > 0) {
			place = (String) list.get(0);
		}
		return place;
	}

	@Override
	public String getPerRegionalismName(String province, String city,
			String town) {
		String hql = "select pt.regionalismName from BaseProTown pt where pt.provinceName='"
				+ province
				+ "' and pt.cityName='"
				+ city
				+ "' and pt.townName='" + town + "'";
		List list = this.queryForList(hql, null);
		String place = "";
		if (list.size() > 0) {
			place = (String) list.get(0);
		}
		return place;
	}
	
	@Override
	public List getTownListByTownName(String provinceName, String cityName,String townName) {
		// TODO Auto-generated method stub
		String hql = "from BaseProTown bpt where bpt.provinceName='"+provinceName+"'";
		if(cityName!=null&&!cityName.trim().equals("")){
			hql += " and bpt.cityName='"+cityName+"'";
		}
		if(townName!=null&&!townName.trim().equals("")){
			hql += " and bpt.townName='"+townName+"'";
		}
		
		hql += "order by regionalismCode asc";
		return this.queryForList(hql, null);
	}

	public String getNativePlace(String province, String city, String town) {
		String hql = "select pt.degreePlace from BaseProTown pt where pt.provinceName='"
				+ province
				+ "' and pt.cityName='"
				+ city
				+ "' and pt.townName='" + town + "'";
		List list = this.queryForList(hql, null);
		String place = "";
		if (list.size() > 0) {
			place = (String) list.get(0);
		}
		return place;
	}
	
	public String getCityNameByProTown(String provinceName,String townName){
		String hql = "select pt.cityName from BaseProTown pt where pt.provinceName='"
				+ provinceName + "' and pt.townName='" + townName + "'";
		List list = this.queryForList(hql, null);
		String place = "";
		if (list.size() > 0) {
			place = (String) list.get(0);
		}
		return place;
	}
}
