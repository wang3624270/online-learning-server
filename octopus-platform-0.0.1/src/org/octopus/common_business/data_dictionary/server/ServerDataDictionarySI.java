package org.octopus.common_business.data_dictionary.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.octopus.common_business.data_dictionary.form.DataDictionary;

import cn.edu.sdu.common.form.ListOptionInfo;

public class ServerDataDictionarySI {

	public static ServerDataDictionarySI instance = null;

	public static ServerDataDictionarySI getInstance() {
		if (instance == null) {
			instance = new ServerDataDictionarySI();
		}
		return instance;
	}

	public String getDataCodeByName(String parentCode, String childName) {
		List datalist = this.getAllChildsByCode(parentCode);
		String code = "";
		if (datalist != null) {
			for (int i = 0; i < datalist.size(); i++) {
				DataDictionary data = (DataDictionary) datalist.get(i);
				if (data.getDataItemName().equals(childName)) {
					code = data.getDataItemNum();
					break;
				}
			}
		}

		return code;

	}

	public String getChildNameByCode(String parentCode, String childCode) {
		List datalist = this.getAllChildsByCode(parentCode);
		String name = "";
		if (datalist != null) {
			for (int i = 0; i < datalist.size(); i++) {
				DataDictionary data = (DataDictionary) datalist.get(i);
				if (data.getDataItemNum().equals(childCode)) {
					name = data.getDataItemName();
					break;
				}
			}
		}

		return name;

	}

	/**
	 * 根据父节点code和叶节点code取得叶节点名称
	 * 
	 * @param typeCode
	 *            类型码
	 * @param code
	 *            数据字典码
	 * @return
	 */
	public String getDataNameByCode(String typeCode, String code, DataDictionary latestNotLeaf) {
		if (code == null || typeCode == null)
			return "";
		if (null == latestNotLeaf || !latestNotLeaf.getDataItemName().equals(typeCode)) {
			latestNotLeaf = this.getDataTypeObjByCode(typeCode);
		}
		return this.getDataNameByCode(latestNotLeaf, code);
	}

	/**
	 * 根据父节点code和叶节点code取得叶节点名称
	 * 
	 * @param typeCode
	 *            类型码
	 * @param code
	 *            数据字典码
	 * @return
	 */
	public String getDataNameByCode(boolean englishVersion, String typeCode, String code) {
		if (code == null || typeCode == null)
			return "";
		DataDictionary latestNotLeaf = this.getDataTypeObjByCode(typeCode);
		return this.getDataNameByCode(latestNotLeaf, code);
	}

	public String getDataNameByCode(String typeCode, String code) {
		if (code == null || typeCode == null)
			return "";
		DataDictionary latestNotLeaf = this.getDataTypeObjByCode(typeCode);
		return this.getDataNameByCode(latestNotLeaf, code);
	}

	public String getDataEngNameByCode(String typeCode, String code) {
		if (code == null || typeCode == null)
			return "";
		DataDictionary latestNotLeaf = this.getDataTypeObjByCode(typeCode);
		return this.getDataEngNameByCode(latestNotLeaf, code);
	}

	public String getDataNameByCodes(String... typecode) {
		DataDictionary obj = this.getDataTypeObjByCode(typecode[0]);

		if (null == obj) {
			return null;
		}

		DataDictionary temp = obj;
		if (typecode.length > 1) {
			DataDictionary parenttemp = obj;
			for (int i = 1; i < typecode.length - 1; i++) {
				temp = getDataDicByCode(parenttemp, typecode[i]);

				if (temp != null) {
					parenttemp = temp;
				} else {
					return null;
				}
			}
		}

		return this.getDataNameByCode(temp, typecode[typecode.length - 1]);
	}

