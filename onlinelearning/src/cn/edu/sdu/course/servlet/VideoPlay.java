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

public class VideoPlay extends HttpServlet {

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
		String range=(String)request.getHeader("Range").substring(6);
		Integer startIndex=Integer.valueOf(range.split("-")[0]);
		byte[] b = rule.getFileDataOfAccessoriesInfo(Integer.valueOf(accId));
		Integer length=b.length-startIndex;
		byte[] b1 = new byte[length+1];
		System.arraycopy(b, startIndex, b1, 0, length);
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Content-Length", length + "");
		response.setHeader("Content-Range", "bytes " + startIndex + "-" + (b.length-1000)+"/"+(b.length));
		response.setHeader("Content-Type", "video/mp4");
		// 获取响应报文输出流对象
		ServletOutputStream out = response.getOutputStream();
		// 输出
		out.write(b1);
		out.flush();
		out.close();

	}
}
