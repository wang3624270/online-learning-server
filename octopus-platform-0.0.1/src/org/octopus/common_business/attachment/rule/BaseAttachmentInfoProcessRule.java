package org.octopus.common_business.attachment.rule;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.octopus.common_business.attachment.dao.BaseAttachmentDataDao;
import org.octopus.common_business.attachment.dao.BaseAttachmentInfoDao;
import org.octopus.common_business.attachment.model.BaseAttachmentData;
import org.octopus.common_business.attachment.model.BaseAttachmentInfo;
import org.octopus.common_business.attachment.util.BaseAttachmentInfoProcessUtil;
import org.octopus.common_business.attachment.util.FileProcessObject;
import org.sdu.file_util.FileUtility;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.rmi.UserTokenClientSide;
import org.sdu.spring_util.ApplicationContextHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.sdu.common.form.BaseAttachmentInfoForm;
import cn.edu.sdu.common.pi.AttachmentProcessorI;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.common.util.ZipCompressorTool;

@RestController
public class BaseAttachmentInfoProcessRule implements AttachmentProcessorI{
	@Autowired
	private BaseAttachmentInfoDao baseAttachmentInfoDao;
	@Autowired
	private BaseAttachmentDataDao baseAttachmentDataDao;

	private BaseAttachmentInfoForm getBaseAttachmentInfoFormFromPo(
			BaseAttachmentInfo po) {
		BaseAttachmentInfoForm f = new BaseAttachmentInfoForm();
		f.setAttachId(po.getAttachId());
		f.setAttachType(po.getAttachType());
		f.setDocType(po.getDocType());
		f.setOwnerId(po.getOwnerId());
		f.setUrlAddress(po.getUrlAddress());
		f.setFileName(po.getFileName());
		f.setUploaderId(po.getUploader());
//		f.setUploaderName(po.getUploader().getPerName());
		f.setUploadTime(po.getUploadTime());
		f.setRemark(po.getRemark());
		return f;
	}

	private List<BaseAttachmentInfoForm> getBaseAttachmentInfoFormList(
			String attachType, Integer ownerId) {
		List<BaseAttachmentInfo> list = baseAttachmentInfoDao
				.findBaseAttachmentInfoList(attachType, ownerId);
		if (list == null || list.size() == 0)
			return null;
		List<BaseAttachmentInfoForm> retList = new ArrayList<BaseAttachmentInfoForm>();
		for (int i = 0; i < list.size(); i++) {
			retList.add(getBaseAttachmentInfoFormFromPo(list.get(i)));
		}
		return retList;
	}

	public void getBaseAttachmentInfoFormList(RmiRequest request,
			RmiResponse response) {
		String attachType;
		Integer ownerId;
		attachType = (String) request.get("attachType");// 11
		ownerId = (Integer) request.get("ownerId");
		List list = getBaseAttachmentInfoFormList(attachType, ownerId);
		response.add(RmiKeyConstants.KEY_FORMLIST, list);
	}

	public void upLoadAttachmentFile(RmiRequest request, RmiResponse response) {
		UserTokenClientSide cs = (UserTokenClientSide) request.get("userTokenClientSide");
		String attachType = (String) request.get("attachType");
		Integer ownerId = (Integer) request.get("ownerId");
		String fileName = (String) request.get("fileName");
		String folderName = (String) request.get("folderName");
		Boolean isFileDataInDB = (Boolean)request.get("isFileDataInDB");
		Object obj =request.get("fileData");
		if(obj == null)
			return;
		byte b[] = null;
		InputStream in;
		if(obj instanceof InputStream) {
			in =(InputStream)obj;
		}else {
			b = (byte[])obj;
			in = new ByteArrayInputStream(b);
		}
		Integer attachId = null;
		if(isFileDataInDB != null && isFileDataInDB.booleanValue()) {
			attachId = updateBaseAttachmentData(new Integer(cs.getPersonId()), attachType, ownerId, fileName, folderName,b);
		}else 
			attachId= upLoadAttachmentFile(new Integer(cs.getPersonId()), attachType, ownerId, fileName, folderName,
				in);
		response.add("attachId", attachId);
	}

