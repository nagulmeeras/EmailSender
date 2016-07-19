package com.traveldesk.emailservice.appconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@EnableWebMvc
@Configuration
@ComponentScan("com.traveldesk.emailservice")
public class WebConfig extends WebMvcConfigurerAdapter{
	
}
