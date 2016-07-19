package com.pramati.emailsender.beans;

import java.util.ArrayList;
import java.util.List;

public class Mail {

    private String formAddress;
    private List<String> toAddresses;
    
    private String subject;
    private String body;
    private List<String> attachments;
    
    
    public Mail() {
        // TODO Auto-generated constructor stub
    }

    public String getFormAddress() {
        return formAddress;
    }

    public void setFormAddress(String formAddress) {
        this.formAddress = formAddress;
    }

    public List<String> getToAddresses() {
    	if(toAddresses == null){
    		toAddresses = new ArrayList<String>();
    	}
        return toAddresses;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return "Mail [formAddress=" + formAddress + ", toAddresses=" + toAddresses + ", subject=" + subject + ", body="
				+ body + ", attachments=" + attachments + "]";
	}
    
	
    
}