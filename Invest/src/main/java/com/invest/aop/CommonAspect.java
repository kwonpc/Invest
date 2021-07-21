package com.invest.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invest.dto.RequestDTO;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Component
public class CommonAspect {

	@Autowired
	private HttpServletRequest request;
		
	@Before("execution(* com.invest.controller.InvestController.invest(..)) || execution(* com.invest.controller.InvestController.myinvest(..))")
	public void getXUserId(JoinPoint joinPoint) {
		
		RequestDTO requestDTO = new RequestDTO();
		
		 for (Object arg: joinPoint.getArgs()) {
		      if (arg instanceof RequestDTO) {
		    	  requestDTO = (RequestDTO) arg;
		      }
		 }
		 
		 if(request.getHeader("X-USER-ID")!=null) {
			 requestDTO.setUserId(Long.parseLong(request.getHeader("X-USER-ID")));	 
		 }
		 
	}
}
