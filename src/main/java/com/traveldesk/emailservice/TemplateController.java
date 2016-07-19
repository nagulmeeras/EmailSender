package com.traveldesk.emailservice;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.traveldesk.emailservice.beans.TravelInfo;

@Component
public class TemplateController {

	private TemplateHandler templateHandler;
	private static final Logger logger = Logger.getLogger(TemplateController.class); 
	public TemplateHandler getTemplateHandler() {
		return templateHandler;
	}

	@Autowired
	public void setTemplateHandler(TemplateHandler templateHandler) {
		this.templateHandler = templateHandler;
	}

	public HashMap<String, String> loadTemplate(String triggerName, String workLocation, TravelInfo travelInfo)
			throws Exception {
		logger.debug("In loadTemplate method");
		logger.debug("Trigger Name :"+triggerName);
		logger.debug("Work location"+workLocation);
		
		HashMap<String, String> contentMap = new HashMap<String, String>();
		Template bodyTemplate = templateHandler.getTemplate("body", triggerName);
		String mailContent[] = templateHandler.populateData(bodyTemplate, travelInfo);
		if (mailContent.length > 0) {
			contentMap.put("body", mailContent[1].replace("MailBody:", ""));
			contentMap.put("subject", mailContent[0].replace("MailSubject:", ""));
		}else{
			logger.error("Unable to load the template file from request and trigger name");
			throw new Exception("Unable to load template");
		}
		return contentMap;
	}

}
