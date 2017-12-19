//package com.jing.config.kafka;
//
//import java.util.HashMap;
//import java.util.List;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Repository;
//
//import com.google.common.collect.Maps;
//import com.jing.base.service.BaseService;
//import com.jing.utils.JsonUtil;
//import com.jing.utils.SpringUtil;
//
//@Repository
//@Aspect
//@Order(2)
///** 
// * 把这个类声明为一个切面： 
// * 1. 使用注解“@Repository”把该类放入到IOC容器中 
// * 2. 使用注解“@Aspect”把该类声明为一个切面 
// * 3. 使用注解“@Order(number)”指定前面的优先级，值越小，优先级越高 
// */
//public class KafkaDaoInterceptor  {
//
//	private static final Logger logger = LoggerFactory.getLogger(KafkaDaoInterceptor.class);
//
//	@Autowired
//	KafkaConfig kafkaConfig;
//	
//	@Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//	
////	@Pointcut("@annotation(com.jing.config.kafka.KafkaMethod)")  
////    public void methodCachePointcut() {  
////		
////	}  
//	
////	@Before("@annotation(kafkaMethod)")  
////	public void Before(JoinPoint joinPoint,KafkaMethod kafkaMethod) {
////		logger.debug("kafka aop before");
////	}
//	
////	@After("@annotation(kafkaMethod)")  
////    public void After(JoinPoint joinPoint,KafkaMethod kafkaMethod) throws Throwable {
////		logger.debug("kafka aop After");
////	}
//	
////	@Around("@annotation(kafkaMethod)")  
////	public void Around(ProceedingJoinPoint joinPoint,KafkaMethod kafkaMethod) throws Throwable {
////		logger.debug("kafka aop Around");
////	}
//	
//	/**
//	 * 
//	* @Title: AfterRunning 
//	* @Description: aop结果返回后拦截
//	* 消息提供者用类名作为key，所以消费者在用可以消费时要以可以开始来判断是否是需要消费的消息
//	* @param joinPoint 拦截点
//	* @param result 返回结果
//	* @throws Throwable  void    返回类型 
//	* @throws
//	 */
//	@AfterReturning(value="execution(public * com.jing.base.service.impl.*.delete*(..))"
//			+ "||execution(public * com.jing.base.service.impl.*.insert*(..))"
//			+ "||execution(public * com.jing.base.service.impl.*.update*(..))",returning="result")  
//	public void AfterRunning(JoinPoint joinPoint,Object result) throws Throwable {
//		//获取拦截类名
//		Class<? extends Object> cls = joinPoint.getTarget().getClass();
//		//通过反射获取类对象
//		BaseService<?> baseService = (BaseService<?>)SpringUtil.getBean(cls);
//		//配置的消息主题
//		String topic = kafkaConfig.getTopic();
//		//配置的全局判断是否发送消息
//		boolean issend = kafkaConfig.isSend();
//		//配置的具体发送消息的service
//		List<String> mapperList = kafkaConfig.getServicesList();
//		//拦截方法所在类名
//		String impl = joinPoint.getTarget().getClass().getSimpleName().toString();
//		//拦截方法参数
//        Object args = joinPoint.getArgs()[0];
//		//拦截方法
//		String method = joinPoint.getSignature().getName();
//		int strlength = impl.length()-4;
//		impl = impl.substring(0,strlength);
//		String key = impl;
//		String operate = "";
//		Object data = "";
//		HashMap<String, String> datamap = Maps.newHashMap();
//		if(issend){
//			boolean contain = mapperList.contains(impl);
//			if(contain){
//				if(args instanceof Integer){
//					//执行方法
//					data = baseService.selectByPrimaryKey((Integer)args);
//				}else{
//					data = baseService.selectByPrimaryKey((Integer)result);
//				}
//				if(method.startsWith("update")){
//					operate = "UPDATE";
//				}else if(method.startsWith("insert")){
//					operate = "INSERT";
//				}else{
//					operate = "DEL";
//				}
//				datamap.put(operate, JsonUtil.object2json(data));
//				//开线程发送kafka消息
//				Thread thread = new Thread(){
//					String topic;
//					String key;
//					String data;
//					public Thread setData(String topic,String key,String data) {
//						this.topic = topic;
//						this.key = key;
//						this.data = data;
//						return this;
//					}
//					public void run() {  
//						kafkaTemplate.send(topic, key, data);
//						logger.info("kafka消息 topic:"+topic+"key："+key+"data:"+JsonUtil.map2json(datamap));
//					}
//				}.setData(topic,key,JsonUtil.map2json(datamap));
//				thread.start();
//			}
//		}
//	}
//	
////	@AfterThrowing(value="@annotation(kafkaMethod)",throwing="e")  
////	public void AfterThrowing(JoinPoint joinPoint,KafkaMethod kafkaMethod,Exception e) throws Throwable {
////		logger.debug("kafka aop AfterThrowing");
////	}
//}
