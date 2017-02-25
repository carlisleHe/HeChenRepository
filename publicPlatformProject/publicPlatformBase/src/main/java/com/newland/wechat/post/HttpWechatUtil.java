package com.newland.wechat.post;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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

import com.newland.base.post.AbstractHttpUtil;
import com.newland.wechat.env.HttpWechatApplication;


/**
 * HTTP通讯工具类 用连接池，主要用于向微信服务器发送信息
 * 
 * @author hongye
 * @since 2014-09-17
 */

public class HttpWechatUtil extends AbstractHttpUtil {
	
private static final Logger logger = LoggerFactory.getLogger(HttpWechatUtil.class);
	
	private static ThreadSafeClientConnManager cm  = null;
	
	 static {
		   //设置访问协议 
	        cm = new ThreadSafeClientConnManager(SchemeRegistryFactory.createDefault());
	       //客户端总并行链接最大数 
	        cm.setMaxTotal(Integer.parseInt(HttpWechatApplication.getWxMaxTotal()));
	       //每个主机的最大并行链接数 
	        cm.setDefaultMaxPerRoute(Integer.parseInt(HttpWechatApplication.getWxMaxPerRoute()));
	      
	    }


	  public static DefaultHttpClient getPoolHttpClient() {
		       // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9 
				HttpParams params = new BasicHttpParams(); 
			    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1); 
			    HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1"); 
			    HttpProtocolParams.setUseExpectContinue(params, true); 	  

			   //设置请求超时 时间
				//设置等待数据超时时间

			    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.parseInt(HttpWechatApplication.getWxConnectionTimeout()));  
			    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, Integer.parseInt(HttpWechatApplication.getWxSoTimeout())); 
			     
				
				DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);  
				return httpClient;
	}
	  
	  /****
		 * 通过 httpclient连接池 
		 * 提交HTTP请求至服务端，并返回成功的响应结果，如果返回的状态码>400 统一抛出APPBIZEXCEPTION
		 * 
		 * @param httpUrl
		 * @param content
		 * @return
		 * @throws Exception
		 */
		public static String submitRequestByPool(String httpUrl,String content,
				String encoding) throws Exception {

			int statusCode = 0;

			HttpClient hc =  getPoolHttpClient();
			// 开发模式使用代理
			setProxy(hc);

			HttpPost httpPost = new HttpPost(httpUrl);
			httpPost.setHeader("Connection", "close");
			String result = "";
			HttpEntity entity = null;
			try {
				setSSLParam(hc);

				StringEntity stringEntity = new StringEntity(content, encoding);
				stringEntity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(stringEntity);// 将参数传入post方法中
				logger.info("post_url:" + httpUrl);
				logger.info("post_content:" + content);
				HttpResponse response = hc.execute(httpPost);
				statusCode = response.getStatusLine().getStatusCode();
				logger.info("post_result:" + statusCode);
				if (statusCode >= 400 || statusCode == 0) {
					throw new Exception(String.format("网络异常,状态码：[%s]",
							Integer.valueOf(statusCode)));
				}
				entity = response.getEntity();
				result = EntityUtils.toString(entity, encoding);
			} catch (Exception e) {
				httpPost.abort();
				logger.error(e.getMessage(), e);
				throw e;
			}
			finally{
				EntityUtils.consume(entity);
			}
			logger.info("收到响应内容:" + result);
			return result;
		}
		
		
		/**
		 * 提交HTTP请求至服务端，并返回成功的响应结果，如果返回的状态码>400
		 * 
		 * @param httpUrl
		 * @param nameValueList
		 * @param encoding
		 * @return
		 * @throws Exception
		 */
		public static String submitRequestListByPool(String httpUrl, 
				List<NameValuePair> nameValueList, String encoding) throws Exception {

			int statusCode = 0;

			HttpClient hc =  getPoolHttpClient();

			HttpPost httpPost = new HttpPost(httpUrl);
			httpPost.setHeader("Connection", "close");
			String result = "";
			HttpEntity resultEntity = null;
			try {
				setSSLParam(hc);
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						nameValueList, encoding);
				httpPost.setEntity(entity);// 将参数传入post方法中
				logger.debug("post_url:" + httpUrl);
				logger.info("post_content:" + nameValueList);
				HttpResponse response = hc.execute(httpPost);
				statusCode = response.getStatusLine().getStatusCode();
				logger.debug("post_result:" + statusCode);
				if (statusCode >= 400 || statusCode == 0) {
					throw new Exception("网络繁忙");
				}
				resultEntity = response.getEntity();
				result = EntityUtils.toString(resultEntity, encoding);
			} catch (Exception e) {
				httpPost.abort();
				throw e;
			}
			finally{
				EntityUtils.consume(resultEntity);
			}
			logger.info("post_response:" + result);
			return result;

		}
	  

}
