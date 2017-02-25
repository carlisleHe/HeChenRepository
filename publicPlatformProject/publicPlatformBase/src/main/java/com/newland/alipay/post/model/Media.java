package com.newland.alipay.post.model;

import java.io.File;
import java.io.Serializable;
/**
 * 上传媒体类型
 * @author Shizn
 *
 */
public class Media implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文件名
	 */
	private File file;
	/**
	 * 媒体类型
	 */
	private MediaType mediaType;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

	public MediaType getMediaType() {
		return mediaType;
	}
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	

}
