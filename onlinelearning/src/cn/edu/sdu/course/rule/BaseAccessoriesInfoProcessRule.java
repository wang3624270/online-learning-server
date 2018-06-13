package cn.edu.sdu.course.rule;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.sdu.file_util.FileUtility;
import org.sdu.rmi.RmiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.sdu.common.pi.AttachmentProcessorI;
import cn.edu.sdu.course.dao.AccessoriesInfoDao;
import cn.edu.sdu.course.model.AccessoriesInfo;

@RestController
public class BaseAccessoriesInfoProcessRule implements AttachmentProcessorI{
	@Autowired
	private AccessoriesInfoDao accessoriesInfoDao;
	
	@Override
	public List<Integer> getDownloadAttachIdList(RmiRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public byte[] getFileDataOfAccessoriesInfo(Integer accId) {
		AccessoriesInfo info= accessoriesInfoDao.query(accId);
		return getFileDataOfAccessoriesInfo(info);
	}
	
	private byte[] getFileDataOfAccessoriesInfo(AccessoriesInfo info) {
		if (info == null)
			return null;
		String urlAdress = info.getAccUrl();
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			FileUtility.downloadFile(out, urlAdress);
			byte[] b = out.toByteArray();
			return b;
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	public String getFileName(Integer accId){
		AccessoriesInfo info= accessoriesInfoDao.getAccById(accId);
		return info.getAccName();
	}

}
