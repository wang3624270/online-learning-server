package cn.edu.sdu.common.pi;

import java.util.List;

import org.sdu.rmi.RmiRequest;

public interface AttachmentProcessorI {
	public List<Integer> getDownloadAttachIdList(RmiRequest request);
}
