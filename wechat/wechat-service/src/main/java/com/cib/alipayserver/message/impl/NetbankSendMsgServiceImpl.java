package com.cib.alipayserver.message.impl;

import java.io.File;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cib.alipayserver.message.NetbankSendMsgService;
import com.cib.alipayserver.txn.outgoing.Tr3908.Tr3908Request;
import com.cib.alipayserver.txn.outgoing.Tr3909.Tr3909Request;
import com.cib.alipayserver.txn.service.TxnService;
import com.intensoft.exception.AppBizException;
@Service("netbankSendMsgService")
public class NetbankSendMsgServiceImpl implements NetbankSendMsgService {
	
	private static final Logger logger = LoggerFactory.getLogger(NetbankSendMsgServiceImpl.class);

	@Resource(name = "mailSender")
	MailSender mailSender;
	@Resource(name = "mailSender")
	private JavaMailSenderImpl javaMailSender;
	@Resource(name = "txnService")
	private TxnService txnService;
	@Override
	public void sendNormalSms(String mphone, String msg) throws AppBizException {
		Assert.hasText(mphone, "系统异常，获取手机号失败！");
		Tr3909Request req = new Tr3909Request();
		req.sjhm=mphone;
		req.dxnr=msg;
		txnService.doTr3909(req);
	}

	@Override
	public void sendAuthCodeSms(String mphone, String msg)
			throws AppBizException {
		Assert.hasText(mphone, "系统异常，获取手机号失败！");
		Tr3908Request req = new Tr3908Request();
		req.sjhm=mphone;
		req.dxnr=msg;
		txnService.doTr3908(req);
	}

	@Override
	public void sendMail(String mailAddr, String title, String mailContent) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(javaMailSender.getUsername());
		msg.setTo(mailAddr);
		msg.setSubject(title);
		msg.setText(mailContent);
		if (logger.isDebugEnabled()) {
			logger.debug(mailContent);
		}
		mailSender.send(msg);
	}

	@Override
	public void sendMail(String mailAddr, String title, String mailContent,
			File appendix) {
//		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//		try {
//			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			messageHelper.setFrom(javaMailSender.getUsername());
//			messageHelper.setTo(mailAddr);
//			messageHelper.setSubject(title);
//			messageHelper.setText(mailContent, true);
//			if (appendix != null && appendix.exists()) {
//				FileSystemResource file = new FileSystemResource(appendix);
//				messageHelper.addAttachment(file.getFilename(), file);
//			}
//		} catch (MessagingException e) {
//			if (logger.isDebugEnabled()) {
//				logger.debug("邮件发送失败！", e);
//			}
//		}
//		javaMailSender.send(mimeMessage);
	}

}
