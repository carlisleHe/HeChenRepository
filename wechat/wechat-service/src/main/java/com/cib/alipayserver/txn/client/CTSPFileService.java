package com.cib.alipayserver.txn.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TuxedoClient.FileProcess.FileProcessService;
import TuxedoClient.common.config.ClientConfig;
import TuxedoClient.exception.DomainException;

import com.cib.alipayserver.util.ZuncompressUtil;
import com.intensoft.coresyst.exception.CoreSystException;

/**
 * <p>使用此服务前，需有环境的支持。包括配置文件接收服务，安装TCP或JNI加密程序，将配置文件放至用户主目录等</p>
 * <p>1 传输需 CTSP 添加客户端IP的支持</p>
 * <p>2 上传文件需CTSP的权限支持</p>
 * @author wubh
 * @description
 * @since 2012-12-13
 */
public class CTSPFileService { 

	private Logger log = LoggerFactory.getLogger(getClass());

    /**
     *  <p><b>上传文件服务</b></p>
     * <p> 1在用户主目录下建立etc。用户主目录在UNIX平台下可通过$HOME环境变量获得，Windows平台的用户主目录则通常位于系统盘的根目录“Documents and Settings”子目录下以用户名为名称的子目录。</p>
       <p> 2在该目录下建立客户端配置文件（client.properties）并参照配置文件说明进行配置</p>
       <p> 3构造函数初始化时将加载用户主目录/etc下的客户端配置文件，如果不存在则会抛出异常</p>
     * @param filename
     * @return 文件ID,上传失败返回空
     */
    public String uploadFile(String filename) {
        try {
            FileProcessService fps = new FileProcessService();
            return fps.ofepsendfile(filename);
        } catch (DomainException e) {
            log.error("upload failed...", e);
            return null;
        }

    }

    /**
     * 文件压缩上传服务
     * @param filename client.properties配置文件的LocalFilePath属性设置目录下的文件
    参数:128位以内相对路径
     * @return
     */
    public String uploadFileByComprees(String filename) {
        try {
            FileProcessService fps = new FileProcessService();
            return fps.ofepsendgzipfile(filename);
        } catch (DomainException e) {
            log.error("upload(ZIP) failed...", e);
            return null;
        }

    }

    /**
     * 上传并覆盖掉原来已上传的文件
     * @param filename
     * @return
     */
    public String uploadAndReplaceFile(String filename) {
        try {
            FileProcessService fps = new FileProcessService();
            return fps.ofepsendowfile(filename);
        } catch (DomainException e) {
            log.error("uploadAndReplace failed...", e);
            return null;
        }

    }

    /**
     * 压缩上传并覆盖掉原来已上传的文件
     * @param filename
     * @return
     */
    public String uploadAndReplaceFileByCompress(String filename) {
        try {
            FileProcessService fps = new FileProcessService();
            return fps.ofepsendowgzipfile(filename);
        } catch (DomainException e) {
            log.error("uploadAndReplace(ZIP) failed...", e);
            return null;
        }

    }

    /**
     * 文件下载服务
     * @param downloadFilename client.properties配置文件的LocalFilePath属性设置目录下的文件
    参数:128位以内相对路径
     * @param downloadFileId 下载文件ID
     * @return
     */
    public boolean downloadFile(String downloadFilename, String downloadFileId) {
        try {
            FileProcessService fps = new FileProcessService();
            return fps.ofepgetfile(downloadFilename, downloadFileId);
        } catch (DomainException e) {
            log.error("download failed...", e);
            return false;
        }

    }

    /**
     * 文件下载服务
     * @param downloadFilename client.properties配置文件的LocalFilePath属性设置目录下的文件
     * 参数:128位以内相对路径
     * @param downloadFileId 下载文件ID
     * @param newFileName 保存为新文件名
     * @return
     */
    public boolean downloadFile(String downloadFilename, String downloadFileId, String newFileName) {
        try {
            FileProcessService fps = new FileProcessService();
            return fps.ofepgetfile(downloadFilename, downloadFileId, newFileName);
        } catch (DomainException e) {
            log.error("download failed...", e);
            return false;
        }

    }

    /**
     * 获取CTSP本地文件(压缩文件自动解压)
     * modified by zhengsm 2013-12-20
     * @param fileName
     * @return
     */
    public File getLocalFile(String fileName,String outFile) throws CoreSystException {
    	if(StringUtils.isBlank(fileName)) throw new CoreSystException("0941", "文件不存在");
    	String clientProperty = ClientConfig.HomeDir + File.separator + "etc/client.properties";
        if (!(new File(clientProperty).exists())) {
          log.error("客户端配置文件不存在[" + clientProperty + "]");
          throw new CoreSystException("0921", "客户端配置文件不存在");
        }
        if (!(new File(clientProperty).canRead())) {
          log.error("客户端配置文件不可读[" + clientProperty + "]");
          throw new CoreSystException("0921", "客户端配置文件不可读");
        }
		Properties p = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(clientProperty));
			if (in == null) {
				log.error("打开配置文件失败:[" + clientProperty + "]");
				throw new CoreSystException("0921", "打开配置文件失败");
			}
			p.load(in);
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				log.error("读取配置文件[" + clientProperty + "]失败");
				throw new CoreSystException("0921", "读取配置文件失败");
			}
		} catch (FileNotFoundException e) {
			throw new CoreSystException("0920", "配置文件不存在");
		} catch (IOException e) {
			throw new CoreSystException("0921", "读取配置文件失败");
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				log.error("读取配置文件[" + clientProperty + "]失败");
				throw new CoreSystException("0921", "读取配置文件失败");
			}
		}
		String localFilePath = p.getProperty("LocalFilePath");
		if (StringUtils.isBlank(localFilePath)||!(new File(localFilePath).exists())) {
		      log.error("上传下载文件目录为空 LocalFilePath");
		      throw new CoreSystException("0922", "上传下载文件目录为空 LocalFilePath");
		}
		int pos = fileName.indexOf(".Z");
		if(pos!=-1){
			try{
				ZuncompressUtil.uncompressUtil(localFilePath+File.separatorChar+fileName,outFile);
				fileName = fileName.substring(0, pos);
			}catch(Exception e){
				 throw new CoreSystException("0922", "解压文件"+fileName+"出错");
			}
		}
        return new File(localFilePath+File.separatorChar+fileName);
    }
}
