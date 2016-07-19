package com.pramati.emailsender;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pramati.emailsender.appconfig.ApplicationConfig;
import com.pramati.emailsender.beans.TravelInfo;

@Component
public class TemplateHandler {
	private VelocityEngine velocityEngine;
	
	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		System.out.println("in velocityEngine");
		this.velocityEngine = velocityEngine;
	}

	public Template getTemplate(String templateType, String triggerName) throws Exception {

		Template template = velocityEngine
				.getTemplate(ApplicationConfig.getPropertyValue("app.file.templates.path") + "/" + triggerName);
		return template;
	}

	public String[] populateData(Template template, TravelInfo travelInfo) throws Exception {
		VelocityContext velocityContext = BeanFactory.getInstance().getVelocityContext();

		velocityContext.put("requestId", travelInfo.getRequestId());
		velocityContext.put("employeeName", travelInfo.getEmployee().getName());
		velocityContext.put("workLocation", travelInfo.getWorkLocation());

		velocityContext.put("tripsDetails", travelInfo.getTrips());
		velocityContext.put("travellersDetails", travelInfo.getTravellers());
		velocityContext.put("otherDetails", travelInfo.getEmployee());
		velocityContext.put("facilities", travelInfo.getFacilites());

		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		String content = stringWriter.toString();
		String mailContetns[] = content.split("MailBody:");
		return mailContetns;
	}

	public String populateSubjectData(Template template) throws Exception {
		VelocityContext velocityContext = BeanFactory.getInstance().getVelocityContext();
		velocityContext.put("requestId", "12603");

		StringWriter stringWriter = new StringWriter();
		template.merge(velocityContext, stringWriter);
		return stringWriter.toString();
	}

}