	/**
	 * 根据父节点code和叶节点code获取叶节点ListOptionInfo对象
	 * 
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public ListOptionInfo getListOptionInfoByCode(String typeCode, String code, DataDictionary latestNotLeaf) {
		String label = getDataNameByCode(typeCode, code, latestNotLeaf);
		ListOptionInfo option = new ListOptionInfo();
		option.setLabel(label);
		option.setValue(code);
		return option;
	}

	/**
	 * 根据父节点code和叶节点code获取叶节点ListOptionInfo对象
	 * 
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public ListOptionInfo getListOptionInfoByCode(String typeCode, String code) {
		String label = getDataNameByCode(typeCode, code);
		ListOptionInfo option = new ListOptionInfo();
		option.setLabel(label);
		option.setValue(code);
		return option;
	}

	/**
	 * 根据节点code取得节点下的所有节点
	 * 
	 * @param typecode
	 * @return
	 */
	public List getAllChildsByCode(String... typecode) {
		DataDictionary obj = this.getDataTypeObjByCode(typecode[0]);

		if (null == obj) {
			return null;
		}
		DataDictionary temp = obj;
		DataDictionary parenttemp = obj;

		if (typecode.length > 1) {

			for (int i = 1; i < typecode.length; i++) {
				temp = getDataDicByCode(parenttemp, typecode[i]);

				if (temp != null) {
					parenttemp = temp;
				} else {
					return null;
				}
			}
		}

		List result = new ArrayList();

		for (DataDictionary d1 : parenttemp.getOrderedchildDictionaryList()) {

			result.add(d1);
		}

		return result;
	}

	/**
	 * 根据code取得第一层的数据类型对象
	 * 
	 * @param typecode
	 * @return
	 */
	public DataDictionary getDataTypeObjByCode(String typecode) {
		// DataDictionary root = (DataDictionary) RmiClientRequest
		// .getData(DataDictionaryConstants.DataDicKey);
		DataDictionary root = DataDictionaryInfoBean.getDatadicroot();
		if (root == null)
			return null;

		for (DataDictionary d1 : root.getOrderedchildDictionaryList()) {

			if (d1.getIsLeaf().equals(0) && d1.getDataItemNum().trim().equals(typecode)) {

				return d1;
			}
		}

		return null;
	}

	/**
	 * 根据节点code取得节点下的所有节点信息
	 * 
	 * List里存放由节点code和节点name组成的ListOptionInfo
	 * 
	 * @param typecode
	 * @return
	 */
	public List getComboxListByCode(String... typecode) {
		return getComboxListByCode(false, typecode);
	}

	public List getComboxListByCodeSort(String... typecode) {
		return getComboxListByCodeSort(false, typecode);
	}

	public List getComboxListByCode(boolean englishVersion, String... typecode) {
		DataDictionary obj = this.getDataTypeObjByCode(typecode[0]);
		List result = new ArrayList();

		if (null == obj) {
			return result;
		}

		DataDictionary temp = obj;
		if (typecode.length > 1) {
			DataDictionary parenttemp = obj;
			for (int i = 1; i < typecode.length; i++) {
				temp = getDataDicByCode(parenttemp, typecode[i]);

				if (temp != null) {
					parenttemp = temp;
				} else {
					return result;
				}
			}
		}

		ListOptionInfo option = null;
		for (DataDictionary d1 : temp.getOrderedchildDictionaryList()) {

			if (!englishVersion) {
				option = new ListOptionInfo(d1.getDataItemNum(), d1.getDataItemName());
			} else {
				option = new ListOptionInfo(d1.getDataItemNum(), d1.getDataItemEngName());
			}
			result.add(option);
		}
		return result;
	}

	public List getComboxListByCodeSort(boolean englishVersion, String... typecode) {
		DataDictionary obj = this.getDataTypeObjByCode(typecode[0]);

		List result = new ArrayList();
		if (null == obj) {
			return result;
		}

		DataDictionary temp = obj;
		if (typecode.length > 1) {
			DataDictionary parenttemp = obj;
			for (int i = 1; i < typecode.length; i++) {
				temp = getDataDicByCode(parenttemp, typecode[i]);

				if (temp != null) {
					parenttemp = temp;
				} else {
					return result;
				}
			}
		}

		ListOptionInfo option = null;
		List<DataDictionary> dList = new ArrayList();
		for (DataDictionary d1 : temp.getOrderedchildDictionaryList()) {

			dList.add(d1);
		}
		if (dList == null || dList.size() == 0)
			return result;

		for (int i = 0; i < dList.size(); i++) {
			DataDictionary d1 = dList.get(i);
			if (!englishVersion) {
				option = new ListOptionInfo(d1.getDataItemNum(), d1.getDataItemName());
			} else {
				option = new ListOptionInfo(d1.getDataItemNum(), d1.getDataItemEngName());
			}
			result.add(option);
		}
		return result;
	}

