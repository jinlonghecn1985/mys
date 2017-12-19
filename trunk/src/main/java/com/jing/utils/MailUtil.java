package com.jing.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.jing.platform.model.entity.EmailMsg;
import com.jing.platform.service.EmailMsgService;

@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailUtil {

	private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailMsgService emailMsgService;

	private boolean ispass;

	private String mailfrom;

	public String getMailfrom() {
		return mailfrom;
	}

	public void setMailfrom(String mailfrom) {
		this.mailfrom = mailfrom;
	}

	public boolean isIspass() {
		return ispass;
	}

	public void setIspass(boolean ispass) {
		this.ispass = ispass;
	}

	/**
	 * 
	 * @Title: sendSimpleMail
	 * @Description: 发送简单邮件
	 * @throws MessagingException
	 *             void
	 * @author: li chao
	 * @throws ExecutionException
	 */
	@Async
	public Future<Integer> sendSimpleMail(EmailMsg emailMsg) throws ExecutionException {
		AsyncResult<Integer> rsyncResult = null;
		if (!this.ispass) {
			logger.debug("mail is not pass");
			rsyncResult = new AsyncResult<>(0);
		} else {
			try {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom(mailfrom);
				helper.setTo(emailMsg.getEmail());
				helper.setSubject(emailMsg.getTitle());
				helper.setText(emailMsg.getDetail());
				mailSender.send(mimeMessage);
				rsyncResult = new AsyncResult<>(1);
			} catch (MessagingException e) {
				rsyncResult = new AsyncResult<>(0);
			}
		}
		// 保存emailMsg到数据库
		emailMsg.setStatus(rsyncResult.get());
		emailMsg.setMsgId(null);
		emailMsgService.addEmailMsg(emailMsg);
		return rsyncResult;
	}
}
