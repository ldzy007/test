package cn.jboa.service;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;

public interface MailService {
	public void sendMail(String fromMail, String toMail,String mailTitle, Map tempData) throws MessagingException,IOException;
}
