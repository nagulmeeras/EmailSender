package com.traveldesk.emailservice;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveldesk.emailservice.beans.TravelInfo;

@RestController
public class ApplicationInitializer {

	private static DataParser dataParser;
	private static MailSenderService mailSenderService;
	private static final Logger logger = org.apache.log4j.Logger.getLogger(ApplicationInitializer.class);

	@Autowired
	public void setDataParser(DataParser dataParser) {
		ApplicationInitializer.dataParser = dataParser;
	}

	@Autowired
	public void setMailSenderService(MailSenderService mailSenderService) {
		ApplicationInitializer.mailSenderService = mailSenderService;
	}

	@RequestMapping("/")
	public void test() {

		try {
			HashMap<String, String> input = new HashMap<String, String>();
			TravelInfo travelInfo = dataParser.parseRequestData(input);
			String requestType = "new";
			logger.debug("Initailzer starts");
			System.out.println("Coming ");
			mailSenderService.sendMail(requestType, travelInfo);
			logger.debug("Initialiser stops");
			System.out.println("ends");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
