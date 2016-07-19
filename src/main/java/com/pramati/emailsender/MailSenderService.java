package com.pramati.emailsender;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pramati.emailsender.appconfig.ApplicationConfig;
import com.pramati.emailsender.beans.Mail;
import com.pramati.emailsender.beans.TravelInfo;
import com.pramati.emailsender.util.Constants;
import com.pramati.emailsender.util.SendMail;

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

		String eventsString = ApplicationConfig.getPropertyValue(eventsPropertyName);
		System.out.println("events " + eventsString);
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
					break;
				case Constants.EMPLOYEE:

					propertyName.append(".");
					propertyName.append(Constants.EMPLOYEE);
					propertyName.append(editString);

					email = travelInfo.getEmployee().getEmailId();
					break;
				case Constants.TD:
					StringBuilder emailPropertyName = new StringBuilder();
					if (travelInfo.getBilledTo().equals("client")) {

						propertyName.append(".");
						propertyName.append(Constants.EMPLOYEE);
						propertyName.append(".");
						propertyName.append(Constants.TD);

						email = ApplicationConfig
								.getPropertyValue("dl." + Constants.CLIENT + "." + travelInfo.getClientName());
						emailPropertyName.append("dl");
						emailPropertyName.append(".");
						emailPropertyName.append(Constants.CLIENT);
						emailPropertyName.append(".");
						emailPropertyName.append(travelInfo.getClientName());

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
					break;
				}
				triggerName = ApplicationConfig.getPropertyValue(propertyName.toString());
				logger.debug("trigger Name :" + triggerName);
				logger.debug("email :" + email);
				if (triggerName != null && email != null) {

					HashMap<String, String> contentMap = templateController.loadTemplate(triggerName,
							travelInfo.getWorkLocation(), travelInfo);

					Mail mail = new Mail();
					mail.setFormAddress("sknagulmeera06@gmail.com");
					mail.getToAddresses().add("nagulmeera.shaik@imaginea.com");
					mail.setSubject(contentMap.get("subject").trim());
					mail.setBody(contentMap.get("body").trim());

					// sendMail.sendMail(mail);
				} else {
					throw new Exception(
							MailSenderService.class.getName() + ":" + "Trigger Name or Email should not be empty");
				}
			}
		}else{
			throw new Exception(MailSenderService.class.getName() + ":" + "No Triggers found on given request");
		}
	}
}
