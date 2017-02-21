package com.newland.wechatServer.util;

import java.io.Serializable;

import com.cib.alipay.env.AlipayApplication;
import com.intensoft.exception.AppRTException;
/**
 * 短信安全对象
 * @author ShiZhenning
 *
 * @since 2011-7-7上午11:15:47
 */
public class SmsSecurity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_ERR_COUNT = 6; 
	
	public static final int DEFAULT_SMS_LENGTH = 6;
	/**
	 * 默认过期时间2分钟
	 * 
	 */
	public static long DEFAULT_MILLISECOND = 120000;
	/**
	 * 短信码
	 */
	private String smsCode;
	/**
	 * 短信序号
	 */
	private String seqence;
	/**
	 * 生成时毫秒数
	 */
	private long timemillisecond;
	/**
	 * 验证错误次数
	 */
	private int errCount = 0;
	/**
	 * 短信对象是否已失效<br/>
	 * 初衷：原验证完短信码后，直接清空Session中短信对象，但页面触发短信认证码的情况下，则会认为没有发送
	 * 过短信认证码而在后续处理报错返回页面时显示 <code>获取短信认证码</code> ，为了能正确判断该情况为短信已
	 * 失效，增加该标识位，当验证短信码成功时，置该标识为true，则页面显示已失效
	 * @author 徐重振 2015-04-17 
	 */
	private boolean invalidStatus;
        private boolean showSeqence = false;//默认不显示序号 第一次触发短信口令，页面不显示序号
	
	private int allowErrCount = DEFAULT_ERR_COUNT;
	
	public SmsSecurity(){
		this(DEFAULT_ERR_COUNT, DEFAULT_SMS_LENGTH);
	}
	
	public SmsSecurity(int allowErrCount, int smsLength){
		this.allowErrCount = allowErrCount;
		WordGenerator gen = new RandomWordGenerator("0123456789");
		this.timemillisecond = System.currentTimeMillis();
		this.smsCode = gen.getWord(smsLength);
		WordGenerator word = new RandomWordGenerator("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		String temp = word.getWord(1);
		temp += gen.getWord(2);
		this.seqence = temp;
		this.invalidStatus = false;
	}
	
	public boolean valid(String code){
		if (AlipayApplication.isPerformance()) return true;
		if (!isAvailability()) throw new AppRTException("SMS_EXPIRE", "短信码6次错误已失效");
		if (this.expire()) throw new AppRTException("SMS_INVALIDATION", "短信码超时已失效");
		if (this.isInvalidStatus()) throw new AppRTException("SMS_INVALIDATION", "短信码已失效");
		if (this.smsCode.equalsIgnoreCase(code)){
			//当前逻辑短信码验证一次后失效，只修改状态位确保，页面能够显示已失效提示
			invalidStatus=true;
			return true;
		}else{
			this.errCount++;
			return false;
		}
	}
	
	public boolean expire(){
		long sysmillisecond = System.currentTimeMillis();
		if ((sysmillisecond - this.timemillisecond) > DEFAULT_MILLISECOND){
			return true;
		}
		return false;
	}

	public String getSerials() {
		String temp = smsCode;
		temp += ", 顺序号：" + this.seqence + ""; 
		return temp;
	}
	
	public String getSmsCode(){
		return smsCode;
	}


	public boolean isAvailability() {
		return this.errCount < this.allowErrCount;
	}

	public String getSeqence() {
		return seqence;
	}

	public void setSeqence(String seqence) {
		this.seqence = seqence;
	}
	
	public static void main(String[] args){
		double begin = System.currentTimeMillis();
		for(int i = 0; i<1000000 ; i++){
			//System.err.println(sms.getSeqence() + "_" + sms.getSmsCode());
		}
		System.err.println((System.currentTimeMillis() - begin) + "ms");
	}

	public String leftCount() {
		return String.valueOf(allowErrCount - errCount);
	}
	/**
	 * 剩余时间（秒）
	 * @return
	 */
	public Integer getLeftTime() {
		if(isInvalidStatus()){//若状态已设为失效，则模板所需的剩余时间置为0，页面则直接显示为已失效；
			return 0;
		}
		long left = DEFAULT_MILLISECOND  + this.timemillisecond - System.currentTimeMillis() - 1;
		return (left < 0) ? 0 : (int)(left / 1000);
	}

	public boolean isInvalidStatus() {
		return invalidStatus;
	}

	public void setInvalidStatus(boolean invalidStatus) {
		this.invalidStatus = invalidStatus;
	}
	

        public boolean isShowSeqence() {
            return showSeqence;
        }

        public void setShowSeqence(boolean showSeqence) {
            this.showSeqence = showSeqence;
        }
}
