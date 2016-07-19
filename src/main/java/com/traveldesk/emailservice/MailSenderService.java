package com.traveldesk.emailservice;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.traveldesk.emailservice.appconfig.ApplicationConfig;
import com.traveldesk.emailservice.beans.Mail;
import com.traveldesk.emailservice.beans.TravelInfo;
import com.traveldesk.emailservice.util.Constants;
import com.traveldesk.emailservice.util.SendMail;

@Component
public class MailSenderService {
	private static TemplateController templateController;
	private static SendMail sendMail;
	private static final Logger logger = Logger.getLogger(MailSenderService.class);

	public TemplateController getTemplateController() {
		return templateController;
	}

	@Autowired
	public void setSendMail(SendMail sendMail) {
		this.sendMail = sendMail;
	}

	@Autowired
	public void setTemplateController(TemplateController templateController) {
		this.templateController = templateController;
	}

	public void sendMail(String requestType, TravelInfo travelInfo) throws Exception {

		logger.debug("Request type :" + requestType);
		String eventsPropertyName = "dl." + Constants.EVENT + "." + requestType + "." + Constants.MAILS + "."
				+ Constants.TRIGGERS;
		String editString = "";
		if (requestType.equalsIgnoreCase("edit")) {
			if (travelInfo.isEmployee()) {
				eventsPropertyName += "." + Constants.EMPLOYEE;
				editString = "." + Constants.EMPLOYEE;
			} else {
				eventsPropertyName += "." + Constants.NONEMPLOYEE;
				editString = "." + Constants.NONEMPLOYEE;
			}
		}
		logger.debug("property name for triggers :" + eventsPropertyName);

		String eventsString = ApplicationConfig.getPropertyValue(eventsPropertyName);
		String[] events = null;
		if (eventsString.indexOf(",") > -1) {
			events = eventsString.split(",");
		} else {
			events = new String[] { eventsString };
		}
		if (events.length > 0) {
			for (int i = 0; i < events.length; i++) {
				String triggerName = null;
				String email = null;
				StringBuilder propertyName = new StringBuilder();

				logger.debug("event triggered is :" + events[i]);
				propertyName.append("dl.");
				propertyName.append(Constants.EVENT);
				propertyName.append(".");
				propertyName.append(requestType);
				propertyName.append(".");
				propertyName.append(Constants.MAILS);
				propertyName.append(".");
				propertyName.append(Constants.TRIGGER);

				switch (events[i].trim()) {
				case Constants.AM:

					propertyName.append(editString);
					propertyName.append(".");
					propertyName.append(Constants.AM);

					email = travelInfo.getApprovingManagerEmail();
					logger.debug("In Approving manager block");
					break;
				case Constants.EMPLOYEE:

					propertyName.append(".");
					propertyName.append(Constants.EMPLOYEE);
					propertyName.append(editString);

					email = travelInfo.getEmployee().getEmailId();
					logger.debug("In Employee block");
					break;
				case Constants.TD:
					StringBuilder emailPropertyName = new StringBuilder();
					email = ApplicationConfig
							.getPropertyValue("dl." + Constants.CLIENT + "." + travelInfo.getClientName());
					String billedTo = travelInfo.getBilledTo();

					if (billedTo != null && !billedTo.isEmpty() && billedTo.equalsIgnoreCase("client")
							&& (email != null || email != "")) {

						propertyName.append(".");
						propertyName.append(Constants.EMPLOYEE);
						propertyName.append(".");
						propertyName.append(Constants.TD);

					} else if (travelInfo.isEmployee()) {

						propertyName.append(".");
						propertyName.append(Constants.EMPLOYEE);
						propertyName.append(".");
						propertyName.append(Constants.TD);

						if (travelInfo.isTicketChecked()) {

							emailPropertyName.append("dl").append(".").append(Constants.EMPLOYEE).append(".")
									.append(Constants.TICKET).append(".")
									.append(travelInfo.getWorkLocation().toLowerCase());
						} else {

							emailPropertyName.append("dl").append(".").append(Constants.EMPLOYEE).append(".")
									.append(Constants.NONTICKET).append(".")
									.append(travelInfo.getWorkLocation().toLowerCase());
						}
					} else {

						propertyName.append(".");
						propertyName.append(Constants.NONEMPLOYEE);
						propertyName.append(".");
						propertyName.append(Constants.TD);

						if (travelInfo.isTicketChecked()) {

							emailPropertyName.append("dl").append(".").append(Constants.NONEMPLOYEE).append(".")
									.append(Constants.TICKET).append(".")
									.append(travelInfo.getWorkLocation().toLowerCase());
						} else {

							emailPropertyName.append("dl").append(".").append(Constants.NONEMPLOYEE).append(".")
									.append(Constants.TICKET).append(".")
									.append(travelInfo.getWorkLocation().toLowerCase());
						}
					}
					email = ApplicationConfig.getPropertyValue(emailPropertyName.toString());
					logger.debug("In travel desk block");
					break;
				case Constants.TRAVEL_AGENT:

					if (travelInfo.isEmployee()) {
						triggerName = ApplicationConfig.getPropertyValue("dl." + Constants.EVENT + "." + requestType
								+ "." + Constants.MAILS + "." + Constants.TRIGGER + "." + Constants.TICKET + "."
								+ Constants.EMPLOYEE + "." + Constants.TRAVEL_AGENT);
						propertyName.append(".");
						propertyName.append(Constants.TICKET);
						propertyName.append(".");
						propertyName.append(Constants.EMPLOYEE);
						propertyName.append(".");
						propertyName.append(Constants.TRAVEL_AGENT);

						if (travelInfo.isTicketChecked()) {
							email = ApplicationConfig.getPropertyValue("dl." + Constants.TRAVEL_AGENT.toLowerCase());
						} else {
							continue;
						}
					} else {
						propertyName.append(".");
						propertyName.append(Constants.TICKET);
						propertyName.append(".");
						propertyName.append(Constants.NONEMPLOYEE);
						propertyName.append(".");
						propertyName.append(Constants.TRAVEL_AGENT);
						if (travelInfo.isTicketChecked()) {
							email = ApplicationConfig.getPropertyValue("dl." + Constants.TRAVEL_AGENT.toLowerCase());
						} else {
							continue;
						}
					}
					logger.debug("In travel agent block");
					break;
				}
				triggerName = ApplicationConfig.getPropertyValue(propertyName.toString());
				logger.debug("Trigger Name :" + triggerName);
				logger.debug("Email :" + email);
				if (triggerName != null && email != null) {

					HashMap<String, String> contentMap = templateController.loadTemplate(triggerName,
							travelInfo.getWorkLocation(), travelInfo);

					Mail mail = new Mail();
					mail.setFormAddress("sknagulmeera06@gmail.com");
					mail.getToAddresses().add("nagulmeera.shaik@imaginea.com");
					mail.setSubject(contentMap.get("subject").trim());
					mail.setBody(contentMap.get("body").trim());

					logger.debug(mail.toString());

					sendMail.sendMail(mail);

				} else {
					logger.error("Trigger name or email is null");
					logger.error("Trigger Name :" + triggerName);
					logger.error("Email ID:" + email);

					throw new Exception(
							MailSenderService.class.getName() + ":" + "Trigger Name or Email should not be empty");

				}
			}
		} else {
			logger.error("No triggers found for request:" + requestType);

			throw new Exception(MailSenderService.class.getName() + ":" + "No Triggers found on given request");
		}
	}
}
