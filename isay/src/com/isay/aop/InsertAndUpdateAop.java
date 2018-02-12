package com.isay.aop;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class InsertAndUpdateAop {
	
	Logger log = Logger.getRootLogger();
	@Before("execution(* com.isay.service.*.update*(*))")
	public Object updateBefore(JoinPoint jp){
		//method name
		String methodName = jp.getSignature().getName();
		List<Object> args = Arrays.asList(jp.getArgs());
		log.info("update - "+methodName+" - "+args);
	    return null;
	}
	
	@Before("execution(* com.isay.service.*.create*(*))")
	public void insertBefore(JoinPoint jp){
		//method name
		String methodName = jp.getSignature().getName();
		List<Object> args = Arrays.asList(jp.getArgs());
		log.info("create - "+methodName+" - "+args);
	}
	
	@Before("execution(* com.isay.service.*.del*(*))")
	public void delBefore(JoinPoint jp){
		//method name
		String methodName = jp.getSignature().getName();
		List<Object> args = Arrays.asList(jp.getArgs());
		log.info("del - "+methodName+" - "+args);
	}
}
