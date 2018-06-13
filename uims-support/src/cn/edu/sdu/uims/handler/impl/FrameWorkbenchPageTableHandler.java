package cn.edu.sdu.uims.handler.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.uims.form.impl.UTableQueryDataForm;
import cn.edu.sdu.uims.handler.TablePageDataQueryHandlerI;

public class FrameWorkbenchPageTableHandler extends FrameWorkbenchBaseHandler implements TablePageDataQueryHandlerI {

	public void getPageDataList(UTableQueryDataForm dForm){
		if(dForm.getTotal() <1)
			return;
		int start = dForm.getStart();
		int count;
		List<Integer>idList = dForm.getIdList();
		int pageRowNum = dForm.getPageNum();
		if(start + pageRowNum < idList.size())
			count = pageRowNum;
		else 
			count = idList.size()-start;
		List<Integer> partIdList = new ArrayList<Integer>(count);
		for(int i = 0; i < count;i++){
			partIdList.add(idList.get(start+i));
		}
		List dataList= getDataListByIdList(partIdList);
		dForm.setDataList(dataList);
	}

	@Override
	public void getTablePageData(UTableQueryDataForm form) {
		// TODO Auto-generated method stub
		getPageDataList(form);
	}

	@Override
	public List getDataListByIdList(List<Integer> idList) {
		// TODO Auto-generated method stub
		return null;
	}

}
