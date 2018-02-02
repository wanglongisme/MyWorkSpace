package com.isay.aop;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InsertAndUpdateAop {
	
	Logger log = Logger.getRootLogger();
	@After("execution(* com.isay.dao.*.update*(*))")
	public void updateAfter(JoinPoint jp){
		//method name
		String methodName = jp.getSignature().getName();
		List<Object> args = Arrays.asList(jp.getArgs());
		log.info("update - "+methodName+" - "+args);
	}
	
	@After("execution(* com.isay.dao.*.insert*(*))")
	public void InsertAfter(JoinPoint jp){
		//method name
		String methodName = jp.getSignature().getName();
		List<Object> args = Arrays.asList(jp.getArgs());
		log.info("insert - "+methodName+" - "+args);
	}
}
