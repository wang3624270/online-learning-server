package org.octopus.common_business.data_dictionary.jpa_dao;

import java.util.HashMap;
import java.util.List;

import org.octopus.common_business.data_dictionary.form.DataDictionary;
import org.octopus.common_business.data_dictionary.form.DataDictionaryForm;
import org.octopus.common_business.data_dictionary.jpa_model.BaseDataDictionary;
import org.octopus.spring_utils.jpa.GenericServiceI;


public interface BaseDataDictionaryDao extends GenericServiceI<BaseDataDictionary> {
	
	public HashMap<String,DataDictionaryForm> getBaseDataDictionary();

	public void saveDictionaryMap(HashMap map);

	public DataDictionary getDataDictionaryList();
	
	public List getDataDictionaryList(Integer id);
	public BaseDataDictionary findDataDictionaryById(Integer id);
	
	public BaseDataDictionary getRoot();
	public List getDataDictionaryListByRole(Integer id, String roleString,String isVisual);
	public void savePo(BaseDataDictionary po);
	public void deletePo(BaseDataDictionary po); 
	public List getDictionListForNum(String dataItemNum);
	
	public List getDictionListForNumAddPleaseChoose(String dataItemNum);
	public List getDictionListByDataItemIdAndNum(Integer DataItemId,String dataItemNum);

	List getSpecialListByCondition(String dataItemId, String dataItemNum);
	 
}
