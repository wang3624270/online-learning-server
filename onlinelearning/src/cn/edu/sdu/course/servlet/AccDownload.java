package cn.edu.sdu.course.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sdu.spring_util.ApplicationContextHandle;

import cn.edu.sdu.course.rule.BaseAccessoriesInfoProcessRule;

public class AccDownload extends HttpServlet {

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
		// 处理请求
		// 读取要下载的文件
		String accId=request.getParameter("accId");
		File f = new File("");
		BaseAccessoriesInfoProcessRule rule = (BaseAccessoriesInfoProcessRule) ApplicationContextHandle
				.getBean("baseAccessoriesInfoProcessRule");
		FileChannel fc = null;
		byte[] b = rule.getFileDataOfAccessoriesInfo(Integer.valueOf(accId));
		String accName=rule.getFileName(Integer.valueOf(accId));
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(accName.getBytes("UTF-8"),"ISO-8859-1"));
		response.setHeader("Content-Length", b.length + "");
		// 获取响应报文输出流对象
		ServletOutputStream out = response.getOutputStream();
		// 输出
		out.write(b);
		out.flush();
		out.close();

	}
}
