package com.newland.base.post;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.wechat.env.HttpProxyApplication;

/**
 * HTTP通讯工具类 用连接池，主要用于转发
 * 
 * @author hongye
 * @since 2014-09-17
 */

public class HttpProxyUtil extends AbstractHttpUtil {
	
private static final Logger logger = LoggerFactory.getLogger(HttpProxyUtil.class);
	
	private static ThreadSafeClientConnManager cm  = null;
	
	 static {
		   //设置访问协议 
	        cm = new ThreadSafeClientConnManager(SchemeRegistryFactory.createDefault());
	       //客户端总并行链接最大数 
	        cm.setMaxTotal(Integer.parseInt(HttpProxyApplication.getPyMaxTotal()));
	       //每个主机的最大并行链接数 
	        cm.setDefaultMaxPerRoute(Integer.parseInt(HttpProxyApplication.getPyMaxPerRoute()));
	      
	    }


	  public static DefaultHttpClient getPoolHttpClient() {
		       // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9 
				HttpParams params = new BasicHttpParams(); 
			    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1); 
			    HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1"); 
			    HttpProtocolParams.setUseExpectContinue(params, true); 	  

                //设置请求超时时间
				//设置等待数据超时时间
			    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.parseInt(HttpProxyApplication.getPyConnectionTimeout()));  
			    params.setParameter(CoreConnectionPNames.SO_TIMEOUT,Integer.parseInt(HttpProxyApplication.getPySoTimeout())); 
			     
				
				DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);  
				return httpClient;
	}
	
	/**
	 * 判断一个URL地址是否可连通
	 * 
	 * @param httpUrl
	 *            http url地址
	 * @return true表示可连通
	 */
	public static boolean isActiveByPool(String httpUrl) {


		HttpClient hc = getPoolHttpClient();
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("Connection", "close");
		boolean result = false;
		HttpEntity entity = null;
		try {
			setSSLParam(hc);

			StringEntity stringEntity = new StringEntity("test","UTF-8");
			stringEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(stringEntity);// 将参数传入post方法中
			logger.debug("post_url:" + httpUrl);
			HttpResponse response = hc.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.debug("post_result:" + statusCode);
			if(HttpStatus.SC_OK!=statusCode){  
				throw new Exception(String.format("网络异常,状态码：[%s]",
						Integer.valueOf(statusCode)));
			}
			entity = response.getEntity();
			EntityUtils.consume(entity);
			result = true;
		} catch (Exception e) {
			httpPost.abort();
			logger.error(e.getMessage(), e);
		} 
		finally{
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return result;
	}
	
	/****
	 *  通过线程池 取得HttpClient  提交HTTP请求至服务端，并返回成功的响应结果
	 *  主要用于转发 
	 * @param httpUrl
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String transmitByPool(String httpUrl,String content,String encoding) throws Exception {

		int statusCode = 0;

		HttpClient hc = getPoolHttpClient();
	
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("Connection", "close");
		String result = "";
		HttpEntity entity = null;
		try {
			setSSLParam(hc);
			setProxy(hc);
			StringEntity stringEntity = new StringEntity(content, encoding);
			//stringEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(stringEntity);// 将参数传入post方法中
			logger.debug("post_url:" + httpUrl);
			logger.info("post_content:" + content);
			HttpResponse response = hc.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			logger.debug("post_result:" + statusCode);
			entity = response.getEntity();
			result = EntityUtils.toString(entity, encoding);
			logger.info("收到响应内容:" + result);
			if (statusCode >= 400 || statusCode == 0) {
				throw new Exception(String.format("网络异常,状态码：[%s]",
						Integer.valueOf(statusCode)));
			}
			
		} catch (Exception e) {
			httpPost.abort();
			logger.error(e.getMessage(), e);
			throw e;
		}
		finally{
			EntityUtils.consume(entity);
		}
		return result;
	}
	
	/****
	 *  通过线程池 取得HttpClient  提交HTTP请求至服务端，并返回成功的响应结果
	 *  主要用于转发 
	 * @param httpUrl
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String transmitByPool(String httpUrl,String content,String encoding,boolean isProxy) throws Exception {

		int statusCode = 0;

		HttpClient hc = getPoolHttpClient();
	
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("Connection", "close");
		String result = "";
		HttpEntity entity = null;
		try {
			setSSLParam(hc);
			if(isProxy){
				setProxy(hc);
			}
			StringEntity stringEntity = new StringEntity(content, encoding);
			//stringEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(stringEntity);// 将参数传入post方法中
			logger.debug("post_url:" + httpUrl);
			logger.info("post_content:" + content);
			HttpResponse response = hc.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			logger.debug("post_result:" + statusCode);
			entity = response.getEntity();
			result = EntityUtils.toString(entity, encoding);
			logger.info("收到响应内容:" + result);
			if (statusCode >= 400 || statusCode == 0) {
				throw new Exception(String.format("网络异常,状态码：[%s]",
						Integer.valueOf(statusCode)));
			}
			
		} catch (Exception e) {
			httpPost.abort();
			logger.error(e.getMessage(), e);
			throw e;
		}
		finally{
			EntityUtils.consume(entity);
		}
		return result;
	}
	
	/****
	 *  通过线程池 取得HttpClient  提交HTTP请求至服务端，并返回成功的响应结果
	 *  主要用于转发 
	 * @param httpUrl
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String transmitByPoolPicture(String httpUrl,String content,String encoding,File file) throws Exception {
		int statusCode = 0;
		HttpClient hc = getPoolHttpClient();
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("Connection", "close");
		HttpEntity entity = null;
		OutputStream output = new FileOutputStream(file);
		FileInputStream fis = null;
		try {
			setSSLParam(hc);
			setProxy(hc);
			StringEntity stringEntity = new StringEntity(content, encoding);
			httpPost.setEntity(stringEntity);// 将参数传入post方法中
			logger.info("post_url:" + httpUrl);
			logger.info("post_content:" + content);
			HttpResponse response = hc.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			logger.info("statusCode:" + statusCode);
			if (statusCode >= 400 || statusCode == 0) {
				throw new Exception(String.format("网络异常,状态码：[%s]",
						Integer.valueOf(statusCode)));
			}
			String contentTypeStr = response.getFirstHeader("Content-Type").toString();
			 logger.info("contentTypeStr:" + contentTypeStr);
			 if(!contentTypeStr.contains("image/jpeg")){
				 entity = response.getEntity();
				 String result = EntityUtils.toString(entity, encoding);
				 logger.info("微信服务器中取图片失败，原因："+result);
				 return "fail";
			 } 
			 
			entity = response.getEntity();
			byte[] b =  EntityUtils.toByteArray(entity);
			output.write(b, 0, b.length);
			output.flush();
		} catch (Exception e) {
			httpPost.abort();
			logger.error(e.getMessage(), e);
			return "fail";
		}
		finally{
			EntityUtils.consume(entity);
			if(fis != null){
				fis.close();fis = null;
				}
			if(output != null){
				output.close();output = null;
			}
		}
		return "success";
	}
	



}