	public void replaceAttachmentFileOfAttachId(RmiRequest request, RmiResponse response)	{
		UserTokenClientSide cs = (UserTokenClientSide) request.get("userTokenClientSide");
		Integer attachId = (Integer)request.get("attachId");
		Boolean isFileDataInDB = (Boolean)request.get("isFileDataInDB");
		if(attachId == null)
			return;
		BaseAttachmentInfo a = baseAttachmentInfoDao.findBaseAttachmentInfoById(attachId);
		if(a == null)
			return;
		Object obj =request.get("fileData");		
		if(obj == null)
			return;
		try {			
			if(isFileDataInDB != null && isFileDataInDB.booleanValue()) {
				updateBaseAttachmentData(attachId,(byte[])obj);
			}else {
				InputStream in;
				if(obj instanceof InputStream) {
					in =(InputStream)obj;
				}else {
					byte b[] = (byte[])obj;
					in = new ByteArrayInputStream(b);
				}
				FileUtility.uploadFile(in,a.getUrlAddress());				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		a.setUploader(new Integer(cs.getPersonId()));
		a.setUploadTime(DateTimeTool.getNowTime());
		baseAttachmentInfoDao.saveOrUpdatePo(a);
	}
	public Integer upLoadAttachmentFile(Integer personId, String attachType,
			Integer ownerId, String fileName, String folderName, InputStream in) {
		String path = folderName + "/" + ownerId + "/" + fileName;
		return this.upLoadAttachmentFileByCondition(personId, attachType, ownerId,
				fileName, path, in);
	}
	
	public Integer upLoadAttachmentFileToWWW(Integer personId, String attachType,
			Integer ownerId, String fileName, String folderName, InputStream in) {
		String path = folderName + "/" + ownerId + "/" + fileName;
		return this.upLoadAttachmentFileByConditionToWWW(personId, attachType, ownerId,
				fileName, path, in);
	}

	public Integer upLoadAttachmentFileByCondition(Integer personId,
			String attachType, Integer ownerId, String fileName, String path,
			InputStream in) {
		try {
			FileUtility.uploadFile(in, path);
			BaseAttachmentInfo info = baseAttachmentInfoDao
					.findBaseAttachmentInfo(attachType, ownerId, fileName);
			if (info == null) {
				info = new BaseAttachmentInfo();
			}
			info.setAttachType(attachType);
			info.setFileName(fileName);
			info.setOwnerId(ownerId);
			info.setUrlAddress(path);
			info.setUploader(personId);
			info.setUploadTime(DateTimeTool.getNowTime());
			baseAttachmentInfoDao.saveOrUpdatePo(info);
			return info.getAttachId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer upLoadAttachmentFileByConditionToWWW(Integer personId,
			String attachType, Integer ownerId, String fileName, String path,
			InputStream in) {
		try {
			FileUtility.uploadFileToWWW(in, path);
			BaseAttachmentInfo info = baseAttachmentInfoDao
					.findBaseAttachmentInfo(attachType, ownerId, fileName);
			if (info == null) {
				info = new BaseAttachmentInfo();
			}
			info.setAttachType(attachType);
			info.setFileName(fileName);
			info.setOwnerId(ownerId);
			info.setUrlAddress(path);
			info.setUploader(personId);
			info.setUploadTime(DateTimeTool.getNowTime());
			baseAttachmentInfoDao.saveOrUpdatePo(info);
			return info.getAttachId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	private byte[] getFileDataOfBaseAttachmentInfo(BaseAttachmentInfo info) {
		if (info == null)
			return null;
		String urlAdress = info.getUrlAddress();
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
	
	private byte[] getFileDataOfBaseAttachmentInfoFromWWW(BaseAttachmentInfo info) {
		if (info == null)
			return null;
		String urlAdress = info.getUrlAddress();
		return getFileDataOfBaseAttachmentInfoFromWWW(urlAdress);
	}

	public  byte[] getFileDataOfBaseAttachmentInfoFromWWW(String urlAdress) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			FileUtility.downloadFileFromWWW(out, urlAdress);
			byte[] b = out.toByteArray();
			return b;
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	public void downloadAttachmentFile(RmiRequest request, RmiResponse response) {
		String attachId = (String) request.get("attachId");
		Boolean isFileDataInDB = (Boolean)request.get("isFileDataInDB");
		BaseAttachmentInfo info = null;
		if (attachId != null &&! attachId.equals("") && !attachId.equals("null")) {
			info = baseAttachmentInfoDao.findBaseAttachmentInfoById(Integer
					.valueOf(attachId));
		} else {
			String attachType = (String) request.get("attachType");
			String ownerId = (String) request.get("ownerId");
			String fileName = (String) request.get("fileName");
			info = baseAttachmentInfoDao.findBaseAttachmentInfo(attachType,
					Integer.valueOf(ownerId), fileName);
		}
		if(info != null) {
			response.add("fileName", info.getFileName());
			byte data[] = null;
			if(isFileDataInDB != null && isFileDataInDB.booleanValue()) {
				data = getBaseAttachmentData(info.getAttachId());
			}else
				data = getFileDataOfBaseAttachmentInfo(info);
			response.add(RmiKeyConstants.KEY_BYTES,data);
		}
	}

	public Object[] getDownloadAttachmentFile(Integer attachId) {
		BaseAttachmentInfo info = null;
		if (attachId == null)
			return null;
		Object ret[] = new Object[2];
		info = baseAttachmentInfoDao.findBaseAttachmentInfoById(Integer
				.valueOf(attachId));
		ret[0] = info.getFileName();
		ret[1] = getFileDataOfBaseAttachmentInfo(info);
		return ret;
	}
	
	public Object[] getDownloadAttachmentFileFromWWW(Integer attachId) {
		BaseAttachmentInfo info = null;
		if (attachId == null)
			return null;
		Object ret[] = new Object[2];
		info = baseAttachmentInfoDao.findBaseAttachmentInfoById(Integer
				.valueOf(attachId));
		ret[0] = info.getFileName();
		ret[1] = getFileDataOfBaseAttachmentInfoFromWWW(info);
		return ret;
	}

	public void deleteAttachmentFile(Integer attachId) {
		if (attachId == null) {
			return;
		}
		BaseAttachmentInfo info = baseAttachmentInfoDao
				.findBaseAttachmentInfoById(attachId);
		if(info == null)
			return;
		BaseAttachmentData po = baseAttachmentDataDao.findBaseAttachmentDataById(attachId);
		if(po == null)
			return;
		try {
			FileUtility.deleteFile(info.getUrlAddress());
			//baseAttachmentInfoDao.deletePo(info);
			baseAttachmentInfoDao.delete(info);
			baseAttachmentDataDao.delete(po);
		} catch (Exception e) {
		}
	}
	
	public void deleteAttachmentFileFromWWW(Integer attachId) {
		if (attachId == null) {
			return;
		}
		BaseAttachmentInfo info = baseAttachmentInfoDao
				.findBaseAttachmentInfoById(attachId);
		if (info == null) {
			return;
		}
		try {
			FileUtility.deleteFileFromWWW(info.getUrlAddress());
			baseAttachmentInfoDao.deletePo(info);
		} catch (Exception e) {
		}
	}

	public void deleteAttachmentFile(RmiRequest request, RmiResponse response) {
		Object o = request.get("attachId");
		Integer attachId;
		if(o instanceof String)
			attachId = Integer.valueOf((String) request.get("attachId"));
		else
			attachId = (Integer) request.get("attachId");			
		deleteAttachmentFile(attachId);
	}

	public void downloadAttachmentBSFile(RmiRequest request,
			RmiResponse response) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) request
				.get(RmiKeyConstants.HTTPSERVLETRESPONSE);
		String attachId = (String) request.get("attachId");
		String saveFileName = (String) request.get("saveFileName");
		BaseAttachmentInfo info = baseAttachmentInfoDao
				.findBaseAttachmentInfoById(Integer.valueOf(attachId));
		if (saveFileName == null || saveFileName.equals(""))
			saveFileName = info.getFileName();
		httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
		httpServletResponse.setHeader(
				"Content-Disposition",
				"attachment; fileName=\""
						+ new String(saveFileName.getBytes("gb2312"),
								"ISO8859-1") + "\"");
		try {
			FileUtility.downloadFile(httpServletResponse.getOutputStream(),
					info.getUrlAddress());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
	}

	@Override
	public List<Integer> getDownloadAttachIdList(RmiRequest request) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public void downloadAttachmentBSFileMulti(RmiRequest request,
			RmiResponse respond) {
		String zipFileName = (String)request.get("fileName");
		if(zipFileName == null || zipFileName.equals("")) {
			zipFileName = "download.zip";
		}
		else {
			if(!zipFileName.toUpperCase().endsWith(".ZIP")) {
				zipFileName += ".zip";
			}
		}
		String beanName = (String)request.get("beanName");
		AttachmentProcessorI pi = null;
		if(beanName != null) {
			pi = (AttachmentProcessorI)ApplicationContextHandle.getBean(beanName);
		}
		if(pi == null)
			pi = this;
		List <Integer> idList = pi.getDownloadAttachIdList(request);
		String outFileName;
		if (idList != null && idList.size() > 0) {
			HttpServletResponse httpServletResponse = (HttpServletResponse) request
					.get(RmiKeyConstants.HTTPSERVLETRESPONSE);
			httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
			try {
				httpServletResponse.setHeader(
						"Content-Disposition",
						"attachment; fileName=\""
								+ new String(zipFileName.getBytes("gb2312"),
										"ISO8859-1") + "\"");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ZipCompressorTool zipfile = null;
			Object[] a;
			byte []data;
			try {
				zipfile = new ZipCompressorTool(
						httpServletResponse.getOutputStream());
				for (int i = 0; i < idList.size(); i++) {
					a  = getDownloadAttachmentFile(idList.get(i));
					outFileName = (String)a[0];
					data = (byte[]) a[1];
					zipfile.compress(new ByteArrayInputStream(data), outFileName);
				}
				zipfile.close();
				// httpServletResponse.getOutputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void getAttachmentFileModifyTime(RmiRequest request, RmiResponse respond) {
		Integer attachId = (Integer) request.get("attachId");
		BaseAttachmentInfo info = null;
		if (attachId == null)
			return;
		info = baseAttachmentInfoDao.findBaseAttachmentInfoById(Integer
				.valueOf(attachId));
		if (info == null)
			return;
		respond.add("modifyTime", info.getUploadTime());
	}
	public void openDownloadAttachmentFile(RmiRequest request, RmiResponse respond) {
		Integer attachId = (Integer) request.get("attachId");
		BaseAttachmentInfo info = null;
		if (attachId == null)
			return;
		info = baseAttachmentInfoDao.findBaseAttachmentInfoById(Integer
				.valueOf(attachId));
		if (info == null)
			return;
		String urlAdress = info.getUrlAddress();
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			FileUtility.downloadFile(out, urlAdress);
			byte[] b = out.toByteArray();
			out.close();
			FileProcessObject o = BaseAttachmentInfoProcessUtil.getNewInstance();
			o.setData(b);
			respond.add("fileKey", o.getKey());
			respond.add("fileSize", b.length);
			respond.add("fileName", info.getFileName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readAttachmentFileData(RmiRequest request, RmiResponse respond) {
		String fileKey = (String)request.get("fileKey");
		int length = (Integer)request.get("length");
		FileProcessObject o = BaseAttachmentInfoProcessUtil.getFileProcessObject(fileKey);
		int l;
		boolean isEnd = false;
		if(o.getPos() + length < o.getData().length ) {
			l = length;
			isEnd = false;
		}else {
			l = o.getData().length - o.getPos();
			isEnd= true;
		}
		byte b[] = new byte[l];
		byte d[] = o.getData();
		int pos = o.getPos();
		for(int i = 0; i < l;i++) {
			b[i] = d[pos+i];
		}
		respond.add(RmiKeyConstants.KEY_ARRAY,b);
		o.setPos(pos+l);
		if(isEnd) {
			BaseAttachmentInfoProcessUtil.remove(fileKey);
		}
	}
	public void openUploadAttachmentFile(RmiRequest request, RmiResponse respond) {
		Integer attachId = (Integer) request.get("attachId");
		Integer fileSize = (Integer) request.get("fileSize");
		if (attachId == null)
			return;
		FileProcessObject o = BaseAttachmentInfoProcessUtil.getNewInstance();
		byte data[] = new byte[fileSize];
		o.setData(data);
		respond.add("fileKey", o.getKey());
		return;
	}
	
	public void writeAttachmentFileData(RmiRequest request, RmiResponse respond) {
		Integer attachId= (Integer)request.get("attachId");
		String fileKey = (String)request.get("fileKey");
		int length = (Integer)request.get("length");
		FileProcessObject o = BaseAttachmentInfoProcessUtil.getFileProcessObject(fileKey);
		int l;
		boolean isEnd = false;
		if(o.getPos() + length < o.getData().length ) {
			l = length;
			isEnd = false;
		}else {
			l = o.getData().length - o.getPos();
			isEnd= true;
		}
		byte b[] = (byte[]) request.get(RmiKeyConstants.KEY_ARRAY);
		byte d[] = o.getData();
		int pos = o.getPos();
		for(int i = 0; i <l ;i++ ) {
			d[i + pos]  = b[i];
		}
		o.setPos(o.getPos()+l);
		if(isEnd) {
			BaseAttachmentInfoProcessUtil.remove(fileKey);
			BaseAttachmentInfo info = baseAttachmentInfoDao.findBaseAttachmentInfoById(Integer
					.valueOf(attachId));
			if (info == null)
				return;
			String urlAdress = info.getUrlAddress();
			try {
				FileUtility.uploadFile(new ByteArrayInputStream(d), urlAdress);
				info.setUploadTime(DateTimeTool.getNowTime());
				baseAttachmentInfoDao.saveOrUpdatePo(info);
			} catch (Exception e) {
			}
		}
	}
	public void clearAttachmentFileByDateAndType(RmiRequest request, RmiResponse response) {
		String attachType = (String)request.get("attachType");
		Date date = (Date)request.get("date");
		List <Integer> list = baseAttachmentInfoDao.findBaseAttachmentIdListBeforeDate(attachType, date);
		if(list == null ||  list.size() == 0)
			return;
		for(int i = 0; i < list.size();i++) {
			deleteAttachmentFile(list.get(i));			
		}
	}
	public byte[] getFileDataOfBaseAttachmentInfo(Integer attachId) {
		BaseAttachmentInfo info= baseAttachmentInfoDao.query(attachId);
		return getFileDataOfBaseAttachmentInfo(info);
	}
	
	public String getFileName(Integer accId){
		BaseAttachmentInfo info= baseAttachmentInfoDao.find(accId);
		return info.getFileName();
	}
	
	public Integer updateBaseAttachmentData(Integer personId, String attachType,
			Integer ownerId, String fileName, String folderName, byte data[]) {
		String path = folderName + "/" + ownerId + "/" + fileName;
		BaseAttachmentInfo info = baseAttachmentInfoDao
				.findBaseAttachmentInfo(attachType, ownerId, fileName);
		if (info == null) {
			info = new BaseAttachmentInfo();
		}
		info.setAttachType(attachType);
		info.setFileName(fileName);
		info.setOwnerId(ownerId);
		info.setUrlAddress(path);
		info.setUploader(personId);
		info.setUploadTime(DateTimeTool.getNowTime());
		baseAttachmentInfoDao.saveOrUpdatePo(info);
		updateBaseAttachmentData(info.getAttachId(),data);
		return info.getAttachId();

	}
	
	public void updateBaseAttachmentData(Integer attachId, byte[]data){
		BaseAttachmentData po =  baseAttachmentDataDao.query(attachId);
		if(po == null) {
			po = new BaseAttachmentData();
			po.setAttachId(attachId);
		}
		try {
			SerialBlob b = new SerialBlob(data);
			po.setFileData(b);
			baseAttachmentDataDao.update(po);
		}catch(Exception e) {
		}
	}

	public byte[] getBaseAttachmentData(Integer attachId){
		BaseAttachmentData po =  baseAttachmentDataDao.query(attachId);
		if(po == null) 
			return null;
		try {
			Blob blob = po.getFileData();
		       BufferedInputStream is = null;  
		       is = new BufferedInputStream(blob.getBinaryStream());  
		       byte[] bytes = new byte[(int) blob.length()];  
		       int len = bytes.length;  
		       int offset = 0;  
		       int read = 0;  
		       while (offset < len  && (read = is.read(bytes, offset, len - offset)) >= 0) {  
		              offset += read;  
		       }  
		        return bytes;  
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void saveFileDataToDb(RmiRequest request, RmiResponse response){
		List list = baseAttachmentInfoDao.findBaseAttachmentNotInData();
		if(list == null || list.size() == 0)
			return;
		Integer attachId;
		String urlAdress;
		byte [] data;
		SerialBlob b;
		BaseAttachmentInfo info;
		ByteArrayOutputStream out;
		BaseAttachmentData po;
		try {
			for(int i = 0; i < list.size();i++) {
				info = (BaseAttachmentInfo)list.get(i);
				attachId= info.getAttachId();
				urlAdress = info.getUrlAddress();
				out = new ByteArrayOutputStream();
				FileUtility.downloadFile(out, urlAdress);
				data = out.toByteArray();
				out.close();
				po = new BaseAttachmentData();
				po.setAttachId(attachId);
				b = new SerialBlob(data);
				po.setFileData(b);
				baseAttachmentDataDao.update(po);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
