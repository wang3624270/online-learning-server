package org.octopus.common_business.data_dictionary.jpa_dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.octopus.common_business.data_dictionary.form.DataDictionary;
import org.octopus.common_business.data_dictionary.form.DataDictionaryForm;
import org.octopus.common_business.data_dictionary.jpa_dao.BaseDataDictionaryDao;
import org.octopus.common_business.data_dictionary.jpa_model.BaseDataDictionary;
import org.octopus.common_business.utility.BeanExtendUtils;
import org.octopus.spring_utils.jpa.GenericServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.sdu.common.form.ListOptionInfo;

@Repository
public class BaseDataDictionaryDaoImpl extends GenericServiceImpl<BaseDataDictionary> implements BaseDataDictionaryDao {

	public BaseDataDictionaryDaoImpl() {
		super(BaseDataDictionary.class);
		// TODO Auto-generated constructor stub
	}

	public HashMap<String, DataDictionaryForm> getBaseDataDictionary() {
		HashMap<String, DataDictionaryForm> map = new HashMap<String, DataDictionaryForm>();
		String select = "from BaseDataDictionary dic where  dic.dataItemNum='root'";
		BaseDataDictionary dic = (BaseDataDictionary) this.queryForObject(
				select, null);
		DataDictionaryForm form = new DataDictionaryForm();
		form.setChildMap(this.getChildsByFarherId(dic.getDataItemId()));
		form.setDataItem(dic.getDataItem());
		form.setDataItemName(dic.getDataItemName());
		form.setDataItemEngName(dic.getDataItemEngName());
		form.setDataItemNum(dic.getDataItemNum());
		form.setIsLeaf(dic.getIsLeaf());
		form.setIsVisual(dic.getIsVisual());
		form.setOrderNum(dic.getOrderNum());
		form.setRemark(dic.getRemark());
		map.put("root", form);
		return map;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, DataDictionaryForm> getChildsByFarherId(
			Integer dataItemId) {
		// TODO Auto-generated method stub
		HashMap<String, DataDictionaryForm> map = new HashMap<String, DataDictionaryForm>();
		String select = "from BaseDataDictionary dic where (dic.isVisual is null or dic.isVisual ='1' ) and dic.baseDataDictionary.dataItemId="
				+ dataItemId;
		List<BaseDataDictionary> list = (List<BaseDataDictionary>) this.queryForList(select);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				BaseDataDictionary dic = (BaseDataDictionary) list.get(i);
				DataDictionaryForm form = new DataDictionaryForm();
				form.setChildMap(this.getChildsByFarherId(dic.getDataItemId()));
				form.setDataItem(dic.getDataItem());
				form.setDataItemName(dic.getDataItemName());
				form.setDataItemEngName(dic.getDataItemEngName());
				form.setDataItemNum(dic.getDataItemNum());
				form.setIsLeaf(dic.getIsLeaf());
				form.setIsVisual(dic.getIsVisual());
				form.setOrderNum(dic.getOrderNum());
				form.setRemark(dic.getRemark());
				map.put(dic.getDataItemNum(), form);
			}
		}
		return map;
	}

	@Transactional
	public void saveDictionaryMap(HashMap map) {
		// TODO Auto-generated method stub
		DataDictionaryForm form = (DataDictionaryForm) map.get("root");
		BaseDataDictionary root = this.getRoot();
		BaseDataDictionary dataDic;
		if (root != null) {
			HashMap childMap = form.getChildMap();
			this.saveChildMap(childMap, root);
		} else {
			dataDic = new BaseDataDictionary();
			dataDic.setDataItem(form.getDataItem());
			dataDic.setDataItemName(form.getDataItemName());
			dataDic.setDataItemEngName(form.getDataItemEngName());
			dataDic.setDataItemNum(form.getDataItemNum());
			dataDic.setIsLeaf(form.getIsLeaf());
			dataDic.setIsVisual(form.getIsVisual());
			dataDic.setOrderNum(form.getOrderNum());
			dataDic.setRemark(form.getRemark());
			dataDic.setBaseDataDictionary(null);
			this.savePo(dataDic);
			HashMap childMap = form.getChildMap();
			this.saveChildMap(childMap, dataDic);
		}
	}

	private void saveChildMap(HashMap childMap, BaseDataDictionary fatherItem) {
		// TODO Auto-generated method stub
		BaseDataDictionary dataDic;
		if (childMap != null) {
			Set keySet = childMap.keySet();
			Iterator iter = keySet.iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				DataDictionaryForm form = (DataDictionaryForm) childMap
						.get(key);
				BaseDataDictionary dic = this.getDicByFatherIdAndNum(form
						.getDataItemNum(), fatherItem.getDataItemId());
				if (dic != null) {
					HashMap childTempMap = form.getChildMap();
					this.saveChildMap(childTempMap, dic);
				} else {
					dataDic = new BaseDataDictionary();
					dataDic.setDataItem(form.getDataItem());
					dataDic.setDataItemName(form.getDataItemName());
					dataDic.setDataItemEngName(form.getDataItemEngName());
					dataDic.setDataItemNum(form.getDataItemNum());
					dataDic.setIsLeaf(form.getIsLeaf());
					dataDic.setIsVisual(form.getIsVisual());
					dataDic.setOrderNum(form.getOrderNum());
					dataDic.setRemark(form.getRemark());
					dataDic.setBaseDataDictionary(fatherItem);
					this.savePo(dataDic);
					HashMap childTempMap = form.getChildMap();
					this.saveChildMap(childTempMap, dataDic);
				}
			}
		}
	}

	private BaseDataDictionary getDicByFatherIdAndNum(String dataItemNum,
			Integer dataItemId) {
		// TODO Auto-generated method stub
		String select = "from BaseDataDictionary dic where dic.dataItemNum='"
				+ dataItemNum + "' and dic.baseDataDictionary.dataItemId="
				+ dataItemId;
		BaseDataDictionary dic = (BaseDataDictionary) this.queryForObject(
				select, null);
		return dic;
	}

	public  BaseDataDictionary getRoot() {
		// TODO Auto-generated method stub
		String select = "from BaseDataDictionary dic where dic.dataItemNum='root'";
		BaseDataDictionary dic = (BaseDataDictionary) this.queryForObject(
				select, null);
		return dic;
	}

	public DataDictionary getDataDictionaryList() {
		// TODO Auto-generated method stub
		String hql = "from BaseDataDictionary as t where (t.isVisual is null or t.isVisual ='1' ) and t.baseDataDictionary is null ";
		List tempList = this.queryForList(hql);
		BaseDataDictionary baseDataDictionary;
		DataDictionary dataDictionary = new DataDictionary();
		if (tempList.size() != 0) {
			baseDataDictionary = (BaseDataDictionary) tempList.get(0);
			BeanExtendUtils.copyTreeObject(dataDictionary, baseDataDictionary,
					"baseDataDictionary", "baseDataDictionaries");
		}
		return dataDictionary;
	}

	@Override
	public List getDataDictionaryList(Integer id) {
		// TODO Auto-generated method stub
		String hql;
		if(id == null)
			hql = "from BaseDataDictionary as t where (t.isVisual is null or t.isVisual ='1' ) and t.baseDataDictionary is null ";
		else
			hql = "from BaseDataDictionary as t where (t.isVisual is null or t.isVisual ='1' ) and t.baseDataDictionary.dataItemId= "+id;			
		List tempList = this.queryForList(hql);
		return tempList;
	}
	public BaseDataDictionary findDataDictionaryById(Integer id){
		String hql = "from BaseDataDictionary as t where t.dataItemId = " + id;;		
		List tempList = this.queryForList(hql);
		if(tempList != null && tempList.size() != 0)
			return (BaseDataDictionary)tempList.get(0);
		return null;
	}
	
	public List getByWorkAndStuId(Integer stuId){
		String sql="select b from InfoFormerWorkPostgraduateInfo i,BaseDataDictionary b where i.bachelorDegreeCode=b.dataItemNum and b.baseDataDictionary.dataItemNum='BKZYMLM' and  i.personId="
		+ stuId;
		List list=this.queryForList(sql);
		if(list!=null&&list.size()>0)
			return list;
		else
			return null;
	}
	public List getByFullAndStuId(Integer stuId){
		String sql="select b from InfoFormerFullPostgraduateInfo i,BaseDataDictionary b where i.bachelorDegreeCode=b.dataItemNum and b.baseDataDictionary.dataItemNum='BKZYMLM' and  i.personId="
			+ stuId;
		List list=this.queryForList(sql);
		if(list!=null&&list.size()>0)
			return list;
		else
			return null;
	}
	public List getWorkByFullAndStuId(Integer stuId){
		String sql="select b from InfoFormerWorkPostgraduateInfo i,BaseDataDictionary b where i.bachelorDegreeCode=b.dataItemNum and b.baseDataDictionary.dataItemNum='BKZYMLM' and  i.personId="
			+ stuId;
		List list=this.queryForList(sql);
		if(list!=null&&list.size()>0)
			return list;
		else
			return null;
	}
	public List getMasterByFullAndStuId(Integer stuId){
		String sql="select b from InfoFormerFullPostgraduateInfo i,BaseDataDictionary b where i.masterDegreeCode=b.dataItemNum and b.baseDataDictionary.dataItemNum='BSXKZYMLM' and  i.personId="
				+ stuId;
			List list=this.queryForList(sql);
			if(list!=null&&list.size()>0)
				return list;
			else
				return null;
	}
	public List getMasterByWorkAndStuId(Integer stuId){
		String sql="select b from InfoFormerWorkPostgraduateInfo i,BaseDataDictionary b where i.masterDegreeCode=b.dataItemNum and b.baseDataDictionary.dataItemNum='BSXKZYMLM' and  i.personId="
				+ stuId;
			List list=this.queryForList(sql);
			if(list!=null&&list.size()>0)
				return list;
			else
				return null;
	}


	@Override
	public List getDataDictionaryListByRole(Integer fatherId, String roleString,String isVisual) {
		// TODO Auto-generated method stub
		String select = "from BaseDataDictionary dic where dic.baseDataDictionary.dataItemId=" + fatherId;
		if(isVisual != null) {
			if(isVisual.equals("1"))
				select += " and (dic.isVisual is null or dic.isVisual ='1' ) ";
			else
				select += " and dic.isVisual ='0' ";
		}
		if(roleString != null && roleString.length() > 0) {
			select += " and dic.roleId in " + roleString;
		}
		return this.queryForList(select);
	}
	@Transactional
	public void savePo(BaseDataDictionary po) {
		if(po.getDataItemId() == null)
			this.save(po);
		else
			this.update(po);
	}

	@Transactional
	public void deletePo(BaseDataDictionary po) {
			this.delete(po);
	}

 	@Override
	public List getDictionListForNum(String dataItemNum) {
		// TODO Auto-generated method stub
		String hql = "from BaseDataDictionary dic where dic.dataItemNum = '"+dataItemNum+"'";
		BaseDataDictionary dic = new BaseDataDictionary();
		List list=this.queryForList(hql);
		if(list!=null&&list.size()>0){
			dic = (BaseDataDictionary)list.get(0);
			String hql2 = "from BaseDataDictionary dic where (dic.isVisual is null or dic.isVisual ='1' ) and dic.baseDataDictionary.dataItemId = "+dic.getDataItemId();
			List<BaseDataDictionary> dicList = new ArrayList();
			dicList = this.queryForList(hql2);
			if(dicList!=null&&dicList.size()>0){
				ListOptionInfo listOptionInfo;
				String sonDataItemNum;
				String sonDataItemName;
				List dicsList = new ArrayList();
				for(int i=0;i<dicList.size();i++){
					BaseDataDictionary sonDic = new BaseDataDictionary();
					sonDic = dicList.get(i);
					sonDataItemNum = sonDic.getDataItemNum();
					sonDataItemName = sonDic.getDataItemName();
					listOptionInfo = new ListOptionInfo(sonDataItemNum,
							sonDataItemName);
					dicsList.add(listOptionInfo);
				}
				return dicsList;
				}
			else return null;
		}
		else return null;
	}
	@Override
	public List getDictionListForNumAddPleaseChoose(String dataItemNum) {
		// TODO Auto-generated method stub
		String hql = "from BaseDataDictionary dic where dic.dataItemNum = '"+dataItemNum+"'";
		BaseDataDictionary dic = new BaseDataDictionary();
		List list=this.queryForList(hql);
		if(list!=null&&list.size()>0){
			dic = (BaseDataDictionary)list.get(0);
			String hql2 = "from BaseDataDictionary dic where (dic.isVisual is null or dic.isVisual ='1' ) and dic.baseDataDictionary.dataItemId = "+dic.getDataItemId();
			List<BaseDataDictionary> dicList = new ArrayList();
			dicList = this.queryForList(hql2);
			if(dicList!=null&&dicList.size()>0){
				ListOptionInfo listOptionInfo;
				String sonDataItemNum;
				String sonDataItemName;
				List dicsList = new ArrayList();
				listOptionInfo = new ListOptionInfo("10000","请选择");
				dicsList.add(listOptionInfo);
				for(int i=1;i<dicList.size()+1;i++){
					BaseDataDictionary sonDic = new BaseDataDictionary();
					sonDic = dicList.get(i-1);
					sonDataItemNum = sonDic.getDataItemNum();
					sonDataItemName = sonDic.getDataItemName();
					listOptionInfo = new ListOptionInfo(sonDataItemNum,
							sonDataItemName);
					dicsList.add(listOptionInfo);
				}
				return dicsList;
				}
			else return null;
		}
		else return null;
	}

	@Override
	public List getDictionListByDataItemIdAndNum(Integer dataItemId,
			String dataItemNum) {
		String sql = "from BaseDataDictionary b where (b.isVisual is null or b.isVisual ='1' ) and b.baseDataDictionary.dataItemId="
									+ dataItemId + " and b.dataItemNum='"
									+ dataItemNum + "'" ;
		return this.queryForList(sql);
	}
	
	@Override
	public List getSpecialListByCondition(String dataItemId,String dataItemNum){
		String sql = "from BaseDataDictionary b where (b.isVisual is null or b.isVisual ='1' ) and b.baseDataDictionary.dataItemId in (select b.dataItemId from BaseDataDictionary b where b.baseDataDictionary.dataItemNum= '"+dataItemId+"' ) and b.dataItemNum='"
				+ dataItemNum + "'" ;
		return this.queryForList(sql);
	}

 

}
