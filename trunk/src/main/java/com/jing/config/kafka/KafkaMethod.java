package com.jing.config.kafka;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface KafkaMethod {
	boolean send() default true;//是否发送消息
	String topic() default "";  //消息主题
	String key() default "";  //消息key
	enum operate{ INSERT,DEL,UPDATE};  //消息操作枚举
	operate operation() default operate.DEL;  //消息操作
}
