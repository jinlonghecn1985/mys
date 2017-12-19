package com.jing.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.jing.platform.service.ProductAuthService;


/**
 * @ClassName: SchedulingConfig
 * @Description: 定时任务配置类
 * @author: Jinlong He
 * @date: 2017年11月16日
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
	private static final Logger logger = LoggerFactory.getLogger(SchedulingConfig.class);

	@Autowired
	private ProductAuthService productAuthService;
	
	
	
	/** 
	* @Title: updateProductAuthOutTime 
	* @Description: 定时任务-0点7分
	* @throws 
	*/
	@Scheduled(cron = "0 7 0 * * ?")
	public void updateProductAuthOutTime() {
		try {
			productAuthService.updateProductAuthOutTime();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("定时任务执行发生错误：ProductAuth状态更新" + e.getMessage());
		}
	}
	
	
	
}
