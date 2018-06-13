package cn.edu.sdu.common.util;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * author:zhengjiecai
 * Nov 26, 2009
 */
public class ZipCompressorTool {
	static final int BUFFER = 102400;
	public ZipOutputStream out;   
	  
    public ZipCompressorTool(String pathName) {   
    	File zipSrcFile = new File(pathName);
    	try{
	    	FileOutputStream fileOutputStream = new FileOutputStream(zipSrcFile);   
	        CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,   
	                new CRC32());   
	        out = new ZipOutputStream(cos);  
    	}catch (Exception e) {   
            throw new RuntimeException(e);   
        } 
    }  
    public ZipCompressorTool( OutputStream fileOutputStream ) {   
    	try{
	        CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,   
	                new CRC32());   
	        out = new ZipOutputStream(cos);  
    	}catch (Exception e) {   
            throw new RuntimeException(e);   
        } 
    }  
    
    public void compress(String srcPathName){
        File file = new File(srcPathName);  
        compress(file);
    }
    
    public void compress(File file) {    
        if (!file.exists())   
            throw new RuntimeException("文件不存在！");   
        try {   
            String basedir = "";   
            compress(file, out, basedir);   
//            out.close();   
        } catch (Exception e) {   
            throw new RuntimeException(e);   
        }   
    }   
    
    public void close(){
    	try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
    }
    
    public void compress(File file, ZipOutputStream out, String basedir) {   
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {   
            System.out.println("压缩：" + basedir + file.getName());   
            this.compressDirectory(file, out, basedir);   
        } else {   
            System.out.println("压缩：" + basedir + file.getName());   
            this.compressFile(file, out, basedir);   
        }   
    }  
    /** 压缩一个目录 */  
    public void compressDirectory(File dir, ZipOutputStream out, String basedir) {   
        if (!dir.exists())   
            return;   
  
        File[] files = dir.listFiles();   
        for (int i = 0; i < files.length; i++) {   
            /* 递归 */  
            compress(files[i], out, basedir + dir.getName() + "/");   //wangqianqiaan,兼容Ios文件夹格式
        }   
    }   
  
    /** 压缩一个文件 */  
    private void compressFile(File file, ZipOutputStream out, String basedir) {   
        if (!file.exists()) {   
            return;   
        }   
        try {   
            BufferedInputStream bis = new BufferedInputStream(   
                    new FileInputStream(file));   
            String name = basedir + file.getName();
            ZipEntry entry = new ZipEntry(name);   
            out.putNextEntry(entry);   
            int count;   
            byte data[] = new byte[BUFFER];   
            while ((count = bis.read(data, 0, BUFFER)) != -1) {   
                out.write(data, 0, count);   
            }   
            bis.close();   
        } catch (Exception e) {   
            throw new RuntimeException(e);   
        }   
    }
 
    public boolean  compress(File file, String name) {    
        if (!file.exists())   
        	return false;
        try {   
        	out.setEncoding("gbk");
            BufferedInputStream bis = new BufferedInputStream(   
                    new FileInputStream(file));   
            compressFile(bis, out, name);   
            bis.close();   
        } catch (Exception e) {   
        	return false;
        }
        return true;
    }   
 
    private void compressFile(InputStream bis, ZipOutputStream out, String name) {   
    	try {
            ZipEntry entry = new ZipEntry(name);   
            out.putNextEntry(entry);   
            int count;   
            byte data[] = new byte[BUFFER];   
            while ((count = bis.read(data, 0, BUFFER)) != -1) {   
                out.write(data, 0, count);   
            }  
            out.flush();
            out.closeEntry();
        } catch (Exception e) {   
            throw new RuntimeException(e);   
        }finally{
        	 try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
        }   
    }
    public boolean  compress(InputStream bis, String name) {    
        try {   
            compressFile(bis, out, name);   
            bis.close();   
        } catch (Exception e) {   
        	return false;
        }
        return true;
    }   
    public void compressFile(File file, String basedir) {   
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {   
            System.out.println("压缩：" + basedir + file.getName());   
            this.compressDirectory(file, out, basedir);   
        } else {   
            System.out.println("压缩：" + basedir + file.getName());   
            this.compressFile(file, out, basedir);   
        }   
    }  
}
