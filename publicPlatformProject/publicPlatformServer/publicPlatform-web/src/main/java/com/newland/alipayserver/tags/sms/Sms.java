/*
 * Sms.java
 * 功能：发送短信控件
 * 类名：Sms
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-2-25 	黄博飞         初版
 *  V1.2    2014-06-24  徐重振         扩展短信控件，增加autoJudge、btnResendValue以更好适用于手动发送短信情况
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.newland.alipayserver.tags.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.UIBean;

import com.newland.alipayService.message.MsgService;
import com.newland.base.util.SmsSecurity;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 发送短信控件
 *
 * @author 黄博飞
 * @version Ver 1.0 2011-2-25
 * @since Sms Ver 1.0
 */
public class Sms extends UIBean {

    public static final String DEF_LEFT_TIME = "120";
    public static final String DEF_LEFT_COUNT = "6";
    /**
     * 输入框控件p的Class
     */
    private String pClass;
    /**
     * 标签的Class
     */
    private String labelClass;
    /**
     * 标签显示
     */
    private String labelValue;
    /**
     * 短信输入框的Class
     */
    private String inputClass;
    /**
     * 输入框的值
     */
    private String inputValue;
    /**
     * 点击输入值
     */
    private String buttonValue;
    /**
     * 短信输入框ID
     */
    private String id;
    /**
     * 输入框最大值
     */
    private String maxLength;
    /**
     * 先发送后倒计时标志
     */
    private boolean sendLater;
    /**
     * 是否显示免费信息
     */
    private boolean freeInfo;
    /**
     * 短信是否为必输入 加*标示
     */
    private boolean noStar;
    /**
     * 页面手机号ID
     */
    private String telPhoneId;
    /**
     * 是否外部发送短信
     */
    private boolean fromOut;
    /**
     * 发送完成后显示文字
     */
    private String sentMsg;
    /**
     * 是否校验码(当前页验证)
     */
//    private boolean validSms;
    /**
     * 是否自动判断已发送 规则：适用于页面手动触发发送短信验证码，初始进页面时未发送短信页面可触发发送，
     * 下一步报错返回时验证码未失效时，页面显示验证码倒计时。 判断依据：是否能取到缓存的短信。 前提：初始进入含短信验证码页面时，先清除缓存的短信
     * -----------该属性可以不再使用了，用sendLater="true"替换----------
     */
    private boolean autoJudge;
    /**
     * 可重发或失效时发送按钮名称
     */
    private String btnResendValue;
	/**
	 * 发送短信校验码触发事件<br/>
	 * 不配置时，使用模板中默认的sendSms()方法<br/>
	 * 适用于：页面 手动触发短信校验码并且发送短信前需加自定义脚本判断，甚至异步调用时使用
	 */
	private String onClick;
	
