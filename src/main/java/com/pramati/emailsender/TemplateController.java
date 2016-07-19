package com.pramati.emailsender;

import java.util.HashMap;

import org.apache.velocity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pramati.emailsender.beans.TravelInfo;

@Component
public class TemplateController {

	private TemplateHandler templateHandler;

	public TemplateHandler getTemplateHandler() {
		return templateHandler;
	}

	@Autowired
	public void setTemplateHandler(TemplateHandler templateHandler) {
		this.templateHandler = templateHandler;
	}

	public HashMap<String, String> loadTemplate(String triggerName, String workLocation, TravelInfo travelInfo)
			throws Exception {
		HashMap<String, String> contentMap = new HashMap<String, String>();
		Template bodyTemplate = templateHandler.getTemplate("body", triggerName);
		String mailContent[] = templateHandler.populateData(bodyTemplate, travelInfo);
		if (mailContent.length > 0) {
			contentMap.put("body", mailContent[1].replace("MailBody:", ""));
			contentMap.put("subject", mailContent[0].replace("MailSubject:", ""));
		}
		return contentMap;
	}

}