	/**
	 * 根据父对象和code 取得数据名
	 * 
	 * @param parent
	 * @param code
	 * @return
	 */
	public String getDataNameByCode(DataDictionary parent, String code) {
		if (parent == null)
			return "";
		Iterator ite = parent.getBaseDataDictionaries().iterator();

		String dataName = null;
		for (DataDictionary d1 : parent.getOrderedchildDictionaryList()) {

			if (d1.getDataItemNum().equals(code)) {

				return d1.getDataItemName();
			} else {

				if (d1.getBaseDataDictionaries().size() != 0) {
					dataName = getDataNameByCode(d1, code);
				}
			}
		}

		return dataName;
	}

	public String getDataEngNameByCode(DataDictionary parent, String code) {
		if (parent == null)
			return "";

		String dataEngName = null;
		for (DataDictionary d1 : parent.getOrderedchildDictionaryList()) {

			if (d1.getDataItemNum().equals(code)) {

				return d1.getDataItemEngName();
			} else {

				if (d1.getBaseDataDictionaries().size() != 0) {
					dataEngName = getDataEngNameByCode(d1, code);
				}
			}
		}

		return dataEngName;
	}

	/**
	 * 根据父对象和code 取得数据项
	 * 
	 * @param parent
	 * @param code
	 * @return
	 */
	public String getDataItemByCode(String typeCode, String code) {
		if (code == null || typeCode == null)
			return "";
		DataDictionary latestNotLeaf = this.getDataTypeObjByCode(typeCode);

		if (latestNotLeaf == null)
			return "";

		String dataItem = null;
		for (DataDictionary d1 : latestNotLeaf.getOrderedchildDictionaryList()) {
			if (d1.getDataItemNum().equals(code)) {
				return d1.getDataItem();
			} else {
				if (d1.getBaseDataDictionaries().size() != 0) {
					dataItem = getDataNameByCode(d1, code);
				}
			}
		}

		return dataItem;
	}

	/**
	 * 根据父对象和code 取得数据字典对象
	 * 
	 * @param parent
	 * @param code
	 * @return
	 */
	public DataDictionary getDataDicByCode(DataDictionary parent, String code) {
		Iterator ite = parent.getBaseDataDictionaries().iterator();

		for (DataDictionary d1 : parent.getOrderedchildDictionaryList()) {

			if (d1.getDataItemNum().equals(code)) {

				return d1;
			}

		}

		return null;
	}

	public List getStringListByCode(String... typecode) {
		DataDictionary obj = this.getDataTypeObjByCode(typecode[0]);

		if (null == obj) {
			return null;
		}

		DataDictionary temp = obj;
		if (typecode.length > 1) {
			DataDictionary parenttemp = obj;
			for (int i = 1; i < typecode.length; i++) {
				temp = getDataDicByCode(parenttemp, typecode[i]);

				if (temp != null) {
					parenttemp = temp;
				} else {
					return null;
				}
			}
		}

		List result = new ArrayList();

		for (DataDictionary d1 : temp.getOrderedchildDictionaryList()) {

			result.add(d1.getDataItemName());
		}

		return result;
	}

	/**
	 * 根据父结点code和子结点code 取得数据字典对象
	 * 
	 * @param parent
	 * @param code
	 * @return
	 */
	public DataDictionary getDataDicByCode(String parent, String code) {

		DataDictionary parentDataDic = getDataTypeObjByCode(parent);
		if (parentDataDic == null) {
			return null;
		}

		for (DataDictionary d1 : parentDataDic.getOrderedchildDictionaryList()) {

			if (d1.getDataItemNum().equals(code)) {

				return d1;
			}
		}
		return null;
	}

	/**
	 * 根据父结点code和子结点code,以及二级子节点取得二级子节点的名称
	 * 
	 * @param parent
	 * @param code
	 * @return
	 */
	public String getDataNameBySecondCode(String parent, String child, String code) {

		DataDictionary dic = getDataDicByCode(parent, child);
		return getDataNameByCode(dic, code);
	}

