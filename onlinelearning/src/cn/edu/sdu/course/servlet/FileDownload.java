package cn.edu.sdu.course.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.octopus.common_business.attachment.rule.BaseAttachmentInfoProcessRule;
import org.sdu.spring_util.ApplicationContextHandle;

public class FileDownload extends HttpServlet {

	public String ReadFile(String Path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��������
		// ��ȡҪ���ص��ļ�
		String attachId=request.getParameter("attachId");
		File f = new File("");
		BaseAttachmentInfoProcessRule rule = (BaseAttachmentInfoProcessRule) ApplicationContextHandle
				.getBean("baseAttachmentInfoProcessRule");
		FileChannel fc = null;
		byte[] b = rule.getFileDataOfBaseAttachmentInfo(Integer.valueOf(attachId));
		String accName=rule.getFileName(Integer.valueOf(attachId));
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(accName.getBytes("UTF-8"),"ISO-8859-1"));
		response.setHeader("Content-Length", b.length + "");
		// ��ȡ��Ӧ�������������
		ServletOutputStream out = response.getOutputStream();
		// ���
		out.write(b);
		out.flush();
		out.close();

	}
}
