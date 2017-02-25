package com.newland.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Z格式的解压缩工具类
 * @author zhengsm
 *
 */
public class ZuncompressUtil {
	
	public static void uncompressUtil(String zfileName) throws Exception{
		int pos = zfileName.indexOf(".Z");
		String defaultOutName = zfileName.substring(0, pos!=-1?pos:zfileName.length());
		uncompressUtil(zfileName,defaultOutName);
	}
	
	public static void uncompressUtil(String zfileName,String outFileName) throws Exception{
		uncompressUtil(new File(zfileName),outFileName);
	}
	
	public static void uncompressUtil(File zfile,String outFileName) throws Exception{
		if(zfile==null || !zfile.exists() || !zfile.getName().endsWith(".Z")){
			throw new Exception("解压文件不存在，或者格式不是.Z文件");
		}
		uncompressUtil(new FileInputStream(zfile),outFileName);
	}
	
	public static void uncompressUtil(InputStream zfile,String outFileName) throws Exception{
		InputStream in = null;
		FileOutputStream out = null;
		try{
			in = new UncompressInputStream(zfile);
			out = new FileOutputStream(new File(outFileName));
			byte[] buf = new byte[1024];
			int read = 0;
			while((read = in.read(buf, 0, buf.length))!=-1){
				out.write(buf,0,read);
			}
			out.flush();
			out.close();
		}catch(Exception e){
			throw e;
		}finally{
			if(in!=null) in.close();
			if(out!=null) out.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		ZuncompressUtil.uncompressUtil("d:/tr2043_9999.txt.Z");
	}
}