	public String getOnClick() {
		return onClick;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
    /**
     * 回单查询
     * 手机短信方式发送受理单查询号
     * 当该属性为true时，sendLatter直接设置为true，页面不显示剩余时间和序号
     * 
     */
    private boolean receiptBusiness;

    public void setBtnResendValue(String btnResendValue) {
        this.btnResendValue = btnResendValue;
    }

    public void setAutoJudge(boolean autoJudge) {
        this.autoJudge = autoJudge;
    }

    public void setReceiptBusiness(boolean receiptBusiness) {
        this.receiptBusiness = receiptBusiness;
    }
    

//    public void setValidSms(boolean validSms) {
//        this.validSms = validSms;
//    }

    public void setSentMsg(String sentMsg) {
        this.sentMsg = sentMsg;
    }

    public void setFromOut(boolean fromOut) {
        this.fromOut = fromOut;
    }

    public void setTelPhoneId(String telPhoneId) {
        this.telPhoneId = telPhoneId;
    }

    public void setNoStar(boolean noStar) {
        this.noStar = noStar;
    }

    public void setFreeInfo(boolean freeInfo) {
        this.freeInfo = freeInfo;
    }


    public void setSendLater(boolean sendLater) {
        this.sendLater = sendLater;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setpClass(String pClass) {
        this.pClass = pClass;
    }

    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public void setInputClass(String inputClass) {
        this.inputClass = inputClass;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public Sms(ValueStack stack, HttpServletRequest request,
            HttpServletResponse response) {
        super(stack, request, response);
    }

    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }

    @Override
    protected String getDefaultTemplate() {
        return "sms";
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();
        addParameter("ctx", request.getContextPath());
        if (id != null) {
            addParameter("id", id);
        } else {
            addParameter("id", "hSendSms");
        }
        if (pClass != null) {
            addParameter("pClass", pClass);
        } else {
            addParameter("pClass", "form-p");
        }
        if (labelClass != null) {
            addParameter("labelClass", labelClass);
        } else {
            addParameter("labelClass", "form-label");
        }
        if (labelValue != null) {
            addParameter("labelValue", labelValue);
        } else {
            addParameter("labelValue", "lab.sms");
        }
        if (buttonValue != null) {
            addParameter("buttonValue", buttonValue);
        } else {
            addParameter("buttonValue", "btn.sms.again");
        }
        if (btnResendValue != null) {
            addParameter("btnResendValue", btnResendValue);
        } else {
            addParameter("btnResendValue", "btn.sms.again");
        }
        if (inputClass != null) {
            addParameter("inputClass", inputClass);
        } else {
            addParameter("inputClass", "smscode");
        }
        if (inputValue != null) {
            addParameter("inputValue", inputValue);
        }
        if (maxLength != null) {
            addParameter("maxLength", maxLength);
        } else {
            addParameter("maxLength", "6");
        }
        if (telPhoneId != null) {
            addParameter("telPhoneId", telPhoneId);
        }
        if (sentMsg != null) {
            addParameter("sentMsg", sentMsg);
        } else {
            addParameter("sentMsg", "has_sent_sms");
        }
		if(onClick != null){
			addParameter("onClick", onClick);
		} else {
			addParameter("onClick", "sendSms();");//默认是sendSms方法
		}
        if (autoJudge) {//autoJudge  可以用sendLater = "true"替换
            sendLater = true;
        }
        addParameter("receiptBusiness", receiptBusiness);
//        if(receiptBusiness){//回单查询
//            sendLater = true;
//            if(fromOut){
//            	super.request.getSession().setAttribute(MsgService.SMS_SENT,null);
//            }
//            else{
//            	NetbankUserDetails nud = LoginFormHolder.getUserDetail();
//                if (nud != null) {
//                     nud.getSessionContext().put(MsgService.SMS_SENT, null);
//                }
//            }
//        }
        addParameter("sendLater", sendLater);//如果标签中sendLater属性设置为true 那么session中的MsgService.SMS_SENT 值要先清空
        addParameter("freeInfo", freeInfo);
        addParameter("noStar", noStar);
        addParameter("fromOut", fromOut);
        SmsSecurity ss = null;
        boolean noSeqence = false;
//        if (fromOut) {
        ss = (SmsSecurity) super.request.getSession().getAttribute(MsgService.SMS_SENT);

//        } else {
//            NetbankUserDetails nud = LoginFormHolder.getUserDetail();
//            if (nud != null) {
//                ss = (SmsSecurity) nud.getSessionContext().get(MsgService.SMS_SENT);
//            }
//        }
        if (ss != null && StringUtils.isBlank(ss.getSeqence()) == false) {
            if(ss.getLeftTime()==0||"0".equals(ss.leftCount())){//已经失效了 不显示序号
            	noSeqence = true;
            }
            else{
            	noSeqence = ss.isShowSeqence() ? false : true;//第一次触发短信口令，页面不显示序号
            }
            addParameter("seqence", ss.getSeqence());
            addParameter("leftTime", ss.getLeftTime());
            
            addParameter("leftCount", ss.leftCount());
        } else {
            noSeqence = true;
            addParameter("leftTime", DEF_LEFT_TIME);

            addParameter("leftCount", DEF_LEFT_COUNT);
        }
        addParameter("sendLater", sendLater);
        addParameter("noSeqence", noSeqence);//设置是否显示短信序号
    }
}