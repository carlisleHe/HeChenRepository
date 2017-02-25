package com.newland.wechat.post;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.intensoft.exception.AppBizException;
import com.newland.base.post.HttpUtil;
import com.newland.wechat.post.model.CustList;
import com.newland.wechat.post.model.DataWrapper;
import com.newland.wechat.post.model.Groups;
import com.newland.wechat.post.model.Media;
import com.newland.wechat.post.model.Menu;
import com.newland.wechat.post.model.NotifyMessage;
import com.newland.wechat.post.model.PostResult;
import com.newland.wechat.post.model.WeixinUser;
import com.newland.wechat.resp.Resp;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
/**
 * 微信服务交互工具类
 * @author Shizn
 *
 */
public class WeixinPostUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(WeixinPostUtils.class);
	/**
	 * 上传文件url
	 */
	public static final String UPLOAD_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload";
	/**
	 * 文件下载url
	 */
	public static final String DOWNLOAD_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get";
	/**
	 * 默认编码
	 */
	@Value(value = "default_encoding")
	public final static String DEFAULT_ENCODING = "UTF-8";  

   

	/**
	 * 动户通知接口
	 * @param url
	 * @param accessToken
	 * @param msg 动户通知消息对象
	 * @param data 动户通知的字段数据
	 * @return
	 * @throws Exception
	 */
	public static PostResult sendNotifyMessage(String url, String accessToken, NotifyMessage msg, DataWrapper data) throws Exception{
		try {
			Assert.notNull(url, "请求地址不能为空");
			Assert.notNull(accessToken, "accessToken不能 为空");
			Assert.notNull(msg, "消息类不能 为空");
			String param = "access_token={0}";
			url = setParam(url, param, accessToken);
			msg.setData(data.toMap());
			String json = WeixinJsonUtils.convertToJson(msg, true);
			String result = HttpWechatUtil.submitRequestByPool(url, json,
					DEFAULT_ENCODING);
			if (result.equals("hbok!")) {
				return null;
			}
			PostResult res = WeixinJsonUtils.convertFromJson(result,
					PostResult.class);
			if (StringUtils.isNotBlank(res.getErrMsg())) {
				checkResultCode(res);
				return res;
			} else {
				return null;
			}
		} catch (Exception e) {
			LOG.error("发送消息发生错误:" + e.getMessage(), e);
			throw e;
		}
	}
	/**
	 * 用户组操作
	 * @param url
	 * @param accessToken
	 * @param groups
	 * @return
	 * @throws Exception
	 */
	public static Groups groupsOper(String url, String accessToken, Groups groups) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		Assert.notNull(groups, "groups不能 为空");
		String param = "access_token={0}";
		url = setParam(url, param, accessToken);
		String json = WeixinJsonUtils.convertToJson(groups, true);
		String result = HttpWechatUtil.submitRequestByPool(url, json, DEFAULT_ENCODING);
		PostResult res = WeixinJsonUtils.convertFromJson(result, PostResult.class);
		if (StringUtils.isNotBlank(res.getErrMsg())){
			checkResultCode(res);
			return null;
		}else{
			return WeixinJsonUtils.convertFromJson(result, Groups.class);
		}
	}
	/**
	 * 查询分组列表
	 * @param url
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static final Groups queryGroups(String url, String accessToken) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		String param = "access_token={0}";
		url = setParam(url, param, accessToken);
		String result = HttpWechatUtil.submitRequestByPool(url, "", DEFAULT_ENCODING);
		PostResult res = WeixinJsonUtils.convertFromJson(result, PostResult.class);
		if (StringUtils.isNotBlank(res.getErrMsg())){
			checkResultCode(res);
			return null;
		}else{
			return WeixinJsonUtils.convertFromJson(result, Groups.class);
		}
	}

	
	/**
	 * 查询关注用户
	 * @param url
	 * @param accessToken
	 * @param nextOpenid
	 * @return
	 * @throws Exception
	 */
	public static CustList queryCustList(String url, String accessToken, String nextOpenid) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		String param = "access_token={0}&next_openid={1}";
		url = setParam(url, param, accessToken, (StringUtils.isBlank(nextOpenid))?"":nextOpenid);
		String result = HttpWechatUtil.submitRequestByPool(url, "", DEFAULT_ENCODING);
		PostResult res = WeixinJsonUtils.convertFromJson(result, PostResult.class);
		if (StringUtils.isNotBlank(res.getErrMsg())){
			checkResultCode(res);
			return null;
		}else{
			return WeixinJsonUtils.convertFromJson(result, CustList.class);
		}
	}
	
	/**
	 * 上传多媒体文件
	 * @param url
	 * @param proxy
	 * @param accessToken
	 * @param media
	 * @throws Exception 
	 */
	public static PostResult uploadMediaFile(String url, String accessToken, Media media) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		Assert.notNull(media, "媒体参数不能为空");
		String param = "access_token={0}&type={1}";
		url = setParam(url, param, accessToken, media.getMediaType().name());
		String result = HttpUtil.uploadFile(url, media.getFile());
		PostResult res = WeixinJsonUtils.convertFromJson(result, PostResult.class);
		checkResultCode(res);
		return res;
	}
	/**
	 * 下载媒体文件
	 * @param url
	 * @param proxy
	 * @param accessToken
	 * @param mediaId
	 * @return
	 * @throws Exception 
	 */
	public static PostResult downloadMediaFile(String url, String accessToken, String mediaId, String localFile) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		Assert.notNull(localFile, "本地文件不能为空");
		String param = "access_token={0}&media_id={1}";
		url = setParam(url, param, accessToken, mediaId);
		HttpUtil.downloadFile(url, localFile);
		return new PostResult();
	}
	private static String setParam(String url, String content, String... params) {
		MessageFormat mf = new MessageFormat(content);
		String param = mf.format(params);
		if (url.indexOf("?") >= 0){
			url = url + "&" + param;
		}else{
			url = url + "?" + param;
		}
		return url;
	}
	/**
	 * 发送客户消息
	 * @param url
	 * @param proxy
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public static PostResult postCustomerMessage(String url, String accessToken, Resp msg) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		Assert.notNull(msg, "客服消息不能为空");
		String param = "access_token={0}";
		url = setParam(url, param, accessToken);
		String content = WeixinJsonUtils.convertToJson(msg, true);
		String json = HttpUtil.submitRequest(url, content, DEFAULT_ENCODING);
		PostResult res = WeixinJsonUtils.convertFromJson(json, PostResult.class);
		checkResultCode(res);
		return res;
	}
	
	/**
	 * 通过httpclient池 发送客户消息
	 * @param url
	 * @param proxy
	 * @param resp
	 * @return
	 * @throws Exception 
	 */
	public static PostResult postCustomerMessageByPool(String url, String accessToken, Resp msg) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		Assert.notNull(msg, "客服消息不能为空");
		String param = "access_token={0}";
		url = setParam(url, param, accessToken);
		String content = WeixinJsonUtils.convertToJson(msg, true);
		String json = HttpWechatUtil.submitRequestByPool(url, content, DEFAULT_ENCODING);
		PostResult res = WeixinJsonUtils.convertFromJson(json, PostResult.class);
		checkResultCode(res);
		return res;
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
	public static PostResult addMenu(String url , String accessToken, Menu button) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		Assert.notNull(button, "菜单对象不能为空");
		String param = "access_token={0}";
		url = setParam(url, param, accessToken);
		String content = "";
		if (button != null){
			content = WeixinJsonUtils.convertToJson(button, true);
			LOG.debug(content);
		}
		String json = HttpWechatUtil.submitRequestByPool(url, content, DEFAULT_ENCODING);
		PostResult res = WeixinJsonUtils.convertFromJson(json, PostResult.class);
		checkResultCode(res);
		return res;
	}
	
	/**
	 * 检查返回信息
	 * @param res
	 * @throws Exception
	 */
	private static void checkResultCode(PostResult res) throws AppBizException {
		if (res.getErrCode() != 0){
			throw new AppBizException(String.valueOf(res.getErrCode()), res.getErrMsg());
		}
		
	}
	/**
	 * 查询菜单
	 * @param url
	 * @param proxy
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static Menu queryMenu(String url, String accessToken) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		String param = "access_token={0}";
		url = setParam(url, param, accessToken);
		String json = HttpWechatUtil.submitRequestByPool(url, "", DEFAULT_ENCODING);
		Menu res = WeixinJsonUtils.convertFromJson(json, Menu.class); 
		return res;
	}
	
	/**
	 * 删除菜单
	 * @param url
	 * @param proxy
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static PostResult delMenu(String url, String accessToken) throws Exception{
		return addMenu(url, accessToken, null);
	}
	
	/**
	 * 获取AccessToken
	 * @param url
	 * @param proxy
	 * @param appId
	 * @param secret
	 * @return
	 * @throws AppBizException TODO
	 */
	public static PostResult getAccessToken(String url,String appId, String secret) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(appId, "应用Id不能 为空");
		Assert.notNull(secret, "应用密钥不能为空");
		String param = "grant_type=client_credential&appid={0}&secret={1}";
		url = setParam(url, param, appId, secret);
		String result = HttpWechatUtil.submitRequestByPool(url, "", DEFAULT_ENCODING);
		PostResult res = WeixinJsonUtils.convertFromJson(result, PostResult.class);
		checkResultCode(res);
		return res;
	}
	
	/**
	 * 查询微信客户基本信息
	 * @param url
	 * @param proxy
	 * @param accessToken
	 * @param openid
	 * @param lang
	 * @return
	 * @throws Exception
	 */
	public static WeixinUser getUserInfo(String url, String accessToken, String openid, String lang)throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(accessToken, "accessToken不能 为空");
		Assert.notNull(openid, "用户ID不能为空");
		Assert.notNull(lang, "应用密钥不能为空");
		String param = "access_token={0}&openid={1}&lang={2}";
		url = setParam(url, param, accessToken, openid, lang);
		String json = HttpWechatUtil.submitRequestByPool(url, "", DEFAULT_ENCODING);
		if (json.indexOf("errcode") >= 0){
			PostResult res = WeixinJsonUtils.convertFromJson(json, PostResult.class);
			checkResultCode(res);
			return null;
		}else{
			return WeixinJsonUtils.convertFromJson(json, WeixinUser.class);
		}
	}
	
	public static PostResult getApiTicket(String url,String token) throws Exception{
		Assert.notNull(url, "请求地址不能为空");
		Assert.notNull(token, "AccessToken不能 为空");
		String param = "access_token={0}&type=jsapi";
		url = setParam(url, param, token);
		String result = HttpWechatUtil.submitRequestByPool(url, "", DEFAULT_ENCODING);
		PostResult res = WeixinJsonUtils.convertFromJson(result, PostResult.class);
		checkResultCode(res);
		return res;
	}
}
