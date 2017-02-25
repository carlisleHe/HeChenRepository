package com.newland.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intensoft.exception.AppRTException;
import com.newland.base.common.AppExCode;
/**
 * 
 * @ClassName: ZipUtil  
 * @Description: 文件压缩工具类 
 * @author: xuzz 
 * @date:2013-1-17 上午10:33:04
 */
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 压缩下载文件
	 * @param path 待压缩文件的路径(注意:path结束要带有"/")
	 * @param fileNames 待压缩文件名
	 * @param outPath 压缩文件存放目录
	 * @param outPutFileName 压缩完后的文件名
	 * @return
	 */
	public static String zipFile(String path,String[] fileNames,String outPath,String outPutFileName){
		File tempOutFile = null;
		FileOutputStream fileout= null;
		ZipOutputStream zipout = null;
		try {
			//创建temp目录
		    File dir = new File(outPath);
		    if(!dir.exists()){
		    	dir.mkdirs();
		    }
			tempOutFile = new File(dir.getPath()+"/"+outPutFileName);
			fileout = new FileOutputStream(tempOutFile);
			zipout = new ZipOutputStream(fileout);
			byte [] bs = new byte[4096];
			boolean flag = false;
			for (String name : fileNames) {
				try {
					File temp = new File(path, name);
					if (temp.exists()) {
						flag = true;
						InputStream in = new FileInputStream(temp);
                        ZipEntry ze = new ZipEntry(temp.getName());
                        zipout.putNextEntry(ze);
						int length = 0;
						while ((length=in.read(bs)) >0){
							zipout.write(bs, 0, length);
						}
						in.close();
						zipout.flush();
					} else {
						logger.warn(String.format("文件:[%s]未生成或已删除或无记录!",name));
					}

				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
			//所有的文件都不存在，则不进行压缩，直接异常提示文件未生成或已被删除
			if(!flag){
				throw new AppRTException(AppExCode.FILE_NOT_FOUND, "待压缩文件不存在!");
			}
			return tempOutFile.getPath();
		} catch (Exception e) {
			if(e instanceof AppRTException)
				throw (AppRTException)e;
			logger.error(e.getMessage(),e);
			throw new AppRTException(AppExCode.UNKNOWN, "文件压缩失败!");
		}
		finally{
			try {
				zipout.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	/**
	 * 
	 * @param filePath 待压缩文件集（全路径）
	 * @param outPutFilePath 输出文件.ZIP
	 * @return
	 */
	public static String zipFile(String[] filePath,String outPutFilePath){
		File tempOutFile = null;
		FileOutputStream fileout= null;
		ZipOutputStream zipout = null;
		try {
			tempOutFile = new File(outPutFilePath);
			//创建输入出文件的目录
			File dir = tempOutFile.getParentFile();
			if(dir != null){
				if(!dir.exists()){
					dir.mkdirs();
				}
			}
			fileout = new FileOutputStream(tempOutFile);
			zipout = new ZipOutputStream(fileout);
			byte [] bs = new byte[4096];
			boolean flag = false;
			for (String name : filePath) {
				try {
					File temp = new File(name);
					if (temp.exists()) {
						flag = true;
						InputStream in = new FileInputStream(temp);
                        ZipEntry ze = new ZipEntry(temp.getName());
                        zipout.putNextEntry(ze);
						int length = 0;
						while ((length=in.read(bs)) >0){
							zipout.write(bs, 0, length);
						}
						in.close();
						zipout.flush();
					} else {
						logger.warn(String.format("文件:[%s]未生成或已删除或无记录!",name));
					}

				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
			//所有的文件都不存在，则不进行压缩，直接异常提示文件未生成或已被删除
			if(!flag){
				throw new AppRTException(AppExCode.FILE_NOT_FOUND, "待压缩文件不存在!");
			}
			return tempOutFile.getPath();
		} catch (Exception e) {
			if(e instanceof AppRTException)
				throw (AppRTException)e;
			logger.error(e.getMessage(),e);
			throw new AppRTException(AppExCode.UNKNOWN, "文件压缩失败!");
		}
		finally{
			try {
				zipout.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	//解压指定zip文件 
    @SuppressWarnings("unchecked")
	public static List<String> unZip(String unZipFile, String outputDir) {
		try {
			//创建temp目录
		    File dirs = new File(outputDir);
		    if(!dirs.exists()){
		    	dirs.mkdirs();
		    }
		    File file = new File(unZipFile);
		    if(!file.exists()){
				throw new AppRTException(AppExCode.TXN_GET_FILE, "压缩文件不存在!");
		    }
			ZipFile zipFile = new ZipFile(unZipFile);
			Enumeration e =zipFile.entries();
			ZipEntry zipEntry = null;
			List<String> list = new ArrayList<String>();
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDir + "/" + name);
					f.mkdirs();
				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace('\\', '/');
					int pos = fileName.lastIndexOf("/");
					if(pos>0){
						File dir = new File(outputDir+"/"+fileName.substring(0,pos));
						dir.mkdirs();	
					}
					File f = new File(outputDir + "/"+ fileName);
					f.createNewFile();
					InputStream in = null;
					FileOutputStream out = null;
					try{
						in = zipFile.getInputStream(zipEntry);
						out = new FileOutputStream(f);
						byte[] by = new byte[4096];
						int c;
						while ((c = in.read(by)) != -1) {
							out.write(by, 0, c);
						}
						list.add(outputDir+"/"+fileName);
					}
					finally{
						out.close();
						in.close();
					}
				}
			}
			return list;
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			throw new AppRTException(AppExCode.UNKNOWN, "文件解压缩失败!");
		}
	}
    public static void main(String[] args) {
		File f = new File("d:/ttt/tt");
		File dir = f.getParentFile();
		if(dir !=null){
			if(!dir.exists()){
				dir.mkdirs();
			}
		}
		System.out.println(f.getPath());
	}
}
