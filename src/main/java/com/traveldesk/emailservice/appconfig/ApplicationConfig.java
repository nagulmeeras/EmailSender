package com.traveldesk.emailservice.appconfig;

import java.io.IOException;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@ComponentScan("com.traveldesk.emailservice")
@PropertySource(value = {"classpath:dl.properties" ,"classpath:mail.properties"})
public class ApplicationConfig {

	@Value("${MAIL_SMTP_USERNAME}")
	private String username;
	@Value("${MAIL_SMTP_PASSWORD}")
	private String password = "my3solutions";

	@Value("${MAIL_SMTP_AUTH}")
	private String mailSmtpAuth;

	@Value("${MAIL_SMTP_STARTTLS_ENABLE}")
	private String mailSmtpStarttlsEnable;

	@Value("${MAIL_SMTP_HOST}")
	private String mailSmtpHost;

	@Value("${MAIL_SMTP_PORT}")
	private String mailSmtpPort;

	private static Environment env;

	@Autowired
	public void setEnv(Environment env) {
		ApplicationConfig.env = env;
	}

//	@Bean
//	public static PropertyPlaceholderConfigurer placeholderConfigurer() throws IOException {
//		FileSystemResource resource;
//
//		PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
//
//		resource = new FileSystemResource("classpath:mail.properties");
//
//		if (resource.exists()) {
//			System.out.println("mail properties found");
//		} else {
//			System.out.println("mail.properties not found");
//		}
//
//		placeholderConfigurer.setLocation(resource);
//		placeholderConfigurer.setIgnoreResourceNotFound(true);
//		placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
//		placeholderConfigurer.setLocalOverride(false);
//		return placeholderConfigurer;
//	}

	@Bean
	 public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
	  return new PropertySourcesPlaceholderConfigurer();
	 }
	
	@Bean
	public Session getMailSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", mailSmtpAuth);
		props.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
		props.put("mail.smtp.host", mailSmtpHost);
		props.put("mail.smtp.port", mailSmtpPort);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		return session;

	}

	public static String getPropertyValue(String propertyName) {
		return env.getProperty(propertyName);
	}

	@Bean
	public VelocityEngine velocityEngine() throws VelocityException, IOException {
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader." + "ClasspathResourceLoader");
		factory.setVelocityProperties(props);
		System.out.println("creating velocity engine ");
		return factory.createVelocityEngine();
	}

}