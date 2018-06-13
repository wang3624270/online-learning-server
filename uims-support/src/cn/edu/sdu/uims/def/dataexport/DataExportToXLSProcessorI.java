package cn.edu.sdu.uims.def.dataexport;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.uims.progress.ProgressElementObject;
import jxl.write.WritableSheet;

public interface DataExportToXLSProcessorI {
	void outTableContentToSheet(WritableSheet sheet1,DataExportTemplateToXLS template,HashMap para, ProgressElementObject proObject)throws Exception;
	List getDataExportFormDataKeyList(DataExportTemplateToXLS template,HashMap para);
	List getDataExportFormDataPartList(List keyList,DataExportTemplateToXLS template,HashMap para);
	
}
