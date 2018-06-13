package org.octopus.common_business.data_dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.octopus.common_business.data_dictionary.constants.DataDictionaryConstants;
import org.octopus.common_business.data_dictionary.form.DataDictionary;
import org.sdu.rmi.RmiClientRequest;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.common.pi.ClientInitPlugInI;

public class ClientDataDictionary implements ClientDataDictionaryI {
	private DataDictionary root = null;
	private static ClientDataDictionaryI instance;
	String startupProcessMessage = "正在从服务器获取业务数据信息，请稍等...";

	public String getStartupProcessMessage() {
		return this.startupProcessMessage;
	}

	public void init() {
		// TODO Auto-generated method stub
		initDataDictionary();

	}

	private ClientDataDictionary() {
	}

	public static ClientDataDictionaryI getInstance() {
		if (instance == null) {
			instance = new ClientDataDictionary();
		}
		return instance;
	}

	synchronized public String initDataDictionary() {
		RmiRequest request = new RmiRequest();
		request.setCmd("sysInit_getDataDictionary");
 
		RmiResponse respond = RmiClientRequest.getClientRequest().request(request);

		if (null == respond.getErrorMsg()) {
			root = (DataDictionary) respond.get(DataDictionaryConstants.DataDicKey);
			if (null == root) {
				return "获取数据字典失败！";
			}
		} else {
			return respond.getErrorMsg();
		}

		return null;
	}

	@Override
	public List getComboxListByCode(String... typecode) {
		List result = new ArrayList();
		DataDictionary obj = this.getDataTypeObjByCode(typecode[0]);

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
			option = new ListOptionInfo(null, d1.getDataItemNum(), d1.getDataItemName(), d1.getDataItemEngName());
			result.add(option);
		}

		return result;
	}

	public DataDictionary getDataDicByCode(DataDictionary parent, String code) {

		for (DataDictionary d1 : parent.getOrderedchildDictionaryList()) {
			if (d1.getDataItemNum().equals(code)) {
				return d1;
			}
		}
		return null;
	}

	@Override
	public List getComboxListByCode(String code) {
		// TODO Auto-generated method stub
		return getComboxListByCode(new String[] { code });
	}

	@Override
	public String getDataNameByCode(String typeCode, String code) {
		// if (null == latestNotLeaf ||
		// !latestNotLeaf.getDataItemName().equals(typeCode)) {
		DataDictionary latestNotLeaf = this.getDataTypeObjByCode(typeCode);
		// }

		return this.getDataNameByCode(latestNotLeaf, code);
	}

	public DataDictionary getDataTypeObjByCode(String typecode) {
		if (root == null)
			return null;

		for (DataDictionary d1 : root.getOrderedchildDictionaryList()) {

			if (d1.getIsLeaf().equals(0) && d1.getDataItemNum().equals(typecode)) {
				return d1;
			}
		}
		return null;
	}

	private String getDataNameByCode(DataDictionary parent, String code) {
		if (parent == null)
			return null;

		for (DataDictionary d1 : parent.getOrderedchildDictionaryList()) {

			if (d1.getDataItemNum().equals(code)) {

				return d1.getDataItemName();
			} else {

				if (d1.getBaseDataDictionaries().size() != 0) {
					return getDataNameByCode(d1, code);
				}
			}
		}

		return null;
	}

}
