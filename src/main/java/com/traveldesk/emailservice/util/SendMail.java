package com.traveldesk.emailservice.util;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.traveldesk.emailservice.beans.Mail;

@Component
public class SendMail {

	@Autowired
	Session session = null;

	public SendMail() {
		// TODO Auto-generated constructor stub
		System.out.println("Send Mail object created");
	}

	public boolean sendMail(Mail mail) {

		boolean result = false;

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getFormAddress()));
			String toAddressString = "";
			for (String toAddress : mail.getToAddresses()) {
				toAddressString += toAddress + ",";
			}
			toAddressString = new String(toAddressString.substring(0, toAddressString.length() - 1));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddressString));
			message.setSubject(mail.getSubject());

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mail.getBody(), "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			if (mail.getAttachments() != null && !mail.getAttachments().isEmpty()) {

				for (String attachment : mail.getAttachments()) {

					messageBodyPart = new MimeBodyPart();

					File file = new File(attachment);

					DataSource source = new FileDataSource(file);

					messageBodyPart.setDataHandler(new DataHandler(source));

					messageBodyPart.setFileName(file.getName());
					multipart.addBodyPart(messageBodyPart);
				}

			}

			message.setContent(multipart, "text/html; charset=utf-8");

			Transport.send(message);

			System.out.println("Done");

			result = true;

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		return result;

	}

}