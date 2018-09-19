package com.eric.springasync.config;


import java.util.concurrent.CompletableFuture;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectConfig {

	private Logger log = LoggerFactory.getLogger(AspectConfig.class);
	
	@Pointcut("execution(* com.eric.springasync.service.*.*(..)) && @annotation(org.springframework.scheduling.annotation.Async)")
	public void asyncMethod() {}
	
	@Around("asyncMethod()")
	public Object execute(ProceedingJoinPoint pjp) throws Throwable {
		Object result = CompletableFuture.completedFuture(null);
		try {
			result = pjp.proceed();
		} catch (Exception e) {
			log.info("Exception:"+e.getMessage());
		}
		return result;
	}
}
