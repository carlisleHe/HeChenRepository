package com.newland.wechat.post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alipay.api.internal.util.AlipaySignature;
import com.cib.alipay.env.AlipayApplication;
import com.cib.alipay.resp.Resp;
import com.intensoft.exception.AppBizException;
import com.newland.wechat.post.model.Menu;
/**
 * 支付宝服务交互工具类
 * @author Shizn
 *
 */
public class AlipayPostUtils {
	private static final Logger logger = LoggerFactory.getLogger(AlipayPostUtils.class);
	
	/**
	 * 默认编码
	 */
	@Value(value = "default_encoding")
	public final static String DEFAULT_ENCODING = "GBK";
	/**
	 * 消息网关地址
	 */
	public static final String URL_GATE_WAY = "https://openapi.alipay.com/gateway.do";
	/**
	 * 发送客户消息SERVICE
	 */
	public static final String SERVICE_SEND_CUSTOME_MESSAGE = "alipay.mobile.public.message.custom.send";
	/**
	 * 添加菜单 
	 */
	public static final String SERVICE_MENU_ADD = "alipay.mobile.public.menu.add";
	/**
	 * 更新菜单
	 */
	public static final String SERVICE_MENU_UPDATE = "alipay.mobile.public.menu.update";
	/**
	 * 查询菜单
	 */
	public static final String SERVICE_MENU_GET = "alipay.mobile.public.menu.get";
	/*
	 * TOEKN
	 */
	public static final String SERVICE_OAUTH_TOKEN = "alipay.system.oauth.token";
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   

	
	/**
	 * 通过httpclient池 发送客户消息
	 * @param url
	 * @param proxy
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public static void postCustomerMessageByPool(Resp resp) throws Exception{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("method", SERVICE_SEND_CUSTOME_MESSAGE));
		list.add(new BasicNameValuePair("version", "1.0"));
		list.add(new BasicNameValuePair("charset", DEFAULT_ENCODING));
		list.add(new BasicNameValuePair("sign_type", "RSA"));
		String content = AlipayJsonUtils.convertToJson(resp, false);
		list.add(new BasicNameValuePair("biz_content",content));
		list.add(new BasicNameValuePair("app_id",AlipayApplication.getAppId()));
		list.add(new BasicNameValuePair("timestamp",sdf.format(new Date())));
		list.add(new BasicNameValuePair("sign", AlipaySignature.rsaSign(CompareUtil.calcStr(list), AlipayApplication.getCusPrivateKey(), DEFAULT_ENCODING)));
		
		String json = HttpAlipayUtil.submitRequestListByPool(URL_GATE_WAY, list, DEFAULT_ENCODING);
		checkResultCode(json,SERVICE_SEND_CUSTOME_MESSAGE);
	}
	
	/**
	 * 增加菜单
	 * @param url
	 * @param proxy
	 * @param accessToken
	 * @param button
	 * @return
	 * @throws Exception
	 */
	public static void addMenu(Menu button) throws Exception{
		
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("method", SERVICE_MENU_ADD));
		list.add(new BasicNameValuePair("version", "1.0"));
		list.add(new BasicNameValuePair("charset", DEFAULT_ENCODING));
		list.add(new BasicNameValuePair("sign_type", "RSA"));
		String content = AlipayJsonUtils.convertToJson(button, true);
		list.add(new BasicNameValuePair("biz_content",content));
		list.add(new BasicNameValuePair("app_id",AlipayApplication.getAppId()));
		list.add(new BasicNameValuePair("timestamp",sdf.format(new Date())));
		list.add(new BasicNameValuePair("sign", AlipaySignature.rsaSign(CompareUtil.calcStr(list), AlipayApplication.getCusPrivateKey(), DEFAULT_ENCODING)));
		String json = HttpAlipayUtil.submitRequestListByPool(URL_GATE_WAY, list, DEFAULT_ENCODING);
		checkResultCode(json,SERVICE_MENU_ADD);
	}
	/**
	 * 更新菜单
	 * @param url
	 * @param proxy
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static void updateMenu(Menu button) throws Exception{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("method", SERVICE_MENU_UPDATE));
		list.add(new BasicNameValuePair("version", "1.0"));
		list.add(new BasicNameValuePair("charset", DEFAULT_ENCODING));
		list.add(new BasicNameValuePair("sign_type", "RSA"));
		String content = AlipayJsonUtils.convertToJson(button, false);
		list.add(new BasicNameValuePair("biz_content",content));
		list.add(new BasicNameValuePair("app_id",AlipayApplication.getAppId()));
		list.add(new BasicNameValuePair("timestamp",sdf.format(new Date())));
		list.add(new BasicNameValuePair("sign", AlipaySignature.rsaSign(CompareUtil.calcStr(list), AlipayApplication.getCusPrivateKey(), DEFAULT_ENCODING)));
		String json = HttpAlipayUtil.submitRequestListByPool(URL_GATE_WAY, list, DEFAULT_ENCODING);
		checkResultCode(json,SERVICE_MENU_UPDATE);
	}
	/**
	 * 更新菜单
	 * @param url
	 * @param proxy
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static boolean isExitMenu() throws Exception{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("method", SERVICE_MENU_GET));
		list.add(new BasicNameValuePair("version", "1.0"));
		list.add(new BasicNameValuePair("charset", DEFAULT_ENCODING));
		list.add(new BasicNameValuePair("sign_type", "RSA"));
		list.add(new BasicNameValuePair("app_id",AlipayApplication.getAppId()));
		list.add(new BasicNameValuePair("timestamp",sdf.format(new Date())));
		list.add(new BasicNameValuePair("sign", AlipaySignature.rsaSign(CompareUtil.calcStr(list), AlipayApplication.getCusPrivateKey(), DEFAULT_ENCODING)));
		String json = HttpAlipayUtil.submitRequestListByPool(URL_GATE_WAY, list, DEFAULT_ENCODING);
		String resp = SERVICE_MENU_GET.replaceAll("\\.", "_")+"_response";
		
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject o  = jsonObject.getJSONObject(resp);
			String c = o.getString("menu_content");
			if(StringUtils.isNotBlank(c)){
				return true;
			}
		} catch (JSONException e) {
			logger.error(e.getMessage(),e);
		}
		return false;
	}
	/**
	 * 
	 * @param code
	 * @return
	 * @throws AppBizException
	 */
	public static String getOauthOpenId(String code) throws AppBizException{
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("method", SERVICE_OAUTH_TOKEN));
		list.add(new BasicNameValuePair("version", "1.0"));
		list.add(new BasicNameValuePair("charset", DEFAULT_ENCODING));
		list.add(new BasicNameValuePair("sign_type", "RSA"));
		list.add(new BasicNameValuePair("grant_type","authorization_code"));
		list.add(new BasicNameValuePair("code",code));
		list.add(new BasicNameValuePair("app_id",AlipayApplication.getAppId()));
		list.add(new BasicNameValuePair("timestamp",sdf.format(new Date())));
		try{
			list.add(new BasicNameValuePair("sign", AlipaySignature.rsaSign(CompareUtil.calcStr(list), AlipayApplication.getCusPrivateKey(), DEFAULT_ENCODING)));
			String json = HttpAlipayUtil.submitRequestListByPool(URL_GATE_WAY, list, DEFAULT_ENCODING);
			JSONObject response = new JSONObject(json);
			JSONObject resp = response.getJSONObject("alipay_system_oauth_token_response");
			if(resp==null){
				throw new AppBizException("", "openid 获取失败！支付宝未返回！");
			}
			String openId = resp.getString("alipay_user_id");
			return openId;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new AppBizException("", String.format("openId 获取失败！原因：[%s]",e.getMessage()));
		}
	}
	/**
	 * 检查返回信息
	 * @param res
	 * @throws Exception
	 */
	private static void checkResultCode(String json,String service) throws AppBizException {
		String resp = service.replaceAll("\\.", "_")+"_response";
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			JSONObject o = null;
			try{
				o = jsonObject.getJSONObject(resp);
				if(o.getInt("code")!=200){
					logger.error(String.format("消息发送失败！错误码:[%s],错误原因:[%s]", o.getInt("code"),o.getString("msg")));
				}
			}
			catch(JSONException e){
				o = jsonObject.getJSONObject("error_response");
				logger.error(String.format("消息发送失败！错误码:[%s],错误原因:[%s],详细错误码:[%s]，详细原因:[%s]", o.getInt("code"),o.getString("msg"),o.getString("sub_code"),o.getString("sub_msg")));
				
			}
			
		} catch (JSONException e) {
			logger.error(e.getMessage(),e);
		}
		
	}
	
	
}
