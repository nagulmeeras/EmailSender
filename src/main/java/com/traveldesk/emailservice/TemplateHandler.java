package com.traveldesk.emailservice;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.traveldesk.emailservice.appconfig.ApplicationConfig;
import com.traveldesk.emailservice.beans.TravelInfo;

@Component
public class TemplateHandler {
	private VelocityEngine velocityEngine;
	private static final Logger logger = Logger.getLogger(TemplateHandler.class);
	
	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		logger.debug("Velocity object injected");
		this.velocityEngine = velocityEngine;
	}

	public Template getTemplate(String templateType, String triggerName) throws ResourceNotFoundException, ParseErrorException, Exception  {

		Template template = velocityEngine
				.getTemplate(ApplicationConfig.getPropertyValue("app.file.templates.path") + "/" + triggerName);
		logger.debug("velocity template loaded");
		return template;
	}

	public String[] populateData(Template template, TravelInfo travelInfo) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException {
		VelocityContext velocityContext = BeanFactory.getInstance().getVelocityContext();
		logger.debug("Velocity context object created");
		
		velocityContext.put("requestId", travelInfo.getRequestId());
		velocityContext.put("employeeName", travelInfo.getEmployee().getName());
		velocityContext.put("workLocation", travelInfo.getWorkLocation());

		velocityContext.put("tripsDetails", travelInfo.getTrips());
		velocityContext.put("travellersDetails", travelInfo.getTravellers());
		
		logger.debug(travelInfo.getEmployee().toString());
		
		velocityContext.put("otherDetails", travelInfo.getEmployee());
		velocityContext.put("facilities", travelInfo.getFacilites());

		logger.debug("Populated data");
		
		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		logger.debug("data populated to template");
		
		String content = stringWriter.toString();
		String mailContetns[] = content.split("MailBody:");
		return mailContetns;
	}

}