	/**
	 * 取得type指定的上一层父节点的code
	 * 
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public String getNextParentCodeByCode(String typeCode, String code) {
		DataDictionary obj = this.getDataTypeObjByCode(typeCode);
		if (null == obj) {
			return null;
		}
		return getNextParentCodeByCode(obj, code);
	}

	/**
	 * 取得父对象指定的上一层父节点的code
	 * 
	 * @param parent
	 * @param code
	 * @return
	 */
	private String getNextParentCodeByCode(DataDictionary parent, String code) {
		Iterator ite = parent.getBaseDataDictionaries().iterator();

		for (DataDictionary d1 : parent.getOrderedchildDictionaryList()) {

			if (d1.getDataItemNum().equals(code)) {

				return parent.getDataItemNum();
			} else {

				if (d1.getBaseDataDictionaries().size() != 0) {
					return getNextParentCodeByCode(d1, code);
				}
			}
		}

		return null;
	}

	/***************************************************************************
	 * 根据父节点和其叶节点的code获得该叶节点的名称
	 * 
	 * @param parent
	 * @param code
	 * @return
	 */
	public String getCodeNameByLeafCode(String code, String typecode) {
		DataDictionary obj = this.getDataTypeObjByCode(code);

		if (null == obj) {
			return null;
		}
		String codeName = "";
		codeName = getCodeNameByLeafCode(obj, typecode);
		if (codeName != null && !codeName.equals("")) {
			int index = codeName.indexOf("*");
			if (index != -1) {
				codeName = codeName.substring(index + 1);
			}
		}
		return codeName;

	}

	public String getCodeNameOnlyByLeafCode(String code, String typecode) {
		DataDictionary obj = this.getDataTypeObjByCode(code);

		if (null == obj) {
			return null;
		}
		String codeName = "";
		codeName = getCodeNameByLeafCode(obj, typecode);
		if (codeName != null && !codeName.equals("")) {
			int index = codeName.lastIndexOf("*");
			if (index != -1) {
				codeName = codeName.substring(index + 1);
			}
		}
		System.out.println(codeName);
		return codeName;

	}

	public String getCodeNameByLeafCode(DataDictionary parent, String code) {
		if (parent == null) {
			return null;
		}

		// 判断是否是叶节点
		if (parent.getIsLeaf().equals(1)) {// 是叶节点
			if (parent.getDataItemNum().equals(code)) {
				return parent.getDataItemName();
			}
		} else {

			for (DataDictionary d1 : parent.getOrderedchildDictionaryList()) {

				String tmp = this.getCodeNameByLeafCode(d1, code);
				if (tmp != null) {
					return parent.getDataItemName() + "*" + tmp;
				}
			}
		}
		return null;
	}

	/***************************************************************************
	 * 根据节点code取得节点下的所有叶节点信息 List里存放由节点code和节点name组成的ListOptionInfo
	 * 
	 * @param typecode
	 * @return
	 */
	public List getLeafComboxListByCode(String... typecode) {
		DataDictionary obj = this.getDataTypeObjByCode(typecode[0]);

		if (null == obj) {
			return null;
		}

		DataDictionary temp = obj;
		if (typecode.length > 1) {
			DataDictionary parenttemp = obj;
			for (int i = 1; i < typecode.length; i++) {
				temp = getDataDicByCode(parenttemp, typecode[i]);

				if (temp != null) {
					parenttemp = temp;
				} else {
					return null;
				}
			}
		}
		List result = new ArrayList();
		String label = "";
		String code = "";

		for (DataDictionary d1 : temp.getOrderedchildDictionaryList()) {

			processTree(d1, result, label, code);
		}

		return result;
	}

	/**
	 * 递归遍历取叶节点信息
	 * 
	 * @param obj
	 * @param list
	 * @param label
	 * @param code
	 */
	public void processTree(DataDictionary obj, List list, String label, String code) {
		if (obj == null)
			return;

		// 判断是否是叶节点
		if (obj.getIsLeaf().equals(1)) {// 是叶节点
			label += obj.getDataItemName();
			code = obj.getDataItemNum();
			list.add(new ListOptionInfo(code, label));
		} else {// 不是叶节点
			label += obj.getDataItemName() + "*";

			for (DataDictionary d1 : obj.getOrderedchildDictionaryList()) {

				processTree(d1, list, label, code);
			}
		}
	}

}
