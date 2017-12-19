package com.jing.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.Future;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.jing.platform.model.entity.PhoneMsg;
import com.jing.platform.service.PhoneMsgService;
import com.jing.platform.controller.config.HttpMessages;
import com.jing.platform.controller.vo.HttpEntityVo;


/**
 * 
 * @ClassName: SmsUtil
 * @Description: 短信发送
 * @author: li chao
 * @date: 2017年6月23日 上午10:16:13
 */
@Component
@ConfigurationProperties(prefix = "spring.sms")
public class SmsUtil {

	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

	@Autowired
	private PhoneMsgService phoneMsgService;
	
	@Autowired
	private HttpMessages httpMessages;
	
	/**sms第三方url*/
	private String url;

	/**sms是否调用短信接口*/
	private boolean ispass;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isIspass() {
		return ispass;
	}

	public void setIspass(boolean ispass) {
		this.ispass = ispass;
	}

	/**
	 * 
	 * @Title: send
	 * @Description: 短信发送
	 * @param phone
	 *            接收手机号码
	 * @param detail
	 *            短信内容
	 * @return Future<HttpEntityVo>
	 * @author: li chao
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Async
	public Future<HttpEntityVo> send(PhoneMsg phoneMsg) throws ClientProtocolException, IOException {
		AsyncResult<HttpEntityVo> rsyncResult = null;
		Integer code = null;
		String smsUrl = MessageFormat.format(this.url, phoneMsg.getPhone(), phoneMsg.getDetail());
		logger.info(smsUrl);
		boolean pass = this.ispass;
		HttpEntityVo httpEntityVo = null;
		if(pass){
			httpEntityVo = HttpTool.simpleURIGet(smsUrl,httpMessages.getTimeOut());
			logger.debug("smsdata", httpEntityVo);
			if (null != httpEntityVo) {
			    logger.info(httpEntityVo.getEntity());
				String[] back = httpEntityVo.getEntity().split(",");
				code = Integer.valueOf(back[1].split("\n")[0]);
			} else {
				code = 500;
			}
		}else{
			httpEntityVo = new HttpEntityVo();
			code = 500;
			logger.debug("sms is not pass");
		}
		httpEntityVo.setCode(code);
		rsyncResult = new AsyncResult<>(httpEntityVo);
		//添加phoneMsg到数据库
		phoneMsg.setMsgId(null);
		phoneMsg.setStatus(code==500?0:1);
		phoneMsgService.addPhoneMsg(phoneMsg);
		return rsyncResult;
	}

	@Override
	public String toString() {
		return StringUtil.entityToString(this);
	}
}
