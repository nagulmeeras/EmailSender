package com.traveldesk.emailservice;

import org.apache.velocity.VelocityContext;
import org.springframework.context.annotation.Bean;

public class BeanFactory {
	private VelocityContext velocityContext;
	private static BeanFactory beanFactory;
	
	private BeanFactory() {
	}
	
	@Bean
	public VelocityContext getVelocityContext() {
		if(velocityContext == null){
			velocityContext = new VelocityContext();
		}
		return velocityContext;
	}
	public static BeanFactory getInstance(){
		if(beanFactory == null){
			beanFactory = new BeanFactory();
		}
		return beanFactory;
	}
	
}
