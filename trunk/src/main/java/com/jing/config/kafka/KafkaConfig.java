package com.jing.config.kafka;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.jing.utils.StringUtil;


/**
 * 
 * @ClassName: KafkaConfig
 * @Description: 默认kafka消息配置类
 * @author: lichao
 * @date: 2017年2月13日 上午10:12:51
 */
@Configuration
@Component
public class KafkaConfig {
	
	@Value("${kafka.base.topic:\"\"}")
	private String topic;

	@Value("${kafka.base.issend:false}")
	private boolean send;
	
	@Value("${kafka.base.services:\"\"}")
	private String services;
	
	private static List<String> servicesList = Lists.newArrayList();
	
	public String getTopic() {
		return topic;
	}

	public boolean isSend() {
		return send;
	}

	public String getServices() {
		return services;
	}

	public List<String> getServicesList(){
		String[] mappers = this.services.split(";");
		if(KafkaConfig.servicesList.isEmpty()){
			for (String mapper : mappers) {
				StringUtil.toUpperCaseFirstOne(mapper);
				KafkaConfig.servicesList.add(mapper);
			}
		}
		return KafkaConfig.servicesList;
	}
	
}
